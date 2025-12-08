/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;
import DTOs.MembresiaDTO;
import Modelo.Membresia;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author huete
 */
public class MembresiaMapper {

    public static MembresiaDTO toDTO(Membresia membresia) {
        if (membresia == null) return null;

        MembresiaDTO dto = new MembresiaDTO(
            membresia.getIdMembresia(),
            membresia.getNombre(),
            membresia.getPrecio(),
            membresia.getDuracionDias(),
            membresia.getBeneficios()
        );
        return dto;
    }

    public static Membresia toEntity(MembresiaDTO dto) {
        if (dto == null) return null;

        return new Membresia(
            dto.getIdMembresia(),
            dto.getNombre(),
            dto.getPrecio(),
            dto.getDuracionDias(),
            dto.getBeneficios()
        );
    }

    public static List<MembresiaDTO> toDTOList(List<Membresia> membresias) {
        if (membresias == null) return null;

        List<MembresiaDTO> dtos = new ArrayList<>();
        for (Membresia membresia : membresias) {
            dtos.add(toDTO(membresia));
        }
        return dtos;
    }

    public static List<Membresia> toEntityList(List<MembresiaDTO> dtos) {
        if (dtos == null) return null;

        List<Membresia> membresias = new ArrayList<>();
        for (MembresiaDTO dto : dtos) {
            membresias.add(toEntity(dto));
        }
        return membresias;
    }
}
