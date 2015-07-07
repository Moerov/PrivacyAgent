package com.example.gui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ContactsAdapterUnassigned extends ArrayAdapter<Contact> {

	private final int mResLayout;
	private ArrayList<Contact> contacts = new ArrayList<Contact>();

	public ContactsAdapterUnassigned(Context context, int resource,
			List<Contact> objects) {
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
		UserHolder holder = null;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(
					R.layout.contact_list_item_suggestion, parent, false);
			holder = new UserHolder();
			holder.name = (TextView) convertView.findViewById(R.id.name_view);
			holder.category = (TextView) convertView
					.findViewById(R.id.category_view);
			// holder.bApply = (Button) convertView.findViewById(R.id.bApply);
			convertView.setTag(holder);
		} else {
			holder = (UserHolder) convertView.getTag();
		}

		Contact contact = contacts.get(position);

		TextView nameTextView = (TextView) convertView
				.findViewById(R.id.name_view);
		nameTextView.setText(contact.getName());

		TextView categoryTextView = (TextView) convertView
				.findViewById(R.id.category_view);
		categoryTextView.setText("Suggested: " + contact.getCategory());

		// holder.bApply.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Log.i("Edit Button Clicked", "**********");
		// }
		// });

		// Button applyButton = (Button) convertView.findViewById(R.id.bApply);
		// applyButton.setTag(position);
		// applyButton.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Log.d("Applying suggestion", "******");
		// int position = (Integer) v.getTag();
		// Log.d("Applying suggestion", contacts.get(position)
		// .getCategory());
		// }
		// });

		return convertView;
	}

	static class UserHolder {
		TextView name;
		TextView category;
		Button bApply;
	}

}
