package tk.melnichuk.jobsniffer.view;

import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;

import tk.melnichuk.jobsniffer.R;
import tk.melnichuk.jobsniffer.adapter.JobListPagerAdapter;
import tk.melnichuk.jobsniffer.fragment.JobListFragment;
import tk.melnichuk.jobsniffer.presenter.MainPresenter;

/**
 * Created by al on 14.05.16.
 */
public class MainView {

    ViewPager mViewPager;
    EditText mSearchInput;
    TextView mNoJobsTextView;
    TextView mPagination;
    ImageView mSearchIcon;
    ImageView mMenuIcon;

    View mMainContainer;

    JobListPagerAdapter mViewPagerAdapter;
    MainPresenter mPresenter;
    AppCompatActivity mActivity;

    int mPage = 1;
    int mNumPages = 0;

    public MainView(MainPresenter presenter, AppCompatActivity activity){

        mPresenter = presenter;
        mActivity = activity;

        mMainContainer = mActivity.findViewById(R.id.main_container);
        mViewPager   = (ViewPager) mActivity.findViewById(R.id.view_pager);
        mSearchInput = (EditText)  mActivity.findViewById(R.id.text_search);
        mSearchIcon  = (ImageView) mActivity.findViewById(R.id.icon_search);
        mNoJobsTextView = (TextView) mActivity.findViewById(R.id.no_jobs_found);
        mPagination = (TextView) mActivity.findViewById(R.id.pagination);
        mMenuIcon    = (ImageView) mActivity.findViewById(R.id.icon_menu);

        mViewPagerAdapter = new JobListPagerAdapter(mActivity.getSupportFragmentManager(),this);
        mViewPager.setAdapter(mViewPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                mPage = position + 1;
                updatePagination();
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        mSearchIcon.setOnClickListener(v -> {
            String keywords = mSearchInput.getText().toString();
            mPresenter.onKeywordsChanged(keywords, mPage);
        });

        Toolbar toolbar = (Toolbar) mActivity.findViewById(R.id.toolbar);
        mActivity.setSupportActionBar(toolbar);

        mMenuIcon.setOnClickListener(v -> {
            showPopup();
        });

    }

    public void update(int numPages, int page, boolean resetPages) {
        mNumPages = numPages;
        int newPage = -1;
        if(numPages <= 0) {
            mNoJobsTextView.setVisibility(View.VISIBLE);
        } else {
            newPage = resetPages ? 0 : page - 1;
            mNoJobsTextView.setVisibility(View.GONE);
        }

        ((JobListPagerAdapter)mViewPager.getAdapter()).setNumPages(numPages);

        mViewPager.getAdapter().notifyDataSetChanged();
        if(newPage != -1) {
            mViewPager.setCurrentItem(newPage);
            mPage = mViewPager.getCurrentItem() + 1;
        }
        updatePagination();
    }

    public AppCompatActivity getActivity(){
        return mActivity;
    }

    public void updateJobList(JobListFragment frag, int page) {
        mPresenter.onJobListUpdate(frag, page);
    }

    public void showFilterAlertDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        View dialogView = mActivity.getLayoutInflater().inflate(R.layout.job_filter, null);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mActivity,
                android.R.layout.simple_dropdown_item_1line, mActivity.getResources().getStringArray(R.array.locations_list));
        AutoCompleteTextView locationView = (AutoCompleteTextView) dialogView.findViewById(R.id.input_location);
        locationView.setAdapter(adapter);



        adb.setView(dialogView);

        mPresenter.onFilterValuesRequested(dialogView);

        adb.setPositiveButton(R.string.filter_use, (dialog, which) -> {

            EditText salaryView = (EditText) dialogView.findViewById(R.id.input_salary);
            Spinner radiusView = (Spinner) dialogView.findViewById(R.id.spinner_radius);

            String location = locationView.getText().toString();
            String salary = salaryView.getText().toString();
            String radius = radiusView.getSelectedItem().toString();
            mPresenter.onServiceFilterAdded(
                    location.isEmpty() ? null : location,
                    salary.isEmpty()   ? null : salary,
                    radius.isEmpty()   ? null : radius
                    );
        })
        .setNegativeButton(R.string.filter_cancel, null)
        .show();
    }

    public void showError(Throwable e){
        String errorString;
        Resources res = mActivity.getResources();
        if (e instanceof HttpException) {
            // We had non-2XX http error
            errorString = res.getString(R.string.err_http);
        }
        else if (e instanceof IOException) {
            // A network or conversion error happened
            errorString = res.getString(R.string.err_connection);

        } else {
            errorString = res.getString(R.string.err_unknown);
        }
        Snackbar.make(mMainContainer, errorString, Snackbar.LENGTH_LONG).show();
    }




    public void setAlertDialogValues(View v, String location, String salary, String radius) {
        AutoCompleteTextView locationView = (AutoCompleteTextView) v.findViewById(R.id.input_location);
        EditText salaryView = (EditText) v.findViewById(R.id.input_salary);
        Spinner radiusView = (Spinner) v.findViewById(R.id.spinner_radius);

        if(location != null)
            locationView.setText(location);
        if(salary != null)
            salaryView.setText(salary);
        if(radius != null){
            int index = -1;
            for (int i=0;i<radiusView.getCount();i++){
                if (radiusView.getItemAtPosition(i).toString().equalsIgnoreCase(radius)){
                    index = i;
                    break;
                }
            }

            if(index != -1) {
                radiusView.setSelection(index, true);
            }
        }
    }

    public void showPopup(){

        PopupMenu popup = new PopupMenu(mActivity, mMenuIcon);
        popup.setOnMenuItemClickListener(item -> {

            switch (item.getItemId()) {
                case R.id.action_add_filter:
                    showFilterAlertDialog();
                    break;
                case R.id.action_remove_filter:
                    mPresenter.onServiceFilterRemoved();
                    break;
            }
            return false;
        });
        MenuInflater inflate = popup.getMenuInflater();
        inflate.inflate(R.menu.options_popup, popup.getMenu());
        popup.show();

    }

    public void setSearchInput(String searchInput) {
        if(mSearchInput != null && searchInput != null)
            mSearchInput.setText(searchInput);
    }

    public int getPage(){
        return mPage;
    }

    public String getPaginationString(){
        return mNumPages == 0 ? "---" : mPage + " / " + mNumPages;
    }

    public void updatePagination(){
        if(mPagination != null)
            mPagination.setText(getPaginationString());
    }

    
}
