package pruebas;

import Conexiones.Conexion;
import VentanaEmergente.Calendario.AgregarFechas;
import VentanaEmergente.Calendario.CodigoColores;
import VentanaEmergente.ProyectoManager.Editar;
import VentanaEmergente.ProyectoManager.InfoProyectos;
import VentanaEmergente.ProyectoManager.InformeProyect;
import VentanaEmergente.ProyectoManager.addFecha;
import VentanaEmergente.ProyectoManager.addPrioridadCompras;
import VentanaEmergente.ProyectoManager.filtrar;
import VentanaEmergente.Ventas.verDocumentos;
import VentanaEmergente.Ventas.ColorVentas;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import java.util.Stack;
import java.util.concurrent.TimeUnit;
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
import static org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND;
import org.apache.poi.ss.usermodel.Font;
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

public class ProyectManager extends javax.swing.JInternalFrame implements ActionListener{

    TableRowSorter<TableModel> elQueOrdena;
    verDocumentos verDoc;
    byte[] spec = null;
    byte[] coti = null;
    byte[] oc = null;
    byte[] fac = null;

    File espe = null;
    File cotizacion = null;
    File orden = null;
    File factura = null;

    JFileChooser seleccionar;
    JFileChooser selec;
    JFileChooser sel;
    JFileChooser sele;
    addFecha s;
    int row, col;
    String numEmpleado;
    filtrar filtro;
    Stack<InfoProyectos> proyectos;

    public void filtrarXProyecto(){
        limpiarTabla();
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            Date d1 = filtro.fecha1.getDatoFecha();
            Date d2 = filtro.fecha2.getDatoFecha();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String sql = "select Id,NumCotizacion,OC,Proyecto,Descripcion,FechaCreacion,"
                    + "Planta,FechaEntrega,Estatus, Facturado, Comentarios, Costo, Moneda,DueDate,Responsable from proyectos order by Id desc";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[20];
            while(rs.next()){
                datos[0] = rs.getString("Id");
                datos[1] = rs.getString("NumCotizacion");
                datos[2] = rs.getString("OC");
                datos[3] = rs.getString("Proyecto");
                datos[4] = rs.getString("Descripcion");
                datos[5] = rs.getString("FechaCreacion");
                datos[6] = rs.getString("Planta");
                datos[7] = rs.getString("FechaEntrega");
                datos[8] = rs.getString("Estatus");
                datos[9] = rs.getString("Facturado");
                datos[10] = rs.getString("Costo");
                datos[11] = rs.getString("Moneda");
                datos[13] = rs.getString("Comentarios");
                datos[14] = rs.getString("DueDate");
                datos[15] = rs.getString("Responsable");
                Date d  = sdf.parse(datos[5]);
                int com1 = d.compareTo(d1);
                int com2 = d.compareTo(d2);
                if(com1 > 0 && com2 < 0){
                    miModelo.addRow(datos);
                }
                }
            }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            Logger.getLogger(ProyectManager.class.getName()).log(Level.SEVERE, null, ex);
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

    public String getLetra(Date fechaAhora, String fechaTermino, String fechaFin, SimpleDateFormat sdf){
        Date fecTer;
        Date fecFin;
        try{fecTer = sdf.parse(fechaTermino);}catch(Exception e){fecTer = null;}
        try{fecFin = sdf.parse(fechaFin);}catch(Exception e){fecFin = null;}
        if(fecTer == null && fechaAhora.after(fecFin)){
            return "R";
        }else if(fecTer != null && fechaAhora.after(fecFin)){
            return "N";
        }else if(fecTer != null && fecTer.before(fechaAhora)){
            return "V";
        }
        return "";
    }
    
    public final void getAgenda(){
        try{
            proyectos = new Stack<>();
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from agenda where Estatus != 'Cancelado'";
            ResultSet rs = st.executeQuery(sql);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = new Date();
            while(rs.next()){
                String proyecto = rs.getString("Proyecto");
                String fechaFin = rs.getString("FechaFin");
                String fecha = rs.getString("FechaTermino");
                String depa = rs.getString("Departamento");
                proyectos.push(new InfoProyectos(proyecto,fechaFin + getLetra(d, fecha, fechaFin, sdf), depa));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: " + e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public int getProyecto(String proyecto, String depa){
        int pos = -1;
        for (int i = 0; i < proyectos.size(); i++) {
            InfoProyectos inf = proyectos.get(i);
            if(proyecto.equals(inf.getProyecto()) && inf.getDepa().equals(depa)){
                return i;
            }
        }
        return pos;
    }
    
    public final void buscar(String sql){
    DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
    try{
        Connection con;
        Conexion con1 = new Conexion();
        con = con1.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        String datos[] = new String[20];
        while(rs.next()){
            datos[0] = rs.getString("Id");
            datos[1] = rs.getString("NumCotizacion");
            datos[2] = rs.getString("OC");
            datos[3] = rs.getString("Proyecto");
            datos[4] = rs.getString("Descripcion");
            datos[5] = rs.getString("FechaCreacion");
            datos[6] = rs.getString("Planta");
            datos[7] = rs.getString("FechaEntrega");
            datos[8] = rs.getString("Estatus");
            datos[9] = rs.getString("Facturado");
            datos[10] = rs.getString("Costo");
            datos[11] = rs.getString("Moneda");
            try{datos[14] = proyectos.get(getProyecto(datos[3], "HERRAMENTISTA")).getFecha();}catch(Exception e){datos[14] = "";}
            try{datos[12] = proyectos.get(getProyecto(datos[3], "DISEÑO")).getFecha();}catch(Exception e){datos[12] = "";}
            try{datos[15] = proyectos.get(getProyecto(datos[3], "INTEGRACION")).getFecha();}catch(Exception e){datos[15] = "";}
            try{datos[13] = proyectos.get(getProyecto(datos[3], "COMPRAS")).getFecha();}catch(Exception e){datos[13] = "";}
            datos[16] = rs.getString("FechaCierre");
            miModelo.addRow(datos);
            }
        }catch(SQLException e){
        JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
    }
}
    
    public final void limpiarTabla(){
        Tabla1 = new ColorVentas();
        Tabla1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "ID","NO COTIZACION", "ORDEN COMPRA", "PROYECTO", "DESCRIPCION", "FECHA", "PLANTA", "FECHA COMPROMISO", "ESTATUS", "FACTURADO", "COSTO", "MONEDA",
                "DISENO", "COMPRAS", "MAQUINADOS", "INTEGRACION", "FECHA CIERRE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        Tabla1.getTableHeader().setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
        Tabla1.setGridColor(new Color(240,240,240));
        scrollTabla.getViewport().setBackground(Color.white);
        scrollTabla.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setMinWidth(0);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(0);
            Tabla1.getColumnModel().getColumn(0).setMaxWidth(0);
        }
    }
    
    public ProyectManager(String numEmpleado) {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.numEmpleado = numEmpleado;
        getAgenda();
        limpiarTabla();
        buscar("select Id,NumCotizacion,OC,Proyecto,Descripcion,FechaCreacion,"
                + "Planta,FechaEntrega,Estatus, Facturado, Comentarios, Costo, Moneda, FechaCierre from proyectos order by Id desc");
        DefaultTableModel Modelo = (DefaultTableModel) Tabla1.getModel();
        elQueOrdena = new TableRowSorter<>(Modelo);
        Tabla1.setRowSorter(elQueOrdena);
        scrollTabla.setVerticalScrollBar(new ScrollBarCustom(new java.awt.Color(0,165,255)));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        editar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        verDocumentos = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        filtrar = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        agregarFecha = new javax.swing.JMenuItem();
        eliminarFecha = new javax.swing.JMenuItem();
        informe = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        scrollTabla = new javax.swing.JScrollPane();
        Tabla1 = new ColorVentas();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        btnVer = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        pan = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        jPopupMenu1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jPopupMenu1PopupMenuWillBecomeVisible(evt);
            }
        });

        editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/editar (1).png"))); // NOI18N
        editar.setText("Editar          ");
        editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(editar);
        jPopupMenu1.add(jSeparator1);

        verDocumentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pdf.png"))); // NOI18N
        verDocumentos.setText("Ver documentos                   ");
        verDocumentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verDocumentosActionPerformed(evt);
            }
        });
        jPopupMenu1.add(verDocumentos);
        jPopupMenu1.add(jSeparator2);

        filtrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/filtrar.png"))); // NOI18N
        filtrar.setText("Filtrar por proyecto");
        filtrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtrarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(filtrar);
        jPopupMenu1.add(jSeparator3);

        agregarFecha.setText("Agregar fecha a proyecto ");
        agregarFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarFechaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(agregarFecha);

        eliminarFecha.setText("Eliminar fecha de proyecto");
        jPopupMenu1.add(eliminarFecha);

        informe.setText("Eliminar fecha de proyecto");
        informe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                informeActionPerformed(evt);
            }
        });
        jPopupMenu1.add(informe);

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        scrollTabla.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        Tabla1.setBackground(new java.awt.Color(255, 255, 255));
        Tabla1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NO REQUISICION", "NO COTIZACION", "ORDEN COMPRA", "PROYECTO", "DESCRIPCION", "FECHA", "PLANTA", "FECHA COMPROMISO", "ESTATUS", "FACTURADO", "COSTO", "MONEDA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        Tabla1.setGridColor(new java.awt.Color(255, 255, 255));
        Tabla1.setRowHeight(25);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Tabla1MouseEntered(evt);
            }
        });
        scrollTabla.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setMinWidth(0);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(0);
            Tabla1.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jPanel2.add(scrollTabla, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel1.setText("Buscar:");
        jPanel3.add(jLabel1);

        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jTextField1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        jTextField1.setPreferredSize(new java.awt.Dimension(300, 25));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel3.add(jTextField1);

        btnVer.setBackground(new java.awt.Color(255, 255, 255));
        btnVer.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnVer.setForeground(new java.awt.Color(51, 51, 51));
        btnVer.setText("Ver todos");
        btnVer.setBorder(null);
        btnVer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVer.setFocusPainted(false);
        btnVer.setPreferredSize(new java.awt.Dimension(120, 25));
        btnVer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVerMouseExited(evt);
            }
        });
        btnVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerActionPerformed(evt);
            }
        });
        jPanel3.add(btnVer);

        jButton1.setBackground(new java.awt.Color(255, 102, 0));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Ver codigos de colores");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Lexend", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 165, 252));
        jLabel12.setText("Proyectos");
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

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        jMenuItem1.setText("     Prioridad en compras     ");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/excel_1.png"))); // NOI18N
        jMenuItem2.setText("Descargar Excel");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        if(Tabla1.getSelectedColumn() == 14){
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            row = Tabla1.getSelectedRow();
            col = Tabla1.getSelectedColumn();
            s = new addFecha(f,true);
            s.btnGuardar.addActionListener(this);
            s.btnCancelar.addActionListener(this);
            s.setLocation(evt.getLocationOnScreen().x-370,evt.getLocationOnScreen().y);
            s.setVisible(true);
        }else if(evt.getClickCount() == 2){
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            Editar editar = new Editar(f,true,this);
            int fila = Tabla1.getSelectedRow();
            String re,coti, oc,pro,des,est,fac,com,resp, valor,moneda;
            if(Tabla1.getValueAt(fila, 0) == null){
                re = "";
            }else{
                re = Tabla1.getValueAt(fila, 0).toString();
            }
            editar.txtId.setText(re);

            if(Tabla1.getValueAt(fila, 10) == null){
                valor = "";
            }else{
                valor = Tabla1.getValueAt(fila, 10).toString();
            }
                System.out.println(valor);
            editar.txtValor.setText(valor);

            if(Tabla1.getValueAt(fila, 11) == null){
                moneda = "";
            }else{
                moneda = Tabla1.getValueAt(fila, 11).toString();
            }
            editar.jcbMoneda.setSelectedItem(moneda);

            if(Tabla1.getValueAt(fila, 1) == null){
                coti = "";
            }else{
                coti = Tabla1.getValueAt(fila, 1).toString();
            }
            editar.txtCotizacion.setText(coti);
            if(Tabla1.getValueAt(fila, 2) == null){
                oc = "";
            }else{
                oc = Tabla1.getValueAt(fila, 2).toString();
            }
            editar.txtOrden.setText(oc);
            if(Tabla1.getValueAt(fila, 3) == null){
                pro = "";
            }else{
                pro = Tabla1.getValueAt(fila, 3).toString();
            }
            editar.txtProyecto.setText(pro);
            if(Tabla1.getValueAt(fila, 4) == null){
                des = "";
            }else{
                des = Tabla1.getValueAt(fila, 4).toString();
            }
            editar.txtDescripcion.setText(des);
            if(Tabla1.getValueAt(fila, 8) == null){
                est = "";
            }else{
                est = Tabla1.getValueAt(fila, 8).toString();
            }

            if(Tabla1.getValueAt(fila, 9) == null){
                fac = "";
            }else{
                fac = Tabla1.getValueAt(fila, 9).toString();
            }

            if(Tabla1.getValueAt(fila, 13) == null){
                com = "";
            }else{
                com = Tabla1.getValueAt(fila, 13).toString();
            }

            if(Tabla1.getValueAt(fila, 15) == null){
                resp = "";
            }else{
                resp = Tabla1.getValueAt(fila, 15).toString();
            }
            editar.txtResponsable.setText(resp);
            editar.txtAcciones.setText(com);
            if(est.equals("")){
                editar.cmbEstatus.setSelectedIndex(0);
            }else{
                editar.cmbEstatus.setSelectedItem(est);
            }

            if(fac.equals("") || fac.equals("NO")){
                editar.facturado.setSelected(false);
            }else if(fac.equals("SI")){
                editar.facturado.setSelected(true);
            }
            editar.setVisible(true);
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        addPrioridadCompras d = new addPrioridadCompras(f, false,numEmpleado);
        d.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
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
        
        Sheet hoja = book.createSheet("REPORTE DE PROYECTO");
        Row fila = hoja.createRow(2);
        Cell col = fila.createCell(2);
        
        
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
        
        hoja.setColumnWidth(2, 3000);
        hoja.setColumnWidth(3, 3000);
        hoja.setColumnWidth(4, 3000); 
        hoja.setColumnWidth(6, 15000); 
        hoja.setColumnWidth(5, 3000); 
        hoja.setColumnWidth(7, 3000); 
        hoja.setColumnWidth(8, 3000); 
        hoja.setColumnWidth(9, 3000); 
        hoja.setColumnWidth(10, 3000); 
        hoja.setColumnWidth(11, 3000); 
        hoja.setColumnWidth(12, 3000); 
        hoja.setColumnWidth(13, 3000);
        hoja.setColumnWidth(14, 3000);
        hoja.setColumnWidth(15, 15000);
        hoja.setColumnWidth(16, 3000);
        
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
        16
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
        
        for (int i = -1; i < Tabla1.getRowCount(); i++) {
                Row fila10=hoja.createRow(i+7);
                for (int j = 0; j < Tabla1.getColumnCount(); j++) {
                    Cell celda=fila10.createCell(j+2);
                    if(i == -1 && (j >= 0 && j <=16)){
                        CellStyle s = book.createCellStyle();
                        Font f = book.createFont();
                        f.setBold(true);
                        f.setColor(IndexedColors.WHITE.getIndex());
                        s.setFont(f);
                        s.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }
                    if(i > -1 && (j > -1 && j <= 16) && (i%2 == 0)){
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
    } catch (FileNotFoundException ex) {
        Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void Tabla1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Tabla1MouseEntered

    private void editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        Editar editar = new Editar(f,true,this);
        int fila = Tabla1.getSelectedRow();
        String re,coti, oc,pro,des,est,fac,com,resp, valor, moneda;
        if(Tabla1.getValueAt(fila, 0) == null){
            re = "";
        }else{
            re = Tabla1.getValueAt(fila, 0).toString();
        }
        editar.txtId.setText(re);
        if(Tabla1.getValueAt(fila, 1) == null){
            coti = "";
        }else{
            coti = Tabla1.getValueAt(fila, 1).toString();
        }
        editar.txtCotizacion.setText(coti);
        if(Tabla1.getValueAt(fila, 2) == null){
            oc = "";
        }else{
            oc = Tabla1.getValueAt(fila, 2).toString();
        }
        editar.txtOrden.setText(oc);
        if(Tabla1.getValueAt(fila, 3) == null){
            pro = "";
        }else{
            pro = Tabla1.getValueAt(fila, 3).toString();
        }
        editar.txtProyecto.setText(pro);
        if(Tabla1.getValueAt(fila, 4) == null){
            des = "";
        }else{
            des = Tabla1.getValueAt(fila, 4).toString();
        }
        editar.txtDescripcion.setText(des);
        if(Tabla1.getValueAt(fila, 8) == null){
            est = "";
        }else{
            est = Tabla1.getValueAt(fila, 8).toString();
        }
        
        if(Tabla1.getValueAt(fila, 9) == null){
            fac = "";
        }else{
            fac = Tabla1.getValueAt(fila, 9).toString();
        }
        
        if(Tabla1.getValueAt(fila, 10) == null){
            valor = "";
        }else{
            valor = Tabla1.getValueAt(fila, 10).toString();
        }
            System.out.println(valor);
        editar.txtValor.setText(valor);
        
        if(Tabla1.getValueAt(fila, 11) == null){
            moneda = "";
        }else{
            moneda = Tabla1.getValueAt(fila, 11).toString();
        }
        editar.jcbMoneda.setSelectedItem(moneda);
        
        if(Tabla1.getValueAt(fila, 13) == null){
            com = "";
        }else{
            com = Tabla1.getValueAt(fila, 13).toString();
        }
        if(Tabla1.getValueAt(fila, 15) == null){
            resp = "";
        }else{
            resp = Tabla1.getValueAt(fila, 15).toString();
        }
        editar.txtResponsable.setText(resp);
        editar.txtAcciones.setText(com);
        if(est.equals("")){
            editar.cmbEstatus.setSelectedIndex(0);
        }else{
            editar.cmbEstatus.setSelectedItem(est);
        }

        if(fac.equals("") || fac.equals("NO")){
            editar.facturado.setSelected(false);
        }else if(fac.equals("SI")){
            editar.facturado.setSelected(true);
        }
        editar.setVisible(true);
    }//GEN-LAST:event_editarActionPerformed

    private void verDocumentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verDocumentosActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        verDoc = new verDocumentos(f,true,Tabla1.getValueAt(Tabla1.getSelectedRow(), 3).toString());
        verDoc.lblProyecto.setText(Tabla1.getValueAt(Tabla1.getSelectedRow(), 3).toString());
        verDoc.setVisible(true);
    }//GEN-LAST:event_verDocumentosActionPerformed

    private void filtrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtrarActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        filtro = new filtrar(f,true);
        filtro.btnFiltrar.addActionListener(this);
        filtro.setVisible(true);
    }//GEN-LAST:event_filtrarActionPerformed

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

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        String text = jTextField1.getText();
        String sql = "select * from proyectos where Proyecto like '" + text + "%' or NumCotizacion like '" + text + "%' or OC like '" + text + "%'";
        limpiarTabla();
        buscar(sql);
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void btnVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerActionPerformed
        limpiarTabla();
        buscar("select Id,NumCotizacion,OC,Proyecto,Descripcion,FechaCreacion,"
                + "Planta,FechaEntrega,Estatus, Facturado, Comentarios, Costo, Moneda from proyectos order by Id desc");
    }//GEN-LAST:event_btnVerActionPerformed

    private void btnVerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerMouseEntered
        btnVer.setBackground(new Color(0,102,204));
        btnVer.setForeground(Color.white);
    }//GEN-LAST:event_btnVerMouseEntered

    private void btnVerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerMouseExited
        btnVer.setBackground(Color.white);
        btnVer.setForeground(new Color(51,51,51));
    }//GEN-LAST:event_btnVerMouseExited

    private void agregarFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarFechaActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        AgregarFechas agregar = new AgregarFechas(f, true, numEmpleado);
        agregar.setLocationRelativeTo(f);
        agregar.txtProyecto.setText(Tabla1.getValueAt(Tabla1.getSelectedRow(), 3).toString());
        agregar.buscarProyectos(Tabla1.getValueAt(Tabla1.getSelectedRow(), 3).toString());
        agregar.setVisible(true);
    }//GEN-LAST:event_agregarFechaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        CodigoColores codigo = new CodigoColores(f, true);
        codigo.setLocationRelativeTo(f);
        codigo.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jPopupMenu1PopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jPopupMenu1PopupMenuWillBecomeVisible
        if (Tabla1.getSelectedRow() != -1) {
            informe.setEnabled(true);
            informe.setText("Ver informe completo de proyecto " + Tabla1.getValueAt(Tabla1.getSelectedRow(), 3));
        }else {
            informe.setText("Ver informe completo de proyecto");
            informe.setEnabled(false);
        }
    }//GEN-LAST:event_jPopupMenu1PopupMenuWillBecomeVisible

    private void informeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informeActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        InformeProyect info = new InformeProyect(f,true, Tabla1.getValueAt(Tabla1.getSelectedRow(), 3).toString());
        info.txtProyecto.setText(Tabla1.getValueAt(Tabla1.getSelectedRow(), 3).toString());
        info.setVisible(true);
    }//GEN-LAST:event_informeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JMenuItem agregarFecha;
    private javax.swing.JButton btnVer;
    private javax.swing.JMenuItem editar;
    private javax.swing.JMenuItem eliminarFecha;
    private javax.swing.JMenuItem filtrar;
    private javax.swing.JMenuItem informe;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelSalir;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JMenuItem verDocumentos;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(filtro != null){
            if(e.getSource() == filtro.btnFiltrar){
                filtrarXProyecto();
            }
        }
        
        if(s != null){
            if(e.getSource() == this.s.btnGuardar){
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String hora = (String) sdf.format(s.fecha.getDatoFecha());
                Tabla1.setValueAt(hora, row, col);
                try{
                    Connection con;
                    Conexion con1 = new Conexion();
                    con = con1.getConnection();
                    Statement st = con.createStatement();
                    String sql = "update proyectos set DueDate = ? where Id = ?";
                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, hora);
                    pst.setString(2, Tabla1.getValueAt(row, 0).toString());
                    int n = pst.executeUpdate();

                    if(n == 0){
                        JOptionPane.showMessageDialog(this, "INFORMACION NO GUARDADA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                    }

                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, "ERROR: "+ex,"ERROR",JOptionPane.ERROR_MESSAGE);
                }
                s.dispose();
            }
        }
    }
}
