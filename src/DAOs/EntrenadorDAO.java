/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Modelo.Entrenador;
import Utilidades.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author huete
 */

public class EntrenadorDAO {
    private final Connection conexion;

    public EntrenadorDAO() {
        this.conexion = ConexionBD.getInstancia().getConexion();
    }

    public boolean registrarEntrenador(Entrenador entrenador) {
        String sql = "{CALL agregar_entrenador(?, ?, ?, ?, ?)}"; 
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setString(1, entrenador.getNombre());
            cs.setString(2, entrenador.getPrimerApellido());
            cs.setString(3, entrenador.getSegundoApellido());
            cs.setString(4, entrenador.getTelefono());
            cs.setString(5, entrenador.getCorreo());

            int filas = cs.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error registrando entrenador: " + e.getMessage());
        }
        return false;
    }

    public Entrenador buscarPorId(int idEntrenador) {
        String sql = "{CALL ver_entrenador(?)}"; 
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, idEntrenador);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                return new Entrenador(
                    rs.getInt("idEntrenador"),
                    rs.getString("nombre"),
                    rs.getString("primerApellido"),
                    rs.getString("segundoApellido"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("especialidades")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error buscando entrenador: " + e.getMessage());
        }
        return null;
    }

    public List<Entrenador> listarTodos() {
        List<Entrenador> lista = new ArrayList<>();
        String sql = "{CALL ver_entrenadores()}"; 
        try (CallableStatement cs = conexion.prepareCall(sql); 
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                lista.add(new Entrenador(
                    rs.getInt("idEntrenador"),
                    rs.getString("nombre"),
                    rs.getString("primerApellido"),
                    rs.getString("segundoApellido"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("especialidades")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error listando entrenadores: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizarEntrenador(Entrenador entrenador) {
        String sql = "{CALL actualizar_entrenador(?, ?, ?, ?, ?, ?)}"; 
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, entrenador.getIdEntrenador());
            cs.setString(2, entrenador.getNombre());
            cs.setString(3, entrenador.getPrimerApellido());
            cs.setString(4, entrenador.getSegundoApellido());
            cs.setString(5, entrenador.getTelefono());
            cs.setString(6, entrenador.getCorreo());

            int filas = cs.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizando entrenador: " + e.getMessage());
        }
        return false;
    }

    public boolean eliminarEntrenador(int idEntrenador) {
        String sql = "{CALL eliminar_entrenador(?)}"; 
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, idEntrenador);
            int filas = cs.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminando entrenador: " + e.getMessage());
        }
        return false;
    }
}