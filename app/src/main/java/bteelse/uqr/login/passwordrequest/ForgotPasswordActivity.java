package bteelse.uqr.login.passwordrequest;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import bteelse.uqr.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText mUsernameField, mEmailField;
    private String usuario, email;
    Context ctx = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mUsernameField = (EditText) findViewById(R.id.edtName_pass);
        mEmailField = (EditText) findViewById(R.id.edtEmail_pass);
        sendEmail();
    }

    private void sendEmail() {
        Button btnSendEmail = (Button) findViewById(R.id.btnPassRequest);

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = mUsernameField.getText().toString();
                email = mEmailField.getText().toString();
                BackGround b = new BackGround();
                b.execute(usuario, email);
                finish();
            }
        });

    }

    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String usuario = params[0];
            String email = params[1];
            String data = "";
            int tmp;

            try {
                URL url = new URL("http://www.bteelse.com/blog/uqr/rec_senha/recuperacao_senha.php");
                String urlParams = "usuario=" + usuario + "&email="
                        + email;

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
            if (s.equals("Esse usuario nao existe!")) {
                Toast.makeText(ctx, R.string.this_user_doesnt_exist, Toast.LENGTH_LONG).show();
            }
            if(s.equals("Email esta incorreto!")){
                Toast.makeText(ctx, R.string.this_email_doesnt_exist, Toast.LENGTH_LONG).show();
            }
            if(s.equals("Sua senha foi redefinida. Por favor, verifique seu Email.")){
                Toast.makeText(ctx, R.string.sent_email_to_change_password, Toast.LENGTH_LONG).show();
            }
            else if (s.equals("Exception: failed to connect to /52.67.170.233 (port 80): connect failed: ENETUNREACH (Network is unreachable)")) {
                Toast.makeText(ctx, R.string.internet_error,
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }

}