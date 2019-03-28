import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {

        /*
         * Open a csv file for writing.
         * Open a XML file for reading.
         * Serialize the XML to CSV (parse then...).
         */

        File xmlInput = new File("bookstore2_7633.xml");
        Path fCSVOutput = Paths.get("./bookstore2_7633.csv");

        Document xmldoc = Jsoup.parse(xmlInput, "UTF-8", "");

        List<String> csvList = new ArrayList<>(List.of("isbn, title, publisher, author, store, location"));

        Elements books = xmldoc.select("book");
        for (Element book : books) {

            List<String> currentBook = List.of(
                book.attr("isbn"),
                book.select("title").text(),
                book.select("publisher").text(),
                book.select("author").text(),
                book.select("store").text(),
                book.select("location").text());
            //LOGGER.log(Level.INFO, String.join(",", currentBook));
            csvList.add(String.join(",", currentBook));
        }
        Files.write(fCSVOutput, csvList, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
