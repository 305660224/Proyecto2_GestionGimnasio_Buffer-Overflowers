/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import DAOs.ClasesDAO;
import Modelo.Clases;
import Modelo.TipoClase;
import Utilidades.Validaciones; 
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.JOptionPane;



/**
 *
 * @author Jeshuan
 */
public class ControladorClases {
    private final ClasesDAO clasesDAO;
    
    public ControladorClases(){
       this.clasesDAO = new ClasesDAO(); 
    }
  public boolean registrarClase(TipoClase tipo, String descripcion, String precioStr, 
                                  String ubicacion, String fechaHoraStr, String capacidadStr, 
                                  String idEntrenadorStr) {
        
        if (descripcion.trim().isEmpty() || ubicacion.trim().isEmpty() || fechaHoraStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Descripción, Ubicación y Horario son obligatorios.");
            return false;
        }
        
        if (tipo == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un tipo de clase.");
            return false;
        }

        double precio;
        int capacidad;
        int idEntrenador;
        try {
            precio = Double.parseDouble(precioStr);
            capacidad = Integer.parseInt(capacidadStr);
            idEntrenador = Integer.parseInt(idEntrenadorStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Precio, Capacidad e ID Entrenador deben ser números.");
            return false;
        }

        LocalDateTime horario = Validaciones.parsearFechaHora(fechaHoraStr);
        
        if (horario == null) {
            JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto. Use: dd/MM/yyyy HH:mm (Ej: 07/12/2025 14:30)");
            return false;
        }
        
        if (horario.isBefore(LocalDateTime.now())) {
            JOptionPane.showMessageDialog(null, "No puedes registrar una clase en el pasado.");
            return false;
        }

        Clases nuevaClase = new Clases(0, tipo, descripcion, precio, ubicacion, horario, capacidad, 0, idEntrenador);

        if (clasesDAO.registrarClase(nuevaClase)) {
            JOptionPane.showMessageDialog(null, "Clase registrada con éxito.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar (Verifique ID Entrenador).");
            return false;
        }
    }

    public boolean actualizarClase(String idClaseStr, TipoClase tipo, String descripcion, String precioStr, 
                                   String ubicacion, String fechaHoraStr, String capacidadStr, 
                                   int personasInscritasActuales, String idEntrenadorStr) {
        
        int idClase;
        try {
            idClase = Integer.parseInt(idClaseStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID Clase inválido.");
            return false;
        }

        if (descripcion.trim().isEmpty() || ubicacion.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Faltan datos obligatorios.");
            return false;
        }

        double precio;
        int capacidad;
        int idEntrenador;
        try {
            precio = Double.parseDouble(precioStr);
            capacidad = Integer.parseInt(capacidadStr);
            idEntrenador = Integer.parseInt(idEntrenadorStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Verifique los valores numéricos.");
            return false;
        }

        if (capacidad < personasInscritasActuales) {
            JOptionPane.showMessageDialog(null, "La capacidad no puede ser menor a los inscritos actuales.");
            return false;
        }

        LocalDateTime horario = Validaciones.parsearFechaHora(fechaHoraStr);
        if (horario == null) {
            JOptionPane.showMessageDialog(null, "Fecha incorrecta. Use formato dd/MM/yyyy HH:mm");
            return false;
        }

        Clases claseEditada = new Clases(idClase, tipo, descripcion, precio, ubicacion, horario, capacidad, personasInscritasActuales, idEntrenador);

        if (clasesDAO.actualizarClase(claseEditada)) {
            JOptionPane.showMessageDialog(null, "Clase actualizada.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar.");
            return false;
        }
    }
    
    public List<Clases> listarClases() {
        return clasesDAO.listarTodos();
    }

    public List<Clases> filtrarPorTipo(TipoClase tipo) {
        return clasesDAO.verClasesPorTipo(tipo);
    }
    
    public boolean eliminarClase(String idClaseStr) {
        try {
            int id = Integer.parseInt(idClaseStr);
            if(JOptionPane.showConfirmDialog(null, "¿Seguro?", "Borrar", JOptionPane.YES_NO_OPTION) == 0){
                 if (clasesDAO.eliminarClase(id)) {
                    JOptionPane.showMessageDialog(null, "Eliminado.");
                    return true;
                }
            }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "ID inválido.");
        }
        return false;
    }
    
    public Clases buscarClase(String idStr){
        try{
            return clasesDAO.buscarPorId(Integer.parseInt(idStr));
        }catch(Exception e){
            return null;
        }
    }  
}
