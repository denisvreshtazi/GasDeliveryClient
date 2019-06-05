package com.example.gasdelivery;

import android.content.Context;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gasdelivery.Common.Common;
import com.example.gasdelivery.Database.Database;
import com.example.gasdelivery.Model.Order;
import com.example.gasdelivery.Model.Request;
import com.example.gasdelivery.ViewHolder.CartAdapter;
//import com.example.gasdelivery.ViewHolder.CartViewHolder;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

import android.app.Dialog;

public class Cart extends AppCompatActivity  {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase    database;
    DatabaseReference   requests;

    public TextView    txtTotalPrice;
    FButton btnPlace;
    ImageView btn_cacnel;

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;

    String[] title;
    String spinner_item;

    SpinnerAdapter spinnerAdapter;
    FButton negative_button;
    FButton positive_button;
    Place shippingaddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Start Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        //Init
        recyclerView = (RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.total);
        btnPlace = (FButton)findViewById(R.id.btnPlaceOrder);
        btn_cacnel=(ImageView)findViewById(R.id.cart_item_cancel);


        spinnerAdapter= new SpinnerAdapter(getApplicationContext());
        title = getResources().getStringArray(R.array.time_chooser);
        //place the order
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        loadListProduct();

    }

    //Start the alertdialog based on the order_fill_time_address layout
    private void showAlertDialog() {

        final Dialog dialog = new Dialog(Cart.this);
        dialog.setContentView(R.layout.order_fill_time_address);
        dialog.setTitle("Insert Address and Time");

         Spinner spinner = (Spinner) dialog.findViewById(R.id.time_spinner);

        final EditText edittext = (EditText) dialog.findViewById(R.id.order_alert_adress);
        spinner.setAdapter(spinnerAdapter);
        positive_button = (FButton)dialog.findViewById(R.id.positive_button);
        negative_button = (FButton)dialog.findViewById(R.id.negative_button);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                spinner_item = title[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        positive_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edittext.length()==0){
                    Toast.makeText(getApplicationContext(),"address shouldnt be empty" , Toast.LENGTH_LONG).show();
                }
                else {
                    Request request = new Request(Common.currentUser.getPhone(),
                            Common.currentUser.getName(),
                            edittext.getText().toString(),
                            spinner_item,
                            txtTotalPrice.getText().toString(),
                            cart);
                    requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);
                    //delete cart
                    new Database(getBaseContext()).cleanCart(Common.currentUser.getPhone());
                    Toast.makeText(Cart.this, "Order placed", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        negative_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    public void loadListProduct() {
        cart = new Database(this).getCarts(Common.currentUser.getPhone());
        adapter = new CartAdapter(cart, this);
        recyclerView.setAdapter(adapter);

        //total Price
        int total = 0;
        for (Order  order:cart)
           total += (Integer.parseInt(order.getPrice()));

        NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.ITALY);
        txtTotalPrice.setText(fmt.format(total));
    }

    public class SpinnerAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater mInflater;

        public SpinnerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ListContent holder;
            View v = convertView;
            if (v == null) {
                mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                v = mInflater.inflate(R.layout.row_textview, null);
                holder = new ListContent();
                holder.text = (TextView) v.findViewById(R.id.textView1);

                v.setTag(holder);
            } else {
                holder = (ListContent) v.getTag();
            }

            holder.text.setText(title[position]);

            return v;
        }
    }

    static class ListContent {
        TextView text;
    }
}
