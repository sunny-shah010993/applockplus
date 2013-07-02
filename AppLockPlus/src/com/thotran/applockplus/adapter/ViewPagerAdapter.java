package com.thotran.applockplus.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thotran.applockplus.AllFragment;
import com.thotran.applockplus.DownloadFragment;
import com.thotran.applockplus.R;
import com.thotran.applockplus.SystemFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	public static String TITLE_VIEWPAGER[] = null;

	public Context context;

	private ArrayList<Fragment> mArrFragment;

	public ViewPagerAdapter(Context context, FragmentManager fm,
			ArrayList<Fragment> mArrFragment) {
		super(fm);
		this.context = context;
		TITLE_VIEWPAGER = context.getResources().getStringArray(
				R.array.titleviewpager);
		this.mArrFragment = mArrFragment;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return AllFragment.newInstance();
		case 1:
			return DownloadFragment.newInstance();
		case 2:
			return SystemFragment.newInstance();
		default:
			break;
		}

		return null;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return TITLE_VIEWPAGER[position];
	}

	@Override
	public int getCount() {
		return mArrFragment.size();
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

}
