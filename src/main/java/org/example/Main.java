package org.example;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
public class Main {
    private final static ArrayList<WebCrawlerDataBase> webCrawlerDataBase=new ArrayList<>();
    private final static ArrayList<WebScrapperDataBase> webScrapperDataBase=new ArrayList<>();
    public static void main(String[] args) {
        //new MouseTracker();Scanner scanner=new Scanner(System.in);scanner.nextLine();
        //new ScrollerTracker();Scanner scanner=new Scanner(System.in);scanner.nextLine();

        /*MouseMovementPaths.fillPaths();
        ChromeDriver chromeDriver=Utility.getModifiedChromeDriver();
        chromeDriver.get("https://store.steampowered.com/app/1172470/Apex_Legends/");
        WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(10));
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div[7]/div[6]/div[3]/div[2]/div[1]/div[5]/div[2]/div[12]/div[2]/div/a[1]/img")));
            MouseController mouseController=new MouseController(chromeDriver,element);
            mouseController.MouseOperator();
        }catch (TimeoutException e){
            System.out.println("the element didnt appear in the given time...");
        } catch (Exception e){
            e.printStackTrace();
        }*/

        Utility.isConnected();
        MouseMovementPaths.fillPaths();
        mainMenu();

        //System.out.println(FileLinksExtractor.getAllFileLinks("https://scholar.archive.org/"));
        //for (String link:FileLinksExtractor.extractHyperLinks(chromeDriver)){System.out.println(link);}

        //System.out.println(FileLinksExtractor.isDownloadLink("https://ssj.mp3juice.blog/dl/DCtouot15cA",chromeDriver));

        //for (String link:FileLinksExtractor.getAllDownloadLinks("https://licensing.jamendo.com/en/track/2126785/infinite-inspiration-uplifting-upbeat-corporate-inspiring-motivational")){System.out.println(link);}

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
        final String option5="5";final String option6="6";final String option7="7";final String option8="8";
        final String goBackOption="0";
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
            System.out.println("press "+option5+" to retrieve download files.");
            System.out.println("press "+option6+" to check web scrapper data base history");
            System.out.println("press "+option7+" to erase a data base section.");
            System.out.println("press "+option8+" to erase all data base history.");
            decision=scanner.nextLine();
            if(decision.equals(goBackOption)){mainMenu();}
            counter++;
        }while (!decision.matches("\\d+") || Integer.parseInt(decision) > 8 || Integer.parseInt(decision) < 0);
        if(Integer.parseInt(decision)>0 && Integer.parseInt(decision)<6){
            String url=Utility.promptUrl();
            if (url.equals("0")){webScrapperOptionsMenu();return;}
          processPhase(url,decision);

        }else if (decision.equals(option6)){
            getWebScrapperHistoryMenu();
        }else if (decision.equals(option7)){
            eraseWebScrapperData();
        }else if (decision.equals(option8)){
            eraseWebScrapperDataBase();

        }
        System.out.println();webScrapperOptionsMenu();
    }
    private static void processPhase(String url, String decision){
        for (WebScrapperDataBase data:webScrapperDataBase){
            if (data.getUrl().equals(url)){
                webScrapperDataBase.add(WebScrapper.webScrapperOperator(decision,data));
                webScrapperDataBase.remove(data);
                return;
            }
        }
        webScrapperDataBase.add(WebScrapper.webScrapperOperator(decision,new WebScrapperDataBase(url,WebScrapper.getContentType(url),null,null,null,null)));
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
        if (!Utility.isMainPageUrl(url)){url=Utility.shouldUseMainPageUrl(url);}
        WebCrawlerDataBase webCrawlerData=WebCrawler.getAnalyzedInfo(url);
        System.out.println(webCrawlerData);    //למחוק אחרי בדיקה
        webCrawlerDataBase.add(webCrawlerData);
        System.out.println();webCrawlerOptionsMenu();
    }
    private static void getWebScrapperHistoryMenu(){
        if (webScrapperDataBase.isEmpty()){
            System.out.println("not data yet...");System.out.println();webScrapperOptionsMenu();return;
        }
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
                webScrapperOptionsMenu();}
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
                webCrawlerOptionsMenu();}
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
