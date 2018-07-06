package tutorial.lorence.template.other.storage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import tutorial.lorence.template.R;
import tutorial.lorence.template.data.storage.database.entities.Folder;
import tutorial.lorence.template.view.activities.BaseActivity;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class StorageActivity extends BaseActivity {

    private ArrayList<Folder> arrFolder;

    @Override
    public void distributedDaggerComponents() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_storage;
    }

    @Override
    protected void initAttributes(Bundle savedInstanceState) {
        super.initAttributes(savedInstanceState);
        Intent intent = getIntent();
        arrFolder = intent.getParcelableArrayListExtra("arrFolder");
        Log.i("TAG", arrFolder.size()+" -> SIZE");
    }
}
