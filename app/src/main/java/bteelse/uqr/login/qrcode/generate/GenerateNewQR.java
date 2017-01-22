package bteelse.uqr.login.qrcode.generate;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;
import android.widget.Toast;

import bteelse.uqr.R;

/**
 * Created by Felipe on 11/16/2016.
 */

public class GenerateNewQR {

    private Context context;
    private TextView mUsername, mSIP;

    public GenerateNewQR(final Context context, final TextView mUsername, final TextView mSIP) {
        this.context = context;
        this.mUsername = mUsername;
        this.mSIP = mSIP;

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setIcon(R.drawable.ic_qrcode128);
        alertDialog.setTitle(R.string.create_new_qr);
        alertDialog.setMessage(context.getResources().getString(R.string.are_you_sure));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, (context.getResources().getString(R.string.qr_7_days)),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String username = mUsername.getText().toString();
                        new CreateQRCode1(context, 1, mSIP).execute(username);
                        Toast.makeText(context, R.string.qr_generate_successfully,
                                Toast.LENGTH_LONG).show();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, (context.getResources().getString(R.string.qr_30_days)),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String username = mUsername.getText().toString();
                        new CreateQRCode2(context, 1, mSIP).execute(username);
                        Toast.makeText(context, R.string.qr_generate_successfully,
                                Toast.LENGTH_LONG).show();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, (context.getResources().getString(R.string.qr_90_days)),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String username = mUsername.getText().toString();
                        new CreateQRCode3(context, 1, mSIP).execute(username);
                        Toast.makeText(context, R.string.qr_generate_successfully,
                                Toast.LENGTH_LONG).show();

                    }
                });
        alertDialog.show();
    }
}
