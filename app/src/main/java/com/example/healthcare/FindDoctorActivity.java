package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FindDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);
        CardView exit=findViewById(R.id.Card_Find_Doctor_Back);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindDoctorActivity.this,HomeActivity.class));
            }
        });

        CardView familyphysician=findViewById(R.id.Card_Find_Doctor_Family_Physician);
        familyphysician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it= new Intent(FindDoctorActivity.this,DoctorDetailsActivity.class);
                it.putExtra("Title","Family Physicians ");
                startActivity(it);
            }
        });
        CardView dietician=findViewById(R.id.Card_Find_Doctor_Dietician);
        dietician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it= new Intent(FindDoctorActivity.this,DoctorDetailsActivity.class);
                it.putExtra("Title","Dietician");
                startActivity(it);
            }
        });

        CardView dentist=findViewById(R.id.Card_Find_Doctor_Dentist);
        dentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it= new Intent(FindDoctorActivity.this,DoctorDetailsActivity.class);
                it.putExtra("Title","Dentist");
                startActivity(it);
            }
        });

        CardView surgeon=findViewById(R.id.Card_Find_Doctor_Surgeon);
        surgeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it= new Intent(FindDoctorActivity.this,DoctorDetailsActivity.class);
                it.putExtra("Title","Surgeon");
                startActivity(it);
            }
        });

        CardView cardiologists=findViewById(R.id.Card_Find_Doctor_Cardiologists);
        cardiologists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it= new Intent(FindDoctorActivity.this,DoctorDetailsActivity.class);
                it.putExtra("Title","Cardiologists");
                startActivity(it);
            }
        });
    }
}