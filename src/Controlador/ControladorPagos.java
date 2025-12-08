/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Pago;
import DAOs.PagoDAO;
import DTOs.PagoDTO;
import Mappers.PagoMapper;
import Modelo.Cliente;
import Utilidades.ComprobanteFactory;
import Utilidades.GeneradorComprobante;
import Utilidades.GeneradorPDF;
import Utilidades.GeneradorXML;
import java.awt.Desktop;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class ControladorPagos {
    private PagoDAO pagoDAO;

    public ControladorPagos() {
        this.pagoDAO = new PagoDAO();
    }

/**
 * Registra el pago de un cliente YA EXISTENTE en la DB, 
 * @param cedulaCliente
 * @param fecha
 * @param totalAPagar
 * @param generarPDF
 * @param generarXML
 * @return booleano Si el pago fue registrado o no
 */
      public boolean registrarPago(String cedulaCliente, Date fecha, double subtotal, double impuesto, boolean generarPDF, boolean generarXML) {
        
        // Calcular subtotal e impuesto
            double imp = impuesto/100;
            double total = subtotal+(subtotal*imp);
            
        // Crear objeto Pago completo
        Pago pago = new Pago(0, cedulaCliente, fecha, subtotal, impuesto, total);
        
        // Registrar en BD
        boolean registrado = pagoDAO.registrarPago(pago);
        
        if (registrado) {
            System.out.println("Pago registrado");
            
            // Obtener el ID generado en BD
            int idPagoGenerado = pagoDAO.obtenerUltimoIdInsertado();
            pago.setIdPago(idPagoGenerado);
            
            String nombreCliente = obtenerNombreCliente(cedulaCliente);
            
            // Generar comprobantes
            generarComprobantes(pago, nombreCliente, generarPDF, generarXML);
            
            return true;
        }
        
        System.err.println("No se registro pago en BD.");
        return false;
    }

    /**
     * Obtiene todos los pagos como DTOs
     */
    public List<PagoDTO> obtenerTodosLosPagos() {
        List<Pago> pagosBD = pagoDAO.listarTodos();
        List<PagoDTO> pagosDTO = PagoMapper.toDTOList(pagosBD);
        
        // Enriquecer con nombres de clientes
        for (PagoDTO dto : pagosDTO) {
            dto.setNombreCliente(obtenerNombreCliente(dto.getCedulaCliente()));
        }
        
        return pagosDTO;
    }

    /**
     * Busca un pago por ID
     */
    public PagoDTO obtenerPagoPorId(int idPago) {
        Pago pago = pagoDAO.buscarPorId(idPago);
        if (pago == null) return null;
        
        PagoDTO dto = PagoMapper.toDTO(pago);
        dto.setNombreCliente(obtenerNombreCliente(dto.getCedulaCliente()));
        return dto;
    }

/**
 * Elimina un pago existente, el id es DEL PAGO no del cliente
 * @param idPago
 * @return 
 */
public boolean eliminarPago(int idPago) {
    try {
        //pago existe
        Pago pago = pagoDAO.buscarPorId(idPago);
        if (pago == null) {
            return false;
        }
        
        //eliminarDAO
        boolean eliminado = pagoDAO.eliminarPago(idPago);
        
        if (!eliminado) {
        }
        
        return eliminado;
        
    } catch (Exception e) {
        return false;
    }
}
    
//METODOS PRIVADOS --------------------------------------------------------------

    private void generarComprobantes(Pago pago, String nombreCliente, boolean generarPDF, boolean generarXML) {
    if (pago == null) return;
    
    Date fecha = (pago.getFechaPago() != null) ? pago.getFechaPago() : new Date();
    String basePath = "facturas/factura_" + pago.getIdPago();
    if (generarPDF) {
        try {
            GeneradorComprobante generador = ComprobanteFactory.crearGenerador("PDF");
            generador.generarComprobante(
                basePath + ".pdf", 
                pago.getIdPago(), 
                nombreCliente,
                fecha,
                pago.getSubtotal(),
                pago.getImpuesto(),
                pago.getTotal()
            );
            
            abrirComprobanteEnNavegador(basePath + ".html");
        } catch (Exception e) {
        }
    }

    if (generarXML) {
        try {
            GeneradorComprobante generador = ComprobanteFactory.crearGenerador("XML");
            generador.generarComprobante(
                basePath + ".xml", 
                pago.getIdPago(), 
                nombreCliente, 
                fecha,
                pago.getSubtotal(),
                pago.getImpuesto(),
                pago.getTotal()
            );
            
        } catch (Exception e) {
            System.err.println("Error generando XML: " + e.getMessage());
        }
    }
}
    
    private void abrirComprobanteEnNavegador(String rutaArchivo) {
        System.out.println(rutaArchivo);
        try {
            File archivo = new File(rutaArchivo);
            
            if (Desktop.isDesktopSupported() && archivo.exists()) {
                Desktop.getDesktop().open(archivo);
            }
        } catch (Exception e) {
            System.err.println("No funko el html :( : " + e.getMessage());
        }
    }

private String obtenerNombreCliente(String cedula) {
    ControladorCliente controladorCliente = new ControladorCliente();
    Cliente cliente = controladorCliente.buscarCliente(cedula);
    
    if (cliente != null) {
        String nombreCompleto = "";
        
        if (cliente.getNombre() != null && !cliente.getNombre().trim().isEmpty()) {
            nombreCompleto = cliente.getNombre().trim();
        }       
        if (cliente.getPrimerApellido() != null && !cliente.getPrimerApellido().trim().isEmpty()) {
            if (!nombreCompleto.isEmpty()) {
                nombreCompleto = nombreCompleto + " ";
            }
            nombreCompleto = nombreCompleto + cliente.getPrimerApellido().trim();
        }        
        if (cliente.getSegundoApellido() != null && !cliente.getSegundoApellido().trim().isEmpty()) {
            if (!nombreCompleto.isEmpty()) {
                nombreCompleto = nombreCompleto + " ";
            }
            nombreCompleto = nombreCompleto + cliente.getSegundoApellido().trim();
        }
        return !nombreCompleto.isEmpty() ? nombreCompleto : cedula;
    }   
    return cedula;
}
}
