package tutorial.lorence.template.di.component;

import dagger.Subcomponent;
import tutorial.lorence.template.di.module.ScheduleModule;
import tutorial.lorence.template.di.module.HomeModule;
import tutorial.lorence.template.di.scope.ActivityScope;
import tutorial.lorence.template.view.activities.home.HomeActivity;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

@ActivityScope
@Subcomponent(

        modules = {
                HomeModule.class
        }
)
public interface HomeComponent {
    HomeActivity inject(HomeActivity homeActivity);
    FragmentComponent plus(ScheduleModule scheduleModule);
}


