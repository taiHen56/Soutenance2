package com.example.demo2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.*;

import java.io.File;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

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



    @FXML
    protected void onFermerClick(){
        Stage stage = (Stage) fermer.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    protected void onValiderClick(){

        if(nomServ.getText().matches("([0-9]+(\\.[0-9]+)+)") || nomServ.getText().equals("localhost")){
            if(nomBDD.getText().matches("[A-Za-z0-9]+")){
                if(portBDD.getText().matches("[0-9]+")){

                    String ecris = nomServ.getText() +"/" + nomBDD.getText()+"/" + portBDD.getText()+"/" + userBDD.getText()+"/" + mdpBDD.getText();

                    ScrapController.enregistrerFicher(ecris,paramBDD);

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
