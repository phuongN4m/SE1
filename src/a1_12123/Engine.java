package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *  This class represents the search engine
 */
public class Engine {

    /**
     * private attribute
     */
    private List<Doc> docs;

    /**
     * Constructor
     */
    public Engine() {
        // initialize the list of document -> avoid NullPointerException
        this.docs = new ArrayList<>();
    }

    /**
     *
     * @effects loads the documents from the specific folder
     * @param dirname
     * @return the number of documents loaded
     */
    public int loadDocs(String dirname) {
        File folder = new File(dirname);
        File[] files = folder.listFiles();
        if (files == null) {
            return 0;
        }

        int count = 0;
        for (File file : files) {
            String[] lines = new String[2];
            String content = "";
            if (file.isFile()) {
                try {
                    Scanner reader = new Scanner(file);
                    // add two lines into content of the doc
                    while (reader.hasNext()) {
                        for (int i = 0; i < lines.length; i++) {
                            lines[i] = reader.nextLine();
                        }
                    }
                    content += lines[0] + "\n" + lines[1];
                    Doc doc = new Doc(content);
                    docs.add(doc);
                    count++;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return count;
    }

    /**
     *
     * @return an array of documents in the original order
     */
    public Doc[] getDocs() {
        return docs.toArray(new Doc[0]);
    }

    /**
     * perform the search function of the engine
     * @param q
     * @return a list of sorted search results
     */
    public List<Result> search(Query q) {
        List<Result> results = new ArrayList<>();
        for (Doc doc : docs) {
            List<Match> matches = q.matchAgainst(doc);
            Result result = new Result(doc, matches);
            results.add(result);
        }
        results.sort(Result::compareTo);
        return results;
    }

    /**
     *
     * @param results
     * @effects converts a list of search results into HTML format
     * @return HTML format of a list of search results
     */
    public String htmlResult(List<Result> results) {
        if (results.isEmpty()) {
            return null;
        }
        String resultHTMl = "";
        for (Result result : results) {
            resultHTMl = result.htmlHighlight();
        }
        return resultHTMl;
    }

    public static void main(String[] args) {
        Engine e = new Engine();
        int docs = e.loadDocs("docs");
        System.out.println(docs);
    }
}