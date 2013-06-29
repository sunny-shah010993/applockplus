package com.thotran.applockplus.adapter;

import com.thotran.applockplus.MyFragment;
import com.thotran.applockplus.R;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	public static String TITLE_VIEWPAGER[] = null;

	public Context context;

	private int count = 0;

	public ViewPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		this.context = context;
		TITLE_VIEWPAGER = context.getResources().getStringArray(
				R.array.titleviewpager);
		count = TITLE_VIEWPAGER.length;
	}

	@Override
	public Fragment getItem(int position) {
		return MyFragment.getInstance(TITLE_VIEWPAGER[position % TITLE_VIEWPAGER.length]);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return TITLE_VIEWPAGER[position % TITLE_VIEWPAGER.length];
	}

	@Override
	public int getCount() {
		return count;
	}

}
