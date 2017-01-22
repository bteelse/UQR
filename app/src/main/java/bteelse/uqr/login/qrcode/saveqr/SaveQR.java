package bteelse.uqr.login.qrcode.saveqr;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;
import android.widget.Toast;

import bteelse.uqr.R;

/**
 * Created by Felipe on 11/16/2016.
 */

public class SaveQR {

    private Context context;
    private TextView mUsername, mSIP;

    public SaveQR(final Context context, final TextView mUsername, final TextView mSIP) {
        this.context = context;
        this.mUsername = mUsername;
        this.mSIP = mSIP;

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setIcon(R.drawable.ic_qrcode128);
        alertDialog.setTitle(R.string.save_qr);
        alertDialog.setMessage(context.getResources().getString(R.string.save_qr_message));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, (context.getResources().getString(R.string.qr_7_days)),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String username = mUsername.getText().toString();
                        new SaveQR7Days(context, 1, mSIP).execute(username);
                        Toast.makeText(context, R.string.save_qr_successfully,
                                Toast.LENGTH_LONG).show();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, (context.getResources().getString(R.string.qr_30_days)),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String username = mUsername.getText().toString();
                        new SaveQR30Days(context, 1, mSIP).execute(username);
                        Toast.makeText(context, R.string.save_qr_successfully,
                                Toast.LENGTH_LONG).show();

                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, (context.getResources().getString(R.string.qr_90_days)),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String username = mUsername.getText().toString();
                        new SaveQR90Days(context, 1, mSIP).execute(username);
                        Toast.makeText(context, R.string.save_qr_successfully,
                                Toast.LENGTH_LONG).show();

                    }
                });
        alertDialog.show();
    }

}
