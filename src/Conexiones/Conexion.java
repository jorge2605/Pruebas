package Conexiones;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;

public class Conexion {
    
    Connection con = null;
    
    public Connection getConnection(){
    con = null;
    try{
        Class.forName("com.mysql.jdbc.Driver");
        con = (Connection) DriverManager.getConnection("jdbc:mysql://serverdell:3306/towi?autoReconnect=true&useSSL=false","Jorge","123456789Aa.");
//        con = (Connection) DriverManager.getConnection("jdbc:mysql://192.168.100.14:3306/towi?autoReconnect=true&useSSL=false","Jorge","123456789Aa.");
    
    }catch(Exception e){
        System.out.println("ERROR AL CONECTARSE"+e);
    }
    return con;
    }
    Statement createStatement(){
    throw new UnsupportedOperationException("NO SOPORTADO");
    
    }
}

