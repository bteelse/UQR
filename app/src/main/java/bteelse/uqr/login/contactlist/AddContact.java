package bteelse.uqr.login.contactlist;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;

/**
 * Created by Felipe on 11/16/2016.
 */

public class AddContact {

    private Context context;

    public AddContact(Context context, String telefone) {
        this.context = context;
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        intent
                .putExtra(ContactsContract.Intents.Insert.PHONE, telefone)
                .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
        context.startActivity(intent);
    }

}
