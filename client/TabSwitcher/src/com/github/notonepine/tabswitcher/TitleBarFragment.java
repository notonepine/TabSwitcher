package com.github.notonepine.tabswitcher;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class TitleBarFragment extends Fragment {
	private boolean mSwitching = false;

	private View mView;
	private View toolbar;
	private MainActivity mMainActivity;
	private ToolBarAnimator mToolBarAnimator;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mMainActivity = (MainActivity) getActivity();
		mView = inflater.inflate(R.layout.title_bar, container, false);

		toolbar = mView.findViewById(R.id.toolbar);

		mToolBarAnimator = new ToolBarAnimator(
				mView.findViewById(R.id.titlebar),
				mView.findViewById(R.id.right_titlebar_buttons),
				mView.findViewById(R.id.new_tab), getActivity(), mSwitching);

		toolbar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mToolBarAnimator.toggleToolbarState();
				mMainActivity.toggleTabListVisibility();
				mSwitching = !mSwitching;
			}
		});

		return mView;
	}

	public boolean isSwitching() {
		return mSwitching;
	}
}
