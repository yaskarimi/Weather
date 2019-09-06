package com.example.weather;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CityNamesSQLiteDataBase extends SQLiteOpenHelper {

    static final String TABLE_NAME = "yasiKhare";


    public CityNamesSQLiteDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String ADD_DATA_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " name TEXT " +
                ")";
        sqLiteDatabase.execSQL(ADD_DATA_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addData(String name) {
        if(name != null && !name.isEmpty()) {
            SQLiteDatabase database = this.getWritableDatabase();
            String INSERT_INTO_DATABASE_QUERY = "INSERT INTO " + TABLE_NAME +
                    " (name) VALUES(" +
                    "'" + name + "'"
                    + ")";
            database.execSQL(INSERT_INTO_DATABASE_QUERY);
            database.close();
//        String cmd = "PRAGMA table_info(names);";
//        Cursor cursor = database.rawQuery(cmd, null);
//        Log.e("sssssss", cursor.getString(0))
        }
    }

    public List<String> loadData() {
        String load_week_weather = "SELECT name FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(load_week_weather, null);

        List<String> result = new ArrayList<>();

        while (cursor.moveToNext()) {
            result.add(cursor.getString(0));

        }

        database.close();
        return result;

    }

}
