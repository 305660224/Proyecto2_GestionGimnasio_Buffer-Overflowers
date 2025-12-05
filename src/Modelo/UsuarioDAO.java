/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Utilidades.ConexionBD;
/**
 *
 * @author Jeshuan
 */
public class UsuarioDAO implements UsuarioIN {
private final Connection conexion;

public UsuarioDAO(){
   this.conexion = ConexionBD.getInstancia().getConexion();
}
    @Override
    public Usuario buscarPorUsername(String usuario) {
        String sql = "SELECT idUsuario, usuario, contrasena, idRol FROM usuario WHERE usuarios = ?";
        Usuario u = null;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u = new Usuario();
                    u.setIdusuario(rs.getInt("idusuario"));
                    u.setUsuario(rs.getString("usuario"));
                    
                    u.setContrasena(rs.getBytes("contrasena")); 

                    int rolId = rs.getInt("idRol");
                    u.setIdRol(EnumRol.porId(rolId)); 
                }
            }
        } catch (SQLException e) {
            System.err.println("Error buscando usuario: " + e.getMessage());
        }
        return u;
    }

    @Override
    public boolean registrar(Usuario usuario) {
     String sql = "{call agregar_usuario(?, ?, ?)}";
    
    try (java.sql.CallableStatement cs = conexion.prepareCall(sql)) {
        
        cs.setString(1, usuario.getUsuario());
        cs.setBytes(2, usuario.getContrasena()); 
        cs.setInt(3, usuario.getIdRol().getId()); 
      
        return cs.executeUpdate() > 0;
        
    } catch (SQLException e) {
        System.err.println("Error llamando al procedimiento: " + e.getMessage());
        return false;
    }
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        String sql = "{call ver_usuarios()}"; 
        try (java.sql.CallableStatement cs = conexion.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                
                u.setIdusuario(rs.getInt("idUsuario"));
                u.setUsuario(rs.getString("usuario"));
                u.setContrasena(rs.getBytes("contrasena"));
                
                int idRolBD = rs.getInt("idRol"); 
                u.setIdRol(EnumRol.porId(idRolBD));

                listaUsuarios.add(u);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        }
       
        return listaUsuarios;
    }
    public Usuario buscarPorId(int id) {
        String sql = "SELECT idUsuario, usuario, contrasena, idRol FROM usuarios WHERE idUsuario = ?";
        Usuario u = null;
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u = new Usuario();
                    u.setIdusuario(rs.getInt("idUsuario"));
                    u.setUsuario(rs.getString("usuario"));
                    u.setContrasena(rs.getBytes("contrasena")); 
                    
                    int rolId = rs.getInt("idRol");
                    u.setIdRol(EnumRol.porId(rolId));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error buscando usuario por ID: " + e.getMessage());
        }
        return u;
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        String sql = "{call actualizar_usuario(?, ?, ?, ?)}";

        try (java.sql.CallableStatement cs = conexion.prepareCall(sql)) {
            
            cs.setInt(1, usuario.getIdusuario());    // p_id
            cs.setString(2, usuario.getUsuario());   // p_usuario
            cs.setBytes(3, usuario.getContrasena()); // p_contra
            cs.setInt(4, usuario.getIdRol().getId()); // p_idRol

            return cs.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error actualizando usuario: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "{call eliminar_usuario(?)}";

        try (java.sql.CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, id); // p_id
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminando usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean cambiarContrasena(int idUsuario, byte[] nuevaContrasena) {
        String sql = "UPDATE usuarios SET contrasena = ? WHERE idUsuario = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setBytes(1, nuevaContrasena);
            ps.setInt(2, idUsuario);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error cambiando contraseña: " + e.getMessage());
            return false;
        }
    }
    }
    

