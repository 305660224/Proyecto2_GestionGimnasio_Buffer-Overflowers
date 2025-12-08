/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author huete
 */
public class ClienteClase {
    private String cedulaCliente;
    private int idClase;

    public ClienteClase() {
    }
    
    //constructor
    public ClienteClase(String cedulaCliente, int idClase) {
        this.cedulaCliente = cedulaCliente;
        this.idClase = idClase;
    }
    
    //Metodos Getter y setter
    public String getCedulaCliente() { 
        return cedulaCliente; 
    }
    public void setCedulaCliente(String cedulaCliente) { 
        this.cedulaCliente = cedulaCliente; 
    }
    public int getIdClase() { 
        return idClase; 
    }
    public void setIdClase(int idClase) { 
        this.idClase = idClase; 
    }
}
