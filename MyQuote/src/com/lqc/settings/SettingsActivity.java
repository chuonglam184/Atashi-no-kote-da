package com.lqc.settings;

import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.lqc.dto.MyLanguage;
import com.lqc.main.MySherlockActivity;
import com.lqc.myquote.R;

public class SettingsActivity extends MySherlockActivity{
	public static int text_size;
	public static String language;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);
	}
}
