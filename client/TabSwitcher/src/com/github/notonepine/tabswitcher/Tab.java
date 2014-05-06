package com.github.notonepine.tabswitcher;

public class Tab {
	private int mThumbnailId;
	private int mPageId;
	private String mTitle;

	public Tab(int resId, String title, int pageId) {
		setThumbnailId(resId);
		setTitle(title);
		setPageId(pageId);

	}

	public int getThumbnailId() {
		return mThumbnailId;
	}

	public void setThumbnailId(int resId) {
		mThumbnailId = resId;
	}

	public int getPageId() {
		return mPageId;
	}

	public void setPageId(int resId) {
		mPageId = resId;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public String getTitle() {
		return mTitle;
	}
}
