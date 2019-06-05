package com.example.gasdelivery.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.gasdelivery.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME = "d.db";
    private static final int DB_VER = 1;


    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Order> getCarts(String userPhone) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"ID","UserPhone", "ProductId", "ProductName", "Quantity",  "Price"};

        String sqlTable = "OrderDetail";
        qb.setTables(sqlTable);

        Cursor c;
        c = qb.query(db, sqlSelect, "UserPhone=?", new String[]{userPhone}, null, null, null);
        final List<Order> result = new ArrayList<>();
        if (c.moveToFirst()) {

            do {
                result.add(new Order(c.getInt(c.getColumnIndex("ID")),
                        c.getString(c.getColumnIndex("UserPhone")),
                        c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price"))));
            } while (c.moveToNext());

        }
        return result;
    }


    public void addToCart(Order order) {

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(UserPhone, ProductId, ProductName, Quantity, Price) VALUES('%s','%s','%s','%s','%s');",
                order.getUserPhone(),
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice());
        db.execSQL(query);

    }


    public void cleanCart(String userPhone) {

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail Where UserPhone='%s'", userPhone);
        db.execSQL(query);

    }

    public int getCountCart(String userPhone) {
        int count = 0;
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT COUNT(*) FROM OrderDetail WHERE UserPhone='%s'", userPhone);
        Cursor  cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                count =cursor.getInt(0);
            }while (cursor.moveToNext());
        }
        return count;
    }

    public void deleteItem(Order order){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail Where ProductName='%s' and ID = '%d'", order.getProductName(), order.getID());
        db.execSQL(query);

    }
}