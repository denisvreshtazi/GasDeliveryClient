package com.example.gasdelivery;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.gasdelivery.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import info.hoang8f.widget.FButton;

public class SignUp extends AppCompatActivity {

    MaterialEditText edtName, edtPhone, edtEmail, edtPassword;
    FButton btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Assign the buttons and editTexts
        edtName = (MaterialEditText)findViewById(R.id.edtName);
        edtPhone = (MaterialEditText)findViewById(R.id.edtPhone);
        edtEmail = (MaterialEditText)findViewById(R.id.edtEmail);
        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);

        btnSignUp = (FButton)findViewById(R.id.btnSignUp);

        //Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Waiting...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //Check if Users phone exists
                        if(dataSnapshot.child(edtPhone.getText().toString()).exists()){
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Phone Number alredy exists !", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            mDialog.dismiss();
                            User user = new User(edtEmail.getText().toString(),edtName.getText().toString(),edtPassword.getText().toString(), edtPhone.getText().toString());
                            table_user.child(edtPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "Sign Up successfully !", Toast.LENGTH_SHORT).show();
                            finish();
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
