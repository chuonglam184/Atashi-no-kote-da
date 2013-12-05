package com.lqc.authorsquotes;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.lqc.main.MySherlockFragmentActivity;
import com.lqc.myquote.R;

public class AuthorsQuotesFragmentActivity extends MySherlockFragmentActivity{
	
	private int num_quote = 0;
	private String author_name;
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
		author_name = getIntent().getBundleExtra("bundle").getString("author_name");
		selectedIndex = getIntent().getBundleExtra("bundle").getInt("selectedIndex");
		
		// set action bar's title
		getSupportActionBar().setTitle(author_name);
		
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
