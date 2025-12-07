/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Pago;
import DAOs.PagoDAO;
import DTOs.PagoDTO;
import Mappers.PagoMapper;
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
     * Registra un pago (solo cedulaCliente y fecha en BD)
     * y genera facturas con cálculos
     */
      public boolean registrarPago(String cedulaCliente, Date fecha, 
                               double totalAPagar, boolean generarPDF, boolean generarXML) {
        
        // Calcular subtotal e impuesto (13%)
        double subtotal = totalAPagar / 1.13;
        double impuesto = totalAPagar - subtotal;
        
        // Crear objeto Pago completo
        Pago pago = new Pago(0, cedulaCliente, fecha, subtotal, impuesto, totalAPagar);
        
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
     * Genera comprobantes para un pago existente
     */
    public void generarComprobantesExistente(int idPago, boolean generarPDF, boolean generarXML) {
        Pago pago = pagoDAO.buscarPorId(idPago);
        
        if (pago == null) {
            System.err.println("Pago no encontrado con ID: " + idPago);
            return;
        }
        
        String nombreCliente = obtenerNombreCliente(pago.getCedulaCliente());
        generarComprobantes(pago, nombreCliente, generarPDF, generarXML);
    }

public boolean eliminarPago(int idPago) {
    try {
        // Primero, verifica si el pago existe
        Pago pago = pagoDAO.buscarPorId(idPago);
        if (pago == null) {
            System.err.println("Pago con ID " + idPago + " no encontrado");
            return false;
        }
        
        // Llama al método eliminar del DAO
        boolean eliminado = pagoDAO.eliminarPago(idPago);
        
        if (!eliminado) {
            System.err.println("Error eliminando" + idPago);
        }
        
        return eliminado;
        
    } catch (Exception e) {
        System.err.println("Error en eliminarPago: " + e.getMessage());
        return false;
    }
}
    
    /**
     * Método privado para generar comprobantes
     */
    private void generarComprobantes(Pago pago, String nombreCliente, boolean generarPDF, boolean generarXML) {
        if (pago == null) return;
        
        Date fecha = (pago.getFechaPago() != null) ? pago.getFechaPago() : new Date();
        
        if (generarPDF) {
            String rutaPDF = "facturas/factura_" + pago.getIdPago() + ".pdf";
        GeneradorPDF.generarComprobantePago(
            rutaPDF, 
            pago.getIdPago(), 
            nombreCliente,
            fecha,
            pago.getSubtotal(),
            pago.getImpuesto(),
            pago.getTotal()
        );
            
            // Abrir automáticamente si hay archivo HTML
            abrirComprobanteEnNavegador(rutaPDF.replace(".pdf", ".html"));
        }
        
        if (generarXML) {
        GeneradorXML.generarReportePagoXML(
            "facturas/factura_" + pago.getIdPago() + ".xml", 
            pago.getIdPago(), 
            nombreCliente, 
            fecha,
            pago.getSubtotal(),
            pago.getImpuesto(),
            pago.getTotal()
        );
    }
}
    
    private void abrirComprobanteEnNavegador(String rutaArchivo) {
        try {
            File archivo = new File(rutaArchivo);
            
            if (Desktop.isDesktopSupported() && archivo.exists()) {
                Desktop.getDesktop().open(archivo);
                System.out.println("Comprobante abierto en navegador");
            }
        } catch (Exception e) {
            System.err.println("No se pudo abrir :( : " + e.getMessage());
        }
    }

    /**
     * ELIMINAR XXXXXXXXXXXXXXXX
     */
    private String obtenerNombreCliente(String cedulaCliente) {
        // TODO: Implementar conexión a tabla clientes
        if ("102345678".equals(cedulaCliente)) return "Laura Soto Ramirez";
        if ("71234567890".equals(cedulaCliente)) return "Pedro Lopez Mora";
        return "Cliente con cédula: " + cedulaCliente;
    }
}
