package com.example.gui;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class AllContacts extends Activity {

	// public TextView outputText;

	private ContactsAdapter contactsAdapter;
	private ListView listView;
	private ContactsOpenHelper contactsOpenHelper;
	private List<Contact> contacts;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.all);
		super.onCreate(savedInstanceState);
		setTitle("All contacts");

		contactsOpenHelper = new ContactsOpenHelper(this);
		contacts = contactsOpenHelper.getAllContacts();
		listView = (ListView) findViewById(R.id.contact_list);
		contactsAdapter = new ContactsAdapter(this,
				android.R.layout.simple_list_item_1, contacts);
		listView.setAdapter(contactsAdapter);

		// fetchContacts();
	}

	// public void onResume() {
	// Toast.makeText(getApplicationContext(), "resume", Toast.LENGTH_LONG)
	// .show();
	// super.onResume();
	// contacts.clear();
	// contacts.addAll(contactsAdapter.getAll());
	// contactsAdapter.notifyDataSetChanged();
	//
	// }

	// public void fetchContacts() {
	//
	// String phoneNumber = null;
	// String email = null;
	// Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
	// String _ID = ContactsContract.Contacts._ID;
	// String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
	// String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
	//
	// Uri PhoneCONTENT_URI =
	// ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
	// String Phone_CONTACT_ID =
	// ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
	// String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
	//
	// Uri EmailCONTENT_URI =
	// ContactsContract.CommonDataKinds.Email.CONTENT_URI;
	// String EmailCONTACT_ID =
	// ContactsContract.CommonDataKinds.Email.CONTACT_ID;
	// String DATA = ContactsContract.CommonDataKinds.Email.DATA;
	//
	// StringBuffer output = new StringBuffer();
	//
	// ContentResolver contentResolver = getContentResolver();
	//
	// Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null,
	// null);
	//
	// // Loop for every contact in the phone
	// if (cursor.getCount() > 0) {
	// while (cursor.moveToNext()) {
	//
	// String contact_id = cursor
	// .getString(cursor.getColumnIndex(_ID));
	// String name = cursor.getString(cursor
	// .getColumnIndex(DISPLAY_NAME));
	// Log.w("myApp", name);
	// int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor
	// .getColumnIndex(HAS_PHONE_NUMBER)));
	//
	// if (hasPhoneNumber > 0) {
	//
	// output.append("\n First Name:" + name);
	//
	// // Query and loop for every phone number of the contact
	// Cursor phoneCursor = contentResolver.query(
	// PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?",
	// new String[] { contact_id }, null);
	//
	// while (phoneCursor.moveToNext()) {
	// phoneNumber = phoneCursor.getString(phoneCursor
	// .getColumnIndex(NUMBER));
	// output.append("\n Phone number:" + phoneNumber);
	//
	// }
	//
	// phoneCursor.close();
	//
	// // Query and loop for every email of the contact
	// Cursor emailCursor = contentResolver.query(
	// EmailCONTENT_URI, null, EmailCONTACT_ID + " = ?",
	// new String[] { contact_id }, null);
	//
	// while (emailCursor.moveToNext()) {
	//
	// email = emailCursor.getString(emailCursor
	// .getColumnIndex(DATA));
	//
	// output.append("\nEmail:" + email);
	//
	// }
	//
	// emailCursor.close();
	// }
	//
	// output.append("\n");
	// }
	//
	// outputText.setText(output);
	// }
	// }

}
