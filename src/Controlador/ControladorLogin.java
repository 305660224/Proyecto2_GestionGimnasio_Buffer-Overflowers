/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Modelo.Usuario;
import Modelo.SesionUsuario;
import Modelo.UsuarioIN;
import Modelo.UsuarioDAO;
import Utilidades.Seguridad;
/**
 *
 * @author Jeshuan
 */
public class ControladorLogin {
    private UsuarioDAO usuarioDAO;

    public ControladorLogin() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public boolean iniciarSesion(String username, String passwordPlana) {
        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        
        if (usuario == null) {
            System.out.println("El usuario no existe.");
            return false;
        }
        
        if (Seguridad.verificar(passwordPlana, usuario.getContrasena())) {
            
            SesionUsuario.getInstancia().login(usuario);
            System.out.println("Login correcto. Rol: " + usuario.getIdRol());
            return true;
            
        } else {
            System.out.println("Contraseña incorrecta.");
            return false;
        }
    }
    public void cerrarSesion() {
        SesionUsuario.getInstancia().logout();
        System.out.println("Sesión cerrada correctamente.");
    }
}
