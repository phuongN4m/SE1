package engine;

import java.util.ArrayList;
import java.util.List;

/**
 * Doc is the class to represent a document which has a title and a body.
 * The title and body of a document are lists of Word objects
 */
public class Doc {
    /**
     * private attributes
     */
    private List<Word> title;
    private List<Word> body;
    private String content;

    /**
     * A constructor for class Doc
     * @param content - raw text of a document (.txt files)
     * @effects extract titles and body parts from the raw text
     */
    public Doc(String content) {
        this.content = content;
        // count the lines of the document, split it into an array of lines
        String[] lines = this.content.split("\n");
        // check if it has enough lines
        if (lines.length != 2) {
            throw new IllegalArgumentException("Invalid document content. Expected two lines.");
        }
        // get the title and the body
        this.title = convertToWordList(lines[0]);
        this.body = convertToWordList(lines[1]);
    }

    /**
     *
     * @return the document’s title as a list of Word objects.
     */
    public List<Word> getTitle(){
        return this.title;
    }

    /**
     *
     * @return the document’s body as a list of Word objects.
     */
    public List<Word> getBody(){
        return this.body;
    }

    /**
     * use equal() method from class Word
     * @param o
     * @return whether doc's titles and bodies contain the same words in the same order
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Doc other = (Doc) o;
        return title.equals(other.title) && body.equals(other.body);
    }

    /**
     *
     * @param text
     * @return a list of Word objects from a string
     */
    private List<Word> convertToWordList(String text) {
        List<Word> wordList = new ArrayList<>();
        String[] words = text.split(" ");
        for (String word : words) {
            wordList.add(Word.createWord(word));
        }
        return wordList;
    }


    public static void main(String[] args) {
        // Sample document content
        String content = "Hello world!\nThis is a test document.";
        String content2 = "Hello world!\nThis a is document test.";

        //
        Doc d = new Doc("Object-oriented \"design\": with UML's diagrams\n" +
                "Definition: An object-oriented system's context made up of (interacting) objects.");
        System.out.println("Title: ");
        List<Word> title = d.getTitle();
        List<Word> body = d.getBody();

        System.out.println("Title: " + title);
        System.out.println("Body: " + body);

        /*
        // Create a new document
        Doc document = new Doc(content);
        Doc doc2 = new Doc(content2);
        // Print the title
        System.out.println("Title:");
        List<Word> title = document.getTitle();
        for (Word word : title) {
            System.out.println(word.toString());
        }

        // Print the body
        System.out.println("Body:");
        List<Word> body = document.getBody();
        for (Word word : body) {
            System.out.println(word.toString());
        }

        System.out.println(document.equals(doc2));

     */
    }
}