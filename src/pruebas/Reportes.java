package pruebas;

import Conexiones.Conexion;
import VentanaEmergente.Reportes.Plano;
import VentanaEmergente.Reportes.ReporteHerramienta;
import VentanaEmergente.Reportes.ReporteHoras;
import VentanaEmergente.Reportes.ReporteMensual;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
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

public class Reportes extends javax.swing.JInternalFrame {

    private TextAutoCompleter ac;
    String numEmpleado;
    
    public void limpiarTabla(){
        Tabla1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Proyecto", "Plano", "Cantidad", "Tipo de material", "Medidas", "Tiempo", "Realizado por", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setRowHeight(25);
    }

    public void verTerminados(String ubi, String bd){
        try {
            
            SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy");
            String fec = nuevo.format(calen.getDate());
            int diaSig = Integer.parseInt(fec.substring(0,2));
            diaSig += 1;
            String dia = diaSig + "/" + fec.substring(3,fec.length());
            Date da = nuevo.parse(dia);
            String fecSig = nuevo.format(da);
            lblUbi.setText(ubi);
            limpiarTabla();
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st1 = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            String datos[] = new String[10];
            String datos1[] = new String[10];

            if(btnEstacion.isSelected()){
                String sql2 = "select * from "+bd+" where Terminado like 'NO'";
                ResultSet rs2 = st2.executeQuery(sql2);
                while(rs2.next()){
                    datos1[4] = rs2.getString("FechaFinal");
                        datos[0] = rs2.getString("Plano");
                        datos[1] = rs2.getString("Proyecto");
                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
                        String cantidad = "", tipo = "";
                        Statement st5 = con.createStatement();
                        ResultSet rs5 = st5.executeQuery(sql4);
                        while(rs5.next()){
                            cantidad = rs5.getString("Cantidad");
                            tipo = rs5.getString("Material");
                        }
                        datos[2] = cantidad;
                        datos[3] = tipo;
                        datos[4] = "0";
                        datos[5] = rs2.getString("Cronometro");
                        datos[6] = rs2.getString("Empleado");
                        
                        miModelo.addRow(datos);
                }
            }else if(btnCurso.isSelected()){
                String sql3 = "select * from "+bd+" where FechaInicio != '' and FechaFinal like '' and Terminado like 'NO'";
                ResultSet rs3 = st3.executeQuery(sql3);
                while(rs3.next()){
                    datos1[4] = rs3.getString("FechaFinal");
                        datos[0] = rs3.getString("Plano");
                        datos[1] = rs3.getString("Proyecto");
                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
                        String cantidad = "", tipo = "";
                        Statement st5 = con.createStatement();
                        ResultSet rs5 = st5.executeQuery(sql4);
                        while(rs5.next()){
                            cantidad = rs5.getString("Cantidad");
                            tipo = rs5.getString("Material");
                        }
                        datos[2] = cantidad;
                        datos[3] = tipo;
                        datos[4] = "0";
                        datos[5] = rs3.getString("Cronometro");
                        datos[6] = rs3.getString("Empleado");
                        miModelo.addRow(datos);
                }
            }else if(btnTerminados.isSelected()){
                String sql3 = "select Terminado, FechaFinal, Plano, Proyecto,"
                        + "Cronometro, Empleado from "+bd+" where Terminado like 'SI' and FechaFinal != ''";
                ResultSet rs3 = st3.executeQuery(sql3);
                while(rs3.next()){
                    datos1[4] = rs3.getString("FechaFinal");
                    boolean turno = false;
                    if(turno1.isSelected()){
                        turno = true;
                    }
                    boolean sen1 = false;
                    
                    
                    String s = datos1[4].substring(0,10);
                    int hor =40;
                    try{
                    hor = Integer.parseInt(datos1[4].substring(11, 13));
                    }catch(Exception e){
//                        System.out.println("Exception: "+e);
                    }
                    if(turno){
                       if((fec.equals(s) && (hor >= 7 && hor <= 17))){
                        sen1 = true;
                        }
                    }else {
                        if((fec.equals(s) && (hor >=19)) || (fecSig.equals(s)) && (hor >= 19 || hor <= 6)){
                            sen1 = true;
                        }
                    }
                    
                    if(sen1){
                        
                        datos[0] = rs3.getString("Plano");
                        datos[1] = rs3.getString("Proyecto");
                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
                        String cantidad = "", tipo = "";
                        Statement st5 = con.createStatement();
                        ResultSet rs5 = st5.executeQuery(sql4);
                        while(rs5.next()){
                            cantidad = rs5.getString("Cantidad");
                            tipo = rs5.getString("Material");
                        }
                        datos[2] = cantidad;
                        datos[3] = tipo;
                        datos[4] = "0";
                        datos[5] = rs3.getString("Cronometro");
                        datos[6] = rs3.getString("Empleado");
                        datos[7] = rs3.getString("FechaFinal");
                        miModelo.addRow(datos);
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL VER BASE DE DATOS" + e);
        } catch (ParseException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void terminarPlano(String planoa, String numEmpleado){
        try{
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
            
            String sql = "select Plano, Proyecto, Terminado from datos where Proyecto like '"+planoa+"' and Terminado like 'NO'";
            ResultSet rs = st.executeQuery(sql);
            String plano;
            String sql2 = "update datos set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ? where Proyecto = ?";
            PreparedStatement pst = con.prepareStatement(sql2);
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat();
            String fecha = sdf.format(d);
            int n = 0;
            while(rs.next()){
                plano = rs.getString("Proyecto");
                pst.setString(1, fecha);
                pst.setString(2, fecha);
                pst.setString(3, "SI");
                pst.setString(4, numEmpleado + "," + numEmpleado);
                pst.setString(5, plano);
                
                n = pst.executeUpdate();
            }
            
            String sql3 = "select Plano, Proyecto, Terminado from acabados where Proyecto like '"+planoa+"' and Terminado like 'NO'";
            ResultSet rs3 = st3.executeQuery(sql3);
            String sql4 = "update acabados set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ? where Proyecto = ?";
            PreparedStatement pst4 = con.prepareStatement(sql4);
            int n1 = 0;
            while(rs3.next()){
                plano = rs3.getString("Proyecto");
                pst4.setString(1, fecha);
                pst4.setString(2, fecha);
                pst4.setString(3, "SI");
                pst4.setString(4, numEmpleado + "," + numEmpleado);
                pst4.setString(5, plano);
                
                n1 = pst4.executeUpdate();
            }
            
            String sql5 = "select Plano, Proyecto, Terminado from calidad where Proyecto like '"+planoa+"' and Terminado like 'NO'";
            ResultSet rs5 = st5.executeQuery(sql5);
            String sql6 = "update calidad set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ?, Tratamiento = ? where Proyecto = ?";
            PreparedStatement pst6 = con.prepareStatement(sql6);
            int n2 = 0;
            while(rs5.next()){
                plano = rs5.getString("Proyecto");
                pst6.setString(1, fecha);
                pst6.setString(2, fecha);
                pst6.setString(3, "SI");
                pst6.setString(4, numEmpleado + "," + numEmpleado);
                pst6.setString(5, "NO");
                pst6.setString(6, plano);
                
                n2 = pst6.executeUpdate();
            }
            
            String sql7 = "select Plano, Proyecto, Terminado from cnc where Proyecto like '"+planoa+"' and Terminado like 'NO'";
            ResultSet rs7 = st7.executeQuery(sql7);
            String sql8 = "update cnc set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ? where Proyecto = ?";
            PreparedStatement pst8 = con.prepareStatement(sql8);
            int n3 = 0;
            while(rs7.next()){
                plano = rs7.getString("Proyecto");
                pst8.setString(1, fecha);
                pst8.setString(2, fecha);
                pst8.setString(3, "SI");
                pst8.setString(4, numEmpleado + "," + numEmpleado);
                pst8.setString(5, plano);
                
                n3 = pst8.executeUpdate();
            }
            
            String sql9 = "select Plano, Proyecto, Terminado from fresadora where Proyecto like '"+planoa+"' and Terminado like 'NO'";
            ResultSet rs9 = st9.executeQuery(sql9);
            String sql10 = "update fresadora set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ? where Proyecto = ?";
            PreparedStatement pst10 = con.prepareStatement(sql10);
            int n4 = 0;
            while(rs9.next()){
                plano = rs9.getString("Proyecto");
                pst10.setString(1, fecha);
                pst10.setString(2, fecha);
                pst10.setString(3, "SI");
                pst10.setString(4, numEmpleado + "," + numEmpleado);
                pst10.setString(5, plano);
                
                n4 = pst10.executeUpdate();
            }
            
            String sql11 = "select Plano, Proyecto, Terminado from torno where Proyecto like '"+planoa+"' and Terminado like 'NO'";
            ResultSet rs11 = st11.executeQuery(sql11);
            String sql12 = "update torno set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ? where Proyecto = ?";
            PreparedStatement pst12 = con.prepareStatement(sql12);
            int n5 = 0;
            while(rs11.next()){
                plano = rs11.getString("Proyecto");
                pst12.setString(1, fecha);
                pst12.setString(2, fecha);
                pst12.setString(3, "SI");
                pst12.setString(4, numEmpleado + "," + numEmpleado);
                pst12.setString(5, plano);
                
                n5 = pst12.executeUpdate();
            }
            
            String sql13 = "select Plano, Proyecto, Prioridad from planos where Plano like '"+planoa+"'";
            ResultSet rs13 = st13.executeQuery(sql13);
            String sql14 = "insert into calidad (Proyecto, Plano, FechaInicio, FechaFinal, Terminado,"
                    + "Estado, Tratamiento, Cronometro, Prioridad, Empleado) values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst14 = con.prepareStatement(sql14);
            String pl = null;
            String pr = null;
            String pri = null;
            int n6 = 0;
            while(rs13.next()){
                pl = rs13.getString("Plano");
                pr = rs13.getString("Proyecto");
                pri = rs13.getString("Prioridad");
                String sql15 = "select Proyecto from calidad where Proyecto like '"+pl+"'";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql15);
                String exis = null;
                while(rs2.next()){
                    exis = rs2.getString("Proyecto");
                }
                if(exis == null){
                    pst14.setString(1, pl);
                    pst14.setString(2, pr);
                    pst14.setString(3, fecha);
                    pst14.setString(4, fecha);
                    pst14.setString(5, "SI");
                    pst14.setString(6, "");
                    pst14.setString(7, "NO");
                    pst14.setString(8, "00:00");
                    pst14.setString(9, pri);
                    pst14.setString(10, numEmpleado + "," + numEmpleado);
                    
                    n6 = pst14.executeUpdate();
                }
            }
            
            if(n > 0 && n1 > 0 && n2 > 0 && n3 > 0 && n4 > 0 && n5 > 0){
                JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
            }else{
                if(n6 > 0){
                    String faltantes = "";
                    if(n < 1){
                        faltantes += "\nCortes";
                    }
                    if(n1 < 1){
                        faltantes += "\nAcabados";
                    }
                    if(n2 < 1){
                        faltantes += "\nCalidad";
                    }
                    if(n3 < 1){
                        faltantes += "\nCnc";
                    }
                    if(n4 < 1){
                        faltantes += "\nFresadora";
                    }
                    if(n5 < 1){
                        faltantes += "\nTorno";
                    }

                    JOptionPane.showMessageDialog(this, "DATOS GUARDADOS, SIN CAMBIOS EN: "
                            + faltantes);
                }else{
                    JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                }
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: " + e,"ERROR",JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public Reportes(String numEmpleado) {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        Tabla1.setShowVerticalLines(false);
        Tabla1.setShowHorizontalLines(false);
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.getTableHeader().setFont(new Font("Roboto",Font.BOLD,12));
        Tabla1.setRowHeight(25);
        jScrollPane1.getViewport().setBackground(new Color(255,255,255));
        jScrollPane1.setVerticalScrollBar(new ScrollBarCustom(new Color(0,165,255)));
        Date fe = new Date();
        calen.setDate(fe);
        this.numEmpleado = numEmpleado;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        TerminarPlanos = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        lblEstado = new javax.swing.JLabel();
        lblUbi = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        turno1 = new javax.swing.JRadioButton();
        turno2 = new javax.swing.JRadioButton();
        jPanel14 = new javax.swing.JPanel();
        calen = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        txtProyecto = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnTerminados = new javax.swing.JToggleButton();
        btnCurso = new javax.swing.JToggleButton();
        btnEstacion = new javax.swing.JToggleButton();
        btnCortes = new javax.swing.JButton();
        txtCortes = new javax.swing.JTextField();
        txtCortes1 = new javax.swing.JTextField();
        txtCortes2 = new javax.swing.JTextField();
        btnFresa = new javax.swing.JButton();
        txtFresa = new javax.swing.JTextField();
        txtFresa1 = new javax.swing.JTextField();
        txtFresa2 = new javax.swing.JTextField();
        btnCnc = new javax.swing.JButton();
        txtCnc = new javax.swing.JTextField();
        txtCnc1 = new javax.swing.JTextField();
        txtCnc2 = new javax.swing.JTextField();
        btnTorno = new javax.swing.JButton();
        txtTorno = new javax.swing.JTextField();
        txtTorno1 = new javax.swing.JTextField();
        txtTorno2 = new javax.swing.JTextField();
        btnAcabados = new javax.swing.JButton();
        txtAcabados = new javax.swing.JTextField();
        txtAcabados1 = new javax.swing.JTextField();
        txtAcabados2 = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        TerminarPlanos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        TerminarPlanos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/eliminar (1).png"))); // NOI18N
        TerminarPlanos.setText("        Terminar Plano(s)             ");
        TerminarPlanos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TerminarPlanosActionPerformed(evt);
            }
        });
        jPopupMenu1.add(TerminarPlanos);

        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(15, 15));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.GridLayout(1, 2, 20, 0));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(null);

        Tabla1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Proyecto", "Plano", "Cantidad", "Tipo de material", "Medidas", "Tiempo", "Realizado por", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        Tabla1.setRowHeight(25);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.BorderLayout(15, 15));

        lblEstado.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblEstado.setForeground(new java.awt.Color(204, 102, 0));
        lblEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEstado.setText("PLANOS TERMINADOS");
        jPanel7.add(lblEstado, java.awt.BorderLayout.CENTER);

        lblUbi.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblUbi.setForeground(new java.awt.Color(204, 102, 0));
        lblUbi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUbi.setText("PLANOS");
        jPanel7.add(lblUbi, java.awt.BorderLayout.PAGE_START);

        jPanel4.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/excel_1.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton1);

        jPanel4.add(jPanel6, java.awt.BorderLayout.PAGE_END);

        jPanel5.add(jPanel4);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout(15, 15));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.BorderLayout(15, 15));

        jLabel1.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Buscar por fecha");
        jPanel9.add(jLabel1, java.awt.BorderLayout.NORTH);

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/buscar_24.png"))); // NOI18N
        btnBuscar.setBorder(null);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setContentAreaFilled(false);
        btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnBuscar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/buscar_24.png"))); // NOI18N
        btnBuscar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/buscar_32.png"))); // NOI18N
        btnBuscar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel9.add(btnBuscar, java.awt.BorderLayout.LINE_END);

        jPanel12.setBackground(new java.awt.Color(153, 204, 255));
        jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 5));

        turno1.setBackground(new java.awt.Color(153, 204, 255));
        buttonGroup1.add(turno1);
        turno1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        turno1.setSelected(true);
        turno1.setText("Turno No. 1 (07:00 a 17:00)");
        jPanel12.add(turno1);

        turno2.setBackground(new java.awt.Color(153, 204, 255));
        buttonGroup1.add(turno2);
        turno2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        turno2.setText("Turno No.2 (18:00 a 06:00)");
        jPanel12.add(turno2);

        jPanel9.add(jPanel12, java.awt.BorderLayout.PAGE_END);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new java.awt.GridLayout(3, 0, 10, 10));

        calen.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.add(calen);

        jLabel5.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Buscar por proyecto");
        jPanel14.add(jLabel5);

        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtProyecto.setPreferredSize(new java.awt.Dimension(300, 22));
        txtProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProyectoActionPerformed(evt);
            }
        });
        jPanel14.add(txtProyecto);

        jPanel9.add(jPanel14, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel9, java.awt.BorderLayout.PAGE_START);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.GridLayout(6, 4, 10, 10));

        jLabel4.setText("   ");
        jPanel10.add(jLabel4);

        btnTerminados.setBackground(new java.awt.Color(180, 198, 231));
        btnTerminados.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnTerminados.setSelected(true);
        btnTerminados.setText("<html>\n<p style=\"text-align: center;\">Planos</p>\n<p style=\"text-align: center;\">Terminados</p>\n</html>");
        btnTerminados.setBorder(null);
        btnTerminados.setBorderPainted(false);
        btnTerminados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTerminados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTerminadosActionPerformed(evt);
            }
        });
        jPanel10.add(btnTerminados);

        btnCurso.setBackground(new java.awt.Color(142, 169, 219));
        btnCurso.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnCurso.setText("<html>\n<p style=\"text-align: center;\">Planos</p>\n<p style=\"text-align: center;\">En curso</p>\n</html>");
        btnCurso.setBorder(null);
        btnCurso.setBorderPainted(false);
        btnCurso.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCursoActionPerformed(evt);
            }
        });
        jPanel10.add(btnCurso);

        btnEstacion.setBackground(new java.awt.Color(48, 84, 150));
        btnEstacion.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnEstacion.setText("<html>\n<p style=\"text-align: center;\">Planos</p>\n<p style=\"text-align: center;\">En estacion</p>\n</html>");
        btnEstacion.setBorder(null);
        btnEstacion.setBorderPainted(false);
        btnEstacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEstacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstacionActionPerformed(evt);
            }
        });
        jPanel10.add(btnEstacion);

        btnCortes.setBackground(new java.awt.Color(255, 255, 255));
        btnCortes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/segueta.png"))); // NOI18N
        btnCortes.setToolTipText("ACABADOS");
        btnCortes.setBorder(null);
        btnCortes.setBorderPainted(false);
        btnCortes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCortes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCortesActionPerformed(evt);
            }
        });
        jPanel10.add(btnCortes);

        txtCortes.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtCortes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCortes.setEnabled(false);
        jPanel10.add(txtCortes);

        txtCortes1.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtCortes1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCortes1.setEnabled(false);
        jPanel10.add(txtCortes1);

        txtCortes2.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtCortes2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCortes2.setEnabled(false);
        jPanel10.add(txtCortes2);

        btnFresa.setBackground(new java.awt.Color(255, 255, 255));
        btnFresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fresadora.png"))); // NOI18N
        btnFresa.setToolTipText("FRESADORA");
        btnFresa.setBorder(null);
        btnFresa.setBorderPainted(false);
        btnFresa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFresaActionPerformed(evt);
            }
        });
        jPanel10.add(btnFresa);

        txtFresa.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtFresa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFresa.setEnabled(false);
        jPanel10.add(txtFresa);

        txtFresa1.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtFresa1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFresa1.setEnabled(false);
        jPanel10.add(txtFresa1);

        txtFresa2.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtFresa2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFresa2.setEnabled(false);
        jPanel10.add(txtFresa2);

        btnCnc.setBackground(new java.awt.Color(255, 255, 255));
        btnCnc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/maquina.png"))); // NOI18N
        btnCnc.setToolTipText("CNC");
        btnCnc.setBorder(null);
        btnCnc.setBorderPainted(false);
        btnCnc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCnc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCncActionPerformed(evt);
            }
        });
        jPanel10.add(btnCnc);

        txtCnc.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtCnc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCnc.setEnabled(false);
        jPanel10.add(txtCnc);

        txtCnc1.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtCnc1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCnc1.setEnabled(false);
        jPanel10.add(txtCnc1);

        txtCnc2.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtCnc2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCnc2.setEnabled(false);
        jPanel10.add(txtCnc2);

        btnTorno.setBackground(new java.awt.Color(255, 255, 255));
        btnTorno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/torno.png"))); // NOI18N
        btnTorno.setToolTipText("TORNO");
        btnTorno.setBorder(null);
        btnTorno.setBorderPainted(false);
        btnTorno.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTorno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTornoActionPerformed(evt);
            }
        });
        jPanel10.add(btnTorno);

        txtTorno.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtTorno.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTorno.setEnabled(false);
        jPanel10.add(txtTorno);

        txtTorno1.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtTorno1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTorno1.setEnabled(false);
        jPanel10.add(txtTorno1);

        txtTorno2.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtTorno2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTorno2.setEnabled(false);
        jPanel10.add(txtTorno2);

        btnAcabados.setBackground(new java.awt.Color(255, 255, 255));
        btnAcabados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Acabados.png"))); // NOI18N
        btnAcabados.setToolTipText("ACABADOS");
        btnAcabados.setBorder(null);
        btnAcabados.setBorderPainted(false);
        btnAcabados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAcabados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcabadosActionPerformed(evt);
            }
        });
        jPanel10.add(btnAcabados);

        txtAcabados.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtAcabados.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAcabados.setEnabled(false);
        jPanel10.add(txtAcabados);

        txtAcabados1.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtAcabados1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAcabados1.setEnabled(false);
        jPanel10.add(txtAcabados1);

        txtAcabados2.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtAcabados2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAcabados2.setEnabled(false);
        jPanel10.add(txtAcabados2);

        jPanel3.add(jPanel10, java.awt.BorderLayout.CENTER);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.GridLayout(3, 0));

        jLabel2.setText(" ");
        jPanel11.add(jLabel2);

        jButton2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton2.setText("<html>\n<p>T</p>\n<p>A</p>\n<p>L</p>\n<p>L</p>\n<p>E</p>\n<p>R</p>\n</html>");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton2);

        jLabel7.setText(" ");
        jPanel11.add(jLabel7);

        jPanel3.add(jPanel11, java.awt.BorderLayout.WEST);

        jPanel5.add(jPanel3);

        jPanel1.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

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

        jPanel13.add(btnSalir);

        jPanel2.add(jPanel13, java.awt.BorderLayout.EAST);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 48)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 165, 252));
        jLabel9.setText("Reportes");
        jPanel8.add(jLabel9);

        jPanel2.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/completed-task.png"))); // NOI18N
        jMenuItem1.setText("Reportes de calidad");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/ventas_16.png"))); // NOI18N
        jMenuItem2.setText("Reporte de ventas");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/documento.png"))); // NOI18N
        jMenuItem3.setText("Reporte de planos");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/noti_16.png"))); // NOI18N
        jMenuItem4.setText("Reporte mensual");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/entrega-rapida.png"))); // NOI18N
        jMenuItem5.setText("Reporte herramienta");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCortesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCortesActionPerformed
        verTerminados("CORTES", "datos");
    }//GEN-LAST:event_btnCortesActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
            String fec = fecha.format(calen.getDate());
            int diaSig = Integer.parseInt(fec.substring(0,2));
            diaSig += 1;
            String dia = diaSig + "/" + fec.substring(3,fec.length());
            Date da = fecha.parse(dia);
            String fecSig = fecha.format(da);
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st1 = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            Statement st4 = con.createStatement();
            Statement st5 = con.createStatement();
            String sql1 = "select FechaFinal, Proyecto, Plano,Cronometro, Empleado, Terminado,FechaInicio from datos where FechaFinal != null or FechaFinal != ''";
            ResultSet rs1 = st1.executeQuery(sql1);
            String sql2 = "select FechaFinal, Proyecto, Plano,Cronometro, Empleado, Terminado,FechaInicio from fresadora where FechaFinal != null or FechaFinal != ''";
            ResultSet rs2 = st2.executeQuery(sql2);
            String sql3 = "select FechaFinal, Proyecto, Plano,Cronometro, Empleado, Terminado,FechaInicio from cnc where FechaFinal != null or FechaFinal != ''";
            ResultSet rs3 = st3.executeQuery(sql3);
            String sql4 = "select FechaFinal, Proyecto, Plano,Cronometro, Empleado, Terminado,FechaInicio from torno where FechaFinal != null or FechaFinal != ''";
            ResultSet rs4 = st4.executeQuery(sql4);
            String sql5 = "select FechaFinal, Proyecto, Plano,Cronometro, Empleado, Terminado,FechaInicio from acabados where FechaFinal != null or FechaFinal != ''";
            ResultSet rs5 = st5.executeQuery(sql5);
            String datos1[] = new String[10];
            String datos2[] = new String[10];
            String datos3[] = new String[10];
            String datos4[] = new String[10];
            String datos5[] = new String[10];
            int cont1 = 0, cont2 = 0, cont22 = 0, cont3 = 0, cont33 = 0, cont4 = 0, cont44 = 0, cont5 = 0, cont55 = 0, cont6 = 0;

            while (rs1.next()) {
                datos1[0] = rs1.getString("Proyecto");
                datos1[1] = rs1.getString("Plano");
                datos1[2] = rs1.getString("Cronometro");
                datos1[3] = rs1.getString("Empleado");
                datos1[4] = rs1.getString("FechaFinal");
                datos1[5] = rs1.getString("Terminado");
                datos1[6] = rs1.getString("FechaInicio");
                if (datos1[4] != null || !"".equals(datos1[4])) {
                    String f = datos1[4].substring(0, 10);
                    int hor =40;
                    try{
                    hor = Integer.parseInt(datos1[4].substring(11, 13));
                    }catch(Exception e){
//                        System.out.println("Exception: "+e);
                    }
                    if ((fec.equals(f) && (hor >= 7 && hor <= 17))) {
                        cont1++;
                    }else if((fec.equals(f) && (hor >=19)) || (fecSig.equals(f)) && (hor >= 19 || hor <= 6)){
                        cont6++;
                    }
                }

            }
            while (rs2.next()) {
                datos2[0] = rs2.getString("Proyecto");
                datos2[1] = rs2.getString("Plano");
                datos2[2] = rs2.getString("Cronometro");
                datos2[3] = rs2.getString("Empleado");
                datos2[4] = rs2.getString("FechaFinal");
                datos2[5] = rs2.getString("Terminado");
                if (datos2[4] != null || !"".equals(datos2[4])) {
                    String f = datos2[4].substring(0, 10);
                    int hor =40;
                    try{
                    hor = Integer.parseInt(datos2[4].substring(11, 13));
                    }catch(Exception e){
//                        System.out.println("Exception: "+e);
                    }
                    if ((fec.equals(f) && (hor >= 7 && hor <= 17))) {
                        cont2++;
                    }else if((fec.equals(f) && (hor >=19)) || (fecSig.equals(f)) && (hor >= 19 || hor <= 6)){
                        cont22++;
                    }
                }
            }
            while (rs3.next()) {
                datos3[0] = rs3.getString("Proyecto");
                datos3[1] = rs3.getString("Plano");
                datos3[2] = rs3.getString("Cronometro");
                datos3[3] = rs3.getString("Empleado");
                datos3[4] = rs3.getString("FechaFinal");
                datos3[5] = rs3.getString("Terminado");
                if (datos3[4] != null || !"".equals(datos3[4])) {
                    String f = datos3[4].substring(0, 10);
                    int hor =40;
                    try{
                    hor = Integer.parseInt(datos3[4].substring(11, 13));
                    }catch(Exception e){
//                        System.out.println("Exception: "+e);
                    }
                    if ((fec.equals(f) && (hor >= 7 && hor <= 17))) {
                        cont3++;
                    }else if((fec.equals(f) && (hor >=19)) || (fecSig.equals(f)) && (hor >= 19 || hor <= 6)){
                        cont33++;
                    }
                }
            }
            while (rs4.next()) {
                datos4[0] = rs4.getString("Proyecto");
                datos4[1] = rs4.getString("Plano");
                datos4[2] = rs4.getString("Cronometro");
                datos4[3] = rs4.getString("Empleado");
                datos4[4] = rs4.getString("FechaFinal");
                datos4[5] = rs4.getString("Terminado");
                if (datos4[4] != null || !"".equals(datos4[4])) {
                    String f = datos4[4].substring(0, 10);
                    int hor =40;
                    try{
                    hor = Integer.parseInt(datos4[4].substring(11, 13));
                    }catch(Exception e){
//                        System.out.println("Exception: "+e);
                    }
                    if ((fec.equals(f) && (hor >= 7 && hor <= 17))) {
                        cont4++;
                    }else if((fec.equals(f) && (hor >=19)) || (fecSig.equals(f)) && (hor >= 19 || hor <= 6)){
                        cont44++;
                    }
                }
            }
            while (rs5.next()) {
                datos5[0] = rs5.getString("Proyecto");
                datos5[1] = rs5.getString("Plano");
                datos5[2] = rs5.getString("Cronometro");
                datos5[3] = rs5.getString("Empleado");
                datos5[4] = rs5.getString("FechaFinal");
                datos5[5] = rs5.getString("Terminado");
                if (datos5[4] != null || !"".equals(datos5[4])) {
                    String f = datos5[4].substring(0, 10);
                    int hor =40;
                    try{
                    hor = Integer.parseInt(datos5[4].substring(11, 13));
                    }catch(Exception e){
//                        System.out.println("Exception: "+e);
                    }
                    if ((fec.equals(f) && (hor >= 7 && hor <= 17))) {
                        cont5++;
                    }else if((fec.equals(f) && (hor >=19)) || (fecSig.equals(f)) && (hor >= 19 || hor <= 6)){
                        cont55++;
                    }
                }
            }
            txtCortes.setText("" + cont1 + "/" + cont6);
            txtFresa.setText("" + cont2  + "/" + cont22);
            txtCnc.setText("" + cont3  + "/" + cont33);
            txtTorno.setText("" + cont4  + "/" + cont44);
            txtAcabados.setText("" + cont5 + "/" + cont55);
        } catch (SQLException E) {
            JOptionPane.showMessageDialog(this, "ERROR EN LA BD" + E);
        } catch (ParseException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            Date fecha1 = new Date();
            SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
            String fec = fecha.format(calen.getDate());

            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st1 = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            Statement st4 = con.createStatement();
            Statement st5 = con.createStatement();
            String sql1 = "select FechaFinal, Proyecto, Plano,Cronometro, Empleado, Terminado,FechaInicio from datos where FechaInicio != null or FechaInicio != '' and (FechaFinal = null or FechaFinal = '')";
            ResultSet rs1 = st1.executeQuery(sql1);
            String sql2 = "select FechaFinal, Proyecto, Plano,Cronometro, Empleado, Terminado,FechaInicio from fresadora where FechaInicio != null or FechaInicio != '' and (FechaFinal = null or FechaFinal = '')";
            ResultSet rs2 = st2.executeQuery(sql2);
            String sql3 = "select FechaFinal, Proyecto, Plano,Cronometro, Empleado, Terminado,FechaInicio from cnc where FechaInicio != null or FechaInicio != '' and (FechaFinal = null or FechaFinal = '')";
            ResultSet rs3 = st3.executeQuery(sql3);
            String sql4 = "select FechaFinal, Proyecto, Plano,Cronometro, Empleado, Terminado,FechaInicio from torno where FechaInicio != null or FechaInicio != '' and (FechaFinal = null or FechaFinal = '')";
            ResultSet rs4 = st4.executeQuery(sql4);
            String sql5 = "select FechaFinal, Proyecto, Plano,Cronometro, Empleado, Terminado,FechaInicio from acabados where FechaInicio != null or FechaInicio != '' and (FechaFinal = null or FechaFinal = '')";
            ResultSet rs5 = st5.executeQuery(sql5);
            String datos1[] = new String[10];
            String datos2[] = new String[10];
            String datos3[] = new String[10];
            String datos4[] = new String[10];
            String datos5[] = new String[10];
            int cont1 = 0, cont2 = 0, cont3 = 0, cont4 = 0, cont5 = 0;
            while (rs1.next()) {
                datos1[0] = rs1.getString("Proyecto");
                datos1[1] = rs1.getString("Plano");
                datos1[2] = rs1.getString("Cronometro");
                datos1[3] = rs1.getString("Empleado");
                datos1[4] = rs1.getString("FechaInicio");
                if (datos1[4] != null || datos1[4] != "") {
                    String f = datos1[4].substring(0, 10);
                    if (fec.equals(f)) {
                        cont1++;
                    }
                }
            }
            while (rs2.next()) {
                datos2[0] = rs2.getString("Proyecto");
                datos2[1] = rs2.getString("Plano");
                datos2[2] = rs2.getString("Cronometro");
                datos2[3] = rs2.getString("Empleado");
                datos2[4] = rs2.getString("FechaInicio");
                if (datos2[4] != null || datos2[4] != "") {
                    String f = datos2[4].substring(0, 10);
                    if (fec.equals(f)) {
                        cont2++;
                    }
                }
            }
            while (rs3.next()) {
                datos3[0] = rs3.getString("Proyecto");
                datos3[1] = rs3.getString("Plano");
                datos3[2] = rs3.getString("Cronometro");
                datos3[3] = rs3.getString("Empleado");
                datos3[4] = rs3.getString("FechaInicio");
                if (datos3[4] != null || datos3[4] != "") {
                    String f = datos3[4].substring(0, 10);
                    if (fec.equals(f)) {
                        cont3++;
                    }
                }
            }
            while (rs4.next()) {
                datos4[0] = rs4.getString("Proyecto");
                datos4[1] = rs4.getString("Plano");
                datos4[2] = rs4.getString("Cronometro");
                datos4[3] = rs4.getString("Empleado");
                datos4[4] = rs4.getString("FechaInicio");
                if (datos4[4] != null || datos4[4] != "") {
                    String f = datos4[4].substring(0, 10);
                    if (fec.equals(f)) {
                        cont4++;
                    }
                }
            }
            while (rs5.next()) {
                datos5[0] = rs5.getString("Proyecto");
                datos5[1] = rs5.getString("Plano");
                datos5[2] = rs5.getString("Cronometro");
                datos5[3] = rs5.getString("Empleado");
                datos5[4] = rs5.getString("FechaInicio");
                if (datos5[4] != null || datos5[4] != "") {
                    String f = datos5[4].substring(0, 10);
                    if (fec.equals(f)) {
                        cont5++;
                    }
                }
            }
            txtCortes1.setText("" + cont1);
            txtFresa1.setText("" + cont2);
            txtCnc1.setText("" + cont3);
            txtTorno1.setText("" + cont4);
            txtAcabados1.setText("" + cont5);
        } catch (SQLException E) {
            JOptionPane.showMessageDialog(this, "ERROR EN LA BD" + E);
            Logger.getLogger(OrdenDeCompra.class.getName()).log(Level.SEVERE, null, E);
        }
        try {
            Date fecha1 = new Date();
            SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
            String fec = fecha.format(calen.getDate());

            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st1 = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            Statement st4 = con.createStatement();
            Statement st5 = con.createStatement();
            String sql1 = "select * from datos where Terminado like 'NO'";
            ResultSet rs1 = st1.executeQuery(sql1);
            String sql2 = "select * from fresadora where Terminado like 'NO'";
            ResultSet rs2 = st2.executeQuery(sql2);
            String sql3 = "select * from cnc where Terminado like 'NO'";
            ResultSet rs3 = st3.executeQuery(sql3);
            String sql4 = "select * from torno where Terminado like 'NO'";
            ResultSet rs4 = st4.executeQuery(sql4);
            String sql5 = "select * from acabados where Terminado like 'NO'";
            ResultSet rs5 = st5.executeQuery(sql5);
            String datos1[] = new String[10];
            String datos2[] = new String[10];
            String datos3[] = new String[10];
            String datos4[] = new String[10];
            String datos5[] = new String[10];
            int cont1 = 0, cont2 = 0, cont3 = 0, cont4 = 0, cont5 = 0;
            while (rs1.next()) {
                cont1++;
            }
            while (rs2.next()) {
                cont2++;
            }
            while (rs3.next()) {
                cont3++;
            }
            while (rs4.next()) {
                cont4++;
            }
            while (rs5.next()) {
                cont5++;
            }
            txtCortes2.setText("" + cont1);
            txtFresa2.setText("" + cont2);
            txtCnc2.setText("" + cont3);
            txtTorno2.setText("" + cont4);
            txtAcabados2.setText("" + cont5);
        } catch (SQLException E) {
            JOptionPane.showMessageDialog(this, "ERROR EN LA BD" + E);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnTerminadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTerminadosActionPerformed
        btnCurso.setSelected(false);
        btnEstacion.setSelected(false);
        btnTerminados.setSelected(true);
        lblEstado.setText("PLANOS TERMINADOS");
        lblEstado.setForeground(new java.awt.Color(0,153,153));
    }//GEN-LAST:event_btnTerminadosActionPerformed

    private void btnEstacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstacionActionPerformed
        btnTerminados.setSelected(false);
        btnCurso.setSelected(false);
        btnEstacion.setSelected(true);
        lblEstado.setText("PLANOS EN ESTACION");
        lblEstado.setForeground(new java.awt.Color(102,0,102));
    }//GEN-LAST:event_btnEstacionActionPerformed

    private void btnFresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFresaActionPerformed
//        try {
//            SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy");
//            String fec = nuevo.format(calen.getDate());
//            lblUbi.setText("FRESADORA");
//            limpiarTabla();
//            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
//            Connection con = null;
//            Conexion con1 = new Conexion();
//            con = con1.getConnection();
//            Statement st1 = con.createStatement();
//            Statement st2 = con.createStatement();
//            Statement st3 = con.createStatement();
//            String datos[] = new String[10];
//            String datos1[] = new String[10];
//
//            if(btnEstacion.isSelected()){
//                String sql2 = "select * from fresadora where Terminado like 'NO'";
//                ResultSet rs2 = st2.executeQuery(sql2);
//                while(rs2.next()){
//                    datos1[4] = rs2.getString("FechaFinal");
//                        datos[0] = rs2.getString("Plano");
//                        datos[1] = rs2.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs2.getString("Cronometro");
//                        datos[6] = rs2.getString("Empleado");
//                        miModelo.addRow(datos);
//                }
//            }else if(btnCurso.isSelected()){
//                String sql3 = "select * from fresadora where FechaInicio != '' and FechaFinal like '' and Terminado like 'NO'";
//                ResultSet rs3 = st3.executeQuery(sql3);
//                while(rs3.next()){
//                    datos1[4] = rs3.getString("FechaFinal");
//                        datos[0] = rs3.getString("Plano");
//                        datos[1] = rs3.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs3.getString("Cronometro");
//                        datos[6] = rs3.getString("Empleado");
//                        miModelo.addRow(datos);
//                }
//            }else if(btnTerminados.isSelected()){
//                String sql3 = "select * from fresadora where Terminado like 'SI' and FechaFinal != ''";
//                ResultSet rs3 = st3.executeQuery(sql3);
//                while(rs3.next()){
//                    datos1[4] = rs3.getString("FechaFinal");
//                    String s = datos1[4].substring(0,10);
//                    if(fec.equals(s)){
//                        
//                        datos[0] = rs3.getString("Plano");
//                        datos[1] = rs3.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs3.getString("Cronometro");
//                        datos[6] = rs3.getString("Empleado");
//                        miModelo.addRow(datos);
//                    }
//                }
//            }
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "ERROR AL VER BASE DE DATOS" + e);
//        }
        verTerminados("FRESADORA", "fresadora");
    }//GEN-LAST:event_btnFresaActionPerformed

    private void btnAcabadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcabadosActionPerformed
//        try {
//            SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy");
//            String fec = nuevo.format(calen.getDate());
//            lblUbi.setText("ACABADOS");
//            limpiarTabla();
//            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
//            Connection con = null;
//            Conexion con1 = new Conexion();
//            con = con1.getConnection();
//            Statement st1 = con.createStatement();
//            Statement st2 = con.createStatement();
//            Statement st3 = con.createStatement();
//            String datos[] = new String[10];
//            String datos1[] = new String[10];
//
//            if(btnEstacion.isSelected()){
//                String sql2 = "select * from acabados where Terminado like 'NO'";
//                ResultSet rs2 = st2.executeQuery(sql2);
//                while(rs2.next()){
//                    datos1[4] = rs2.getString("FechaFinal");
//                        datos[0] = rs2.getString("Plano");
//                        datos[1] = rs2.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs2.getString("Cronometro");
//                        datos[6] = rs2.getString("Empleado");
//                        miModelo.addRow(datos);
//                }
//            }else if(btnCurso.isSelected()){
//                String sql3 = "select * from acabados where FechaInicio != '' and FechaFinal like '' and Terminado like 'NO'";
//                ResultSet rs3 = st3.executeQuery(sql3);
//                while(rs3.next()){
//                    datos1[4] = rs3.getString("FechaFinal");
//                        datos[0] = rs3.getString("Plano");
//                        datos[1] = rs3.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs3.getString("Cronometro");
//                        datos[6] = rs3.getString("Empleado");
//                        miModelo.addRow(datos);
//                }
//            }else if(btnTerminados.isSelected()){
//                String sql3 = "select * from acabados where Terminado like 'SI' and FechaFinal != ''";
//                ResultSet rs3 = st3.executeQuery(sql3);
//                while(rs3.next()){
//                    datos1[4] = rs3.getString("FechaFinal");
//                    String s = datos1[4].substring(0,10);
//                    if(fec.equals(s)){
//                        
//                        datos[0] = rs3.getString("Plano");
//                        datos[1] = rs3.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs3.getString("Cronometro");
//                        datos[6] = rs3.getString("Empleado");
//                        miModelo.addRow(datos);
//                    }
//                }
//            }
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "ERROR AL VER BASE DE DATOS" + e);
//        }
        verTerminados("ACABADOS", "acabados");
    }//GEN-LAST:event_btnAcabadosActionPerformed

    private void btnCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCursoActionPerformed
        btnTerminados.setSelected(false);
        btnEstacion.setSelected(false);
        btnCurso.setSelected(true);
        lblEstado.setText("PLANOS EN CURSO");
        lblEstado.setForeground(new java.awt.Color(102,102,0));
    }//GEN-LAST:event_btnCursoActionPerformed

    private void btnCncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCncActionPerformed
//        try {
//            SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy");
//            String fec = nuevo.format(calen.getDate());
//            lblUbi.setText("CNC");
//            limpiarTabla();
//            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
//            Connection con = null;
//            Conexion con1 = new Conexion();
//            con = con1.getConnection();
//            Statement st1 = con.createStatement();
//            Statement st2 = con.createStatement();
//            Statement st3 = con.createStatement();
//            String datos[] = new String[10];
//            String datos1[] = new String[10];
//
//            if(btnEstacion.isSelected()){
//                String sql2 = "select * from cnc where Terminado like 'NO'";
//                ResultSet rs2 = st2.executeQuery(sql2);
//                while(rs2.next()){
//                    datos1[4] = rs2.getString("FechaFinal");
//                        datos[0] = rs2.getString("Plano");
//                        datos[1] = rs2.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs2.getString("Cronometro");
//                        datos[6] = rs2.getString("Empleado");
//                        miModelo.addRow(datos);
//                }
//            }else if(btnCurso.isSelected()){
//                String sql3 = "select * from cnc where FechaInicio != '' and FechaFinal like '' and Terminado like 'NO'";
//                ResultSet rs3 = st3.executeQuery(sql3);
//                while(rs3.next()){
//                    datos1[4] = rs3.getString("FechaFinal");
//                        datos[0] = rs3.getString("Plano");
//                        datos[1] = rs3.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs3.getString("Cronometro");
//                        datos[6] = rs3.getString("Empleado");
//                        miModelo.addRow(datos);
//                }
//            }else if(btnTerminados.isSelected()){
//                String sql3 = "select * from cnc where Terminado like 'SI' and FechaFinal != ''";
//                ResultSet rs3 = st3.executeQuery(sql3);
//                while(rs3.next()){
//                    datos1[4] = rs3.getString("FechaFinal");
//                    String s = datos1[4].substring(0,10);
//                    if(fec.equals(s)){
//                        
//                        datos[0] = rs3.getString("Plano");
//                        datos[1] = rs3.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs3.getString("Cronometro");
//                        datos[6] = rs3.getString("Empleado");
//                        miModelo.addRow(datos);
//                    }
//                }
//            }
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "ERROR AL VER BASE DE DATOS" + e);
//        }
        verTerminados("CNC", "cnc");
    }//GEN-LAST:event_btnCncActionPerformed

    private void btnTornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTornoActionPerformed
//        try {
//            SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy");
//            String fec = nuevo.format(calen.getDate());
//            lblUbi.setText("TORNO");
//            limpiarTabla();
//            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
//            Connection con = null;
//            Conexion con1 = new Conexion();
//            con = con1.getConnection();
//            Statement st1 = con.createStatement();
//            Statement st2 = con.createStatement();
//            Statement st3 = con.createStatement();
//            String datos[] = new String[10];
//            String datos1[] = new String[10];
//
//            if(btnEstacion.isSelected()){
//                String sql2 = "select * from torno where Terminado like 'NO'";
//                ResultSet rs2 = st2.executeQuery(sql2);
//                while(rs2.next()){
//                    datos1[4] = rs2.getString("FechaFinal");
//                        datos[0] = rs2.getString("Plano");
//                        datos[1] = rs2.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs2.getString("Cronometro");
//                        datos[6] = rs2.getString("Empleado");
//                        miModelo.addRow(datos);
//                }
//            }else if(btnCurso.isSelected()){
//                String sql3 = "select * from torno where FechaInicio != '' and FechaFinal like '' and Terminado like 'NO'";
//                ResultSet rs3 = st3.executeQuery(sql3);
//                while(rs3.next()){
//                    datos1[4] = rs3.getString("FechaFinal");
//                        datos[0] = rs3.getString("Plano");
//                        datos[1] = rs3.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs3.getString("Cronometro");
//                        datos[6] = rs3.getString("Empleado");
//                        miModelo.addRow(datos);
//                }
//            }else if(btnTerminados.isSelected()){
//                String sql3 = "select * from torno where Terminado like 'SI' and FechaFinal != ''";
//                ResultSet rs3 = st3.executeQuery(sql3);
//                while(rs3.next()){
//                    datos1[4] = rs3.getString("FechaFinal");
//                    String s = datos1[4].substring(0,10);
//                    if(fec.equals(s)){
//                        
//                        datos[0] = rs3.getString("Plano");
//                        datos[1] = rs3.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs3.getString("Cronometro");
//                        datos[6] = rs3.getString("Empleado");
//                        miModelo.addRow(datos);
//                    }
//                }
//            }
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "ERROR AL VER BASE DE DATOS" + e);
//        }
        verTerminados("TORNO", "torno");
    }//GEN-LAST:event_btnTornoActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JFrame frame = (JFrame)JOptionPane.getFrameForComponent(this);
        VentanaEmergente.Inicio1.Reportes r = new VentanaEmergente.Inicio1.Reportes(frame, true);
        r.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        dialog v = new dialog(f,true);
        v.btnModificar.setVisible(false);
        v.jLabel2.setVisible(false);
        v.jLabel3.setVisible(false);
        v.jLabel4.setVisible(false);
        v.jLabel5.setVisible(false);
        v.jLabel6.setVisible(false);
        v.jSeparator1.setVisible(false);
        v.jSeparator2.setVisible(false);
        v.jSeparator3.setVisible(false);
        v.jSeparator4.setVisible(false);
        v.txtCotizacion.setVisible(false);
        v.txtDescripcion.setVisible(false);
        v.txtOc.setVisible(false);
        v.txtProyecto.setVisible(false);
        v.txtRequisicion.setVisible(false);
        v.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        
        Plano p = new Plano();
        p.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        ReporteMensual r = new ReporteMensual(f, true);
        r.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        ReporteHerramienta r = new ReporteHerramienta(f, true);
        r.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
        btnSalir.setBackground(java.awt.Color.red);
    }//GEN-LAST:event_jLabel3MouseEntered

    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseExited
        btnSalir.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_jLabel3MouseExited

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
        
        Sheet hoja = book.createSheet("REPORTE DE "+lblEstado.getText());
        Row fila = hoja.createRow(2);
        Cell col = fila.createCell(2);
        
        Row fila1 = hoja.createRow(4);
        Cell col1 = fila1.createCell(2);
        
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
        hoja.setColumnWidth(8, 8200); 
        
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
        8
        ));
        
        hoja.addMergedRegion(new CellRangeAddress (
        4,
        4,
        2,
        4
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
        col.setCellValue("Reporte de planos de "+lblUbi.getText());
        
        
        for (int i = -1; i < Tabla1.getRowCount(); i++) {
                Row fila10=hoja.createRow(i+7);
                for (int j = 0; j < 7; j++) {
                    Cell celda=fila10.createCell(j+2);
                    if(i == -1 && (j >= 0 && j <=6)){
                        CellStyle s = book.createCellStyle();
                        org.apache.poi.ss.usermodel.Font f = book.createFont();
                        f.setBold(true);
                        f.setColor(IndexedColors.WHITE.getIndex());
                        s.setFont(f);
                        s.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }
                    if(i > -1 && (j > -1 && j <= 6) && (i%2 == 0)){
                        CellStyle s = book.createCellStyle();
                        s.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }
                    
                    if(i==-1){
                        celda.setCellValue(String.valueOf(Tabla1.getColumnName(j)));
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
                        celda.setCellValue(String.valueOf(Tabla1.getValueAt(i, j)));
//                        CellUtil.setCellStyleProperties(celda, properties);
                        
                    }
                    File ad = new File(a);
                    book.write(new FileOutputStream(a));  
                    
                }
            }
            
        book.close();
        try{
                       Runtime.getRuntime().exec("cmd /c start "+a);
                       Runtime.getRuntime().exec("cmd /c close");
                    }catch(IOException  ee){
                           JOptionPane.showMessageDialog(this, ee);
                       }
    } catch (FileNotFoundException ex) {
        Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            
            SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy");
            String fec = nuevo.format(calen.getDate());
            int diaSig = Integer.parseInt(fec.substring(0,2));
            diaSig += 1;
            String dia = diaSig + "/" + fec.substring(3,fec.length());
            Date da = nuevo.parse(dia);
            String fecSig = nuevo.format(da);
            lblUbi.setText("TALLER");
            limpiarTabla();
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st1 = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            String datos[] = new String[10];
            String datos1[] = new String[10];

            if(btnEstacion.isSelected()){
                String sql2 = "select * from fresadora where Terminado like 'NO'";
                ResultSet rs2 = st2.executeQuery(sql2);
                while(rs2.next()){
                    datos1[4] = rs2.getString("FechaFinal");
                        datos[0] = rs2.getString("Plano");
                        datos[1] = rs2.getString("Proyecto");
                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
                        String cantidad = "", tipo = "";
                        Statement st5 = con.createStatement();
                        ResultSet rs5 = st5.executeQuery(sql4);
                        while(rs5.next()){
                            cantidad = rs5.getString("Cantidad");
                            tipo = rs5.getString("Material");
                        }
                        datos[2] = cantidad;
                        datos[3] = tipo;
                        datos[4] = "0";
                        datos[5] = rs2.getString("Cronometro");
                        datos[6] = rs2.getString("Empleado");
                        miModelo.addRow(datos);
                }
                Statement st6 = con.createStatement();
                String sql6 = "select * from torno where Terminado like 'NO'";
                ResultSet rs6 = st6.executeQuery(sql6);
                while(rs6.next()){
                    datos1[4] = rs6.getString("FechaFinal");
                        datos[0] = rs6.getString("Plano");
                        datos[1] = rs6.getString("Proyecto");
                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
                        String cantidad = "", tipo = "";
                        Statement st5 = con.createStatement();
                        ResultSet rs5 = st5.executeQuery(sql4);
                        while(rs5.next()){
                            cantidad = rs5.getString("Cantidad");
                            tipo = rs5.getString("Material");
                        }
                        datos[2] = cantidad;
                        datos[3] = tipo;
                        datos[4] = "0";
                        datos[5] = rs6.getString("Cronometro");
                        datos[6] = rs6.getString("Empleado");
                        miModelo.addRow(datos);
                }
                Statement st7 = con.createStatement();
                String sql7 = "select * from cnc where Terminado like 'NO'";
                ResultSet rs7 = st7.executeQuery(sql7);
                while(rs7.next()){
                    datos1[4] = rs7.getString("FechaFinal");
                        datos[0] = rs7.getString("Plano");
                        datos[1] = rs7.getString("Proyecto");
                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
                        String cantidad = "", tipo = "";
                        Statement st5 = con.createStatement();
                        ResultSet rs5 = st5.executeQuery(sql4);
                        while(rs5.next()){
                            cantidad = rs5.getString("Cantidad");
                            tipo = rs5.getString("Material");
                        }
                        datos[2] = cantidad;
                        datos[3] = tipo;
                        datos[4] = "0";
                        datos[5] = rs7.getString("Cronometro");
                        datos[6] = rs7.getString("Empleado");
                        miModelo.addRow(datos);
                }
            }else if(btnCurso.isSelected()){
                //---------------------------------------------------------------
                //---------------------------------------------------------------
                //------------------------planos en curso----------------------------
                //---------------------------------------------------------------
                //---------------------------------------------------------------
                String sql3 = "select * from fresadora where FechaInicio != '' and FechaFinal like '' and Terminado like 'NO'";
                ResultSet rs3 = st3.executeQuery(sql3);
                while(rs3.next()){
                    datos1[4] = rs3.getString("FechaFinal");
                        datos[0] = rs3.getString("Plano");
                        datos[1] = rs3.getString("Proyecto");
                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
                        String cantidad = "", tipo = "";
                        Statement st5 = con.createStatement();
                        ResultSet rs5 = st5.executeQuery(sql4);
                        while(rs5.next()){
                            cantidad = rs5.getString("Cantidad");
                            tipo = rs5.getString("Material");
                        }
                        datos[2] = cantidad;
                        datos[3] = tipo;
                        datos[4] = "0";
                        datos[5] = rs3.getString("Cronometro");
                        datos[6] = rs3.getString("Empleado");
                        miModelo.addRow(datos);
                }
                
                Statement st6 = con.createStatement();
                String sql6 = "select * from torno where FechaInicio != '' and FechaFinal like '' and Terminado like 'NO'";
                ResultSet rs6 = st6.executeQuery(sql6);
                while(rs6.next()){
                    datos1[4] = rs6.getString("FechaFinal");
                        datos[0] = rs6.getString("Plano");
                        datos[1] = rs6.getString("Proyecto");
                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
                        String cantidad = "", tipo = "";
                        Statement st5 = con.createStatement();
                        ResultSet rs5 = st5.executeQuery(sql4);
                        while(rs5.next()){
                            cantidad = rs5.getString("Cantidad");
                            tipo = rs5.getString("Material");
                        }
                        datos[2] = cantidad;
                        datos[3] = tipo;
                        datos[4] = "0";
                        datos[5] = rs6.getString("Cronometro");
                        datos[6] = rs6.getString("Empleado");
                        miModelo.addRow(datos);
                }
                Statement st7 = con.createStatement();
                String sql7 = "select * from cnc where FechaInicio != '' and FechaFinal like '' and Terminado like 'NO'";
                ResultSet rs7 = st7.executeQuery(sql7);
                while(rs7.next()){
                    datos1[4] = rs7.getString("FechaFinal");
                        datos[0] = rs7.getString("Plano");
                        datos[1] = rs7.getString("Proyecto");
                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
                        String cantidad = "", tipo = "";
                        Statement st5 = con.createStatement();
                        ResultSet rs5 = st5.executeQuery(sql4);
                        while(rs5.next()){
                            cantidad = rs5.getString("Cantidad");
                            tipo = rs5.getString("Material");
                        }
                        datos[2] = cantidad;
                        datos[3] = tipo;
                        datos[4] = "0";
                        datos[5] = rs7.getString("Cronometro");
                        datos[6] = rs7.getString("Empleado");
                        miModelo.addRow(datos);
                }
                
            }else if(btnTerminados.isSelected()){
                //---------------------------------------------------------------
                //---------------------------------------------------------------
                //------------------------planos terminados----------------------------
                //---------------------------------------------------------------
                //---------------------------------------------------------------
                String sql3 = "select Terminado, FechaFinal, Plano, Proyecto,"
                        + "Cronometro, Empleado from fresadora where Terminado like 'SI' and FechaFinal != ''";
                ResultSet rs3 = st3.executeQuery(sql3);
                while(rs3.next()){
                    datos1[4] = rs3.getString("FechaFinal");
                    boolean turno = false;
                    if(turno1.isSelected()){
                        turno = true;
                    }
                    boolean sen1 = false;
                    
                    
                    String s = datos1[4].substring(0,10);
                    int hor =40;
                    try{
                    hor = Integer.parseInt(datos1[4].substring(11, 13));
                    }catch(Exception e){
//                        System.out.println("Exception: "+e);
                    }
                    if(turno){
                       if((fec.equals(s) && (hor >= 7 && hor <= 17))){
                        sen1 = true;
                        }
                    }else {
                        if((fec.equals(s) && (hor >=19)) || (fecSig.equals(s)) && (hor >= 19 || hor <= 6)){
                            sen1 = true;
                        }
                    }
                    
                    if(sen1){
                        
                        datos[0] = rs3.getString("Plano");
                        datos[1] = rs3.getString("Proyecto");
                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
                        String cantidad = "", tipo = "";
                        Statement st5 = con.createStatement();
                        ResultSet rs5 = st5.executeQuery(sql4);
                        while(rs5.next()){
                            cantidad = rs5.getString("Cantidad");
                            tipo = rs5.getString("Material");
                        }
                        datos[2] = cantidad;
                        datos[3] = tipo;
                        datos[4] = "0";
                        datos[5] = rs3.getString("Cronometro");
                        datos[6] = rs3.getString("Empleado");
                        datos[7] = rs3.getString("FechaFinal");
                        miModelo.addRow(datos);
                    }
                }
                
                Statement st6 = con.createStatement();
                String sql6 = "select * from torno where Terminado like 'SI' and FechaFinal != ''";
                ResultSet rs6 = st6.executeQuery(sql6);
                while(rs6.next()){
                    datos1[4] = rs6.getString("FechaFinal");
                    boolean turno = false;
                    if(turno1.isSelected()){
                        turno = true;
                    }
                    boolean sen1 = false;
                    
                    
                    String s = datos1[4].substring(0,10);
                    int hor =40;
                    try{
                    hor = Integer.parseInt(datos1[4].substring(11, 13));
                    }catch(Exception e){
//                        System.out.println("Exception: "+e);
                    }
                    if(turno){
                       if((fec.equals(s) && (hor >= 7 && hor <= 17))){
                        sen1 = true;
                        }
                    }else {
                        if((fec.equals(s) && (hor >=19)) || (fecSig.equals(s)) && (hor >= 19 || hor <= 6)){
                            sen1 = true;
                        }
                    }
                    
                    if(sen1){
                        
                        datos[0] = rs6.getString("Plano");
                        datos[1] = rs6.getString("Proyecto");
                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
                        String cantidad = "", tipo = "";
                        Statement st5 = con.createStatement();
                        ResultSet rs5 = st5.executeQuery(sql4);
                        while(rs5.next()){
                            cantidad = rs5.getString("Cantidad");
                            tipo = rs5.getString("Material");
                        }
                        datos[2] = cantidad;
                        datos[3] = tipo;
                        datos[4] = "0";
                        datos[5] = rs6.getString("Cronometro");
                        datos[6] = rs6.getString("Empleado");
                        datos[7] = rs6.getString("FechaFinal");
                        miModelo.addRow(datos);
                    }
                }
                
                Statement st7 = con.createStatement();
                String sql7 = "select * from cnc where Terminado like 'SI' and FechaFinal != ''";
                ResultSet rs7 = st7.executeQuery(sql7);
                while(rs7.next()){
                    datos1[4] = rs7.getString("FechaFinal");
                    boolean turno = false;
                    if(turno1.isSelected()){
                        turno = true;
                    }
                    boolean sen1 = false;
                    
                    
                    String s = datos1[4].substring(0,10);
                    int hor =40;
                    try{
                    hor = Integer.parseInt(datos1[4].substring(11, 13));
                    }catch(Exception e){
//                        System.out.println("Exception: "+e);
                    }
                    if(turno){
                       if((fec.equals(s) && (hor >= 7 && hor <= 17))){
                        sen1 = true;
                        }
                    }else {
                        if((fec.equals(s) && (hor >=19)) || (fecSig.equals(s)) && (hor >= 19 || hor <= 6)){
                            sen1 = true;
                        }
                    }
                    
                    if(sen1){
                        
                        datos[0] = rs7.getString("Plano");
                        datos[1] = rs7.getString("Proyecto");
                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
                        String cantidad = "", tipo = "";
                        Statement st5 = con.createStatement();
                        ResultSet rs5 = st5.executeQuery(sql4);
                        while(rs5.next()){
                            cantidad = rs5.getString("Cantidad");
                            tipo = rs5.getString("Material");
                        }
                        datos[2] = cantidad;
                        datos[3] = tipo;
                        datos[4] = "0";
                        datos[5] = rs7.getString("Cronometro");
                        datos[6] = rs7.getString("Empleado");
                        datos[7] = rs7.getString("FechaFinal");
                        miModelo.addRow(datos);
                    }
                }
                
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL VER BASE DE DATOS" + e);
        } catch (ParseException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        
    }//GEN-LAST:event_Tabla1MouseClicked

    private void TerminarPlanosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TerminarPlanosActionPerformed
        for (int i = 0; i < Tabla1.getSelectedRows().length; i++) {
            terminarPlano(Tabla1.getValueAt(Tabla1.getSelectedRows()[i], 1).toString(), numEmpleado);
        }
        
    }//GEN-LAST:event_TerminarPlanosActionPerformed

    private void txtProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProyectoActionPerformed
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String proyecto = txtProyecto.getText();
            String sql = "select Plano, Proyecto, Integracion, Fresadora, Torno, Cnc from Planos where Proyecto like '" + proyecto + "%'";
            ResultSet rs = st.executeQuery(sql);
            int fre = 0;
            int cn = 0;
            int tor = 0;
            int planos = 0;
            while(rs.next()){
                String fresadora = rs.getString("Fresadora");
                String torno = rs.getString("Fresadora");
                String cnc = rs.getString("Fresadora");
                boolean f = true,c = true,t = true;
                int fr,c1,t1;
                try{fr = Integer.parseInt(fresadora.substring(0, fresadora.indexOf("/")));}catch(Exception e){fr = 0;}
                try{c1 = Integer.parseInt(cnc.substring(0, cnc.indexOf("/")));}catch(Exception e){c1 = 0;}
                try{t1 = Integer.parseInt(torno.substring(0, torno.indexOf("/")));}catch(Exception e){t1 = 0;}
                if(fr == 0){
                    f = false;
                }
                if(c1 == 0){
                    c = false;
                }
                if(t1 == 0){
                    t = false;
                }
                fre += fr;
                cn += c1;
                tor += t1;
                if(f == false && c == false && t == false){
                    planos++;
                }
            }
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            ReporteHoras reporte = new ReporteHoras(f,true);
            reporte.lblCnc.setText(String.valueOf((cn / 60)) + " HRS");
            reporte.lblTorno.setText(String.valueOf((tor / 60)) + " HRS");
            reporte.lblFresa.setText(String.valueOf((fre / 60)) + " HRS");
            reporte.lblPlanos.setText(String.valueOf(planos));
            reporte.setLocationRelativeTo(f);
            reporte.setVisible(true);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtProyectoActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JMenuItem TerminarPlanos;
    private javax.swing.JButton btnAcabados;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCnc;
    private javax.swing.JButton btnCortes;
    private javax.swing.JToggleButton btnCurso;
    private javax.swing.JToggleButton btnEstacion;
    private javax.swing.JButton btnFresa;
    private javax.swing.JPanel btnSalir;
    private javax.swing.JToggleButton btnTerminados;
    private javax.swing.JButton btnTorno;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser calen;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblUbi;
    private javax.swing.JRadioButton turno1;
    private javax.swing.JRadioButton turno2;
    private javax.swing.JTextField txtAcabados;
    private javax.swing.JTextField txtAcabados1;
    private javax.swing.JTextField txtAcabados2;
    private javax.swing.JTextField txtCnc;
    private javax.swing.JTextField txtCnc1;
    private javax.swing.JTextField txtCnc2;
    private javax.swing.JTextField txtCortes;
    private javax.swing.JTextField txtCortes1;
    private javax.swing.JTextField txtCortes2;
    private javax.swing.JTextField txtFresa;
    private javax.swing.JTextField txtFresa1;
    private javax.swing.JTextField txtFresa2;
    private javax.swing.JTextField txtProyecto;
    private javax.swing.JTextField txtTorno;
    private javax.swing.JTextField txtTorno1;
    private javax.swing.JTextField txtTorno2;
    // End of variables declaration//GEN-END:variables
}
