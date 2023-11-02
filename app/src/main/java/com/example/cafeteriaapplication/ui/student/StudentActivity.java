package com.example.cafeteriaapplication.ui.student;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteriaapplication.MyDbHelper;
import com.example.cafeteriaapplication.R;
import com.example.cafeteriaapplication.ui.staff.FoodItem;
import java.io.Serializable;


import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity implements MealItemAdapter.CartUpdateListener {
    private RecyclerView itemsRecyclerView;
    private TextView welcomeTextView;
    private Cart cart;
    private TextView cartItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        itemsRecyclerView = findViewById(R.id.itemsRecyclerView);
        welcomeTextView = findViewById(R.id.welcome);
        cart = new Cart();
        cartItemCount = findViewById(R.id.cartItemCount); // Initialize cartItemCount

        // Get the user's name from the intent (replace 'userFullName' with the actual extra name)
        String userFullName = getIntent().getStringExtra("userFullName");

        // Display the "Welcome" message with the user's name
        welcomeTextView.setText("Welcome " + userFullName);

        // Assuming you have a list of food items to display
        List<FoodItem> foodItems = getFoodItemsFromDatabase(); // Implement this method to get your data

        // Create an instance of your custom adapter and set it to the RecyclerView
        MealItemAdapter adapter = new MealItemAdapter(foodItems, this, cart);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemsRecyclerView.setAdapter(adapter);
        adapter.setCartUpdateListener(this);
        // Add a click listener to the cart icon or checkout button to start the CartView activity
        findViewById(R.id.cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the CartView activity and pass the cart object
                Intent cartIntent = new Intent(StudentActivity.this, CartView.class);
                cartIntent.putExtra("cart", cart);
                startActivity(cartIntent);
            }
        });
        Button checkoutButton = findViewById(R.id.checkoutButton);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(StudentActivity.this, CartView.class);
                cartIntent.putExtra("cart", cart);
                startActivity(cartIntent);
                finish();
            }
        });

    }
    public void onCartItemAdded() {
        // Handle the event when an item is added to the cart
        updateCartCount();
    }

    private void updateCartCount() {
        int itemCount = cart.getItems().size();
        Log.d("CartUpdate", "Item count: " + itemCount);

        if (cartItemCount != null) {
            if (itemCount > 0) {
                cartItemCount.setVisibility(View.VISIBLE);
                cartItemCount.setText(String.valueOf(itemCount));
                Log.d("CartUpdate", "CartItemCount visible");
            } else {
                cartItemCount.setVisibility(View.GONE);
                Log.d("CartUpdate", "CartItemCount hidden");
            }
        }
    }


    // Implement this method to get your food items from the database or any other source
    private List<FoodItem> getFoodItemsFromDatabase() {
        List<FoodItem> foodItems = new ArrayList<>();

        MyDbHelper myDbHelper = new MyDbHelper(this);
        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        String[] projection = {
                "name",
                "description",
                "price",
                "image_path"
        };

        Cursor cursor = db.query(
                "food_items", // The table to query
                    projection,    // The columns to return
                    null,           // The columns for the WHERE clause
                    null,           // The values for the WHERE clause
                    null,           // don't group the rows
                    null,           // don't filter by row groups
                    null            // don't order the rows
            );

        try {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                String imagePath = cursor.getString(cursor.getColumnIndexOrThrow("image_path"));

                FoodItem foodItem = new FoodItem(name, description, price, imagePath);
                foodItems.add(foodItem);
            }
        } finally {
            cursor.close();
        }

        return foodItems;
    }

}

