/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author huete
 */
public class ClienteDTO {
    private final String cedula;
    private final String nombre;
    private final String primerApellido;
    private final String segundoApellido;
    private final String fechaNacimiento;
    private final String telefono;
    private final String correo;
    private final int tipoMembresia;

    //CONSTRUCTOR
    public ClienteDTO(String cedula, String nombre, String primerApellido, String segundoApellido, String fechaNacimiento, String telefono, String correo, int tipoMembresia) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correo = correo;
        this.tipoMembresia = tipoMembresia;
    }

    //METODOS
    public String getCedula() { return cedula; }
    public String getNombre() { return nombre; }
    public String getPrimerApellido() { return primerApellido; }
    public String getSegundoApellido() { return segundoApellido; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public int getTipoMembresia() { return tipoMembresia; }
}