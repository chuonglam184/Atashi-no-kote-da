package com.lqc.allquotes;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;
import com.lqc.database.MyAssetDatabase;
import com.lqc.main.MySherlockFragmentActivity;
import com.lqc.myquote.R;

public class AllQuotesFragmentActivity extends MySherlockFragmentActivity implements TabListener{
	private ViewPager viewPager;
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
		// set up view pager to display all quotes in database when user click All quotes in main menu.
		/*viewPager = (ViewPager)findViewById(R.id.pager);
		adapter = new QuoteSlidePagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);*/

		mainLayout = (RelativeLayout)findViewById(R.id.rlQuotesSlide);
		transaction = getSupportFragmentManager().beginTransaction();
		// NumPage is the number of paging group of quotes
		madb = new MyAssetDatabase(getApplicationContext());
		NUMPAGE = madb.getPageNumber();

		ActionBar bar = getSupportActionBar();
		for (int i=0; i<NUMPAGE; i++){
			String title = "Page " + String.valueOf(i+1) + "/" + String.valueOf(NUMPAGE);
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
		private final String[] TIT = new String[] { 
				"Title 1", 
				"Title 2"
		};

		public QuoteSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {

			return new QuoteFragment(arg0);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TIT[position];
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
		// TODO Auto-generated method stub

	}


	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}
}
