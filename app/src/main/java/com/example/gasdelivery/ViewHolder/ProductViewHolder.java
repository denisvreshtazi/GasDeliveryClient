package com.example.gasdelivery.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gasdelivery.Interface.ItemClickListener;
import com.example.gasdelivery.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView product_name;
    public TextView product_price;
    public ImageView product_image;
    public Button btnCart;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ProductViewHolder(View itemView) {
        super(itemView);

        product_name = (TextView)itemView.findViewById(R.id.product_name);
        product_price = (TextView)itemView.findViewById(R.id.product_price);
        product_image = (ImageView)itemView.findViewById(R.id.product_image);
        btnCart = (Button)itemView.findViewById(R.id.btnCart);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
