package tutorial.lorence.template.di.module;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import tutorial.lorence.template.data.storage.database.entities.Schedule;
import tutorial.lorence.template.di.scope.FragmentScope;
import tutorial.lorence.template.service.DisposableManager;
import tutorial.lorence.template.view.activities.home.HomeActivity;
import tutorial.lorence.template.view.activities.home.fragment.schedule.ScheduleModel;
import tutorial.lorence.template.view.activities.home.fragment.schedule.SchedulePresenter;
import tutorial.lorence.template.view.activities.home.fragment.schedule.SchedulePresenterImpl;
import tutorial.lorence.template.view.activities.home.fragment.schedule.ScheduleView;
import tutorial.lorence.template.view.activities.home.fragment.schedule.FragmentSchedule;
import tutorial.lorence.template.view.activities.home.fragment.adapter.ScheduleAdapter;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

@Module
public class ScheduleModule {

    private FragmentSchedule mFragment;
    private ScheduleView mView;

    public ScheduleModule(FragmentSchedule fragmentSchedule, ScheduleView view) {
        mFragment = fragmentSchedule;
        mView = view;
    }

    @Provides
    @FragmentScope
    ScheduleAdapter provideScheduleAdapter(Context context) {
        return new ScheduleAdapter(context, mFragment, new ArrayList<Schedule>());
    }

    @Provides
    @FragmentScope
    ScheduleToDayAdapter provideScheduleToDayAdapter(Context context) {
        return new ScheduleToDayAdapter(context, mFragment, new ArrayList<Schedule>());
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
}
