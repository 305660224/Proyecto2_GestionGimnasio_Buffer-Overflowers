/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.util.Date;

/**
 *
 * @author ASUS
 */
public class PagoDTO {
    private int idPago;
    private String cedulaCliente;
    private Date fechaPago;
    private double subtotal;
    private double impuesto;
    private double total;
    private String nombreCliente; // Para vistas
    
    //GETS y SETS
    public int getIdPago() {return idPago; }
    public void setIdPago(int idPago) { this.idPago = idPago;   }
    
    public String getCedulaCliente() { return cedulaCliente; }
    public void setCedulaCliente(String cedulaCliente) { this.cedulaCliente = cedulaCliente; }
    
    public Date getFechaPago() {return fechaPago; }
    public void setFechaPago(Date fechaPago) { this.fechaPago = fechaPago; }
    
    public double getSubtotal() {return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    
    public double getImpuesto() {return impuesto; }
    public void setImpuesto(double impuesto) { this.impuesto = impuesto; }
    
    public double getTotal() { return total;    }
    public void setTotal(double total) { this.total = total;     }
    
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
}
