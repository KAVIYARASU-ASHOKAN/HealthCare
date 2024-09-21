package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class LabTestActivity extends AppCompatActivity {

    private String[][] packages = {
            {"package 1 : Full Body Checkup", "", "", "", "1000"},
            {"package 2 : Blood Test", "", "", "", "800"},
            {"package 3 : Complete Urine Test", "", "", "", "400"},
            {"package 4 : Thyroid Check", "", "", "", "500"},
            {"package 5 : Immunity Check", "", "", "", "700"},
    };

    private String[] packageDetails = {
            "Blood Glucose Fasting\n" +
                    "hbA1c\n" +
                    "Iron Studies\n" +
                    "Kidney Function Test\n" +
                    "LHD Lactate Dehydrogenase,Serum\n" +
                    "lipid Profile\n" +
                    "Liver Function Test",
            "Blood Glucose Fasting",
            "COVID-19 Antibody -IgG",
            "Thyroid profile -Total (T3,T4 & TSH Ultra-sensitive)",
            "Complete Hemogram\n" +
                    "CRP (c Reactive protien ) Quantitative,Serum\n" +
                    "Iron Studies\n" +
                    "Kidney Function Test\n" +
                    "Vitamin D Total-25 Hydroxy\n" +
                    "Liver Function Test\n" +
                    "Lipid profile"
    };

    private ArrayList<HashMap<String, String>> list;
    private SimpleAdapter sa;
    private ListView Lab_Test_List_View;
    Button Lab_Test_Add_Cart,Lab_Test_Back_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test);

        Lab_Test_Back_Button = findViewById(R.id.Lab_Test_BACK_Button);
        Lab_Test_Add_Cart=findViewById(R.id.Lab_Test_ADD_Cart);
        Lab_Test_List_View = findViewById(R.id.Lab_Test_ListView);

        Lab_Test_Back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabTestActivity.this, HomeActivity.class));
            }
        });

        Lab_Test_Add_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabTestActivity.this,CartLabActivity.class));
            }
        });

        list = new ArrayList<>();
        for (int i = 0; i < packages.length; i++) {
            HashMap<String, String> item = new HashMap<>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", packages[i][2]);
            item.put("line4", packages[i][3]);
            item.put("line5", "Total Cost:" + packages[i][4] + "/-");
            list.add(item);
        }

        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});


        Lab_Test_List_View.setAdapter(sa);

        Lab_Test_List_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(LabTestActivity.this, LabTestDetailsActivity.class);
                it.putExtra("text1", packages[i][0]);
                it.putExtra("text2", packageDetails[i]);
                it.putExtra("text3", packages[i][4]);
                startActivity(it);
            }
        });

    }
}
