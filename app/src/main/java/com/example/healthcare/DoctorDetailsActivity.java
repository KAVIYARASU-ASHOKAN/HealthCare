package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailsActivity extends AppCompatActivity {

    private String[][] doctor_details1=
            {
                    {"Doctor Name : Dr A Anitha","Hospital Address : Coimbatore","Experiance : 5yrs","Mobileno:6380071062","600"},
                    {"Doctor Name : DR S Prabakar","Hospital Address : Chennai","Experiance : 15yrs","Mobileno:8945845343","800"},
                    {"Doctor Name : Dr K Arjun","Hospital Address : Dharmapuri","Experiance : 20yrs","Mobileno:9535843583","500"},
                    {"Doctor Name : Dr J Jayavelu","Hospital Address : Coimbatore","Experiance : 7yrs","Mobileno:7348324234","900"},
                    {"Doctor Name : Dr M Mohan Krishna","Hospital Address : Chennai","Experiance : 25yrs","Mobileno:9349324832","1000"},

            };



    private String[][] doctor_details2=
            {
                    {"Doctor Name : Dr A Shankar","Hospital Address : Coimbatore","Experiance : 5yrs","Mobileno:9845374535","1200"},
                    {"Doctor Name : Dr A P Selvam","Hospital Address : Erode","Experiance : 7yrs","Mobileno:8687677667","900"},
                    {"Doctor Name : Dr S P Subash Kumar","Hospital Address : Kanchipuram","Experiance : 12yrs","Mobileno:9596585445","1200"},
                    {"Doctor Name : Dr K Sudha","Hospital Address : Chennai","Experiance : 9yrs","Mobileno:7453453343","400"},
                    {"Doctor Name : Dr T Aashish Shah","Hospital Address : Namakka","Experiance : 14yrs","Mobileno:6345435534","2000"},

            };

    private String[][] doctor_details3=
            {
                    {"Doctor Name : Dr S Raja Rajendran","Hospital Address : Coimbatore","Experiance : 5yrs","Mobileno:6388573945","600"},
                    {"Doctor Name : DR T Manoj Durairaj","Hospital Address : Erode","Experiance : 12yrs","Mobileno:8676786786","450"},
                    {"Doctor Name : Dr P Arunmozhi Rajan ","Hospital Address : Chennai","Experiance : 8yrs","Mobileno:9304534503","1200"},
                    {"Doctor Name : Dr A Sivakumar","Hospital Address : Salem","Experiance : 7yrs","Mobileno:9234232323","800"},
                    {"Doctor Name : Dr V Ganesh ","Hospital Address : Chennai","Experiance : 15yrs","Mobileno:8345398345","1500"},

            };

    private String[][] doctor_details4=
            {
                    {"Doctor Name : Dr A Baskar","Hospital Address : Chennai","Experiance : 5yrs","Mobileno:9488586842","600"},
                    {"Doctor Name : DR S Shanmugasundaram","Hospital Address : Coimbatore","Experiance : 10yrs","Mobileno:9487790719","1000"},
                    {"Doctor Name : Dr T  Padma Kumar","Hospital Address : Bangalore","Experiance : 7yrs","Mobileno:6365457454","800"},
                    {"Doctor Name : Dr V Malar Vetrievel","Hospital Address : Salem","Experiance : 8yrs","Mobileno:6380071062","1200"},
                    {"Doctor Name : Dr Ramanujam","Hospital Address : Erode","Experiance : 15yrs","Mobileno:6380071062","1700"},

            };

    private String[][] doctor_details5=
            {
                    {"Doctor Name : Dr A Muthukumar","Hospital Address : Coimbatore","Experiance : 5yrs","Mobileno:6380071062","600"},
                    {"Doctor Name : DR S  RAMYA","Hospital Address : Chennai","Experiance : 12yrs","Mobileno:9495964454","800"},
                    {"Doctor Name : Dr V PREM CHANDAR","Hospital Address : Erode","Experiance : 8yrs","Mobileno:7877667845","1200"},
                    {"Doctor Name : Dr R Veneeth","Hospital Address : Bangalor","Experiance : 15yrs","Mobileno:9898785757","1200"},
                    {"Doctor Name : Dr P PARAMESWARI","Hospital Address : Coimbatore","Experiance : 9yrs","Mobileno:8786767865","900"},

            };


    TextView Location;
    Button Back_Button;
    HashMap<String,String> item;
    String[][] doctor_details={};

    ArrayList list;

    SimpleAdapter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);


        Location=findViewById(R.id.Doctor_Details_Doctor_Type);
        Back_Button =findViewById(R.id.Doctor_Details_Page_BACK);

        Intent it=getIntent();
        String title =it.getStringExtra("Title");
        Location.setText(title);

        if(title.compareTo("Family Physicians")==0)
            doctor_details=doctor_details1;
        else
        if(title.compareTo("Dietician")==0)
            doctor_details=doctor_details2;
        else
        if(title.compareTo("Dentist")==0)
            doctor_details=doctor_details3;
        else
        if(title.compareTo("Surgeon")==0)
            doctor_details=doctor_details4;
        else
            doctor_details=doctor_details5;
        Back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorDetailsActivity.this,FindDoctorActivity.class));
            }
        });
        list =new ArrayList();
        for (int i = 0; i < doctor_details.length; i++) {
            // Extract mobile number without the prefix "Mobileno:"
            String[] details = doctor_details[i][3].split(":");
            String mobileNumber = details[1].trim(); // Get the mobile number without the "Mobileno:" prefix

            // Populate the HashMap with the extracted mobile number
            HashMap<String, String> item = new HashMap<>();
            item.put("line1", doctor_details[i][0]);
            item.put("line2", doctor_details[i][1]);
            item.put("line3", doctor_details[i][2]);
            item.put("line4", "Mobile No: " + mobileNumber);

            if (doctor_details[i].length > 4) {
                item.put("line5", "Cons Fees:" + doctor_details[i][4] + "/-");
            } else {
                item.put("line5", "Cons Fees: N/A");
            }

            list.add(item);
        }

        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});


        ListView lst =findViewById(R.id.Doctor_Details_Doctor_ListView);
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it= new Intent(DoctorDetailsActivity.this,BookAppointmentActivity.class);
                it.putExtra("text1",title);
                it.putExtra("text2",doctor_details[i][0]);
                it.putExtra("text3",doctor_details[i][1]);
                it.putExtra("text4",doctor_details[i][3]);
                it.putExtra("text5",doctor_details[i][4]);
                startActivity(it);
            }

        });
    }
}