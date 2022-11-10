package com.example.demo2;

/**
 * Contrôleur de l'IHM des paramètres de la BDD
 * @author Rafael Tavares
 * @version 4.5
 * @since 0.0
 */


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.*;
import java.io.File;

public class BaseDonneesControler {

    @FXML
    private Button valider;

    @FXML
    private Button fermer;

    @FXML
    protected Label labelBDD;

    @FXML
    protected TextField nomServ;
    @FXML
    protected TextField nomBDD;
    @FXML
    protected TextField portBDD;
    @FXML
    protected TextField userBDD;
    @FXML
    protected TextField mdpBDD;

    File paramBDD = new File("ParamBDD.txt");


    /**
     * Methode pour fermer la fenêtre a l'aide d'un bouton
     */
    @FXML
    protected void onFermerClick(){
        Stage stage = (Stage) fermer.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    /**
     * Methode qui permet d'enregistrer les données dans un fichier. Verification d'abord de ces dernières pour être sûr qu'elles sont conformes
     * @throws InterruptedException
     */
    @FXML
    protected void onValiderClick() throws InterruptedException {

        if(nomServ.getText().matches("([0-9]+(\\.[0-9]+)+)") || nomServ.getText().equals("localhost")){
            if(nomBDD.getText().matches("[A-Za-z0-9]+")){
                if(portBDD.getText().matches("[0-9]+")){

                    System.out.println("je suis al");

                    String ecris = nomServ.getText() +"/" + nomBDD.getText()+"/" + portBDD.getText()+"/" + userBDD.getText()+"/" + mdpBDD.getText();
                    ScrapController.enregistrerFicher(ecris,paramBDD);

                    valider.setDisable(true);
                    labelBDD.setText("Enregistré !  Merci");

                    onFermerClick();

                }else{
                    labelBDD.setText("J'ai besoin d'un numero de port valide!");
                }
            }else{
                labelBDD.setText("J'ai besoin d'un nom de base de données valide!");
            }
        }else{
            labelBDD.setText("J'ai besoin d'un nom de serveur valide!");
        }


    }


}
