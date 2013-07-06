package com.thotran.applockplus.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.thotran.applockplus.R;
import com.thotran.applockplus.model.ModelApp;

public class AppAdapter extends ArrayAdapter<ModelApp> implements Filterable {

	private Context mContext;

	private ArrayList<ModelApp> mArrApps;

	private ArrayList<ModelApp> mArrAppsOrginal = new ArrayList<ModelApp>();

	private ViewHolder mViewHolder;

	private final Object mLock = new Object();

	public AppAdapter(Context context, ArrayList<ModelApp> objects) {
		super(context, R.layout.item_app, objects);
		mContext = context;
		mArrApps = objects;
		mArrAppsOrginal.addAll(objects);
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

		if (position >= 0 && position < mArrApps.size()) {
			ModelApp item = mArrApps.get(position);

			mViewHolder.image.setImageDrawable(item.getImage());
			mViewHolder.name.setText(item.getName());
			mViewHolder.type.setText(item.getType());
			mViewHolder.check.setChecked(item.isChecked);
		}

		return view;
	}

	@Override
	public Filter getFilter() {
		return new Filter() {
			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				if (results.count == 0) {
					notifyDataSetInvalidated();
				} else {
					notifyDataSetChanged();
					clear();
					final ArrayList<ModelApp> mArrApps = (ArrayList<ModelApp>) results.values;
					addAll(mArrApps);
				}
			}

			@SuppressWarnings("null")
			@SuppressLint("DefaultLocale")
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults mFilterResults = new FilterResults();
				ArrayList<ModelApp> mNewArrApp = new ArrayList<ModelApp>();
				if (constraint != null || constraint.length() > 0) {
					synchronized (mLock) {
						for (ModelApp item : mArrAppsOrginal) {
							if (item.getName()
									.toUpperCase()
									.startsWith(
											constraint.toString().toUpperCase()))
								mNewArrApp.add(item);
						}
						mFilterResults.values = mNewArrApp;
						mFilterResults.count = mNewArrApp.size();
						Log.e("mNewArrApp.size()", mNewArrApp.size() + "");
					}

				} else {
					synchronized (mLock) {
						mFilterResults.values = mArrAppsOrginal;
						mFilterResults.count = mArrAppsOrginal.size();
					}
				}
				Log.e("mNewArrApp", mNewArrApp.size() + "");
				Log.e("mArrOrginal", mArrAppsOrginal.size() + "");
				return mFilterResults;
			}
		};
	}

	public class ViewHolder {

		ImageView image;
		TextView name;
		TextView type;
		ToggleButton check;

	}

}
