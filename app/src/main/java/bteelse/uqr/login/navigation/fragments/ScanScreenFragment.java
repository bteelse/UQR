package bteelse.uqr.login.navigation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bteelse.uqr.R;

public class ScanScreenFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scan_screen_fragment, container, false);
        TextView msip = (TextView) view.findViewById(R.id.tvSIP);
        msip.setText(R.string.sip_response);
        return view;

    }
}

