package com.example.cafeteriaapplication.ui.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.cafeteriaapplication.R;

import java.util.List;

public class CartView extends AppCompatActivity {
    private Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);
        Log.d("CartView", "CartView activity started.");
        TextView totalPriceTextView = findViewById(R.id.totalPriceTextView);


        // Retrieve the cart from the intent and assign it to the class-level field
        cart = getIntent().getParcelableExtra("cart");

        if (cart != null) {
            Log.d("CartView", "Cart object is not null.");
            // Now you can use the cart object
        } else {
            Log.d("CartView", "Cart object is null.");
            // Handle the case where the cart object is null
        }

        RecyclerView cartRecyclerView = findViewById(R.id.cartRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        CartItemAdapter adapter = new CartItemAdapter(cart.getItems());
        cartRecyclerView.setLayoutManager(layoutManager);
        cartRecyclerView.setAdapter(adapter);
        double total = 0.0;
        List<CartItem> cartItems = cart.getItems();
        for (CartItem cartItem : cartItems) {
            total += cartItem.getTotalPrice();
        }
        String totalText = String.format("Total: Ksh.%.2f", total);
        totalPriceTextView.setText(totalText);

    }
}

