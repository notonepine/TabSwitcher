package com.github.notonepine.tabswitcher;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class TitleBarFragment extends Fragment {
    private boolean mSwitching = false;

    private View mView;
    private View toolbar;
    private MainActivity mMainActivity;
    private ToolBarAnimator mToolBarAnimator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mMainActivity = (MainActivity) getActivity();
        mView = inflater.inflate(R.layout.title_bar, container, false);

        toolbar = mView.findViewById(R.id.toolbar);

        mToolBarAnimator = new ToolBarAnimator(
                                /* titleBar */ mView.findViewById(R.id.titlebar),
                                /* rightButtons */ mView.findViewById(R.id.right_titlebar_buttons),
                                /* newTabButton */ mView.findViewById(R.id.new_tab),
                                /* context */ getActivity(),
                                /* open */ mSwitching
                            );

        toolbar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mToolBarAnimator.toggleToolbarState();
                mMainActivity.toggleTabListVisibility();
                makeHapticFeedback();
                mSwitching = !mSwitching;
            }
        });

        return mView;
    }

    public boolean isSwitching() {
        return mSwitching;
    }

    private void makeHapticFeedback() {
        final long[] vibrationPattern = { 0L, 18L };
        final Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(vibrationPattern, -1);
    }
}
