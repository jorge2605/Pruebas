package pruebas;

import Conexiones.Conexion;
import Conexiones.ConexionChat;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

public class RegistrarEmpleado extends javax.swing.JFrame {

    String puesto;
    public RegistrarEmpleado() {
        initComponents();
//        deshabilitar();
        txtCodigo.setEnabled(false);
        this.setTitle("SERVICIOS INDUSTRIALES 3i");
        this.setIconImage(new ImageIcon(getClass().getResource("/Imagenes/Imagen1.png")).getImage());
    }

    public void actualizar(){
        if(txtNombre.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN EMPLEADO","ADVERTENCIA",JOptionPane.ERROR_MESSAGE);
        }else{
            try{
                Connection con = null;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql = "update registroempleados set Nombre = ?,Apellido = ?,Direccion = ?,Telefono = ?,Puesto = ?,Diseño = ?,Cambio = ?,Reportes = "
                        + "?,Carga = ?,Ventas = ?,Cortes = ?,Fresa = ?,Cnc = ?,Torno = ?,Acabados = ?,Calidad = ?,Tratamiento = ?,Electrico = ?,"
                        + "CrearEmpleado = ?,VerEmpleado = ?,Inventario = ?,Ensamble = ?,InventarioPlanos = ?,Requisiciones = ?,Orden = ?,Aprobacion = ?,"
                        + "Recibo = ?,Prestamo = ?,Cotizacion = ?,VerRequisiciones = ?,Cotizar = ?, ProyectMan = ?, Checador = ?, Entrega = ?,Pedidos = ? where NumEmpleado = ?";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, txtNombre.getText());
                pst.setString(2, txtApellido.getText());
                pst.setString(3, txtDireccion.getText());
                pst.setString(4, txtTelefono.getText());
                pst.setString(5, (String) cmbPuesto.getSelectedItem());
                pst.setBoolean(6, diseño.isSelected());
                pst.setBoolean(7, estados.isSelected());
                pst.setBoolean(8, reportes.isSelected());
                pst.setBoolean(9, carga.isSelected());
                pst.setBoolean(10, ventas.isSelected());
                pst.setBoolean(11, cortes.isSelected());
                pst.setBoolean(12, fresadora.isSelected());
                pst.setBoolean(13, cnc.isSelected());
                pst.setBoolean(14, torno.isSelected());
                pst.setBoolean(15, acabados.isSelected());
                pst.setBoolean(16, calidad.isSelected());
                pst.setBoolean(17, tratamiento.isSelected());
                pst.setBoolean(18, electrico.isSelected());
                pst.setBoolean(19, crear.isSelected());
                pst.setBoolean(20, verEmpleado.isSelected());
                pst.setBoolean(21, inventario.isSelected());
                pst.setBoolean(22, ensambles.isSelected());
                pst.setBoolean(23, invPlanos.isSelected());
                pst.setBoolean(24, requisiciones.isSelected());
                pst.setBoolean(25, compras.isSelected());
                pst.setBoolean(26, aprobacion.isSelected());
                pst.setBoolean(27, recibos.isSelected());
                pst.setBoolean(28, prestamos.isSelected());
                pst.setBoolean(29, cotizacion.isSelected());
                pst.setBoolean(30, verRequisicion.isSelected());
                pst.setBoolean(31, cotizar.isSelected());
                pst.setBoolean(32, rSCheckBox8.isSelected());
                pst.setBoolean(33, checador.isSelected());
                pst.setBoolean(34, entrega.isSelected());
                pst.setBoolean(35, pedidos.isSelected());
                pst.setString(36, txtCodigo.getText());

                int n = pst.executeUpdate();

                if(n > 0){
                    JOptionPane.showMessageDialog(this, "DATOS ACTUALIZADOS CORRECTAMENTE");
                }

            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void guardar(){
        if(txtNombre.getText().isEmpty()){

            JOptionPane.showMessageDialog(this, "EL CAMPO NOMBRE ESTA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);

        }else if(txtApellido.getText().isEmpty()){

            JOptionPane.showMessageDialog(this, "EL CAMPO APELLIDO ESTA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);

        }else if(txtDireccion.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "EL CAMPO DIRECCION ESTA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtTelefono.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "EL CAMPO TELEFONO ESTA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(cmbPuesto.getSelectedItem().equals("SELECCIONAR")){
            JOptionPane.showMessageDialog(this, "EL CAMPO PUESTO AUN NO A SIDO SELECCIONADO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtContra.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "EL CAMPO CONTRASEÑA ESTA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtRepetir.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "EL CAMPO REPEITR CONTRASEÑA ESTA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtContra.getText() == null ? txtRepetir.getText() != null : !txtContra.getText().equals(txtRepetir.getText())){
            JOptionPane.showMessageDialog(this, "LA CONTRASEÑA NO COINCIDE","",JOptionPane.WARNING_MESSAGE);
        }else if(txtNumero.getText() == null ? txtRepetir.getText() != null : !txtContra.getText().equals(txtRepetir.getText())){
            JOptionPane.showMessageDialog(this, "EL CAMPO NUMERO DE EMPLEADO ESTA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else
        {
            {

                puesto = (String) cmbPuesto.getSelectedItem();

                try{

                    Connection con2 = null;
                    Conexion c = new Conexion();
                    con2 = c.getConnection();
                    Statement st3 = con2.createStatement();
                    String sql = "insert into registroEmpleados (Nombre,Apellido,Direccion,Telefono,Puesto,Contraseña,NumEmpleado,Diseño,Cambio,Reportes,"
                            + "Carga,Ventas,Cortes,Fresa,Cnc,Torno,Acabados,Calidad,Tratamiento,Electrico,CrearEmpleado,VerEmpleado,Inventario,Ensamble,"
                            + "InventarioPlanos,Requisiciones,Orden,Aprobacion,Recibo,Prestamo,Cotizacion,VerRequisiciones,Cotizar,ProyectMan, Checador, Entrega, Pedidos,"
                            + "Herramentista, Disenio, Administracion, Almacen, Super,Remisiones,Puerto) "
                            + "values (?,?,?,?,?,AES_ENCRYPT(?,'mi_llave'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement pst3 = con2.prepareStatement(sql);

                    byte dato[] = txtContra.getText().getBytes();
                    Blob blob= new SerialBlob(dato);

                    pst3.setString(1, txtNombre.getText());
                    pst3.setString(2, txtApellido.getText());
                    pst3.setString(3, txtDireccion.getText());
                    pst3.setString(4, txtTelefono.getText());
                    pst3.setString(5, puesto);
                    pst3.setBlob(6, blob);
                    pst3.setString(7, txtNumero.getText());
                    pst3.setBoolean(8, diseño.isSelected());
                    pst3.setBoolean(9, estados.isSelected());
                    pst3.setBoolean(10, reportes.isSelected());
                    pst3.setBoolean(11, carga.isSelected());
                    pst3.setBoolean(12, ventas.isSelected());
                    pst3.setBoolean(13, cortes.isSelected());
                    pst3.setBoolean(14, fresadora.isSelected());
                    pst3.setBoolean(15, cnc.isSelected());
                    pst3.setBoolean(16, torno.isSelected());
                    pst3.setBoolean(17, acabados.isSelected());
                    pst3.setBoolean(18, calidad.isSelected());
                    pst3.setBoolean(19, tratamiento.isSelected());
                    pst3.setBoolean(20, electrico.isSelected());
                    pst3.setBoolean(21, crear.isSelected());
                    pst3.setBoolean(22, verEmpleado.isSelected());
                    pst3.setBoolean(23, inventario.isSelected());
                    pst3.setBoolean(24, ensambles.isSelected());
                    pst3.setBoolean(25, invPlanos.isSelected());
                    pst3.setBoolean(26, requisiciones.isSelected());
                    pst3.setBoolean(27, compras.isSelected());
                    pst3.setBoolean(28, aprobacion.isSelected());
                    pst3.setBoolean(29, recibos.isSelected());
                    pst3.setBoolean(30, prestamos.isSelected());
                    pst3.setBoolean(31, cotizacion.isSelected());
                    pst3.setBoolean(32, verRequisicion.isSelected());
                    pst3.setBoolean(33, cotizar.isSelected());
                    pst3.setBoolean(34, rSCheckBox8.isSelected());
                    pst3.setBoolean(35, checador.isSelected());
                    pst3.setBoolean(36, entrega.isSelected());
                    pst3.setBoolean(37, pedidos.isSelected());
                    
                    pst3.setString(38, "NO");
                    pst3.setString(39, "NO");
                    pst3.setString(40, "NO");
                    pst3.setString(41, "NO");
                    pst3.setString(42, "NO");
                    pst3.setString(43, "0");
                    
                    String sql4 = "select Puerto from registroempleados";
                    Statement st4 = con2.createStatement();
                    ResultSet rs4 = st4.executeQuery(sql4);
                    int puerto = 0;
                    while(rs4.next()){
                        try{
                            puerto = Integer.parseInt(rs4.getString("Puerto")) + 5;
                        }catch(NumberFormatException e){
                            puerto = 0;
                        }
                    }
                    pst3.setString(44, String.valueOf(puerto));
                    int n = pst3.executeUpdate();
                    
                    
                    String crearNotificacion = "CREATE TABLE `noti"+txtNumero.getText()+"` (\n" +
                            "  `Id` int NOT NULL AUTO_INCREMENT,\n" +
                            "  `Departamento` varchar(45) DEFAULT NULL,\n" +
                            "  `Titulo` varchar(100) DEFAULT NULL,\n" +
                            "  `Texto` varchar(500) DEFAULT NULL,\n" +
                            "  `Visto` varchar(45) DEFAULT NULL,\n" +
                            "  `Fecha` varchar(45) DEFAULT NULL,\n" +
                            "  `Visto2` varchar(45) DEFAULT NULL,\n" +
                            "  PRIMARY KEY (`Id`)\n" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
                    
                    Connection con3;
                    ConexionChat con4 = new ConexionChat();
                    con3 = con4.getConnection();
                    Statement st5 = con3.createStatement();
                    st5.execute(crearNotificacion);
                    
                    if(n>0){

                        JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                        borrarDatos();
                        deshabilitar();
                    }

                }catch(SQLException ex){

                    JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR DATOS"+ex);

                }

            }
        }
    }
    
    public void deshabilitar(){
    
        txtNombre.setEnabled(false);
        txtCodigo.setEnabled(false);
        txtApellido.setEnabled(false);
        txtDireccion.setEnabled(false);
        txtTelefono.setEnabled(false);
        cmbPuesto.setEnabled(false);
        btnGuardar.setEnabled(false);
        txtContra.setEnabled(false);
        txtRepetir.setEnabled(false);
    }
    public void habilitar(){
    
        txtNombre.setEnabled(true);
        txtApellido.setEnabled(true);
        txtDireccion.setEnabled(true);
        txtTelefono.setEnabled(true);
        cmbPuesto.setEnabled(true);
        btnGuardar.setEnabled(true);
        txtContra.setEnabled(true);
        txtRepetir.setEnabled(true);
    
    }
    
    
    public void borrarDatos(){
    
        txtNombre.setText("");
        txtApellido.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        cmbPuesto.setSelectedItem("SELECCIONAR");
        txtContra.setText("");
        txtRepetir.setText("");
    
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        diseño = new rojerusan.RSCheckBox();
        estados = new rojerusan.RSCheckBox();
        reportes = new rojerusan.RSCheckBox();
        carga = new rojerusan.RSCheckBox();
        ventas = new rojerusan.RSCheckBox();
        checador = new rojerusan.RSCheckBox();
        rSCheckBox8 = new rojerusan.RSCheckBox();
        proyect = new rojerusan.RSCheckBox();
        jPanel3 = new javax.swing.JPanel();
        cortes = new rojerusan.RSCheckBox();
        fresadora = new rojerusan.RSCheckBox();
        cnc = new rojerusan.RSCheckBox();
        torno = new rojerusan.RSCheckBox();
        acabados = new rojerusan.RSCheckBox();
        calidad = new rojerusan.RSCheckBox();
        tratamiento = new rojerusan.RSCheckBox();
        electrico = new rojerusan.RSCheckBox();
        jPanel4 = new javax.swing.JPanel();
        crear = new rojerusan.RSCheckBox();
        ensambles = new rojerusan.RSCheckBox();
        verEmpleado = new rojerusan.RSCheckBox();
        inventario = new rojerusan.RSCheckBox();
        invPlanos = new rojerusan.RSCheckBox();
        invPlanos1 = new rojerusan.RSCheckBox();
        entrega = new rojerusan.RSCheckBox();
        pedidos = new rojerusan.RSCheckBox();
        jPanel5 = new javax.swing.JPanel();
        cotizar = new rojerusan.RSCheckBox();
        verRequisicion = new rojerusan.RSCheckBox();
        requisiciones = new rojerusan.RSCheckBox();
        compras = new rojerusan.RSCheckBox();
        aprobacion = new rojerusan.RSCheckBox();
        recibos = new rojerusan.RSCheckBox();
        prestamos = new rojerusan.RSCheckBox();
        cotizacion = new rojerusan.RSCheckBox();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        txtNombre = new RSMaterialComponent.RSTextFieldMaterial();
        txtApellido = new RSMaterialComponent.RSTextFieldMaterial();
        txtDireccion = new RSMaterialComponent.RSTextFieldMaterial();
        txtTelefono = new RSMaterialComponent.RSTextFieldMaterial();
        txtNumero = new RSMaterialComponent.RSTextFieldMaterial();
        cmbPuesto = new RSMaterialComponent.RSComboBoxMaterial();
        txtContra = new RSMaterialComponent.RSPasswordMaterial();
        txtRepetir = new RSMaterialComponent.RSPasswordMaterial();
        jPanel11 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.BorderLayout(15, 15));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12)), "ACCESOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 1, 14), new java.awt.Color(51, 153, 255))); // NOI18N
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        jPanel2.setLayout(new java.awt.GridLayout(2, 2));

        diseño.setForeground(new java.awt.Color(51, 51, 51));
        diseño.setText("Diseño");
        diseño.setColorUnCheck(new java.awt.Color(51, 51, 51));
        diseño.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel2.add(diseño);

        estados.setForeground(new java.awt.Color(51, 51, 51));
        estados.setText("Estados");
        estados.setColorUnCheck(new java.awt.Color(51, 51, 51));
        estados.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel2.add(estados);

        reportes.setForeground(new java.awt.Color(51, 51, 51));
        reportes.setText("Reportes");
        reportes.setColorUnCheck(new java.awt.Color(51, 51, 51));
        reportes.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel2.add(reportes);

        carga.setForeground(new java.awt.Color(51, 51, 51));
        carga.setText("Carga de trabajo");
        carga.setColorUnCheck(new java.awt.Color(51, 51, 51));
        carga.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel2.add(carga);

        ventas.setForeground(new java.awt.Color(51, 51, 51));
        ventas.setText("Ventas");
        ventas.setColorUnCheck(new java.awt.Color(51, 51, 51));
        ventas.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel2.add(ventas);

        checador.setForeground(new java.awt.Color(51, 51, 51));
        checador.setText("Checador");
        checador.setColorUnCheck(new java.awt.Color(51, 51, 51));
        checador.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel2.add(checador);

        rSCheckBox8.setForeground(new java.awt.Color(51, 51, 51));
        rSCheckBox8.setText("Proyectos");
        rSCheckBox8.setColorUnCheck(new java.awt.Color(51, 51, 51));
        rSCheckBox8.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel2.add(rSCheckBox8);

        proyect.setForeground(new java.awt.Color(51, 51, 51));
        proyect.setText("Proyect Manager");
        proyect.setColorUnCheck(new java.awt.Color(51, 51, 51));
        proyect.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel2.add(proyect);

        jPanel1.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        jPanel3.setLayout(new java.awt.GridLayout(2, 0));

        cortes.setForeground(new java.awt.Color(51, 51, 51));
        cortes.setText("Cortes");
        cortes.setColorUnCheck(new java.awt.Color(51, 51, 51));
        cortes.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel3.add(cortes);

        fresadora.setForeground(new java.awt.Color(51, 51, 51));
        fresadora.setText("Fresadora");
        fresadora.setColorUnCheck(new java.awt.Color(51, 51, 51));
        fresadora.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel3.add(fresadora);

        cnc.setForeground(new java.awt.Color(51, 51, 51));
        cnc.setText("Cnc");
        cnc.setColorUnCheck(new java.awt.Color(51, 51, 51));
        cnc.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel3.add(cnc);

        torno.setForeground(new java.awt.Color(51, 51, 51));
        torno.setText("Torno");
        torno.setColorUnCheck(new java.awt.Color(51, 51, 51));
        torno.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel3.add(torno);

        acabados.setForeground(new java.awt.Color(51, 51, 51));
        acabados.setText("Acabados");
        acabados.setColorUnCheck(new java.awt.Color(51, 51, 51));
        acabados.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel3.add(acabados);

        calidad.setForeground(new java.awt.Color(51, 51, 51));
        calidad.setText("Calidad");
        calidad.setColorUnCheck(new java.awt.Color(51, 51, 51));
        calidad.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel3.add(calidad);

        tratamiento.setForeground(new java.awt.Color(51, 51, 51));
        tratamiento.setText("Tratamiento");
        tratamiento.setColorUnCheck(new java.awt.Color(51, 51, 51));
        tratamiento.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel3.add(tratamiento);

        electrico.setForeground(new java.awt.Color(51, 51, 51));
        electrico.setText("Electrico");
        electrico.setColorUnCheck(new java.awt.Color(51, 51, 51));
        electrico.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel3.add(electrico);

        jPanel1.add(jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        jPanel4.setLayout(new java.awt.GridLayout(2, 0));

        crear.setForeground(new java.awt.Color(51, 51, 51));
        crear.setText("Crear empleado");
        crear.setColorUnCheck(new java.awt.Color(51, 51, 51));
        crear.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel4.add(crear);

        ensambles.setForeground(new java.awt.Color(51, 51, 51));
        ensambles.setText("Ensambles");
        ensambles.setColorUnCheck(new java.awt.Color(51, 51, 51));
        ensambles.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel4.add(ensambles);

        verEmpleado.setForeground(new java.awt.Color(51, 51, 51));
        verEmpleado.setText("Ver empleados");
        verEmpleado.setColorUnCheck(new java.awt.Color(51, 51, 51));
        verEmpleado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel4.add(verEmpleado);

        inventario.setForeground(new java.awt.Color(51, 51, 51));
        inventario.setText("Inventario");
        inventario.setColorUnCheck(new java.awt.Color(51, 51, 51));
        inventario.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel4.add(inventario);

        invPlanos.setForeground(new java.awt.Color(51, 51, 51));
        invPlanos.setText("Inv. planos");
        invPlanos.setColorUnCheck(new java.awt.Color(51, 51, 51));
        invPlanos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel4.add(invPlanos);

        invPlanos1.setForeground(new java.awt.Color(51, 51, 51));
        invPlanos1.setText("Remisiones");
        invPlanos1.setColorUnCheck(new java.awt.Color(51, 51, 51));
        invPlanos1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel4.add(invPlanos1);

        entrega.setForeground(new java.awt.Color(51, 51, 51));
        entrega.setText("Entrega");
        entrega.setColorUnCheck(new java.awt.Color(51, 51, 51));
        entrega.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel4.add(entrega);

        pedidos.setForeground(new java.awt.Color(51, 51, 51));
        pedidos.setText("Pedidos");
        pedidos.setColorUnCheck(new java.awt.Color(51, 51, 51));
        pedidos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel4.add(pedidos);

        jPanel1.add(jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        jPanel5.setLayout(new java.awt.GridLayout(2, 0));

        cotizar.setForeground(new java.awt.Color(51, 51, 51));
        cotizar.setText("Cotizar");
        cotizar.setColorUnCheck(new java.awt.Color(51, 51, 51));
        cotizar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel5.add(cotizar);

        verRequisicion.setForeground(new java.awt.Color(51, 51, 51));
        verRequisicion.setText("Ver requisiciones");
        verRequisicion.setColorUnCheck(new java.awt.Color(51, 51, 51));
        verRequisicion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel5.add(verRequisicion);

        requisiciones.setForeground(new java.awt.Color(51, 51, 51));
        requisiciones.setText("Requisiciones");
        requisiciones.setColorUnCheck(new java.awt.Color(51, 51, 51));
        requisiciones.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel5.add(requisiciones);

        compras.setForeground(new java.awt.Color(51, 51, 51));
        compras.setText("Compras");
        compras.setColorUnCheck(new java.awt.Color(51, 51, 51));
        compras.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel5.add(compras);

        aprobacion.setForeground(new java.awt.Color(51, 51, 51));
        aprobacion.setText("Aprobaciones");
        aprobacion.setColorUnCheck(new java.awt.Color(51, 51, 51));
        aprobacion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel5.add(aprobacion);

        recibos.setForeground(new java.awt.Color(51, 51, 51));
        recibos.setText("Recibos");
        recibos.setColorUnCheck(new java.awt.Color(51, 51, 51));
        recibos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel5.add(recibos);

        prestamos.setForeground(new java.awt.Color(51, 51, 51));
        prestamos.setText("Prestamos");
        prestamos.setColorUnCheck(new java.awt.Color(51, 51, 51));
        prestamos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel5.add(prestamos);

        cotizacion.setForeground(new java.awt.Color(51, 51, 51));
        cotizacion.setText("Cotizacion");
        cotizacion.setColorUnCheck(new java.awt.Color(51, 51, 51));
        cotizacion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel5.add(cotizacion);

        jPanel1.add(jPanel5);

        jPanel7.add(jPanel1, java.awt.BorderLayout.EAST);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto Black", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setText("Empleados");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 102, 204)));
        jPanel9.add(jLabel1);

        jPanel8.add(jPanel9);

        jPanel7.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jPanel10.setLayout(new java.awt.GridLayout(9, 1));

        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(null);
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });
        jPanel10.add(txtCodigo);

        txtNombre.setForeground(new java.awt.Color(51, 51, 51));
        txtNombre.setPlaceholder("Nombre(s)");
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        jPanel10.add(txtNombre);

        txtApellido.setForeground(new java.awt.Color(51, 51, 51));
        txtApellido.setPlaceholder("Apellido(s)");
        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoKeyTyped(evt);
            }
        });
        jPanel10.add(txtApellido);

        txtDireccion.setForeground(new java.awt.Color(51, 51, 51));
        txtDireccion.setPlaceholder("Direccion");
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionKeyTyped(evt);
            }
        });
        jPanel10.add(txtDireccion);

        txtTelefono.setForeground(new java.awt.Color(51, 51, 51));
        txtTelefono.setPlaceholder("Telefono");
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });
        jPanel10.add(txtTelefono);

        txtNumero.setForeground(new java.awt.Color(51, 51, 51));
        txtNumero.setPlaceholder("Numero de empleado");
        txtNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroActionPerformed(evt);
            }
        });
        txtNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroKeyTyped(evt);
            }
        });
        jPanel10.add(txtNumero);

        cmbPuesto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SELECCIONAR", "ALMACEN", "CALIDAD", "COMPRAS", "DISEÑO", "INTEGRACION", "LIMPIEZA", "HERRAMENTISTA", "VENTAS", "COSTOS", "IT", "SUPER" }));
        cmbPuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPuestoActionPerformed(evt);
            }
        });
        jPanel10.add(cmbPuesto);

        txtContra.setPlaceholder("Contraseña");
        jPanel10.add(txtContra);

        txtRepetir.setPlaceholder("Repetir contraseña");
        jPanel10.add(txtRepetir);

        jPanel7.add(jPanel10, java.awt.BorderLayout.CENTER);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar_32.png"))); // NOI18N
        btnGuardar.setBorder(null);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setPreferredSize(new java.awt.Dimension(50, 50));
        btnGuardar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar_32.png"))); // NOI18N
        btnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar_48.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel11.add(btnGuardar);

        jPanel7.add(jPanel11, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel7, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroKeyTyped
        char c = evt.getKeyChar();
        if(Character.isLowerCase(c))
        {
            String cad = (""+c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtNumeroKeyTyped

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        char c = evt.getKeyChar();
        if(Character.isLowerCase(c))
        {
            String cad = (""+c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtDireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyTyped
        char c = evt.getKeyChar();
        if(Character.isLowerCase(c))
        {
            String cad = (""+c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtDireccionKeyTyped

    private void txtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyTyped
        char c = evt.getKeyChar();
        if(Character.isLowerCase(c))
        {
            String cad = (""+c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtApellidoKeyTyped

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        char c = evt.getKeyChar();
        if(Character.isLowerCase(c))
        {
            String cad = (""+c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtNombreKeyTyped

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        if(txtCodigo.getText().equals("")){
            guardar();
        }else{
            actualizar();
        }
        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
        char c = evt.getKeyChar();
        if(Character.isLowerCase(c))
        {
            String cad = (""+c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtCodigoKeyTyped

    private void cmbPuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPuestoActionPerformed
        if(cmbPuesto.getSelectedItem().equals("ALMACEN")){
            inventario.setSelected(true);
            invPlanos.setSelected(true);
            verRequisicion.setSelected(true);
            requisiciones.setSelected(true);
            recibos.setSelected(true);
            prestamos.setSelected(true);
        }else if(cmbPuesto.getSelectedItem().equals("CALIDAD")){
            estados.setSelected(true);
            reportes.setSelected(true);
            carga.setSelected(true);
            cortes.setSelected(true);
            fresadora.setSelected(true);
            torno.setSelected(true);
            cnc.setSelected(true);
            acabados.setSelected(true);
            calidad.setSelected(true);
            tratamiento.setSelected(true);
        }else if(cmbPuesto.getSelectedItem().equals("COMPRAS")){
            verRequisicion.setSelected(true);
            requisiciones.setSelected(true);
            compras.setSelected(true);
            inventario.setSelected(true);
        }else if(cmbPuesto.getSelectedItem().equals("DISEÑO")){
            diseño.setSelected(true);
            estados.setSelected(true);
            reportes.setSelected(true);
            carga.setSelected(true);
            requisiciones.setSelected(true);
            verRequisicion.setSelected(true);
        }else if(cmbPuesto.getSelectedItem().equals("INTEGRACION")){
            
        }else if(cmbPuesto.getSelectedItem().equals("LIMPIEZA")){
            
        }else if(cmbPuesto.getSelectedItem().equals("MAQUINADOS")){
            
        }else if(cmbPuesto.getSelectedItem().equals("VENTAS")){
            
        }else if(cmbPuesto.getSelectedItem().equals("COSTOS")){
            
        }else if(cmbPuesto.getSelectedItem().equals("IT")){
            
        }else if(cmbPuesto.getSelectedItem().equals("SUPER")){
            
        }
    }//GEN-LAST:event_cmbPuestoActionPerformed

    private void txtNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroActionPerformed
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroempleados where NumEmpleado like '"+txtNumero.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[33];
            while(rs.next()){
                txtNombre.setText(rs.getString("Nombre"));
                txtApellido.setText(rs.getString("Apellido"));
                txtDireccion.setText(rs.getString("Direccion"));
                txtCodigo.setText(rs.getString("NumEmpleado"));
                datos[1] = rs.getString("Diseño");
                datos[2] = rs.getString("Cambio");
                datos[3] = rs.getString("Reportes");
                datos[4] = rs.getString("Carga");
                datos[5] = rs.getString("Ventas");
                datos[6] = rs.getString("Cortes");
                datos[7] = rs.getString("Fresa");
                datos[8] = rs.getString("Cnc");
                datos[9] = rs.getString("Torno");
                datos[10] = rs.getString("Acabados");
                datos[12] = rs.getString("Calidad");
                datos[13] = rs.getString("Tratamiento");
                datos[14] = rs.getString("Electrico");
                datos[15] = rs.getString("CrearEmpleado");
                datos[16] = rs.getString("VerEmpleado");
                datos[17] = rs.getString("Inventario");
                datos[18] = rs.getString("Ensamble");
                datos[19] = rs.getString("InventarioPlanos");
                datos[20] = rs.getString("Requisiciones");
                datos[21] = rs.getString("Orden");
                datos[22] = rs.getString("Aprobacion");
                datos[23] = rs.getString("Recibo");
                datos[24] = rs.getString("Prestamo");
                datos[25] = rs.getString("Cotizacion");
                datos[26] = rs.getString("VerRequisiciones");
                datos[27] = rs.getString("Cotizar");
                datos[28] = rs.getString("ProyectMan");
                datos[29] = rs.getString("Remisiones");
                datos[30] = rs.getString("Checador");
                datos[31] = rs.getString("Entrega");
                datos[32] = rs.getString("Pedidos");
            }
            if(datos[1].equals("1")){
                diseño.setSelected(true);
            }else{
                diseño.setSelected(false);
            }
            
            if(datos[2].equals("1")){
                estados.setSelected(true);
            }else{
                estados.setSelected(false);
            }
            
            if(datos[3].equals("1")){
                reportes.setSelected(true);
            }else{
                reportes.setSelected(false);
            }
            
            if(datos[4].equals("1")){
                carga.setSelected(true);
            }else{
                carga.setSelected(false);
            }
            
            if(datos[5].equals("1")){
                ventas.setSelected(true);
            }else{
                ventas.setSelected(false);
            }
            
            if(datos[6].equals("1")){
                cortes.setSelected(true);
            }else{
                cortes.setSelected(false);
            }
            
            if(datos[7].equals("1")){
                fresadora.setSelected(true);
            }else{
                fresadora.setSelected(false);
            }
            
            if(datos[8].equals("1")){
                fresadora.setSelected(true);
            }else{
                fresadora.setSelected(false);
            }
            if(datos[9].equals("1")){
                torno.setSelected(true);
            }else{
                torno.setSelected(false);
            }
            
            if(datos[10].equals("1")){
                acabados.setSelected(true);
            }else{
                acabados.setSelected(false);
            
            }if(datos[12].equals("1")){
                calidad.setSelected(true);
            }else{
                calidad.setSelected(false);
            }
            
            if(datos[13].equals("1")){
                tratamiento.setSelected(true);
            }else{
                tratamiento.setSelected(false);
            }
            
            if(datos[14].equals("1")){
                electrico.setSelected(true);
            }else{
                electrico.setSelected(false);
            }
            
            if(datos[15].equals("1")){
                crear.setSelected(true);
            }else{
                crear.setSelected(false);
            }
            
            if(datos[16].equals("1")){
                verEmpleado.setSelected(true);
            }else{
                verEmpleado.setSelected(false);
            }
            
            if(datos[17].equals("1")){
                inventario.setSelected(true);
            }else{
                inventario.setSelected(false);
            }
            
            if(datos[18].equals("1")){
                ensambles.setSelected(true);
            }else{
                ensambles.setSelected(false);
            }
            
            if(datos[19].equals("1")){
                invPlanos.setSelected(true);
            }else{
                invPlanos.setSelected(false);
            }
            
            if(datos[20].equals("1")){
                requisiciones.setSelected(true);
            }else{
                requisiciones.setSelected(false);
            }
            
            if(datos[21].equals("1")){
                compras.setSelected(true);
            }else{
                compras.setSelected(false);
            }
            
            if(datos[22].equals("1")){
                aprobacion.setSelected(true);
            }else{
                aprobacion.setSelected(false);
            }
            
            if(datos[23].equals("1")){
                recibos.setSelected(true);
            }else{
                recibos.setSelected(false);
            }
            
            if(datos[24].equals("1")){
                prestamos.setSelected(true);
            }else{
                prestamos.setSelected(false);
            }
            
            if(datos[25].equals("1")){
                cotizacion.setSelected(true);
            }else{
                cotizacion.setSelected(false);
            }
            
            if(datos[26].equals("1")){
                verRequisicion.setSelected(true);
            }else{
                verRequisicion.setSelected(false);
            }
            
            if(datos[27].equals("1")){
                cotizar.setSelected(true);
            }else{
                cotizar.setSelected(false);
            }
            
            if(datos[28].equals("1")){
                proyect.setSelected(true);
            }else{
                proyect.setSelected(false);
            }
            
            if(datos[30].equals("1")){
                checador.setSelected(true);
            }else{
                checador.setSelected(false);
            }
            
            if(datos[31].equals("1")){
                entrega.setSelected(true);
            }else{
                entrega.setSelected(false);
            }
            
            if(datos[32].equals("1")){
                pedidos.setSelected(true);
            }else{
                pedidos.setSelected(false);
            }
            
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtNumeroActionPerformed

    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RegistrarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistrarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistrarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistrarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistrarEmpleado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSCheckBox acabados;
    private rojerusan.RSCheckBox aprobacion;
    private javax.swing.JButton btnGuardar;
    private rojerusan.RSCheckBox calidad;
    private rojerusan.RSCheckBox carga;
    private rojerusan.RSCheckBox checador;
    private RSMaterialComponent.RSComboBoxMaterial cmbPuesto;
    private rojerusan.RSCheckBox cnc;
    private rojerusan.RSCheckBox compras;
    private rojerusan.RSCheckBox cortes;
    private rojerusan.RSCheckBox cotizacion;
    private rojerusan.RSCheckBox cotizar;
    private rojerusan.RSCheckBox crear;
    private rojerusan.RSCheckBox diseño;
    private rojerusan.RSCheckBox electrico;
    private rojerusan.RSCheckBox ensambles;
    private rojerusan.RSCheckBox entrega;
    private rojerusan.RSCheckBox estados;
    private rojerusan.RSCheckBox fresadora;
    private rojerusan.RSCheckBox invPlanos;
    private rojerusan.RSCheckBox invPlanos1;
    private rojerusan.RSCheckBox inventario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private rojerusan.RSCheckBox pedidos;
    private rojerusan.RSCheckBox prestamos;
    private rojerusan.RSCheckBox proyect;
    private rojerusan.RSCheckBox rSCheckBox8;
    private rojerusan.RSCheckBox recibos;
    private rojerusan.RSCheckBox reportes;
    private rojerusan.RSCheckBox requisiciones;
    private rojerusan.RSCheckBox torno;
    private rojerusan.RSCheckBox tratamiento;
    private RSMaterialComponent.RSTextFieldMaterial txtApellido;
    private javax.swing.JTextField txtCodigo;
    private RSMaterialComponent.RSPasswordMaterial txtContra;
    private RSMaterialComponent.RSTextFieldMaterial txtDireccion;
    private RSMaterialComponent.RSTextFieldMaterial txtNombre;
    private RSMaterialComponent.RSTextFieldMaterial txtNumero;
    private RSMaterialComponent.RSPasswordMaterial txtRepetir;
    private RSMaterialComponent.RSTextFieldMaterial txtTelefono;
    private rojerusan.RSCheckBox ventas;
    private rojerusan.RSCheckBox verEmpleado;
    private rojerusan.RSCheckBox verRequisicion;
    // End of variables declaration//GEN-END:variables
}
