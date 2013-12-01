package com.lqc.authorsquotes;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lqc.dto.Quote;
import com.lqc.myquote.R;

public class AuthorsQuotesAdapter extends ArrayAdapter<Quote>{
	private Activity mContext;
	private int layout;
	private ArrayList<Quote> list;
	public AuthorsQuotesAdapter(Activity context, int resource,
			ArrayList<Quote> objects) {
		super(context, resource, objects);
		mContext = context;
		layout = resource;
		list = objects;
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
		ViewHolder holder;
		if (convertView == null){
			holder = new ViewHolder();
			LayoutInflater inflater = mContext.getLayoutInflater();
			convertView = inflater.inflate(layout, null);
			holder.layoutAuthorsQuotesItem = (LinearLayout)convertView.findViewById(R.id.layoutAuthorsQuotesItem);
			holder.ivBookmark = (ImageView) convertView.findViewById(R.id.ivBookmark);
			holder.tvQuoteContent = (TextView) convertView.findViewById(R.id.tvQuoteContent);
			
			
			//holder.ivBookmark.setImageResource(R.drawable.icon_bookmark);
			Typeface font;
			font = Typeface.createFromAsset(mContext.getAssets(), "TIMESI.TTF");
			holder.tvQuoteContent.setTypeface(font, Typeface.ITALIC );
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		Quote quote = list.get(position);
		if (quote!=null){
			if (position%2==0)
				holder.layoutAuthorsQuotesItem.setBackgroundColor(mContext.getResources().getColor(R.color.silver_item));
			else
				holder.layoutAuthorsQuotesItem.setBackgroundColor(mContext.getResources().getColor(R.color.silver_item_less));
			holder.ivBookmark.setImageResource(R.drawable.icon_bookmark);
			holder.tvQuoteContent.setText(quote.getQuoteContent());
			
		}
		return convertView;
	}
	
	static class ViewHolder{
		LinearLayout layoutAuthorsQuotesItem;
		ImageView ivBookmark;
		TextView tvQuoteContent;
	}
}
