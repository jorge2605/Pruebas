package Conexiones;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class ConexionChat {
    
    Connection con = null;
    
    public Connection getConnection() throws ClassNotFoundException, SQLException{
    con = null;
    Class.forName("com.mysql.jdbc.Driver");
    con = (Connection) DriverManager.getConnection("jdbc:mysql://192.168.100.40:3306/chat?autoReconnect=true&useSSL=false","Jorge","123456789Aa.");
//    con = (Connection) DriverManager.getConnection("jdbc:mysql://192.168.100.14:3306/chat?autoReconnect=true&useSSL=false","Jorge","123456789Aa.");
    return con;
    }
    Statement createStatement(){
    throw new UnsupportedOperationException("NO SOPORTADO");
    
    }
}

