package com.example.demo2;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scrapping {

    private String titre,genre,date,prixmin,prixmax;
    private ArrayList<String[]> resuBDD;


    /**
     *Constructeur de Scrapping. Il prend tout en constructeur pour pouvoir ensuite les utiliser dans les differentes methodes
     * @param titre Titre recherché
     * @param genre Genre voulu
     * @param date Date
     * @param prixmin Prix minimum renseigné
     * @param prixmax prix maximum renseigné
     */
    public Scrapping(String titre, String genre, String date, String prixmin, String prixmax){
        this.titre=titre;
        this.genre=genre;
        this.date=date;
        this.prixmin=prixmin;
        this.prixmax=prixmax;
        resuBDD = new ArrayList<String[]>();
    }

    /**
     *
     * Methode qui retourne la ArrayList de chaque résultat
     * @return
     */
    public ArrayList<String[]> getResuBDD() {
        return resuBDD;
    }

    /**
     * Methode qui fait le Scraping dans Discogs.fr
     * @return String resultat
     * @throws IOException
     */
    public String discogs() throws IOException {

        String url = "https://www.discogs.com/fr/sell/list?price1=" + prixmin +
                "&price2=" + prixmax +
                "&currency=EUR&q=" + titre;


        String resultat = "";

        if(!genre.equals("")){
            url+="&style=" + genre;
        }
        if(!date.equals("")){
            url+="&year=" + date;
        }

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
                    String descrip = d.getTextContent().replaceAll("(?m)^[ \t]*\r?\n", "");

                    descrip = descrip.replaceAll("PositionArtistsTitle/CreditsDuration1", "");
                    descrip = descrip.replaceAll("PositionTitle/CreditsDuration1", "");


                    resultat += descrip + "\n";
                    resultat += page2.getUrl() + "\n\n";
                    resultat += "================================================================================================\n";


                    String[] ajouter = {valeur, descrip, prixArticle, date};
                    resuBDD.add(ajouter);

                }


            }

        }


        return resultat;
    }//fin discogs
    /**
     * Methode qui fait le Scraping dans fnac.fr
     * @return String resultat
     * @throws IOException
     */
    public String fnac() throws IOException {

        String url = "https://www.fnac.com/SearchResult/ResultList.aspx?SCat=3!1&SDM=list&Search="
                + titre
                + genre
                + "&SFilt=1!101%2c4!101&sft=1";

        String resultat = "";

        if (prixmin.equals("")) {
            prixmin = "0";
        }
        if (prixmax.equals("")) {
            prixmax = "999999999";
        }
        if(date.equals("")){
            date=".";
        }

        int nbResu = 0;

        WebClient webClient = new WebClient(BrowserVersion.FIREFOX);

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(url);


        List<HtmlAnchor> liste = htmlPage.getByXPath("//article/form/div[2]/div/p[1]/span/a");

        System.out.println("Je suis dans le site !");


        for (HtmlElement element : liste) {

            for (int i = 0; i < liste.size(); i++){


                if (nbResu < 6) {


                    String valeur = element.getTextContent();


                    HtmlPage page2 = element.click();

                    String parution = ((HtmlParagraph) page2.getByXPath("//div[2]/dl[2]/dd/p").get(0)).getTextContent();
                    parution += ".";
                    System.out.println(parution);

                    if (parution.contains(date)) {


                        System.out.println("Je suis dans liste !");


                        String prixArticle = ((HtmlSpan) page2.getByXPath(".//span[contains(@class,'userPrice')]").get(0)).getTextContent();

                        System.out.println(prixArticle);

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

                                String[] ajouter = {valeur, descrip, prixArticle, date};
                                resuBDD.add(ajouter);
                            }

                            nbResu++;
                            System.out.println(nbResu);

                            resultat += page2.getUrl() + "\n\n";
                            resultat += "================================================================================================\n";
                            nbResu++;




                        }
                    }

                }
        }

        }

        return resultat;
    }//fin fnac
    /**
     * Methode qui fait le Scraping dans VinylCorner.fr
     * @return String resultat
     * @throws IOException
     */
    public String vinylcorner() throws IOException {

        int idGenre = trouverMonGenre(genre);

        String url = "https://www.vinylcorner.fr/catalogsearch/result/?q=" + titre + "&category=" + idGenre;

        String resultat = "";
        if (prixmin.equals("")) {
            prixmin = "0";
        }
        if (prixmax.equals("")) {
            prixmax = "999999999";
        }
        if(date.equals("")){
            date=".";
        }

        WebClient webClient = new WebClient(BrowserVersion.FIREFOX);

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(url);

        List<HtmlElement> liste = htmlPage.getByXPath("//div/div[2]/strong/a");

        for (HtmlElement element : liste) {

            HtmlPage page2 = element.click();

                String parution = ((HtmlParagraph) page2.getByXPath("//div/div/div[1]/div[2]/p[2]").get(0)).getTextContent();
                parution += ".";

                if (parution.contains(date)) {

                    String valeur = ((HtmlSpan) page2.getByXPath("//span[@class='base']").get(0)).getTextContent();
                    String prixArticle = ((HtmlSpan) page2.getByXPath("//span[@class='price']").get(0)).getTextContent();

                    prixArticle = prixArticle.replace("€", "");
                    prixArticle = prixArticle.replaceAll("\\s+", "");
                    prixArticle = prixArticle.replace("\u00a0", "");
                    prixArticle = prixArticle.replace(",", ".");

                    if (Double.parseDouble(prixmin) <= Double.parseDouble(prixArticle) && Double.parseDouble(prixArticle) <= Double.parseDouble(prixmax)) {

                        resultat += valeur + "\n Prix : " + prixArticle + "\n";


                        String descrip = "";

                        List<HtmlElement> description = page2.getByXPath("//div[@class='product-specifications']");

                        for (HtmlElement d : description) {
                            descrip += d.getTextContent().replaceAll("(?m)^[ \t]*\r?\n", "") + "\n";
                        }

                        descrip = descrip.replace(valeur, "");

                        String[] ajouter = {valeur, descrip, prixArticle, date};
                        resuBDD.add(ajouter);


                        resultat += "Description : " + descrip + "\n";
                        resultat += page2.getUrl() + "\n\n";
                        resultat += "================================================================================================\n";
                    }

                }


        }
        return resultat;
    }//fin vinylcorner

    /**
     * Methode qui fait le Scraping dans Leboncoin.fr
     * @return String resultat
     * @throws IOException
     */
    public String leboncoin() throws  IOException{

        if (genre.equals("")){
            genre="";
        }
        if(date.equals("")){
            date=".";
        }

        String url ="https://www.leboncoin.fr/recherche?category=26&text="+titre+" "+genre+"&price=" + prixmin + "-" + prixmax;
        String resultat="";

        WebClient webClient = new WebClient(BrowserVersion.FIREFOX);

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(url);

        List<HtmlElement> liste = htmlPage.getByXPath("//div[2]/div[1]/p");

        for(HtmlElement element : liste) {
            for (int i = 0; i < liste.size();i++){

            HtmlPage page2 = element.click();

            String nomArticle = ((HtmlHeading1) page2.getByXPath("//div[3]/div/div/h1").get(0)).getTextContent();
            String prixArticle = ((HtmlSpan) page2.getByXPath("//div[3]/div/span//div/div[1]/div/span").get(0)).getTextContent();
            String description = ((HtmlParagraph) page2.getByXPath("//div[5]/div/div/p").get(0)).getTextContent();


            resultat += "Titre : " + nomArticle + "\nPrix : " + prixArticle + "\nDescription : " + description + "\nLien : " + page2.getUrl() +
                    "\n\"================================================================================================\\n\"\n";

                String[] ajouter = {nomArticle, description, prixArticle, date};
                resuBDD.add(ajouter);

        }
        }



        return resultat;
    }//fin lbc
    /**
     * Methode qui fait le Scraping dans mesVinyles.fr
     * @return String resultat
     * @throws IOException
     */
    public String mesVinyles()throws  IOException{
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

        for(HtmlElement element :  liste) {

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

                    resultat += "Titre de l'album" + valeur + "\n Prix : " + prixArticle + "€\n";


                    String descrip = "";

                    List<HtmlElement> description = page2.getByXPath("//div[@class='product-description']");

                    for (HtmlElement d : description) {
                        descrip += d.getTextContent().replaceAll("(?m)^[ \t]*\r?\n", "");
                    }

                    String[] ajouter = {valeur, descrip, prixArticle, date};
                    resuBDD.add(ajouter);


                    resultat += "Description : " + descrip + "\n";
                    resultat += page2.getUrl() + "\n\n";
                    resultat += "================================================================================================\n";
                }


            }


        }

        return resultat;
    }

    /**
     * Methode qui fait le Scraping dans CultureFactory.fr
     * @return String resultat
     * @throws IOException
     */
    public String cultureFac()throws  IOException{

        String resultat= "";
        String url = "https://culturefactory.fr/recherche?controller=search&s="+titre+" "+genre;

        if (prixmin.equals("")) {
            prixmin = "0";
        }
        if (prixmax.equals("")) {
            prixmax = "999999999";
        }
        if(date.equals("")){
            date=".";
        }

        WebClient webClient = new WebClient(BrowserVersion.FIREFOX);

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(url);

        List<HtmlElement> liste = htmlPage.getByXPath("//article/div[2]/h4/a");

        for(HtmlElement element :  liste) {

            if ( element.getTextContent().contains(titre) ) {

                if (element.getTextContent().toLowerCase().contains("cd") || element.getTextContent().toLowerCase().contains("vinyl")) {

                    HtmlPage page2 = element.click();
                    String prixArticle = ((HtmlSpan) page2.getByXPath("//span[@class='price ']").get(0)).getTextContent();

                    prixArticle = prixArticle.replace("€", "");
                    prixArticle = prixArticle.replaceAll("\\s+", "");
                    prixArticle = prixArticle.replace("\u00a0", "");
                    prixArticle = prixArticle.replace(",", ".");

                    if (Double.parseDouble(prixmin) <= Double.parseDouble(prixArticle) && Double.parseDouble(prixArticle) <= Double.parseDouble(prixmax)) {

                        String valeur = ((HtmlHeading1) page2.getByXPath("//h1[@class='h1 namne_details']").get(0)).getTextContent();

                        resultat += "Titre de l'album" + valeur + "\n Prix : " + prixArticle + "€\n";


                        String descrip = "";

                        List<HtmlElement> description = page2.getByXPath("//div[@class='product-description']");

                        for (HtmlElement d : description) {
                            descrip += d.getTextContent().replaceAll("(?m)^[ \t]*\r?\n", "") + "\n";
                        }

                        resultat += "Description : " + descrip + "\n";
                        resultat += page2.getUrl() + "\n\n";
                        resultat += "================================================================================================\n";

                        String[] ajouter = {valeur, descrip, prixArticle, date};
                        resuBDD.add(ajouter);



                    }


                }
            }

        }


        if(resultat.equals("")){
            resultat="Aucun résultat trouvé....";
        }


        return resultat;
    }
    /**
     * Methode qui fait le Scraping dans Discogs.fr
     * @return int Genre pour mieux rechercher
     */
    private int trouverMonGenre(String g){

        switch (g) {
            case "Rock":
                return 5;
            case "Blues":
            case "Jazz":
                return 11;
            case "Reggae":
                return 10;
            case "Funk":
                return 9;
            case "Electro":
                return 7;
            case "Soul":
                return 9;
            default:
                return 3;
        }
    }


}//fin Scrapping
