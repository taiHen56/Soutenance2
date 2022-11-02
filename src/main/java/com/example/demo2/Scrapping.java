package com.example.demo2;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scrapping {


    public static String discogs(String titre, String genre, String date, String prixmin, String prixmax) throws IOException {

        String url = "https://www.discogs.com/fr/sell/list?price1=" + prixmin +
                "&price2=" + prixmax +
                "&currency=EUR&q=" + titre +
                "&style=" + genre +
                "&year=" + date;
        String resultat = "x";

        WebClient webClient = new WebClient(BrowserVersion.FIREFOX);

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(url);

        List<HtmlElement> liste = htmlPage.getByXPath("//td[2]/strong/a");

        for (HtmlElement element : liste) {

            String valeur = element.getTextContent();


            HtmlPage page2 = element.click();
            List<HtmlElement> prix = page2.getByXPath("/html/body/div[1]/div[4]/div[2]/div/div[1]/div/div/p/span[1]");

            for (HtmlElement p : prix) {
                String prixArticle = p.getTextContent();
                prixArticle = prixArticle.replace("€", "");

                prixArticle = prixArticle.replaceAll("\\s+", "");
                prixArticle = prixArticle.replace("\u00a0", "");
                prixArticle = prixArticle.replace(",", ".");


                resultat += valeur + "\n Prix : " + prixArticle + "\n Description";

                List<HtmlElement> description = page2.getByXPath("//div/div[2]/div[2]");

                for (HtmlElement d : description) {
                    String descrip = d.getTextContent();

                    descrip = descrip.replaceAll("\\s+ ", "");
                    //descrip = descrip.replaceAll("\n", "");
                    descrip = descrip.replaceAll("PositionArtistsTitle/CreditsDuration1", "");
                    descrip = descrip.replaceAll("PositionTitle/CreditsDuration1", "");


                    resultat += descrip + "\n";
                    resultat += page2.getUrl() + "\n\n";
                    resultat += "================================================================================================\n";
                }

            }

        }
        return resultat;
    }//fin discogs

    public static String fnac(String titre, String genre, String date, String prixmin, String prixmax) throws IOException {

        String url = "https://www.fnac.com/SearchResult/ResultList.aspx?SCat=3!1&SDM=list&Search="
                + titre
                + genre
                + "&SFilt=1!101%2c4!101&sft=1";

        String resultat = "x";

        if (prixmin.equals("")) {
            prixmin = "0";
        }
        if (prixmax.equals("")) {
            prixmax = "999999999";
        }

        int nbResu = 0;

        WebClient webClient = new WebClient(BrowserVersion.FIREFOX);

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(url);


        List<HtmlAnchor> liste = htmlPage.getByXPath("//span/a");

        System.out.println("Je suis dans le site !");


        for (HtmlElement element : liste) {

            //for(int i = 0;i<5;i++) {

            String valeur = element.getTextContent();


            HtmlPage page2 = element.click();

            System.out.println("Je suis dans liste !");

            List<HtmlElement> prix = page2.getByXPath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div[1]/form/div/div[1]/label[1]/div[2]/span");

            for (HtmlElement p : prix) {
                System.out.println("Je suis dans prix !");

                String prixArticle = p.getTextContent();
                prixArticle = prixArticle.replace("€", "");

                prixArticle = prixArticle.replaceAll("\\s+", "");
                prixArticle = prixArticle.replace("\u00a0", "");
                prixArticle = prixArticle.replace(",", ".");

                if (Double.parseDouble(prixmin) <= Double.parseDouble(prixArticle) && Double.parseDouble(prixArticle) <= Double.parseDouble(prixmax)) {

                    resultat += valeur + "\n Prix : " + prixArticle + "\n Description";
                    System.out.println("J'ai commencé le resultat' !");

                    List<HtmlElement> description = page2.getByXPath("/html/body/div[2]/div/div[1]/div[2]/div[2]/div[1]/div[2]/div");

                    for (HtmlElement d : description) {

                        String descrip = d.getTextContent();

                        System.out.println("Je suis dans description !");

                        resultat += descrip + "\n";

                    }

                    nbResu++;
                    System.out.println(nbResu);

                    resultat += page2.getUrl() + "\n\n";
                    resultat += "================================================================================================\n";


                }


            }
        }


        // }

        return resultat;
    }

    public static String vinylcorner(String titre, String genre, String date, String prixmin, String prixmax) throws IOException {

        String url = "https://www.vinylcorner.fr/recherche?controller=search&s=" + titre + "+" + genre;

        String resultat = "x";
        if (prixmin.equals("")) {
            prixmin = "0";
        }
        if (prixmax.equals("")) {
            prixmax = "999999999";
        }

        int nbResu = 5;

        WebClient webClient = new WebClient(BrowserVersion.FIREFOX);

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(url);

        List<HtmlElement> liste = htmlPage.getByXPath("//h2/a");

        for (HtmlElement element : liste) {

            HtmlPage page2 = element.click();
            String valeur = ((HtmlHeading2) page2.getByXPath("//div[1]/h2").get(0)).getTextContent();
            String prixArticle = ((HtmlElement) page2.getByXPath("/html/body/main/section/div[2]/div/div/div/section/div[1]/div[2]/div[2]/div[1]/div/span").get(0)).getTextContent();

            prixArticle = prixArticle.replace("€", "");

            prixArticle = prixArticle.replaceAll("\\s+", "");
            prixArticle = prixArticle.replace("\u00a0", "");
            prixArticle = prixArticle.replace(",", ".");

            if (Double.parseDouble(prixmin) <= Double.parseDouble(prixArticle) && Double.parseDouble(prixArticle) <= Double.parseDouble(prixmax)) {

                resultat += valeur + "\n Prix : " + prixArticle + "\n";


                String descrip = "";

                descrip += ((HtmlElement) page2.getByXPath("//div[2]/div[4]/div").get(0)).getTextContent();

                if (descrip.equals("")) {

                    List<HtmlElement> description = page2.getByXPath("//div/div[2]/div[2]");

                    for (int t = 0; t < description.size() - 1; t++) {


                        List<HtmlElement> audio = page2.getByXPath("/html/body/main/section/div[2]/div/div/div/section/div[1]/div[2]/div[4]/div[" + Integer.toString(2 + t) + "]/span");

                        for (HtmlElement a : audio) {


                            descrip += a.getTextContent()+ ", ";

                        }

                    }


                }

                resultat += "Description : " + descrip + "\n";
                resultat += page2.getUrl() + "\n\n";
                resultat += "================================================================================================\n";
            }

        }
        return resultat;
    }






}//fin Scrapping
