
package acrimev;


import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;




public class Ventana1 extends javax.swing.JFrame {
    
    public static Ventana1 v;
    
    DefaultTableModel model;
    public Ventana1() {
        initComponents();
        this.getContentPane().setBackground(Color.DARK_GRAY);
        bloquear();
        this.setLocationRelativeTo(null);
        this.setExtendedState(Ventana1.MAXIMIZED_BOTH);
        cargarmat();
        cargarcolor();

    }
    
    public void bloquear(){
        b_nuevoc.setEnabled(false);
        b_nuevom.setEnabled(false);
        c_orientación.setEnabled(false);
        t_código.setEnabled(false);
        t_ubicación.setEnabled(false);
        b_guardar.setEnabled(false);
        b_actualizar.setEnabled(false);
    }
    
    public void desbloquear(){
        b_nuevoc.setEnabled(true);
        b_nuevom.setEnabled(true);
        c_orientación.setEnabled(true);
        t_código.setEnabled(true);
        t_ubicación.setEnabled(true);
        b_guardar.setEnabled(true);
    }
    
    public void limpiar(){
        t_ancho.setText("");
        t_largo.setText("");
        t_grosor.setText("");
        t_código.setText("");
        t_id.setText("");
        c_orientación.setEnabled(false);
        t_código.setEnabled(false);
        t_ubicación.setEnabled(false);
        t_ubicación.setText("");
        b_guardar.setEnabled(false);
        b_actualizar.setEnabled(false);
        b_nuevoc.setEnabled(false);
        b_nuevom.setEnabled(false);
    }
    
    @SuppressWarnings("empty-statement")
    public void buscar(){
        String [] titul = {"IdCorte","Ancho","Largo","Grosor","Descripción","Marca","Tipo_Color","Color","Ubicación","Código","Orientación","% Desperdicio"};
        String [] regis = new String [12];
        String ancho = t_ancho.getText();
        String largo = t_largo.getText();
        String grosor = t_grosor.getText();
        if (ancho.equals("")|| largo.equals("")|| grosor.equals("")){
            JOptionPane.showMessageDialog(null,"No dejar campos vacíos");
        }else{
            
        String material = c_material.getSelectedItem().toString();
        String color = c_color.getSelectedItem().toString();
        int inimat = material.indexOf(" ");
        String mat = material.substring(0, inimat);
        int inicol = color.indexOf(" ");
        String col = color.substring(0, inicol);
        float an = Integer.parseInt(ancho);
        float lar = Integer.parseInt(largo);
        
        
        
        
        
            String sql = "SELECT cortes.IdCorte, cortes.Ancho, cortes.Largo, cortes.Grosor, material.Descripción, material.Marca, color.Tipo_Color, color.Color, cortes.Ubicación, cortes.Código, cortes.Orientación FROM acrimev.cortes, acrimev.material, acrimev.color WHERE cortes.Ancho <= ("+ancho+"+500) AND cortes.Ancho >= "+ancho+"  AND cortes.Largo <= ("+largo+"+500) AND cortes.Largo >= "+largo+" AND cortes.Grosor = "+grosor+" AND cortes.IdMaterial = "+(mat)+" AND cortes.IdMaterial = material.IdMaterial AND cortes.IdColor = "+(col)+" AND cortes.IdColor = color.IdColor;";
            model = new DefaultTableModel(null,titul){
            @Override
            public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
            };
            TableRowSorter sorter = new TableRowSorter(model);
        
            Conectar cc = new Conectar();
            Connection cn = cc.conexion();
            try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql);
        
             while (rs.next()){
                 regis[0] = rs.getString("IdCorte");
                 regis[1] = rs.getString("Ancho");
                 regis[2] = rs.getString("Largo");
                 float anb = Integer.parseInt(regis[1]);
                 float larb = Integer.parseInt(regis[2]);
                 float indice = (100-((an*lar)*100)/((anb*larb)));
                 String ind = Float.toString(indice)+"%";
                 regis[3] = rs.getString("Grosor");
                 regis[4] = rs.getString("Descripción");
                 regis[5] = rs.getString("Marca");
                 regis[6] = rs.getString("Tipo_Color");
                 regis[7] = rs.getString("Color");
                 regis[8] = rs.getString("Ubicación");
                 regis[9] = rs.getString("Código");
                 regis[10] = rs.getString("Orientación");
                 regis[11] = ind;
                
                 if (regis[10].equals ("1")){
                     regis[10] = "Si";
                 } else {
                     regis[10] = "No";
                 }
            
             model.addRow(regis);
        }
        
         t_datos.setModel(model);
        
                
                
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
            t_datos.getTableHeader().setFont(new Font("Dialog",1,14));
            t_datos.setRowSorter(sorter);
        
        }
        
    }
    
    public static void cargarmat(){
        Conectar cc = new Conectar();
        Connection cn = cc.conexion();
        String sql;
        sql="SELECT DISTINCT material.IdMaterial, material.Descripción, material.Marca FROM acrimev.material ORDER BY material.Descripción ASC";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()){
                c_material.addItem(rs.getObject("IdMaterial")+" - "+rs.getObject("Descripción")+" - "+rs.getObject("Marca"));
            }
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public static void cargarcolor(){
        Conectar cc = new Conectar();
        Connection cn = cc.conexion();
        String sql;
        sql="SELECT DISTINCT color.IdColor, color.Tipo_Color, color.Color FROM acrimev.color ORDER BY color.Tipo_Color ASC";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()){
                c_color.addItem(rs.getObject("IdColor")+" - "+rs.getObject("Tipo_Color")+" - "+rs.getObject("Color"));
            }
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
     
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        modificar = new javax.swing.JMenuItem();
        eliminar = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        t_ancho = new javax.swing.JTextField();
        t_largo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        t_grosor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        c_material = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        c_color = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_datos = new javax.swing.JTable();
        b_buscar = new javax.swing.JButton();
        b_nuevo = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        t_ubicación = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        t_código = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        c_orientación = new javax.swing.JComboBox<>();
        b_nuevoc = new javax.swing.JButton();
        b_nuevom = new javax.swing.JButton();
        b_guardar = new javax.swing.JButton();
        b_actualizar = new javax.swing.JButton();
        b_cancelar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        t_id = new javax.swing.JTextField();

        modificar.setText("Modificar");
        modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(modificar);

        eliminar.setText("Eliminar");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(eliminar);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imágenes/logo-acrimev-png.png"))); // NOI18N

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("ANCHO (mm):");

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("LARGO (mm):");

        t_largo.setPreferredSize(new java.awt.Dimension(44, 19));

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("GROSOR (mm):");

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("MATERIAL:");

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("COLOR:");

        c_color.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_colorActionPerformed(evt);
            }
        });

        t_datos.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        t_datos.setForeground(new java.awt.Color(0, 0, 0));
        t_datos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        t_datos.setComponentPopupMenu(jPopupMenu1);
        t_datos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        t_datos.setDragEnabled(true);
        t_datos.setGridColor(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(t_datos);

        b_buscar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        b_buscar.setText("BUSCAR");
        b_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_buscarActionPerformed(evt);
            }
        });

        b_nuevo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        b_nuevo.setText("NUEVO");
        b_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_nuevoActionPerformed(evt);
            }
        });

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("UBICACIÓN:");

        t_ubicación.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_ubicaciónActionPerformed(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("CÓDIGO:");

        t_código.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_códigoActionPerformed(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("ORIENTACIÓN:");

        c_orientación.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SI", "NO" }));

        b_nuevoc.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        b_nuevoc.setText("NUEVO COLOR");
        b_nuevoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_nuevocActionPerformed(evt);
            }
        });

        b_nuevom.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        b_nuevom.setText("NUEVO MATERIAL");
        b_nuevom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_nuevomActionPerformed(evt);
            }
        });

        b_guardar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        b_guardar.setText("GUARDAR");
        b_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_guardarActionPerformed(evt);
            }
        });

        b_actualizar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        b_actualizar.setText("ACTUALIZAR");
        b_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_actualizarActionPerformed(evt);
            }
        });

        b_cancelar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        b_cancelar.setText("CANCELAR");
        b_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_cancelarActionPerformed(evt);
            }
        });

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("IDCORTE:");

        t_id.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 12, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(b_buscar)
                                    .addGap(62, 62, 62)
                                    .addComponent(b_nuevo)
                                    .addGap(58, 58, 58)
                                    .addComponent(b_guardar)
                                    .addGap(64, 64, 64)
                                    .addComponent(b_actualizar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(b_cancelar))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel6)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(c_color, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(b_nuevoc)
                                            .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel8)
                                                .addComponent(jLabel7))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(t_ubicación, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(t_id, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel9)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel5)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(c_material, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(b_nuevom))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(t_código, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabel10)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(c_orientación, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(3, 3, 3)
                                .addComponent(t_ancho, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addGap(4, 4, 4)
                                .addComponent(t_largo, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(t_grosor, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 39, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(t_ancho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(t_grosor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(t_largo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(c_material, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(c_color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b_nuevoc)
                    .addComponent(b_nuevom))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t_ubicación, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(t_código, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(c_orientación, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(t_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b_buscar)
                    .addComponent(b_nuevo)
                    .addComponent(b_guardar)
                    .addComponent(b_actualizar)
                    .addComponent(b_cancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_buscarActionPerformed
        buscar();
    }//GEN-LAST:event_b_buscarActionPerformed

    private void b_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_nuevoActionPerformed
            desbloquear();
            b_buscar.setEnabled(false);
            b_nuevo.setEnabled(false);
            
            
    }//GEN-LAST:event_b_nuevoActionPerformed

    private void t_ubicaciónActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_ubicaciónActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_ubicaciónActionPerformed

    private void t_códigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_códigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_códigoActionPerformed

    private void b_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_cancelarActionPerformed
        limpiar();
        b_buscar.setEnabled(true);
        b_nuevo.setEnabled(true);
    }//GEN-LAST:event_b_cancelarActionPerformed

    private void b_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_guardarActionPerformed
        Conectar cc = new Conectar();
            Connection cn = cc.conexion();
            String ancho, largo, grosor, color, material, ubicación, código, orientación;
            ancho = t_ancho.getText();
            largo = t_largo.getText();
            grosor = t_grosor.getText();
            color = c_color.getSelectedItem().toString();
            int inicol = color.indexOf(" ");
            String col = color.substring(0, inicol);
            material = c_material.getSelectedItem().toString();
            int inimat = material.indexOf(" ");
            String mat = material.substring(0, inimat);
            ubicación = t_ubicación.getText();
            código = t_código.getText();
            orientación = c_orientación.getSelectedItem().toString();
            String ori;
             if (orientación.equals ("SI")){
                     ori = "1";
                 } else {
                     ori = "0";
                 }
            
            String sql = "INSERT INTO acrimev.cortes (IdMaterial, IdColor, Ubicación, Ancho, Largo, Grosor, Orientación, Código) VALUES (?,?,?,?,?,?,?,?)";
          try {
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1,mat);
            pst.setString(2,col);
            pst.setString(3,ubicación);
            pst.setString(4,ancho);
            pst.setString(5,largo);
            pst.setString(6,grosor);
            pst.setString(7,ori);
            pst.setString(8,código);
            
            int n = pst.executeUpdate();
            if(n>0){
                JOptionPane.showMessageDialog(null,"Registro guardado con éxito");
                
            }
            limpiar();
            b_buscar.setEnabled(true);
            b_nuevo.setEnabled(true);
            
            
        } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Registro no guardado, verificar datos ingresados");
            Logger.getLogger(Ventana1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_b_guardarActionPerformed

    private void b_nuevocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_nuevocActionPerformed
        v = this;
        Ventana2 v2 = new Ventana2();
        v2.setVisible(true);
        this.setEnabled(false);
    }//GEN-LAST:event_b_nuevocActionPerformed

    private void c_colorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_colorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_c_colorActionPerformed

    private void b_nuevomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_nuevomActionPerformed
        v = this;
        Ventana3 v3 = new Ventana3();
        v3.setVisible(true);
        this.setEnabled(false);
    }//GEN-LAST:event_b_nuevomActionPerformed

    private void modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarActionPerformed
        desbloquear();
        t_código.setEditable(false);
        c_orientación.setEditable(false);
        t_grosor.setEditable(false);
        int fila = t_datos.getSelectedRow();
        if (fila >=0){
          t_id.setText(t_datos.getValueAt(fila,0).toString());
          t_ancho.setText(t_datos.getValueAt(fila,1).toString());
          t_largo.setText(t_datos.getValueAt(fila,2).toString());
          t_grosor.setText(t_datos.getValueAt(fila,3).toString());
          t_ubicación.setText(t_datos.getValueAt(fila,8).toString());
          t_código.setText(t_datos.getValueAt(fila,9).toString());
          b_guardar.setEnabled(false);
          b_nuevo.setEnabled(false);
          b_buscar.setEnabled(false);
          b_actualizar.setEnabled(true);
          
      }
      else{
          JOptionPane.showMessageDialog(null,"Ninguna fila seleccionada");
      }
    }//GEN-LAST:event_modificarActionPerformed

    private void b_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_actualizarActionPerformed
        Conectar cc = new Conectar();
        Connection cn = cc.conexion();
        try {
            PreparedStatement pst = cn.prepareStatement("UPDATE acrimev.cortes SET Ancho ='"+t_ancho.getText()+"', Largo='"+t_largo.getText()+"', Ubicación = '"+t_ubicación.getText()+"' WHERE IdCorte = '"+t_id.getText()+"';");
            pst.executeUpdate();
            limpiar();
            bloquear();
             t_código.setEditable(true);
        c_orientación.setEditable(true);
        t_grosor.setEditable(true);
            JOptionPane.showMessageDialog(null, "Registro modificado con éxito");
        } catch (SQLException ex) {
            Logger.getLogger(Ventana1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_b_actualizarActionPerformed

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
         int fila = t_datos.getSelectedRow();
        String idcorte = "";
        idcorte = t_datos.getValueAt(fila,0).toString();

        try {
           Conectar cc = new Conectar();
           Connection cn = cc.conexion();
           PreparedStatement pst = cn.prepareStatement ("DELETE FROM acrimev.cortes WHERE IdCorte ='"+idcorte+"'");
           pst.executeUpdate();
           
           JOptionPane.showMessageDialog(null,"Registro eliminado");
        } catch (SQLException ex) {
            Logger.getLogger(Ventana1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_eliminarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventana1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_actualizar;
    private javax.swing.JButton b_buscar;
    private javax.swing.JButton b_cancelar;
    private javax.swing.JButton b_guardar;
    private javax.swing.JButton b_nuevo;
    private javax.swing.JButton b_nuevoc;
    private javax.swing.JButton b_nuevom;
    public static javax.swing.JComboBox<String> c_color;
    public static javax.swing.JComboBox<String> c_material;
    private javax.swing.JComboBox<String> c_orientación;
    private javax.swing.JMenuItem eliminar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem modificar;
    private javax.swing.JTextField t_ancho;
    private javax.swing.JTextField t_código;
    private javax.swing.JTable t_datos;
    private javax.swing.JTextField t_grosor;
    private javax.swing.JTextField t_id;
    private javax.swing.JTextField t_largo;
    private javax.swing.JTextField t_ubicación;
    // End of variables declaration//GEN-END:variables
}
