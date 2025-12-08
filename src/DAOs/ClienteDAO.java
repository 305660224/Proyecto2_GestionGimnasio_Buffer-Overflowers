/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;
import Modelo.Cliente;
import Utilidades.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author huete
 */
public class ClienteDAO {
    private final Connection conexion;

    public ClienteDAO() {
        this.conexion = ConexionBD.getInstancia().getConexion();
    }

    public boolean registrarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (cedula, nombre, primerApellido, segundoApellido, fechaNacimiento, telefono, correo, tipoMembresia) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cliente.getCedula());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getPrimerApellido());
            ps.setString(4, cliente.getSegundoApellido());
            ps.setDate(5, Date.valueOf(cliente.getFechaNacimiento())); // Convertir a Date
            ps.setString(6, cliente.getTelefono());
            ps.setString(7, cliente.getCorreo());
            ps.setInt(8, cliente.getTipoMembresia());

            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error registrando cliente: " + e.getMessage());
        }
        return false;
    }

    public Cliente buscarPorId(String cedula) {
        String sql = "SELECT * FROM clientes WHERE cedula = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Cliente(
                    rs.getString("cedula"),
                    rs.getString("nombre"),
                    rs.getString("primerApellido"),
                    rs.getString("segundoApellido"),
                    rs.getDate("fechaNacimiento").toLocalDate(),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getInt("tipoMembresia")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error buscando cliente: " + e.getMessage());
        }
        return null;
    }

    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Statement st = conexion.createStatement(); 
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Cliente(
                    rs.getString("cedula"),
                    rs.getString("nombre"),
                    rs.getString("primerApellido"),
                    rs.getString("segundoApellido"),
                    rs.getDate("fechaNacimiento").toLocalDate(),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getInt("tipoMembresia")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error listando clientes: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizarCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre = ?, primerApellido = ?, segundoApellido = ?, fechaNacimiento = ?, telefono = ?, correo = ?, tipoMembresia = ? WHERE cedula = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getPrimerApellido());
            ps.setString(3, cliente.getSegundoApellido());
            ps.setDate(4, Date.valueOf(cliente.getFechaNacimiento())); // Convertir a Date
            ps.setString(5, cliente.getTelefono());
            ps.setString(6, cliente.getCorreo());
            ps.setInt(7, cliente.getTipoMembresia());
            ps.setString(8, cliente.getCedula());

            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizando cliente: " + e.getMessage());
        }
        return false;
    }

    public boolean eliminarCliente(String cedula) {
        String sql = "DELETE FROM clientes WHERE cedula = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, cedula);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminando cliente: " + e.getMessage());
        }
        return false;
    }
}