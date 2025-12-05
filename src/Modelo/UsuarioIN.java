/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Modelo;

import java.util.List;

/**
 *
 * @author Jeshuan
 */
public interface UsuarioIN {
  Usuario buscarPorUsername(String usuario);
  boolean registrar(Usuario usuario);
  List<Usuario> listarTodos();
  Usuario buscarPorId(int id);
  boolean actualizar(Usuario usuario);
  boolean eliminar(int id);
}
