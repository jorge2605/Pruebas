package pruebas;

import Componentes.PanelMultiColor;
import Conexiones.Conexion;
import VentanaEmergente.Calendario.AgregarFechas;
import VentanaEmergente.Calendario.EliminarFecha;
import VentanaEmergente.Calendario.InfoAgenda;
import VentanaEmergente.Calendario.Modelo.PropiedadesFechas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Calendario extends javax.swing.JInternalFrame {

    public String numEmpleado;
    public String nomEmpleado;
    public String departamento;
    Stack<PropiedadesFechas> props;
    
    public void limpiarPanel(){
        jPanel3.removeAll();
        revalidate();
        repaint();
    }
    
    public int getDay(String day){
        switch(day){
            case "LUNES":
                return 0;
            case "MARTES":
                return 1;
            case "MIÉRCOLES":
                return 2;
            case "JUEVES":
                return 3;
            case "VIERNES":
                return 4;
            case "SÁBADO":
                return 5;
            case "DOMINGO":
                return 6;
            default:
                return -1;
        }
    }
    
    public boolean compararDias(String fecha, String inicio, String termino){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d1 = sdf.parse(fecha);
            Date d2 = sdf.parse(inicio);
            Date d3 = sdf.parse(termino);
            return (d1.equals(d2) || d1.after(d2)) && (d1.equals(d3) || d1.before(d3));
        } catch (ParseException ex) {
            Logger.getLogger(Calendario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public final void setCalendario(){
        int mes = jComboBox1.getSelectedIndex()+1;
        int ano = Ano.getYear();
        int dia = 1;
        LocalDate fecha = LocalDate.of(ano, mes, dia);
        LocalDate primerDia = fecha.withDayOfMonth(1);
        String diaSemana = primerDia.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es","ES"));
        LocalDate fechaEspecifica = LocalDate.of(ano, mes, dia);
        
        int ultimoDia = fechaEspecifica.lengthOfMonth();
        limpiarPanel();
        boolean day = false;
        int cont = 1;
        for (int i = 0; i < 42; i++) {
            if(getDay(diaSemana.toUpperCase()) == i){
                day = true;
            }
            JPanel panel = new JPanel();
            if(day && cont <= ultimoDia){
                String fec = ano + "-" + mes + "-" + cont;
                Stack<Color> pila = new Stack<>();
                for (int j = 0; j < props.size(); j++) {
                    if(compararDias(fec, props.get(j).getFechaInicio(), props.get(j).getFechaFinal())){
                        int r = Integer.parseInt(props.get(j).getColor().split(",")[0]);
                        int g = Integer.parseInt(props.get(j).getColor().split(",")[1]);
                        int b = Integer.parseInt(props.get(j).getColor().split(",")[2]);
                        pila.push(new Color(r,g,b));
                    }
                }
                if(pila.isEmpty()){
                    pila.push(Color.white);
                }
                panel = new PanelMultiColor(pila);
                panel.setBackground(new java.awt.Color(255, 255, 255));
                panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230)));
                java.awt.GridBagLayout pnl1Layout = new java.awt.GridBagLayout();
                pnl1Layout.columnWeights = new double[] {1.0};
                pnl1Layout.rowWeights = new double[] {1.0};
                panel.setLayout(pnl1Layout);
                JLabel label = new javax.swing.JLabel();
                label.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
                label.setText(String.valueOf(cont));
                panel.add(label, new java.awt.GridBagConstraints());
                cont++;
            } else {
                panel.setBackground(new Color(230,230,230));
            }
            jPanel3.add(panel);
        }
    }
    
    public void crearFecha(String proyecto, String color, String f1, String f2, String descripcion, String id, JFrame f){
        JButton boton = new javax.swing.JButton();
        boton.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        boton.setForeground(new java.awt.Color(51, 51, 51));
        String[] rgb = color.split(",");
        int r = Integer.parseInt(rgb[0]);
        int g = Integer.parseInt(rgb[1]);
        int b = Integer.parseInt(rgb[2]);
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InfoAgenda info = new InfoAgenda(null, true, nomEmpleado);
                info.lblId.setText(id);
                info.lblId.setForeground(new Color(r,g,b));
                info.setColores(new Color(r,g,b));
                info.verInformacion(id);
                info.panelColor.setBackground(new Color(r,g,b));
                info.setLocationRelativeTo(f);
                boolean band = info.ver();
                if (band) {
                    verFechas(f);
                    setCalendario();
                }
            }
        });
        boton.setText("<html>\n<div style='width:200px; border-bottom: 5px solid white;'>"
                + "<p style='font-size:10px; font-weight: 700;'>" + proyecto + "</p>"
                + "<p>" + descripcion + "</p>"
                + "<span style='color: rgb(150,150,150); font-size: 10;'>" + f1 + "</span>"
                + "<span style='color: rgb(" + color + "); font-size: 10;'>" + f2 + "</span>"
                + "</div>"
                + "</html>");
        
        boton.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true), 
                javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 5, 0, 0, new java.awt.Color(r,g,b)), 
                        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3))));
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel10.add(boton);
    }
    
    public final void verFechas(JFrame f){
        try{
            props = new Stack<>();
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from agenda where Departamento like '" + departamento + "' and Estatus like 'Nuevo'";
            ResultSet rs = st.executeQuery(sql);
            jPanel10.removeAll();
            repaint();
            revalidate();
            while(rs.next()){
                String proyecto = rs.getString("Proyecto");
                String id = rs.getString("IdAgenda");
                String color = rs.getString("Color");
                String f1 = rs.getString("FechaInicio");
                String f2 = rs.getString("FechaFin");
                String descripcion = rs.getString("Descripcion");
                props.push(new PropiedadesFechas(f1, f2, color));
                crearFecha(proyecto, color, f1, f2, descripcion, id, f);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: "+e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void setMes(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        int mes = Integer.parseInt(sdf.format(d));
        jComboBox1.setSelectedIndex(mes - 1);
    }
    
    public final void setEmpleado(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroempleados where NumEmpleado like '" + numEmpleado + "'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                departamento = rs.getString("Puesto");
                nomEmpleado = rs.getString("Nombre") + " " + rs.getString("Apellido");
                String sup = rs.getString("Super");
                if(sup != null){
                    if(sup.equals("SI")){
                        panelAdministrador.setVisible(true);
                    }else{
                        panelAdministrador.setVisible(false);
                    }
                }
            }
            System.out.println(departamento);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: " + e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public Calendario(String numEmpleado, JFrame f) {
        initComponents();
        this.numEmpleado = numEmpleado;
        setEmpleado();
        setMes();
        verFechas(f);
        setCalendario();
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(15);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        pan = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        Ano = new com.toedter.calendar.JYearChooser();
        panelAdministrador = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel10 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 50, 5));

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 165, 252));
        jLabel12.setText("Calendario");
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
        jPanel2.setLayout(new java.awt.BorderLayout(20, 0));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jComboBox1.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel11.add(jComboBox1);

        Ano.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.add(Ano);

        jPanel8.add(jPanel11, java.awt.BorderLayout.CENTER);

        panelAdministrador.setBackground(new java.awt.Color(255, 255, 255));
        panelAdministrador.setLayout(new java.awt.BorderLayout());

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Administrador");
        panelAdministrador.add(jLabel8, java.awt.BorderLayout.NORTH);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(0, 153, 0));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Agregar fecha");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton1);

        jButton2.setBackground(new java.awt.Color(153, 0, 0));
        jButton2.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Eliminar fecha");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton2);

        panelAdministrador.add(jPanel13, java.awt.BorderLayout.CENTER);

        jPanel8.add(panelAdministrador, java.awt.BorderLayout.PAGE_START);

        jPanel2.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Lunes");
        jPanel6.add(jLabel1);

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Martes");
        jPanel6.add(jLabel2);

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Miercoles");
        jPanel6.add(jLabel3);

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Jueves");
        jPanel6.add(jLabel4);

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Viernes");
        jPanel6.add(jLabel5);

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Sabado");
        jPanel6.add(jLabel6);

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Domingo");
        jPanel6.add(jLabel7);

        jPanel7.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridLayout(6, 7, 3, 3));
        jPanel7.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.Y_AXIS));

        jLabel53.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(51, 51, 51));
        jLabel53.setText("<html>\n<div style='width:200px; border-bottom: 5px solid white;'>\n<p style='font-size:10px; font-weight: 700;'>Proyecto</p>\n<p> Descripcion de mi proyecto</p>\n<span style='color: rgb(150,150,150); font-size: 10;'>2024-11-20</span>\n<span style='color: rgb(0,102,204); font-size: 10;'>2024-11-28</span>\n</div>\n</html>");
        jLabel53.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 5, 0, 0, new java.awt.Color(0, 102, 204)), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3))));
        jLabel53.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel10.add(jLabel53);

        jLabel54.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(51, 51, 51));
        jLabel54.setText("<html>\n<p style='width:200px; border-bottom: 5px solid white;'>Proyecto 1012  activo para la recepcion de proyectos es la hora iundicada en su matricula</p>\n<span style='color: rgb(150,150,150); font-size: 10;'>2024-11-20</span>\n<span style='color: rgb(255,51,51); font-size: 10;'>2024-11-28</span>\n</html>");
        jLabel54.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 5, 0, 0, new java.awt.Color(255, 51, 51)), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3))));
        jLabel54.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel10.add(jLabel54);

        jScrollPane1.setViewportView(jPanel10);

        jPanel9.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel9, java.awt.BorderLayout.EAST);

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

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if(this.isVisible()){
            setCalendario();
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        AgregarFechas calendario = new AgregarFechas(f,true,this.numEmpleado);
        calendario.setLocationRelativeTo(f);
        calendario.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        EliminarFecha eliminar = new EliminarFecha(f,true);
        eliminar.setLocationRelativeTo(f);
        eliminar.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JYearChooser Ano;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelAdministrador;
    private javax.swing.JPanel panelSalir;
    // End of variables declaration//GEN-END:variables
}
