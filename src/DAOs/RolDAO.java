/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;
import Modelo.Rol;
import Utilidades.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author huete
 */
public class RolDAO {
    private final Connection conexion;

    public RolDAO() {
        this.conexion = ConexionBD.getInstancia().getConexion();
    }

    public boolean registrarRol(Rol rol) {
        String sql = "{CALL agregar_rol(?, ?)}"; 
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setString(1, rol.getTipoRol());
            cs.setInt(2, rol.getIdRol());  

            int filas = cs.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error registrando rol: " + e.getMessage());
        }
        return false;
    }

    public Rol buscarRolPorId(int idRol) {
        String sql = "SELECT * FROM roles WHERE idRol = ?;";
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, idRol);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                return new Rol(
                    rs.getInt("idRol"),
                    rs.getString("tipoRol")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error buscando rol por id: " + e.getMessage());
        }
        return null;
    }

    public List<Rol> verTodosLosRoles() {
        List<Rol> lista = new ArrayList<>();
        String sql = "{CALL ver_roles()}";
        try (CallableStatement cs = conexion.prepareCall(sql); 
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                lista.add(new Rol(
                    rs.getInt("idRol"),
                    rs.getString("tipoRol")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error listando todos los roles: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizarRol(Rol rol) {
        String sql = "{CALL actualizar_rol(?, ?)}"; 
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, rol.getIdRol());
            cs.setString(2, rol.getTipoRol());

            int filas = cs.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizando rol: " + e.getMessage());
        }
        return false;
    }

    public boolean eliminarRol(int idRol) {
        String sql = "{CALL eliminar_rol(?)}";
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, idRol);
            int filas = cs.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminando rol: " + e.getMessage());
        }
        return false;
    }
}
