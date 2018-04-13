package co.edu.udea.compumovil.gr06_20181.lab2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.DatabaseMetaData;

/**
 * Created by daniel on 9/04/18.
 */

public class AdminDBHelper extends SQLiteOpenHelper{

    public AdminDBHelper(Context context, String name,SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users (email text primary key, password text, name text)");
        //db.execSQL("create table plate (name text primary key, price real, schedule text, type text, " +
        //        "preparation_time text,ingredients text, photo blob)");
        //db.execSQL("create table drinks(name text primary key, price real, ingredients text, photo blob)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("create table user (email text primary key, password text, name text)");

    }
}
