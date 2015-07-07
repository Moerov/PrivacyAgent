package com.example.gui;

import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 
 */

/**
 * @author andrey
 * 
 */
public class Category extends Activity {

	private ContactsAdapter contactsAdapter;
	private ListView listView;
	private ContactsOpenHelper contactsOpenHelper;
	private List<Contact> contacts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category);

		Bundle extras = getIntent().getExtras();
		String category = extras.getString("Category");

		if (extras != null) {
			switch (category) {
			case "ACQ":
				// // Read from db all contacts for that category
				// setContentView(R.layout.category);
				// super.onCreate(savedInstanceState);
				// setTitle("Acquaintance");
				// contactsOpenHelper = new ContactsOpenHelper(this);
				// contacts = contactsOpenHelper.getContactsCategory("Acq");
				// Log.d("Insert: ", Integer.toString(contacts.size()));
				// listView = (ListView)
				// findViewById(R.id.category_contact_list);
				//
				// contactsAdapter = new ContactsAdapter(this,
				// android.R.layout.simple_list_item_1, contacts);
				// listView.setAdapter(contactsAdapter);
				setAdapter(category);
				break;
			case "FAMILY":
				setAdapter(category);
				break;
			case "FRIEND":
				setAdapter(category);
				break;
			case "WORK":
				setAdapter(category);
				break;
			case "UNI":
				setAdapter(category);
				break;
			default:
				setAdapter(category);
				break;
			}

		}

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					final int position, long id) {
				final CharSequence categories[] = new CharSequence[] {
						"Unassigned", "Acquaintance", "Family", "Friend",
						"Coworker", "Uni" };

				AlertDialog.Builder builder = new AlertDialog.Builder(
						Category.this);
				builder.setTitle("Change the category");
				builder.setItems(categories,
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
								contactsAdapter = new ContactsAdapter(
										Category.this,
										android.R.layout.simple_list_item_1,
										contacts);
								listView.setAdapter(contactsAdapter);
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

	}

	public void setAdapter(String category) {
		// Read from db all contacts for that category
		setTitle(category);
		contactsOpenHelper = new ContactsOpenHelper(this);
		contacts = contactsOpenHelper.getContactsCategory(category);
		listView = (ListView) findViewById(R.id.category_contact_list);
		contactsAdapter = new ContactsAdapter(this,
				android.R.layout.simple_list_item_1, contacts);
		listView.setAdapter(contactsAdapter);
	}

}
