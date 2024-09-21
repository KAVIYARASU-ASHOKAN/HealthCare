package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LabTestDetailsActivity extends AppCompatActivity {

    TextView Lab_Test_Details_packageName, Lab_Test_Details_TotalCost;
    EditText Lab_Test_Details;
    Button Lab_Test_Details_Add_To_Cart,Lab_Test_Details_Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_details);

        Lab_Test_Details_packageName = findViewById(R.id.Lab_Test_Details_Package_Name);
        Lab_Test_Details_TotalCost = findViewById(R.id.Lab_Test_Details_Cost);
        Lab_Test_Details = findViewById(R.id.Lab_Test_Details_ListView);
        Lab_Test_Details_Add_To_Cart = findViewById(R.id.Lab_Test_Details_ADD_Cart);
        Lab_Test_Details_Back = findViewById(R.id.Lab_Test_Details_BACK);

        Lab_Test_Details.setKeyListener(null);

        Intent intent = getIntent();
        Lab_Test_Details_packageName.setText(intent.getStringExtra("text1"));
        Lab_Test_Details.setText(intent.getStringExtra("text2"));
        Lab_Test_Details_TotalCost.setText("Total Cost : " + intent.getStringExtra("text3") + "/-");

        Lab_Test_Details_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabTestDetailsActivity.this, LabTestActivity.class));
            }
        });
        Lab_Test_Details_Add_To_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username=sharedPreferences.getString("username","").toString();
                String product=Lab_Test_Details_packageName.getText().toString();
                float price=Float.parseFloat(intent.getStringExtra("text3").toString());

                Database db = new Database(getApplicationContext(),"healthcare",null,1);

                if(db.checkCart(username,product)==1)
                {
                    Toast.makeText(getApplicationContext(),"Product ALready Added",Toast.LENGTH_SHORT).show();
                }
                else {
                    db.addCart(username,product,price,"lab");
                    Toast.makeText(getApplicationContext(),"Record Inserted to Cart",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LabTestDetailsActivity.this,LabTestActivity.class));

                }
            }
        });
    }
}
