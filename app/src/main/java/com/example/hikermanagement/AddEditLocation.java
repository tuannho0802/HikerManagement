package com.example.hikermanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class AddEditLocation extends AppCompatActivity {

    private Spinner difficultySpinner, parkingSpinner;

    private EditText nameEt, locationEt, dateEt, lengthEt;
    private FloatingActionButton fab;

    private String id, name, location, difficultyLevel, parkingAvailable, date, length, addedTime, updatedTime;
    private Boolean isEditMode;

    private ActionBar actionBar;

    private DbHelper dbHelper;

    public AddEditLocation() {
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_location);

        difficultySpinner = findViewById(R.id.difficultyCb);
        parkingSpinner = findViewById(R.id.parkingCb);

        String[] difficultyLevels = {"Easy", "Medium", "Hard"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, difficultyLevels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(adapter);

        String[] parkingOptions = {"Yes", "No"};
        ArrayAdapter<String> parkingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, parkingOptions);
        parkingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        parkingSpinner.setAdapter(parkingAdapter);

        difficultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Capture the selected difficulty level
                difficultyLevel = difficultyLevels[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        parkingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Capture the selected difficulty level
                parkingAvailable = parkingOptions[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });


        dbHelper = new DbHelper(this);

        actionBar = getSupportActionBar();

        //back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //init view
        nameEt = findViewById(R.id.nameEt);
        locationEt = findViewById(R.id.locationEt);
        dateEt = findViewById(R.id.dateEt);
        lengthEt = findViewById(R.id.lengthEt);
        fab = findViewById(R.id.fab);

        //get intent data
        Intent intent = getIntent();
        isEditMode = intent.getBooleanExtra("isEditMode", false);

        if (isEditMode) {
            actionBar.setTitle("Update Location");

            id = intent.getStringExtra("ID");
            name = intent.getStringExtra("NAME");
            location = intent.getStringExtra("LOCATION");
            date = intent.getStringExtra("DATE");
            length = intent.getStringExtra("LENGTH");
            addedTime = intent.getStringExtra("ADDEDTIME");
            updatedTime = intent.getStringExtra("UPDATEDTIME");

            //set value in editTextField
            nameEt.setText(name);
            locationEt.setText(location);
            dateEt.setText(date);
            lengthEt.setText(length);


        } else {
            actionBar.setTitle("Add Location");
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });


    }

    private void saveData() {
        name = nameEt.getText().toString();
        location = locationEt.getText().toString();
        date = dateEt.getText().toString();
        length = lengthEt.getText().toString();
        // Retrieve the selected difficulty level from the Spinner
        difficultyLevel = difficultySpinner.getSelectedItem().toString();

        // Retrieve the selected parking value from the Spinner
        parkingAvailable = parkingSpinner.getSelectedItem().toString();

        String timeStamp = "" + System.currentTimeMillis();

        // Check if required fields are empty
        if (name.isEmpty() || location.isEmpty() || date.isEmpty() || length.isEmpty() || difficultyLevel.isEmpty() || parkingAvailable.isEmpty()) {
            // Display a toast message indicating that all fields are required
            Toast.makeText(getApplicationContext(), "All fields are required. Please fill in the required information.", Toast.LENGTH_SHORT).show();
            return; // Exit the method to prevent further execution
        }

        if (isEditMode) {
            dbHelper.updateLocation(
                    "" + id,
                    "" + name,
                    "" + location,
                    "" + date,
                    "" + difficultyLevel,
                    "" + parkingAvailable,
                    "" + length,
                    "" + addedTime,
                    "" + timeStamp
            );

            Toast.makeText(getApplicationContext(), "Updated Successfully....", Toast.LENGTH_SHORT).show();

        } else {
            // Retrieve the selected difficulty level from the Spinner
            difficultyLevel = difficultySpinner.getSelectedItem().toString();

            // Retrieve the selected parking value from the Spinner
            parkingAvailable = parkingSpinner.getSelectedItem().toString();

            long newId = dbHelper.insertLocation(
                    "" + name,
                    "" + location,
                    "" + date,
                    "" + length,
                    "" + parkingAvailable,
                    "" + timeStamp,
                    "" + timeStamp,
                    "" + difficultyLevel

            );
            Toast.makeText(getApplicationContext(), "Inserted Successfully.... " + newId, Toast.LENGTH_SHORT).show();
        }

        // Navigate to the main activity
        Intent intent = new Intent(AddEditLocation.this, MainActivity.class);
        startActivity(intent);
        finish(); // Close the current activity
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


}
