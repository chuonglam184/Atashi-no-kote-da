package com.lqc.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.lqc.MySharedPreferences.MySharedPreferences;
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
	private static final String COLUMN_PAGE_INDEX = "page_index";
	private static final String COLUMN_QUOTE_ID = "quote_id";
	private static final String COLUMN_QUOTE_CONTENT = "quote_content";
	
	private Context mContext;
	public MyAssetDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.mContext = context;
	}
	
	public ArrayList<Quote> getBookmarkedQuotes(){
		ArrayList<Quote> result = new ArrayList<Quote>();
		int size = getNumOfQuotes();
		MySharedPreferences msp = new MySharedPreferences(mContext);
		for (int i=0;i<=size;i++){
			int bookmarkedId = msp.getBookmarkQuote(String.valueOf(i));
			if (bookmarkedId >=0){
				Quote quote = getQuoteById(bookmarkedId);
				result.add(quote);
			}
		}
		Collections.shuffle(result, new Random(System.nanoTime()));
		return result;
	}
	public Quote getQuoteById(int id){
		Quote quote = null;

		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		qb.setTables(TABLE_QUOTE);
		Cursor c = db.rawQuery("Select * From " + TABLE_QUOTE + " Where " + COLUMN_QUOTE_ID + " = " + id, null);

		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			quote = new Quote();
			int quote_id = c.getInt(c.getColumnIndex(COLUMN_QUOTE_ID));
			String content = c.getString(c.getColumnIndex(COLUMN_QUOTE_CONTENT));
			int author_id = c.getInt(c.getColumnIndex(COLUMN_AUTHOR_ID));
			quote.setQuoteId(quote_id);
			quote.setQuoteContent(content);
			quote.setAuthorId(author_id);
		}
		c.close();
		return quote;
	}
	public Author getAuthorById(int id){
		Author author = null;

		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		qb.setTables(TABLE_AUTHOR);
		Cursor c = db.rawQuery("Select * From " + TABLE_AUTHOR + " Where " + COLUMN_AUTHOR_ID + " = " + id, null);

		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			author = new Author();
			String name = c.getString(c.getColumnIndex(COLUMN_AUTHOR_NAME));
			String description = c.getString(c.getColumnIndex(COLUMN_AUTHOR_DESCRIPTION));
			String url = c.getString(c.getColumnIndex(COLUMN_AUTHOR_URL));
			String image = c.getString(c.getColumnIndex(COLUMN_AUTHOR_IMAGE));
			author.setAuthorName(name);
			author.setAuthorDescription(description);
			Bitmap bmp = Base64ToBitmap(image);
			author.setAuthorImage(bmp);
			author.setAuthorURL(url);
		}
		c.close();
		return author;
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
		c.close();
		//Collections.shuffle(list, new Random(System.nanoTime()));
		return list;
	}

	public int getPageNumber(){
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		qb.setTables(TABLE_QUOTE);
		int page_num = 1;
		Cursor c = db.rawQuery("Select count(*) as page_num From " + TABLE_QUOTE + " group by " + COLUMN_PAGE_INDEX, null);
		page_num = c.getCount();
		c.close();
		return page_num;

	}
	
	public int getNumOfQuotes(){
		ArrayList<Quote> list = new ArrayList<Quote>();

		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		qb.setTables(TABLE_QUOTE);
		Cursor c = db.rawQuery("Select * From " + TABLE_QUOTE, null);
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			Quote quote = new Quote();
			int quote_id = c.getInt(c.getColumnIndex(COLUMN_QUOTE_ID));
			String quote_content = c.getString(c.getColumnIndex(COLUMN_QUOTE_CONTENT));
			int author_id = c.getInt(c.getColumnIndex(COLUMN_AUTHOR_ID));
			int page_index = c.getInt(c.getColumnIndex(COLUMN_PAGE_INDEX));
			quote.setQuoteId(quote_id);
			quote.setQuoteContent(quote_content);
			quote.setAuthorId(author_id);
			quote.setPageIndex(page_index);

			list.add(quote);
		}
		c.close();
		return list.size();
	}
	public ArrayList<Quote> getAllQuoteByPageIndex(int idx){
		ArrayList<Quote> list = new ArrayList<Quote>();

		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		qb.setTables(TABLE_QUOTE);
		Cursor c = db.rawQuery("Select * From " + TABLE_QUOTE + " Where " + COLUMN_PAGE_INDEX + " = " + idx, null);
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			Quote quote = new Quote();
			int quote_id = c.getInt(c.getColumnIndex(COLUMN_QUOTE_ID));
			String quote_content = c.getString(c.getColumnIndex(COLUMN_QUOTE_CONTENT));
			int author_id = c.getInt(c.getColumnIndex(COLUMN_AUTHOR_ID));
			int page_index = c.getInt(c.getColumnIndex(COLUMN_PAGE_INDEX));
			quote.setQuoteId(quote_id);
			quote.setQuoteContent(quote_content);
			quote.setAuthorId(author_id);
			quote.setPageIndex(page_index);

			list.add(quote);
		}
		c.close();
		Collections.shuffle(list, new Random(System.nanoTime()));
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
		c.close();
		Collections.shuffle(list, new Random(System.nanoTime()));
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
		c.close();
		//Collections.shuffle(list, new Random(System.nanoTime()));
		return list;
	}


	public Bitmap Base64ToBitmap(String data){
		byte[] imageAsBytes = Base64.decode(data.getBytes(), 0);
		return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

	}
}
