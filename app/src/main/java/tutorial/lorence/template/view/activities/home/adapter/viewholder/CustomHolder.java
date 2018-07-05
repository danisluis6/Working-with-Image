package tutorial.lorence.template.view.activities.home.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tutorial.lorence.template.R;

/**
 * The {@link CustomHolder} class.
 * Provides a reference to each view in the menu item view.
 */

public class CustomHolder extends RecyclerView.ViewHolder {

    public TextView tvUserName;

    public CustomHolder(View view) {
        super(view);
        tvUserName = view.findViewById(R.id.userName);
    }
}
