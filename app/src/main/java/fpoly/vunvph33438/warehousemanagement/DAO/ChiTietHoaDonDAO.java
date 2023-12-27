package fpoly.vunvph33438.warehousemanagement.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.vunvph33438.warehousemanagement.Database.DbHelper;
import fpoly.vunvph33438.warehousemanagement.Model.ChiTietHoaDon;
import fpoly.vunvph33438.warehousemanagement.Model.HoaDon;

public class ChiTietHoaDonDAO {

    private static final String TABLE_NAME = "ChiTietHoaDon";
    public static final String COLUMN_ID_HOA_DON = "id_hoaDon";
    public static final String COLUMN_ID_SAN_PHAM = "id_sanPham";
    private static final String COLUMN_SO_LUONG = "soLuong";
    private static final String COLUMN_DON_GIA = "donGia";
    DbHelper dbHelper;

    public ChiTietHoaDonDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean insert(ChiTietHoaDon obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID_HOA_DON, obj.getId_hoaDon());
        contentValues.put(COLUMN_ID_SAN_PHAM, obj.getId_sanPham());
        contentValues.put(COLUMN_SO_LUONG, obj.getSoLuong());
        contentValues.put(COLUMN_DON_GIA, obj.getDonGia());
        long check = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return check != -1;
    }

    public boolean update(ChiTietHoaDon obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getId_hoaDon()), String.valueOf(obj.getId_sanPham())};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID_HOA_DON, obj.getId_hoaDon());
        contentValues.put(COLUMN_ID_SAN_PHAM, obj.getId_sanPham());
        contentValues.put(COLUMN_SO_LUONG, obj.getSoLuong());
        contentValues.put(COLUMN_DON_GIA, obj.getDonGia());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_ID_HOA_DON + "=? AND " + COLUMN_ID_SAN_PHAM + "=?", dk);
        return check != -1;
    }

    public boolean delete(ChiTietHoaDon obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getId_sanPham())};
        long check = sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID_SAN_PHAM + "=?", dk);
        return check != -1;
    }

    public ArrayList<ChiTietHoaDon> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ArrayList<ChiTietHoaDon> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int maHD = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_HOA_DON));
                int maSP = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_SAN_PHAM));
                int soLuong = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SO_LUONG));
                int donGia = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DON_GIA));

                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(maSP, maHD, soLuong, donGia);
                list.add(chiTietHoaDon);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<ChiTietHoaDon> selectAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return getAll(sql);
    }

    public ArrayList<ChiTietHoaDon> selectByIdHoaDon(String id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID_HOA_DON + " = ?";
        ArrayList<ChiTietHoaDon> list = getAll(sql, id);
        return list.isEmpty() ? new ArrayList<>() : list;
    }
}
