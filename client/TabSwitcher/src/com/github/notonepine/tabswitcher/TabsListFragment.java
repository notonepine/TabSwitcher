package com.github.notonepine.tabswitcher;

import java.util.LinkedList;

import android.app.ListFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TabsListFragment extends ListFragment {
	static final String LOGTAG = "TabsListFragment";
	// Views.
	private View mView;
	private View mViewOverlay;
	private TabListAdapter mAdapter;
	private LinkedList<Tab> mTabs;
	int mCurrentTabIndex;

	private Animation mAnimationFadeIn;
	private Animation mAnimationFadeOut;
	private Animation mAnimationMoveLeft;
	private Animation mAnimationMoveRight;

	OnTabItemHoverListener mOnTabItemHoverListener;

	private MainActivity mMainActivity;

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mMainActivity = (MainActivity) getActivity();

		mView = inflater.inflate(R.layout.tabs_list, container, false);
		mViewOverlay = mView.findViewById(R.id.preview_overlay);
		mViewOverlay.setOnClickListener(new OnClickListener() {

			@Override public void onClick(View v) {
				mMainActivity.stateToggle();
			}
		});
		mTabs = new LinkedList<Tab>();
		mTabs.add(new Tab(R.drawable.thumbnail_mozilla, "Home of the Mozilla Project Ñ Mozilla",
				R.drawable.screen_mozilla));
		mTabs.add(new Tab(R.drawable.thumbnail_facebook, "Joshua Dover on FB", R.drawable.screen_facebook));
		mTabs.add(new Tab(R.drawable.thumbnail_twitter, "Twitter home", R.drawable.screen_twitter));
		mTabs.add(new Tab(R.drawable.thumbnail_yelp, "Yelp-Canela", R.drawable.screen_yelp));
		mAdapter = new TabListAdapter(mMainActivity);
		setListAdapter(mAdapter);

		mAnimationMoveLeft = AnimationUtils.loadAnimation(mMainActivity, R.anim.tabs_list_entrance);
		mAnimationMoveLeft.setAnimationListener(new Animation.AnimationListener() {
			@Override public void onAnimationStart(Animation animation) {}

			@Override public void onAnimationRepeat(Animation animation) {}

			@Override public void onAnimationEnd(Animation animation) {
				getListView().setVisibility(View.VISIBLE);
			}
		});

		mAnimationFadeIn = AnimationUtils.loadAnimation(mMainActivity, R.anim.fade_in);
		mAnimationFadeIn.setAnimationListener(new Animation.AnimationListener() {
			@Override public void onAnimationStart(Animation arg0) {
				getListView().startAnimation(mAnimationMoveLeft);
			}

			@Override public void onAnimationRepeat(Animation arg0) {}

			@Override public void onAnimationEnd(Animation arg0) {}
		});

		mAnimationFadeOut = AnimationUtils.loadAnimation(mMainActivity, R.anim.fade_out);
		mAnimationFadeOut.setAnimationListener(new Animation.AnimationListener() {
			@Override public void onAnimationStart(Animation animation) {}

			@Override public void onAnimationRepeat(Animation animation) {}

			@Override public void onAnimationEnd(Animation animation) {
				mView.setVisibility(View.GONE);
			}
		});

		mAnimationMoveRight = AnimationUtils.loadAnimation(mMainActivity, R.anim.tabs_list_exit);
		mAnimationMoveRight.setAnimationListener(new Animation.AnimationListener() {
			@Override public void onAnimationStart(Animation arg0) {}

			@Override public void onAnimationRepeat(Animation arg0) {}

			@Override public void onAnimationEnd(Animation animation) {
				getListView().setVisibility(View.INVISIBLE);
				mViewOverlay.startAnimation(mAnimationFadeOut);
			}
		});

		mViewOverlay.setOnDragListener(new OnDragListener() {
			@Override public boolean onDrag(View v, DragEvent event) {
				switch (event.getAction()) {
				case DragEvent.ACTION_DRAG_EXITED:
				case DragEvent.ACTION_DROP:
					if (mMainActivity.getTabChange()) {
						setCurrentTabAndClose();
					} else {
						mMainActivity.stateToggle();
					}
					break;
				case DragEvent.ACTION_DRAG_STARTED:
				case DragEvent.ACTION_DRAG_ENTERED:
				case DragEvent.ACTION_DRAG_ENDED:
					mMainActivity.setTabChange(false);

				default:
					break;
				}
				return true;
			}
		});
		return mView;
	}

	public void addNewTab() {
		mTabs.add(1, new Tab(R.drawable.thumbnail_mozilla, "New Tab Page", R.drawable.screen_mozilla));
		setListAdapter(new TabListAdapter(mMainActivity));

	}

	public void toggleVisibility() {
		if (mView.isShown()) {
			transitionOut();
		} else {
			transitionIn();
		}
	}

	public void setCurrentTabAndClose() {
		final Tab currentTab = mTabs.remove(mCurrentTabIndex);
		Log.d(LOGTAG, "Current Tab Size: " + mTabs.size());
		mTabs.addFirst(currentTab);
		setListAdapter(mAdapter);
		new AsyncTask<Integer, Void, Void>(

		) {

			@Override protected Void doInBackground(Integer... params) {
				mMainActivity.setPreview(params[0]);
				return null;
			}
		}.execute(currentTab.getPageId());
		mMainActivity.setPreview(currentTab.getPageId());
		mMainActivity.stateToggle();
	}

	public boolean tabsShowing() {
		return mView.isShown();
	}

	private void transitionIn() {
		Log.d(LOGTAG, "TRANSITION IN");
		mView.setVisibility(View.VISIBLE);
		mViewOverlay.startAnimation(mAnimationFadeIn);
	}

	private void transitionOut() {
		Log.d(LOGTAG, "TRANSITION OUT");
		getListView().startAnimation(mAnimationMoveRight);
	}

	class TabListAdapter extends ArrayAdapter<Tab> {
		public TabListAdapter(Context context) {
			super(context, R.layout.switcher_list_item, mTabs);
		}

		@Override public View getView(final int position, View convertView, ViewGroup parent) {
			final Tab tab = mTabs.get(position);
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.switcher_list_item, null);
				ImageView image = (ImageView) convertView.findViewById(R.id.thumbnail);
				image.setImageResource(tab.getThumbnailId());

				TextView titleView = (TextView) convertView.findViewById(R.id.title);
				titleView.setText(tab.getTitle());
			}

			convertView.setOnClickListener(new OnClickListener() {

				@Override public void onClick(View v) {
					mCurrentTabIndex = position;
					setCurrentTabAndClose();

				}
			});

			convertView.setOnDragListener(new TabItemDragListener(position));

			// convertView.setOnDragListener(new TabItemDragListener(position));
			/*
			 * convertView.setOnClickListener(new View.OnClickListener() {
			 * 
			 * @Override public void onClick(View v) { if (!mInDragMode) {
			 * mOnTabItemHoverListener.onDrop(tab); mCurrentTabIndex = position;
			 * 
			 * setCurrentTabAndClose();
			 * 
			 * } } }); convertView.setOnTouchListener(new OnTouchListener() {
			 * 
			 * @Override public boolean onTouch(View v, MotionEvent event) { if
			 * (event.getAction() == MotionEvent.ACTION_DOWN) {
			 * mOnTabItemHoverListener
			 * .onTabItemHover(mTabs.get(mList.getPositionForView(v))); } return
			 * false; } });
			 */
			return convertView;
		}
	}

	static private void debug(String msg) {
		Log.d(LOGTAG, msg);
	}

	public LinkedList<Tab> getTabs() {
		return mTabs;
	}

	public void setTabs(LinkedList<Tab> tabs) {
		mTabs = tabs;
	}

	class TabItemDragListener implements OnDragListener {
		int mTabIndex;

		public TabItemDragListener(int tabIndex) {
			mTabIndex = tabIndex;
		}

		@Override public boolean onDrag(View v, DragEvent event) {
			switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_ENTERED:

				mOnTabItemHoverListener.onTabItemHover(mTabs.get(getListView().getPositionForView(v)));
				mCurrentTabIndex = mTabIndex;
				mMainActivity.setTabChange(true);

				// mLastHoveredTabPosition = mList.getPositionForView(v);
				debug("CURRENT TAB: " + mCurrentTabIndex);
				break;
			case DragEvent.ACTION_DRAG_STARTED:
				// mDropping = false;
				break;
			case DragEvent.ACTION_DRAG_EXITED:
				break;
			case DragEvent.ACTION_DROP:
				// mDropping = true;
				// if (mInDragMode) {
				// Tab tab = mTabs.get(mList.getPositionForView(v));
				setCurrentTabAndClose();
				// mOnTabItemHoverListener.onDrop(tab);
				// }
				break;
			case DragEvent.ACTION_DRAG_ENDED:
				// if (!mDropping) {
				// if (mLastHoveredTabPosition != -1) {
				// setCurrentTabAndClose(mLastHoveredTabPosition);
				// mLastHoveredTabPosition = -1;
				// }
				// hide();
				// }
				break;
			default:
				break;
			}
			return true;
		}
	}

	interface OnTabItemHoverListener {
		public void onTabItemHover(Tab item);

		public void onDrop(Tab item);
	}
}
