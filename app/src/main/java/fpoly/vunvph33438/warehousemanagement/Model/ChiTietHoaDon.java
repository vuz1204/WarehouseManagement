package fpoly.vunvph33438.warehousemanagement.Model;

public class ChiTietHoaDon {
    private int id_chiTietHoaDon;
    private int id_sanPham;
    private int id_hoaDon;
    private int soLuong;
    private int donGia;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(int id_chiTietHoaDon, int id_sanPham, int id_hoaDon, int soLuong, int donGia) {
        this.id_chiTietHoaDon = id_chiTietHoaDon;
        this.id_sanPham = id_sanPham;
        this.id_hoaDon = id_hoaDon;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public int getId_chiTietHoaDon() {
        return id_chiTietHoaDon;
    }

    public void setId_chiTietHoaDon(int id_chiTietHoaDon) {
        this.id_chiTietHoaDon = id_chiTietHoaDon;
    }

    public int getId_sanPham() {
        return id_sanPham;
    }

    public void setId_sanPham(int id_sanPham) {
        this.id_sanPham = id_sanPham;
    }

    public int getId_hoaDon() {
        return id_hoaDon;
    }

    public void setId_hoaDon(int id_hoaDon) {
        this.id_hoaDon = id_hoaDon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }
}
