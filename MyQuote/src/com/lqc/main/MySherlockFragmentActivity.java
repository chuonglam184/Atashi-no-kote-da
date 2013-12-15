package com.lqc.main;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.lqc.myquote.R;

public class MySherlockFragmentActivity extends SherlockFragmentActivity{
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		ActionBar bar = getSupportActionBar();
		//bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue_bar)));
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bar));
	}
}
