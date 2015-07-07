package com.example.gui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContactsAdapter extends ArrayAdapter<Contact> {

	private final int mResLayout;
	private ArrayList<Contact> contacts = new ArrayList<Contact>();

	public ContactsAdapter(Context context, int resource, List<Contact> objects) {
		super(context, resource, objects);
		mResLayout = resource;
		for (int i = 0; i < objects.size(); i++) {
			contacts.add(objects.get(i));
		}

	}

	// @Override
	public int getCount() {
		// TODO Auto-generated method stub
		return contacts.size();
	}

	//
	@Override
	public Contact getItem(int position) {
		// TODO Auto-generated method stub
		return contacts.get(position);
	}

	//
	// @Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public List<Contact> getAll() {
		return contacts;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.contact_list_item, parent,
					false);
		}

		Contact contact = contacts.get(position);

		TextView nameTextView = (TextView) convertView
				.findViewById(R.id.name_view);
		nameTextView.setText(contact.getName());

		TextView categoryTextView = (TextView) convertView
				.findViewById(R.id.category_view);
		categoryTextView.setText(contact.getCategory());

		return convertView;
	}

}
