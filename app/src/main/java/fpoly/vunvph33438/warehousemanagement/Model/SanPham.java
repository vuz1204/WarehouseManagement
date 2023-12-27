package fpoly.vunvph33438.warehousemanagement.Model;

import java.text.NumberFormat;
import java.util.Locale;

public class SanPham {
    private int id_sanPham;
    private String tenSanPham;
    private int gia;
    private int soLuong;
    private int id_theLoai;

    public SanPham() {
    }

    public SanPham(int id_sanPham, String tenSanPham, int gia, int soLuong, int id_theLoai) {
        this.id_sanPham = id_sanPham;
        this.tenSanPham = tenSanPham;
        this.gia = gia;
        this.soLuong = soLuong;
        this.id_theLoai = id_theLoai;
    }

    public int getId_sanPham() {
        return id_sanPham;
    }

    public void setId_sanPham(int id_sanPham) {
        this.id_sanPham = id_sanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getId_theLoai() {
        return id_theLoai;
    }

    public void setId_theLoai(int id_theLoai) {
        this.id_theLoai = id_theLoai;
    }

    public String getPriceFormatted() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        return numberFormat.format(gia);
    }
}
