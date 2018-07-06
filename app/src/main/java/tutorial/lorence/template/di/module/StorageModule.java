package tutorial.lorence.template.di.module;

import android.content.Context;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import tutorial.lorence.template.data.storage.database.entities.Folder;
import tutorial.lorence.template.di.scope.ActivityScope;
import tutorial.lorence.template.other.storage.StorageActivity;
import tutorial.lorence.template.other.storage.adapter.StorageAdapter;
import tutorial.lorence.template.view.activities.home.HomeView;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

@Module
public class StorageModule {

    private StorageActivity mActivity;
    private HomeView mHomeView;

    public StorageModule(StorageActivity storageActivity) {
        this.mActivity = storageActivity;
    }

    @Provides
    @ActivityScope
    StorageActivity provideStorageActivity() {
        return mActivity;
    }

    @Provides
    @ActivityScope
    StorageAdapter provideStorageAdapter(Context context) {
        return new StorageAdapter(context, mActivity, new ArrayList<Folder>());
    }
}
