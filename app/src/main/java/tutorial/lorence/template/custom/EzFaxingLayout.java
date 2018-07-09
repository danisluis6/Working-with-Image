package tutorial.lorence.template.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import tutorial.lorence.template.R;
import tutorial.lorence.template.other.Utils;

/**
 * Using custom layout to display message on each screen make everything will be consistent
 * for example such as: no internet connection,..vv
 * Created by lorence on 18/09/2017.
 */

public class EzFaxingLayout extends LinearLayout {
    @BindView(R.id.tvLayoutMessage)
    TextView tvLayoutMessage;
    @BindView(R.id.imvBtnLayoutRetry)
    View imvBtnLayoutRetry;

    private OnRetryListener mOnRetryListener;

    public EzFaxingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_custom, this, true);
        ButterKnife.bind(this, view);
        imvBtnLayoutRetry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isDoubleClick()) {
                    return;
                }
                mOnRetryListener.onRetry();
            }
        });
    }

    public void setListenerRetry(OnRetryListener onRetryListener) {
        this.mOnRetryListener = onRetryListener;
    }

    public void setupMessageDisplay(String _message) {
        tvLayoutMessage.setText(_message);
    }

    public void setVisibleButtonRetry(boolean status) {
        if (status) {
            imvBtnLayoutRetry.setVisibility(VISIBLE);
        } else {
            imvBtnLayoutRetry.setVisibility(GONE);
        }
    }

    public interface OnRetryListener {
        void onRetry();
    }
}
