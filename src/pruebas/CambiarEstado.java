package pruebas;

import Conexiones.Conexion;
import Controlador.maquinados.revisarPlanos;
import VentanaEmergente.CambiarEstado.InformacionProyectos;
import VentanaEmergente.CambiarEstado.TerminarProyecto;
import VentanaEmergente.CambiarEstado.TerminarTodo;
import VentanaEmergente.Inicio1.Espera;
import VentanaEmergente.Reportes.ReporteScrap;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.HEIGHT;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
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

public class CambiarEstado extends JInternalFrame implements ActionListener {

    private TableRowSorter<TableModel> modeloOrdenado;

    JFileChooser seleccionar = new JFileChooser();
    Espera espera = new Espera();
    String numEmpleado;
    TerminarProyecto terminar;
    TextAutoCompleter autoCompleter;
    
    public String getDirectorio(String proyecto) {
        String path = "\\\\serverdell\\03 Project\\04 DISENO\\" + proyecto;
        File direccion = new File(path);

        if (!direccion.isDirectory()) {
            boolean res = direccion.mkdirs();
            if (res) {
                return path;
            } else {
                return path;
            }
        } else {
            return path;
        }
    }
    
    public final void agregarProyectos() {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select proyecto from proyectos";
            ResultSet rs = st.executeQuery(sql);
            autoCompleter = new TextAutoCompleter(txtBuscar);
            while (rs.next()) {
                autoCompleter.addItem(rs.getString("proyecto"));
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void exportar() {
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

    public final void limpiarTabla() {
        TablaDeDatos1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Plano", "Proyecto", "Ubicacion", "Cantidad", "Intentos"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        TablaDeDatos1.getTableHeader().setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 14));
        TablaDeDatos1.getTableHeader().setOpaque(false);
        TablaDeDatos1.getTableHeader().setBackground(new Color(0, 78, 171));
        TablaDeDatos1.getTableHeader().setForeground(Color.white);
        TablaDeDatos1.setRowHeight(25);
        TablaDeDatos1.setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 12));
        TablaDeDatos1.setColumnSelectionAllowed(true);
        DefaultTableModel Modelo = (DefaultTableModel) TablaDeDatos1.getModel();
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(Modelo);
        TablaDeDatos1.setRowSorter(elQueOrdena);
    }

    public final void limpiarTabla1() {
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Cliente", "Descripcion", "Proyecto"
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
        Tabla1.getTableHeader().setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 12));
    }

    public void verDatos2() {
        try {
            DefaultTableModel miModelo = (DefaultTableModel) TablaDeDatos1.getModel();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String datos1[] = new String[6];
            String sl = "select Prioridad,Plano,Proyecto from Planos where Proyecto like '" + txtProyecto.getText() + "'";
            ResultSet r1 = st.executeQuery(sl);
            while (r1.next()) {
                datos1[0] = r1.getString("Plano");
                datos1[1] = r1.getString("Proyecto");
                miModelo.addRow(datos1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public final void verDatos() {
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
        try {

            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String datos[] = new String[5];
            String sql = "select Planta,Descripcion,Proyecto from Proyectos order by iD desc";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("Planta");
                datos[1] = rs.getString("Descripcion");
                datos[2] = rs.getString("Proyecto");
                miModelo.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "NO SE PUEDEN MOSTRAR LOS DATOS", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void terminarProyecto(String proyecto) {
        try {
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

            String sql = "select Plano, Proyecto, Terminado from datos where Plano like '" + proyecto + "' and Terminado like 'NO'";
            ResultSet rs = st.executeQuery(sql);
            String plano;
            String sql2 = "update datos set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ? where Proyecto = ?";
            PreparedStatement pst = con.prepareStatement(sql2);
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat();
            String fecha = sdf.format(d);
            int n = 0;
            while (rs.next()) {
                plano = rs.getString("Proyecto");
                pst.setString(1, fecha);
                pst.setString(2, fecha);
                pst.setString(3, "SI");
                pst.setString(4, numEmpleado + "," + numEmpleado);
                pst.setString(5, plano);

                n = pst.executeUpdate();
            }

            String sql3 = "select Plano, Proyecto, Terminado from acabados where Plano like '" + proyecto + "' and Terminado like 'NO'";
            ResultSet rs3 = st3.executeQuery(sql3);
            String sql4 = "update acabados set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ? where Proyecto = ?";
            PreparedStatement pst4 = con.prepareStatement(sql4);
            int n1 = 0;
            while (rs3.next()) {
                plano = rs3.getString("Proyecto");
                pst4.setString(1, fecha);
                pst4.setString(2, fecha);
                pst4.setString(3, "SI");
                pst4.setString(4, numEmpleado + "," + numEmpleado);
                pst4.setString(5, plano);

                n1 = pst4.executeUpdate();
            }

            String sql5 = "select Plano, Proyecto, Terminado from calidad where Plano like '" + proyecto + "' and Terminado like 'NO'";
            ResultSet rs5 = st5.executeQuery(sql5);
            String sql6 = "update calidad set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ?, Tratamiento = ? where Proyecto = ?";
            PreparedStatement pst6 = con.prepareStatement(sql6);
            int n2 = 0;
            while (rs5.next()) {
                plano = rs5.getString("Proyecto");
                pst6.setString(1, fecha);
                pst6.setString(2, fecha);
                pst6.setString(3, "SI");
                pst6.setString(4, numEmpleado + "," + numEmpleado);
                pst6.setString(5, "NO");
                pst6.setString(6, plano);

                n2 = pst6.executeUpdate();
            }

            String sql7 = "select Plano, Proyecto, Terminado from cnc where Plano like '" + proyecto + "' and Terminado like 'NO'";
            ResultSet rs7 = st7.executeQuery(sql7);
            String sql8 = "update cnc set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ? where Proyecto = ?";
            PreparedStatement pst8 = con.prepareStatement(sql8);
            int n3 = 0;
            while (rs7.next()) {
                plano = rs7.getString("Proyecto");
                pst8.setString(1, fecha);
                pst8.setString(2, fecha);
                pst8.setString(3, "SI");
                pst8.setString(4, numEmpleado + "," + numEmpleado);
                pst8.setString(5, plano);

                n3 = pst8.executeUpdate();
            }

            String sql9 = "select Plano, Proyecto, Terminado from fresadora where Plano like '" + proyecto + "' and Terminado like 'NO'";
            ResultSet rs9 = st9.executeQuery(sql9);
            String sql10 = "update fresadora set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ? where Proyecto = ?";
            PreparedStatement pst10 = con.prepareStatement(sql10);
            int n4 = 0;
            while (rs9.next()) {
                plano = rs9.getString("Proyecto");
                pst10.setString(1, fecha);
                pst10.setString(2, fecha);
                pst10.setString(3, "SI");
                pst10.setString(4, numEmpleado + "," + numEmpleado);
                pst10.setString(5, plano);

                n4 = pst10.executeUpdate();
            }

            String sql11 = "select Plano, Proyecto, Terminado from torno where Plano like '" + proyecto + "' and Terminado like 'NO'";
            ResultSet rs11 = st11.executeQuery(sql11);
            String sql12 = "update torno set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ? where Proyecto = ?";
            PreparedStatement pst12 = con.prepareStatement(sql12);
            int n5 = 0;
            while (rs11.next()) {
                plano = rs11.getString("Proyecto");
                pst12.setString(1, fecha);
                pst12.setString(2, fecha);
                pst12.setString(3, "SI");
                pst12.setString(4, numEmpleado + "," + numEmpleado);
                pst12.setString(5, plano);

                n5 = pst12.executeUpdate();
            }

            String sql13 = "select Plano, Proyecto, Prioridad from planos where Proyecto like '" + proyecto + "'";
            ResultSet rs13 = st13.executeQuery(sql13);
            String sql14 = "insert into calidad (Proyecto, Plano, FechaInicio, FechaFinal, Terminado,"
                    + "Estado, Tratamiento, Cronometro, Prioridad, Empleado) values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst14 = con.prepareStatement(sql14);
            String pl = null;
            String pr = null;
            String pri = null;
            int n6 = 0;
            while (rs13.next()) {
                pl = rs13.getString("Plano");
                pr = rs13.getString("Proyecto");
                pri = rs13.getString("Prioridad");
                String sql15 = "select Proyecto from calidad where Proyecto like '" + pl + "'";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql15);
                String exis = null;
                while (rs2.next()) {
                    exis = rs2.getString("Proyecto");
                }
                if (exis == null) {
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

            if (n > 0 && n1 > 0 && n2 > 0 && n3 > 0 && n4 > 0 && n5 > 0) {
                JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
            } else {
                if (n6 > 0) {
                    String faltantes = "";
                    if (n < 1) {
                        faltantes += "\nCortes";
                    }
                    if (n1 < 1) {
                        faltantes += "\nAcabados";
                    }
                    if (n2 < 1) {
                        faltantes += "\nCalidad";
                    }
                    if (n3 < 1) {
                        faltantes += "\nCnc";
                    }
                    if (n4 < 1) {
                        faltantes += "\nFresadora";
                    }
                    if (n5 < 1) {
                        faltantes += "\nTorno";
                    }

                    JOptionPane.showMessageDialog(this, "DATOS GUARDADOS, SIN CAMBIOS EN: "
                            + faltantes);
                } else {
                    JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void conteo() {
        int terminados = 0;
        int maqui = 0;
        int calidad = 0;
        int cortes = 0;
        int total = TablaDeDatos1.getRowCount();
        int liberacion = 0;
        int trata = 0;
        int inte = 0;
        int fin = 0;
        for (int i = 0; i < TablaDeDatos1.getRowCount(); i++) {
            switch (TablaDeDatos1.getValueAt(i, 2).toString()) {
                case "MAQUINADOS":
                    maqui++;
                    break;
                case "TRATAMIENTO":
                    trata++;
                    break;
                case "TERMINADO (CALIDAD)":
                    terminados++;
                    break;
                case "LIBERACION":
                    liberacion++;
                    break;
                case "CALIDAD":
                    calidad++;
                    break;
                case "CORTES":
                    cortes++;
                    break;
                case "INTEGRACION":
                    inte++;
                    break;
                case "PROYECTO FINALIZADO":
                    fin++;
                    break;
            }
        }
        lblConteo.setText("<html>"
                + "<div>"
                + "<p style='padding:3px;'> Liberacion: " + liberacion + "</p>"
                + "<p style='padding:3px;'> Cortes: " + cortes + "</p>"
                + "<p style='padding:3px;'> Maquinados: " + maqui + "</p>"
                + "<p style='padding:3px;'> Tratamiento: " + trata + "</p>"
                + "<p style='padding:3px;'> Calidad: " + calidad + "</p>"
                + "<p style='padding:3px;'> Integracion: " + inte + "</p>"
                + "<p style='padding:3px;'> Terminados: " + terminados + "</p>"
                + "<p style='padding:3px;'> Finalizado: " + fin + "</p>"
                + "<p style='padding:3px;'> Total: " + total + "</p>"
                + "</div>"
                + "</html>");
    }

    public HashMap<String, Integer> getPlanosSccrap(Connection con, String proyecto) throws SQLException{
        String sql = "select * from scrap where Plano like '" + proyecto + "'";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        ArrayList<String> planos = new ArrayList<>();
        while (rs.next()) {
            String plano = rs.getString("Proyecto");
            planos.add(plano);
        }
        HashMap<String, Integer> conteo = new HashMap<>();
        for (String plano : planos) {
            conteo.put(plano, conteo.getOrDefault(plano, 0) + 1);
        }
        return conteo;
    }
    
    public void buscarProyecto(String proyecto) {
        if (proyecto != null) {
            Thread hilo = new Thread() {
                public void run() {
                    Informacion.setText("Informacion de planos " + proyecto + "                         ");
                    espera.activar();
                    espera.setVisible(true);
                    limpiarTabla();

                    btnLiberar.setEnabled(false);
                    txtProyecto.setText((String) proyecto);
                    txtPlano.setText("TODOS");
                    btnExportarD.setEnabled(true);
                    btnPrioridad.setEnabled(true);
                    btnVer.setEnabled(false);

                    DefaultTableModel miModelo = (DefaultTableModel) TablaDeDatos1.getModel();
                    try {
                        Connection con;
                        Conexion con1 = new Conexion();
                        con = con1.getConnection();
                        HashMap<String, Integer> planos = getPlanosSccrap(con, proyecto);
                        
                        String sql2 = "select Prioridad, Plano, Proyecto, Estado, Cantidad from Planos where Proyecto like '" + proyecto + "' order by Plano asc";
                        Statement st2 = con.createStatement();
                        ResultSet rs2 = st2.executeQuery(sql2);
                        String dat[] = new String[10];
                        while (rs2.next()) {
                            dat[0] = rs2.getString("Plano");
                            dat[1] = rs2.getString("Proyecto");
                            dat[2] = rs2.getString("Estado");
                            try {
                                String part[] = dat[0].split(" ");
                                int ultimo = Integer.parseInt(part[2]);
                                if(ultimo < 100) {
                                    dat[2] = "SUB ENSAMBLE";
                                }
                            } catch (Exception e) {}
                            if (dat[2].equals("TERMINADO")) {
                                dat[2] = "TERMINADO (CALIDAD)";
                            }
                            dat[3] = rs2.getString("Cantidad");
                            dat[4] = String.valueOf((Integer.parseInt(planos.getOrDefault(dat[0], 0).toString()) + 1));
                            miModelo.addRow(dat);
                            conteo();
                        }
                    } catch (SQLException e) {
                        espera.band = false;
                        espera.dispose();
                        JOptionPane.showMessageDialog(null, "ERROR AL ENVIAR A CORTES: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    espera.band = false;
                    espera.dispose();
                }
            };
            hilo.start();
        }
    }
    
    public void verPdf(String plano) {
        try {
            Desktop.getDesktop().open(new File("\\\\192.168.100.40\\03 Project\\04 DISENO\\" + txtProyecto.getText() + "\\" + plano + ".pdf"));
        } catch (Exception ex) {
            String dir = getDirectorio(txtProyecto.getText()) + "\\" + plano + ".pdf";
            try {
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql = "select Pdf,Plano from pdfplanos where Plano like '" + plano + "'";
                ResultSet rs = st.executeQuery(sql);
                byte[] b = null;
                while (rs.next()) {
                    b = rs.getBytes("Pdf");
                }

                InputStream bos = new ByteArrayInputStream(b);
                int tamInput = bos.available();
                byte[] datosPdf = new byte[tamInput];
                bos.read(datosPdf, 0, tamInput);

                OutputStream out = new FileOutputStream(dir);
                out.write(datosPdf);

                out.close();
                bos.close();

                Desktop.getDesktop().open(new File(dir));
            } catch (SQLException | NumberFormatException | IOException e) {
                JOptionPane.showMessageDialog(this, "Error al descargar: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public CambiarEstado(String numEmpleado) {
        initComponents();
        limpiarTabla();
        limpiarTabla1();
        verDatos();
        jScrollPane1.getViewport().setBackground(Color.white);
        jScrollPane2.getViewport().setBackground(Color.white);
        this.numEmpleado = numEmpleado;
        agregarProyectos();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        Informacion = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        InformacionPlano = new javax.swing.JMenuItem();
        InformacionProyecto = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        verPdf = new javax.swing.JMenuItem();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        lblX = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaDeDatos1 = new javax.swing.JTable();
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
        jLabel9 = new javax.swing.JLabel();
        lblConteo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        jPopupMenu1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jPopupMenu1PopupMenuWillBecomeVisible(evt);
            }
        });

        jMenuItem4.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jMenuItem4.setText("Menu");
        jMenuItem4.setEnabled(false);
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem4);

        Informacion.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Informacion.setText("Informacion de planos                        ");
        Informacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InformacionActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Informacion);
        jPopupMenu1.add(jSeparator2);

        InformacionPlano.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        InformacionPlano.setText("Ver inofrmacion de plano (Intentos)");
        InformacionPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InformacionPlanoActionPerformed(evt);
            }
        });
        jPopupMenu1.add(InformacionPlano);

        InformacionProyecto.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        InformacionProyecto.setText("Ver inofrmacion de proyecto ] (Intentos)");
        InformacionProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InformacionProyectoActionPerformed(evt);
            }
        });
        jPopupMenu1.add(InformacionProyecto);
        jPopupMenu1.add(jSeparator3);

        verPdf.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        verPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pdf.png"))); // NOI18N
        verPdf.setText("Ver PDF");
        verPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verPdfActionPerformed(evt);
            }
        });
        jPopupMenu1.add(verPdf);

        setBorder(null);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout(5, 5));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 165, 252));
        jLabel1.setText("ESTADO DE PROYECTOS");
        jPanel6.add(jLabel1);

        jPanel5.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblX.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        lblX.setForeground(new java.awt.Color(0, 0, 0));
        lblX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblX.setText(" X ");
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

        jPanel10.setBackground(new java.awt.Color(248, 248, 248));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 204));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Buscar por proyecto:");
        jPanel10.add(jLabel3, java.awt.BorderLayout.NORTH);

        jPanel11.setBackground(new java.awt.Color(248, 248, 248));

        txtBuscar.setBackground(new java.awt.Color(248, 248, 248));
        txtBuscar.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtBuscar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBuscar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtBuscar.setPreferredSize(new java.awt.Dimension(300, 25));
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        jPanel11.add(txtBuscar);

        jPanel10.add(jPanel11, java.awt.BorderLayout.PAGE_END);

        jPanel5.add(jPanel10, java.awt.BorderLayout.SOUTH);

        jPanel2.add(jPanel5, java.awt.BorderLayout.NORTH);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.GridLayout(1, 2, 15, 15));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

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

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        TablaDeDatos1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Plano", "Proyecto", "Ubicacion", "Cantidad", "Intentos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaDeDatos1.setColumnSelectionAllowed(true);
        TablaDeDatos1.setComponentPopupMenu(jPopupMenu1);
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

        jLabel9.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel9.setText("Conteo");

        lblConteo.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        lblConteo.setForeground(new java.awt.Color(0, 102, 102));
        lblConteo.setText("<html>\n <div>\n<p style='padding:3px;'> Liberacion: </p>\n<p style='padding:3px;'> Cortes: </p>\n<p style='padding:3px;'> Tratamiento: </p>\n<p style='padding:3px;'> Calidad: </p>\n<p style='padding:3px;'> Terminados: </p>\n<p style='padding:3px;'> Total: </p>\n</div>\n</html>");
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
                        .addComponent(btnPrioridad, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnVer, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(lblConteo, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnExportarD, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPrioridad, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnLiberar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnVer, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );

        jPanel3.add(jPanel1, java.awt.BorderLayout.EAST);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2);

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setForeground(new java.awt.Color(51, 51, 51));

        jMenu1.setText("File");

        jMenuItem1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/circulo rojo.png"))); // NOI18N
        jMenuItem1.setText("    Terminar proyecto(s)                                                         ");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator1);

        jMenuItem3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/editarEmpleado_16.png"))); // NOI18N
        jMenuItem3.setText("Liberar Proyecto");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        if (evt.getClickCount() == 2) {
            String proyecto = TablaDeDatos1.getValueAt(Tabla1.getSelectedRow(), 2).toString();
            int opc = JOptionPane.showConfirmDialog(this, "¿Estas seguro de marcar como terminado el proyecto " + proyecto + " ?");
            if (opc == 0) {
                terminarProyecto(proyecto);
            }
        } else {
            buscarProyecto(Tabla1.getValueAt(Tabla1.getSelectedRow(), 2).toString());
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void Tabla1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MousePressed

    }//GEN-LAST:event_Tabla1MousePressed

    private void TablaDeDatos1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaDeDatos1MouseClicked
        if (TablaDeDatos1.getSelectedRow() != -1) {
            txtPlano.setText("");
            txtProyecto.setText("");
            int fila = TablaDeDatos1.getSelectedRow();
            txtProyecto.setText(TablaDeDatos1.getValueAt(fila, 1).toString());
            txtPlano.setText(TablaDeDatos1.getValueAt(fila, 0).toString());
            btnVer.setEnabled(true);
        }
    }//GEN-LAST:event_TablaDeDatos1MouseClicked

    private void btnExportarDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarDActionPerformed
        Workbook book;
        try {
            JFileChooser fc = new JFileChooser();
            File archivo = null;
            fc.setFileFilter(new FileNameExtensionFilter("EXCEL (*.xlsx)", "xlsx"));
            int n = fc.showSaveDialog(this);

            if (n == JFileChooser.APPROVE_OPTION) {
                archivo = fc.getSelectedFile();
            }
            String a = "" + archivo;
            if (a.endsWith("xls")) {
                book = new HSSFWorkbook();
            } else {
                book = new XSSFWorkbook();
                a = archivo + ".xlsx";
            }

            Sheet hoja = book.createSheet("REPORTE DE PROYECTO " + txtProyecto.getText());
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
            font.setFontHeightInPoints((short) 12);
            estilo1.setFont(font);

            estilo1.setAlignment(HorizontalAlignment.LEFT);

            font3.setBold(false);
            font3.setColor(IndexedColors.BLACK.getIndex());
            font3.setFontHeightInPoints((short) 15);
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
            font1.setFontHeightInPoints((short) 16);
            style.setFont(font1);

            style.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style.setFillPattern(SOLID_FOREGROUND);
            style.setVerticalAlignment(VerticalAlignment.BOTTOM);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setWrapText(true);

            hoja.addMergedRegion(new CellRangeAddress(
                    2,
                    2,
                    2,
                    5
            ));

            hoja.addMergedRegion(new CellRangeAddress(
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
                Row fila10 = hoja.createRow(i + 7);
                for (int j = 0; j < 5; j++) {
                    Cell celda = fila10.createCell(j + 2);
                    if (i == -1 && (j >= 0 && j <= 5)) {
                        CellStyle s = book.createCellStyle();
                        Font f = book.createFont();
                        f.setBold(true);
                        f.setColor(IndexedColors.WHITE.getIndex());
                        s.setFont(f);
                        s.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }
                    if (i > -1 && (j > -1 && j <= 5) && (i % 2 == 0)) {
                        CellStyle s = book.createCellStyle();
                        s.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }

                    if (i == -1) {
                        celda.setCellValue(String.valueOf(TablaDeDatos1.getColumnName(j)));
//                        CellUtil.setCellStyleProperties(celda, properties);
                    } else {
                        if (j == 3) {
                            CellStyle ss = book.createCellStyle();
                            ss.setWrapText(true);

                            if (i % 2 == 0) {
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
        try {
            String a = JOptionPane.showInputDialog("INGRESA EL % DE PRIORIDAD");
            int p = Integer.parseInt(a);
            DefaultTableModel miModelo = (DefaultTableModel) TablaDeDatos1.getModel();
            try {
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

                String verPl = "select Plano,Proyecto from Planos where Proyecto like '" + txtProyecto.getText() + "%'";
                String verCo = "select Proyecto from Datos where Plano like '" + txtProyecto.getText() + "%' and Terminado like 'NO'";
                String verFr = "select Proyecto from Fresadora where Plano like '" + txtProyecto.getText() + "%' and Terminado like 'NO'";
                String verCn = "select Proyecto from CNC where Plano like '" + txtProyecto.getText() + "%' and Terminado like 'NO'";
                String verTo = "select Proyecto from Torno where Plano like '" + txtProyecto.getText() + "%' and Terminado like 'NO'";
                String verAc = "select Proyecto from Acabados where Plano like '" + txtProyecto.getText() + "%' and Terminado like 'NO'";
                String verCa = "select Proyecto from Calidad where Plano like '" + txtProyecto.getText() + "%' and Terminado like 'NO'";

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
                if (txtPlano.getText().equals("TODOS")) {
                    int n = 0, n1 = 0, n2 = 0, n3 = 0, n4 = 0, n5 = 0, n6 = 0;
                    int cont = 0;
                    while (r.next()) {
                        datos[2] = r.getString("Plano");
                        pst.setString(1, "" + p);
                        pst.setString(2, datos[2]);

                        n = pst.executeUpdate();
                        System.out.println(cont);
                        cont++;
                    }

                    while (r1.next()) {
                        cortes[1] = r1.getString("Proyecto");
                        pst1.setString(1, "" + p);
                        pst1.setString(2, cortes[1]);
                        n1 = pst1.executeUpdate();
                        System.out.println(cont);
                        cont++;
                    }

                    while (r2.next()) {
                        fresa[1] = r2.getString("Proyecto");
                        pst2.setString(1, "" + p);
                        pst2.setString(2, fresa[1]);
                        n2 = pst2.executeUpdate();
                        System.out.println(cont);
                        cont++;
                    }

                    while (r3.next()) {
                        cnc[1] = r3.getString("Proyecto");
                        pst3.setString(1, "" + p);
                        pst3.setString(2, cnc[1]);
                        n3 = pst3.executeUpdate();
                        System.out.println(cont);
                        cont++;
                    }

                    while (r4.next()) {
                        torno[1] = r4.getString("Proyecto");
                        pst4.setString(1, "" + p);
                        pst4.setString(2, torno[1]);
                        n4 = pst4.executeUpdate();
                        System.out.println(cont);
                        cont++;
                    }

                    while (r5.next()) {
                        acabados[1] = r5.getString("Proyecto");
                        pst5.setString(1, "" + p);
                        pst5.setString(2, acabados[1]);
                        n5 = pst5.executeUpdate();
                        System.out.println(cont);
                        cont++;
                    }

                    while (r6.next()) {
                        calidad[1] = r6.getString("Proyecto");
                        pst6.setString(1, "" + p);
                        pst6.setString(2, calidad[1]);
                        n6 = pst6.executeUpdate();
                        System.out.println(cont);
                        cont++;
                    }

                    limpiarTabla();
//                    buscar();
                } else {
                    pst.setString(1, "" + p);
                    pst.setString(2, txtPlano.getText());

                    pst1.setString(1, "" + p);
                    pst1.setString(2, txtPlano.getText());

                    pst2.setString(1, "" + p);
                    pst2.setString(2, txtPlano.getText());

                    pst3.setString(1, "" + p);
                    pst3.setString(2, txtPlano.getText());

                    pst4.setString(1, "" + p);
                    pst4.setString(2, txtPlano.getText());

                    pst5.setString(1, "" + p);
                    pst5.setString(2, txtPlano.getText());

                    pst6.setString(1, "" + p);
                    pst6.setString(2, txtPlano.getText());

                    int n = pst.executeUpdate();
                    int n1 = pst1.executeUpdate();
                    int n2 = pst2.executeUpdate();
                    int n3 = pst3.executeUpdate();
                    int n4 = pst4.executeUpdate();
                    int n5 = pst5.executeUpdate();
                    int n6 = pst6.executeUpdate();
                    limpiarTabla();
//                    buscar();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "NO SE ACTUALIZO" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "DEBES INGRESAR UNA CANTIDAD CORRECTA");
        }
    }//GEN-LAST:event_btnPrioridadActionPerformed

    private void btnLiberarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiberarActionPerformed

    }//GEN-LAST:event_btnLiberarActionPerformed

    private void btnVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerActionPerformed
        verPdf(txtPlano.getText());
    }//GEN-LAST:event_btnVerActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        Date dato = new Date();
        SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        String fec;
        fec = fecha.format(dato);
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            int n1 = 0, n2 = 0, n3 = 0, n4 = 0, n5 = 0, n6 = 0, n7 = 0, n8 = 0, n9 = 0;
            if (txtPlano.equals("TODOS")) {
                String a = JOptionPane.showInputDialog("CONTRSASEÑA: ..........");
                if (a.equals(fec + "1234")) {
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
                    if (n1 > 0 && n2 > 0 && n3 > 0 && n4 > 0 && n5 > 0 && n6 > 0 && n7 > 0 && n8 > 0 && n9 > 0) {
                        JOptionPane.showMessageDialog(this, "DATOS BORRADOS CORRECTAMENTE");
                    }
                }

            } else {
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
            if (n1 > 0 && n2 > 0 && n3 > 0 && n4 > 0 && n5 > 0 && n6 > 0 && n7 > 0 && n8 > 0 && n9 > 0) {
                JOptionPane.showMessageDialog(this, "DATOS BORRADOS CORRECTAMENTE");

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL BORRAR " + e);
        }
    }//GEN-LAST:event_btnBorrarActionPerformed

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
        terminar = new TerminarProyecto(f, true, numEmpleado);
        terminar.setLocationRelativeTo(f);
        terminar.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void InformacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InformacionActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        InformacionProyectos info = new InformacionProyectos(f, true, TablaDeDatos1.getValueAt(TablaDeDatos1.getSelectedRow(), 1).toString());
        info.setLocationRelativeTo(f);
        info.setVisible(true);
    }//GEN-LAST:event_InformacionActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        buscarProyecto(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        try {
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

            if (n > 0) {
                JOptionPane.showMessageDialog(this, "PROYECTO LIBERADO");
                btnLiberar.setEnabled(true);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void InformacionPlanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InformacionPlanoActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        ReporteScrap rep = new ReporteScrap(f, true);
        rep.limpiarTabla();
        rep.jTextField1.setVisible(false);
        rep.verDatos("select * from scrap where Proyecto like '" + TablaDeDatos1.getValueAt(TablaDeDatos1.getSelectedRow(), 0) + "' order by id desc");
        rep.setVisible(true);
    }//GEN-LAST:event_InformacionPlanoActionPerformed

    private void InformacionProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InformacionProyectoActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        ReporteScrap rep = new ReporteScrap(f, true);
        rep.limpiarTabla();
        rep.jTextField1.setVisible(false);
        rep.verDatos("select * from scrap where Plano like '" + TablaDeDatos1.getValueAt(TablaDeDatos1.getSelectedRow(), 1) + "' order by id desc");
        rep.setVisible(true);
    }//GEN-LAST:event_InformacionProyectoActionPerformed

    private void jPopupMenu1PopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jPopupMenu1PopupMenuWillBecomeVisible
        if (TablaDeDatos1.getSelectedRow() == -1) {
            InformacionPlano.setEnabled(false);
            InformacionProyecto.setEnabled(false);
            verPdf.setEnabled(false);
            InformacionPlano.setText("Ver inofrmacion de plano (Intentos)");
            InformacionProyecto.setText("Ver inofrmacion de proyecto (Intentos)");
        } else {
            InformacionPlano.setEnabled(true);
            InformacionProyecto.setEnabled(true);
            verPdf.setEnabled(true);
            InformacionPlano.setText("Ver inofrmacion de plano " + TablaDeDatos1.getValueAt(TablaDeDatos1.getSelectedRow(), 0).toString() + " (Intentos)");
            InformacionProyecto.setText("Ver inofrmacion de proyecto " + TablaDeDatos1.getValueAt(TablaDeDatos1.getSelectedRow(), 1).toString() + " (Intentos)");
        }
    }//GEN-LAST:event_jPopupMenu1PopupMenuWillBecomeVisible

    private void verPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verPdfActionPerformed
        if (TablaDeDatos1.getSelectedRow() != -1) {
            verPdf(TablaDeDatos1.getValueAt(TablaDeDatos1.getSelectedRow(), 0).toString());
        }
    }//GEN-LAST:event_verPdfActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Informacion;
    private javax.swing.JMenuItem InformacionPlano;
    private javax.swing.JMenuItem InformacionProyecto;
    private javax.swing.JTable Tabla1;
    public javax.swing.JTable TablaDeDatos1;
    public javax.swing.JButton btnBorrar;
    public javax.swing.JButton btnExportarD;
    public javax.swing.JButton btnLiberar;
    public javax.swing.JButton btnPrioridad;
    private javax.swing.JPanel btnSalir;
    public javax.swing.JButton btnVer;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JLabel lblConteo;
    private javax.swing.JLabel lblX;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JLabel txtLiberado;
    private javax.swing.JLabel txtPlano;
    private javax.swing.JLabel txtProyecto;
    private javax.swing.JMenuItem verPdf;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
