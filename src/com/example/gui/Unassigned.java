package com.example.gui;

import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class Unassigned extends Activity {

	private ContactsAdapterUnassigned contactsAdapter;
	private ContactsOpenHelper contactsOpenHelper;
	private List<Contact> contacts;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.unassigned);
		super.onCreate(savedInstanceState);
		setTitle("Unassigned contacts");
		contactsOpenHelper = new ContactsOpenHelper(this);
		contacts = contactsOpenHelper.getContactsCategory("Unassigned");
		createSuggestions();
		Log.d("Insert: ", Integer.toString(contacts.size()));
		final ListView listView = (ListView) findViewById(R.id.unassigned_contact_list);

		contactsAdapter = new ContactsAdapterUnassigned(this,
				android.R.layout.simple_list_item_1, contacts);
		listView.setAdapter(contactsAdapter);
		readAllFromDB(contactsOpenHelper);
		// Test
		// Insert contacts
		Log.d("Insert: ", "Inserting ..");
		// contactsOpenHelper.addContact(new Contact("Ravi", "91", "ff@gg",
		// "Family"));
		// contactsOpenHelper.addContact(new Contact("Srinivas", "92", "aa@gg",
		// "Friend"));
		// contactsOpenHelper
		// .addContact(new Contact("Tommy", "93", "gg@gg", "Uni"));
		// contactsOpenHelper.addContact(new Contact("Karthik", "94", "vv@gg",
		// "Work"));
		// String category = "Friend";
		// readCatFromDB(contactsOpenHelper, category);
		// readAllFromDB(contactsOpenHelper);
		// contactsOpenHelper.updateCategory(new Contact("Ravi", "91", "ff@gg",
		// "Work"));
		// // deleteAllFromDB(contactsOpenHelper);
		// readAllFromDB(contactsOpenHelper);
		// Test
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					final int position, long id) {
				final CharSequence categoriesToShow[] = new CharSequence[] {
						"Acquaintance", "Family", "Friend", "Coworker", "Uni" };
				final CharSequence categories[] = new CharSequence[] {
						"Acquaintance", "Family", "Friend", "Coworker", "Uni" };
				switch (contacts.get(position).getCategory()) {
				case "Acquaintance":
					categoriesToShow[0] = categoriesToShow[0].toString()
							+ " (suggested)";
					break;
				case "Family":
					categoriesToShow[1] = categoriesToShow[1].toString()
							+ " (suggested)";
					break;
				case "Friend":
					categoriesToShow[2] = categoriesToShow[2].toString()
							+ " (suggested)";
					break;
				case "Coworker":
					categoriesToShow[3] = categoriesToShow[3].toString()
							+ " (suggested)";
					break;
				case "Uni":
					categoriesToShow[4] = categoriesToShow[4].toString()
							+ " (suggested)";
					break;

				}

				AlertDialog.Builder builder = new AlertDialog.Builder(
						Unassigned.this);
				builder.setTitle("Change the category");
				builder.setItems(categoriesToShow,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								contactsOpenHelper.updateCategory(new Contact(
										contacts.get(position).getName(),
										contacts.get(position).getPhone(),
										contacts.get(position).getEmail(),
										categories[which].toString()));
								// contacts.set(
								// position,
								// new Contact(contacts.get(position)
								// .getName(), contacts.get(
								// position).getPhone(), contacts
								// .get(position).getEmail(),
								// categories[which].toString()));
								contacts.remove(position);
								contactsAdapter = new ContactsAdapterUnassigned(
										Unassigned.this,
										android.R.layout.simple_list_item_1,
										contacts);
								listView.setAdapter(contactsAdapter);
								// readAllFromDB(contactsOpenHelper);
							}
						});
				builder.show();
				// TODO Auto-generated method stub
				// list.remove(item);
				// list.add(new Contact("5465", "4242"));
				// contactsAdapter = new ContactsAdapter(AllContacts.this,
				// android.R.layout.simple_list_item_1, list);
				// listView.setAdapter(contactsAdapter);
				// contactsOpenHelper.deleteContact(contacts.get(position));

				// list.clear();

			}

		});

		Button applyAllButton = (Button) findViewById(R.id.bApplyAll);
		applyAllButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO:
				for (Contact contact : contacts) {
					contactsOpenHelper.updateCategory(contact);
				}
				contacts.clear();
				contactsAdapter = new ContactsAdapterUnassigned(
						Unassigned.this, android.R.layout.simple_list_item_1,
						contacts);
				listView.setAdapter(contactsAdapter);
			}
		});

	}

	public void readAllFromDB(ContactsOpenHelper contactsOpenHelper) {
		List<Contact> contacts = contactsOpenHelper.getAllContacts();
		Log.d("Reading: ", "Reading all contacts..");

		for (Contact cn : contacts) {
			String log = "Id: " + cn.getId() + " ,Name: " + cn.getName()
					+ " ,Phone: " + cn.getPhone() + " ,Email: " + cn.getEmail()
					+ " ,Category: " + cn.getCategory();
			// Writing Contacts to log
			Log.d("Name: ", log);
		}
	}

	public void readCatFromDB(ContactsOpenHelper contactsOpenHelper,
			String category) {
		List<Contact> contacts = contactsOpenHelper
				.getContactsCategory(category);
		Log.d("Reading: ", "Reading all contacts from " + category);

		for (Contact cn : contacts) {
			String log = "Id: " + cn.getId() + " ,Name: " + cn.getName()
					+ " ,Phone: " + cn.getPhone() + " ,Email: " + cn.getEmail()
					+ " ,Category: " + cn.getCategory();
			// Writing Contacts to log
			Log.d("Name: ", log);
		}
	}

	public void deleteAllFromDB(ContactsOpenHelper contactsOpenHelper) {
		List<Contact> contacts = contactsOpenHelper.getAllContacts();
		Log.d("Deleting: ", "Deleting all contacts..");

		for (Contact cn : contacts) {
			contactsOpenHelper.deleteContact(cn);
		}
	}

	public void createSuggestions() {
		Random randomGenerator = new Random();
		for (Contact contact : contacts) {
			int randomInt = randomGenerator.nextInt(5);
			switch (randomInt) {
			case 0:
				contact.setCategory("Acquaintance");
				break;
			case 1:
				contact.setCategory("Family");
				break;
			case 2:
				contact.setCategory("Friend");
				break;
			case 3:
				contact.setCategory("Coworker");
				break;
			case 4:
				contact.setCategory("Uni");
				break;
			}
		}
	}

	// public void applySuggestedCategory(View v) {
	// // Contact contactToChange = (Contact) v.getTag();
	// // Log.d("Applying suggestion", contactToChange.getName());
	// int position = (Integer) v.getTag();
	// Log.d("Applying ", Integer.toString(position));
	//
	// }

}
