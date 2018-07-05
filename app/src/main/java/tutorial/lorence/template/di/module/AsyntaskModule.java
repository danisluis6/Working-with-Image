package tutorial.lorence.template.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tutorial.lorence.template.app.Application;
import tutorial.lorence.template.service.asyntask.DownloadImage;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

@Module
public class AsyntaskModule {

    private Application mApplication;
    private Context mContext;

    public AsyntaskModule(Application application, Context context) {
        mApplication = application;
        mContext = context;
    }

    @Singleton
    @Provides
    DownloadImage provideDownloadImage() {
        return new DownloadImage();
    }
}
