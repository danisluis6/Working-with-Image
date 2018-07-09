package tutorial.lorence.template.view.activities.home;

import android.content.Context;

import java.util.ArrayList;

import tutorial.lorence.template.R;
import tutorial.lorence.template.data.storage.database.DbAccess.DASchedule;
import tutorial.lorence.template.data.storage.database.entities.Schedule;
import tutorial.lorence.template.other.Constants;
import tutorial.lorence.template.other.GenerateWebsite;
import tutorial.lorence.template.service.DisposableManager;
import tutorial.lorence.template.service.IDisposableListener;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class HomeModelImpl implements HomeModel, IDisposableListener<Schedule> {

    private Context mContext;
    private HomePresenter mHomePresenter;
    private DisposableManager mDisposableManager;
    private GenerateWebsite mGenerateWebsite;
    private HomeActivity mHomeActivity;
    private DASchedule mDaoSchedule;

    public HomeModelImpl(Context context, GenerateWebsite generateWebsite, DASchedule daoSchedule) {
        mContext = context;
        mGenerateWebsite = generateWebsite;
        mDaoSchedule = daoSchedule;
    }

    @Override
    public void attachPresenter(HomePresenterImpl homePresenter) {
        mHomePresenter = homePresenter;
    }

    @Override
    public void attachActivity(HomeActivity homeActivity) {
        mHomeActivity = homeActivity;
    }

    @Override
    public void attachDisposable(DisposableManager disposableManager) {
        mDisposableManager = disposableManager;
        mDisposableManager.setDisposableInterface(this);
    }

    @Override
    public void getItems(Constants.MVP mvp) {
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onHandleData(ArrayList<Schedule> items) {
        mHomePresenter.onGetItemsSuccess(mDaoSchedule.getAll(mContext));
    }

    @Override
    public void onApiFailure(Throwable error) {
        mHomePresenter.onGetItemsFailure(mContext.getString(R.string.error_time_out));
    }
}
