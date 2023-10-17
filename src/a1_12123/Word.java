package engine;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * The basic class to store each of these strings.
 * Word objects can later be merged together to obtain the original text
 */
public class Word {

    /**
     * A set of stop words loaded by the loadStopWords() method
     */
    public static Set<String> stopWords;

    private String prefix;
    private String suffix;
    private String text;

    /**
     * Constructor for Word objects
     * @param prefix the prefix part of the word
     * @param suffix the suffix part of the word
     * @param text the text part of the word
     */
    public Word(String prefix, String text, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.text = text;
    }

    /**
     *
     * @return whether a word is a keyword or not
     */
    boolean isKeyword() {
        return !stopWords.contains(text.toLowerCase()) && prefix.isEmpty() && suffix.isEmpty();
    }

    /**
     *
     * @return the prefix part of the word
     */
    public String getPrefix() {
        return this.prefix;
    }

    /**
     *
     * @return the suffix part of the word
     */
    public String getSuffix() {
        return this.suffix;
    }

    /**
     *
     * @return the text part of the word
     */
    public String getText() {
        return this.text;
    }

    /**
     *
     * @param o
     * @return whether two words are equal or not, case-insensitively based on their text part
     */
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Word)) {
            return false;
        }
        Word other = (Word) o;
        return this.text.equalsIgnoreCase(other.text) ;
    }

    /**
     *
     * @return the raw text of the word
     */
    @Override
    public String toString() {
        return this.prefix + this.text + this.suffix;
    }

    /**
     *
     * @param rawText
     * @return a complete Word object from raw text
     */
    public static Word createWord(String rawText) {
        String prefix = "";
        String text = "";
        String suffix = "";

        // Find the index of the first alphabetic character
        int start = 0;
        for (int i = 0; i < rawText.length(); i++) {
            if (Character.isLetter(rawText.charAt(i))) {
                start = i;
                break;
            }
        }

        // Extract the prefix
        prefix = rawText.substring(0, start);

        // Find the index of the last alphabetic character
        int end = rawText.length();
        for (int i = rawText.length() - 1; i > 0; i--) {
            if (Character.isLetter(rawText.charAt(i))) {
                end = i+1;
                // if the character before the last letter is not a letter
                if (i > 1 && !Character.isLetter(rawText.charAt(i-1))) {
                    // update the end position to be the position of that character
                    end = i-1; // need update
                    break;
                }
                break;
            }
        }

        // Extract the text
        text = rawText.substring(start, end);

        // Extract the suffix
        suffix = rawText.substring(end);

        return new Word(prefix, text, suffix);
    }

    /**
     *
     * @param fileName
     * @effects load the stop words into the set Word.stopWords from the file fileName.txt
     * @return whether the stop words are loaded successfully or not
     * Checked
     */
    public static boolean loadStopWords(String fileName) {
        // initialize the set of stop words as a HashSet -> not throwing NullPointerException
        stopWords = new HashSet<>();
        // Scan file stopwords.txt
        File stopWordFile = new File("stopwords.txt");
        try {
            // reader
            Scanner reader = new Scanner(stopWordFile);
            // loop through the file
            while (reader.hasNext()) {
                // add words to set
                String[] words = reader.nextLine().split("\\s+");
                Word.stopWords.addAll(Arrays.asList(words));
            }
            // close reader
            reader.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // if failed, return false
        return false;
    }

    public static void main(String[] args) {
        loadStopWords("stopwords.txt");
        Word w1 = Word.createWord("word");
        Word w2 = Word.createWord("word123");
        System.out.println("is keyword: " + w1.isKeyword());
        System.out.println("prefix: "+ w1.getPrefix());
        System.out.println("text: "+ w1.getText());
        System.out.println("suffix: "+  w1.getSuffix());
        System.out.println("2 word are equal? " + w1.equals(w2));
    }
}