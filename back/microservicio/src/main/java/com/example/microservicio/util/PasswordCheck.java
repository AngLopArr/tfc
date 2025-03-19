package com.example.microservicio.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordCheck {
    // Método para comprobar si la contraseña pasada corresponde con la contraseña almacenada para el usuario pasado
    public boolean checkPassword(String password, String passwordRecibida) {
        // Se hashea la contraseña pasada
        String passwordHasheada = hashString(passwordRecibida);

        // Se retorna el resultado de comparar la contraseña recibida hasheada y la contraseña hasheada del usuario almacenada en la base de datos
        return passwordHasheada.equals(password);
    }

    // Método para hashear la contraseña con el algoritmo SHA-256
    public String hashString(String cadena) {
        try {
            // Tomo una instancia de MessageDigest configurado con el algoritmo SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Hasheo los bytes de la cadena
            md.update(cadena.getBytes());

            // Devuelvo el resultado de pasar el hasheo a hexadecimal
            return bytesToHex(md.digest());
        } 
        catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    // Método para convertir un array de bytes a cadena hexadecimal
    String bytesToHex(byte[] bytes) {    	
        // Creamos un StringBuilder para construir la cadena hexadecimal
        StringBuilder hexString = new StringBuilder();

        // Establecemos un for que itera sobre cada byte del array
        for (byte b : bytes) {
            // Convertimos el byte en su representación hexadecimal sin signo
            String hex = Integer.toHexString(0xFF & b);

            // Si la representación hexadecimal tiene solo un dígito
            if (hex.length() == 1){
                // Aseguramos dos añadiendo un 0
                hexString.append('0');
            } 
            // Añadimos la representación hexadecimal del byte al StringBuilder
            hexString.append(hex);
        }
        // Devolvemos la cadena resultante
        return hexString.toString();
    }
}
