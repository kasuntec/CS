package com.kn.ce.service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */

public class EncryptService {

    public static String getEncryptPass(String pass) {
        String encryptPass = "";
        if (pass != null && pass.length() > 0) {
            try {
                MessageDigest m = MessageDigest.getInstance("MD5");
                m.update(pass.getBytes(), 0, pass.length());
                encryptPass = new BigInteger(1, m.digest()).toString(16);

            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(EncryptService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return encryptPass;
    }
}
