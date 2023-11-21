package org.example;
import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
public class WebCrawler {
    public static WebCrawlerData getUrlInfo(String url){
        WebCrawlerData webCrawlerData=new WebCrawlerData(-1,-1, new ArrayList<>(),null);
        getPageLinks(url,webCrawlerData);
        String language = getLanguageFromHTML(url);
        webCrawlerData.setLanguage(language);
        return webCrawlerData;
    }
    private static void getPageLinks(String URL,WebCrawlerData webCrawlerData) {
        ArrayList<String> links = new ArrayList<>();
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
            webCrawlerData.setIterationsCount(counter);
            webCrawlerData.setUniqueLinksCount(links.size());
            webCrawlerData.setLinksList(links);
            System.out.println(webCrawlerData);

        } catch (IOException e) {System.err.println("For '" + URL + "': " + e.getMessage());}
    }
    private static String getLanguageFromHTML(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Element htmlTag = document.select("html").first();
            return htmlTag.attr("lang"); // Returns the value of the lang attribute
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private static String extractDomainInfo(String urlString) {
        try {
            URL url = new URL(urlString);
            String host = url.getHost();
            String[] parts = host.split("\\.");
            String domain = parts[parts.length - 2] + "." + parts[parts.length - 1];
            String subdomain = (parts.length > 2) ? parts[0] : "";
            return "Domain: " + domain + ", Subdomain: " + subdomain;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private static String getServerLocation(String url) {
        try {
            InetAddress address = InetAddress.getByName(new URL(url).getHost());
            String ip = address.getHostAddress();
            // Here, use an IP geolocation API to get the location from the IP
            return "IP: " + ip + ", Location: [Use API to get location]";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
