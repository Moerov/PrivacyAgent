package com.example.gui;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ContactsOpenHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 8;

	public static final String DATABASE_NAME = "MyContacts.db";
	public static final String CONTACTS_TABLE_NAME = "contacts";
	public static final String CONTACTS_COLUMN_ID = "id";
	public static final String CONTACTS_COLUMN_CONTACT_ID = "contact_id";
	public static final String CONTACTS_COLUMN_NAME = "name";
	public static final String CONTACTS_COLUMN_PHONE = "phone";
	public static final String CONTACTS_COLUMN_EMAIL = "email";
	public static final String CONTACTS_COLUMN_CATEGORY = "category";

	public ContactsOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// db.execSQL("create table contacts "
		// +
		// "(id integer primary key, name text,phone text,email text,category text)");
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + CONTACTS_TABLE_NAME
				+ "(" + CONTACTS_COLUMN_ID + " INTEGER PRIMARY KEY,"
				+ CONTACTS_COLUMN_CONTACT_ID + " TEXT," + CONTACTS_COLUMN_NAME
				+ " TEXT," + CONTACTS_COLUMN_PHONE + " TEXT,"
				+ CONTACTS_COLUMN_EMAIL + " TEXT," + CONTACTS_COLUMN_CATEGORY
				+ " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
		// if (newVersion > oldVersion) {
		// db.execSQL("ALTER TABLE contacts ADD COLUMN CONTACTS_COLUMN_CONTACT_ID TEXT");
		// }
		// Create tables again
		onCreate(db);
	}

	public void addContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(CONTACTS_COLUMN_CONTACT_ID, contact.getId());
		values.put(CONTACTS_COLUMN_NAME, contact.getName()); // Contact Name
		values.put(CONTACTS_COLUMN_PHONE, contact.getPhone()); // Contact Phone
																// Number
		values.put(CONTACTS_COLUMN_EMAIL, contact.getEmail());
		values.put(CONTACTS_COLUMN_CATEGORY, contact.getCategory());

		// Inserting Row
		db.insert(CONTACTS_TABLE_NAME, null, values);
		db.close(); // Closing database connection
	}

	public Contact getContact(String id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(CONTACTS_TABLE_NAME, new String[] {
				CONTACTS_COLUMN_NAME, CONTACTS_COLUMN_PHONE,
				CONTACTS_COLUMN_EMAIL, CONTACTS_COLUMN_CATEGORY },
				CONTACTS_COLUMN_ID + "=?", new String[] { String.valueOf(id) },
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Contact contact = new Contact(id, cursor.getString(0),
				cursor.getString(1), cursor.getString(2), cursor.getString(3));

		return contact;
	}

	public List<Contact> getAllContacts() {
		List<Contact> contactList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery = "SELECT " + CONTACTS_COLUMN_CONTACT_ID + ","
				+ CONTACTS_COLUMN_NAME + "," + CONTACTS_COLUMN_PHONE + ","
				+ CONTACTS_COLUMN_EMAIL + "," + CONTACTS_COLUMN_CATEGORY
				+ " FROM " + CONTACTS_TABLE_NAME;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact(cursor.getString(0),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3), cursor.getString(4));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}

	public List<Contact> getContactsCategory(String category) {
		List<Contact> contactList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery = "SELECT " + CONTACTS_COLUMN_CONTACT_ID + ","
				+ CONTACTS_COLUMN_NAME + "," + CONTACTS_COLUMN_PHONE + ","
				+ CONTACTS_COLUMN_EMAIL + "," + CONTACTS_COLUMN_CATEGORY
				+ " FROM " + CONTACTS_TABLE_NAME + " WHERE category=" + "'"
				+ category + "';";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact(cursor.getString(0),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3), cursor.getString(4));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}

	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + CONTACTS_TABLE_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public int updateContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(CONTACTS_COLUMN_NAME, contact.getName());
		values.put(CONTACTS_COLUMN_PHONE, contact.getPhone());
		values.put(CONTACTS_COLUMN_EMAIL, contact.getEmail());
		values.put(CONTACTS_COLUMN_CATEGORY, contact.getCategory());

		// updating row
		return db.update(CONTACTS_TABLE_NAME, values, CONTACTS_COLUMN_ID
				+ " = ?", new String[] { String.valueOf(contact.getId()) });
	}

	public void updateCategory(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		String selectQuery = "UPDATE " + CONTACTS_TABLE_NAME
				+ " SET category='" + contact.getCategory() + "'"
				+ " WHERE name='" + contact.getName() + "' AND " + "phone='"
				+ contact.getPhone() + "' AND " + "email='"
				+ contact.getEmail() + "';";
		// String selectQuery = "UPDATE " + CONTACTS_TABLE_NAME
		// + " SET category='" + contact.getCategory() + "';";
		db.execSQL(selectQuery);

	}

	public void deleteContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(CONTACTS_TABLE_NAME, CONTACTS_COLUMN_ID + " = ?",
				new String[] { String.valueOf(contact.getId()) });
		db.close();
	}

	public void readAll() {
		List<Contact> contactList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + CONTACTS_TABLE_NAME;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Log.d("Read all_0: ", cursor.getString(0));
				Log.d("Read all_1: ", cursor.getString(1));
				Log.d("Read all_2: ", cursor.getString(2));
				Log.d("Read all_3: ", cursor.getString(3));
				Log.d("Read all_4: ", cursor.getString(4));

				Contact contact = new Contact(cursor.getString(0),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3), cursor.getString(4));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}

		// return contact list

	}

}
