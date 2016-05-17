
package tk.melnichuk.jobsniffer.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class JoobleJobListResponse {

    @SerializedName("totalCount")
    @Expose
    private Integer totalCount;
    @SerializedName("jobs")
    @Expose
    private List<Job> jobs = new ArrayList<Job>();

    /**
     *
     * @return
     * The totalCount
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     *
     * @param totalCount
     * The totalCount
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    /**
     *
     * @return
     * The jobs
     */
    public List<Job> getJobs() {
        return jobs;
    }

    /**
     *
     * @param jobs
     * The jobs
     */
    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

}