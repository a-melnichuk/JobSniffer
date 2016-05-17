package tk.melnichuk.jobsniffer.service;



import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;
import tk.melnichuk.jobsniffer.model.JoobleJobListRequest;
import tk.melnichuk.jobsniffer.model.JoobleJobListResponse;

/**
 * Created by al on 10.05.16.
 */
public interface JoobleService {

    String BASE_URL =  "http://ua.jooble.org/api/";
    String API_KEY  = "43ca2686-01bb-4a02-9cf0-cf5af24ea5cc";

    @Headers( "Content-Type: application/x-www-form-urlencoded" )
    @POST("{apiKey}")
    Observable<JoobleJobListResponse> getJobList(@Path("apiKey") String apiKey, @Body JoobleJobListRequest req);

}
