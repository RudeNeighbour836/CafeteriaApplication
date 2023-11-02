package com.example.cafeteriaapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.example.cafeteriaapplication.ui.staff.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class MyDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cafeteria.db";
    private static final int DATABASE_VERSION = 1;

    // Define the table name and column names for the users table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "_id";
    private static final String COLUMN_FULL_NAME = "full_name";
    private static final String COLUMN_SID = "sid";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_MOBILE_NUMBER = "mobile_number";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_IS_STAFF = "staff";

    // Define the table name and column names for the food_items table
    private static final String TABLE_FOOD_ITEMS = "food_items";
    private static final String COLUMN_FOOD_ITEM_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_IMAGE_PATH = "image_path";

    private static MyDbHelper instance;

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized MyDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new MyDbHelper(context.getApplicationContext());
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the "users" table
        String createUsersTableQuery = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FULL_NAME + " TEXT, " +
                COLUMN_SID + " INTEGER, " +
                COLUMN_GENDER + " TEXT, " +
                COLUMN_MOBILE_NUMBER + " INTEGER, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_IS_STAFF + " INTEGER" + // Use 1 for staff, 0 for regular users
                ");";
        db.execSQL(createUsersTableQuery);

        // Create the "food_items" table
        String createFoodItemsTableQuery = "CREATE TABLE " + TABLE_FOOD_ITEMS + " (" +
                COLUMN_FOOD_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_PRICE + " REAL, " +
                COLUMN_IMAGE_PATH + " TEXT" +
                ");";
        db.execSQL(createFoodItemsTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the "users" and "food_items" tables if they exist (you can implement migration logic if needed)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD_ITEMS);
        onCreate(db);
    }

    public long insertFoodItem(String name, String description, double price, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        long newRowId = -1;

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_IMAGE_PATH, imagePath);

        try {
            newRowId = db.insert(TABLE_FOOD_ITEMS, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return newRowId;
    }

    public List<FoodItem> getUploadedItems() {
        List<FoodItem> uploadedItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String[] projection = {
                    COLUMN_NAME,
                    COLUMN_DESCRIPTION,
                    COLUMN_PRICE,
                    COLUMN_IMAGE_PATH
            };

            cursor = db.query(TABLE_FOOD_ITEMS, projection, null, null, null, null, null);

            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
                String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_PATH));
                FoodItem foodItem = new FoodItem(name, description, price, imagePath);
                uploadedItems.add(foodItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return uploadedItems;
    }
}
