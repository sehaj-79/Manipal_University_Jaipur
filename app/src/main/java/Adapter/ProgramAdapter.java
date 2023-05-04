package Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aliferous.mujapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

import Model.Program;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder>{

    private Context mContext;
    private List<Program> mUsers;


    public ProgramAdapter(Context mContext, List<Program> mUsers, boolean b) {
        this.mUsers = mUsers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.program_item,parent,false);
        return new ProgramAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Program user = mUsers.get(position);
        Log.i(TAG, "onBindViewHolder: "+position);
        holder.f_name.setText(user.getName());
        holder.f_department.setText(user.getDepartment());
        holder.f_duration.setText(user.getDuration());
        holder.f_school.setText(user.getSchool());
        holder.f_desc.setText(user.getDescription());



    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView f_name,f_department,f_school,f_desc,f_duration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            f_name = itemView.findViewById(R.id.program_item_name);
            f_department = itemView.findViewById(R.id.Program_item_department);
            f_duration = itemView.findViewById(R.id.Program_item_duration);
            f_school = itemView.findViewById(R.id.Program_item_school);
            f_desc = itemView.findViewById(R.id.Program_item_desc);

        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}