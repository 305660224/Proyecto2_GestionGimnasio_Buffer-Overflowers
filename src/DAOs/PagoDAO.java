/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Modelo.Pago;
import Utilidades.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class PagoDAO {
    private final Connection conexion;

    public PagoDAO() {
        this.conexion = ConexionBD.getInstancia().getConexion();
    }
    
    // Registra solo cedulaCliente y fecha en BD
    public boolean registrarPago(Pago pago) {
        String sql = "INSERT INTO historialpagos (cedulaCliente, fechaPago) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, pago.getCedulaCliente());
            ps.setTimestamp(2, new java.sql.Timestamp(pago.getFechaPago().getTime()));
            
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error registrando pago: " + e.getMessage());
        }
        return false;
    }
    
    // Busca por ID (solo devuelve los 3 campos de BD)
    public Pago buscarPorId(int idPago) {
        String sql = "SELECT idPago, cedulaCliente, fechaPago FROM historialpagos WHERE idPago = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idPago);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Pago(
                    rs.getInt("idPago"),
                    rs.getString("cedulaCliente"),
                    rs.getTimestamp("fechaPago")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error buscando pago: " + e.getMessage());
        }
        return null;
    }
    
    // Lista todos (solo los 3 campos de BD)
    public List<Pago> listarTodos() {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT * FROM historialpagos ORDER BY fechaPago DESC";
        try (Statement st = conexion.createStatement(); 
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Pago(
                    rs.getInt("idPago"),
                    rs.getString("cedulaCliente"),
                    rs.getTimestamp("fechaPago")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error listando pagos: " + e.getMessage());
        }
        return lista;
    }
    
    // Obtiene último ID insertado
    public int obtenerUltimoIdInsertado() {
        String sql = "SELECT LAST_INSERT_ID() as last_id";
        try (Statement st = conexion.createStatement(); 
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("last_id");
            }
        } catch (SQLException e) {
            System.err.println("Error obteniendo último ID: " + e.getMessage());
        }
        return -1;
    }
}