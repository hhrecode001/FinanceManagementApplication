/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author hai
 */
public class DongGopModel {
    
    private Integer iD_HK;
    private String tenChuHo;
    private String iD_SK;
    private String tenSuKien;
    private Integer soTien;
    
    public void setID_HK(Integer ID_HK) {
        this.iD_HK = ID_HK;
    }

    public void setTenChuHo(String tenChuHo) {
        this.tenChuHo = tenChuHo;
    }

    public void setID_SK(String ID_SK) {
        this.iD_SK = ID_SK;
    }

    public void setTenSuKien(String tenSuKien) {
        this.tenSuKien = tenSuKien;
    }

    public void setSoTien(Integer soTien) {
        this.soTien = soTien;
    }

    public Integer getID_HK() {
        return iD_HK;
    }

    public String getTenChuHo() {
        return tenChuHo;
    }

    public String getID_SK() {
        return iD_SK;
    }

    public String getTenSuKien() {
        return tenSuKien;
    }

    public Integer getSoTien() {
        return soTien;
    }    
}
