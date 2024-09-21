package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {



    EditText Login_Username, Login_Password;
    Button Login_Button;
    TextView NewUser_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login_Username = findViewById(R.id.Login_User_Name);
        Login_Password = findViewById(R.id.Login_Password);
        Login_Button = findViewById(R.id.Login_Button);
        NewUser_Button = findViewById(R.id.Click_new_user);

        Login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = Login_Username.getText().toString();
                String password = Login_Password.getText().toString();
                Database db = new Database(getApplicationContext(), "healthcare", null, 1);

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    if (db.login(username, password) == 1) {
                        Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();

                        // Save username to SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", username);
                        editor.apply();

                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid username and password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        NewUser_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}