/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Jeshuan
 */
public class SesionUsuario {
 //Unica instancia
    private static SesionUsuario instancia;
    
    // 2. Aquí se guardra el usuario que logro entrar
    private Usuario usuarioLogueado;
    
    private SesionUsuario() {}
    
    public static SesionUsuario getInstancia() {
        if (instancia == null) {
            instancia = new SesionUsuario();
        }
        return instancia;
    }
    
    public void login(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }
    
    public void logout() {
        this.usuarioLogueado = null;
    }
    
    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }
    
    public boolean haySesionActiva() {
        return usuarioLogueado != null;
    }
    
    public boolean esAdministrador() {
        return usuarioLogueado != null && usuarioLogueado.getIdRol() == EnumRol.ADMINISTRADOR;
    }
    public boolean esEntrenador(){
        return usuarioLogueado != null && usuarioLogueado.getIdRol() == EnumRol.ENTRENADOR;
    }
}   
