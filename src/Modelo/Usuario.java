/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Jeshuan
 */
public class Usuario {
  private int idusuario;
  private String usuario;
  private byte[] contrasena;
  private EnumRol idRol;
   
   public Usuario(){
       
   }
    public Usuario(int idusuario, String usuario, byte[] contrasena, EnumRol idRol) {
        this.idusuario = idusuario;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.idRol = idRol;
    }

    public int getIdusuario() { return idusuario;}

    public String getUsuario() {
        return usuario;
    }

    public byte[] getContrasena() {
        return contrasena;
    }

    public EnumRol getIdRol() {
        return idRol;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasena(byte[] contrasena) {
        this.contrasena = contrasena;
    }

    public void setIdRol(EnumRol idRol) {
        this.idRol = idRol;
    }
}
