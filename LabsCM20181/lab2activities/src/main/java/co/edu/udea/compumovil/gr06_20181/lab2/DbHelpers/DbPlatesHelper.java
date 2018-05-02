package co.edu.udea.compumovil.gr06_20181.lab2.DbHelpers;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import co.edu.udea.compumovil.gr06_20181.lab2.DatabaseModels.DrinksDb;
import co.edu.udea.compumovil.gr06_20181.lab2.DatabaseModels.PlatesDb;

public class DbPlatesHelper extends SQLiteOpenHelper{
    private static final String TAG = DbPlatesHelper.class.getSimpleName();

    public DbPlatesHelper(Context context){
        super(context, PlatesDb.DB_NAME, null, PlatesDb.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format(
                "create table %s (%s int primary key autoincrement, %s text, %s text, %s text, " +
                        "%s text, %s text, %s double, %s blob)",
                PlatesDb.TABLE,
                PlatesDb.Column.ID,
                PlatesDb.Column.NAME,
                PlatesDb.Column.KIND,
                PlatesDb.Column.PRICE,
                PlatesDb.Column.PREPARATION_TIME,
                PlatesDb.Column.PHOTO
        );
        Log.d(TAG, "onCreate with SQL: " + sql);
        db.execSQL(sql);
    }

    public void insertData(String name, String kind, double price, String preparation_time, byte[] photo){
        SQLiteDatabase db = getWritableDatabase();

        String sql = "insert into " + PlatesDb.TABLE + " values (null, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);

        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, kind);
        statement.bindDouble(3, price);
        statement.bindString(4, preparation_time);
        statement.bindBlob(5, photo);

        try {
            statement.executeInsert();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Cursor getData(){
        Cursor cursor = null;
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * " +
                "from " + PlatesDb.TABLE;
        try {
            cursor = db.rawQuery(sql, null);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + PlatesDb.TABLE);
        onCreate(db);
    }
}
