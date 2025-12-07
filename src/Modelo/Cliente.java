/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;

/**
 *
 * @author Jeshuan
 */
public class Cliente {
   private String cedula;        
   private String nombre;
   private String primerApellido;
   private String segundoApellido;
   private LocalDate fechaNacimiento; 
   private String telefono;
   private String correo;
   private int tipoMembresia;     

    public Cliente() {
    }

    public Cliente(String cedula, String nombre, String primerApellido, String segundoApellido, 
                   LocalDate fechaNacimiento, String telefono, String correo, int tipoMembresia) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correo = correo;
        this.tipoMembresia = tipoMembresia;
    }

    // Getters y Setters
    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }

    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public int getTipoMembresia() { return tipoMembresia; }
    public void setTipoMembresia(int tipoMembresia) { this.tipoMembresia = tipoMembresia; }

}

