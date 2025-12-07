/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Date;

/**
 *
 * @author ASUS
 */
public class Pago {
    private int idPago;
    private String cedulaCliente;
    private Date fechaPago;
    private double subtotal;
    private double impuesto;
    private double total;

    /**
     * contructor normalito
     * @param idPago
     * @param idCliente
     * @param fecha
     * @param subtotal
     * @param impuesto
     * @param total 
     */
    public Pago(int idPago, String idCliente, Date fecha, double subtotal, double impuesto, double total) {
        this.idPago = idPago;
        this.cedulaCliente = idCliente;
        this.fechaPago = fecha;
        this.subtotal = subtotal;
        this.impuesto = impuesto;
        this.total = total;
    }
    
    /**
     * Constructor para la base de datos :p
     * @param idPago
     * @param cedulaCliente
     * @param fechaPago 
     */
    public Pago(int idPago, String cedulaCliente, Date fechaPago) {
        this.idPago = idPago;
        this.cedulaCliente = cedulaCliente;
        this.fechaPago = fechaPago;
    }

    public Pago() {

    }
    
    //GET Y SETS ---------------------------------------------------------------------------
    public int getIdPago() { return idPago;}
    public void setIdPago(int idPago) {this.idPago = idPago;}

    public String getCedulaCliente() {return cedulaCliente;}
    public void setCedulaCliente(String idCliente) {this.cedulaCliente = idCliente;}

    public Date getFechaPago() {return fechaPago;}
    public void setFechaPago(Date fecha) {this.fechaPago = fecha;}

    public double getSubtotal() {return subtotal;}
    public void setSubtotal(double subtotal) {this.subtotal = subtotal;}

    public double getImpuesto() {return impuesto;}
    public void setImpuesto(double impuesto) {this.impuesto = impuesto;}

    public double getTotal() {return total;}
    public void setTotal(double total) {this.total = total;}

}
