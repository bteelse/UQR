package bteelse.uqr.login.qrcode.show;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by Felipe on 11/18/2016.
 */

public interface QRCode {

    void QRCode7Selected(Context context, TextView mId, TextView mSIP);
    void QRCode30Selected(Context context, TextView mId, TextView mSIP);
    void QRCode90Selected(Context context, TextView mId, TextView mSIP);

}
