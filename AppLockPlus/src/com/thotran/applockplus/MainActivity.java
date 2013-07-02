package com.thotran.applockplus;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.thotran.applockplus.adapter.ViewPagerAdapter;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class MainActivity extends SherlockFragmentActivity {

	ViewPagerAdapter mPagerAdapter;
	ViewPager mViewPager;
	PageIndicator mIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ArrayList<Fragment> mArrFragments = new ArrayList<Fragment>();
		mArrFragments.add(Fragment.instantiate(this,
				AllFragment.class.getName()));
		mArrFragments.add(Fragment.instantiate(this,
				DownloadFragment.class.getName()));
		mArrFragments.add(Fragment.instantiate(this,
				SystemFragment.class.getName()));

		mPagerAdapter = new ViewPagerAdapter(getApplicationContext(),
				getSupportFragmentManager(), mArrFragments);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mPagerAdapter);
		mIndicator = (TitlePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mViewPager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

}
