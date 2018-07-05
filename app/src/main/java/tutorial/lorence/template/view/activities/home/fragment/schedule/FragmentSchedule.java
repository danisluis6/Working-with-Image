package tutorial.lorence.template.view.activities.home.fragment.schedule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import tutorial.lorence.template.R;
import tutorial.lorence.template.app.Application;
import tutorial.lorence.template.custom.SnackBarLayout;
import tutorial.lorence.template.data.storage.database.entities.Schedule;
import tutorial.lorence.template.di.module.HomeModule;
import tutorial.lorence.template.di.module.ScheduleModule;
import tutorial.lorence.template.service.asyntask.DownloadImage;
import tutorial.lorence.template.view.activities.home.HomeActivity;
import tutorial.lorence.template.view.activities.home.fragment.adapter.ScheduleAdapter;
import tutorial.lorence.template.view.activities.home.loading.FragmentLoading;
import tutorial.lorence.template.view.fragments.BaseFragment;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

@SuppressLint("ValidFragment")
public class FragmentSchedule extends BaseFragment implements ScheduleView {

    @BindView(R.id.test)
    Button test;

    @Inject
    Context mContext;

    @Inject
    HomeActivity mHomeActivity;

    @Inject
    DownloadImage mDownloadImage;

    @Inject
    ScheduleAdapter mScheduleAdapter;

    @Inject
    FragmentLoading mFragmentLoading;

    @Inject
    SchedulePresenter mSchedulePresenter;

    @Inject
    FragmentSchedule mFragmentSchedule;

    @Inject
    Snackbar mSnackbar;

    @Inject
    SnackBarLayout mSnackBarLayout;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Disposable mDisposable;

    public void distributedDaggerComponents() {
        Application.getInstance()
                .getAppComponent()
                .plus(new HomeModule((HomeActivity) getActivity()))
                .plus(new ScheduleModule((HomeActivity) getActivity() ,this, this))
                .inject(this);
    }

    @SuppressLint("ValidFragment")
    public FragmentSchedule() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @SuppressLint("CommitTransaction")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        distributedDaggerComponents();
        bindView(view);
        initComponents();
        mFragmentManager = this.getChildFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.fragment_container, mFragmentLoading);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
        mSchedulePresenter.getItems();
        return view;
    }

    @Override
    public void initComponents() {
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSnackbar.show();
            }
        });
    }

    @Override
    public void onGetItemsSuccess(ArrayList<Schedule> items) {
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            mFragmentManager.popBackStack();
        }
    }

    @Override
    public void onGetItemsFailure(String message) {

    }

    @Override
    public void setDisposable(Disposable disposable) {
        mDisposable = disposable;
    }

    @OnClick({R.id.fragment_container})
    void onClick (View v) {
        switch (v.getId()) {
            case R.id.fragment_container:
                if (mSnackbar.isShown())
                    mSnackbar.dismiss();
                else
                    mSnackbar.show();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}