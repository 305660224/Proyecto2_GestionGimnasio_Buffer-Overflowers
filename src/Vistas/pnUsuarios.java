/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vistas;

import Controlador.ControladorUsuario;
import Modelo.Usuario;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author galla
 */
public class pnUsuarios extends javax.swing.JPanel {
    private ControladorUsuario controladorUsuario;
    private DefaultTableModel modelo;
    /**
     * Creates new form pnUsuarios
     */
    public pnUsuarios() {
        initComponents();
        inicializarComponentes();
    }
  
    private void inicializarComponentes() {
        controladorUsuario = new ControladorUsuario();
        configurarTabla();
        cargarDatosTabla();
    }
    
        private void configurarTabla() {
        modelo = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Usuario", "Rol"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblUsuarios.setModel(modelo);
    }
   
     private void cargarDatosTabla() {
        modelo.setRowCount(0);
        
        List<Usuario> usuarios = controladorUsuario.obtenerTodosLosUsuarios();
        
        for (Usuario usuario : usuarios) {
            modelo.addRow(new Object[]{
                usuario.getIdusuario(),
                usuario.getUsuario(),
                usuario.getIdRol() != null ? usuario.getIdRol().name() : "Sin rol"
            });
        }
    }
     
     private void limpiarCampos() {
        txtUsuario.setText("");
        txtContrasena.setText("");
        cbRol.setSelectedIndex(0);
    }
     
    private boolean validarCampos() {
        if (txtUsuario.getText().trim().isEmpty() || 
            txtContrasena.getPassword().length == 0) {
            
            JOptionPane.showMessageDialog(this, 
                "Complete todos los campos obligatorios", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    } 
    
    private void metodoGuardar() {
    if (!validarCampos()) {
        return;
    }
    
    try {
        String nombreUsuario = txtUsuario.getText().trim();
        String passwordPlana = new String(txtContrasena.getPassword());
 
        String rolSeleccionado = (String) cbRol.getSelectedItem();
        int idRol = rolSeleccionado.equals("Administrador") ? 1 : 2; 

        boolean resultado = controladorUsuario.agregarUsuario(
            nombreUsuario, passwordPlana, idRol);
        
        if (resultado) {
            JOptionPane.showMessageDialog(this, 
                "Usuario registrado correctamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarDatosTabla();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error al registrar el usuario. El nombre de usuario ya existe.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error al registrar el usuario: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}
    
    private void metodoActualizar() {
    String idStr = JOptionPane.showInputDialog(this, 
        "Ingrese el ID del usuario a actualizar:", 
        "Actualizar Usuario", 
        JOptionPane.QUESTION_MESSAGE);
    
    if (idStr == null || idStr.trim().isEmpty()) {
        return;
    }
    
    if (!validarCampos()) {
        return;
    }
    
    try {
        int id = Integer.parseInt(idStr);
        String nombreUsuario = txtUsuario.getText().trim();
        String passwordPlana = new String(txtContrasena.getPassword());
        

        String rolSeleccionado = (String) cbRol.getSelectedItem();
        int idRol = rolSeleccionado.equals("Administrador") ? 1 : 2;

        boolean resultado = controladorUsuario.actualizarUsuario(
            id, nombreUsuario, passwordPlana, idRol);
        
        if (resultado) {
            JOptionPane.showMessageDialog(this, 
                "Usuario actualizado correctamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarDatosTabla();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error al actualizar el usuario", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, 
            "ID inválido. Debe ser un número.", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error al actualizar el usuario: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}
    
    private void metodoBuscar() {
        String idStr = JOptionPane.showInputDialog(this, 
            "Ingrese el ID del usuario a buscar:", 
            "Buscar Usuario", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }
        
        try {
            int id = Integer.parseInt(idStr);
            Usuario usuario = controladorUsuario.buscarPorUsername(txtUsuario.getText().trim());
            
            // Aquí deberías tener un método en el controlador para buscar por ID
            // Por ahora uso buscarPorUsername como alternativa
            if (usuario != null && usuario.getIdusuario() == id) {
                txtUsuario.setText(usuario.getUsuario());
                // No podemos mostrar la contraseña encriptada
                // txtContrasena.setText(""); // No mostrar contraseña por seguridad
                if (usuario.getIdRol() != null) {
                    cbRol.setSelectedItem(usuario.getIdRol().name().equals("ADMINISTRADOR") ? 
                                          "Administrador" : "Entrenador");
                }
            } else {
                // Buscar en la lista de usuarios
                List<Usuario> usuarios = controladorUsuario.obtenerTodosLosUsuarios();
                Usuario usuarioEncontrado = null;
                for (Usuario u : usuarios) {
                    if (u.getIdusuario() == id) {
                        usuarioEncontrado = u;
                        break;
                    }
                }
                
                if (usuarioEncontrado != null) {
                    txtUsuario.setText(usuarioEncontrado.getUsuario());
                    if (usuarioEncontrado.getIdRol() != null) {
                        cbRol.setSelectedItem(usuarioEncontrado.getIdRol().name().equals("ADMINISTRADOR") ? 
                                              "Administrador" : "Entrenador");
                    }
                    JOptionPane.showMessageDialog(this, 
                        "Usuario encontrado: " + usuarioEncontrado.getUsuario(), 
                        "Búsqueda", 
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "No se encontró el usuario con ID: " + idStr, 
                        "Búsqueda", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "ID inválido. Debe ser un número.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al buscar el usuario: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void metodoEliminar() {
        String idStr = JOptionPane.showInputDialog(this, 
            "Ingrese el ID del usuario a eliminar:", 
            "Eliminar Usuario", 
            JOptionPane.WARNING_MESSAGE);
        
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }
        
        try {
            int id = Integer.parseInt(idStr);
            
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de eliminar al usuario con ID: " + id + "?", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean resultado = controladorUsuario.eliminarUsuario(id);
                
                if (resultado) {
                    JOptionPane.showMessageDialog(this, 
                        "Usuario eliminado correctamente", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarDatosTabla();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Error al eliminar el usuario. No se puede eliminar a sí mismo.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "ID inválido. Debe ser un número.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al eliminar el usuario: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        txtContrasena = new javax.swing.JPasswordField();
        txtUsuario = new javax.swing.JTextField();
        cbRol = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 0));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel1.setBackground(new java.awt.Color(255, 204, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnGuardar.setBackground(new java.awt.Color(0, 0, 0));
        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnActualizar.setBackground(new java.awt.Color(0, 0, 0));
        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setText("Actualizar");
        btnActualizar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(0, 0, 0));
        btnBuscar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(0, 0, 0));
        btnEliminar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        tblUsuarios.setBackground(new java.awt.Color(255, 204, 0));
        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblUsuarios);

        txtContrasena.setBackground(new java.awt.Color(255, 204, 0));
        txtContrasena.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        txtUsuario.setBackground(new java.awt.Color(255, 204, 0));
        txtUsuario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        cbRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Entrenador", "Administrador" }));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Nombre de Usuario ");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Contrasena");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel1)
                        .addGap(207, 207, 207)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)
                        .addComponent(txtContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(cbRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75)
                .addComponent(cbRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        metodoActualizar();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
         metodoGuardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        metodoEliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        metodoBuscar();
    }//GEN-LAST:event_btnBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cbRol;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
