package bteelse.uqr.login.howtouse.fragments;

/**
 * Created by Felipe on 11/28/2016.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bteelse.uqr.R;


public class HowToUseStepFour extends Fragment {

    public static HowToUseStepFour newInstance(){
        return new HowToUseStepFour();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.how_to_use_step_four, container, false);
        return rootView;
    }
}