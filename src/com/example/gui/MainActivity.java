package com.example.gui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	// SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ContactsOpenHelper contactsOpenHelper = new ContactsOpenHelper(this);
		test();
		Button unassignedButton = (Button) findViewById(R.id.bUnassigned);
		unassignedButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO:
				// Launch Activity Two
				// Hint: use Context's startActivity() method

				// Create an intent stating which Activity you would like to
				// start
				Intent intent = new Intent(MainActivity.this, Unassigned.class);

				// Launch the Activity using the intent
				startActivity(intent);
			}
		});

		Button allButton = (Button) findViewById(R.id.bAll);
		allButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO:
				// Launch Activity Two
				// Hint: use Context's startActivity() method

				// Create an intent stating which Activity you would like to
				// start
				Intent intent = new Intent(MainActivity.this, AllContacts.class);

				// Launch the Activity using the intent
				startActivity(intent);
			}
		});

		Button acqButton = (Button) findViewById(R.id.bAcq);
		acqButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO:
				// Launch Activity Two
				// Hint: use Context's startActivity() method

				// Create an intent stating which Activity you would like to
				// start
				Intent intent = new Intent(MainActivity.this, Category.class);
				intent.putExtra("Category", "Acquaintance");
				// Launch the Activity using the intent
				startActivity(intent);
			}
		});

		Button workButton = (Button) findViewById(R.id.bWork);
		workButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO:
				// Launch Activity Two
				// Hint: use Context's startActivity() method

				// Create an intent stating which Activity you would like to
				// start
				Intent intent = new Intent(MainActivity.this, Category.class);
				intent.putExtra("Category", "Coworker");
				// Launch the Activity using the intent
				startActivity(intent);
			}
		});

		Button friendButton = (Button) findViewById(R.id.bFriend);
		friendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO:
				// Launch Activity Two
				// Hint: use Context's startActivity() method

				// Create an intent stating which Activity you would like to
				// start
				Intent intent = new Intent(MainActivity.this, Category.class);
				intent.putExtra("Category", "Friend");
				// Launch the Activity using the intent
				startActivity(intent);
			}
		});

		Button familyButton = (Button) findViewById(R.id.bFamily);
		familyButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO:
				// Launch Activity Two
				// Hint: use Context's startActivity() method

				// Create an intent stating which Activity you would like to
				// start
				Intent intent = new Intent(MainActivity.this, Category.class);
				intent.putExtra("Category", "Family");
				// Launch the Activity using the intent
				startActivity(intent);
			}
		});

		Button uniButton = (Button) findViewById(R.id.bUni);
		uniButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO:
				// Launch Activity Two
				// Hint: use Context's startActivity() method

				// Create an intent stating which Activity you would like to
				// start
				Intent intent = new Intent(MainActivity.this, Category.class);
				intent.putExtra("Category", "Uni");
				// Launch the Activity using the intent
				startActivity(intent);
			}
		});

		Button refreshButton = (Button) findViewById(R.id.bRefresh);
		refreshButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ContactsOpenHelper contactsOpenHelper = new ContactsOpenHelper(
						MainActivity.this);
				List<Contact> userContacts = new ArrayList<Contact>();
				List<Contact> dbContacts = new ArrayList<Contact>();
				// 1. Read contacts from Android to the list of Contacts
				userContacts = fetchContacts();

				// 2. Read all contacts from the database to the second list
				dbContacts = contactsOpenHelper.getAllContacts();
				// 3 Compare two lists and update the database
				if (dbContacts.containsAll(userContacts)
						&& userContacts.containsAll(dbContacts)) {
					// no need to synchronize
				} else {
					synchonizeContacts(userContacts, dbContacts);
				}

				Toast.makeText(getApplicationContext(),
						"Done! Contacts are syncronized.", Toast.LENGTH_SHORT)
						.show();
			}
		});
		Button clearButton = (Button) findViewById(R.id.bClear);
		clearButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ContactsOpenHelper contactsOpenHelper = new ContactsOpenHelper(
						MainActivity.this);
				List<Contact> contacts = contactsOpenHelper.getAllContacts();

				for (Contact cn : contacts) {
					contactsOpenHelper.deleteContact(cn);
				}
				Toast.makeText(getApplicationContext(),
						"Done! Database is empty.", Toast.LENGTH_SHORT).show();
			}
		});

		Button generateButton = (Button) findViewById(R.id.bGenerateLogs);
		generateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// generateCalls();
				generateSMS();
			}
		});

	}

	public List<Contact> fetchContacts() {

		List<Contact> contacts = new ArrayList<Contact>();

		String phone = null;
		String email = null;
		String name = null;
		Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		String _ID = ContactsContract.Contacts._ID;
		String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
		String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

		Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

		Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
		String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
		String DATA = ContactsContract.CommonDataKinds.Email.DATA;

		ContentResolver contentResolver = getContentResolver();

		Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null,
				null);

		// Loop for every contact in the phone
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {

				String contact_id = cursor
						.getString(cursor.getColumnIndex(_ID));
				name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
				int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor
						.getColumnIndex(HAS_PHONE_NUMBER)));

				if (hasPhoneNumber > 0) {

					// Take the first phone only
					Cursor phoneCursor = contentResolver.query(
							PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?",
							new String[] { contact_id }, null);

					if (phoneCursor.moveToFirst()) {
						phone = phoneCursor.getString(phoneCursor
								.getColumnIndex(NUMBER));
					}

					phoneCursor.close();

					// Query and loop for every email of the contact
					Cursor emailCursor = contentResolver.query(
							EmailCONTENT_URI, null, EmailCONTACT_ID + " = ?",
							new String[] { contact_id }, null);

					if (emailCursor.moveToFirst()) {

						email = emailCursor.getString(emailCursor
								.getColumnIndex(DATA));
					}

					emailCursor.close();
				}
				contacts.add(new Contact(contact_id, name, phone, email,
						"Unassigned"));

			}

		}
		return contacts;
	}

	public void synchonizeContacts(List<Contact> userContacts,
			List<Contact> dbContacts) {
		ContactsOpenHelper contactsOpenHelper = new ContactsOpenHelper(
				MainActivity.this);
		// Check whether we need to add a new contact
		for (Contact currentContact : userContacts) {
			if (dbContacts.contains(currentContact)) {
				// do nothing
			} else {
				// TODO
				contactsOpenHelper.addContact(currentContact);
			}
		}
		// Check whether we need to delete a new contact from our db
		for (Contact currentContact : dbContacts) {
			if (userContacts.contains(currentContact)) {
				// do nothing
			} else {
				// TODO
				// delete this contact from our db
				contactsOpenHelper.deleteContact(currentContact);
			}
		}

	}

	public void test() {
		ContactsOpenHelper contactsOpenHelper = new ContactsOpenHelper(
				MainActivity.this);
		contactsOpenHelper.readAll();

	}

	public void generateCalls() {
		ContentValues values = new ContentValues();
		values.put(CallLog.Calls.NUMBER, "12345545667890");
		values.put(CallLog.Calls.DATE, System.currentTimeMillis());
		values.put(CallLog.Calls.DURATION, 500);
		values.put(CallLog.Calls.TYPE, CallLog.Calls.INCOMING_TYPE);
		values.put(CallLog.Calls.NEW, 1);
		values.put(CallLog.Calls.CACHED_NAME, "");
		values.put(CallLog.Calls.CACHED_NUMBER_TYPE, 0);
		values.put(CallLog.Calls.CACHED_NUMBER_LABEL, "");
		// values.put(Data.RAW_CONTACT_ID, rawContactId);
		// values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
		// values.put(Phone.NUMBER, "1-800-GOOG-411");
		// values.put(Phone.TYPE, Phone.TYPE_CUSTOM);
		// values.put(Phone.LABEL, "free directory assistance");
		// Uri dataUri = getContentResolver().insert(Data.CONTENT_URI,
		// values);
		getContentResolver().insert(CallLog.Calls.CONTENT_URI, values);
	}

	public void generateSMS() {
		ContentValues values = new ContentValues();
		values.put("address", "1234567890");
		values.put("body", "foo bar");
		getContentResolver().insert(Uri.parse("content://sms/sent"), values);
	}

}
