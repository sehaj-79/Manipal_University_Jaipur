package Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aliferous.mujapp.MessageActivity;
import com.aliferous.mujapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import Model.Incharge;

import static android.content.ContentValues.TAG;

public class InchargeAdapter extends RecyclerView.Adapter<InchargeAdapter.ViewHolder>{
    private Context mContext;
    private List<Incharge> mUsers;
    private String dd_val;




    public InchargeAdapter(Context mContext, List<Incharge> mUsers, String dd_val, boolean b) {
        this.mUsers = mUsers;
        this.mContext = mContext;
        this.dd_val = dd_val;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.incharge_item,parent,false);
        return new InchargeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {


        final Incharge user = mUsers.get(position);
        Log.i(TAG, "onBindViewHolder: "+position);

        final String check_noreq = user.getName();

        if (check_noreq.equals("No Incharge"))
        {
            holder.f_name.setVisibility(View.GONE);
            holder.f_chat.setVisibility(View.GONE);
        }
        holder.f_name.setText(user.getName());

     //   final DatabaseReference referenceIncharge = FirebaseDatabase.getInstance().getReference("Access").child("Incharge").child(dd_val);
        final DatabaseReference referenceList = FirebaseDatabase.getInstance().getReference("Access").child("Incharge").child(dd_val);


        holder.f_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userid", user.getID());
                mContext.startActivity(intent);


            }
        });


    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView f_name,f_chat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            f_name = itemView.findViewById(R.id.incharge_name);
            f_chat = itemView.findViewById(R.id.button_chat);



        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}