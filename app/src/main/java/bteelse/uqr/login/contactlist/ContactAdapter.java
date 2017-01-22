package bteelse.uqr.login.contactlist;

/**
 * Created by Felipe on 10/30/2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import bteelse.uqr.R;

public class ContactAdapter extends CursorAdapter {

    int[] indices;

    public ContactAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        indices = new int[] {
                cursor.getColumnIndex(ContactsContract.Contacts._ID),
                cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY),
                cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
        };
        return LayoutInflater.from(context).inflate(R.layout.item_contato, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtNome = (TextView) view.findViewById(R.id.txtNome);


        Uri uriContato = ContactsContract.Contacts.getLookupUri(
                mCursor.getLong(indices[0]),
                mCursor.getString(indices[1]));

        txtNome.setText(cursor.getString(indices[2]));
        Picasso.with(mContext)
                .load(uriContato)
                .placeholder(android.R.drawable.sym_def_app_icon);
    }
}