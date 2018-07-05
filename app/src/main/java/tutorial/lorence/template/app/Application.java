package tutorial.lorence.template.app;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import tutorial.lorence.template.di.component.AppComponent;
import tutorial.lorence.template.di.component.DaggerAppComponent;
import tutorial.lorence.template.di.module.AppModule;
import tutorial.lorence.template.di.module.AsyntaskModule;
import tutorial.lorence.template.di.module.DatabaseModule;
import tutorial.lorence.template.di.module.JsoupModule;
import tutorial.lorence.template.di.module.LoadingModule;

/**
 * Created by vuongluis on 4/14/2018.
 * @author vuongluis
 * @version 0.0.1
 */

public class Application extends android.app.Application {

    private AppComponent mApplicationComponent;
    private Context mContext;
    private static Application sInstance;

    public static synchronized Application getInstance() {
        if (sInstance == null) {
            sInstance = new Application();
        }
        return sInstance;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        sInstance = this;
        initAppComponent();
    }

    private void initAppComponent() {
        mApplicationComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this,mContext))
                .loadingModule(new LoadingModule(this,mContext))
                .databaseModule(new DatabaseModule(this, mContext))
                .asyntaskModule(new AsyntaskModule(this, mContext))
                .jsoupModule(new JsoupModule(this, mContext))
                .build();
    }

    public AppComponent getAppComponent() {
        return mApplicationComponent;
    }

}
