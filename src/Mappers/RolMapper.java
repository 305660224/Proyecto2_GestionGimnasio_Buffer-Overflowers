/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;
import DTOs.RolDTO;
import Modelo.Rol;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author huete
 */
public class RolMapper {

    public static RolDTO toDTO(Rol rol) {
        if (rol == null) return null;

        return new RolDTO(
            rol.getIdRol(),
            rol.getTipoRol()
        );
    }

    public static Rol toEntity(RolDTO dto) {
        if (dto == null) return null;

        return new Rol(
            dto.getIdRol(),
            dto.getTipoRol()
        );
    }

    public static List<RolDTO> toDTOList(List<Rol> roles) {
        if (roles == null) return null;

        List<RolDTO> dtos = new ArrayList<>();
        for (Rol rol : roles) {
            dtos.add(toDTO(rol));
        }
        return dtos;
    }

    public static List<Rol> toEntityList(List<RolDTO> dtos) {
        if (dtos == null) return null;

        List<Rol> roles = new ArrayList<>();
        for (RolDTO dto : dtos) {
            roles.add(toEntity(dto));
        }
        return roles;
    }
}