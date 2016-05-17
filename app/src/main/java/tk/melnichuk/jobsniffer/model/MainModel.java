package tk.melnichuk.jobsniffer.model;

import retrofit2.http.Body;
import retrofit2.http.Path;
import rx.Observable;
import tk.melnichuk.jobsniffer.presenter.MainPresenter;
import tk.melnichuk.jobsniffer.service.JoobleService;
import tk.melnichuk.jobsniffer.service.RetrofitServiceFactory;

/**
 * Created by al on 14.05.16.
 */
public class MainModel {

    private int mNumPages = 0;
    private static final int JOBS_PER_PAGE = 20;
    MainPresenter mPresenter;
    JoobleService mService;
    JoobleJobListRequest mRequest;

    public MainModel(MainPresenter presenter){
        mPresenter = presenter;
        mService = RetrofitServiceFactory.getNewRetrofitServiceInstance(JoobleService.class, JoobleService.BASE_URL);
        mRequest = new JoobleJobListRequest();
    }

    public Observable<JoobleJobListResponse> getJobList() {
        return mService.getJobList(JoobleService.API_KEY, mRequest);
    }

    public JoobleJobListRequest getRequest(){
        return mRequest;
    }

    public JoobleService getService(){
        return mService;
    }

    public int getNumPages(){
        return mNumPages;
    }

    public void updateNumPages(int totalCount){
        mNumPages = (int) Math.ceil( (float)totalCount/JOBS_PER_PAGE );
    }

    public void setNumPages(int numPages) {
        mNumPages = numPages;
    }

    public void setRequest(JoobleJobListRequest request) {
        mRequest = request;
    }

    public boolean isNewKeywordInput(String newKeywords) { return !mRequest.getKeywords().equals(newKeywords); }



}
