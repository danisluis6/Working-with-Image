package tutorial.lorence.template.view.activities.home.fragment.schedule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.OnClick;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import tutorial.lorence.template.BuildConfig;
import tutorial.lorence.template.R;
import tutorial.lorence.template.app.Application;
import tutorial.lorence.template.custom.SnackBarLayout;
import tutorial.lorence.template.data.storage.database.entities.Schedule;
import tutorial.lorence.template.di.module.HomeModule;
import tutorial.lorence.template.di.module.ScheduleModule;
import tutorial.lorence.template.other.Constants;
import tutorial.lorence.template.other.Utils;
import tutorial.lorence.template.service.asyntask.DownloadImage;
import tutorial.lorence.template.view.activities.crop.CropImageActivity;
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
public class FragmentSchedule extends BaseFragment implements ScheduleView, SnackBarLayout.DialogInterface, HomeActivity.HomeInterface {

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
    private String mCurrentPhotoPath;

    public void distributedDaggerComponents() {
        Application.getInstance()
                .getAppComponent()
                .plus(new HomeModule((HomeActivity) getActivity()))
                .plus(new ScheduleModule((HomeActivity) getActivity(), this, this))
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
        showDialogProgress();
        return view;
    }

    private void showDialogProgress() {
        mSchedulePresenter.getItems();
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
            takePhotoByCamera();
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
                takePhotoByCamera();
                break;
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_CAMERA:
                    Bitmap bitmap = Utils.fixOrientationBugOfProcessedBitmap(mContext,
                            BitmapFactory.decodeFile(mCurrentPhotoPath), mCurrentPhotoPath);
                    Uri tempUri = Utils.getImageUri(mContext, bitmap);
                    if (tempUri != null) {
                        File newFile = new File(Utils.getRealPathFromURI(mHomeActivity, tempUri));
                        executeCroppedImage(mCurrentPhotoPath);
                    } else {
                        Toast.makeText(mContext, getString(R.string.message_wrong_image), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Constants.REQUEST_IMAGE_CROP:
//                    Bitmap _bitmap = data.getParcelableExtra("Path");
//                    setImageProfile(_bitmap);
//                    if (_bitmap.getByteCount() <= 1920) {
//                        setImageProfile(_bitmap);
//                    } else {
//                        new ExecuteSetProfileImgAsyncTask().execute(bitmap);
//                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void executeCroppedImage(final String currentPhotoPath) {
        Intent intent = new Intent(mHomeActivity, CropImageActivity.class);
        intent.putExtra("Path", currentPhotoPath);
        startActivityForResult(intent, Constants.REQUEST_IMAGE_CROP);
    }

    private void takePhotoByCamera() {
        if (mSnackbar.isShown()) mSnackbar.dismiss();
                Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photoFile = null;
                try {
                    photoFile = Utils.createImageFile();
                    mCurrentPhotoPath = photoFile.getAbsolutePath();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(mContext,
                            BuildConfig.APPLICATION_ID + getString(R.string.fileprovider),
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, Constants.REQUEST_CAMERA);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
            }
        });
    }

    @Override
    public void onBackPressOnFragment() {
    }

}