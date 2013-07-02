package com.thotran.applockplus;

import com.thotran.applockplus.adapter.AppAdapter;
import com.thotran.applockplus.db.DbProvider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AllFragment extends MyFragment {

	public static Fragment newInstance() {
		AllFragment mAllFragment = new AllFragment();
		Log.e("newInstance() ", "newInstance() ");
		return mAllFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mArrApps = DbProvider.getAllApp(getActivity());
		mAppAdapter = new AppAdapter(getActivity(), mArrApps);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		initView();
		mAppAdapter.notifyDataSetChanged();
	}
}
