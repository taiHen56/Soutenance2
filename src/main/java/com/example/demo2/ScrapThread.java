package com.example.demo2;

import javafx.concurrent.Task;

import java.io.IOException;

public class ScrapThread extends Task {

    private String site,nom,genre,annee,mini,max;
    private String out;

    public ScrapThread(String site, String nom,String genre,String annee,String mini,String max){

        this.site=site;
        this.nom=nom;
        this.genre=genre;
        this.annee=annee;
        this.mini=mini;
        this.max=max;
        out=null;
    }

    public Object call(){
        try {
        if(site.equals("d")){

            out=Scrapping.discogs(nom,genre, annee,mini,max);

        }else if(site.equals("f")){

            out=Scrapping.fnac(nom,genre, annee,mini,max);

        } else if (site.equals("v")) {

            out=Scrapping.vinylcorner(nom,genre, annee,mini,max);

        } else if (site.equals("l")) {

            out=Scrapping.leboncoin(nom,genre, mini,max);


        }else if ((site.equals("m"))){

            out=Scrapping.mesVinyles(nom,genre, annee,mini,max);


        } else if (site.equals("c")) {

            out=Scrapping.cultureFac(nom,genre,mini,max);

        }
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

        return null;
    }

    public String getResu(){
        return out;
    }


}
