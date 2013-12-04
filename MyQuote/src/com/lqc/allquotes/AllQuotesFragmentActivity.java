package com.lqc.allquotes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.lqc.database.MyAssetDatabase;
import com.lqc.main.MySherlockFragmentActivity;
import com.lqc.myquote.R;

public class AllQuotesFragmentActivity extends MySherlockFragmentActivity{
	private ViewPager viewPager;
	public static int NUMPAGE;
	QuoteSlidePagerAdapter adapter;
	private MyAssetDatabase madb;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.quote_slide_pager);
		viewPager = (ViewPager)findViewById(R.id.pager);
		adapter = new QuoteSlidePagerAdapter(getSupportFragmentManager());
		madb = new MyAssetDatabase(getApplicationContext());
		NUMPAGE = madb.getPageNumber();
		viewPager.setAdapter(adapter);
			
	}
	
	private class QuoteSlidePagerAdapter extends FragmentStatePagerAdapter{

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
}
