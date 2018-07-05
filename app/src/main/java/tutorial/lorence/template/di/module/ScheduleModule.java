package tutorial.lorence.template.di.module;

import android.content.Context;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import tutorial.lorence.template.R;
import tutorial.lorence.template.custom.SnackBarLayout;
import tutorial.lorence.template.data.storage.database.entities.Schedule;
import tutorial.lorence.template.di.scope.FragmentScope;
import tutorial.lorence.template.service.DisposableManager;
import tutorial.lorence.template.view.activities.home.HomeActivity;
import tutorial.lorence.template.view.activities.home.fragment.adapter.ScheduleAdapter;
import tutorial.lorence.template.view.activities.home.fragment.schedule.FragmentSchedule;
import tutorial.lorence.template.view.activities.home.fragment.schedule.ScheduleModel;
import tutorial.lorence.template.view.activities.home.fragment.schedule.SchedulePresenter;
import tutorial.lorence.template.view.activities.home.fragment.schedule.SchedulePresenterImpl;
import tutorial.lorence.template.view.activities.home.fragment.schedule.ScheduleView;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

@Module
public class ScheduleModule {

    private FragmentSchedule mFragment;
    private HomeActivity mActivity;
    private ScheduleView mView;

    public ScheduleModule(HomeActivity homeActivity, FragmentSchedule fragmentSchedule, ScheduleView view) {
        mFragment = fragmentSchedule;
        mActivity = homeActivity;
        mView = view;
    }

    @Provides
    @FragmentScope
    ScheduleAdapter provideScheduleAdapter(Context context) {
        return new ScheduleAdapter(context, mFragment, new ArrayList<Schedule>());
    }

    /**
     * Show up recyclerView adapter
     * @return FragmentTransaction
     */
    @Provides
    @FragmentScope
    FragmentManager provideFragmentTransaction() {
        return mFragment.getChildFragmentManager();
    }

    @Provides
    @FragmentScope
    SchedulePresenter provideSchedulePresenter(Context context, HomeActivity homeActivity, FragmentSchedule fragment, ScheduleModel scheduleModel, DisposableManager disposableManager) {
        return new SchedulePresenterImpl(context, homeActivity, fragment, mView, scheduleModel, disposableManager);
    }

    @Provides
    @FragmentScope
    SnackBarLayout provideSnackBarLayout(Context context) {
        return new SnackBarLayout(context);
    }

    @Provides
    @FragmentScope
    Snackbar provideSnackbar(Context context, SnackBarLayout snackBarLayout) {
        View parentLayout = mActivity.getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(parentLayout, "File Choosers", Snackbar.LENGTH_LONG);
        final Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) layout.getLayoutParams();
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER | Gravity.BOTTOM;
        layout.setLayoutParams(params);
        TextView textView = layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);
        if(snackBarLayout.getParent() != null)
            ((ViewGroup)snackBarLayout.getParent()).removeView(snackBarLayout);
        layout.addView(snackBarLayout);
        layout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        snackbar.setDuration(BaseTransientBottomBar.LENGTH_INDEFINITE);
        return snackbar;
    }
}
