package com.example.hikermanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

//class for database helper
public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create table on the database
        db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // upgrade table if any structure change in db

        // drop table if exists
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        // create table again
        onCreate(db);
    }

    // Insert Function to insert data in the database
    public long insertLocation(String name, String location, String date, String length, String parking, String addedTime, String updatedTime, String difficulty) {
        // get writable database to write data on db
        SQLiteDatabase db = this.getWritableDatabase();

        // create ContentValues class object to save data
        ContentValues contentValues = new ContentValues();

        // id will save automatically as we write a query
        contentValues.put(Constants.C_NAME, name);
        contentValues.put(Constants.C_LOCATION, location);
        contentValues.put(Constants.C_DATE, date);
        contentValues.put(Constants.C_LENGTH, length);
        contentValues.put(Constants.C_PARKING, parking);
        contentValues.put(Constants.C_DIFFICULTY, difficulty);
        contentValues.put(Constants.C_ADDED_TIME, addedTime);
        contentValues.put(Constants.C_UPDATED_TIME, updatedTime);


        // insert data in the row, It will return the id of the record
        long id = db.insert(Constants.TABLE_NAME, null, contentValues);

        // close db
        db.close();

        // return id
        return id;
    }

    // Update Function to update data in the database
    public void updateLocation(String id, String name, String location, String date, String difficulty, String parking, String length, String addedTime, String updatedTime) {
        // get writable database to write data on db
        SQLiteDatabase db = this.getWritableDatabase();

        // create ContentValue class object to save data
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.C_NAME, name);
        contentValues.put(Constants.C_LOCATION, location);
        contentValues.put(Constants.C_DATE, date);
        contentValues.put(Constants.C_LENGTH, length);
        contentValues.put(Constants.C_PARKING, parking);
        contentValues.put(Constants.C_ADDED_TIME, addedTime);
        contentValues.put(Constants.C_UPDATED_TIME, updatedTime);
        contentValues.put(Constants.C_DIFFICULTY, difficulty);

        // update data in row, It will return id of the record
        db.update(Constants.TABLE_NAME, contentValues, Constants.C_ID + " =? ", new String[]{id});

        // close db
        db.close();
    }

    // delete data by id
    public void deleteLocation(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.C_ID + " = ?", new String[]{id});
        db.close();
    }


    // delete all data
    public void deleteAllLocation() {
        //get writable database
        SQLiteDatabase db = getWritableDatabase();

        //query for delete
        db.execSQL("DELETE FROM " + Constants.TABLE_NAME);
        db.close();
    }

    // get data
    public ArrayList<ModelLocation> getAllData() {
        // create arrayList
        ArrayList<ModelLocation> arrayList = new ArrayList<>();
        // sql command query
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME;

        // get readable db
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all records and add to list
        if (cursor.moveToFirst()) {
            do {
                // only id is int type
                ModelLocation modelLocation = new ModelLocation(
                        "" + cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_ID)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_LOCATION)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PARKING)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_DATE)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_LENGTH)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIME)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_UPDATED_TIME)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_DIFFICULTY))
                );
                arrayList.add(modelLocation);
            } while (cursor.moveToNext());
        }
        db.close();
        return arrayList;
    }

    // search data in sql Database
    public ArrayList<ModelLocation> getSearchLocation(String query) {

        // it will return arraylist of modelContact class
        ArrayList<ModelLocation> contactList = new ArrayList<>();

        // get readable database
        SQLiteDatabase db = getReadableDatabase();

        //query for search
        String queryToSearch = "SELECT * FROM " + Constants.TABLE_NAME +
                " WHERE " + Constants.C_NAME + " LIKE '%" + query + "%' OR " +
                Constants.C_LOCATION + " LIKE '%" + query + "%'";

        Cursor cursor = db.rawQuery(queryToSearch, null);

        // looping through all record and add to list
        if (cursor.moveToFirst()) {
            do {
                ModelLocation modelContact = new ModelLocation(
                        // only id is integer type
                        "" + cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_ID)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_LOCATION)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PARKING)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_DATE)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_LENGTH)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIME)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_UPDATED_TIME)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_DIFFICULTY))
                );
                contactList.add(modelContact);
            } while (cursor.moveToNext());
        }
        db.close();
        return contactList;

    }
}
