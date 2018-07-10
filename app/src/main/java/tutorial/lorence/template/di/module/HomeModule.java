package tutorial.lorence.template.di.module;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import dagger.Module;
import dagger.Provides;
import tutorial.lorence.template.di.scope.ActivityScope;
import tutorial.lorence.template.service.DisposableManager;
import tutorial.lorence.template.service.JsonData;
import tutorial.lorence.template.view.activities.home.HomeActivity;
import tutorial.lorence.template.view.activities.home.HomeModel;
import tutorial.lorence.template.view.activities.home.HomePresenter;
import tutorial.lorence.template.view.activities.home.HomePresenterImpl;
import tutorial.lorence.template.view.activities.home.HomeView;
import tutorial.lorence.template.view.activities.home.adapter.PaperAdapter;
import tutorial.lorence.template.view.activities.home.fragment.content.FragmentContent;
import tutorial.lorence.template.view.dialog.DialogLoading;

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

    @Provides
    @ActivityScope
    DialogLoading provideVGLoadingDialog(HomeActivity homeActivity) {
        return new DialogLoading(homeActivity);
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
    PaperAdapter provideViewPagerAdapter(Context context, FragmentManager fragmentManager, FragmentContent fragmentContent) {
        return new PaperAdapter(context, fragmentManager, fragmentContent);
    }

    @Provides
    @ActivityScope
    FragmentContent provideFragmentRecycler() {
        return new FragmentContent();
    }

    @Provides
    @ActivityScope
    HomePresenter provideHomePresenter(Context context, HomeActivity activity, HomeModel homeModel, DisposableManager disposableManager) {
        return new HomePresenterImpl(context, activity, mHomeView, homeModel, disposableManager);
    }
}
