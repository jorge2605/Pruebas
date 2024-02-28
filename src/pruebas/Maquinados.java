package pruebas;

import Conexiones.Conexion;
import VentanaEmergente.Maquinados.empleado;
import com.mxrck.autocompleter.TextAutoCompleter;
import com.mysql.jdbc.Connection;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Maquinados extends javax.swing.JInternalFrame implements ActionListener {

    public String numEmpleado;
    public Stack<JButton> btnEmpleado;
    public Stack<JPanel> pnlEmpleado;
    public Stack<String> numEmpleados;
    public Stack<String> nombreEmpleados;
    public int PROYECTO = 1;
    public int PLANO = 2;
    
    public void limpiarFormulario(){
        txtPlano2.setText("");
        txtProyecto.setText("");
        txtDimensiones.setText("");
        txtMaterial.setText("");
        txtCantidad.setText("");
        txtComentarios.setText("");
        txtTiempo.setText("");
        txtTiempo2.setText("");
        pnlCnc.setBackground(Color.white);
        pnlFresa.setBackground(Color.white);
        pnlTorno.setBackground(Color.white);
        pnlRecti.setBackground(Color.white);
    }
    
    public JButton addBoton(String nombre){
        JButton boton = new javax.swing.JButton();
        boton.setBackground(new java.awt.Color(255, 255, 255));
        boton.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        boton.setForeground(new java.awt.Color(0, 102, 204));
        boton.setText(nombre);
        boton.setBorder(null);
        boton.addActionListener(this);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        boton.setFocusPainted(false);
        return boton;
    }
    
    public void addPanel(JButton boton, int i){
        pnlEmpleado.push(new JPanel());
        pnlEmpleado.get(i).setBackground(new java.awt.Color(240, 240, 240));
        pnlEmpleado.get(i).add(boton);
        panelGastos.add(pnlEmpleado.get(i));
    }
    
    public void addEmpleados(String empleado){
        try{
            panelGastos.removeAll();
            revalidate();
            repaint();
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM towi.empleadoscheck where NumEmpleado like '" + empleado + "'";
            ResultSet rs = st.executeQuery(sql);
            numEmpleados = new Stack<>();
            nombreEmpleados = new Stack<>();
            while(rs.next()){
                String nombre = rs.getString("Nombre");
                String numero = rs.getString("NumEmpleado");
                numEmpleados.push(numero);
                nombreEmpleados.push(nombre);
            }
            if(numEmpleados.isEmpty()){
                JOptionPane.showMessageDialog(this, "El numero que ingresaste no existe","Error",JOptionPane.ERROR_MESSAGE);
                getNumEmpleado();
            }else{
                btnEmpleado = new Stack<>();
                pnlEmpleado = new Stack<>();
                for (int i = 0; i < numEmpleados.size(); i++) {
                    btnEmpleado.push(addBoton(nombreEmpleados.get(i)));
                    addPanel(btnEmpleado.get(i), i);
                }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String getNumEmpleado(){
        String empleado;
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        empleado emp = new empleado(f,true);
        empleado = emp.getEmpleado();
        addEmpleados(empleado);
        return empleado;
    }
    
    public Color setBack(JComponent comp){
        Color color;
        if(comp.getBackground().equals(Color.white)){
            color = (Color.green);
        }else{
            color = (Color.white);
        }
        return color;
    }
    
    public String verificarNomenclatura(String plano, int seleccion){
        String proyecto = plano.substring(0, plano.indexOf(" "));
        String plano3 = plano.substring(plano.indexOf(" "),plano.length());
        if(proyecto.length() >= 3){
            try{
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st =con.createStatement();
                String sql = "select Proyecto from proyectos where Proyecto like '" + proyecto + "%'";
                ResultSet rs = st.executeQuery(sql);
                while(rs.next()){
                    proyecto = rs.getString("Proyecto");
                }
                if(proyecto == null){
                    return null;
                }else{
                    if(seleccion == PROYECTO){
                        return proyecto;
                    }else{
                        return proyecto + plano3;
                    }
                }
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "ERROR "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Datos no validos [0,3]","Error",JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
    public void verPlanoPorProyecto(String plano){
        String proyecto = verificarNomenclatura(plano, PROYECTO);
        if(proyecto.length() < 6){
            JOptionPane.showMessageDialog(this, "Este Proyecto no existe","Error",JOptionPane.ERROR_MESSAGE);
            limpiarFormulario();
        }else{
            txtProyecto.setText(proyecto);
            txtPlano2.setText(plano);
        }
    }
    
    public void verPlano(String plano){
        String plan = plano;
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Plano, Proyecto, Cantidad, Material from planos where Plano like '" + plano + "'";
            ResultSet rs = st.executeQuery(sql);
            plano = null;
            while(rs.next()){
                plano = rs.getString("Plano");
                txtPlano2.setText(plano);
                txtProyecto.setText(rs.getString("Proyecto"));
                txtCantidad.setText(rs.getString("Cantidad"));
                txtMaterial.setText(rs.getString("Material"));
            }
            if(plano == null){
                JOptionPane.showMessageDialog(this, "Este plano no existe","Advertencia",JOptionPane.WARNING_MESSAGE);
                verPlanoPorProyecto(plan);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public Maquinados(String numEmpleado) {
        initComponents();
        this.numEmpleado = numEmpleado;
        jScrollPane5.getVerticalScrollBar().setUnitIncrement(15);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
//        String empleado = getNumEmpleado();
        
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
        jScrollPane5 = new javax.swing.JScrollPane();
        panelGastos = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        pnlGuardar = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtPlano = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtPlano2 = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtProyecto = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtDimensiones = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtMaterial = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtComentarios = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        pnlFresa = new javax.swing.JPanel();
        btnFresa = new javax.swing.JButton();
        pnlCnc = new javax.swing.JPanel();
        btnCnc = new javax.swing.JButton();
        pnlTorno = new javax.swing.JPanel();
        btnTorno = new javax.swing.JButton();
        pnlRecti = new javax.swing.JPanel();
        btnRecti = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        txtTiempo = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTiempo2 = new javax.swing.JTextField();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 50, 5));

        jLabel12.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 165, 252));
        jLabel12.setText("Maquinados Reporte de produccion");
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
        jPanel2.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jScrollPane5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jScrollPane5.setMaximumSize(new java.awt.Dimension(200, 70));
        jScrollPane5.setPreferredSize(new java.awt.Dimension(102, 65));

        panelGastos.setBackground(new java.awt.Color(255, 255, 255));
        panelGastos.setMaximumSize(new java.awt.Dimension(150, 100));
        panelGastos.setPreferredSize(new java.awt.Dimension(100, 60));
        jScrollPane5.setViewportView(panelGastos);

        jPanel3.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel2.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Fecha:");
        jPanel7.add(jLabel2);

        jLabel3.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("14-02-2024");
        jPanel7.add(jLabel3);

        jPanel3.add(jPanel7, java.awt.BorderLayout.EAST);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        pnlGuardar.setBackground(new java.awt.Color(255, 255, 255));

        btnGuardar.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar.setFont(new java.awt.Font("Lexend", 1, 24)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(0, 102, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(null);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setPreferredSize(new java.awt.Dimension(180, 40));
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarMouseExited(evt);
            }
        });
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        pnlGuardar.add(btnGuardar);

        jPanel8.add(pnlGuardar);

        jPanel2.add(jPanel8, java.awt.BorderLayout.SOUTH);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Ingresa numero de Plano:");
        jPanel6.add(jLabel1);

        txtPlano.setBackground(new java.awt.Color(255, 255, 255));
        txtPlano.setFont(new java.awt.Font("Lexend", 0, 18)); // NOI18N
        txtPlano.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPlano.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
        txtPlano.setNextFocusableComponent(txtDimensiones);
        txtPlano.setPreferredSize(new java.awt.Dimension(400, 30));
        txtPlano.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPlanoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPlanoFocusLost(evt);
            }
        });
        txtPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlanoActionPerformed(evt);
            }
        });
        jPanel6.add(txtPlano);

        jPanel9.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.GridBagLayout());

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setPreferredSize(new java.awt.Dimension(500, 30));
        jPanel12.setLayout(new java.awt.BorderLayout(20, 10));

        jLabel4.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 204, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("<html> <span style='color: rgb(240,0,0)'>* </span> <span>Plano:  </span>");
        jPanel12.add(jLabel4, java.awt.BorderLayout.WEST);

        txtPlano2.setEditable(false);
        txtPlano2.setBackground(new java.awt.Color(255, 255, 255));
        txtPlano2.setFont(new java.awt.Font("Lexend", 0, 18)); // NOI18N
        txtPlano2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPlano2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtPlano2.setEnabled(false);
        txtPlano2.setPreferredSize(new java.awt.Dimension(300, 30));
        jPanel12.add(txtPlano2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel11.add(jPanel12, gridBagConstraints);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setPreferredSize(new java.awt.Dimension(500, 30));
        jPanel13.setLayout(new java.awt.BorderLayout(20, 10));

        jLabel5.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 204, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("<html> <span style='color: rgb(240,0,0)'>* </span> <span>Proyecto:                </span>");
        jPanel13.add(jLabel5, java.awt.BorderLayout.WEST);

        txtProyecto.setEditable(false);
        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setFont(new java.awt.Font("Lexend", 0, 18)); // NOI18N
        txtProyecto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtProyecto.setEnabled(false);
        txtProyecto.setPreferredSize(new java.awt.Dimension(300, 30));
        jPanel13.add(txtProyecto, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel11.add(jPanel13, gridBagConstraints);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setPreferredSize(new java.awt.Dimension(500, 30));
        jPanel14.setLayout(new java.awt.BorderLayout(20, 10));

        jLabel6.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 204, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Dimensiones:");
        jPanel14.add(jLabel6, java.awt.BorderLayout.WEST);

        txtDimensiones.setBackground(new java.awt.Color(255, 255, 255));
        txtDimensiones.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtDimensiones.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDimensiones.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtDimensiones.setNextFocusableComponent(txtMaterial);
        txtDimensiones.setPreferredSize(new java.awt.Dimension(300, 30));
        txtDimensiones.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDimensionesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDimensionesFocusLost(evt);
            }
        });
        jPanel14.add(txtDimensiones, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel11.add(jPanel14, gridBagConstraints);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setPreferredSize(new java.awt.Dimension(500, 30));
        jPanel15.setLayout(new java.awt.BorderLayout(20, 10));

        jLabel7.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 204, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Material:     ");
        jPanel15.add(jLabel7, java.awt.BorderLayout.WEST);

        txtMaterial.setBackground(new java.awt.Color(255, 255, 255));
        txtMaterial.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtMaterial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMaterial.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtMaterial.setNextFocusableComponent(txtCantidad);
        txtMaterial.setPreferredSize(new java.awt.Dimension(300, 30));
        txtMaterial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMaterialFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaterialFocusLost(evt);
            }
        });
        jPanel15.add(txtMaterial, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel11.add(jPanel15, gridBagConstraints);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setPreferredSize(new java.awt.Dimension(500, 30));
        jPanel16.setLayout(new java.awt.BorderLayout(20, 10));

        jLabel8.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(153, 204, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("<html>\n<span style='color: rgb(240,0,0)'>*</span>\n<span>Cantidad:              </span>");
        jPanel16.add(jLabel8, java.awt.BorderLayout.WEST);

        txtCantidad.setBackground(new java.awt.Color(255, 255, 255));
        txtCantidad.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtCantidad.setNextFocusableComponent(txtComentarios);
        txtCantidad.setPreferredSize(new java.awt.Dimension(300, 30));
        txtCantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCantidadFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCantidadFocusLost(evt);
            }
        });
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });
        jPanel16.add(txtCantidad, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel11.add(jPanel16, gridBagConstraints);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setPreferredSize(new java.awt.Dimension(500, 30));
        jPanel17.setLayout(new java.awt.BorderLayout(20, 10));

        jLabel9.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 204, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Comentarios:         ");
        jPanel17.add(jLabel9, java.awt.BorderLayout.WEST);

        txtComentarios.setBackground(new java.awt.Color(255, 255, 255));
        txtComentarios.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtComentarios.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtComentarios.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtComentarios.setNextFocusableComponent(txtTiempo);
        txtComentarios.setPreferredSize(new java.awt.Dimension(300, 30));
        txtComentarios.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtComentariosFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtComentariosFocusLost(evt);
            }
        });
        jPanel17.add(txtComentarios, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel11.add(jPanel17, gridBagConstraints);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setPreferredSize(new java.awt.Dimension(500, 35));
        jPanel18.setLayout(new java.awt.BorderLayout(20, 10));

        jLabel10.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(153, 204, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Maquina:");
        jPanel18.add(jLabel10, java.awt.BorderLayout.WEST);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setPreferredSize(new java.awt.Dimension(414, 40));

        pnlFresa.setBackground(new java.awt.Color(255, 255, 255));

        btnFresa.setBackground(new java.awt.Color(255, 255, 255));
        btnFresa.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        btnFresa.setText("Fresadora");
        btnFresa.setBorder(null);
        btnFresa.setBorderPainted(false);
        btnFresa.setContentAreaFilled(false);
        btnFresa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFresa.setFocusPainted(false);
        btnFresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFresaActionPerformed(evt);
            }
        });
        pnlFresa.add(btnFresa);

        jPanel19.add(pnlFresa);

        pnlCnc.setBackground(new java.awt.Color(255, 255, 255));

        btnCnc.setBackground(new java.awt.Color(255, 255, 255));
        btnCnc.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        btnCnc.setText("Cnc");
        btnCnc.setBorder(null);
        btnCnc.setBorderPainted(false);
        btnCnc.setContentAreaFilled(false);
        btnCnc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCnc.setFocusPainted(false);
        btnCnc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCncActionPerformed(evt);
            }
        });
        pnlCnc.add(btnCnc);

        jPanel19.add(pnlCnc);

        pnlTorno.setBackground(new java.awt.Color(255, 255, 255));

        btnTorno.setBackground(new java.awt.Color(255, 255, 255));
        btnTorno.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        btnTorno.setText("Torno");
        btnTorno.setBorder(null);
        btnTorno.setBorderPainted(false);
        btnTorno.setContentAreaFilled(false);
        btnTorno.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTorno.setFocusPainted(false);
        btnTorno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTornoActionPerformed(evt);
            }
        });
        pnlTorno.add(btnTorno);

        jPanel19.add(pnlTorno);

        pnlRecti.setBackground(new java.awt.Color(255, 255, 255));

        btnRecti.setBackground(new java.awt.Color(255, 255, 255));
        btnRecti.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        btnRecti.setText("Rectificadora");
        btnRecti.setBorder(null);
        btnRecti.setBorderPainted(false);
        btnRecti.setContentAreaFilled(false);
        btnRecti.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRecti.setFocusPainted(false);
        btnRecti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRectiActionPerformed(evt);
            }
        });
        pnlRecti.add(btnRecti);

        jPanel19.add(pnlRecti);

        jPanel18.add(jPanel19, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel11.add(jPanel18, gridBagConstraints);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setPreferredSize(new java.awt.Dimension(500, 35));
        jPanel20.setLayout(new java.awt.BorderLayout(20, 10));

        jLabel11.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(153, 204, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Tiempo:");
        jPanel20.add(jLabel11, java.awt.BorderLayout.WEST);

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));

        txtTiempo.setBackground(new java.awt.Color(255, 255, 255));
        txtTiempo.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtTiempo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTiempo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtTiempo.setNextFocusableComponent(txtTiempo2);
        txtTiempo.setPreferredSize(new java.awt.Dimension(100, 30));
        txtTiempo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTiempoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTiempoFocusLost(evt);
            }
        });
        txtTiempo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTiempoKeyTyped(evt);
            }
        });
        jPanel21.add(txtTiempo);

        jLabel13.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel13.setText(":");
        jPanel21.add(jLabel13);

        txtTiempo2.setBackground(new java.awt.Color(255, 255, 255));
        txtTiempo2.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtTiempo2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTiempo2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtTiempo2.setNextFocusableComponent(txtPlano);
        txtTiempo2.setPreferredSize(new java.awt.Dimension(100, 30));
        txtTiempo2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTiempo2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTiempo2FocusLost(evt);
            }
        });
        txtTiempo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTiempo2KeyTyped(evt);
            }
        });
        jPanel21.add(txtTiempo2);

        jPanel20.add(jPanel21, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel11.add(jPanel20, gridBagConstraints);

        jPanel9.add(jPanel11, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

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

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        this.numEmpleado = getNumEmpleado();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnFresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFresaActionPerformed
        pnlFresa.setBackground(setBack(pnlFresa));
    }//GEN-LAST:event_btnFresaActionPerformed

    private void btnCncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCncActionPerformed
        pnlCnc.setBackground(setBack(pnlCnc));
    }//GEN-LAST:event_btnCncActionPerformed

    private void btnTornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTornoActionPerformed
        pnlTorno.setBackground(setBack(pnlTorno));
    }//GEN-LAST:event_btnTornoActionPerformed

    private void btnRectiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRectiActionPerformed
        pnlRecti.setBackground(setBack(pnlRecti));
    }//GEN-LAST:event_btnRectiActionPerformed

    private void txtPlanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlanoActionPerformed
        limpiarFormulario();
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Plano, Proyecto, Cantidad, Material from planos where Plano like '" + txtPlano.getText() + "'";
            ResultSet rs = st.executeQuery(sql);
            String plano = null;
            while(rs.next()){
                plano = rs.getString("Plano");
                txtPlano2.setText(plano);
                txtProyecto.setText(rs.getString("Proyecto"));
                txtCantidad.setText(rs.getString("Cantidad"));
                txtMaterial.setText(rs.getString("Material"));
            }
            if(plano == null){
                plano = verificarNomenclatura(txtPlano.getText(),PLANO );
                verPlano(plano);
            }
            txtPlano.setText("");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtPlanoActionPerformed

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtTiempoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTiempoKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTiempoKeyTyped

    private void txtTiempo2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTiempo2KeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTiempo2KeyTyped

    private void txtPlanoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPlanoFocusGained
        txtPlano.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 102, 204)));
    }//GEN-LAST:event_txtPlanoFocusGained

    private void txtPlanoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPlanoFocusLost
        txtPlano.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
    }//GEN-LAST:event_txtPlanoFocusLost

    private void txtDimensionesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDimensionesFocusGained
        txtDimensiones.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 102, 204)));
    }//GEN-LAST:event_txtDimensionesFocusGained

    private void txtDimensionesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDimensionesFocusLost
        txtDimensiones.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
    }//GEN-LAST:event_txtDimensionesFocusLost

    private void txtMaterialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaterialFocusGained
        txtMaterial.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 102, 204)));
    }//GEN-LAST:event_txtMaterialFocusGained

    private void txtMaterialFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaterialFocusLost
        txtMaterial.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
    }//GEN-LAST:event_txtMaterialFocusLost

    private void txtCantidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadFocusGained
        txtCantidad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 102, 204)));
    }//GEN-LAST:event_txtCantidadFocusGained

    private void txtCantidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadFocusLost
        txtCantidad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
    }//GEN-LAST:event_txtCantidadFocusLost

    private void txtComentariosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComentariosFocusGained
        txtComentarios.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 102, 204)));
    }//GEN-LAST:event_txtComentariosFocusGained

    private void txtComentariosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComentariosFocusLost
        txtComentarios.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
    }//GEN-LAST:event_txtComentariosFocusLost

    private void txtTiempoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTiempoFocusGained
        txtTiempo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 102, 204)));
    }//GEN-LAST:event_txtTiempoFocusGained

    private void txtTiempoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTiempoFocusLost
        txtTiempo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
    }//GEN-LAST:event_txtTiempoFocusLost

    private void txtTiempo2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTiempo2FocusGained
        txtTiempo2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 102, 204)));
    }//GEN-LAST:event_txtTiempo2FocusGained

    private void txtTiempo2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTiempo2FocusLost
        txtTiempo2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
    }//GEN-LAST:event_txtTiempo2FocusLost

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        pnlGuardar.setBackground(new Color(0,102,255));
        btnGuardar.setForeground(Color.white);
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        pnlGuardar.setBackground(Color.white);
        btnGuardar.setForeground(new Color(0,102,255));
    }//GEN-LAST:event_btnGuardarMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCnc;
    private javax.swing.JButton btnFresa;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnRecti;
    private javax.swing.JButton btnTorno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelGastos;
    private javax.swing.JPanel panelSalir;
    private javax.swing.JPanel pnlCnc;
    private javax.swing.JPanel pnlFresa;
    private javax.swing.JPanel pnlGuardar;
    private javax.swing.JPanel pnlRecti;
    private javax.swing.JPanel pnlTorno;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtComentarios;
    private javax.swing.JTextField txtDimensiones;
    private javax.swing.JTextField txtMaterial;
    private javax.swing.JTextField txtPlano;
    private javax.swing.JTextField txtPlano2;
    private javax.swing.JTextField txtProyecto;
    private javax.swing.JTextField txtTiempo;
    private javax.swing.JTextField txtTiempo2;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < btnEmpleado.size(); i++) {
            if(e.getSource() == btnEmpleado.get(i)){
                String empleado = getNumEmpleado();
            }
        }
    }
}
