package bteelse.uqr.login.howtouse.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bteelse.uqr.R;

/**
 * Created by Felipe on 11/28/2016.
 */

public class HowToUseStepSix extends Fragment {

    public static HowToUseStepSix newInstance(){
        return new HowToUseStepSix();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.how_to_use_step_six, container, false);
        return rootView;
    }
}
