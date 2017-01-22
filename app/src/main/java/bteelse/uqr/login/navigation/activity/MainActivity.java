package bteelse.uqr.login.navigation.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import bteelse.uqr.R;
import bteelse.uqr.login.androidpermissions.CameraPermission;
import bteelse.uqr.login.howtouse.HowToUseActivity;
import bteelse.uqr.login.navigation.classes.NavigationContent;
import bteelse.uqr.login.navigation.fragments.TabFragment;
import bteelse.uqr.login.navigation.preferences.SaveActivityState;
import bteelse.uqr.login.qrcode.generate.GenerateNewQR;
import bteelse.uqr.login.qrcode.saveqr.SaveQR;
import bteelse.uqr.login.qrcode.show.QRCodeSelection;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ZXingScannerView.ResultHandler {

    private TextView mId, mUsername, mSIP, mNameNavView, mEmailNavView, mPassword;
    private ImageView qrCodeImageview;
    private ZXingScannerView mScannerView;
    private final static int WIDTH = 350;
    private ProgressBar mProgressBar;
    private boolean disableEvent = true;

    private SaveActivityState preferences = new SaveActivityState();
    private QRCodeSelection qrcode = new QRCodeSelection();
    private NavigationContent navigation = new NavigationContent();
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mId = (TextView) findViewById(R.id.idUser);
        mUsername = (TextView) findViewById(R.id.txtWelcomeUser);
        mPassword = (TextView) findViewById(R.id.tvPassword);
        mSIP = (TextView) findViewById(R.id.textViewSIP);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();

        preferences.session(this);
        preferences.setLoggedin(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        mNameNavView = (TextView) header.findViewById(R.id.name_nav_view);
        mEmailNavView = (TextView) header.findViewById(R.id.email_nav_view);

        navigation.ads(MainActivity.this);
        navigation.checkConnection(MainActivity.this, mNameNavView, mProgressBar);
        new CameraPermission(MainActivity.this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btnClickToQrCode) {
         //   mProgressBar.setVisibility(View.VISIBLE);
            qrcode.QRCode7Selected(this, mId, mSIP);
        } else if (id == R.id.btnClickToQrCode2) {
            qrcode.QRCode30Selected(this, mId, mSIP);
        } else if (id == R.id.btnClickToQrCode3) {
            qrcode.QRCode90Selected(this, mId, mSIP);
        } else if (id == R.id.create_new_qr) {
            new GenerateNewQR(MainActivity.this, mUsername, mSIP);
        } else if (id == R.id.save_qr) {
            new SaveQR(MainActivity.this, mUsername, mSIP);
        }else if (id == R.id.howtouse) {
            Intent intent = new Intent(this, HowToUseActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            navigation.shareApp(MainActivity.this);
        } else if (id == R.id.nav_rate) {
            navigation.rateApp(MainActivity.this);
        } else if (id == R.id.btnLogout) {
            preferences.setLoggedin(false);
            navigation.logout(MainActivity.this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        generateImageQR();
        return true;
    }

    private void getImageID() {
        qrCodeImageview = (ImageView) findViewById(R.id.img_qr_code_image);
    }

    private void generateImageQR() {

        getImageID();
        // create thread to avoid ANR Exception
        Thread t = new Thread(new Runnable() {
            public void run() {

                try {
                    synchronized (this) {
                        wait(300);
                        // runOnUiThread method used to do UI task in main thread.
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap bitmap = null;

                                    bitmap = encodeAsBitmap(mSIP.getText().toString());
                                    qrCodeImageview.setImageBitmap(bitmap);
                                    mProgressBar.setVisibility(View.GONE);

                                } catch (WriterException e) {
                                    e.printStackTrace();
                                } // end of catch block
                            } // end of run method
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    // this is method call from on create and return bitmap image of QRCode.
    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? getResources().getColor(R.color.bg_register) : getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 350, 0, 0, w, h);
        return bitmap;
    }


    public void QrScanner(View view) {
        disableEvent = false;
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();
    }

    @Override
    public void onBackPressed() {
        if (disableEvent == true) {
            moveTaskToBack(true);
            navigation.checkConnection(MainActivity.this, mNameNavView, mProgressBar);
        }
        if (disableEvent == false) {
            finish();
            startActivity(getIntent());
            mScannerView.stopCamera();

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        preferences.savePreferences(MainActivity.this, mUsername, mId, mPassword);
        navigation.checkConnection(MainActivity.this, mNameNavView, mProgressBar);
    }

    @Override
    public void onResume() {
        super.onResume();
        preferences.loadPreferencesMainActivity(MainActivity.this, mUsername, mId, mPassword, mNameNavView, mEmailNavView);
        navigation.checkConnection(MainActivity.this, mNameNavView, mProgressBar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        preferences.savePreferences(MainActivity.this, mUsername, mId, mPassword);
        navigation.checkConnection(MainActivity.this, mNameNavView, mProgressBar);
    }

    @Override
    public void handleResult(Result result) {
        navigation.scannerResult(result, MainActivity.this);
        //mScannerView.resumeCameraPreview(this); ---> esse metodo serve pra deixar a camera rolando
        //talvez possa ser util no futuro, por isso comentei
        // mas se ele estiver rodando no código, os qr codes serao escaneados varias vezes
    }
}