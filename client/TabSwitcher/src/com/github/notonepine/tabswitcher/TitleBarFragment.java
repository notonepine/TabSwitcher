package com.github.notonepine.tabswitcher;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class TitleBarFragment extends Fragment {

	View mView;
	View titlebar;
	View toolbar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.title_bar, container, false);

		titlebar = mView.findViewById(R.id.titlebar);
		toolbar = mView.findViewById(R.id.toolbar);
		toolbar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				animateTitlebar(titlebar.getTranslationX() < 0);
			}
		});

		return mView;
	}

	// TODO: move this animation somewhere else
	private void animateTitlebar(boolean in) {
		ObjectAnimator anim;
		if (in) {
			anim = ObjectAnimator.ofFloat(titlebar, "translationX", 0);
		} else {
			float pixels = ViewUtils.dpToPx(getActivity(), -250);
			anim = ObjectAnimator.ofFloat(titlebar, "translationX", pixels);
		}
		anim.setDuration(200);
		anim.start();
	}

}
