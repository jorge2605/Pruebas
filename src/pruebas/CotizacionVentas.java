package pruebas;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import Conexiones.Conexion;
import Modelo.CabezeraRemisiones;
import VentanaEmergente.CotizacionVentas.AgregarEmpresaVentas;
import VentanaEmergente.CotizacionVentas.CabeceraCotizacionVentas;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.String.format;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;

public class CotizacionVentas extends javax.swing.JInternalFrame {

    ResultSet res;
    private java.awt.Image data;
    public String numEmpleado;
    TextAutoCompleter au;
    String nombre = null;
    String contacto = null;
    String telefono = null;
    String direccion = null;
    String correo = null;
    String nombreEmpleado = null;
    String telefonoEmpleado = null;
    String correoEmpleado = null;
    int selectedRow = -1;
    
    private BufferedImage ConvertirImagen(byte[] bytes) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator readers = ImageIO.getImageReadersByFormatName("jpeg");
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis; 
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        return reader.read(0, param);
    }
    
    public java.awt.Image recuperarfotos(String usuario) {
        try {
                Connection con = null;
                Conexion conect = new Conexion();
                con = conect.getConnection();
                Statement st = con.createStatement();
                PreparedStatement pst = con.prepareStatement("Select Cotizacion.Url From cotizacion where cotizacion.CotizacionNo = ? ");
                pst.setString(1, usuario);
                res = pst.executeQuery();
                int i = 0;
                    while (res.next()) 
                        {
                            byte[] b = res.getBytes("Url");
                            data = ConvertirImagen(b);       
                            i++;
                        }
                    res.close();
            } catch (IOException | SQLException ex) 
                {
                    Logger.getLogger(pruebas.Inventario.class.getName()).log(Level.SEVERE, null, ex);
                }
        return data;
    }

    public void Guardar(){
        if(txtProyecto.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE PROYECTO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        } else if(txtIngeniero.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE INGENIERO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtModelo.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE MODELO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtDescripcion.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE DESCRIPCION","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtTiempo.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE TIEMPO DE ENTREGA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(jcbMoneda.getSelectedItem().equals("SELECCIONAR MONEDA")){
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR EL TIPO DE MONEDA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(Tabla1.getRowCount() == 0){
            JOptionPane.showMessageDialog(this, "DEBES INTRODUCIR POR LO MENOS UN CAMPO EN LA TABLA CARACTERISTICAS","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(Valido.getDate() ==  null){
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE FECHA LIMITE","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }
        else{

            try{
                btnGuardar.setVisible(true);
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql = "insert into cotizacion (CotizacionNo,nombreEmpresa,Proyecto,Ingeniero,Modelo,Descripcion,TiempoEntrega,Moneda,FechaLimite,Nota,Fecha,Dirigido,Limite) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, OC.getText());
                pst.setString(2, txtEmpresa.getText());
                pst.setString(3, txtProyecto.getText());
                pst.setString(4, txtIngeniero.getText());
                pst.setString(5, txtModelo.getText());
                pst.setString(6, txtDescripcion.getText());
                pst.setString(7, txtTiempo.getText());
                pst.setString(8, jcbMoneda.getSelectedItem().toString());
                pst.setString(9, txtTiempo.getText());
                pst.setString(10, txtNotas.getText());
                pst.setString(11, lblFecha.getText());
                pst.setString(12, txtTelefono.getText());

                SimpleDateFormat s = new SimpleDateFormat("dd MMMM yyyy");
                String f = s.format(Valido.getDate());
                pst.setString(13, f);

                int n = pst.executeUpdate();

                String sql4 = "select * from cotizacion";
                Statement st2 = con.createStatement();
                ResultSet rs = st2.executeQuery(sql4);
                String datos[] = new String[10];
                while(rs.next()){
                    datos[0] = rs.getString("CotizacionNo");
                }


                String sql2 = "insert into caracteristicas (caracteristica,CotizacionNo) values (?,?)";
                PreparedStatement pst2 = con.prepareStatement(sql2);

                int n2 = 0;

                for (int i = 0; i < Tabla1.getRowCount(); i++) {
                    pst2.setString(1, Tabla1.getValueAt(i, 0).toString());
                    pst2.setString(2, datos[0]);

                    n2 = pst2.executeUpdate();

                }
                pst2.setString(1, sql);

                if(n > 0 && n2 > 0){
                    JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                    exportarPdf();
                    btnGuardar.setVisible(false);
                }

            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "ERROR: "+e);
            }
        }
    }

    public void exportarPdf(){
        try {
            Document documento = new Document(PageSize.A4, 36, 36, 80, 36);
            PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream("\\\\192.168.100.40\\bd\\Cotizacion\\"+OC.getText()+".pdf"));
            
            CabeceraCotizacionVentas encabezado = new CabeceraCotizacionVentas();
            encabezado.setEncabezado("ENCABEZADO DE ENTREGA DE REQUISICION");
            writer.setPageEvent(encabezado);
            
            documento.open();

            //-------------------------FUENTES---------------------------------
            com.itextpdf.text.Font fuente30 = new com.itextpdf.text.Font();
            fuente30.setSize(30);
            fuente30.setFamily("Arial");
            fuente30.setColor(BaseColor.WHITE);
            fuente30.setStyle(com.itextpdf.text.Font.BOLDITALIC);

            com.itextpdf.text.Font fuente25 = new com.itextpdf.text.Font();
            fuente25.setSize(12);
            fuente25.setFamily("Arial");
            fuente25.setColor(BaseColor.WHITE);
            fuente25.setStyle(com.itextpdf.text.Font.BOLDITALIC);
            
            com.itextpdf.text.Font fuente25N = new com.itextpdf.text.Font();
            fuente25N.setSize(10);
            fuente25N.setFamily("Arial");
            fuente25N.setColor(BaseColor.WHITE);
            fuente25N.setStyle(com.itextpdf.text.Font.BOLD);

            com.itextpdf.text.Font fuente25Negro = new com.itextpdf.text.Font();
            fuente25Negro.setSize(12);
            fuente25Negro.setFamily("Arial");
            fuente25Negro.setColor(BaseColor.BLACK);
            fuente25Negro.setStyle(com.itextpdf.text.Font.NORMAL);

            com.itextpdf.text.Font fuente12N = new com.itextpdf.text.Font();
            fuente12N.setSize(12);
            fuente12N.setFamily("Arial");
            fuente12N.setColor(BaseColor.BLACK);
            fuente12N.setStyle(com.itextpdf.text.Font.BOLD);

            com.itextpdf.text.Font fuente12 = new com.itextpdf.text.Font();
            fuente12.setSize(12);
            fuente12.setFamily("Arial");
            fuente12.setColor(BaseColor.BLACK);
            fuente12.setStyle(com.itextpdf.text.Font.NORMAL);

            com.itextpdf.text.Font fuente8 = new com.itextpdf.text.Font();
            fuente8.setSize(8);
            fuente8.setFamily("Arial");
            fuente8.setColor(BaseColor.BLACK);
            fuente8.setStyle(com.itextpdf.text.Font.NORMAL);

            com.itextpdf.text.Font fuente8N = new com.itextpdf.text.Font();
            fuente8N.setSize(8);
            fuente8N.setFamily("Arial");
            fuente8N.setColor(BaseColor.BLACK);
            fuente8N.setStyle(com.itextpdf.text.Font.BOLD);

            com.itextpdf.text.Font fuente10 = new com.itextpdf.text.Font();
            fuente10.setSize(10);
            fuente10.setFamily("Arial");
            fuente10.setColor(BaseColor.BLACK);
            fuente10.setStyle(com.itextpdf.text.Font.NORMAL);

            com.itextpdf.text.Font fuente10B = new com.itextpdf.text.Font();
            fuente10B.setSize(12);
            fuente10B.setFamily("Arial");
            fuente10B.setColor(BaseColor.WHITE);
            fuente10B.setStyle(com.itextpdf.text.Font.BOLD);

            com.itextpdf.text.Font fuente10N = new com.itextpdf.text.Font();
            fuente10N.setSize(10);
            fuente10N.setFamily("Arial");
            fuente10N.setColor(BaseColor.BLACK);
            fuente10N.setStyle(com.itextpdf.text.Font.BOLD);

            com.itextpdf.text.Font fuente20 = new com.itextpdf.text.Font();
            fuente20.setSize(20);
            fuente20.setFamily("Arial");
            fuente20.setColor(BaseColor.BLACK);
            fuente20.setStyle(com.itextpdf.text.Font.BOLD);


            //------------------------------------------------------------------
            BaseColor myColor = WebColors.getRGBColor("#e36a07");
            BaseColor cNB = WebColors.getRGBColor("#f8d7cd");
            BaseColor blanco = WebColors.getRGBColor("#ffffff");
            BaseColor fondo = WebColors.getRGBColor("#fbe5d6");

            //------------------------------------------------------------------
            //------------------------TABLAS------------------------------------
            

            //-------------------------------------------------------------------

            PdfPTable tabla2 = new PdfPTable(1);
            tabla2.setWidthPercentage(100);
            char comillas = '"';

            String negritas = comillas+txtProyecto.getText()+comillas;
            
            Chunk chunk5 = new Chunk("Estimado Cliente. \n", fuente10);
            Chunk chunk6 = new Chunk("Ponemos a su consideración la siguiente cotización , la cual cumple con las especificaciones del documento ", fuente10);
            Chunk chunk7 = new Chunk(negritas, fuente10N);
            Chunk chunk8 = new Chunk(" de igual manera indicamos las características de nuestra solución así como nuestra propuesta económica.", fuente10);
            
            Phrase p2 = new Phrase();
            p2.add(chunk5);
            p2.add(chunk6);
            p2.add(chunk7);
            p2.add(chunk8);
            
            PdfPCell c3 = new PdfPCell(p2);

            PdfPCell c4 = new PdfPCell(new Phrase(" ",fuente20));
//            PdfPCell c5 = new PdfPCell(new Phrase(lblFecha.getText()+"\n",fuente25Negro));


            c3.setBackgroundColor(fondo);
            c3.setBorder(0);
            c3.setHorizontalAlignment(Element.ALIGN_LEFT);

            c4.setBackgroundColor(fondo);
            c4.setBorder(0);
            c4.setHorizontalAlignment(Element.ALIGN_CENTER);
//
//            c5.setBackgroundColor(fondo);
//            c5.setBorder(0);
//            c5.setHorizontalAlignment(Element.ALIGN_CENTER);
//
            tabla2.addCell(c3);
            tabla2.addCell(c4);

            //------------------------Tabla Informacion-------------------------
            PdfPTable tablaInformacion = new PdfPTable(2);
            tablaInformacion.getDefaultCell().setBorder(0);
            tablaInformacion.setWidthPercentage(100);
            
            //------------------------------------------------------------------
            PdfPTable tabla3 = new PdfPTable(1);
            tabla3.getDefaultCell().setBorderWidth(1f);
            tabla3.getDefaultCell().setBorderColor(myColor);
            tabla3.setWidthPercentage(80);
            tabla3.setHorizontalAlignment(Element.ALIGN_LEFT);
            
            //------------------------------------------------------------------
            Chunk chunk1 = new Chunk("Contacto: ",fuente10N);
            Chunk chunk2 = new Chunk(txtIngeniero.getText(),fuente10);
            
            Phrase phrase1 = new Phrase();
            phrase1.add(chunk1);
            phrase1.add(chunk2);
            
            PdfPCell c11 = new PdfPCell();
            c11.setBorder(0);
            c11.setBorderWidthTop(1);
            c11.setBorderWidthLeft(1);
            c11.setBorderWidthRight(1);
            c11.setBorderColor(myColor);
            c11.addElement(phrase1);
            //------------------------------------------------------------------
            chunk1 = new Chunk("Direccion: " ,fuente10N);
            chunk2 = new Chunk(direccion,fuente10);
            
            phrase1 = new Phrase();
            phrase1.add(chunk1);
            phrase1.add(chunk2);
            
            PdfPCell c10 = new PdfPCell();
            c10.setBorder(0);
            c10.setBorderWidthLeft(1);
            c10.setBorderWidthRight(1);
            c10.setBorderColor(myColor);
            c10.addElement(phrase1);
             //-----------------------------------------------------------------
            chunk1 = new Chunk("Atencion a: " ,fuente10N);
            chunk2 = new Chunk(txtIngeniero.getText(),fuente10);
            
            phrase1 = new Phrase();
            phrase1.add(chunk1);
            phrase1.add(chunk2);
            
            PdfPCell c12 = new PdfPCell();
            c12.setBorder(0);
            c12.setBorderWidthLeft(1);
            c12.setBorderWidthRight(1);
            c12.setBorderColor(myColor);
            c12.addElement(phrase1);
             //-----------------------------------------------------------------
            chunk2 = new Chunk(telefono,fuente10);
            
            phrase1 = new Phrase();
            phrase1.add(chunk2);
            
            PdfPCell c13 = new PdfPCell();
            c13.setBorder(0);
            c13.setBorderWidthLeft(1);
            c13.setBorderWidthRight(1);
            c13.setBorderColor(myColor);
            c13.addElement(phrase1);
             //-----------------------------------------------------------------
            chunk2 = new Chunk(correo,fuente10);
            
            phrase1 = new Phrase();
            phrase1.add(chunk2);
            
            PdfPCell c14 = new PdfPCell();
            c14.setBorder(0);
            c14.setBorderWidthLeft(1);
            c14.setBorderWidthRight(1);
            c14.setBorderWidthBottom(1);
            c14.setBorderColor(myColor);
            c14.addElement(phrase1);
             //-----------------------------------------------------------------

            tabla3.addCell(c11);
            tabla3.addCell(c10);
            tabla3.addCell(c12);
            tabla3.addCell(c13);
            tabla3.addCell(c14);
            
            tablaInformacion.addCell(tabla3);
            //---------------------TABLA DE INFORMACION-------------------------
            PdfPTable tabla9 = new PdfPTable(1);
            tabla9.getDefaultCell().setBorderWidth(1f);
            tabla9.getDefaultCell().setBorderColor(myColor);
            tabla9.setWidthPercentage(80);
            tabla9.setHorizontalAlignment(Element.ALIGN_LEFT);
            
            //------------------------------------------------------------------
            Chunk chunk3 = new Chunk("Fecha: ",fuente10N);
            Chunk chunk4 = new Chunk(lblFecha.getText(),fuente10);
            
            Phrase phrase2 = new Phrase();
            phrase2.add(chunk3);
            phrase2.add(chunk4);
            
            PdfPCell c15 = new PdfPCell();
            c15.setBorder(0);
            c15.setBorderWidthLeft(1);
            c15.setBorderWidthRight(1);
            c15.setBorderWidthTop(1);
            c15.setBorderColor(myColor);
            c15.addElement(phrase2);
            //------------------------------------------------------------------
            chunk3 = new Chunk("Numero de cotizacion: " ,fuente10N);
            chunk4 = new Chunk(OC.getText(),fuente10);
            
            phrase2 = new Phrase();
            phrase2.add(chunk3);
            phrase2.add(chunk4);
            
            PdfPCell c16 = new PdfPCell();
            c16.setBorder(0);
            c16.setBorderWidthLeft(1);
            c16.setBorderWidthRight(1);
            c16.setBorderColor(myColor);
            c16.addElement(phrase2);
             //-----------------------------------------------------------------
            chunk3 = new Chunk("RFS: " ,fuente10N);
//            chunk4 = new Chunk(txtIngeniero.getText(),fuente10);
            
            phrase2 = new Phrase();
            phrase2.add(chunk3);
//            phrase1.add(chunk2);
            
            PdfPCell c17 = new PdfPCell();
            c17.setBorder(0);
            c17.setBorderWidthLeft(1);
            c17.setBorderWidthRight(1);
            c17.setBorderColor(myColor);
            c17.addElement(phrase2);
             //-----------------------------------------------------------------
            chunk3 = new Chunk("Vendedor: ",fuente10N);
            chunk4 = new Chunk(nombreEmpleado,fuente10);
            
            phrase2 = new Phrase();
            phrase2.add(chunk3);
            phrase2.add(chunk4);
            
            PdfPCell c18 = new PdfPCell();
            c18.setBorder(0);
            c18.setBorderWidthLeft(1);
            c18.setBorderWidthRight(1);
            c18.setBorderColor(myColor);
            c18.addElement(phrase2);
             //-----------------------------------------------------------------
            chunk2 = new Chunk(telefonoEmpleado,fuente10);
            
            phrase1 = new Phrase();
            phrase1.add(chunk2);
            
            PdfPCell c19 = new PdfPCell();
            c19.setBorder(0);
            c19.setBorderWidthLeft(1);
            c19.setBorderWidthRight(1);
            c19.setBorderColor(myColor);
            c19.addElement(phrase1);
             //-----------------------------------------------------------------
            chunk2 = new Chunk(correoEmpleado,fuente10);
            
            phrase1 = new Phrase();
            phrase1.add(chunk2);
            
            PdfPCell c111 = new PdfPCell();
            c111.setBorder(0);
            c111.setBorderWidthLeft(1);
            c111.setBorderWidthRight(1);
            c111.setBorderWidthBottom(1);
            c111.setBorderColor(myColor);
            c111.addElement(phrase1);
             //-----------------------------------------------------------------

            tabla9.addCell(c15);
            tabla9.addCell(c16);
            tabla9.addCell(c17);
            tabla9.addCell(c18);
            tabla9.addCell(c19);
            tabla9.addCell(c111);

            tablaInformacion.addCell(tabla9);
            //-----------------------Tabla de descripcion de proyecto-----------
            PdfPTable tablaDescripcion = new PdfPTable(1);
            tablaDescripcion.setWidthPercentage(100);
            
            chunk1 = new Chunk("Descripción general del proyecto: ", fuente10N);
            chunk2 = new Chunk(txtProyecto.getText() + "\n", fuente10);
            chunk3 = new Chunk("Tiempo de entrega:", fuente10N);
            chunk4 = new Chunk(txtTiempo.getText() + "\n", fuente10);
            
            Phrase p1 = new Phrase();
            p1.add(chunk1);
            p1.add(chunk2);
            p1.add(chunk3);
            p1.add(chunk4);
            
            PdfPCell celDescripcion = new PdfPCell();
            celDescripcion.addElement(p1);
            celDescripcion.setBorderWidth(1);
            celDescripcion.setBorderColor(myColor);
            
            tablaDescripcion.addCell(celDescripcion);
            //------------------------------------------------------------------
            PdfPTable tabla4 = new PdfPTable(1);
            tabla4.setWidthPercentage(100);

            PdfPCell c20 = new PdfPCell(new Phrase("Caracterísiticas",fuente20));
            c20.setHorizontalAlignment(Element.ALIGN_LEFT);
            c20.setBorderColor(myColor);
            
            tabla4.addCell(c20);

            //------------------------------------------------------------------
            PdfPTable tablaCar = new PdfPTable(4);
            tablaCar.setWidthPercentage(100);
            float medias[] = {500,100,150,150};
            tablaCar.setWidths(medias);
            
            PdfPCell cel1 = new PdfPCell(new Phrase("Descripcion",fuente25N));
            cel1.setBorder(0);
            cel1.setBorderWidthBottom(1);
            cel1.setBorderColor(blanco);
            cel1.setBackgroundColor(myColor);
            tablaCar.addCell(cel1);
            
            cel1 = new PdfPCell(new Phrase("Cantidad",fuente25N));
            cel1.setBorder(0);
            cel1.setBorderWidthBottom(1);
            cel1.setBorderColor(blanco);
            cel1.setBackgroundColor(myColor);
            tablaCar.addCell(cel1);
            
            cel1 = new PdfPCell(new Phrase("Costo Unitario",fuente25N));
            cel1.setBorder(0);
            cel1.setBorderWidthBottom(1);
            cel1.setBorderColor(blanco);
            cel1.setBackgroundColor(myColor);
            tablaCar.addCell(cel1);
            
            cel1 = new PdfPCell(new Phrase("Costo Total",fuente25N));
            cel1.setBorder(0);
            cel1.setBorderWidthBottom(1);
            cel1.setBorderColor(blanco);
            cel1.setBackgroundColor(myColor);
            tablaCar.addCell(cel1);
            
            PdfPCell c21 = new PdfPCell(new Phrase("•"+txtProyecto.getText(),fuente10N));
            c21.setBorder(0);
            c21.setBackgroundColor(cNB);
            c21.setHorizontalAlignment(Element.ALIGN_LEFT);
            c21.setBorderColor(cNB);
            tablaCar.addCell(c21);
            
            c21 = new PdfPCell(new Phrase(" ",fuente10N));
            c21.setBorder(0);
            c21.setHorizontalAlignment(Element.ALIGN_LEFT);
            c21.setBackgroundColor(cNB);
            c21.setBorderColor(cNB);
            tablaCar.addCell(c21);
            tablaCar.addCell(c21);
            tablaCar.addCell(c21);
            
            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                for (int j = 0; j < 4; j++) {
                    double cant;
                    double precio;
                    double total;
                    try{cant = Double.parseDouble(Tabla1.getValueAt(i, 1).toString());}catch(Exception e){cant = 0;}
                    try{precio = Double.parseDouble(Tabla1.getValueAt(i, 2).toString());}catch(Exception e){precio = 0;}
                    total = cant * precio;
                    PdfPCell c22 = null;
                    switch (j) {
                        case 0:
                            c22 = new PdfPCell(new Phrase("     •"+Tabla1.getValueAt(i, j).toString(),fuente10));
                            break;
                        case 1:
                            c22 = new PdfPCell(new Phrase(String.valueOf(cant),fuente10));
                            break;
                        case 2:
                            c22 = new PdfPCell(new Phrase(String.valueOf(precio),fuente10));
                            break;
                        case 3:
                            c22 = new PdfPCell(new Phrase(String.valueOf(total),fuente10));
                            break;
                        default:
                            break;
                    }
                    c22.setBorder(0);
                    c22.setBackgroundColor(cNB);
                    tablaCar.addCell(c22);
                }
            }

            if(!txtNotas.getText().equals("")){
                PdfPCell notas = new PdfPCell(new Phrase("  NOTA: "+txtNotas.getText(),fuente8));
                notas.setBorder(0);
                tabla4.addCell(notas);
            }

            //------------------------------------------------------------------

            //------------------------------------------------------------------

            PdfPTable tabla6 = new PdfPTable(2);
            tabla6.setWidthPercentage(45);
            tabla6.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell c50 = new PdfPCell(new Phrase("Iva no incluido.",fuente8N));
            c50.setBorder(0);
            c50.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell c51 = new PdfPCell(new Phrase("Moneda: ",fuente8N));
            c51.setBorder(0);
            c51.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell c52 = new PdfPCell(new Phrase(jcbMoneda.getSelectedItem().toString(),fuente8));
            c52.setBorder(0);
            c52.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell c53 = new PdfPCell(new Phrase("Cotizacion valida hasta: ",fuente8N));
            c53.setBorder(0);
            c53.setHorizontalAlignment(Element.ALIGN_LEFT);

            SimpleDateFormat s = new SimpleDateFormat("dd MMMM yyyy");
            String fecha = s.format(Valido.getDate());

            PdfPCell c54 = new PdfPCell(new Phrase(fecha,fuente8));
            c54.setBorder(0);
            c54.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell c55 = new PdfPCell(new Phrase("",fuente8N));
            c55.setBorder(0);
            c55.setHorizontalAlignment(Element.ALIGN_LEFT);

            tabla6.addCell(c50);
            tabla6.addCell(c55);
            tabla6.addCell(c51);
            tabla6.addCell(c52);
            tabla6.addCell(c53);
            tabla6.addCell(c54);

            //-------------------------------------------------------------------

            PdfPTable tabla7 = new PdfPTable(1);
            tabla7.setWidthPercentage(30);
            tabla7.setHorizontalAlignment(Element.ALIGN_RIGHT);

            PdfPCell c59 = new PdfPCell(new Phrase(" "));
            c59.setBorder(0);

            tabla7.addCell(c59);

            Paragraph coti = new Paragraph(" ",fuente8);
            coti.setAlignment(Element.ALIGN_RIGHT);

            //------------------------------------------------------------------
            
            documento.add(tabla2);
            documento.add(coti);
            documento.add(tablaInformacion);
            documento.add(coti);
            documento.add(tablaDescripcion);
            documento.add(coti);
            documento.add(tabla4);
            documento.add(coti);
            documento.add(tablaCar);
            documento.add(Chunk.NEWLINE);
            documento.add(tabla6);

            documento.close();

            try{
            Runtime.getRuntime().exec("cmd /c start \\\\192.168.100.40\\bd\\Cotizacion\\"+OC.getText()+".pdf");
            Runtime.getRuntime().exec("cmd /c close");
            }catch(IOException  e){
              JOptionPane.showMessageDialog(this, e);
            }

        } catch (DocumentException ex) {
                Logger.getLogger(OrdenDeCompra.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(OrdenDeCompra.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(OrdenDeCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public final void insertarFecha(){
        SimpleDateFormat s1 = new SimpleDateFormat("EEEE, dd ' de ' MMMM ' de ' yyyy", new Locale("es", "ES"));
        Date d = new Date();
        lblFecha.setText(s1.format(d));
     }

     public final void insertarCotizacion(){
         try{
             Connection con = null;
             Conexion con1 = new Conexion();
             con = con1.getConnection();
             Statement st = con.createStatement();
             String sql = "select * from cotizacion";
             ResultSet rs = st.executeQuery(sql);
             String datos[] = new String[10];
             while(rs.next()){
                 datos[0] = rs.getString("CotizacionNo");
             }
             String numero = datos[0].substring(1,6);
             int num = Integer.parseInt(numero);
             num = num + 1;
             String cambio = "C"+num;
             
             OC.setText(cambio);
         }catch(SQLException e){
             JOptionPane.showMessageDialog(this, "ERROR" +e);
         }
     }
     
    public final void limpiarTabla(){
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Caracteristica", "Cantidad", "Precio", "Id", "Neg"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });

        Tabla1.setComponentPopupMenu(jPopupMenu1);



        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });

        jScrollPane3.setViewportView(Tabla1);

        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setResizable(false);
            Tabla1.getColumnModel().getColumn(1).setResizable(false);
            Tabla1.getColumnModel().getColumn(2).setResizable(false);
        }
        
        Tabla1.getTableHeader().setFont(new Font("Lexend", Font.BOLD, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0,153,255));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowGrid(false);
        Tabla1.setShowHorizontalLines(true);
        Tabla1.setGridColor(new Color(240,240,240));
        jScrollPane3.getViewport().setBackground(Color.white);
        jScrollPane3.setViewportView(Tabla1);
     }
     
    public void limpiarDatos(){
         txtProyecto.setText("");
         txtEmpresa.setText("");
         txtIngeniero.setText("");
         txtTelefono.setText("");
         txtModelo.setText("");
         txtDescripcion.setText("");
         txtTiempo.setText("");
         jcbMoneda.removeAllItems();
         jcbMoneda.addItem("SELECCIONAR MONEDA");
         jcbMoneda.addItem("MXN");
         jcbMoneda.addItem("USD");
         txtCaracteristicas.setText("");
         txtNotas.setText("");
         Valido.setDate(null);
         limpiarTabla();
     }
    
    public final void agregarClientes(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from clientes";
            ResultSet rs = st.executeQuery(sql);
            au = new TextAutoCompleter(txtEmpresa);
            au.removeAllItems();
            while(rs.next()){
                au.addItem(rs.getString("Nombre"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: " + e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void validarCliente(String empresa){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from clientes where Nombre like '" + empresa + "'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                nombre = rs.getString("Nombre");
                contacto = rs.getString("Contacto");
                telefono = rs.getString("Telefono");
                direccion = rs.getString("Direccion");
                correo = rs.getString("Correo");
            }
            if(nombre != null){
                txtIngeniero.setText(contacto);
                txtTelefono.setText(telefono);
                txtEmpresa.setEnabled(false);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: "+e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void getEmpleado(String numero){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroempleados where NumEmpleado like '" + numero + "'";
            ResultSet rs = st.executeQuery(sql);
            String empleado = null;
            String telefono = null;
            String correo = null;
            while(rs.next()){
                empleado = rs.getString("Nombre") + " " + rs.getString("Apellido");
                telefono = rs.getString("Telefono");
                correo = rs.getString("Correo");
            }
            nombreEmpleado = empleado;
            telefonoEmpleado = telefono;
            correoEmpleado = correo;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: "+e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public CotizacionVentas(String numEmpleado) {
        initComponents();
        insertarFecha();
        insertarCotizacion();
        agregarClientes();
        getEmpleado(numEmpleado);
        limpiarTabla();
        this.numEmpleado = numEmpleado;
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenu1 = new javax.swing.JPopupMenu();
        Agregar = new javax.swing.JMenuItem();
        Eliminar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        pan = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        panelCentral = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtProyecto = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtEmpresa = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtIngeniero = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        Valido = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTiempo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jcbMoneda = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtCaracteristicas = new javax.swing.JTextField();
        btnAgregarC = new javax.swing.JButton();
        botonRedondo2 = new scrollPane.BotonRedondo();
        botonRedondo3 = new scrollPane.BotonRedondo();
        btnGuardar = new scrollPane.BotonRedondo();
        lblCambios = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JFormattedTextField();
        txtCantidad = new javax.swing.JFormattedTextField();
        lblCaracteristica = new javax.swing.JLabel();
        panelIzquierda = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblFecha = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        OC = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtNotas = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();

        Agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Agregar_16.png"))); // NOI18N
        Agregar.setText("Agregar                                                      ");
        Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Agregar);

        Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/error.png"))); // NOI18N
        Eliminar.setText("Eliminar");
        jPopupMenu1.add(Eliminar);

        setBorder(null);
        getContentPane().setLayout(new java.awt.BorderLayout(10, 10));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setFont(new java.awt.Font("Lexend", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 153, 255));
        jLabel18.setText("Cotizaciones");
        jPanel9.add(jLabel18);

        jPanel8.add(jPanel9);

        jPanel7.add(jPanel8, java.awt.BorderLayout.CENTER);

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

        jPanel7.add(pan, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        panelCentral.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout panelCentralLayout = new java.awt.GridBagLayout();
        panelCentralLayout.columnWidths = new int[] {0, 5, 0, 5, 0};
        panelCentralLayout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        panelCentralLayout.columnWeights = new double[] {1.0, 1.0};
        panelCentralLayout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0};
        panelCentral.setLayout(panelCentralLayout);

        jLabel2.setFont(new java.awt.Font("Lexend", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Nombre del proyecto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(jLabel2, gridBagConstraints);

        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtProyecto.setNextFocusableComponent(txtEmpresa);
        txtProyecto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtProyectoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtProyectoFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(txtProyecto, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Lexend", 1, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Nombre de la empresa:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(jLabel13, gridBagConstraints);

        txtEmpresa.setBackground(new java.awt.Color(255, 255, 255));
        txtEmpresa.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtEmpresa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtEmpresa.setNextFocusableComponent(txtIngeniero);
        txtEmpresa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmpresaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmpresaFocusLost(evt);
            }
        });
        txtEmpresa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtEmpresaMouseClicked(evt);
            }
        });
        txtEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpresaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(txtEmpresa, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Lexend", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Ingeniero requisitor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(jLabel3, gridBagConstraints);

        txtIngeniero.setEditable(false);
        txtIngeniero.setBackground(new java.awt.Color(255, 255, 255));
        txtIngeniero.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtIngeniero.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtIngeniero.setEnabled(false);
        txtIngeniero.setNextFocusableComponent(txtTelefono);
        txtIngeniero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIngenieroFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIngenieroFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(txtIngeniero, gridBagConstraints);

        jLabel14.setFont(new java.awt.Font("Lexend", 1, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("Telefono:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(jLabel14, gridBagConstraints);

        txtTelefono.setEditable(false);
        txtTelefono.setBackground(new java.awt.Color(255, 255, 255));
        txtTelefono.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtTelefono.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtTelefono.setEnabled(false);
        txtTelefono.setNextFocusableComponent(txtModelo);
        txtTelefono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTelefonoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTelefonoFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(txtTelefono, gridBagConstraints);

        jLabel16.setFont(new java.awt.Font("Lexend", 1, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("Modelo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(jLabel16, gridBagConstraints);

        txtModelo.setBackground(new java.awt.Color(255, 255, 255));
        txtModelo.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtModelo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtModelo.setNextFocusableComponent(Valido);
        txtModelo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtModeloFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtModeloFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(txtModelo, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Lexend", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Valido hasta:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(jLabel4, gridBagConstraints);

        Valido.setBackground(new java.awt.Color(255, 255, 255));
        Valido.setDateFormatString("dd MMMM yyyy");
        Valido.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        Valido.setNextFocusableComponent(txtDescripcion);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(Valido, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Lexend", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Descripcion:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(jLabel5, gridBagConstraints);

        txtDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtDescripcion.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtDescripcion.setNextFocusableComponent(txtTiempo);
        txtDescripcion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDescripcionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescripcionFocusLost(evt);
            }
        });
        txtDescripcion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDescripcionMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(txtDescripcion, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Lexend", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Tiempo de entrega:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(jLabel6, gridBagConstraints);

        txtTiempo.setBackground(new java.awt.Color(255, 255, 255));
        txtTiempo.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtTiempo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtTiempo.setNextFocusableComponent(jcbMoneda);
        txtTiempo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTiempoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTiempoFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(txtTiempo, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Lexend", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Moneda:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(jLabel7, gridBagConstraints);

        jcbMoneda.setBackground(new java.awt.Color(255, 255, 255));
        jcbMoneda.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        jcbMoneda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONAR MONEDA", "MXN", "USD" }));
        jcbMoneda.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(222, 222, 222), 2, true));
        jcbMoneda.setNextFocusableComponent(txtCaracteristicas);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(jcbMoneda, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Lexend", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Precio Unitario:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(jLabel8, gridBagConstraints);

        txtCaracteristicas.setBackground(new java.awt.Color(255, 255, 255));
        txtCaracteristicas.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtCaracteristicas.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtCaracteristicas.setNextFocusableComponent(txtCantidad);
        txtCaracteristicas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCaracteristicasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCaracteristicasFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(txtCaracteristicas, gridBagConstraints);

        btnAgregarC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/derecha_32.png"))); // NOI18N
        btnAgregarC.setBorder(null);
        btnAgregarC.setContentAreaFilled(false);
        btnAgregarC.setFocusPainted(false);
        btnAgregarC.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAgregarC.setNextFocusableComponent(txtCaracteristicas);
        btnAgregarC.setPreferredSize(new java.awt.Dimension(50, 50));
        btnAgregarC.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/derecha_32.png"))); // NOI18N
        btnAgregarC.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/derecha_48.png"))); // NOI18N
        btnAgregarC.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnAgregarC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridheight = 10;
        gridBagConstraints.ipadx = 23;
        gridBagConstraints.ipady = 23;
        panelCentral.add(btnAgregarC, gridBagConstraints);

        botonRedondo2.setBackground(new java.awt.Color(255, 255, 255));
        botonRedondo2.setForeground(new java.awt.Color(0, 153, 255));
        botonRedondo2.setText("Limpiar Datos");
        botonRedondo2.setBorderColor(new java.awt.Color(0, 153, 255));
        botonRedondo2.setBorderColorSelected(new java.awt.Color(0, 102, 204));
        botonRedondo2.setColor(new java.awt.Color(0, 153, 255));
        botonRedondo2.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        botonRedondo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRedondo2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.ipadx = 16;
        gridBagConstraints.ipady = 16;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(botonRedondo2, gridBagConstraints);

        botonRedondo3.setBackground(new java.awt.Color(255, 255, 255));
        botonRedondo3.setForeground(new java.awt.Color(204, 0, 0));
        botonRedondo3.setText("Generar PDF");
        botonRedondo3.setBorderColor(new java.awt.Color(204, 0, 0));
        botonRedondo3.setBorderColorSelected(new java.awt.Color(153, 0, 0));
        botonRedondo3.setColor(new java.awt.Color(204, 0, 0));
        botonRedondo3.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        botonRedondo3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRedondo3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.ipadx = 16;
        gridBagConstraints.ipady = 16;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(botonRedondo3, gridBagConstraints);

        btnGuardar.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar.setForeground(new java.awt.Color(0, 153, 0));
        btnGuardar.setText("Guardar");
        btnGuardar.setBorderColor(new java.awt.Color(0, 153, 0));
        btnGuardar.setBorderColorSelected(new java.awt.Color(0, 102, 0));
        btnGuardar.setColor(new java.awt.Color(0, 153, 0));
        btnGuardar.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.ipadx = 16;
        gridBagConstraints.ipady = 16;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(btnGuardar, gridBagConstraints);

        lblCambios.setFont(new java.awt.Font("Lexend", 1, 16)); // NOI18N
        lblCambios.setForeground(new java.awt.Color(51, 51, 51));
        lblCambios.setText("Caracteristicas:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(lblCambios, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Lexend", 1, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Cantidad:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(jLabel10, gridBagConstraints);

        txtPrecio.setBackground(new java.awt.Color(255, 255, 255));
        txtPrecio.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtPrecio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        txtPrecio.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        txtPrecio.setNextFocusableComponent(btnAgregarC);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(txtPrecio, gridBagConstraints);

        txtCantidad.setBackground(new java.awt.Color(255, 255, 255));
        txtCantidad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtCantidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        txtCantidad.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        txtCantidad.setNextFocusableComponent(txtPrecio);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        panelCentral.add(txtCantidad, gridBagConstraints);

        lblCaracteristica.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        lblCaracteristica.setText("Caracteristica");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        panelCentral.add(lblCaracteristica, gridBagConstraints);

        jPanel1.add(panelCentral, java.awt.BorderLayout.CENTER);

        panelIzquierda.setBackground(new java.awt.Color(255, 255, 255));
        panelIzquierda.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2), "Fecha", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 1, 14), new java.awt.Color(51, 51, 51))); // NOI18N

        lblFecha.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        lblFecha.setText("Fecha");
        jPanel3.add(lblFecha);

        panelIzquierda.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2), "Cotizacion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 1, 14), new java.awt.Color(51, 51, 51))); // NOI18N

        OC.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        OC.setText("Orden de compra");
        jPanel4.add(OC);

        jPanel10.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2), "Notas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 1, 14), new java.awt.Color(51, 51, 51))); // NOI18N
        jPanel5.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jPanel5.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        txtNotas.setBackground(new java.awt.Color(255, 255, 255));
        txtNotas.setColumns(20);
        txtNotas.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtNotas.setLineWrap(true);
        txtNotas.setRows(5);
        txtNotas.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtNotas);

        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel5, java.awt.BorderLayout.SOUTH);

        jPanel10.add(jPanel11, java.awt.BorderLayout.CENTER);

        panelIzquierda.add(jPanel10, java.awt.BorderLayout.CENTER);

        jPanel1.add(panelIzquierda, java.awt.BorderLayout.WEST);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Caracteristica", "Cantidad", "Precio", "Id", "Neg"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setResizable(false);
            Tabla1.getColumnModel().getColumn(1).setResizable(false);
            Tabla1.getColumnModel().getColumn(2).setResizable(false);
        }

        jPanel1.add(jScrollPane3, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/new_doc_16.png"))); // NOI18N
        jMenuItem1.setText("Nueva cotizacion");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator10);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/save_16.png"))); // NOI18N
        jMenuItem3.setText("Guardar");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);
        jMenu1.add(jSeparator11);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/open_16.png"))); // NOI18N
        jMenuItem2.setText("Abrir");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pdf.png"))); // NOI18N
        jMenuItem4.setText("Abrir PDF");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);
        jMenu1.add(jSeparator12);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/add.png"))); // NOI18N
        jMenuItem5.setText("Agregar Empresa");
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

    private void btnAgregarCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCActionPerformed
        if(txtCaracteristicas.getText().equals("")){
        JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE CARACTERISTICAS","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else{
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
        String datos[] = new String[5];
        datos[0] = txtCaracteristicas.getText();
        miModelo.addRow(datos);
        txtCaracteristicas.setText("");
        }
    }//GEN-LAST:event_btnAgregarCActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        Guardar();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        String opc = JOptionPane.showInputDialog("INTRODUCE EL NUMERO DE COTIZACION");
        if(opc.equals("")){
            JOptionPane.showMessageDialog(this, "Debes ingresar el numero de cotizacion","Error",JOptionPane.ERROR_MESSAGE);
        }else{
            try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from cotizacion where CotizacionNo like '"+opc+"'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[20];
            while(rs.next()){
                datos[0] = rs.getString("CotizacionNo");
                datos[1] = rs.getString("nombreEmpresa");
                datos[2] = rs.getString("Proyecto");
                datos[3] = rs.getString("Ingeniero");
                datos[4] = rs.getString("Modelo");
                datos[5] = rs.getString("Descripcion");
                datos[6] = rs.getString("TiempoEntrega");
                datos[7] = rs.getString("Moneda");
                datos[8] = rs.getString("FechaLimite");
                datos[9] = rs.getString("Nota");
                datos[10] = rs.getString("Fecha");
                datos[12] = rs.getString("Dirigido");
                datos[13] = rs.getString("Url");
                datos[14] = rs.getString("Limite");
            }
            if(opc.equals(datos[0])){
            lblFecha.setText(datos[10]);
            OC.setText(datos[0]);
            txtNotas.setText(datos[9]);
            txtProyecto.setText(datos[2]);
            txtEmpresa.setText(datos[1]);
            txtIngeniero.setText(datos[3]);
            txtTelefono.setText(datos[12]);
            txtModelo.setText(datos[4]);
            txtDescripcion.setText(datos[5]);
            txtTiempo.setText(datos[6]);
            jcbMoneda.setSelectedItem(datos[7]);
            btnGuardar.setEnabled(false);
            
            SimpleDateFormat s = new SimpleDateFormat("dd MMMM yyyy");
            Date fecha = s.parse(datos[14]);
            Valido.setDate(fecha);
            
            limpiarTabla();
            
            DefaultTableModel Modelo = (DefaultTableModel) Tabla1.getModel();
            
            Statement st3 = con.createStatement();
            String sql3 = "select * from caracteristicas where CotizacionNo like '"+datos[0]+"'";
            String datos3[] = new String[10];
            ResultSet rs3 = st3.executeQuery(sql3);
            while(rs3.next()){
                datos3[0] = rs3.getString("Caracteristica");
                Modelo.addRow(datos3);
            }
            
            }
            validarCliente(txtEmpresa.getText());
            }catch(SQLException E){
            JOptionPane.showMessageDialog(this, "ERROR: "+E,"ERROR",JOptionPane.ERROR_MESSAGE);
        }   catch (ParseException ex) {
                Logger.getLogger(CotizacionVentas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        insertarFecha();
        insertarCotizacion();
        limpiarDatos();
        btnGuardar.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        try{
          Runtime.getRuntime().exec("cmd /c start \\\\192.168.100.40\\bd\\Cotizacion\\"+OC.getText()+".pdf");
          Runtime.getRuntime().exec("cmd /c close");
       }catch(IOException  e){
              JOptionPane.showMessageDialog(this, e);
          }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

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

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        Guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void botonRedondo3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRedondo3ActionPerformed
        if(txtProyecto.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE PROYECTO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        } else if(txtIngeniero.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE INGENIERO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtModelo.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE MODELO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtDescripcion.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE DESCRIPCION","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtTiempo.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE TIEMPO DE ENTREGA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(jcbMoneda.getSelectedItem().equals("SELECCIONAR MONEDA")){
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR EL TIPO DE MONEDA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(Tabla1.getRowCount() == 0){
            JOptionPane.showMessageDialog(this, "DEBES INTRODUCIR POR LO MENOS UN CAMPO EN LA TABLA CARACTERISTICAS","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(Valido.getDate() ==  null){
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE FECHA LIMITE","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }
        else{
            exportarPdf();
        }
    }//GEN-LAST:event_botonRedondo3ActionPerformed

    private void botonRedondo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRedondo2ActionPerformed
        limpiarDatos();               
    }//GEN-LAST:event_botonRedondo2ActionPerformed

    private void txtProyectoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProyectoFocusGained
        txtProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0,153,255)));
    }//GEN-LAST:event_txtProyectoFocusGained

    private void txtEmpresaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmpresaFocusGained
        txtEmpresa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0,153,255)));
    }//GEN-LAST:event_txtEmpresaFocusGained

    private void txtIngenieroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIngenieroFocusGained
        txtIngeniero.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0,153,255)));
    }//GEN-LAST:event_txtIngenieroFocusGained

    private void txtTelefonoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTelefonoFocusGained
        txtTelefono.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0,153,255)));
    }//GEN-LAST:event_txtTelefonoFocusGained

    private void txtModeloFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtModeloFocusGained
        txtModelo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0,153,255)));
    }//GEN-LAST:event_txtModeloFocusGained

    private void txtDescripcionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDescripcionMouseClicked
        
    }//GEN-LAST:event_txtDescripcionMouseClicked

    private void txtTiempoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTiempoFocusGained
        txtTiempo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0,153,255)));
    }//GEN-LAST:event_txtTiempoFocusGained

    private void txtCaracteristicasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCaracteristicasFocusGained
        txtCaracteristicas.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0,153,255)));
    }//GEN-LAST:event_txtCaracteristicasFocusGained

    private void txtProyectoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProyectoFocusLost
        txtProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
    }//GEN-LAST:event_txtProyectoFocusLost

    private void txtEmpresaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmpresaFocusLost
        txtEmpresa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
    }//GEN-LAST:event_txtEmpresaFocusLost

    private void txtIngenieroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIngenieroFocusLost
        txtIngeniero.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
    }//GEN-LAST:event_txtIngenieroFocusLost

    private void txtTelefonoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTelefonoFocusLost
        txtTelefono.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
    }//GEN-LAST:event_txtTelefonoFocusLost

    private void txtModeloFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtModeloFocusLost
        txtModelo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
    }//GEN-LAST:event_txtModeloFocusLost

    private void txtDescripcionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescripcionFocusGained
        txtDescripcion.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0,153,255)));
    }//GEN-LAST:event_txtDescripcionFocusGained

    private void txtDescripcionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescripcionFocusLost
        txtDescripcion.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
    }//GEN-LAST:event_txtDescripcionFocusLost

    private void txtTiempoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTiempoFocusLost
        txtTiempo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
    }//GEN-LAST:event_txtTiempoFocusLost

    private void txtCaracteristicasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCaracteristicasFocusLost
        txtCaracteristicas.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
    }//GEN-LAST:event_txtCaracteristicasFocusLost

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        AgregarEmpresaVentas add = new AgregarEmpresaVentas(f,true);
        add.setLocationRelativeTo(f);
        boolean isValid = add.getValidation();
        if(isValid){
            agregarClientes();
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void txtEmpresaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEmpresaMouseClicked
        txtEmpresa.setEnabled(true);
        nombre = null;
        contacto = null;
        telefono = null;
        direccion = null;
        correo = null;
        txtEmpresa.setText("");
        txtTelefono.setText("");
        txtIngeniero.setText("");
    }//GEN-LAST:event_txtEmpresaMouseClicked

    private void txtEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpresaActionPerformed
        validarCliente(txtEmpresa.getText());
    }//GEN-LAST:event_txtEmpresaActionPerformed

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        selectedRow = Tabla1.getSelectedRow() + 1;
        boolean neg = false;
        try{neg = (boolean) Tabla1.getValueAt(Tabla1.getSelectedRow(), 3);}catch(Exception e){}
        if(neg){
            lblCambios.setText("Propiedad:");
            lblCaracteristica.setVisible(true);
            lblCaracteristica.setText(Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString());
            txtPrecio.setEnabled(false);
            txtCantidad.setEnabled(false);
        }else{
            lblCambios.setText("Caracteristica:");
            lblCaracteristica.setVisible(false);
            txtPrecio.setEnabled(true);
            txtCantidad.setEnabled(true);
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
        if(Tabla1.getValueAt(selectedRow-1, 1) != null){
            if(!Tabla1.getValueAt(selectedRow-1, 1).toString().equals("")){
                String row = Tabla1.getValueAt(selectedRow-1, 1).toString();
                
            }
        }
    }//GEN-LAST:event_AgregarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Agregar;
    private javax.swing.JMenuItem Eliminar;
    private javax.swing.JLabel OC;
    private javax.swing.JTable Tabla1;
    private com.toedter.calendar.JDateChooser Valido;
    private scrollPane.BotonRedondo botonRedondo2;
    private scrollPane.BotonRedondo botonRedondo3;
    private javax.swing.JButton btnAgregarC;
    private scrollPane.BotonRedondo btnGuardar;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JComboBox<String> jcbMoneda;
    private javax.swing.JLabel lblCambios;
    private javax.swing.JLabel lblCaracteristica;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelCentral;
    private javax.swing.JPanel panelIzquierda;
    private javax.swing.JPanel panelSalir;
    private javax.swing.JFormattedTextField txtCantidad;
    private javax.swing.JTextField txtCaracteristicas;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtEmpresa;
    private javax.swing.JTextField txtIngeniero;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextArea txtNotas;
    private javax.swing.JFormattedTextField txtPrecio;
    private javax.swing.JTextField txtProyecto;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTiempo;
    // End of variables declaration//GEN-END:variables
}
