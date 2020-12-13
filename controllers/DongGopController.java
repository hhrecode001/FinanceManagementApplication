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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import models.DongGopModel;
import models.SuKienModel;
import services.DongGopService;
import utility.ClassTableModel;
import views.ThemMoiDongGopFrame;
import views.ThemMoiSuKienFrame;
import views.XoaSuKienFrame;

/**
 *
 * @author hoang
 */
public class DongGopController {
    
    private JPanel jpnView;
    private List<DongGopModel> listDongGop;
    private ClassTableModel classTableModel = null;
    private final String[] COLUMNS = new String[]{"ID_HK", "TenChuHo", "ID_SK", "Ten_su_kien", "So_tien"};
    private DongGopService dongGopService = new DongGopService();
    private ThemMoiSuKienFrame themMoiSuKien = null;
    private XoaSuKienFrame xoaSuKien = null;
    private ThemMoiDongGopFrame themMoiDongGop = null;
    
    public DongGopController(JPanel jpnView){  
        this.jpnView = jpnView;
        classTableModel = new ClassTableModel();
        updateListDongGop();
    }
    
    public DongGopController(){
    }
    
    public void editDongGopFrame(JFrame prentFrame){
        themMoiDongGop = new ThemMoiDongGopFrame(prentFrame,this);
        themMoiDongGop.setLocationRelativeTo(null);
        themMoiDongGop.setVisible(true);
    }
    
    public void editDongGopModel(DongGopModel dongGop){
        dongGopService.editDongGop(dongGop);
        updateListDongGop();
    }
    
    public List<SuKienModel> getListSuKien(){
        return dongGopService.getListSuKien();
    }
    
    public void deleteSuKienModel(SuKienModel sk){
        dongGopService.deleteSuKien(sk);
        updateListDongGop();
    }
    
    public void deleteSuKienFrame(JFrame parentFrame){
        xoaSuKien = new XoaSuKienFrame(parentFrame,this);
        xoaSuKien.setLocationRelativeTo(null);
        xoaSuKien.setVisible(true);
    }
    
    public void addSuKienFrame(JFrame parentFrame){
        themMoiSuKien = new ThemMoiSuKienFrame(parentFrame,this);
        themMoiSuKien.setLocationRelativeTo(null);
        themMoiSuKien.setVisible(true);
    }
    
    public void addSuKienModel(SuKienModel suKien){
        dongGopService.addSuKien(suKien);
        updateListDongGop();
    }
    
    public void updateListDongGop(){
        this.listDongGop = this.dongGopService.getListDongGop();
        setDataTable();
    }
    
    public void setDataTable(){
        List<DongGopModel> listItem = new ArrayList<>();
        this.listDongGop.forEach(DongGop -> {
           listItem.add(DongGop); 
        });
        DefaultTableModel model = classTableModel.setTableDongGop(listItem,COLUMNS);
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
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(1).setMaxWidth(160);
        table.getColumnModel().getColumn(2).setMaxWidth(50);
        table.getColumnModel().getColumn(3).setMaxWidth(300);
        table.getColumnModel().getColumn(4).setMaxWidth(80);
        table.addMouseListener(new MouseAdapter() {
        }); 
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);
        
        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(table);
        scroll.setPreferredSize(new Dimension(640, 400));
        jpnView.removeAll();
        jpnView.setLayout(new BorderLayout());
        jpnView.add(scroll);
        jpnView.validate();
        jpnView.repaint();
    }
    
}