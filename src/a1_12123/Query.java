package engine;

import java.util.ArrayList;
import java.util.List;

/**
 * Query is the class to represent a user’s search query,
 * A Query object should store a list of keywords internally.
 */
public class Query {

    private List<Word> keywords;

    /**
     * A constructor
     * @param searchPhrase
     * @effects extract keywords from search phrases
     */
    public Query (String searchPhrase) {
        this.keywords = extractKeywords(searchPhrase);
    }

    /**
     *
     * @return a list of the query's keywords in order with their appearances in raw text
     */
    public List<Word> getKeywords() {
        return this.keywords;
    }

    /**
     *
     * @param d
     * @return a list of matches against the input document
     * @requires matches are sort by the first position of keywords in d
     */
    public List<Match> matchAgainst(Doc d) {
        List<Match> matches = new ArrayList<>();
        // get content of the document
        List<Word> title = d.getTitle();
        List<Word> body = d.getBody();
        // loop to find the keyword in the content document
        for (Word keyword : keywords) {
            int titleFrequency = countWordFrequency(title, keyword);
            int bodyFrequency = countWordFrequency(body, keyword);
            if (titleFrequency > 0 || bodyFrequency > 0) {
                int titlePosition = findWordPosition(title, keyword);
                int bodyPosition = findWordPosition(body, keyword);
                if (titlePosition < 1 || bodyPosition < 1) {
                    matches.add(new Match(d, keyword, titleFrequency + bodyFrequency, Math.max(titlePosition, bodyPosition)));
                } else {
                    matches.add(new Match(d, keyword, titleFrequency + bodyFrequency, Math.min(titlePosition, bodyPosition)));
                }
            }
        }
        matches.sort((m1, m2) -> m1.getFirstIndex() - m2.getFirstIndex());
        return matches;
    }

    /**
     *
     * @param searchPhrase
     * @return a list of keywords
     */
    private List<Word> extractKeywords(String searchPhrase) {
        // split the search phrase into individual words
        String[] words = searchPhrase.split(" ");
        List<Word> keywords = new ArrayList<>();
        // iterate through the words and create Word objects from them
        for (String word : words) {
            if (!word.isEmpty()) {
                Word keyword = Word.createWord(word);
                // check if it is keyword
                if (keyword.isKeyword()) {
                    keywords.add(keyword);
                }
            }
        }
        return keywords;
    }

    /**
     *
     * @param wordList
     * @param word
     * @return the frequency of a word in a list of word
     */
    private int countWordFrequency(List<Word> wordList, Word word) {
        int frequency = 0;
        for (Word w : wordList) {
            if (w.equals(word)) {
                frequency++;
            }
        }
        return frequency;
    }

    /**
     *
     * @param wordList
     * @param word
     * @return the position of a word in a word list
     */
    private int findWordPosition(List<Word> wordList, Word word) {
        for (int i = 0; i < wordList.size(); i++) {
            Word currentWord = wordList.get(i);
            if (currentWord.equals(word)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Testing method
     *

     public static void main(String[] args) {
     Word.loadStopWords("stopwords.txt");
     Word word1 = Word.createWord("apple");
     Word word2 = Word.createWord("banana");
     Word word3 = Word.createWord("cherry");

     Doc doc1 = new Doc("Hello world\nThis is apple, banana");
     Doc doc2 = new Doc("Hello world\nThis is cherry, banana, apple, apple, app");
     Doc doc3 = new Doc("Hello world\nThis is apple, banana and cherry");

     Query query = new Query("apple banana cherry");

     System.out.println(query.getKeywords());
     // Matching the query against documents
     List<Match> matches1 = query.matchAgainst(doc1);
     List<Match> matches2 = query.matchAgainst(doc2);
     List<Match> matches3 = query.matchAgainst(doc3);

     // Printing the matches
     System.out.println("Matches in doc1:");
     printMatch(matches1);

     System.out.println("Matches in doc2:");
     printMatch(matches2);

     System.out.println("Matches in doc3:");
     printMatch(matches3);
     }

     private static void printMatch(List<Match> matches) {
     for (Match match : matches) {
     System.out.println("getWord(): "+match.getWord());
     System.out.println("getFreq(): "+match.getFreq());
     System.out.println("getFirstIndex(): "+match.getFirstIndex());
     }
     }  */
}