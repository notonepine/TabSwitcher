package com.github.notonepine.tabswitcher;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;

public class ToolBarAnimator {

	/** Distance the url bar moves to the side. **/
	private final int BAR_ANIM_DIST = -250;

	/** Distance the new tab button moves off the screen. **/
	private final int TAB_ANIM_DIST = -55;

	/** Duration of the bar animation **/
	private final int BAR_ANIM_DURATION = 80;

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
	private ObjectAnimator barAnimIn, barAnimOut, buttonsAnimIn, buttonsAnimOut, newTabButtonAnimIn,
			newTabButtonAnimOut;

	public ToolBarAnimator(View titleBar, View rightButtons, View newTabButton, Context context, boolean open) {
		mTitleBar = titleBar;
		mRightButtons = rightButtons;
		mNewTabButton = newTabButton;
		mContext = context;
		mOpen = open;

		// Convert our distances from dp to pixels
		mBarDistance = ViewUtils.dpToPx(mContext, BAR_ANIM_DIST);
		mTabDistance = ViewUtils.dpToPx(mContext, TAB_ANIM_DIST);

		new ObjectAnimator();
		// Define animations here, so we don't instantiate them every time we
		// call toggle.
		barAnimIn = ObjectAnimator.ofFloat(mTitleBar, "translationX", 0, mBarDistance).setDuration(BAR_ANIM_DURATION);

		barAnimOut = ObjectAnimator.ofFloat(mTitleBar, "translationX", mBarDistance, 0).setDuration(BAR_ANIM_DURATION);

		buttonsAnimIn = ObjectAnimator.ofFloat(mRightButtons, "alpha", 1.0f, 0).setDuration(BUTTON_FADE_DURATION);

		buttonsAnimOut = ObjectAnimator.ofFloat(mRightButtons, "alpha", 0, 1.0f).setDuration(BUTTON_FADE_DURATION);

		newTabButtonAnimIn = ObjectAnimator.ofFloat(mNewTabButton, "translationX", 0, mTabDistance).setDuration(
				TAB_BUTTON_DURATION);

		newTabButtonAnimOut = ObjectAnimator.ofFloat(mNewTabButton, "translationX", mTabDistance, 0).setDuration(
				TAB_BUTTON_DURATION);
	}

	public void toggleToolbarState() {
		ObjectAnimator barAnim;
		ObjectAnimator tabAnim;
		ObjectAnimator fAnim;

		if (!mOpen) {
			tabAnim = newTabButtonAnimIn;
			barAnim = barAnimIn;
			fAnim = buttonsAnimIn;
			tabAnim.setStartDelay(TRANSLATE_OFFSET);

		} else {
			tabAnim = newTabButtonAnimOut;
			barAnim = barAnimOut;
			fAnim = buttonsAnimOut;

			fAnim.setStartDelay(FADE_OFFSET);
		}

		tabAnim.start();
		barAnim.start();
		fAnim.start();

		mOpen = !mOpen;
	}

	public void setState(boolean open) {
		mOpen = open;
	}
}
