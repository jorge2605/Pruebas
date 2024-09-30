package VentanaEmergente.InicioSesion;

import java.awt.Color;

public class Informacion extends javax.swing.JDialog {

    int x, y;
    
    public Informacion(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        panelRound2.setBackground(new Color(0,0,0,0));
        panelRound3.setBackground(new Color(0,0,0,0));
        pnlX.setBackground(new Color(0,0,0,0));
        jPanel1.setBackground(new Color(0,0,0,0));
        jPanel2.setBackground(new Color(0,0,0,0));
        jScrollPane1.getViewport().setBackground(new Color(0,0,0,0));
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(15);
        this.setBackground(new Color(0,0,0,0));
        revalidate();
        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelRound1 = new scrollPane.PanelRound();
        panelRound2 = new scrollPane.PanelRound();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        pnlX = new scrollPane.PanelRound();
        lblX = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelRound3 = new scrollPane.PanelRound();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(741, 754));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        panelRound1.setBackground(new java.awt.Color(250, 250, 250));
        panelRound1.setRoundBottomLeft(70);
        panelRound1.setRoundBottomRight(70);
        panelRound1.setRoundTopLeft(70);
        panelRound1.setRoundTopRight(70);
        panelRound1.setLayout(new java.awt.BorderLayout());

        panelRound2.setBackground(new java.awt.Color(255, 255, 255));
        panelRound2.setRoundTopLeft(100);
        panelRound2.setRoundTopRight(80);
        panelRound2.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 102, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/titulo actualizacion.png"))); // NOI18N
        jLabel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel1MouseDragged(evt);
            }
        });
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
        });
        panelRound2.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        pnlX.setBackground(new java.awt.Color(255, 255, 255));
        pnlX.setRoundBottomLeft(20);
        pnlX.setRoundBottomRight(20);
        pnlX.setRoundTopLeft(20);
        pnlX.setRoundTopRight(20);

        lblX.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
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

        jPanel2.add(pnlX);

        panelRound2.add(jPanel2, java.awt.BorderLayout.EAST);

        panelRound1.add(panelRound2, java.awt.BorderLayout.NORTH);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panelRound3.setBackground(new java.awt.Color(255, 255, 255));
        panelRound3.setRoundBottomLeft(100);
        panelRound3.setRoundBottomRight(100);
        panelRound3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 51, 0));
        jLabel2.setText("Ver requisiciones:");
        panelRound3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 410, -1, -1));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("<html><p>En la parte de ver requisiciones se podrá abrir el documento de orden de compra, al seleccionar una requisición, si una partida tiene orden de compra deberá dar clic derecho a la partida y seleccionar la orden de compra para que el documento se pueda abrir.");
        panelRound3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 790, 620, 90));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgTemp/ver requisiciones orden de compra.jpg"))); // NOI18N
        jLabel4.setText("jLabel4");
        panelRound3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 880, 450, 250));

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 51, 0));
        jLabel5.setText("Checador:");
        panelRound3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, -1, -1));

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("<html><p>Para supervisores, en la parte de asistencias, tendrán que modificar (si es necesario) el horario de su empleado, al momento de editar algún dato, pedirá el comentario del porque se está modificando, a esto se le llamaran <b>incidencias</b>, al terminar de editar el documento se listaran todas las incidencias echas y se añadirán al reporte de horario.");
        panelRound3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 620, 90));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgTemp/checador incidencias.jpg"))); // NOI18N
        jLabel7.setText("jLabel4");
        panelRound3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 450, 250));

        jLabel8.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("<html><p>Ahora en la parte del inicio, en módulo de ver requisiciones aparecerá un número, ese número representa las requisiciones atrasadas de cada usuario. Entrando al módulo hay una nueva opción, se llama requisiciones atrasadas, en esa opción podrá revisar que requisiciones están atrasadas.");
        panelRound3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 440, 620, 90));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgTemp/ver requisiciones.jpg"))); // NOI18N
        jLabel9.setText("jLabel4");
        panelRound3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 520, 450, 250));

        jScrollPane1.setViewportView(panelRound3);

        panelRound1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(panelRound1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseEntered
        pnlX.setBackground(Color.red);
        lblX.setForeground(Color.white);
    }//GEN-LAST:event_lblXMouseEntered

    private void lblXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseExited
        pnlX.setBackground(new Color(0,0,0,0));
        lblX.setForeground(new Color(52,52,52));
        revalidate();
        repaint();
    }//GEN-LAST:event_lblXMouseExited

    private void lblXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseClicked
        this.dispose();
    }//GEN-LAST:event_lblXMouseClicked

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jLabel1MousePressed

    private void jLabel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseDragged
        int xx = evt.getXOnScreen();
        int yy = evt.getYOnScreen();
        this.setLocation(xx - (x), yy - y);
    }//GEN-LAST:event_jLabel1MouseDragged

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Informacion dialog = new Informacion(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblX;
    private scrollPane.PanelRound panelRound1;
    private scrollPane.PanelRound panelRound2;
    private scrollPane.PanelRound panelRound3;
    private scrollPane.PanelRound pnlX;
    // End of variables declaration//GEN-END:variables
}
