package bteelse.uqr.login.qrcode.show;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import bteelse.uqr.R;

/**
 * Created by Felipe on 11/16/2016.
 */

public class ResultSIP {

    Context context;
    TextView mSIP;

    public ResultSIP(Context context, TextView mSIP) {
        this.context = context;
        this.mSIP = mSIP;

        if (mSIP.getText().equals(context.getResources().getString(R.string.sip_time_over))) {
            Toast.makeText(context, R.string.sip_time_over, Toast.LENGTH_LONG).show();
        } else if (!mSIP.getText().equals(context.getResources().getString(R.string.sip_time_over))) {
            Toast.makeText(context, R.string.this_is_your_qr, Toast.LENGTH_SHORT).show();
        }
    }
}
