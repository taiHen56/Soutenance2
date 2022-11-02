package com.example.demo2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.*;

import java.io.IOException;

public class BaseDonneesControler {

    @FXML
    private Button valider;

    @FXML
    private Button fermer;


    @FXML
    protected void onFermerClick(){
        Stage stage = (Stage) fermer.getScene().getWindow();
        // do what you have to do
        stage.close();
    }



}
