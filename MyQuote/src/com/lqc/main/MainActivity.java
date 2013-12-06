package com.lqc.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.lqc.allquotes.AllQuotesFragmentActivity;
import com.lqc.authors.AuthorsNameActivity;
import com.lqc.favourites.FavouritesActivity;
import com.lqc.myquote.R;
import com.lqc.settings.SettingsActivity;

public class MainActivity extends MySherlockActivity implements OnClickListener {

	Button bAuthors, bQuotes, bShareApp, bPopular, bFavorites, bRate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		bAuthors = (Button)findViewById(R.id.bAuthors);
		bQuotes = (Button)findViewById(R.id.bQuotes);
		bShareApp = (Button)findViewById(R.id.bShareApp);
		bPopular = (Button)findViewById(R.id.bPopular);
		bFavorites = (Button)findViewById(R.id.bFavorites);
		bRate = (Button)findViewById(R.id.bRate);

		bAuthors.setOnClickListener(this);
		bQuotes.setOnClickListener(this);
		bShareApp.setOnClickListener(this);
		bPopular.setOnClickListener(this);
		bFavorites.setOnClickListener(this);
		bRate.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.i_main_settings:
			Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
			startActivity(i);
			break;
		}
		return true;
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()){
		case R.id.bAuthors:
			Intent authors = new Intent(getApplicationContext(), AuthorsNameActivity.class);
			startActivity(authors);
			break;
		case R.id.bQuotes:
			Intent quotes = new Intent(getApplicationContext(), AllQuotesFragmentActivity.class); 
			startActivity(quotes);
			break;
		case R.id.bShareApp:
			
			break;
		case R.id.bPopular:
			
			break;
		case R.id.bFavorites:
			Intent favourites = new Intent(getApplicationContext(), FavouritesActivity.class);
			startActivity(favourites);
			break;
		case R.id.bRate:
			Toast.makeText(getApplicationContext(), "Thank you for rating us!", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK){
			exit();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void exit() {
		AlertDialog.Builder b=new AlertDialog.Builder(MainActivity.this);
		b.setTitle("Warning");
		b.setMessage("Are you sure you want to exit?");
		b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				finish();
			}
		});
		b.setNegativeButton("No", new DialogInterface. OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.cancel();
			}
		});
		b.create().show();
	}

}
