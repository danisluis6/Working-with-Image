package tutorial.lorence.template.view.activities.home.fragment.schedule;

import tutorial.lorence.template.other.Constants;
import tutorial.lorence.template.service.DisposableManager;
import tutorial.lorence.template.view.activities.home.HomeActivity;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public interface ScheduleModel {
    void getItems(Constants.MVP mvp);
    void attachPresenter(SchedulePresenter presenter);
    void attachDisposable(DisposableManager disposableManager);
    void attachFragment(FragmentSchedule fragmentSchedule);
    void attachActivity(HomeActivity mHomeActivity);
}
