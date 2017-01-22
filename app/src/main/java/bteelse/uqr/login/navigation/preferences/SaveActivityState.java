package bteelse.uqr.login.navigation.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import bteelse.uqr.R;

/**
 * Created by Felipe on 11/18/2016.
 */

public class SaveActivityState implements SavePreferences {

    private static final String PREFS_NAME = "preferences";
    private static final String PREF_UNAME = "Username";
    private static final String PREF_ID = "Id";
    private static final String PREF_PASSWORD = "Email";
    private final String DefaultUnameValue = "";
    private final String DefaultIDValue = "";
    private final String DefaultPasswordValue = "";
    private String username, id, password;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Context ctx;

    public void savePreferences(Context context, TextView mUsername, TextView mId, TextView mPassword) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        username = mUsername.getText().toString();
        id = mId.getText().toString();
        password = mPassword.getText().toString();
        System.out.println("onPause save name: " + username);
        System.out.println("onPause save id: " + id);
        System.out.println("onPause save senha: " + password);
        editor.putString(PREF_UNAME, username);
        editor.putString(PREF_PASSWORD, password);
        editor.putString(PREF_ID, id);
        editor.commit();
    }

    public void loadPreferencesMainActivity(Context context, TextView mUsername, TextView mId, TextView mPassword, TextView mNameNavView, TextView mEmailNavView) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                context.MODE_PRIVATE);

        username = settings.getString(PREF_UNAME, DefaultUnameValue);
        id = settings.getString(PREF_ID, DefaultIDValue);
        password = settings.getString(PREF_PASSWORD, DefaultPasswordValue);
        mUsername.setText(username);
        mPassword.setText(password);
        mId.setText(id);
        mNameNavView.setText(username);
        mEmailNavView.setText(R.string.welcome_user);
        System.out.println("onResume load name: " + username);
        System.out.println("onResume load senha: " + password);
        System.out.println("onResume load id: " + id);
    }

    @Override
    public void loadPreferencesLoginActivity(Context context, TextView mUsername, TextView mId, TextView mPassword) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                context.MODE_PRIVATE);

        username = settings.getString(PREF_UNAME, DefaultUnameValue);
        id = settings.getString(PREF_ID, DefaultIDValue);
        password = settings.getString(PREF_PASSWORD, DefaultPasswordValue);
        mUsername.setText(username);
        mPassword.setText(password);
        mId.setText(id);
        System.out.println("onResume load name: " + username);
        System.out.println("onResume load senha: " + password);
        System.out.println("onResume load id: " + id);
    }

    public void session(Context ctx){
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedin(boolean logggedin){
        //   QRCodeSelectionActivity qr = new QRCodeSelectionActivity();
        editor.putBoolean("loggedInmode",logggedin);
        editor.commit();
    }

    public boolean loggedin(){
        return prefs.getBoolean("loggedInmode", false);
    }

}
