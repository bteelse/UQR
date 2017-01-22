package bteelse.uqr.login.navigation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bteelse.uqr.R;

/**
 * Created by Felipe on 10/28/2016.
 */
public class ContactListFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_fragment, container, false);
        return view;

    }
}