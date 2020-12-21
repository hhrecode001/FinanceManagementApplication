/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import models.HoKhauModel;
import services.ThongTinService;
import utility.ClassTableModel;
import views.DanhSachHoKhauFrame;

/**
 *
 * @author hoang
 */
public class ThongTinController {
    
    private ThongTinService thongTinService = new ThongTinService();
    private JPanel jpnView;
    private List<HoKhauModel> listHoKhau = null;
    private ClassTableModel classTableModel = null;
    private DanhSachHoKhauFrame danhSachHoKhauFrame = null;
    private final String[] COLUMNS = new String[]{"ID_HK", "TenChuHo", "ID_ChuHo", "DiaChi", "SoThanhVien", "SoTien"};
    
    public ThongTinController(){
        this.jpnView = jpnView;
        classTableModel = new ClassTableModel();
        listHoKhau = thongTinService.getListHoKhau();
    }
    
    public void updateThongTin(JLabel soNhanKhau, JLabel soHoKhau, JLabel tienDongPhiVS, JLabel tienDongGop){
        soNhanKhau.setText("Số nhân khẩu: "+thongTinService.totalNhanKhau());
        soHoKhau.setText("Số hộ khẩu: "+thongTinService.totalHoKhau());
        tienDongPhiVS.setText("Tổng tiền phí vệ sinh: "+thongTinService.totalDongPhiVS()+" đồng");
        tienDongGop.setText("Tổng tiền đóng góp: "+thongTinService.totalDongGop()+" đồng");
    }
    
    public void listHoKhauFrame(JFrame parentFrame){
        danhSachHoKhauFrame = new DanhSachHoKhauFrame(parentFrame, this);
        danhSachHoKhauFrame.setLocationRelativeTo(null);
        danhSachHoKhauFrame.setVisible(true);
    }
    
    public JScrollPane getDataTable(){
        List<HoKhauModel> listItem = new ArrayList<>();
        this.listHoKhau.forEach(DongGop -> {
           listItem.add(DongGop); 
        });
        DefaultTableModel model = classTableModel.setTableHoKhau(listItem,COLUMNS);
        JTable table = new JTable(model){
            @Override
            public boolean editCellAt(int row, int column, EventObject e) {
                return false;
            }
        };
        // thiet ke bang
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(100, 50));
        table.setRowHeight(50);
        table.validate();
        table.repaint();
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        //kich thuoc cot
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(160);
        table.getColumnModel().getColumn(2).setPreferredWidth(70);
        table.getColumnModel().getColumn(3).setPreferredWidth(300);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.addMouseListener(new MouseAdapter() {
        }); 
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);
        
        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(table);
        scroll.setPreferredSize(new Dimension(640, 400));
        return scroll;
    }
}
