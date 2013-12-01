package com.lqc.authors_details;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.lqc.database.MyAssetDatabase;
import com.lqc.dto.Author;
import com.lqc.main.MySherlockActivity;
import com.lqc.myquote.R;

public class AuthorDetailsActivity extends MySherlockActivity implements OnClickListener{
	private Author author;
	private int author_id;
	private ImageView ivAvatar;
	private TextView tvName, tvDescription;
	private Button bMoreDetails;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.author_details_activity);
		
		author_id = getIntent().getBundleExtra("bundle").getInt("author_id");
		
		Typeface font = Typeface.createFromAsset(getAssets(), "TIMESI.TTF");
		
		MyAssetDatabase madb = new MyAssetDatabase(getApplicationContext());
		author = madb.getAuthorById(author_id);
		ivAvatar = (ImageView)findViewById(R.id.ivAuthorAvatar);
		tvName= (TextView)findViewById(R.id.tvAuthorName);
		tvDescription = (TextView)findViewById(R.id.tvAuthorDescription);
		tvDescription.setTypeface(font);
		bMoreDetails = (Button)findViewById(R.id.bAuthoreMoreInformation);
		
		ActionBar bar = getSupportActionBar();
		bar.setTitle(author.getAuthorName());
		ivAvatar.setImageBitmap(author.getAuthorImage());
		tvName.setText(author.getAuthorName());
		tvDescription.setText(author.getAuthorDescription());
		bMoreDetails.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		
		
	}
}
