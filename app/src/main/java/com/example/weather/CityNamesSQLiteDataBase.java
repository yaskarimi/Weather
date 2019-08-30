package com.example.weather;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CityNamesSQLiteDataBase extends SQLiteOpenHelper {

    String TABLE_NAME = "names";



    public CityNamesSQLiteDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String ADD_DATA_QUERY = "CREATE TABLE " + TABLE_NAME + "("+
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                 " name TEXT" +
                ")" ;
        sqLiteDatabase.execSQL(ADD_DATA_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addData(String name){

      String INSERT_INTO_DATABASE_QUERY = "INSERT INTO " + TABLE_NAME + "(name)" + "VALUES("+
              "'" + name + "'"
              +")";
      SQLiteDatabase database = this.getWritableDatabase();
      database.execSQL(INSERT_INTO_DATABASE_QUERY);
      database.close();
    }

    public List<String> loadData(){
        String load_week_weather = "SELECT name FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(load_week_weather , null);

        List<String> result = new ArrayList<>();

       while (cursor.moveToNext()){
            result.add(cursor.getString(0));

        }
        database.close();
       return result;


    }

}
