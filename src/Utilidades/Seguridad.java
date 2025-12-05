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
            return md.digest(contraseña.getBytes(StandardCharsets.UTF_8));    
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }
    
    public static boolean verificar(String contraseñaIngresada, byte[] hashGuardado) {
        byte[] hashIngresado = encriptar(contraseñaIngresada);
        return Arrays.equals(hashIngresado, hashGuardado);
    }  
}
