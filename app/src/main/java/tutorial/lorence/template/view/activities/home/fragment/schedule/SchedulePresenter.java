package tutorial.lorence.template.view.activities.home.fragment.schedule;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import tutorial.lorence.template.data.storage.database.entities.Schedule;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public interface SchedulePresenter {
    void getItems();

    void setDisposable(Disposable disposable);

    void onGetItemsSuccess(ArrayList<Schedule> items);

    void onGetItemsFailure(String message);
}
