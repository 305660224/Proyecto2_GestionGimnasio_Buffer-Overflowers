/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Utilidades;

import java.util.Date;

/**
 *
 * @author ASUS
 */
public interface GeneradorComprobante {
        void generarComprobante(String ruta, int idPago, String cliente, Date fecha, 
                           double subtotal, double impuesto, double total);
}
