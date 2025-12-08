/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vistas;

import Controlador.ControladorEntrenador;
import Modelo.Entrenador;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author galla
 */
public class pnEntrenadores extends javax.swing.JPanel {

    private ControladorEntrenador controladorEntrenador;
    private DefaultTableModel modeloTabla;
    
    /**
     * Creates new form pnEntrenadores
     */
    public pnEntrenadores() {
        initComponents();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        controladorEntrenador = new ControladorEntrenador();
        configurarTabla();
        cargarDatosTabla();
        configurarComboBox();
    }
    
      private void configurarTabla() {
        // Configurar modelo de tabla
        modeloTabla = new DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "Nombre", "Primer Apellido", "Segundo Apellido", 
                "Teléfono", "Correo", "Especialidad"
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No editable
            }
        };
        jTable1.setModel(modeloTabla);
    }
      
       private void configurarComboBox() {
        // Configurar opciones de especialidad
        txtEspecialidad.removeAllItems();
        txtEspecialidad.addItem("Fitness");
        txtEspecialidad.addItem("Yoga");
        txtEspecialidad.addItem("Pilates");
        txtEspecialidad.addItem("Crossfit");
        txtEspecialidad.addItem("Spinning");
        txtEspecialidad.addItem("Artes Marciales");
        txtEspecialidad.addItem("Nutrición Deportiva");
        txtEspecialidad.addItem("Rehabilitación");
    }
       
    private void cargarDatosTabla() {
        // Limpiar tabla
        modeloTabla.setRowCount(0);
        
        // Obtener datos del controlador
        List<Entrenador> entrenadores = controladorEntrenador.listarEntrenadores();
        
        // Llenar tabla
        for (Entrenador entrenador : entrenadores) {
            modeloTabla.addRow(new Object[]{
                entrenador.getIdEntrenador(),
                entrenador.getNombre(),
                entrenador.getPrimerApellido(),
                entrenador.getSegundoApellido(),
                entrenador.getTelefono(),
                entrenador.getCorreo(),
                entrenador.getEspecialidades()
            });
        }
    }
    
    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido1.setText("");
        txtApellido2.setText("");
        txtContacto.setText("");
        TxtCorreo.setText("");
        txtEspecialidad.setSelectedIndex(0);
    }
    
    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty() || 
            txtApellido1.getText().trim().isEmpty() ||
            txtContacto.getText().trim().isEmpty() ||
            TxtCorreo.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, 
                "Por favor complete todos los campos obligatorios", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }   
    
    private void guardar() {
    if (!validarCampos()) {
            return;
        }
        
        try {
            String nombre = txtNombre.getText().trim();
            String primerApellido = txtApellido1.getText().trim();
            String segundoApellido = txtApellido2.getText().trim();
            String telefono = txtContacto.getText().trim();
            String correo = TxtCorreo.getText().trim();
            String especialidad = txtEspecialidad.getSelectedItem().toString();
            
            boolean resultado = controladorEntrenador.registrarEntrenador(
                nombre, primerApellido, segundoApellido, telefono, correo, especialidad);
            
            if (resultado) {
                limpiarCampos();
                cargarDatosTabla();
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al registrar el entrenador: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }    
    }  
    
    private void eliminar() {
    String idStr = JOptionPane.showInputDialog(this, 
            "Ingrese el ID del entrenador a eliminar:", 
            "Eliminar Entrenador", 
            JOptionPane.WARNING_MESSAGE);
        
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar al entrenador con ID: " + idStr + "?", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean resultado = controladorEntrenador.eliminarEntrenador(idStr);
            if (resultado) {
                limpiarCampos();
                cargarDatosTabla();
            }
        }
    }  
        
    private void actualizar() {
    // Solicitar ID del entrenador a actualizar
        String idStr = JOptionPane.showInputDialog(this, 
            "Ingrese el ID del entrenador a actualizar:", 
            "Actualizar Entrenador", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }
        
        if (!validarCampos()) {
            return;
        }
        
        try {
            String nombre = txtNombre.getText().trim();
            String primerApellido = txtApellido1.getText().trim();
            String segundoApellido = txtApellido2.getText().trim();
            String telefono = txtContacto.getText().trim();
            String correo = TxtCorreo.getText().trim();
            String especialidad = txtEspecialidad.getSelectedItem().toString();
            
            boolean resultado = controladorEntrenador.actualizarEntrenador(
                idStr, nombre, primerApellido, segundoApellido, telefono, correo, especialidad);
            
            if (resultado) {
                limpiarCampos();
                cargarDatosTabla();
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al actualizar el entrenador: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }    
    }  
        
    private void buscar() {
     String idStr = JOptionPane.showInputDialog(this, 
            "Ingrese el ID del entrenador:", 
            "Buscar Entrenador", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }
        
        Entrenador entrenador = controladorEntrenador.buscarEntrenador(idStr);
        
        if (entrenador != null) {
            txtNombre.setText(entrenador.getNombre());
            txtApellido1.setText(entrenador.getPrimerApellido());
            txtApellido2.setText(entrenador.getSegundoApellido());
            txtContacto.setText(entrenador.getTelefono());
            TxtCorreo.setText(entrenador.getCorreo());
            txtEspecialidad.setSelectedItem(entrenador.getEspecialidades());
        } else {
            JOptionPane.showMessageDialog(this, 
                "No se encontró el entrenador con ID: " + idStr, 
                "Búsqueda", 
                JOptionPane.INFORMATION_MESSAGE);
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
        jTable1 = new javax.swing.JTable();
        txtContacto = new javax.swing.JTextField();
        TxtCorreo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtApellido1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtApellido2 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtEspecialidad = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();

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
                .addGap(57, 57, 57)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jTable1.setBackground(new java.awt.Color(255, 204, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        txtContacto.setBackground(new java.awt.Color(255, 204, 0));
        txtContacto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtContacto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        TxtCorreo.setBackground(new java.awt.Color(255, 204, 0));
        TxtCorreo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        TxtCorreo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Contacto");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Correo");

        txtNombre.setBackground(new java.awt.Color(255, 204, 0));
        txtNombre.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre");

        txtApellido1.setBackground(new java.awt.Color(255, 204, 0));
        txtApellido1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtApellido1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("1er Apellido");

        txtApellido2.setBackground(new java.awt.Color(255, 204, 0));
        txtApellido2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtApellido2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("2do Apellido");

        txtEspecialidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Especialidad");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(66, 66, 66)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(jLabel5))
                                    .addComponent(TxtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(txtApellido2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel6)
                            .addComponent(txtEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        actualizar();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField TxtCorreo;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtApellido1;
    private javax.swing.JTextField txtApellido2;
    private javax.swing.JTextField txtContacto;
    private javax.swing.JComboBox<String> txtEspecialidad;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
