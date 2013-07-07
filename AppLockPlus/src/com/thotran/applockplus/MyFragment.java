package com.thotran.applockplus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnActionExpandListener;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.actionbarsherlock.widget.SearchView.OnSuggestionListener;
import com.thotran.applockplus.adapter.AppAdapter;
import com.thotran.applockplus.db.DbHelper;
import com.thotran.applockplus.db.DbProvider;
import com.thotran.applockplus.model.ModelApp;
import com.thotran.applockplus.pref.BooleanPref;
import com.thotran.applockplus.pref.IntPref;
import com.thotran.applockplus.util.Config;
import com.thotran.applockplus.util.Util;

public class MyFragment extends SherlockFragment implements
		OnQueryTextListener, OnSuggestionListener, OnNavigationListener {

	public String mTitle;

	public ArrayList<ModelApp> mArrApps = new ArrayList<ModelApp>();

	public AppAdapter mAppAdapter;

	public ListView mListView;

	public DbHelper mDbHelper;

	private Context mContext;

	public static final int ID_MENU_SEARCH = 1;

	private ActionBar mActionBar;

	private String[] listNavigation;
	
	private View view;
	
	public MyFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		mContext = getActivity();
		// Navigation
		mActionBar = getSherlockActivity().getSupportActionBar();
		listNavigation = getResources().getStringArray(R.array.listNavigation);
		Context context = getSherlockActivity().getSupportActionBar()
				.getThemedContext();
		ArrayAdapter<CharSequence> list = ArrayAdapter
				.createFromResource(context, R.array.listNavigation,
						R.layout.sherlock_spinner_item);
		list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		mActionBar.setListNavigationCallbacks(list, this);
		mActionBar.setSelectedNavigationItem(IntPref.getPreference(mContext,
				Config.KEY_POSITION_NAVIGATION, 0));
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		LayoutInflater layoutInflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = layoutInflater.inflate(R.layout.list_app, container, false);
		mListView = (ListView) view.findViewById(R.id.listApp);
		mAppAdapter = new AppAdapter(getActivity(), mArrApps);
		mListView.setAdapter(mAppAdapter);
		
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		SearchView searchView = new SearchView(getSherlockActivity()
				.getSupportActionBar().getThemedContext());
		searchView.setQueryHint(getString(R.string.hint_search_app));
		searchView.setOnQueryTextListener(this);
		searchView.setOnSuggestionListener(this);
		menu.add(1, ID_MENU_SEARCH, 0, getString(R.string.search))
				.setIcon(android.R.drawable.ic_menu_search)
				.setActionView(searchView)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_IF_ROOM
								| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
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
		if (BooleanPref.getPreference(getActivity(),
				Config.KEY_SORT_BY_NAME_A_Z, true)) {
			sortAZ();
		}

		if (BooleanPref.getPreference(getActivity(),
				Config.KEY_SORT_BY_NAME_Z_A, false)) {
			sortZA();
		}

		if (BooleanPref.getPreference(getActivity(),
				Config.KEY_SORT_BY_DATE_FIRST, false)) {
			sortFirstDate();
		}

		if (BooleanPref.getPreference(getActivity(),
				Config.KEY_SORT_BY_DATE_LAST, false)) {
			sortLastDate();
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

	public void sortFirstDate() {
		Collections.sort(mArrApps, new Comparator<ModelApp>() {
			@Override
			public int compare(ModelApp lhs, ModelApp rhs) {
				return Long.valueOf(lhs.getTime()).compareTo(
						Long.valueOf(rhs.getTime()));
			}
		});
		mAppAdapter.notifyDataSetChanged();
	}

	public void sortLastDate() {
		Collections.sort(mArrApps, new Comparator<ModelApp>() {
			@Override
			public int compare(ModelApp lhs, ModelApp rhs) {
				return Long.valueOf(rhs.getTime()).compareTo(
						Long.valueOf(lhs.getTime()));
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
		if (newText != null && mAppAdapter != null)
			mAppAdapter.getFilter().filter(newText);
		return true;
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
    public void onDestroyView() {
        super.onDestroyView();
        if (view != null) {
            ViewGroup parentViewGroup = (ViewGroup) view.getParent();
            if (parentViewGroup != null) {
                parentViewGroup.removeView(view);
            }
        }
    }
}
