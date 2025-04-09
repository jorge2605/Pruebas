package VentanaEmergente.CambiarEstado;

import Conexiones.Conexion;
import Controlador.maquinados.revisarPlanos;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Frame;
import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import pruebas.CambiarEstado;
import pruebas.Inicio1;

public class TerminarProyecto extends javax.swing.JDialog {

    TextAutoCompleter ac1;
    public String numEmpleado;

    public void terminarProyecto(String proyecto) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            Statement st3 = con.createStatement();
            Statement st5 = con.createStatement();
            Statement st7 = con.createStatement();
            Statement st9 = con.createStatement();
            Statement st11 = con.createStatement();
            Statement st13 = con.createStatement();

            String sql = "select Plano, Proyecto, Terminado from datos where Plano like '" + proyecto + "' and Terminado like 'NO'";
            ResultSet rs = st.executeQuery(sql);
            String plano;
            String sql2 = "update datos set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ? where Proyecto = ?";
            PreparedStatement pst = con.prepareStatement(sql2);
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat();
            String fecha = sdf.format(d);
            int n = 0;
            while (rs.next()) {
                plano = rs.getString("Proyecto");
                pst.setString(1, fecha);
                pst.setString(2, fecha);
                pst.setString(3, "SI");
                pst.setString(4, numEmpleado + "," + numEmpleado);
                pst.setString(5, plano);

                n = pst.executeUpdate();
            }

            String sql3 = "select Plano, Proyecto, Terminado from acabados where Plano like '" + proyecto + "' and Terminado like 'NO'";
            ResultSet rs3 = st3.executeQuery(sql3);
            String sql4 = "update acabados set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ? where Proyecto = ?";
            PreparedStatement pst4 = con.prepareStatement(sql4);
            int n1 = 0;
            while (rs3.next()) {
                plano = rs3.getString("Proyecto");
                pst4.setString(1, fecha);
                pst4.setString(2, fecha);
                pst4.setString(3, "SI");
                pst4.setString(4, numEmpleado + "," + numEmpleado);
                pst4.setString(5, plano);

                n1 = pst4.executeUpdate();
            }

            String sql5 = "select Plano, Proyecto, Terminado from calidad where Plano like '" + proyecto + "' and Terminado like 'NO'";
            ResultSet rs5 = st5.executeQuery(sql5);
            String sql6 = "update calidad set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ?, Tratamiento = ? where Proyecto = ?";
            PreparedStatement pst6 = con.prepareStatement(sql6);
            int n2 = 0;
            while (rs5.next()) {
                plano = rs5.getString("Proyecto");
                pst6.setString(1, fecha);
                pst6.setString(2, fecha);
                pst6.setString(3, "SI");
                pst6.setString(4, numEmpleado + "," + numEmpleado);
                pst6.setString(5, "NO");
                pst6.setString(6, plano);

                n2 = pst6.executeUpdate();
            }

            String sql7 = "select Plano, Proyecto, Terminado from cnc where Plano like '" + proyecto + "' and Terminado like 'NO'";
            ResultSet rs7 = st7.executeQuery(sql7);
            String sql8 = "update cnc set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ? where Proyecto = ?";
            PreparedStatement pst8 = con.prepareStatement(sql8);
            int n3 = 0;
            while (rs7.next()) {
                plano = rs7.getString("Proyecto");
                pst8.setString(1, fecha);
                pst8.setString(2, fecha);
                pst8.setString(3, "SI");
                pst8.setString(4, numEmpleado + "," + numEmpleado);
                pst8.setString(5, plano);

                n3 = pst8.executeUpdate();
            }

            String sql9 = "select Plano, Proyecto, Terminado from fresadora where Plano like '" + proyecto + "' and Terminado like 'NO'";
            ResultSet rs9 = st9.executeQuery(sql9);
            String sql10 = "update fresadora set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ? where Proyecto = ?";
            PreparedStatement pst10 = con.prepareStatement(sql10);
            int n4 = 0;
            while (rs9.next()) {
                plano = rs9.getString("Proyecto");
                pst10.setString(1, fecha);
                pst10.setString(2, fecha);
                pst10.setString(3, "SI");
                pst10.setString(4, numEmpleado + "," + numEmpleado);
                pst10.setString(5, plano);

                n4 = pst10.executeUpdate();
            }

            String sql11 = "select Plano, Proyecto, Terminado from torno where Plano like '" + proyecto + "' and Terminado like 'NO'";
            ResultSet rs11 = st11.executeQuery(sql11);
            String sql12 = "update torno set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ? where Proyecto = ?";
            PreparedStatement pst12 = con.prepareStatement(sql12);
            int n5 = 0;
            while (rs11.next()) {
                plano = rs11.getString("Proyecto");
                pst12.setString(1, fecha);
                pst12.setString(2, fecha);
                pst12.setString(3, "SI");
                pst12.setString(4, numEmpleado + "," + numEmpleado);
                pst12.setString(5, plano);

                n5 = pst12.executeUpdate();
            }

            String sql13 = "select Plano, Proyecto, Prioridad from planos where Proyecto like '" + proyecto + "'";
            ResultSet rs13 = st13.executeQuery(sql13);
            revisarPlanos rev = new revisarPlanos();
            int n6 = 0;
            while (rs13.next()) {
                String pl = rs13.getString("Plano");
                String pr = rs13.getString("Proyecto");
                rev.terminarPlano(pl, pr, numEmpleado, null, "integracion", con);
            }

            if (n > 0 && n1 > 0 && n2 > 0 && n3 > 0 && n4 > 0 && n5 > 0) {
                JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
            } else {
                if (n6 > 0) {
                    String faltantes = "";
                    if (n < 1) {
                        faltantes += "\nCortes";
                    }
                    if (n1 < 1) {
                        faltantes += "\nAcabados";
                    }
                    if (n2 < 1) {
                        faltantes += "\nCalidad";
                    }
                    if (n3 < 1) {
                        faltantes += "\nCnc";
                    }
                    if (n4 < 1) {
                        faltantes += "\nFresadora";
                    }
                    if (n5 < 1) {
                        faltantes += "\nTorno";
                    }

                    JOptionPane.showMessageDialog(this, "DATOS GUARDADOS, SIN CAMBIOS EN: "
                            + faltantes);
                } else {
                    JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private static void cargarUbicacionVentana(Frame ventana) {
        Preferences prefs = Preferences.userNodeForPackage(Inicio1.class);
        int x = prefs.getInt("posX", 100);
        int y = prefs.getInt("posY", 100);
        ventana.setLocation(x, y);
    }

    private static void guardarUbicacionVentana(Frame ventana) {
        Preferences prefs = Preferences.userNodeForPackage(Inicio1.class);
        Point ubicacion = ventana.getLocation();
        prefs.putInt("posX", ubicacion.x);
        prefs.putInt("posY", ubicacion.y);
    }

    public final void autoCompletarProyecto() {
        ac1 = new TextAutoCompleter(txtProyecto);
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Proyecto from proyectos";
            String datos[] = new String[10];
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("Proyecto");
                ac1.addItem(datos[0]);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL AUTOCOMPLETAR" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final boolean buscarEnTabla() {
        for (int i = 0; i < Tabla1.getRowCount(); i++) {
            if (Tabla1.getValueAt(i, 0).toString().equals(txtProyecto.getText())) {
                return true;
            }
        }
        return false;
    }

    public TerminarProyecto(java.awt.Frame parent, boolean modal, String numEmpleado) {
        super(parent, modal);
        initComponents();
        this.numEmpleado = numEmpleado;
        autoCompletarProyecto();
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        cargarUbicacionVentana(f);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        txtProyecto = new RSMaterialComponent.RSTextFieldMaterial();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(705, 715));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Introduce proyectos que deseas terminar");
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("     ");
        jPanel3.add(jLabel2);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton1.setText("Terminar proyecto(s)");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1);

        jPanel2.add(jPanel4, java.awt.BorderLayout.SOUTH);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel5Layout = new java.awt.GridBagLayout();
        jPanel5Layout.columnWeights = new double[] {1.0};
        jPanel5Layout.rowWeights = new double[] {0.0, 1.0};
        jPanel5.setLayout(jPanel5Layout);

        txtProyecto.setForeground(new java.awt.Color(51, 51, 51));
        txtProyecto.setColorMaterial(new java.awt.Color(153, 204, 255));
        txtProyecto.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtProyecto.setPhColor(new java.awt.Color(153, 204, 255));
        txtProyecto.setPlaceholder("Proyecto");
        txtProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProyectoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel5.add(txtProyecto, gridBagConstraints);

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Proyecto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(14, 0, 14, 0);
        jPanel5.add(jScrollPane1, gridBagConstraints);

        jPanel2.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        for (int i = 0; i < Tabla1.getRowCount() ;i++) {
            terminarProyecto(Tabla1.getValueAt(i, 0).toString());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProyectoActionPerformed
        if (buscarEnTabla()) {
            JOptionPane.showMessageDialog(this, "Este proyecto ya esta incluida en la tabla", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (!ac1.itemExists(txtProyecto.getText())) {
                JOptionPane.showMessageDialog(this, "Debes ingresar un proyecto valido","Error", JOptionPane.ERROR);
            } else {
                DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
                String dat[] = {txtProyecto.getText()};
                miModelo.addRow(dat);
            }
        }
        txtProyecto.setText("");
    }//GEN-LAST:event_txtProyectoActionPerformed

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        int opc = JOptionPane.showConfirmDialog(this, "Estas segura de eliminar este proyecto?");
        if (opc == 0) {
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            miModelo.removeRow(Tabla1.getSelectedRow());
        }
    }//GEN-LAST:event_Tabla1MouseClicked

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
            java.util.logging.Logger.getLogger(TerminarProyecto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TerminarProyecto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TerminarProyecto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TerminarProyecto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TerminarProyecto dialog = new TerminarProyecto(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JTable Tabla1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    public RSMaterialComponent.RSTextFieldMaterial txtProyecto;
    // End of variables declaration//GEN-END:variables
}
