package com.example.gasdelivery.ViewHolder;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.gasdelivery.Cart;
import com.example.gasdelivery.Database.Database;
import com.example.gasdelivery.Interface.ItemClickListener;
import com.example.gasdelivery.Model.Order;
import com.example.gasdelivery.ProductList;
import com.example.gasdelivery.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class CartViewHolder extends RecyclerView.ViewHolder {

    public TextView txt_cart_name, txt_cart_price;
    public ImageView btn_cart_count;


    public void setTxt_cart_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }

    public CartViewHolder(View itemView) {
        super(itemView);
        txt_cart_name = (TextView)itemView.findViewById(R.id.cart_item_name);
        txt_cart_price =(TextView)itemView.findViewById(R.id.cart_item_price);
        btn_cart_count = (ImageView) itemView.findViewById(R.id.cart_item_cancel);

    }

}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> listData = new ArrayList<>();
    // private Context context;
    private Cart cart;

    public CartAdapter(List<Order> listData, Cart cart) {
        this.listData = listData;
        this.cart = cart;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(cart);
        View itemView = inflater.inflate(R.layout.cart_layout, parent, false);
        return new CartViewHolder(itemView);
    }

    //update the RecyclerView.ViewHolder contents with the item at the given position
    // and also sets up some private fields to be used by RecyclerView.
    @Override
    public void onBindViewHolder(final CartViewHolder holder, final int position) {

        //the price to be showin in euros
        NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.ITALY);
        int price = (Integer.parseInt(listData.get(position).getPrice()));

        //fill the holder
        holder.txt_cart_price.setText(fmt.format(price));
        holder.txt_cart_name.setText(listData.get(position).getProductName());
        holder.btn_cart_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(cart)
                        .setTitle("Delete Item")
                        .setMessage("Are you sure you want to delete this Item?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Order order = listData.get(position);
                                new Database(cart).deleteItem(order);
                                Toast.makeText(cart, "Item Deleted", Toast.LENGTH_SHORT).show();
                                cart.loadListProduct();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.ic_warning_red_24dp).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public Order getItem(int position){return listData.get(position);}

    public void removeItem(int position){
        listData.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Order item,int position){
        listData.add(position, item);
        notifyItemInserted(position);
    }
}
