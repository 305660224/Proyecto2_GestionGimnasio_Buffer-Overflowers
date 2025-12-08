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
        String sql = "INSERT INTO entrenadores (nombre, primerApellido, segundoApellido, telefono, correo, especialidades) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entrenador.getNombre());
            ps.setString(2, entrenador.getPrimerApellido());
            ps.setString(3, entrenador.getSegundoApellido());
            ps.setString(4, entrenador.getTelefono());
            ps.setString(5, entrenador.getCorreo());
            ps.setString(6, entrenador.getEspecialidades());

            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error registrando entrenador: " + e.getMessage());
        }
        return false;
    }

    public Entrenador buscarPorId(int idEntrenador) {
        String sql = "SELECT * FROM entrenadores WHERE idEntrenador = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idEntrenador);
            ResultSet rs = ps.executeQuery();
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
        String sql = "SELECT * FROM entrenadores";
        try (Statement st = conexion.createStatement(); 
             ResultSet rs = st.executeQuery(sql)) {
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
        String sql = "UPDATE entrenadores SET nombre = ?, primerApellido = ?, segundoApellido = ?, telefono = ?, correo = ?, especialidades = ? WHERE idEntrenador = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, entrenador.getNombre());
            ps.setString(2, entrenador.getPrimerApellido());
            ps.setString(3, entrenador.getSegundoApellido());
            ps.setString(4, entrenador.getTelefono());
            ps.setString(5, entrenador.getCorreo());
            ps.setString(6, entrenador.getEspecialidades());
            ps.setInt(7, entrenador.getIdEntrenador());

            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizando entrenador: " + e.getMessage());
        }
        return false;
    }

    public boolean eliminarEntrenador(int idEntrenador) {
        String sql = "DELETE FROM entrenadores WHERE idEntrenador = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idEntrenador);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminando entrenador: " + e.getMessage());
        }
        return false;
    }
}
