package com.aliferous.mujapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import Model.Users;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView tv_name,tv_designation,tv_faculty,tv_school,tv_department;
    ConstraintLayout home_splash,home_page;
    FrameLayout class_info,chats,about;
    FirebaseUser firebaseUser;
    CountDownTimer cdt_change_update,cdt_splash;
    long change_update = 60000;
    int currentupdate = 0;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        imageView = findViewById(R.id.im_gender);
        tv_name = findViewById(R.id.tv_name);
        tv_designation = findViewById(R.id.tv_designation);
        tv_faculty = findViewById(R.id.tv_faculty);
        tv_department = findViewById(R.id.tv_department);
        tv_school = findViewById(R.id.tv_school);
        class_info = findViewById(R.id.classinfo);
        chats = findViewById(R.id.chats);
        about = findViewById(R.id.about);
        home_splash=findViewById(R.id.home_splash);
        home_page= findViewById(R.id.home_page);



        String splash = "0";
        splash= getIntent().getStringExtra("splash");
        if(splash.equals("1")){
            home_page.setVisibility(View.GONE);
            home_splash.setVisibility(View.VISIBLE);

            cdt_splash = new CountDownTimer(3100,1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    home_splash.setVisibility(View.GONE);
                    home_page.setVisibility(View.VISIBLE);
                }
            }.start();

        }


        class_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ClassActivity.class);
                startActivity(intent);
            }
        });
        chats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ChatActivity.class);
                startActivity(intent);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);
                assert user != null;

                tv_name.setText(user.getUsername());
                tv_designation.setText(user.getCourse());

                tv_department.setText("Department: "+user.getDepartment());
                tv_faculty.setText("Faculty: "+user.getFaculty());
                tv_school.setText("School: "+user.getSchool());


                /*if(user.getGender().equals("Male"))
                    imageView.setBackgroundResource(R.drawable.boy);
                else
                    imageView.setBackgroundResource(R.drawable.girl);*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








    }

}