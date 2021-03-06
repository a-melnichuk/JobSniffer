package tk.melnichuk.jobsniffer.presenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import tk.melnichuk.jobsniffer.fragment.JobListFragment;

import tk.melnichuk.jobsniffer.model.JoobleJobListRequest;

import tk.melnichuk.jobsniffer.model.MainModel;
import tk.melnichuk.jobsniffer.view.MainView;

/**
 * Created by al on 14.05.16.
 */
public class MainPresenter {

    MainModel mModel;
    MainView mView;

    public MainPresenter(AppCompatActivity activity) {

        mModel = new MainModel(this);
        mView = new MainView(this, activity);
    }


    public void onKeywordsChanged(String keywords, int page){
        JoobleJobListRequest request = mModel.getRequest();
        boolean isNewKeywordInput = mModel.isNewKeywordInput(keywords);
        if(isNewKeywordInput) {
            request.setKeywords(keywords);
            request.setPage(1);
        }

        mModel.getJobList().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                r-> {
                    mModel.updateNumPages(r.getTotalCount());
                    mView.update(mModel.getNumPages(), page, isNewKeywordInput);
                }, e-> {
                    mView.showError(e);
                }, ()-> {}
            );
    }

    public void onFilterValuesRequested(View v) {
        JoobleJobListRequest request = mModel.getRequest();
        mView.setAlertDialogValues(v, request.getLocation(), request.getSalary(), request.getRadius());
    }

    public void onServiceFilterAdded(String location, String salary, String radius){
        JoobleJobListRequest request = mModel.getRequest();

        request.setLocation(location);
        request.setSalary(salary);
        request.setRadius(radius);

        onKeywordsChanged(request.getKeywords(), 1);
    }

    public void onServiceFilterRemoved(){
       onServiceFilterAdded(null, null, null);
    }

    public void onJobListUpdate(JobListFragment fragment, int page) {
        JoobleJobListRequest request = mModel.getRequest();

        request.setPage(page);

        fragment.showProgressBar();

        mModel.getJobList()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                r -> {
                    mModel.updateNumPages(r.getTotalCount());

                    fragment.update(r.getJobs());
                }, e -> {
                    mView.showError(e);
                    fragment.hideProgressBar();
                }, () -> {
                    fragment.hideProgressBar();
                }
            );

    }

    public void onCreate(Bundle savedInstanceState){
        JoobleJobListRequest request = mModel.getRequest();
        if(savedInstanceState == null)  {
            request.setKeywords("програміст");
            request.setPage(1);
            mView.setSearchInput(request.getKeywords());
        } else {
            request.setKeywords( savedInstanceState.getString("keywords", "програміст") );
            request.setLocation( savedInstanceState.getString("location") );
            request.setSalary( savedInstanceState.getString("salary") );
            request.setRadius( savedInstanceState.getString("radius") );
            request.setPage(savedInstanceState.getInt("page", 1) );
        }
        onKeywordsChanged(request.getKeywords(), request.getPageInt() );
    }

    public void onSaveInstanceState(Bundle outState){
        JoobleJobListRequest request = mModel.getRequest();
        outState.putString("keywords", request.getKeywords());
        outState.putString("location", request.getLocation());
        outState.putString("salary", request.getSalary());
        outState.putString("radius", request.getRadius());
        outState.putInt("page", mView.getPage() );

    }

}
