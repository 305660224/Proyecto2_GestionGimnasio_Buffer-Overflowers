/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author huete
 */

public class ClienteClaseDTO {
    private final String cedulaCliente;
    private final int idClase;

    public ClienteClaseDTO(String cedulaCliente, int idClase) {
        this.cedulaCliente = cedulaCliente;
        this.idClase = idClase;
    }

    public String getCedulaCliente() { 
        return cedulaCliente; 
    }

    public int getIdClase() { 
        return idClase; 
    }
}