package tutorial.lorence.template.view.activities.home.fragment.content;

import tutorial.lorence.template.service.DisposableManager;
import tutorial.lorence.template.view.activities.home.HomeActivity;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public interface ContentModel {
    void attachPresenter(ContentPresenter presenter);
    void attachDisposable(DisposableManager disposableManager);
    void attachFragment(FragmentContent fragmentContent);
    void attachActivity(HomeActivity mHomeActivity);
    void getItems(String word);
}
