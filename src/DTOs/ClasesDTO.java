/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author huete
 */

public class ClasesDTO {
    private final String idClase;
    private final String tipoClase;
    private final String descripcion;
    private final String precio;
    private final String ubicacion;
    private final String horario;
    private final String idEntrenador;
    private final int capacidadMax;
    
//CONSTRUCTOR
    public ClasesDTO(String idClase, String tipoClase, String descripcion, String precio, String ubicacion, String horario, String idEntrenador, int capacidadMax) {
        this.idClase = idClase;
        this.tipoClase = tipoClase;
        this.descripcion = descripcion;
        this.precio = precio;
        this.ubicacion = ubicacion;
        this.horario = horario;
        this.idEntrenador = idEntrenador;
        this.capacidadMax = capacidadMax;
    }

    //METODOS
    public String getIdClase() { return idClase; }
    public String getTipoClase() { return tipoClase; }
    public String getDescripcion() { return descripcion; }
    public String getPrecio() { return precio; }
    public String getUbicacion() { return ubicacion; }
    public String getHorario() { return horario; }
    public String getIdEntrenador() { return idEntrenador; }
    public int getCapacidadMax() { return capacidadMax; }
}