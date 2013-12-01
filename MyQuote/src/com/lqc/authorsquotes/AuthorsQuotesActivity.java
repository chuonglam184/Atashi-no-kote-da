package com.lqc.authorsquotes;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.lqc.MySharedPreferences.MySharedPreferences;
import com.lqc.authors_details.AuthorDetailsActivity;
import com.lqc.database.MyAssetDatabase;
import com.lqc.dto.Author;
import com.lqc.dto.Quote;
import com.lqc.main.MySherlockActivity;
import com.lqc.myquote.R;

public class AuthorsQuotesActivity extends MySherlockActivity implements OnItemClickListener {
	private Author author;
	private int author_id;
	private String author_name;
	private ArrayList<Quote> listQuote;
	private MyAssetDatabase madb = null;
	private ListView lvAuthorsQuotes;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authors_quotes_activity);
		
		Bundle b = getIntent().getBundleExtra("bundle");
		
		author_name = b.getString("author_name");
		author_id = b.getInt("author_id");
		
		ActionBar bar = getSupportActionBar();
		bar.setTitle(author_name);
		madb = new MyAssetDatabase(getApplicationContext());
		listQuote = new ArrayList<Quote>();
		listQuote = madb.getListQuoteByAuthorId(author_id);
		lvAuthorsQuotes = (ListView)findViewById(R.id.lvAuthorsQuotes);
		AuthorsQuotesAdapter adapter = new AuthorsQuotesAdapter(AuthorsQuotesActivity.this, R.layout.authors_quotes_list_item, listQuote);
		lvAuthorsQuotes.setAdapter(adapter);
		//lvAuthorsQuotes.setOnItemClickListener(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.authors_quotes_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.item_author_details:
			Intent i = new Intent(getApplicationContext(), AuthorDetailsActivity.class);
			Bundle b = new Bundle();
			b.putInt("author_id", author_id);
			i.putExtra("bundle", b);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private boolean is = false;
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		final Quote quote = listQuote.get(arg2);	
	}

}
