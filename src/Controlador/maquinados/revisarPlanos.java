package Controlador.maquinados;

import Conexiones.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import pruebas.Inicio1;

public class revisarPlanos {
    
    public String seleccion;
    
    public Stack checarRevisionPlano(String plano){
        Stack<String> revision = new Stack<>();
        if(plano.contains("/")){
            revision.push(plano.substring(plano.lastIndexOf("/")+1, plano.length()));
            if(getPlano(plano.substring(0, (plano.lastIndexOf("/"))) ,revision.firstElement())){
                return revision;
            }else{
                return null;
            }
        }else{
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Plano, Revision from Planos where Plano like '"+plano+"'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                revision.push(rs.getString("Revision"));
            }
            if(revision.isEmpty()){
                JOptionPane.showMessageDialog(null, "NO EXISTE ESTE PLANO","ERROR",JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        return revision;
        }
    }
    
    public boolean getPlano(String plano, String revision){
        boolean band = false;
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Plano, Revision from planos where Plano like '" + plano + "'";
            ResultSet rs = st.executeQuery(sql);
            String plan = null;
            String rev = null;
            while(rs.next()){
                plan = rs.getString("Plano");
                rev = rs.getString("Revision");
            }
            if(plan != null){
                if(rev == null ? revision == null : rev.equals(revision)){
                    band = true;
                }else{
                    JOptionPane.showMessageDialog(null, "No se encontro la Revision","Error",JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null, "No se encontro este Plano","Error",JOptionPane.ERROR_MESSAGE);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error: "+e,"Error",JOptionPane.ERROR_MESSAGE);
        }
        return band;
    }
    
    public String buscar(String plano) {
        String datos[] = new String[1000];
        datos[3] = null;
        try {
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

            String sql = "select * from Planos where Plano like '" + plano + "'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int cont = 0;
                datos[0] = rs.getString("Prioridad");
                datos[1] = rs.getString("Plano");
                datos[2] = rs.getString("Proyecto");
                String acabados[] = new String[10];
                String calidad[] = new String[10];
                String cnc[] = new String[10];
                String fresa[] = new String[10];
                String cortes[] = new String[10];
                String torno[] = new String[10];
                String trata[] = new String[10];
                String id = datos[1];

                String sq = "select * from Calidad where Proyecto like '" + datos[1] + "'";
                ResultSet r = st1.executeQuery(sq);
                String sql1 = "select * from Acabados where Proyecto like '" + datos[1] + "'";
                ResultSet rs1 = st2.executeQuery(sql1);
                String sql2 = "select * from CNC where Proyecto like '" + datos[1] + "'";
                ResultSet rs2 = st3.executeQuery(sql2);
                String sql3 = "select * from Fresadora where Proyecto like '" + datos[1] + "'";
                ResultSet rs3 = st4.executeQuery(sql3);
                String sql5 = "select * from Torno where Proyecto like '" + datos[1] + "'";
                ResultSet rs5 = st5.executeQuery(sql5);
                String sql4 = "select * from Datos where Proyecto like '" + datos[1] + "'";
                ResultSet rs4 = st6.executeQuery(sql4);
                String sql6 = "select * from Trata where Proyecto like '" + datos[1] + "'";
                ResultSet rs6 = st7.executeQuery(sql6);
                while (r.next()) {
                    calidad[0] = r.getString("Id");
                    calidad[1] = r.getString("Proyecto");
                    calidad[2] = r.getString("Plano");
                    calidad[3] = r.getString("Terminado");
                    calidad[4] = r.getString("Tratamiento");
                    calidad[5] = r.getString("Prioridad");
                    calidad[6] = r.getString("Revision");
                }

                while (rs1.next()) {
                    acabados[0] = rs1.getString("Id");
                    acabados[1] = rs1.getString("Proyecto");
                    acabados[2] = rs1.getString("Plano");
                    acabados[3] = rs1.getString("Terminado");
                    acabados[5] = rs1.getString("Prioridad");
                    acabados[6] = rs1.getString("Revision");
                }
                while (rs2.next()) {
                    cnc[0] = rs2.getString("Id");
                    cnc[1] = rs2.getString("Proyecto");
                    cnc[2] = rs2.getString("Plano");
                    cnc[3] = rs2.getString("Terminado");
                    cnc[5] = rs2.getString("Prioridad");
                    cnc[6] = rs2.getString("Revision");
                }
                while (rs3.next()) {
                    fresa[0] = rs3.getString("Id");
                    fresa[1] = rs3.getString("Proyecto");
                    fresa[2] = rs3.getString("Plano");
                    fresa[3] = rs3.getString("Terminado");
                    fresa[5] = rs3.getString("Prioridad");
                    fresa[6] = rs3.getString("Revision");
                }
                while (rs4.next()) {
                    cortes[0] = rs4.getString("Id");
                    cortes[1] = rs4.getString("Proyecto");
                    cortes[2] = rs4.getString("Plano");
                    cortes[3] = rs4.getString("Terminado");
                    cortes[5] = rs4.getString("Prioridad");
                    cortes[6] = rs4.getString("Revision");
                }
                while (rs5.next()) {
                    torno[0] = rs5.getString("Id");
                    torno[1] = rs5.getString("Proyecto");
                    torno[2] = rs5.getString("Plano");
                    torno[3] = rs5.getString("Terminado");
                    torno[5] = rs5.getString("Prioridad");
                    torno[6] = rs5.getString("Revision");
                }
                while (rs6.next()) {
                    trata[0] = rs6.getString("Id");
                    trata[1] = rs6.getString("Proyecto");
                    trata[2] = rs6.getString("Plano");
                    trata[3] = rs6.getString("Terminado");
                    trata[5] = rs6.getString("Prioridad");
                    trata[6] = rs6.getString("Revision");
                }

                if (id.equals(cortes[1]) && cortes[3].equals("NO")) {
                    datos[3] = "CORTES";
                } else if (id.equals(cnc[1]) && cnc[3].equals("NO")) {
                    datos[3] = "CNC";
                } else if (id.equals(fresa[1]) && fresa[3].equals("NO")) {
                    datos[3] = "FRESADORA";
                } else if (id.equals(torno[1]) && torno[3].equals("NO")) {
                    datos[3] = "TORNO";
                } else if (id.equals(acabados[1]) && acabados[3].equals("NO")) {
                    datos[3] = "ACABADOS";
                } else if (id.equals(trata[1]) && trata[3].equals("NO")) {
                    datos[3] = "TRATAMIENTO";
                } else if (id.equals(trata[1]) && trata[3].equals("TERMINADO")) {
                    datos[3] = "TERMINADO TRATAMIENTO";
                } else if (id.equals(calidad[1]) && calidad[3].equals("NO")) {
                    datos[3] = "CALIDAD";
                } else if (id.equals(calidad[1]) && calidad[3].equals("SI") && "NO".equals(calidad[4])) {
                    datos[3] = "TERMINADO CALIDAD";
                } else {
                    datos[3] = "LIBERACION";
                }

                cont += 1;
            }
            if(datos[3] == null){
//                setRevisionBD();
//                return buscar(plano);
                return null;
            }
        } catch (SQLException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE,null,e);
            JOptionPane.showMessageDialog(null, "NO SE PUEDEN MOSTRAR LOS DATOS" + " " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return datos[3];
    }
    
    public String buscar(String plano, String revision) {
        String datos[] = new String[1000];
        datos[3] = null;
        try {
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
            setRevisionBD();

            String sql = "select * from Planos where Plano like '" + plano + "' and Revision like '" + revision + "'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int cont = 0;
                datos[0] = rs.getString("Prioridad");
                datos[1] = rs.getString("Plano");
                datos[2] = rs.getString("Proyecto");
                String acabados[] = new String[10];
                String calidad[] = new String[10];
                String cnc[] = new String[10];
                String fresa[] = new String[10];
                String cortes[] = new String[10];
                String torno[] = new String[10];
                String trata[] = new String[10];
                String id = datos[1];

                String sq = "select * from Calidad where Proyecto like '" + datos[1] + "' and Revision like '" + revision + "'";
                ResultSet r = st1.executeQuery(sq);
                String sql1 = "select * from Acabados where Proyecto like '" + datos[1] + "' and Revision like '" + revision + "'";
                ResultSet rs1 = st2.executeQuery(sql1);
                String sql2 = "select * from CNC where Proyecto like '" + datos[1] + "' and Revision like '" + revision + "'";
                ResultSet rs2 = st3.executeQuery(sql2);
                String sql3 = "select * from Fresadora where Proyecto like '" + datos[1] + "' and Revision like '" + revision + "'";
                ResultSet rs3 = st4.executeQuery(sql3);
                String sql5 = "select * from Torno where Proyecto like '" + datos[1] + "' and Revision like '" + revision + "'";
                ResultSet rs5 = st5.executeQuery(sql5);
                String sql4 = "select * from Datos where Proyecto like '" + datos[1] + "' and Revision like '" + revision + "'";
                ResultSet rs4 = st6.executeQuery(sql4);
                String sql6 = "select * from Trata where Proyecto like '" + datos[1] + "' and Revision like '" + revision + "'";
                ResultSet rs6 = st7.executeQuery(sql6);
                while (r.next()) {
                    calidad[0] = r.getString("Id");
                    calidad[1] = r.getString("Proyecto");
                    calidad[2] = r.getString("Plano");
                    calidad[3] = r.getString("Terminado");
                    calidad[4] = r.getString("Tratamiento");
                    calidad[5] = r.getString("Prioridad");
                }

                while (rs1.next()) {
                    acabados[0] = rs1.getString("Id");
                    acabados[1] = rs1.getString("Proyecto");
                    acabados[2] = rs1.getString("Plano");
                    acabados[3] = rs1.getString("Terminado");
                    acabados[5] = rs1.getString("Prioridad");
                }
                while (rs2.next()) {
                    cnc[0] = rs2.getString("Id");
                    cnc[1] = rs2.getString("Proyecto");
                    cnc[2] = rs2.getString("Plano");
                    cnc[3] = rs2.getString("Terminado");
                    cnc[5] = rs2.getString("Prioridad");
                }
                while (rs3.next()) {
                    fresa[0] = rs3.getString("Id");
                    fresa[1] = rs3.getString("Proyecto");
                    fresa[2] = rs3.getString("Plano");
                    fresa[3] = rs3.getString("Terminado");
                    fresa[5] = rs3.getString("Prioridad");
                }
                while (rs4.next()) {
                    cortes[0] = rs4.getString("Id");
                    cortes[1] = rs4.getString("Proyecto");
                    cortes[2] = rs4.getString("Plano");
                    cortes[3] = rs4.getString("Terminado");
                    cortes[5] = rs4.getString("Prioridad");
                }
                while (rs5.next()) {
                    torno[0] = rs5.getString("Id");
                    torno[1] = rs5.getString("Proyecto");
                    torno[2] = rs5.getString("Plano");
                    torno[3] = rs5.getString("Terminado");
                    torno[5] = rs5.getString("Prioridad");
                }
                while (rs6.next()) {
                    trata[0] = rs6.getString("Id");
                    trata[1] = rs6.getString("Proyecto");
                    trata[2] = rs6.getString("Plano");
                    trata[3] = rs6.getString("Terminado");
                    trata[5] = rs6.getString("Prioridad");
                }

                if (id.equals(cortes[1]) && cortes[3].equals("NO")) {
                    datos[3] = "CORTES";
                } else if (id.equals(cnc[1]) && cnc[3].equals("NO")) {
                    datos[3] = "CNC";
                } else if (id.equals(fresa[1]) && fresa[3].equals("NO")) {
                    datos[3] = "FRESADORA";
                } else if (id.equals(torno[1]) && torno[3].equals("NO")) {
                    datos[3] = "TORNO";
                } else if (id.equals(acabados[1]) && acabados[3].equals("NO")) {
                    datos[3] = "ACABADOS";
                } else if (id.equals(trata[1]) && trata[3].equals("NO")) {
                    datos[3] = "TRATAMIENTO";
                } else if (id.equals(trata[1]) && trata[3].equals("TERMINADO")) {
                    datos[3] = "TERMINADO TRATAMIENTO";
                } else if (id.equals(calidad[1]) && calidad[3].equals("NO")) {
                    datos[3] = "CALIDAD";
                } else if (id.equals(calidad[1]) && calidad[3].equals("SI") && "NO".equals(calidad[4])) {
                    datos[3] = "TERMINADO CALIDAD";
                } else {
                    datos[3] = "LIBERACION";
                }

                cont += 1;
            }
            if(datos[3] == null){
                setRevisionBD();
                return buscar(plano,revision);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "NO SE PUEDEN MOSTRAR LOS DATOS" + " " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return datos[3];
    }
    
    public void setRevisionBD(){
        setRevision("datos");
        setRevision("fresadora");
        setRevision("torno");
        setRevision("cnc");
        setRevision("acabados");
        setRevision("calidad");
    }
    
    public void setRevision(String bd){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from "+bd+" where Revision is null and Revision = ''";
            ResultSet rs = st.executeQuery(sql);
            String sql2 = "update "+bd+" set Revision = ? where Proyecto = ?";
            PreparedStatement pst = con.prepareStatement(sql2);
            while(rs.next()){
                String plano = rs.getString("Proyecto");
                String rev = rs.getString("Revision");
                System.out.println(bd);
                    if(rev != null){
                    String sql3 = "select Plano, Revision from Planos where Plano like '"+plano+"'";
                    System.out.println("Plano: "+plano+"  =====   BD: "+bd);
                    Statement st2 = con.createStatement();
                    ResultSet rs2 = st2.executeQuery(sql3);
                    String revision = "";
                    while(rs2.next()){
                        revision = rs2.getString("Revision");
                    }

                    pst.setString(1, revision);
                    pst.setString(2, plano);
                    int n = pst.executeUpdate();

                    if(n < 1){
                        JOptionPane.showMessageDialog(null, "Datos no actualizados: Revision - Plano: " + plano,"Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void enviarCortes(String bd, String plano, String numEmpleado,String proyecto, String revision){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            razon razon = new razon(null,true,this);
            String raz = razon.getRazon();
            //                                   1          2           3       4       5       6           7
            String sql = "insert into scrap (Proyecto, NumeroEmpleado, Fecha, Plano, Razon, Comentarios,Desde, Revision) values(?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = new Date();
            String fecha = sdf.format(d);
            
            pst.setString(1, plano);
            pst.setString(2, numEmpleado);
            pst.setString(3, fecha);
            pst.setString(4, proyecto);
            pst.setString(5, seleccion);
            pst.setString(6, raz);
            pst.setString(7, bd);
            pst.setString(8, revision);
            
            int n  = pst.executeUpdate();
            
            if(n < 1){
                JOptionPane.showMessageDialog(null, "NO SE ENVIO CORRECTAMENTE","ERROR",JOptionPane.ERROR_MESSAGE);
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public int intentosRealizadorPorPieza(String plano,String revision){
        int cont = 0;
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from scrap where Proyecto like '"+plano+"' and Revision like '"+revision+"'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                cont++;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error: "+e,"error",JOptionPane.ERROR_MESSAGE);
        }
        return cont;
    }
    
}
