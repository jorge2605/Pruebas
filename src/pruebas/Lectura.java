package pruebas;

public class Lectura extends javax.swing.JDialog {

    
    
    public Lectura(java.awt.Frame parent, boolean modal, String nominal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        txtNominal.setText(nominal);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblPunto = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnBorrar = new javax.swing.JButton();
        btnEnviar = new javax.swing.JButton();
        txtObservaciones = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        txtReal = new javax.swing.JFormattedTextField();
        txtNominal = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel1.setText("PUNTO NO:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 21, -1, -1));

        lblPunto.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPunto.setText("PUNTO");
        jPanel1.add(lblPunto, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel3.setText("REAL:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, -1, -1));

        btnBorrar.setBackground(new java.awt.Color(255, 255, 255));
        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/borrra_48.png"))); // NOI18N
        btnBorrar.setBorder(null);
        btnBorrar.setBorderPainted(false);
        btnBorrar.setContentAreaFilled(false);
        btnBorrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBorrar.setFocusPainted(false);
        btnBorrar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnBorrar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/borrra_48.png"))); // NOI18N
        btnBorrar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/borrar_64.png"))); // NOI18N
        btnBorrar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel1.add(btnBorrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, 64, 64));

        btnEnviar.setBackground(new java.awt.Color(255, 255, 255));
        btnEnviar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/send_48.png"))); // NOI18N
        btnEnviar.setBorder(null);
        btnEnviar.setBorderPainted(false);
        btnEnviar.setContentAreaFilled(false);
        btnEnviar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEnviar.setFocusPainted(false);
        btnEnviar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEnviar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/send_48.png"))); // NOI18N
        btnEnviar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/send_64.png"))); // NOI18N
        btnEnviar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEnviar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 64, 64));

        txtObservaciones.setBackground(new java.awt.Color(255, 255, 255));
        txtObservaciones.setBorder(null);
        txtObservaciones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtObservacionesKeyTyped(evt);
            }
        });
        jPanel1.add(txtObservaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 610, 20));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 610, 10));

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel4.setText("OBSERVACIONES:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel5.setText("NOMINAL:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 70, 10));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 70, 10));

        txtReal.setBackground(new java.awt.Color(255, 255, 255));
        txtReal.setBorder(null);
        txtReal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0.###"))));
        txtReal.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtReal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRealFocusLost(evt);
            }
        });
        txtReal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRealKeyTyped(evt);
            }
        });
        jPanel1.add(txtReal, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, 70, 20));

        txtNominal.setBackground(new java.awt.Color(255, 255, 255));
        txtNominal.setBorder(null);
        txtNominal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0.###"))));
        txtNominal.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtNominal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNominalFocusLost(evt);
            }
        });
        txtNominal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNominalKeyTyped(evt);
            }
        });
        jPanel1.add(txtNominal, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, 70, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void txtObservacionesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObservacionesKeyTyped
        char c = evt.getKeyChar();
        if(Character.isLowerCase(c))
        {
            String cad = (""+c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtObservacionesKeyTyped

    private void txtRealFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRealFocusLost
        
    }//GEN-LAST:event_txtRealFocusLost

    private void txtRealKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRealKeyTyped
//        char c = evt.getKeyChar();
//        if(c == '.' || c<'0'||c>'9') evt.consume();
    }//GEN-LAST:event_txtRealKeyTyped

    private void txtNominalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNominalFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNominalFocusLost

    private void txtNominalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNominalKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNominalKeyTyped

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
            java.util.logging.Logger.getLogger(Lectura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Lectura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Lectura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Lectura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Lectura dialog = new Lectura(new javax.swing.JFrame(), true,"");
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
    public javax.swing.JButton btnBorrar;
    public javax.swing.JButton btnEnviar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    public javax.swing.JLabel lblPunto;
    public javax.swing.JFormattedTextField txtNominal;
    public javax.swing.JTextField txtObservaciones;
    public javax.swing.JFormattedTextField txtReal;
    // End of variables declaration//GEN-END:variables
}
