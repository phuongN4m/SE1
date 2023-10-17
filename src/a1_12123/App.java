package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * A simple document search engine
 */
public class App {
    public static void main(String[] args) throws FileNotFoundException {

        /*
         * Load stop words into set stopWords
         */
        // a boolean to check whether stop words are loaded from the file stopwords2.txt successfully or not
        boolean canLoadStopWords = Word.loadStopWords("stopwords2.txt");
        // if loaded completely
        if (canLoadStopWords) {
            // return incorrect since there is no file named stopwords2.txt
            System.out.println("Word.loadStopWords(): incorrect return value (expected: false) : " + canLoadStopWords);
        }
        // update the boolean to check whether stop words are loaded from the file stopwords.txt successfully or not
        canLoadStopWords = Word.loadStopWords("stopwords.txt");
        // if failed to load
        if (!canLoadStopWords) {
            // throw exception
            System.out.println("Word.loadStopWords(): incorrect return value (expected: true)" + canLoadStopWords);
        }
        // check if size of set stopWords is not equal to the number of words in the file
        if (Word.stopWords.size() != 174) {
            // throw exception
            System.out.println("Word.loadStopWords(): incorrect number of stopWords loaded (expected: 174)" + Word.stopWords.size());
        }


        // create a new Engine obj
        Engine e = new Engine();
        // docs folder contains: 1.txt, 2.txt... 10.txt
        // get the number of docs loaded
        int loadedDocs = e.loadDocs("docs"); // takes folder name as input
        // check if docs are loaded enough
        if (loadedDocs != 10) System.out.println("Word.loadDocs(): incorrect return value (expected: 10) : " + loadedDocs);

        /*
         * check if a word is a keyword or a stopWord
         * testing the isKeyword(), getPrefix(), getSuffix() and getText() functions
         */
        if (Word.createWord("").isKeyword())
            System.out.println("Word.createWord(): empty string ('') should be an invalid word (not a keyword)");
        if (Word.createWord("123456").isKeyword())
            System.out.println("Word.createWord(): '123456' should be an invalid word (not a keyword)");
        if (Word.createWord("!@#$%^").isKeyword())
            System.out.println("Word.createWord(): '!@#$%^' should be an invalid word (not a keyword)");
        if (Word.createWord("se2021").isKeyword())
            System.out.println("Word.createWord(): 'se2021' should be an invalid word (not a keyword)");
        if (Word.createWord(" and").isKeyword())
            System.out.println("Word.createWord(): ' and' should be treated as an invalid word (not a keyword because it contains a space)");
        if (Word.createWord(",se2021.").isKeyword())
            System.out.println("Word.createWord(): ',se2021.' should be an invalid word");
        if (Word.createWord("the").isKeyword())
            System.out.println("Word.isKeyword(): 'the' is a stop word (not a keyword)");
        if (Word.createWord("of").isKeyword())
            System.out.println("Word.isKeyword(): 'of' is a stop word (not a keyword)");
        if (!Word.createWord("context").isKeyword())
            System.out.println("Word.isKeyword(): 'context' should be a keyword");
        if (!Word.createWord("design").isKeyword())
            System.out.println("Word.isKeyword(): 'design' should be a keyword");
        if (!Word.createWord(",se2021.").getText().equals(",se2021."))
            System.out.println("Word.createWord(): the text part of ',se2021.' should be ',se2021.'");
        if (!Word.createWord(",se2021.").getPrefix().equals(""))
            System.out.println("Word.createWord(): the prefix of ',se2021.' should be empty");
        if (!Word.createWord(",se2021.").getSuffix().equals(""))
            System.out.println("Word.createWord(): the suffix of ',se2021.' should be empty");
        if (!Word.createWord("word,").getText().equals("word"))
            System.out.println("Word.createWord(): the text part of 'word,' should be 'word'");
        if (!Word.createWord("word,").getPrefix().equals(""))
            System.out.println("Word.createWord(): the prefix of 'word,' should be empty");
        if (!Word.createWord("word,").getSuffix().equals(","))
            System.out.println("Word.createWord(): the suffix of 'word,' should be ','");
        if (!Word.createWord("«word»").getText().equals("word"))
            System.out.println("Word.createWord(): the text part of '«word»' should be 'word'");
        if (!Word.createWord("«WORD»").getPrefix().equals("«"))
            System.out.println("Word.createWord(): the prefix of '«WORD»' should be '«'");
        if (!Word.createWord("«Word»").getSuffix().equals("»"))
            System.out.println("Word.createWord(): the prefix of '«Word»' should be '»'");
        if (!Word.createWord("apple").equals(Word.createWord("apple")))
            System.out.println("Word.equals() failed with case 'apple'");
        if (!Word.createWord("apple").equals(Word.createWord("Apple")))
            System.out.println("Word.equals() should be case-insensitive, so 'apple' should be equal to 'Apple'");
        if (!Word.createWord("content").equals(Word.createWord("\"content\".")))
            System.out.println("Word.equals() should compare the text part only, so 'content' should be equal to '\"content\".'");

        // create a document object with content = title + body
        Doc d = new Doc("Object-oriented \"design\": with UML's diagrams\n" +
                "Definition: An object-oriented system's context made up of (interacting) objects.");
        // create a query obj
        Query q = new Query("the <context> of observer: design");

        // Testing Doc & Query
        // create an array of objects, including the title and the body of the document and the keywords from the query
        Object[] tests = {d.getTitle(), d.getBody(), q.getKeywords()};
        // a list of sizes of sth (maybe number of words in the title, body and number of keywords
        int[] listSizes = {5, 10, 3};
        // an array of method name
        String[] methods = {"Doc.getTitle()", "Doc.getBody()", "Query.getKeywords()"};
        // a 2D array of objects known as the word part of the text
        Object[] wordTexts = new Object[3];
        wordTexts[0] = new String[]{"Object-oriented", "design", "with", "UML", "diagrams"};
        wordTexts[1] = new String[]{"Definition", "An", "object-oriented", "system", "context", "made", "up", "of", "interacting", "objects"};
        wordTexts[2] = new String[]{"context", "observer", "design"};
        // a 2D array of prefixes
        Object[] wordPrefixes = new Object[3];
        wordPrefixes[0] = new String[]{"", "\"", "", "", ""};
        wordPrefixes[1] = new String[]{"", "", "", "", "", "", "", "", "(", ""};
        wordPrefixes[2] = new String[]{"<", "", ""};
        // a 2D array of suffixes
        Object[] wordSuffixes = new Object[3];
        wordSuffixes[0] = new String[]{"", "\":", "", "'s", ""};
        wordSuffixes[1] = new String[]{":", "", "", "'s", "", "", "", "", ")", "."};
        wordSuffixes[2] = new String[]{">", ":", ""};
        // a 2D array of boolean values whether a word is a keyword or not
        Object[] wordTypes = new Object[3]; // if a word is keyword or not
        wordTypes[0] = new boolean[]{true, true, false, true, true};
        wordTypes[1] = new boolean[]{true, false, true, true, true, true, false, false, true, true};
        wordTypes[2] = new boolean[]{true, true, true};

        // loop thr the length of tests array
        for (int x = 0; x < tests.length; x++) {
            // create a tmp list that store the word from each element in the tests array
            List<Word> tmp = (List<Word>) tests[x];
            // an array of word text get from each wordTexts array element
            String[] wtxt = (String[]) wordTexts[x];
            // arr of prefixes
            String[] wpf = (String[]) wordPrefixes[x];
            // array of suffixes
            String[] wsf = (String[]) wordSuffixes[x];
            // array of wordTypes
            boolean[] wtp = (boolean[]) wordTypes[x];
            // check if the tmp list do not have enough elements
            if (tmp.size() != listSizes[x]) {
                // throw exception
                System.out.println(methods[x] + ": unexpected list length");
            } else {
                // if not, loop thr the tmp list
                for (int i = 0; i < tmp.size(); i++) {
                    /*
                     * compare each part of each text from the 2D array above with each part of each text from the tmp file
                     */
                    if (!wtxt[i].equals(tmp.get(i).getText()))
                        System.out.println(methods[x] + ": incorrect word text '" + tmp.get(i).getText() + "' (expected '" + wtxt[i] + "')");
                    if (!wpf[i].equals(tmp.get(i).getPrefix()))
                        System.out.println(methods[x] + ": incorrect word prefix '" + tmp.get(i).getPrefix() + "' (expected '" + wpf[i] + "')");
                    if (!wsf[i].equals(tmp.get(i).getSuffix()))
                        System.out.println(methods[x] + ": incorrect word suffix '" + tmp.get(i).getSuffix() + "' (expected '" + wsf[i] + "')");
                    if (wtp[i] != tmp.get(i).isKeyword())
                        System.out.println(methods[x] + ": incorrect isKeyword for '" + tmp.get(i).toString() + "' (expected: " + wtp[i] + ")");
                }
            }
        }
        // Testing Query & Match
        // a list of matches against the input doc
        List<Match> matches = q.matchAgainst(d);
        // an array of matched words for testing
        String[] matchedWords = {"design", "context"};
        // check if matches count is correct
        if (matches.size() != 2) {
            // throw exception
            System.out.println("Query.matchAgainst(): incorrect matches count (expected: 2)");
        }
        // loop thr the matches list
        for (int i = 0; i < matches.size(); i++) {
            // compare the element of the list of matches against the doc with the matchedWord array's elements
            if (!matches.get(i).getWord().getText().equals(matchedWords[i])) {
                // throw exception
                System.out.println("Query.matchAgainst(): incorrect word '" + matches.get(i).getWord().getText() + " (expected: '" + matchedWords[i] + "')");
            }
        }

        // Testing Result
        // a list of sorted search result
        List<Result> results = e.search(q);
        // check if enough results are found
        if (results.size() != 8) System.out.println("Engine.search(): incorrect results count");
        // an array of correct match counts
        int[] matchCounts = {2, 1, 1, 1, 1, 1, 1, 1};
        // loop thr the results list
        for (int i = 0; i < results.size(); i++) {
            // get the number of matched objects in the list of results
            int x = results.get(i).getMatches().size();
            // check if enough matches count
            if (x != matchCounts[i]) {
                // throw exception
                System.out.println("Engine.search(): incorrect match count (actual: " + x + ", expected: " + matchCounts[i]);
            }
        }
        // get the title of the document in the first result
        String tmpTitle = results.get(0).getDoc().getTitle().toString();
        // check if correct
        if (!tmpTitle.equals("[System, context, and, interactions]")) {
            System.out.println("Engine.search(): incorrect first result '" + tmpTitle + "' (expected '[System, context, and, interactions]'");
        }
        // update the tmpTitle to be the title of the document in the second result
        tmpTitle = results.get(1).getDoc().getTitle().toString();
        // check if correct
        if (!tmpTitle.equals("[Design, patterns]")) {
            System.out.println("Engine.search(): incorrect second result '" + tmpTitle + "' (expected '[Design, patterns]'");
        }

        // Testing HTML highlights
        Scanner sc = new Scanner(new File("testCases.html"));
        String firstResultHTML = sc.nextLine();
        if (!firstResultHTML.equals(results.get(0).htmlHighlight().trim())) {
            System.out.println("Result.htmlHighlight(): incorrect output for first result");
        }
        String secondResultHTML = sc.nextLine();
        if (!secondResultHTML.equals(results.get(1).htmlHighlight().trim())) {
            System.out.println("Result.htmlHighlight(): incorrect output for second result");
        }
        String html = e.htmlResult(results).trim(); // ranked result in simple HTML format
        String expectedHTML = sc.nextLine();
        if (!html.equals(expectedHTML)) {
            System.out.println("Engine.htmlResult(): incorrect output");
        }
    }
}