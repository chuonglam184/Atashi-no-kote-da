package com.lqc.authorsquotes;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lqc.MySharedPreferences.MySharedPreferences;
import com.lqc.dto.Quote;
import com.lqc.myquote.R;

public class AuthorsQuotesAdapter extends ArrayAdapter<Quote>{
	private Activity mContext;
	private int layout;
	private ArrayList<Quote> list;
	private MySharedPreferences msp;
	public AuthorsQuotesAdapter(Activity context, int resource,
			ArrayList<Quote> objects) {
		super(context, resource, objects);
		mContext = context;
		layout = resource;
		list = objects;
		msp = new MySharedPreferences(mContext.getApplicationContext());
	}

	@Override
	public Quote getItem(int position) {
		return list.get(position);
	}
	@Override
	public int getCount() {
		return this.list.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null){
			holder = new ViewHolder();
			LayoutInflater inflater = mContext.getLayoutInflater();
			convertView = inflater.inflate(layout, null);
			holder.layoutAuthorsQuotesItem = (LinearLayout)convertView.findViewById(R.id.layoutAuthorsQuotesItem);
			holder.ivBookmark = (ImageView) convertView.findViewById(R.id.ivBookmark);
			holder.tvQuoteContent = (TextView) convertView.findViewById(R.id.tvQuoteContent);
			Typeface font;
			font = Typeface.createFromAsset(mContext.getAssets(), "TIMESI.TTF");
			holder.tvQuoteContent.setTypeface(font, Typeface.ITALIC );
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		final Quote quote = list.get(position);
		if (quote!=null){
			if (position % 2 == 0)
				holder.layoutAuthorsQuotesItem.setBackgroundColor(mContext.getResources().getColor(R.color.silver_item));
			else
				holder.layoutAuthorsQuotesItem.setBackgroundColor(mContext.getResources().getColor(R.color.silver_item_less));
			int marked = msp.getBookmarkQuote(String.valueOf(quote.getQuoteId()));
			if (marked >=0){
				holder.isBookmarked = true;
				holder.ivBookmark.setImageResource(R.drawable.icon_bookmarked);
			} else {
				holder.ivBookmark.setImageResource(R.drawable.icon_bookmark);
				holder.isBookmarked = false;
			}
			
			holder.tvQuoteContent.setText(quote.getQuoteContent());
		}
		holder.ivBookmark.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				holder.isBookmarked = !holder.isBookmarked;
				if (holder.isBookmarked != true){
					holder.ivBookmark.setImageResource(R.drawable.icon_bookmark);
					msp.setBookmarkQuote(String.valueOf(quote.getQuoteId()), -1);
				} else {
					holder.ivBookmark.setImageResource(R.drawable.icon_bookmarked);
					msp.setBookmarkQuote(String.valueOf(quote.getQuoteId()), quote.getQuoteId());
				}
			}
		});
		return convertView;
	}

	static class ViewHolder{
		LinearLayout layoutAuthorsQuotesItem;
		ImageView ivBookmark;
		boolean isBookmarked;
		TextView tvQuoteContent;
	}
}
