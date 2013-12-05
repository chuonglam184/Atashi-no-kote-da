package com.lqc.favourites;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ListView;

import com.lqc.authorsquotes.AuthorsQuotesAdapter;
import com.lqc.dto.Quote;
import com.lqc.main.MySherlockActivity;
import com.lqc.myquote.R;

public class FavouritesActivity extends MySherlockActivity{
	private ListView lvFavourites;
	private ArrayList<Quote> listBookmarkedQuotes;
	private AuthorsQuotesAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favourites_activity);
		
		lvFavourites = (ListView)findViewById(R.id.lvFavourites);
		
	}
}
