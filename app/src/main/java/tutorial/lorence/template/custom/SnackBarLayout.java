package tutorial.lorence.template.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tutorial.lorence.template.R;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class SnackBarLayout extends LinearLayout {

    @BindView(R.id.lnCamera)
    LinearLayout lnCamera;

    @BindView(R.id.lnGallery)
    LinearLayout lnGallery;

    @BindView(R.id.lnStorage)
    LinearLayout lnStorage;

    public interface DialogInterface {
        void openStorage();
    }

    private static DialogInterface listener;

    public SnackBarLayout(Context context) {
        super(context);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (mInflater == null)
            return;
        View view = mInflater.inflate(R.layout.dialog_pick, this, true);
        ButterKnife.bind(this, view);
    }

    public void attachDialogInterface(DialogInterface _interface) {
        listener = _interface;
    }

    @OnClick(R.id.lnStorage)
    public void AccessStorage() {
        listener.openStorage();
    }

}