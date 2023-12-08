package com.example.hikermanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    //view
    private FloatingActionButton fab;
    private RecyclerView locationRv;

    //adapter
    private AdapterLocation adapterLocation;

    //db
    private DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init db
        dbHelper = new DbHelper(this);

        //initialization
        fab = findViewById(R.id.fab);
        locationRv = findViewById(R.id.locationRv);

        locationRv.setHasFixedSize(true);

        // add listener
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // move to new activity to add location
                Intent intent = new Intent(MainActivity.this, AddEditLocation.class);
                intent.putExtra("isEditMode", false);
                startActivity(intent);
            }
        });
        loadData();
    }

    private void loadData() {
        adapterLocation = new AdapterLocation(this, dbHelper.getAllData());
        locationRv.setAdapter(adapterLocation);
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadData(); // refresh data
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_top_menu, menu);

        //get search item from menu
        MenuItem item = menu.findItem(R.id.searchLocation);
        //search area
        SearchView searchView = (SearchView) item.getActionView();
        //set max value for width

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchLocation(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchLocation(newText);
                return true;
            }
        });


        return true;

    }

    private void searchLocation(String query) {
        adapterLocation = new AdapterLocation(this, dbHelper.getSearchLocation(query));
        locationRv.setAdapter(adapterLocation);
    }

    //handle delete all
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deleteAllLocation) {
            showDeleteAllConfirmationDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteAllConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All Locations");
        builder.setMessage("Are you sure you want to delete all hiking locations?");

        // Add positive button (Yes)
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked Yes, delete all locations
                dbHelper.deleteAllLocation();
                onResume();
            }
        });

        // Add negative button (No)
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked No, do nothing
            }
        });

        // Show the dialog
        builder.show();
    }
}