package com.notonepine.tabswitcher;

import android.app.Fragment;

import com.github.notonepine.tabswitcher.R;

public class TabSwitcherFragment extends Fragment {
	
	TabsListFragment getTabsListFragment() {
		return (TabsListFragment) getFragmentManager().findFragmentById(
				R.id.tabs_list_fragment);
	}

}
