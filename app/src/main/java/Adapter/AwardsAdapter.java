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

import Model.Awards;
import Model.Faculty;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class AwardsAdapter extends RecyclerView.Adapter<AwardsAdapter.ViewHolder> {

    private Context mContext;
    private List<Awards> mUsers;

    public AwardsAdapter(Context mContext, List<Awards> mUsers, boolean b) {
        this.mUsers = mUsers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AwardsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.award_item,parent,false);
        return new AwardsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AwardsAdapter.ViewHolder holder, int position) {
        final Awards user = mUsers.get(position);
        Log.i(TAG, "onBindViewHolder: "+position);

        holder.award_det.setText(user.getName());


    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView award_det;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            award_det = itemView.findViewById(R.id.award_det);
        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}
