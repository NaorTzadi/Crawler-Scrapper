package org.example;
import java.util.ArrayList;
public class WebCrawlerData {
    private int iterationsCount;
    private int uniqueLinksCount;
    private ArrayList<String> linksList;
    private String language;
    public WebCrawlerData(int iterationsCount, int uniqueLinksCount, ArrayList<String> linksList,String language) {
        this.iterationsCount = iterationsCount;
        this.uniqueLinksCount = uniqueLinksCount;
        this.linksList = linksList;
        this.language=language;
    }
    public int getIterationsCount() {return iterationsCount;}
    public int getUniqueLinksCount() {return uniqueLinksCount;}
    public ArrayList<String> getLinksList() {return linksList;}
    public String getLanguage(){return language;}
    public void setIterationsCount(int iterationsCount) {this.iterationsCount = iterationsCount;}
    public void setUniqueLinksCount(int uniqueLinksCount) {this.uniqueLinksCount = uniqueLinksCount;}
    public void setLinksList(ArrayList<String> linksList) {this.linksList = linksList;}
    public void setLanguage(String language){this.language=language;}

    @Override
    public String toString() {
        return "WebCrawlerData{" +
                "iterationsCount=" + iterationsCount +
                ", uniqueLinksCount=" + uniqueLinksCount +
                ", linksList=" + linksList +
                ", language='" + language + '\'' +
                '}';
    }

}
