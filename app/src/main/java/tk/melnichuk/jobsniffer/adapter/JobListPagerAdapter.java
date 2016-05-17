package tk.melnichuk.jobsniffer.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import tk.melnichuk.jobsniffer.fragment.JobListFragment;
import tk.melnichuk.jobsniffer.view.MainView;

/**
 * Created by al on 17.05.16.
 */
public class JobListPagerAdapter extends FragmentStatePagerAdapter {
    int mNumPages = 0;
    MainView mMainView;
    public JobListPagerAdapter(FragmentManager fm, MainView mainView) {
        super(fm);
        mMainView = mainView;
    }

    public void setNumPages(int numPages) {
        mNumPages = numPages;
    }


    @Override
    public Fragment getItem(int position) {
        JobListFragment jobListFragment = new JobListFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("position",position + 1);

        jobListFragment.setArguments(bundle);
        return jobListFragment;
    }

    @Override
    public int getCount() {
        return mNumPages;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public MainView getMainView() {
        return mMainView;
    }
}