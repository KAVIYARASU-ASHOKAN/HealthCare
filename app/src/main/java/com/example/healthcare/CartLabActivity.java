package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CartLabActivity extends AppCompatActivity {
    HashMap<String,String> item;
    ArrayList list;
    SimpleAdapter sa;
    TextView Cart_Lab_Total_Cost;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button Cart_Lab_Select_Date,Cart_Lab_Select_Time,Cart_Lab_Checkout,Cart_Lab_Back;
    private ListView Cart_Lab_ListView;

    private String[][] packages={};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_lab);

        Cart_Lab_Select_Date = findViewById(R.id.Cart_Lab_Select_Date);
        Cart_Lab_Select_Time = findViewById(R.id.Cart_Lab_Select_Time);
        Cart_Lab_Checkout=findViewById(R.id.Cart_Lab_Checkout);
        Cart_Lab_Back=findViewById(R.id.Cart_Lab_BACK);
        Cart_Lab_Total_Cost=findViewById(R.id.Cart_Lab_Cost);
        Cart_Lab_ListView =findViewById(R.id.Cart_Lab_ListView);


        SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username=sharedPreferences.getString("username","").toString();
        Database db = new Database(getApplicationContext(),"healthcare",null,1);

        float totalAmmout=0;
        ArrayList dbdata=db.getCardData(username,"lab");


        packages =new String[dbdata.size()][];
        for (int i=0;i<packages.length;i++)
        {
            packages[i]=new String[5];
        }

        for (int i=0;i<dbdata.size();i++){
            String arrData=dbdata.get(i).toString();
            String[] strData=arrData.split(java.util.regex.Pattern.quote("$"));
            packages[i][0]=strData[0];
            packages[i][4]=""+strData[1]+"-/";
            totalAmmout=totalAmmout + Float.parseFloat(strData[1]);
        }

        Cart_Lab_Total_Cost.setText("Total cost : "+totalAmmout);

        list=new ArrayList();

        for (int i=0;i<packages.length;i++)
        {
            item=new HashMap<String,String>();
            item.put("line1",packages[i][0]);
            item.put("line2",packages[i][1]);
            item.put("line3",packages[i][2]);
            item.put("line4",packages[i][3]);
            item.put("line5",packages[i][4]);
            list.add(item);
        }

        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});

        Cart_Lab_ListView.setAdapter(sa);


        Cart_Lab_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartLabActivity.this,LabTestActivity.class));
            }
        });

        Cart_Lab_Checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(CartLabActivity.this, LabTestBookActivity.class);
                it.putExtra("price", Cart_Lab_Total_Cost.getText().toString());
                it.putExtra("date", Cart_Lab_Select_Date.getText().toString());
                it.putExtra("time", Cart_Lab_Select_Time.getText().toString());
                startActivity(it);

            }
        });
        initDatePicker();
        Cart_Lab_Select_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        initTimePicker();
        Cart_Lab_Select_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });
    }
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener datasetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1;
                Cart_Lab_Select_Date.setText(i2 + "/" + i1 + "/" + i);
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
                Cart_Lab_Select_Time.setText(i + ":" + i1);
            }
        };
        Calendar cal = Calendar.getInstance();
        int hrs = cal.get(Calendar.HOUR_OF_DAY);
        int mins = cal.get(Calendar.MINUTE);

        int style = AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this, style, timeSetListener, hrs, mins, true);
    }
}