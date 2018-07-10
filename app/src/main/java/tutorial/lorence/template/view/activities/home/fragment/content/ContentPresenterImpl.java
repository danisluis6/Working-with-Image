package tutorial.lorence.template.view.activities.home.fragment.content;

import android.content.Context;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import tutorial.lorence.template.data.storage.database.entities.Schedule;
import tutorial.lorence.template.service.DisposableManager;
import tutorial.lorence.template.view.activities.home.HomeActivity;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class ContentPresenterImpl implements ContentPresenter {

    private Context mContext;
    private FragmentContent mFragmentContent;
    private ContentView mContentView;
    private ContentModel mContentModel;
    private HomeActivity mHomeActivity;

    public ContentPresenterImpl(Context context, HomeActivity homeActivity, FragmentContent fragmentContent, ContentView contentView, ContentModel contentModel, DisposableManager disposableManager) {
        mContext = context;
        mHomeActivity = homeActivity;
        mContentView = contentView;
        mContentModel = contentModel;
        mFragmentContent = fragmentContent;
        mContentModel.attachActivity(mHomeActivity);
        mContentModel.attachFragment(mFragmentContent);
        mContentModel.attachPresenter(this);
        mContentModel.attachDisposable(disposableManager);
    }

    @Override
    public void getItems(String word) {
        mContentModel.getItems(word);
    }

    @Override
    public void setDisposable(Disposable disposable) {
        mContentView.setDisposable(disposable);
    }

    @Override
    public void onGetItemsSuccess(ArrayList<Schedule> items) {
        mContentView.onGetItemsSuccess(items);
    }

    @Override
    public void onGetItemsFailure(String message) {
        mContentView.onGetItemsFailure(message);
    }
}
