package VentanaEmergente.Compras;

import Conexiones.Conexion;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class addProveedor extends javax.swing.JDialog {

    TextAutoCompleter au;
    
    public void completar(){
        au = new TextAutoCompleter(txtProv);
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Nombre from registroprov_compras";
            String datos[] = new String[10];
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("Nombre");
                au.addItem(datos[0]);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL AUTOCOMPLETAR" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void guardar(){
        if(txtProv.getText().equals("")){
        JOptionPane.showMessageDialog(this,"CAMPO PROVEEDOR VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }else if(txtContacto.getText().equals("")){
            JOptionPane.showMessageDialog(this, "CAMPO CONTACTO VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                }else if(txtIva.getText().equals("")){
                JOptionPane.showMessageDialog(this, "CAMPO DIRECCION VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                    }else if(txtTelefono.getText().equals("")){
                    JOptionPane.showMessageDialog(this, "CAMPO TELEFONO VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                        } else if(txtIva.getText().equals("")){
                    JOptionPane.showMessageDialog(this, "CAMPO IVA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                            }else if(cmbCondicion.getSelectedItem().toString().equals("SELECCIONAR CONDICION")){
                        JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN CAMPO DE CONDICION DE PAGO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                                }else if(cmbMoneda.getSelectedItem().toString().equals("SELECCIONAR MONEDA")){
                            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN TIPO DE MONEDA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                                }   
                    else{
                        if(btnGuardar.getText().equals("Guardar")){
                            try{
                            Connection con = null;
                            Conexion con1 = new Conexion();
                            con = con1.getConnection();
                            Statement st = con.createStatement();
                            String sql = "insert into registroProv_Compras (Nombre,Contacto,Direccion,Telefono,Condiciones,Iva,Moneda,Correo,Celular) values(?,?,?,?,?,?,?,?,?)";
                            PreparedStatement pst = con.prepareStatement(sql);

                            pst.setString(1, txtProv.getText());
                            pst.setString(2, txtContacto.getText());
                            pst.setString(3, txtDireccion1.getText());
                            pst.setString(4, txtTelefono.getText());
                            pst.setString(5, cmbCondicion.getSelectedItem().toString());
                            pst.setString(6, txtIva.getText());
                            pst.setString(7, cmbMoneda.getSelectedItem().toString());
                            pst.setString(8, txtCorreo.getText());
                            pst.setString(9, txtTelefonoPersonal.getText());

                            int n = pst.executeUpdate();
                            if(n > 0){
                                txtProv.setText("");
                                txtContacto.setText("");
                                txtIva.setText("");
                                txtTelefono.setText("");
                                txtCorreo.setText("");
                                txtTelefonoPersonal.setText("");
                                JOptionPane.showMessageDialog(this, "DATOS GUARDADOS CORRECTAMENTE");
                            }

                            }catch(SQLException e){
                            JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR DATOS" + e,"ERROR",JOptionPane.ERROR_MESSAGE);
                            }
                        }else{
                            try{
                            Connection con = null;
                            Conexion con1 = new Conexion();
                            con = con1.getConnection();
                            Statement st = con.createStatement();
                            String sql = "update registroProv_Compras set Nombre = ?,Contacto = ?,Direccion = ?,Telefono = ?,Condiciones = ?,Iva = ?"
                                    + ",Moneda = ?, Correo = ?, Celular = ? where Nombre = ?";
                            PreparedStatement pst = con.prepareStatement(sql);

                            pst.setString(1, txtProv.getText());
                            pst.setString(2, txtContacto.getText());
                            pst.setString(3, txtDireccion1.getText());
                            pst.setString(4, txtTelefono.getText());
                            pst.setString(5, cmbCondicion.getSelectedItem().toString());
                            pst.setString(6, txtIva.getText());
                            pst.setString(7, cmbMoneda.getSelectedItem().toString());
                            pst.setString(8,txtCorreo.getText());
                            pst.setString(9,txtTelefonoPersonal.getText());
                            pst.setString(10,txtProv.getText());

                            int n = pst.executeUpdate();
                            if(n > 0){
                                txtProv.setText("");
                                txtContacto.setText("");
                                txtIva.setText("");
                                txtTelefono.setText("");
                                txtTelefonoPersonal.setText("");
                                txtCorreo.setText("");
                                JOptionPane.showMessageDialog(this, "DATOS ACTUALIZADOS CORRECTAMENTE");
                            }

                            }catch(SQLException e){
                            JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR DATOS" + e,"ERROR",JOptionPane.ERROR_MESSAGE);
                            }
                        }
                 }
    }
    
    public void verProveedor(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroprov_compras where Nombre like '"+txtProv.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                txtProv.setText(rs.getString("Nombre"));
                txtContacto.setText(rs.getString("Contacto"));
                txtDireccion1.setText(rs.getString("Direccion"));
                txtTelefono.setText(rs.getString("Telefono"));
                txtIva.setText(rs.getString("Iva"));
                txtCorreo.setText(rs.getString("Correo"));
                cmbCondicion.setSelectedItem(rs.getString("Condiciones"));
                cmbMoneda.setSelectedItem(rs.getString("Moneda"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public addProveedor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        completar();
        this.setBackground(new Color(0,0,0,0));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelRound1 = new scrollPane.PanelRound();
        jLabel1 = new javax.swing.JLabel();
        txtProv = new rojeru_san.RSMTextFull();
        txtContacto = new rojeru_san.RSMTextFull();
        txtIva = new rojeru_san.RSMTextFull();
        cmbMoneda = new RSMaterialComponent.RSComboBoxMaterial();
        cmbCondicion = new RSMaterialComponent.RSComboBoxMaterial();
        btnLimpiar = new rojeru_san.rsbutton.RSButtonRoundRipple();
        btnGuardar = new rojeru_san.rsbutton.RSButtonRoundRipple();
        txtTelefono = new RSComponentShade.RSFormatFieldShade();
        panelX = new javax.swing.JPanel();
        lblX = new javax.swing.JLabel();
        txtDireccion1 = new rojeru_san.RSMTextFull();
        txtCorreo = new rojeru_san.RSMTextFull();
        txtTelefonoPersonal = new RSComponentShade.RSFormatFieldShade();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(477, 688));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(436, 763));

        panelRound1.setBackground(new java.awt.Color(51, 51, 51));
        panelRound1.setRoundBottomLeft(100);
        panelRound1.setRoundBottomRight(100);
        panelRound1.setRoundTopLeft(100);
        panelRound1.setRoundTopRight(100);
        panelRound1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Lexend", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("AGREGAR PROVEEDOR");
        panelRound1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, -1));

        txtProv.setBackground(new java.awt.Color(51, 51, 51));
        txtProv.setForeground(new java.awt.Color(255, 255, 255));
        txtProv.setBordeColorFocus(new java.awt.Color(255, 255, 255));
        txtProv.setBordeColorNoFocus(new java.awt.Color(102, 102, 102));
        txtProv.setBotonColor(new java.awt.Color(51, 51, 51));
        txtProv.setCaretColor(new java.awt.Color(255, 255, 255));
        txtProv.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtProv.setMayusculas(true);
        txtProv.setModoMaterial(true);
        txtProv.setNextFocusableComponent(txtContacto);
        txtProv.setPlaceholder("Nombre de proveedor");
        txtProv.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtProv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtProvFocusLost(evt);
            }
        });
        txtProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProvActionPerformed(evt);
            }
        });
        panelRound1.add(txtProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 420, -1));

        txtContacto.setBackground(new java.awt.Color(51, 51, 51));
        txtContacto.setForeground(new java.awt.Color(255, 255, 255));
        txtContacto.setBordeColorFocus(new java.awt.Color(255, 255, 255));
        txtContacto.setBordeColorNoFocus(new java.awt.Color(102, 102, 102));
        txtContacto.setBotonColor(new java.awt.Color(51, 51, 51));
        txtContacto.setCaretColor(new java.awt.Color(255, 255, 255));
        txtContacto.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtContacto.setMayusculas(true);
        txtContacto.setModoMaterial(true);
        txtContacto.setNextFocusableComponent(txtIva);
        txtContacto.setPlaceholder("Contacto");
        txtContacto.setSelectionColor(new java.awt.Color(255, 255, 255));
        panelRound1.add(txtContacto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 420, -1));

        txtIva.setBackground(new java.awt.Color(51, 51, 51));
        txtIva.setForeground(new java.awt.Color(255, 255, 255));
        txtIva.setBordeColorFocus(new java.awt.Color(255, 255, 255));
        txtIva.setBordeColorNoFocus(new java.awt.Color(102, 102, 102));
        txtIva.setBotonColor(new java.awt.Color(51, 51, 51));
        txtIva.setCaretColor(new java.awt.Color(255, 255, 255));
        txtIva.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtIva.setModoMaterial(true);
        txtIva.setNextFocusableComponent(cmbCondicion);
        txtIva.setPlaceholder("Iva");
        txtIva.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtIva.setSoloNumeros(true);
        panelRound1.add(txtIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 420, -1));

        cmbMoneda.setBackground(new java.awt.Color(51, 51, 51));
        cmbMoneda.setForeground(new java.awt.Color(255, 255, 255));
        cmbMoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SELECCIONAR MONEDA", "MXN", "DLLS" }));
        cmbMoneda.setColorMaterial(new java.awt.Color(255, 255, 255));
        cmbMoneda.setNextFocusableComponent(btnGuardar);
        panelRound1.add(cmbMoneda, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 630, 420, -1));

        cmbCondicion.setBackground(new java.awt.Color(51, 51, 51));
        cmbCondicion.setForeground(new java.awt.Color(255, 255, 255));
        cmbCondicion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SELECCIONAR CONDICION", "CREDITO", "CONTADO" }));
        cmbCondicion.setColorMaterial(new java.awt.Color(255, 255, 255));
        cmbCondicion.setNextFocusableComponent(cmbMoneda);
        panelRound1.add(cmbCondicion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 550, 420, -1));

        btnLimpiar.setText("Limpiar");
        btnLimpiar.setNextFocusableComponent(txtProv);
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        panelRound1.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 720, 130, -1));

        btnGuardar.setText("Guardar");
        btnGuardar.setNextFocusableComponent(btnLimpiar);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        panelRound1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 720, 150, -1));

        txtTelefono.setBackground(new java.awt.Color(51, 51, 51));
        txtTelefono.setForeground(new java.awt.Color(255, 255, 255));
        txtTelefono.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTelefono.setBgShade(new java.awt.Color(204, 204, 204));
        txtTelefono.setBgShadeHover(new java.awt.Color(255, 255, 255));
        txtTelefono.setCaretColor(new java.awt.Color(255, 255, 255));
        txtTelefono.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtTelefono.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        try {
            txtTelefono.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###-###-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTelefono.setNextFocusableComponent(txtIva);
        txtTelefono.setPlaceholder("Phone number");
        txtTelefono.setPreferredSize(new java.awt.Dimension(300, 45));
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });
        panelRound1.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 420, 40));

        panelX.setBackground(new java.awt.Color(51, 51, 51));

        lblX.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblX.setForeground(new java.awt.Color(255, 255, 255));
        lblX.setText(" X ");
        lblX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblXMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblXMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblXMouseExited(evt);
            }
        });
        panelX.add(lblX);

        panelRound1.add(panelX, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, -1, -1));

        txtDireccion1.setBackground(new java.awt.Color(51, 51, 51));
        txtDireccion1.setForeground(new java.awt.Color(255, 255, 255));
        txtDireccion1.setBordeColorFocus(new java.awt.Color(255, 255, 255));
        txtDireccion1.setBordeColorNoFocus(new java.awt.Color(102, 102, 102));
        txtDireccion1.setBotonColor(new java.awt.Color(51, 51, 51));
        txtDireccion1.setCaretColor(new java.awt.Color(255, 255, 255));
        txtDireccion1.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtDireccion1.setMayusculas(true);
        txtDireccion1.setModoMaterial(true);
        txtDireccion1.setNextFocusableComponent(txtTelefono);
        txtDireccion1.setPlaceholder("Direccion");
        txtDireccion1.setSelectionColor(new java.awt.Color(255, 255, 255));
        panelRound1.add(txtDireccion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 420, -1));

        txtCorreo.setBackground(new java.awt.Color(51, 51, 51));
        txtCorreo.setForeground(new java.awt.Color(255, 255, 255));
        txtCorreo.setBordeColorFocus(new java.awt.Color(255, 255, 255));
        txtCorreo.setBordeColorNoFocus(new java.awt.Color(102, 102, 102));
        txtCorreo.setBotonColor(new java.awt.Color(51, 51, 51));
        txtCorreo.setCaretColor(new java.awt.Color(255, 255, 255));
        txtCorreo.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtCorreo.setModoMaterial(true);
        txtCorreo.setNextFocusableComponent(cmbCondicion);
        txtCorreo.setPlaceholder("Correo");
        txtCorreo.setSelectionColor(new java.awt.Color(255, 255, 255));
        panelRound1.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 420, 40));

        txtTelefonoPersonal.setBackground(new java.awt.Color(51, 51, 51));
        txtTelefonoPersonal.setForeground(new java.awt.Color(255, 255, 255));
        txtTelefonoPersonal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTelefonoPersonal.setBgShade(new java.awt.Color(204, 204, 204));
        txtTelefonoPersonal.setBgShadeHover(new java.awt.Color(255, 255, 255));
        txtTelefonoPersonal.setCaretColor(new java.awt.Color(255, 255, 255));
        txtTelefonoPersonal.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtTelefonoPersonal.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        try {
            txtTelefonoPersonal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###-###-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTelefonoPersonal.setNextFocusableComponent(txtIva);
        txtTelefonoPersonal.setPlaceholder("Phone number");
        txtTelefonoPersonal.setPreferredSize(new java.awt.Dimension(300, 45));
        txtTelefonoPersonal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoPersonalKeyTyped(evt);
            }
        });
        panelRound1.add(txtTelefonoPersonal, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 420, 40));

        jLabel2.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel2.setText("Personal");
        panelRound1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, -1, -1));

        jLabel3.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel3.setText("Oficina");
        panelRound1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, -1, -1));

        getContentPane().add(panelRound1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void lblXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseEntered
        panelX.setBackground(Color.red);
    }//GEN-LAST:event_lblXMouseEntered

    private void lblXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseExited
        panelX.setBackground(new Color(51,51,51));
    }//GEN-LAST:event_lblXMouseExited

    private void lblXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseClicked
        dispose();
    }//GEN-LAST:event_lblXMouseClicked

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        txtProv.setText("");
        txtContacto.setText("");
        txtIva.setText("");
        txtIva.setText("");
        txtTelefono.setText("");
        cmbCondicion.setSelectedIndex(0);
        cmbMoneda.setSelectedIndex(0);
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
       guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtProvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProvFocusLost
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "select * from registroprov_compras where Nombre like '"+txtProv.getText()+"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            String prov = null;
            while(rs.next()){
                prov = rs.getString("Nombre");
            }
            if(prov == null){
                btnGuardar.setText("Guardar");
            }else{
                btnGuardar.setText("Actualizar");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtProvFocusLost

    private void txtProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProvActionPerformed
        verProveedor();
    }//GEN-LAST:event_txtProvActionPerformed

    private void txtTelefonoPersonalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoPersonalKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoPersonalKeyTyped

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
            java.util.logging.Logger.getLogger(addProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                addProveedor dialog = new addProveedor(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public rojeru_san.rsbutton.RSButtonRoundRipple btnGuardar;
    private rojeru_san.rsbutton.RSButtonRoundRipple btnLimpiar;
    private RSMaterialComponent.RSComboBoxMaterial cmbCondicion;
    private RSMaterialComponent.RSComboBoxMaterial cmbMoneda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblX;
    private scrollPane.PanelRound panelRound1;
    private javax.swing.JPanel panelX;
    private rojeru_san.RSMTextFull txtContacto;
    private rojeru_san.RSMTextFull txtCorreo;
    private rojeru_san.RSMTextFull txtDireccion1;
    private rojeru_san.RSMTextFull txtIva;
    public rojeru_san.RSMTextFull txtProv;
    private RSComponentShade.RSFormatFieldShade txtTelefono;
    private RSComponentShade.RSFormatFieldShade txtTelefonoPersonal;
    // End of variables declaration//GEN-END:variables
}
