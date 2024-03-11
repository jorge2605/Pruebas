package VentanaEmergente.Costos;

import Conexiones.Conexion;
import VentanaEmergente.Diseño.codigoBarras;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Stack;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Costeo extends javax.swing.JInternalFrame implements MouseListener, ActionListener, PropertyChangeListener{

    public String numEmpleado;
    public Stack<JPanel> panel;
    public Stack<JLabel> parte;
    public Stack<JLabel> cantidad;
    public Stack<JLabel> precio;
    public Stack<JLabel> subtotal;
    public Stack<JTextArea> descripcion;
    public TextAutoCompleter au;
    int cont = 0;
    public int parteSeleccionada;
    opciones opc;
    
    public final void agregarPartes(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select NumeroDeParte from inventario";
            ResultSet rs = st.executeQuery(sql);
            au = new TextAutoCompleter(txtParte);
            while(rs.next()){
                au.addItem(rs.getString("NumeroDeParte"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void crearPanel(String numero, String desc, String canti, String prec){
        //------------------PANEL GLOBAL------------------------------
        JPanel jpanel = new JPanel();
        jpanel.addMouseListener(this);
        jpanel.setBackground(new java.awt.Color(255, 255, 255));
        jpanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255,255, 255), 4, true));
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = cont;
        gridBagConstraints.insets = new java.awt.Insets(2, 12, 2, 12);
        
        //----------------------------NUMERO DE PARTE-------------------
        JLabel lab = new javax.swing.JLabel();
        lab.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lab.setForeground(new java.awt.Color(51, 51, 51));
        lab.addMouseListener(this);
        lab.setText(numero);
        lab.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
        lab.setPreferredSize(new java.awt.Dimension(300, 20));
        parte.push(lab);
        jpanel.add(parte.get(cont));
        
        //--------------------------DESCRIPCION*----------------------------
        JTextArea area = new javax.swing.JTextArea();
        area.setEditable(false);
        area.setBackground(new java.awt.Color(255, 255, 255));
        area.setColumns(20);
        area.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        area.setLineWrap(true);
        area.addMouseListener(this);
        area.setRows(5);
        area.setText(desc);
        area.setWrapStyleWord(true);
        area.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        JScrollPane scroll = new JScrollPane();
        scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scroll.setPreferredSize(new java.awt.Dimension(350, 74));
        descripcion.push(area);
        scroll.setViewportView(descripcion.get(cont));
        jpanel.add(scroll);
        
        //-----------------------------CANTIDAD---------------------------------
        JLabel cant = new javax.swing.JLabel();
        cant.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        cant.setForeground(new java.awt.Color(51, 51, 51));
        cant.setText(canti);
        cant.addMouseListener(this);
        cant.addPropertyChangeListener(this);
        cant.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cant.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
        cant.setPreferredSize(new java.awt.Dimension(150, 20));
        cantidad.push(cant);
        jpanel.add(cantidad.get(cont));
        
        //------------------------------PRECIO----------------------------------
        JLabel preci = new javax.swing.JLabel();
        preci.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        preci.setForeground(new java.awt.Color(51, 51, 51));
        preci.setText(prec);
        preci.addMouseListener(this);
        preci.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        preci.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
        preci.setPreferredSize(new java.awt.Dimension(150, 20));
        precio.push(preci);
        jpanel.add(precio.get(cont));
        
        //------------------------------Subtotal----------------------------------
        double cantidadD, precioD;
        try{cantidadD = Double.parseDouble(canti);}catch(Exception e){cantidadD = 0;}
        try{precioD = Double.parseDouble(prec);}catch(Exception e){precioD = 0;}
        
        double subtot = cantidadD * precioD;
        
        JLabel sub = new javax.swing.JLabel();
        sub.setFont(new java.awt.Font("Roboto", Font.BOLD, 16)); // NOI18N
        sub.setForeground(new java.awt.Color(51, 51, 51));
        sub.setText(String.valueOf(subtot));
        sub.addMouseListener(this);
        sub.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sub.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
        sub.setPreferredSize(new java.awt.Dimension(150, 20));
        subtotal.push(sub);
        jpanel.add(subtotal.get(cont));
        
        panel.push(jpanel);
        panelPrincipal.add(panel.get(cont), gridBagConstraints);
        revalidate();
        repaint();
        cont++;
    }
    
    public int verificarExistencia(String codigo){
        int ubicacion = -1;
        for (int i = 0; i < parte.size(); i++) {
            if(parte.get(i).getText().equals(codigo)){
                if(panel.get(i).isVisible()){
                    return i;
                }
            }
        }
        return ubicacion;
    }
    
    public void agregarParte(){
        if(txtParte.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Debes agregar un numero de parte","Error",JOptionPane.ERROR_MESSAGE);
        }else{
            try{
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql = "select Codigo, Descripcion, Precio from requisiciones where Codigo like '" + txtParte.getText() + "' and Precio is not null and Precio != '' and Precio != '0'";
                ResultSet rs = st.executeQuery(sql);
                String desc = null;
                String codigo = null;
                String prec = null;
                while(rs.next()){
                    desc = rs.getString("Descripcion");
                    codigo = rs.getString("Codigo");
                    prec = rs.getString("Precio");
                }
                if(codigo == null){
                    JOptionPane.showMessageDialog(this, "No se encontraron datos para este numero de parte","Advertencia",JOptionPane.WARNING_MESSAGE);
                    txtParte.setText("");
                }else{
                    int b = verificarExistencia(codigo);
                    if(b != -1){
                        String c = cantidad.get(b).getText();
                        double cant;
                        try{cant = Double.parseDouble(c);}catch(Exception e){cant = 0;}
                        cantidad.get(b).setText(String.valueOf(cant + 1));
                        txtParte.setText("");
                    }else{
                        crearPanel(codigo, desc, "1", prec);
                        txtParte.setText("");
                    }
                }
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void calcularTotal(){
        double total = 0;
        for (int i = 0; i < subtotal.size(); i++) {
            if(panel.get(i).isVisible()){
                double sub;
                try{sub = Double.parseDouble(subtotal.get(i).getText().replaceAll(",", ""));}catch(Exception e){sub = 0;}
                total += sub;
            }
        }
        DecimalFormat formato = new DecimalFormat("#,###.##");
        txtTotal.setText(formato.format(total));
    }
    
    public Costeo(String numEmpleado) {
        initComponents();
        this.numEmpleado = numEmpleado;
        scrollPrincipal.getVerticalScrollBar().setUnitIncrement(15);
        panel = new Stack<>();
        parte = new Stack<>();
        cantidad = new Stack<>();
        precio = new Stack<>();
        descripcion = new Stack<>();
        subtotal = new Stack<>();
        agregarPartes();
        panelPrincipal.removeAll();
        revalidate();
        repaint();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        pan = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtParte = new javax.swing.JTextField();
        panelRound2 = new scrollPane.PanelRound();
        jButton3 = new javax.swing.JButton();
        scrollPrincipal = new javax.swing.JScrollPane();
        panelPrincipal = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        panelRound1 = new scrollPane.PanelRound();
        jButton1 = new javax.swing.JButton();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 50, 5));

        jLabel12.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 165, 252));
        jLabel12.setText("Costeo de partes");
        jPanel5.add(jLabel12);

        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        pan.setBackground(new java.awt.Color(255, 255, 255));

        panelSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblSalir.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblSalir.setForeground(new java.awt.Color(0, 0, 0));
        lblSalir.setText(" X ");
        lblSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSalirMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSalirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSalirMouseExited(evt);
            }
        });
        panelSalir.add(lblSalir);

        pan.add(panelSalir);

        jPanel4.add(pan, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        txtParte.setBackground(new java.awt.Color(255, 255, 255));
        txtParte.setFont(new java.awt.Font("Lexend", 0, 18)); // NOI18N
        txtParte.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtParte.setPreferredSize(new java.awt.Dimension(400, 40));
        txtParte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtParteActionPerformed(evt);
            }
        });
        jPanel3.add(txtParte);

        panelRound2.setBackground(new java.awt.Color(204, 204, 255));
        panelRound2.setRoundBottomLeft(20);
        panelRound2.setRoundBottomRight(20);
        panelRound2.setRoundTopLeft(20);

        jButton3.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(51, 51, 51));
        jButton3.setText("Agregar");
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setFocusPainted(false);
        jButton3.setPreferredSize(new java.awt.Dimension(100, 25));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        panelRound2.add(jButton3);

        jPanel3.add(panelRound2);

        jPanel2.add(jPanel3, java.awt.BorderLayout.NORTH);

        scrollPrincipal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        panelPrincipal.setBackground(new java.awt.Color(250, 250, 250));
        panelPrincipal.setLayout(new java.awt.GridBagLayout());

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 153), 4, true));

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Numero de parte");
        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
        jLabel4.setPreferredSize(new java.awt.Dimension(300, 20));
        jPanel9.add(jLabel4);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(350, 74));

        jTextArea2.setEditable(false);
        jTextArea2.setBackground(new java.awt.Color(255, 255, 255));
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jTextArea2.setWrapStyleWord(true);
        jTextArea2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jScrollPane2.setViewportView(jTextArea2);

        jPanel9.add(jScrollPane2);

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Cantidad");
        jLabel5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
        jLabel5.setPreferredSize(new java.awt.Dimension(150, 20));
        jLabel5.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jLabel5PropertyChange(evt);
            }
        });
        jPanel9.add(jLabel5);

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Precio");
        jLabel6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
        jLabel6.setPreferredSize(new java.awt.Dimension(150, 20));
        jPanel9.add(jLabel6);

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("subtotal");
        jLabel7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
        jLabel7.setPreferredSize(new java.awt.Dimension(150, 20));
        jPanel9.add(jLabel7);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 5, 12);
        panelPrincipal.add(jPanel9, gridBagConstraints);

        scrollPrincipal.setViewportView(panelPrincipal);

        jPanel2.add(scrollPrincipal, java.awt.BorderLayout.CENTER);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel1.setText("Total: ");
        jPanel7.add(jLabel1);

        txtTotal.setEditable(false);
        txtTotal.setBackground(new java.awt.Color(255, 255, 255));
        txtTotal.setFont(new java.awt.Font("Lexend", 1, 24)); // NOI18N
        txtTotal.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(153, 153, 153)));
        txtTotal.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel7.add(txtTotal);

        jPanel2.add(jPanel7, java.awt.BorderLayout.PAGE_END);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 50, 5));

        panelRound1.setBackground(new java.awt.Color(204, 0, 0));
        panelRound1.setRoundBottomLeft(20);
        panelRound1.setRoundBottomRight(20);
        panelRound1.setRoundTopLeft(20);

        jButton1.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pdf_1.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setFocusPainted(false);
        jButton1.setPreferredSize(new java.awt.Dimension(100, 25));
        panelRound1.add(jButton1);

        jPanel6.add(panelRound1);

        jPanel1.add(jPanel6, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        panelSalir.setBackground(Color.red);
        lblSalir.setForeground(Color.white);
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        panelSalir.setBackground(Color.white);
        lblSalir.setForeground(Color.black);
    }//GEN-LAST:event_lblSalirMouseExited

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        agregarParte();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtParteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtParteActionPerformed
        agregarParte();
    }//GEN-LAST:event_txtParteActionPerformed

    private void jLabel5PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jLabel5PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel5PropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelPrincipal;
    private scrollPane.PanelRound panelRound1;
    private scrollPane.PanelRound panelRound2;
    private javax.swing.JPanel panelSalir;
    private javax.swing.JScrollPane scrollPrincipal;
    private javax.swing.JTextField txtParte;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < parte.size(); i++) {
            if(e.getSource() == parte.get(i) || e.getSource() == descripcion.get(i) || e.getSource() == cantidad.get(i)
                     || e.getSource() == precio.get(i) || e.getSource() == subtotal.get(i) || e.getSource() == panel.get(i)){
                opc = new opciones();
                opc.setLocation(e.getXOnScreen(), e.getYOnScreen());
                opc.btnAgregar.addActionListener(this);
                opc.btnEliminar.addActionListener(this);
                parteSeleccionada = i;
                opc.setVisible(true);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        for (int i = 0; i < parte.size(); i++) {
            if(e.getSource() == parte.get(i) || e.getSource() == descripcion.get(i) || e.getSource() == cantidad.get(i)
                     || e.getSource() == precio.get(i) || e.getSource() == subtotal.get(i) || e.getSource() == panel.get(i)){
                panel.get(i).setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0,102,153), 4, true));
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        for (int i = 0; i < parte.size(); i++) {
            if(e.getSource() == parte.get(i) || e.getSource() == descripcion.get(i) || e.getSource() == cantidad.get(i)
                     || e.getSource() == precio.get(i) || e.getSource() == subtotal.get(i) || e.getSource() == panel.get(i)){
                panel.get(i).setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255,255, 255), 4, true));
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(opc != null){
            if(e.getSource() == opc.btnAgregar){
                String cant = JOptionPane.showInputDialog("Introduce cantidad");
                try{
                    double c = Double.parseDouble(cant);
                    cantidad.get(parteSeleccionada).setText(cant);
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error al introducir cantidad","Error",JOptionPane.ERROR_MESSAGE);
                }
            }else if(e.getSource() == opc.btnEliminar){
                panel.get(parteSeleccionada).setVisible(false);
//                panel.remove(panel.get(parteSeleccionada));
                calcularTotal();
                revalidate();
                repaint();
                
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(!cantidad.isEmpty()){
            for (int i = 0; i < parte.size(); i++) {
                try{
                    if(evt.getSource() == cantidad.get(i)){
                        double cant;
                        double prec;
                        try{cant = Double.parseDouble(this.cantidad.get(i).getText().replaceAll(",", ""));}catch(Exception e){cant = 0;}
                        try{prec = Double.parseDouble(this.precio.get(i).getText().replaceAll(",", ""));}catch(Exception e){prec = 0;}
                        double total = cant * prec;
                        DecimalFormat formato = new DecimalFormat("#,###.##");
    //                        subtotal.get(i).setText(String.valueOf(formato.format(total)));
                        subtotal.get(i).setText(formato.format((total)));
                        calcularTotal();
                    }
                }catch(Exception e){}
            }
        }
    }
}
