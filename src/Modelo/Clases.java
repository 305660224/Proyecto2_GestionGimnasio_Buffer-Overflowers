/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.time.LocalDateTime;
/**
 *
 * @author Jeshuan
 */
public class Clases {
  private int idClase;
  private TipoClase tipoClase; 
  private String descripcion;
  private double precio;
  private String ubicacion;
  private LocalDateTime horario;
  private int capacidadMax;
  private int personasInscritas;
  private int idEntrenador; 
  
  public Clases(){
      
  }
  public Clases(int idClase, TipoClase tipoClase, String descripcion, double precio, 
                 String ubicacion, LocalDateTime horario, int capacidadMax, 
                 int personasInscritas, int idEntrenador) {
        this.idClase = idClase;
        this.tipoClase = tipoClase; 
        this.descripcion = descripcion;
        this.precio = precio;
        this.ubicacion = ubicacion;
        this.horario = horario;
        this.capacidadMax = capacidadMax;
        this.personasInscritas = personasInscritas;
        this.idEntrenador = idEntrenador;
    }

    public TipoClase getTipoClase() { 
        return tipoClase; 
    }
    
    public void setTipoClase(TipoClase tipoClase) { 
        this.tipoClase = tipoClase; 
    }

    public int getIdClase() { return idClase; }
    public void setIdClase(int idClase) { this.idClase = idClase; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    
    public LocalDateTime getHorario() { return horario; }
    public void setHorario(LocalDateTime horario) { this.horario = horario; }
    
    public int getCapacidadMax() { return capacidadMax; }
    public void setCapacidadMax(int capacidadMax) { this.capacidadMax = capacidadMax; }
    
    public int getPersonasInscritas() { return personasInscritas; }
    public void setPersonasInscritas(int personasInscritas) { this.personasInscritas = personasInscritas; }
    
    public int getIdEntrenador() { return idEntrenador; }
    public void setIdEntrenador(int idEntrenador) { this.idEntrenador = idEntrenador; }
}

