package fpoly.vunvph33438.warehousemanagement.Model;

public class HoaDon {
    private int id_hoaDon;
    private int id_thuKho;
    private int soHoaDon;
    private String loaiHoaDon;
    private String ngayThang;

    public HoaDon() {
    }

    public HoaDon(int id_hoaDon, int id_thuKho, int soHoaDon, String loaiHoaDon, String ngayThang) {
        this.id_hoaDon = id_hoaDon;
        this.id_thuKho = id_thuKho;
        this.soHoaDon = soHoaDon;
        this.loaiHoaDon = loaiHoaDon;
        this.ngayThang = ngayThang;
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

    public String getLoaiHoaDon() {
        return loaiHoaDon;
    }

    public void setLoaiHoaDon(String loaiHoaDon) {
        this.loaiHoaDon = loaiHoaDon;
    }

    public String getNgayThang() {
        return ngayThang;
    }

    public void setNgayThang(String ngayThang) {
        this.ngayThang = ngayThang;
    }
}
