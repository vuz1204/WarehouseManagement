package fpoly.vunvph33438.warehousemanagement.Model;

import java.security.SecureRandom;

public class HoaDon {
    private int id_hoaDon;
    private int id_thuKho;
    private int soHoaDon;
    private int loaiHoaDon;
    private String ngayThang;

    public HoaDon() {
    }

    public HoaDon(int id_hoaDon, int id_thuKho, int soHoaDon, int loaiHoaDon, String ngayThang) {
        this.id_hoaDon = id_hoaDon;
        this.id_thuKho = id_thuKho;
        this.soHoaDon = soHoaDon;
        this.loaiHoaDon = loaiHoaDon;
        this.ngayThang = ngayThang;
    }

    public int generateSoHoaDon() {
        SecureRandom secureRandom = new SecureRandom();
        this.soHoaDon = secureRandom.nextInt(900000) + 100000;

        return this.soHoaDon;
    }

    public int getId_hoaDon() {
        return id_hoaDon;
    }

    public void setId_hoaDon(int id_hoaDon) {
        this.id_hoaDon = id_hoaDon;
    }

    public int getId_thuKho() {
        return id_thuKho;
    }

    public void setId_thuKho(int id_thuKho) {
        this.id_thuKho = id_thuKho;
    }

    public int getSoHoaDon() {
        return soHoaDon;
    }

    public void setSoHoaDon(int soHoaDon) {
        this.soHoaDon = soHoaDon;
    }

    public int getLoaiHoaDon() {
        return loaiHoaDon;
    }

    public void setLoaiHoaDon(int loaiHoaDon) {
        this.loaiHoaDon = loaiHoaDon;
    }

    public String getNgayThang() {
        return ngayThang;
    }

    public void setNgayThang(String ngayThang) {
        this.ngayThang = ngayThang;
    }
}
