package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class  LabTestBookActivity extends AppCompatActivity {

    EditText Lab_Book_Name, Lab_Book_Address,Lab_Book_Contact, Lab_Book_Pincode;
    Button Lab_Book_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_book);

        Lab_Book_Name = findViewById(R.id.Lab_Book_Name);
        Lab_Book_Address = findViewById(R.id.Lab_Book_Address);
        Lab_Book_Contact = findViewById(R.id.Lab_Book_Contact_Number);
        Lab_Book_Pincode = findViewById(R.id.Lab_Book_Pincode);
        Lab_Book_Button = findViewById(R.id.Lab_Book_Button);

        Intent intent = getIntent();
        String priceString = intent.getStringExtra("price");
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");

        if (priceString != null && date != null && time != null) {
            String[] price = priceString.split(Pattern.quote(":"));
            Lab_Book_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String pincodeText = Lab_Book_Pincode.getText().toString();
                    String name = Lab_Book_Name.getText().toString();
                    String address = Lab_Book_Address.getText().toString();
                    String contact = Lab_Book_Contact.getText().toString();

                    // Validate the Pincode input
                    if (!pincodeText.matches("\\d+")) {  // Check if it's a number
                        Toast.makeText(getApplicationContext(), "Please enter a valid pincode", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (name.isEmpty() || address.isEmpty() || contact.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();
                        return;
                    }

                    int pincode = Integer.parseInt(pincodeText);  // Now it's safe to parse

                    SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                    String username = sharedpreferences.getString("username", "");

                    Database db = new Database(getApplicationContext(), "healthcare", null, 1);
                    db.addOrder(username, name, address, contact, pincode, date, time, Float.parseFloat(price[1]), "lab");
                    db.removeCart(username, "lab");
                    Toast.makeText(getApplicationContext(), "Your booking is done successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LabTestBookActivity.this, HomeActivity.class));
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "Error: Missing booking details", Toast.LENGTH_LONG).show();
        }

    }
}
