package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import models.DongPhiVSModel;

public class DongPhiVSService {
   
    //lay ra danh sach dong phi ve sinh
    public List<DongPhiVSModel> getListDongPhiVS() {
        List<DongPhiVSModel> list = new ArrayList<>();
        try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "SELECT nhan_khau.HoTen as TenChuHo , dong_phi_vs.*\n" +
                            "FROM ho_khau , dong_phi_vs , nhan_khau\n" +
                            "WHERE ho_khau.ID_HK = dong_phi_vs.ID_HK and ho_khau.ID_ChuHo = nhan_khau.ID_NK\n" +
                            "ORDER BY dong_phi_vs.Nam DESC";
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                DongPhiVSModel DongPhiVS = new DongPhiVSModel();
                DongPhiVS.setID_HK(rs.getInt("ID_HK"));
                DongPhiVS.setTenChuHo(rs.getString("TenChuHo"));
                DongPhiVS.setNam(rs.getInt("Nam"));
                DongPhiVS.setTien_Q1(rs.getInt("Tien_Q1"));
                DongPhiVS.setTien_Q2(rs.getInt("Tien_Q2"));
                DongPhiVS.setTien_Q3(rs.getInt("Tien_Q3"));
                DongPhiVS.setTien_Q4(rs.getInt("Tien_Q4"));
                list.add(DongPhiVS);
            }
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return list;
    }
    
    public List<DongPhiVSModel> getListDongPhiVS(String nam, String ID_HK) {
        List<DongPhiVSModel> list = new ArrayList<>();
        try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "SELECT nhan_khau.HoTen as TenChuHo , dong_phi_vs.*\n" +
                            "FROM ho_khau , dong_phi_vs , nhan_khau\n" +
                            "WHERE ho_khau.ID_HK = dong_phi_vs.ID_HK and ho_khau.ID_ChuHo = nhan_khau.ID_NK ";
            if (!nam.equals("Tat ca nam")) query += " and dong_phi_vs.Nam = "+nam;
            if (!ID_HK.equals("Tat ca cac ho") && !ID_HK.equals("")) query += " and ho_khau.ID_HK = "+ID_HK;
            query += " \nORDER BY dong_phi_vs.Nam DESC";
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                DongPhiVSModel DongPhiVS = new DongPhiVSModel();
                DongPhiVS.setID_HK(rs.getInt("ID_HK"));
                DongPhiVS.setTenChuHo(rs.getString("TenChuHo"));
                DongPhiVS.setNam(rs.getInt("Nam"));
                DongPhiVS.setTien_Q1(rs.getInt("Tien_Q1"));
                DongPhiVS.setTien_Q2(rs.getInt("Tien_Q2"));
                DongPhiVS.setTien_Q3(rs.getInt("Tien_Q3"));
                DongPhiVS.setTien_Q4(rs.getInt("Tien_Q4"));
                list.add(DongPhiVS);
            }
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return list;
    }
    
    //sua mot dong trong danh sach dong phi ve sinh
    public void setDongPhiVS(DongPhiVSModel dongPhiVS,int quy){
        try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String so_tien = null;
            switch(quy){
                case 1 -> so_tien = dongPhiVS.getTien_Q1().toString();
                case 2 -> so_tien = dongPhiVS.getTien_Q2().toString();
                case 3 -> so_tien = dongPhiVS.getTien_Q3().toString();
                case 4 -> so_tien = dongPhiVS.getTien_Q4().toString();
            }
            String query = "UPDATE dong_phi_vs SET Tien_Q"+quy+" = " + so_tien + " WHERE dong_phi_vs.ID_HK = " + dongPhiVS.getID_HK() + " AND dong_phi_vs.Nam = " + dongPhiVS.getNam();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
        } 
    }
    
    //them moi mot dong trong danh sach dong phi ve sinh
    public void addNewDongPhiVS(Connection connection,DongPhiVSModel dongPhiVS){
        try {
            String query = "INSERT INTO dong_phi_vs (ID_HK, Nam, Tien_Q1, Tien_Q2, Tien_Q3, Tien_Q4)\n"
                    + " VALUES ("+dongPhiVS.getID_HK()+" , "+dongPhiVS.getNam()+" , "+dongPhiVS.getTien_Q1()+" , "+dongPhiVS.getTien_Q2()+" , "+dongPhiVS.getTien_Q3()+" , "+dongPhiVS.getTien_Q4()+")";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }  
    }
    
    public void addNewNam(String nam){
        List<String> listNam = this.getListNamString();
        if (listNam.contains(nam))
            JOptionPane.showMessageDialog(null,"Da co nam " + nam + " trong DataBase!");
        else try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "INSERT INTO danh_sach_nam (Nam) VALUES ("+nam+")";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            query = "SELECT * FROM ho_khau";
            preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                DongPhiVSModel dongPhiVS = new DongPhiVSModel();
                dongPhiVS.setID_HK(rs.getInt("ID_HK"));
                dongPhiVS.setNam(Integer.parseInt(nam));
                dongPhiVS.setTien_Q1(0);
                dongPhiVS.setTien_Q2(0);
                dongPhiVS.setTien_Q3(0);
                dongPhiVS.setTien_Q4(0);
                addNewDongPhiVS(connection,dongPhiVS);
            }
            //

            preparedStatement.close();
            connection.close();
         } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
        } 
    }
    
    public void deleteNam(String nam){
        List<String> listNam = this.getListNamString();
        if (listNam.contains(nam))
               try {
                   Connection connection = MysqlConnection.getMysqlConnection();
                   //
                   String query = "DELETE FROM dong_phi_vs WHERE dong_phi_vs.Nam = '"+nam+"';";
                   PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
                   preparedStatement.executeUpdate();
                   //
                   query = "DELETE FROM danh_sach_nam WHERE danh_sach_nam.Nam = '"+nam+"';";
                   preparedStatement = (PreparedStatement)connection.prepareStatement(query);
                   preparedStatement.executeUpdate();
                   //
                   preparedStatement.close();
                   connection.close();     
               }catch (ClassNotFoundException | SQLException e) {
                   System.out.println(e.getMessage());
               }
        else
           JOptionPane.showMessageDialog(null,"Khong co nam " + nam + " trong DataBase!");
    }
    
    public List<String> getListNamString(){
        List<String> listNam = new ArrayList<>();
        try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "SELECT * FROM danh_sach_nam ORDER BY Nam DESC ";
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) listNam.add(rs.getString("Nam"));
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return listNam;
    }
    
    @SuppressWarnings("unchecked")
    public void getListNam(JComboBox<String> cb){
        List<String> listNam = this.getListNamString();
        listNam.forEach(item -> {
           cb.addItem(item);
        });
   }
}
