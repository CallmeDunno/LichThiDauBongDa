package com.example.lichthidaubongda;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DB_SQLite extends SQLiteOpenHelper {

    public DB_SQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryCreateDB = "CREATE TABLE IF NOT EXISTS LichThiDau (ma_tran_dau INTEGER PRIMARY KEY AUTOINCREMENT" +
                ", ten_doi_1 VARCHAR(200) NOT NULL" +
                ", logo_1 VARCHAR(200) NOT NULL" +
                ", ten_doi_2 VARCHAR(200) NOT NULL" +
                ", logo_2 VARCHAR(200) NOT NULL" +
                ", gio VARCHAR(200) NOT NULL" +
                ", ngay VARCHAR(200) NOT NULL" +
                ", vong VARCHAR(200) NOT NULL" +
                ", more VARCHAR(200) NOT NULL)";
        sqLiteDatabase.execSQL(queryCreateDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String queryDropTable = "DROP TABLE IF EXISTS LichThiDau";
        sqLiteDatabase.execSQL(queryDropTable);
        onCreate(sqLiteDatabase);
    }
}
