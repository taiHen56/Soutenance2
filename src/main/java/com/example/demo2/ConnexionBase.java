package com.example.demo2;

import java.io.*;

import java.sql.*;
import java.util.ArrayList;


public class ConnexionBase {

    public static void InsertRecherche(File fichier, ArrayList<String[]> recherche, int genre) {

        System.out.println("On envoi le tout? LETS GO");


        String SQL_INSERT = "INSERT INTO `recherche` (`Titre`, `Description`, `Prix`, `id_genre`, `Annee`) VALUES (?,?,?,?,?)";

        String[] donnnes = getDonnees(fichier);

        try (Connection con = DriverManager.getConnection(
                donnnes[0], donnnes[1], "");
             PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT)
        ) {





            for (String[] r : recherche) {

                preparedStatement.setString(1, r[0]);//titre
                preparedStatement.setString(2, r[1]);//descriotion
                preparedStatement.setDouble(3, Double.parseDouble(r[2]));//prix
                preparedStatement.setInt(4, genre);//id genre
                preparedStatement.setString(5, r[3]);//annee

                preparedStatement.execute();

            }

            System.out.println("Fini!");


        } catch (SQLException e) {
            System.out.println("Oups, petit flop, verifie tes données.\n");
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch(Exception e){
            e.printStackTrace();
        }
    }

        private static String[] getDonnees(File fichier){


            String[] donnees = new String[3];

            try {
                FileReader fr = new FileReader(fichier);
                // Créer l'objet BufferedReader
                BufferedReader br = new BufferedReader(fr);
                String line;
                String resu = "";
                while ((line = br.readLine()) != null) {
                    resu += line + "\n";

                }
                fr.close();
                String[] champs = resu.split("[/]", 5);

                String url = "jdbc:mysql://" + champs[0] + ":" + champs[2] + "/" + champs[1];

                donnees[0] = url;
                donnees[1] = champs[3];
                donnees[2] = champs[4];

            } catch (IOException e) {
                System.out.println("Frerot j'arrive pas a lire le fichier, mate l'erreur :" + e);
            }

            return donnees;
        }


    }

