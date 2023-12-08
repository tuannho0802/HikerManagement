package com.example.hikermanagement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;

import java.util.ArrayList;

public class AdapterLocation extends RecyclerView.Adapter<AdapterLocation.LocationViewHolder> {

    private DbHelper dbHelper;

    private Context context;
    private ArrayList<ModelLocation> locationList;

    public AdapterLocation(Context context, ArrayList<ModelLocation> locationList) {
        this.context = context;
        this.locationList = locationList;
        this.dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_location_item, parent, false);
        LocationViewHolder vh = new LocationViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        ModelLocation modelLocation = locationList.get(position);

        // Get data
        String id = modelLocation.getId();
        String name = modelLocation.getName();
        String date = modelLocation.getDate();
        String location = modelLocation.getLocation();
        String length = modelLocation.getLength();
        String parking = modelLocation.getParking();
        String difficulty = modelLocation.getDifficulty();
        String addedTime = modelLocation.getAddedTime();
        String updatedTime = modelLocation.getUpdatedTime();

        // Set data in view
        holder.hikerName.setText(name);
        holder.locationName.setText(location);

        // Handle item click and show location details
        holder.relativeLayout.setOnClickListener(v -> {
            // Create intent to move to LocationDetails Activity with location id as reference
            Intent intent = new Intent(context, LocationDetails.class);
            intent.putExtra("locationId", id);
            context.startActivity(intent); // Now get data from details Activity
            // Display the customized Toast message
            Toast.makeText(context, "Hello Hiker: " + name, Toast.LENGTH_SHORT).show();
        });

        // handle editBtn click
        holder.locationEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create intent to move AddEditActivity to update data
                Intent intent = new Intent(context, AddEditLocation.class);
                //pass the value of current position
                intent.putExtra("ID", id);
                intent.putExtra("NAME", name);
                intent.putExtra("LOCATION", location);
                intent.putExtra("DATE", date);
                intent.putExtra("LENGTH", length);
                intent.putExtra("DIFFICULTY", difficulty);
                intent.putExtra("LENGTH", parking);
                intent.putExtra("ADDEDTIME", addedTime);
                intent.putExtra("UPDATEDTIME", updatedTime);


                // pass a boolean data to define it is for edit purpose
                intent.putExtra("isEditMode", true);

                //start intent
                context.startActivity(intent);


            }
        });

        // handle delete click
        holder.locationDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //database helper class reference
                dbHelper.deleteLocation(id);

                // Refresh data by calling the onResume method of MainActivity
                ((MainActivity) context).onResume();

                // Show Toast notification
                Toast.makeText(context, "Location deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {

        // View for row_location_item
        TextView hikerName, locationName, locationEdit, locationDelete;
        ImageView locationImage, locationPin;
        RelativeLayout relativeLayout;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize view
            hikerName = itemView.findViewById(R.id.hiker_name);
            locationName = itemView.findViewById(R.id.location_name);
            locationImage = itemView.findViewById(R.id.location_image);
            locationPin = itemView.findViewById(R.id.location_pin);

            locationEdit = itemView.findViewById(R.id.location_edit);
            locationDelete = itemView.findViewById(R.id.location_delete);

            relativeLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
