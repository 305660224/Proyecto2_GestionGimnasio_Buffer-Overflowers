/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import DAOs.ClienteDAO;
import Modelo.Cliente;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import Utilidades.Validaciones;
/**
 *
 * @author Jeshuan
 */
public class ControladorCliente {
   private final ClienteDAO clienteDAO;
   
   public ControladorCliente(){
       this.clienteDAO = new ClienteDAO();
               
   }
 
    public boolean registrarCliente(String cedula, String nombre, String primerApellido, 
                                    String segundoApellido, String fechaNacimientoTexto, 
                                    String telefono, String correo, int tipoMembresia) {
        
        // Validaciones
        if (!Validaciones.esCedulaValida(cedula)) {
            JOptionPane.showMessageDialog(null, "Cédula inválida (Debe empezar con 1-8 y tener 9 u 11 dígitos).");
            return false;
        }

        if (!Validaciones.esCorreoValido(correo)) {
            JOptionPane.showMessageDialog(null, "Correo inválido (Debe terminar en .com).");
            return false;
        }
        
        if (!Validaciones.esTelefonoValido(telefono)) {
            JOptionPane.showMessageDialog(null, "Teléfono inválido (Debe empezar con 6, 7 u 8 y tener 8 dígitos).");
            return false;
        }

        LocalDate fechaLocal = Validaciones.parsearFecha(fechaNacimientoTexto);
        if (fechaLocal == null) {
            JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto. Verifque el formato DD/MM/YYYY)");
            return false;
        }

        Cliente cliente = new Cliente(
            cedula, 
            nombre, 
            primerApellido, 
            segundoApellido, 
            fechaLocal, 
            telefono, 
            correo, 
            tipoMembresia
        );
        return clienteDAO.registrarCliente(cliente);
    }
    
 
    public List<Cliente> listarClientes() {
        return clienteDAO.listarTodos();
    }
    
    public boolean eliminarCliente(String cedula) {
        return clienteDAO.eliminarCliente(cedula);
    }
    
    public boolean actualizarCliente(String cedula, String nombre, String primerApellido, 
                                     String segundoApellido, String fechaNacimientoTexto, 
                                     String telefono, String correo, int tipoMembresia) {
        
         if (!Validaciones.esCorreoValido(correo)) {
            JOptionPane.showMessageDialog(null, "Correo inválido.");
            return false;
        }
         
        LocalDate fechaLocal = Validaciones.parsearFecha(fechaNacimientoTexto);
        if (fechaLocal == null) {
            JOptionPane.showMessageDialog(null, "Fecha inválida.");
            return false;
        }

        Cliente cliente = new Cliente(
            cedula, nombre, primerApellido, segundoApellido, 
            fechaLocal, telefono, correo, tipoMembresia
        );

        return clienteDAO.actualizarCliente(cliente);
    }
      public Cliente buscarCliente(String cedula){
       if(cedula == null || cedula.trim().isEmpty()){
       return null;
   }
   return clienteDAO.buscarPorId(cedula.trim());
}
}
