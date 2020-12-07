/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;

/**
 *
 * @author hoang
 */
public class NhanKhauModel {
    
    private int ID_NK;
    private String hoTen;
    private Date namSinh;
    private int ID_HK;
    
    public void setID_NK(int ID_NK) {
        this.ID_NK = ID_NK;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setNamSinh(Date namSinh) {
        this.namSinh = namSinh;
    }

    public void setID_HK(int ID_HK) {
        this.ID_HK = ID_HK;
    }

    public int getID_NK() {
        return ID_NK;
    }

    public String getHoTen() {
        return hoTen;
    }

    public Date getNamSinh() {
        return namSinh;
    }

    public int getID_HK() {
        return ID_HK;
    }

}
