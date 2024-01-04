package org.example;
import java.util.ArrayList;
public class WebCrawlerDataBase {
    private String url;
    private int iterationsCount;
    private int uniqueLinksCount;
    private ArrayList<String> uniqueLinksList;//i think it suppose to be uniqueLinksList which is also a set.
    private String language;
    private String urlIPAddress;
    private String location;
    public WebCrawlerDataBase(String url, int iterationsCount, int uniqueLinksCount, ArrayList<String> uniqueLinksList, String language, String urlIPAddress, String location) {
        this.url=url;
        this.iterationsCount = iterationsCount;
        this.uniqueLinksCount = uniqueLinksCount;
        this.uniqueLinksList = uniqueLinksList;
        this.language=language;
        this.urlIPAddress=urlIPAddress;
        this.location=location;
    }
    public String getUrl(){return url;}
    public int getIterationsCount() {return iterationsCount;}
    public int getUniqueLinksCount() {return uniqueLinksCount;}
    public ArrayList<String> getUniqueLinksList() {return uniqueLinksList;}
    public String getLanguage(){return language;}
    public String getUrlIPAddress(){return urlIPAddress;}
    public String getLocation(){return location;}
    public void setUrl(String url){this.url=url;}
    public void setIterationsCount(int iterationsCount) {this.iterationsCount = iterationsCount;}
    public void setUniqueLinksCount(int uniqueLinksCount) {this.uniqueLinksCount = uniqueLinksCount;}
    public void setUniqueLinksList(ArrayList<String> uniqueLinksList) {this.uniqueLinksList = uniqueLinksList;}
    public void setLanguage(String language){this.language=language;}
    public void setUrlIPAddress(String urlIPAddress){this.urlIPAddress=urlIPAddress;}
    public void setLocation(String location){this.location=location;}

    @Override
    public String toString() {
        return
                "\n url='" + url + '\'' +
                        ",\n iterationsCount=" + iterationsCount +
                        ",\n uniqueLinksCount=" + uniqueLinksCount +
                        ",\n linksList=" + uniqueLinksList +
                        ",\n language='" + language + '\'' +
                        ",\n urlIPAddress='" + urlIPAddress + '\'' +
                        ",\n location='" + location + '\'' +
                        "\n";
    }

}
