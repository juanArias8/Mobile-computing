package co.edu.udea.compumovil.gr06_20181.lab2.DbHelpers;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import co.edu.udea.compumovil.gr06_20181.lab2.DatabaseModels.DrinksDb;

public class DbDrinksHelper extends SQLiteOpenHelper{
    private static final String TAG = DbDrinksHelper.class.getSimpleName();

    public DbDrinksHelper(Context context){
        super(context, DrinksDb.DB_NAME, null, DrinksDb.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format(
                "create table %s (%s int primary key autoincrement, %s text, %s text, " +
                        "%s double, %s blob)",
                DrinksDb.TABLE,
                DrinksDb.Column.ID,
                DrinksDb.Column.NAME,
                DrinksDb.Column.PRICE,
                DrinksDb.Column.PHOTO
        );
        Log.d(TAG, "onCreate with SQL: " + sql);
        db.execSQL(sql);
    }

    public void insertData(String name, double price, byte[] photo){
        SQLiteDatabase db = getWritableDatabase();

        String sql = "insert into " + DrinksDb.TABLE + " values (null, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);

        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindDouble(3, price);
        statement.bindBlob(4, photo);

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
                "from " + DrinksDb.TABLE;
        try {
            cursor = db.rawQuery(sql, null);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DrinksDb.TABLE);
        onCreate(db);
    }
}
