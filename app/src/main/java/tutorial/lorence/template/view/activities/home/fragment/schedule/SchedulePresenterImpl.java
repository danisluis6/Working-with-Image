package tutorial.lorence.template.view.activities.home.fragment.schedule;

import android.content.Context;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import tutorial.lorence.template.data.storage.database.entities.Schedule;
import tutorial.lorence.template.other.Constants;
import tutorial.lorence.template.service.DisposableManager;
import tutorial.lorence.template.view.activities.home.HomeActivity;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class SchedulePresenterImpl implements SchedulePresenter {

    private Context mContext;
    private FragmentSchedule mFragmentSchedule;
    private ScheduleView mScheduleView;
    private ScheduleModel mScheduleModel;
    private HomeActivity mHomeActivity;

    public SchedulePresenterImpl(Context context, HomeActivity homeActivity, FragmentSchedule fragmentSchedule, ScheduleView scheduleView, ScheduleModel scheduleModel, DisposableManager disposableManager) {
        mContext = context;
        mHomeActivity = homeActivity;
        mScheduleView = scheduleView;
        mScheduleModel = scheduleModel;
        mFragmentSchedule = fragmentSchedule;
        mScheduleModel.attachActivity(mHomeActivity);
        mScheduleModel.attachFragment(mFragmentSchedule);
        mScheduleModel.attachPresenter(this);
        mScheduleModel.attachDisposable(disposableManager);
    }

    @Override
    public void getItems() {
        mScheduleModel.getItems(Constants.MVP._JSOUP);
    }

    @Override
    public void setDisposable(Disposable disposable) {
        mScheduleView.setDisposable(disposable);
    }

    @Override
    public void onGetItemsSuccess(ArrayList<Schedule> items) {
        mScheduleView.onGetItemsSuccess(items);
    }

    @Override
    public void onGetItemsFailure(String message) {
        mScheduleView.onGetItemsFailure(message);
    }
}
