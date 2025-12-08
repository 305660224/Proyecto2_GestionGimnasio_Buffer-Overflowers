/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;
import DTOs.ClienteClaseDTO;
import Modelo.ClienteClase;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author huete
 */
public class ClienteClaseMapper {

    public static ClienteClaseDTO toDTO(ClienteClase clienteClase) {
        if (clienteClase == null) return null;
        return new ClienteClaseDTO(
            clienteClase.getCedulaCliente(),
            clienteClase.getIdClase()
        );
    }

    public static ClienteClase toEntity(ClienteClaseDTO dto) {
        if (dto == null) return null;
        return new ClienteClase(
            dto.getCedulaCliente(),
            dto.getIdClase()
        );
    }

    public static List<ClienteClaseDTO> toDTOList(List<ClienteClase> clienteClases) {
        if (clienteClases == null) return null;
        List<ClienteClaseDTO> dtos = new ArrayList<>();
        for (ClienteClase clienteClase : clienteClases) {
            dtos.add(toDTO(clienteClase));
        }
        return dtos;
    }

    public static List<ClienteClase> toEntityList(List<ClienteClaseDTO> dtos) {
        if (dtos == null) return null;
        List<ClienteClase> clienteClases = new ArrayList<>();
        for (ClienteClaseDTO dto : dtos) {
            clienteClases.add(toEntity(dto));
        }
        return clienteClases;
    }
}