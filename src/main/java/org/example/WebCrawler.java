package org.example;
import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
public class WebCrawler {

    public static WebCrawlerData getPageLinks(String URL) {
        ArrayList<String> links = new ArrayList<>();
        WebCrawlerData webCrawlerData=null;
        try {
            Document document = Jsoup.connect(URL).get();
            Elements linksOnPage = document.select("a[href]");
            int counter=0;
            for (Element page : linksOnPage) {
                counter++;
                String link = page.attr("abs:href");
                if (!links.contains(link)) {
                    links.add(link);
                }
            }
            webCrawlerData=new WebCrawlerData(counter,links.size(),links);
            System.out.println(webCrawlerData);


        } catch (IOException e) {
            System.err.println("For '" + URL + "': " + e.getMessage());
        }
        return webCrawlerData;
    }




}
