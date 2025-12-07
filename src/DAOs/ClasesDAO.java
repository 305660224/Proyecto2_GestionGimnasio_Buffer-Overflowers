/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;
import Modelo.Clases;
import Utilidades.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author huete
 */
public class ClasesDAO {
    private final Connection conexion;

    public ClasesDAO() {
        this.conexion = ConexionBD.getInstancia().getConexion();
    }

    public boolean registrarClase(Clases clase) {
        String sql = "INSERT INTO clases (tipoClase, descripcion, precio, ubicacion, horario, capacidad) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, clase.getTipoClase());
            ps.setString(2, clase.getDescripcion());
            ps.setDouble(3, clase.getPrecio());
            ps.setString(4, clase.getUbicacion());
            ps.setTimestamp(5, Timestamp.valueOf(clase.getHorario())); // Convertir a Timestamp
            ps.setInt(6, clase.getCapacidad());

            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error registrando clase: " + e.getMessage());
        }
        return false;
    }

    public Clases buscarPorId(int idClase) {
        String sql = "SELECT * FROM clases WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idClase);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Clase(
                    rs.getInt("id"),
                    rs.getString("tipoClase"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getString("ubicacion"),
                    rs.getTimestamp("horario").toLocalDateTime(),
                    rs.getInt("capacidad")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error buscando clase: " + e.getMessage());
        }
        return null;
    }

    public List<Clase> listarTodos() {
        List<Clase> lista = new ArrayList<>();
        String sql = "SELECT * FROM clases ORDER BY horario DESC";
        try (Statement st = conexion.createStatement(); 
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Clase(
                    rs.getInt("id"),
                    rs.getString("tipoClase"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getString("ubicacion"),
                    rs.getTimestamp("horario").toLocalDateTime(),
                    rs.getInt("capacidad")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error listando clases: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizarClase(Clases clase) {
        String sql = "UPDATE clases SET tipoClase = ?, descripcion = ?, precio = ?, ubicacion = ?, horario = ?, capacidad = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, clase.getTipoClase());
            ps.setString(2, clase.getDescripcion());
            ps.setDouble(3, clase.getPrecio());
            ps.setString(4, clase.getUbicacion());
            ps.setTimestamp(5, Timestamp.valueOf(clase.getHorario())); // Convertir a Timestamp
            ps.setInt(6, clase.getCapacidad());
            ps.setInt(7, clase.getId());

            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizando clase: " + e.getMessage());
        }
        return false;
    }

    public boolean eliminarClase(int idClase) {
        String sql = "DELETE FROM clases WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idClase);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminando clase: " + e.getMessage());
        }
        return false;
    }
}

