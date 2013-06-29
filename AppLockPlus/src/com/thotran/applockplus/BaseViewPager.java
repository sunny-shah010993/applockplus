package com.thotran.applockplus;

import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.thotran.applockplus.adapter.ViewPagerAdapter;
import com.viewpagerindicator.PageIndicator;

public class BaseViewPager extends SherlockFragmentActivity {

	ViewPagerAdapter mPagerAdapter;
	ViewPager mViewPager;
	PageIndicator mIndicator;

}
