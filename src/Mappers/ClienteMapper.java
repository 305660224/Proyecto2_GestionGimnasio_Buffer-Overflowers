/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;
import DTOs.ClienteDTO;
import Modelo.Cliente;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author huete
 */
public class ClienteMapper {

    public static ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) return null;

        ClienteDTO dto = new ClienteDTO(
            cliente.getCedula(),
            cliente.getNombre(),
            cliente.getPrimerApellido(),
            cliente.getSegundoApellido(),
            cliente.getFechaNacimiento().toString(), 
            cliente.getTelefono(),
            cliente.getCorreo(),
            cliente.getTipoMembresia()
        );
        return dto;
    }

    public static Cliente toEntity(ClienteDTO dto) {
        if (dto == null) return null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNacimiento = LocalDate.parse(dto.getFechaNacimiento(), formatter);

        return new Cliente(
            dto.getCedula(),
            dto.getNombre(),
            dto.getPrimerApellido(),
            dto.getSegundoApellido(),
            fechaNacimiento,  
            dto.getTelefono(),
            dto.getCorreo(),
            dto.getTipoMembresia()
        );
    }

    public static List<ClienteDTO> toDTOList(List<Cliente> clientes) {
        if (clientes == null) return null;

        List<ClienteDTO> dtos = new ArrayList<>();
        for (Cliente cliente : clientes) {
            dtos.add(toDTO(cliente));
        }
        return dtos;
    }

    public static List<Cliente> toEntityList(List<ClienteDTO> dtos) {
        if (dtos == null) return null;

        List<Cliente> clientes = new ArrayList<>();
        for (ClienteDTO dto : dtos) {
            clientes.add(toEntity(dto));
        }
        return clientes;
    }
}