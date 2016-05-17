package tk.melnichuk.jobsniffer.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class JoobleJobListRequest {

    @SerializedName("keywords")
    @Expose
    private String keywords;


    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("radius")
    @Expose
    private String radius;


    @SerializedName("salary")
    @Expose
    private String salary;

    @SerializedName("page")
    @Expose
    private String page;


    public void setPage(String page) {
        try {
            int pageInt = Integer.valueOf(page);
            if(pageInt < 1) throw new NumberFormatException();
            this.page = page;
        } catch (NumberFormatException e) {

        }
    }

    public void setPage(int page) {
        if(page > 0) {
            this.page = String.valueOf(page);
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSalary(double salary) {
        if(salary >= 0.0) {
            this.salary = String.valueOf(salary);
        }
    }

    public void setSalary(String salary) {
        try {
            double salaryDbl = Double.valueOf(salary);
            if(salaryDbl >= 0.0) {
                this.salary = salary;
            }
        } catch (Exception e){
            this.salary = null;
        }
    }

    public void setRadius(String radius) {
        try {

            double radiusDbl = Double.parseDouble(radius);
            if(radiusDbl >= 0.0) {
                this.radius = radius;
            }
        } catch (Exception e) {
            this.radius = null;
        }
    }

    public String getPage(){ return page;}
    public int getPageInt() { return Integer.valueOf(page);}
    public String getSalary(){return salary;}
    public String getRadius(){return radius;}
    public String getLocation(){return location;}

    /**
     * No args constructor for use in serialization
     *
     */
    public JoobleJobListRequest() {
    }

    /**
     *
     * @param keywords
     */
    public JoobleJobListRequest(String keywords) {
        this.keywords = keywords;
    }

    public JoobleJobListRequest(String keywords,
                                String location,
                                String radius,
                                String salary,
                                String page) {
        this.keywords = keywords;
        this.location = location;
        this.radius = radius;
        this.salary = salary;
        this.page = page;
    }

    public JoobleJobListRequest(String keywords,
                                String location,
                                int radius,
                                String salary,
                                int page) {
        this.keywords = keywords;
        this.location = location;
        this.salary = salary;


        try {
            this.radius = String.valueOf(radius);
        } catch (NumberFormatException e) {}

        try {
            this.page = String.valueOf(page);
        } catch (NumberFormatException e) {}

    }

    public JoobleJobListRequest(String keywords,
                                int page) {
        this.keywords = keywords;

        try {
            this.page = String.valueOf(page);
        } catch (NumberFormatException e) {}

    }

    /**
     *
     * @return
     * The keywords
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     *
     * @param keywords
     * The keywords
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

}