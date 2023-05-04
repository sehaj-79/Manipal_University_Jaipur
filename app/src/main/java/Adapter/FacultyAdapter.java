package Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aliferous.mujapp.MessageActivity;
import com.aliferous.mujapp.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import Model.Chat;
import Model.Faculty;
import Model.Users;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.ViewHolder>{
    private Context mContext;
    private List<Faculty> mUsers;


    public FacultyAdapter(Context mContext, List<Faculty> mUsers, boolean b) {
        this.mUsers = mUsers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.faculty_item,parent,false);
        return new FacultyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.f_name.setTag(holder.f_name.getKeyListener());
        holder.f_name.setKeyListener(null);
        holder.f_department.setTag(holder.f_department.getKeyListener());
        holder.f_department.setKeyListener(null);
        holder.f_designation.setTag(holder.f_designation.getKeyListener());
        holder.f_designation.setKeyListener(null);
        holder.f_school.setTag(holder.f_school.getKeyListener());
        holder.f_school.setKeyListener(null);
        holder.f_contact.setTag(holder.f_contact.getKeyListener());
        holder.f_contact.setKeyListener(null);
        holder.f_mail.setTag(holder.f_mail.getKeyListener());
        holder.f_mail.setKeyListener(null);

        final Faculty user = mUsers.get(position);
        Log.i(TAG, "onBindViewHolder: "+position);
        holder.f_name.setText(user.getName());
        holder.f_department.setText(user.getDepartment());
        holder.f_designation.setText(user.getDesignation());
        holder.f_school.setText(user.getSchool());
        holder.f_mail.setText(user.getMail());
        holder.f_contact.setText("Contact:\n"+user.getContact());
        Glide.with(mContext).load(user.getImage()).into(holder.profile_image);

        holder.f_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.f_save.setVisibility(View.VISIBLE);
                holder.f_edit.setVisibility(View.GONE);
                holder.f_name.setKeyListener((KeyListener) holder.f_name.getTag());
                holder.f_department.setKeyListener((KeyListener) holder.f_department.getTag());
                holder.f_designation.setKeyListener((KeyListener) holder.f_designation.getTag());
                holder.f_school.setKeyListener((KeyListener) holder.f_school.getTag());
                holder.f_contact.setKeyListener((KeyListener) holder.f_contact.getTag());
                holder.f_mail.setKeyListener((KeyListener) holder.f_mail.getTag());

            }
        });


        final DatabaseReference referenceTeachers = FirebaseDatabase.getInstance().getReference("Faculty").child("1").child("Teachers");


        //Save button for edited data

        holder.f_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.f_edit.setVisibility(View.VISIBLE);
                holder.f_save.setVisibility(View.GONE);

                holder.f_name.setTag(holder.f_name.getKeyListener());
                holder.f_name.setKeyListener(null);
                holder.f_department.setTag(holder.f_department.getKeyListener());
                holder.f_department.setKeyListener(null);
                holder.f_designation.setTag(holder.f_designation.getKeyListener());
                holder.f_designation.setKeyListener(null);
                holder.f_school.setTag(holder.f_school.getKeyListener());
                holder.f_school.setKeyListener(null);
                holder.f_contact.setTag(holder.f_contact.getKeyListener());
                holder.f_contact.setKeyListener(null);
                holder.f_mail.setTag(holder.f_mail.getKeyListener());
                holder.f_mail.setKeyListener(null);




                referenceTeachers.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                        String name;

                        name = user.getName().toString();

                        String Updatedname = holder.f_name.getText().toString();
                        String UpdatedDepartment = holder.f_department.getText().toString();
                        String UpdateDesignation = holder.f_designation.getText().toString();
                        String UpdatedSchool = holder.f_school.getText().toString();
                        String UpdatedContact = holder.f_contact.getText().toString();
                        String UpdatedMail = holder.f_mail.getText().toString();


//

                        for (int i=1;i<=500;i++){
                            if (dataSnapshot.hasChild(""+i)){

                                String checkID = dataSnapshot.child(""+i).child("Name").getValue().toString();
                                if (checkID.equals(name)){
                                    
                                    referenceTeachers.child(""+i).child("Name").setValue(Updatedname);
                                    referenceTeachers.child(""+i).child("Department").setValue(UpdatedDepartment);
                                    referenceTeachers.child(""+i).child("Designation").setValue(UpdateDesignation);
                                    referenceTeachers.child(""+i).child("School").setValue(UpdatedSchool);
                                   // referenceTeachers.child(""+i).child("Contact").setValue(UpdatedContact);
                                    referenceTeachers.child(""+i).child("Mail").setValue(UpdatedMail);

                                    Toast.makeText(mContext, "Faculty Info Updated Successfully", Toast.LENGTH_SHORT).show();
                                    mUsers.clear();

                                }
                            }


                        }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                holder.f_edit.setVisibility(View.VISIBLE);
                holder.f_save.setVisibility(View.GONE);
            }
        });


    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView f_name,f_department,f_designation,f_school,f_mail,f_contact;
        public CircleImageView profile_image;
        public Button f_edit,f_save;
        public View f_shield;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            f_name = itemView.findViewById(R.id.faculty_item_name);
            f_department = itemView.findViewById(R.id.Faculty_item_department);
            f_designation = itemView.findViewById(R.id.Faculty_item_designtion);
            f_school = itemView.findViewById(R.id.Faculty_item_school);
            f_mail = itemView.findViewById(R.id.Faculty_item_mail);
            f_contact = itemView.findViewById(R.id.Faculty_item_contact);
            profile_image = itemView.findViewById(R.id.faculty_item_image);
            f_edit = itemView.findViewById(R.id.edit_data);
            f_save = itemView.findViewById(R.id.save_data);
            f_shield = itemView.findViewById(R.id.shield);



        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}