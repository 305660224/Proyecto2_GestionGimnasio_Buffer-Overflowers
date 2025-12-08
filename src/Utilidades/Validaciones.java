/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 *
 * @author Jeshuan
 */
public class Validaciones {
  private static final String REGEX_CORREO = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.com$";  
  private static final String REGEX_TELEFONO = "^[678][0-9]{7}$";
  
  //Valida si un correo tiene el formato valido
  public static boolean esCorreoValido(String correo) {
        if (correo == null || correo.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGEX_CORREO);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }

    //Valida si un numero es correcto en el formato tico
    public static boolean esTelefonoValido(String telefono) {
        if (telefono == null || telefono.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGEX_TELEFONO);
        Matcher matcher = pattern.matcher(telefono);
        return matcher.matches();
    }
    public static boolean esCedulaValida(String cedula){
        if(cedula == null) return false;
        return cedula.matches("^[1-8]\\d{8}(\\d{2})?$");
    }
     public static LocalDate parsearFecha(String fechaTexto) {
        if (fechaTexto == null || fechaTexto.trim().isEmpty()) {
            return null;
        }
        
        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(fechaTexto, formato);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
      public static LocalDateTime parsearFechaHora(String fechaHoraTexto) {
        if (fechaHoraTexto == null || fechaHoraTexto.trim().isEmpty()) {
            return null;
        }
        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return LocalDateTime.parse(fechaHoraTexto, formato);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
}
