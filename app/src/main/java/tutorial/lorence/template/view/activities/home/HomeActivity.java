package tutorial.lorence.template.view.activities.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import tutorial.lorence.template.R;
import tutorial.lorence.template.app.Application;
import tutorial.lorence.template.data.storage.database.entities.Schedule;
import tutorial.lorence.template.di.module.HomeModule;
import tutorial.lorence.template.other.Constants;
import tutorial.lorence.template.service.JsonData;
import tutorial.lorence.template.service.asyntask.DownloadImage;
import tutorial.lorence.template.view.activities.BaseActivity;
import tutorial.lorence.template.view.activities.home.adapter.ViewPaperAdapter;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class HomeActivity extends BaseActivity implements HomeView {

    @Inject
    Context mContext;

    @Inject
    FragmentTransaction mFragmentTransaction;

    @Inject
    JsonData mJsonData;

    @Inject
    HomePresenter mHomePresenter;

    @Inject
    DownloadImage mDownloadImage;

    @Inject
    ViewPaperAdapter mPaperAdapter;


    @BindView(R.id.pager)
    ViewPager mViewPager;

    @BindView(R.id.tabHost)
    MaterialTabHost mTabHost;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private Disposable mDisposable;

    @Override
    public void distributedDaggerComponents() {
        Application.getInstance()
                .getAppComponent()
                .plus(new HomeModule(this, this))
                .inject(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initAttributes(Bundle savedInstanceState) {
        super.initAttributes(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.getWindow().setStatusBarColor(ContextCompat.getColor(HomeActivity.this, R.color.home_statusbar_color));
        }
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        mViewPager.setAdapter(mPaperAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabHost.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int index = 0; index < mPaperAdapter.getCount(); index++) {
            mTabHost.addTab(mTabHost.newTab()
                    .setText(getTabTitle(index))
                    .setTabListener(new MaterialTabListener() {
                        @Override
                        public void onTabSelected(MaterialTab tab) {
                            mTabHost.setSelectedNavigationItem(tab.getPosition());
                        }

                        @Override
                        public void onTabReselected(MaterialTab tab) {
                        }

                        @Override
                        public void onTabUnselected(MaterialTab tab) {

                        }
                    }));
        }
    }

    private String getTabTitle(int index) {
        if (index == 0) {
            return mContext.getResources().getString(R.string.tab_1);
        } else if (index == 1) {
            return mContext.getResources().getString(R.string.tab_2);
        } else if (index == 2) {
            return mContext.getResources().getString(R.string.tab_3);
        }
        return Constants.EMPTY_STRING;
    }

    @Override
    public void setDisposable(Disposable disposable) {
        mDisposable = disposable;
    }

    @Override
    public void onGetItemsSuccess(ArrayList<Schedule> items) {
    }

    @Override
    public void onGetItemsFailure(String message) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    public interface HomeInterface {
        void onBackPressOnFragment();
    }

    private static HomeInterface listener;

    public void attachHomeInterface(HomeInterface _interface) {
        listener = _interface;
    }

    @Override
    public void onBackPressed() {
        listener.onBackPressOnFragment();
        super.onBackPressed();
    }
}
