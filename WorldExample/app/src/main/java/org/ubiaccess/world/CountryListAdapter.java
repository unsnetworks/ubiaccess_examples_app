package org.ubiaccess.world;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.ubiaccess.world.data.CountryResult;

import java.util.ArrayList;

/**
 * Adapter for Country ListView
 */
public class CountryListAdapter extends BaseAdapter {
	private static final String TAG = "CountryListAdapter";

	Context context;
	ArrayList<CountryResult> items = new ArrayList<CountryResult>();

	public CountryListAdapter(Context context) {
		this.context = context;
	}

	public void clear() {
		items.clear();

		notifyDataSetChanged();
	}

	public void setItems(ArrayList<CountryResult> items) {
		this.items = items;

		notifyDataSetChanged();
	}

	public void remove(int position){
		try {
			items.remove(position);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		notifyDataSetChanged();
	}

	public ArrayList<CountryResult> getList() {
		return items;
	}

	public int getCount() {
		if (items == null) {
			return 0;
		}

		return items.size();
	}

	public CountryResult getItem(int position) {
		if (items == null) {
			return null;
		}

		CountryResult item = null;
		if (position < items.size()) {
			try {
				item = items.get(position);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}

		return item;
	}

	public boolean areAllItemsSelectable() {
		return true;
	}

	public boolean isSelectable(int position) {
		return true;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		CountryListItemView itemView = null;
		if (convertView == null) {
			itemView = new CountryListItemView(context);
		} else {
			itemView = (CountryListItemView) convertView;
		}


		if (items != null) {
			CountryResult item = items.get(position);

			// name
			itemView.setName(item.name);

			// GNP
			itemView.setGNP(item.GNP);

		}

		return itemView;
	}

}
