package com.example.demo2;
/**
 * Contrôleur de l'IHM principale
 * @author Rafael Tavares
 * @version 4.5
 * @since 0.0
 */


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;


public class ScrapController {

    @FXML
    protected Label labelRecherche;

    @FXML
    protected MenuItem paramBDD;

    @FXML
    protected MenuItem saveFichier;

    @FXML
    protected TextArea resultat;

    /*

    Menu Recherche

     */
    @FXML
    protected ToggleGroup choixSite = new ToggleGroup();


    @FXML
    protected RadioButton discogs;
    @FXML
    protected RadioButton fnac;
    @FXML
    protected RadioButton vinylcorn;
    @FXML
    protected RadioButton leboncoin;
    @FXML
    protected RadioButton mesvinyles;
    @FXML
    protected RadioButton culturefac;

    @FXML
    protected TextField titre;
    @FXML
    protected ComboBox menuGenre;

    @FXML
    protected DatePicker date;

    @FXML
    protected TextField prixMin;
    @FXML
    protected TextField prixMax;

    @FXML
    protected Button effacer;

    @FXML
    protected Button rechercheButton;

    @FXML
    protected ProgressBar barreRecherche;

    File fichierResultat = new File("resultat.txt");
    File modeEmploi = new File("modeEmploi.txt");
    File paramFichier = new File("ParamBDD.txt");


    ArrayList<String[]> resuBDD  = new ArrayList<String[]>();
    int idGenre = 0;
/**
*  Méthode pour créér une fenêtre pour les paramètres BDD
* @throws IOException s'il trouve pas le fichier.xml a prendre
 */
    private void fenetreBDD() throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BaseDonnees.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Parametres Base de Données");
            stage.setScene(new Scene(root, 450, 515));
            stage.showAndWait();
        }catch(IOException e){
            e.printStackTrace();
        }


    }
    /**
     *
     * Methode pour appeler fenetreBDD()
     */
    @FXML
    protected void onClickParametresBDD() throws IOException{ fenetreBDD();}

    /**
     * Methode qui fait les differentes recherche Scraping sur differents sites. C'est egalement ici qu'on donne une valeur a idGenre & la ArrayList
     * qui vont nous servir pour envoyer la recherche dans une BDD
     * Récupère les informations pour dans la fenetre IHM
     * @throws IOException s'il arrive pas a avoir le resultat du Scraping
     */
    @FXML
    protected void onClickRecherche() throws IOException {

        String nom = titre.getText();
        String genre = (String) menuGenre.getValue();
        LocalDate year= date.getValue();
        String annee;
        String prixMini = prixMin.getText();
        String prixMaxi = prixMax.getText();


        if(year==null){

            annee="";

        }else {

             annee = Integer.toString(year.getYear());

        }

        if(genre==null){
            genre="";
        } else if (genre.equals("Rock")) {
            idGenre=1;
        } else if (genre.equals("Blues")) {
            idGenre=2;
        } else if (genre.equals("Jazz")) {
            idGenre=3;
        } else if (genre.equals("Reggae")) {
            idGenre=4;
        } else if (genre.equals("Funk")) {
            idGenre=5;
        } else if (genre.equals("Electro")) {
            idGenre=6;
        } else if (genre.equals("DubStep")) {
            idGenre=7;
        } else if (genre.equals("Soul")) {
            idGenre=8;
        }

        if(Objects.equals(nom, "")){

            labelRecherche.setText("J'ai besoin d'une Titre...");


        }else{
            if (discogs.isSelected()) {


                barreRecherche.setProgress(0);
                Scrapping s= new Scrapping(nom,genre,annee, prixMini,prixMaxi);
                resultat.setText(s.discogs());

                resuBDD.addAll(s.getResuBDD());
                barreRecherche.setProgress(100);



            } else if (fnac.isSelected()) {



                barreRecherche.setProgress(0);
                Scrapping s= new Scrapping(nom,genre,annee, prixMini,prixMaxi);
                resultat.setText(s.fnac());

                resuBDD.addAll(s.getResuBDD());
                barreRecherche.setProgress(100);




            } else if (vinylcorn.isSelected()) {



                barreRecherche.setProgress(0);
                Scrapping s= new Scrapping(nom,genre,annee, prixMini,prixMaxi);
                resultat.setText(s.vinylcorner());

                resuBDD.addAll(s.getResuBDD());
                barreRecherche.setProgress(100);




            } else if (leboncoin.isSelected()) {


                barreRecherche.setProgress(0);
                Scrapping s= new Scrapping(nom,genre,annee, prixMini,prixMaxi);
                resultat.setText(s.leboncoin());

                resuBDD.addAll(s.getResuBDD());
                barreRecherche.setProgress(100);




            } else if (mesvinyles.isSelected()) {

                barreRecherche.setProgress(0);
                Scrapping s= new Scrapping(nom,genre,annee, prixMini,prixMaxi);
                resultat.setText(s.mesVinyles());

                resuBDD.addAll(s.getResuBDD());
                barreRecherche.setProgress(100);




            } else if (culturefac.isSelected()) {
                barreRecherche.setProgress(0);
                Scrapping scrap= new Scrapping(nom,genre,annee, prixMini,prixMaxi);
                String scrapping=scrap.cultureFac();
                resultat.setText(scrapping);

                resuBDD.addAll(scrap.getResuBDD());
                barreRecherche.setProgress(100);




            } else {
                labelRecherche.setText("Veuillez selectionner un site!");
            }
        }



    }//onClickRecherche

    /**
     * Methode pour creer une nouvelle fenetre pour ainsi enregistrer un fichier de type texte
     * @param event
     * @throws FileNotFoundException
     */
    public void saveFile(ActionEvent event) throws FileNotFoundException {

        Stage stage = new Stage();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fichierResultat = fileChooser.showSaveDialog(stage);
        if (fichierResultat != null) {
            PrintWriter writer = new PrintWriter(fichierResultat);
            writer.print("");
            writer.close();
            enregistrerFicher(resultat.getText(), fichierResultat);
        }

    }

    /**
     *
     * methode qui enregistre une string dans un fichier
     * @param contenu a enregistrer
     * @param fichier où enregisrter
     * @throws IOException si le processus est interrompu ou si le fichier n'est plus disponible
     */
    static void enregistrerFicher(String contenu, File fichier){
        try {
            PrintWriter writer = new PrintWriter(fichier);
            writer.println(contenu);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Appelle la methode popupBDD
     * @throws IOException
     */
    @FXML
    protected void onEnregistrerBDDClick() throws IOException{
        popupBDD();
    }

    /**
     *
     * Methode consistant a envoyer la recherche sur la base de données grace aux paramètres ecris au prealable dans un fichier (fenetreBDD())
     * @throws IOException
     */
    private void popupBDD() throws IOException {

        Stage popupwindow=new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Transmission BDD imminente");
        Label label1= new Label("Transmission des données à la base de données ");
        Label label2= new Label("Cliquez sur valider pour lancer la transmission ");

        Button button1= new Button("Valider");
        Button button2= new Button("Annuler");
        button1.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent event) {
                        if(!resuBDD.isEmpty()){
                            if(paramFichier.length()!=0){
                                ConnexionBase.InsertRecherche(paramFichier, resuBDD, idGenre);
                            }else{
                                label1.setText("Définis déjà tes paramètres de BDD oh");
                            }
                        }else{
                            label1.setText("Vous n'avez pas fait de recherche !");
                            label2.setText("hop hop hop non mais oh");
                        }

                    }
                });
        button2.setOnAction(e -> popupwindow.close());

        VBox layout= new VBox(10);
        layout.getChildren().addAll(label1,label2,button1,button2);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();

    }

    /**
     * appelle la methode popoupEmail
     * @throws IOException
     */
    @FXML
    protected void onEmailClick() throws IOException{
        popupEmail();
    }

    /**
     * Envoi un email a condition que ce dernier soit bien ecrit
     * @throws IOException
     */
    private void popupEmail() throws IOException {

        Stage popupwindow=new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Envoi par email");
        Label label1= new Label("Saisie de l'email ");
        Label label2= new Label("Veuillez saisir l'email de l'expéditeur ");


        TextField email = new TextField();

        Button button1= new Button("Valider");
        Button button2= new Button("Annuler");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                if(email.getText().matches(".+@.+" )){
                    if (fichierResultat.length() != 0) {
                        enregistrerFicher(resultat.getText(), fichierResultat);

                        EnvoiEmail.envoi(email.getText(),fichierResultat);
                    }else{
                        label1.setText("Mais fais une recherche déjà avant d'envoyer des emails");
                    }
                }else{
                    label1.setText("C'est pas un email ça bg");
                }
            }
        });
        button2.setOnAction(e -> popupwindow.close());

        VBox layout= new VBox(10);
        layout.getChildren().addAll(label1,label2,email,button1,button2);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();

    }

    /**
     * Reinitialise tout les champs de la fenêtre IHM
     */
    public void onEffacerClick(){

        titre.clear();
        menuGenre.getSelectionModel().clearSelection();
        menuGenre.setPromptText("Selectionnez un genre....");
        date.setValue(null);
        prixMin.clear();
        prixMax.clear();

        discogs.setSelected(false);
        fnac.setSelected(false);
        vinylcorn.setSelected(false);
        leboncoin.setSelected(false);
        mesvinyles.setSelected(false);
        culturefac.setSelected(false);

        barreRecherche.setProgress(0);
        rechercheButton.setDisable(false);


    }

    /**
     * Ouvre une fenêtre pour avoir droit au mode d'emploi
     * @throws IOException
     */
    public void onModeDemploiClick() throws IOException {

        Stage popupwindow=new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Comment utiliser Scraping?");
        TextArea manual = new TextArea();
        manual.setPrefWidth(450);
        manual.setPrefHeight(515);

        FileReader fr = new FileReader(modeEmploi);
        // Créer l'objet BufferedReader
        BufferedReader br = new BufferedReader(fr);
        //StringBuffer sb = new StringBuffer();
        String line;
        String resu = "";
        while((line = br.readLine()) != null)
        {
            resu+=line +"\n";


        }
        fr.close();
        manual.setText(resu);
        VBox layout= new VBox(10);
        layout.getChildren().addAll(manual);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 450, 515);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();

    }




}