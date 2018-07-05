package tutorial.lorence.template.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tutorial.lorence.template.app.Application;
import tutorial.lorence.template.other.GenerateWebsite;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

@Module
public class JsoupModule {

    private Application mApplication;
    private Context mContext;

    public JsoupModule(Application application, Context context) {
        this.mApplication = application;
        this.mContext = context;
    }

    @Provides
    @Singleton
    GenerateWebsite provideGenerateWebsite() {
        return new GenerateWebsite();
    }

}
