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

import Model.Functional;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class FunctionalAdapter extends RecyclerView.Adapter<FunctionalAdapter.ViewHolder>{
    private Context mContext;
    private List<Functional> mUsers;


    public FunctionalAdapter(Context mContext, List<Functional> mUsers, boolean b) {
        this.mUsers = mUsers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.functional_item,parent,false);
        return new FunctionalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Functional user = mUsers.get(position);
        Log.i(TAG, "onBindViewHolder: "+position);
        holder.f_name.setText(user.getName());
        holder.f_about.setText(user.getAbout());
        holder.f_achievements.setText(user.getAchievements());
        holder.f_designation.setText(user.getDesignation());
        Glide.with(mContext).load(user.getImage()).into(holder.f_image);



    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView f_name,f_about,f_designation,f_achievements;
        public CircleImageView f_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            f_name = itemView.findViewById(R.id.functional_item_name);
            f_about = itemView.findViewById(R.id.Functional_item_about);
            f_achievements = itemView.findViewById(R.id.Functional_item_achievements);
            f_designation = itemView.findViewById(R.id.Functional_item_designtion);
            f_image = itemView.findViewById(R.id.functional_item_image);

        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}