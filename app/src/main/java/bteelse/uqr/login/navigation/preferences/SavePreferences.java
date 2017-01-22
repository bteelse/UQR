package bteelse.uqr.login.navigation.preferences;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by Felipe on 11/18/2016.
 */

public interface SavePreferences {

    void savePreferences(Context context, TextView mUsername, TextView mId, TextView mPassword);
    void loadPreferencesMainActivity(Context context, TextView mUsername, TextView mId, TextView mPassword,
                         TextView mNameNavView, TextView mEmailNavView);
    void loadPreferencesLoginActivity(Context context, TextView mUsername, TextView mId, TextView mPassword);
    void session(Context context);
    void setLoggedin(boolean logggedin);
    boolean loggedin();
}
