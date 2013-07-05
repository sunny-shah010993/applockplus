package com.thotran.applockplus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thotran.applockplus.adapter.AppAdapter;
import com.thotran.applockplus.db.DbProvider;

public class AllFragment extends MyFragment {

	public static Fragment newInstance() {
		AllFragment mAllFragment = new AllFragment();
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
