/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;
import Modelo.Clases;
import Modelo.TipoClase;
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
        String sql = "{CALL registrar_clase(?, ?, ?, ?, ?, ?, ?, ?)}"; 
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setString(1, clase.getTipoClase().getNombreBD());
            cs.setString(2, clase.getDescripcion());
            cs.setDouble(3, clase.getPrecio());
            cs.setString(4, clase.getUbicacion());
            cs.setTimestamp(5, Timestamp.valueOf(clase.getHorario())); 
            cs.setInt(6, clase.getCapacidadMax());
            cs.setInt(7, clase.getPersonasInscritas());
            cs.setInt(8, clase.getIdEntrenador());

            int filas = cs.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error registrando clase: " + e.getMessage());
        }
        return false;
    }
    
     public Clases buscarPorId(int idClase) {
        String sql = "{CALL buscar_clase(?)}";
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, idClase);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                TipoClase tipoClase = TipoClase.fromString(rs.getString("tipoClase"));
                return new Clases(
                    rs.getInt("idClase"),
                    tipoClase,
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getString("ubicacion"),
                    rs.getTimestamp("horario").toLocalDateTime(),
                    rs.getInt("capacidadMax"),
                    rs.getInt("personasInscritas"),
                    rs.getInt("idEntrenador")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error buscando clase por id: " + e.getMessage());
        }
        return null;
    }

    public List<Clases> verClasesPorTipo(TipoClase tipoClase) {
        String sql = "{CALL ver_clase_por_tipo(?)}"; 
        List<Clases> lista = new ArrayList<>();
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setString(1, tipoClase.getNombreBD());  
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                lista.add(new Clases(
                    rs.getInt("idClase"),
                    tipoClase,
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getString("ubicacion"),
                    rs.getTimestamp("horario").toLocalDateTime(),
                    rs.getInt("capacidadMax"),
                    rs.getInt("personasInscritas"),
                    rs.getInt("idEntrenador")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error obteniendo clases por tipo: " + e.getMessage());
        }
        return lista;
    }


    public List<Clases> listarTodos() {
        List<Clases> lista = new ArrayList<>();
        String sql = "{CALL ver_clases()}"; 
        try (CallableStatement cs = conexion.prepareCall(sql); 
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                TipoClase tipoClase = TipoClase.fromString(rs.getString("tipoClase"));
                lista.add(new Clases(
                    rs.getInt("idClase"),
                    tipoClase,
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getString("ubicacion"),
                    rs.getTimestamp("horario").toLocalDateTime(),
                    rs.getInt("capacidadMax"),
                    rs.getInt("personasInscritas"),
                    rs.getInt("idEntrenador")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error listando clases: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizarClase(Clases clase) {
        String sql = "{CALL actualizar_clase(?, ?, ?, ?, ?, ?, ?, ?, ?)}"; 
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, clase.getIdClase());
            cs.setString(2, clase.getTipoClase().getNombreBD());
            cs.setString(3, clase.getDescripcion());
            cs.setDouble(4, clase.getPrecio());
            cs.setString(5, clase.getUbicacion());
            cs.setTimestamp(6, Timestamp.valueOf(clase.getHorario())); // Convertir a Timestamp
            cs.setInt(7, clase.getCapacidadMax());
            cs.setInt(8, clase.getPersonasInscritas());
            cs.setInt(9, clase.getIdEntrenador());

            int filas = cs.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizando clase: " + e.getMessage());
        }
        return false;
    }

    public boolean eliminarClase(int idClase) {
        String sql = "{CALL eliminar_clase(?)}"; // Confirmado por la base de datos
        try (CallableStatement cs = conexion.prepareCall(sql)) {
            cs.setInt(1, idClase);
            int filas = cs.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminando clase: " + e.getMessage());
        }
        return false;
    }
}