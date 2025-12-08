/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author huete
 */
public class Rol {
    private int idRol;
    private String tipoRol;

    public Rol() {}
    
    //constructor
    public Rol(int idRol, String tipoRol) {
        this.idRol = idRol;
        this.tipoRol = tipoRol;
    }

    //metodos
    public int getIdRol() {
        return idRol;
    }
    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }
    public String getTipoRol() {
        return tipoRol;
    }
    public void setTipoRol(String tipoRol) {
        this.tipoRol = tipoRol;
    }
}