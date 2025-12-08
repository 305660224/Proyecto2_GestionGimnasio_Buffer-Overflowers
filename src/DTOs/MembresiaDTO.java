/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author huete
 */
public class MembresiaDTO {
    private final int idMembresia;
    private final String nombre;
    private final double precio;
    private final int duracionDias;
    private final String beneficios;

    public MembresiaDTO(int idMembresia, String nombre, double precio, int duracionDias, String beneficios) {
        this.idMembresia = idMembresia;
        this.nombre = nombre;
        this.precio = precio;
        this.duracionDias = duracionDias;
        this.beneficios = beneficios;
    }

    public int getIdMembresia() { return idMembresia; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getDuracionDias() { return duracionDias; }
    public String getBeneficios() { return beneficios; }
}