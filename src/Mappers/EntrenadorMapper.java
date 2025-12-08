/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;
import DTOs.EntrenadorDTO;
import Modelo.Entrenador;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author huete
 */
public class EntrenadorMapper {

    public static EntrenadorDTO toDTO(Entrenador entrenador) {
        if (entrenador == null) return null;

        EntrenadorDTO dto = new EntrenadorDTO(
            entrenador.getIdEntrenador(),
            entrenador.getNombre(),
            entrenador.getPrimerApellido(),
            entrenador.getSegundoApellido(),
            null,
            entrenador.getTelefono(),
            entrenador.getCorreo(),
            entrenador.getEspecialidades()
        );
        return dto;
    }

    public static Entrenador toEntity(EntrenadorDTO dto) {
        if (dto == null) return null;

        return new Entrenador(
            dto.getIdEntrenador(),
            dto.getNombre(),
            dto.getPrimerApellido(),
            dto.getSegundoApellido(),
            dto.getTelefono(),
            dto.getCorreo(),
            dto.getEspecialidad()
        );
    }

    public static List<EntrenadorDTO> toDTOList(List<Entrenador> entrenadores) {
        if (entrenadores == null) return null;

        List<EntrenadorDTO> dtos = new ArrayList<>();
        for (Entrenador entrenador : entrenadores) {
            dtos.add(toDTO(entrenador));
        }
        return dtos;
    }

    public static List<Entrenador> toEntityList(List<EntrenadorDTO> dtos) {
        if (dtos == null) return null;

        List<Entrenador> entrenadores = new ArrayList<>();
        for (EntrenadorDTO dto : dtos) {
            entrenadores.add(toEntity(dto));
        }
        return entrenadores;
    }
}
