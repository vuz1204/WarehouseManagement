package fpoly.vunvph33438.warehousemanagement.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WarehouseManagement.db";
    private static final int DATABASE_VERSION = 2;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng thủ kho
        String createTableThuKho = "CREATE TABLE ThuKho (" +
                "id_thuKho INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE, " +
                "password TEXT NOT NULL, " +
                "fullname TEXT NOT NULL, " +
                "email TEXT NOT NULL, " +
                "role INTEGER NOT NULL)";
        db.execSQL(createTableThuKho);

        String insertDefaultThuKho = "INSERT INTO ThuKho (username, password, fullname, email, role) VALUES ('admin', 'admin', 'Nguyễn Văn Vũ', 'vanvu101204@gmail.com', 0)";
        db.execSQL(insertDefaultThuKho);

        // Tạo bảng thể loại
        String createTableTheLoai = "CREATE TABLE TheLoai (" +
                "id_theLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenTheLoai TEXT NOT NULL)";
        db.execSQL(createTableTheLoai);

        // Tạo bảng sản phẩm
        String createTableSanPham= "CREATE TABLE SanPham (" +
                "id_sanPham INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenSanPham TEXT NOT NULL, " +
                "gia INTEGER NOT NULL, " +
                "soLuong INTEGER NOT NULL, " +
                "id_theLoai INTEGER REFERENCES TheLoai(id_theLoai))";
        db.execSQL(createTableSanPham);

        // Tạo bảng hóa đơn
        String createTableHoaDon = "CREATE TABLE HoaDon (" +
                "id_hoaDon INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_thuKho INTEGER REFERENCES ThuKho (id_thuKho), " +
                "soHoaDon INTEGER NOT NULL, " +
                "loaiHoaDon INTEGER NOT NULL, " +
                "ngayThang TEXT NOT NULL)";
        db.execSQL(createTableHoaDon);

        // Tạo bảng chi tiết hóa đơn
        String createTableChiTietHoaDon = "CREATE TABLE ChiTietHoaDon (" +
                "id_chiTietHoaDon INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_sanPham INTEGER REFERENCES SanPham(id_sanPham) ON DELETE CASCADE, " +
                "id_hoaDon INTEGER REFERENCES HoaDon(id_hoaDon) ON DELETE CASCADE, " +
                "soLuong INTEGER NOT NULL, " +
                "donGia INTEGER NOT NULL)";
        db.execSQL(createTableChiTietHoaDon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS ThuKho");
            db.execSQL("DROP TABLE IF EXISTS TheLoai");
            db.execSQL("DROP TABLE IF EXISTS SanPham");
            db.execSQL("DROP TABLE IF EXISTS HoaDon");
            db.execSQL("DROP TABLE IF EXISTS ChiTietHoaDon");
            onCreate(db);
        }
    }
}
