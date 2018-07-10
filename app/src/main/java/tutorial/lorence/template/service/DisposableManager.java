package tutorial.lorence.template.service;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import tutorial.lorence.template.data.storage.database.entities.Schedule;
import tutorial.lorence.template.view.activities.home.HomeModelImpl;
import tutorial.lorence.template.view.activities.home.fragment.content.ContentModelImpl;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class DisposableManager {

    private IDisposableListener listener;
    private Disposable disposable;

    @Inject
    public DisposableManager() {
    }

    public void setDisposableInterface(HomeModelImpl disposableInterface) {
        this.listener = disposableInterface;
    }

    public void setInterfaceOnSchedule(ContentModelImpl disposableInterface) {
        this.listener = disposableInterface;
    }

    public Disposable callDisposable(Observable<ArrayList<Schedule>> observable) {
        disposable = observable.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<Schedule>>() {
                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }

                    @Override
                    public void onNext(ArrayList<Schedule> items) {
                        listener.onHandleData(items);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onApiFailure(e);
                    }
                });
        return disposable;
    }
}