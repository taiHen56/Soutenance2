package com.example.demo2;

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
//import com.gargoylesoftware.htmlunit.WebClient;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import sendinblue.*;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.*;
import java.sql.Date;



public class EnvoiEmail {





public static void envoi(String pour, File fichier) {


    ApiClient defaultClient = Configuration.getDefaultApiClient();
    // Configure API key authorization: api-key
    ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
    apiKey.setApiKey("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");

    try{

        TransactionalEmailsApi api = new TransactionalEmailsApi();
        SendSmtpEmailSender sender = new SendSmtpEmailSender();
        sender.setEmail("tavaresraf98@gmail.com");
        sender.setName("Raf Tav");

        List<SendSmtpEmailTo> toList = new ArrayList<SendSmtpEmailTo>();
        SendSmtpEmailTo to = new SendSmtpEmailTo();
        to.setEmail(pour);
        toList.add(to);

        SendSmtpEmailReplyTo replyTo = new SendSmtpEmailReplyTo();
        replyTo.setEmail("tavaresraf98@gmail.com");
        replyTo.setName("El Bogosso");

        SendSmtpEmailAttachment attachment = new SendSmtpEmailAttachment();
        attachment.setName("modeEmploi.txt");
        byte[] encode = Files.readAllBytes(Paths.get(fichier.toURI()));
        attachment.setContent(encode);
        List<SendSmtpEmailAttachment> attachmentList = new ArrayList<SendSmtpEmailAttachment>();
        attachmentList.add(attachment);

        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
        sendSmtpEmail.setSender(sender);
        sendSmtpEmail.setTo(toList);
        /*
        sendSmtpEmail.setCc(ccList);
        sendSmtpEmail.setBcc(bccList);
        */
        sendSmtpEmail.setReplyTo(replyTo);
        sendSmtpEmail.setHtmlContent("<html><body><h1>This is my first transactional email </h1></body></html>");
        sendSmtpEmail.setSubject("Test");
        sendSmtpEmail.setAttachment(attachmentList);

        CreateSmtpEmail response = api.sendTransacEmail(sendSmtpEmail);
        System.out.println(response.toString());


    }catch (Exception e){
        System.out.println("Exception occurred:- " + e.getMessage());
    }
}

















}
