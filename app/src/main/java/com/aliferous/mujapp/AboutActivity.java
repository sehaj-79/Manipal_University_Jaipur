package com.aliferous.mujapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AboutActivity extends AppCompatActivity {

    FrameLayout frame_faculty,frame_awards,frame_research,frame_placement,frame_program,frame_functional,frame_student,frame_statutory,frame_nopermission;
    FrameLayout frame1,frame2,frame3,frame4,frame5,frame6,frame7,frame8;
    FrameLayout placement1,placement2,placement3,placement4,placement5,placement6;
    FrameLayout research1,research2,research3,research4,research5,research6;
    FrameLayout faculty1,faculty2,faculty3,faculty4,faculty5;
    FrameLayout program1,program2,program3,program4,program5;
    FrameLayout functional1,functional2,functional3;
    FrameLayout student1,student2,student3,student4,student5;
    ImageView im_faculty,im_awards,im_research,im_placement,im_program,im_functional,im_student,im_statutory;
    TextView tv_faculty,tv_awards,tv_research,tv_placement,tv_programs,tv_functional,tv_student,tv_statutory;
    Button button2020,button2019,button2018,button2017,button2016,button2015,button2014,button2013;
    Button rq_access,rq_sent;
    CheckBox viewStats;
    String permission_faculty = "No",permission_awards = "No",permission_research = "No",permission_placement = "No",permission_programs = "No",permission_functional = "No",permission_student = "No",permission_statutory = "No";
    String currentFrame;
    String name,userID;
    FirebaseUser firebaseUser;
    DatabaseReference reference, reference_list, referenceUID, referenceRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        frame1=findViewById(R.id.about_frame1);
        frame2=findViewById(R.id.about_frame2);
        frame3=findViewById(R.id.about_frame3);
        frame4=findViewById(R.id.about_frame4);
        frame5=findViewById(R.id.about_frame5);
        frame6=findViewById(R.id.about_frame6);
        frame7=findViewById(R.id.about_frame7);
        frame8=findViewById(R.id.about_frame8);


        faculty1=findViewById(R.id.faculty1);
        faculty2=findViewById(R.id.faculty2);
        faculty3=findViewById(R.id.faculty3);
        faculty4=findViewById(R.id.faculty4);
        faculty5=findViewById(R.id.faculty5);

        placement1=findViewById(R.id.placement1);
        placement2=findViewById(R.id.placement2);
        placement3=findViewById(R.id.placement3);
        placement4=findViewById(R.id.placement4);
        placement5=findViewById(R.id.placement5);
        placement6=findViewById(R.id.placement6);

        research1=findViewById(R.id.research1);
        research2=findViewById(R.id.research2);
        research3=findViewById(R.id.research3);
        research4=findViewById(R.id.research4);
        research5=findViewById(R.id.research5);
        research6=findViewById(R.id.research6);

        program1 = findViewById(R.id.program1);
        program2 = findViewById(R.id.program2);
        program3 = findViewById(R.id.program3);
        program4 = findViewById(R.id.program4);
        program5 = findViewById(R.id.program5);


        functional1 = findViewById(R.id.functional1);
        functional2 = findViewById(R.id.functional2);
        functional3 = findViewById(R.id.functional3);


        frame_faculty=findViewById(R.id.frame_faculty);
        frame_awards=findViewById(R.id.frame_awards);
        frame_research=findViewById(R.id.frame_research);
        frame_placement=findViewById(R.id.frame_placement);
        frame_program=findViewById(R.id.frame_program);
        frame_functional=findViewById(R.id.frame_functional);
        frame_student=findViewById(R.id.frame_student);
        frame_statutory=findViewById(R.id.frame_statutory);
        frame_nopermission=findViewById(R.id.frame_no_permission);


        im_faculty=findViewById(R.id.image_faculty);
        im_awards=findViewById(R.id.image_awards);
        im_research=findViewById(R.id.image_research);
        im_placement=findViewById(R.id.image_placement);
        im_program = findViewById(R.id.image_programs);
        im_functional = findViewById(R.id.image_unit);
        im_student = findViewById(R.id.image_student);
        im_statutory = findViewById(R.id.image_statutory);

        tv_faculty=findViewById(R.id.about_text_faculty);
        tv_awards=findViewById(R.id.about_text_awards);
        tv_research=findViewById(R.id.about_text_research);
        tv_placement=findViewById(R.id.about_text_placement);
        tv_programs=findViewById(R.id.about_text_programs);
        tv_functional=findViewById(R.id.about_text_unit);
        tv_student=findViewById(R.id.about_text_student);
        tv_statutory=findViewById(R.id.about_text_statutory);


        button2020 = findViewById(R.id.button2020);
        button2019 = findViewById(R.id.button2019);
        button2018 = findViewById(R.id.button2018);
        button2017 = findViewById(R.id.button2017);
        button2016 = findViewById(R.id.button2016);
        button2015 = findViewById(R.id.button2015);
        button2014 = findViewById(R.id.button2014);
        button2013 = findViewById(R.id.button2013);

        student1=findViewById(R.id.student1);
        student2=findViewById(R.id.student2);
        student3=findViewById(R.id.student3);
        student4=findViewById(R.id.student4);
        student5=findViewById(R.id.student5);

        rq_access = findViewById(R.id.about_request_access);
        rq_sent = findViewById(R.id.about_request_sent);

        viewStats = findViewById(R.id.check_viewStats);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navbar);
        bottomNavigationView.setSelectedItemId(R.id.nav_about);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        referenceUID = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference_list = FirebaseDatabase.getInstance().getReference("Access").child("List");
        referenceRequests = FirebaseDatabase.getInstance().getReference("Access").child("Requests");


        referenceUID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name =  dataSnapshot.child("username").getValue().toString();
                userID = dataSnapshot.child("id").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        reference_list.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int allPermissions=0;

                int no_of_users_all = (int) dataSnapshot.child("All").getChildrenCount();
                int no_of_users_faculty = (int) dataSnapshot.child("Faculty").getChildrenCount();
                int no_of_users_awards = (int) dataSnapshot.child("Awards").getChildrenCount();
                int no_of_users_research = (int) dataSnapshot.child("Research").getChildrenCount();
                int no_of_users_placement = (int) dataSnapshot.child("Placement").getChildrenCount();
                int no_of_users_programs = (int) dataSnapshot.child("Programs").getChildrenCount();
                int no_of_users_functional = (int) dataSnapshot.child("Functional Units").getChildrenCount();

                for (int i=1;i<=2000;i++){
                    if (dataSnapshot.child("All").hasChild(""+i))
                    {
                        String checkUID = dataSnapshot.child("All").child(""+i).child("ID").getValue().toString();
                        if(checkUID.equals(userID)){
                            permission_faculty = "Yes";
                            permission_awards = "Yes";
                            permission_research = "Yes";
                            permission_placement = "Yes";
                            permission_programs = "Yes";
                            permission_functional = "Yes";
                            permission_student = "Yes";
                            permission_statutory = "Yes";
                            allPermissions=1;
                        }
                    }

                }

                if (allPermissions==0){

                    for (int i=1;i<=2000;i++)
                    {
                        if (dataSnapshot.child("Faculty").hasChild(""+i))
                        {
                            String checkUID = dataSnapshot.child("Faculty").child(""+i).child("ID").getValue().toString();
                            if (checkUID.equals(userID))
                                permission_faculty = "Yes";
                        }

                    }

                    for (int i=1;i<=2000;i++)
                    {

                        if (dataSnapshot.child("Awards").hasChild(""+i))
                        {
                            String checkUID = dataSnapshot.child("Awards").child(""+i).child("ID").getValue().toString();
                            if (checkUID.equals(userID))
                                permission_awards = "Yes";
                        }
                    }

                    for (int i=1;i<=2000;i++)
                    {
                        if (dataSnapshot.child("Research").hasChild(""+i))
                        {
                            String checkUID = dataSnapshot.child("Research").child(""+i).child("ID").getValue().toString();
                            if (checkUID.equals(userID))
                                permission_research = "Yes";
                        }
                    }

                    for (int i=1;i<=2000;i++)
                    {
                        if (dataSnapshot.child("Placement").hasChild(""+i))
                        {
                            String checkUID = dataSnapshot.child("Placement").child(""+i).child("ID").getValue().toString();
                            if (checkUID.equals(userID))
                                permission_placement = "Yes";
                        }
                    }

                    for (int i=1;i<=2000;i++)
                    {
                        if (dataSnapshot.child("Programs").hasChild(""+i))
                        {
                            String checkUID = dataSnapshot.child("Programs").child(""+i).child("ID").getValue().toString();
                            if (checkUID.equals(userID))
                                permission_programs = "Yes";
                        }
                    }

                    for (int i=1;i<=2000;i++)
                    {
                        if (dataSnapshot.child("Functional Units").hasChild(""+i))
                        {
                            String checkUID = dataSnapshot.child("Functional Units").child(""+i).child("ID").getValue().toString();
                            if (checkUID.equals(userID))
                                permission_functional = "Yes";
                        }
                    }
                    for (int i=1;i<=2000;i++)
                    {
                        if (dataSnapshot.child("Students").hasChild(""+i))
                        {
                            String checkUID = dataSnapshot.child("Students").child(""+i).child("ID").getValue().toString();
                            if (checkUID.equals(userID))
                                permission_student = "Yes";
                        }
                    }
                    for (int i=1;i<=2000;i++)
                    {
                        if (dataSnapshot.child("Statutory Meeting").hasChild(""+i))
                        {
                            String checkUID = dataSnapshot.child("Statutory Meeting").child(""+i).child("ID").getValue().toString();
                            if (checkUID.equals(userID))
                                permission_statutory = "Yes";
                        }
                    }

                }

           }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        frame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFrame = "Faculty";
                frame1.setBackgroundResource(R.drawable.vector_squareorange);
                frame2.setBackgroundResource(R.drawable.vector_square);
                frame3.setBackgroundResource(R.drawable.vector_square);
                frame4.setBackgroundResource(R.drawable.vector_square);
                frame5.setBackgroundResource(R.drawable.vector_square);
                frame6.setBackgroundResource(R.drawable.vector_square);
                frame7.setBackgroundResource(R.drawable.vector_square);
                frame8.setBackgroundResource(R.drawable.vector_square);
                im_faculty.setBackgroundResource(R.drawable.vector_about_facultyorange);
                im_awards.setBackgroundResource(R.drawable.vector_about_awards);
                im_research.setBackgroundResource(R.drawable.vector_about_research);
                im_placement.setBackgroundResource(R.drawable.vector_about_placement);
                im_program.setBackgroundResource(R.drawable.vector_about_programs);
                im_functional.setBackgroundResource(R.drawable.vector_about_functional);
                im_student.setBackgroundResource(R.drawable.vector_about_student);
                im_statutory.setBackgroundResource(R.drawable.vector_about_statutory);
                tv_faculty.setTextColor(getResources().getColor(R.color.orange));
                tv_awards.setTextColor(getResources().getColor(R.color.grey));
                tv_research.setTextColor(getResources().getColor(R.color.grey));
                tv_placement.setTextColor(getResources().getColor(R.color.grey));
                tv_programs.setTextColor(getResources().getColor(R.color.grey));
                tv_functional.setTextColor(getResources().getColor(R.color.grey));
                tv_student.setTextColor(getResources().getColor(R.color.grey));
                tv_statutory.setTextColor(getResources().getColor(R.color.grey));


                        if (permission_faculty.equals("Yes")){

                            frame_faculty.setVisibility(View.VISIBLE);
                            frame_awards.setVisibility(View.GONE);
                            frame_research.setVisibility(View.GONE);
                            frame_placement.setVisibility(View.GONE);
                            frame_program.setVisibility(View.GONE);
                            frame_functional.setVisibility(View.GONE);
                            frame_student.setVisibility(View.GONE);
                            frame_statutory.setVisibility(View.GONE);
                            frame_nopermission.setVisibility(View.GONE);
                        }

                        else{

                            frame_faculty.setVisibility(View.GONE);
                            frame_awards.setVisibility(View.GONE);
                            frame_research.setVisibility(View.GONE);
                            frame_placement.setVisibility(View.GONE);
                            frame_program.setVisibility(View.GONE);
                            frame_functional.setVisibility(View.GONE);
                            frame_student.setVisibility(View.GONE);
                            frame_statutory.setVisibility(View.GONE);
                            frame_nopermission.setVisibility(View.VISIBLE);
                            rq_access.setVisibility(View.VISIBLE);
                            rq_sent.setVisibility(View.GONE);


                            referenceRequests.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    for (int i=1;i<=5000;i++) {
                                        if (dataSnapshot.child("Faculty").hasChild(""+i)) {
                                            String check = dataSnapshot.child("Faculty").child("" + i).child("ID").getValue().toString();
                                            if (check.equals(userID)) {
                                                rq_access.setVisibility(View.GONE);
                                                rq_sent.setVisibility(View.VISIBLE);

                                            }
                                        }
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }




            }
        });

        frame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFrame = "Awards";

                frame2.setBackgroundResource(R.drawable.vector_squareorange);
                frame1.setBackgroundResource(R.drawable.vector_square);
                frame3.setBackgroundResource(R.drawable.vector_square);
                frame4.setBackgroundResource(R.drawable.vector_square);
                frame5.setBackgroundResource(R.drawable.vector_square);
                frame6.setBackgroundResource(R.drawable.vector_square);
                frame7.setBackgroundResource(R.drawable.vector_square);
                frame8.setBackgroundResource(R.drawable.vector_square);
                im_awards.setBackgroundResource(R.drawable.vector_about_awardsorange);
                im_faculty.setBackgroundResource(R.drawable.vector_about_faculty);
                im_research.setBackgroundResource(R.drawable.vector_about_research);
                im_placement.setBackgroundResource(R.drawable.vector_about_placement);
                im_program.setBackgroundResource(R.drawable.vector_about_programs);
                im_functional.setBackgroundResource(R.drawable.vector_about_functional);
                im_student.setBackgroundResource(R.drawable.vector_about_student);
                im_statutory.setBackgroundResource(R.drawable.vector_about_statutory);
                tv_awards.setTextColor(getResources().getColor(R.color.orange));
                tv_faculty.setTextColor(getResources().getColor(R.color.grey));
                tv_research.setTextColor(getResources().getColor(R.color.grey));
                tv_placement.setTextColor(getResources().getColor(R.color.grey));
                tv_programs.setTextColor(getResources().getColor(R.color.grey));
                tv_functional.setTextColor(getResources().getColor(R.color.grey));
                tv_student.setTextColor(getResources().getColor(R.color.grey));
                tv_statutory.setTextColor(getResources().getColor(R.color.grey));
                if (permission_awards.equals("Yes"))
                {

                    frame_faculty.setVisibility(View.GONE);
                    frame_awards.setVisibility(View.VISIBLE);
                    frame_research.setVisibility(View.GONE);
                    frame_placement.setVisibility(View.GONE);
                    frame_program.setVisibility(View.GONE);
                    frame_functional.setVisibility(View.GONE);
                    frame_student.setVisibility(View.GONE);
                    frame_statutory.setVisibility(View.GONE);
                    frame_nopermission.setVisibility(View.GONE);



                }


                else {

                    frame_faculty.setVisibility(View.GONE);
                    frame_awards.setVisibility(View.GONE);
                    frame_research.setVisibility(View.GONE);
                    frame_placement.setVisibility(View.GONE);
                    frame_program.setVisibility(View.GONE);
                    frame_functional.setVisibility(View.GONE);
                    frame_student.setVisibility(View.GONE);
                    frame_statutory.setVisibility(View.GONE);
                    frame_nopermission.setVisibility(View.VISIBLE);
                    rq_access.setVisibility(View.VISIBLE);
                    rq_sent.setVisibility(View.GONE);


                    referenceRequests.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (int i=1;i<=5000;i++) {
                                if (dataSnapshot.child("Awards").hasChild(""+i)) {
                                    String check = dataSnapshot.child("Awards").child("" + i).child("ID").getValue().toString();
                                    if (check.equals(userID)) {
                                        rq_access.setVisibility(View.GONE);
                                        rq_sent.setVisibility(View.VISIBLE);

                                    }
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }



            }
        });

        frame3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFrame = "Research";
                frame3.setBackgroundResource(R.drawable.vector_squareorange);
                frame1.setBackgroundResource(R.drawable.vector_square);
                frame2.setBackgroundResource(R.drawable.vector_square);
                frame4.setBackgroundResource(R.drawable.vector_square);
                frame5.setBackgroundResource(R.drawable.vector_square);
                frame6.setBackgroundResource(R.drawable.vector_square);
                frame7.setBackgroundResource(R.drawable.vector_square);
                frame8.setBackgroundResource(R.drawable.vector_square);
                im_research.setBackgroundResource(R.drawable.vector_about_researchorange);
                im_faculty.setBackgroundResource(R.drawable.vector_about_faculty);
                im_awards.setBackgroundResource(R.drawable.vector_about_awards);
                im_placement.setBackgroundResource(R.drawable.vector_about_placement);
                im_program.setBackgroundResource(R.drawable.vector_about_programs);
                im_functional.setBackgroundResource(R.drawable.vector_about_functional);
                im_student.setBackgroundResource(R.drawable.vector_about_student);
                im_statutory.setBackgroundResource(R.drawable.vector_about_statutory);
                tv_research.setTextColor(getResources().getColor(R.color.orange));
                tv_faculty.setTextColor(getResources().getColor(R.color.grey));
                tv_awards.setTextColor(getResources().getColor(R.color.grey));
                tv_placement.setTextColor(getResources().getColor(R.color.grey));
                tv_programs.setTextColor(getResources().getColor(R.color.grey));
                tv_functional.setTextColor(getResources().getColor(R.color.grey));
                tv_student.setTextColor(getResources().getColor(R.color.grey));
                tv_statutory.setTextColor(getResources().getColor(R.color.grey));

                if (permission_research.equals("Yes"))
                {

                    frame_faculty.setVisibility(View.GONE);
                    frame_awards.setVisibility(View.GONE);
                    frame_research.setVisibility(View.VISIBLE);
                    frame_placement.setVisibility(View.GONE);
                    frame_program.setVisibility(View.GONE);
                    frame_functional.setVisibility(View.GONE);
                    frame_student.setVisibility(View.GONE);
                    frame_statutory.setVisibility(View.GONE);
                    frame_nopermission.setVisibility(View.GONE);

                }

                else {

                    frame_faculty.setVisibility(View.GONE);
                    frame_awards.setVisibility(View.GONE);
                    frame_research.setVisibility(View.GONE);
                    frame_placement.setVisibility(View.GONE);
                    frame_program.setVisibility(View.GONE);
                    frame_functional.setVisibility(View.GONE);
                    frame_student.setVisibility(View.GONE);
                    frame_statutory.setVisibility(View.GONE);
                    frame_nopermission.setVisibility(View.VISIBLE);
                    rq_access.setVisibility(View.VISIBLE);
                    rq_sent.setVisibility(View.GONE);

                    referenceRequests.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (int i=1;i<=5000;i++) {
                                if (dataSnapshot.child("Research").hasChild(""+i)) {
                                    String check = dataSnapshot.child("Research").child("" + i).child("ID").getValue().toString();
                                    if (check.equals(userID)) {
                                        rq_access.setVisibility(View.GONE);
                                        rq_sent.setVisibility(View.VISIBLE);

                                    }
                                }
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }


            }
        });

        frame4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFrame = "Placement";

                frame4.setBackgroundResource(R.drawable.vector_squareorange);
                frame1.setBackgroundResource(R.drawable.vector_square);
                frame2.setBackgroundResource(R.drawable.vector_square);
                frame3.setBackgroundResource(R.drawable.vector_square);
                frame5.setBackgroundResource(R.drawable.vector_square);
                frame6.setBackgroundResource(R.drawable.vector_square);
                frame7.setBackgroundResource(R.drawable.vector_square);
                frame8.setBackgroundResource(R.drawable.vector_square);
                im_placement.setBackgroundResource(R.drawable.vector_about_placementorange);
                im_faculty.setBackgroundResource(R.drawable.vector_about_faculty);
                im_awards.setBackgroundResource(R.drawable.vector_about_awards);
                im_research.setBackgroundResource(R.drawable.vector_about_research);
                im_program.setBackgroundResource(R.drawable.vector_about_programs);
                im_functional.setBackgroundResource(R.drawable.vector_about_functional);
                im_student.setBackgroundResource(R.drawable.vector_about_student);
                im_statutory.setBackgroundResource(R.drawable.vector_about_statutory);
                tv_placement.setTextColor(getResources().getColor(R.color.orange));
                tv_faculty.setTextColor(getResources().getColor(R.color.grey));
                tv_awards.setTextColor(getResources().getColor(R.color.grey));
                tv_research.setTextColor(getResources().getColor(R.color.grey));
                tv_programs.setTextColor(getResources().getColor(R.color.grey));
                tv_functional.setTextColor(getResources().getColor(R.color.grey));
                tv_student.setTextColor(getResources().getColor(R.color.grey));
                tv_statutory.setTextColor(getResources().getColor(R.color.grey));

                if (permission_placement.equals("Yes"))

                {

                    frame_faculty.setVisibility(View.GONE);
                    frame_awards.setVisibility(View.GONE);
                    frame_research.setVisibility(View.GONE);
                    frame_placement.setVisibility(View.VISIBLE);
                    frame_program.setVisibility(View.GONE);
                    frame_functional.setVisibility(View.GONE);
                    frame_student.setVisibility(View.GONE);
                    frame_statutory.setVisibility(View.GONE);
                    frame_nopermission.setVisibility(View.GONE);

                }

                else
                {


                    frame_faculty.setVisibility(View.GONE);
                    frame_awards.setVisibility(View.GONE);
                    frame_research.setVisibility(View.GONE);
                    frame_placement.setVisibility(View.GONE);
                    frame_program.setVisibility(View.GONE);
                    frame_functional.setVisibility(View.GONE);
                    frame_student.setVisibility(View.GONE);
                    frame_statutory.setVisibility(View.GONE);
                    frame_nopermission.setVisibility(View.VISIBLE);
                    rq_access.setVisibility(View.VISIBLE);
                    rq_sent.setVisibility(View.GONE);


                    referenceRequests.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (int i=1;i<=5000;i++) {
                                if (dataSnapshot.child("Placement").hasChild(""+i)) {
                                    String check = dataSnapshot.child("Placement").child("" + i).child("ID").getValue().toString();
                                    if (check.equals(userID)) {
                                        rq_access.setVisibility(View.GONE);
                                        rq_sent.setVisibility(View.VISIBLE);

                                    }
                                }
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


            }
        });

        frame5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFrame = "Programs";

                frame5.setBackgroundResource(R.drawable.vector_squareorange);
                frame1.setBackgroundResource(R.drawable.vector_square);
                frame2.setBackgroundResource(R.drawable.vector_square);
                frame3.setBackgroundResource(R.drawable.vector_square);
                frame4.setBackgroundResource(R.drawable.vector_square);
                frame6.setBackgroundResource(R.drawable.vector_square);
                frame7.setBackgroundResource(R.drawable.vector_square);
                frame8.setBackgroundResource(R.drawable.vector_square);
                im_program.setBackgroundResource(R.drawable.vector_about_programsorange);
                im_faculty.setBackgroundResource(R.drawable.vector_about_faculty);
                im_awards.setBackgroundResource(R.drawable.vector_about_awards);
                im_research.setBackgroundResource(R.drawable.vector_about_research);
                im_placement.setBackgroundResource(R.drawable.vector_about_placement);
                im_functional.setBackgroundResource(R.drawable.vector_about_functional);
                im_student.setBackgroundResource(R.drawable.vector_about_student);
                im_statutory.setBackgroundResource(R.drawable.vector_about_statutory);
                tv_programs.setTextColor(getResources().getColor(R.color.orange));
                tv_faculty.setTextColor(getResources().getColor(R.color.grey));
                tv_awards.setTextColor(getResources().getColor(R.color.grey));
                tv_research.setTextColor(getResources().getColor(R.color.grey));
                tv_placement.setTextColor(getResources().getColor(R.color.grey));
                tv_functional.setTextColor(getResources().getColor(R.color.grey));
                tv_student.setTextColor(getResources().getColor(R.color.grey));
                tv_statutory.setTextColor(getResources().getColor(R.color.grey));


                if (permission_programs.equals("Yes"))
                {

                    frame_faculty.setVisibility(View.GONE);
                    frame_awards.setVisibility(View.GONE);
                    frame_research.setVisibility(View.GONE);
                    frame_placement.setVisibility(View.GONE);
                    frame_program.setVisibility(View.VISIBLE);
                    frame_functional.setVisibility(View.GONE);
                    frame_student.setVisibility(View.GONE);
                    frame_statutory.setVisibility(View.GONE);
                    frame_nopermission.setVisibility(View.GONE);
                }

                else {

                    frame_faculty.setVisibility(View.GONE);
                    frame_awards.setVisibility(View.GONE);
                    frame_research.setVisibility(View.GONE);
                    frame_placement.setVisibility(View.GONE);
                    frame_program.setVisibility(View.GONE);
                    frame_functional.setVisibility(View.GONE);
                    frame_student.setVisibility(View.GONE);
                    frame_statutory.setVisibility(View.GONE);
                    frame_nopermission.setVisibility(View.VISIBLE);
                    rq_access.setVisibility(View.VISIBLE);
                    rq_sent.setVisibility(View.GONE);

                    referenceRequests.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (int i=1;i<=5000;i++) {
                                if (dataSnapshot.child("Programs").hasChild(""+i)) {
                                    String check = dataSnapshot.child("Programs").child("" + i).child("ID").getValue().toString();
                                    if (check.equals(userID)) {
                                        rq_access.setVisibility(View.GONE);
                                        rq_sent.setVisibility(View.VISIBLE);

                                    }
                                }
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


            }
        });

        frame6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFrame = "Functional Units";

                frame6.setBackgroundResource(R.drawable.vector_squareorange);
                frame1.setBackgroundResource(R.drawable.vector_square);
                frame2.setBackgroundResource(R.drawable.vector_square);
                frame3.setBackgroundResource(R.drawable.vector_square);
                frame4.setBackgroundResource(R.drawable.vector_square);
                frame5.setBackgroundResource(R.drawable.vector_square);
                frame7.setBackgroundResource(R.drawable.vector_square);
                frame8.setBackgroundResource(R.drawable.vector_square);
                im_functional.setBackgroundResource(R.drawable.vector_about_functionalorange);
                im_faculty.setBackgroundResource(R.drawable.vector_about_faculty);
                im_awards.setBackgroundResource(R.drawable.vector_about_awards);
                im_research.setBackgroundResource(R.drawable.vector_about_research);
                im_placement.setBackgroundResource(R.drawable.vector_about_placement);
                im_program.setBackgroundResource(R.drawable.vector_about_programs);
                im_student.setBackgroundResource(R.drawable.vector_about_student);
                im_statutory.setBackgroundResource(R.drawable.vector_about_statutory);
                tv_functional.setTextColor(getResources().getColor(R.color.orange));
                tv_faculty.setTextColor(getResources().getColor(R.color.grey));
                tv_awards.setTextColor(getResources().getColor(R.color.grey));
                tv_research.setTextColor(getResources().getColor(R.color.grey));
                tv_placement.setTextColor(getResources().getColor(R.color.grey));
                tv_programs.setTextColor(getResources().getColor(R.color.grey));
                tv_student.setTextColor(getResources().getColor(R.color.grey));
                tv_statutory.setTextColor(getResources().getColor(R.color.grey));


                if (permission_functional.equals("Yes"))
                {

                    frame_faculty.setVisibility(View.GONE);
                    frame_awards.setVisibility(View.GONE);
                    frame_research.setVisibility(View.GONE);
                    frame_placement.setVisibility(View.GONE);
                    frame_program.setVisibility(View.GONE);
                    frame_functional.setVisibility(View.VISIBLE);
                    frame_student.setVisibility(View.GONE);
                    frame_statutory.setVisibility(View.GONE);
                    frame_nopermission.setVisibility(View.GONE);
                }

             else {

                    frame_faculty.setVisibility(View.GONE);
                    frame_awards.setVisibility(View.GONE);
                    frame_research.setVisibility(View.GONE);
                    frame_placement.setVisibility(View.GONE);
                    frame_program.setVisibility(View.GONE);
                    frame_functional.setVisibility(View.GONE);
                    frame_student.setVisibility(View.GONE);
                    frame_statutory.setVisibility(View.GONE);
                    frame_nopermission.setVisibility(View.VISIBLE);
                    rq_access.setVisibility(View.VISIBLE);
                    rq_sent.setVisibility(View.GONE);


                    referenceRequests.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (int i=1;i<=5000;i++) {
                                if (dataSnapshot.child("Functional Units").hasChild(""+i)) {
                                    String check = dataSnapshot.child("Functional Units").child("" + i).child("ID").getValue().toString();
                                    if (check.equals(userID)) {
                                        rq_access.setVisibility(View.GONE);
                                        rq_sent.setVisibility(View.VISIBLE);

                                    }
                                }
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });


        frame7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFrame = "Students";

                frame6.setBackgroundResource(R.drawable.vector_square);
                frame1.setBackgroundResource(R.drawable.vector_square);
                frame2.setBackgroundResource(R.drawable.vector_square);
                frame3.setBackgroundResource(R.drawable.vector_square);
                frame4.setBackgroundResource(R.drawable.vector_square);
                frame5.setBackgroundResource(R.drawable.vector_square);
                frame7.setBackgroundResource(R.drawable.vector_squareorange);
                frame8.setBackgroundResource(R.drawable.vector_square);
                im_functional.setBackgroundResource(R.drawable.vector_about_functional);
                im_faculty.setBackgroundResource(R.drawable.vector_about_faculty);
                im_awards.setBackgroundResource(R.drawable.vector_about_awards);
                im_research.setBackgroundResource(R.drawable.vector_about_research);
                im_placement.setBackgroundResource(R.drawable.vector_about_placement);
                im_program.setBackgroundResource(R.drawable.vector_about_programs);
                im_student.setBackgroundResource(R.drawable.vector_about_studentorange);
                im_statutory.setBackgroundResource(R.drawable.vector_about_statutory);
                tv_functional.setTextColor(getResources().getColor(R.color.grey));
                tv_faculty.setTextColor(getResources().getColor(R.color.grey));
                tv_awards.setTextColor(getResources().getColor(R.color.grey));
                tv_research.setTextColor(getResources().getColor(R.color.grey));
                tv_placement.setTextColor(getResources().getColor(R.color.grey));
                tv_programs.setTextColor(getResources().getColor(R.color.grey));
                tv_student.setTextColor(getResources().getColor(R.color.orange));
                tv_statutory.setTextColor(getResources().getColor(R.color.grey));


                if (permission_student.equals("Yes"))
                {

                    frame_faculty.setVisibility(View.GONE);
                    frame_awards.setVisibility(View.GONE);
                    frame_research.setVisibility(View.GONE);
                    frame_placement.setVisibility(View.GONE);
                    frame_program.setVisibility(View.GONE);
                    frame_functional.setVisibility(View.GONE);
                    frame_student.setVisibility(View.VISIBLE);
                    frame_statutory.setVisibility(View.GONE);
                    frame_nopermission.setVisibility(View.GONE);
                }

                else {

                    frame_faculty.setVisibility(View.GONE);
                    frame_awards.setVisibility(View.GONE);
                    frame_research.setVisibility(View.GONE);
                    frame_placement.setVisibility(View.GONE);
                    frame_program.setVisibility(View.GONE);
                    frame_functional.setVisibility(View.GONE);
                    frame_student.setVisibility(View.GONE);
                    frame_statutory.setVisibility(View.GONE);
                    frame_nopermission.setVisibility(View.VISIBLE);
                    rq_access.setVisibility(View.VISIBLE);
                    rq_sent.setVisibility(View.GONE);


                    referenceRequests.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (int i=1;i<=5000;i++) {
                                if (dataSnapshot.child("Students").hasChild(""+i)) {
                                    String check = dataSnapshot.child("Students").child("" + i).child("ID").getValue().toString();
                                    if (check.equals(userID)) {
                                        rq_access.setVisibility(View.GONE);
                                        rq_sent.setVisibility(View.VISIBLE);

                                    }
                                }
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });

        frame8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFrame = "Statutory Meeting";

                frame6.setBackgroundResource(R.drawable.vector_square);
                frame1.setBackgroundResource(R.drawable.vector_square);
                frame2.setBackgroundResource(R.drawable.vector_square);
                frame3.setBackgroundResource(R.drawable.vector_square);
                frame4.setBackgroundResource(R.drawable.vector_square);
                frame5.setBackgroundResource(R.drawable.vector_square);
                frame7.setBackgroundResource(R.drawable.vector_square);
                frame8.setBackgroundResource(R.drawable.vector_squareorange);
                im_functional.setBackgroundResource(R.drawable.vector_about_functional);
                im_faculty.setBackgroundResource(R.drawable.vector_about_faculty);
                im_awards.setBackgroundResource(R.drawable.vector_about_awards);
                im_research.setBackgroundResource(R.drawable.vector_about_research);
                im_placement.setBackgroundResource(R.drawable.vector_about_placement);
                im_program.setBackgroundResource(R.drawable.vector_about_programs);
                im_student.setBackgroundResource(R.drawable.vector_about_student);
                im_statutory.setBackgroundResource(R.drawable.vector_about_statutoryorange);
                tv_functional.setTextColor(getResources().getColor(R.color.grey));
                tv_faculty.setTextColor(getResources().getColor(R.color.grey));
                tv_awards.setTextColor(getResources().getColor(R.color.grey));
                tv_research.setTextColor(getResources().getColor(R.color.grey));
                tv_placement.setTextColor(getResources().getColor(R.color.grey));
                tv_programs.setTextColor(getResources().getColor(R.color.grey));
                tv_student.setTextColor(getResources().getColor(R.color.grey));
                tv_statutory.setTextColor(getResources().getColor(R.color.orange));


                if (permission_statutory.equals("Yes"))
                {

                    frame_faculty.setVisibility(View.GONE);
                    frame_awards.setVisibility(View.GONE);
                    frame_research.setVisibility(View.GONE);
                    frame_placement.setVisibility(View.GONE);
                    frame_program.setVisibility(View.GONE);
                    frame_functional.setVisibility(View.GONE);
                    frame_student.setVisibility(View.GONE);
                    frame_statutory.setVisibility(View.VISIBLE);
                    frame_nopermission.setVisibility(View.GONE);
                }

                else {

                    frame_faculty.setVisibility(View.GONE);
                    frame_awards.setVisibility(View.GONE);
                    frame_research.setVisibility(View.GONE);
                    frame_placement.setVisibility(View.GONE);
                    frame_program.setVisibility(View.GONE);
                    frame_functional.setVisibility(View.GONE);
                    frame_student.setVisibility(View.GONE);
                    frame_statutory.setVisibility(View.GONE);
                    frame_nopermission.setVisibility(View.VISIBLE);
                    rq_access.setVisibility(View.VISIBLE);
                    rq_sent.setVisibility(View.GONE);


                    referenceRequests.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (int i=1;i<=5000;i++) {
                                if (dataSnapshot.child("Statutory Meeting").hasChild(""+i)) {
                                    String check = dataSnapshot.child("Statutory Meeting").child("" + i).child("ID").getValue().toString();
                                    if (check.equals(userID)) {
                                        rq_access.setVisibility(View.GONE);
                                        rq_sent.setVisibility(View.VISIBLE);

                                    }
                                }
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });

        faculty1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewStats.isChecked()){
                    Intent intent = new Intent(AboutActivity.this,FacultyStatsActivity.class);
                    intent.putExtra("i","1");
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(AboutActivity.this,FacultyListActivity.class);
                    intent.putExtra("i","1");
                    startActivity(intent);
                }


            }
        });
        faculty2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewStats.isChecked()){
                    Intent intent = new Intent(AboutActivity.this,FacultyStatsActivity.class);
                    intent.putExtra("i","2");
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(AboutActivity.this,FacultyListActivity.class);
                    intent.putExtra("i","2");
                    startActivity(intent);
                }
            }
        });
        faculty3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewStats.isChecked()){
                    Intent intent = new Intent(AboutActivity.this,FacultyStatsActivity.class);
                    intent.putExtra("i","3");
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(AboutActivity.this,FacultyListActivity.class);
                    intent.putExtra("i","3");
                    startActivity(intent);
                }
            }
        });
        faculty4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewStats.isChecked()){
                    Intent intent = new Intent(AboutActivity.this,FacultyStatsActivity.class);
                    intent.putExtra("i","4");
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(AboutActivity.this,FacultyListActivity.class);
                    intent.putExtra("i","4");
                    startActivity(intent);
                }
            }
        });
        faculty5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewStats.isChecked()){
                    Intent intent = new Intent(AboutActivity.this,FacultyStatsActivity.class);
                    intent.putExtra("i","5");
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(AboutActivity.this,FacultyListActivity.class);
                    intent.putExtra("i","5");
                    startActivity(intent);
                }
            }
        });

        research1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,ResearchActivity.class);
                intent.putExtra("i","1");
                startActivity(intent);
            }
        });
        research2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,ResearchActivity.class);
                intent.putExtra("i","2");
                startActivity(intent);
            }
        });
        research3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,ResearchActivity.class);
                intent.putExtra("i","3");
                startActivity(intent);
            }
        });
        research4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,ResearchActivity.class);
                intent.putExtra("i","4");
                startActivity(intent);
            }
        });
        research5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,ResearchActivity.class);
                intent.putExtra("i","5");
                startActivity(intent);
            }
        });
        research6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,ResearchActivity.class);
                intent.putExtra("i","6");
                startActivity(intent);
            }
        });

        placement1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,PlacementActivity.class);
                intent.putExtra("i","1");
                startActivity(intent);
            }
        });
        placement2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,PlacementActivity.class);
                intent.putExtra("i","2");
                startActivity(intent);
            }
        });
        placement3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,PlacementActivity.class);
                intent.putExtra("i","3");
                startActivity(intent);
            }
        });
        placement4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,PlacementActivity.class);
                intent.putExtra("i","4");
                startActivity(intent);
            }
        });
        placement5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,PlacementActivity.class);
                intent.putExtra("i","5");
                startActivity(intent);
            }
        });
        placement6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,PlacementActivity.class);
                intent.putExtra("i","6");
                startActivity(intent);
            }
        });


        program1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this, ProgramListActivity.class);
                intent.putExtra("i","1");
                startActivity(intent);
            }
        });

        program2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this, ProgramListActivity.class);
                intent.putExtra("i","2");
                startActivity(intent);
            }
        });

        program3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this, ProgramListActivity.class);
                intent.putExtra("i","3");
                startActivity(intent);
            }
        });

        program4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this, ProgramListActivity.class);
                intent.putExtra("i","4");
                startActivity(intent);
            }
        });

        program5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this, ProgramListActivity.class);
                intent.putExtra("i","5");
                startActivity(intent);
            }
        });



        button2020.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,AwardsActivity.class);
                intent.putExtra("i","2020");
                startActivity(intent);
            }
        });
        button2019.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,AwardsActivity.class);
                intent.putExtra("i","2019");
                startActivity(intent);
            }
        });
        button2018.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,AwardsActivity.class);
                intent.putExtra("i","2018");
                startActivity(intent);
            }
        });
        button2017.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,AwardsActivity.class);
                intent.putExtra("i","2017");
                startActivity(intent);
            }
        });
        button2016.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,AwardsActivity.class);
                intent.putExtra("i","2016");
                startActivity(intent);
            }
        });
        button2015.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,AwardsActivity.class);
                intent.putExtra("i","2015");
                startActivity(intent);
            }
        });
        button2014.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,AwardsActivity.class);
                intent.putExtra("i","2014");
                startActivity(intent);
            }
        });
        button2013.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,AwardsActivity.class);
                intent.putExtra("i","2013");
                startActivity(intent);
            }
        });



        functional1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this, FunctionalListActivity.class);
                intent.putExtra("i","MUJ");
                startActivity(intent);
            }
        });

        functional2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this, FunctionalListActivity.class);
                intent.putExtra("i","MIT");
                startActivity(intent);
            }
        });

        functional3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this, FunctionalListActivity.class);
                intent.putExtra("i","SMU");
                startActivity(intent);
            }
        });



        student1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,StudentActivity.class);
                intent.putExtra("i","1");
                startActivity(intent);
            }
        });
        student2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,StudentActivity.class);
                intent.putExtra("i","2");
                startActivity(intent);
            }
        });
        student3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,StudentActivity.class);
                intent.putExtra("i","3");
                startActivity(intent);
            }
        });
        student4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,StudentActivity.class);
                intent.putExtra("i","4");
                startActivity(intent);
            }
        });
        student5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,StudentActivity.class);
                intent.putExtra("i","5");
                startActivity(intent);
            }
        });



        rq_access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                referenceRequests.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            int noOfRequests = (int) dataSnapshot.child(currentFrame).getChildrenCount();
                            noOfRequests++;
                            referenceRequests.child(currentFrame).child(""+noOfRequests).child("ID").setValue(userID);
                            referenceRequests.child(currentFrame).child(""+noOfRequests).child("Name").setValue(name);
                            rq_access.setVisibility(View.GONE);
                            rq_sent.setVisibility(View.VISIBLE);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("splash","0");
                        startActivity(intent);
                        return true;
                    case R.id.nav_board:
                        startActivity(new Intent(getApplicationContext()
                                ,ClassActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_chats:
                        startActivity(new Intent(getApplicationContext()
                                ,ChatActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_about:
                        return true;
                }
                return false;
            }
        });
    }
}