package tutorial.lorence.template.view.activities.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import tutorial.lorence.template.view.activities.home.HomeActivity;
import tutorial.lorence.template.view.activities.home.fragment.schedule.FragmentSchedule;
import tutorial.lorence.template.view.activities.home.loading.FragmentLoading;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class ViewPaperAdapter extends FragmentStatePagerAdapter {

    private FragmentSchedule mFragmentSchedule;

    public ViewPaperAdapter(HomeActivity homeActivity, FragmentManager fm, FragmentSchedule fragmentSchedule) {
        super(fm);
        mFragmentSchedule = fragmentSchedule;
        mFragmentSchedule.distributedDaggerComponents(homeActivity);
    }

    public Fragment getItem(int position) {
        switch(position){
            case 0:{
                return mFragmentSchedule;
            }
            case 1:{
                return new FragmentLoading();
            }
            case 2:{
                return new FragmentLoading();
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Football Schedule";
            case 1:
                return "Result of Matches";
            case 2:
                return "Teams";
            default:
                return null;
        }
    }
}