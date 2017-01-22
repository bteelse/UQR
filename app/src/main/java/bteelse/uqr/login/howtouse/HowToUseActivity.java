package bteelse.uqr.login.howtouse;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import bteelse.uqr.R;
import bteelse.uqr.login.howtouse.adapter.MainActivityViewPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HowToUseActivity extends AppCompatActivity {

    @BindView(R.id.activity_main_viewPager)
    ViewPager mainViewPager;

    @BindView(R.id.activity_main_tabLayout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_use);
        ButterKnife.bind(this);
        MainActivityViewPagerAdapter adapter = new MainActivityViewPagerAdapter(getSupportFragmentManager(),this);
        mainViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mainViewPager);
    }
}
