package tk.melnichuk.jobsniffer.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tk.melnichuk.jobsniffer.R;
import tk.melnichuk.jobsniffer.model.Job;
import tk.melnichuk.jobsniffer.view.MainView;

/**
 * Created by al on 12.05.16.
 */

public class JobListAdapter extends BaseAdapter {

    Context mContext;
    List<Job> mData;

    private static LayoutInflater mInflater = null;

    public JobListAdapter(Context context, List<Job> data) {
        mContext = context;
        mData = data;

        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<Job> data){
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View vi = convertView == null ? mInflater.inflate(R.layout.job_list_item, null) : convertView;

        TextView titleView = (TextView) vi.findViewById(R.id.title);
        TextView linkView = (TextView) vi.findViewById(R.id.text_link);
        TextView descriptionView = (TextView) vi.findViewById(R.id.text_description);

        Job job = mData.get(position);
        String title = job.getTitle();
        String description = job.getSnippet();

        handleInput(job.getLocation(), vi, R.id.text_location, R.id.icon_location);
        handleInput(job.getCompany(), vi, R.id.text_company, R.id.icon_company);
        handleInput(job.getType(), vi, R.id.text_employment_type, R.id.icon_employment_type);
        handleInput(job.getSalary(), vi, R.id.text_salary, R.id.icon_salary);

        titleView.setText(title);
        linkView.setText(Html.fromHtml("<a href=\"" + job.getLink() + "\">" + job.getSource() + "</a>"));
        linkView.setMovementMethod(LinkMovementMethod.getInstance());
        descriptionView.setText(Html.fromHtml(description));

        return vi;
    }

    private void setHeight(View v, int height){
       ViewGroup.LayoutParams params = v.getLayoutParams();
        params.height = height;
        v.setLayoutParams(params);

    }

    private void handleInput(String text, View v, int textViewId, int imageViewId) {
        if(text == null || text.isEmpty()) {
            setHeight(v.findViewById(imageViewId),0);
            setHeight(v.findViewById(textViewId),0);
        } else {
            ((TextView) v.findViewById(textViewId)).setText(text);
        }

    }
}