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
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author hoang
 */
public class DongPhiVSController {
    
    private JPanel jpnView;
    private List<DongPhiVSModel> listDongPhiVS;
    private ClassTableModel classTableModel = null;
    private final String[] COLUMNS = new String[]{"ID_HK", "TenChuHo", "Nam", "Tien_Q1", "Tien_Q2", "Tien_Q3", "Tien_Q4"};
    private DongPhiVSService dongPhiVSService = new DongPhiVSService();
    
    public DongPhiVSController(JPanel jpnView){  
        this.jpnView = jpnView;
        classTableModel = new ClassTableModel();
        updateListDongPhiVS();
    }
    
    @SuppressWarnings("unchecked")
    public void getListNam(JComboBox<String> cb){
        dongPhiVSService.getListNam(cb);
    }
    
    public void themMoiNam(String nam){
        try {
            dongPhiVSService.addNewNam(nam);
        } catch (SQLException ex) {
            Logger.getLogger(DongPhiVSController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DongPhiVSController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addNam(JFrame parentFrame){
        ThemMoiNamFrame themMoiNam = new ThemMoiNamFrame(parentFrame,this);
        themMoiNam.setLocationRelativeTo(null);
        themMoiNam.setVisible(true);
    }
    
    public void deleteNam(JFrame parentFrame){
        XoaNamFrame xoaNamFrame = new XoaNamFrame(parentFrame,this);
        xoaNamFrame.setLocationRelativeTo(null);
        xoaNamFrame.setVisible(true);
    }
    
    public void xoaNam(String Nam){
        dongPhiVSService.deleteNam(Nam);
    }
    
    
    
    public void addDongPhiVS(JFrame parentFrame){
        ThemMoiDongPhiVSFrame themMoiDongPhiVS = new ThemMoiDongPhiVSFrame(parentFrame,this);
        themMoiDongPhiVS.setLocationRelativeTo(null);
        themMoiDongPhiVS.setVisible(true);
        
    }
    
    public void updateListDongPhiVS(){
        this.listDongPhiVS = this.dongPhiVSService.getListDongPhiVS();
        setDataTable();
    }
    
    public void themMoiDongPhiVS(String ID_HK, String nam, String quy, String tien){
        try {
            this.dongPhiVSService.setDongPhiVS(Integer.parseInt(ID_HK),Integer.parseInt(nam),Integer.parseInt(quy),Integer.parseInt(tien));
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DongPhiVSController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public DongPhiVSController(){  
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
