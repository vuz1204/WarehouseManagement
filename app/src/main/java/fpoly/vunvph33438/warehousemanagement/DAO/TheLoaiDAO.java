package fpoly.vunvph33438.warehousemanagement.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.vunvph33438.warehousemanagement.Database.DbHelper;
import fpoly.vunvph33438.warehousemanagement.Model.TheLoai;

public class TheLoaiDAO {
    DbHelper dbHelper;

    private static final String TABLE_NAME = "TheLoai";
    private static final String COLUMN_ID_THE_LOAI = "id_theLoai";
    private static final String COLUMN_TEN_LOAI = "tenTheLoai";

    public TheLoaiDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean insertData(TheLoai obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TEN_LOAI, obj.getTenTheLoai());
        long check = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        obj.setId_theLoai((int) check);
        return check != -1;
    }

    public boolean delete(TheLoai obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getId_theLoai())};
        long check = sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID_THE_LOAI + "=?", dk);
        return check != -1;
    }

    public boolean update(TheLoai obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getId_theLoai())};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TEN_LOAI, obj.getTenTheLoai());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_ID_THE_LOAI + "=?", dk);
        return check != -1;
    }
    private ArrayList<TheLoai> getAll(String sql, String... selectionArgs){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<TheLoai> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.moveToFirst()) {
            do {
                int idTheLoai = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_THE_LOAI));
                String tenLoai = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_LOAI));
                list.add(new TheLoai(idTheLoai, tenLoai));
            } while (cursor.moveToNext());
        }
        return list;
    }
    public ArrayList<TheLoai> selectAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return getAll(sql);
    }
}
