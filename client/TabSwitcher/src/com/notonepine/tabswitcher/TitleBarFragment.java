package com.notonepine.tabswitcher;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.notonepine.tabswitcher.R;

public class TitleBarFragment extends TabSwitcherFragment {
	private boolean mSwitching = false;

	private View mView;
	private View toolbar;
	private ToolBarAnimator mToolBarAnimator;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.title_bar, container, false);

		toolbar = mView.findViewById(R.id.toolbar);

		mToolBarAnimator = new ToolBarAnimator(/* the view */mView,
		/* context */getActivity(),
		/* open */mSwitching);

		toolbar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mToolBarAnimator.toggleToolbarState();
				getTabsListFragment().toggleVisibility();
				makeHapticFeedback();
				mSwitching = !mSwitching;
			}
		});
		
		((ImageView) mView.findViewById(R.id.new_tab)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});

		return mView;
	}

	public boolean isSwitching() {
		return mSwitching;
	}

	private void makeHapticFeedback() {
		final long[] vibrationPattern = { 0L, 18L };
		final Vibrator vibrator = (Vibrator) getActivity().getSystemService(
				Context.VIBRATOR_SERVICE);
		vibrator.vibrate(vibrationPattern, -1);
	}
}
