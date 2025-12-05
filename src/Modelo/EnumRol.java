/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Modelo;

/**
 *
 * @author Jeshuan
 */
public enum EnumRol {
  ADMINISTRADOR(1),
  ENTRENADOR(2);
  private final int id;  
  
  EnumRol(int id){
      this.id = id;
  }
  public int getId(){
      return id;
  }
   public static EnumRol porId(int id){
      for(EnumRol r : values()){
          if(r.id == id) return r;
      } 
      throw new IllegalArgumentException("Rol no valido");
    }
  
}
