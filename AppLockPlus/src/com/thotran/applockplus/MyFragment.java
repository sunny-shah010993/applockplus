package com.thotran.applockplus;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.thotran.applockplus.adapter.AppAdapter;
import com.thotran.applockplus.db.DbHelper;
import com.thotran.applockplus.db.DbProvider;
import com.thotran.applockplus.model.ModelApp;
import com.thotran.applockplus.util.Config;

public class MyFragment extends SherlockFragment {

	private String mTitle;

	private ArrayList<ModelApp> mArrApps;

	private AppAdapter mAppAdapter;

	private ListView mListView;

	private DbHelper mDbHelper;

	public static MyFragment getInstance(String title) {
		MyFragment myFragment = new MyFragment();
		myFragment.mTitle = title;
		return myFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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

		if (mTitle.equals(getActivity().getString(R.string.all))) {
			mArrApps = DbProvider.getAllApp(getActivity());
		} else if (mTitle.equals(getActivity().getString(R.string.system))) {
			mArrApps = DbProvider.getSystemApp(getActivity());
		} else {
			mArrApps = DbProvider.getDownloadApp(getActivity());
		}

		mAppAdapter = new AppAdapter(getActivity(), mArrApps);
		mListView.setAdapter(mAppAdapter);
		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(Config.KEY_TITLE, mTitle);
	}

}
