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
        String sql = "{CALL agregar_cliente(?, ?, ?, ?, ?, ?, ?, ?)}";
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setString(1, cliente.getCedula());
            cs.setString(2, cliente.getNombre());
            cs.setString(3, cliente.getPrimerApellido());
            cs.setString(4, cliente.getSegundoApellido());
            cs.setDate(5, Date.valueOf(cliente.getFechaNacimiento())); 
            cs.setString(6, cliente.getTelefono());
            cs.setString(7, cliente.getCorreo());
            cs.setInt(8, cliente.getTipoMembresia());

            int filas = cs.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error registrando cliente: " + e.getMessage());
        }
        return false;
    }

    public Cliente buscarPorId(String cedula) {
        String sql = "{CALL ver_cliente(?)}"; 
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setString(1, cedula.trim());
            ResultSet rs = cs.executeQuery();
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
        String sql = "{CALL ver_clientes()}";
        try (CallableStatement cs = conexion.prepareCall(sql); 
             ResultSet rs = cs.executeQuery()) {
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
        String sql = "{CALL actualizar_cliente(?, ?, ?, ?, ?, ?, ?, ?)}"; 
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setString(1, cliente.getCedula());
            cs.setString(2, cliente.getNombre());
            cs.setString(3, cliente.getPrimerApellido());
            cs.setString(4, cliente.getSegundoApellido());
            cs.setDate(5, Date.valueOf(cliente.getFechaNacimiento())); 
            cs.setString(6, cliente.getTelefono());
            cs.setString(7, cliente.getCorreo());
            cs.setInt(8, cliente.getTipoMembresia());

            int filas = cs.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizando cliente: " + e.getMessage());
        }
        return false;
    }

    public boolean eliminarCliente(String cedula) {
    String sql = "{CALL eliminar_cliente(?)}";
    try (CallableStatement cs = conexion.prepareCall(sql)) {
        cs.setString(1, cedula.trim());

        cs.execute();
        return true; 
        
    } catch (SQLException e) {
        return false;
    }
}
}