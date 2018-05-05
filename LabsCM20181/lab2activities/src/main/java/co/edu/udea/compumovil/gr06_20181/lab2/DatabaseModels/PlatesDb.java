package co.edu.udea.compumovil.gr06_20181.lab2.DatabaseModels;

import android.provider.BaseColumns;

public class PlatesDb {
    public static abstract  class PlatesEntry implements BaseColumns{
        public static final String TABLE = "plates";

        public static final String NAME = "name";
        public static final String KIND = "kind";
        public static final String PREPARATION_TIME = "preparationTime";
        public static final String PRICE = "price";
        public static final String PHOTO = "photo";
    }
}
