package com.ellekay.lucie.diabetesms;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UserDetails extends AppCompatActivity {

    EditText et_name, et_height, et_weight, et_location, et_dob;
    RadioGroup radioGender;
    RadioButton genderBtn;
    Button btnProfile;

    String name,dateOfBirth, location, height, weight, gender, userId;
    String TAG = "Diabetes";

    private FirebaseUser user;
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    int selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            startActivity(new Intent(UserDetails.this, Login.class));
        }
        userId = user.getUid();

        et_name = (EditText) findViewById(R.id.et_name);
        et_dob = (EditText) findViewById(R.id.et_dob);
        et_height = (EditText) findViewById(R.id.et_height);
        et_weight = (EditText) findViewById(R.id.et_weight);
        et_location = (EditText) findViewById(R.id.et_location);
        btnProfile = (Button) findViewById(R.id.btnSubmitDetails);
        radioGender = (RadioGroup)findViewById(R.id.radioGroup);

        selectedId = radioGender.getCheckedRadioButtonId();
        Log.d(TAG, "Id: " +selectedId);
        genderBtn = (RadioButton) findViewById(selectedId);

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener dob = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar);
            }
        };

        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(UserDetails.this, dob, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitDetails();
            }
        });
    }

    private void updateLabel(Calendar cal) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        EditText dateOfBirth = (EditText) findViewById(R.id.et_dob);
        dateOfBirth.setText(sdf.format(cal.getTime()));
    }

    public void submitDetails(){
        name = et_name.getText().toString().trim();
        height = et_height.getText().toString().trim();
        weight = et_weight.getText().toString().trim();
        location = et_location.getText().toString().trim();
        gender = genderBtn.getText().toString();

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener dob = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar);
            }

        };
        dateOfBirth = et_dob.getText().toString().trim();
         //dob = myCalendar.getTime();

        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        UserInformation userInformation = new UserInformation(name,dateOfBirth,gender,height,weight,location, userId);

        myRef = database.getInstance().getReference("users");
        myRef.setValue(userInformation);
        //myRef.child("users").setValue(userInformation);

        Toast.makeText(this, "Information saved", Toast.LENGTH_SHORT).show();
    }

}
