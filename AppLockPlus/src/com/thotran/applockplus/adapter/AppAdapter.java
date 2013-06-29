package com.thotran.applockplus.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.thotran.applockplus.R;
import com.thotran.applockplus.model.ModelApp;

public class AppAdapter extends ArrayAdapter<ModelApp> {

	private Context mContext;

	private ArrayList<ModelApp> mArrApps;

	private ViewHolder mViewHolder;

	public AppAdapter(Context context, ArrayList<ModelApp> objects) {
		super(context, R.layout.item_app, objects);
		mContext = context;
		mArrApps = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		if (view == null) {
			LayoutInflater layoutInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.item_app, parent, false);
			mViewHolder = new ViewHolder();
			mViewHolder.image = (ImageView) view.findViewById(R.id.image);
			mViewHolder.name = (TextView) view.findViewById(R.id.name);
			mViewHolder.type = (TextView) view.findViewById(R.id.type);
			mViewHolder.check = (ToggleButton) view.findViewById(R.id.check);
			view.setTag(mViewHolder);
		} else
			mViewHolder = (ViewHolder) view.getTag();

		if (position > 0 && position < mArrApps.size()) {
			ModelApp item = mArrApps.get(position);

			mViewHolder.image.setImageDrawable(item.getImage());
			mViewHolder.name.setText(item.getName());
			mViewHolder.type.setText(item.getType());
			mViewHolder.check.setChecked(item.isChecked);
		}

		return view;
	}

	public class ViewHolder {

		ImageView image;
		TextView name;
		TextView type;
		ToggleButton check;

	}

}
