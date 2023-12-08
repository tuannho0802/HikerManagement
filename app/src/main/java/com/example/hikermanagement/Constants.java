package com.example.hikermanagement;

public class Constants {

    // database or db name
    public static final String DATABASE_NAME = "LOCATION_DB"; // Updated database name
    // database version
    public static final int DATABASE_VERSION = 1;

    // table name
    public static final String TABLE_NAME = "LOCATION_TABLE"; // Updated table name

    // table column or field name
    public static final String C_ID = "ID";
    public static final String C_NAME = "NAME";
    public static final String C_LOCATION = "LOCATION";
    public static final String C_DIFFICULTY = "DIFFICULTY";
    public static final String C_DATE = "DATE";
    public static final String C_LENGTH = "LENGTH";
    public static final String C_PARKING = "PARKING";
    public static final String C_ADDED_TIME = "ADDED_TIME";
    public static final String C_UPDATED_TIME = "UPDATED_TIME";

    // query for create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( "
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_NAME + " TEXT, "
            + C_LOCATION + " TEXT, "
            + C_DIFFICULTY + " TEXT, "
            + C_DATE + " TEXT, "
            + C_LENGTH + " TEXT, "
            + C_PARKING + " TEXT, "
            + C_ADDED_TIME + " TEXT, "
            + C_UPDATED_TIME + " TEXT"
            + " );";
}
