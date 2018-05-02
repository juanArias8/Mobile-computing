package co.edu.udea.compumovil.gr06_20181.lab2.DatabaseModels;

import android.provider.BaseColumns;

public class UsersDb {
    public static final String DB_NAME = "restaurant.db";
    public static final int DB_VERSION = 1;
    public static String TABLE = "users";

    public class Column{
        public static final String ID = BaseColumns._ID;
        public static final String NAME = "name";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String PHOTO = "photo";
        public static final String STATE = "state";
    }
}
