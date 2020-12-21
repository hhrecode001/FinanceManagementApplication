/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.mysql.cj.jdbc.PreparedStatementWrapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import models.DongGopModel;
import models.SuKienModel;

/**
 *
 * @author dung
 */
public class DongGopService {
    
    //lay ra danh sach dong gop ung ho
    public List<DongGopModel> getListDongGop() {
        List<DongGopModel> list = new ArrayList<>();
        try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "SELECT ho_khau.ID_HK, nhan_khau.HoTen as TenChuHo , danh_sach_su_kien.ID_SK , danh_sach_su_kien.Ten_su_kien, dong_gop.So_tien \n"+
                            "FROM nhan_khau, ho_khau , danh_sach_su_kien , dong_gop \n"+
                            "WHERE ho_khau.ID_HK = dong_gop.ID_HK and danh_sach_su_kien.ID_SK = dong_gop.ID_SK and ho_khau.ID_ChuHo = nhan_khau.ID_NK and dong_gop.So_tien>0 \n"+
                            "ORDER BY danh_sach_su_kien.ID_SK DESC";
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                DongGopModel DongGop = new DongGopModel();
                DongGop.setID_HK(rs.getInt("ID_HK"));
                DongGop.setTenChuHo(rs.getString("TenChuHo"));
                DongGop.setID_SK(rs.getString("ID_SK"));
                DongGop.setTenSuKien(rs.getString("Ten_su_kien"));
                DongGop.setSoTien(rs.getInt("So_tien"));
                list.add(DongGop);
            }
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return list;
    }
    
    public void addNewDongGop(Connection connection,DongGopModel dongPhiVS){
        try {
            String query = "INSERT INTO dong_gop (ID_HK, ID_SK, So_tien)\n"
                    + " VALUES ("+dongPhiVS.getID_HK()+" , "+dongPhiVS.getID_SK()+" , "+dongPhiVS.getSoTien()+")";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
        }  
    }
    
    public void addSuKien(SuKienModel suKien){
        try{
            Connection connection = MysqlConnection.getMysqlConnection();
            String ID_SK;
            //them vao danh_sach_su_kien
            String query = "INSERT INTO danh_sach_su_kien (ID_SK, Ten_su_kien, Ngay_to_chuc) VALUES (NULL, '"+suKien.getTen_su_kien()+"', '"+suKien.getNgay_to_chuc()+"')";
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            //tim ra ID cua su kien hien tai
            query = "SELECT MAX(danh_sach_su_kien.ID_SK) as maxID FROM danh_sach_su_kien";
            preparedStatement = (PreparedStatement)connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            ID_SK = rs.getString("maxID");
            
            //them vao danh_sach_dong_gop
            query = "SELECT * FROM ho_khau";
            preparedStatement = (PreparedStatement)connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                DongGopModel dongGop = new DongGopModel();
                dongGop.setID_HK(rs.getInt("ID_HK"));
                dongGop.setID_SK(ID_SK);
                dongGop.setSoTien(0);
                addNewDongGop(connection,dongGop);
            }
            preparedStatement.close();
            connection.close();
            JOptionPane.showMessageDialog(null,"Da them thanh cong su kien "+suKien.getTen_su_kien());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,"Ngày bắt đầu không đúng định dạng YYYY-MM-DD");
        }
    }
    
    public List<String> getListSuKienString(){
        List<String> listSuKien = new ArrayList<>();
        try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "SELECT * FROM danh_sach_su_kien ORDER BY danh_sach_su_kien.ID_SK DESC ";
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) listSuKien.add(rs.getString("Ten_su_kien"));
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return listSuKien;
    }
    
    @SuppressWarnings("unchecked")
    public void getListSuKien(JComboBox<String> cb){
        List<String> listSuKien = this.getListSuKienString();
        listSuKien.forEach(item -> {
           cb.addItem(item);
        });
    }
    
    public List<SuKienModel> getListSuKien(){
        List<SuKienModel> list = new ArrayList<>();
        try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "SELECT * FROM danh_sach_su_kien ORDER BY danh_sach_su_kien.ID_SK DESC";
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                SuKienModel sk = new SuKienModel();
                sk.setID_SK(rs.getString("ID_SK"));
                sk.setTen_su_kien(rs.getString("Ten_su_kien"));
                sk.setNgay_to_chuc(rs.getString("Ngay_to_chuc"));
                list.add(sk);
            }
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return list;
    }      
    
    public void deleteSuKien(SuKienModel sk){
        try{
            Connection connection = MysqlConnection.getMysqlConnection();
            //xoa trong danh_sach_dong_gop
            String query = "DELETE FROM dong_gop WHERE dong_gop.ID_SK = "+sk.getID_SK();
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            //xoa khoi danh_sach_su_kien
            query = "DELETE FROM danh_sach_su_kien WHERE danh_sach_su_kien.ID_SK = "+sk.getID_SK();
            preparedStatement = (PreparedStatement)connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
        } 
    }
    
    public void editDongGop(DongGopModel dongGop){
        try{
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "UPDATE dong_gop SET So_tien = '"+dongGop.getSoTien()+"' WHERE dong_gop.ID_HK = "+dongGop.getID_HK()+" AND dong_gop.ID_SK = '"+dongGop.getID_SK()+"'";
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareCall(query);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
        } 
    }
}
