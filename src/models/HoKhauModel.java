/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author hoang
 */
public class HoKhauModel {

    public void setID_HK(String ID_HK) {
        this.iD_HK = ID_HK;
    }

    public void setTenChuHo(String TenChuHo) {
        this.tenChuHo = TenChuHo;
    }

    public void setID_ChuHo(String ID_ChuHo) {
        this.iD_ChuHo = ID_ChuHo;
    }

    public void setDiaChi(String DiaChi) {
        this.diaChi = DiaChi;
    }

    public void setSoThanhVien(Integer SoThanhVien) {
        this.soThanhVien = SoThanhVien;
    }

    public String getID_HK() {
        return iD_HK;
    }

    public String getTenChuHo() {
        return tenChuHo;
    }

    public String getID_ChuHo() {
        return iD_ChuHo;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public Integer getSoThanhVien() {
        return soThanhVien;
    }
    public String iD_HK;
    public String tenChuHo;
    public String iD_ChuHo;
    public String diaChi;
    public Integer soThanhVien;
    
    
}
