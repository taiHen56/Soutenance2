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
        out="nada amigo";
    }

    public Object call(){
        if(site.equals("d")){

            //out=Scrapping.discogs(nom,genre, annee,mini,max);

        }else if(site.equals("f")){

            //out=Scrapping.fnac(nom,genre, annee,mini,max);

        } else if (site.equals("v")) {

            //out=Scrapping.vinylcorner(nom,genre, annee,mini,max);

        } else if (site.equals("l")) {

            //out=Scrapping.leboncoin(nom,genre,annee, mini,max);


        }else if ((site.equals("m"))){

            //ut=Scrapping.mesVinyles(nom,genre, annee,mini,max);


        } else if (site.equals("c")) {

            //out=Scrapping.cultureFac(nom,genre,annee,mini,max);

        }

        return null;
    }

    public String getResu(){
        return out;
    }


}
