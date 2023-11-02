package com.example.cafeteriaapplication.ui.student;

import com.example.cafeteriaapplication.ui.staff.FoodItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class Cart implements Parcelable {
    private List<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    protected Cart(Parcel in) {
        items = in.createTypedArrayList(CartItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(items);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    public void addItem(FoodItem foodItem, int quantity) {
        CartItem cartItem = new CartItem(foodItem, quantity);
        items.add(cartItem);
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void clearCart() {
        items.clear();
    }
}
