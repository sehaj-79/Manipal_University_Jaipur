package com.aliferous.mujapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.AwardsAdapter;
import Adapter.FacultyAdapter;
import Model.Awards;
import Model.Faculty;

public class AwardsActivity extends AppCompatActivity {

    ImageView back;
    TextView year;

    DatabaseReference reference;

    private RecyclerView recyclerView;
    private AwardsAdapter userAdapter;
    private List<Awards> mUsers;

    String i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awards);

        back = findViewById(R.id.awards_back);
        year = findViewById(R.id.awards_year);
        recyclerView = findViewById(R.id.awards_recycler_view);

        i= getIntent().getStringExtra("i");
        reference = FirebaseDatabase.getInstance().getReference("Awards").child(i);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AwardsActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("Name").getValue().toString();
                year.setText(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AwardsActivity.this));

        mUsers = new ArrayList<>();

        readUsers();


    }

    private void readUsers() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Awards").child(i).child("AwardList");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    mUsers.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Awards user = snapshot.getValue(Awards.class);

                        if (user != null && user.getName() != null) {
                            mUsers.add(user);
                        }

                        userAdapter = new AwardsAdapter(AwardsActivity.this, mUsers, false);
                        recyclerView.setAdapter(userAdapter);
                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
