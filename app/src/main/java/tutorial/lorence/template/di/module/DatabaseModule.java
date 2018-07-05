package tutorial.lorence.template.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tutorial.lorence.template.app.Application;
import tutorial.lorence.template.data.storage.database.DbAccess.DASchedule;
import tutorial.lorence.template.other.GenerateWebsite;
import tutorial.lorence.template.service.DisposableManager;
import tutorial.lorence.template.view.activities.home.HomeModel;
import tutorial.lorence.template.view.activities.home.HomeModelImpl;
import tutorial.lorence.template.view.activities.home.fragment.schedule.ScheduleModel;
import tutorial.lorence.template.view.activities.home.fragment.schedule.ScheduleModelImpl;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

@Module
public class DatabaseModule {

    private Application mApplication;
    private Context mContext;

    public DatabaseModule(Application application, Context context) {
        mApplication = application;
        mContext = context;
    }

    @Singleton
    @Provides
    DASchedule provideDASchedule() {
        return new DASchedule();
    }

    @Singleton
    @Provides
    HomeModel provideHomeModel(GenerateWebsite generateWebsite, DASchedule daSchedule) {
        return new HomeModelImpl(mContext, generateWebsite, daSchedule);
    }

    @Singleton
    @Provides
    ScheduleModel provideScheduleModel(GenerateWebsite generateWebsite, DASchedule daSchedule) {
        return new ScheduleModelImpl(mContext, generateWebsite, daSchedule);
    }

    @Singleton
    @Provides
    DisposableManager provideDisposableManager() {
        return new DisposableManager();
    }
}
