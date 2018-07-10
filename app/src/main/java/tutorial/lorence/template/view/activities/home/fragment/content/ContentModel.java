package tutorial.lorence.template.view.activities.home.fragment.content;

import tutorial.lorence.template.other.Constants;
import tutorial.lorence.template.service.DisposableManager;
import tutorial.lorence.template.view.activities.home.HomeActivity;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public interface ContentModel {
    void getItems(Constants.MVP mvp);
    void attachPresenter(ContentPresenter presenter);
    void attachDisposable(DisposableManager disposableManager);
    void attachFragment(FragmentContent fragmentContent);
    void attachActivity(HomeActivity mHomeActivity);
}
