package com.lqc.top;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.lqc.main.MySherlockActivity;
import com.lqc.main.MySherlockFragmentActivity;
import com.lqc.myquote.R;

public class TopActivity extends MySherlockFragmentActivity implements TabListener{
	
	LinearLayout mainLayout;
	FragmentTransaction transaction= null;
	private final String TITLE_TOP_VIEW = "Top view";
	private final String TITLE_TOP_LIKE = "Top like";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.top_activity);
		
		try{
			mainLayout = (LinearLayout)findViewById(R.id.layoutTop);
			transaction = getSupportFragmentManager().beginTransaction();
			ActionBar bar = getSupportActionBar();
			bar.addTab(bar.newTab().setText(TITLE_TOP_LIKE).setTabListener(this));
			
			bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM|ActionBar.DISPLAY_USE_LOGO);
			bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			bar.setDisplayShowHomeEnabled(false);
			bar.setDisplayShowTitleEnabled(false);
			bar.show();
		} catch(Exception e){
			
		}
		
	}

	public void executeFragment(Fragment fragment){
		try{
			mainLayout.removeAllViews();
			transaction.addToBackStack(null);
			transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(mainLayout.getId(), fragment);
			transaction.commit();
		} catch(Exception e){
			
		}
	}
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if (tab.getText().equals(TITLE_TOP_LIKE)){
			executeFragment(new TopLikeActivity());
		}
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
