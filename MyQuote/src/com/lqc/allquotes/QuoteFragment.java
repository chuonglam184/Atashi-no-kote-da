package com.lqc.allquotes;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.lqc.authorsquotes.AuthorsQuotesAdapter;
import com.lqc.authorsquotes.AuthorsQuotesFragmentActivity;
import com.lqc.database.MyAssetDatabase;
import com.lqc.dto.Quote;
import com.lqc.myquote.R;

@SuppressLint("ValidFragment")
public class QuoteFragment extends Fragment implements OnItemClickListener{

	private ListView lvQuotes;
	private ArrayList<Quote> listQuotes;
	private AuthorsQuotesAdapter adapter;
	private int page_index;
	private MyAssetDatabase madb;

	public QuoteFragment(){

	}

	public QuoteFragment(int page){
		this.page_index = page;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// setup rootview
		ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.authors_quotes_activity, container, false);

		// setup listview
		lvQuotes = (ListView)rootView.findViewById(R.id.lvAuthorsQuotes);

		// get data from database for displaying in listview
		madb = new MyAssetDatabase(getActivity().getApplicationContext());
		listQuotes = new ArrayList<Quote>();
		listQuotes = madb.getAllQuoteByPageIndex(page_index);
		Log.d("listQuotes", String.valueOf(listQuotes.size()));
		adapter = new AuthorsQuotesAdapter(getActivity(),R.layout.authors_quotes_list_item , listQuotes);
		lvQuotes.setAdapter(adapter);

		// event when user click an item in listview
		lvQuotes.setOnItemClickListener(this);
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		ArrayList<Integer> listQuoteId = new ArrayList<Integer>();
		for (int i=0; i<listQuotes.size(); i++)
			listQuoteId.add(listQuotes.get(i).getQuoteId());

		Intent i = new Intent(getActivity().getApplicationContext(), AuthorsQuotesFragmentActivity.class);
		Bundle b = new Bundle();
		b.putIntegerArrayList("listQuoteId", listQuoteId);
		b.putString("author_name", "Quotes");
		b.putInt("selectedIndex", arg2);
		i.putExtra("bundle", b);
		startActivity(i);
	}
}
