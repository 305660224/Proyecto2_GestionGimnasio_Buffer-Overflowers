/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import DAOs.EntrenadorDAO;
import Modelo.Entrenador;
import Utilidades.Validaciones; 
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author Jeshuan
 */
public class ControladorEntrenador {
    private final EntrenadorDAO entrenadorDAO;
    
    public ControladorEntrenador(){
        this.entrenadorDAO = new EntrenadorDAO();
    }
    public boolean registrarEntrenador(String nombre, String primerApellido, String segundoApellido, 
                                       String telefono, String correo, String especialidad) {

        if (!Validaciones.esTelefonoValido(telefono)) {
            JOptionPane.showMessageDialog(null, "Teléfono inválido (Debe empezar con 6, 7 u 8 y tener 8 dígitos).");
            return false;
        }

        if (!Validaciones.esCorreoValido(correo)) {
            JOptionPane.showMessageDialog(null, "Correo inválido (Verifique el formato).");
            return false;
        }

        // Crear objeto (ID va en 0 porque es auto-incremental en BD)
        Entrenador entrenador = new Entrenador(
            0, 
            nombre, 
            primerApellido, 
            segundoApellido, 
            telefono, 
            correo, 
            especialidad
        );

        if (entrenadorDAO.registrarEntrenador(entrenador)) {
            JOptionPane.showMessageDialog(null, "Entrenador registrado con éxito.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar en la base de datos.");
            return false;
        }
    }

    public List<Entrenador> listarEntrenadores() {
        return entrenadorDAO.listarTodos();
    }

    public boolean actualizarEntrenador(String idStr, String nombre, String primerApellido, String segundoApellido, 
                                        String telefono, String correo, String especialidad) {
        
        int idEntrenador;
        try {
            idEntrenador = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número válido.");
            return false;
        }

        if (!Validaciones.esTelefonoValido(telefono)) {
            JOptionPane.showMessageDialog(null, "Teléfono inválido.");
            return false;
        }
        
        if (!Validaciones.esCorreoValido(correo)) {
            JOptionPane.showMessageDialog(null, "Correo inválido.");
            return false;
        }

        Entrenador entrenador = new Entrenador(
            idEntrenador, 
            nombre, 
            primerApellido, 
            segundoApellido, 
            telefono, 
            correo, 
            especialidad
        );

        if (entrenadorDAO.actualizarEntrenador(entrenador)) {
            JOptionPane.showMessageDialog(null, "Entrenador actualizado con éxito.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar el entrenador.");
            return false;
        }
    }

    public boolean eliminarEntrenador(String idStr) {
        // Validar que hay ID
        if (idStr == null || idStr.trim().isEmpty()) {
             JOptionPane.showMessageDialog(null, "Debe ingresar un ID para eliminar.");
             return false;
        }

        // Validar que sea número
        int idEntrenador;
        try {
            idEntrenador = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID debe ser numérico.");
            return false;
        }

        if (entrenadorDAO.eliminarEntrenador(idEntrenador)) {
            JOptionPane.showMessageDialog(null, "Entrenador eliminado con éxito.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al eliminar (Verifique que el ID exista).");
            return false;
        }
    }

    public Entrenador buscarEntrenador(String idStr) {
        if (idStr == null || idStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID para buscar.");
            return null;
        }
        
        try {
            int id = Integer.parseInt(idStr);
            return entrenadorDAO.buscarPorId(id);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID debe ser numérico.");
            return null;
        }
    }
}
    
  