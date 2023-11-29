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
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class WebCrawler {
    private static HashSet<String> visitedLinks= new HashSet<>();
    private static int iterationsCount=0;
    private static boolean shouldStop=false;
    public static WebCrawlerDataBase getAnalyzedInfo(String url){
        visitedLinks.clear();iterationsCount=0; shouldStop=false;
        WebCrawlerDataBase webCrawlerData=new WebCrawlerDataBase(url,-1,-1, new ArrayList<>(),null,null,null);

        startCrawling(url);
        webCrawlerData.setLinksList(new ArrayList<>(visitedLinks));
        webCrawlerData.setUniqueLinksCount(new ArrayList<>(visitedLinks).size());
        webCrawlerData.setIterationsCount(iterationsCount);

        webCrawlerData.setLanguage(getLanguageFromHTML(url));
        webCrawlerData.setUrlIPAddress(getIPAddressFromUrl(url));
        webCrawlerData.setLocation(getServerLocationFromIPAddress(webCrawlerData.getUrlIPAddress()));
        return webCrawlerData;
    }
    private static void startCrawling(String startUrl) {
        crawl(startUrl);
    }
    private static void crawl(String url) {
        if(shouldStop){return;}
        iterationsCount++;
        if (visitedLinks.contains(url)) {return;}
        visitedLinks.add(url); // Mark this URL as visited
        if (visitedLinks.size() % 1500 == 0) {shouldProceed();}

        System.out.print("\rNumber of links counted so far: " + visitedLinks.size());
        List<String> links = getLinksFromUrl(url); // Fetch links from the URL
        for (String link : links) {
            crawl(link); // Recursive call for each link
        }
    }
    private static void shouldProceed(){
        Scanner scanner=new Scanner(System.in);
        int hasFailedOnce=0;
        final String option1="1"; final String option2="0";
        String decision;
        do {
            if (hasFailedOnce>0){System.out.println("invalid input!!");}
            System.out.println();
            System.out.println("so far we counted: "+visitedLinks.size()+" links.");
            System.out.println("press "+option1+" if you wish to proceed.");
            System.out.println("press "+option2+" if you want to finish.");
            decision=scanner.nextLine();
            hasFailedOnce++;
        }while (!decision.equals(option1)&&!decision.equals(option2));
        shouldStop=decision.equals("0");
        iterationsCount--;
    }
    private static List<String> getLinksFromUrl(String url) {
        List<String> links = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get(); // Connect to the URL and parse its HTML

            Elements linkElements = doc.select("a[href]"); // Select all hyperlinks

            for (Element linkElement : linkElements) {
                String link = linkElement.attr("abs:href"); // Extract absolute URL
                if (!link.isEmpty()) {
                    links.add(link);
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching links from " + url + ": " + e.getMessage());
        }
        return links;
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
