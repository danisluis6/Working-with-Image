package tutorial.lorence.template.service;

import java.util.ArrayList;

import tutorial.lorence.template.data.storage.database.entities.Schedule;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public interface IDisposableListener<T> {
    void onComplete();

    void onHandleData(ArrayList<Schedule> items);

    void onApiFailure(Throwable error);
}
