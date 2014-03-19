package com.github.notonepine.tabswitcher;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class TitleBarFragment extends Fragment {
	private View mView;
    private View mTitlebar;
    private View mToolbar;
    private MainActivity mMainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	mMainActivity = (MainActivity) getActivity();
        mView = inflater.inflate(R.layout.title_bar, container, false);

        mTitlebar = mView.findViewById(R.id.titlebar);
        mToolbar = mView.findViewById(R.id.toolbar);
        mToolbar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	toggleTabSwitcherMode(mTitlebar.getTranslationX() < 0);
            }
        });

        return mView;
    }
    
    // TODO: move this animation somewhere else
    private void toggleTabSwitcherMode(boolean in) {
    	mMainActivity.toggleTabList(in);
        ObjectAnimator anim;
        if (in) {
            anim = ObjectAnimator.ofFloat(mTitlebar, "translationX", 0);
        } else {
            float pixels = ViewUtils.dpToPx(getActivity(), -250);
            anim = ObjectAnimator.ofFloat(mTitlebar, "translationX", pixels);
        }
        anim.setDuration(200);
        anim.start();
    }
}
