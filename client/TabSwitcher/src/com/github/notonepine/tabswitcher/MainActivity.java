package com.github.notonepine.tabswitcher;

import java.util.LinkedList;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private View mToolbar;
	private View mTitlebar;
	private ListView mListView;
	private LinkedList<Tab> mTabs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_main);

		mTitlebar = findViewById(R.id.titlebar);
		mToolbar = findViewById(R.id.toolbar);
		mToolbar.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				animateTitlebar(mTitlebar.getTranslationX() < 0);
			}
		});

		mTabs = new LinkedList<Tab>();
		for (int i = 0; i < 1; i++) {
			mTabs.add(new Tab(R.drawable.screen_mozilla, R.drawable.tabs_count_foreground,
					"Francis Has Changed American CatholicsÕ Attitudes, but Not Their Behavior, a Poll Finds - NYTimes.com"));
			mTabs.add(new Tab(R.drawable.screen_facebook, R.drawable.tabs_count_foreground,
					"Democrats in Senate Reject Pick by Obama - USAToday.com"));
			mTabs.add(new Tab(R.drawable.screen_yelp, R.drawable.tabs_count_foreground, "Home of the Mozilla Project Ñ Mozilla"));
			mTabs.add(new Tab(R.drawable.screen_sosh, R.drawable.tabs_count_foreground, "Google"));
		}
		
		mListView = (ListView) findViewById(R.id.tabslist);
		mListView.setAdapter(new TabListAdapter(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	// TODO: move this animation somewhere else
	private void animateTitlebar(boolean in) {
		ObjectAnimator anim;
		if (in) {
			anim = ObjectAnimator.ofFloat(mToolbar, "translationX", 0);
		} else {
			float pixels = ViewUtils.dpToPx(this, -250);
			anim = ObjectAnimator.ofFloat(mTitlebar, "translationX", pixels);
		}
		anim.setDuration(200);
		anim.start();
	}

	class TabListAdapter extends ArrayAdapter<Tab> {
		public TabListAdapter(Context context) {
			super(context, R.layout.switcher_list_item, mTabs);
		}

		@Override public View getView(final int position, View convertView, ViewGroup parent) {
			final Tab tab = mTabs.get(position);
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.switcher_list_item, null);
			}

			ImageView favicon = (ImageView) convertView.findViewById(R.id.favicon);

			ImageView image = (ImageView) convertView.findViewById(R.id.thumbnail);

			favicon.setVisibility(View.VISIBLE);
			image.setImageResource(tab.getResId());

			favicon.setImageResource(tab.getFaviconId());
			TextView titleView = (TextView) convertView.findViewById(R.id.title);
			titleView.setText(tab.getTitle());
			/*convertView.setOnDragListener(new TabItemDragListener(position));
			convertView.setOnClickListener(new OnClickListener() {
				@Override public void onClick(View v) {
					if (!mInDragMode) {
						mOnTabItemHoverListener.onDrop(tab);
						mCurrentTabIndex = position;

						setCurrentTabAndClose();

					}
				}
			});
			convertView.setOnTouchListener(new OnTouchListener() {
				@Override public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						mOnTabItemHoverListener.onTabItemHover(mTabs.get(mList.getPositionForView(v)));
					}
					return false;
				}
			});
			*/
			return convertView;
		}
	}
}
