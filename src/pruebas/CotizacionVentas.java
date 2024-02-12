package pruebas;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import Conexiones.Conexion;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Header;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class CotizacionVentas extends javax.swing.JInternalFrame {

    ResultSet res;
    private java.awt.Image data;
    FileInputStream Cargar_Archivo;
    
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
 public void cargarfoto(String id) {
        java.awt.Image dtCat = recuperarfotos(id);
        javax.swing.ImageIcon icon = new javax.swing.ImageIcon(dtCat);
        java.awt.Image img = icon.getImage();
        java.awt.Image newimg = img.getScaledInstance(222, 212, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newimg);
        if (newIcon == null) 
            {
                JOptionPane.showMessageDialog(null, "EL EQUIPO NO TIENE FOTO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } 
        else 
            {
                imagen.setIcon(newIcon);
                imagen.setSize(imagen.getWidth(), imagen.getWidth()); 
            }
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
        }else if(tblCaracteristicas.getRowCount() == 0){
            JOptionPane.showMessageDialog(this, "DEBES INTRODUCIR POR LO MENOS UN CAMPO EN LA TABLA CARACTERISTICAS","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(tblPropuestas.getRowCount() == 0){
            JOptionPane.showMessageDialog(this, "DEBES INTRODUCIR POR LO MENOS UN CAMPO EN LA TABLA PROPUESTAS","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(Valido.getDate() ==  null){
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE FECHA LIMITE","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }
        else{
            
            try{
                btnGuardar.setVisible(true);
                jLabel11.setVisible(true);
                Connection con = null;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql = "insert into cotizacion (CotizacionNo,nombreEmpresa,Proyecto,Ingeniero,Modelo,Descripcion,TiempoEntrega,Moneda,FechaLimite,Nota,Fecha,Imagen,Dirigido,Url,Limite) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
                Cargar_Archivo = new FileInputStream(lblUrl.getText());
                pst.setBinaryStream(12, Cargar_Archivo);
                pst.setString(13, txtDirigido.getText());
                pst.setString(14, lblUrl.getText());
                
                SimpleDateFormat s = new SimpleDateFormat("dd MMMM yyyy");
                String f = s.format(Valido.getDate());
                pst.setString(15, f);
                
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
                
                int n2 = 0,n3 = 0;
                
                for (int i = 0; i < tblCaracteristicas.getRowCount(); i++) {
                    pst2.setString(1, tblCaracteristicas.getValueAt(i, 0).toString());
                    pst2.setString(2, datos[0]);
                    
                    n2 = pst2.executeUpdate();
                    
                }
                pst2.setString(1, sql);
                
                String sql3 = "insert into propuestas (propuesta,costo,CotizacionNo) values (?,?,?)";
                PreparedStatement pst3 = con.prepareStatement(sql3);
                
                for (int i = 0; i < tblPropuestas.getRowCount(); i++) {
                    pst3.setString(1, tblPropuestas.getValueAt(i, 0).toString());
                    pst3.setString(2, tblPropuestas.getValueAt(i, 1).toString());
                    pst3.setString(3, datos[0]);
                    
                    n3 = pst3.executeUpdate();
                    
                }
                
                if(n > 0 && n2 > 0 && n3 > 0){
                    JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                    exportarPdf();
                    jLabel11.setVisible(false);
                    btnGuardar.setVisible(false);
                }
                
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "ERROR: "+e);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CotizacionVentas.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
 
    public void exportarPdf(){
        
        try {
            Document documento = new Document();
            PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream("\\\\100.100.200.10\\bd\\Cotizacion\\"+OC.getText()+".pdf"));
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
            BaseColor blanco = WebColors.getRGBColor("#ffffff");
            BaseColor fondo = WebColors.getRGBColor("#fbe5d6");
            
            //------------------------RELLENOS----------------------------------
            PdfContentByte r1 = writer.getDirectContentUnder();
            r1.saveState();
            r1.setRGBColorFill(227, 106, 7);
            r1.rectangle(0, 730, 800, 90);
            r1.fill();
            r1.restoreState();
            
            
            //------------------------------------------------------------------
            //------------------------TABLAS------------------------------------
            PdfPTable tabla1 = new PdfPTable(1);
            tabla1.setWidthPercentage(100);
            PdfPCell c1 = new PdfPCell(new Phrase("Servicios Industriales 3i S de RL MI",fuente30));
            PdfPCell c2 = new PdfPCell(new Phrase("Integración de Equipos Industriales – Programación de Sistemas de Control \n" +
            "Capacitación PLC – Refacciones en General.",fuente25));
            PdfPCell c6 = new PdfPCell(new Phrase("       "
                    + "            ",fuente30));
            
            c1.setBorder(0);
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            c2.setBorder(0);
            c6.setBorder(0);
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla1.addCell(c1);
            tabla1.addCell(c2);
            
            tabla1.addCell(c6);
            
            //-------------------------------------------------------------------
            
            PdfPTable tabla2 = new PdfPTable(1);
            tabla2.setWidthPercentage(100);
            char comillas = '"';
            
            String negritas = comillas+txtProyecto.getText()+comillas;
            PdfPCell c3 = new PdfPCell(new Phrase("Estimado cliente.\n" +
            "Ponemos a su consideración la siguiente cotización , la cual cumple con las especificaciones del documento \n"+negritas+" de igual manera indicamos las características de nuestra solución así como nuestra propuesta económica.\n"
                    + "     ",fuente10));
            
            PdfPCell c4 = new PdfPCell(new Phrase(txtDirigido.getText(),fuente20));
            PdfPCell c5 = new PdfPCell(new Phrase(lblFecha.getText()+"\n",fuente25Negro));
            
            
            c3.setBackgroundColor(fondo);
            c3.setBorder(0);
            c3.setHorizontalAlignment(Element.ALIGN_LEFT);
            
            c4.setBackgroundColor(fondo);
            c4.setBorder(0);
            c4.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            c5.setBackgroundColor(fondo);
            c5.setBorder(0);
            c5.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            tabla2.addCell(c3);
            tabla2.addCell(c4);
            tabla2.addCell(c5);
            
            //------------------------------------------------------------------
            
            PdfPTable tabla3 = new PdfPTable(2);
            tabla3.setWidthPercentage(90);
            tabla3.setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            PdfPCell c10 = new PdfPCell(new Phrase("\nProyecto-Modelo: ",fuente10N));
            c10.setBorder(0);
            c10.setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            PdfPCell c11 = new PdfPCell(new Phrase("\n"+txtProyecto.getText(),fuente10));
            c11.setBorder(0);
            c11.setHorizontalAlignment(Element.ALIGN_LEFT);
            
            PdfPCell c12 = new PdfPCell(new Phrase("Ingeniero Requester: ",fuente10N));
            c12.setBorder(0);
            c12.setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            PdfPCell c13 = new PdfPCell(new Phrase(txtIngeniero.getText(),fuente10));
            c13.setBorder(0);
            c13.setHorizontalAlignment(Element.ALIGN_LEFT);
            
            PdfPCell c14 = new PdfPCell(new Phrase("Modelo: ",fuente10N));
            c14.setBorder(0);
            c14.setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            PdfPCell c15 = new PdfPCell(new Phrase(txtModelo.getText(),fuente10));
            c15.setBorder(0);
            c15.setHorizontalAlignment(Element.ALIGN_LEFT);
            
            PdfPCell c16 = new PdfPCell(new Phrase("Descripcion: ",fuente10N));
            c16.setBorder(0);
            c16.setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            PdfPCell c17 = new PdfPCell(new Phrase(txtDescripcion.getText(),fuente10));
            c17.setBorder(0);
            c17.setHorizontalAlignment(Element.ALIGN_LEFT);
            
            PdfPCell c18 = new PdfPCell(new Phrase("Tiempo de entrega: ",fuente10N));
            c18.setBorder(0);
            c18.setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            PdfPCell c19 = new PdfPCell(new Phrase(txtTiempo.getText(),fuente10));
            c19.setBorder(0);
            c19.setHorizontalAlignment(Element.ALIGN_LEFT);
            
            tabla3.addCell(c10);
            tabla3.addCell(c11);
            tabla3.addCell(c12);
            tabla3.addCell(c13);
            tabla3.addCell(c14);
            tabla3.addCell(c15);
            tabla3.addCell(c16);
            tabla3.addCell(c17);
            tabla3.addCell(c18);
            tabla3.addCell(c19);
            
            //-------------------------------------------------------------------
            
            PdfPTable tabla4 = new PdfPTable(1);
            tabla4.setWidthPercentage(100);
            
            PdfPCell c20 = new PdfPCell(new Phrase("Caracterísiticas",fuente20));
            c20.setHorizontalAlignment(Element.ALIGN_LEFT);
            c20.setBorderColor(myColor);
            
            PdfPCell c21 = new PdfPCell(new Phrase("•"+txtProyecto.getText(),fuente12N));
            c21.setBorder(0);
            c21.setHorizontalAlignment(Element.ALIGN_LEFT);
            c21.setBorderColor(myColor);
            
            tabla4.addCell(c20);
            tabla4.addCell(c21);
            
            for (int i = 0; i < tblCaracteristicas.getRowCount(); i++) {
                PdfPCell c22 = new PdfPCell(new Phrase("     •"+tblCaracteristicas.getValueAt(i, 0).toString(),fuente10));
                c22.setBorder(0);
                tabla4.addCell(c22);
                
            }
            
            if(txtNotas.getText() != ("")){
                PdfPCell notas = new PdfPCell(new Phrase("  NOTA: "+txtNotas.getText(),fuente8));
                notas.setBorder(0);
                tabla4.addCell(notas);
            }
            
            //------------------------------------------------------------------
            
            PdfPTable tabla5 = new PdfPTable(2);
            tabla5.setWidthPercentage(100);
            
            PdfPCell c30 = new PdfPCell(new Phrase("Popuesta económica",fuente20));
            c30.setColspan(2);
            c30.setBorderColor(myColor);
            tabla5.addCell(c30);
            
            for (int i = 0; i < tblPropuestas.getRowCount(); i++) {
                for (int j = 0; j < tblPropuestas.getColumnCount(); j++) {
                    PdfPCell c31 = new PdfPCell(new Phrase(tblPropuestas.getValueAt(i, j).toString(),fuente10));
                    c31.setBorder(0);
                    if(j%2 == 0 || j == 0){
                        c31.setHorizontalAlignment(Element.ALIGN_LEFT);
                    }else{
                    
                    c31 = new PdfPCell(new Phrase("$"+tblPropuestas.getValueAt(i, j).toString(),fuente10));
                    c31.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    c31.setBorder(0);
                    }
                    tabla5.addCell(c31);
                }
                
            }
            
            
            double total = 0;
            for (int i = 0; i < tblPropuestas.getRowCount(); i++) {
                String s = tblPropuestas.getValueAt(i, 1).toString();
                int con = Integer.parseInt(s);
                total = total +con;
            }
            
            PdfPCell c32 = new PdfPCell(new Phrase("TOTAL: $"+total,fuente10N));
            c32.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c32.setVerticalAlignment(Element.ALIGN_BOTTOM);
            c32.setColspan(2);
            c32.setBorder(0);
            
            tabla5.addCell(c32);
            
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
            
            PdfPCell c59 = new PdfPCell(new Phrase("Cotizacion: "+OC.getText(),fuente10B));
            
            c59.setHorizontalAlignment(Element.ALIGN_CENTER);
            c59.setBackgroundColor(myColor);
            c59.setBorderColor(blanco);
            
            tabla7.addCell(c59);
            
            //------------------------PIE DE PAGINA-----------------------------
            
            
            //------------------------------------------------------------------
            
            //-----------------------FRSAES-------------------------------------
            
            Paragraph coti = new Paragraph("");
            coti.setAlignment(Element.ALIGN_RIGHT);
            
            //------------------------------------------------------------------
            Image img = Image.getInstance("C:\\Pruebas\\BD\\si3i.png");
            img.setAbsolutePosition(500, 700);
            
//            Image imgPie = Image.getInstance("C:\\Pruebas\\BD\\pie.png");
//            imgPie.setAbsolutePosition(0, 0);
//            imgPie.scaleAbsolute(600,30);
            
            Image imgLogo = Image.getInstance(lblUrl.getText());
            imgLogo.setAbsolutePosition(40, 470);
            imgLogo.scaleAbsolute(120,120);
            
            documento.add(tabla1);
            
            documento.add(img);
            documento.add(tabla2);
            documento.add(tabla7);
            documento.add(imgLogo);
            documento.add(coti);
            documento.add(tabla3);
            documento.add(Chunk.NEWLINE);
            documento.add(Chunk.NEWLINE);
            documento.add(tabla4);
            documento.add(Chunk.NEWLINE);
            documento.add(tabla5);
            documento.add(tabla6);
//            documento.add(imgPie);
            
            documento.close();
            
            try{
            Runtime.getRuntime().exec("cmd /c start \\\\100.100.200.10\\bd\\Cotizacion\\"+OC.getText()+".pdf");
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
    
     public void insertarFecha(){
         DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        lblFecha.setText(dtf3.format(LocalDateTime.now()));
     }
     
     public void insertarCotizacion(){
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
     
     public void limpiarTabla1(){
         DefaultTableModel miModelo = (DefaultTableModel) tblCaracteristicas.getModel();
         String titulos[] = {"CARACTERISTICAS"};
         miModelo = new DefaultTableModel(null, titulos);
         tblCaracteristicas.setModel(miModelo);
     }
     public void limpiarTabla2(){
         DefaultTableModel miModelo = (DefaultTableModel) tblPropuestas.getModel();
         String titulos[] = {"PROPUESTA","COSTO"};
         miModelo = new DefaultTableModel(null, titulos);
        
         tblPropuestas.setModel(miModelo);
          tblPropuestas.getColumnModel().getColumn(0).setPreferredWidth(300);
     }
     
     public void limpiarDatos(){
         txtProyecto.setText("");
         txtEmpresa.setText("");
         txtIngeniero.setText("");
         txtDirigido.setText("");
         txtModelo.setText("");
         txtDescripcion.setText("");
         txtTiempo.setText("");
         jcbMoneda.removeAllItems();
         jcbMoneda.addItem("SELECCIONAR MONEDA");
         jcbMoneda.addItem("MXN");
         jcbMoneda.addItem("USD");
         txtCaracteristicas.setText("");
         txtPropuesta.setText("");
         txtCosto.setText("");
         txtNotas.setText("");
         lblUrl.setText("");
         imagen.setIcon(null);
         Valido.setDate(null);
         limpiarTabla1();
         limpiarTabla2();
     }
    
    public CotizacionVentas() {
        initComponents();
        insertarFecha();
        insertarCotizacion();
        
        tblCaracteristicas.getTableHeader().setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 14));
        tblCaracteristicas.getTableHeader().setOpaque(false);
        tblCaracteristicas.getTableHeader().setBackground(Color.white);
        tblCaracteristicas.getTableHeader().setForeground(java.awt.Color.black);
        tblCaracteristicas.setRowHeight(25);
        
        tblPropuestas.getTableHeader().setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 14));
        tblPropuestas.getTableHeader().setOpaque(false);
        tblPropuestas.getTableHeader().setBackground(new java.awt.Color(0,0,0,0));
        tblPropuestas.getTableHeader().setForeground(java.awt.Color.black);
        tblPropuestas.setRowHeight(25);
        
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblFecha = new javax.swing.JLabel();
        btnSalir = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        OC = new javax.swing.JLabel();
        txtProyecto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        txtIngeniero = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        txtTiempo = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jcbMoneda = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtCaracteristicas = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        txtAgregarP = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtPropuesta = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        txtCosto = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        btnAgregarC = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCaracteristicas = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPropuestas = new javax.swing.JTable();
        btnGuardar = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        imagen = new javax.swing.JLabel();
        btnAbrir = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        lblUrl = new javax.swing.JLabel();
        txtEmpresa = new javax.swing.JTextField();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtNotas = new javax.swing.JTextArea();
        txtDirigido = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        btnLimpiar = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        Valido = new com.toedter.calendar.JDateChooser();
        btnGenerar = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 165, 252));
        jPanel2.setForeground(new java.awt.Color(0, 204, 255));

        jLabel1.setFont(new java.awt.Font("Roboto Light", 0, 60)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("COTIZACION VENTAS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(91, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(77, 77, 77))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 4, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(256, 6, 750, 75));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "FECHA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        lblFecha.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblFecha.setText("FECHA");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFecha)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 230, 50));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel27.setFont(new java.awt.Font("Roboto Light", 0, 48)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("x");
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel27MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel27MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel27MouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnSalirLayout = new javax.swing.GroupLayout(btnSalir);
        btnSalir.setLayout(btnSalirLayout);
        btnSalirLayout.setHorizontalGroup(
            btnSalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnSalirLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnSalirLayout.setVerticalGroup(
            btnSalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSalirLayout.createSequentialGroup()
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 0, 60, 50));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "COTIZACION:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        OC.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        OC.setText("ORDEN DE COMPRA");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(OC, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(OC)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 230, -1));

        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setBorder(null);
        jPanel1.add(txtProyecto, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 140, 210, -1));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel2.setText("NOMBRE DEL PROYECTO");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 120, -1, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 160, 210, 10));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel3.setText("INGENIERO REQUESTER");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 170, -1, -1));

        txtIngeniero.setBackground(new java.awt.Color(255, 255, 255));
        txtIngeniero.setBorder(null);
        jPanel1.add(txtIngeniero, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 190, 210, -1));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, 210, 10));

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel4.setText("VALIDO HASTA:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 230, -1, -1));

        txtModelo.setBackground(new java.awt.Color(255, 255, 255));
        txtModelo.setBorder(null);
        jPanel1.add(txtModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 250, 210, -1));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 270, 210, 10));

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel5.setText("DESCRIPCION");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 280, -1, -1));

        txtDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setBorder(null);
        jPanel1.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 300, 410, -1));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 320, 410, 10));

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel6.setText("TIEMPO DE ENTREGA");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 330, -1, -1));

        txtTiempo.setBackground(new java.awt.Color(255, 255, 255));
        txtTiempo.setBorder(null);
        jPanel1.add(txtTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 350, 410, -1));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 370, 410, 10));

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel7.setText("MONEDA");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 390, -1, -1));

        jcbMoneda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONAR MONEDA", "MXN", "USD" }));
        jPanel1.add(jcbMoneda, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 410, 410, -1));

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel8.setText("CARACTERISTICAS:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 450, -1, -1));

        txtCaracteristicas.setBackground(new java.awt.Color(255, 255, 255));
        txtCaracteristicas.setBorder(null);
        jPanel1.add(txtCaracteristicas, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 470, 310, -1));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 490, 310, 10));

        txtAgregarP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/derecha_32.png"))); // NOI18N
        txtAgregarP.setBorder(null);
        txtAgregarP.setContentAreaFilled(false);
        txtAgregarP.setFocusPainted(false);
        txtAgregarP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtAgregarP.setPreferredSize(new java.awt.Dimension(50, 50));
        txtAgregarP.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/derecha_32.png"))); // NOI18N
        txtAgregarP.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/derecha_48.png"))); // NOI18N
        txtAgregarP.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        txtAgregarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAgregarPActionPerformed(evt);
            }
        });
        jPanel1.add(txtAgregarP, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 560, -1, -1));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel9.setText("PROPUESTA ECONOMICA:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 510, -1, -1));

        txtPropuesta.setBackground(new java.awt.Color(255, 255, 255));
        txtPropuesta.setBorder(null);
        jPanel1.add(txtPropuesta, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 530, 310, -1));
        jPanel1.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 550, 310, 10));

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel10.setText("COSTO");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 560, -1, -1));

        txtCosto.setBackground(new java.awt.Color(255, 255, 255));
        txtCosto.setBorder(null);
        txtCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCostoKeyTyped(evt);
            }
        });
        jPanel1.add(txtCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 580, 290, -1));
        jPanel1.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 600, 290, 10));

        btnAgregarC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/derecha_32.png"))); // NOI18N
        btnAgregarC.setBorder(null);
        btnAgregarC.setContentAreaFilled(false);
        btnAgregarC.setFocusPainted(false);
        btnAgregarC.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAgregarC.setPreferredSize(new java.awt.Dimension(50, 50));
        btnAgregarC.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/derecha_32.png"))); // NOI18N
        btnAgregarC.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/derecha_48.png"))); // NOI18N
        btnAgregarC.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnAgregarC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregarC, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 460, -1, -1));

        tblCaracteristicas.setBackground(new java.awt.Color(255, 255, 255));
        tblCaracteristicas.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        tblCaracteristicas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CARACTERISTICAS"
            }
        ));
        tblCaracteristicas.setGridColor(new java.awt.Color(255, 255, 255));
        tblCaracteristicas.setRowHeight(25);
        tblCaracteristicas.setShowHorizontalLines(true);
        tblCaracteristicas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCaracteristicasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblCaracteristicas);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 120, 600, 260));

        tblPropuestas.setBackground(new java.awt.Color(255, 255, 255));
        tblPropuestas.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        tblPropuestas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PROPUESTA", "COSTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPropuestas.setGridColor(new java.awt.Color(255, 255, 255));
        tblPropuestas.setRowHeight(25);
        tblPropuestas.setShowHorizontalLines(true);
        tblPropuestas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPropuestasMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblPropuestas);
        if (tblPropuestas.getColumnModel().getColumnCount() > 0) {
            tblPropuestas.getColumnModel().getColumn(0).setPreferredWidth(300);
        }

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 390, 600, 260));

        btnGuardar.setBackground(new java.awt.Color(0, 153, 255));
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel11.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("GUARDAR");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel11MouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnGuardarLayout = new javax.swing.GroupLayout(btnGuardar);
        btnGuardar.setLayout(btnGuardarLayout);
        btnGuardarLayout.setHorizontalGroup(
            btnGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
        btnGuardarLayout.setVerticalGroup(
            btnGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(553, 620, 110, 30));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(imagen, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(imagen, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 230, 220));

        btnAbrir.setBackground(new java.awt.Color(0, 153, 255));
        btnAbrir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel12.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("ABRIR");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel12MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel12MouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnAbrirLayout = new javax.swing.GroupLayout(btnAbrir);
        btnAbrir.setLayout(btnAbrirLayout);
        btnAbrirLayout.setHorizontalGroup(
            btnAbrirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
        );
        btnAbrirLayout.setVerticalGroup(
            btnAbrirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(btnAbrir, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 470, -1, -1));
        jPanel1.add(lblUrl, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 230, 20));

        txtEmpresa.setBackground(new java.awt.Color(255, 255, 255));
        txtEmpresa.setBorder(null);
        jPanel1.add(txtEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 140, 210, -1));
        jPanel1.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 160, 210, 10));

        jLabel13.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel13.setText("NOMBRE DE LA EMPRESA");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 120, -1, -1));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "NOTAS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel5.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        txtNotas.setBackground(new java.awt.Color(255, 255, 255));
        txtNotas.setColumns(20);
        txtNotas.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtNotas.setLineWrap(true);
        txtNotas.setRows(5);
        txtNotas.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtNotas);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 520, 230, 130));

        txtDirigido.setBackground(new java.awt.Color(255, 255, 255));
        txtDirigido.setBorder(null);
        jPanel1.add(txtDirigido, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 190, 210, -1));

        jLabel14.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel14.setText("DIRIGIDO A:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 170, -1, -1));
        jPanel1.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 210, 210, 10));

        btnLimpiar.setBackground(new java.awt.Color(0, 153, 255));
        btnLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel15.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("LIMPIAR DATOS");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel15MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel15MouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnLimpiarLayout = new javax.swing.GroupLayout(btnLimpiar);
        btnLimpiar.setLayout(btnLimpiarLayout);
        btnLimpiarLayout.setHorizontalGroup(
            btnLimpiarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
        );
        btnLimpiarLayout.setVerticalGroup(
            btnLimpiarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 620, -1, -1));

        jLabel16.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel16.setText("MODELO");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, -1, -1));

        Valido.setBackground(new java.awt.Color(255, 255, 255));
        Valido.setDateFormatString("dd MMMM yyyy");
        jPanel1.add(Valido, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 250, 210, -1));

        btnGenerar.setBackground(new java.awt.Color(0, 153, 255));
        btnGenerar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel17.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("GENERAR PDF");
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel17MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel17MouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnGenerarLayout = new javax.swing.GroupLayout(btnGenerar);
        btnGenerar.setLayout(btnGenerarLayout);
        btnGenerarLayout.setHorizontalGroup(
            btnGenerarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
        btnGenerarLayout.setVerticalGroup(
            btnGenerarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(btnGenerar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 620, 110, 30));

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/new.png"))); // NOI18N
        jMenuItem1.setText("Nueva cotizacion");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator10);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/save.png"))); // NOI18N
        jMenuItem3.setText("Guardar");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);
        jMenu1.add(jSeparator11);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/open.png"))); // NOI18N
        jMenuItem2.setText("Abrir");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/open pdf.png"))); // NOI18N
        jMenuItem4.setText("Abrir PDF");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel27MouseClicked

    private void jLabel27MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseEntered
        btnSalir.setBackground(Color.red);
    }//GEN-LAST:event_jLabel27MouseEntered

    private void jLabel27MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseExited
        btnSalir.setBackground(Color.white);
    }//GEN-LAST:event_jLabel27MouseExited

    private void btnAgregarCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCActionPerformed
        if(txtCaracteristicas.getText().equals("")){
        JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE CARACTERISTICAS","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else{
        DefaultTableModel miModelo = (DefaultTableModel) tblCaracteristicas.getModel();
        String datos[] = new String[5];
        datos[0] = txtCaracteristicas.getText();
        miModelo.addRow(datos);
        txtCaracteristicas.setText("");
        }
    }//GEN-LAST:event_btnAgregarCActionPerformed

    private void jLabel11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseEntered
        btnGuardar.setBackground(new Color(0,82,137));
    }//GEN-LAST:event_jLabel11MouseEntered

    private void jLabel11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseExited
        btnGuardar.setBackground(new Color(0,153,255));
    }//GEN-LAST:event_jLabel11MouseExited

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        Guardar();
        
    }//GEN-LAST:event_jLabel11MouseClicked

    private void txtAgregarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAgregarPActionPerformed
        if(txtPropuesta.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE PROPUESTAS","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else{
            if(txtCosto.getText().equals("")){
                JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE COSTO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }else{
                DefaultTableModel miModelo = (DefaultTableModel) tblPropuestas.getModel();
                String datos[] = new String[5];
                datos[0] = txtPropuesta.getText();
                datos[1] = txtCosto.getText();
                miModelo.addRow(datos);
                txtPropuesta.setText("");
                txtCosto.setText("");
            }
        }
    }//GEN-LAST:event_txtAgregarPActionPerformed

    private void tblCaracteristicasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCaracteristicasMouseClicked
        int opc = JOptionPane.showConfirmDialog(this, "¿DESEAS ELIMIAR ESTA FILA?");
        if(opc == 0){
            DefaultTableModel miModelo = (DefaultTableModel) tblCaracteristicas.getModel();
            int fila = tblCaracteristicas.getSelectedRow();;
            miModelo.removeRow(fila);
        }
    }//GEN-LAST:event_tblCaracteristicasMouseClicked

    private void tblPropuestasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPropuestasMouseClicked
        int opc = JOptionPane.showConfirmDialog(this, "¿DESEAS ELIMIAR ESTA FILA?");
        if(opc == 0){
            DefaultTableModel miModelo = (DefaultTableModel) tblPropuestas.getModel();
            int fila = tblPropuestas.getSelectedRow();;
            miModelo.removeRow(fila);
        }
    }//GEN-LAST:event_tblPropuestasMouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        FileFilter ff = new FileNameExtensionFilter("JPG","jpg");
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("SELECCIONA UNA IMAGEN");
        jfc.setFileFilter(ff);
        int Open = jfc.showOpenDialog(null);
        if(Open == JFileChooser.APPROVE_OPTION){
            File archivo = jfc.getSelectedFile();
            lblUrl.setText(String.valueOf(archivo));
            java.awt.Image imagen = getToolkit().getImage(lblUrl.getText());
            imagen = imagen.getScaledInstance(222, 212, java.awt.Image.SCALE_SMOOTH);
            this.imagen.setIcon(new ImageIcon(imagen));
        }
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseEntered
        btnAbrir.setBackground(new Color(0,82,137));
    }//GEN-LAST:event_jLabel12MouseEntered

    private void jLabel12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseExited
        btnAbrir.setBackground(new Color(0,153,255));
    }//GEN-LAST:event_jLabel12MouseExited

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        Guardar();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        limpiarDatos();
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseEntered
        btnLimpiar.setBackground(new Color(0,82,137));
    }//GEN-LAST:event_jLabel15MouseEntered

    private void jLabel15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseExited
        btnLimpiar.setBackground(new Color(0,153,255));
    }//GEN-LAST:event_jLabel15MouseExited

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        
        
        String opc = JOptionPane.showInputDialog("INTRODUCE EL NUMERO DE COTIZACION");
        if(opc.equals("")){
            
        }else{
            try{
            btnGuardar.setVisible(false);
            jLabel11.setVisible(false);
            Connection con = null;
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
            cargarfoto(datos[0]);
            txtNotas.setText(datos[9]);
            txtProyecto.setText(datos[2]);
            txtEmpresa.setText(datos[1]);
            txtIngeniero.setText(datos[3]);
            txtDirigido.setText(datos[12]);
            txtModelo.setText(datos[4]);
            txtDescripcion.setText(datos[5]);
            txtTiempo.setText(datos[6]);
            jcbMoneda.setSelectedItem(datos[7]);
            lblUrl.setText(datos[13]);
            
            
            SimpleDateFormat s = new SimpleDateFormat("dd MMMM yyyy");
            Date fecha = s.parse(datos[14]);
            Valido.setDate(fecha);
            
            limpiarTabla1();
            limpiarTabla2();
            
            DefaultTableModel miModelo = (DefaultTableModel) tblPropuestas.getModel();
            DefaultTableModel Modelo = (DefaultTableModel) tblCaracteristicas.getModel();
            
            Statement st2 = con.createStatement();
            String sql2 = "select * from propuestas where CotizacionNo like '"+datos[0]+"'";
            String datos1[] = new String[10];
            ResultSet rs2 = st2.executeQuery(sql2);
            while(rs2.next()){
                datos1[0] = rs2.getString("Propuesta");
                datos1[1] = rs2.getString("Costo");
                miModelo.addRow(datos1);
            }
            
            Statement st3 = con.createStatement();
            String sql3 = "select * from caracteristicas where CotizacionNo like '"+datos[0]+"'";
            String datos3[] = new String[10];
            ResultSet rs3 = st3.executeQuery(sql3);
            while(rs3.next()){
                datos3[0] = rs3.getString("Caracteristica");
                Modelo.addRow(datos3);
            }
            
            }
            
            }catch(SQLException E){
            JOptionPane.showMessageDialog(this, "ERROR: "+E,"ERROR",JOptionPane.ERROR_MESSAGE);
        }   catch (ParseException ex) {
                Logger.getLogger(CotizacionVentas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void txtCostoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoKeyTyped
        char c = evt.getKeyChar();
        if(c<'0'||c>'9') evt.consume();
    }//GEN-LAST:event_txtCostoKeyTyped

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        insertarFecha();
        insertarCotizacion();
        limpiarDatos();
        btnGuardar.setVisible(true);
        jLabel11.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
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
        }else if(tblCaracteristicas.getRowCount() == 0){
            JOptionPane.showMessageDialog(this, "DEBES INTRODUCIR POR LO MENOS UN CAMPO EN LA TABLA CARACTERISTICAS","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(tblPropuestas.getRowCount() == 0){
            JOptionPane.showMessageDialog(this, "DEBES INTRODUCIR POR LO MENOS UN CAMPO EN LA TABLA PROPUESTAS","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(Valido.getDate() ==  null){
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE FECHA LIMITE","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }
        else{
        exportarPdf();
        }
    }//GEN-LAST:event_jLabel17MouseClicked

    private void jLabel17MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseEntered
        btnGenerar.setBackground(new Color(0,82,137));
    }//GEN-LAST:event_jLabel17MouseEntered

    private void jLabel17MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseExited
        btnGenerar.setBackground(new Color(0,153,255));
    }//GEN-LAST:event_jLabel17MouseExited

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        try{
          Runtime.getRuntime().exec("cmd /c start \\\\100.100.200.10\\bd\\Cotizacion\\"+OC.getText()+".pdf");
          Runtime.getRuntime().exec("cmd /c close");
       }catch(IOException  e){
              JOptionPane.showMessageDialog(this, e);
          }
    }//GEN-LAST:event_jMenuItem4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel OC;
    private com.toedter.calendar.JDateChooser Valido;
    private javax.swing.JPanel btnAbrir;
    private javax.swing.JButton btnAgregarC;
    private javax.swing.JPanel btnGenerar;
    private javax.swing.JPanel btnGuardar;
    private javax.swing.JPanel btnLimpiar;
    private javax.swing.JPanel btnSalir;
    private javax.swing.JLabel imagen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JComboBox<String> jcbMoneda;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblUrl;
    private javax.swing.JTable tblCaracteristicas;
    private javax.swing.JTable tblPropuestas;
    private javax.swing.JButton txtAgregarP;
    private javax.swing.JTextField txtCaracteristicas;
    private javax.swing.JTextField txtCosto;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtDirigido;
    private javax.swing.JTextField txtEmpresa;
    private javax.swing.JTextField txtIngeniero;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextArea txtNotas;
    private javax.swing.JTextField txtPropuesta;
    private javax.swing.JTextField txtProyecto;
    private javax.swing.JTextField txtTiempo;
    // End of variables declaration//GEN-END:variables
}
