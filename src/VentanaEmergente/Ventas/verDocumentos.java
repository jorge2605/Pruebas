package VentanaEmergente.Ventas;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import scrollPane.ScrollBarCustom;

public class verDocumentos extends java.awt.Dialog implements ActionListener{

    String proyecto;
    JButton[] btnCotizacion;
    JButton[] btnPO;
    JButton[] btnSpec;
    JButton[] btnFactura;
    JPanel[] pnlCotizacion;
    JPanel[] pnlPO;
    JPanel[] pnlSpec;
    JPanel[] pnlFactura;
    String idCoti[];
    String idPO[];
    String idSpec[];
    String idFactura[];
    int contCoti;
    int contPO;
    int contSpec;
    int contFactura;
    
    public void limpiarPaneles(){
        pnCo.removeAll();
        pnPo.removeAll();
        pnFa.removeAll();
        pnSp.removeAll();
        revalidate();
        repaint();
    }

    public void subirArchivo(String departamento){
        JFileChooser seleccionar = new JFileChooser();
        File documento;
            documento = null;
            seleccionar.setFileFilter(new FileNameExtensionFilter("PDF (*.pdf)","pdf"));
                if(seleccionar.showDialog(null, "SELECCIONAR ARCHIVO") == JFileChooser.APPROVE_OPTION){
                    documento = seleccionar.getSelectedFile();
            }
            if(documento != null){
                try{
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Date f = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String fecha = sdf.format(f);
                String sql1 = "insert into archivosproyectos (NombreArchivo, Fecha, Proyecto, Documento, Archivo) values(?,?,?,?,?)";
                PreparedStatement pst1 = con.prepareStatement(sql1);

                byte[] pe;

                    pe = new byte[(int) documento.length()];
                    try{
                        InputStream input = new FileInputStream(documento);
                        input.read(pe);
                    }catch(IOException e){
                        System.out.println("Error archivo: "+e);
                    }

                pst1.setString(1,documento.getName());
                pst1.setString(2,fecha);
                pst1.setString(3,proyecto);
                pst1.setString(4,departamento);
                pst1.setBytes(5,pe);

                int n2 = pst1.executeUpdate();
                if(n2 > 0){
                    JOptionPane.showMessageDialog(this,"DOCUMENTOS GUARDADOS");
                    verDocumentos();
                }
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(verDocumentos.class.getName()).log(Level.SEVERE,null,e);
                }
            }
    }

    public void descargar(byte[] byt){
            try{
            byte[] b = byt;
            
            InputStream bos = new ByteArrayInputStream(b);
            
            int tamInput = bos.available();
            byte[] datosPdf = new byte[tamInput];
            bos.read(datosPdf, 0, tamInput);
            
            JFileChooser fc = new JFileChooser();
            File archivo = null;
            fc.setFileFilter(new FileNameExtensionFilter("Pdf (*.pdf)", "pdf"));
            int n = fc.showSaveDialog(this);

            if(n == JFileChooser.APPROVE_OPTION){
            archivo = fc.getSelectedFile();
            }
            String a = ""+archivo;
            if(a.endsWith("pdf")){
            }else {
            a = archivo + ".pdf";
            }
            
            OutputStream out = new FileOutputStream(a);
            out.write(datosPdf);
            
            out.close();
            bos.close();
            
             Desktop.getDesktop().open(new File(a));
                    
            
            }catch(NumberFormatException  |IOException e){
                JOptionPane.showMessageDialog(this,"ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
        
    public JButton addPropiedades(JButton boton){
        boton.setBackground(new java.awt.Color(255, 255, 255));
        boton.setFont(new java.awt.Font("Roboto", 1, 11));
        boton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pdf_32.png")));
        boton.setBorder(null);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        boton.setFocusPainted(false);
        boton.addActionListener(this);
        return boton;
    }
    
    public void verDocumentos(){
        limpiarPaneles();
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from archivosproyectos where Proyecto like '"+proyecto+"'";
            ResultSet rs = st.executeQuery(sql);
            String doc = "";
            String nombre = "";
            int id;
            pnlSpec = new JPanel[30];
            pnlCotizacion = new JPanel[30];
            pnlPO = new JPanel[30];
            pnlFactura = new JPanel[30];
            btnSpec = new JButton[30];
            btnCotizacion = new JButton[30];
            btnPO = new JButton[30];
            btnFactura = new JButton[30];
            idCoti = new String[30];
            idPO = new String[30];
            idSpec = new String[30];
            idFactura = new String[30];
            contSpec =0;
            contCoti =0;
            contFactura =0;
            contPO =0;
            while(rs.next()){
                doc = rs.getString("Documento");
                nombre = rs.getString("NombreArchivo");
                id = rs.getInt("Id");
                switch(doc){
                    case "0":
                        btnSpec[contSpec] = new JButton(nombre);
                        addPropiedades(btnSpec[contSpec]);
                        pnlSpec[contSpec] = new JPanel();
                        pnlSpec[contSpec].setLayout(new FlowLayout());
                        pnlSpec[contSpec].setBackground(new java.awt.Color(255, 255, 255));
                        pnlSpec[contSpec].add(btnSpec[contSpec]);
                        pnSp.add(pnlSpec[contSpec]);
                        idSpec[contSpec] = String.valueOf(id);
                        contSpec++;
                        break;
                    case "1":
                        btnCotizacion[contCoti] = new JButton(nombre);
                        addPropiedades(btnCotizacion[contCoti]);
                        pnlCotizacion[contCoti] = new JPanel();
                        pnlCotizacion[contCoti].setLayout(new FlowLayout());
                        pnlCotizacion[contCoti].setBackground(new java.awt.Color(255, 255, 255));
                        pnlCotizacion[contCoti].add(btnCotizacion[contCoti]);
                        pnCo.add(pnlCotizacion[contCoti]);
                        idCoti[contCoti] = String.valueOf(id);
                        contCoti++;
                        break;
                    case "2":
                        btnPO[contPO] = new JButton(nombre);
                        addPropiedades(btnPO[contPO]);
                        pnlPO[contPO] = new JPanel();
                        pnlPO[contPO].setLayout(new FlowLayout());
                        pnlPO[contPO].setBackground(new java.awt.Color(255, 255, 255));
                        pnlPO[contPO].add(btnPO[contPO]);
                        pnPo.add(pnlPO[contPO]);
                        idPO[contPO] = String.valueOf(id);
                        contPO++;
                        break;
                    case "3":
                        btnFactura[contFactura] = new JButton(nombre);
                        addPropiedades(btnFactura[contFactura]);
                        pnlFactura[contFactura] = new JPanel();
                        pnlFactura[contFactura].setLayout(new FlowLayout());
                        pnlFactura[contFactura].setBackground(new java.awt.Color(255, 255, 255));
                        pnlFactura[contFactura].add(btnFactura[contFactura]);
                        pnFa.add(pnlFactura[contFactura]);
                        idFactura[contFactura] = String.valueOf(id);
                        contFactura++;
                        break;
                }
            }
            revalidate();
            repaint();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    
    }
    
    public void descargarDocumento(String id){
        byte[] byt = null;
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from archivosproyectos where Id like '"+id+"'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                byt = rs.getBytes("Archivo");
            }
            descargar(byt);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this,"ERROR: "+ex,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void eliminarDocumento(String id){
        int opc = JOptionPane.showConfirmDialog(this, "¿Estas seguro de borras este archivo?");
        if(opc == 0){
            try{
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql = "delete from archivosproyectos where Id = ?";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, id);

                int n = pst.executeUpdate();

                if(n > 0){
                    JOptionPane.showMessageDialog(this, "Datos Borrados");
                    verDocumentos();
                }

            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this,"ERROR: "+ex,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public verDocumentos(java.awt.Frame parent, boolean modal, String Proyecto) {
        super(parent, modal);
        initComponents();
        proyecto = Proyecto;
        Color color = new Color(0,165,252);
        scroll1.setVerticalScrollBar(new ScrollBarCustom(color));
        scroll2.setVerticalScrollBar(new ScrollBarCustom(color));
        scroll3.setVerticalScrollBar(new ScrollBarCustom(color));
        scroll4.setVerticalScrollBar(new ScrollBarCustom(color));
        
        verDocumentos();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        panelCotizacion = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnSubirCotizacion = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        btnDescargarCoti = new javax.swing.JButton();
        btnEliminarCoti = new javax.swing.JButton();
        scroll1 = new javax.swing.JScrollPane();
        pnCo = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        panelPO = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        btnSubirPO = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        btnDescargarPO = new javax.swing.JButton();
        btnEliminarPO = new javax.swing.JButton();
        scroll2 = new javax.swing.JScrollPane();
        pnPo = new javax.swing.JPanel();
        panelSpec = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        btnSubirSpec = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        btnDescargarSpec = new javax.swing.JButton();
        btnEliminarSpec = new javax.swing.JButton();
        scroll3 = new javax.swing.JScrollPane();
        pnSp = new javax.swing.JPanel();
        panelFactura = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        btnSubirFactura = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        btnDescargarFactura = new javax.swing.JButton();
        btnEliminarFactura = new javax.swing.JButton();
        scroll4 = new javax.swing.JScrollPane();
        pnFa = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblProyecto = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1095, 577));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridLayout(1, 5));

        panelCotizacion.setBackground(new java.awt.Color(255, 255, 255));
        panelCotizacion.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(0, 165, 252));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("      COTIZACION      ");
        jPanel3.add(jLabel1);

        jPanel6.add(jPanel3);

        panelCotizacion.add(jPanel6, java.awt.BorderLayout.NORTH);

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        btnSubirCotizacion.setBackground(new java.awt.Color(255, 255, 255));
        btnSubirCotizacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/subir-archivo.png"))); // NOI18N
        btnSubirCotizacion.setText("SUBIR");
        btnSubirCotizacion.setBorder(null);
        btnSubirCotizacion.setBorderPainted(false);
        btnSubirCotizacion.setComponentPopupMenu(jPopupMenu1);
        btnSubirCotizacion.setContentAreaFilled(false);
        btnSubirCotizacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSubirCotizacion.setFocusPainted(false);
        btnSubirCotizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubirCotizacionActionPerformed(evt);
            }
        });
        jPanel4.add(btnSubirCotizacion);

        jPanel22.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        btnDescargarCoti.setBackground(new java.awt.Color(255, 255, 255));
        btnDescargarCoti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pdf.png"))); // NOI18N
        btnDescargarCoti.setText("Descargar");
        btnDescargarCoti.setBorder(null);
        btnDescargarCoti.setBorderPainted(false);
        btnDescargarCoti.setComponentPopupMenu(jPopupMenu1);
        btnDescargarCoti.setContentAreaFilled(false);
        btnDescargarCoti.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDescargarCoti.setFocusPainted(false);
        btnDescargarCoti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescargarCotiActionPerformed(evt);
            }
        });
        jPanel9.add(btnDescargarCoti);

        btnEliminarCoti.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminarCoti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/eliminar (1).png"))); // NOI18N
        btnEliminarCoti.setText("Eliminar");
        btnEliminarCoti.setBorder(null);
        btnEliminarCoti.setBorderPainted(false);
        btnEliminarCoti.setComponentPopupMenu(jPopupMenu1);
        btnEliminarCoti.setContentAreaFilled(false);
        btnEliminarCoti.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarCoti.setFocusPainted(false);
        btnEliminarCoti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCotiActionPerformed(evt);
            }
        });
        jPanel9.add(btnEliminarCoti);

        jPanel22.add(jPanel9, java.awt.BorderLayout.PAGE_START);

        panelCotizacion.add(jPanel22, java.awt.BorderLayout.SOUTH);

        scroll1.setBackground(new java.awt.Color(255, 255, 255));
        scroll1.setBorder(null);
        scroll1.setForeground(new java.awt.Color(255, 255, 255));

        pnCo.setBackground(new java.awt.Color(255, 255, 255));
        pnCo.setLayout(new javax.swing.BoxLayout(pnCo, javax.swing.BoxLayout.Y_AXIS));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        pnCo.add(jPanel2);

        scroll1.setViewportView(pnCo);

        panelCotizacion.add(scroll1, java.awt.BorderLayout.CENTER);

        jPanel1.add(panelCotizacion);

        panelPO.setBackground(new java.awt.Color(255, 255, 255));
        panelPO.setLayout(new java.awt.BorderLayout());

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(0, 165, 252));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("      PO      ");
        jPanel5.add(jLabel2);

        jPanel23.add(jPanel5);

        panelPO.add(jPanel23, java.awt.BorderLayout.NORTH);

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setLayout(new java.awt.BorderLayout());

        btnSubirPO.setBackground(new java.awt.Color(255, 255, 255));
        btnSubirPO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/subir-archivo.png"))); // NOI18N
        btnSubirPO.setText("SUBIR");
        btnSubirPO.setBorder(null);
        btnSubirPO.setBorderPainted(false);
        btnSubirPO.setContentAreaFilled(false);
        btnSubirPO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSubirPO.setFocusPainted(false);
        btnSubirPO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubirPOActionPerformed(evt);
            }
        });
        jPanel24.add(btnSubirPO, java.awt.BorderLayout.CENTER);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        btnDescargarPO.setBackground(new java.awt.Color(255, 255, 255));
        btnDescargarPO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pdf.png"))); // NOI18N
        btnDescargarPO.setText("Descargar");
        btnDescargarPO.setBorder(null);
        btnDescargarPO.setBorderPainted(false);
        btnDescargarPO.setComponentPopupMenu(jPopupMenu1);
        btnDescargarPO.setContentAreaFilled(false);
        btnDescargarPO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDescargarPO.setFocusPainted(false);
        btnDescargarPO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescargarPOActionPerformed(evt);
            }
        });
        jPanel10.add(btnDescargarPO);

        btnEliminarPO.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminarPO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/eliminar (1).png"))); // NOI18N
        btnEliminarPO.setText("Eliminar");
        btnEliminarPO.setBorder(null);
        btnEliminarPO.setBorderPainted(false);
        btnEliminarPO.setComponentPopupMenu(jPopupMenu1);
        btnEliminarPO.setContentAreaFilled(false);
        btnEliminarPO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarPO.setFocusPainted(false);
        btnEliminarPO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPOActionPerformed(evt);
            }
        });
        jPanel10.add(btnEliminarPO);

        jPanel24.add(jPanel10, java.awt.BorderLayout.PAGE_START);

        panelPO.add(jPanel24, java.awt.BorderLayout.SOUTH);

        scroll2.setBackground(new java.awt.Color(255, 255, 255));
        scroll2.setBorder(null);
        scroll2.setForeground(new java.awt.Color(255, 255, 255));

        pnPo.setBackground(new java.awt.Color(255, 255, 255));
        pnPo.setLayout(new javax.swing.BoxLayout(pnPo, javax.swing.BoxLayout.Y_AXIS));
        scroll2.setViewportView(pnPo);

        panelPO.add(scroll2, java.awt.BorderLayout.CENTER);

        jPanel1.add(panelPO);

        panelSpec.setBackground(new java.awt.Color(255, 255, 255));
        panelSpec.setLayout(new java.awt.BorderLayout());

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(0, 165, 252));

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("      SPEC      ");
        jPanel7.add(jLabel4);

        jPanel27.add(jPanel7);

        panelSpec.add(jPanel27, java.awt.BorderLayout.NORTH);

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setLayout(new java.awt.BorderLayout());

        btnSubirSpec.setBackground(new java.awt.Color(255, 255, 255));
        btnSubirSpec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/subir-archivo.png"))); // NOI18N
        btnSubirSpec.setText("SUBIR");
        btnSubirSpec.setBorder(null);
        btnSubirSpec.setBorderPainted(false);
        btnSubirSpec.setContentAreaFilled(false);
        btnSubirSpec.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSubirSpec.setFocusPainted(false);
        btnSubirSpec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubirSpecActionPerformed(evt);
            }
        });
        jPanel28.add(btnSubirSpec, java.awt.BorderLayout.CENTER);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        btnDescargarSpec.setBackground(new java.awt.Color(255, 255, 255));
        btnDescargarSpec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pdf.png"))); // NOI18N
        btnDescargarSpec.setText("Descargar");
        btnDescargarSpec.setBorder(null);
        btnDescargarSpec.setBorderPainted(false);
        btnDescargarSpec.setComponentPopupMenu(jPopupMenu1);
        btnDescargarSpec.setContentAreaFilled(false);
        btnDescargarSpec.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDescargarSpec.setFocusPainted(false);
        btnDescargarSpec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescargarSpecActionPerformed(evt);
            }
        });
        jPanel13.add(btnDescargarSpec);

        btnEliminarSpec.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminarSpec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/eliminar (1).png"))); // NOI18N
        btnEliminarSpec.setText("Eliminar");
        btnEliminarSpec.setBorder(null);
        btnEliminarSpec.setBorderPainted(false);
        btnEliminarSpec.setComponentPopupMenu(jPopupMenu1);
        btnEliminarSpec.setContentAreaFilled(false);
        btnEliminarSpec.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarSpec.setFocusPainted(false);
        btnEliminarSpec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarSpecActionPerformed(evt);
            }
        });
        jPanel13.add(btnEliminarSpec);

        jPanel28.add(jPanel13, java.awt.BorderLayout.PAGE_START);

        panelSpec.add(jPanel28, java.awt.BorderLayout.SOUTH);

        scroll3.setBackground(new java.awt.Color(255, 255, 255));
        scroll3.setBorder(null);
        scroll3.setForeground(new java.awt.Color(255, 255, 255));

        pnSp.setBackground(new java.awt.Color(255, 255, 255));
        pnSp.setLayout(new javax.swing.BoxLayout(pnSp, javax.swing.BoxLayout.Y_AXIS));
        scroll3.setViewportView(pnSp);

        panelSpec.add(scroll3, java.awt.BorderLayout.CENTER);

        jPanel1.add(panelSpec);

        panelFactura.setBackground(new java.awt.Color(255, 255, 255));
        panelFactura.setLayout(new java.awt.BorderLayout());

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setBackground(new java.awt.Color(0, 165, 252));

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("      FACTURA      ");
        jPanel8.add(jLabel5);

        jPanel31.add(jPanel8);

        panelFactura.add(jPanel31, java.awt.BorderLayout.NORTH);

        jPanel32.setBackground(new java.awt.Color(255, 255, 255));
        jPanel32.setLayout(new java.awt.BorderLayout());

        btnSubirFactura.setBackground(new java.awt.Color(255, 255, 255));
        btnSubirFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/subir-archivo.png"))); // NOI18N
        btnSubirFactura.setText("SUBIR");
        btnSubirFactura.setBorder(null);
        btnSubirFactura.setBorderPainted(false);
        btnSubirFactura.setContentAreaFilled(false);
        btnSubirFactura.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSubirFactura.setFocusPainted(false);
        btnSubirFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubirFacturaActionPerformed(evt);
            }
        });
        jPanel32.add(btnSubirFactura, java.awt.BorderLayout.CENTER);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        btnDescargarFactura.setBackground(new java.awt.Color(255, 255, 255));
        btnDescargarFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pdf.png"))); // NOI18N
        btnDescargarFactura.setText("Descargar");
        btnDescargarFactura.setBorder(null);
        btnDescargarFactura.setBorderPainted(false);
        btnDescargarFactura.setComponentPopupMenu(jPopupMenu1);
        btnDescargarFactura.setContentAreaFilled(false);
        btnDescargarFactura.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDescargarFactura.setFocusPainted(false);
        btnDescargarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescargarFacturaActionPerformed(evt);
            }
        });
        jPanel14.add(btnDescargarFactura);

        btnEliminarFactura.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminarFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/eliminar (1).png"))); // NOI18N
        btnEliminarFactura.setText("Eliminar");
        btnEliminarFactura.setBorder(null);
        btnEliminarFactura.setBorderPainted(false);
        btnEliminarFactura.setComponentPopupMenu(jPopupMenu1);
        btnEliminarFactura.setContentAreaFilled(false);
        btnEliminarFactura.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarFactura.setFocusPainted(false);
        btnEliminarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarFacturaActionPerformed(evt);
            }
        });
        jPanel14.add(btnEliminarFactura);

        jPanel32.add(jPanel14, java.awt.BorderLayout.PAGE_START);

        panelFactura.add(jPanel32, java.awt.BorderLayout.SOUTH);

        scroll4.setBackground(new java.awt.Color(255, 255, 255));
        scroll4.setBorder(null);
        scroll4.setForeground(new java.awt.Color(255, 255, 255));

        pnFa.setBackground(new java.awt.Color(255, 255, 255));
        pnFa.setLayout(new javax.swing.BoxLayout(pnFa, javax.swing.BoxLayout.Y_AXIS));
        scroll4.setViewportView(pnFa);

        panelFactura.add(scroll4, java.awt.BorderLayout.CENTER);

        jPanel1.add(panelFactura);

        add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        jPanel19.setBackground(new java.awt.Color(0, 102, 156));

        jLabel3.setFont(new java.awt.Font("Roboto Medium", 0, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("PROYECTO:");
        jPanel19.add(jLabel3);

        lblProyecto.setFont(new java.awt.Font("Roboto Medium", 0, 36)); // NOI18N
        lblProyecto.setForeground(new java.awt.Color(255, 255, 255));
        lblProyecto.setText(" ");
        jPanel19.add(lblProyecto);

        jPanel18.add(jPanel19);

        add(jPanel18, java.awt.BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void btnSubirCotizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirCotizacionActionPerformed
        subirArchivo("1");
    }//GEN-LAST:event_btnSubirCotizacionActionPerformed

    private void btnSubirPOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirPOActionPerformed
        subirArchivo("2");
    }//GEN-LAST:event_btnSubirPOActionPerformed

    private void btnSubirSpecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirSpecActionPerformed
       subirArchivo("0");
    }//GEN-LAST:event_btnSubirSpecActionPerformed

    private void btnSubirFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirFacturaActionPerformed
        subirArchivo("3");
    }//GEN-LAST:event_btnSubirFacturaActionPerformed

    private void btnDescargarCotiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescargarCotiActionPerformed
        String id = "";
        for (int i = 0; i < btnCotizacion.length; i++) {
            if(pnlCotizacion[i] != null){
                if(pnlCotizacion[i].getBackground().equals(Color.green)){
                    id = String.valueOf(idCoti[i]);
                }
            }
        }
        descargarDocumento(id);
    }//GEN-LAST:event_btnDescargarCotiActionPerformed

    private void btnEliminarCotiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCotiActionPerformed
        String id = "";
        for (int i = 0; i < btnCotizacion.length; i++) {
            if(pnlCotizacion[i] != null){
                if(pnlCotizacion[i].getBackground().equals(Color.green)){
                    id = String.valueOf(idCoti[i]);
                }
            }
        }
        eliminarDocumento(id);
    }//GEN-LAST:event_btnEliminarCotiActionPerformed

    private void btnDescargarPOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescargarPOActionPerformed
        String id = "";
        for (int i = 0; i < btnPO.length; i++) {
            if(pnlPO[i] != null){
                if(pnlPO[i].getBackground().equals(Color.green)){
                    id = String.valueOf(idPO[i]);
                }
            }
        }
        descargarDocumento(id);
    }//GEN-LAST:event_btnDescargarPOActionPerformed

    private void btnEliminarPOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPOActionPerformed
        String id = "";
        for (int i = 0; i < btnPO.length; i++) {
            if(pnlPO[i] != null){
                if(pnlPO[i].getBackground().equals(Color.green)){
                    id = String.valueOf(idPO[i]);
                }
            }
        }
        eliminarDocumento(id);
    }//GEN-LAST:event_btnEliminarPOActionPerformed

    private void btnDescargarSpecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescargarSpecActionPerformed
        String id = "";
        for (int i = 0; i < btnSpec.length; i++) {
            if(pnlSpec[i] != null){
                if(pnlSpec[i].getBackground().equals(Color.green)){
                    id = String.valueOf(idSpec[i]);
                }
            }
        }
        descargarDocumento(id);
    }//GEN-LAST:event_btnDescargarSpecActionPerformed

    private void btnEliminarSpecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarSpecActionPerformed
        String id = "";
        for (int i = 0; i < btnSpec.length; i++) {
            if(pnlSpec[i] != null){
                if(pnlSpec[i].getBackground().equals(Color.green)){
                    id = String.valueOf(idSpec[i]);
                }
            }
        }
        eliminarDocumento(id);
    }//GEN-LAST:event_btnEliminarSpecActionPerformed

    private void btnDescargarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescargarFacturaActionPerformed
        String id = "";
        for (int i = 0; i < btnFactura.length; i++) {
            if(pnlFactura[i] != null){
                if(pnlFactura[i].getBackground().equals(Color.green)){
                    id = String.valueOf(idFactura[i]);
                }
            }
        }
        descargarDocumento(id);
    }//GEN-LAST:event_btnDescargarFacturaActionPerformed

    private void btnEliminarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarFacturaActionPerformed
        String id = "";
        for (int i = 0; i < btnFactura.length; i++) {
            if(pnlFactura[i] != null){
                if(pnlFactura[i].getBackground().equals(Color.green)){
                    id = String.valueOf(idFactura[i]);
                }
            }
        }
        eliminarDocumento(id);
    }//GEN-LAST:event_btnEliminarFacturaActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                verDocumentos dialog = new verDocumentos(new java.awt.Frame(), true,"");
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
    public javax.swing.JButton btnDescargarCoti;
    public javax.swing.JButton btnDescargarFactura;
    public javax.swing.JButton btnDescargarPO;
    public javax.swing.JButton btnDescargarSpec;
    public javax.swing.JButton btnEliminarCoti;
    public javax.swing.JButton btnEliminarFactura;
    public javax.swing.JButton btnEliminarPO;
    public javax.swing.JButton btnEliminarSpec;
    public javax.swing.JButton btnSubirCotizacion;
    public javax.swing.JButton btnSubirFactura;
    public javax.swing.JButton btnSubirPO;
    public javax.swing.JButton btnSubirSpec;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    public javax.swing.JLabel lblProyecto;
    private javax.swing.JPanel panelCotizacion;
    private javax.swing.JPanel panelFactura;
    private javax.swing.JPanel panelPO;
    private javax.swing.JPanel panelSpec;
    private javax.swing.JPanel pnCo;
    private javax.swing.JPanel pnFa;
    private javax.swing.JPanel pnPo;
    private javax.swing.JPanel pnSp;
    private javax.swing.JScrollPane scroll1;
    private javax.swing.JScrollPane scroll2;
    private javax.swing.JScrollPane scroll3;
    private javax.swing.JScrollPane scroll4;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < btnCotizacion.length; i++) {
            if(e.getSource() == btnCotizacion[i]){
                if(pnlCotizacion[i].getBackground().equals(Color.green)){
                    pnlCotizacion[i].setBackground(Color.white);
                }else{
                    for (JPanel pnlCotizacion1 : pnlCotizacion) {
                        if (pnlCotizacion1 != null) {
                            pnlCotizacion1.setBackground(Color.white);
                        }
                    }
                    pnlCotizacion[i].setBackground(Color.green);
                }
            }
        }
        
        for (int i = 0; i < btnPO.length; i++) {
            if(e.getSource() == btnPO[i]){
                if(pnlPO[i].getBackground().equals(Color.green)){
                    pnlPO[i].setBackground(Color.white);
                }else{
                    for (JPanel pnlCotizacion1 : pnlPO) {
                        if (pnlCotizacion1 != null) {
                            pnlCotizacion1.setBackground(Color.white);
                        }
                    }
                    pnlPO[i].setBackground(Color.green);
                }
            }
        }
        
        for (int i = 0; i < btnFactura.length; i++) {
            if(e.getSource() == btnFactura[i]){
                if(pnlFactura[i].getBackground().equals(Color.green)){
                    pnlFactura[i].setBackground(Color.white);
                }else{
                    for (JPanel pnlCotizacion1 : pnlFactura) {
                        if (pnlCotizacion1 != null) {
                            pnlCotizacion1.setBackground(Color.white);
                        }
                    }
                    pnlFactura[i].setBackground(Color.green);
                }
            }
        }
        
        for (int i = 0; i < btnSpec.length; i++) {
            if(e.getSource() == btnSpec[i]){
                if(pnlSpec[i].getBackground().equals(Color.green)){
                   pnlSpec[i].setBackground(Color.white);
                }else{
                    for (JPanel pnlCotizacion1 : pnlSpec) {
                        if (pnlCotizacion1 != null) {
                            pnlCotizacion1.setBackground(Color.white);
                        }
                    }
                    pnlSpec[i].setBackground(Color.green);
                }
            }
        }
        
    }
}
