package com.github.notonepine.tabswitcher;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class ToolBarAnimator {

	/** Distance the url bar moves to the side. **/
	private final int BAR_ANIM_DIST = -250;

	/** Distance the new tab button moves off the screen. **/
	private final int TAB_ANIM_DIST = -55;

	/** Duration of the bar animation **/
	private final int BAR_ANIM_DURATION = 200;

	/** Duration of fade of side buttons **/
	private final int BUTTON_FADE_DURATION = 100;

	/** Duration of slide of new tab button **/
	private final int TAB_BUTTON_DURATION = 100;

	/** Offsets. These animations do not begin immediately **/
	private final int FADE_OFFSET = 100;
	private final int TRANSLATE_OFFSET = 100;

	/** The animated views **/
	private View mTitleBar;
	private View mRightButtons;
	private View mNewTabButton;

	/** Local reference to context **/
	private Context mContext;

	/** Whether or not the tab switcher is open **/
	private boolean mOpen;

	/** Distances in actual values (not pixels) of animation translations **/
	private float mBarDistance;
	private float mTabDistance;

	/** All animations **/
	private Animation barAnimIn, barAnimOut, buttonsAnimIn, buttonsAnimOut,
			newTabButtonAnimIn, newTabButtonAnimOut;

	public ToolBarAnimator(View titleBar, View rightButtons, View newTabButton,
			Context context, boolean open) {
		mTitleBar = titleBar;
		mRightButtons = rightButtons;
		mNewTabButton = newTabButton;
		mContext = context;
		mOpen = open;

		//Convert our distances from dp to pixels
		mBarDistance = ViewUtils.dpToPx(mContext, BAR_ANIM_DIST);
		mTabDistance = ViewUtils.dpToPx(mContext, TAB_ANIM_DIST);

		// Define animations here, so we don't instantiate them every time we call toggle.
		barAnimIn = new TranslateAnimation(0, mBarDistance, 0, 0);
		barAnimOut = new TranslateAnimation(mBarDistance, 0, 0, 0);
		buttonsAnimIn = new AlphaAnimation(1.0f, 0);
		buttonsAnimOut = new AlphaAnimation(0, 1.0f);
		newTabButtonAnimIn = new TranslateAnimation(0, mTabDistance, 0, 0);
		newTabButtonAnimOut = new TranslateAnimation(mTabDistance, 0, 0, 0);
		

	}

	public void toggleToolbarState() {
		Animation barAnim;
		Animation tabAnim;
		Animation fAnim;

		if (!mOpen) {
			tabAnim = newTabButtonAnimIn;
			barAnim = barAnimIn;
			fAnim = buttonsAnimIn;
			tabAnim.setStartOffset(TRANSLATE_OFFSET);

		} else {
			tabAnim = newTabButtonAnimOut;
			barAnim = barAnimOut;
			fAnim = buttonsAnimOut;

			fAnim.setStartOffset(FADE_OFFSET);
		}

		barAnim.setFillAfter(true);
		barAnim.setDuration(BAR_ANIM_DURATION);

		fAnim.setFillAfter(true);
		fAnim.setDuration(BUTTON_FADE_DURATION);

		tabAnim.setFillAfter(true);
		tabAnim.setDuration(TAB_BUTTON_DURATION);

		mNewTabButton.startAnimation(tabAnim);
		mTitleBar.startAnimation(barAnim);
		mRightButtons.startAnimation(fAnim);

		mOpen = !mOpen;
	}

	public void setState(boolean open) {
		mOpen = open;
	}

}
