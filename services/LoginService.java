/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author dung
 */
public class LoginService {
    
    public boolean tryLogin(String user, String password) {
        boolean isLogin = false;
        try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "SELECT * FROM users";
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                if (user.equals(rs.getString("user")) && password.equals(rs.getString("password")) ) isLogin = true;
            }
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return isLogin;
    }
    
}
