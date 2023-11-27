package org.example;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
public class Main {
    private final static ArrayList<WebCrawlerDataBase> webCrawlerDataBase=new ArrayList<>();
    private final static ArrayList<WebScrapperDataBase> webScrapperDataBase=new ArrayList<>();
    // urls to test:
    //https://books.toscrape.com/
    //https://www.adobe.com/express/feature/image/resize
    //https://onlinestringtools.com/
    public static void main(String[] args) {
        mainMenu();
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
        }else {

        }

    }

    private static void webCrawlerOptionsMenu(){
        final String option1="1"; final String option2="2";final String option3="3";final  String option4="4";
        final String goBackOption="0";
        Scanner scanner=new Scanner(System.in);
        String decision;
        int counter=0;
        do {
            if(counter!=0){System.out.println("make sure you choose one of the options.");}
            System.out.println("press "+option1+" to analyze a url");
            System.out.println("press "+option2+" to check web crawler data base history");
            System.out.println("press "+option3+" to erase a data base section.");
            System.out.println("press "+option4+" to erase all data base history.");
            System.out.println("press "+goBackOption+" to go back to the main menu.");
            decision=scanner.nextLine();
            if(decision.equals(goBackOption)){mainMenu();}
            counter++;
        }while (!decision.equals(option1) && !decision.equals(option2) && !decision.equals(option3)&&!decision.equals(option4));
        if(decision.equals(option1)){
            insertUrlToAnalyze();
        }else if (decision.equals(option2)){
            if(!webCrawlerDataBase.isEmpty()){
                getHistoryMenu();
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
    }
    private static void webScrapperOptionsMenu(){
        final String option1="1"; final String option2="2";final String option3="3";
        Scanner scanner=new Scanner(System.in);
        String decision;

    }

    private static void eraseWebScrapperData(String url){


        for(WebCrawlerDataBase dataBase:webCrawlerDataBase){
            if(dataBase.getUrl().equals(url)){
                webCrawlerDataBase.remove(dataBase);
            }
        }
    }
    private static void eraseWebCrawlerData(){
        Scanner scanner=new Scanner(System.in);

        String decision;

        do {
            System.out.println("choose the url of the data base section you want to erase.");
            System.out.println(" press '1' if you are having difficulties.");
            System.out.println("press '0' to go back.");
            decision=scanner.nextLine();
            if (decision.equals("0")){webCrawlerOptionsMenu();}
            if(decision.equals("1")){
                System.out.println("here's a list of url's:");
                for(WebCrawlerDataBase dataBase:webCrawlerDataBase){
                    System.out.println(dataBase.getUrl());
                }
                System.out.println();
                eraseWebCrawlerDataBase();
            }
        }while (!doesExistsInCrawlerDataBase(decision));

        for(WebScrapperDataBase dataBase:webScrapperDataBase){
            if(dataBase.getUrl().equals(decision)){
                webCrawlerDataBase.remove(dataBase);
            }
        }
    }
    private static boolean doesExistsInCrawlerDataBase(String url){
        for(WebCrawlerDataBase dataBase:webCrawlerDataBase){
            if(dataBase.getUrl().equals(url)){
                return true;
            }
        }
        return false;
    }

    private static void eraseWebScrapperDataBase(){webScrapperDataBase.clear();}
    private static void eraseWebCrawlerDataBase(){webCrawlerDataBase.clear();}
    private static void countAppearanceOfKeyWordOnWebsite(ArrayList<String> links){

    }
    private static void insertUrlToAnalyze(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("insert a url to start:");
        String url=scanner.nextLine();
        if(!isValidUrl(url)){System.out.println("the url you entered is not valid!");
            mainMenu();}
        for(WebCrawlerDataBase data:webCrawlerDataBase){
            if(url.equals(data.getUrl())){
                System.out.println("url already exists in history:");
                System.out.println(data);
            }
        }
        WebCrawlerDataBase webCrawlerDataBase=WebCrawler.getUrlInfo(url);
        System.out.println(webCrawlerDataBase);
        Main.webCrawlerDataBase.add(webCrawlerDataBase);
        mainMenu();
    }
    private static void getHistoryMenu(){
        Scanner scanner=new Scanner(System.in);
        int counter=1;
        HashMap<Integer,String> linksMenu=new HashMap<>();
        System.out.println("press 0 at any time to go back.");
        for(WebCrawlerDataBase data:webCrawlerDataBase){
            System.out.println("press "+counter+" for the link: "+data.getUrl());
            linksMenu.put(counter,data.getUrl());
            counter++;
        }
        String decision=scanner.nextLine();
        if(!decision.matches(".*[a-zA-Z]+.*")){
            int decisionToInt=Integer.parseInt(decision);
            if(decisionToInt==0){
                mainMenu();}
            if(decisionToInt<counter ||decisionToInt>1){
                for(WebCrawlerDataBase data:webCrawlerDataBase){
                    if(data.getUrl().equals(linksMenu.get(decisionToInt))){
                        System.out.println(data);
                    }
                }
                getHistoryMenu();
            }
        }
        System.out.println();System.out.println("invalid input!");System.out.println();
        getHistoryMenu();
    }

    private static boolean isValidUrl(String url){try {new URL(url);return true;} catch (MalformedURLException e) {return false;}}
}
