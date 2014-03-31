package com.notonepine.tabswitcher;

public class Tab {
	private int mResId;
	private String mTitle;

	public Tab(int resId, String title) {
		setResId(resId);
		setTitle(title);
	}

	public int getResId() {
		return mResId;
	}

	public void setResId(int resId) {
		mResId = resId;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public String getTitle() {
		return mTitle;
	}
}
