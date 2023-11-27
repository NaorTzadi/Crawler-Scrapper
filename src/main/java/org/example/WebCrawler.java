package org.example;
import org.json.JSONObject;
import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
public class WebCrawler {

    public static WebCrawlerDataBase getUrlInfo(String url){
        WebCrawlerDataBase webCrawlerData=new WebCrawlerDataBase(url,-1,-1, new ArrayList<>(),null,null,null);

        setPageLinksInfo(url,webCrawlerData);
        webCrawlerData.setLanguage(getLanguageFromHTML(url));
        webCrawlerData.setUrlIPAddress(getIPAddressFromUrl(url));
        webCrawlerData.setLocation(getServerLocationFromIPAddress(webCrawlerData.getUrlIPAddress()));
        return webCrawlerData;
    }
    private static void setPageLinksInfo(String URL, WebCrawlerDataBase webCrawlerData) {
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
    private static String getIPAddressFromUrl(String url){
        String ip;
        try {
            InetAddress address = InetAddress.getByName(new URL(url).getHost());
            ip = address.getHostAddress();
        }catch (Exception e) {e.printStackTrace();return null;}
        return ip;
    }
    public static String getServerLocationFromIPAddress(String ip) {
        //לצורך חידוש מפתח: https://ipgeolocation.io/documentation/ip-geolocation-api.html
        final String APIKey="2ce1e6fb6b484fd4a39a431ecd66ae9f";
        String info;
        try {
           String requestUrl = "https://api.ipgeolocation.io/ipgeo?apiKey=" + APIKey + "&ip=" + ip;
           info=extractGeoLocationBasicInformation(getResponseFromURL(requestUrl));
        } catch (Exception e) {e.printStackTrace();return null;}
        return info;
    }
    private static String getResponseFromURL(String urlString) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
        } catch (IOException e) {e.printStackTrace();}
        return response.toString();
    }
    private static String extractGeoLocationBasicInformation(String info){
        JSONObject jsonObject = new JSONObject(info);
        String continent = jsonObject.getString("continent_name");
        String country = jsonObject.getString("country_name");
        String district = jsonObject.optString("district", "N/A"); // Using optString for optional fields
        String city = jsonObject.getString("city");
        String zipCode = jsonObject.getString("zipcode");
        String latitude = jsonObject.getString("latitude");
        String longitude = jsonObject.getString("longitude");
        return "Continent: " + continent + ", Country: " + country +
                ", District: " + district + ", City: " + city +
                ", Zip Code: " + zipCode + ", Latitude: " + latitude +
                ", Longitude: " + longitude;
    }


}
