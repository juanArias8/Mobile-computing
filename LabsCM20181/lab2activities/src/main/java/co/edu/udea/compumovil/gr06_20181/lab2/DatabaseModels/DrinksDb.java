package co.edu.udea.compumovil.gr06_20181.lab2.DatabaseModels;

import android.provider.BaseColumns;

public class DrinksDb {
    public static final String DB_NAME = "restaurant.db";
    public static final int DB_VERSION = 1;
    public static String TABLE = "drinks";

    public class Column{
        public static final String ID = BaseColumns._ID;
        public static final String NAME = "name";
        public static final String PRICE = "price";
        public static final String PHOTO = "photo";
    }
}
