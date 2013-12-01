package com.lqc.authorsquotes;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.lqc.database.MyAssetDatabase;
import com.lqc.dto.Quote;
import com.lqc.main.MySherlockActivity;
import com.lqc.myquote.R;

public class AuthorsQuotesActivity extends MySherlockActivity{
	private int author_id;
	private ArrayList<Quote> listQuote;
	private MyAssetDatabase madb = null;
	private ListView lvAuthorsQuotes;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authors_quotes_activity);
		
		Bundle b = getIntent().getBundleExtra("bundle");
		int author_id = b.getInt("author_id");
		String author_name = b.getString("author_name");
		ActionBar bar = getSupportActionBar();
		bar.setTitle(author_name);
		madb = new MyAssetDatabase(getApplicationContext());
		listQuote = new ArrayList<Quote>();
		listQuote = madb.getListQuoteByAuthorId(author_id);
		lvAuthorsQuotes = (ListView)findViewById(R.id.lvAuthorsQuotes);
		AuthorsQuotesAdapter adapter = new AuthorsQuotesAdapter(AuthorsQuotesActivity.this, R.layout.authors_quotes_list_item, listQuote);
		lvAuthorsQuotes.setAdapter(adapter);
	}
}
