package bteelse.uqr.login.login;

/**
 * Created by Felipe on 9/10/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import bteelse.uqr.R;
import bteelse.uqr.login.navigation.activity.MainActivity;


public class LoginRequest extends AsyncTask<String, Void, String> {
    private Context context;
    private int byGetOrPost = 0;
    private TextView mID, mUsername, mPassword;
    private String username, password;

    //flag 0 means get and 1 means post.(By default it is get.)
    public LoginRequest(Context context, int flag, TextView roleField, TextView nameUser, TextView password) {
        this.context = context;
        byGetOrPost = flag;
        this.mID = roleField;
        this.mUsername = nameUser;
        this.mPassword = password;

    }

    @Override
    protected String doInBackground(String... arg0) {
        if (byGetOrPost == 0) { //means by Get Method

            try {
                username = (String) arg0[0];
                password = (String) arg0[1];
                String link = "usuario=" + username + "& password=" + password;

                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                return sb.toString();
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        } else {
            try {
                username = (String) arg0[0];
                password = (String) arg0[1];

                String link = "http://www.bteelse.com/blog/uqr/login/loginmysql.php";
                String data = URLEncoder.encode("usuario", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("senha", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                return sb.toString();
            } catch (Exception e) {
                String erro = "erro";
                return new String("Exception: " + e.getMessage());
            }
        }
    }


    @Override
    protected void onPostExecute(String result) {
        if (result.isEmpty()) {
            this.mID.setText(result);
            Toast.makeText(context, R.string.account_error,
                    Toast.LENGTH_LONG).show();
        } else if (result.equals("Exception: failed to connect to /52.67.170.233 (port 80): connect failed: ENETUNREACH (Network is unreachable)")) {
            Toast.makeText(context, R.string.internet_error,
                    Toast.LENGTH_LONG).show();
        } else {
            try {
                this.mID.setText(result);
                this.mUsername.setText(username);
                this.mPassword.setText(password);
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("nome", mUsername.getText().toString());
                intent.putExtra("senha", mPassword.getText().toString());
                intent.putExtra("valor", mID.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            } catch (Exception e) {
                this.mID.setText(result);
            }
        }
    }
}
