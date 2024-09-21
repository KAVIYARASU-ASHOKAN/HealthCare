package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText Register_Username,Register_Email,Register_Password,Register_ConformPassword;
    Button Register_Button;
    TextView Click_ExistingUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Register_Username = findViewById(R.id.Register_User);
        Register_Email = findViewById(R.id.Register_Email);
        Register_Password = findViewById(R.id.Register_Password);
        Register_ConformPassword = findViewById(R.id.Register_ConformPassword);
        Register_Button = findViewById(R.id.Register_Button);
        Click_ExistingUser = findViewById(R.id.Click_Existinguser);

        Click_ExistingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        Register_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = Register_Username.getText().toString();
                String email = Register_Email.getText().toString();
                String password = Register_Password.getText().toString();
                String confirmPassword = Register_ConformPassword.getText().toString();
                Database db = new Database(getApplicationContext(),"healthcare",null,1);
                if (username.isEmpty() || password.isEmpty() || email.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.compareTo(confirmPassword)==0) {
                        if (isValid(password)) {

                            db.register(username,email,password);
                            Toast.makeText(getApplicationContext(),"Register Successfully ",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this ,LoginActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid password format", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Password and confirm password didn't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public static boolean isValid(String passwordhere) {
        int f1 = 0, f2 = 0, f3 = 0;
        if (passwordhere.length() < 8) {
            return false;
        } else {
            for (int p = 0; p < passwordhere.length(); p++) {
                if (Character.isLetter(passwordhere.charAt(p))) {
                    f1 = 1;
                }
            }
            for (int r = 0; r < passwordhere.length(); r++) {
                if (Character.isDigit(passwordhere.charAt(r))) {
                    f2 = 1;
                }
            }
            for (int s = 0; s < passwordhere.length(); s++) {
                char c = passwordhere.charAt(s);
                if (c >= 33 && c <= 46 || c == 64) {
                    f3 = 1;
                }
            }
            if(f1 == 1 && f2 == 1 && f3 == 1)
            return true;
            return false;
        }
    }
}
