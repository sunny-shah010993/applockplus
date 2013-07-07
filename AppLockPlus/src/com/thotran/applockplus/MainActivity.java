package com.thotran.applockplus;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnActionExpandListener;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.actionbarsherlock.widget.SearchView.OnSuggestionListener;
import com.thotran.applockplus.adapter.AppAdapter;
import com.thotran.applockplus.db.DbProvider;
import com.thotran.applockplus.model.ModelApp;
import com.thotran.applockplus.pref.BooleanPref;
import com.thotran.applockplus.pref.IntPref;
import com.thotran.applockplus.util.Config;
import com.thotran.applockplus.util.Util;

public class MainActivity extends SherlockActivity implements
		OnNavigationListener, OnQueryTextListener, OnSuggestionListener {

	private String[] listNavigation;

	private Context mContext;

	private ArrayList<ModelApp> mArrApps = new ArrayList<ModelApp>();

	private ActionBar mActionBar;

	private AppAdapter mAppAdapter;

	private ListView mListView;

	private static final int ID_MENU_SEARCH = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = getApplicationContext();
		listNavigation = getResources().getStringArray(R.array.listNavigation);
		Context context = getSupportActionBar().getThemedContext();
		ArrayAdapter<CharSequence> list = ArrayAdapter
				.createFromResource(context, R.array.listNavigation,
						R.layout.sherlock_spinner_item);
		list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getSupportActionBar().setListNavigationCallbacks(list, this);

		mActionBar = getSupportActionBar();
		mActionBar.setSelectedNavigationItem(IntPref.getPreference(mContext,
				Config.KEY_POSITION_NAVIGATION, 0));
		// ----------
		mListView = (ListView) findViewById(R.id.listApp);
	}

	public void initArrApps() {
		if (BooleanPref.getPreference(mContext, Config.KEY_ALL_APP, true)) {
			mArrApps.addAll(DbProvider.getAllApp(mContext));
		}

		if (BooleanPref.getPreference(mContext, Config.KEY_DOWNLOAD_APP, false)) {
			mArrApps.addAll(DbProvider.getDownloadApp(mContext));
		}

		if (BooleanPref.getPreference(mContext, Config.KEY_SYSTEM_APP, false)) {
			mArrApps.addAll(DbProvider.getSystemApp(mContext));
		}
	}

	public void initView() {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SearchView searchView = new SearchView(getSupportActionBar()
				.getThemedContext());
		searchView.setQueryHint(getString(R.string.hint_search_app));
		searchView.setOnQueryTextListener(this);
		searchView.setOnSuggestionListener(this);
		menu.add(1, ID_MENU_SEARCH, 0, getString(R.string.search))
				.setIcon(android.R.drawable.ic_menu_search)
				.setActionView(searchView)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_IF_ROOM
								| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(ID_MENU_SEARCH);
		item.setOnActionExpandListener(new OnActionExpandListener() {
			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
				return true;
			}

			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				mArrApps.clear();
				initArrApps();
				Util.sortAll(mContext, mArrApps);
				mAppAdapter.notifyDataSetChanged();
				return true;
			}
		});
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			Toast.makeText(mContext, "home", Toast.LENGTH_SHORT).show();
			break;

		case ID_MENU_SEARCH:
			Util.sortAll(mContext, mArrApps);
			mAppAdapter.notifyDataSetChanged();
			break;
		case R.id.menu_sort_by_name:
			if (BooleanPref.getPreference(mContext,
					Config.KEY_SORT_BY_NAME_A_Z, true)) {
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_NAME_Z_A, true);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_NAME_A_Z, false);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_DATE_FIRST, false);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_DATE_LAST, false);
				Util.sortZA(mArrApps);
				mAppAdapter.notifyDataSetChanged();
				Toast.makeText(mContext, getString(R.string.sort_by_name_a_z),
						Toast.LENGTH_SHORT).show();

			} else {
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_NAME_A_Z, true);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_NAME_Z_A, false);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_DATE_FIRST, false);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_DATE_LAST, false);
				Util.sortAZ(mArrApps);
				mAppAdapter.notifyDataSetChanged();
				Toast.makeText(mContext, getString(R.string.sort_by_name_z_a),
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.menu_sort_by_date:
			if (BooleanPref.getPreference(mContext,
					Config.KEY_SORT_BY_DATE_LAST, true)) {
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_NAME_Z_A, false);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_NAME_A_Z, false);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_DATE_FIRST, true);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_DATE_LAST, false);
				Util.sortFirstDate(mArrApps);
				mAppAdapter.notifyDataSetChanged();
				Toast.makeText(mContext,
						getString(R.string.sort_by_date_first),
						Toast.LENGTH_SHORT).show();
			} else {
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_NAME_Z_A, false);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_NAME_A_Z, false);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_DATE_FIRST, false);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_DATE_LAST, true);
				Util.sortLastDate(mArrApps);
				mAppAdapter.notifyDataSetChanged();
				Toast.makeText(mContext, getString(R.string.sort_by_date_last),
						Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		IntPref.setPreference(mContext, Config.KEY_POSITION_NAVIGATION,
				itemPosition);
		if (listNavigation[itemPosition].equals(getString(R.string.all))) {
			mArrApps.clear();
			mArrApps = DbProvider.getAllApp(mContext);
			mAppAdapter = new AppAdapter(mContext, mArrApps);
			Util.sortAll(mContext, mArrApps);
			mAppAdapter.notifyDataSetChanged();
			BooleanPref.setPreference(mContext, Config.KEY_ALL_APP, true);
			BooleanPref.setPreference(mContext, Config.KEY_DOWNLOAD_APP, false);
			BooleanPref.setPreference(mContext, Config.KEY_SYSTEM_APP, false);
		} else if (listNavigation[itemPosition]
				.equals(getString(R.string.download))) {
			mArrApps.clear();
			mArrApps = DbProvider.getDownloadApp(mContext);
			mAppAdapter = new AppAdapter(mContext, mArrApps);
			Util.sortAll(mContext, mArrApps);
			mAppAdapter.notifyDataSetChanged();
			BooleanPref.setPreference(mContext, Config.KEY_ALL_APP, false);
			BooleanPref.setPreference(mContext, Config.KEY_DOWNLOAD_APP, true);
			BooleanPref.setPreference(mContext, Config.KEY_SYSTEM_APP, false);
		} else {
			mArrApps.clear();
			mArrApps = DbProvider.getSystemApp(mContext);
			mAppAdapter = new AppAdapter(mContext, mArrApps);
			Util.sortAll(mContext, mArrApps);
			mAppAdapter.notifyDataSetChanged();
			BooleanPref.setPreference(mContext, Config.KEY_ALL_APP, false);
			BooleanPref.setPreference(mContext, Config.KEY_DOWNLOAD_APP, false);
			BooleanPref.setPreference(mContext, Config.KEY_SYSTEM_APP, true);
		}
		mListView.setAdapter(mAppAdapter);
		return true;
	}

	@Override
	public boolean onSuggestionSelect(int position) {
		return false;
	}

	@Override
	public boolean onSuggestionClick(int position) {
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		mAppAdapter.getFilter().filter(newText);
		return true;
	}
	
}
