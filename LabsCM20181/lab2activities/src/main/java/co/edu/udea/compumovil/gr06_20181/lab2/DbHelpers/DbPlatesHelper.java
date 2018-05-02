package co.edu.udea.compumovil.gr06_20181.lab2.DbHelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
                PlatesDb.Column.TIMETABLE,
                PlatesDb.Column.KIND,
                PlatesDb.Column.PREPARATION_TIME,
                PlatesDb.Column.INGREDIENTS,
                PlatesDb.Column.PRICE,
                PlatesDb.Column.PHOTO
        );
        Log.d(TAG, "onCreate with SQL: " + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + PlatesDb.TABLE);
        onCreate(db);
    }
}
