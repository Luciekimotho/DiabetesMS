package com.ellekay.lucie.diabetesms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDetails extends AppCompatActivity {

    EditText et_name, et_dob, et_height, et_weight, et_location;
    RadioButton radioGender;
    Button btnProfile;

    String name, dob, location, height, weight;

    private FirebaseUser user;
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            startActivity(new Intent(UserDetails.this, Login.class));
        }

        et_name = (EditText) findViewById(R.id.et_name);
        et_dob = (EditText) findViewById(R.id.et_dob);
        et_height = (EditText) findViewById(R.id.et_height);
        et_weight = (EditText) findViewById(R.id.et_weight);
        et_location = (EditText) findViewById(R.id.et_location);
        btnProfile = (Button) findViewById(R.id.btnSubmitDetails);

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Write a message to the database
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("message");

                myRef.setValue("Hello, World!");
            }
        });



    }

    public void submitDetails(){
        name = et_name.getText().toString();
        dob = et_dob.getText().toString();
        height = et_height.getText().toString();
        weight = et_weight.getText().toString();
        location = et_location.getText().toString();
    }
}
