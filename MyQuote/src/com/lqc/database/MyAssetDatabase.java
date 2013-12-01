package com.lqc.database;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.lqc.dto.Author;
import com.lqc.dto.Quote;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyAssetDatabase extends SQLiteAssetHelper{

	private static final String DATABASE_NAME = "db_quotes";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_AUTHOR = "AUTHOR";
	private static final String TABLE_QUOTE = "QUOTE";

	private static final String COLUMN_AUTHOR_ID = "author_id";
	private static final String COLUMN_AUTHOR_NAME = "author_name";
	private static final String COLUMN_AUTHOR_DESCRIPTION = "author_description";
	private static final String COLUMN_AUTHOR_URL = "author_url";
	private static final String COLUMN_AUTHOR_IMAGE = "author_image";

	private static final String COLUMN_QUOTE_ID = "quote_id";
	private static final String COLUMN_QUOTE_CONTENT = "quote_content";

	public MyAssetDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public ArrayList<Author> getAllAuthor(){
		ArrayList<Author> list = new ArrayList<Author>();

		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		qb.setTables(TABLE_AUTHOR);
		Cursor c = db.rawQuery("Select * From " + TABLE_AUTHOR + " order by " + COLUMN_AUTHOR_NAME + " asc", null);

		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			Author author = new Author();
			int id = c.getInt(c.getColumnIndex(COLUMN_AUTHOR_ID));
			String name = c.getString(c.getColumnIndex(COLUMN_AUTHOR_NAME));
			String description = c.getString(c.getColumnIndex(COLUMN_AUTHOR_DESCRIPTION));
			String url = c.getString(c.getColumnIndex(COLUMN_AUTHOR_URL));
			String image = c.getString(c.getColumnIndex(COLUMN_AUTHOR_IMAGE));
			author.setAuthorId(id);
			author.setAuthorName(name);
			author.setAuthorDescription(description);
			Bitmap bmp = Base64ToBitmap(image);
			author.setAuthorImage(bmp);
			list.add(author);
		}
		return list;
	}

	public ArrayList<Quote> getListQuoteByAuthorId(int id){
		ArrayList<Quote> list = new ArrayList<Quote>();
		
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		qb.setTables(TABLE_QUOTE);
		Cursor c = db.rawQuery("Select * From " + TABLE_QUOTE + " Where " + COLUMN_AUTHOR_ID + " = " + id, null);
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			Quote quote = new Quote();
			int quote_id = c.getInt(c.getColumnIndex(COLUMN_QUOTE_ID));
			String quote_content = c.getString(c.getColumnIndex(COLUMN_QUOTE_CONTENT));
			int author_id = c.getInt(c.getColumnIndex(COLUMN_AUTHOR_ID));
			quote.setQuoteId(quote_id);
			quote.setQuoteContent(quote_content);
			quote.setAuthorId(author_id);
			list.add(quote);
		}
		
		return list;
	}
	public ArrayList<String> getAllAuthorName(){
		ArrayList<String> list = new ArrayList<String>();

		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		qb.setTables(TABLE_AUTHOR);
		Cursor c = db.rawQuery("Select author_name From " + TABLE_AUTHOR + " order by " + COLUMN_AUTHOR_NAME + " asc", null);

		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			String name = c.getString(c.getColumnIndex(COLUMN_AUTHOR_NAME));
			list.add(name);
		}
		return list;
	}

	
	public Bitmap Base64ToBitmap(String data){
		byte[] imageAsBytes = Base64.decode(data.getBytes(), 0);
		return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
				
	}
}
