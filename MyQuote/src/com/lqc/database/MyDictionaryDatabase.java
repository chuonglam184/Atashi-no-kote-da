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
		String meaning = "Word not found!";
		String path = Environment.getExternalStorageDirectory().getPath();
		path += "/DIC/anh_viet.db";
		File dbfile = new File(path ); 
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		qb.setTables(TABLE_ANH_VIET);
		Cursor c = db.rawQuery("Select content From " + TABLE_ANH_VIET + " Where " + COLUMN_WORD + " = '" + word + "'", null);

		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			meaning = c.getString(c.getColumnIndex(COLUMN_CONTENT));
		}
		c.close();
		return meaning;
	}
}
