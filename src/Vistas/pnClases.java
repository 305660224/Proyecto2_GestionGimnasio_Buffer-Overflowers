/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vistas;

import Controlador.ControladorClases;
import Modelo.Clases;
import Modelo.TipoClase;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author galla
 */
public class pnClases extends javax.swing.JPanel {

    private ControladorClases controladorClases;
    private DefaultTableModel modeloTabla;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    /**
     * Creates new form pnClases
     */
    public pnClases() {
        initComponents();
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        controladorClases = new ControladorClases();
        configurarTabla();
        cargarDatosTabla();
        configurarComboBoxTipoClase();
        configurarComboBoxSalas();
    }
    
    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "Tipo", "Descripción", "Precio", 
                "Ubicación", "Fecha/Hora", "Capacidad", 
                "Inscritos", "ID Entrenador"
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbClase.setModel(modeloTabla);
    }
    
    private void configurarComboBoxTipoClase() {
        txtTipoClase.removeAllItems();
        txtTipoClase.addItem("SELECCIONE");
        
        // Agregar todos los tipos de clase disponibles
        for (TipoClase tipo : TipoClase.values()) {
            txtTipoClase.addItem(tipo.name());
        }
    }
    
    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        
        List<Clases> clases = controladorClases.listarClases();
        
        for (Clases clase : clases) {
            modeloTabla.addRow(new Object[]{
                clase.getIdClase(),
                clase.getTipoClase().name(),
                clase.getDescripcion(),
                clase.getPrecio(),
                clase.getUbicacion(),
                clase.getHorario().format(formatter),
                clase.getCapacidadMax(),
                clase.getPersonasInscritas(),
                clase.getIdEntrenador()
            });
        }
    }
    
    private void limpiarCampos() {
        txtTipoClase.setSelectedIndex(0);
        txtDescripcion.setText("");
        txtPrecio.setText("");
        txtDireccion.setSelectedIndex(0);
        txtFechaHora.setText("");
        spCapacidad.setValue(1);
        txtEntrenador2.setText("");
    }
    
    private void configurarComboBoxSalas() {
    txtDireccion.removeAllItems();
    txtDireccion.addItem("SELECCIONE SALA");
    txtDireccion.addItem("Sala 1");
    txtDireccion.addItem("Sala 2");
    txtDireccion.addItem("Sala 3");
    txtDireccion.addItem("Sala 4");
    txtDireccion.addItem("Sala 5");
}
    
    private boolean validarCampos() {
    if (txtTipoClase.getSelectedIndex() == 0) {
        JOptionPane.showMessageDialog(this, 
            "Seleccione un tipo de clase", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
    if (txtDireccion.getSelectedIndex() == 0) {
        JOptionPane.showMessageDialog(this, 
            "Seleccione una sala", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
    if (txtDescripcion.getText().trim().isEmpty() || 
        txtFechaHora.getText().trim().isEmpty() ||
        txtPrecio.getText().trim().isEmpty() ||
        txtEntrenador2.getText().trim().isEmpty()) {
        
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
        TipoClase tipo = TipoClase.valueOf(txtTipoClase.getSelectedItem().toString());
        String descripcion = txtDescripcion.getText().trim();
        String precio = txtPrecio.getText().trim();
        String ubicacion = txtDireccion.getSelectedItem().toString(); // Cambiado: ahora es ComboBox
        String fechaHora = txtFechaHora.getText().trim();
        String capacidad = spCapacidad.getValue().toString();
        String idEntrenador = txtEntrenador2.getText().trim();
        
        boolean resultado = controladorClases.registrarClase(
            tipo, descripcion, precio, ubicacion, fechaHora, capacidad, idEntrenador);
        
        if (resultado) {
            limpiarCampos();
            cargarDatosTabla();
        }
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error al registrar la clase: " + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
    }
    cargarDatosTabla();
}
    
    private void metodoActualizar() {
    String idStr = JOptionPane.showInputDialog(this, 
        "Ingrese el ID de la clase a actualizar:", 
        "Actualizar Clase", 
        JOptionPane.QUESTION_MESSAGE);
    
    if (idStr == null || idStr.trim().isEmpty()) {
        return;
    }
    
    if (!validarCampos()) {
        return;
    }
    
    try {
        Clases claseExistente = controladorClases.buscarClase(idStr);
        if (claseExistente == null) {
            JOptionPane.showMessageDialog(this, 
                "No se encontró la clase con ID: " + idStr, 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        TipoClase tipo = TipoClase.valueOf(txtTipoClase.getSelectedItem().toString());
        String descripcion = txtDescripcion.getText().trim();
        String precio = txtPrecio.getText().trim();
        String ubicacion = txtDireccion.getSelectedItem().toString(); // Cambiado
        String fechaHora = txtFechaHora.getText().trim();
        String capacidad = spCapacidad.getValue().toString();
        int personasInscritas = claseExistente.getPersonasInscritas();
        String idEntrenador = txtEntrenador2.getText().trim();
        
        boolean resultado = controladorClases.actualizarClase(
            idStr, tipo, descripcion, precio, ubicacion, fechaHora, 
            capacidad, personasInscritas, idEntrenador);
        
        if (resultado) {
            limpiarCampos();
            cargarDatosTabla();
        }
        
    } catch (Exception e) {

    }
    cargarDatosTabla();
}
    
    private void metodoBuscar() {
    String idStr = JOptionPane.showInputDialog(this, 
        "Ingrese el ID de la clase:", 
        "Buscar Clase", 
        JOptionPane.QUESTION_MESSAGE);
    
    if (idStr == null || idStr.trim().isEmpty()) {
        return;
    }
    
    Clases clase = controladorClases.buscarClase(idStr);
    
    if (clase != null) {
        txtTipoClase.setSelectedItem(clase.getTipoClase().name());
        txtDescripcion.setText(clase.getDescripcion());
        txtPrecio.setText(String.valueOf(clase.getPrecio()));
        String ubicacion = clase.getUbicacion();
        boolean encontrada = false;
        for (int i = 0; i < txtDireccion.getItemCount(); i++) {
            if (txtDireccion.getItemAt(i).equals(ubicacion)) {
                txtDireccion.setSelectedIndex(i);
                encontrada = true;
                break;
            }
        }
        if (!encontrada && txtDireccion.getItemCount() > 0) {
            txtDireccion.setSelectedIndex(0); 
        }
        txtFechaHora.setText(clase.getHorario().format(formatter));
        spCapacidad.setValue(clase.getCapacidadMax());
        txtEntrenador2.setText(String.valueOf(clase.getIdEntrenador()));
    } else {
        JOptionPane.showMessageDialog(this, 
            "No se encontró la clase con ID: " + idStr, 
            "Búsqueda", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}

     private void metodoEliminar() {
        String idStr = JOptionPane.showInputDialog(this, 
            "Ingrese el ID de la clase a eliminar:", 
            "Eliminar Clase", 
            JOptionPane.WARNING_MESSAGE);
        
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }
        
        boolean resultado = controladorClases.eliminarClase(idStr);
        if (resultado) {
            limpiarCampos();
            cargarDatosTabla();
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
        tbClase = new javax.swing.JTable();
        txtTipoClase = new javax.swing.JComboBox<>();
        txtPrecio = new javax.swing.JTextField();
        spCapacidad = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtFechaHora = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtEntrenador2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JComboBox<>();

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
                .addGap(46, 46, 46)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        tbClase.setBackground(new java.awt.Color(255, 204, 0));
        tbClase.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbClase);

        txtTipoClase.setBackground(new java.awt.Color(255, 204, 0));
        txtTipoClase.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTipoClase.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        txtTipoClase.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtPrecio.setBackground(new java.awt.Color(255, 204, 0));
        txtPrecio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtPrecio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        spCapacidad.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        spCapacidad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tipo de Clase");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Capacidad");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Precio");

        txtDescripcion.setBackground(new java.awt.Color(255, 204, 0));
        txtDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtDescripcion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Descripcion");

        txtFechaHora.setBackground(new java.awt.Color(255, 204, 0));
        txtFechaHora.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm"))));
        txtFechaHora.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Fecha y Hora");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Id Entrenador");

        txtEntrenador2.setBackground(new java.awt.Color(255, 204, 0));
        txtEntrenador2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtEntrenador2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Direccion");

        txtDireccion.setBackground(new java.awt.Color(255, 204, 0));
        txtDireccion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtDireccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        txtDireccion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(txtTipoClase, 0, 285, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(49, 49, 49))
                                    .addComponent(txtFechaHora, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtEntrenador2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(spCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(txtDireccion, 0, 285, Short.MAX_VALUE))))
                .addGap(68, 68, 68))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTipoClase, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(spCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtEntrenador2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(41, 41, 41)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        metodoGuardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        metodoActualizar();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        metodoBuscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        metodoEliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner spCapacidad;
    private javax.swing.JTable tbClase;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JComboBox<String> txtDireccion;
    private javax.swing.JTextField txtEntrenador2;
    private javax.swing.JFormattedTextField txtFechaHora;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JComboBox<String> txtTipoClase;
    // End of variables declaration//GEN-END:variables
}
