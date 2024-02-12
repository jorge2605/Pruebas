package pruebas;

import Conexiones.Conexion;
import VentanaEmergente.Inicio1.Espera;
import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.pdfbox.util.Hex;
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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class CargaTrabajo extends javax.swing.JInternalFrame {
    int acum4 = 0;
    int hrsCortes = 0;
    int hrsFresa = 0;
    int hrsCnc = 0;
    int hrsTorno = 0;
    String proyectos[];
    int contProy = 0;
    Inicio1 inicio;
    
    public void limpiarTabla1(){
        Tabla1.setBackground(new java.awt.Color(255, 255, 255));

Tabla1.setModel(new javax.swing.table.DefaultTableModel(
    new Object [][] {

    },
    new String [] {
        "PROYECTO", "FECHA DE ENTREGA", "CORTES", "FRESADORA", "CNC", "TORNO"
    }
) {
    boolean[] canEdit = new boolean [] {
        false, false, false, false, true, false
    };

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
    }
});

Tabla1.setRowHeight(25);
    }
    
    public void verCarga() {
        try {
            jPanel9.removeAll();
            revalidate();
            repaint();
            proyectos = new String[1000];
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String aux = "";
            int acum1 = 0,acum2 = 0,acum3 = 0,acum4 = 0;
            int aux1 = 0;
            Statement st1 = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            Statement st4 = con.createStatement();
            Statement st5 = con.createStatement();
            String sql1 = "select * from fresadora where Terminado like 'NO'";
            ResultSet rs1 = st1.executeQuery(sql1);
            
            String sql3 = "select * from cnc where Terminado like 'NO'";
            ResultSet rs3 = st3.executeQuery(sql3);
            
            String sql4 = "select * from torno where Terminado like 'NO'";
            ResultSet rs4 = st4.executeQuery(sql4);
            
            String sql5 = "select * from datos where Terminado like 'NO'";
            ResultSet rs5 = st5.executeQuery(sql5);
            
            String datos1[] = new String[10];
            while (rs1.next()) {
                datos1[0] = rs1.getString("Proyecto");
                datos1[1] = rs1.getString("Plano");
                String sql2 = "select Fresadora from Planos where Plano like '" + datos1[0] + "'";
                ResultSet rs2 = st2.executeQuery(sql2);
                
                if(datos1[1] != null){
                if(contProy == 0){
                    proyectos[contProy] = datos1[1];
                    contProy++;
                }else{
                    boolean band = false;
                    for (int i = 0; i < contProy; i++) {
                        if(datos1[1].equals(proyectos[i])){
                            band = true;
                        }
                    }
                    if(band == false){
                        proyectos[contProy] = datos1[1];
                            contProy++;
                    }
                    
                }
                }
                
                while(rs2.next()){
                datos1[1] = rs2.getString("Fresadora");
                if(datos1[1].equals("") || datos1[1].equals("0")){
                
                }else{
                hrsFresa++;
                String buscar = "/";
                char arreglo[] = datos1[1].toCharArray();
                for (int i = 0; i < datos1[1].length(); i++) {
                    String letra = String.valueOf(arreglo[i]);
                    if(buscar.equalsIgnoreCase(letra)){
                    aux1 = i;
                    }
                }
                
                aux = datos1[1].substring(0,aux1);
                int a = 0;
                try{
                    a = Integer.parseInt(aux);
                }catch(Exception ex){
                    System.out.println("Exception: "+ex);
                }
                acum1 = (acum1 + a);
                lblFresa.setText(""+(acum1)/60);
                }
                }
            }
            
            
            
            while (rs3.next()) {
                datos1[0] = rs3.getString("Proyecto");
                datos1[1] = rs3.getString("Plano");
                String sql2 = "select Cnc from Planos where Plano like '" + datos1[0] + "'";
                ResultSet rs2 = st2.executeQuery(sql2);
                
                if(datos1[1] != null){
                if(contProy == 0){
                    proyectos[contProy] = datos1[1];
                    contProy++;
                }else{
                    boolean band = false;
                    for (int i = 0; i < contProy; i++) {
                        if(datos1[1].equals(proyectos[i])){
                            band = true;
                        }
                    }
                    if(band == false){
                        proyectos[contProy] = datos1[1];
                            contProy++;
                    }
                    
                }
                }
                
                while(rs2.next()){
                datos1[1] = rs2.getString("Cnc");
                if(datos1[1].equals("") || datos1[1].equals("0")){
                
                }else{
                    hrsCnc++;
                String buscar = "/";
                char arreglo[] = datos1[1].toCharArray();
                for (int i = 0; i < datos1[1].length(); i++) {
                    String letra = String.valueOf(arreglo[i]);
                    if(buscar.equalsIgnoreCase(letra)){
                    aux1 = i;
                    }
                }
                
                aux = datos1[1].substring(0,aux1);
                int a = 0;
                try{
                    a = Integer.parseInt(aux);
                }catch(Exception ex){
                    System.out.println("Exception: "+ex);
                }
                acum2 = (acum2 + a);
                lblCnc.setText(""+(acum2)/60);
                }
                }
            }
            while (rs4.next()) {
                datos1[0] = rs4.getString("Proyecto");
                datos1[1] = rs4.getString("Plano");
                String sql2 = "select Torno from Planos where Plano like '" + datos1[0] + "'";
                ResultSet rs2 = st2.executeQuery(sql2);
                
                if(datos1[1] != null){
                if(contProy == 0){
                    proyectos[contProy] = datos1[1];
                    contProy++;
                }else{
                    boolean band = false;
                    for (int i = 0; i < contProy; i++) {
                        if(datos1[1].equals(proyectos[i])){
                            band = true;
                        }
                    }
                    if(band == false){
                        proyectos[contProy] = datos1[1];
                            contProy++;
                    }
                    
                }
                }
                
                while(rs2.next()){
                datos1[1] = rs2.getString("Torno");
                if(datos1[1].equals("") || datos1[1].equals("0")){
                
                }else{
                hrsTorno++;
                String buscar = "/";
                char arreglo[] = datos1[1].toCharArray();
                for (int i = 0; i < datos1[1].length(); i++) {
                    String letra = String.valueOf(arreglo[i]);
                    if(buscar.equalsIgnoreCase(letra)){
                    aux1 = i;
                    }
                }
                
                aux = datos1[1].substring(0,aux1);
                int a = 0;
                try{
                    a = Integer.parseInt(aux);
                }catch(Exception ex){
                    System.out.println("Exception: "+ex);
                }
                acum3 = (acum3 + a);
                lblTorno.setText(""+(acum3)/60);
                }
                }
            }
            while (rs5.next()) {
                datos1[0] = rs5.getString("Proyecto");
                datos1[1] = rs5.getString("Plano");
                String sql2 = "select Fresadora,Cnc,Torno from Planos where Plano like '" + datos1[0] + "'";
                ResultSet rs2 = st2.executeQuery(sql2);
                
                if(datos1[1] != null){
                if(contProy == 0){
                    proyectos[contProy] = datos1[1];
                    contProy++;
                }else{
                    boolean band = false;
                    for (int i = 0; i < contProy; i++) {
                        if(datos1[1].equals(proyectos[i])){
                            band = true;
                        }
                    }
                    if(band == false){
                        proyectos[contProy] = datos1[1];
                            contProy++;
                    }
                    
                }
                }
                
                while(rs2.next()){
                    try{
                        datos1[1] = rs2.getString("Fresadora");
                        datos1[2] = rs2.getString("Cnc");
                        datos1[3] = rs2.getString("Torno");
                        if(datos1[1].equals("") || datos1[1].equals("0")){

                        }else{
                            String buscar = "/";
                            char arreglo[] = datos1[1].toCharArray();
                            for (int i = 0; i < datos1[1].length(); i++) {
                                String letra = String.valueOf(arreglo[i]);
                                if(buscar.equalsIgnoreCase(letra)){
                                aux1 = i;
                                }
                            }

                            aux = datos1[1].substring(0,aux1);
                            int a = 0;
                            try{
                                a = Integer.parseInt(aux);
                            }catch(Exception ex){
                                System.out.println("Exception: "+ex);
                            }
                            acum4 = (acum4 + a);

                        }
                        if(datos1[2].equals("") || datos1[2].equals("0")){

                        }else{
                            String buscar = "/";
                            char arreglo[] = datos1[2].toCharArray();
                            for (int i = 0; i < datos1[2].length(); i++) {
                                String letra = String.valueOf(arreglo[i]);
                                if(buscar.equalsIgnoreCase(letra)){
                                aux1 = i;
                                }
                            }

                            aux = datos1[2].substring(0,aux1);
                            int a = 0;
                            try{
                                a = Integer.parseInt(aux);
                            }catch(Exception ex){
                                System.out.println("Exception: "+ex);
                            }
                            acum4 = (acum4 + a);

                        }
                        if(datos1[3].equals("") || datos1[3].equals("0")){

                        }else{
                            String buscar = "/";
                            char arreglo[] = datos1[3].toCharArray();
                            for (int i = 0; i < datos1[3].length(); i++) {
                                String letra = String.valueOf(arreglo[i]);
                                if(buscar.equalsIgnoreCase(letra)){
                                aux1 = i;
                                }
                            }

                            aux = datos1[3].substring(0,aux1);
                            int a = 0;
                            try{
                                a = Integer.parseInt(aux);
                            }catch(Exception ex){
                                System.out.println("Exception: "+ex);
                            }

                            acum4 = (acum4 + a);

                        }
                        lblCortes.setText(""+(acum4)/60);
                    }catch(Exception e){
                        
                    }
                }
            }
            
            int total = 0,t1 = 0,t2 = 0,t3 = 0,t4 = 0;
            try{
                t1 = Integer.parseInt(lblTorno.getText());
            }catch(NumberFormatException e){
                t1 = 0;
            }
            try{
                t2 = Integer.parseInt(lblCnc.getText());
            }catch(NumberFormatException e){
                t2 = 0;
            }
            try{
                t3 = Integer.parseInt(lblFresa.getText());
            }catch(NumberFormatException e){
                t3 = 0;
            }
            try{
                t4 = Integer.parseInt(lblCortes.getText());
            }catch(NumberFormatException e){
                t4 = 0;
            }
            
            total = t1+t2+t3+t4;
            lblTotal.setText(""+total);
            
            int cor = 0;
            int fresa = 0;
            int cnc = 0;
            int torno = 0;
            
            try{
                cor = Integer.parseInt(lblCortes.getText());
            }catch(NumberFormatException e){
                cor = 0;
            }
            try{
                fresa = Integer.parseInt(lblFresa.getText());
            }catch(NumberFormatException e){
               fresa = 0; 
            }
            try{
                cnc = Integer.parseInt(lblCnc.getText());
            }catch(NumberFormatException e){
                cnc = 0;
            }
            try{
                 torno = Integer.parseInt(lblTorno.getText());
            }catch(NumberFormatException e){
                torno = 0;
            }
            
            DefaultCategoryDataset dtsc = new DefaultCategoryDataset();
            dtsc.setValue(cor, "CORTES","CORTES");
            dtsc.setValue(fresa, "FRESADORA","FRESADORA");
            dtsc.setValue(cnc, "CNC","CNC");
            dtsc.setValue(torno, "TORNO","TORNO");
            JFreeChart ch = ChartFactory.createBarChart3D("CARGA DE TRABAJO", "MAQUINADOS", "HORAS", dtsc,PlotOrientation.HORIZONTAL, true, true, false);
            ChartPanel cp = new ChartPanel(ch);
            jPanel9.add(cp);
            cp.setBounds(650,150,600,400);
            jComboBox1.removeAllItems();
            jComboBox1.addItem("TODOS LOS PROYECTOS");
            for (int i = 0; i < contProy; i++) {
                jComboBox1.addItem(proyectos[i]);
            }
        } catch (SQLException E) {
            Logger.getLogger(CargaTrabajo.class.getName()).log(Level.SEVERE, null, E);
        }
    }

    public CargaTrabajo(Inicio1 inic) {
        initComponents();
    ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(51,126,255,255));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowGrid(false);
        inicio = inic;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblCortes = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblFresa = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblCnc = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblTorno = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel13 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        btnExcel = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        setBorder(null);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.GridLayout(2, 0));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.GridLayout(5, 2, 5, 5));

        jLabel1.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jLabel1.setText("CORTES");
        jPanel8.add(jLabel1);

        lblCortes.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jPanel8.add(lblCortes);

        jLabel4.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jLabel4.setText("FRESA");
        jPanel8.add(jLabel4);

        lblFresa.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jPanel8.add(lblFresa);

        jLabel5.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jLabel5.setText("CNC");
        jPanel8.add(jLabel5);

        lblCnc.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jPanel8.add(lblCnc);

        jLabel7.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jLabel7.setText("TORNO");
        jPanel8.add(jLabel7);

        lblTorno.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jPanel8.add(lblTorno);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(255, 102, 0));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 255));
        jButton1.setText("VER CARGA");
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton1);

        jPanel8.add(jPanel7);

        lblTotal.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jPanel8.add(lblTotal);

        jPanel5.add(jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.GridLayout(1, 0));
        jPanel5.add(jPanel9);

        jPanel4.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jComboBox1.setBackground(new java.awt.Color(255, 255, 255));
        jComboBox1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODOS LOS PROYECTOS" }));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel11);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jButton2.setBackground(new java.awt.Color(255, 102, 0));
        jButton2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 255));
        jButton2.setText("VER");
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton2);

        jPanel10.add(jPanel13);

        btnExcel.setBackground(new java.awt.Color(255, 255, 255));
        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/excel_1.png"))); // NOI18N
        btnExcel.setBorder(null);
        btnExcel.setBorderPainted(false);
        btnExcel.setContentAreaFilled(false);
        btnExcel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnExcel.setEnabled(false);
        btnExcel.setFocusPainted(false);
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });
        jPanel10.add(btnExcel);

        jPanel6.add(jPanel10, java.awt.BorderLayout.PAGE_START);

        jPanel12.setLayout(new java.awt.GridLayout(1, 0));

        Tabla1.setBackground(new java.awt.Color(255, 255, 255));
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PROYECTO", "FECHA DE ENTREGA", "CORTES", "FRESADORA", "CNC", "TORNO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setRowHeight(25);
        jScrollPane1.setViewportView(Tabla1);

        jPanel12.add(jScrollPane1);

        jPanel6.add(jPanel12, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel6);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText(" X ");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel3MouseExited(evt);
            }
        });
        btnSalir.add(jLabel3);

        jPanel14.add(btnSalir);

        jPanel2.add(jPanel14, java.awt.BorderLayout.EAST);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 48)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 165, 252));
        jLabel9.setText("Carga de trabajo");
        jPanel15.add(jLabel9);

        jPanel2.add(jPanel15, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Espera espera = new Espera();
        espera.activar();
        espera.setVisible(true);
        verCarga();
        espera.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Espera espera = new Espera();
        espera.activar();
        espera.setVisible(true);
        btnExcel.setEnabled(true);
        try{
            limpiarTabla1();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            int real = contProy;
            if(jComboBox1.getSelectedIndex() != 0){
                real = 1;
            }
            
                for (int j = 0; j < real; j++) {
                Statement st = con.createStatement();
                
                String proyecto = proyectos[j];
                if(jComboBox1.getSelectedIndex() != 0){
                proyecto = jComboBox1.getSelectedItem().toString();
                }
                
                String sql = "select Plano, Proyecto from fresadora where Plano like '"+proyecto+"' and Terminado like 'NO'";
                ResultSet rs = st.executeQuery(sql);
                
                String sql1 = "select Plano, Proyecto from cnc where Plano like '"+proyecto+"' and Terminado like 'NO'";
                Statement st1 = con.createStatement();
                ResultSet rs1 = st1.executeQuery(sql1);
                
                String sql3 = "select Plano, Proyecto from torno where Plano like '"+proyecto+"' and Terminado like 'NO'";
                Statement st3 = con.createStatement();
                ResultSet rs3 = st3.executeQuery(sql3);
                
                String sql4 = "select Plano, Proyecto from datos where Plano like '"+proyecto+"' and Terminado like 'NO'";
                Statement st4 = con.createStatement();
                ResultSet rs4 = st4.executeQuery(sql4);
                
                String datos[] = new String[15];
                
                String aux = "";
                int cont = 0;
                int acum = 0;
                int contP = 0;
                
                String aux1 = "";
                int cont1 = 0;
                int acum1 = 0;
                int contP1 = 0;
                
                String aux3 = "";
                int cont3 = 0;
                int acum3 = 0;
                int contP3 = 0;
                
                String aux4 = "";
                int cont4 = 0;
                int acum4 = 0;
                int contP4 = 0;
                
                while(rs.next()){
                    datos[0] = rs.getString("Proyecto");
                    String sql2 = "select Fresadora from Planos where Plano like '" + datos[0] + "'";
                    Statement st2 = con.createStatement();
                    ResultSet rs2 = st2.executeQuery(sql2);
                    
                    contP++;
                    
                    while(rs2.next()){
                    datos[1] = rs2.getString("Fresadora");
                    if(datos[1].equals("") || datos[1].equals("0")){

                    }else{
                    String buscar = "/";
                    char arreglo[] = datos[1].toCharArray();
                    for (int i = 0; i < datos[1].length(); i++) {
                        String letra = String.valueOf(arreglo[i]);
                        if(buscar.equalsIgnoreCase(letra)){
                        cont = i;
                        }
                    }

                    aux = datos[1].substring(0,cont);
                    if(aux.equals("")){
                        aux = "0";
                    }
                    int a = Integer.parseInt(aux);
                    acum = (acum + a);
                    }
                    }
            
                    
                }
                
                while(rs1.next()){
                    datos[0] = rs1.getString("Proyecto");
                    String sql2 = "select Cnc from Planos where Plano like '" + datos[0] + "'";
                    Statement st2 = con.createStatement();
                    ResultSet rs2 = st2.executeQuery(sql2);
                    
                    contP1++;
                    
                    while(rs2.next()){
                    datos[1] = rs2.getString("Cnc");
                    if(datos[1].equals("") || datos[1].equals("0")){

                    }else{
                    String buscar = "/";
                    char arreglo[] = datos[1].toCharArray();
                    for (int i = 0; i < datos[1].length(); i++) {
                        String letra = String.valueOf(arreglo[i]);
                        if(buscar.equalsIgnoreCase(letra)){
                        cont1 = i;
                        }
                    }

                    aux1 = datos[1].substring(0,cont1);
                    if(aux1.equals("")){
                        aux1 = "0";
                    }
                    int a = Integer.parseInt(aux1);
                    acum1 = (acum1 + a);
                    }
                    }
            
                    
                }
                
                while(rs3.next()){
                    datos[0] = rs3.getString("Proyecto");
                    String sql2 = "select Torno from Planos where Plano like '" + datos[0] + "'";
                    Statement st2 = con.createStatement();
                    ResultSet rs2 = st2.executeQuery(sql2);
                    
                    contP3++;
                    
                    while(rs2.next()){
                    datos[1] = rs2.getString("Torno");
                    if(datos[1].equals("") || datos[1].equals("0")){

                    }else{
                    String buscar = "/";
                    char arreglo[] = datos[1].toCharArray();
                    for (int i = 0; i < datos[1].length(); i++) {
                        String letra = String.valueOf(arreglo[i]);
                        if(buscar.equalsIgnoreCase(letra)){
                        cont3 = i;
                        }
                    }

                    aux3 = datos[1].substring(0,cont3);
                    if(aux3.equals("")){
                        aux3 = "0";
                    }
                    int a = Integer.parseInt(aux3);
                    acum3 = (acum3 + a);
                    }
                    }
                }
                
                while (rs4.next()) {
                datos[0] = rs4.getString("Proyecto");
                datos[1] = rs4.getString("Plano");
                String sql2 = "select Fresadora,Cnc,Torno from Planos where Plano like '" + datos[0] + "'";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql2);
                contP4++;
                while(rs2.next()){
                datos[1] = rs2.getString("Fresadora");
                datos[2] = rs2.getString("Cnc");
                datos[3] = rs2.getString("Torno");
                if(datos[1].equals("") || datos[1].equals("0")){
                
                }else{
                String buscar = "/";
                char arreglo[] = datos[1].toCharArray();
                for (int i = 0; i < datos[1].length(); i++) {
                    String letra = String.valueOf(arreglo[i]);
                    if(buscar.equalsIgnoreCase(letra)){
                    cont4 = i;
                    }
                }
                
                aux4 = datos[1].substring(0,cont4);
                if(aux4.equals("")){
                        aux4 = "0";
                    }
                int a = Integer.parseInt(aux4);
                
                acum4 = (acum4 + a);
                
                }
                if(datos[2].equals("") || datos[2].equals("0")){
                
                }else{
                String buscar = "/";
                char arreglo[] = datos[2].toCharArray();
                for (int i = 0; i < datos[2].length(); i++) {
                    String letra = String.valueOf(arreglo[i]);
                    if(buscar.equalsIgnoreCase(letra)){
                    cont4 = i;
                    }
                }
                
                aux4 = datos[2].substring(0,cont4);
                if(aux4.equals("")){
                        aux4 = "0";
                    }
                int a = Integer.parseInt(aux4);
                acum4 = (acum4 + a);
                
                }
                if(datos[3].equals("") || datos[3].equals("0")){
                
                }else{
                String buscar = "/";
                char arreglo[] = datos[3].toCharArray();
                for (int i = 0; i < datos[3].length(); i++) {
                    String letra = String.valueOf(arreglo[i]);
                    if(buscar.equalsIgnoreCase(letra)){
                    cont4 = i;
                    }
                }
                
                aux4 = datos[3].substring(0,cont4);
                if (aux4.equals("")){
                    aux4 = "0";
                }
                int a = Integer.parseInt(aux4);
                acum4 = (acum4 + a);
                
                }
                }
            }
             String sql5 = "select * from Proyectos where Proyecto like '"+proyecto+"'";
             Statement st5 = con.createStatement();
             ResultSet rs5 = st5.executeQuery(sql5);
             String fechaEntrega = "";
             while(rs5.next()){
                 fechaEntrega = rs5.getString("FechaEntrega");
             }
             String cortes = String.valueOf(acum4/60)+","+contP4;
             String fresa = String.valueOf(acum/60)+","+contP;
             String cnc = String.valueOf(acum1/60)+","+contP1;
             String torno = String.valueOf(acum3/60)+","+contP3;
             String tabla[] = {proyecto,fechaEntrega,cortes,fresa,cnc,torno};
             DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
             miModelo.addRow(tabla);
            }
            
            
        }catch(SQLException e){
            Logger.getLogger(OrdenDeCompra.class.getName()).log(Level.SEVERE, null, e);
        }
        espera.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
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
        
        Sheet hoja = book.createSheet("CARGA DE TRABAJO");
        Row fila = hoja.createRow(2);
        Cell col = fila.createCell(2);
        
        Row fila1 = hoja.createRow(4);
        Cell col1 = fila1.createCell(2);
        
        //-------------------------------ESTILOS
        org.apache.poi.ss.usermodel.Font font = book.createFont();
        XSSFCellStyle estilo1 = (XSSFCellStyle) book.createCellStyle();
        
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
        
//        hoja.setColumnWidth(2, 4000);
//        hoja.setColumnWidth(3, 6500);
//        hoja.setColumnWidth(4, 6500); 
//        hoja.setColumnWidth(5, 8200); 
        
        org.apache.poi.ss.usermodel.Font font1 = book.createFont();
        XSSFCellStyle style = (XSSFCellStyle) book.createCellStyle();
        
        font1.setBold(true);
        font1.setColor(IndexedColors.WHITE.getIndex());
        font1.setFontHeightInPoints((short)18);
        style.setFont(font1);
        
        String rgbS = "375623";
        byte[] rgbB = Hex.decodeHex(rgbS);
        XSSFColor color = new XSSFColor(rgbB, null);
        
        style.setFillBackgroundColor(color);
        style.setFillForegroundColor(color);
        style.setFillPattern(SOLID_FOREGROUND);
        style.setVerticalAlignment(VerticalAlignment.BOTTOM);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
        
        org.apache.poi.ss.usermodel.Font font2 = book.createFont();
        XSSFCellStyle estilo2 = (XSSFCellStyle) book.createCellStyle();
        
        font2.setBold(true);
        font2.setColor(IndexedColors.WHITE.getIndex());
        font2.setFontHeightInPoints((short)12);
        estilo2.setFont(font2);
        
        String rgbSt = "548235";
        byte[] rgbBt = Hex.decodeHex(rgbSt);
        XSSFColor colort = new XSSFColor(rgbBt, null);
        
        estilo2.setFillBackgroundColor(colort);
        estilo2.setFillForegroundColor(colort);
        estilo2.setFillPattern(SOLID_FOREGROUND);
        estilo2.setVerticalAlignment(VerticalAlignment.BOTTOM);
        estilo2.setAlignment(HorizontalAlignment.CENTER);
        estilo2.setWrapText(true);
        
        hoja.addMergedRegion(new CellRangeAddress (
        2,
        2,
        2,
        13
        ));
        
        hoja.addMergedRegion(new CellRangeAddress (
        4,
        4,
        2,
        3
        ));
        
        hoja.addMergedRegion(new CellRangeAddress (
        4,
        5,
        4,
        5
        ));
        
        hoja.addMergedRegion(new CellRangeAddress (
        4,
        4,
        6,
        7
        ));
        
        hoja.addMergedRegion(new CellRangeAddress (
        4,
        4,
        8,
        9
        ));
        
        hoja.addMergedRegion(new CellRangeAddress (
        4,
        4,
        10,
        11
        ));
        
        hoja.addMergedRegion(new CellRangeAddress (
        4,
        4,
        12,
        13
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
        col.setCellValue("CARGA DE TRABAJO");
        col1.setCellStyle(estilo1);
        
        Row fila10 = hoja.createRow(4);
        
        Cell col10 = fila10.createCell(2);
        col10.setCellStyle(estilo2);
        col10.setCellValue("PROYECTO");
        Cell col11 = fila10.createCell(4);
        col11.setCellStyle(estilo2);
        col11.setCellValue("FECHA DE ENTREGA");
        Cell col12 = fila10.createCell(6);
        col12.setCellStyle(estilo2);
        col12.setCellValue("CORTES");
        Cell col13 = fila10.createCell(8);
        col13.setCellStyle(estilo2);
        col13.setCellValue("FRESADORA");
        Cell col14 = fila10.createCell(10);
        col14.setCellStyle(estilo2);
        col14.setCellValue("CNC");
        Cell col15 = fila10.createCell(12);
        col15.setCellStyle(estilo2);
        col15.setCellValue("TORNO");
        
        Row fila5 = hoja.createRow(5);
        Cell col52 = fila5.createCell(2);
        col52.setCellStyle(estilo2);
        col52.setCellValue("");
        Cell col53 = fila5.createCell(3);
        col53.setCellStyle(estilo2);
        col53.setCellValue("");
        Cell col56 = fila5.createCell(6);
        col56.setCellStyle(estilo2);
        col56.setCellValue("hrs");
        Cell col57 = fila5.createCell(7);
        col57.setCellStyle(estilo2);
        col57.setCellValue("planos");
        Cell col58 = fila5.createCell(8);
        col58.setCellStyle(estilo2);
        col58.setCellValue("hrs");
        Cell col59 = fila5.createCell(9);
        col59.setCellStyle(estilo2);
        col59.setCellValue("planos");
        Cell col510 = fila5.createCell(10);
        col510.setCellStyle(estilo2);
        col510.setCellValue("hrs");
        Cell col511 = fila5.createCell(11);
        col511.setCellStyle(estilo2);
        col511.setCellValue("planos");
        Cell col512 = fila5.createCell(12);
        col512.setCellStyle(estilo2);
        col512.setCellValue("hrs");
        Cell col513 = fila5.createCell(13);
        col513.setCellStyle(estilo2);
        col513.setCellValue("planos");
            
        int cCortesH = 0;
        int cCortesP = 0;
        
        int cFresaH = 0;
        int cFresaP = 0;
        
        int cCncH = 0;
        int cCncP = 0;
        
        int cTornoH = 0;
        int cTornoP = 0;
        
        for (int i = 0; i < Tabla1.getRowCount(); i++) {
                Row fila11=hoja.createRow(i+6);
                for (int j = 0; j < 6; j++) {
                    Cell celda;
                    Cell celda2 = null;
                    switch (j) {
                        case 0:
                            celda = fila11.createCell(j+2);
                            break;
                        case 1:
                            celda = fila11.createCell(j+3);
                            break;
                        default:
                            int rea = (j*2)+2;
                            celda = fila11.createCell(rea);
                            celda2 = fila11.createCell(rea+1);
                            break;
                    }
                    
                    XSSFCellStyle s = (XSSFCellStyle) book.createCellStyle();
                        String rgbSt1 = "a9d08e";
                        byte[] rgbBt1 = Hex.decodeHex(rgbSt1);
                        XSSFColor colort1 = new XSSFColor(rgbBt1, null);
                    if(j == 0){
                        hoja.addMergedRegion(new CellRangeAddress (
                        i+6,
                        i+6,
                        2,
                        3
                        ));
                    }else if(j == 1){
                        hoja.addMergedRegion(new CellRangeAddress (
                        i+6,
                        i+6,
                        4,
                        5
                        ));
                    }
                    if(i > -1 && (j > -1 && j <= 6) && (i%2 == 0)){
                        
                        s.setFillForegroundColor(colort1);
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                        if(celda2 != null){
                          celda2.setCellStyle(s);  
                        }
                        
                    }
                    
                    
                        if(j != 0 && j != 1){
                            
                            String buscar = ",";
                            String aux = "", aux2 = "";
                            int cont = 0;
                            char arreglo[] = Tabla1.getValueAt(i, j).toString().toCharArray();
                            for (int l = 0; l < Tabla1.getValueAt(i, j).toString().length(); l++) {
                                String letra = String.valueOf(arreglo[l]);
                                if(buscar.equalsIgnoreCase(letra)){
                                cont = l;
                                }
                            }

                            aux = Tabla1.getValueAt(i, j).toString().substring(0,cont);
                            aux2 = Tabla1.getValueAt(i, j).toString().substring(cont+1,Tabla1.getValueAt(i, j).toString().length());
                            if(j == 2){
                                cCortesH = cCortesH + Integer.parseInt(aux);
                                cCortesP = cCortesP + Integer.parseInt(aux2);
                            } else if(j == 3){
                                cFresaH = cFresaH + Integer.parseInt(aux);
                                cFresaP = cFresaP + Integer.parseInt(aux2);
                            }else if(j == 4){
                                cCncH = cCncH + Integer.parseInt(aux);
                                cCncP = cCncH + Integer.parseInt(aux2);
                            } else if(j == 5){
                                cTornoH = cTornoH + Integer.parseInt(aux);
                                cTornoP = cTornoP + Integer.parseInt(aux2);
                            }
                            celda.setCellValue(Integer.parseInt(aux));
                            if(celda2 != null){
                            celda2.setCellValue(Integer.parseInt(aux2));
                            }
                            
                        }else{
                        celda.setCellValue(String.valueOf(Tabla1.getValueAt(i, j)));
//                        CellUtil.setCellStyleProperties(celda, properties);
                        }
                    if(i == Tabla1.getRowCount()-1){
                        Row row = hoja.createRow(Tabla1.getRowCount()+6);
                        
                        org.apache.poi.ss.usermodel.Font font4 = book.createFont();
                        XSSFCellStyle style4 = (XSSFCellStyle) book.createCellStyle();

                        font4.setBold(true);
                        font4.setColor(IndexedColors.WHITE.getIndex());
                        font4.setFontHeightInPoints((short)12);
                        style4.setFont(font4);

                        String rgbS4 = "375623";
                        byte[] rgbB4 = Hex.decodeHex(rgbS4);
                        XSSFColor color4 = new XSSFColor(rgbB4, null);

                        style4.setFillBackgroundColor(color4);
                        style4.setFillForegroundColor(color4);
                        style4.setFillPattern(SOLID_FOREGROUND);
                        style4.setVerticalAlignment(VerticalAlignment.BOTTOM);
                        style4.setAlignment(HorizontalAlignment.CENTER);
                        style4.setWrapText(true);
                        
                        Cell Col0 = row.createCell(5);
                        Cell Col1 = row.createCell(6);
                        Cell Col2 = row.createCell(7);
                        Cell Col3 = row.createCell(8);
                        Cell Col4 = row.createCell(9);
                        Cell Col5 = row.createCell(10);
                        Cell Col6 = row.createCell(11);
                        Cell Col7 = row.createCell(12);
                        Cell Col8 = row.createCell(13);
                        
                        Col0.setCellStyle(style4);
                        Col1.setCellStyle(style4);
                        Col2.setCellStyle(style4);
                        Col3.setCellStyle(style4);
                        Col4.setCellStyle(style4);
                        Col5.setCellStyle(style4);
                        Col6.setCellStyle(style4);
                        Col7.setCellStyle(style4);
                        Col8.setCellStyle(style4);
                        
                        Col0.setCellValue("TOTAL:");
                        Col1.setCellValue(cCortesH);
                        Col2.setCellValue(cFresaH);
                        Col3.setCellValue(cCncH);
                        Col4.setCellValue(cTornoH);
                        Col5.setCellValue(cCortesP);
                        Col6.setCellValue(cFresaP);
                        Col7.setCellValue(cCncP);
                        Col8.setCellValue(cTornoP);
                        
//                        hoja.addMergedRegion(new CellRangeAddress (
//                        (6+Tabla1.getRowCount()+2),
//                        (6+Tabla1.getRowCount()+2),
//                        6,
//                        7
//                        ));
//                        hoja.addMergedRegion(new CellRangeAddress (
//                        (6+Tabla1.getRowCount()+2),
//                        (6+Tabla1.getRowCount()+2),
//                        9,
//                        10
//                        ));
//                        
//                        org.apache.poi.ss.usermodel.Font font4 = book.createFont();
//                        XSSFCellStyle style4 = (XSSFCellStyle) book.createCellStyle();
//
//                        font4.setBold(true);
//                        font4.setColor(IndexedColors.WHITE.getIndex());
//                        font4.setFontHeightInPoints((short)12);
//                        style4.setFont(font4);
//
//                        String rgbS4 = "375623";
//                        byte[] rgbB4 = Hex.decodeHex(rgbS4);
//                        XSSFColor color4 = new XSSFColor(rgbB4, null);
//
//                        style4.setFillBackgroundColor(color4);
//                        style4.setFillForegroundColor(color4);
//                        style4.setFillPattern(SOLID_FOREGROUND);
//                        style4.setVerticalAlignment(VerticalAlignment.BOTTOM);
//                        style4.setAlignment(HorizontalAlignment.CENTER);
//                        style4.setWrapText(true);
//                        
//                        Row fila12 = hoja.createRow((6+Tabla1.getRowCount()+2));
//                        Cell colHoras = fila12.createCell(6);
//                        colHoras.setCellStyle(style4);
//                        colHoras.setCellValue("Horas totales");
//                        
//                        Cell colPlanos = fila12.createCell(9);
//                        colPlanos.setCellStyle(style4);
//                        colPlanos.setCellValue("Planos totales");
//                        
//                        for (int k = 0; k < 4; k++) {
//                            Row fila13 = hoja.createRow((6+Tabla1.getRowCount()+2+1));
//                            for (int l = 0; l < 5; l++) {
//                                Cell co13 = fila13.createCell(j+6);
//                                if(k == 0 && l == 0){
//                                    co13.setCellValue("Cortes:");
//                                }else if(k == 0 && l == 1){
//                                    co13.setCellValue(cCortesH);
//                                }
//                                if(k == 1 && l == 0){
//                                    co13.setCellValue("Fresadora:");
//                                }else if(k == 1 && l == 1){
//                                    co13.setCellValue(cFresaH);
//                                }
//                                if(k == 2 && l == 0){
//                                    co13.setCellValue("Cnc:");
//                                }else if(k == 2 && l == 1){
//                                    co13.setCellValue(cCncH);
//                                }
//                                if(k == 3 && l == 0){
//                                    co13.setCellValue("Torno:");
//                                }else if(k == 3 && l == 1){
//                                    co13.setCellValue(cTornoH);
//                                }
//                                
//                                if(k == 0 && l == 3){
//                                    co13.setCellValue("Cortes:");
//                                }else if(k == 0 && l == 4){
//                                    co13.setCellValue(cCortesP);
//                                }
//                                if(k == 1 && l == 3){
//                                    co13.setCellValue("Fresadora:");
//                                }else if(k == 1 && l == 4){
//                                    co13.setCellValue(cFresaP);
//                                }
//                                if(k == 2 && l == 3){
//                                    co13.setCellValue("Cnc:");
//                                }else if(k == 2 && l == 4){
//                                    co13.setCellValue(cCncP);
//                                }
//                                if(k == 3 && l == 3){
//                                    co13.setCellValue("Torno:");
//                                }else if(k == 3 && l == 4){
//                                    co13.setCellValue(cTornoP);
//                                }
//                            }
//                        }
                    }
                    File ad = new File(a);
                    book.write(new FileOutputStream(a));                
                }
            }
        book.close();
    } catch (FileNotFoundException ex) {
        Logger.getLogger(CargaTrabajo.class.getName()).log(Level.SEVERE, null, ex);
    }   catch (IOException ex) { 
            Logger.getLogger(CargaTrabajo.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }//GEN-LAST:event_btnExcelActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
        btnSalir.setBackground(java.awt.Color.red);
    }//GEN-LAST:event_jLabel3MouseEntered

    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseExited
        btnSalir.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_jLabel3MouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JButton btnExcel;
    private javax.swing.JPanel btnSalir;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCnc;
    private javax.swing.JLabel lblCortes;
    private javax.swing.JLabel lblFresa;
    private javax.swing.JLabel lblTorno;
    private javax.swing.JLabel lblTotal;
    // End of variables declaration//GEN-END:variables
}
