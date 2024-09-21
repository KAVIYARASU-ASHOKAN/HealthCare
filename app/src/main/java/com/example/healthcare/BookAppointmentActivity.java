package com.example.healthcare;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class BookAppointmentActivity extends AppCompatActivity {

    EditText Book_Appoinment_Name, Book_Appoinment_Contact_Number,Book_Appoinment_Address, Book_Appoinment_Fees;
    TextView Book_Appoinment_Title;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button Book_Appoinment_Select_Date,Book_Appoinment_Select_Time ,Book_Appoinment_Button,Book_Appoinment_Button_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        Book_Appoinment_Title = findViewById(R.id.Book_Appointment_Title);
        Book_Appoinment_Name = findViewById(R.id.Book_Appoinment_Name);
        Book_Appoinment_Contact_Number = findViewById(R.id.Book_Appoinment_Contact_Number);
        Book_Appoinment_Address = findViewById(R.id.Book_Appoinment_Address);
        Book_Appoinment_Fees = findViewById(R.id.Book_Appoinment_Fees);
        Book_Appoinment_Select_Date= findViewById(R.id.Book_Appoinment_Select_Date);
        Book_Appoinment_Select_Time = findViewById(R.id.Book_Appoinment_Select_Time);
        Book_Appoinment_Button=findViewById(R.id.Book_Appoinment_Button);
        Book_Appoinment_Button_Back=findViewById(R.id.Book_Appoinment_Button_Back);

        Book_Appoinment_Name.setKeyListener(null);
        Book_Appoinment_Contact_Number.setKeyListener(null);
        Book_Appoinment_Address.setKeyListener(null);
        Book_Appoinment_Fees.setKeyListener(null);

        Intent it = getIntent();
        String title = it.getStringExtra("text1");
        String fullname = it.getStringExtra("text2");
        String contact = it.getStringExtra("text3");
        String address = it.getStringExtra("text4");
        String fees = it.getStringExtra("text5");

        Book_Appoinment_Title.setText(title);
        Book_Appoinment_Name.setText(fullname);
        Book_Appoinment_Contact_Number.setText(contact);
        Book_Appoinment_Address.setText(address);
        Book_Appoinment_Fees.setText("Cons Fees : " + fees + "/-");

        initDatePicker();
        Book_Appoinment_Select_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        initTimePicker();
        Book_Appoinment_Select_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });

        Book_Appoinment_Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookAppointmentActivity.this,FindDoctorActivity.class));
            }
        });

        Book_Appoinment_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database db = new Database(getApplicationContext(),"healthcare",null,1);
                SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedpreferences.getString("username","").toString();
                if(db.checkAppointmentExists(username,title+" => "+fullname,address,contact,Book_Appoinment_Select_Date.getText().toString(),Book_Appoinment_Select_Time.getText().toString())==1){
                    Toast.makeText(getApplicationContext(),"Appointmnet already booked",Toast.LENGTH_LONG).show();
                }else{
                    db.addOrder(username,title+" => "+fullname,address,contact,0,Book_Appoinment_Select_Date.getText().toString(),Book_Appoinment_Select_Time.getText().toString(),Float.parseFloat(fees),"appointment");
                    Toast.makeText(getApplicationContext(),"Your appointmnet is done successfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(BookAppointmentActivity.this,HomeActivity.class));
                }
            }
        });
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener datasetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1;
                Book_Appoinment_Select_Date.setText(i2 + "/" + i1 + "/" + i);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this, style, datasetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis() + 86400000);
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                Book_Appoinment_Select_Time.setText(i + ":" + i1);
            }
        };
        Calendar cal = Calendar.getInstance();
        int hrs = cal.get(Calendar.HOUR_OF_DAY);
        int mins = cal.get(Calendar.MINUTE);

        int style = AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this, style, timeSetListener, hrs, mins, true);
    }
}
