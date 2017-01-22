package bteelse.uqr.login.howtouse.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import bteelse.uqr.R;
import bteelse.uqr.login.howtouse.fragments.HowToUseStepFive;
import bteelse.uqr.login.howtouse.fragments.HowToUseStepFour;
import bteelse.uqr.login.howtouse.fragments.HowToUseStepOne;
import bteelse.uqr.login.howtouse.fragments.HowToUseStepSix;
import bteelse.uqr.login.howtouse.fragments.HowToUseStepThree;
import bteelse.uqr.login.howtouse.fragments.HowToUseStepTwo;

/**
 * Created by Felipe on 11/28/2016.
 */

public class MainActivityViewPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;

    public MainActivityViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment returnFragment;
        switch (position){
            case 0:
                returnFragment = HowToUseStepOne.newInstance();
                break;
            case 1:
                returnFragment = HowToUseStepTwo.newInstance();
                break;
            case 2:
                returnFragment = HowToUseStepThree.newInstance();
                break;
            case 3:
                returnFragment = HowToUseStepFour.newInstance();
                break;
            case 4:
                returnFragment = HowToUseStepFive.newInstance();
                break;
            case 5:
                returnFragment = HowToUseStepSix.newInstance();
                break;
            default:
                return null;
        }
        return returnFragment;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        CharSequence title;

        switch (position){
            case 0:
                title = context.getResources().getString(R.string.title_screen1);
                break;
            case 1:
                title = context.getResources().getString(R.string.title_screen2);
                break;
            case 2:
                title = context.getResources().getString(R.string.title_screen3);
                break;
            case 3:
                title = context.getResources().getString(R.string.title_screen4);
                break;
            case 4:
                title = context.getResources().getString(R.string.title_screen5);
                break;
            case 5:
                title = context.getResources().getString(R.string.title_screen6);
                break;
            default:
                title = "";
                break;
        }
        return title;
    }
}
