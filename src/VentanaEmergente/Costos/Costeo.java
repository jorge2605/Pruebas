package VentanaEmergente.Costos;

import Conexiones.Conexion;
import VentanaEmergente.Diseño.codigoBarras;
import VentanaEmergente.Inicio1.Espera;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import static org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pruebas.CambiarEstado;
import pruebas.Inicio1;

public class Costeo extends javax.swing.JInternalFrame implements MouseListener, ActionListener, PropertyChangeListener{

    public String numEmpleado;
    public Stack<JPanel> panel;
    public Stack<JLabel> parte;
    public Stack<JLabel> cantidad;
    public Stack<JLabel> precio;
    public Stack<JLabel> subtotal;
    public Stack<JTextArea> descripcion;
    public Stack<String> proveedor;
    public Stack<String> proveedores;
    public TextAutoCompleter au;
    int cont = 0;
    public int parteSeleccionada;
    opciones opc;
    Espera espera;
    
    public final void totalProveedores(){
        try{
            proveedores = new Stack<>();
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from clientes";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                proveedores.push(rs.getString("Nombre"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+ e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
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
                String sql = "select Codigo, Descripcion, Precio, Proveedor from requisiciones where Codigo like '" + txtParte.getText() + "' and Precio is not null and Precio != '' and Precio != '0'";
                ResultSet rs = st.executeQuery(sql);
                String desc = null;
                String codigo = null;
                String prec = null;
                String prov = null;
                while(rs.next()){
                    desc = rs.getString("Descripcion");
                    codigo = rs.getString("Codigo");
                    prec = rs.getString("Precio");
                    prov = rs.getString("Proveedor");
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
                        this.proveedor.push(prov);
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
    
    public void setEmpresa(String empresa){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from clientes where Nombre like '"+empresa+"'";
            ResultSet rs = st.executeQuery(sql);
            String nombre = null;
            String contacto = null;
            while(rs.next()){
                nombre = rs.getString("Nombre");
                contacto = rs.getString("Contacto");
            }
            if(nombre != null){
                lblEmpresa.setText("<html>\n" +
                "<style>\n" +
                ".titulo{\n" +
                "font-size: 8px;\n" +
                "font-weight: 700;\n" +
                "padding: 5px;\n" +
                "color: rgb(7, 96, 124);\n" +
                "}\n" +
                "</style>\n" +
                "<div style='width:200px;'>\n" +
                "<div>\n" +
                "<p class = 'titulo'>Empresa :</p>\n" +
                "<p>" + nombre + "</p>\n" +
                "</div>\n" +
                "<div>\n" +
                "<p  class = 'titulo'>Contacto: </p>\n" +
                "<p>" + contacto + "</p>\n" +
                "</div>\n" +
                "</div>");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: "+e,"Error",JOptionPane.ERROR_MESSAGE);
        }
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
        proveedor = new Stack<>();
        agregarPartes();
        panelPrincipal.removeAll();
        totalProveedores();
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
        botonRedondo5 = new scrollPane.BotonRedondo();
        scrollPrincipal = new javax.swing.JScrollPane();
        panelPrincipal = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        lblEmpresa = new javax.swing.JLabel();
        botonRedondo2 = new scrollPane.BotonRedondo();
        botonRedondo3 = new scrollPane.BotonRedondo();
        botonRedondo4 = new scrollPane.BotonRedondo();
        botonRedondo6 = new scrollPane.BotonRedondo();
        botonRedondo7 = new scrollPane.BotonRedondo();

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

        botonRedondo5.setBackground(new java.awt.Color(255, 255, 255));
        botonRedondo5.setForeground(new java.awt.Color(0, 102, 255));
        botonRedondo5.setText("Agregar");
        botonRedondo5.setBorderColor(new java.awt.Color(0, 102, 255));
        botonRedondo5.setBorderColorSelected(new java.awt.Color(0, 51, 153));
        botonRedondo5.setColor(new java.awt.Color(0, 102, 255));
        botonRedondo5.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        botonRedondo5.setPreferredSize(new java.awt.Dimension(100, 35));
        botonRedondo5.setThickness(3);
        botonRedondo5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRedondo5ActionPerformed(evt);
            }
        });
        jPanel3.add(botonRedondo5);

        jPanel2.add(jPanel3, java.awt.BorderLayout.NORTH);

        scrollPrincipal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        panelPrincipal.setBackground(new java.awt.Color(250, 250, 250));
        panelPrincipal.setLayout(new java.awt.GridBagLayout());
        scrollPrincipal.setViewportView(panelPrincipal);

        jPanel2.add(scrollPrincipal, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setText("Total: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel7.add(jLabel1, gridBagConstraints);

        txtTotal.setEditable(false);
        txtTotal.setBackground(new java.awt.Color(255, 255, 255));
        txtTotal.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotal.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(204, 204, 204)));
        txtTotal.setPreferredSize(new java.awt.Dimension(200, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel7.add(txtTotal, gridBagConstraints);

        jPanel6.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.GridBagLayout());

        lblEmpresa.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        lblEmpresa.setForeground(new java.awt.Color(113, 180, 202));
        lblEmpresa.setText("<html>\n<style>\n.titulo{\nfont-size: 8px;\nfont-weight: 700;\npadding: 5px;\ncolor: rgb(7, 96, 124);\n}\n</style>\n<div style='width:200px;'>\n<div>\n<p class = 'titulo'>Empresa :</p>\n<p></p>\n</div>\n<div>\n<p  class = 'titulo'>Contacto: </p>\n<p></p>\n</div>\n</div>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(17, 17, 17, 17);
        jPanel8.add(lblEmpresa, gridBagConstraints);

        botonRedondo2.setBackground(new java.awt.Color(255, 255, 255));
        botonRedondo2.setForeground(new java.awt.Color(153, 153, 255));
        botonRedondo2.setText("Guardar");
        botonRedondo2.setBorderColor(new java.awt.Color(153, 153, 255));
        botonRedondo2.setBorderColorSelected(new java.awt.Color(153, 51, 255));
        botonRedondo2.setColor(new java.awt.Color(153, 153, 255));
        botonRedondo2.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        botonRedondo2.setPreferredSize(new java.awt.Dimension(100, 35));
        botonRedondo2.setThickness(3);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(17, 17, 17, 17);
        jPanel8.add(botonRedondo2, gridBagConstraints);

        botonRedondo3.setBackground(new java.awt.Color(255, 255, 255));
        botonRedondo3.setForeground(new java.awt.Color(51, 204, 0));
        botonRedondo3.setText("Excel");
        botonRedondo3.setBorderColor(new java.awt.Color(51, 204, 0));
        botonRedondo3.setBorderColorSelected(new java.awt.Color(0, 102, 0));
        botonRedondo3.setColor(new java.awt.Color(51, 204, 0));
        botonRedondo3.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        botonRedondo3.setPreferredSize(new java.awt.Dimension(100, 35));
        botonRedondo3.setThickness(3);
        botonRedondo3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRedondo3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(17, 17, 17, 17);
        jPanel8.add(botonRedondo3, gridBagConstraints);

        botonRedondo4.setBackground(new java.awt.Color(255, 255, 255));
        botonRedondo4.setForeground(new java.awt.Color(51, 153, 255));
        botonRedondo4.setText("Abrir");
        botonRedondo4.setBorderColor(new java.awt.Color(51, 153, 255));
        botonRedondo4.setBorderColorSelected(new java.awt.Color(0, 51, 153));
        botonRedondo4.setColor(new java.awt.Color(51, 153, 255));
        botonRedondo4.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        botonRedondo4.setPreferredSize(new java.awt.Dimension(100, 35));
        botonRedondo4.setThickness(3);
        botonRedondo4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRedondo4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(17, 17, 17, 17);
        jPanel8.add(botonRedondo4, gridBagConstraints);

        botonRedondo6.setBackground(new java.awt.Color(255, 255, 255));
        botonRedondo6.setForeground(new java.awt.Color(255, 51, 51));
        botonRedondo6.setText("Agregar Empresa");
        botonRedondo6.setBorderColor(new java.awt.Color(255, 51, 51));
        botonRedondo6.setBorderColorSelected(new java.awt.Color(153, 0, 0));
        botonRedondo6.setColor(new java.awt.Color(255, 51, 51));
        botonRedondo6.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        botonRedondo6.setPreferredSize(new java.awt.Dimension(170, 35));
        botonRedondo6.setThickness(3);
        botonRedondo6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRedondo6ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(17, 17, 17, 17);
        jPanel8.add(botonRedondo6, gridBagConstraints);

        botonRedondo7.setBackground(new java.awt.Color(255, 255, 255));
        botonRedondo7.setForeground(new java.awt.Color(255, 102, 255));
        botonRedondo7.setText("Agregar Cliente");
        botonRedondo7.setBorderColor(new java.awt.Color(255, 102, 255));
        botonRedondo7.setBorderColorSelected(new java.awt.Color(204, 0, 204));
        botonRedondo7.setColor(new java.awt.Color(255, 102, 255));
        botonRedondo7.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        botonRedondo7.setPreferredSize(new java.awt.Dimension(170, 35));
        botonRedondo7.setThickness(3);
        botonRedondo7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRedondo7ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(17, 17, 17, 17);
        jPanel8.add(botonRedondo7, gridBagConstraints);

        jPanel6.add(jPanel8, java.awt.BorderLayout.SOUTH);

        jPanel1.add(jPanel6, java.awt.BorderLayout.EAST);

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

    private void txtParteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtParteActionPerformed
        agregarParte();
    }//GEN-LAST:event_txtParteActionPerformed

    private void botonRedondo5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRedondo5ActionPerformed
        agregarParte();
    }//GEN-LAST:event_botonRedondo5ActionPerformed

    private void botonRedondo3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRedondo3ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        Thread hilo = new Thread(){
          public void run(){
                espera = new Espera();
                espera.activar();
                espera.setLocationRelativeTo(f);
                espera.setExtendedState(Inicio1.MAXIMIZED_BOTH);
                espera.setVisible(true);
                Workbook book;
                try {
                    JFileChooser fc = new JFileChooser();
                    File archivo = null;
                    fc.setFileFilter(new FileNameExtensionFilter("EXCEL (*.xlsx)", "xlsx"));
                    int n = fc.showSaveDialog(f);

                    if(n == JFileChooser.APPROVE_OPTION){
                        archivo = fc.getSelectedFile();
                        String a = ""+archivo;
                        if(a.endsWith("xls")){
                            book = new  HSSFWorkbook();
                        }else {
                            book = new XSSFWorkbook();
                            a = archivo + ".xlsx";
                        }

                        Sheet hoja = book.createSheet("Reporte de costos");

                        //-------------------------------ESTILOS
                        org.apache.poi.ss.usermodel.Font font = book.createFont();
                        CellStyle estilo1 = book.createCellStyle();

                        org.apache.poi.ss.usermodel.Font font3 = book.createFont();
                        CellStyle estilo3 = book.createCellStyle();

                        font.setBold(true);
                        font.setColor(IndexedColors.BLACK.getIndex());
                        font.setFontHeightInPoints((short)12);
                        estilo1.setFont(font);

                        estilo1.setAlignment(HorizontalAlignment.LEFT);

                        font3.setBold(false);
                        font3.setColor(IndexedColors.BLACK.getIndex());
                        font3.setFontHeightInPoints((short)15);
                        estilo3.setFont(font3);

                        estilo3.setAlignment(HorizontalAlignment.CENTER);
                        estilo3.setWrapText(true);

                        //--------------------------------------
                        //        hoja.setColumnWidth(2, 5000);
                        //---------------------------------------
                        int valorFijo = 28;

                        hoja.setColumnWidth(0, valorFijo * 120);
                        hoja.setColumnWidth(1, valorFijo * 261);
                        hoja.setColumnWidth(2, valorFijo * 390);
                        hoja.setColumnWidth(3, valorFijo * 71);
                        hoja.setColumnWidth(4, valorFijo * 200);
                        hoja.setColumnWidth(5, valorFijo * 104);
                        hoja.setColumnWidth(6, valorFijo * 251);
                        hoja.setColumnWidth(7, valorFijo * 251);
                        hoja.setColumnWidth(8, valorFijo * 150);
                        hoja.setColumnWidth(9, valorFijo * 150);
                        hoja.setColumnWidth(10, valorFijo * 150);
                        hoja.setColumnWidth(11, valorFijo * 150);

                        Map<String, Object> properties = new HashMap<String, Object>();
                        properties.put(CellUtil.BORDER_TOP, BorderStyle.MEDIUM);
                        properties.put(CellUtil.BORDER_BOTTOM, BorderStyle.MEDIUM);
                        properties.put(CellUtil.BORDER_LEFT, BorderStyle.MEDIUM);
                        properties.put(CellUtil.BORDER_RIGHT, BorderStyle.MEDIUM);

                        properties.put(CellUtil.TOP_BORDER_COLOR, IndexedColors.BLACK.getIndex());
                        properties.put(CellUtil.BOTTOM_BORDER_COLOR, IndexedColors.BLACK.getIndex());
                        properties.put(CellUtil.LEFT_BORDER_COLOR, IndexedColors.BLACK.getIndex());
                        properties.put(CellUtil.RIGHT_BORDER_COLOR, IndexedColors.BLACK.getIndex());

                        for (int i = -1; i < parte.size(); i++) {
                            Row fila10=hoja.createRow(i+3);
                            for (int j = 0; j < 12; j++) {
                                Cell celda=fila10.createCell(j);
                                boolean band = false;
                                if(i == -1){
                                    XSSFCellStyle s = (XSSFCellStyle) book.createCellStyle();
                                    org.apache.poi.ss.usermodel.Font f = book.createFont();
                                    f.setBold(true);
                                    f.setColor(IndexedColors.WHITE.getIndex());
                                    s.setFont(f);
                                    XSSFColor customColor = new XSSFColor(new java.awt.Color(56, 118, 142));
                                    s.setWrapText(true);
                                    s.setFillForegroundColor(customColor);
                                    s.setFillPattern(SOLID_FOREGROUND);
                                    celda.setCellStyle(s);
                                    band = true;
                                }
                                String texto = "";
                                boolean form = false;
                                boolean total = false;
                                boolean num = false;
                                boolean conta = false;
                                switch(j){
                                    case 0:
                                    if(band){
                                        texto = "Assembly Structure Path";
                                    }
                                    break;
                                    case 1:
                                    if(band){
                                        texto = "Part Number";
                                    }else{
                                        texto = parte.get(i).getText();
                                    }
                                    break;
                                    case 2:
                                    if(band){
                                        texto = "Description";
                                    }else{
                                        texto = descripcion.get(i).getText();
                                    }
                                    break;
                                    case 3:
                                    if(band){
                                        texto = "Quantity per subassembly";
                                    }else{
                                        texto = cantidad.get(i).getText();
                                        num = true;
                                    }
                                    break;
                                    case 4:
                                    if(band){
                                        texto = "Quantity per machine";
                                    }
                                    break;
                                    case 5:
                                    if(band){
                                        texto = "Unit";
                                    }
                                    break;
                                    case 6:
                                    if(band){
                                        texto = "Supplier";
                                    }else{
                                        texto = proveedor.get(i);
                                    }
                                    break;
                                    case 7:
                                    if(band){
                                        texto = "Comments";
                                    }
                                    break;
                                    case 8:
                                    if(band){
                                        texto = "costo";
                                    }else{
                                        texto = precio.get(i).getText();
                                        num = true;
                                    }
                                    break;
                                    case 9:
                                    if(band){
                                        texto = "TOTAL";
                                    }else{
                                        form = true;
                                    }
                                    break;
                                    case 10:
                                    if(band){
                                        texto = "margen";
                                    }else{
                                        texto = "30%";
                                    }
                                    break;
                                    case 11:
                                    if(band){
                                        texto = "total";
                                    }else{
                                        conta = true;
                                    }
                                    break;

                                }
                                if(form){
                                    celda.setCellFormula("D"+(i + 4)+" * I"+(i + 4));
                                }else if(total){
                                    celda.setCellFormula("J"+(i + 4)+"*(1+K"+(i + 4)+")");
                                }else if(conta){
                                    celda.setCellFormula("J"+(i + 4)+"*(1+K"+(i + 4)+")");
                                    CellStyle style = book.createCellStyle();
                                    style.setDataFormat(book.createDataFormat().getFormat("#,##0.00"));
                                    celda.setCellStyle(style);
                                }else{
                                    if(num){
                                        celda.setCellValue(Double.parseDouble(texto));
                                    }else{
                                        celda.setCellValue(texto);
                                    }
                                }
                                book.write(new FileOutputStream(a));
                            }
                        }
                        book.close();
                        try {
                            Desktop desktop = Desktop.getDesktop();

                            if (desktop.isSupported(Desktop.Action.OPEN) && new File(a).exists()) {
                                desktop.open(new File(a));
                            } else {
                                JOptionPane.showMessageDialog(null,"No se puede abrir el archivo automáticamente. Abre manualmente en Excel.");
                            }
                        } catch (IOException e) {
                            espera.dispose();
                            e.printStackTrace();
                        }
                    }
                    espera.dispose();
                } catch (FileNotFoundException ex) {
                    espera.dispose();
                    Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    espera.dispose();
                    Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        hilo.start();
    }//GEN-LAST:event_botonRedondo3ActionPerformed

    private void botonRedondo4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRedondo4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonRedondo4ActionPerformed

    private void botonRedondo6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRedondo6ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        agregarProveedor agregar = new agregarProveedor(f, true);
        agregar.setLocationRelativeTo(f);
        agregar.autocompletar(proveedores);
        String cliente = agregar.getProveedor();
        setEmpresa(cliente);
    }//GEN-LAST:event_botonRedondo6ActionPerformed

    private void botonRedondo7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRedondo7ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        AgregarCliente add = new AgregarCliente(f,true);
        add.setLocationRelativeTo(f);
        add.setVisible(true);
        totalProveedores();
    }//GEN-LAST:event_botonRedondo7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private scrollPane.BotonRedondo botonRedondo2;
    private scrollPane.BotonRedondo botonRedondo3;
    private scrollPane.BotonRedondo botonRedondo4;
    private scrollPane.BotonRedondo botonRedondo5;
    private scrollPane.BotonRedondo botonRedondo6;
    private scrollPane.BotonRedondo botonRedondo7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lblEmpresa;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelPrincipal;
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
