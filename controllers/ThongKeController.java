/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import models.DongGopModel;
import models.DongPhiVSModel;
import models.SuKienModel;
import services.ThongKeService;
import utility.ClassTableModel;
import views.ThongKeDongGopFrame;
import views.ThongKeDongPhiVSFrame;

/**
 *
 * @author nhat
 */

public class ThongKeController{
    
    private JPanel jpnView;
    private ThongKeDongGopFrame thongKeDongGop = null;
    private ThongKeDongPhiVSFrame thongKeDongPhiVS = null;
    private ClassTableModel classTableModel = null;
    private List<DongGopModel> listDongGop = null;
    private List<DongPhiVSModel> listDongPhiVS = null;
    private JTextField reportedJText = null; 
    private final String[] COLUMNS_DONG_GOP = new String[]{"ID_HK", "TenChuHo", "ID_SK", "Ten_su_kien", "So_tien"};
    private final String[] COLUMNS_DONG_PHI_VS = new String[]{"ID_HK", "TenChuHo", "Nam", "Tien_Q1", "Tien_Q2", "Tien_Q3", "Tien_Q4"};
    
    
            
    private ThongKeService thongKeService = new ThongKeService();
    
    public  ThongKeController(JPanel jpnView,JTextField jText){
        this.jpnView = jpnView;
        this.reportedJText = jText;
    }
    
    public void thongKeDongGopFrame(JFrame parentFrame){
        thongKeDongGop = new ThongKeDongGopFrame(parentFrame,this);
        thongKeDongGop.setLocationRelativeTo(null);
        thongKeDongGop.setVisible(true);
    }
    
    public List<String> getListNam(){
        return thongKeService.getListNam();
    }
    
    public List<SuKienModel> getListSuKien(){
        return thongKeService.getListSuKien();
    }
    
    public void thongKeDongPhiVSFrame(JFrame parentFrame){
        thongKeDongPhiVS = new ThongKeDongPhiVSFrame(parentFrame,this);
        thongKeDongPhiVS.setLocationRelativeTo(null);
        thongKeDongPhiVS.setVisible(true);
    }
    
    public void setTheoHoKhau(String ID_HK){
    //dong gop
    JPanel mainPanel;
      mainPanel = new JPanel(); // main panel
      mainPanel.setLayout(new GridLayout(2, 1));
      mainPanel.setBackground(Color.white);
      mainPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
      this.listDongGop = this.thongKeService.getListDongGop(ID_HK);
      this.classTableModel = new ClassTableModel();
      setDataTable(this.classTableModel,mainPanel,"DongGop");
      this.listDongPhiVS = this.thongKeService.getListDongPhiVS(ID_HK);
      this.classTableModel = new ClassTableModel();
      setDataTable(this.classTableModel,mainPanel,"DongPhiVS");
      jpnView.removeAll();
      jpnView.setLayout(new BorderLayout());
      jpnView.add(mainPanel);
      jpnView.validate();
      jpnView.repaint();
    }
    
    
    public void setSelectedDongGop(SuKienModel sk){
        this.listDongGop = this.thongKeService.getListDongGop(sk);
        this.classTableModel = new ClassTableModel();
        updateTable(this.classTableModel,this.jpnView,"DongGop");
        String reported = thongKeService.getReportedDongGop(sk);
        this.reportedJText.setText(reported);
    }
    
    public void setSelectedDongPhiVS(String nam,String quy){
        this.listDongPhiVS = this.thongKeService.getListDongPhiVS(nam,quy);
        this.classTableModel = new ClassTableModel();
        updateTable(classTableModel, jpnView,"DongPhiVS");
        String reported = thongKeService.getReportedDongPhiVS(nam,quy);
        this.reportedJText.setText(reported);
    }
    
    public void updateTable(ClassTableModel classTableModel,JPanel jpnView,String kind){
        jpnView.removeAll();
        jpnView.setLayout(new BorderLayout());
        setDataTable(classTableModel,jpnView,kind);
        jpnView.validate();
        jpnView.repaint(); 
    }
    
    public void setDataTable(ClassTableModel classTableModel,JPanel jpnView,String kind){

        DefaultTableModel model = null;
        if (kind.equals("DongPhiVS")){
            List<DongPhiVSModel> listItem = new ArrayList<>();
            this.listDongPhiVS.forEach(dongTien -> {
                  listItem.add(dongTien); 
             });
            model = classTableModel.setTableDongPhiVS(listItem,COLUMNS_DONG_PHI_VS);
        }
        if (kind.equals("DongGop")){
            List<DongGopModel> listItem = new ArrayList<>();
            this.listDongGop.forEach(dongTien -> {
                listItem.add(dongTien); 
            });
            model = classTableModel.setTableDongGop(listItem,COLUMNS_DONG_GOP);
        }
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
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(160);
        table.getColumnModel().getColumn(2).setMaxWidth(50);
        table.addMouseListener(new MouseAdapter() { 
        });
        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(table);
        scroll.setPreferredSize(new Dimension(750, 400));
        jpnView.add(scroll);
    }
}
