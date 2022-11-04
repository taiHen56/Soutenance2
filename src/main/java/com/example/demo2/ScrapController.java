package com.example.demo2;

import com.gargoylesoftware.htmlunit.WebClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;


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

    public void HomeScene() throws IOException {
        try{
            WebClient oe = new WebClient();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BaseDonnees.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 450, 515));
            stage.showAndWait();
        }catch(IOException e){
            e.printStackTrace();
        }


    }

    @FXML
    protected void onClickParametresBDD() throws IOException{ HomeScene();}

    @FXML
    protected void onClickRecherche()  {

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
        }


        if(nom==""){

            labelRecherche.setText("J'ai besoin d'une Titre...");

        }else{
            String site="";
            if (discogs.isSelected()) {

                // resultat.setText(Scrapping.discogs(nom,genre, annee,prixMini,prixMaxi));

                site="d";

                ScrapThread scrapTask = new ScrapThread(site,nom,genre, annee,prixMini,prixMaxi);

                Thread scrapThread = new Thread(scrapTask);
                scrapThread.start();
                while(scrapThread.isAlive()){
                    rechercheButton.setDisable(true);
                    barreRecherche.setProgress(0);
                    barreRecherche.progressProperty().unbind();

                   barreRecherche.progressProperty().bind(scrapTask.progressProperty());
                }

                rechercheButton.setDisable(false);
                resultat.setText(scrapTask.getResu());

            } else if (fnac.isSelected()) {

                //resultat.setText(Scrapping.fnac(nom,genre, annee, prixMini, prixMaxi));

                site="f";
                ScrapThread scrapTask = new ScrapThread(site,nom,genre, annee,prixMini,prixMaxi);



                Thread scrapThread = new Thread(scrapTask);
                scrapThread.start();
                while(scrapThread.isAlive()){
                    rechercheButton.setDisable(true);
                    barreRecherche.setProgress(0);
                    barreRecherche.progressProperty().unbind();

                    barreRecherche.progressProperty().bind(scrapTask.progressProperty());
                }

                rechercheButton.setDisable(false);
                resultat.setText(scrapTask.getResu());


            } else if (vinylcorn.isSelected()) {

                //resultat.setText(Scrapping.vinylcorner(nom,genre, annee, prixMini, prixMaxi));

                site="v";
                ScrapThread scrapTask = new ScrapThread(site,nom,genre, annee,prixMini,prixMaxi);

                Thread scrapThread = new Thread(scrapTask);
                scrapThread.start();
                while(scrapThread.isAlive()){
                    rechercheButton.setDisable(true);
                    barreRecherche.setProgress(0);
                    barreRecherche.progressProperty().unbind();

                    barreRecherche.progressProperty().bind(scrapTask.progressProperty());
                }

                rechercheButton.setDisable(false);
                resultat.setText(scrapTask.getResu());


            } else if (leboncoin.isSelected()) {

               // resultat.setText(Scrapping.leboncoin(nom,genre,prixMini,prixMaxi));

                site="l";
                ScrapThread scrapTask = new ScrapThread(site,nom,genre, annee,prixMini,prixMaxi);

                Thread scrapThread = new Thread(scrapTask);
                scrapThread.start();
                while(scrapThread.isAlive()){
                    rechercheButton.setDisable(true);
                    barreRecherche.setProgress(0);
                    barreRecherche.progressProperty().unbind();

                    barreRecherche.progressProperty().bind(scrapTask.progressProperty());
                }

                rechercheButton.setDisable(false);
                resultat.setText(scrapTask.getResu());


            } else if (mesvinyles.isSelected()) {

                //resultat.setText(Scrapping.mesVinyles(nom,genre, annee, prixMini, prixMaxi));
                site="m";
                ScrapThread scrapTask = new ScrapThread(site,nom,genre, annee,prixMini,prixMaxi);

                Thread scrapThread = new Thread(scrapTask);
                scrapThread.start();
                while(scrapThread.isAlive()){
                    rechercheButton.setDisable(true);
                    barreRecherche.setProgress(0);
                    barreRecherche.progressProperty().unbind();

                    barreRecherche.progressProperty().bind(scrapTask.progressProperty());
                }

                rechercheButton.setDisable(false);
                resultat.setText(scrapTask.getResu());


            } else if (culturefac.isSelected()) {

                //resultat.setText(Scrapping.cultureFac(nom,genre,prixMini,prixMaxi));

                site="c";
                ScrapThread scrapTask = new ScrapThread(site,nom,genre, annee,prixMini,prixMaxi);

                Thread scrapThread = new Thread(scrapTask);


                rechercheButton.setDisable(true);
                barreRecherche.setProgress(0);
                barreRecherche.progressProperty().unbind();
                barreRecherche.progressProperty().bind(scrapTask.progressProperty());

                scrapThread.start();

                resultat.setText(scrapTask.getResu());

                /*
                @TODO
                continue a chercher pour la progressbar, tu peux la mettre a 100 des que t'as un resultat oklm
                 */



            } else {
                labelRecherche.setText("Veuillez selectionner un site!");
            }
        }



    }//onClickRecherche

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


    private void enregistrerFicher(String contenu, File fichier){
        try {
            PrintWriter writer = new PrintWriter(fichier);
            writer.println(contenu);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onEnregistrerBDDClick() throws IOException{
        popupBDD();
    }

    public void popupBDD() throws IOException {

        Stage popupwindow=new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Transmission BDD imminente");
        Label label1= new Label("Transmission des données à la base de données ");
        Label label2= new Label("Cliquez sur valider pour lancer la transmission ");

        Button button1= new Button("Valider");
        Button button2= new Button("Annuler");
        button1.setOnAction(e ->label1.setText("NEVER GONNA GIVE YOU UP"));
        button2.setOnAction(e -> popupwindow.close());

        VBox layout= new VBox(10);
        layout.getChildren().addAll(label1,label2,button1,button2);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();

    }

    @FXML
    protected void onEmailClick() throws IOException{
        popupEmail();
    }

    public void popupEmail() throws IOException {

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
                   // label1.setText("NEVER GONNA GIVE YOU UP");

                    enregistrerFicher(resultat.getText(), fichierResultat);

                    EnvoiEmail.envoi(email.getText(),fichierResultat);

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
        StringBuffer sb = new StringBuffer();
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