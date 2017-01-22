package bteelse.uqr.login.androidpermissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import bteelse.uqr.R;

/**
 * Created by Felipe on 11/16/2016.
 */

public class ContactsPermission extends Activity {
    private Context context;

    public ContactsPermission(Context context){
        this.context = context;
        if (weHavePermissionToReadContacts()) {
            readTheContacts();
        } else {
            requestReadContactsPermissionFirst();
        }
    }

    private boolean weHavePermissionToReadContacts() {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

    private void readTheContacts() {
        ContactsContract.Contacts.getLookupUri(context.getContentResolver(), ContactsContract.Contacts.CONTENT_URI);
    }

    private void requestReadContactsPermissionFirst() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_CONTACTS)) {
            Toast.makeText(context, R.string.need_permission_to_text, Toast.LENGTH_LONG).show();
            requestForResultContactsPermission();
        } else {
            requestForResultContactsPermission();
        }
    }

    private void requestForResultContactsPermission() {
        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_CONTACTS}, 123);
    }

}
