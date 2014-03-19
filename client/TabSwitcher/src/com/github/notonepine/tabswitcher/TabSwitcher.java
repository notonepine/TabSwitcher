package com.github.notonepine.tabswitcher;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class TabSwitcher extends FragmentActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		
		setContentView(R.layout.main_activity);
		
	}

}
