package co.edu.udea.compumovil.gr06_20181.lab3.DbHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import co.edu.udea.compumovil.gr06_20181.lab3.DatabaseModels.DrinksDb;
import co.edu.udea.compumovil.gr06_20181.lab3.DatabaseModels.PlatesDb;
import co.edu.udea.compumovil.gr06_20181.lab3.DatabaseModels.UsersDb;
import co.edu.udea.compumovil.gr06_20181.lab3.Models.DrinkModel;
import co.edu.udea.compumovil.gr06_20181.lab3.Models.PlateModel;
import co.edu.udea.compumovil.gr06_20181.lab3.Models.UserModel;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "restaurant.db";
    public static final int DATABASE_VERSION = 1;

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + UsersDb.UsersEntry.TABLE + " ("
                        + UsersDb.UsersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + UsersDb.UsersEntry.NAME + " TEXT NOT NULL, "
                        + UsersDb.UsersEntry.EMAIL + " TEXT NOT NULL, "
                        + UsersDb.UsersEntry.PASSWORD + " TEXT NOT NULL, "
                        + UsersDb.UsersEntry.STATE + " TEXT NOT NULL, "
                        + UsersDb.UsersEntry.PHOTO + " BLOB NOT NULL ) "
        );

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + PlatesDb.PlatesEntry.TABLE + " ("
                        + PlatesDb.PlatesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + PlatesDb.PlatesEntry.NAME + " TEXT NOT NULL, "
                        + PlatesDb.PlatesEntry.KIND + " TEXT NOT NULL, "
                        + PlatesDb.PlatesEntry.PREPARATION_TIME + " TEXT NOT NULL, "
                        + PlatesDb.PlatesEntry.PRICE + " DOUBLE NOT NULL, "
                        + PlatesDb.PlatesEntry.PHOTO + " BLOB NOT NULL )"
        );

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + DrinksDb.DrinksEntry.TABLE + " ("
                        + DrinksDb.DrinksEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + DrinksDb.DrinksEntry.NAME + " TEXT NOT NULL, "
                        + DrinksDb.DrinksEntry.PRICE + " DOUBLE NOT NULL, "
                        + DrinksDb.DrinksEntry.PHOTO + " BLOB NOT NULL ) "
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + PlatesDb.PlatesEntry.TABLE);
        db.execSQL("drop table if exists " + DrinksDb.DrinksEntry.TABLE);
        onCreate(db);
    }

    /*************************************************************************
     *
     * USER TABLE OPERATIONS
     *
     *************************************************************************/
    public ContentValues userToContentValues(UserModel user){
        ContentValues values = new ContentValues();
        values.put(UsersDb.UsersEntry.NAME, user.getName());
        values.put(UsersDb.UsersEntry.EMAIL, user.getEmail());
        values.put(UsersDb.UsersEntry.PASSWORD, user.getPassword());
        values.put(UsersDb.UsersEntry.STATE, user.getState());
        values.put(UsersDb.UsersEntry.PHOTO, user.getPhoto());
        return values;
    }

    public long saveUser(UserModel user){
        SQLiteDatabase db = getWritableDatabase();

        return db.insert(
                UsersDb.UsersEntry.TABLE,
                null,
                userToContentValues(user)
        );
    }

    public Cursor getUserByEmail(String email){
        return getReadableDatabase()
                .query(
                        UsersDb.UsersEntry.TABLE,
                        null,
                        UsersDb.UsersEntry.EMAIL + " LIKE ?",
                        new String[]{email},
                        null,
                        null,
                        null
                );
    }

    public Cursor getUserByStateActive(){
        return getReadableDatabase()
                .query(
                        UsersDb.UsersEntry.TABLE,
                        null,
                        UsersDb.UsersEntry.STATE + " LIKE ?",
                        new String[]{"1"},
                        null,
                        null,
                        null
                );
    }

    public int updateUserState(UserModel user){
        return getWritableDatabase().update(
                UsersDb.UsersEntry.TABLE,
                userToContentValues(user),
                UsersDb.UsersEntry.EMAIL+ " LIKE ?",
                new String[]{user.getEmail()}
        );
    }

    /*************************************************************************
     *
     * PLATES TABLE OPERATIONS
     *
     *************************************************************************/
    public ContentValues plateToContentValues(PlateModel plate){
        ContentValues values = new ContentValues();
        values.put(PlatesDb.PlatesEntry.NAME, plate.getName());
        values.put(PlatesDb.PlatesEntry.KIND, plate.getKind());
        values.put(PlatesDb.PlatesEntry.PREPARATION_TIME, plate.getPreparationTime());
        values.put(PlatesDb.PlatesEntry.PRICE, plate.getPrice());
        values.put(PlatesDb.PlatesEntry.PHOTO, plate.getPhoto());
        return values;
    }

    public long savePlate(PlateModel plate){
        SQLiteDatabase db = getWritableDatabase();

        return db.insert(
                PlatesDb.PlatesEntry.TABLE,
                null,
                plateToContentValues(plate)
        );
    }

    public Cursor getAllPlates(){
        return getReadableDatabase()
                .query(
                        PlatesDb.PlatesEntry.TABLE,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );
    }

    /*************************************************************************
     *
     * DRINKS TABLE OPERATIONS
     *
     *************************************************************************/
    public ContentValues drinkToContentValues(DrinkModel drink){
        ContentValues values = new ContentValues();
        values.put(DrinksDb.DrinksEntry.NAME, drink.getName());
        values.put(DrinksDb.DrinksEntry.PRICE, drink.getPrice());
        values.put(DrinksDb.DrinksEntry.PHOTO, drink.getPhoto());
        return values;
    }

    public long saveDrink(DrinkModel drink){
        SQLiteDatabase db = getWritableDatabase();

        return db.insert(
                DrinksDb.DrinksEntry.TABLE,
                null,
                drinkToContentValues(drink)
        );
    }

    public Cursor getAllDrinks(){
        return getReadableDatabase()
                .query(
                        DrinksDb.DrinksEntry.TABLE,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );
    }
}
