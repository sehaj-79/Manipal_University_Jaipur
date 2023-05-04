package Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aliferous.mujapp.ClassActivity;
import com.aliferous.mujapp.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import Model.AccessList;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class AccessListAdapter extends RecyclerView.Adapter<AccessListAdapter.ViewHolder>{
    private Context mContext;
    private List<AccessList> mUsers;
    private String dd_val;




    public AccessListAdapter(Context mContext, List<AccessList> mUsers, String dd_val, boolean b) {
        this.mUsers = mUsers;
        this.mContext = mContext;
        this.dd_val = dd_val;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.aceesslist_item,parent,false);
        return new AccessListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {


        final AccessList user = mUsers.get(position);
        Log.i(TAG, "onBindViewHolder: "+position);

        final String check_noreq = user.getName();

        if (check_noreq.equals("No AccessLists"))
        {
            holder.f_name.setVisibility(View.GONE);
            holder.f_remove.setVisibility(View.GONE);
        }
        holder.f_name.setText(user.getName());

     //   final DatabaseReference referenceAccessLists = FirebaseDatabase.getInstance().getReference("Access").child("AccessLists").child(dd_val);
        final DatabaseReference referenceList = FirebaseDatabase.getInstance().getReference("Access").child("List").child(dd_val);


        holder.f_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                referenceList.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        int noORequests = (int) dataSnapshot.getChildrenCount();
                        String id = user.getID();

                        for (int i=2;i<=500;i++){
                            if (dataSnapshot.hasChild(""+i)){
                                String checkID = dataSnapshot.child(""+i).child("ID").getValue().toString();
                                if (checkID.equals(id)){
                                    referenceList.child(""+i).child("ID").removeValue();
                                    referenceList.child(""+i).child("Name").removeValue();
                                    mUsers.clear();
                                }
                            }


                        }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView f_name,f_remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            f_name = itemView.findViewById(R.id.access_item_name);
            f_remove = itemView.findViewById(R.id.button_remove);



        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}