package bteelse.uqr.login.register;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import bteelse.uqr.R;
import bteelse.uqr.login.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity{

    boolean acccept = false;
    private TextView mTermAndCondition;
    private EditText mEdtNome, mEdtSenha, mEdtEmail, mEdtUser, mEdtConfirmarSenha, mEdtConfirmarEmail;
    private String nome, senha, email, usuario, confirmaSenha, confirmaEmail;
    private Context ctx = this;

    private static final String TAG = RegisterActivity.class.getSimpleName();
 //   private static final int RC_SIGN_IN = 007;

//    private GoogleApiClient mGoogleApiClient;

 //   private SignInButton btnSignIn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEdtNome = (EditText) findViewById(R.id.edtName);
        mEdtSenha = (EditText) findViewById(R.id.edtPassword);
        mEdtEmail = (EditText) findViewById(R.id.edtEmail);
        mEdtUser = (EditText) findViewById(R.id.edtUser);
        mEdtConfirmarSenha = (EditText) findViewById(R.id.edtConfirmPassword);
        mEdtConfirmarEmail = (EditText) findViewById(R.id.edtConfirmEmail);

        mTermAndCondition = (TextView) findViewById(R.id.termodeUsolink);
        mTermAndCondition.setMovementMethod(LinkMovementMethod.getInstance());

        clickCancel();
        clickRegister();
        clickTermsAndConditions();


       /* btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);

        btnSignIn.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customizing G+ button
        btnSignIn.setSize(SignInButton.SIZE_ICON_ONLY);
        btnSignIn.setScopes(gso.getScopeArray());*/

    }

    private void clickCancel() {
        Button btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(it);
                finish();

            }
        });
    }
    private void clickTermsAndConditions(){
        final CheckBox checkBox = (CheckBox) findViewById(R.id.termodeUso);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked() == true) {
                    acccept = true;
                } else {
                    acccept = false;
                }
            }
        });
    }


    private void clickRegister() {
        Button btnRegister = (Button) findViewById(R.id.btnCreateAccont);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mEdtEmail.getText().toString().equals(mEdtConfirmarEmail.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, R.string.email_address_error,
                            Toast.LENGTH_LONG).show();
                    mEdtEmail.setError(getText(R.string.email_address_error));
                    mEdtConfirmarEmail.setError(getText(R.string.email_address_error));

                } else if (!mEdtSenha.getText().toString().equals(mEdtConfirmarSenha.getText().toString())) {
                   Toast.makeText(RegisterActivity.this, R.string.password_error,
                            Toast.LENGTH_LONG).show();
                    mEdtSenha.setError(getText(R.string.password_error));
                    mEdtConfirmarSenha.setError(getText(R.string.password_error));

                } else if (mEdtEmail.getText().toString().length() < 5 || mEdtUser.getText().toString().length() < 5
                        || mEdtSenha.getText().toString().length() < 5) {

                    Toast.makeText(RegisterActivity.this, R.string.lenght_caracter_error,
                            Toast.LENGTH_LONG).show();

                }else if(mEdtNome.getText().toString().length() < 3){
                    Toast.makeText(RegisterActivity.this, R.string.lenght_caracter_error_name,
                            Toast.LENGTH_LONG).show();
                }else if(acccept == false){
                    Toast.makeText(RegisterActivity.this, R.string.you_need_to_accept_the_terms,
                            Toast.LENGTH_LONG).show();
                } else {
                    nome = mEdtNome.getText().toString();
                    senha = mEdtSenha.getText().toString();
                    email = mEdtEmail.getText().toString();
                    usuario = mEdtUser.getText().toString();
                    confirmaEmail = mEdtConfirmarEmail.getText().toString();
                    confirmaSenha = mEdtConfirmarSenha.getText().toString();
                    BackGround b = new BackGround();
                    b.execute(nome, email, usuario, senha, confirmaEmail, confirmaSenha);
                    finish();
                }
            }
        });

    }

    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String nome = params[0];
            String email = params[1];
            String usuario = params[2];
            String senha = params[3];
            String data = "";
            int tmp;

            try {
                URL url = new URL("http://www.bteelse.com/blog/uqr/cadastro/gerarsip.php");
                String urlParams = "nome=" + nome + "&email="
                        + email + "&usuario=" + usuario + "&senha=" + senha;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();
                InputStream is = httpURLConnection.getInputStream();
                while ((tmp = is.read()) != -1) {
                    data += (char) tmp;
                }
                is.close();
                httpURLConnection.disconnect();

                return data;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("")) {
                Toast.makeText(ctx, R.string.data_saved_successfully, Toast.LENGTH_LONG).show();
                Intent it = new Intent(ctx, LoginActivity.class);
                startActivity(it);
            }if (s.equals("Username or email already exist!")) {
                Toast.makeText(ctx, R.string.username_or_email_already, Toast.LENGTH_LONG).show();
            }else if(s.equals("Exception: failed to connect to /52.67.195.192 (port 80): connect failed: ENETUNREACH (Network is unreachable)")){
                Toast.makeText(ctx, R.string.internet_error,
                        Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
            }
        }
    }/*

    private void signIn() {
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

            new RegisterGoogle(RegisterActivity.this, personName, email, email).execute(personName, email, email);
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_sign_in:
                if (acccept == false) {
                    Toast.makeText(RegisterActivity.this, R.string.you_need_to_accept_the_terms,
                            Toast.LENGTH_LONG).show();
                } else {
                    signIn();
                }
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
        Log.d(TAG, "onConnectionFailed:" + connectionResult);*/

}