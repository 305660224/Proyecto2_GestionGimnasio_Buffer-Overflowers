/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;
import Modelo.ClienteClase;
import Utilidades.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author huete
 */

public class ClienteClaseDAO {
    private final Connection conexion;

    public ClienteClaseDAO() {
        this.conexion = ConexionBD.getInstancia().getConexion();
    }

    public boolean registrarClienteClase(ClienteClase clienteClase) {
        String sql = "{CALL registrar_cliente_clase(?, ?)}"; 
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setString(1, clienteClase.getCedulaCliente());
            cs.setInt(2, clienteClase.getIdClase());

            int filas = cs.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error registrando cliente en clase: " + e.getMessage());
        }
        return false;
    }
    
    public boolean actualizarClienteClase(String cedulaCliente, int nuevoIdClase) {
    String sql = "UPDATE ClienteClase SET idClase = ? WHERE cedulaCliente = ?";
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setInt(1, nuevoIdClase);       
        ps.setString(2, cedulaCliente);   

        int filas = ps.executeUpdate();   
        return filas > 0;                  
    } catch (SQLException e) {
        System.err.println("Error al actualizar la relación ClienteClase: " + e.getMessage());
    }
    return false;  
}

    public List<ClienteClase> VerClasesPorCliente(String cedulaCliente) {
        List<ClienteClase> lista = new ArrayList<>();
        String sql = "{CALL ver_clases_inscrito_cliente(?)}";
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setString(1, cedulaCliente);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                lista.add(new ClienteClase(
                    rs.getString("cedulaCliente"),
                    rs.getInt("idClase")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error obteniendo clases por cliente: " + e.getMessage());
        }
        return lista;
    }

    public boolean eliminarClienteDeClase(String cedulaCliente, int idClase) {
        String sql = "{CALL eliminar_cliente_clase(?, ?)}"; 
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setString(1, cedulaCliente);
            cs.setInt(2, idClase);
            int filas = cs.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminando cliente de clase: " + e.getMessage());
        }
        return false;
    }

    public List<ClienteClase> VerTodosClienteClase() {
        List<ClienteClase> lista = new ArrayList<>();
        String sql = "SELECT * FROM claseCliente";
        try (CallableStatement cs = conexion.prepareCall(sql); 
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                lista.add(new ClienteClase(
                    rs.getString("cedulaCliente"),
                    rs.getInt("idClase")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error listando todas las relaciones cliente-clase: " + e.getMessage());
        }
        return lista;
    }
}