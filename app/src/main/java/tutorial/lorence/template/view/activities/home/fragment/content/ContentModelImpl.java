package tutorial.lorence.template.view.activities.home.fragment.content;

import android.content.Context;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import tutorial.lorence.template.R;
import tutorial.lorence.template.data.storage.database.DbAccess.DASchedule;
import tutorial.lorence.template.data.storage.database.entities.Schedule;
import tutorial.lorence.template.other.Constants;
import tutorial.lorence.template.other.GenerateWebsite;
import tutorial.lorence.template.other.Utils;
import tutorial.lorence.template.service.DisposableManager;
import tutorial.lorence.template.service.IDisposableListener;
import tutorial.lorence.template.view.activities.home.HomeActivity;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class ContentModelImpl implements ContentModel, IDisposableListener<Schedule> {

    private Context mContext;
    private HomeActivity mHomeActivity;
    private ContentPresenter mContentPresenter;
    private DisposableManager mDisposableManager;
    private GenerateWebsite mGenerateWebsite;
    private FragmentContent mFragmentContent;
    private DASchedule mDaoSchedule;

    public ContentModelImpl(Context context, GenerateWebsite generateWebsite, DASchedule daoSchedule) {
        mContext = context;
        mGenerateWebsite = generateWebsite;
        mDaoSchedule = daoSchedule;
    }

    @Override
    public void attachFragment(FragmentContent fragmentContent) {
        mFragmentContent = fragmentContent;
    }

    @Override
    public void attachActivity(HomeActivity homeActivity) {
        mHomeActivity = homeActivity;
    }

    @Override
    public void attachPresenter(ContentPresenter presenter) {
        mContentPresenter = presenter;
    }

    @Override
    public void attachDisposable(DisposableManager disposableManager) {
        mDisposableManager = disposableManager;
        mDisposableManager.setInterfaceOnSchedule(this);
    }

    @Override
    public void getItems(Constants.MVP mvp) {
        if (!Utils.isInternetOn(mContext)) {
            mContentPresenter.setDisposable(mDisposableManager.callDisposable(Observable.just(mDaoSchedule.getAll(mContext))));
        } else {
            if (mvp == Constants.MVP._JSOUP) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final List<String> content = new ArrayList<>();
                        final List<String> flag = new ArrayList<>();
                        try {
                            Document doc = Jsoup.connect(mGenerateWebsite.jsoup_URL()).get();
                            Elements trs = doc.getElementsByClass("ltd-tr2");
                            for (Element tr : trs) {
                                content.add(tr.text());
                            }

                            Elements arrImage = doc.select("img");
                            for (Element img : arrImage) {
                                flag.add(img.absUrl("src"));
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mHomeActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mDaoSchedule.getAll(mContext).size() == 0) {
                                    mDaoSchedule.addAll(Utils.convertStringToObject(content, flag), mContext);
                                }
                                if (Utils.isInternetOn(mContext)) {
                                    mContentPresenter.setDisposable(mDisposableManager.callDisposable(Observable.just(mDaoSchedule.getAll(mContext))));
                                } else {
                                    mContentPresenter.onGetItemsFailure(mContext.getString(R.string.no_internet_connection));
                                }
                            }
                        });
                    }

                }).start();
            }
        }
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onHandleData(ArrayList<Schedule> items) {
        mContentPresenter.onGetItemsSuccess(mDaoSchedule.getAll(mContext));
    }

    @Override
    public void onApiFailure(Throwable error) {
        mContentPresenter.onGetItemsFailure(mContext.getString(R.string.error_time_out));
    }
}
