package com.aliferous.mujapp;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.FacultyAdapter;
import Model.Faculty;

public class FacultyListActivity extends AppCompatActivity {

    ImageView back;
    TextView dep;

    DatabaseReference reference;

    private RecyclerView recyclerView;

    private FacultyAdapter userAdapter;
    private List<Faculty> mUsers;

    EditText search_faculty;
    String i;
    String[] sortby_list = new String[]{""};

    Spinner sortby_spinner,sortby_spinner1,sortby_spinner2,sortby_spinner3,sortby_spinner4;

    EditText sortby,sub_sortby;
    int school = 0, department = 0;

    Button button;

    // School of Computing & Information Technology Engineering : Department of Computer Science Engineering, Department of Information Technology Engineering, Department of Computer & Communication Engineering
    // School of Electrical, Electronics & Communication Engineering : Department of Electrical Engineering, Department of Electronics & Communication Engineering
    // School of Automobile & Mechanical Engineering : Department of Automobile Engineering, Department of Mechanical Engineering, Department of Mechatronics Engineering
    // School of Civil & Chemical Engineering : Department of Civil Engineering, Department of Chemical Engineering


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_list);


        back = findViewById(R.id.facultylist_back);
        dep = findViewById(R.id.facultylist_department);
        recyclerView = findViewById(R.id.faculty_recycler_view);
        search_faculty = findViewById(R.id.search_faculty);
        sortby_spinner = findViewById(R.id.faculty_sortby_spinner);
        sortby_spinner1 = findViewById(R.id.faculty_sortby_spinner1);
        sortby_spinner2 = findViewById(R.id.faculty_sortby_spinner2);
        sortby_spinner3 = findViewById(R.id.faculty_sortby_spinner3);
        sortby_spinner4 = findViewById(R.id.faculty_sortby_spinner4);



        sortby = findViewById(R.id.faculty_sortby);
        sub_sortby = findViewById(R.id.faculty_sub_sortby);


        sortby_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String st_school = adapterView.getSelectedItem().toString();
                if (st_school.equals("School of Computing & Information Technology Engineering")){
                    school=1;
                    department=0;
                    sortby_spinner1.setVisibility(View.VISIBLE);
                    sortby_spinner2.setVisibility(View.GONE);
                    sortby_spinner3.setVisibility(View.GONE);
                    sortby_spinner4.setVisibility(View.GONE);
                    readUsers();
                }
                else if (st_school.equals("School of Electrical, Electronics & Communication Engineering")){
                    school=2;
                    department=0;
                    sortby_spinner2.setVisibility(View.VISIBLE);
                    sortby_spinner1.setVisibility(View.GONE);
                    sortby_spinner3.setVisibility(View.GONE);
                    sortby_spinner4.setVisibility(View.GONE);
                    readUsers();
                }
                else if (st_school.equals("School of Automobile & Mechanical Engineering")){
                    school=3;
                    department=0;
                    sortby_spinner3.setVisibility(View.VISIBLE);
                    sortby_spinner1.setVisibility(View.GONE);
                    sortby_spinner2.setVisibility(View.GONE);
                    sortby_spinner4.setVisibility(View.GONE);
                    readUsers();
                }
                else if (st_school.equals("School of Civil & Chemical Engineering")){
                    school=4;
                    department=0;
                    sortby_spinner4.setVisibility(View.VISIBLE);
                    sortby_spinner1.setVisibility(View.GONE);
                    sortby_spinner2.setVisibility(View.GONE);
                    sortby_spinner3.setVisibility(View.GONE);
                    readUsers();
                }
                else
                {   school=0;
                    department=0;
                    sortby_spinner1.setVisibility(View.GONE);
                    sortby_spinner2.setVisibility(View.GONE);
                    sortby_spinner3.setVisibility(View.GONE);
                    sortby_spinner4.setVisibility(View.GONE);
                    readUsers();
                }


                //readUsers();



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sortby_spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String st_dep = adapterView.getSelectedItem().toString();
                if (st_dep.equals("Department of Computer Science Engineering")){
                    department=1;
                }
                else if (st_dep.equals("Department of Information Technology Engineering")){
                    department=2;
                }
                else if (st_dep.equals("Department of Computer & Communication Engineering")){
                    department=3;
                }
                else
                    department=0;

                readUsers();



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sortby_spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String st_dep = adapterView.getSelectedItem().toString();
                if (st_dep.equals("Department of Electrical Engineering")){
                    department=1;
                }
                else if (st_dep.equals("Department of Electronics & Communication Engineering")){
                    department=2;
                }
                else
                    department=0;

                readUsers();



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        sortby_spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String st_dep = adapterView.getSelectedItem().toString();
                if (st_dep.equals("Department of Automobile Engineering")){
                    department=1;
                }
                else if (st_dep.equals("Department of Mechanical Engineering")){
                    department=2;
                }
                else if (st_dep.equals("Department of Mechatronics Engineering")){
                    department=3;
                }
                else
                    department=0;

                readUsers();



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        sortby_spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String st_dep = adapterView.getSelectedItem().toString();
                if (st_dep.equals("Department of Civil Engineering")){
                    department=1;
                }
                else if (st_dep.equals("Department of Chemical Engineering")){
                    department=2;
                }
                else
                    department=0;

                readUsers();



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        button = findViewById(R.id.button);

        i= getIntent().getStringExtra("i");
        reference = FirebaseDatabase.getInstance().getReference("Faculty").child(i);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FacultyListActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("Name").getValue().toString();
                dep.setText(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(FacultyListActivity.this));

        mUsers = new ArrayList<>();

        readUsers();

        search_faculty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUsers(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setText(""+school);
                if(sortby.getText().toString().equals("1"))
                {
                    school=1;

                    if(sub_sortby.getText().toString().equals("1"))
                    {
                        department = 1;
                    }
                    else if(sub_sortby.getText().toString().equals("2"))
                    {
                        department = 2;
                    }
                    if(sub_sortby.getText().toString().equals("3"))
                    {
                        department = 3;
                    }
                }
                else if(sortby.getText().toString().equals("2"))
                {
                    school=2;
                    if(sub_sortby.getText().toString().equals("1"))
                    {
                        department = 1;
                    }
                    else if(sub_sortby.getText().toString().equals("2"))
                    {
                        department = 2;
                    }
                }
                else if(sortby.getText().toString().equals("3"))
                {
                    school=3;
                    if(sub_sortby.getText().toString().equals("1"))
                    {
                        department = 1;
                    }
                    else if(sub_sortby.getText().toString().equals("2"))
                    {
                        department = 2;
                    }
                    if(sub_sortby.getText().toString().equals("3"))
                    {
                        department = 3;
                    }
                }
                else if(sortby.getText().toString().equals("4"))
                {
                    school=4;
                    if(sub_sortby.getText().toString().equals("1"))
                    {
                        department = 1;
                    }
                    else if(sub_sortby.getText().toString().equals("2"))
                    {
                        department = 2;
                    }
                }

                readUsers();
            }
        });*/
    }

    private void searchUsers(String s) {

        Query query = FirebaseDatabase.getInstance().getReference("Faculty").child(i).child("Teachers").orderByChild("Search")
                .startAt(s)
                .endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Faculty user = snapshot.getValue(Faculty.class);

                    assert user != null;
                    if (user.getName() != null){
                        mUsers.add(user);
                    }
                }

                userAdapter = new FacultyAdapter(FacultyListActivity.this, mUsers, false);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void readUsers() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Faculty").child(i).child("Teachers");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (search_faculty.getText().toString().equals("")) {
                    mUsers.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Faculty user = snapshot.getValue(Faculty.class);

                        if (school == 0)
                        {
                            if (user!=null && user.getName() != null ) {
                                mUsers.add(user);
                            }
                        }

                        if (school == 1)
                        {
                            if(department == 0) {
                                if (user != null && user.getName() != null && user.getSchool().equals("School of Computing & Information Technology Engineering")) {
                                    mUsers.add(user);
                                }
                            }
                            else if(department == 1) {
                                if (user != null && user.getName() != null && user.getDepartment().equals("Department of Computer Science Engineering") ) {
                                    mUsers.add(user);
                                }
                            }
                            else if(department == 2) {
                                if (user != null && user.getName() != null && user.getDepartment().equals("Department of Information Technology Engineering")) {
                                    mUsers.add(user);
                                }
                            }
                            else if(department == 3) {
                                if (user != null && user.getName() != null && user.getDepartment().equals("Department of Computer & Communication Engineering")) {
                                    mUsers.add(user);
                                }
                            }

                        }
                        else if (school == 2)
                        {
                            if(department == 0) {
                                if (user!=null && user.getName() != null && user.getSchool().equals("School of Electrical, Electronics & Communication Engineering")) {
                                    mUsers.add(user);
                                }
                            }
                            else if(department == 1) {
                                if (user != null && user.getName() != null && user.getDepartment().equals("Department of Electrical Engineering") ) {
                                    mUsers.add(user);
                                }
                            }
                            else if(department == 2) {
                                if (user != null && user.getName() != null && user.getDepartment().equals("Department of Electronics & Communication Engineering")) {
                                    mUsers.add(user);
                                }
                            }

                        }
                        else if (school == 3)
                        {
                            if(department == 0) {
                                if (user!=null && user.getName() != null && user.getSchool().equals("School of Automobile & Mechanical Engineering")) {
                                    mUsers.add(user);
                                }
                            }
                            else if(department == 1) {
                                if (user != null && user.getName() != null && user.getDepartment().equals("Department of Automobile Engineering") ) {
                                    mUsers.add(user);
                                }
                            }
                            else if(department == 2) {
                                if (user != null && user.getName() != null && user.getDepartment().equals("Department of Mechanical Engineering")) {
                                    mUsers.add(user);
                                }
                            }
                            else if(department == 3) {
                                if (user != null && user.getName() != null && user.getDepartment().equals("Department of Mechatronics Engineering")) {
                                    mUsers.add(user);
                                }
                            }
                        }
                        else if (school == 4)
                        {
                            if(department == 0) {
                                if (user!=null && user.getName() != null && user.getSchool().equals("School of Civil & Chemical Engineering")) {
                                    mUsers.add(user);
                                }
                            }
                            else if(department == 1) {
                                if (user != null && user.getName() != null && user.getDepartment().equals("Department of Civil Engineering") ) {
                                    mUsers.add(user);
                                }
                            }
                            else if(department == 2) {
                                if (user != null && user.getName() != null && user.getDepartment().equals("Department of Chemical Engineering")) {
                                    mUsers.add(user);
                                }
                            }
                        }

                    }

                    userAdapter = new FacultyAdapter(FacultyListActivity.this, mUsers, false);
                    recyclerView.setAdapter(userAdapter);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}





// School of Computing & Information Technology Engineering : Department of Computer Science Engineering, Department of Information Technology Engineering, Department of Computer & Communication Engineering
// School of Electrical, Electronics & Communication Engineering : Department of Electrical Engineering, Department of Electronics & Communication Engineering
// School of Automobile & Mechanical Engineering : Department of Automobile Engineering, Department of Mechanical Engineering, Department of Mechatronics Engineering
// School of Civil & Chemical Engineering : Department of Civil Engineering, Department of Chemical Engineering
