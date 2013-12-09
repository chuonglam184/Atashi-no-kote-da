package com.lqc.top;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.actionbarsherlock.view.Menu;
import com.lqc.main.MySherlockActivity;
import com.lqc.myquote.R;

public class TopActivity extends MySherlockActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.top_activity);
		
		TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
		
		TabSpec tabTopView = tabHost.newTabSpec("Top view");
		TabSpec tabTopLike = tabHost.newTabSpec("Top like");
		/*
		tabTopView.setIndicator("Top view");
		tabTopView.setContent(new Intent(this, TopViewActivity.class));
		
		tabTopLike.setIndicator("Top like");
		tabTopLike.setContent(new Intent(this, TopLikeActivity.class));
		
		tabHost.addTab(tabTopView);
		tabHost.addTab(tabTopLike);*/
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_top, menu);
		return true;
	}
}
