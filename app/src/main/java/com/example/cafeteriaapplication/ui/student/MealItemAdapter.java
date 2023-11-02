package com.example.cafeteriaapplication.ui.student;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteriaapplication.R;
import com.example.cafeteriaapplication.ui.staff.FoodItem;

import java.io.IOException;
import java.util.List;

public class MealItemAdapter extends RecyclerView.Adapter<MealItemAdapter.ViewHolder> {

    private List<FoodItem> foodItems;
    private Context context;
    private Cart cart;
    public interface CartUpdateListener {
        void onCartItemAdded();
    }

    private CartUpdateListener cartUpdateListener;

    public void setCartUpdateListener(CartUpdateListener listener) {
        this.cartUpdateListener = listener;
    }

    public MealItemAdapter(List<FoodItem> foodItems, Context context, Cart cart) {
        this.foodItems = foodItems;
        this.context = context;
        this.cart = cart;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int currentPosition = holder.getAdapterPosition();

        if (currentPosition != RecyclerView.NO_POSITION) {
            FoodItem foodItem = foodItems.get(currentPosition);

            holder.itemNameTextView.setText(foodItem.getName());
            holder.itemDescriptionTextView.setText(foodItem.getDescription());
            holder.itemPriceTextView.setText(String.format("$%.2f", foodItem.getPrice()));

            // Load and display the image using the Uri
            if (foodItem.getImagePath() != null) {
                try {
                    Uri imageUri = Uri.parse(foodItem.getImagePath());
                    Bitmap myBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
                    holder.itemImageView.setImageBitmap(myBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle the exception (e.g., display a placeholder image)
                }
            } else {
                // Handle the case where the image path is null (e.g., display a placeholder image)
            }

            // Set click listeners for the quantity buttons
            holder.incrementQuantityButton.setOnClickListener(v -> {
                // Increase the quantity and update the TextView
                int currentQuantity = Integer.parseInt(holder.quantityTextView.getText().toString());
                currentQuantity++;
                holder.quantityTextView.setText(String.valueOf(currentQuantity));
            });

            holder.decrementQuantityButton.setOnClickListener(v -> {
                // Decrease the quantity and update the TextView (with a minimum value of 0)
                int currentQuantity = Integer.parseInt(holder.quantityTextView.getText().toString());
                if (currentQuantity > 0) {
                    currentQuantity--;
                    holder.quantityTextView.setText(String.valueOf(currentQuantity));
                }
            });

            holder.buyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get the selected FoodItem and its quantity
                    FoodItem foodItem = foodItems.get(currentPosition);
                    int quantity = Integer.parseInt(holder.quantityTextView.getText().toString());

                    if (quantity > 0) {  // Check if quantity is greater than zero
                        // Add the item to the cart
                        cart.addItem(foodItem, quantity);
                        if (cartUpdateListener != null) {
                            cartUpdateListener.onCartItemAdded();
                        }
                        // Optionally, display a confirmation message to the user
                        Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
                    } else {
                        // Optionally, display a message indicating that the quantity should be greater than zero.
                        Toast.makeText(context, "Quantity should be greater than zero", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemNameTextView;
        public TextView itemDescriptionTextView;
        public TextView itemPriceTextView;
        public ImageView itemImageView;
        public Button incrementQuantityButton;
        public TextView quantityTextView;
        public Button decrementQuantityButton;
        public Button buyButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemName);
            itemDescriptionTextView = itemView.findViewById(R.id.itemDescription);
            itemPriceTextView = itemView.findViewById(R.id.itemPrice);
            itemImageView = itemView.findViewById(R.id.itemImage);
            incrementQuantityButton = itemView.findViewById(R.id.incrementQuantityButton);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            decrementQuantityButton = itemView.findViewById(R.id.decrementQuantityButton);
            buyButton = itemView.findViewById(R.id.buyButton);
        }
    }
}