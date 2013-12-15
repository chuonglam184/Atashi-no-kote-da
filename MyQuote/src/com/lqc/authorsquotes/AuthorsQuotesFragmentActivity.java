package com.lqc.authorsquotes;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.lqc.myquote.R;

public class AuthorsQuotesFragmentActivity extends FragmentActivity{
	
	private int num_quote = 0;
	private int selectedIndex = 0;
	private ArrayList<Integer> listQuoteId;
	
	private ViewPager vp;
	private AuthorsQuoteSlidePagerAdapter adapter;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		// get intent
		listQuoteId = new ArrayList<Integer>();
		listQuoteId = getIntent().getBundleExtra("bundle").getIntegerArrayList("listQuoteId");
		selectedIndex = getIntent().getBundleExtra("bundle").getInt("selectedIndex");
		
		// size of view pager
		num_quote = listQuoteId.size();
		
		// set content view
		setContentView(R.layout.quote_slide_pager);
		
		// setup viewpager
		vp = (ViewPager)findViewById(R.id.pager);
		adapter = new AuthorsQuoteSlidePagerAdapter(getSupportFragmentManager());
		vp.setAdapter(adapter);
		vp.setCurrentItem(selectedIndex);
	}

	private class AuthorsQuoteSlidePagerAdapter extends FragmentStatePagerAdapter{

		public AuthorsQuoteSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}
		
		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition((AuthorsQuotesFragment)object);
		}
		
		@Override
		public Fragment getItem(int arg0) {
			return new AuthorsQuotesFragment(listQuoteId.get(arg0));
		}

		@Override
		public int getCount() {
			return num_quote;
		}
		
	}
}
