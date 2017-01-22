package bteelse.uqr.login.register;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import bteelse.uqr.R;
import bteelse.uqr.login.login.LoginActivity;

/**
 * Created by Felipe on 11/20/2016.
 */

public class RegisterGoogle extends AsyncTask<String, String, String> {

    private Context ctx;
    private String mUsername, mEmail, usuario;

    public RegisterGoogle(Context context, String nameUser, String email, String usuario) {
        this.ctx = context;
        this.mUsername = nameUser;
        this.mEmail = email;
        this.usuario = usuario;
    }

    @Override
        protected String doInBackground(String... params) {
            String nome = params[0];
            String email = params[1];
            String usuario = params[2];
            String data = "";
            int tmp;

            try {
                URL url = new URL("http://www.bteelse.com/blog/uqr/cadastro/google_cadastro.php");
                String urlParams = "nome=" + nome + "&google_email=" + email + "&usuario=" + usuario;

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
            ctx.startActivity(it);
        }else if(s.equals("Exception: failed to connect to /52.67.170.233 (port 80): connect failed: ENETUNREACH (Network is unreachable)")){
            Toast.makeText(ctx, R.string.internet_error,
                    Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
        }
    }
}
