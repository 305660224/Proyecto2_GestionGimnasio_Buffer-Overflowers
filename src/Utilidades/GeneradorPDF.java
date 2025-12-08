/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ASUS
 */
public class GeneradorPDF {
    public static void generarComprobantePago(String ruta, int idPago, String cliente, Date fecha, double subtotal, double impuesto, double total) {
        
        try {
            String rutaHTML = ruta.replace(".pdf", ".html");
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            
            String html = String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Factura #%d - Gimnasio UTN</title>
                    <style>
                        body { font-family: Arial, sans-serif; margin: 40px; }
                        .factura { border: 2px solid #333; padding: 30px; max-width: 600px; margin: 0 auto; }
                        .titulo { text-align: center; font-size: 24px; font-weight: bold; margin-bottom: 30px; }
                        .info { margin-bottom: 20px; }
                        table { width: 100%%; border-collapse: collapse; margin: 25px 0; }
                        th { background: #f2f2f2; text-align: left; padding: 12px; border-bottom: 2px solid #333; }
                        td { padding: 10px 12px; border-bottom: 1px solid #ddd; }
                        .total-row { font-weight: bold; font-size: 16px; background: #e8f4ff; }
                        .footer { margin-top: 40px; text-align: center; font-style: italic; color: #666; }
                    </style>
                </head>
                <body>
                    <div class="factura">
                        <div class="titulo">GIMNASIO SMARTFIT - COMPROBANTE DE PAGO</div>
                        
                        <div class="info">
                            <div><strong>Número de Factura:</strong> %d</div>
                            <div><strong>Fecha:</strong> %s</div>
                            <div><strong>Cliente:</strong> %s</div>
                        </div>
                        
                        <table>
                            <tr>
                                <th>Descripción</th>
                                <th>Monto</th>
                            </tr>
                            <tr>
                                <td>Subtotal</td>
                                <td>%,.2f₡</td>
                            </tr>
                            <tr>
                                <td>Impuesto (%)</td>
                                <td>%,.2f₡</td>
                            </tr>
                            <tr class="total-row">
                                <td>TOTAL</td>
                                <td>%,.2f₡</td>
                            </tr>
                        </table>
                        
                        <div class="footer">
                            <p>Gracias por su preferencia</p>
                            <p>Factura generada automáticamente</p>
                        </div>
                    </div>
                </body>
                </html>
                """, idPago, idPago, sdf.format(fecha), cliente, subtotal, impuesto, total);
            
            try (FileWriter writer = new FileWriter(rutaHTML)) {
                writer.write(html);
            }
            
        } catch (IOException e) {
            System.err.println("Error generando factura: " + e.getMessage());
        }
    }
}
