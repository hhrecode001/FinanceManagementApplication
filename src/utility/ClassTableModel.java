package utility;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import models.DongGopModel;
import models.DongPhiVSModel;
import models.HoKhauModel;
import models.NhanKhauModel;

/**
 *
 * @author Hai
 * class dinh nghia cac dang table co trong phan mem
 */
public class ClassTableModel {

    public DefaultTableModel setTableNhanKhau(List<NhanKhauModel> listItem, String[] listColumn) {
        final int columns = listColumn.length;
        DefaultTableModel dtm = new DefaultTableModel()  {
            @Override
            public boolean isCellEditable(int row, int column) {
                return super.isCellEditable(row, column); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 5 ? Boolean.class : String.class;
            }
        };
        dtm.setColumnIdentifiers(listColumn);
        Object[] obj;
        obj = new Object[columns];
        listItem.forEach((NhanKhauModel item) -> {
            obj[0] = item.getID_NK();
            obj[1] = item.getHoTen();
            obj[2] = item.getNamSinh();
            obj[3] = item.getID_HK();
            dtm.addRow(obj);
        });
        return dtm;
    }
    
    public DefaultTableModel setTableDongPhiVS(List<DongPhiVSModel> listItem, String[] listColumn) {
        final int columns = listColumn.length;
        DefaultTableModel dtm = new DefaultTableModel()  {
            @Override
            public boolean isCellEditable(int row, int column) {
                return super.isCellEditable(row, column); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return listColumn.length == columnIndex ? Boolean.class : String.class;
            }
        };
        dtm.setColumnIdentifiers(listColumn);
        Object[] obj;
        obj = new Object[columns];
        listItem.forEach((DongPhiVSModel item) -> {
            obj[0] = item.getID_HK();
            obj[1] = item.getTenChuHo();
            obj[2] = item.getNam();
            obj[3] = item.getTien_Q1();
            obj[4] = item.getTien_Q2();
            obj[5] = item.getTien_Q3();
            obj[6] = item.getTien_Q4();
            dtm.addRow(obj);
        });
        return dtm;
    }
        
    public DefaultTableModel setTableDongGop(List<DongGopModel> listItem, String[] listColumn) {
        final int columns = listColumn.length;
        DefaultTableModel dtm = new DefaultTableModel()  {
            @Override
            public boolean isCellEditable(int row, int column) {
                return super.isCellEditable(row, column); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return listColumn.length == columnIndex ? Boolean.class : String.class;
            }
        };
        dtm.setColumnIdentifiers(listColumn);
        Object[] obj;
        obj = new Object[columns];
        listItem.forEach((DongGopModel item) -> {
            obj[0] = item.getID_HK();
            obj[1] = item.getTenChuHo();
            obj[2] = item.getID_SK();
            obj[3] = item.getTenSuKien();
            obj[4] = item.getSoTien();
            dtm.addRow(obj);
        });
        return dtm;
    }
    
    public DefaultTableModel setTableHoKhau(List<HoKhauModel> listItem, String[] listColumn) {
        final int columns = listColumn.length;
        DefaultTableModel dtm = new DefaultTableModel()  {
            @Override
            public boolean isCellEditable(int row, int column) {
                return super.isCellEditable(row, column); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return listColumn.length == columnIndex ? Boolean.class : String.class;
            }
        };
        dtm.setColumnIdentifiers(listColumn);
        Object[] obj;
        obj = new Object[columns];
        listItem.forEach((HoKhauModel item) -> {
            obj[0] = item.getID_HK();
            obj[1] = item.getTenChuHo();
            obj[2] = item.getID_ChuHo();
            obj[3] = item.getDiaChi();
            obj[4] = item.getSoThanhVien();
            dtm.addRow(obj);
        });
        return dtm;
    }
}
