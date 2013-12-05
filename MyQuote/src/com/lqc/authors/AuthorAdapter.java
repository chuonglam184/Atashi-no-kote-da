package com.lqc.authors;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lqc.dto.Author;
import com.lqc.myquote.R;

public class AuthorAdapter extends ArrayAdapter<Author>{
	public AuthorAdapter(Activity context, int resource, ArrayList<Author> objects) {
		super(context, resource, objects);
		this.mContext = context;
		this.layout = resource;
		this.listAuthor = objects;

	}
	private Activity mContext;
	private ArrayList<Author> listAuthor;
	private int layout;

	@Override
	public Author getItem(int position) {
		return listAuthor.get(position);
	}
	@Override
	public int getCount() {
		return listAuthor.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null){
			holder = new ViewHolder();
			LayoutInflater inflater = mContext.getLayoutInflater();
			convertView = inflater.inflate(layout, null);
			holder.ivAuthorImage = (ImageView)convertView.findViewById(R.id.ivAuthorImage);
			holder.layoutItem = (LinearLayout)convertView.findViewById(R.id.layoutItem);
			holder.tvAuthorName = (TextView)convertView.findViewById(R.id.tvAuthorName);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		Author author = listAuthor.get(position);
		if (author != null){
			holder.ivAuthorImage.setImageBitmap(author.getAuthorImage());
			holder.tvAuthorName.setText(author.getAuthorName());
			if (position % 2 == 0)
				holder.layoutItem.setBackgroundColor(mContext.getResources().getColor(R.color.silver_item));
			else 
				holder.layoutItem.setBackgroundColor(mContext.getResources().getColor(R.color.silver_item_less));
		}
		return convertView;
	}

	static class ViewHolder{
		LinearLayout layoutItem;
		ImageView ivAuthorImage;
		TextView tvAuthorName;
	}

}
