package bteelse.uqr.login.qrcode.generate;

import android.content.Context;
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

/**
 * Created by Felipe on 10/26/2016.
 */
public class CreateQRCode3 extends AsyncTask<String, Void, String> {
    private Context context;
    private int byGetOrPost = 0;
    private TextView mSIP;
    private String userID;

    //flag 0 means get and 1 means post.(By default it is get.)
    public CreateQRCode3(Context context, int flag, TextView sip) {
        this.context = context;
        byGetOrPost = flag;
        this.mSIP = sip;
    }


    @Override
    protected String doInBackground(String... arg0) {
        if (byGetOrPost == 0) { //means by Get Method

            try {
                userID = (String) arg0[0];
                String password = (String) arg0[1];
                String link = "usuario=" + userID;

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
                userID = (String) arg0[0];

                String link = "http://www.bteelse.com/blog/uqr/criar_novo_qr/novoqr_90dias.php";

                String data = URLEncoder.encode("usuario", "UTF-8") + "=" + URLEncoder.encode(userID, "UTF-8");

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
            mSIP.setText(R.string.sip_time_over);
        } else if (result.equals("Exception: failed to connect to /52.67.170.233 (port 80): connect failed: ENETUNREACH (Network is unreachable)")) {
            Toast.makeText(context, R.string.internet_error,
                    Toast.LENGTH_LONG).show();
        } else if (result.equals("Erro conexão banco")) {
            mSIP.setText(R.string.qr_code_problem);
        } else {
            try {
                mSIP.setText(result);
            } catch (Exception e) {
            }
        }
    }
}