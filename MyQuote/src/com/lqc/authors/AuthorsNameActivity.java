package com.lqc.authors;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.lqc.authorsquotes.AuthorsQuotesActivity;
import com.lqc.database.MyAssetDatabase;
import com.lqc.dto.Author;
import com.lqc.main.MySherlockActivity;
import com.lqc.myquote.R;

public class AuthorsNameActivity extends MySherlockActivity implements OnItemClickListener{
	ListView lvAuthor;
	ArrayList<Author> listAuthor;
	ArrayList<String> listAuthorName;
	MyAssetDatabase madb = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authors_name_activity);

		lvAuthor = (ListView)findViewById(R.id.lvAuthorName);
		madb = new MyAssetDatabase(getApplicationContext());
		listAuthor = new ArrayList<Author>();
		listAuthorName = new ArrayList<String>();
		listAuthor = madb.getAllAuthor();
		listAuthorName = madb.getAllAuthorName();
		AuthorAdapter adapter = new AuthorAdapter(AuthorsNameActivity.this, R.layout.author_name_list_item, listAuthor);
		lvAuthor.setAdapter(adapter);
		lvAuthor.setOnItemClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.authors_name_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.item_authors_name_search:
			ActionBar actionBar = getSupportActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowCustomEnabled(true);
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setIcon(R.drawable.action_search);
			LayoutInflater inflator = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflator.inflate(R.layout.search_bar, null);
			actionBar.setCustomView(v);
			ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listAuthorName);
			final AutoCompleteTextView textView = (AutoCompleteTextView)v.findViewById(R.id.etSearch);
			textView.setFocusable(true);
			textView.setAdapter(adapter1);
			textView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					String text = textView.getText().toString();
					int position = listAuthorName.indexOf(text);
					lvAuthor.setSelection(position);
				}
			});
			break;

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Author author = listAuthor.get(arg2);
		int author_id = author.getAuthorId();
		Bundle b = new Bundle();
		b.putInt("author_id", author_id);
		b.putString("author_name", listAuthorName.get(arg2));
		Intent i = new Intent(getApplicationContext(), AuthorsQuotesActivity.class);
		i.putExtra("bundle", b);
		startActivity(i);
	}
}
