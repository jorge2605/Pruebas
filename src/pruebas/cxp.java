package pruebas;

import Conexiones.Conexion;
import VentanaEmergente.cxp.extraerArticulo;
import VentanaEmergente.cxp.info;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
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

public class cxp extends javax.swing.JInternalFrame {

    public boolean bandView = true;
    
    public int filaSeleccionada = 0;
    
    public final void insertarSemanas(){
        cmbMes.removeAllItems();
        cmbAnio.removeAllItems();
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek( Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek( 4);
        int numberWeekOfYear = calendar.get(Calendar.YEAR);
        for (int i = numberWeekOfYear; i >= 2020; i--) {
            cmbAnio.addItem(String.valueOf(i));
        }
        
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        int mes = Integer.parseInt(sdf.format(d));
        
        String[] spanishMonthNames = DateFormatSymbols.getInstance(new Locale("es")).getMonths();
        for (int i = spanishMonthNames.length-1; i >= 0; i--) {
            if(i <= mes){
                cmbMes.addItem(spanishMonthNames[i]);
            }
        }
        
        cmbMes.removeItemAt(0);
    }
    
    public final void limpiarTabla(){
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "FOLIO", "FECHA FACTURA", "FECHA CAPTURA", "ARTICULOS", "IMPRIMIR", "VISTO", "ID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false,false
            };
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableColumn col;
        col = Tabla1.getColumnModel().getColumn(4);
        col.setCellEditor(new myeditorCxp(Tabla1,this));
        col.setCellRenderer(new rendererCxp(false));
        
        Tabla1.getTableHeader().setFont(new Font("Lexend", Font.BOLD, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
        Tabla1.setGridColor(new Color(240,240,240));
        
        jScrollPane1.getViewport().setBackground(new Color(255,255,255));
        
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(0).setMaxWidth(100);
            Tabla1.getColumnModel().getColumn(1).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(1).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(1).setMaxWidth(100);
            Tabla1.getColumnModel().getColumn(2).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(2).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(2).setMaxWidth(100);
            Tabla1.getColumnModel().getColumn(4).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(4).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(4).setMaxWidth(100);
            Tabla1.getColumnModel().getColumn(5).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(5).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(5).setMaxWidth(100);
            Tabla1.getColumnModel().getColumn(6).setMinWidth(0);
            Tabla1.getColumnModel().getColumn(6).setPreferredWidth(0);
            Tabla1.getColumnModel().getColumn(6).setMaxWidth(0);
        }
    }
    
    public final void verDatos(String sql){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            Object dat[] = new Object[10];
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            while(rs.next()){
                dat[0] = rs.getString("FolioFactura");
                dat[1] = rs.getString("FechaFactura");
                dat[2] = rs.getString("FechaCaptura");
                dat[3] = rs.getString("Articulos");
                dat[5] = rs.getBoolean("Visto");
                dat[6] = rs.getString("idfacturacion");
                miModelo.addRow(dat);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void applyBorderToRows(JTable table, int numRowsToBorder) {
        DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();

        DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                // Cambiar el borde para las filas específicas
                if (row >= filaSeleccionada && row < (numRowsToBorder + filaSeleccionada)) {
                    Border border = BorderFactory.createLineBorder(Color.RED); // Puedes cambiar el color del borde según tus preferencias
                    ((JComponent) c).setBorder(border);
                } else {
                    ((JComponent) c).setBorder(null); // Restaurar el borde a null para las demás filas
                }

                return c;
            }
        };

        // Aplicar el renderizador personalizado a todas las columnas
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }
    }
    
    public cxp() {
        initComponents();
        insertarSemanas();
        limpiarTabla();
        verDatos("select * from facturacion where Visto is null order by idfacturacion desc");
        applyBorderToRows(Tabla1, 15);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        pan = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        cmbMes = new RSMaterialComponent.RSComboBoxMaterial();
        cmbAnio = new RSMaterialComponent.RSComboBoxMaterial();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Lexend", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 165, 252));
        jLabel12.setText("CXP");
        jPanel6.add(jLabel12);

        jPanel5.add(jPanel6);

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
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 10));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/excel.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        btnView.setBackground(new java.awt.Color(255, 255, 255));
        btnView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/verOc_32.png"))); // NOI18N
        btnView.setBorder(null);
        btnView.setBorderPainted(false);
        btnView.setContentAreaFilled(false);
        btnView.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnView.setFocusPainted(false);
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });
        jPanel3.add(btnView);

        jPanel2.add(jPanel3, java.awt.BorderLayout.SOUTH);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        cmbMes.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        cmbMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMesActionPerformed(evt);
            }
        });
        jPanel8.add(cmbMes);

        cmbAnio.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        cmbAnio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAnioActionPerformed(evt);
            }
        });
        jPanel8.add(cmbAnio);

        jPanel7.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        Tabla1.setBackground(new java.awt.Color(255, 255, 255));
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FOLIO", "FECHA FACTURA", "FECHA CAPTURA", "ARTICULOS", "IMPRIMIR", "VISTO", "ID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

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
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(0).setMaxWidth(100);
            Tabla1.getColumnModel().getColumn(1).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(1).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(1).setMaxWidth(100);
            Tabla1.getColumnModel().getColumn(2).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(2).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(2).setMaxWidth(100);
            Tabla1.getColumnModel().getColumn(4).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(4).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(4).setMaxWidth(10);
            Tabla1.getColumnModel().getColumn(5).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(5).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(5).setMaxWidth(100);
            Tabla1.getColumnModel().getColumn(6).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(6).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(6).setMaxWidth(100);
        }

        jPanel7.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("         ");
        jPanel7.add(jLabel1, java.awt.BorderLayout.PAGE_END);

        jLabel2.setText("         ");
        jPanel7.add(jLabel2, java.awt.BorderLayout.EAST);

        jLabel3.setText("         ");
        jPanel7.add(jLabel3, java.awt.BorderLayout.WEST);

        jPanel2.add(jPanel7, java.awt.BorderLayout.CENTER);

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

    private void cmbMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMesActionPerformed
        
    }//GEN-LAST:event_cmbMesActionPerformed

    private void cmbAnioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAnioActionPerformed
        
    }//GEN-LAST:event_cmbAnioActionPerformed

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        filaSeleccionada = Tabla1.getSelectedRow();
        limpiarTabla();
        verDatos("select * from facturacion where Visto is null order by idfacturacion desc");
        applyBorderToRows(Tabla1, 15);
    }//GEN-LAST:event_Tabla1MouseClicked

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        if(bandView == false){
            btnView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/verOc_32.png")));
            bandView = true;
            limpiarTabla();
            verDatos("select * from facturacion where Visto is null order by idfacturacion desc");
        }else{
            btnView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/ver_32.png")));
            bandView = false;
            limpiarTabla();
            verDatos("select * from facturacion order by idfacturacion desc");
        }
    }//GEN-LAST:event_btnViewActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Workbook book;
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
            
            //------------------------------------------------------------------
            org.apache.poi.ss.usermodel.Font font1 = book.createFont();
            CellStyle style = book.createCellStyle();

            font1.setBold(true);
            font1.setColor(IndexedColors.BLACK.getIndex());
            font1.setFontHeightInPoints((short)18);
            font1.setFontName("Bahnschrift");
            style.setFont(font1);

            style.setVerticalAlignment(VerticalAlignment.BOTTOM);
            style.setAlignment(HorizontalAlignment.CENTER);
            
            //------------------------------------------------------------------
            org.apache.poi.ss.usermodel.Font font2 = book.createFont();
            CellStyle styleTituloFondoAzul = book.createCellStyle();

            font2.setBold(true);
            font2.setColor(IndexedColors.WHITE.getIndex());
            font2.setFontHeightInPoints((short)14);
            font2.setFontName("Bahnschrift");
            styleTituloFondoAzul.setFont(font2);

            ((XSSFCellStyle) styleTituloFondoAzul).setFillForegroundColor(new XSSFColor(new java.awt.Color(70, 112, 182)));
            styleTituloFondoAzul.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            styleTituloFondoAzul.setVerticalAlignment(VerticalAlignment.BOTTOM);
            styleTituloFondoAzul.setAlignment(HorizontalAlignment.CENTER);
            styleTituloFondoAzul.setWrapText(true);
            //--------------------------------------
            
            Sheet hoja = book.createSheet("REPORTE DE FACTURACION");
            
            Row fila = hoja.createRow(1);
            Cell col = fila.createCell(1);

            col.setCellStyle(style);
            col.setCellValue("Facturas Capturadas");
            
            Row fila31 = hoja.createRow(3);
            Cell col31 = fila31.createCell(1);

            col31.setCellStyle(styleTituloFondoAzul);
            col31.setCellValue("Folio");
            
//            fila31 = hoja.createRow(3);
            col31 = fila31.createCell(2);

            col31.setCellStyle(styleTituloFondoAzul);
            col31.setCellValue("Fecha Factura");
            
//            fila31 = hoja.createRow(3);
            col31 = fila31.createCell(3);

            col31.setCellStyle(styleTituloFondoAzul);
            col31.setCellValue("Fecha Captura");
            
//            fila31 = hoja.createRow(3);
            col31 = fila31.createCell(4);

            col31.setCellStyle(styleTituloFondoAzul);
            col31.setCellValue("Articulos");
            
//            fila31 = hoja.createRow(3);
            col31 = fila31.createCell(7);

            col31.setCellStyle(styleTituloFondoAzul);
            col31.setCellValue("Total");
            
            col31 = fila31.createCell(8);

            col31.setCellStyle(styleTituloFondoAzul);
            col31.setCellValue("Proveedor");
            //---------------------------------------
            int valorFijo = 28;
            
            hoja.setColumnWidth(0, valorFijo * 27);
            hoja.setColumnWidth(1, valorFijo * 220);
            hoja.setColumnWidth(2, valorFijo * 220);
            hoja.setColumnWidth(3, valorFijo * 220);
            hoja.setColumnWidth(4, valorFijo * 210);
            hoja.setColumnWidth(5, valorFijo * 210);
            hoja.setColumnWidth(6, valorFijo * 210);
            hoja.setColumnWidth(7, valorFijo * 280);
            hoja.setColumnWidth(8, valorFijo * 280);

            hoja.addMergedRegion(new CellRangeAddress (
            1,
            1,
            1,
            8
            ));
            
            //---------------Articulos
            hoja.addMergedRegion(new CellRangeAddress (
            3,
            3,
            4,
            6
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

            int acumFilaTotal = 0;
            int inicio = 0;
            int totalArticulos = 0;
            
            for (int i = filaSeleccionada; i < (15 + filaSeleccionada); i++) {
                Row fila10=hoja.createRow((i-filaSeleccionada)+4+acumFilaTotal);
                inicio = (i-filaSeleccionada) + 4 + acumFilaTotal;
                for (int j = 0; j < 4; j++) {
                    Cell celda=fila10.createCell(j+1);
                    CellStyle s;
                    if((i-filaSeleccionada) == -1 && (j >= 0 && j <=5)){
                        s = book.createCellStyle();
                        org.apache.poi.ss.usermodel.Font f = book.createFont();
                        f.setBold(true);
                        f.setColor(IndexedColors.WHITE.getIndex());
                        s.setFont(f);
                        s.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        s.setFillPattern(SOLID_FOREGROUND);
                        s.setAlignment(HorizontalAlignment.CENTER);
                        s.setVerticalAlignment(VerticalAlignment.CENTER);
                        celda.setCellStyle(s);
                    }
                    if((i-filaSeleccionada) > -1 && (j > -1 && j <= 5) && (i%2 == 0)){
                        s = book.createCellStyle();
                        s.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                        s.setAlignment(HorizontalAlignment.CENTER);
                        s.setVerticalAlignment(VerticalAlignment.CENTER);
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }
//                    CellStyle ss;
                    if((i-filaSeleccionada)==-1){
                        celda.setCellValue(String.valueOf(Tabla1.getColumnName(j)));
                    }else{
                            s = book.createCellStyle();
                            s.setWrapText(true);
                            s.setAlignment(HorizontalAlignment.CENTER);
                            s.setVerticalAlignment(VerticalAlignment.CENTER);
//                            if(i%2 == 0){
                                s.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                                ((XSSFCellStyle) s).setFillForegroundColor(new XSSFColor(new java.awt.Color(235, 235, 235)));
                                s.setFillPattern(SOLID_FOREGROUND);
//                            }
                            celda.setCellStyle(s);

                        if(j == 3){
                            extraerArticulo extraer = new extraerArticulo(String.valueOf(Tabla1.getValueAt(i, j)));
                            for (int k = 0; k < extraer.articulos.size(); k++) {
                                if(k == 0){
                                    String codigo = extraer.articulos.get(k).getCodigo();
                                    celda.setCellStyle(s);
                                    celda.setCellValue(codigo);
                                    double cantidad = extraer.articulos.get(k).getCantidad();
                                    double precio = extraer.articulos.get(k).getPrecio();
                                    double total = extraer.articulos.get(k).getTotal();
                                    
                                    celda=fila10.createCell(j+4);
                                    
                                    int cant = extraer.articulos.size();
                                    String rango = "";
                                    for (int l = 0; l < cant; l++) {
                                        if(l == 0){
                                            rango += "G"+(inicio+2);
                                        }else{
                                            rango += "+G"+(inicio+(2+(l * 2)));
                                        }
                                    }
                                    
                                    celda.setCellStyle(s);
                                    double iva = extraer.total.getIva();
                                    double isr = extraer.total.getIsr();
                                    celda.setCellFormula("("+rango+") + "+iva+"-"+isr);
                                    
                                    celda=fila10.createCell(j+5);
                                    celda.setCellStyle(s);
                                    celda.setCellValue(extraer.articulos.get(0).getProveedor());
                                    
                                    hoja.addMergedRegion(new CellRangeAddress (
                                    (i-filaSeleccionada)+4+acumFilaTotal,
                                    (i-filaSeleccionada)+4+acumFilaTotal,
                                    4,
                                    6
                                    ));
                                    
                                    
                                    acumFilaTotal++;
                                    
                                    fila10 = hoja.createRow((i-filaSeleccionada)+4+acumFilaTotal);
                                    
                                    celda=fila10.createCell(j+1);
                                    celda.setCellStyle(celda.getCellStyle());
                                    celda.setCellValue(precio);
                                    
                                    celda=fila10.createCell(j+2);
                                    celda.setCellStyle(celda.getCellStyle());
                                    celda.setCellValue(cantidad);
                                    
                                    celda=fila10.createCell(j+3);
                                    celda.setCellStyle(celda.getCellStyle());
                                    celda.setCellValue(total);
                                }else{
                                    acumFilaTotal++;
                                    fila10 = hoja.createRow((i-filaSeleccionada)+4+acumFilaTotal);
                                    
                                    String codigo = extraer.articulos.get(k).getCodigo();
//                                    celda.setCellValue(codigo);
                                    
                                    double cantidad = extraer.articulos.get(k).getCantidad();
                                    double precio = extraer.articulos.get(k).getPrecio();
                                    double total = extraer.articulos.get(k).getTotal();
                                    
                                    celda=fila10.createCell(j+1);
                                    celda.setCellStyle(s);
                                    celda.setCellValue(codigo);
                                    
                                    hoja.addMergedRegion(new CellRangeAddress (
                                    (i-filaSeleccionada)+4+acumFilaTotal,
                                    (i-filaSeleccionada)+4+acumFilaTotal,
                                    4,
                                    6
                                    ));
                                    
                                    
                                    acumFilaTotal++;
                                    
                                    fila10 = hoja.createRow((i-filaSeleccionada)+4+acumFilaTotal);
                                    
                                    celda=fila10.createCell(j+1);
                                    celda.setCellStyle(celda.getCellStyle());
                                    celda.setCellValue(precio);
                                    
                                    celda=fila10.createCell(j+2);
                                    celda.setCellStyle(celda.getCellStyle());
                                    celda.setCellValue(cantidad);
                                    
                                    celda=fila10.createCell(j+3);
                                    celda.setCellStyle(celda.getCellStyle());
                                    celda.setCellValue(total);
                                }
                            }
                            
                        }else{
                            celda.setCellValue(String.valueOf(Tabla1.getValueAt(i, j)));
                        }
                    }
                    
                }
            }
            book.write(new FileOutputStream(a));
            book.close();
            
            try {
            Desktop desktop = Desktop.getDesktop();

            if (desktop.isSupported(Desktop.Action.OPEN) && new File(a).exists()) {
                desktop.open(new File(a));
            } else {
                System.out.println("No se puede abrir el archivo automáticamente. Abre manualmente en Excel.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    public javax.swing.JButton btnView;
    private RSMaterialComponent.RSComboBoxMaterial cmbAnio;
    private RSMaterialComponent.RSComboBoxMaterial cmbMes;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelSalir;
    // End of variables declaration//GEN-END:variables
    
}

class rendererCxp extends JLabel implements TableCellRenderer {

    boolean isBordered = true;

    public rendererCxp(boolean isBordered) {
        this.isBordered = isBordered;
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object color, boolean isSelected, boolean hasFocus, int row, int column) {
            JButton boton = new JButton();
            boton.setBackground(new java.awt.Color(255, 255, 255));
            boton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/print_16.png"))); // NOI18N
            boton.setBorder(null);
            boton.setBorderPainted(false);
            boton.setContentAreaFilled(false);
            boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            boton.setFocusPainted(false);
            return boton;
    }
}

class myeditorCxp extends AbstractCellEditor implements TableCellEditor, ActionListener {

    Boolean currentValue;
    JButton button;
    cxp cxp;
    protected static final String EDIT = "edit";
    private JTable tabla1;

    public myeditorCxp(JTable jTable1, cxp cxp) {
        button = new JButton();
        button.setActionCommand(EDIT);
        button.addActionListener(this);
        button.setBorderPainted(false);
        this.tabla1 = jTable1;
        this.cxp = cxp;
    }

    public void actionPerformed(ActionEvent e) {
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(cxp);
        info info = new info(f,true, tabla1.getValueAt(tabla1.getSelectedRow(), 3).toString());
        info.lblId.setText(tabla1.getValueAt(tabla1.getSelectedRow(), 6).toString());
        info.setVisible(true);
        cxp.limpiarTabla();
        if(cxp.bandView == false){
            cxp.limpiarTabla();
            cxp.verDatos("select * from facturacion order by idfacturacion desc");
        }else{
            cxp.limpiarTabla();
            cxp.verDatos("select * from facturacion where Visto is null order by idfacturacion desc");
        }
        fireEditingStopped();
    }

    //Implement the one CellEditor method that AbstractCellEditor doesn't.
    public Object getCellEditorValue() {
        return currentValue;
    }

    //Implement the one method defined by TableCellEditor.
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentValue = (Boolean) value;
            return button;
    }
}