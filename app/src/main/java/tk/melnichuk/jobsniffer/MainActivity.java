package tk.melnichuk.jobsniffer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tk.melnichuk.jobsniffer.presenter.MainPresenter;


public class MainActivity extends AppCompatActivity {

    MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainPresenter = new MainPresenter(this);
        mMainPresenter.onCreate(savedInstanceState);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMainPresenter.onSaveInstanceState(outState);
    }
}
