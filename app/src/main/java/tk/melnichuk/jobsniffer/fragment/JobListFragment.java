package tk.melnichuk.jobsniffer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tk.melnichuk.jobsniffer.R;
import tk.melnichuk.jobsniffer.adapter.JobListAdapter;
import tk.melnichuk.jobsniffer.adapter.JobListPagerAdapter;
import tk.melnichuk.jobsniffer.model.Job;
import tk.melnichuk.jobsniffer.model.JoobleJobListRequest;
import tk.melnichuk.jobsniffer.model.JoobleJobListResponse;
import tk.melnichuk.jobsniffer.service.JoobleService;
import tk.melnichuk.jobsniffer.service.RetrofitServiceFactory;
import tk.melnichuk.jobsniffer.view.MainView;

/**
 * Created by al on 12.05.16.
 */
public class JobListFragment extends Fragment {

    int mPosition = 1;
    ListView mListView;
    ProgressBar mProgressBar;

    JobListAdapter mListAdapter;
    MainView mMainView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mPosition = bundle.getInt("position", 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.job_list, container, false);
        mListView = (ListView) v.findViewById(R.id.list_view);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progress_bar);

        mListAdapter = new JobListAdapter(getContext(), new ArrayList<Job>());
        mListView.setAdapter(mListAdapter);

        ViewPager vp = (ViewPager) getActivity().findViewById(R.id.view_pager);
        mMainView = ((JobListPagerAdapter) vp.getAdapter()).getMainView();
        mMainView.updateJobList(this, mPosition);

        return v;
    }

    public void update(List<Job> jobs) {

        if(mListView != null && mListAdapter != null) {
            ((JobListAdapter)mListView.getAdapter()).setData(jobs);
        }
    }

    public void showProgressBar() { mProgressBar.setVisibility(View.VISIBLE);}
    public void hideProgressBar() { mProgressBar.setVisibility(View.GONE);}
}
