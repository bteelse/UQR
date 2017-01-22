package bteelse.uqr.login.navigation.classes;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.zxing.Result;

import bteelse.uqr.R;
import bteelse.uqr.login.contactlist.AddContact;
import bteelse.uqr.login.login.LoginActivity;

/**
 * Created by Felipe on 11/18/2016.
 */

public class NavigationContent implements Navigation {

    private static final String PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=bteelse.uqr";
    private InterstitialAd interstitial;

    public void rateApp(final Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setIcon(android.R.drawable.btn_star_big_on);
        alertDialog.setTitle(R.string.rate_us);
        alertDialog.setMessage(context.getResources().getString(R.string.rate_us_message));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, (context.getResources().getString(R.string.rate_uqr)),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        playStore(context);
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, (context.getResources().getString(R.string.not_now)),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void shareApp(Context context) {
        context.getResources().getString(R.string.check_out_app);
        final SpannableString linkPlayStore = new SpannableString(PLAY_STORE_URL);
        Linkify.addLinks(linkPlayStore, Linkify.ALL);
        Intent it = new Intent(Intent.ACTION_SEND);
        it.putExtra(Intent.EXTRA_TEXT, context.getResources().getString(R.string.check_out_app) + " " + linkPlayStore);
        it.setType("text/plain");
        context.startActivity(it);
    }

    public void ads(Context context) {
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitial = new InterstitialAd(context);
        interstitial.setAdUnitId("ca-app-pub-3481841045180328/9531523297");
        interstitial.loadAd(adRequest);
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                displayInterstitial();
            }
        });
    }

    public void logout(Context context) {
        Intent log = new Intent(context, LoginActivity.class);
        context.startActivity(log);
        System.exit(0);
    }


    public void scannerResult(Result result, final Context context) {
        final SpannableString s = new SpannableString(result.toString());
        Linkify.addLinks(s, Linkify.ALL);

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setIcon(android.R.drawable.ic_menu_call);
        alertDialog.setTitle(R.string.call_now);
        alertDialog.setMessage(s);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, (context.getResources().getString(R.string.action_inserir_contato)),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new AddContact(context, s.toString());
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, (context.getResources().getString(R.string.call_now)),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String url = s.toString();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        context.startActivity(i);
                    }
                });
        alertDialog.show();
    }

    public boolean checkConnection(Context context, TextView mNameNavView, ProgressBar mProgressBar) {
        boolean connected;
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            connected = true;
            mNameNavView.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.presence_online, 0);
        } else {
            connected = false;
            mNameNavView.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.presence_invisible, 0);
            mProgressBar.setVisibility(View.VISIBLE);
        }
        return connected;
    }

    private void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    private void playStore(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "error", Toast.LENGTH_LONG).show();
        }
    }
}
