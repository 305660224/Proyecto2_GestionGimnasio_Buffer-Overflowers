/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
/**
 *
 * @author Jeshuan
 */
public class Seguridad {
  public static byte[] encriptar(String contraseña) {
        try {
         MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(contraseña.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString().getBytes(StandardCharsets.UTF_8);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }
    
    public static boolean verificar(String contraseñaIngresada, byte[] hashGuardado) {
        byte[] hashIngresado = encriptar(contraseñaIngresada);
        return Arrays.equals(hashIngresado, hashGuardado);
    }  
}
