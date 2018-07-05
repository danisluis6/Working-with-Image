package tutorial.lorence.template.view.activities.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import tutorial.lorence.template.R;
import tutorial.lorence.template.view.activities.BaseActivity;
import tutorial.lorence.template.view.activities.home.HomeActivity;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends BaseActivity {

    @Override
    public void distributedDaggerComponents() {
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initAttributes(Bundle savedInstanceState) {
        super.initAttributes(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moveToNextActivity();
            }
        }, 300);
    }

    private void moveToNextActivity() {
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        finish();
    }

}
