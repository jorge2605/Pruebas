package VentanaEmergente.Calendario;

import Conexiones.Conexion;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class AgregarFechas extends java.awt.Dialog {

    String numEmpleado;
    TextAutoCompleter au;
    Stack<String> proyectos;
    
    public final void agregarProyecto(){
        try{
            au = new TextAutoCompleter(txtProyecto);
            proyectos = new Stack<>();
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Proyecto from Proyectos";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                String proyecto = rs.getString("Proyecto");
                au.addItem(proyecto);
                proyectos.push(proyecto);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: " + e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void setEmpleado(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        txtFecha.setText(sdf.format(d));
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroempleados where NumEmpleado like '" + numEmpleado + "'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                txtEmpleado.setText(rs.getString("Nombre") + " " + rs.getString("Apellido"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: " + e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void setFechas(int x, int y, JLabel desde, JLabel hasta){
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        SeleccionarFecha sel = new SeleccionarFecha(f, true);
        sel.setLocation(x, y);
        if((x + sel.getWidth()) > f.getWidth()){
            sel.setLocation(x - sel.getWidth(), y);
        }
        Date date[] = sel.getFechas();
        if(date != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            desde.setText(sdf.format(date[0]));
            hasta.setText(sdf.format(date[1]));
        }
    }
    
    public void limpiarForm(){
        txtProyecto.setText("");
        txtDescripcion.setText("");
        FechaFin.setDate(null);
        FechaInicio.setDate(null);
        cmbEstatus.setSelectedIndex(0);
        cmbDepartamento.setSelectedIndex(0);
    }
    
    public AgregarFechas(java.awt.Frame parent, boolean modal,String numEmpleado) {
        super(parent, modal);
        initComponents();
        this.numEmpleado = numEmpleado;
        agregarProyecto();
        setEmpleado();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtEmpleado = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        FechaInicio = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        FechaFin = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        cmbEstatus = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        color = new javax.swing.JColorChooser();
        jButton1 = new javax.swing.JButton();
        cmbDepartamento = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtProyecto = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        panelRound1 = new scrollPane.PanelRound();
        jPanel5 = new javax.swing.JPanel();
        btnDiseno = new javax.swing.JButton();
        lblDesdeD = new javax.swing.JLabel();
        lblHastaD = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnHerramentista = new javax.swing.JButton();
        lblDesdeH = new javax.swing.JLabel();
        lblHastaH = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btnIntegracion = new javax.swing.JButton();
        lblDesdeI = new javax.swing.JLabel();
        lblHastaI = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnCompras = new javax.swing.JButton();
        lblDesdeC = new javax.swing.JLabel();
        lblHastaC = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(759, 800));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Agregar fechas");
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWeights = new double[] {0.0, 1.0, 0.0, 1.0};
        jPanel2Layout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
        jPanel2.setLayout(jPanel2Layout);

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Empleado Creador:");
        jLabel7.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 2, 6);
        jPanel2.add(jLabel7, gridBagConstraints);

        txtEmpleado.setEditable(false);
        txtEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        txtEmpleado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtEmpleado.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 10);
        jPanel2.add(txtEmpleado, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Fecha:");
        jLabel8.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 2, 6);
        jPanel2.add(jLabel8, gridBagConstraints);

        txtFecha.setEditable(false);
        txtFecha.setBackground(new java.awt.Color(255, 255, 255));
        txtFecha.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtFecha.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 10);
        jPanel2.add(txtFecha, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Desde: ");
        jLabel4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 2, 6);
        jPanel2.add(jLabel4, gridBagConstraints);

        FechaInicio.setBackground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 10);
        jPanel2.add(FechaInicio, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Hasta: ");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 2, 6);
        jPanel2.add(jLabel5, gridBagConstraints);

        FechaFin.setBackground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 10);
        jPanel2.add(FechaFin, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Estatus:");
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 2, 6);
        jPanel2.add(jLabel6, gridBagConstraints);

        cmbEstatus.setBackground(new java.awt.Color(255, 255, 255));
        cmbEstatus.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cmbEstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nuevo", "Cancelado", "Terminado" }));
        cmbEstatus.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 10);
        jPanel2.add(cmbEstatus, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Departamento:");
        jLabel9.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 2, 6);
        jPanel2.add(jLabel9, gridBagConstraints);

        color.setBackground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 4;
        jPanel2.add(color, gridBagConstraints);

        jButton1.setBackground(new java.awt.Color(0, 102, 204));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 5;
        jPanel2.add(jButton1, gridBagConstraints);

        cmbDepartamento.setBackground(new java.awt.Color(255, 255, 255));
        cmbDepartamento.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cmbDepartamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "DISEÑO", "HERRAMENTISTA", "COMPRAS", "INTEGRACION" }));
        cmbDepartamento.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 10);
        jPanel2.add(cmbDepartamento, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Proyecto:");
        jLabel10.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 2, 6);
        jPanel2.add(jLabel10, gridBagConstraints);

        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 10);
        jPanel2.add(txtProyecto, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Descripcion:");
        jLabel11.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 2, 6);
        jPanel2.add(jLabel11, gridBagConstraints);

        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));

        txtDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setColumns(20);
        txtDescripcion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setRows(5);
        txtDescripcion.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtDescripcion);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 10);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        panelRound1.setBackground(new java.awt.Color(240, 240, 240));
        panelRound1.setRoundBottomLeft(30);
        panelRound1.setRoundBottomRight(30);
        panelRound1.setRoundTopLeft(30);
        panelRound1.setRoundTopRight(30);
        panelRound1.setLayout(new java.awt.GridLayout(1, 0));

        jPanel5.setBackground(new java.awt.Color(240, 240, 240));
        java.awt.GridBagLayout jPanel5Layout = new java.awt.GridBagLayout();
        jPanel5Layout.columnWeights = new double[] {1.0, 1.0, 1.0, 1.0};
        jPanel5.setLayout(jPanel5Layout);

        btnDiseno.setBackground(new java.awt.Color(204, 204, 255));
        btnDiseno.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnDiseno.setForeground(new java.awt.Color(255, 255, 255));
        btnDiseno.setText("Diseno");
        btnDiseno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDisenoMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel5.add(btnDiseno, gridBagConstraints);

        lblDesdeD.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblDesdeD.setForeground(new java.awt.Color(51, 51, 51));
        lblDesdeD.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel5.add(lblDesdeD, gridBagConstraints);

        lblHastaD.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblHastaD.setForeground(new java.awt.Color(51, 51, 51));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel5.add(lblHastaD, gridBagConstraints);

        jLabel16.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 102, 102));
        jLabel16.setText("Desde:");
        jLabel16.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel5.add(jLabel16, gridBagConstraints);

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 102, 102));
        jLabel17.setText("Hasta:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel5.add(jLabel17, gridBagConstraints);

        panelRound1.add(jPanel5);

        jPanel4.setBackground(new java.awt.Color(240, 240, 240));
        java.awt.GridBagLayout jPanel4Layout = new java.awt.GridBagLayout();
        jPanel4Layout.columnWeights = new double[] {1.0, 1.0, 1.0, 1.0};
        jPanel4.setLayout(jPanel4Layout);

        btnHerramentista.setBackground(new java.awt.Color(153, 204, 255));
        btnHerramentista.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnHerramentista.setForeground(new java.awt.Color(255, 255, 255));
        btnHerramentista.setText("Herramentista");
        btnHerramentista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHerramentistaMouseClicked(evt);
            }
        });
        btnHerramentista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHerramentistaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel4.add(btnHerramentista, gridBagConstraints);

        lblDesdeH.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblDesdeH.setForeground(new java.awt.Color(51, 51, 51));
        lblDesdeH.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel4.add(lblDesdeH, gridBagConstraints);

        lblHastaH.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblHastaH.setForeground(new java.awt.Color(51, 51, 51));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel4.add(lblHastaH, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setText("Desde:");
        jLabel12.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel4.add(jLabel12, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setText("Hasta:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel4.add(jLabel13, gridBagConstraints);

        panelRound1.add(jPanel4);

        jPanel6.setBackground(new java.awt.Color(240, 240, 240));
        java.awt.GridBagLayout jPanel6Layout = new java.awt.GridBagLayout();
        jPanel6Layout.columnWeights = new double[] {1.0, 1.0, 1.0, 1.0};
        jPanel6.setLayout(jPanel6Layout);

        btnIntegracion.setBackground(new java.awt.Color(153, 153, 255));
        btnIntegracion.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnIntegracion.setForeground(new java.awt.Color(255, 255, 255));
        btnIntegracion.setText("Integracion");
        btnIntegracion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnIntegracionMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel6.add(btnIntegracion, gridBagConstraints);

        lblDesdeI.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblDesdeI.setForeground(new java.awt.Color(51, 51, 51));
        lblDesdeI.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel6.add(lblDesdeI, gridBagConstraints);

        lblHastaI.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblHastaI.setForeground(new java.awt.Color(51, 51, 51));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel6.add(lblHastaI, gridBagConstraints);

        jLabel20.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(102, 102, 102));
        jLabel20.setText("Desde:");
        jLabel20.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel6.add(jLabel20, gridBagConstraints);

        jLabel21.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(102, 102, 102));
        jLabel21.setText("Hasta:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel6.add(jLabel21, gridBagConstraints);

        panelRound1.add(jPanel6);

        jPanel7.setBackground(new java.awt.Color(240, 240, 240));
        java.awt.GridBagLayout jPanel7Layout = new java.awt.GridBagLayout();
        jPanel7Layout.columnWeights = new double[] {1.0, 1.0, 1.0, 1.0};
        jPanel7.setLayout(jPanel7Layout);

        btnCompras.setBackground(new java.awt.Color(255, 102, 102));
        btnCompras.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnCompras.setForeground(new java.awt.Color(255, 255, 255));
        btnCompras.setText("Comrpras");
        btnCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnComprasMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel7.add(btnCompras, gridBagConstraints);

        lblDesdeC.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblDesdeC.setForeground(new java.awt.Color(51, 51, 51));
        lblDesdeC.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel7.add(lblDesdeC, gridBagConstraints);

        lblHastaC.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblHastaC.setForeground(new java.awt.Color(51, 51, 51));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel7.add(lblHastaC, gridBagConstraints);

        jLabel24.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(102, 102, 102));
        jLabel24.setText("Desde:");
        jLabel24.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel7.add(jLabel24, gridBagConstraints);

        jLabel25.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(102, 102, 102));
        jLabel25.setText("Hasta:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel7.add(jLabel25, gridBagConstraints);

        panelRound1.add(jPanel7);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel2.add(panelRound1, gridBagConstraints);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (FechaInicio.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Debes llenar informacion en Fecha Inicio","Advertencia",JOptionPane.WARNING_MESSAGE);
        } else if (proyectos.search(txtProyecto.getText()) == -1) {
            JOptionPane.showMessageDialog(this, "El proyecto que ingresaste no existe","Advertencia",JOptionPane.WARNING_MESSAGE);
        } else if (cmbDepartamento.getSelectedItem().toString().equals("Seleccionar")) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar el departamento al que se asignara la tarea","Advertencia",JOptionPane.WARNING_MESSAGE);
        } else if (txtProyecto.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Debes ingresar un proyecto","Advertencia",JOptionPane.WARNING_MESSAGE);
        } else if (FechaFin.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Debes llenar informacion en Fecha Fin","Advertencia",JOptionPane.WARNING_MESSAGE);
        } else if (FechaFin.getDate().before(FechaInicio.getDate())) {
            JOptionPane.showMessageDialog(this, "La fecha de Inicio debe ser mayor a la fecha Final","Advertencia",JOptionPane.WARNING_MESSAGE);
        } else if (color.getColor().equals(Color.white)) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un color","Advertencia",JOptionPane.WARNING_MESSAGE);
        } else{
            try{
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String sql = "insert into agenda(FechaInicio, FechaFin, Estatus, Departamento, Creador, Fecha, Color, Proyecto, Descripcion) values(?,?,?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, sdf.format(FechaInicio.getDate()));
                pst.setString(2, sdf.format(FechaFin.getDate()));
                pst.setString(3, cmbEstatus.getSelectedItem().toString());
                pst.setString(4, cmbDepartamento.getSelectedItem().toString());
                pst.setString(5, txtEmpleado.getText());
                pst.setString(6, txtFecha.getText());
                pst.setString(7, color.getColor().getRed() + "," + color.getColor().getGreen() + "," + color.getColor().getBlue());
                pst.setString(8, txtProyecto.getText());
                pst.setString(9, txtDescripcion.getText());

                int n = pst.executeUpdate();

                if(n > 0){
                    JOptionPane.showMessageDialog(this, "Datos guardados correctamente");
                    limpiarForm();
                }

            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "Error: " + e,"Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnHerramentistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHerramentistaActionPerformed
        
    }//GEN-LAST:event_btnHerramentistaActionPerformed

    private void btnHerramentistaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHerramentistaMouseClicked
        setFechas(evt.getXOnScreen(), evt.getYOnScreen(), lblDesdeH, lblHastaH);
    }//GEN-LAST:event_btnHerramentistaMouseClicked

    private void btnIntegracionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIntegracionMouseClicked
        setFechas(evt.getXOnScreen(), evt.getYOnScreen(), lblDesdeI, lblHastaI);
    }//GEN-LAST:event_btnIntegracionMouseClicked

    private void btnComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnComprasMouseClicked
        setFechas(evt.getXOnScreen(), evt.getYOnScreen(), lblDesdeC, lblHastaC);
    }//GEN-LAST:event_btnComprasMouseClicked

    private void btnDisenoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDisenoMouseClicked
        setFechas(evt.getXOnScreen(), evt.getYOnScreen(), lblDesdeD, lblHastaD);
    }//GEN-LAST:event_btnDisenoMouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AgregarFechas dialog = new AgregarFechas(new java.awt.Frame(), true,"");
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser FechaFin;
    private com.toedter.calendar.JDateChooser FechaInicio;
    private javax.swing.JButton btnCompras;
    private javax.swing.JButton btnDiseno;
    private javax.swing.JButton btnHerramentista;
    private javax.swing.JButton btnIntegracion;
    private javax.swing.JComboBox<String> cmbDepartamento;
    private javax.swing.JComboBox<String> cmbEstatus;
    private javax.swing.JColorChooser color;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDesdeC;
    private javax.swing.JLabel lblDesdeD;
    private javax.swing.JLabel lblDesdeH;
    private javax.swing.JLabel lblDesdeI;
    private javax.swing.JLabel lblHastaC;
    private javax.swing.JLabel lblHastaD;
    private javax.swing.JLabel lblHastaH;
    private javax.swing.JLabel lblHastaI;
    private scrollPane.PanelRound panelRound1;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtEmpleado;
    private javax.swing.JTextField txtFecha;
    public javax.swing.JTextField txtProyecto;
    // End of variables declaration//GEN-END:variables
}
