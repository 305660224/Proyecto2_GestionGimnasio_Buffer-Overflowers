/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;
import DTOs.UsuarioDTO;
import Modelo.Usuario;
import Modelo.EnumRol;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author huete
 */
public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;
        String contrasenaStr = new String(usuario.getContrasena());
        return new UsuarioDTO(
            usuario.getIdusuario(),
            usuario.getUsuario(),
            contrasenaStr,
            usuario.getIdRol().name()
        );
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) return null;
        byte[] contrasenaBytes = dto.getContrasena().getBytes();
        EnumRol idRol = EnumRol.valueOf(dto.getIdRol());
        return new Usuario(
            dto.getIdusuario(),
            dto.getUsuario(),
            contrasenaBytes,
            idRol
        );
    }

    public static List<UsuarioDTO> toDTOList(List<Usuario> usuarios) {
        if (usuarios == null) return null;
        List<UsuarioDTO> dtos = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            dtos.add(toDTO(usuario));
        }
        return dtos;
    }

    public static List<Usuario> toEntityList(List<UsuarioDTO> dtos) {
        if (dtos == null) return null;
        List<Usuario> usuarios = new ArrayList<>();
        for (UsuarioDTO dto : dtos) {
            usuarios.add(toEntity(dto));
        }
        return usuarios;
    }
}