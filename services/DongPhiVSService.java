package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import models.DongPhiVSModel;

/**
 *
 * @author Hai
 */
public class DongPhiVSService {
   
     // lay danh sach 10 nhan khau moi duoc them vao
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
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
    // them moi 
    public boolean setDongPhiVS(int iD_HK, int nam, int quy, int so_tien) throws ClassNotFoundException, SQLException{
        Connection connection = MysqlConnection.getMysqlConnection();
        String tien_quy = "Tien_Q" + quy;
        String query = "UPDATE dong_phi_vs SET " + tien_quy + " = " + so_tien + " WHERE dong_phi_vs.ID_HK = " + iD_HK + " AND dong_phi_vs.Nam = " + nam;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return true;
    }
    
    public void addNewDongPhiVS(Connection connection, String ID_HK, String Nam, String Tien_Q1, String Tien_Q2, String Tien_Q3, String Tien_Q4){
        try {
            String query = "INSERT INTO dong_phi_vs (ID_HK, Nam, Tien_Q1, Tien_Q2, Tien_Q3, Tien_Q4) VALUES ("+ID_HK+" , "+Nam+" , "+Tien_Q1+" , "+Tien_Q2+" , "+Tien_Q3+" , "+Tien_Q4+")";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }  
    }
    
    public void addNewNam(String nam) throws SQLException, ClassNotFoundException{
        List<String> listNam = this.getListNamString();
        if (listNam.contains(nam))
            JOptionPane.showMessageDialog(null,"Da co nam " + nam + " trong DataBase!");
        else try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "SELECT * FROM ho_khau";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) addNewDongPhiVS(connection, String.valueOf(rs.getInt("ID_HK")),nam,"0","0","0","0");
            //
            query = "INSERT INTO danh_sach_nam (Nam) VALUES ("+nam+")";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        } 
    }
    
    public void deleteNam(String nam){
        List<String> listNam = this.getListNamString();
        if (listNam.contains(nam))
               try {
                   Connection connection = MysqlConnection.getMysqlConnection();
                   String query = "DELETE FROM danh_sach_nam WHERE danh_sach_nam.Nam = '"+nam+"';";
                   PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query);
                   preparedStatement.executeUpdate();
                   //
                   query = "DELETE FROM dong_phi_vs WHERE dong_phi_vs.Nam = '"+nam+"';";
                   preparedStatement = (PreparedStatement)connection.prepareStatement(query);
                   preparedStatement.executeUpdate();
                   preparedStatement.close();
                   connection.close();     
               }catch (SQLException e) {
                   System.out.println(e.getMessage());
               } catch (ClassNotFoundException ex) {
                   Logger.getLogger(DongPhiVSService.class.getName()).log(Level.SEVERE, null, ex);
               }
        else
           JOptionPane.showMessageDialog(null,"Khong co nam " + nam + " trong DataBase!");
    }

    /**
     *
     * @return
     */
    
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
       return;
   }
    
    /*
     * Ham sử lý ngoại lệ : thông báo ra lỗi nhận được
     */
    private void exceptionHandle(String message) {
        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.ERROR_MESSAGE);
    }
}
