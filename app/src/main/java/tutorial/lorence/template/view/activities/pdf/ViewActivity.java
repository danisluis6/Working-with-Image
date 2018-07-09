package tutorial.lorence.template.view.activities.pdf;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import tutorial.lorence.template.R;
import tutorial.lorence.template.app.Application;
import tutorial.lorence.template.custom.pdfviewer.PDFView;
import tutorial.lorence.template.custom.pdfviewer.listener.OnDrawListener;
import tutorial.lorence.template.custom.pdfviewer.listener.OnErrorListener;
import tutorial.lorence.template.custom.pdfviewer.listener.OnLoadCompleteListener;
import tutorial.lorence.template.custom.pdfviewer.listener.OnPageChangeListener;
import tutorial.lorence.template.di.module.ViewModule;
import tutorial.lorence.template.view.activities.BaseActivity;
import tutorial.lorence.template.view.activities.home.loading.FragmentLoading;
import tutorial.lorence.template.view.activities.pdf.adapter.CustomZoomViewAdapter;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ViewActivity extends BaseActivity implements OnPageChangeListener, IView {

    @BindView(R.id.pdfViewer)
    PDFView pdfViewer;

    @BindView(R.id.tvPdfPaging)
    TextView tvPdfPaging;

    @BindView(R.id.faxViewerLayout)
    FrameLayout faxViewerLayout;

    @Inject
    FragmentLoading mFragmentLoading;

    private int mCurrentPage = 1;
    private int mPageCount = 0;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Override
    public void distributedDaggerComponents() {
        Application.getInstance()
                .getAppComponent()
                .plus(new ViewModule(this, this))
                .inject(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_view;
    }

    @Override
    protected void initAttributes(Bundle savedInstanceState) {
        super.initAttributes(savedInstanceState);
        final String mCurrentPath = getIntent().getStringExtra("Path");
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
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    mFragmentManager.popBackStack();
                }
            }

            @Override
            public void onError(Throwable e) {
            }
        });
        initPDFEvent();
    }

    private void initPDFEvent() {
        CustomZoomViewAdapter customZoomViewAdapter = new CustomZoomViewAdapter(this, faxViewerLayout);
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

    private void showDialogProgress() {
        mFragmentManager = getSupportFragmentManager();
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
