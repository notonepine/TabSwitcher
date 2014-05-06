package com.github.notonepine.tabswitcher;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class TitleBarFragment extends Fragment {
	private boolean mSwitching = false;

	private View mView;
	private MainActivity mMainActivity;
	private ToolBarAnimator mToolBarAnimator;

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mMainActivity = (MainActivity) getActivity();
		mView = inflater.inflate(R.layout.title_bar, container, false);

		mToolBarAnimator = new ToolBarAnimator(
		/* titleBar */mView.findViewById(R.id.titlebar),
		/* rightButtons */mView.findViewById(R.id.right_titlebar_buttons),
		/* newTabButton */mView.findViewById(R.id.new_tab),
		/* context */getActivity(),
		/* open */mSwitching);
		mView.findViewById(R.id.tabs_button).setOnTouchListener(
				tabSwitchTouchListener(mView.findViewById(R.id.tab_alternative)));

		((ImageView) mView.findViewById(R.id.new_tab)).setOnClickListener(new OnClickListener() {

			@Override public void onClick(View v) {
				mMainActivity.mTabListFragment.addNewTab();
			}
		});

//		mView.findViewById(R.id.toolbar).setOnDragListener(new OnDragListener() {
//			@Override public boolean onDrag(View v, DragEvent event) {
//				switch (event.getAction()) {
//				case DragEvent.ACTION_DRAG_EXITED:
//				case DragEvent.ACTION_DROP:
//					if (mMainActivity.getTabChange()) {
//						mMainActivity.setCurrentTabAndClose();
//					} else {
//						mMainActivity.stateToggle();
//					}
//					break;
//				case DragEvent.ACTION_DRAG_STARTED:
//				case DragEvent.ACTION_DRAG_ENTERED:
//				case DragEvent.ACTION_DRAG_ENDED:
////					mMainActivity.setTabChange(false);
//
//				default:
//					break;
//				}
//				return true;
//			}
//		});

		return mView;
	}

	public boolean isSwitching() {
		return mSwitching;
	}

	private OnTouchListener tabSwitchTouchListener(final View view) {
		return new OnTouchListener() {

			@Override public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
					view.startDrag(null, shadowBuilder, null, View.INVISIBLE);

					mToolBarAnimator.toggleToolbarState();
					mMainActivity.toggleTabListVisibility();
					mMainActivity.makeHapticFeedback();
					mSwitching = !mSwitching;
					mMainActivity.setDragging(true);
					mView.findViewById(R.id.toolbar).setOnClickListener(toolbarClickListener());
					return true;
				}
				mMainActivity.setDragging(false);

				return false;
			}
		};
	}

	private OnClickListener toolbarClickListener() {
		return new OnClickListener() {
			@Override public void onClick(View v) {
				if (((MainActivity) getActivity()).isTabsShowing()) {
					stateToggle();
				}
			}

		};

	}

	public void stateToggle() {
		if (((MainActivity) getActivity()).isTabsShowing()) {
			mToolBarAnimator.toggleToolbarState();
			mMainActivity.toggleTabListVisibility();
			mMainActivity.makeHapticFeedback();
			mSwitching = !mSwitching;
		}
	}

}
