package tutorial.lorence.template.di.component;

import dagger.Subcomponent;
import tutorial.lorence.template.di.module.StorageModule;
import tutorial.lorence.template.di.scope.ActivityScope;
import tutorial.lorence.template.other.storage.StorageActivity;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

@ActivityScope
@Subcomponent(

        modules = {
                StorageModule.class
        }
)
public interface StorageComponent {
    StorageActivity inject(StorageActivity storageActivity);
}


