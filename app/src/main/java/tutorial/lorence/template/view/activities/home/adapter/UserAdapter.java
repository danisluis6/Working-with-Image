package tutorial.lorence.template.view.activities.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.formats.NativeAd;

import java.util.List;

import tutorial.lorence.template.R;
import tutorial.lorence.template.data.storage.database.entities.recycler.Item;
import tutorial.lorence.template.view.activities.home.adapter.viewholder.CustomHolder;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ViewType mViewType;
    private final Context mContext;
    private List<Item> mListObject;

    /**
     * For this example app, the recyclerViewItems list contains only
     * {@link android.view.MenuItem} and {@link NativeAd} types.
     */
    public UserAdapter(Context context, ViewType viewType, List<Item> list) {
        this.mContext = context;
        this.mListObject = list;
        this.mViewType = viewType;
    }

    @Override
    public int getItemCount() {
        return mListObject.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == mViewType.USER_VIEW_TYPE) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.item_viewholder_user, viewGroup, false);
            return new CustomHolder(itemView);
        }
        return  null;
    }

    @Override
    public int getItemViewType(int position) {
        return mViewType.USER_VIEW_TYPE;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == mViewType.USER_VIEW_TYPE) {
            CustomHolder holder = (CustomHolder) viewHolder;
            Item item = mListObject.get(position);
            holder.tvUserName.setText(item.getUserid());
        }
    }

    public void updateData(List<Item> items) {
        mListObject = items;
        notifyDataSetChanged();
    }
}
