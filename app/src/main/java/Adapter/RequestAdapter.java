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

import Model.Request;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder>{
    private Context mContext;
    private List<Request> mUsers;
    private String dd_val;




    public RequestAdapter(Context mContext, List<Request> mUsers, String dd_val, boolean b) {
        this.mUsers = mUsers;
        this.mContext = mContext;
        this.dd_val = dd_val;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.request_item,parent,false);
        return new RequestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {


        final Request user = mUsers.get(position);
        Log.i(TAG, "onBindViewHolder: "+position);

        final String check_noreq = user.getName();

        if (check_noreq.equals("No Requests"))
        {
            holder.f_name.setVisibility(View.GONE);
            holder.f_category.setVisibility(View.GONE);
            holder.f_accept.setVisibility(View.GONE);
            holder.f_decline.setVisibility(View.GONE);
        }

        holder.f_name.setText(user.getName());
        holder.f_category.setText(user.getCategory());

        final DatabaseReference referenceRequests = FirebaseDatabase.getInstance().getReference("Access").child("Requests").child(dd_val);
        final DatabaseReference referenceList = FirebaseDatabase.getInstance().getReference("Access").child("List").child(dd_val);

        holder.f_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String name = user.getName();
                final String id = user.getID();
                String category = user.getCategory();


                referenceList.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int noOfUsers = (int) dataSnapshot.getChildrenCount();
                        noOfUsers++;
                        referenceList.child(""+noOfUsers).child("ID").setValue(id);
                        referenceList.child(""+noOfUsers).child("Name").setValue(name);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



                referenceRequests.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        int noORequests = (int) dataSnapshot.getChildrenCount();





                        for (int i=2;i<=500;i++){
                            if (dataSnapshot.hasChild(""+i)){
                                String checkID = dataSnapshot.child(""+i).child("ID").getValue().toString();
                                if (checkID.equals(id)){
                                    referenceRequests.child(""+i).child("ID").removeValue();
                                    referenceRequests.child(""+i).child("Name").removeValue();
                                    referenceRequests.child(""+i).child("Category").removeValue();
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

        holder.f_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                referenceRequests.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        int noORequests = (int) dataSnapshot.getChildrenCount();
                        String id = user.getID();

                        for (int i=2;i<=500;i++){
                            if (dataSnapshot.hasChild(""+i)){
                                String checkID = dataSnapshot.child(""+i).child("ID").getValue().toString();
                                if (checkID.equals(id)){
                                    referenceRequests.child(""+i).child("ID").removeValue();
                                    referenceRequests.child(""+i).child("Name").removeValue();
                                    referenceRequests.child(""+i).child("Category").removeValue();
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

    public TextView f_name,f_category;
    public Button f_accept,f_decline;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        f_name = itemView.findViewById(R.id.request_item_name);
        f_category = itemView.findViewById(R.id.Request_item_category);
        f_accept = itemView.findViewById(R.id.button_accept);
        f_decline = itemView.findViewById(R.id.button_decline);



    }
}

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}