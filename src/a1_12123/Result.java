package engine;

import java.util.List;

/**
 * A Result object stores information about a related a document, a list of matches found in that document
 * and three derived properties: match count, total frequency, average first index
 */
public class Result implements Comparable<Result> {

    /**
     * private attributes
     */
    private Doc d;
    private List<Match> matches;

    /**
     * A constructor to initialize a Result object with the related document and the list of matches
     * @param d
     * @param matches
     */
    public Result(Doc d, List<Match> matches) {
        this.d = d;
        this.matches = matches;
    }

    /**
     *
     * @return the document that contains matches
     */
    public Doc getDoc() {
        return this.d;
    }

    /**
     *
     * @return a list of Matches objects
     */
    public List<Match> getMatches() {
        return this.matches;
    }

    /**
     *
     * @return the sum of all frequencies of the matches
     */
    public int getTotalFrequency() {
        int totalFrequency = 0;
        for (Match match : matches) {
            totalFrequency += match.getFreq();
        }
        return totalFrequency;
    }

    /**
     *
     * @return the average of the first indexes of the matches
     */
    public double getAverageFirstIndex() {
        if (matches.isEmpty()) {
            return 0.0;
        }
        int total = 0;
        for (Match match : matches) {
            total += match.getFirstIndex();
        }
        return (double) total / matches.size();
    }

    /**
     *
     * @return the matched words being highlighted in the document using HTML markups
     * @requires <pre> if Matches are in title, it will be underlined
     * if Matches are in body, it will be bold </pre>
     */
    public String htmlHighlight() {
        // a result string
        StringBuilder result = new StringBuilder();
        // get the title and body of the document
        List<Word> title = this.d.getTitle();
        List<Word> body = this.d.getBody();
        // loop through the list of matches
        for (Match match : matches) {
            // get the matched word
            Word matchedWord = match.getWord();
            // get the text part of the word
            String text = matchedWord.getText();
            // highlight it
            if (title.contains(matchedWord)) {
                text = "<u>" + text + "</u>";
            }
            if (body.contains(matchedWord)) {
                text = "<b>" + text + "</b>";
            }
            // add parts of the word to the result
            result.append(matchedWord.getPrefix()).append(text).append(matchedWord.getSuffix());
        }
        return result.toString();
    }

    /**
     *
     * @param o the object to be compared.
     * @return whether Result A is greater than Result B, based on match count, total frequency, avg first index
     *
     */
    @Override
    public int compareTo(Result o) {
        int matchesSizeComp = Integer.compare(this.matches.size(), o.matches.size());
        int totalFreqComp = Integer.compare(this.getTotalFrequency(), o.getTotalFrequency());
        double avgFirstIndexComp = Double.compare(this.getAverageFirstIndex(), o.getAverageFirstIndex());
        if (matchesSizeComp != 0) {
            return matchesSizeComp;
        } else if (totalFreqComp != 0) {
            return totalFreqComp;
        } else if (avgFirstIndexComp != 0.0) {
            return (int) avgFirstIndexComp;
        } else return 0;
    }
}