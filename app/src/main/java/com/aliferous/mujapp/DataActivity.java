package com.aliferous.mujapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DataActivity extends AppCompatActivity {

    EditText section,slot1,slot2,slot3,slot4,slot5;
    CheckBox betch,one,two,three,four,timetable,mon,tue,wed,thu,fri;
    Button btn_upload;

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        section = findViewById(R.id.up_section);
        slot1 = findViewById(R.id.up_slot1);
        slot2 = findViewById(R.id.up_slot2);
        slot3 = findViewById(R.id.up_slot3);
        slot4 = findViewById(R.id.up_slot4);
        slot5 = findViewById(R.id.up_slot5);
        betch = findViewById(R.id.up_checkBox_btech);
        one = findViewById(R.id.up_checkBox_1);
        two = findViewById(R.id.up_checkBox_2);
        three = findViewById(R.id.up_checkBox_3);
        four = findViewById(R.id.up_checkBox_4);
        timetable = findViewById(R.id.up_checkBox_timetable);
        mon = findViewById(R.id.up_checkBox_mon);
        tue = findViewById(R.id.up_checkBox_tue);
        wed = findViewById(R.id.up_checkBox_wed);
        thu = findViewById(R.id.up_checkBox_thu);
        fri = findViewById(R.id.up_checkBox_fri);
        btn_upload = findViewById(R.id.up_button);


        auth = FirebaseAuth.getInstance();


        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_section = section.getText().toString();
                String txt_slot1 = slot1.getText().toString();
                String txt_slot2 = slot2.getText().toString();
                String txt_slot3 = slot3.getText().toString();
                String txt_slot4 = slot4.getText().toString();
                String txt_slot5 = slot5.getText().toString();

                String course = null, year = null, child = null, day = null;

                if (betch.isChecked())
                    course = "Btech";

                if (one.isChecked())
                    year = "1st year";
                else if (two.isChecked())
                    year = "2nd year";
                else if (two.isChecked())
                    year = "3rd year";
                else if (two.isChecked())
                    year = "4th year";

                if (timetable.isChecked())
                    child = "Timetable";

                if (mon.isChecked())
                    day = "Mon";
                else if (tue.isChecked())
                    day = "Tue";
                else if (wed.isChecked())
                    day = "Wed";
                else if (thu.isChecked())
                    day = "Thu";
                else if (fri.isChecked())
                    day = "Fri";

                upload(txt_section,txt_slot1,txt_slot2,txt_slot3,txt_slot4,txt_slot5, course, year, child, day);

            }
        });

    }

    private void upload(String txt_section, String txt_slot1, String txt_slot2, String txt_slot3, String txt_slot4, String txt_slot5, String course, String year, String child, String day) {

        btn_upload.setText("UPLOADING....");
        reference = FirebaseDatabase.getInstance().getReference("Class").child("Btech").child("1").child(""+txt_section).child("Timetable").child(""+day);

        reference.child("1").setValue(""+txt_slot1);
        reference.child("2").setValue(""+txt_slot2);
        reference.child("3").setValue(""+txt_slot3);
        reference.child("4").setValue(""+txt_slot4);
        reference.child("5").setValue(""+txt_slot5);

        btn_upload.setText("upload");

    }
}