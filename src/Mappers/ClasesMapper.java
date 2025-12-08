/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.ClasesDTO;
import Modelo.Clases;
import Modelo.TipoClase;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author huete
 */
 
public class ClasesMapper {

    public static ClasesDTO toDTO(Clases clases) {
        if (clases == null) return null;

        ClasesDTO dto = new ClasesDTO(
            clases.getIdClase(),
            clases.getTipoClase().getNombreBD(),  
            clases.getDescripcion(),
            clases.getPrecio(),
            clases.getUbicacion(),
            clases.getHorario().toString(),  
            clases.getCapacidadMax(), 
            clases.getPersonasInscritas(),  
            clases.getIdEntrenador()
        );
        return dto;
    }

    public static Clases toEntity(ClasesDTO dto) {
        if (dto == null) return null;

        TipoClase tipoClase = TipoClase.fromString(dto.getTipoClase());  

       
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime horario = LocalDateTime.parse(dto.getHorario(), formatter);  //nnkjnkn
        return new Clases(
            dto.getIdClase(),
            tipoClase,  
            dto.getDescripcion(),
            dto.getPrecio(),
            dto.getUbicacion(),
            horario,  
            dto.getCapacidadMax(),  
            dto.getPersonasInscritas(),  
            dto.getIdEntrenador()  
        );
    }

    public static List<ClasesDTO> toDTOList(List<Clases> clases) {
        if (clases == null) return null;

        List<ClasesDTO> dtos = new ArrayList<>();
        for (Clases clase : clases) {
            dtos.add(toDTO(clase));
        }
        return dtos;
    }

    public static List<Clases> toEntityList(List<ClasesDTO> dtos) {
        if (dtos == null) return null;

        List<Clases> clases = new ArrayList<>();
        for (ClasesDTO dto : dtos) {
            clases.add(toEntity(dto));
        }
        return clases;
    }
}