package services;

import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author dung
 */
public class MysqlConnection {
    public static Connection getMysqlConnection() throws SQLException, ClassNotFoundException {
        String hostName = "localhost";
        String dbName = "nmcnpm";
        String userName = "root";
        String password = "";
        return getMysqlConnection(hostName, dbName, userName, password);
    }
    
    public static Connection getMysqlConnection(String hostName, String dbName, String userName, String password) 
        throws SQLException, ClassNotFoundException{
        //Class.forName("com.mysql.jdbc.Driver");
        String connectionUrl = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?useUnicode=true&characterEncoding=utf-8";
        Connection conn = null;
        try{
            conn = (Connection)DriverManager.getConnection(connectionUrl, userName, password);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Không kết nối được với hệ thống!");
        }
        return conn;
    }
}
