/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
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
import javax.swing.JOptionPane;

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
 
public Object GetData(JTable table, int row_index, int col_index){
    return table.getModel().getValueAt(row_index, col_index);
}
    
    @SuppressWarnings("unchecked")
    public void inDanhSachHo(){
        try{
            Document doc = new Document();
            PdfWriter writer = PdfWriter.getInstance((Document)doc,(OutputStream)new FileOutputStream("report.pdf"));
            doc.open();
            doc.add(new Paragraph(" DANH SACH CAC HO GIA DINH\n",FontFactory.getFont(FontFactory.TIMES_ROMAN)));
            doc.add(new Paragraph(new Date().toString()));
            doc.add(new Paragraph("\n",FontFactory.getFont(FontFactory.TIMES_ROMAN)));
            PdfPTable ptab = new PdfPTable(6);
            ptab.setWidthPercentage(100);
            ptab.setWidths(new int[]{1, 3, 2, 3, 2, 1});
            com.itextpdf.text.Font f = FontFactory.getFont("Times New Roman", "Cp1253", true);
            JTable table = getDataTable();
            ptab.addCell("ID_HK");
            ptab.addCell("TenChuHo");
            ptab.addCell("ID_ChuHo");
            ptab.addCell("DiaChi");
            ptab.addCell("SoThanhVien");
            ptab.addCell("SoTien");
            for(int i=0;i<table.getRowCount();i++){
                for(int j=0 ; j<5 ; j++){
                    Object obj = GetData(table, i, j);
                    String value = obj .toString();
                    ptab.addCell(new PdfPCell(new Phrase(value,f)));
                }
                ptab.addCell(new PdfPCell(new Phrase("",f)));
            }
            doc.add(ptab);
            doc.close();
            JOptionPane.showMessageDialog(null,"Saved");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
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
    
    public JTable getDataTable(){
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
        return table;
    }
    
    public JScrollPane getDataScrollPane(){
        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(getDataTable());
        scroll.setPreferredSize(new Dimension(640, 400));
        return scroll;
    }
}
