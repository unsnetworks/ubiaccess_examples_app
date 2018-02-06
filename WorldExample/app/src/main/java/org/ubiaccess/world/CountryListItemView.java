package org.ubiaccess.world;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Country Item View for List
 */
public class CountryListItemView extends LinearLayout {
	private Context mContext;

	private TextView nameTextView;
	private TextView GNPTextView;

	public CountryListItemView(Context context) {
		super(context);

		mContext = context;

		initView();
	}

	/**
	 * View initialization
	 */
	private void initView() {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.country_listitem, this, true);

		nameTextView = (TextView) findViewById(R.id.nameTextView);
		GNPTextView = (TextView) findViewById(R.id.GNPTextView);
	}

	/**
	 * set name
	 */
	public void setName(String name) {
		nameTextView.setText(name);
	}

	/**
	 * set GNP
	 */
	public void setGNP(int GNP) {
		GNPTextView.setText(String.valueOf(GNP));
	}

}
