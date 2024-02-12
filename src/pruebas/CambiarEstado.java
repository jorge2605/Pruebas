package pruebas;

import Conexiones.Conexion;
import VentanaEmergente.CambiarEstado.TerminarProyecto;
import VentanaEmergente.CambiarEstado.TerminarTodo;
import VentanaEmergente.Inicio1.Espera;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
//import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import static org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CambiarEstado extends InternalFrameImagen implements ActionListener {
private TableRowSorter<TableModel> modeloOrdenado;

    JFileChooser seleccionar = new JFileChooser();
    Espera espera = new Espera();
    String numEmpleado;
    TerminarProyecto terminar;
    
    public static void exportar(){
    Workbook book = new XSSFWorkbook(); 
    Sheet sheet = book.createSheet("Estado Planos");
        try {
            FileOutputStream fileout = new FileOutputStream("Excel.xlsx");
            book.write(fileout);
            fileout.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void buscar(){
        DefaultTableModel miModelo = (DefaultTableModel) TablaDeDatos1.getModel();
        
        try{
        int fila = Tabla1.getSelectedRow();
        Connection con = null;
        Conexion con1 = new Conexion();
        con = con1.getConnection();
        Statement st = con.createStatement();
        Statement st1 = con.createStatement();
        Statement st2 = con.createStatement();
        Statement st3 = con.createStatement();
        Statement st4 = con.createStatement();
        Statement st5 = con.createStatement();
        Statement st6 = con.createStatement();
        Statement st7 = con.createStatement();
        String datos[] = new String[1000];
        String sql = "select Prioridad,Plano,Proyecto, Cantidad from Planos where Proyecto like '"+(Tabla1.getValueAt(fila, 2).toString())+"' order by Plano asc";
        ResultSet rs = st.executeQuery(sql);
        while(rs.next())
        {
        int cont = 0;
        datos[0] = rs.getString("Prioridad");
        datos[1] = rs.getString("Plano");
        datos[2] = rs.getString("Proyecto");
        datos[4] = rs.getString("Cantidad");
        String acabados[] = new String[10];
            String calidad[] = new String[10];
            String cnc[] = new String [10];
            String fresa[] = new String [10];
            String cortes[] = new String [10];
            String torno[] = new String [10];
            String trata[] = new String [10];
            String id = datos[1];
            
            String sq = "select * from Calidad where Proyecto like '%"+datos[1]+"%'";
            ResultSet r = st1.executeQuery(sq);
            String sql1 = "select * from Acabados where Proyecto like '%"+datos[1]+"%'";
            ResultSet rs1 = st2.executeQuery(sql1);
            String sql2 = "select * from CNC where Proyecto like '%"+datos[1]+"%'";
            ResultSet rs2 = st3.executeQuery(sql2);
            String sql3 = "select * from Fresadora where Proyecto like '%"+datos[1]+"%'";
            ResultSet rs3 = st4.executeQuery(sql3);
            String sql5 = "select * from Torno where Proyecto like '%"+datos[1]+"%'";
            ResultSet rs5 = st5.executeQuery(sql5);
            String sql4 = "select * from Datos where Proyecto like '%"+datos[1]+"%'";
            ResultSet rs4 = st6.executeQuery(sql4);
            String sql6 = "select * from Trata where Proyecto like '%"+datos[1]+"%'";
            ResultSet rs6 = st7.executeQuery(sql6);
            while(r.next()){
                calidad[0] = r.getString("Id");
                calidad[1] = r.getString("Proyecto");
                calidad[2] = r.getString("Plano");
                calidad[3] = r.getString("Terminado");
                calidad[4] = r.getString("Tratamiento");
                calidad[5] = r.getString("Prioridad");
            }

            while(rs1.next()){ 
                acabados[1] = rs1.getString("Proyecto");
                acabados[3] = rs1.getString("Terminado");
                acabados[5] = rs1.getString("Prioridad");
            }
            while(rs2.next()){
                cnc[0] = rs2.getString("Id");
                cnc[1] = rs2.getString("Proyecto");
                cnc[2] = rs2.getString("Plano");
                cnc[3] = rs2.getString("Terminado");
                cnc[5] = rs2.getString("Prioridad");
            }
            while(rs3.next()){
                fresa[0] = rs3.getString("Id");
                fresa[1] = rs3.getString("Proyecto");
                fresa[2] = rs3.getString("Plano");
                fresa[3] = rs3.getString("Terminado");
                fresa[5] = rs3.getString("Prioridad");
            }
            while(rs4.next()){
                cortes[0] = rs4.getString("Id");
                cortes[1] = rs4.getString("Proyecto");
                cortes[2] = rs4.getString("Plano");
                cortes[3] = rs4.getString("Terminado");
                cortes[5] = rs4.getString("Prioridad");
                cortes[6] = rs4.getString("Estado");
            }
            while(rs5.next()){
                torno[0] = rs5.getString("Id");
                torno[1] = rs5.getString("Proyecto");
                torno[2] = rs5.getString("Plano");
                torno[3] = rs5.getString("Terminado");
                torno[5] = rs5.getString("Prioridad");
            }
            while(rs6.next()){
                trata[0] = rs6.getString("Id");
                trata[1] = rs6.getString("Proyecto");
                trata[2] = rs6.getString("Plano");
                trata[3] = rs6.getString("Terminado");
                trata[5] = rs6.getString("Prioridad");
            }
            
            if(id.equals(cortes[1]) && cortes[3].equals("NO")){
                String estado = "";
                if(cortes[6] == null){
                    estado = "";
                }else{
                    estado = cortes[6];
                }
                System.out.println(datos[1]+"-"+estado);
                if(estado.equals("SIN MATERIAL")){
                    datos[3] = "SIN MATERIAL";
                }else{
                datos[3] = "CORTES";
                }
            }else if(id.equals(cnc[1]) && cnc[3].equals("NO")){
                datos[3] = "CNC";
            }else if(id.equals(fresa[1]) && fresa[3].equals("NO")){
                datos[3] = "FRESADORA";
            }else if(id.equals(torno[1]) && torno[3].equals("NO")){
                datos[3] = "TORNO";
            }else if(id.equals(acabados[1]) && acabados[3].equals("NO")){
                datos[3] = "ACABADOS";
            }else if(id.equals(trata[1]) && trata[3].equals("NO")){
               datos[3] = "TRATAMIENTO";
            }else if(id.equals(trata[1]) && trata[3].equals("TERMINADO")){
                datos[3] = "TERMINADO";
            }else if(id.equals(calidad[1]) && calidad[3].equals("NO")){
                datos[3] = "CALIDAD";
            }else if(id.equals(calidad[1]) && calidad[3].equals("SI") &&"NO".equals(calidad[4])){
                datos[3] = "TERMINADO";
            }else{
                datos[3] = "LIBERACION";
            }
            
            if(datos[3].equals("LIBERACION")){
            miModelo.addRow(datos);
                }else if(datos[3].equals("CORTES") || datos[3].equals("SIN MATERIAL")){
                miModelo.addRow(datos);
                }else if(datos[3].equals("TORNO")){
                miModelo.addRow(datos);
                    }else if(datos[3].equals("FRESADORA")){
                    miModelo.addRow(datos);
                        }else if(datos[3].equals("CNC")){
                            miModelo.addRow(datos);
                            }else if(datos[3].equals("ACABADOS")){
                                miModelo.addRow(datos);
                                }else if(datos[3].equals("CALIDAD")){
                                    miModelo.addRow(datos);
                                    }else if(datos[3].equals("TERMINADO")){
                                        miModelo.addRow(datos);
                                        }
            
        cont = cont+1;
        }
        
        }catch(SQLException e){
        JOptionPane.showMessageDialog(this, "NO SE PUEDEN MOSTRAR LOS DATOS"+" "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        espera.setVisible(false);
        }
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(miModelo);
        TablaDeDatos1.setRowSorter(elQueOrdena);
    }
    
    public void limpiarTabla(){
    TablaDeDatos1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "PRIORIDAD", "PLANO", "PROYECTO", "UBICACION", "CANTIDAD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaDeDatos1.setColumnSelectionAllowed(true);
        TablaDeDatos1.setRowHeight(25);
        DefaultTableModel Modelo = (DefaultTableModel) TablaDeDatos1.getModel();
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(Modelo);
        TablaDeDatos1.setRowSorter(elQueOrdena);
    }
    
    public void limpiarTabla1(){
    
        DefaultTableModel Modelo = (DefaultTableModel) TablaDeDatos1.getModel();
        String titulos[] = {"CLIENTE","DESCRIPCION","PROYECTO"};
        Modelo = new DefaultTableModel(null,titulos);
        Tabla1.setModel(Modelo);
    
    }
    
    public void verDatos2(){
        try{
            DefaultTableModel miModelo = (DefaultTableModel) TablaDeDatos1.getModel();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String datos1[] = new String[6];
            String sl = "select Prioridad,Plano,Proyecto from Planos where Proyecto like '"+txtProyecto.getText()+"'";
            ResultSet r1 = st.executeQuery(sl);
            while(r1.next())
            {
            datos1[0] = r1.getString("Prioridad");
            datos1[1] = r1.getString("Plano");
            datos1[2] = r1.getString("Proyecto");
            miModelo.addRow(datos1);
            }
            
            
             
        }catch(SQLException e){
        JOptionPane.showMessageDialog(this, e);
        }
    }
    
    public void verDatos(){
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
    try{
        
        Connection con = null;
        Conexion con1 = new Conexion();
        con = con1.getConnection();
        Statement st = con.createStatement();
        String datos[] = new String[5];
        String sql = "select Planta,Descripcion,Proyecto from Proyectos where Mostrar like 'SI' order by iD desc";
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
        datos[0] = rs.getString("Planta");
        datos[1] = rs.getString("Descripcion");
        datos[2] = rs.getString("Proyecto");
        miModelo.addRow(datos);
        }
        }catch(SQLException e){
        JOptionPane.showMessageDialog(this, "NO SE PUEDEN MOSTRAR LOS DATOS", "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    }
    
    public void terminarProyecto(String proyecto){
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
            
            String sql = "select Plano, Proyecto, Terminado from datos where Plano like '"+proyecto+"' and Terminado like 'NO'";
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
            
            String sql3 = "select Plano, Proyecto, Terminado from acabados where Plano like '"+proyecto+"' and Terminado like 'NO'";
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
            
            String sql5 = "select Plano, Proyecto, Terminado from calidad where Plano like '"+proyecto+"' and Terminado like 'NO'";
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
            
            String sql7 = "select Plano, Proyecto, Terminado from cnc where Plano like '"+proyecto+"' and Terminado like 'NO'";
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
            
            String sql9 = "select Plano, Proyecto, Terminado from fresadora where Plano like '"+proyecto+"' and Terminado like 'NO'";
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
            
            String sql11 = "select Plano, Proyecto, Terminado from torno where Plano like '"+proyecto+"' and Terminado like 'NO'";
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
            
            String sql13 = "select Plano, Proyecto, Prioridad from planos where Proyecto like '"+proyecto+"'";
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
    
    public void conteo(){
        int terminados = 0;
        int cortes = 0;
        int fresa = 0;
        int cnc = 0;
        int torno = 0;
        int acabados = 0;
        int calidad = 0;
        int total = TablaDeDatos1.getRowCount();
        int sm = 0;
        int liberacion = 0;
        int trata = 0;
        for (int i = 0; i < TablaDeDatos1.getRowCount(); i++) {
            switch(TablaDeDatos1.getValueAt(i, 3).toString()){
                case "CORTES":
                    cortes++;
                    break;
                case "CNC":
                    cnc++;
                    break;
                case "FRESADORA":
                    fresa++;
                    break;
                case "TORNO":
                    torno++;
                    break;
                case "ACABADOS":
                    acabados++;
                    break;
                case "TRATAMIENTO":
                    trata++;
                    break;
                case "TERMINADO":
                    terminados++;
                    break;
                case "LIBERACION":
                    liberacion++;
                    break;
                case "SIN MATERIAL":
                    sm++;
                    break;
                case "CALIDAD":
                    calidad++;
                    break;
            }
        }
        lblConteo.setText("<html>"
                + "<div>"
                + "<p style='padding:3px;'> Liberacion: "+liberacion+"</p>"
                + "<p style='padding:3px;'> Cortes: "+cortes+"</p>"
                + "<p style='padding:3px;'> Fresadora: "+fresa+"</p>"
                + "<p style='padding:3px;'> Torno: "+torno+"</p>"
                + "<p style='padding:3px;'> Cnc: "+cnc+"</p>"
                + "<p style='padding:3px;'> Acabados: "+acabados+"</p>"
                + "<p style='padding:3px;'> Tratamiento: "+trata+"</p>"
                + "<p style='padding:3px;'> Calidad: "+calidad+"</p>"
                + "<p style='padding:3px;'> Terminados: "+terminados+"</p>"
                + "<p style='padding:3px;'> Sin Material: "+sm+"</p>"
                + "<p style='padding:3px;'> Total: "+total+"</p>"
                + "</div>"
                + "</html>");
    }
    
    public CambiarEstado(String numEmpleado) {
        initComponents();
        
        verDatos();
        
        Tabla1.getTableHeader().setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowGrid(false);
        
        TablaDeDatos1.getTableHeader().setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 14));
        TablaDeDatos1.getTableHeader().setOpaque(false);
        TablaDeDatos1.getTableHeader().setBackground(new Color(0, 78, 171));
        TablaDeDatos1.getTableHeader().setForeground(Color.white);
        TablaDeDatos1.setRowHeight(25);
        
        jScrollPane1.getViewport().setBackground(Color.white);
        jScrollPane2.getViewport().setBackground(Color.white);
        this.numEmpleado = numEmpleado;
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        lblX = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaDeDatos1 = new ColorRojo();
        jPanel1 = new javax.swing.JPanel();
        txtProyecto = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtPlano = new javax.swing.JLabel();
        btnExportarD = new javax.swing.JButton();
        btnPrioridad = new javax.swing.JButton();
        btnLiberar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtLiberado = new javax.swing.JLabel();
        btnVer = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnPrioridad2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        lblConteo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setBorder(null);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout(5, 5));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 165, 252));
        jLabel1.setText("ESTADO DE PROYECTOS");
        jPanel6.add(jLabel1);

        jPanel5.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblX.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lblX.setForeground(new java.awt.Color(0, 0, 0));
        lblX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblX.setText(" x ");
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
        btnSalir.add(lblX);

        jPanel7.add(btnSalir);

        jPanel5.add(jPanel7, java.awt.BorderLayout.EAST);

        jPanel2.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.GridLayout(1, 2, 15, 15));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.BorderLayout());

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CLIENTE", "DESCRIPCION", "PROYECTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setRowHeight(25);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Tabla1MouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Tabla1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setResizable(false);
            Tabla1.getColumnModel().getColumn(1).setResizable(false);
            Tabla1.getColumnModel().getColumn(2).setResizable(false);
        }

        jPanel8.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.BorderLayout());

        TablaDeDatos1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PRIORIDAD", "PLANO", "PROYECTO", "UBICACION", "CANTIDAD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaDeDatos1.setColumnSelectionAllowed(true);
        TablaDeDatos1.setRowHeight(25);
        TablaDeDatos1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaDeDatos1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TablaDeDatos1);
        TablaDeDatos1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jPanel9.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel9);

        jPanel3.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtProyecto.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txtProyecto.setForeground(new java.awt.Color(0, 102, 102));

        jLabel2.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel2.setText("Proyecto");

        jLabel7.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel7.setText("Plano");

        txtPlano.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txtPlano.setForeground(new java.awt.Color(0, 102, 102));

        btnExportarD.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnExportarD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/excel_48.png"))); // NOI18N
        btnExportarD.setBorder(null);
        btnExportarD.setContentAreaFilled(false);
        btnExportarD.setEnabled(false);
        btnExportarD.setFocusPainted(false);
        btnExportarD.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnExportarD.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExportarD.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/excel_48.png"))); // NOI18N
        btnExportarD.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/excel_64.png"))); // NOI18N
        btnExportarD.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnExportarD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExportarD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarDActionPerformed(evt);
            }
        });

        btnPrioridad.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnPrioridad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/prioridad_48.png"))); // NOI18N
        btnPrioridad.setBorder(null);
        btnPrioridad.setContentAreaFilled(false);
        btnPrioridad.setEnabled(false);
        btnPrioridad.setFocusPainted(false);
        btnPrioridad.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnPrioridad.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPrioridad.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/prioridad_48.png"))); // NOI18N
        btnPrioridad.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/prioridad_64.png"))); // NOI18N
        btnPrioridad.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnPrioridad.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPrioridad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrioridadActionPerformed(evt);
            }
        });

        btnLiberar.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnLiberar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/aceptar_48.png"))); // NOI18N
        btnLiberar.setToolTipText("LIBERAR PROYECTO");
        btnLiberar.setBorder(null);
        btnLiberar.setContentAreaFilled(false);
        btnLiberar.setEnabled(false);
        btnLiberar.setFocusPainted(false);
        btnLiberar.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnLiberar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLiberar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/aceptar_48.png"))); // NOI18N
        btnLiberar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/aceptar_64.png"))); // NOI18N
        btnLiberar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnLiberar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLiberar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLiberarActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel8.setText("Liberado");

        txtLiberado.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txtLiberado.setForeground(new java.awt.Color(0, 102, 102));

        btnVer.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnVer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pdf_48.png"))); // NOI18N
        btnVer.setToolTipText("LIBERAR PROYECTO");
        btnVer.setBorder(null);
        btnVer.setContentAreaFilled(false);
        btnVer.setEnabled(false);
        btnVer.setFocusPainted(false);
        btnVer.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnVer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVer.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pdf_48.png"))); // NOI18N
        btnVer.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pdf_64.png"))); // NOI18N
        btnVer.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnVer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerActionPerformed(evt);
            }
        });

        btnBorrar.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/borrra_48.png"))); // NOI18N
        btnBorrar.setToolTipText("LIBERAR PROYECTO");
        btnBorrar.setBorder(null);
        btnBorrar.setContentAreaFilled(false);
        btnBorrar.setFocusPainted(false);
        btnBorrar.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnBorrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBorrar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/borrra_48.png"))); // NOI18N
        btnBorrar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/borrar_64.png"))); // NOI18N
        btnBorrar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnBorrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        btnPrioridad2.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnPrioridad2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/actualizar_48.png"))); // NOI18N
        btnPrioridad2.setBorder(null);
        btnPrioridad2.setContentAreaFilled(false);
        btnPrioridad2.setFocusPainted(false);
        btnPrioridad2.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnPrioridad2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPrioridad2.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/actualizar_48.png"))); // NOI18N
        btnPrioridad2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/actualizar_64.png"))); // NOI18N
        btnPrioridad2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnPrioridad2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPrioridad2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrioridad2ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel9.setText("Conteo");

        lblConteo.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        lblConteo.setForeground(new java.awt.Color(0, 102, 102));
        lblConteo.setText("<html>\n <div>\n<p style='padding:3px;'> Liberacion: </p>\n<p style='padding:3px;'> Cortes: </p>\n<p style='padding:3px;'> Fresadora:</p>\n<p style='padding:3px;'> Torno:</p>\n<p style='padding:3px;'> Cnc: </p>\n<p style='padding:3px;'> Acabados: </p>\n<p style='padding:3px;'> Tratamiento: </p>\n<p style='padding:3px;'> Calidad: </p>\n<p style='padding:3px;'> Terminados: </p>\n<p style='padding:3px;'> Sin Material: </p>\n<p style='padding:3px;'> Total: </p>\n</div>\n</html>");
        lblConteo.setBorder(new javax.swing.border.MatteBorder(null));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblConteo, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnExportarD, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLiberar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnPrioridad, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnVer, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnPrioridad2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtLiberado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtPlano, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                        .addComponent(txtProyecto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPlano, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLiberado, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblConteo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnExportarD, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPrioridad, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnLiberar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPrioridad2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnVer, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );

        jPanel3.add(jPanel1, java.awt.BorderLayout.EAST);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        jMenuItem1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/circulo rojo.png"))); // NOI18N
        jMenuItem1.setText("    Terminar proyecto");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator1);

        jMenuItem2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/circulo.png"))); // NOI18N
        jMenuItem2.setText("Terminar todos los proyectos excepto...                              ");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1420, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        if(evt.getClickCount() == 2){
            String proyecto = TablaDeDatos1.getValueAt(Tabla1.getSelectedRow(), 2).toString();
            int opc = JOptionPane.showConfirmDialog(this,"¿Estas seguro de marcar como terminado el proyecto "+proyecto+" ?");
            if(opc == 0){
                terminarProyecto(proyecto);
            }
        }else{
        if(Tabla1.getValueAt(Tabla1.getSelectedRow(), 2) == null){
            
        }else{
        espera.activar();
        espera.setVisible(true);
        limpiarTabla();
        buscar();
        conteo();
        btnLiberar.setEnabled(false);
        int fila = Tabla1.getSelectedRow();
        txtProyecto.setText((String) Tabla1.getValueAt(fila, 2));
        txtPlano.setText("TODOS");
        btnExportarD.setEnabled(true);
        btnPrioridad.setEnabled(true);
        btnVer.setEnabled(false);
        try{
        Connection con = null;
        Conexion con1 = new Conexion();
        con = con1.getConnection();
        Statement st = con.createStatement();
        
        String sql = "select Proyecto, Liberado from Proyectos where Proyecto like '"+Tabla1.getValueAt(fila, 2).toString()+"'";
        ResultSet rs = st.executeQuery(sql);
        String datos[] = new String[10];
        int cont = 0, v = 0, f = 0;
        while(rs.next()){
        cont++;
        datos[0] = rs.getString("Liberado");
        if(datos[0].equals("SI")){
        v++;
        }
        if(datos[0].equals("NO")){
        f++;
        }
        }
        if((cont == v)){
        btnLiberar.setEnabled(false);
        txtLiberado.setText("SI");
        }else if(cont == f){
        btnLiberar.setEnabled(true);
        txtLiberado.setText("NO");
        }else{
        btnLiberar.setEnabled(true);
        txtLiberado.setText("INCOMPLETO");
        }
        
        }catch(SQLException e){
        JOptionPane.showMessageDialog(this, "ERROR AL ENVIAR A CORTES: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        
        int total = TablaDeDatos1.getRowCount();
        int v = 0;
        for (int i = 0; i < total; i++) {
            if(TablaDeDatos1.getValueAt(i, 3).toString().equals("TERMINADO")){
            v++;
            }
        }
        if(v == total){
        int opc = JOptionPane.showConfirmDialog(this,"PROYECTO TOTALMENTE TERMINADO, ¿DESEAS CERRARLO?");
        
        if(opc == 0){
        try{
        Connection con = null;
        Conexion con1 = new Conexion();
        con = con1.getConnection();
        Statement st = con.createStatement();
        String sql = "update Proyectos set Mostrar = ? where Proyecto = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        
        int f = Tabla1.getSelectedRow();
        pst.setString(1, "NO");
        pst.setString(2, Tabla1.getValueAt(f, 2).toString());
        
        int n = pst.executeUpdate();
        if(n > 0){
            limpiarTabla1();
            buscar();
            limpiarTabla();
        JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
        }
        
        }catch(SQLException e){
        JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR DATOS","ERROR",JOptionPane.ERROR_MESSAGE);
        
        }
        }
        }
        
        espera.band = false;
        espera.dispose();
        }
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void Tabla1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MousePressed
        
    }//GEN-LAST:event_Tabla1MousePressed

    private void TablaDeDatos1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaDeDatos1MouseClicked
        txtPlano.setText("");
        txtProyecto.setText("");
        int fila = TablaDeDatos1.getSelectedRow();
        try{
        Connection con = null;
        Conexion con1 = new Conexion();
        con = con1.getConnection();
        String datos[] = new String[5];
        Statement st = con.createStatement();
        String sql = "select * from Planos";
//        ResultSet rs = st.executeQuery(sql);
//        while(rs.next()){
//        datos[0] = rs.getString("Prioridad");
//        datos[1] = rs.getString("Plano");
//        datos[2] = rs.getString("Proyecto");
//        }
        txtProyecto.setText(TablaDeDatos1.getValueAt(fila, 2).toString());
        txtPlano.setText(TablaDeDatos1.getValueAt(fila, 1).toString());
        btnVer.setEnabled(true);
        } catch(SQLException e){
        JOptionPane.showMessageDialog(this, e);
        }
        
    }//GEN-LAST:event_TablaDeDatos1MouseClicked

    private void btnExportarDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarDActionPerformed
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
        
        Sheet hoja = book.createSheet("REPORTE DE PROYECTO " +txtProyecto.getText());
        Row fila = hoja.createRow(2);
        Cell col = fila.createCell(2);
        
        Row fila1 = hoja.createRow(4);
        Cell col1 = fila1.createCell(2);
        
        //-------------------------------ESTILOS
        Font font = book.createFont();
        CellStyle estilo1 = book.createCellStyle();
        
        Font font3 = book.createFont();
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
        
        Font font1 = book.createFont();
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
        5
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
        col.setCellValue("ESTADO DE PROYECTOS");
        
        col1.setCellStyle(estilo1);
        col1.setCellValue("PROYECTO: " + txtProyecto.getText());
        
        
        
        for (int i = -1; i < TablaDeDatos1.getRowCount(); i++) {
                Row fila10=hoja.createRow(i+7);
                for (int j = 0; j < 5; j++) {
                    Cell celda=fila10.createCell(j+2);
                    if(i == -1 && (j >= 0 && j <=5)){
                        CellStyle s = book.createCellStyle();
                        Font f = book.createFont();
                        f.setBold(true);
                        f.setColor(IndexedColors.WHITE.getIndex());
                        s.setFont(f);
                        s.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }
                    if(i > -1 && (j > -1 && j <= 5) && (i%2 == 0)){
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
//                        CellUtil.setCellStyleProperties(celda, properties);
                        
                        
                       
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

    }//GEN-LAST:event_btnExportarDActionPerformed

    private void btnPrioridadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrioridadActionPerformed
        try{
        String a = JOptionPane.showInputDialog("INGRESA EL % DE PRIORIDAD");
        int p = Integer.parseInt(a);
        DefaultTableModel miModelo = (DefaultTableModel) TablaDeDatos1.getModel();
        try{
            btnVer.setEnabled(false);
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st7 = con.createStatement();
            Statement st8 = con.createStatement();
            Statement st9 = con.createStatement();
            Statement st10 = con.createStatement();
            Statement st11 = con.createStatement();
            Statement st12 = con.createStatement();
            Statement st13 = con.createStatement();
            String datos[] = new String[10];
            String acabados[] = new String[10];
            String cortes[] = new String[10];
            String fresa[] = new String[10];
            String cnc[] = new String[10];
            String torno[] = new String[10];
            String calidad[] = new String[10];
            String act = "update Planos set Prioridad = ? where Plano = ?";
            String act1 = "update Datos set Prioridad = ? where Proyecto = ?";
            String act2 = "update Fresadora set Prioridad = ? where Proyecto = ?";
            String act3 = "update CNC set Prioridad = ? where Proyecto = ?";
            String act4 = "update Torno set Prioridad = ? where Proyecto = ?";
            String act5 = "update Acabados set Prioridad = ? where Proyecto = ?";
            String act6 = "update Calidad set Prioridad = ? where Proyecto = ?";

            String verPl = "select Plano,Proyecto from Planos where Proyecto like '"+txtProyecto.getText()+"%'";
            String verCo = "select Proyecto from Datos where Plano like '"+txtProyecto.getText()+"%' and Terminado like 'NO'";
            String verFr = "select Proyecto from Fresadora where Plano like '"+txtProyecto.getText()+"%' and Terminado like 'NO'";
            String verCn = "select Proyecto from CNC where Plano like '"+txtProyecto.getText()+"%' and Terminado like 'NO'";
            String verTo = "select Proyecto from Torno where Plano like '"+txtProyecto.getText()+"%' and Terminado like 'NO'";
            String verAc = "select Proyecto from Acabados where Plano like '"+txtProyecto.getText()+"%' and Terminado like 'NO'";
            String verCa = "select Proyecto from Calidad where Plano like '"+txtProyecto.getText()+"%' and Terminado like 'NO'";

            ResultSet r = st7.executeQuery(verPl);
            ResultSet r1 = st8.executeQuery(verCo);
            ResultSet r2 = st9.executeQuery(verFr);
            ResultSet r3 = st10.executeQuery(verCn);
            ResultSet r4 = st11.executeQuery(verTo);
            ResultSet r5 = st12.executeQuery(verAc);
            ResultSet r6 = st13.executeQuery(verCa);

            PreparedStatement pst = con.prepareStatement(act);
            PreparedStatement pst1 = con.prepareStatement(act1);
            PreparedStatement pst2 = con.prepareStatement(act2);
            PreparedStatement pst3 = con.prepareStatement(act3);
            PreparedStatement pst4 = con.prepareStatement(act4);
            PreparedStatement pst5 = con.prepareStatement(act5);
            PreparedStatement pst6 = con.prepareStatement(act6);
            if(txtPlano.getText().equals("TODOS")){
                int n = 0,n1 = 0,n2 = 0,n3 = 0,n4 = 0,n5 = 0,n6 = 0;
                int cont = 0;
                while(r.next()){
                    datos[2] = r.getString("Plano");
                    pst.setString(1, ""+p);
                    pst.setString(2, datos[2]);
                    
                    n = pst.executeUpdate();
                    System.out.println(cont);
                    cont++;
                }

                while(r1.next()){
                    cortes[1] = r1.getString("Proyecto");
                    pst1.setString(1, ""+p);
                    pst1.setString(2, cortes[1]);
                    n1 = pst1.executeUpdate();
                    System.out.println(cont);
                    cont++;
                }

                while(r2.next()){
                    fresa[1] = r2.getString("Proyecto");
                    pst2.setString(1, ""+p);
                    pst2.setString(2, fresa[1]);
                    n2 = pst2.executeUpdate();
                    System.out.println(cont);
                    cont++;
                }

                while(r3.next()){
                    cnc[1] = r3.getString("Proyecto");
                    pst3.setString(1, ""+p);
                    pst3.setString(2, cnc[1]);
                    n3 = pst3.executeUpdate();
                    System.out.println(cont);
                    cont++;
                }

                while(r4.next()){
                    torno[1] = r4.getString("Proyecto");
                    pst4.setString(1, ""+p);
                    pst4.setString(2, torno[1]);
                    n4 = pst4.executeUpdate();
                    System.out.println(cont);
                    cont++;
                }

                while(r5.next()){
                    acabados[1] = r5.getString("Proyecto");
                    pst5.setString(1, ""+p);
                    pst5.setString(2, acabados[1]);
                    n5 = pst5.executeUpdate();
                    System.out.println(cont);
                    cont++;
                }

                while(r6.next()){
                    calidad[1] = r6.getString("Proyecto");
                    pst6.setString(1, ""+p);
                    pst6.setString(2, calidad[1]);
                    n6 = pst6.executeUpdate();
                    System.out.println(cont);
                    cont++;
                }

                limpiarTabla();
                buscar();
            }else {
                pst.setString(1, ""+p);
                pst.setString(2, txtPlano.getText());

                pst1.setString(1, ""+p);
                pst1.setString(2, txtPlano.getText());

                pst2.setString(1, ""+p);
                pst2.setString(2, txtPlano.getText());

                pst3.setString(1, ""+p);
                pst3.setString(2, txtPlano.getText());

                pst4.setString(1, ""+p);
                pst4.setString(2, txtPlano.getText());

                pst5.setString(1, ""+p);
                pst5.setString(2, txtPlano.getText());

                pst6.setString(1, ""+p);
                pst6.setString(2, txtPlano.getText());

                int n = pst.executeUpdate();
                int n1 = pst1.executeUpdate();
                int n2 = pst2.executeUpdate();
                int n3 = pst3.executeUpdate();
                int n4 = pst4.executeUpdate();
                int n5 = pst5.executeUpdate();
                int n6 = pst6.executeUpdate();
                limpiarTabla();
                buscar();
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "NO SE ACTUALIZO"+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "DEBES INGRESAR UNA CANTIDAD CORRECTA");
        }
    }//GEN-LAST:event_btnPrioridadActionPerformed

    private void btnLiberarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiberarActionPerformed
        if(txtLiberado.getText().equals("NO")){
            int ar = Integer.parseInt(TablaDeDatos1.getValueAt(0, 0).toString());
            if(ar == 0){
                JOptionPane.showMessageDialog(this, "DEBES PONER PRIORIDAD AL PROYECTO");
            }else{
            try{
            btnVer.setEnabled(false);
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "insert into Datos (Proyecto,Plano,FechaInicio,FechaFinal,Terminado,Estado,Cronometro,Prioridad,Empleado) values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            int n= 0;
                for (int i = 0; i < TablaDeDatos1.getRowCount(); i++) {
                    
                    if(TablaDeDatos1.getValueAt(i, 3).toString().equals("LIBERACION")){
                        
                    pst.setString(1, TablaDeDatos1.getValueAt(i, 1).toString());
                    pst.setString(2, TablaDeDatos1.getValueAt(i, 2).toString());
                    pst.setString(3, "");
                    pst.setString(4, "");
                    pst.setString(5, "NO");
                    pst.setString(6, "");
                    pst.setString(7, "00:00");
                    pst.setString(8, TablaDeDatos1.getValueAt(i, 0).toString());
                    pst.setString(9, "");
                    
                    n = pst.executeUpdate();
                }
                }
            
            if(n > 0){
            JOptionPane.showMessageDialog(this, "DATOS GUARDADOS CORRECTAMENTE");
            txtLiberado.setText("SI");
            btnLiberar.setEnabled(false);
            
            String sql1 = "update Proyectos set Liberado = ? where Proyecto = ?";
            PreparedStatement pst1 = con.prepareStatement(sql1);
            
            pst1.setString(1, "SI");
            pst1.setString(2, txtProyecto.getText());
            
            int e = pst1.executeUpdate();
            if(e > 0){
            
            }
            }
            
            }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR AL LIBERAR PROYECTO: "+e,"ERORR",JOptionPane.ERROR_MESSAGE);
            }
            }
        }
        DefaultTableModel miModelo = (DefaultTableModel) TablaDeDatos1.getModel();
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(miModelo);
        TablaDeDatos1.setRowSorter(elQueOrdena);
    }//GEN-LAST:event_btnLiberarActionPerformed

    private void btnVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerActionPerformed
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            int fila = TablaDeDatos1.getSelectedRow();
            String sql = "select Pdf,Plano from pdfplanos where Plano like '"+TablaDeDatos1.getValueAt(fila, 1).toString()+"'";
            ResultSet rs = st.executeQuery(sql);
            byte[] b = null;
            while(rs.next()){
                b = rs.getBytes("Pdf");
            }
            
                InputStream bos = new ByteArrayInputStream(b);
                int tamInput = bos.available();
                byte[] datosPdf = new byte[tamInput];
                bos.read(datosPdf, 0, tamInput);
                
                OutputStream out = new FileOutputStream("new.pdf");
                out.write(datosPdf);
                
                
                out.close();
                bos.close();
            
            
             Desktop.getDesktop().open(new File("new.pdf"));
                    
            
        }catch(SQLException | NumberFormatException  |IOException e){
            JOptionPane.showMessageDialog(this,"ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnVerActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        Date dato = new Date();
        SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        String fec;
        fec = fecha.format(dato);
        try{
        Connection con = null;
        Conexion con1 = new Conexion();
        con = con1.getConnection();
        Statement st = con.createStatement();
        int n1 = 0,n2 = 0,n3 = 0,n4 = 0,n5 = 0,n6 = 0,n7 = 0,n8 = 0,n9 = 0;
        if(txtPlano.equals("TODOS")){
            String a = JOptionPane.showInputDialog("CONTRSASEÑA: ..........");
            if(a.equals(fec+"1234")){
            for (int i = 0; i < TablaDeDatos1.getRowCount(); i++) {
                String sql1 = "delete from planos where Plano = ?";
                String sql2 = "delete from proyectos where Plano = ?";
                String sql3 = "delete from datos where Plano = ?";
                String sql4 = "delete from cnc where Plano = ?";
                String sql5 = "delete from fresadora where Plano = ?";
                String sql6 = "delete from torno where Plano like = ?";
                String sql7 = "delete from acabados where Plano = ?";
                String sql8 = "delete from calidad where Plano = ?";
                String sql9 = "delete from tratamiento where Plano = ?";
                PreparedStatement pst1 = con.prepareStatement(sql1);
                PreparedStatement pst2 = con.prepareStatement(sql2);
                PreparedStatement pst3 = con.prepareStatement(sql3);
                PreparedStatement pst4 = con.prepareStatement(sql4);
                PreparedStatement pst5 = con.prepareStatement(sql5);
                PreparedStatement pst6 = con.prepareStatement(sql6);
                PreparedStatement pst7 = con.prepareStatement(sql7);
                PreparedStatement pst8 = con.prepareStatement(sql8);
                PreparedStatement pst9 = con.prepareStatement(sql9);
                
                pst1.setString(1, Tabla1.getValueAt(i, 1).toString());
                pst2.setString(1, Tabla1.getValueAt(i, 1).toString());
                pst3.setString(1, Tabla1.getValueAt(i, 1).toString());
                pst4.setString(1, Tabla1.getValueAt(i, 1).toString());
                pst5.setString(1, Tabla1.getValueAt(i, 1).toString());
                pst6.setString(1, Tabla1.getValueAt(i, 1).toString());
                pst7.setString(1, Tabla1.getValueAt(i, 1).toString());
                pst8.setString(1, Tabla1.getValueAt(i, 1).toString());
                pst9.setString(1, Tabla1.getValueAt(i, 1).toString());
                
                n1 = pst1.executeUpdate();
                n2 = pst2.executeUpdate();
                n3 = pst3.executeUpdate();
                n4 = pst4.executeUpdate();
                n5 = pst5.executeUpdate();
                n6 = pst6.executeUpdate();
                n7 = pst7.executeUpdate();
                n8 = pst8.executeUpdate();
                n9 = pst9.executeUpdate();
            }
            if(n1 > 0 && n2 > 0 && n3 > 0 && n4 > 0 && n5 > 0 && n6 > 0 && n7 > 0 && n8 > 0 && n9 > 0){
            JOptionPane.showMessageDialog(this,"DATOS BORRADOS CORRECTAMENTE");
            }
            }
            
        }else {
                String sql1 = "delete from planos where Plano = ?";
                String sql2 = "delete from proyectos where Plano = ?";
                String sql3 = "delete from datos where Plano = ?";
                String sql4 = "delete from cnc where Plano = ?";
                String sql5 = "delete from fresadora where Plano = ?";
                String sql6 = "delete from torno where Plano = ?";
                String sql7 = "delete from acabados where Plano = ?";
                String sql8 = "delete from calidad where Plano = ?";
                String sql9 = "delete from tratamiento where Plano = ?";
                PreparedStatement pst1 = con.prepareStatement(sql1);
                PreparedStatement pst2 = con.prepareStatement(sql2);
                PreparedStatement pst3 = con.prepareStatement(sql3);
                PreparedStatement pst4 = con.prepareStatement(sql4);
                PreparedStatement pst5 = con.prepareStatement(sql5);
                PreparedStatement pst6 = con.prepareStatement(sql6);
                PreparedStatement pst7 = con.prepareStatement(sql7);
                PreparedStatement pst8 = con.prepareStatement(sql8);
                PreparedStatement pst9 = con.prepareStatement(sql9);
                
                pst1.setString(1, txtPlano.getText());
                pst2.setString(1, txtPlano.getText());
                pst3.setString(1, txtPlano.getText());
                pst4.setString(1, txtPlano.getText());
                pst5.setString(1, txtPlano.getText());
                pst6.setString(1, txtPlano.getText());
                pst7.setString(1, txtPlano.getText());
                pst8.setString(1, txtPlano.getText());
                pst9.setString(1, txtPlano.getText());
                
                n1 = pst1.executeUpdate();
                n2 = pst2.executeUpdate();
                n3 = pst3.executeUpdate();
                n4 = pst4.executeUpdate();
                n5 = pst5.executeUpdate();
                n6 = pst6.executeUpdate();
                n7 = pst7.executeUpdate();
                n8 = pst8.executeUpdate();
                n9 = pst9.executeUpdate();
            }
            if(n1 > 0 && n2 > 0 && n3 > 0 && n4 > 0 && n5 > 0 && n6 > 0 && n7 > 0 && n8 > 0 && n9 > 0){
            JOptionPane.showMessageDialog(this,"DATOS BORRADOS CORRECTAMENTE");
            
        }
        
        }catch(SQLException e){
        JOptionPane.showMessageDialog(this, "ERROR AL BORRAR " + e);
        }
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnPrioridad2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrioridad2ActionPerformed
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "update proyectos set Liberado = ? where Proyecto = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            int p = Tabla1.getSelectedRow();
            pst.setString(1, "NO");
            pst.setString(2, txtProyecto.getText());
            
            int n = pst.executeUpdate();
            
            if(n > 0){
                JOptionPane.showMessageDialog(this, "PROYECTO LIBERADO");
                btnLiberar.setEnabled(true);
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnPrioridad2ActionPerformed

    private void Tabla1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Tabla1MouseEntered

    private void lblXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseClicked
        dispose();
    }//GEN-LAST:event_lblXMouseClicked

    private void lblXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseEntered
        btnSalir.setBackground(Color.red);
        lblX.setForeground(Color.white);
    }//GEN-LAST:event_lblXMouseEntered

    private void lblXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseExited
        btnSalir.setBackground(Color.white);
        lblX.setForeground(Color.black);
    }//GEN-LAST:event_lblXMouseExited

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        terminar = new TerminarProyecto(f,true);
        terminar.btnGuardar.addActionListener(this);
        terminar.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        TerminarTodo terminarT = new TerminarTodo(f,true);
        terminarT.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    public javax.swing.JTable TablaDeDatos1;
    public javax.swing.JButton btnBorrar;
    public javax.swing.JButton btnExportarD;
    public javax.swing.JButton btnLiberar;
    public javax.swing.JButton btnPrioridad;
    public javax.swing.JButton btnPrioridad2;
    private javax.swing.JPanel btnSalir;
    public javax.swing.JButton btnVer;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel lblConteo;
    private javax.swing.JLabel lblX;
    private javax.swing.JLabel txtLiberado;
    private javax.swing.JLabel txtPlano;
    private javax.swing.JLabel txtProyecto;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if(terminar != null){
            if(e.getSource() == terminar.btnGuardar){
                int opc = JOptionPane.showConfirmDialog(this, "¿Estas seguro de terminar el proyecto "
                        +terminar.txtProyecto.getText()+"");
                if(opc == 0){
                    terminarProyecto(terminar.txtProyecto.getText());
                }
            }
        }
    }
}
