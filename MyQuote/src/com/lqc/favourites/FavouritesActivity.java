package com.lqc.favourites;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.lqc.authorsquotes.AuthorsQuotesAdapter;
import com.lqc.authorsquotes.AuthorsQuotesFragmentActivity;
import com.lqc.database.MyAssetDatabase;
import com.lqc.dto.Quote;
import com.lqc.main.MySherlockActivity;
import com.lqc.myquote.R;

public class FavouritesActivity extends MySherlockActivity implements OnItemClickListener{
	private ListView lvFavourites;
	private ArrayList<Quote> listBookmarkedQuotes;
	private AuthorsQuotesAdapter adapter;
	private MyAssetDatabase madb ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favourites_activity);

		madb = new MyAssetDatabase(getApplicationContext());
		int size = madb.getNumOfQuotes();
		Log.d("database size", String.valueOf(size));

		madb = new MyAssetDatabase(getApplicationContext());
		lvFavourites = (ListView)findViewById(R.id.lvFavourites);
		listBookmarkedQuotes = new ArrayList<Quote>();
		listBookmarkedQuotes = madb.getBookmarkedQuotes();
		Log.d("size", String.valueOf(listBookmarkedQuotes.size()));
		adapter = new AuthorsQuotesAdapter(FavouritesActivity.this, R.layout.authors_quotes_list_item, listBookmarkedQuotes);
		lvFavourites.setAdapter(adapter);
		lvFavourites.setOnItemClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i=0; i<listBookmarkedQuotes.size();i++){
			list.add(listBookmarkedQuotes.get(i).getQuoteId());
		}
		Intent i = new Intent(getApplicationContext(), AuthorsQuotesFragmentActivity.class);
		Bundle b = new Bundle();
		b.putIntegerArrayList("listQuoteId", list);
		b.putInt("selectedIndex", arg2);
		b.putString("author_name", "Favourites");
		i.putExtra("bundle", b);
		startActivity(i);
	}
}
