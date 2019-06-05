package com.example.gasdelivery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gasdelivery.Common.Common;
import com.example.gasdelivery.Database.Database;
import com.example.gasdelivery.Interface.ItemClickListener;
import com.example.gasdelivery.Model.Order;
import com.example.gasdelivery.Model.Product;
import com.example.gasdelivery.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase    database;
    DatabaseReference   productList;


    //Add product Details for the order to be submited
    public TextView product_name;
    public TextView product_price;
    public TextView product_quantity;

    FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter;
    public Button btnCart;

    String catId = "";
    String prodId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        //Firebase DB
        database = FirebaseDatabase.getInstance();
        productList = database.getReference("Product");

        //Assign the container of the products
        recyclerView = (RecyclerView)findViewById(R.id.recycler_product);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        product_name = (TextView)findViewById(R.id.product_name);
        product_price = (TextView)findViewById(R.id.product_price);
        product_quantity = product_name;

        btnCart = (Button)findViewById(R.id.btnCart);

        //Get Intenet here
        if(getIntent() != null){
            catId = getIntent().getStringExtra("CatId");
        }
        if(!catId.isEmpty()){
            loadListProduct(catId);
        }

    }

    //Load all the products, and fil the cardviews with the details
    private void loadListProduct(final String catId) {
        adapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(Product.class, R.layout.product_item,
                ProductViewHolder.class,
                productList.orderByChild("CategoryId").equalTo(catId)){//to select all products with matched categoryId


            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, final Product model, int position) {
                NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.ITALY);
                viewHolder.product_name.setText(model.getName());
                viewHolder.product_price.setText(fmt.format(Integer.parseInt(model.getPrice())));
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.product_image);

                final Product local = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(ProductList.this, ""+local.getName(), Toast.LENGTH_SHORT).show();
                    }
                });

                viewHolder.btnCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //assign an id to the element
                        prodId = String.format("%s%s", local.getCategoryId(), local.getName());
                        String name = local.getName();
                        String price = local.getPrice();
                        new Database(getBaseContext()).addToCart(new Order(Integer.parseInt(local.getCategoryId()),
                                Common.currentUser.getPhone(),
                                prodId,
                                name,
                                name,
                                price
                        ));
                      //  Toast.makeText(ProductList.this, Common.currentUser.getPhone()+"_"+prodId, Toast.LENGTH_SHORT).show();
                        Toast.makeText(ProductList.this, "ADDED TO CART", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };
        //Set adapter
        recyclerView.setAdapter(adapter);
    }
}
