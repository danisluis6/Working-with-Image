package tutorial.lorence.template.di.module;

import dagger.Module;
import dagger.Provides;
import tutorial.lorence.template.di.scope.ActivityScope;
import tutorial.lorence.template.view.activities.pdf.IView;
import tutorial.lorence.template.view.activities.pdf.ViewActivity;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

@Module
public class ViewModule {

    private ViewActivity mViewActivity;
    private IView mView;

    public ViewModule(ViewActivity viewActivity, IView view) {
        this.mViewActivity = viewActivity;
        this.mView = view;
    }

    public ViewModule(ViewActivity viewActivity) {
        this.mViewActivity = viewActivity;
    }

    @Provides
    @ActivityScope
    ViewActivity provideViewActivity() {
        return mViewActivity;
    }
}
