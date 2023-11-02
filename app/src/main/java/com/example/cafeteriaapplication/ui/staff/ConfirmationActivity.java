package com.example.cafeteriaapplication.ui.staff;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import com.example.cafeteriaapplication.Login;
import com.example.cafeteriaapplication.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        // Get the confirmation message TextView and back button
        TextView confirmationMessage = findViewById(R.id.confirmationMessage);
        Button backButton = findViewById(R.id.backButton);

        // You can set the confirmation message here
        confirmationMessage.setText("Your item has been uploaded!");

        // Set a click listener for the "Back to Staff Menu" button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // You can navigate back to the staff menu or any other screen as needed
                Intent intent = new Intent(ConfirmationActivity.this, StaffActivity.class);
                startActivity(intent);
                finish(); // Finish this activity to prevent going back to it
            }
        });

        Button exitButton = findViewById(R.id.exitButton);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your exit logic here
                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmationActivity.this);
                builder.setTitle("Exit Confirmation");
                builder.setMessage("Are you sure you want to exit?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ConfirmationActivity.this, Login.class);
                        startActivity(intent);
                        finish(); // Close the activity
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User canceled exit, do nothing
                    }
                });
                builder.show();
            }
        });

    }
}
