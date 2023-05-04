package com.aliferous.mujapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.AccessListAdapter;
import Adapter.InchargeAdapter;
import Adapter.ProgramAdapter;
import Adapter.RequestAdapter;
import Model.AccessList;
import Model.Incharge;
import Model.Program;
import Model.Request;
import Model.Users;

public class ClassActivity extends AppCompatActivity {



    FrameLayout frame_incharge,frame_list,frame_requests,frame_nopermission;
    FrameLayout frame1,frame2,frame3;
    ImageView im_incharge,im_list,im_requests;
    TextView tv_incharge,tv_list,tv_requests;
    TextView tv_nopermission_rq,tv_nopermission_access;
    Spinner dd_list,dd_list_access,dd_incharge;

    String name,userID;
    String permission_faculty="No",permission_awards="No",permission_research="No",permission_placement="No",permission_programs="No",permission_functional="No",permission_students="No",permission_statutory="No";
    String dd_val="Select",dd_val_accessList="Select";
    int all_permissions=0;
    private RecyclerView recyclerView, recyclerView_list,recyclerView_incharge;

    private RequestAdapter userAdapter;
    private AccessListAdapter userAdapter_list;
    private InchargeAdapter userAdapter_incharge;

    private List<Request> mUsers;
    private List<AccessList> mUsers_list;
    private List<Incharge> mUsers_incharge;


    FirebaseUser firebaseUser;
    DatabaseReference reference,referenceIncharge,reference_requests,referenceList,referenceUID;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navbar);
        bottomNavigationView.setSelectedItemId(R.id.nav_board);

        frame1=findViewById(R.id.access_frame1);
        frame2=findViewById(R.id.access_frame2);
        frame3=findViewById(R.id.access_frame3);

        frame_incharge=findViewById(R.id.frame_incharge);
        frame_list=findViewById(R.id.frame_accesslist);
        frame_requests=findViewById(R.id.frame_requests);
        frame_nopermission=findViewById(R.id.frame_no_permission_access);

        dd_list = findViewById(R.id.abc1);
        dd_list_access = findViewById(R.id.abc2);
        dd_incharge = findViewById(R.id.abc);

        im_incharge=findViewById(R.id.image_incharge);
        im_list=findViewById(R.id.image_accesslist);
        im_requests=findViewById(R.id.image_requests);

        tv_incharge=findViewById(R.id.access_text_incharge);
        tv_list=findViewById(R.id.access_text_accesslist);
        tv_requests=findViewById(R.id.access_text_requests);

        tv_nopermission_rq = findViewById(R.id.tv_no_permission_rq);
        tv_nopermission_access = findViewById(R.id.tv_no_permission_access);

        recyclerView = findViewById(R.id.rq_recycler_view);
        recyclerView_list = findViewById(R.id.rq_recycler_view33);
        recyclerView_incharge = findViewById(R.id.rq_recycler_view32);




        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference_requests = FirebaseDatabase.getInstance().getReference("Access").child("Requests");
        referenceList = FirebaseDatabase.getInstance().getReference("Access").child("List");
        referenceIncharge = FirebaseDatabase.getInstance().getReference("Access").child("Incharge");
        referenceUID = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());


        dd_list_access.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dd_val = adapterView.getSelectedItem().toString();
                readUsers_access();

                if (dd_val.equals("Faculty"))
                {
                    if (permission_faculty.equals("Yes")){
                        tv_nopermission_access.setVisibility(View.GONE);
                        recyclerView_list.setVisibility(View.VISIBLE);
                        readUsers_access();
                    }
                    else
                    {
                        tv_nopermission_access.setVisibility(View.VISIBLE);
                        recyclerView_list.setVisibility(View.GONE);
                    }
                }

                if (dd_val.equals("Awards"))
                {
                    if (permission_awards.equals("Yes")){
                        tv_nopermission_access.setVisibility(View.GONE);
                        recyclerView_list.setVisibility(View.VISIBLE);
                        readUsers_access();
                    }
                    else
                    {
                        tv_nopermission_access.setVisibility(View.VISIBLE);
                        recyclerView_list.setVisibility(View.GONE);
                    }
                }

                if (dd_val.equals("Research"))
                {
                    if (permission_research.equals("Yes")){
                        tv_nopermission_access.setVisibility(View.GONE);
                        recyclerView_list.setVisibility(View.VISIBLE);
                        readUsers_access();
                }
                    else
                    {
                        tv_nopermission_access.setVisibility(View.VISIBLE);
                        recyclerView_list.setVisibility(View.GONE);
                    }
                }

                if (dd_val.equals("Placement"))
                {
                    if (permission_placement.equals("Yes")){
                        tv_nopermission_access.setVisibility(View.GONE);
                        recyclerView_list.setVisibility(View.VISIBLE);
                        readUsers_access();
                    }
                    else
                    {
                        tv_nopermission_access.setVisibility(View.VISIBLE);
                        recyclerView_list.setVisibility(View.GONE);
                    }
                }

                if (dd_val.equals("Programs"))
                {
                    if (permission_programs.equals("Yes")){
                        tv_nopermission_access.setVisibility(View.GONE);
                        recyclerView_list.setVisibility(View.VISIBLE);
                        readUsers_access();
                    }
                    else
                    {
                        tv_nopermission_access.setVisibility(View.VISIBLE);
                        recyclerView_list.setVisibility(View.GONE);
                    }
                }

                if (dd_val.equals("Functional Units"))
                {
                    if (permission_functional.equals("Yes")){
                        tv_nopermission_access.setVisibility(View.GONE);
                        recyclerView_list.setVisibility(View.VISIBLE);
                        readUsers_access();
                    }
                    else
                    {
                        tv_nopermission_access.setVisibility(View.VISIBLE);
                        recyclerView_list.setVisibility(View.GONE);
                    }
                }

                if (dd_val.equals("Students"))
                {
                    if (permission_students.equals("Yes")){
                        tv_nopermission_access.setVisibility(View.GONE);
                        recyclerView_list.setVisibility(View.VISIBLE);
                        readUsers_access();
                    }
                    else
                    {
                        tv_nopermission_access.setVisibility(View.VISIBLE);
                        recyclerView_list.setVisibility(View.GONE);
                    }
                }

                if (dd_val.equals("Statutory Meeting"))
                {
                    if (permission_statutory.equals("Yes")){
                        tv_nopermission_access.setVisibility(View.GONE);
                        recyclerView_list.setVisibility(View.VISIBLE);
                        readUsers_access();
                    }
                    else
                    {
                        tv_nopermission_access.setVisibility(View.VISIBLE);
                        recyclerView_list.setVisibility(View.GONE);
                    }
                }





            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dd_incharge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dd_val = adapterView.getSelectedItem().toString();
                readUsers_incharge();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dd_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dd_val = adapterView.getSelectedItem().toString();

                if (dd_val.equals("Faculty"))
                {
                    if (permission_faculty.equals("Yes")){
                        tv_nopermission_rq.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        readUsers();
                    }
                    else
                    {
                        tv_nopermission_rq.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }

                if (dd_val.equals("Awards"))
                {
                    if (permission_awards.equals("Yes")){
                        tv_nopermission_rq.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        readUsers();
                    }
                    else
                    {
                        tv_nopermission_rq.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }

                if (dd_val.equals("Research"))
                {
                    if (permission_research.equals("Yes")){
                        tv_nopermission_rq.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        readUsers();
                    }
                    else
                    {
                        tv_nopermission_rq.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }

                if (dd_val.equals("Placement"))
                {
                    if (permission_placement.equals("Yes")){
                        tv_nopermission_rq.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        readUsers();
                    }
                    else
                    {
                        tv_nopermission_rq.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }

                if (dd_val.equals("Programs"))
                {
                    if (permission_programs.equals("Yes")){
                        tv_nopermission_rq.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        readUsers();
                    }
                    else
                    {
                        tv_nopermission_rq.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }

                if (dd_val.equals("Functional Units"))
                {
                    if (permission_functional.equals("Yes")){
                        tv_nopermission_rq.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        readUsers();
                    }
                    else
                    {
                        tv_nopermission_rq.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }

                if (dd_val.equals("Students"))
                {
                    if (permission_students.equals("Yes")){
                        tv_nopermission_rq.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        readUsers();
                    }
                    else
                    {
                        tv_nopermission_rq.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }

                if (dd_val.equals("Statutory Meeting"))
                {
                    if (permission_statutory.equals("Yes")){
                        tv_nopermission_rq.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        readUsers();
                    }
                    else
                    {
                        tv_nopermission_rq.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ClassActivity.this));

        recyclerView_list.setHasFixedSize(true);
        recyclerView_list.setLayoutManager(new LinearLayoutManager(ClassActivity.this));

        recyclerView_incharge.setHasFixedSize(true);
        recyclerView_incharge.setLayoutManager(new LinearLayoutManager(ClassActivity.this));

        mUsers = new ArrayList<>();
        mUsers_list = new ArrayList<>();
        mUsers_incharge = new ArrayList<>();

        readUsers();

        readUsers_access();

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

        referenceIncharge.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




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
                            permission_students = "Yes";
                            permission_statutory = "Yes";
                            all_permissions = 1;
                        }
                    }
                }

                if (all_permissions==0){

                    for (int i=1;i<=2000;i++){
                        if (dataSnapshot.child("Faculty").hasChild(""+i))
                        {
                            String checkUID = dataSnapshot.child("Faculty").child(""+i).child("ID").getValue().toString();
                            if(checkUID.equals(userID)){
                                permission_faculty = "Yes";
                            }
                        }
                    }

                    for (int i=1;i<=2000;i++){
                        if (dataSnapshot.child("Awards").hasChild(""+i))
                        {
                            String checkUID = dataSnapshot.child("Awards").child(""+i).child("ID").getValue().toString();
                            if(checkUID.equals(userID)){
                                permission_awards = "Yes";
                            }
                        }
                    }

                    for (int i=1;i<=2000;i++){
                        if (dataSnapshot.child("Research").hasChild(""+i))
                        {
                            String checkUID = dataSnapshot.child("Research").child(""+i).child("ID").getValue().toString();
                            if(checkUID.equals(userID)){
                                permission_research = "Yes";
                            }
                        }
                    }

                    for (int i=1;i<=2000;i++){
                        if (dataSnapshot.child("Placement").hasChild(""+i))
                        {
                            String checkUID = dataSnapshot.child("Placement").child(""+i).child("ID").getValue().toString();
                            if(checkUID.equals(userID)){
                                permission_placement = "Yes";
                            }
                        }
                    }

                    for (int i=1;i<=2000;i++){
                        if (dataSnapshot.child("Programs").hasChild(""+i))
                        {
                            String checkUID = dataSnapshot.child("Programs").child(""+i).child("ID").getValue().toString();
                            if(checkUID.equals(userID)){
                                permission_programs = "Yes";
                            }
                        }
                    }

                    for (int i=1;i<=2000;i++){
                        if (dataSnapshot.child("Functional Units").hasChild(""+i))
                        {
                            String checkUID = dataSnapshot.child("Functional Units").child(""+i).child("ID").getValue().toString();
                            if(checkUID.equals(userID)){
                                permission_functional = "Yes";
                            }
                        }
                    }

                    for (int i=1;i<=2000;i++){
                        if (dataSnapshot.child("Students").hasChild(""+i))
                        {
                            String checkUID = dataSnapshot.child("Students").child(""+i).child("ID").getValue().toString();
                            if(checkUID.equals(userID)){
                                permission_students = "Yes";
                            }
                        }
                    }

                    for (int i=1;i<=2000;i++){
                        if (dataSnapshot.child("Statutory Meeting").hasChild(""+i))
                        {
                            String checkUID = dataSnapshot.child("Statutory Meeting").child(""+i).child("ID").getValue().toString();
                            if(checkUID.equals(userID)){
                                permission_statutory = "Yes";
                            }
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
                frame1.setBackgroundResource(R.drawable.vector_squareorange);
                frame2.setBackgroundResource(R.drawable.vector_square);
                frame3.setBackgroundResource(R.drawable.vector_square);
                im_incharge.setBackgroundResource(R.drawable.vector_access_inchargeorange);
                im_list.setBackgroundResource(R.drawable.vector_access_list);
                im_requests.setBackgroundResource(R.drawable.vector_access_requests);
                tv_incharge.setTextColor(getResources().getColor(R.color.orange));
                tv_list.setTextColor(getResources().getColor(R.color.grey));
                tv_requests.setTextColor(getResources().getColor(R.color.grey));
                frame_incharge.setVisibility(View.VISIBLE);
                frame_list.setVisibility(View.GONE);
                frame_requests.setVisibility(View.GONE);

                //displayincharge(tv_mon1,tv_mon2,tv_mon3,tv_mon4,tv_mon5,tv_tue1,tv_tue2,tv_tue3,tv_tue4,tv_tue5,tv_wed1,tv_wed2,tv_wed3,tv_wed4,tv_wed5,tv_thu1,tv_thu2,tv_thu3,tv_thu4,tv_thu5,tv_fri1,tv_fri2,tv_fri3,tv_fri4,tv_fri5);


            }
        });

        frame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frame2.setBackgroundResource(R.drawable.vector_squareorange);
                frame1.setBackgroundResource(R.drawable.vector_square);
                frame3.setBackgroundResource(R.drawable.vector_square);
                im_incharge.setBackgroundResource(R.drawable.vector_access_incharge);
                im_list.setBackgroundResource(R.drawable.vector_access_listorange);
                im_requests.setBackgroundResource(R.drawable.vector_access_requests);
                tv_incharge.setTextColor(getResources().getColor(R.color.grey));
                tv_list.setTextColor(getResources().getColor(R.color.orange));
                tv_requests.setTextColor(getResources().getColor(R.color.grey));
                frame_incharge.setVisibility(View.GONE);
                frame_list.setVisibility(View.VISIBLE);
                frame_requests.setVisibility(View.GONE);

            }
        });

        frame3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frame3.setBackgroundResource(R.drawable.vector_squareorange);
                frame1.setBackgroundResource(R.drawable.vector_square);
                frame2.setBackgroundResource(R.drawable.vector_square);
                im_incharge.setBackgroundResource(R.drawable.vector_access_incharge);
                im_list.setBackgroundResource(R.drawable.vector_access_list);
                im_requests.setBackgroundResource(R.drawable.vector_access_requestsorange);
                tv_incharge.setTextColor(getResources().getColor(R.color.grey));
                tv_list.setTextColor(getResources().getColor(R.color.grey));
                tv_requests.setTextColor(getResources().getColor(R.color.orange));
                frame_incharge.setVisibility(View.GONE);
                frame_list.setVisibility(View.GONE);
                frame_requests.setVisibility(View.VISIBLE);


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
                    case R.id.nav_about:
                        startActivity(new Intent(getApplicationContext()
                                ,AboutActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_chats:
                        startActivity(new Intent(getApplicationContext()
                                ,ChatActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_board:
                        return true;
                }
                return false;
            }
        });
    }


    private void readUsers() {



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Access").child("Requests").child(dd_val);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Request user = snapshot.getValue(Request.class);


                    if (user != null && user.getName() != null) {
                        mUsers.add(user);
                    }


                }

                userAdapter = new RequestAdapter(ClassActivity.this, mUsers,dd_val, false);
                recyclerView.setAdapter(userAdapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void readUsers_access() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Access").child("List").child(dd_val);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUsers_list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AccessList user = snapshot.getValue(AccessList.class);


                    if (user != null && user.getName() != null) {
                        mUsers_list.add(user);
                    }

                }
                
                userAdapter_list = new AccessListAdapter(ClassActivity.this,mUsers_list,dd_val,false);
                recyclerView_list.setAdapter(userAdapter_list);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void readUsers_incharge() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Access").child("Incharge").child(dd_val);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUsers_incharge.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Incharge user = snapshot.getValue(Incharge.class);


                    if (user != null && user.getName() != null) {
                        mUsers_incharge.add(user);
                    }

                }

                userAdapter_incharge = new InchargeAdapter(ClassActivity.this,mUsers_incharge,dd_val,false);
                recyclerView_incharge.setAdapter(userAdapter_incharge);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}


