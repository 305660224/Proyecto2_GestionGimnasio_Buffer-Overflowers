/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import java.util.List;
import Modelo.Usuario;
import Modelo.EnumRol;
import Modelo.SesionUsuario;
import DAOs.UsuarioDAO;
import Utilidades.Seguridad;
/**
 *
 * @author Jeshuan
 */
public class ControladorUsuario {
    private UsuarioDAO usuarioDAO;
    
    public ControladorUsuario(){
       this.usuarioDAO = new UsuarioDAO(); 
    }
     
    /**
     * Método para registrar un nuevo empleado en el sistema.
     * Este método lo usará la ventana de "Administración de Usuarios".
     */
    public boolean agregarUsuario(String nombreUsuario, String passwordPlana, int idRol) {
        if (nombreUsuario == null || nombreUsuario.isEmpty()) {
            System.out.println("Error: El usuario no puede estar vacío");
            return false;
        }
        
        if (usuarioDAO.buscarPorUsername(nombreUsuario) != null) {
            System.out.println("Error: El nombre de usuario ya existe");
            return false;
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsuario(nombreUsuario);

        byte[] passEncriptada = Seguridad.encriptar(passwordPlana);
        nuevoUsuario.setContrasena(passEncriptada);

        nuevoUsuario.setIdRol(EnumRol.porId(idRol));
        return usuarioDAO.registrar(nuevoUsuario);
    }
    public boolean eliminarUsuario(int idUsuario) {
        Usuario logueado = SesionUsuario.getInstancia().getUsuarioLogueado();
        if (logueado != null && logueado.getIdusuario() == idUsuario) {
            return false;
        }   
        return usuarioDAO.eliminar(idUsuario);
    }   
    
    /**
     * Método para listar todos los usuarios y mostrarlos en una tabla
     */
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioDAO.listarTodos(); 
    }
    public Usuario buscarPorUsername(String username) {
        return usuarioDAO.buscarPorUsername(username);
    }

    public boolean actualizarUsuario(int id, String usuario, String passwordPlana, int idRol) {
         Usuario u = new Usuario();
    u.setIdusuario(id);
    u.setUsuario(usuario);

    if (passwordPlana != null && !passwordPlana.isEmpty()) {
        u.setContrasena(Seguridad.encriptar(passwordPlana));
    } else {
        Usuario usuarioExistente = buscarPorId(id);
        if (usuarioExistente != null) {
            u.setContrasena(usuarioExistente.getContrasena());
        } else {

            u.setContrasena(Seguridad.encriptar(""));
        }
    }
    
    u.setIdRol(EnumRol.porId(idRol));
    
    return usuarioDAO.actualizar(u);
}

public Usuario buscarPorId(int id) {
    return usuarioDAO.buscarPorId(id);
}
  
}
