package bteelse.uqr.login.login;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import bteelse.uqr.R;
import bteelse.uqr.login.androidpermissions.ContactsPermission;
import bteelse.uqr.login.navigation.activity.MainActivity;
import bteelse.uqr.login.navigation.preferences.SaveActivityState;
import bteelse.uqr.login.passwordrequest.ForgotPasswordActivity;
import bteelse.uqr.login.register.RegisterActivity;


public class LoginActivity extends AppCompatActivity{

    private SaveActivityState preferences = new SaveActivityState();
    private EditText mUsernameField, mPasswordField;
    private TextView mID, mUsername, mForgotPassword, mEmail, mPassword;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;

  //  private GoogleApiClient mGoogleApiClient;

//    private SignInButton btnSignIn;

    private ProgressBar mProgressBar;
    private CountDownTimer countDownTimer;
    int i = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);

        preferences.session(this);

        mUsernameField = (EditText) findViewById(R.id.edtLogin);
        mPasswordField = (EditText) findViewById(R.id.edtPassword);
        mID = (TextView)findViewById(R.id.textViewRole);
        mUsername = (TextView)findViewById(R.id.textViewName);
        mEmail = (TextView)findViewById(R.id.textViewRole);
        mPassword = (TextView)findViewById(R.id.textViewLastname);

        youNeedToInstallZoiperDialog();
        showPassword();
        registerClick();
        PasswordRequestClick();
        isLogged();


       // btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);

        //btnSignIn.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

       /* mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customizing G+ button
        btnSignIn.setSize(SignInButton.SIZE_ICON_ONLY);
        btnSignIn.setScopes(gso.getScopeArray());*/

    }

    private void timeProgressBar() {
        countDownTimer = new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                mProgressBar.setVisibility(View.VISIBLE);
                i--;
            }
            public void onFinish() {
                mProgressBar.setVisibility(View.GONE);
            }
        }.start();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new ContactsPermission(LoginActivity.this);
    }

    private void isLogged(){
        if(preferences.loggedin()){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            preferences.loadPreferencesLoginActivity(LoginActivity.this, mUsername, mID, mPassword);
            startActivity(intent);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        preferences.savePreferences(LoginActivity.this, mUsername, mID, mPassword);
    }

    @Override
    public void onResume() {
        super.onResume();
        preferences.loadPreferencesLoginActivity(LoginActivity.this, mUsername, mID, mPassword);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        preferences.savePreferences(LoginActivity.this, mUsername, mID, mPassword);
    }

    public void loginPost(View view) {
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            timeProgressBar();
            String username = mUsernameField.getText().toString();
            String password = mPasswordField.getText().toString();
            new LoginRequest(this, 1, mID, mUsername, mPassword).execute(username, password);
        } else {
            Toast.makeText(this, R.string.internet_error,
                    Toast.LENGTH_LONG).show();
            timeProgressBar();
        }
    }

    private void showPassword() {
        final CheckBox checkBox = (CheckBox) findViewById(R.id.chkShowPassaword);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked() == true) {
                    mPasswordField.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    mPasswordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    private void registerClick() {
        TextView registerLink = (TextView) findViewById(R.id.tvRegister);
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
    }
    private void PasswordRequestClick() {
        mForgotPassword = (TextView)findViewById(R.id.tvForgotPassword);
        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

 /*   private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.

            GoogleSignInAccount acct = result.getSignInAccount();
            Log.e(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            String email = acct.getEmail();

            Log.e(TAG, "Name: " + personName + ", email: " + email);

            mUsername.setText(email);
            mPassword.setText(email);

            new LoginGooglePlus(LoginActivity.this, 1, mID, mUsername, mPassword).execute(email, email);

        } else {
            // Signed out, show unauthenticated UI.
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_sign_in:
                signIn();
                timeProgressBar();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }*/

    private void youNeedToInstallZoiperDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setIcon(R.drawable.htuicon);
        alertDialog.setTitle(R.string.please_one_moment);
        alertDialog.setMessage(getResources().getString(R.string.please_install_zoiper));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, (getResources().getString(R.string.download_zoiper)),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sendToZoiper();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, (getResources().getString(R.string.no_thanks_i_already_have_zoiper)),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void sendToZoiper(){
        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.zoiper.android.app&hl=en");
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        }
    }

}
