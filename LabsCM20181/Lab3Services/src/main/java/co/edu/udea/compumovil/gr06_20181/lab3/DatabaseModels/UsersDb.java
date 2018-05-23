package co.edu.udea.compumovil.gr06_20181.lab3.DatabaseModels;

import android.provider.BaseColumns;

public class UsersDb {
    public static abstract  class UsersEntry implements BaseColumns{
        public static final String TABLE = "users";

        public static final String NAME = "name";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String PHOTO = "photo";
        public static final String STATE = "state";
    }
}
