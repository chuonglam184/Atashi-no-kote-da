package com.lqc.top;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.TextView;

import com.lqc.authorsquotes.AuthorsQuotesAdapter;
import com.lqc.myquote.R;

public class TopViewActivity extends Activity{
	ListView lvQuotes;
	AuthorsQuotesAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_quotes_activity);
		TextView  tv=new TextView(this);
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setText("This Is Tab1 Activity");
        
        setContentView(tv);
	}
}
