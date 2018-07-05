package tutorial.lorence.template.view.activities.home.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import tutorial.lorence.template.R;
import tutorial.lorence.template.data.storage.database.entities.Schedule;
import tutorial.lorence.template.view.activities.home.fragment.schedule.FragmentSchedule;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {

    private Context mContext;
    private List<Schedule> mGroupSchedules;
    private FragmentSchedule mFragment;

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imvPlayer, imvEnemy;
        TextView tvPlayer, tvEnemy, tvTime, tvDate;

        MyViewHolder(View view) {
            super(view);
            tvDate = view.findViewById(R.id.tvDate);
            tvTime = view.findViewById(R.id.tvTime);
            tvPlayer = view.findViewById(R.id.tvPlayer);
            tvEnemy = view.findViewById(R.id.tvEnemy);
            imvPlayer = view.findViewById(R.id.imvPlayerPath);
            imvEnemy = view.findViewById(R.id.imvEnemyPath);
        }
    }

    public ScheduleAdapter(Context context, FragmentSchedule fragment, List<Schedule> items) {
        mContext = context;
        mFragment = fragment;
        mGroupSchedules = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_schedule_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Schedule item = mGroupSchedules.get(position);
        holder.tvDate.setText(item.getDate());
        holder.tvTime.setText(item.getTime());
        holder.tvPlayer.setText(item.getPlayer());
        holder.tvEnemy.setText(item.getEnemy());
        Glide.with(mContext).load(item.getPlayerPath()).into(holder.imvPlayer);
        Glide.with(mContext).load(item.getEnemyPath()).into(holder.imvEnemy);
    }

    @Override
    public int getItemCount() {
        return mGroupSchedules.size();
    }

    public void updateSchedules(List<Schedule> list) {
        mGroupSchedules = list;
        notifyDataSetChanged();
    }
}
