package tutorial.lorence.template.di.component;

import javax.inject.Singleton;

import dagger.Component;
import tutorial.lorence.template.di.module.AppModule;
import tutorial.lorence.template.di.module.AsyntaskModule;
import tutorial.lorence.template.di.module.DatabaseModule;
import tutorial.lorence.template.di.module.JsoupModule;
import tutorial.lorence.template.di.module.LoadingModule;
import tutorial.lorence.template.di.module.HomeModule;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

@Singleton
@Component(
        modules = {
                AppModule.class, LoadingModule.class, JsoupModule.class, DatabaseModule.class, AsyntaskModule.class
        }
)
public interface AppComponent {
        HomeComponent plus(HomeModule module);
}
