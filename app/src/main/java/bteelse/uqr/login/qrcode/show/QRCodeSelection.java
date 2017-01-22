package bteelse.uqr.login.qrcode.show;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by Felipe on 11/16/2016.
 */

public class QRCodeSelection implements QRCode {

    private String userID;
    private TextView mId, mSIP;
    private Context context;


    public void QRCode7Selected(Context context, TextView mId, TextView mSIP){
        this.context = context;
        this.mId = mId;
        this.mSIP = mSIP;

        userID = mId.getText().toString();
        new QRCode1(context, 1, mSIP).execute(userID);
        //new ResultSIP(context, mSIP);
    }

    public void QRCode30Selected(Context context, TextView mId, TextView mSIP) {
        this.context = context;
        this.mId = mId;
        this.mSIP = mSIP;

        userID = mId.getText().toString();
        new QRCode2(context, 1, mSIP).execute(userID);
       // new ResultSIP(context, mSIP);
    }

    public void QRCode90Selected(Context context, TextView mId, TextView mSIP) {
        this.context = context;
        this.mId = mId;
        this.mSIP = mSIP;

        userID = mId.getText().toString();
        new QRCode3(context, 1, mSIP).execute(userID);
       // new ResultSIP(context, mSIP);
    }

}
