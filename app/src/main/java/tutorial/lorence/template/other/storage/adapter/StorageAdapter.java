package tutorial.lorence.template.other.storage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import tutorial.lorence.template.R;
import tutorial.lorence.template.data.storage.database.entities.Folder;
import tutorial.lorence.template.other.storage.StorageActivity;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.MyViewHolder> {

    private Context mContext;
    private List<Folder> mGroupFolder;
    private StorageActivity mActivity;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvNameFolder, tvTypeFolder;

        MyViewHolder(View view) {
            super(view);
            tvNameFolder = view.findViewById(R.id.tvNameFolder);
            tvTypeFolder = view.findViewById(R.id.tvTypeFolder);
        }
    }

    public StorageAdapter(Context context, StorageActivity activity, List<Folder> groupFolder) {
        mContext = context;
        mActivity = activity;
        mGroupFolder = groupFolder;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_folder, parent, false);
        return new MyViewHolder(itemView);
    }

    public interface StorageInterface {
        void subChildFolder(Folder folder);
    }

    private static StorageInterface listener;

    public void attachInterface(StorageInterface _interface) {
        listener = _interface;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Folder item = mGroupFolder.get(position);
        holder.tvNameFolder.setText(item.getName());
        holder.tvTypeFolder.setText(item.getType());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.subChildFolder(mGroupFolder.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGroupFolder.size();
    }

    public void updateFolder(List<Folder> folders) {
        mGroupFolder = folders;
        notifyDataSetChanged();
    }
}