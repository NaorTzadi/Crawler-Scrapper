package org.example;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
public class Main {
    private final static ArrayList<WebCrawlerDataBase> webCrawlerDataBase=new ArrayList<>();
    private final static ArrayList<WebScrapperDataBase> webScrapperDataBase=new ArrayList<>();
    public static void main(String[] args) {
        String urlToTest="https://www.sciencedirect.com/science/article/pii/S2090123219301079";
        ChromeDriver chromeDriver=Utility.getModifiedChromeDriver();

        //System.out.println(FileLinksExtractor.getAllFileLinks("https://scholar.archive.org/"));
        //for (String link:FileLinksExtractor.extractHyperLinks(chromeDriver)){System.out.println(link);}

        //System.out.println(FileLinksExtractor.isDownloadLink("https://web.archive.org/web/20171006225602/https://burnstrauma.biomedcentral.com/track/pdf/10.1186/s41038-017-0090-z?site=burnstrauma.biomedcentral.com",chromeDriver));

        //System.out.println(FileLinksExtractor.isDownloadLink("https://www.dwsamplefiles.com/download-ods-sample-files/",chromeDriver));
        for (String link:FileLinksExtractor.getAllDownloadLinks("https://www.dwsamplefiles.com/download-doc-sample-files/")){System.out.println(link);}

        Utility.isConnected();
        mainMenu();

        //for(String url:Utility.getUrlsToTest()){System.out.println( WebScrapper.getContentType(url));} //עובד
        //for(String url:Utility.getUrlsToTest()){System.out.println(Utility.isAccessAllowed(url));}//עובד
    }


    private static void mainMenu(){
        final String option1="1"; final String option2="2";final String option3="3";
        Scanner scanner=new Scanner(System.in);
        String decision;
        int counter=0;
        do {
            if(counter!=0){System.out.println("make sure you choose one of the options.");}
            System.out.println("press "+option1+" for web crawler menu.");
            System.out.println("press "+option2+" for web scrapper menu.");
            System.out.println("press "+option3+" for XXX");
            decision=scanner.nextLine();
            counter++;
        }while (!decision.equals(option1) && !decision.equals(option2) && !decision.equals(option3));

        if(decision.equals(option1)){
            webCrawlerOptionsMenu();
        }else if (decision.equals(option2)){
            webScrapperOptionsMenu();
        }else if (decision.equals(option3)){
            //נכניס כאן פונקציה
        }
        mainMenu();
    }
    private static void webCrawlerOptionsMenu(){
        final String option1="1"; final String option2="2";final String option3="3";final  String option4="4";
        final String goBackOption="0";
        Scanner scanner=new Scanner(System.in);
        String decision;
        int counter=0;
        do {
            if(counter!=0){System.out.println("make sure you choose one of the options.");}
            System.out.println("press "+goBackOption+" to go back to the main menu.");
            System.out.println("press "+option1+" to analyze a url");
            System.out.println("press "+option2+" to check web crawler data base history");
            System.out.println("press "+option3+" to erase a data base section.");
            System.out.println("press "+option4+" to erase all data base history.");
            decision=scanner.nextLine();
            if(decision.equals(goBackOption)){mainMenu();}
            counter++;
        }while (!decision.equals(option1) && !decision.equals(option2) && !decision.equals(option3)&&!decision.equals(option4));
        if(decision.equals(option1)){
            insertUrlToAnalyze();
        }else if (decision.equals(option2)){
            if(!webCrawlerDataBase.isEmpty()){
                getWebCrawlerHistoryMenu();
            }else {
                System.out.println("there is not yet any data in the data base!");
                System.out.println();
                webCrawlerOptionsMenu();
            }
        }else if (decision.equals(option3)){
            eraseWebCrawlerData();
        }else if (decision.equals(option4)){
            eraseWebCrawlerDataBase();
        }
        System.out.println(); webCrawlerOptionsMenu();
    }
    private static void webScrapperOptionsMenu(){
        final String option1="1"; final String option2="2";final String option3="3";final String option4="4";
        final String option5="5";final String option6="6";final String option7="7"; String goBackOption="0";
        Scanner scanner=new Scanner(System.in);
        String decision;
        int counter=0;
        do {
            if(counter!=0){System.out.println("make sure you choose one of the options.");}
            System.out.println("press "+goBackOption+" to go back to the main menu.");
            System.out.println("press "+option1+" to scrap a given url.");
            System.out.println("press "+option2+" to search for a key word.");
            System.out.println("press "+option3+" to retrieve image links.");
            System.out.println("press "+option4+" to retrieve video links.");
            System.out.println("press "+option5+" to check web scrapper data base history");
            System.out.println("press "+option6+" to erase a data base section.");
            System.out.println("press "+option7+" to erase all data base history.");
            decision=scanner.nextLine();
            if(decision.equals(goBackOption)){mainMenu();}
            counter++;
        }while (!decision.equals(option1) && !decision.equals(option2) && !decision.equals(option3)&&!decision.equals(option4)&&!decision.equals(option5)&&!decision.equals(option6)&&!decision.equals(option7));
        String url=Utility.promptUrl();
        if (url.equals("0")){webScrapperOptionsMenu();return;}
        //if(Integer.parseInt(decision)>=2 && Integer.parseInt(decision)<=4){WebScrapper.webScrapperOperator(url,decision);}
        if(decision.equals(option1)){
            hasScrapped(url);webScrapperDataBase.add((WebScrapperDataBase) WebScrapper.webScrapperOperator(url,option1));
        }else if (decision.equals(option2)){
            WebScrapper.webScrapperOperator(url,option2);
        }else if (decision.equals(option3)){
            WebScrapper.webScrapperOperator(url,option3);
        }else if (decision.equals(option4)){
            WebScrapper.webScrapperOperator(url,option4);
        }else if (decision.equals(option5)){
            getWebScrapperHistoryMenu();
        }else if (decision.equals(option6)){
            eraseWebScrapperData();
        }else if (decision.equals(option7)){
            eraseWebScrapperDataBase();
        }
        System.out.println();webScrapperOptionsMenu();
    }

    private static void eraseWebScrapperData(){
        if(webScrapperDataBase.isEmpty()){System.out.println("nothing to erase because the data base is empty!");return;}
        Scanner scanner=new Scanner(System.in);
        String decision;
        do {
            System.out.println("choose the url of the data base section you want to erase.");
            System.out.println("press '1' if you are having difficulties.");
            System.out.println("press '0' to go back.");
            decision=scanner.nextLine();
            if (decision.equals("0")){webScrapperOptionsMenu();}
            if(decision.equals("1")){
                System.out.println("here's a list of url's:");
                for(WebScrapperDataBase dataBase:webScrapperDataBase){
                    System.out.println(dataBase.getUrl());
                }
                System.out.println();
                eraseWebScrapperData();
            }
        }while (!doesExistsInScrapperDataBase(decision));

        for(WebCrawlerDataBase dataBase:webCrawlerDataBase){
            if(dataBase.getUrl().equals(decision)){
                webScrapperDataBase.remove(dataBase);
            }
        }
    }

    private static void eraseWebCrawlerData(){
        if(webCrawlerDataBase.isEmpty()){System.out.println("nothing to erase because the data base is empty!");return;}
        Scanner scanner=new Scanner(System.in);
        String decision;
        do {
            System.out.println("choose the url of the data base section you want to erase.");
            System.out.println("press '1' if you are having difficulties.");
            System.out.println("press '0' to go back.");
            decision=scanner.nextLine();
            if (decision.equals("0")){webCrawlerOptionsMenu();}
            if(decision.equals("1")){
                System.out.println("here's a list of url's:");
                for(WebCrawlerDataBase dataBase:webCrawlerDataBase){
                    System.out.println(dataBase.getUrl());
                }
                System.out.println();
                eraseWebCrawlerData();
            }
        }while (!doesExistsInCrawlerDataBase(decision));

        for(WebScrapperDataBase dataBase:webScrapperDataBase){
            if(dataBase.getUrl().equals(decision)){
                webCrawlerDataBase.remove(dataBase);
            }
        }
    }
    private static boolean doesExistsInScrapperDataBase(String url){return webScrapperDataBase.stream().anyMatch(dataBase -> dataBase.getUrl().equals(url));}
    private static boolean doesExistsInCrawlerDataBase(String url){return webCrawlerDataBase.stream().anyMatch(dataBase -> dataBase.getUrl().equals(url));}
    private static void eraseWebScrapperDataBase(){webScrapperDataBase.clear();}
    private static void eraseWebCrawlerDataBase(){webCrawlerDataBase.clear();}

    private static void hasScrapped(String url){
        for(WebScrapperDataBase data:webScrapperDataBase){
            if(url.equals(data.getUrl())){
                System.out.println("url already exists in history:");
                System.out.println(data);
                System.out.println();
                webScrapperOptionsMenu();
            }
        }
    }
    private static void insertUrlToAnalyze(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("insert a url to start:");
        String url=scanner.nextLine();
        if(!Utility.isValidUrl(url)){System.out.println("the url you entered is not valid!");
            webCrawlerOptionsMenu();}
        for(WebCrawlerDataBase data:webCrawlerDataBase){
            if(url.equals(data.getUrl())){
                System.out.println("url already exists in history:");
                System.out.println(data);
            }
        }
        if (!Utility.isMainPageUrl(url)){
            String decision;
            final String getMainWebsiteUrl="1";final String keepCurrentUrl="2";
            do {
                System.out.println("the url you gave is not the website main url!");
                System.out.println("press 1 to get the main website url.");
                System.out.println("press 2 to proceed with the current url.");
                decision=scanner.nextLine();
            }while (!decision.equals(getMainWebsiteUrl) && !decision.equals(keepCurrentUrl));
            if (decision.equals(getMainWebsiteUrl)){
                String mainWebsiteUrl=Utility.getMainDomainUrl(url);
                if (mainWebsiteUrl!=null && !mainWebsiteUrl.isEmpty()){
                    url=mainWebsiteUrl;
                }else {
                    System.out.println("could not find main website url, continuing with current url.");
                }
            }
        }
        WebCrawlerDataBase webCrawlerData=WebCrawler.getAnalyzedInfo(url);
        System.out.println(webCrawlerData);
        webCrawlerDataBase.add(webCrawlerData);
        System.out.println();webCrawlerOptionsMenu();
    }
    private static void getWebScrapperHistoryMenu(){
        Scanner scanner=new Scanner(System.in);
        int counter=1;
        HashMap<Integer,String> linksMenu=new HashMap<>();
        System.out.println("press 0 at any time to go back.");
        System.out.println("press 'ENTER' to print all history data's.");
        for(WebScrapperDataBase data:webScrapperDataBase){
            System.out.println("press "+counter+" for the link: "+data.getUrl());
            linksMenu.put(counter,data.getUrl());
            counter++;
        }
        String decision=scanner.nextLine();
        if(decision.equals("")){
            for(WebScrapperDataBase data:webScrapperDataBase){
                System.out.println(data);
            }
            System.out.println();
            getWebScrapperHistoryMenu();
        }
        if(decision.matches("^[0-9]+$")){
            int decisionToInt=Integer.parseInt(decision);
            if(decisionToInt==0){
                mainMenu();}
            if(decisionToInt<counter ||decisionToInt>1){
                for(WebScrapperDataBase data:webScrapperDataBase){
                    if(data.getUrl().equals(linksMenu.get(decisionToInt))){
                        System.out.println(data);
                    }
                }
                System.out.println();
                getWebScrapperHistoryMenu();
            }
        }
        System.out.println();System.out.println("invalid input!");System.out.println();
        getWebScrapperHistoryMenu();
    }
    private static void getWebCrawlerHistoryMenu(){
        Scanner scanner=new Scanner(System.in);
        int counter=1;
        HashMap<Integer,String> linksMenu=new HashMap<>();
        System.out.println("press '0' at any time to go back.");
        System.out.println("press 'ENTER' to print all history data's.");
        for(WebCrawlerDataBase data:webCrawlerDataBase){
            System.out.println("press "+counter+" for the link: "+data.getUrl());
            linksMenu.put(counter,data.getUrl());
            counter++;
        }
        String decision=scanner.nextLine();
        if(decision.equals("")){
            for(WebCrawlerDataBase data:webCrawlerDataBase){
                System.out.println(data);
            }
            System.out.println();
            getWebCrawlerHistoryMenu();
        }
        if(decision.matches("^[0-9]+$")){
            int decisionToInt=Integer.parseInt(decision);
            if(decisionToInt==0){
                mainMenu();}
            if(decisionToInt<counter ||decisionToInt>1){
                for(WebCrawlerDataBase data:webCrawlerDataBase){
                    if(data.getUrl().equals(linksMenu.get(decisionToInt))){
                        System.out.println(data);
                    }
                }
                System.out.println();
                getWebCrawlerHistoryMenu();
            }
        }
        System.out.println();System.out.println("invalid input!");System.out.println();
        getWebCrawlerHistoryMenu();
    }



}
