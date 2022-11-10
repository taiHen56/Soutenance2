package com.example.demo2;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.fail;

public class testEmail
{

    @Test(expected = Exception.class)
    public void testEmail(){

        File f = new File("inexistant.txt");
        String eml = "e";
        EnvoiEmail.envoi(eml,f);
        fail("Fichier existant de preference");
}
}
