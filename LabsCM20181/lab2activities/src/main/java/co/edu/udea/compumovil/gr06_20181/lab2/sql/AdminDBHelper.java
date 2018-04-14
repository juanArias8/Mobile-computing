package co.edu.udea.compumovil.gr06_20181.lab2.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.DatabaseMetaData;

/**
 * Created by daniel on 9/04/18.
 */

public class AdminDBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "register.db";

    public static final String TABLE_USERS = "users";
    public static final String COL_U1 = "ID";
    public static final String COL_U2 = "Name";
    public static final String COL_U3 = "Email";
    public static final String COL_U4 = "Password";


    public static final String TABLE_PLATES = "plates";
    public static final String COL_P1 = "ID";
    public static final String COL_P2 = "Name";
    public static final String COL_P3 = "Price";
    public static final String COL_P4 = "Schedule";
    public static final String COL_P5 = "Type";
    public static final String COL_P6 = "PreparationTime";
    public static final String COL_P7 = "Ingredients";



    public static final String TABLE_DRINKS = "drinks";
    public static final String COL_D1 = "ID";
    public static final String COL_D2 = "Name";
    public static final String COL_D3 = "Price";
    public static final String COL_D4 = "Ingredients";

    public AdminDBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Email TEXT, Password TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_PLATES + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Price REAL, Schedule TEXT, Type TEXT, PreparationTime TEXT, Ingredients TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_DRINKS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Price REAL, Ingredients TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_PLATES);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_DRINKS);
        onCreate(db);
    }
}
