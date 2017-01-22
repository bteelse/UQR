package bteelse.uqr.login.navigation.classes;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.zxing.Result;

/**
 * Created by Felipe on 11/18/2016.
 */

public interface Navigation {

    void rateApp(Context context);
    void shareApp(Context context);
    void ads(Context context);
    void logout(Context context);
    void scannerResult(Result result, final Context context);
    boolean checkConnection(Context context, TextView mNameNavView, ProgressBar mProgressBar);
}
