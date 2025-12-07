/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author huete
 */
public class UsuarioDTO {
    private final int idusuario;
    private final String usuario;
    private final String contrasena; 
    private final String idRol;

    //CONSTRUCTOR
    public UsuarioDTO(int idusuario, String usuario, String contrasena, String idRol) {
        this.idusuario = idusuario;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.idRol = idRol;
    }
   //METODOS
    public int getIdusuario() { return idusuario; }
    public String getUsuario() { return usuario; }
    public String getContrasena() { return contrasena; }
    public String getIdRol() { return idRol; }
}