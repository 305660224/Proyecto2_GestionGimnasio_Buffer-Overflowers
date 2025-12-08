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
    private int idClase;
    private String tipoClase;
    private String descripcion;
    private double precio;
    private String ubicacion;
    private String horario;
    private int idEntrenador;
    private int personasInscritas;
    private int capacidadMax;
    
//CONSTRUCTOR
    public ClasesDTO(int idClase, String tipoClase, String descripcion, double precio, String ubicacion, String horario, int idEntrenador,int personasInscritas,  int capacidadMax) {
        this.idClase = idClase;
        this.tipoClase = tipoClase;
        this.descripcion = descripcion;
        this.precio = precio;
        this.ubicacion = ubicacion;
        this.horario = horario;
        this.idEntrenador = idEntrenador;
        this.capacidadMax = capacidadMax;
        this.personasInscritas = personasInscritas;
    }

    //METODOS
    public int getIdClase() { return idClase; }
    public String getTipoClase() { return tipoClase; }
    public String getDescripcion() { return descripcion; }
    public double getPrecio() { return precio; }
    public String getUbicacion() { return ubicacion; }
    public String getHorario() { return horario; }
    public int getIdEntrenador() { return idEntrenador; }
    public int getCapacidadMax() { return capacidadMax; }
    public int getPersonasInscritas() { return personasInscritas; }
}