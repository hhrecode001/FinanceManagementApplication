/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author minh
 */
public class DongPhiVSModel {
    
    private String tenChuHo;
    private Integer iD_HK;
    private Integer nam;
    private Integer tien_Q1 = 0;
    private Integer tien_Q2 = 0;
    private Integer tien_Q3 = 0;
    private Integer tien_Q4 = 0;

    public void setTenChuHo(String tenChuHo) {
        this.tenChuHo = tenChuHo;
    }

    public void setID_HK(int ID_HK) {
        this.iD_HK = ID_HK;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public void setTien_Q1(int tien_Q1) {
        this.tien_Q1 = tien_Q1;
    }

    public void setTien_Q2(int tien_Q2) {
        this.tien_Q2 = tien_Q2;
    }

    public void setTien_Q3(int tien_Q3) {
        this.tien_Q3 = tien_Q3;
    }

    public void setTien_Q4(int tien_Q4) {
        this.tien_Q4 = tien_Q4;
    }

    public String getTenChuHo() {
        return tenChuHo;
    }

    public Integer getID_HK() {
        return iD_HK;
    }

    public Integer getNam() {
        return nam;
    }

    public Integer getTien_Q1() {
        return tien_Q1;
    }

    public Integer getTien_Q2() {
        return tien_Q2;
    }

    public Integer getTien_Q3() {
        return tien_Q3;
    }

    public Integer getTien_Q4() {
        return tien_Q4;
    }

}
