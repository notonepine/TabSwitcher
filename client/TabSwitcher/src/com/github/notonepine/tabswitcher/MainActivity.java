package com.github.notonepine.tabswitcher;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {
	TabsListFragment mTabListFragment; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.main_activity);
		mTabListFragment = (TabsListFragment) getFragmentManager().findFragmentById(R.id.tabs_list_fragment);
	}
	
}
