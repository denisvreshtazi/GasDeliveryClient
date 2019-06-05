package com.example.gasdelivery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gasdelivery.Common.Common;
import com.example.gasdelivery.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import info.hoang8f.widget.FButton;

public class SignIn extends AppCompatActivity {
    EditText edtPhone, edtPassword;
    FButton btnSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Assign the buttons and editTexts
        edtPhone=(MaterialEditText)findViewById(R.id.edtPhone);
        edtPassword=(MaterialEditText)findViewById(R.id.edtPassword);
        btnSignIn = (FButton)findViewById(R.id.btnSignIn);

        //Init firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("Waiting...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Check if User exists in database
                        if(dataSnapshot.child(edtPhone.getText().toString()).exists()){
                            mDialog.dismiss();
                            //Get user info
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            user.setPhone(edtPhone.getText().toString()); // Set Phone

                            if(user.getPassword().equals(edtPassword.getText().toString())){
                                Toast.makeText(SignIn.this, "Sign In successfully !", Toast.LENGTH_SHORT).show();
                                Intent homeIntent = new Intent(SignIn.this, Home.class);
                                Common.currentUser = user;
                                startActivity(homeIntent);
                                finish();
                        }
                        else{
                            Toast.makeText(SignIn.this, "Wrong Password !!!!", Toast.LENGTH_SHORT).show();
                        }

                    }
                        else{
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "User doesn't exist !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }



}
