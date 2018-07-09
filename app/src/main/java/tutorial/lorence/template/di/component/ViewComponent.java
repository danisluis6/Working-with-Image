package tutorial.lorence.template.di.component;

import dagger.Subcomponent;
import tutorial.lorence.template.di.module.ViewModule;
import tutorial.lorence.template.di.scope.ActivityScope;
import tutorial.lorence.template.view.activities.pdf.ViewActivity;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

@ActivityScope
@Subcomponent(

        modules = {
                ViewModule.class
        }
)
public interface ViewComponent {
    ViewActivity inject(ViewActivity viewActivity);
}


