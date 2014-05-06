package com.github.notonepine.tabswitcher;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

public class MainActivity extends FragmentActivity {
	TabsListFragment mTabListFragment;
	TitleBarFragment mTitleBarFragment;
	private ImageView mPreview;
	public boolean mTabChange = false;
	public boolean mDragging, mTabsShowing = false;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.main_activity);
		mTabListFragment = (TabsListFragment) getFragmentManager().findFragmentById(R.id.tabs_list_fragment);
		mTitleBarFragment = (((TitleBarFragment) getFragmentManager().findFragmentById(R.id.title_bar_fragment)));
		mPreview = ((ImageView) findViewById(R.id.preview));

		mTabListFragment.mOnTabItemHoverListener = new TabsListFragment.OnTabItemHoverListener() {
			@Override public void onTabItemHover(Tab tab) {
				setPreview(tab.getPageId());
			}

			@Override public void onDrop(Tab tab) {
				setPreview(tab.getPageId());
			}
		};

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

	public void setPreview(int resId) {
		mPreview.setImageResource(resId);
	}

	public boolean getTabChange() {
		return mTabChange;
	}

	public void setTabChange(boolean tabChange) {
		mTabChange = tabChange;
	}

	public void setCurrentTabAndClose() {
		mTabListFragment.setCurrentTabAndClose();
	}

}
