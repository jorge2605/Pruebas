package pruebas;

import Conexiones.Conexion;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Modelo.TablaVerRequisiciones;
import VentanaEmergente.Compras.Reclamos;
import VentanaEmergente.Requisiciones.Material;
import VentanaEmergente.Requisiciones.liberarCompra;
import VentanaEmergente.verRequisiciones.Detalles;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JTable;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import scrollPane.ScrollBarCustom;

public class VerRequisiciones extends javax.swing.JInternalFrame implements ActionListener{

    TextAutoCompleter au;
    String numEmpleado;
    boolean compras;
    liberarCompra lib;
    int tabla = 0;
    
    public void limpiarTablaOrden(){
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "NO. ORDEN", "NO. REQUISICION"
                }
            ) {
                boolean[] canEdit = new boolean [] {
                    false, false
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });

            Tabla1.setRowHeight(25);
            Tabla1.setShowVerticalLines(false);
            Tabla1.setGridColor(new java.awt.Color(245, 245, 245));

            Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    Tabla1MouseClicked(evt);
                }
            });

            jScrollPane2.setViewportView(Tabla1);

            if (Tabla1.getColumnModel().getColumnCount() > 0) {
                Tabla1.getColumnModel().getColumn(0).setResizable(false);
                Tabla1.getColumnModel().getColumn(1).setResizable(false);
            }
    }
    
    public void verDatosOrden(){
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Statement st = con.createStatement();
            String sql = "select * from ordencompra order by Id DESC";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            while(rs.next()){
                datos[0] = rs.getString("OrdenNo");
                datos[1] = rs.getString("RequisicionNo");
                miModelo.addRow(datos);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void seleccionarTabla(){
        limpiarTabla1();
        if(compras){
            limpiarTabla3();
        }
        try{
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
        Connection con = null;
        Conexion con1 = new Conexion();
        con = con1.getConnection();
        Statement st = con.createStatement();
        String sql = "select Id,NumeroEmpleado, NumeroCotizacion, Estatus, Estado, Progreso, Costo, Fecha from Requisicion order by Id desc";
        ResultSet rs = st.executeQuery(sql);
        String datos[] = new String[10];
        while(rs.next()){
        datos[0] = rs.getString("Id");
        datos[1] = rs.getString("NumeroEmpleado");
        datos[2] = rs.getString("NumeroCotizacion");
        datos[3] = rs.getString("Estatus");
        datos[4] = rs.getString("Estado");
        datos[5] = rs.getString("Progreso");
        datos[6] = rs.getString("Costo");
        datos[7] = rs.getString("Fecha");
        miModelo.addRow(datos);
        }
        }catch(SQLException e){
       JOptionPane.showMessageDialog(null,"ERROR AL VER DATOS TABLA 1","ERROR",JOptionPane.ERROR_MESSAGE);
   }
    }
    
    public void seleccionarRequisicion(String requi){
        try{
        limpiarTabla2();
        DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();
        
        
        Connection con = null;
        Conexion con1 = new Conexion();
        con = con1.getConnection();
        Statement st = con.createStatement();
        Statement st2 = con.createStatement();
        String sql = "select * from Requisiciones where NumRequisicion like '"+requi+"'";
        ResultSet rs = st.executeQuery(sql);
        
        String sql2 = "select * from ordencompra where RequisicionNo like '"+requi+"'";
        ResultSet rs2 = st2.executeQuery(sql2);
        
        String datos[] = new String[10];
        String datos1[] = new String[10];
        
        while(rs.next()){
        datos[0] = rs.getString("NumRequisicion");
        datos[1] = rs.getString("Codigo");
        datos[2] = rs.getString("Descripcion");
        datos[3] = rs.getString("Proyecto");
        datos[4] = rs.getString("Cantidad");
        datos[5] = rs.getString("NumParte");
        datos[6] = rs.getString("Llego");
        datos[7] = rs.getString("Precio");
        datos[8] = rs.getString("TE");
        datos[9] = rs.getString("OC");
        
        miModelo.addRow(datos);
        }
        
        }catch(SQLException e){
        JOptionPane.showMessageDialog(null, "ERROR AL MOSTRAR ITEMS: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
   public void autoCompletar(String bd, String campo){
       try{
           au.removeAllItems();
       }catch(Exception e){
           
       }
       au = new TextAutoCompleter(txtProyecto);
       
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select " + campo + " from " + bd +"";
            String datos[] = new String[10];
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(campo);
                au.addItem(datos[0]);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL AUTOCOMPLETAR" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
       
   }
    
   public void limpiarTabla1(){
//   Tabla1 = new TablaVerRequisiciones();
    Tabla1 = new javax.swing.JTable();
   
   Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        Tabla1MouseClicked(evt);
    }
    });
   Tabla1.setComponentPopupMenu(popMenu);
   jScrollPane2.setViewportView(Tabla1);
   DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
   String titulos[] = {"NO. REQUISICION","NO. EMPLEADO","NO. COTIZACION","ESTATUS","ESTADO","PROGRESO","COSTO","FECHA"};
   miModelo = (new DefaultTableModel(null,titulos){
    boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false, false, false
    };

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
    }
    });
   Tabla1.setModel(miModelo);
   Tabla1.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
//        Tabla1.setShowGrid(false);
   }
   
   public void limpiarTabla3(){
   Tabla1 = new TablaVerRequisiciones();
   
   Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        Tabla1MouseClicked(evt);
    }
    });

    jScrollPane2.setViewportView(Tabla1);
   Tabla1.setComponentPopupMenu(popMenu);
   DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
   String titulos[] = {"NO. REQUISICION","NO. EMPLEADO","NO. COTIZACION","ESTATUS","ESTADO","PROGRESO","COSTO","FECHA"};
   miModelo = (new DefaultTableModel (null,titulos){
    boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false, false, false
    };

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
    }
    });
   Tabla1.setModel(miModelo);
   Tabla1.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
//        Tabla1.setShowGrid(false);
   }
   
   public void limpiarTabla2(){
   Tabla2.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
        },
        new String [] {
            "NO. REQUISICION","CODIGO","DESCRIPCION","PROYECTO","CANTIDAD","REQUISITOR","LLEGO","PRECIO","TE","PO","NOTAS","PROVEEDOR","RECIBO","C.F"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false, false, false, false, false,false, false,false, false, false, false, false
        };
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    jScrollPane1.setViewportView(Tabla2);

    if (Tabla2.getColumnModel().getColumnCount() > 0) {
        Tabla2.getColumnModel().getColumn(0).setResizable(false);
    }
   }
   
   public void verDatos1(){
   try{
   DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
   Connection con = null;
   Conexion con1 = new Conexion();
   con = con1.getConnection();
   Statement st = con.createStatement();
   String sql = "select NumeroEmpleado, Id, NumeroCotizacion, Estatus, Estado, Progreso, Costo, Fecha from Requisicion where NumeroEmpleado like '"+numEmpleado+"' order by Id desc";
   ResultSet rs = st.executeQuery(sql);
   String datos[] = new String[10];
   while(rs.next()){
   datos[0] = rs.getString("Id");
   datos[1] = numEmpleado;
   datos[2] = rs.getString("NumeroCotizacion");
   datos[3] = rs.getString("Estatus");
   datos[4] = rs.getString("Estado");
   datos[5] = rs.getString("Progreso");
   datos[6] = rs.getString("Costo");
   datos[7] = rs.getString("Fecha");
   miModelo.addRow(datos);
   }
   }catch(SQLException e){
       JOptionPane.showMessageDialog(null,"ERROR AL VER DATOS TABLA 1","ERROR",JOptionPane.ERROR_MESSAGE);
   }
   }
   
   public final void verTe(){
       if(numEmpleado.equals("11") || numEmpleado.equals("35")  || numEmpleado.equals("3")  || numEmpleado.equals("1005") || numEmpleado.equals("104")  || numEmpleado.equals("71")  || numEmpleado.equals("57") || numEmpleado.equals("61") || numEmpleado.equals("7") ){
           miVencido.setEnabled(true);
       }
   }
   
   public void compras(){
       try{
           Connection con;
           Conexion con1 = new Conexion();
           con = con1.getConnection();
           Statement st = con.createStatement();
           String sql = "select * from registroempleados where NumEmpleado like '"+numEmpleado+"'";
           ResultSet rs = st.executeQuery(sql);
           String com = null;
           while(rs.next()){
               com = rs.getString("Orden");
           }
           if(com != null){
               if(com.equals("1")){
                   compras = true;
               }else{
                   compras = false;
               }
           }
       }catch(SQLException e){
           JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
       }
   }
   
   public void tablaProv1(){
       Tabla1 = new TablaVerRequisiciones();
   
   Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        Tabla1MouseClicked(evt);
    }
    });

    jScrollPane2.setViewportView(Tabla1);
   Tabla1.setComponentPopupMenu(popMenu);
   DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
   String titulos[] = {"O.C","NO. REQUISICION","NO. EMPLEADO"};
   miModelo = (new DefaultTableModel (null,titulos){
    boolean[] canEdit = new boolean [] {
        false, false, false
    };

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
    }
    });
   Tabla1.setModel(miModelo);
   Tabla1.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
   }
   
   public void exportarExcel(JTable TablaDeDatos1){
       Workbook book;
       int columna = TablaDeDatos1.getColumnCount();
        try {
        JFileChooser fc = new JFileChooser();
        File archivo = null;
        fc.setFileFilter(new FileNameExtensionFilter("EXCEL (*.xlsx)", "xlsx"));
        int n = fc.showSaveDialog(this);
           
        if(n == JFileChooser.APPROVE_OPTION){
        archivo = fc.getSelectedFile();
        }
        String a = ""+archivo;
        if(a.endsWith("xls")){
        book = new  HSSFWorkbook();
        }else {
        book = new XSSFWorkbook();
        a = archivo + ".xlsx";
        }
        
        Sheet hoja = book.createSheet("Reporte de requisiciones");
        Row fila = hoja.createRow(2);
        Cell col = fila.createCell(2);
        
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
        
        hoja.setColumnWidth(2, 4000);
        hoja.setColumnWidth(3, 6500);
        hoja.setColumnWidth(4, 6500); 
        hoja.setColumnWidth(5, 8200); 
        hoja.setColumnWidth(7, 8200); 
        hoja.setColumnWidth(13, 8200);
        
        org.apache.poi.ss.usermodel.Font font1 = book.createFont();
        CellStyle style = book.createCellStyle();
        
        font1.setBold(true);
        font1.setColor(IndexedColors.WHITE.getIndex());
        font1.setFontHeightInPoints((short)16);
        style.setFont(font1);
        
        style.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(SOLID_FOREGROUND);
        style.setVerticalAlignment(VerticalAlignment.BOTTOM);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
        
        hoja.addMergedRegion(new CellRangeAddress (
        2,
        2,
        2,
        columna+1
        ));
        
        
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(CellUtil.BORDER_TOP, BorderStyle.MEDIUM);
        properties.put(CellUtil.BORDER_BOTTOM, BorderStyle.MEDIUM);
        properties.put(CellUtil.BORDER_LEFT, BorderStyle.MEDIUM);
        properties.put(CellUtil.BORDER_RIGHT, BorderStyle.MEDIUM);
        
        properties.put(CellUtil.TOP_BORDER_COLOR, IndexedColors.BLACK.getIndex());
        properties.put(CellUtil.BOTTOM_BORDER_COLOR, IndexedColors.BLACK.getIndex());
        properties.put(CellUtil.LEFT_BORDER_COLOR, IndexedColors.BLACK.getIndex());
        properties.put(CellUtil.RIGHT_BORDER_COLOR, IndexedColors.BLACK.getIndex());  
        
        col.setCellStyle(style);
        col.setCellValue("Reporte de requisiciones");
        
        for (int i = -1; i < TablaDeDatos1.getRowCount(); i++) {
                Row fila10=hoja.createRow(i+7);
                for (int j = 0; j < columna; j++) {
                    Cell celda=fila10.createCell(j+2);
                    if(i == -1 && (j >= 0 && j <= columna+1)){
                        CellStyle s = book.createCellStyle();
                        org.apache.poi.ss.usermodel.Font f = book.createFont();
                        f.setBold(true);
                        f.setColor(IndexedColors.WHITE.getIndex());
                        s.setFont(f);
                        s.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }
                    if(i > -1 && (j > -1 && j <= columna) && (i%2 == 0)){
                        CellStyle s = book.createCellStyle();
                        s.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }
                    
                    if(i==-1){
                        celda.setCellValue(String.valueOf(TablaDeDatos1.getColumnName(j)));
//                        CellUtil.setCellStyleProperties(celda, properties);
                    }else{
                        if(j == 3){
                        CellStyle ss = book.createCellStyle();
                        ss.setWrapText(true);
                        
                            if(i%2 == 0){
                            ss.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                            ss.setFillPattern(SOLID_FOREGROUND);

                            }
                            celda.setCellStyle(ss);
                        }
                        celda.setCellValue(String.valueOf(TablaDeDatos1.getValueAt(i, j)));
                    }
                    File ad = new File(a);
                    book.write(new FileOutputStream(a));                
                }
            }
            
        
    
        
        book.close();
    } catch (FileNotFoundException ex) {
        Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
    }
   }
   
    public VerRequisiciones(String NoEmpleado) {
        initComponents();
        this.numEmpleado = NoEmpleado;
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        verTe();
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowGrid(false);
        
        Tabla2.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 14));
        Tabla2.getTableHeader().setOpaque(false);
        Tabla2.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla2.getTableHeader().setForeground(Color.white);
        Tabla2.setRowHeight(25);
        Tabla2.setShowVerticalLines(false);
        
        compras();
        
        jScrollPane1.setVerticalScrollBar(new ScrollBarCustom(new Color(0,165,255)));
        jScrollPane2.setVerticalScrollBar(new ScrollBarCustom(new Color(0,165,255)));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        popMenu = new javax.swing.JPopupMenu();
        verDetalles = new javax.swing.JMenuItem();
        verRelaciones = new javax.swing.JMenuItem();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jPanel4 = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        panelX = new javax.swing.JPanel();
        lblX = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cmbBuscar = new RSMaterialComponent.RSComboBoxMaterial();
        txtProyecto = new rojeru_san.RSMTextFull();
        jPanel5 = new javax.swing.JPanel();
        rbtnMis = new RSMaterialComponent.RSRadioButtonMaterial();
        rbtnTodas = new RSMaterialComponent.RSRadioButtonMaterial();
        rSRadioButtonMaterial1 = new RSMaterialComponent.RSRadioButtonMaterial();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla2 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        miVencido = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        verDetalles.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        verDetalles.setText("    Detalles                 ");
        verDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verDetallesActionPerformed(evt);
            }
        });
        popMenu.add(verDetalles);

        verRelaciones.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        verRelaciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/nodos.png"))); // NOI18N
        verRelaciones.setText("    Ver relacion de materiales");
        verRelaciones.setToolTipText("");
        verRelaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verRelacionesActionPerformed(evt);
            }
        });
        popMenu.add(verRelaciones);

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        titulo.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 40)); // NOI18N
        titulo.setForeground(new java.awt.Color(0, 165, 252));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("VER REQUISICIONES");
        titulo.setPreferredSize(new java.awt.Dimension(190, 29));
        titulo.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tituloMouseDragged(evt);
            }
        });
        titulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tituloMousePressed(evt);
            }
        });
        jPanel4.add(titulo, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        panelX.setBackground(new java.awt.Color(255, 255, 255));

        lblX.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        lblX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblX.setText("  X  ");
        lblX.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        jPanel3.add(panelX);

        jPanel4.add(jPanel3, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(3, 0, 10, 10));

        cmbBuscar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NUMERO DE REQUISICION", "NUMERO DE PARTE", "DESCRIPCION", "PROYECTO", "PROVEEDOR" }));
        cmbBuscar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbBuscarItemStateChanged(evt);
            }
        });
        cmbBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBuscarActionPerformed(evt);
            }
        });
        jPanel2.add(cmbBuscar);

        txtProyecto.setModoMaterial(true);
        txtProyecto.setPlaceholder("Introducir");
        txtProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProyectoActionPerformed(evt);
            }
        });
        jPanel2.add(txtProyecto);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.GridLayout(1, 3));

        buttonGroup1.add(rbtnMis);
        rbtnMis.setText("Mis requisiciones");
        rbtnMis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnMisActionPerformed(evt);
            }
        });
        jPanel5.add(rbtnMis);

        buttonGroup1.add(rbtnTodas);
        rbtnTodas.setText("Todas las requisiciones");
        rbtnTodas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnTodasActionPerformed(evt);
            }
        });
        jPanel5.add(rbtnTodas);

        buttonGroup1.add(rSRadioButtonMaterial1);
        rSRadioButtonMaterial1.setText("Todas las OC");
        rSRadioButtonMaterial1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSRadioButtonMaterial1ActionPerformed(evt);
            }
        });
        jPanel5.add(rSRadioButtonMaterial1);

        jPanel2.add(jPanel5);

        jPanel6.add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridLayout(2, 0, 15, 15));

        jScrollPane2.setToolTipText("hola");

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NO. REQUISICION", "NO. EMPLEADO", "NO. COTIZACION", "ESTATUS", "ESTADO", "PROGRESO", "COSTO", "FECHA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(popMenu);
        Tabla1.setGridColor(new java.awt.Color(204, 204, 204));
        Tabla1.setRowHeight(25);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setResizable(false);
            Tabla1.getColumnModel().getColumn(1).setResizable(false);
            Tabla1.getColumnModel().getColumn(2).setResizable(false);
            Tabla1.getColumnModel().getColumn(3).setResizable(false);
            Tabla1.getColumnModel().getColumn(4).setResizable(false);
            Tabla1.getColumnModel().getColumn(5).setResizable(false);
            Tabla1.getColumnModel().getColumn(6).setResizable(false);
            Tabla1.getColumnModel().getColumn(7).setResizable(false);
        }

        jPanel1.add(jScrollPane2);

        Tabla2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NO. REQUISICION", "CODIGO", "DESCRIPCION", "PROYECTO", "CANTIDAD", "REQUISITOR", "LLEGO", "TE", "PROVEEDOR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Tabla2);
        if (Tabla2.getColumnModel().getColumnCount() > 0) {
            Tabla2.getColumnModel().getColumn(0).setResizable(false);
        }

        jPanel1.add(jScrollPane1);

        jPanel6.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel6, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        jMenuItem1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/documento.png"))); // NOI18N
        jMenuItem1.setText("Proyectos detalles");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator1);

        miVencido.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        miVencido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/max.png"))); // NOI18N
        miVencido.setText("TE Vencido");
        miVencido.setEnabled(false);
        miVencido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miVencidoActionPerformed(evt);
            }
        });
        jMenu1.add(miVencido);
        jMenu1.add(jSeparator2);

        jMenuItem2.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/excel_1.png"))); // NOI18N
        jMenuItem2.setText("Exportar tabla 1 a Excel                            ");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/excel_1.png"))); // NOI18N
        jMenuItem3.setText("Exportar tabla 2 a Excel                            ");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        
        if(evt.getClickCount() == 2){
            if(Tabla1.getValueAt(Tabla1.getSelectedRow(), 5).toString().equals("COTIZADO") && rbtnMis.isSelected()){
                JOptionPane.showMessageDialog(this, "ESTA ES BUENA");
            }else if(Tabla1.getValueAt(Tabla1.getSelectedRow(), 5).toString().equals("COTIZADO") && rbtnTodas.isSelected() && compras == true){
                JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                lib = new liberarCompra(f,true);
                lib.btnLiberar.addActionListener(this);
                lib.setVisible(true);
            }
        }
        if(tabla == 3){
            try{
                limpiarTabla2();
                Connection con = null;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();
                String sql = "select NumRequisicion, Codigo, Descripcion, Proyecto, Cantidad, NumParte, Llego, TE,OC, Notas, Proveedor, FechaRecibo, Precio, CantRecibida "
                        + "from requisiciones where OC like '"+Tabla1.getValueAt(Tabla1.getSelectedRow(), 0)+"' order by Llego asc";
                ResultSet rs = st.executeQuery(sql);
                String datos[] = new String[15];
                while(rs.next()){
                    datos[0] = rs.getString("NumRequisicion");
                    datos[1] = rs.getString("Codigo");
                    datos[2] = rs.getString("Descripcion");
                    datos[3] = rs.getString("Proyecto");
                    datos[4] = rs.getString("Cantidad");
                    datos[5] = rs.getString("NumParte");
                    datos[6] = rs.getString("Llego");
                    datos[7] = rs.getString("Precio");
                    datos[8] = rs.getString("TE");
                    datos[9] = rs.getString("OC");
                    datos[10] = rs.getString("Notas");
                    datos[11] = rs.getString("Proveedor");
                    datos[12] = rs.getString("FechaRecibo");
                    datos[13] = rs.getString("CantRecibida");
                    double cant;
                    try{cant = Double.parseDouble(datos[4]);}catch(Exception e){cant = 0;}
                    double cantR;
                    try{cantR = Double.parseDouble(datos[13]);}catch(Exception e){cantR = 0;}
                    datos[13] = String.valueOf(cant -  cantR);
                    miModelo.addRow(datos);
                }
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }else{
        try{
        limpiarTabla2();
        int fila = Tabla1.getSelectedRow();
        DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();
        
        
        Connection con = null;
        Conexion con1 = new Conexion();
        con = con1.getConnection();
        Statement st = con.createStatement();
        String sql;
        if(tabla == 5){
            sql = "select NumRequisicion, Codigo, Descripcion, Proyecto, Cantidad, NumParte, Llego, Precio, TE, OC, Notas, Proveedor, FechaRecibo, CantRecibida from Requisiciones where OC like '"+Tabla1.getValueAt(fila, 0).toString()+"' order by Llego asc";
        }else{
            sql = "select NumRequisicion, Codigo, Descripcion, Proyecto, Cantidad, NumParte, Llego, Precio, TE, OC, Notas, Proveedor, FechaRecibo, CantRecibida from Requisiciones where NumRequisicion like '"+Tabla1.getValueAt(fila, 0).toString()+"'  order by Llego asc";
        }
        ResultSet rs = st.executeQuery(sql);
        
        String datos[] = new String[15];
        
        while(rs.next()){
            datos[0] = rs.getString("NumRequisicion");
            datos[1] = rs.getString("Codigo");
            datos[2] = rs.getString("Descripcion");
            datos[3] = rs.getString("Proyecto");
            datos[4] = rs.getString("Cantidad");
            datos[5] = rs.getString("NumParte");
            datos[6] = rs.getString("Llego");
            datos[7] = rs.getString("Precio");
            datos[8] = rs.getString("TE");
            datos[9] = rs.getString("OC");
            datos[10] = rs.getString("Notas");
            datos[11] = rs.getString("Proveedor");
            datos[12] = rs.getString("FechaRecibo");
            datos[13] = rs.getString("CantRecibida");
            double cant;
            try{cant = Double.parseDouble(datos[4]);}catch(Exception e){cant = 0;}
            double cantR;
            try{cantR = Double.parseDouble(datos[13]);}catch(Exception e){cantR = 0;}
            datos[13] = String.valueOf(cant -  cantR);
            miModelo.addRow(datos);
        }
        
        }catch(SQLException e){
        JOptionPane.showMessageDialog(null, "ERROR AL MOSTRAR ITEMS: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
            JFrame j = (JFrame) JOptionPane.getFrameForComponent(this);
            DetallesProyectos d = new DetallesProyectos(j,false);
            d.setVisible(true);
            d.revalidate();
            d.repaint();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void miVencidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miVencidoActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        Reclamos r = new Reclamos(f,true);
        r.btnEditar.setEnabled(false);
        r.btnGuardar.setEnabled(false);
        r.setVisible(true);
    }//GEN-LAST:event_miVencidoActionPerformed

    private void verDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verDetallesActionPerformed
        int fila = Tabla1.getSelectedRow();
        if(fila < 0){
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UNA REQUISICION","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else{
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            Detalles d = new Detalles(f,true,Tabla1.getValueAt(fila, 0).toString());
            d.setVisible(true);
        }
        
    }//GEN-LAST:event_verDetallesActionPerformed

    private void rbtnMisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnMisActionPerformed
        limpiarTabla3();
        verDatos1();
        tabla = 0;
    }//GEN-LAST:event_rbtnMisActionPerformed

    private void rbtnTodasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnTodasActionPerformed
        System.out.println(compras);
        if(compras){
            limpiarTabla3();
        }
        seleccionarTabla();
        tabla = 1;
    }//GEN-LAST:event_rbtnTodasActionPerformed

    private void rSRadioButtonMaterial1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSRadioButtonMaterial1ActionPerformed
        limpiarTablaOrden();
        verDatosOrden();
        tabla = 3;
    }//GEN-LAST:event_rSRadioButtonMaterial1ActionPerformed

    private void tituloMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tituloMouseDragged
        
    }//GEN-LAST:event_tituloMouseDragged

    private void tituloMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tituloMousePressed
        
    }//GEN-LAST:event_tituloMousePressed

    private void lblXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseClicked
        dispose();
    }//GEN-LAST:event_lblXMouseClicked

    private void lblXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseEntered
        panelX.setBackground(Color.red);
        lblX.setForeground(Color.white);
    }//GEN-LAST:event_lblXMouseEntered

    private void lblXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseExited
        panelX.setBackground(Color.white);
        lblX.setForeground(Color.black);
    }//GEN-LAST:event_lblXMouseExited

    private void txtProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProyectoActionPerformed
        limpiarTabla2();
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String e = "";
            if(cmbBuscar.getSelectedItem().equals("NUMERO DE REQUISICION")){
                e = "NumRequisicion";
            }else if(cmbBuscar.getSelectedItem().equals("NUMERO DE PARTE")){
                e = "Codigo";
            }else if(cmbBuscar.getSelectedItem().equals("DESCRIPCION")){
                e = "Descripcion";
            }else if(cmbBuscar.getSelectedItem().equals("PROYECTO")){
                e = "Proyecto";
            }else if(cmbBuscar.getSelectedItem().equals("PROVEEDOR")){
                e = "Proveedor";
            }
            
            if("Proveedor".equals(e)){
                tablaProv1();
                limpiarTabla2();
                String sql = "select * from requisiciones where Proveedor like '"+txtProyecto.getText()+"%' and OC is not null order by Id desc";
                Statement st2 = con.createStatement();
                ResultSet rs = st2.executeQuery(sql);
                String datos[] = new String[15];
                //"O.C","NO. REQUISICION","NO. EMPLEADO","FECHA"
                DefaultTableModel model = (DefaultTableModel) Tabla1.getModel();
                while(rs.next()){
                    datos[0] = rs.getString("OC");
                    datos[1] = rs.getString("NumRequisicion");
                    datos[2] = rs.getString("Requisitor");
                    boolean f = true;
                    for (int i = 0; i < Tabla1.getRowCount(); i++) {
                        if(Tabla1.getValueAt(i, 0).toString().equals(datos[0])){
                            f = false;
                        }
                    }
                    if(f){
                        model.addRow(datos);
                    }
                }
            }else{
                limpiarTabla1();
                if(compras){
                    limpiarTabla3();
                }
                limpiarTabla2();
                
                String sql = "select * from requisiciones where "+e+" like '"+txtProyecto.getText()+"%' order by Id desc";
                Statement st2 = con.createStatement();
                ResultSet rs = st2.executeQuery(sql);
                String datos[] = new String[15];
                //"O.C","NO. REQUISICION","NO. EMPLEADO","FECHA"
                DefaultTableModel model = (DefaultTableModel) Tabla1.getModel();
                while(rs.next()){
                    datos[0] = rs.getString("NumRequisicion");
                    boolean f = true;
                    for (int i = 0; i < Tabla1.getRowCount(); i++) {
                        if(Tabla1.getValueAt(i, 0).toString().equals(datos[0])){
                            f = false;
                        }
                    }
                    if(f){
                        String sql2 = "select Id,NumeroEmpleado, NumeroCotizacion, Estatus, Estado, Progreso, Costo, Fecha from Requisicion where Id like '%"+datos[0]+"%' order by Id desc";
                        Statement st3 = con.createStatement();
                        ResultSet rs2 = st3.executeQuery(sql2);
                        String datos2[] = new String[10];
                        while(rs2.next()){
                            datos2[0] = rs2.getString("Id");
                            datos2[1] = rs2.getString("NumeroEmpleado");
                            datos2[2] = rs2.getString("NumeroCotizacion");
                            datos2[3] = rs2.getString("Estatus");
                            datos2[4] = rs2.getString("Estado");
                            datos2[5] = rs2.getString("Progreso");
                            datos2[6] = rs2.getString("Costo");
                            datos2[7] = rs2.getString("Fecha");
                        }
                        model.addRow(datos2);
                    }
                }
            }
            String sql = "select * from requisiciones where "+e+" like '%"+txtProyecto.getText()+"%' order by Id desc";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[15];
            DefaultTableModel model = (DefaultTableModel) Tabla2.getModel();
            while(rs.next()){
                //"NO. REQUISICION","CODIGO","DESCRIPCION","PROYECTO","CANTIDAD","REQUISITOR","LLEGO","PRECIO","TE","PO","NOTAS"
                datos[0] = rs.getString("NumRequisicion");
                String sql2 = "select Id, Progreso from requisicion where Id like '"+datos[0]+"'";
                Statement st2  = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql2);
                String can = "";
                while(rs2.next()){
                    can = rs2.getString("Progreso");
                }
                datos[1] = rs.getString("Codigo");
                datos[2] = rs.getString("Descripcion");
                datos[3] = rs.getString("Proyecto");
                datos[4] = rs.getString("Cantidad");
                datos[5] = rs.getString("Requisitor");
                datos[6] = rs.getString("Llego");
                datos[7] = rs.getString("Precio");
                datos[8] = rs.getString("TE");
                datos[9] = rs.getString("OC");
                datos[10] = rs.getString("Notas");
                if(can.equals("CANCELADO")){
                    datos[10] = "CANDELADO - " + datos[10];
                }
                datos[11] = rs.getString("Proveedor");
                datos[12] = rs.getString("FechaRecibo");
                datos[13] = rs.getString("CantRecibida");
                double cant;
                try{cant = Double.parseDouble(datos[4]);}catch(Exception ex){cant = 0;}
                double cantR;
                try{cantR = Double.parseDouble(datos[13]);}catch(Exception ex){cantR = 0;}
                datos[13] = String.valueOf(cant -  cantR);
                model.addRow(datos);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_txtProyectoActionPerformed

    private void cmbBuscarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbBuscarItemStateChanged
        if(cmbBuscar.getSelectedItem().equals("PROYECTO")){
            autoCompletar("proyectos","Proyecto");
        }else if(cmbBuscar.getSelectedItem().equals("NUMERO DE REQUISICION")){
            
        }else if(cmbBuscar.getSelectedItem().equals("NUMERO DE PARTE")){
            autoCompletar("requisiciones", "Codigo");
        }else if(cmbBuscar.getSelectedItem().equals("DESCRIPCION")){
            try{
                au.removeAllItems();
            }catch(Exception e){}
        }else if(cmbBuscar.getSelectedItem().equals("PROVEEDOR")){
            autoCompletar("registroprov_compras", "Nombre");
            tabla = 5;
        }
    }//GEN-LAST:event_cmbBuscarItemStateChanged

    private void verRelacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verRelacionesActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        Material mat = new Material(f,true,0);
        mat.btnGuardar.setVisible(false);
        mat.txtPlano.setVisible(false);
        mat.lblMaterial.setText("Requisicion: "+Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString());
        mat.verRelacion(Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString());
        mat.setVisible(true);
        
    }//GEN-LAST:event_verRelacionesActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        exportarExcel(Tabla1);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        exportarExcel(Tabla2);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void cmbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBuscarActionPerformed
        
    }//GEN-LAST:event_cmbBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JTable Tabla2;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private RSMaterialComponent.RSComboBoxMaterial cmbBuscar;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JLabel lblX;
    private javax.swing.JMenuItem miVencido;
    private javax.swing.JPanel panelX;
    private javax.swing.JPopupMenu popMenu;
    private RSMaterialComponent.RSRadioButtonMaterial rSRadioButtonMaterial1;
    private RSMaterialComponent.RSRadioButtonMaterial rbtnMis;
    private RSMaterialComponent.RSRadioButtonMaterial rbtnTodas;
    public javax.swing.JLabel titulo;
    private rojeru_san.RSMTextFull txtProyecto;
    private javax.swing.JMenuItem verDetalles;
    private javax.swing.JMenuItem verRelaciones;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if(lib != null){
            if(e.getSource() == lib.btnLiberar){
                try{
                    Connection con;
                    Conexion con1 = new Conexion();
                    con = con1.getConnection();
                    String sql = "update requisicion set Progreso = ?, Comprar = ? where Id = ?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    
                    pst.setString(1, "APROBACION");
                    pst.setString(2, "SI");
                    pst.setString(3, Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString());
                    
                    int n = pst.executeUpdate();
                    
                    if(n > 0){
                        limpiarTabla3();
                        seleccionarTabla();
                        JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                        lib.dispose();
                    }
                    
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, "ERRORR: "+ex,"ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
