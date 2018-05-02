package co.edu.udea.compumovil.gr06_20181.lab2.DbHelpers;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import co.edu.udea.compumovil.gr06_20181.lab2.DatabaseModels.UsersDb;

public class DbUsersHelper extends SQLiteOpenHelper {

    private static final String TAG = DbUsersHelper.class.getSimpleName();

    public DbUsersHelper(Context context){
        super(context, UsersDb.DB_NAME, null, UsersDb.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format(
                "create table if not exists %s (%s integer primary key autoincrement, %s text," +
                        " %s text, %s text, %s blob, %s text)",
                UsersDb.TABLE,
                UsersDb.Column.ID,
                UsersDb.Column.NAME,
                UsersDb.Column.EMAIL,
                UsersDb.Column.PASSWORD,
                UsersDb.Column.PHOTO,
                UsersDb.Column.STATE
        );

        try {
            db.execSQL(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insertData(String name, String email, String password, byte[] photo, String state){
        SQLiteDatabase db = getWritableDatabase();

        String sql = "insert into " + UsersDb.TABLE + " values (null, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);

        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, email);
        statement.bindString(3, password);
        statement.bindBlob(4, photo);
        statement.bindString(5, state);

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
                "from " + UsersDb.TABLE;
        try {
            cursor = db.rawQuery(sql, null);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return cursor;
    }

    public Cursor getDataByEmail(String email){
        Cursor cursor = null;
        SQLiteDatabase db = getReadableDatabase();

        String sql = "select name, email, password, photo, state " +
                " from " + UsersDb.TABLE +
                " where email = '" + email + "'";

        try {
            cursor = db.rawQuery(sql, null);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return cursor;
    }

    public Cursor searchUser(String email){
        Cursor cursor = null;
        SQLiteDatabase db = getReadableDatabase();

        String sql = "select email, password, state " +
                "from " + UsersDb.TABLE +
                " where email = '" + email + "'";

        try {
            cursor = db.rawQuery(sql, null);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return cursor;
    }

    public void updateState(String email, String state){
        SQLiteDatabase db = getWritableDatabase();

        String sql = "update " + UsersDb.TABLE +
                " set state = '" + state + "'" +
                " where email = '" + email + "'";

        try {
            db.execSQL(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Cursor findByState(String state){
        Cursor cursor = null;
        SQLiteDatabase db = getReadableDatabase();

        String sql = "select id, name, email, password, photo, state " +
                "from " + UsersDb.TABLE +
                " where state = '"+ state +"'";

        try {
            cursor = db.rawQuery(sql, null);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + UsersDb.TABLE);
        onCreate(db);
    }
}
