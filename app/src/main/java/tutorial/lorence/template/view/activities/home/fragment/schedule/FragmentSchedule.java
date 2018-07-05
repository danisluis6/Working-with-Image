package tutorial.lorence.template.view.activities.home.fragment.schedule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import tutorial.lorence.template.R;
import tutorial.lorence.template.app.Application;
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

    @Inject
    Context mContext;

    @Inject
    HomeActivity mHomeActivity;

    @Inject
    DownloadImage mDownloadImage;

    @Inject
    ScheduleAdapter mScheduleAdapter;

    @Inject
    ScheduleToDayAdapter mShScheduleToDayAdapter;

    @Inject
    FragmentLoading mFragmentLoading;

    @Inject
    SchedulePresenter mSchedulePresenter;

    @Inject
    FragmentSchedule mFragmentSchedule;

    @BindView(R.id.rcvSchedules)
    RecyclerView rcvSchedules;


    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Disposable mDisposable;

    public void distributedDaggerComponents(HomeActivity homeActivity) {
        Application.getInstance()
                .getAppComponent()
                .plus(new HomeModule(homeActivity))
                .plus(new ScheduleModule(this, this))
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
        rcvSchedules.setLayoutManager(new LinearLayoutManager(mContext));
        rcvSchedules.setItemAnimator(new DefaultItemAnimator());
        rcvSchedules.setAdapter(mScheduleAdapter);
    }

    @Override
    public void onGetItemsSuccess(ArrayList<Schedule> items) {
        if (this.getChildFragmentManager().getBackStackEntryCount() > 0) {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}