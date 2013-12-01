package com.lqc.main;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.lqc.myquote.R;

public class MySherlockActivity extends SherlockActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar bar = getSupportActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green_action_bar)));
	}
}
