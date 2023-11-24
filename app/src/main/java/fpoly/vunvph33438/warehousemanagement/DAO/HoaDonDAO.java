package fpoly.vunvph33438.warehousemanagement.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.vunvph33438.warehousemanagement.Database.DbHelper;
import fpoly.vunvph33438.warehousemanagement.Model.HoaDon;

public class HoaDonDAO {

    private static final String TABLE_NAME = "HoaDon";
    public static final String COLUMN_ID_HOA_DON = "id_hoaDon";
    private static final String COLUMN_ID_THU_KHO = "id_thuKho";
    private static final String COLUMN_SO_HOA_DON = "soHoaDon";
    private static final String COLUMN_LOAI_HOA_DON = "loaiHoaDon";
    private static final String COLUMN_NGAY_THANG = "ngayThang";
    DbHelper dbHelper;

    public HoaDonDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean insert(HoaDon obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID_THU_KHO, obj.getId_thuKho());
        contentValues.put(COLUMN_SO_HOA_DON, obj.getSoHoaDon());
        contentValues.put(COLUMN_LOAI_HOA_DON, obj.getLoaiHoaDon());
        contentValues.put(COLUMN_NGAY_THANG, obj.getNgayThang());
        long check = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        obj.setId_hoaDon((int) check);
        return check != -1;
    }

    public boolean delete(HoaDon obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getId_hoaDon())};
        long check = sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID_HOA_DON + "=?", dk);
        return check != -1;
    }

    public boolean update(HoaDon obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getId_hoaDon())};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID_THU_KHO, obj.getId_thuKho());
        contentValues.put(COLUMN_SO_HOA_DON, obj.getSoHoaDon());
        contentValues.put(COLUMN_LOAI_HOA_DON, obj.getLoaiHoaDon());
        contentValues.put(COLUMN_NGAY_THANG, obj.getNgayThang());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_ID_HOA_DON + "=?", dk);
        return check != -1;
    }

    public ArrayList<HoaDon> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ArrayList<HoaDon> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                int maHD = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_HOA_DON));
                int maTV = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_THU_KHO));
                int soHoaDon = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SO_HOA_DON));
                int loaiHoaDon = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LOAI_HOA_DON));
                String ngayThang = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NGAY_THANG));

                HoaDon hoaDon = new HoaDon(maHD, maTV, soHoaDon, loaiHoaDon, ngayThang);
                list.add(hoaDon);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<HoaDon> selectAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return getAll(sql);
    }

    public HoaDon selectID(String id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID_HOA_DON + " = ?";
        ArrayList<HoaDon> list = getAll(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }
}
