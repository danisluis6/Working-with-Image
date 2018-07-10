package tutorial.lorence.template.view.activities.home.fragment.content;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import tutorial.lorence.template.R;
import tutorial.lorence.template.app.Application;
import tutorial.lorence.template.custom.SnackBarLayout;
import tutorial.lorence.template.data.storage.database.entities.Schedule;
import tutorial.lorence.template.di.module.HomeModule;
import tutorial.lorence.template.di.module.ScheduleModule;
import tutorial.lorence.template.other.Constants;
import tutorial.lorence.template.other.Utils;
import tutorial.lorence.template.service.asyntask.DownloadImage;
import tutorial.lorence.template.view.activities.home.HomeActivity;
import tutorial.lorence.template.view.activities.home.fragment.adapter.ScheduleAdapter;
import tutorial.lorence.template.view.activities.home.loading.FragmentLoading;
import tutorial.lorence.template.view.dialog.DialogLoading;
import tutorial.lorence.template.view.fragments.BaseFragment;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

@SuppressLint("ValidFragment")
public class FragmentContent extends BaseFragment implements ContentView, SnackBarLayout.DialogInterface, HomeActivity.HomeInterface {

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
    ContentPresenter mContentPresenter;

    @Inject
    FragmentContent mFragmentContent;

    @Inject
    Snackbar mSnackbar;

    @Inject
    SnackBarLayout mSnackBarLayout;

    @Inject
    DialogLoading loadingDialog;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Disposable mDisposable;

    public void distributedDaggerComponents() {
        Application.getInstance()
                .getAppComponent()
                .plus(new HomeModule((HomeActivity) getActivity()))
                .plus(new ScheduleModule((HomeActivity) getActivity(), this, this))
                .inject(this);
    }

    @SuppressLint("ValidFragment")
    public FragmentContent() {
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
        showDialogProgress();
        return view;
    }

    private void showDialogProgress() {
        mContentPresenter.getItems("hello");
        mFragmentManager = this.getChildFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.fragment_container, mFragmentLoading);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    @Override
    public void initComponents() {
        mSnackBarLayout.attachDialogInterface(this);
        mSnackBarLayout.setPadding(0, 0, 0, 0);
        mHomeActivity.attachHomeInterface(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onGetItemsSuccess(ArrayList<Schedule> items) {
        if (isStateSaved()) {
            return;
        }
        new Handler().post(new Runnable() {
            public void run() {
                if (getChildFragmentManager().getBackStackEntryCount() > 0) {
                    mFragmentManager.popBackStack();
                }
            }
        });
    }

    @Override
    public void onGetItemsFailure(String message) {

    }

    @Override
    public void setDisposable(Disposable disposable) {
        mDisposable = disposable;
    }

    @OnClick({R.id.fragment_container})
    void onClick(View v) {
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

    @Override
    public void openCamera() {
        if (Utils.isDoubleClick()) {
            return;
        }
        if (Utils.checkPermissionCamera(mHomeActivity)) {
            // TODO
        } else {
            Utils.settingPermissionCameraOnFragment(this);
        }
    }

    @Override
    public void openGallery() {
    }

    @Override
    public void openStorage() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSION_CAMERA:
                for (int permissionId : grantResults) {
                    if (permissionId != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(mContext, getString(R.string.error_permission), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onBackPressOnFragment() {
    }

}