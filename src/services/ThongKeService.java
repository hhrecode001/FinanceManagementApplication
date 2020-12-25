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
import models.DongGopModel;
import models.DongPhiVSModel;
import models.SuKienModel;

/**
 *
 * @author dung
 */
public class ThongKeService {
    
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
    
    public List<DongGopModel> getListDongGop(SuKienModel sk){
        List<DongGopModel> list = new ArrayList<>();
        try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "SELECT ho_khau.ID_HK, nhan_khau.HoTen as TenChuHo , danh_sach_su_kien.ID_SK , danh_sach_su_kien.Ten_su_kien, dong_gop.So_tien \n"+
                            "FROM nhan_khau, ho_khau , danh_sach_su_kien , dong_gop \n"+
                            "WHERE ho_khau.ID_HK = dong_gop.ID_HK and danh_sach_su_kien.ID_SK = dong_gop.ID_SK and ho_khau.ID_ChuHo = nhan_khau.ID_NK and dong_gop.So_tien>0 \n"+
                            " and danh_sach_su_kien.ID_SK = "+sk.getID_SK()+" \n"+
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return list;
    }
    
    public String getReportedDongGop(SuKienModel sk){
        String reported = "Khong co dong gop nao duoc ghi nhan";
        try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "SELECT COUNT(dong_gop.ID_HK) as TongSoHo, SUM(dong_gop.So_tien) as TongSoTien \n" +
                            "FROM dong_gop \n" +
                            "WHERE dong_gop.ID_SK = "+sk.getID_SK();
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                reported = "Co tat ca "+rs.getString("TongSoHo")+" ho dong gop ung ho voi tong so tien la "+rs.getString("TongSoTien")+" dong.";
            }
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return reported;    
    }
    
    public List<String> getListNam(){
        List<String> listNam = new ArrayList<>();
        try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "SELECT * FROM danh_sach_nam ORDER BY Nam DESC ";
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) listNam.add(rs.getString("Nam"));
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return listNam;
    }
    
    public List<DongPhiVSModel> getListDongPhiVS(String nam, String quy) {
        List<DongPhiVSModel> list = new ArrayList<>();
        try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "SELECT nhan_khau.HoTen as TenChuHo , dong_phi_vs.*\n"+
                    "FROM ho_khau , dong_phi_vs , nhan_khau \nWHERE ho_khau.ID_HK = dong_phi_vs.ID_HK and ho_khau.ID_ChuHo = nhan_khau.ID_NK \n";
            if (!nam.equals("TatCaCacNam")) query += " and dong_phi_vs.Nam = "+nam+" \n";
            if (!quy.equals("TatCaCacQuy")) query += " and Tien_Q"+quy+" > 0 \n";
            else query += " and (dong_phi_vs.Tien_Q1+dong_phi_vs.Tien_Q2+dong_phi_vs.Tien_Q3+dong_phi_vs.Tien_Q4) > 0 \n";
            query += "ORDER BY dong_phi_vs.Nam DESC";
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
    
    public String getReportedDongPhiVS(String nam, String quy){
        String reported = "Khong co dong phi ve sinh duoc ghi nhan";
        String selectedQuy;
        if (quy.equals("TatCaCacQuy")) selectedQuy = "dong_phi_vs.Tien_Q1+dong_phi_vs.Tien_Q2+dong_phi_vs.Tien_Q3+dong_phi_vs.Tien_Q4";
        else selectedQuy = "dong_phi_vs.Tien_Q"+quy;
        try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "SELECT COUNT(DISTINCT dong_phi_vs.ID_HK) as TongSoHo, SUM("+selectedQuy+") as TongSoTien\n" +
                        "FROM ho_khau , dong_phi_vs , nhan_khau \nWHERE ho_khau.ID_HK = dong_phi_vs.ID_HK and ho_khau.ID_ChuHo = nhan_khau.ID_NK \n";
            if (!nam.equals("TatCaCacNam")) query += " and dong_phi_vs.Nam = "+nam+" \n";
            if (!quy.equals("TatCaCacQuy")) query += " and Tien_Q"+quy+" > 0 \n";
            else query += " and (dong_phi_vs.Tien_Q1+dong_phi_vs.Tien_Q2+dong_phi_vs.Tien_Q3+dong_phi_vs.Tien_Q4) > 0 \n";
            query += "ORDER BY dong_phi_vs.Nam DESC";
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                reported = "Da co "+rs.getString("TongSoHo")+" ho dong tien ve sinh";
                if (quy.equals("TatCaCacQuy")) reported += " tat ca cac quy";
                else reported += " quy "+quy;
                if (nam.equals("TatCaCacNam")) reported += " tat ca cac nam";
                else reported += " nam "+nam;
                String tongSoTien = rs.getString("TongSoTien");// de phong truong hop TongSoTien = null;
                if (tongSoTien==null) tongSoTien ="0.0";
                reported += " voi tong so tien la "+tongSoTien+" dong.";
            }
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return reported;
    }
    
    public List<DongPhiVSModel> getListDongPhiVS(String ID_HK) {
        List<DongPhiVSModel> list = new ArrayList<>();
        try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "SELECT nhan_khau.HoTen as TenChuHo , dong_phi_vs.*\n" +
                            "FROM ho_khau , dong_phi_vs , nhan_khau\n" +
                            "WHERE ho_khau.ID_HK = dong_phi_vs.ID_HK and ho_khau.ID_ChuHo = nhan_khau.ID_NK " +
                            " and ho_khau.ID_HK = "+ID_HK;
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
    
    public List<DongGopModel> getListDongGop(String ID_HK) {
        List<DongGopModel> list = new ArrayList<>();
        try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "SELECT ho_khau.ID_HK, nhan_khau.HoTen as TenChuHo , danh_sach_su_kien.ID_SK , danh_sach_su_kien.Ten_su_kien, dong_gop.So_tien \n"+
                            "FROM nhan_khau, ho_khau , danh_sach_su_kien , dong_gop \n"+
                            "WHERE ho_khau.ID_HK = dong_gop.ID_HK and danh_sach_su_kien.ID_SK = dong_gop.ID_SK and ho_khau.ID_ChuHo = nhan_khau.ID_NK and dong_gop.So_tien>0 and ho_khau.ID_HK = "+ID_HK+" \n"+
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return list;
    }
}
