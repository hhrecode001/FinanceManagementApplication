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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import models.DongPhiVSModel;
import services.DongPhiVSService;
import utility.ClassTableModel;
import views.ThemMoiDongPhiVSFrame;
import views.ThemMoiNamFrame;
import views.XoaNamFrame;

/**
 *

 */
public class DongPhiVSController {
    
    private JPanel jpnView;
    private List<DongPhiVSModel> listDongPhiVS;
    private ClassTableModel classTableModel = null;
    private final String[] COLUMNS = new String[]{"ID_HK", "TenChuHo", "Nam", "Tien_Q1", "Tien_Q2", "Tien_Q3", "Tien_Q4"};
    private DongPhiVSService dongPhiVSService = new DongPhiVSService();
    private ThemMoiDongPhiVSFrame themMoiDongPhiVS = null;
    private XoaNamFrame xoaNamFrame = null;
    private ThemMoiNamFrame themMoiNam = null;
    
    public DongPhiVSController(JPanel jpnView){  
        this.jpnView = jpnView;
        classTableModel = new ClassTableModel();
        updateListDongPhiVS();
    }
    
    public DongPhiVSController(){  
    }
    
    @SuppressWarnings("unchecked")
    public void getListNam(JComboBox<String> cb){
        dongPhiVSService.getListNam(cb);
    }
    
    public void addNamModel(String nam){
        dongPhiVSService.addNewNam(nam);
        updateListDongPhiVS();
    }
    
    public void addNamFrame(JFrame parentFrame){
        themMoiNam = new ThemMoiNamFrame(parentFrame,this);
        themMoiNam.setLocationRelativeTo(null);
        themMoiNam.setVisible(true);
    }
    
    public void deleteNamFrame(JFrame parentFrame){
        xoaNamFrame = new XoaNamFrame(parentFrame,this);
        xoaNamFrame.setLocationRelativeTo(null);
        xoaNamFrame.setVisible(true);
    }
    
    public void deleteNamModel(String Nam){
        dongPhiVSService.deleteNam(Nam);
        updateListDongPhiVS();
    }
    
    public void addDongPhiVSFrame(JFrame parentFrame){
        themMoiDongPhiVS = new ThemMoiDongPhiVSFrame(parentFrame,this);
        themMoiDongPhiVS.setLocationRelativeTo(null);
        themMoiDongPhiVS.setVisible(true);
        
    }
    
    public void updateListDongPhiVS(){
        this.listDongPhiVS = this.dongPhiVSService.getListDongPhiVS();
        setDataTable();
    }
    
    public void updateListDongPhiVS(String nam, String ID_HK){
        this.listDongPhiVS = this.dongPhiVSService.getListDongPhiVS(nam,ID_HK);
        setDataTable();
    }
    
    public void themMoiDongPhiVSModel(DongPhiVSModel dongPhiVS, String quy){
        this.dongPhiVSService.setDongPhiVS(dongPhiVS,Integer.parseInt(quy));
        updateListDongPhiVS();
    }
   
    
    
    public void setDataTable(){
        List<DongPhiVSModel> listItem = new ArrayList<>();
        this.listDongPhiVS.forEach(DongPhiVS -> {
           listItem.add(DongPhiVS); 
        });
        DefaultTableModel model = classTableModel.setTableDongPhiVS(listItem,COLUMNS);
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
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(160);
        table.addMouseListener(new MouseAdapter() { 
        });        
        
        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(table);
        scroll.setPreferredSize(new Dimension(1350, 400));
        jpnView.removeAll();
        jpnView.setLayout(new BorderLayout());
        jpnView.add(scroll);
        jpnView.validate();
        jpnView.repaint();
    }
}
