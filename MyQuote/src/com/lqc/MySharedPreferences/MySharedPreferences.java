package com.lqc.MySharedPreferences;

import java.util.ArrayList;

import com.lqc.dto.MyLanguage;
import com.lqc.settings.SettingsActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MySharedPreferences {
	public static final String SP_NAME = "My_SharedPreferences";
	public static final String TEXT_SIZE = "text_size";
	public static final String MY_LANGUAGE = "my_language";
	public static final String BOOKMARK_WORDS = "bookmarked_words";
	public static ArrayList<Integer> listBookmarked = new ArrayList<Integer>();
	private SharedPreferences sp;
	private Editor editor;
	private Context mContext;
	
	public MySharedPreferences(Context context){
		this.mContext = context;
		sp = mContext.getSharedPreferences(SP_NAME, 0);
		editor = sp.edit();
	}
	
	// isBookmarked > 0: save word
	// isBookmarked =0: remove word
	public int getBookmarkQuote(String strQuoteId){
		return sp.getInt(strQuoteId, -1);
	}
	
	public void setBookmarkQuote(String strQuoteId, int quoteId){
		editor.putInt(strQuoteId, quoteId);
		editor.commit();
	}
	public void getTextSize(){
		SettingsActivity.text_size = sp.getInt(TEXT_SIZE, 18);
	}
	
	public void setTextSize(int size){
		editor.putInt(TEXT_SIZE, size);
		editor.commit();
	}
	

	public String getMyLanguage(){
		return sp.getString(MY_LANGUAGE, "");
	}
	
	public void setMyLanguage(String language){
		editor.putString(MY_LANGUAGE, language);
		editor.commit();
	}
	
	public boolean saveArray(ArrayList<Integer> array, String arrayName, Context mContext) {   
		SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);  
		SharedPreferences.Editor editor = prefs.edit();  
		editor.putInt(arrayName +"_size", array.size());  
		for(int i=0;i<array.size();i++)  {
			editor.putInt(arrayName + "_" + i, array.get(i)); 
		}
		return editor.commit();  
	} 

	public ArrayList<Integer> loadArray(String arrayName, Context mContext) {  
		SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);  
		int size = prefs.getInt(arrayName + "_size", 0);  
		if (size != 0){
			ArrayList<Integer> array = new ArrayList<Integer>();
			for(int i=0;i<size;i++)  {
				array.add(prefs.getInt(arrayName + "_"+i, -1));
			}
			return array;  
		}
		return null;
	}
	
}
