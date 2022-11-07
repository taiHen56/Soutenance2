package com.example.demo2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.sql.*;
import java.util.ArrayList;

import java.io.IOException;


public class ConnexionBase {

    public static void InsertRecherche(File fichier,ArrayList<String[]> recherche, int genre){

        System.out.println("On envoi le tout? LETS GO");


        String SQL_INSERT = "INSERT INTO `recherche` (`id_recherche`, `Titre`, `Description`, `Prix`, `id_genre`, `Annee`) VALUES";

        try{
            FileReader fr = new FileReader(fichier);
            // Créer l'objet BufferedReader
            BufferedReader br = new BufferedReader(fr);

            String line;
            String resu = "";
            while((line = br.readLine()) != null)
            {
                resu+=line +"\n";

            }
            fr.close();
            String[] champs = resu.split("[/]",5);
            String url="jdbc:mysql://"+champs[0]+":"+champs[2]+"/"+champs[1];

            Connection con = DriverManager.getConnection(
                    url, champs[3], champs[4]);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM `recherche`");
            rs.next();
            int nbResu=rs.getInt("count(*)");


            for(String[] r : recherche){
                String insert= SQL_INSERT +
                        " ('"+ nbResu +"','"+r[0]+"','"+r[1]+"','"+r[2]+"','"+genre+"','"+r[3]+"')";

                PreparedStatement preparedStatement = con.prepareStatement(insert);
                ResultSet resultSet = preparedStatement.executeQuery();

            }


        }catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (IOException e) {
            System.out.println("Frerot j'arrive pas a lire le fichier, mate l'erreur :" + e);
        }

    }



}
