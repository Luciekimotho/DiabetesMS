package com.ellekay.lucie.diabetesms;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Measure extends AppCompatActivity {

    EditText et_glucoreading, et_time, et_timeperiod, et_action, et_medication, et_notes;
    public Integer glucoReading;
    public String date, timePeriod, action, medication, notes, userId;
    Button btnMeasure;

    private FirebaseUser user;
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    MeasureTask myprogresstask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);

     user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            startActivity(new Intent(this, Login.class));
        }
        userId = user.getUid();

        et_glucoreading = (EditText)findViewById(R.id.et_glucosereading);
        et_time = (EditText) findViewById(R.id.et_time);
        et_timeperiod = (EditText) findViewById(R.id.et_timeperiod);
        et_action = (EditText)findViewById(R.id.et_action);
        et_medication = (EditText)findViewById(R.id.et_medication);
        et_notes = (EditText) findViewById(R.id.et_notes);

        et_notes.setMinLines(2);

        btnMeasure = (Button) findViewById(R.id.btn_measure);

        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Measure.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        et_time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        btnMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myprogresstask = new MeasureTask();
                myprogresstask.execute();
            }
        });
    }

    public void takeMeasurement(){
        date = et_time.getText().toString();
        timePeriod = et_timeperiod.getText().toString();
        glucoReading = Integer.parseInt(et_glucoreading.getText().toString());
        action = et_action.getText().toString();
        medication = et_action.getText().toString();
        notes = et_notes.getText().toString();

        Readings readings = new Readings(date, timePeriod, glucoReading, action, medication, notes);
        myRef = database.getInstance().getReference("gluco");
        //myRef.child(userId).setValue(userInformation);
        myRef.child(userId).setValue(readings);
    }

    public class MeasureTask extends AsyncTask<Void, Void, Void>{
        ProgressDialog progressDialog;
        @Override
        protected Void doInBackground(Void... params) {
            takeMeasurement();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Measure.this,
                    "ProgressDialog",
                    "Wait!");

            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {

                }
            });

            Toast.makeText(Measure.this,
                    "Progress Start",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(Measure.this,
                    "Progress Ended",
                    Toast.LENGTH_LONG).show();
            progressDialog.dismiss();

            Intent i = new Intent(Measure.this, Progress.class);
            startActivity(i);
        }
    }
}
