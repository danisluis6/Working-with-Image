package tutorial.lorence.template.di.module;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import tutorial.lorence.template.data.storage.database.entities.recycler.Item;
import tutorial.lorence.template.di.scope.ActivityScope;
import tutorial.lorence.template.service.DisposableManager;
import tutorial.lorence.template.view.activities.home.HomeModel;
import tutorial.lorence.template.view.activities.home.HomePresenter;
import tutorial.lorence.template.view.activities.home.HomePresenterImpl;
import tutorial.lorence.template.view.activities.home.HomeView;
import tutorial.lorence.template.view.activities.home.adapter.UserAdapter;
import tutorial.lorence.template.view.activities.home.adapter.ViewPaperAdapter;
import tutorial.lorence.template.view.activities.home.adapter.ViewType;
import tutorial.lorence.template.view.activities.home.fragment.schedule.FragmentSchedule;
import tutorial.lorence.template.service.JsonData;
import tutorial.lorence.template.view.activities.home.HomeActivity;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

@Module
public class HomeModule {

    private HomeActivity mHomeActivity;
    private HomeView mHomeView;

    public HomeModule(HomeActivity homeActivity, HomeView homeView) {
        this.mHomeActivity = homeActivity;
        this.mHomeView = homeView;
    }

    public HomeModule(HomeActivity homeActivity) {
        this.mHomeActivity = homeActivity;
    }

    @Provides
    @ActivityScope
    HomeActivity provideHomeActivity() {
        return mHomeActivity;
    }

    /**
     * Show up recyclerView adapter
     * @return FragmentTransaction
     */
    @Provides
    @ActivityScope
    JsonData provideJsonData(Context context) {
        return new JsonData(context);
    }

    /**
     * Show up recyclerView adapter
     * @return FragmentTransaction
     */
    @Provides
    @ActivityScope
    FragmentTransaction provideFragmentTransaction() {
        return mHomeActivity.getSupportFragmentManager().beginTransaction();
    }

    @Provides
    @ActivityScope
    FragmentManager provideFragmentManager()  {
        return mHomeActivity.getSupportFragmentManager();
    }

    @Provides
    @ActivityScope
    ViewPaperAdapter provideViewPagerAdapter(Context context, FragmentManager fragmentManager, FragmentSchedule fragmentSchedule) {
        return new ViewPaperAdapter(context, fragmentManager, fragmentSchedule);
    }

    @Provides
    @ActivityScope
    ViewType provideViewType() {
        return new ViewType();
    }

    @Provides
    @ActivityScope
    UserAdapter provideUserAdapter(Context context, ViewType viewType) {
        return new UserAdapter(context, viewType, new ArrayList<Item>());
    }

    @Provides
    @ActivityScope
    FragmentSchedule provideFragmentRecycler() {
        return new FragmentSchedule();
    }

    @Provides
    @ActivityScope
    HomePresenter provideHomePresenter(Context context, HomeActivity activity, HomeModel homeModel, DisposableManager disposableManager) {
        return new HomePresenterImpl(context, activity, mHomeView, homeModel, disposableManager);
    }
}
