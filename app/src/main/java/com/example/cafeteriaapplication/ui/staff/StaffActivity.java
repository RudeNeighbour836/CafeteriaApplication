package com.example.cafeteriaapplication.ui.staff;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteriaapplication.MyDbHelper;
import com.example.cafeteriaapplication.R;

import java.util.List;

public class StaffActivity extends AppCompatActivity {

    // Declare UI elements
    private Button imageUploadButton;
    private EditText nameEditText;
    private EditText descriptionEditText;
    private EditText priceEditText;
    private Button submitButton;

    private RecyclerView recyclerView;
    private List<FoodItem> foodItemList;
    private UploadedItemsAdapter adapter;

    // Declare a constant for image selection
    private static final int PICK_IMAGE_REQUEST = 1;
    private String imagePath; // Store the selected image path
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 1; // You can choose any integer value
    private static final int READ_REQUEST_CODE = 42;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        MyDbHelper myDbHelper;
        myDbHelper = new MyDbHelper(this);
        recyclerView = findViewById(R.id.recyclerViewUpload);

        // Initialize UI elements
        imageUploadButton = findViewById(R.id.imageUploadButton);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        priceEditText = findViewById(R.id.priceEditText);
        nameEditText =findViewById(R.id.nameEditText);
        submitButton = findViewById(R.id.submitButton);

        List<FoodItem> uploadedItems = myDbHelper.getUploadedItems();
        adapter = new UploadedItemsAdapter(uploadedItems);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    // Set click listener for the "Upload Image" button
        imageUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent to open the image picker
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        // Set click listener for the "Submit" button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("StaffActivity", "button clickeD: ");
                // Retrieve user inputs from the EditText fields
                String name = nameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                double price = Double.parseDouble(priceEditText.getText().toString());

                // Insert the data into the database
                long newRowId = myDbHelper.insertFoodItem(name, description, price, imagePath);

                if (newRowId != -1) {
                    // Data was inserted successfully
                    Log.d("StaffActivity", "Data inserted successfully with row ID: " + newRowId);
                } else {
                    // Failed to insert data
                    Log.e("StaffActivity", "Failed to insert data.");
                }

                // You can also start a new activity or display a confirmation message
                Intent intent = new Intent(StaffActivity.this, ConfirmationActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("description", description);
                intent.putExtra("price", price);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to load and display the image
                loadAndDisplayImage();
            } else {
                // Permission denied, handle it (e.g., display an error message)
                // You can show a message to the user or take appropriate action
            }
        }
    }


    // Handle the result of the image picker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // The user has selected an image
            Uri selectedImageUri = data.getData();
            imagePath = selectedImageUri.toString(); // Store the selected image path

            // Show the selected image in the ImageView
            ImageView imagePreview = findViewById(R.id.imagePreview);
            imagePreview.setImageURI(selectedImageUri);
            imagePreview.setVisibility(View.VISIBLE); // Make the ImageView visible
        }
    }
    private void loadAndDisplayImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }
}
