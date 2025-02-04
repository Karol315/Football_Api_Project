package ScrapeModule.scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


public class JSoupConnection {

    private final String url;

    public JSoupConnection(String url) {
        this.url = url;
    }

    public Document getDocument() throws IOException {
        return Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .header("Accept-Language", "en-US,en;q=0.9").header("Connection", "keep-alive").timeout(20_000).get();
    }
}
