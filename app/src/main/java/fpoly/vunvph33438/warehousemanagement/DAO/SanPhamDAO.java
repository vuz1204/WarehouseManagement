package fpoly.vunvph33438.warehousemanagement.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.vunvph33438.warehousemanagement.Database.DbHelper;
import fpoly.vunvph33438.warehousemanagement.Model.SanPham;

public class SanPhamDAO {

    private static final String TABLE_NAME = "SanPham";
    private static final String COLUMN_ID_SAN_PHAM = "id_sanPham";
    private static final String COLUMN_TEN_SAN_PHAM = "tenSanPham";
    private static final String COLUMN_GIA = "gia";
    private static final String COLUMN_SO_LUONG = "soLuong";
    private static final String COLUMN_ID_THE_LOAI = "id_theLoai";
    DbHelper dbHelper;

    public SanPhamDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean insertData(SanPham obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TEN_SAN_PHAM, obj.getTenSanPham());
        contentValues.put(COLUMN_GIA, obj.getGia());
        contentValues.put(COLUMN_SO_LUONG, obj.getSoLuong());
        contentValues.put(COLUMN_ID_THE_LOAI, obj.getId_theLoai());
        long check = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        obj.setId_sanPham((int) check);
        return check != -1;
    }

    public boolean delete(SanPham obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getId_sanPham())};
        long check = sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID_SAN_PHAM + "=?", dk);
        return check != -1;
    }

    public boolean update(SanPham obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getId_sanPham())};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TEN_SAN_PHAM, obj.getTenSanPham());
        contentValues.put(COLUMN_GIA, obj.getGia());
        contentValues.put(COLUMN_SO_LUONG, obj.getSoLuong());
        contentValues.put(COLUMN_ID_THE_LOAI, obj.getId_theLoai());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_ID_SAN_PHAM + "=?", dk);
        return check != -1;
    }


    private ArrayList<SanPham> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<SanPham> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int maSanPham = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_SAN_PHAM));
                String tenSanPham = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_SAN_PHAM));
                int gia = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_GIA));
                int soLuong = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SO_LUONG));
                int maTheLoai = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_THE_LOAI));
                list.add(new SanPham(maSanPham, tenSanPham, gia, soLuong, maTheLoai));
            }
        }
        return list;
    }

    public ArrayList<SanPham> selectAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return getAll(sql);
    }

    public SanPham selectID(String id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID_SAN_PHAM + " = ?";
        ArrayList<SanPham> list = getAll(sql, id);

        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
