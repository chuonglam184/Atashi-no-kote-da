package com.lqc.database;

import java.io.File;

import com.lqc.dto.Quote;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class MyDictionaryDatabase extends SQLiteAssetHelper{
	
	public MyDictionaryDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.mContext = context;
	}
	
	private static final int DATABASE_VERSION = 1;
	private Context mContext;
	private static final String DATABASE_NAME = "anh_viet";
	private static final String TABLE_ANH_VIET = "anh_viet";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_WORD = "word";
	private static final String COLUMN_CONTENT = "content";
	
	public String searchMeaning(String word){
		String meaning = "";
		word.replace("'", "\"");
		String path = Environment.getExternalStorageDirectory().getPath();
		path += "/DIC/anh_viet/anh_viet.db";
		File dbfile = new File(path ); 
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		qb.setTables(TABLE_ANH_VIET);
		Cursor c = db.rawQuery("Select content From " + TABLE_ANH_VIET + " Where " + COLUMN_WORD + " = '" + word + "'", null);

		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			meaning = c.getString(c.getColumnIndex(COLUMN_CONTENT));
		}
		if (meaning.equals("")){
			String newword = word.substring(0, word.length()-1);
			Log.d("new word", newword);
			c = db.rawQuery("Select content From " + TABLE_ANH_VIET + " Where " + COLUMN_WORD + " = '" + newword + "'", null);

			for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
				meaning = c.getString(c.getColumnIndex(COLUMN_CONTENT));
			}
		}
		if (meaning.equals("")){
			String newword = word.substring(0, word.length()-2);
			Log.d("new word", newword);
			c = db.rawQuery("Select content From " + TABLE_ANH_VIET + " Where " + COLUMN_WORD + " = '" + newword + "'", null);

			for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
				meaning = c.getString(c.getColumnIndex(COLUMN_CONTENT));
			}
		}
		if (meaning.equals("")){
			String newword = word.substring(0, word.length()-3);
			newword +="y";
			Log.d("new word", newword);
			c = db.rawQuery("Select content From " + TABLE_ANH_VIET + " Where " + COLUMN_WORD + " = '" + newword + "'", null);

			for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
				meaning = c.getString(c.getColumnIndex(COLUMN_CONTENT));
			}
		}
		if (meaning.equals("")){
			meaning = "Sorry! Word not found!";
		}
		c.close();
		
		return meaning;
	}
}
