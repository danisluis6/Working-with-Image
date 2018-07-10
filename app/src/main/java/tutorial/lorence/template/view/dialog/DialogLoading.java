package tutorial.lorence.template.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;

import tutorial.lorence.template.R;

public class DialogLoading extends Dialog {
    /**
     * Activity
     */
    private Activity mActivity;

    /**
     * Initiate dialog.
     *
     * @param activity activity
     */
    public DialogLoading(Activity activity) {
        super(activity);
        this.mActivity = activity;
        init();
    }

    /**
     * Initiate the views.
     */
    @SuppressWarnings("ConstantConditions")
    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        setContentView(R.layout.vg_progress_dialog);
        setCancelable(false);
    }

    @Override
    public void show() {
        if (mActivity != null && !mActivity.isFinishing()) {
            super.show();
        }
    }

    @Override
    public void dismiss() {
        if (mActivity != null && !mActivity.isFinishing()) {
            super.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}