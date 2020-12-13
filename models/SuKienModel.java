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
public class SuKienModel {

    
    public boolean equals(SuKienModel sk){
        return (sk.iD_SK == this.iD_SK);
    }
    
    public String toString(){
        return this.getTen_su_kien();
    }
    public void setID_SK(String ID_SK) {
        this.iD_SK = ID_SK;
    }

    public void setTen_su_kien(String Ten_su_kien) {
        this.ten_su_kien = Ten_su_kien;
    }

    public void setNgay_to_chuc(String Ngay_to_chuc) {
        this.ngay_to_chuc = Ngay_to_chuc;
    }

    public String getID_SK() {
        return iD_SK;
    }

    public String getTen_su_kien() {
        return ten_su_kien;
    }

    public String getNgay_to_chuc() {
        return ngay_to_chuc;
    }
    
    private String iD_SK;
    private String ten_su_kien;
    private String ngay_to_chuc;
    
}
