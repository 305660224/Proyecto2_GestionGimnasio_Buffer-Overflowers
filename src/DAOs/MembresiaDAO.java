/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;
import Modelo.Membresia;
import Utilidades.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author huete
 */
public class MembresiaDAO {
    private final Connection conexion;

    public MembresiaDAO() {
        this.conexion = ConexionBD.getInstancia().getConexion();
    }

    // CREATE: Registrar una nueva membresía
    public boolean registrarMembresia(Membresia membresia) {
        String sql = "{CALL registrar_membresia(?, ?, ?, ?, ?)}"; 
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setString(1, membresia.getNombre());
            cs.setDouble(2, membresia.getPrecio());
            cs.setInt(3, membresia.getDuracionDias());
            cs.setString(4, membresia.getBeneficios());
            cs.setInt(5, membresia.getIdMembresia()); // Si idMembresia es auto-incremental, este parámetro se podría omitir

            int filas = cs.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error registrando membresía: " + e.getMessage());
        }
        return false;
    }

    // READ: Buscar membresía por id
    public Membresia buscarPorId(int idMembresia) {
        String sql = "{CALL buscar_membresia(?)}";
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, idMembresia);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                return new Membresia(
                    rs.getInt("idMembresia"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getInt("duracionDias"),
                    rs.getString("beneficios")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error buscando membresía por id: " + e.getMessage());
        }
        return null;
    }

    // READ: Listar todas las membresías
    public List<Membresia> listarTodos() {
        List<Membresia> lista = new ArrayList<>();
        String sql = "{CALL ver_membresias()}";
        try (CallableStatement cs = conexion.prepareCall(sql); 
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                lista.add(new Membresia(
                    rs.getInt("idMembresia"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getInt("duracionDias"),
                    rs.getString("beneficios")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error listando todas las membresías: " + e.getMessage());
        }
        return lista;
    }

    // UPDATE: Actualizar una membresía existente
    public boolean actualizarMembresia(Membresia membresia) {
        String sql = "{CALL actualizar_membresia(?, ?, ?, ?, ?)}"; 
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, membresia.getIdMembresia());
            cs.setString(2, membresia.getNombre());
            cs.setDouble(3, membresia.getPrecio());
            cs.setInt(4, membresia.getDuracionDias());
            cs.setString(5, membresia.getBeneficios());

            int filas = cs.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizando membresía: " + e.getMessage());
        }
        return false;
    }

    // DELETE: Eliminar una membresía por id
    public boolean eliminarMembresia(int idMembresia) {
        String sql = "{CALL eliminar_membresia(?)}";
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, idMembresia);
            int filas = cs.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminando membresía: " + e.getMessage());
        }
        return false;
    }
}
