/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;
import java.time.LocalDate;

/**
 *
 * @author huete
 */
public class EntrenadorDTO {
    private final int idEntrenador;         
    private final String nombre;              
    private final String primerApellido;      
    private final String segundoApellido;    
    private final LocalDate fechaNacimiento;  
    private final String telefono;            
    private final String correo;              
    private final String especialidad;    
    
//CONSTRUCTOR
    public EntrenadorDTO(int idEntrenador, String nombre, String primerApellido, String segundoApellido, 
                         LocalDate fechaNacimiento, String telefono, String correo, String especialidad) {
        this.idEntrenador = idEntrenador;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correo = correo;
        this.especialidad = especialidad;
    }

//METODOS
    public int getIdEntrenador() { return idEntrenador; }
    public String getNombre() { return nombre; }
    public String getPrimerApellido() { return primerApellido; }
    public String getSegundoApellido() { return segundoApellido; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public String getEspecialidad() { return especialidad; }
}