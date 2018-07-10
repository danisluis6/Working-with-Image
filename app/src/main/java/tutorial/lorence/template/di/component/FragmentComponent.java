package tutorial.lorence.template.di.component;

import dagger.Subcomponent;
import tutorial.lorence.template.di.module.ScheduleModule;
import tutorial.lorence.template.di.scope.FragmentScope;
import tutorial.lorence.template.view.activities.home.fragment.content.FragmentContent;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

@FragmentScope
@Subcomponent(

        modules = {
                ScheduleModule.class
        }
)
public interface FragmentComponent {
    FragmentContent inject(FragmentContent fragmentContent);
}


