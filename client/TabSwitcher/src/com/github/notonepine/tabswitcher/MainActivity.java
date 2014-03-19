package com.github.notonepine.tabswitcher;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	View titlebar;
	View toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_main);

		titlebar = findViewById(R.id.titlebar);
		toolbar = findViewById(R.id.toolbar);
		toolbar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				animateTitlebar(titlebar.getTranslationX() < 0);
			}
		});
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
			anim = ObjectAnimator.ofFloat(titlebar, "translationX", 0);
		} else {
			float pixels = ViewUtils.dpToPx(this, -250);
			anim = ObjectAnimator.ofFloat(titlebar, "translationX", pixels);
		}
		anim.setDuration(200);
		anim.start();
	}
}
