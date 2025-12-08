/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author huete
 */
public class RolDTO {
    private final int idRol;
    private final String tipoRol;

    //CONSTRUCTOR
    public RolDTO(int idRol, String tipoRol) {
        this.idRol = idRol;
        this.tipoRol = tipoRol;
    }

    //METODOS
    public int getIdRol() { 
        return idRol; 
    }
    public String getTipoRol() { 
        return tipoRol; 
    }
}