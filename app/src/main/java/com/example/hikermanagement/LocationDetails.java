package com.example.hikermanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

public class LocationDetails extends AppCompatActivity {

    // View
    private TextView nameTv, locationTv, dateTv, lengthTv, difficultyTv, addedTimeTv, updatedTimeTv, parkingTv;

    private String id;

    // Database helper
    private DbHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        // Initialize DB
        dbHelper = new DbHelper(this);

        // Get data from intent
        Intent intent = getIntent();
        id = intent.getStringExtra("locationId");

        // Initialize views
        nameTv = findViewById(R.id.nameTv);
        locationTv = findViewById(R.id.locationTv);
        dateTv = findViewById(R.id.dateTv);
        lengthTv = findViewById(R.id.lengthTv);
        difficultyTv = findViewById(R.id.difficultyTv);
        parkingTv = findViewById(R.id.parkingTv);
        addedTimeTv = findViewById(R.id.addedTimeTv);
        updatedTimeTv = findViewById(R.id.updatedTimeTv);

        loadDataById();
    }

    private void loadDataById() {

        // Get data from database
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_ID + " =\"" + id + "\"";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                // Get data
                String name = "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME));
                String location = "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_LOCATION));
                String date = "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_DATE));
                String length = "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_LENGTH));
                String difficulty = "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_DIFFICULTY));
                String parking = "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PARKING));
                String addTime = "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIME));
                String updateTime = "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_UPDATED_TIME));

                // Convert time to dd/mm/yy hh:mm:aa format
                Calendar calendar = Calendar.getInstance(Locale.getDefault());

                calendar.setTimeInMillis(Long.parseLong(addTime));
                String timeAdd = "" + DateFormat.format("dd/MM/yy hh:mm:aa", calendar);

                calendar.setTimeInMillis(Long.parseLong(updateTime));
                String timeUpdate = "" + DateFormat.format("dd/MM/yy hh:mm:aa", calendar);

                // Set data
                nameTv.setText(name);
                locationTv.setText(location);
                dateTv.setText(date);
                lengthTv.setText(length);
                difficultyTv.setText(difficulty);
                parkingTv.setText(parking);
                addedTimeTv.setText(timeAdd);
                updatedTimeTv.setText(timeUpdate);

            } while (cursor.moveToNext());
        }

        db.close();
    }
}
