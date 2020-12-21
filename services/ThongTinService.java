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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.HoKhauModel;
/**
 *
 * @author dung
 */
public class ThongTinService {
    
    public List<HoKhauModel> getListHoKhau(){
        List<HoKhauModel> list = new ArrayList<>();
        try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "SELECT ho_khau.ID_HK, nhan_khau.HoTen as TenChuHo , ho_khau.ID_ChuHo, ho_khau.DiaChi, COUNT(la_thanh_vien_cua_ho.ID_NK) as SoThanhVien\n" +
                            "FROM ho_khau,nhan_khau,la_thanh_vien_cua_ho\n" +
                            "WHERE ho_khau.ID_ChuHo = nhan_khau.ID_NK and ho_khau.ID_HK = la_thanh_vien_cua_ho.ID_HK\n" +
                            "GROUP BY ho_khau.ID_HK";
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                HoKhauModel hk = new HoKhauModel();
                hk.setID_HK(rs.getString("ID_HK"));
                hk.setTenChuHo(rs.getString("TenChuHo"));
                hk.setID_ChuHo(rs.getString("ID_ChuHo"));
                hk.setDiaChi(rs.getString("DiaChi"));
                hk.setSoThanhVien(rs.getInt("SoThanhVien"));
                list.add(hk);
            }
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return list;
    }     
    
    public String totalHoKhau(){
        String total = "0";
        try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "SELECT COUNT(ho_khau.ID_HK) as TongSoHo FROM ho_khau" ;
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                total = rs.getString("TongSoHo");
            }
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return total;
    } 

    public String totalNhanKhau(){
        String total = "0";
        try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "SELECT COUNT(nhan_khau.ID_NK) as TongSoNguoi FROM nhan_khau" ;
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                total = rs.getString("TongSoNguoi");
            }
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return total;
    }      
    
    public String totalDongPhiVS(){
        String total = null;
        try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "SELECT SUM(dong_phi_vs.Tien_Q1+dong_phi_vs.Tien_Q2+dong_phi_vs.Tien_Q3+dong_phi_vs.Tien_Q4) as TongSoTien\n" +
                            "FROM dong_phi_vs" ;
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                total = rs.getString("TongSoTien");
            }
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        if (total == null) total = "0";
        return total;
    }  

    public String totalDongGop(){
        String total = null;
        try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "SELECT SUM(dong_gop.So_tien) as TongSoTien\n" +
                            "FROM dong_gop";
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                total = rs.getString("TongSoTien");
            }
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        if (total == null) total = "0";
        return total;
    }     
}
