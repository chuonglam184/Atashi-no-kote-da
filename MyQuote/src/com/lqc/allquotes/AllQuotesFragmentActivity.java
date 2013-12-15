package com.lqc.allquotes;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.lqc.database.MyAssetDatabase;
import com.lqc.main.MySherlockFragmentActivity;
import com.lqc.myquote.R;

public class AllQuotesFragmentActivity extends MySherlockFragmentActivity implements TabListener{
	public static int NUMPAGE;
	QuoteSlidePagerAdapter adapter;
	private MyAssetDatabase madb;
	RelativeLayout mainLayout;
	FragmentTransaction transaction;
	private ArrayList<String> listTitle;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.quote_slide_pager);

		listTitle = new ArrayList<String>();

		mainLayout = (RelativeLayout)findViewById(R.id.rlQuotesSlide);
		transaction = getSupportFragmentManager().beginTransaction();
		
		// NumPage is the number of paging quotes
		madb = new MyAssetDatabase(getApplicationContext());
		NUMPAGE = madb.getPageNumber();

		// Setup title bar
		ActionBar bar = getSupportActionBar();
		for (int i=0; i<NUMPAGE; i++){
			String title = String.valueOf(200*i+1)+"-"+String.valueOf(200*(i+1));
			listTitle.add(title);
			bar.addTab(bar.newTab().setText(title).setTabListener(this));
		}
		bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM|ActionBar.DISPLAY_USE_LOGO);
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setDisplayShowHomeEnabled(false);
		bar.setDisplayShowTitleEnabled(false);
		bar.show();
	}


	public static class QuoteSlidePagerAdapter extends FragmentStatePagerAdapter{

		public QuoteSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return new QuoteFragment(arg0);
		}

		@Override
		public int getCount() {
			return NUMPAGE;
		}
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		for (int i=0; i<NUMPAGE;i++){
			if (tab.getPosition() == i){
				executeFragment(new QuoteFragment(i));
			}
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
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}
}
