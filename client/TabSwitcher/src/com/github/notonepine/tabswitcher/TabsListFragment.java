package com.github.notonepine.tabswitcher;

import java.util.LinkedList;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
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
    private ImageView mViewPreview;

    private LinkedList<Tab> mTabs;

    private Animation mAnimationFadeIn;
    private Animation mAnimationFadeOut;
    private Animation mAnimationMoveLeft;
    private Animation mAnimationMoveRight;

    private MainActivity mMainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	mMainActivity = (MainActivity) getActivity();

        mView = inflater.inflate(R.layout.tabs_list, container, false);
        mViewOverlay = mView.findViewById(R.id.preview_overlay);
        mViewPreview = (ImageView) mView.findViewById(R.id.preview);
        mTabs = new LinkedList<Tab>();
        mTabs.add(new Tab(R.drawable.thumbnail_mozilla,  "Francis Has Changed American Catholics Attitudes, but Not Their Behavior, a Poll Finds - NYTimes.com"));
        mTabs.add(new Tab(R.drawable.thumbnail_facebook, "Democrats in Senate Reject Pick by Obama - USAToday.com"));
        mTabs.add(new Tab(R.drawable.thumbnail_twitter,  "Home of the Mozilla Project Ñ Mozilla"));
        mTabs.add(new Tab(R.drawable.thumbnail_yelp,     "Google"));
        setListAdapter(new TabListAdapter(mMainActivity));

        mAnimationMoveLeft = AnimationUtils.loadAnimation(mMainActivity, R.anim.tabs_list_entrance);
        mAnimationMoveLeft.setAnimationListener(new Animation.AnimationListener() {
			@Override public void onAnimationStart(Animation animation)  {}
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
			@Override public void onAnimationStart(Animation animation)  {}
			@Override public void onAnimationRepeat(Animation animation) {}
			@Override public void onAnimationEnd(Animation animation) {
				mView.setVisibility(View.GONE);
			}
		});

        mAnimationMoveRight = AnimationUtils.loadAnimation(mMainActivity, R.anim.tabs_list_exit);
        mAnimationMoveRight.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation arg0)  {}
            @Override public void onAnimationRepeat(Animation arg0) {}
            @Override public void onAnimationEnd(Animation animation) {
            	getListView().setVisibility(View.INVISIBLE);
            	mViewOverlay.startAnimation(mAnimationFadeOut);
            }
        });

        mView.setOnDragListener(new OnDragListener() {
			@Override public boolean onDrag(View v, DragEvent event) {
				debug("DRAG ENTERED!!");
				return false;
			}
		});
        return mView;
    }

    public void toggleVisibility() {
        if (mView.isShown()) {
            transitionOut();
        } else {
            transitionIn();
        }
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
                image.setImageResource(tab.getResId());

                TextView titleView = (TextView) convertView.findViewById(R.id.title);
                titleView.setText(tab.getTitle());
            }

            //convertView.setOnDragListener(new TabItemDragListener(position));
            /*convertView.setOnClickListener(new View.OnClickListener() {
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
            });*/
            return convertView;
        }
    }

	static private void debug(String msg) {
		Log.d(LOGTAG, msg);
	}
}
