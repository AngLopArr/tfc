package com.example.microservicio.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordCheck {

    public boolean checkPassword(String password, String passwordRecibida) {
        String passwordHasheada = hashString(passwordRecibida);

        return passwordHasheada.equals(password);
    }

    public String hashString(String cadena) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(cadena.getBytes());

            return bytesToHex(md.digest());
        } 
        catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    String bytesToHex(byte[] bytes) {    	
        StringBuilder hexString = new StringBuilder();

        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);

            if (hex.length() == 1){
                hexString.append('0');
            } 

            hexString.append(hex);
        }
        return hexString.toString();
    }
}
