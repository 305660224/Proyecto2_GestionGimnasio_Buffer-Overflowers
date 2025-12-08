/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

/**
 *
 * @author ASUS
 */
public class ComprobanteFactory {
     public static GeneradorComprobante crearGenerador(String tipo) {
        if (tipo == null) {
            return null;
        }
        
        switch (tipo.toUpperCase()) {
            case "PDF":
                return new GeneradorPDF();
            case "XML":
                return new GeneradorXML();
            default:
                throw new IllegalArgumentException("Comprobante desonocido :p : " + tipo);
        }
    }
     
     public static GeneradorComprobante crearGeneradorPorDescripcion(String descripcion) {
        if (descripcion == null) {
            return null;
        }
        
        if (descripcion.toLowerCase().contains("pdf") || descripcion.toLowerCase().contains("html")) {
            return new GeneradorPDF();
        } else if (descripcion.toLowerCase().contains("xml") || descripcion.toLowerCase().contains("factura")) {
            return new GeneradorXML();
        } else {
            throw new IllegalArgumentException("Tipo no reconocido: " + descripcion);
        }
    }
}
