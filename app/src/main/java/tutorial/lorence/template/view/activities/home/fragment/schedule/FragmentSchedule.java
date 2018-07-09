package tutorial.lorence.template.view.activities.home.fragment.schedule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import tutorial.lorence.template.R;
import tutorial.lorence.template.app.Application;
import tutorial.lorence.template.custom.SnackBarLayout;
import tutorial.lorence.template.custom.pdfviewer.PDFView;
import tutorial.lorence.template.custom.pdfviewer.listener.OnDrawListener;
import tutorial.lorence.template.custom.pdfviewer.listener.OnErrorListener;
import tutorial.lorence.template.custom.pdfviewer.listener.OnLoadCompleteListener;
import tutorial.lorence.template.custom.pdfviewer.listener.OnPageChangeListener;
import tutorial.lorence.template.data.storage.database.entities.Folder;
import tutorial.lorence.template.data.storage.database.entities.Schedule;
import tutorial.lorence.template.di.module.HomeModule;
import tutorial.lorence.template.di.module.ScheduleModule;
import tutorial.lorence.template.other.Constants;
import tutorial.lorence.template.other.Utils;
import tutorial.lorence.template.other.storage.StorageActivity;
import tutorial.lorence.template.service.asyntask.DownloadImage;
import tutorial.lorence.template.view.activities.home.HomeActivity;
import tutorial.lorence.template.view.activities.home.fragment.adapter.ScheduleAdapter;
import tutorial.lorence.template.view.activities.home.loading.FragmentLoading;
import tutorial.lorence.template.view.activities.pdf.adapter.CustomZoomViewAdapter;
import tutorial.lorence.template.view.fragments.BaseFragment;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

@SuppressLint("ValidFragment")
public class FragmentSchedule extends BaseFragment implements ScheduleView, SnackBarLayout.DialogInterface, HomeActivity.HomeInterface, OnPageChangeListener {

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

    @BindView(R.id.pdfViewer)
    PDFView pdfViewer;

    @BindView(R.id.tvPdfPaging)
    TextView tvPdfPaging;

    @BindView(R.id.faxViewerLayout)
    FrameLayout faxViewerLayout;

    private int mCurrentPage = 1;
    private int mPageCount = 0;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private Disposable mDisposable;
    private ArrayList<Folder> arrFolder;

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
        initPDFEvent();
        initComponents();
        return view;
    }

    private void initPDFEvent() {
        CustomZoomViewAdapter customZoomViewAdapter = new CustomZoomViewAdapter(mHomeActivity, faxViewerLayout);
        customZoomViewAdapter.setGestureEventInterface(new PDFView.GestureEventInterface() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return pdfViewer.onScroll(e1, e2, distanceX, distanceY);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return pdfViewer.onFling(e1, e2, velocityX, velocityY);
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return pdfViewer.onDown(event);
            }

            @Override
            public void handleEndScroll(MotionEvent event) {
                pdfViewer.handleEndScroll(event);
            }
        });
    }

    @Override
    public void initComponents() {
        mSnackBarLayout.attachDialogInterface(this);
        mHomeActivity.attachHomeInterface(this);
        arrFolder = new ArrayList<>();
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_attachment:
                if (mSnackbar.isShown())
                    mSnackbar.dismiss();
                else
                    mSnackbar.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetItemsSuccess(ArrayList<Schedule> items) {
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

    @Override
    public void openStorage() {
        if (Utils.checkPermissionReadExternalStorage(mHomeActivity)) {
            Utils.settingPermissionReadExternalOnFragment(this);
        } else {
            startAccessStorage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSION_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startAccessStorage();
                } else {
                    Toast.makeText(mContext, getString(R.string.error_permission), Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }
    }

    private void startAccessStorage() {
        File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        extractSubFolder(root);
    }

    private void extractSubFolder(File root) {
        if (mSnackbar.isShown())
            mSnackbar.dismiss();
        File[] files = root.listFiles();
        arrFolder.clear();
        for (File f : files) {
            arrFolder.add(new Folder(R.drawable.ic_folder, f.getName(), (f.isDirectory() ? "Directory" : "File"), f.getAbsolutePath()));
        }
        Intent intent = new Intent(mHomeActivity, StorageActivity.class);
        intent.putParcelableArrayListExtra("arrFolder", arrFolder);
        startActivityForResult(intent, Constants.REQUEST_STORAGE);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_STORAGE:
                    final String mCurrentPath = data.getStringExtra("Path");
//                    Intent _viewIntent = new Intent(mHomeActivity, ViewActivity.class);
//                    _viewIntent.putExtra("Path", mCurrentPath);
//                    startActivity(_viewIntent);
                    Completable.fromAction(new Action() {
                        @Override
                        public void run() throws Exception {
                            showDialogProgress();
                            mPageCount = calculateNumberPage(new File(mCurrentPath));
                            loadPdfFile(new File(mCurrentPath));
                        }
                    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onComplete() {
                            if (getChildFragmentManager().getBackStackEntryCount() > 0) {
                                mFragmentManager.popBackStack();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                        }
                    });
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressOnFragment() {
    }

    private void showDialogProgress() {
        mFragmentManager = getChildFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.fragment_container, mFragmentLoading);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    private int calculateNumberPage(File file) {
        try {
            return PDDocument.load(file).getNumberOfPages();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void loadPdfFile(File file) {
        pdfViewer.fromFile(file)
                .defaultPage(mCurrentPage)
                .enableDoubletap(false)
                .onPageChange(this)
                .swipeVertical(false)
                .showMinimap(false)
                .enableAnnotationRendering(true)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        setupPaging();
                    }
                })
                .onDraw(new OnDrawListener() {
                    @Override
                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
                        // TODO
                    }
                })
                .onError(new OnErrorListener() {
                    @Override
                    public void onError(Throwable t) {
                        // TODO
                    }
                }).load();
    }

    private void setupPaging() {
        if (mPageCount > 1) {
            tvPdfPaging.setText(getString(R.string.pageCount, 1, mPageCount));
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        if (pageCount > 1) {
            tvPdfPaging.setText(getString(R.string.pageCount, page, pageCount));
        }
        mCurrentPage = page;
    }
}