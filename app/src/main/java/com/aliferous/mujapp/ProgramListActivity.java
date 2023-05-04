package com.aliferous.mujapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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

import Adapter.ProgramAdapter;
import Model.Program;

public class ProgramListActivity extends AppCompatActivity {
   
    ImageView back;
    TextView dep;

    DatabaseReference reference;

    private RecyclerView recyclerView;

    private ProgramAdapter userAdapter;
    private List<Program> mUsers;

    EditText search_program;
    String i;

    int school = 0, department = 0;



    // School of Computing & Information Technology Engineering : Department of Computer Science Engineering, Department of Information Technology Engineering, Department of Computer & Communication Engineering
    // School of Electrical, Electronics & Communication Engineering : Department of Electrical Engineering, Department of Electronics & Communication Engineering
    // School of Automobile & Mechanical Engineering : Department of Automobile Engineering, Department of Mechanical Engineering, Department of Mechatronics Engineering
    // School of Civil & Chemical Engineering : Department of Civil Engineering, Department of Chemical Engineering


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_list);


        back = findViewById(R.id.programlist_back);
        dep = findViewById(R.id.programlist_department);
        recyclerView = findViewById(R.id.program_recycler_view);
        search_program = findViewById(R.id.search_program);






        i= getIntent().getStringExtra("i");
        reference = FirebaseDatabase.getInstance().getReference("Programs").child(i);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProgramListActivity.this,AboutActivity.class);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(ProgramListActivity.this));

        mUsers = new ArrayList<>();

        readUsers();

        search_program.addTextChangedListener(new TextWatcher() {
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


    }

    private void searchUsers(String s) {

        Query query = FirebaseDatabase.getInstance().getReference("Programs").child(i).child("Courses").orderByChild("search")
                .startAt(s)
                .endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Program user = snapshot.getValue(Program.class);

                    assert user != null;
                    if (user.getName() != null){
                        mUsers.add(user);
                    }
                }

                userAdapter = new ProgramAdapter(ProgramListActivity.this, mUsers, false);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void readUsers() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Programs").child(i).child("Courses");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (search_program.getText().toString().equals("")) {
                    mUsers.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Program user = snapshot.getValue(Program.class);


                            if (user!=null && user.getName() != null ) {
                                mUsers.add(user);
                            }


                    }

                    userAdapter = new ProgramAdapter(ProgramListActivity.this, mUsers, false);
                    recyclerView.setAdapter(userAdapter);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}