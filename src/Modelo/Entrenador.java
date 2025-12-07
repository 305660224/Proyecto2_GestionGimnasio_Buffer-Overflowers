/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Jeshuan
 */
public class Entrenador {
   private int idEntrenador;     
   private String nombre;
   private String primerApellido;
   private String segundoApellido;
   private String telefono;
   private String correo;
   private String especialidades;

    public Entrenador() {
    }

    // Constructor sin ID (para crear nuevos, porque en la BD pone el ID)
    public Entrenador(String nombre, String primerApellido, String segundoApellido, 
                      String telefono, String correo, String especialidades) {
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.telefono = telefono;
        this.correo = correo;
        this.especialidades = especialidades;
    }

    // Constructor completo (para leer de la BD)
    public Entrenador(int idEntrenador, String nombre, String primerApellido, String segundoApellido, 
                      String telefono, String correo, String especialidades) {
        this(nombre, primerApellido, segundoApellido, telefono, correo, especialidades);
        this.idEntrenador = idEntrenador;
    }

    // Getters y Setters
    public int getIdEntrenador() { return idEntrenador; }
    public void setIdEntrenador(int idEntrenador) { this.idEntrenador = idEntrenador; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }

    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getEspecialidades() { return especialidades; }
    public void setEspecialidades(String especialidades) { this.especialidades = especialidades; }
}
