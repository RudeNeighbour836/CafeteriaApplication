package com.example.cafeteriaapplication.ui.student;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteriaapplication.R;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    private List<CartItem> cartItems;

    public CartItemAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view for a cart item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to the ViewHolder's views based on the cart item at the given position
        CartItem cartItem = cartItems.get(position);

        // Set the item name, quantity, and total price
        holder.itemNameTextView.setText(cartItem.getFoodItem().getName());
        holder.itemQuantityTextView.setText(String.valueOf(cartItem.getQuantity()));
        holder.itemTotalPriceTextView.setText(String.format("Ksh.%.2f", cartItem.getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemNameTextView;
        public TextView itemQuantityTextView;
        public TextView itemTotalPriceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.cartItemName);
            itemQuantityTextView = itemView.findViewById(R.id.cartItemQuantity);
            itemTotalPriceTextView = itemView.findViewById(R.id.cartItemTotalPrice);
        }
    }
}


