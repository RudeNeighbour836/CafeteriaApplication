package com.example.cafeteriaapplication.ui.student;

import com.example.cafeteriaapplication.ui.staff.FoodItem;

import android.os.Parcel;
import android.os.Parcelable;
public class CartItem implements Parcelable {
    private FoodItem foodItem;
    private int quantity;

    public CartItem(FoodItem foodItem, int quantity) {
        this.foodItem = foodItem;
        this.quantity = quantity;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return foodItem.getPrice() * quantity;
    }

    // Parcelable implementation
    protected CartItem(Parcel in) {
        foodItem = in.readParcelable(FoodItem.class.getClassLoader());
        quantity = in.readInt();
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable((Parcelable) foodItem, flags);
        dest.writeInt(quantity);
    }
}
