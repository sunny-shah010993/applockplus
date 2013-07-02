package com.thotran.applockplus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thotran.applockplus.adapter.AppAdapter;
import com.thotran.applockplus.db.DbProvider;

public class DownloadFragment extends MyFragment {

	public static Fragment newInstance() {
		DownloadFragment mDownloadFragment = new DownloadFragment();
		return mDownloadFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e("onCreate", "onCreate1");
		mArrApps = DbProvider.getDownloadApp(getActivity());
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
