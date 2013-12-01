package com.lqc.allquotes;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lqc.authorsquotes.AuthorsQuotesAdapter;
import com.lqc.database.MyAssetDatabase;
import com.lqc.dto.Quote;
import com.lqc.myquote.R;

@SuppressLint("ValidFragment")
public class QuoteFragment extends Fragment{
	
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
		ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.authors_quotes_activity, container, false);
		lvQuotes = (ListView)rootView.findViewById(R.id.lvAuthorsQuotes);
		madb = new MyAssetDatabase(getActivity().getApplicationContext());
		listQuotes = new ArrayList<Quote>();
		listQuotes = madb.getAllQuoteByPageIndex(page_index);
		adapter = new AuthorsQuotesAdapter(getActivity(),R.layout.authors_quotes_list_item , listQuotes);
		lvQuotes.setAdapter(adapter);
		
		return rootView;
	}
}
