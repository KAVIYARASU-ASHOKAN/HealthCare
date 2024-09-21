package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;

public class OrderDetailsActivity extends AppCompatActivity {

    private String[][] order_details = {};
    ArrayList<HashMap<String, String>> list;
    SimpleAdapter sa;
    ListView Order_Details_List_View;
    Button Order_Details_Button_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Order_Details_Button_Back = findViewById(R.id.Order_Details_Button_BACK);
        Order_Details_List_View = findViewById(R.id.Order_Details_ListView);

        Order_Details_Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderDetailsActivity.this, HomeActivity.class));
            }
        });

        Database db = new Database(getApplicationContext(), "healthcare", null, 1);
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        ArrayList<String> dbdata = db.getorderdata(username);

        if (dbdata.size() == 0) {
            // Handle case where there are no orders
            return;
        }

        order_details = new String[dbdata.size()][];

        for (int i = 0; i < dbdata.size(); i++) {
            String arrData = dbdata.get(i);
            String[] strData = arrData.split(java.util.regex.Pattern.quote("$"));

            order_details[i] = new String[5]; // Assuming you expect 5 pieces of data
            order_details[i][0] = strData[0]; // Full name
            order_details[i][1] = strData[1]; // Address
            order_details[i][2] = "Rs." + strData[6]; // Price
            order_details[i][3] = "Del: " + strData[4] + " " + strData[5]; // Delivery date & time
            order_details[i][4] = strData[7]; // Order type
        }

        list = new ArrayList<>();

        for (int i = 0; i < order_details.length; i++) {
            HashMap<String, String> item = new HashMap<>();
            item.put("line1", order_details[i][0]); // Full name
            item.put("line2", order_details[i][1]); // Address
            item.put("line3", order_details[i][2]); // Price
            item.put("line4", order_details[i][3]); // Delivery details
            item.put("line5", order_details[i][4]); // Order type
            list.add(item);
        }

        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});

        Order_Details_List_View.setAdapter(sa);
    }
}
