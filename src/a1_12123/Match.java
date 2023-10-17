package engine;

/**
 * A Match represents a situation in which a Doc contains a Word
 */
public class Match implements Comparable<Match> {

    /**
     * private attributes
     */
    private Doc document;
    private Word word;
    private int frequency;
    private int firstIndex;

    /**
     * A constructor of class Match
     * @param d
     * @param w
     * @param freq
     * @param firstIndex
     * @effects initialize a Match Object
     */
    public Match(Doc d, Word w, int freq, int firstIndex) {
        this.document = d;
        this.word = w;
        this.frequency = freq;
        this.firstIndex = firstIndex;
    }

    /**
     *
     * @return the number of times the Word appears in the Doc
     */
    public int getFreq() {
        return this.frequency;
    }

    /**
     *
     * @return the first position which the Word appears
     */
    public int getFirstIndex() {
        return this.firstIndex;
    }

    /**
     *
     * @param o the object to be compared.
     * @return
     * @effects compare this Match object with another Match by their first index
     */
    @Override
    public int compareTo(Match o) {
        return Integer.compare(this.firstIndex, o.getFirstIndex());
    }

    /**
     *
     * @return a Word object
     */
    public Word getWord() {
        return word;
    }

/*
    public static void main(String[] args) {
        // Creating Example Words
        Word word1 = Word.createWord("apple");
        Word word2 = Word.createWord("banana");
        Word word3 = Word.createWord("cherry");

        // Creating Example Matches
        Match match1 = new Match(new Doc("doc1\nLine1"), word1, 5, 10);
        Match match2 = new Match(new Doc("doc2\nLine2"), word2, 3, 20);
        Match match3 = new Match(new Doc("doc3\nLine3"), word3, 2, 30);

        System.out.println(match1.getFirstIndex());
        System.out.println(match2.compareTo(match3));
    }
    */
}
