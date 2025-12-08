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

    public boolean registrarPago(Pago pago) {
        String sql = "{CALL agregar_pago(?, ?)}"; 
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setString(1, pago.getCedulaCliente());
            cs.setTimestamp(2, new java.sql.Timestamp(pago.getFechaPago().getTime())); 

            int filas = cs.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error registrando pago: " + e.getMessage());
        }
        return false;
    }

    public boolean eliminarPago(int idPago) {
        String sql = "{CALL eliminar_pago(?)}"; 
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, idPago);
            int filas = cs.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminando pago: " + e.getMessage());
        }
        return false;
    }

    //buscar pago por idPago
    public Pago buscarPorId(int idPago) {
        String sql = "SELECT idPago, cedulaCliente, fechaPago FROM historialpagos WHERE idPago = ?";
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, idPago);
            ResultSet rs = cs.executeQuery();
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

    public List<Pago> listarTodos() {
        List<Pago> lista = new ArrayList<>();
        String sql = "{CALL ver_historial_pagos()}"; 
        try (CallableStatement cs = conexion.prepareCall(sql); 
             ResultSet rs = cs.executeQuery()) {
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
}