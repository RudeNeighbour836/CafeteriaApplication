package com.example.cafeteriaapplication.ui.staff;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteriaapplication.R;

import java.io.IOException;
import java.util.List;

public class UploadedItemsAdapter extends RecyclerView.Adapter<UploadedItemsAdapter.UploadedItemViewHolder> {
    private List<FoodItem> foodItems;

    public UploadedItemsAdapter(List<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }

    @NonNull
    @Override
    public UploadedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_uploaded_layout, parent, false);
        return new UploadedItemViewHolder(view);
    }

    public void onBindViewHolder(@NonNull UploadedItemViewHolder holder, int position) {
        FoodItem item = foodItems.get(position);

        // Check if the image path is not null
        if (item.imagePath != null) {
            try {
                Uri imageUri = Uri.parse(item.imagePath);
                Bitmap myBitmap = MediaStore.Images.Media.getBitmap(holder.itemView.getContext().getContentResolver(), imageUri);
                holder.uploadedItemImage.setImageBitmap(myBitmap);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception (e.g., display a placeholder image)
            }
        } else {
            // Handle the case where the image path is null (e.g., display a placeholder image)
        }

        // Set other item data
        holder.itemName.setText(item.getDescription());
        holder.itemDescription.setText(item.getDescription());
        holder.itemPrice.setText(String.valueOf(item.getPrice()));
    }




    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public static class UploadedItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView uploadedItemImage;
        public TextView itemName;
        public TextView itemDescription;
        public TextView itemPrice;

        public UploadedItemViewHolder(@NonNull View itemView) {
            super(itemView);
            uploadedItemImage = itemView.findViewById(R.id.uploadedItemImage);
            itemName = itemView.findViewById(R.id.uploadedItemName);
            itemDescription = itemView.findViewById(R.id.uploadedItemDescription);
            itemPrice = itemView.findViewById(R.id.uploadedItemPrice);
        }
    }
}

