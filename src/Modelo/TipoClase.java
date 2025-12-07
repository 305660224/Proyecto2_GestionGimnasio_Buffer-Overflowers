/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Modelo;

/**
 *
 * @author Jeshuan
 */
public enum TipoClase {
   YOGA("Yoga"),
   CROSSFIT("Crossfit"),
   ZUMBA("Zumba"),
   PILATES("Pilates"),
   CARDIO("Cardio");
   private final String nombreBD;

    private TipoClase(String nombreBD) {
        this.nombreBD = nombreBD;
    }

    public String getNombreBD() {
        return nombreBD;
    }
    //Método estatico utilitarario para convertir el texto de la BD a Enum en Java
    //Esto se usara en la clasesDAO cuando se haga el rst.getString("tipoClase)
   public static TipoClase fromString(String texto){
     for(TipoClase tipo: TipoClase.values()){
         if(tipo.nombreBD.equalsIgnoreCase(texto)){
            return tipo;
         }
     }  
     throw new IllegalArgumentException("Tipo de clase desconocido" +texto);
   }
   
}
