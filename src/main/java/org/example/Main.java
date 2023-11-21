package org.example;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    static ArrayList<WebCrawlerData> webCrawlerDataBase=new ArrayList<>();
    //String testUrl="https://books.toscrape.com/";
    public static void main(String[] args) {
        menu();
    }
    private static void menu(){
        final String option1="1"; final String option2="2";final String option3="3";
        Scanner scanner=new Scanner(System.in);
        String decision;
        int counter=0;
        do {
            if(counter!=0){System.out.println("make sure you choose one of the options.");}
            System.out.println("press "+option1+" to check a url");
            System.out.println("press "+option2+" to check XXXXX");
            System.out.println("press "+option3+" check data history");
            decision=scanner.nextLine();
            counter++;
        }while (!decision.equals(option1) && !decision.equals(option2) && !decision.equals(option3));

        if(decision.equals(option1)){
            insertUrl();
        }else if (decision.equals(option2)){
            //נכניס פה פונקציה
        }else if (decision.equals(option3)){
            if(!webCrawlerDataBase.isEmpty()){
                //נכניס פה פונקציה
            }else {
                System.out.println("there is not yet any data in the data base!");
                menu();
            }
        }
    }
    private static void insertUrl(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("insert a url to start:");
        String url=scanner.nextLine();
        if(!isValidUrl(url)){System.out.println("the url you entered is not valid!");menu();}
        WebCrawlerData webCrawlerData=WebCrawler.getUrlInfo(url);
        if(webCrawlerData!=null){webCrawlerDataBase.add(webCrawlerData);}
        else {System.out.println("oops, something went wrong...");}
        menu();
    }
    private static boolean isValidUrl(String url){try {new URL(url);return true;} catch (MalformedURLException e) {return false;}}
}
