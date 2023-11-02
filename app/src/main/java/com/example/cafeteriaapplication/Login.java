package com.example.cafeteriaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cafeteriaapplication.ui.staff.StaffActivity;
import com.example.cafeteriaapplication.ui.student.StudentActivity;

public class Login extends AppCompatActivity {
    private SQLiteDatabase db;
    private MyDbHelper mDbHelper;
    private boolean userLoggedIn = false;

    private static final String[] USER_COLUMNS = {
            "_id",
            "staff",
            "sid",
            "password",
            "full_name"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDbHelper = new MyDbHelper(this);
        db = mDbHelper.getReadableDatabase();

        Button btnLogin = findViewById(R.id.button_login);
        Button register = findViewById(R.id.button_register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputUserId = findViewById(R.id.input_id);
                String id = inputUserId.getText().toString();

                EditText inputPassword = findViewById(R.id.input_password);
                String password = inputPassword.getText().toString();

                String selection = "sid = ? and password = ?";
                String[] selectionArgs = {id, password};

                try (Cursor cursor = db.query(
                        "users", USER_COLUMNS, selection, selectionArgs, null, null, null
                )) {
                    if (cursor.moveToFirst()) {
                        int staffStatus = cursor.getInt(cursor.getColumnIndexOrThrow("staff"));
                        String fullName = cursor.getString(cursor.getColumnIndexOrThrow("full_name"));

                        loginUser();
                        Toast.makeText(Login.this, "Welcome " + fullName, Toast.LENGTH_SHORT).show();

                        Class<?> targetActivity = staffStatus == 1 ? StaffActivity.class : StudentActivity.class;
                        Intent intent = new Intent(Login.this, targetActivity);
                        intent.putExtra("userFullName", fullName);
                        startActivity(intent);

                        inputUserId.setText("");
                        inputPassword.setText("");
                    } else {
                        Toast.makeText(Login.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                        inputPassword.setText("");
                    }
                } catch (Exception e) {
                    // Handle any exceptions, e.g., database query errors
                    e.printStackTrace();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loginUser() {
        userLoggedIn = true;
    }

    @Override
    public void onBackPressed() {
        if (userLoggedIn) {
            super.onBackPressed(); // Allow back navigation
        } else {
            // User is not logged in, prevent back navigation
            // You can display a message or take appropriate action here
        }
    }
}
