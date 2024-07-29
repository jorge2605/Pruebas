package VentanaEmergente.Checador;

import java.awt.Color;
import java.awt.Shape;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;

public class Incidencias extends javax.swing.JDialog {

    int x, y;
    
    public final void setShape(){
        Shape shape = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 50, 50);
        this.setShape(shape);
    }
    
    public Incidencias(java.awt.Frame parent, boolean modal, String horaVieja, String horaNueva, String empleado) {
        super(parent, modal);
        initComponents();
        setShape();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setShape();
            }
        });
        txtHoraNueva.setText(horaNueva);
        txtHoraVieja.setText(horaVieja);
        txtEmpleado.setText(empleado);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        pnlX = new javax.swing.JPanel();
        lblX = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtEmpleado = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtHoraVieja = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        txtHoraNueva = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtComentarios = new javax.swing.JTextArea();
        btnGuardar = new scrollPane.BotonRedondo();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(737, 351));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 0, 0, new java.awt.Color(204, 204, 204)));
        jPanel3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel3MouseDragged(evt);
            }
        });
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel3MousePressed(evt);
            }
        });

        jLabel3.setText(" ");
        jPanel3.add(jLabel3);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 0, 0, 3, new java.awt.Color(204, 204, 204)));

        pnlX.setBackground(new java.awt.Color(255, 255, 255));

        lblX.setBackground(new java.awt.Color(255, 255, 255));
        lblX.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
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
        pnlX.add(lblX);

        jPanel4.add(pnlX);

        jPanel2.add(jPanel4, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 3, 3, 3, new java.awt.Color(204, 204, 204)));
        java.awt.GridBagLayout jPanel5Layout = new java.awt.GridBagLayout();
        jPanel5Layout.columnWidths = new int[] {1, 1, 1, 1};
        jPanel5Layout.columnWeights = new double[] {1.0, 1.0, 1.0, 1.0};
        jPanel5Layout.rowWeights = new double[] {0.0, 0.0, 0.0, 1.0};
        jPanel5.setLayout(jPanel5Layout);

        jLabel1.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Empleado: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 23, 5, 23);
        jPanel5.add(jLabel1, gridBagConstraints);

        txtEmpleado.setEditable(false);
        txtEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        txtEmpleado.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtEmpleado.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtEmpleado.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 40);
        jPanel5.add(txtEmpleado, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Hora anterior:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 23, 5, 23);
        jPanel5.add(jLabel2, gridBagConstraints);

        txtHoraVieja.setEditable(false);
        txtHoraVieja.setBackground(new java.awt.Color(255, 255, 255));
        txtHoraVieja.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        try {
            txtHoraVieja.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtHoraVieja.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtHoraVieja.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 40);
        jPanel5.add(txtHoraVieja, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Hora nueva:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 23, 5, 23);
        jPanel5.add(jLabel4, gridBagConstraints);

        txtHoraNueva.setEditable(false);
        txtHoraNueva.setBackground(new java.awt.Color(255, 255, 255));
        txtHoraNueva.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        try {
            txtHoraNueva.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtHoraNueva.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtHoraNueva.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 40);
        jPanel5.add(txtHoraNueva, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel6.setText("Comentarios");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel5.add(jLabel6, gridBagConstraints);

        jScrollPane1.setBorder(null);

        txtComentarios.setBackground(new java.awt.Color(255, 255, 255));
        txtComentarios.setColumns(20);
        txtComentarios.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtComentarios.setLineWrap(true);
        txtComentarios.setRows(5);
        txtComentarios.setWrapStyleWord(true);
        txtComentarios.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        jScrollPane1.setViewportView(txtComentarios);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 12);
        jPanel5.add(jScrollPane1, gridBagConstraints);

        btnGuardar.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar.setForeground(new java.awt.Color(51, 153, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.setBorderColor(new java.awt.Color(51, 153, 255));
        btnGuardar.setBorderColorSelected(new java.awt.Color(0, 51, 153));
        btnGuardar.setColor(new java.awt.Color(51, 153, 255));
        btnGuardar.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        btnGuardar.setPreferredSize(new java.awt.Dimension(100, 35));
        btnGuardar.setThickness(3);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.insets = new java.awt.Insets(17, 17, 17, 17);
        jPanel5.add(btnGuardar, gridBagConstraints);

        jPanel6.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 3, 0, 3, new java.awt.Color(204, 204, 204)));

        jLabel5.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 153, 255));
        jLabel5.setText("Reporte de incidencias");
        jPanel7.add(jLabel5);

        jPanel6.add(jPanel7, java.awt.BorderLayout.NORTH);

        jPanel1.add(jPanel6, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel3MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseDragged
        int xx = evt.getXOnScreen();
        int yy = evt.getYOnScreen();
        this.setLocation(xx - (x), yy - y);
    }//GEN-LAST:event_jPanel3MouseDragged

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jPanel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jPanel3MousePressed

    private void lblXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseClicked
        this.dispose();
    }//GEN-LAST:event_lblXMouseClicked

    private void lblXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseEntered
        pnlX.setBackground(Color.red);
        lblX.setForeground(Color.white);
    }//GEN-LAST:event_lblXMouseEntered

    private void lblXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseExited
        pnlX.setBackground(Color.white);
        lblX.setForeground(Color.black);
    }//GEN-LAST:event_lblXMouseExited

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        
    }//GEN-LAST:event_btnGuardarActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Incidencias dialog = new Incidencias(new javax.swing.JFrame(), true,"","","");
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
    private scrollPane.BotonRedondo btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblX;
    private javax.swing.JPanel pnlX;
    private javax.swing.JTextArea txtComentarios;
    private javax.swing.JTextField txtEmpleado;
    private javax.swing.JFormattedTextField txtHoraNueva;
    private javax.swing.JFormattedTextField txtHoraVieja;
    // End of variables declaration//GEN-END:variables
}
