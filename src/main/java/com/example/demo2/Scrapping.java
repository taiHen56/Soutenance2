package com.example.demo2;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;
import java.util.List;

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

        String resultat = "";
        if (prixmin.equals("")) {
            prixmin = "0";
        }
        if (prixmax.equals("")) {
            prixmax = "999999999";
        }
        if (genre.equals("")){
            genre=".";
        }
        if(date.equals("")){
            date=".";
        }




        WebClient webClient = new WebClient(BrowserVersion.FIREFOX);

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(url);

        List<HtmlElement> liste = htmlPage.getByXPath("//h2/a");

        for (HtmlElement element : liste) {

            HtmlPage page2 = element.click();

            String genra = ((HtmlParagraph) page2.getByXPath("//p[@class='ref-genre-cat show-list-only']").get(0)).getTextContent();
            genra += ".";

            if (genra.contains(genre)) {

                String parution = ((HtmlStrong) page2.getByXPath("/html/body/main/section/div[2]/div/div/div/section/div[1]/div[2]/div[1]/p[2]/strong").get(0)).getTextContent();
                parution += ".";

                if (parution.contains(date)) {

                    String valeur = ((HtmlHeading2) page2.getByXPath("//div[1]/h2").get(0)).getTextContent();
                    String prixArticle = ((HtmlElement) page2.getByXPath("/html/body/main/section/div[2]/div/div/div/section/div[1]/div[2]/div[2]/div[1]/div/span").get(0)).getTextContent();

                    prixArticle = prixArticle.replace("€", "");
                    prixArticle = prixArticle.replaceAll("\\s+", "");
                    prixArticle = prixArticle.replace("\u00a0", "");
                    prixArticle = prixArticle.replace(",", ".");

                    if (Double.parseDouble(prixmin) <= Double.parseDouble(prixArticle) && Double.parseDouble(prixArticle) <= Double.parseDouble(prixmax)) {

                        resultat += valeur + "\n Prix : " + prixArticle + "\n";


                        String descrip = "";

                        List<HtmlElement> description = page2.getByXPath("//div[@class='features']");

                        for (HtmlElement d : description) {
                            descrip += d.getTextContent();
                        }

                        descrip = descrip.replace(valeur, "");


                        resultat += "Description : " + descrip + "\n";
                        resultat += page2.getUrl() + "\n\n";
                        resultat += "================================================================================================\n";
                    }

                }
            }
        }
        return resultat;
    }//fin vinylcorner


    public static String leboncoin(String titre, String genre, String prixmin, String prixmax) throws  IOException{

        if (genre.equals("")){
            genre="";
        }

        String url ="https://www.leboncoin.fr/recherche?category=26&text="+titre+" "+genre+"&price=" + prixmin + "-" + prixmax;
        String resultat="";

        WebClient webClient = new WebClient(BrowserVersion.FIREFOX);

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(url);

        List<HtmlElement> liste = htmlPage.getByXPath("//div[2]/div[1]/p");

        for(HtmlElement element : liste){

            HtmlPage page2 = element.click();

            String nomArticle = ((HtmlHeading1) page2.getByXPath("//div[3]/div/div/h1").get(0)).getTextContent() ;
            String prixArticle = ((HtmlSpan) page2.getByXPath("//div[3]/div/span//div/div[1]/div/span").get(0)).getTextContent();
            String description = ((HtmlParagraph) page2.getByXPath("//div[5]/div/div/p").get(0)).getTextContent();


            resultat += "Titre : " + nomArticle + "\nPrix : " + prixArticle + "\nDescription : " + description + "\nLien : " + page2.getUrl() +
                    "\n\"================================================================================================\\n\"\n";

        }



        return resultat;
    }//fin lbc

    public static String mesVinyles(String titre, String genre, String date, String prixmin, String prixmax)throws  IOException{
        String resultat="";

        if(date.equals("")){
            date=".";
        }
        if (prixmin.equals("")) {
            prixmin = "0";
        }
        if (prixmax.equals("")) {
            prixmax = "999999999";
        }

        String url="https://mesvinyles.fr/fr/recherche?controller=search&s="+titre + " "+genre;

        WebClient webClient = new WebClient(BrowserVersion.FIREFOX);

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(url);

        List<HtmlElement> liste = htmlPage.getByXPath("//div/div[2]/h3/a");

        for(HtmlElement element :  liste){

            HtmlPage page2 = element.click();

            String parution = ((HtmlParagraph) page2.getByXPath("/html/body/main/section/div/div/div/section/div[1]/div[2]/div[2]/div[1]/p[1]").get(0)).getTextContent();
            System.out.println(parution);
            parution += ".";

            if (parution.contains(date)) {


                String prixArticle = ((HtmlDivision) page2.getByXPath("//div[@class='current-price']").get(0)).getTextContent();

                prixArticle = prixArticle.replace("€", "");
                prixArticle = prixArticle.replaceAll("\\s+", "");
                prixArticle = prixArticle.replace("\u00a0", "");
                prixArticle = prixArticle.replace(",", ".");

                if (Double.parseDouble(prixmin) <= Double.parseDouble(prixArticle) && Double.parseDouble(prixArticle) <= Double.parseDouble(prixmax)) {

                    String valeur = ((HtmlHeading1) page2.getByXPath("//h1[@class='h1 productpage_title']").get(0)).getTextContent();

                    resultat +="Titre de l'album"+ valeur + "\n Prix : " + prixArticle + "€\n";


                    String descrip = "";

                    List<HtmlElement> description = page2.getByXPath("//div[@class='product-description']");

                    for (HtmlElement d : description) {
                        descrip += d.getTextContent();
                    }


                    resultat += "Description : " + descrip + "\n";
                    resultat += page2.getUrl() + "\n\n";
                    resultat += "================================================================================================\n";
                }




            }

        }

        return resultat;
    }




}//fin Scrapping
