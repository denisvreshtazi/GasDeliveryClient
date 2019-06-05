package com.example.gasdelivery.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gasdelivery.Interface.ItemClickListener;
import com.example.gasdelivery.R;

//to display a scrolling list of elements based on a data set, is used RecyclerView.ViewHolder
//              that displays a  single item with a view
public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtCategoryName;
    public ImageView imageView;

    private ItemClickListener itemClickListener;

    public CategoryViewHolder(View itemView) {
        super(itemView);

        txtCategoryName = (TextView)itemView.findViewById(R.id.category_name);
        imageView = (ImageView)itemView.findViewById(R.id.category_image);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void onClick(View view){
        itemClickListener.onClick(view, getAdapterPosition(), false);

    }

}
