package co.edu.udea.compumovil.gr06_20181.lab2.DatabaseModels;

import android.provider.BaseColumns;

public class DrinksDb {
    public static abstract  class DrinksEntry implements BaseColumns{
        public static final String TABLE = "drinks";

        public static final String NAME = "name";
        public static final String PRICE = "price";
        public static final String PHOTO = "photo";
    }
}
