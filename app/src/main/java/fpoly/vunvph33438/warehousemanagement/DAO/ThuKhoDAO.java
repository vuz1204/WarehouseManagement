package fpoly.vunvph33438.warehousemanagement.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.vunvph33438.warehousemanagement.Database.DbHelper;
import fpoly.vunvph33438.warehousemanagement.Model.ThuKho;

public class ThuKhoDAO {
    public static final String TABLE_NAME = "ThuKho";
    public static final String COLUMN_ID_THU_KHO = "id_thuKho";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_FULLNAME = "fullname";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_ROLE = "role";
    DbHelper dbHelper;

    public ThuKhoDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean insertData(ThuKho obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, obj.getUsername());
        contentValues.put(COLUMN_PASSWORD, obj.getPassword());
        contentValues.put(COLUMN_FULLNAME, obj.getFullname());
        contentValues.put(COLUMN_EMAIL, obj.getEmail());
        contentValues.put(COLUMN_ROLE, obj.getRole());
        long check = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        obj.setId_thuKho((int) check);
        return check != -1;
    }

    public boolean delete(ThuKho obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getId_thuKho())};
        long check = sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID_THU_KHO + "= ?", dk);
        return check != -1;
    }

    public boolean update(ThuKho obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getId_thuKho())};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, obj.getUsername());
        contentValues.put(COLUMN_PASSWORD, obj.getPassword());
        contentValues.put(COLUMN_FULLNAME, obj.getFullname());
        contentValues.put(COLUMN_EMAIL, obj.getEmail());
        contentValues.put(COLUMN_ROLE, obj.getRole());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_ID_THU_KHO + "= ?", dk);
        return check != -1;
    }

    public boolean updatePass(ThuKho obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getId_thuKho())};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, obj.getUsername());
        contentValues.put(COLUMN_PASSWORD, obj.getPassword());
        contentValues.put(COLUMN_FULLNAME, obj.getFullname());
        contentValues.put(COLUMN_EMAIL, obj.getEmail());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_ID_THU_KHO + "= ?", dk);
        return check != -1;
    }

    public ArrayList<ThuKho> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<ThuKho> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);

        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    int idThuKho = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_THU_KHO));
                    String username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
                    String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
                    String fullname = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FULLNAME));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));
                    int role = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE)));
                    list.add(new ThuKho(idThuKho, username, password, fullname, email, role));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            sqLiteDatabase.close();
        }

        return list;
    }

    public ArrayList<ThuKho> selectAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return getAll(sql);
    }

    public ThuKho selectID(String id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + "=?";
        ArrayList<ThuKho> list = getAll(sql, id);
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public boolean checkLogin(String username, String password, String role) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=? AND " + COLUMN_ROLE + "=?";
        String[] selectionArgs = new String[]{username, password, role};
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);

        try {
            return cursor.getCount() > 0;
        } finally {
            cursor.close();
            sqLiteDatabase.close();
        }
    }
}
