package com.github.notonepine.tabswitcher;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {
	TabsListFragment mTabListFragment;
	TitleBarFragment mTitleBarFragment;
	public boolean mDragging, mTabsShowing = false;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.main_activity);
		mTabListFragment = (TabsListFragment) getFragmentManager().findFragmentById(R.id.tabs_list_fragment);
		mTitleBarFragment = (((TitleBarFragment) getFragmentManager().findFragmentById(R.id.title_bar_fragment)));

	}

	protected void toggleTabListVisibility() {
		mTabListFragment.toggleVisibility();
	}

	protected boolean isTabsShowing() {
		return mTabListFragment.tabsShowing();
	}

	protected boolean isDragging() {
		return mDragging;
	}

	public void setDragging(boolean dragging) {
		mDragging = dragging;
	}

	public void makeHapticFeedback() {
		final long[] vibrationPattern = { 0L, 18L };
		final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(vibrationPattern, -1);
	}

	public void stateToggle() {
		mTitleBarFragment.stateToggle();
	}

}
