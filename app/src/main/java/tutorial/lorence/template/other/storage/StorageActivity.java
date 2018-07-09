package tutorial.lorence.template.other.storage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import tutorial.lorence.template.R;
import tutorial.lorence.template.app.Application;
import tutorial.lorence.template.data.storage.database.entities.Folder;
import tutorial.lorence.template.di.module.StorageModule;
import tutorial.lorence.template.other.storage.adapter.StorageAdapter;
import tutorial.lorence.template.view.activities.BaseActivity;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class StorageActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, StorageAdapter.StorageInterface {

    @BindView(R.id.rcvFolder)
    RecyclerView rcvFolder;

    @BindView(R.id.swipeRefreshList)
    SwipeRefreshLayout swipeRefreshList;

    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @Inject
    StorageAdapter mStorageAdapter;

    private ArrayList<Folder> arrFolder;

    HashMap<String, ArrayList<Folder>> hierarchyFolder = new HashMap<>();
    private int mIndex = 0;

    @Override
    public void distributedDaggerComponents() {
        Application.getInstance()
                .getAppComponent()
                .plus(new StorageModule(this))
                .inject(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_storage;
    }

    @Override
    protected void initAttributes(Bundle savedInstanceState) {
        super.initAttributes(savedInstanceState);
        initComponents();
        Intent intent = getIntent();
        arrFolder = intent.getParcelableArrayListExtra("arrFolder");
        hierarchyFolder.put(String.valueOf(mIndex), arrFolder);
        mStorageAdapter.updateFolder(arrFolder);
        mStorageAdapter.attachInterface(this);
    }

    private void initComponents() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rcvFolder.setLayoutManager(mLayoutManager);
        rcvFolder.setItemAnimator(new DefaultItemAnimator());
        rcvFolder.setAdapter(mStorageAdapter);
        swipeRefreshList.setOnRefreshListener(this);
        swipeRefreshList.setColorSchemeResources(R.color.colorPrimaryDark);
        toolBar.setSubtitleTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
        toolBar.setTitle(getString(R.string.title_storage));
        toolBar.setNavigationIcon(R.drawable.ic_arrow);
        setSupportActionBar(toolBar);
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void subChildFolder(Folder folder) {
        File root = new File(folder.getPath());
        ArrayList<Folder> temps = new ArrayList<>();
        if (root.isDirectory()) {
            File[] files = root.listFiles();
            temps.clear();
            for (File f : files) {
                temps.add(new Folder(R.drawable.ic_folder, f.getName(), (f.isDirectory()? "Directory" : "File"), f.getPath()));
            }
            mIndex++;
            hierarchyFolder.put(String.valueOf(mIndex), temps);
            mStorageAdapter.updateFolder(temps);
        } else {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("Path", folder.getPath());
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (mIndex == 0) {
            super.onBackPressed();
        } else {
            mIndex--;
            arrFolder = hierarchyFolder.get(String.valueOf(mIndex));
            mStorageAdapter.updateFolder(arrFolder);
        }
    }
}
