package com.thotran.applockplus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
import com.thotran.applockplus.adapter.AppAdapter;
import com.thotran.applockplus.db.DbHelper;
import com.thotran.applockplus.db.DbProvider;
import com.thotran.applockplus.model.ModelApp;
import com.thotran.applockplus.pref.BooleanPref;
import com.thotran.applockplus.util.Config;

public class MyFragment extends SherlockFragment {

	public String mTitle;

	public ArrayList<ModelApp> mArrApps;

	public AppAdapter mAppAdapter;

	public ListView mListView;

	public DbHelper mDbHelper;

	public static MyFragment getInstance(String title) {
		MyFragment myFragment = new MyFragment();
		myFragment.mTitle = title;
		return myFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if ((savedInstanceState != null)
				&& savedInstanceState.containsKey(Config.KEY_TITLE)) {
			mTitle = savedInstanceState.getString(Config.KEY_TITLE);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LayoutInflater layoutInflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.list_app, container, false);
		mListView = (ListView) view.findViewById(R.id.listApp);
		mListView.setAdapter(mAppAdapter);
		if (mArrApps != null)
			initView();
		return view;
	}

	public void initView() {
		if (BooleanPref.getPreference(getActivity(),
				Config.KEY_SORT_BY_NAME_A_Z, true)) {
			sortAZ();
		}

		if (BooleanPref.getPreference(getActivity(),
				Config.KEY_SORT_BY_NAME_Z_A, false)) {
			sortZA();
		}

	}

	public void sortAZ() {
		Collections.sort(mArrApps, new Comparator<ModelApp>() {
			@Override
			public int compare(ModelApp lhs, ModelApp rhs) {
				return lhs.getName().compareToIgnoreCase(rhs.getName());
			}
		});
		mAppAdapter.notifyDataSetChanged();
	}

	public void sortZA() {
		Collections.sort(mArrApps, new Comparator<ModelApp>() {
			@Override
			public int compare(ModelApp lhs, ModelApp rhs) {
				return rhs.getName().compareToIgnoreCase(lhs.getName());
			}
		});
		mAppAdapter.notifyDataSetChanged();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(Config.KEY_TITLE, mTitle);
	}

	@Override
	public void setHasOptionsMenu(boolean hasMenu) {
		super.setHasOptionsMenu(hasMenu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_sort_by_name:
			Toast.makeText(getActivity(), "sort by name", Toast.LENGTH_SHORT)
					.show();

			if (BooleanPref.getPreference(getActivity(),
					Config.KEY_SORT_BY_NAME_A_Z, true)) {
				BooleanPref.setPreference(getActivity(),
						Config.KEY_SORT_BY_NAME_Z_A, true);
				BooleanPref.setPreference(getActivity(),
						Config.KEY_SORT_BY_NAME_A_Z, false);
				Log.e("A-Z", "az");
				sortZA();
			} else {
				BooleanPref.setPreference(getActivity(),
						Config.KEY_SORT_BY_NAME_A_Z, true);
				BooleanPref.setPreference(getActivity(),
						Config.KEY_SORT_BY_NAME_Z_A, false);
				Log.e("Z-A", "az");
				sortAZ();
			}

			break;
		case R.id.menu_sort_by_date:

			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}
