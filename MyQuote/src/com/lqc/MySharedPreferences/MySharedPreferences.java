package com.lqc.MySharedPreferences;

import com.lqc.dto.MyLanguage;
import com.lqc.settings.SettingsActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MySharedPreferences {
	public static final String SP_NAME = "My_SharedPreferences";
	public static final String TEXT_SIZE = "text_size";
	public static final String MY_LANGUAGE = "my_language";
	
	private SharedPreferences sp;
	private Editor editor;
	private Context mContext;
	
	public MySharedPreferences(Context context){
		this.mContext = context;
		sp = mContext.getSharedPreferences(SP_NAME, 0);
		editor = sp.edit();
	}
	
	public void getTextSize(){
		SettingsActivity.text_size = sp.getInt(TEXT_SIZE, 18);
	}
	
	public void setTextSize(int size){
		editor.putInt(TEXT_SIZE, size);
		editor.commit();
	}
	
	public void getMyLanguage(){
		SettingsActivity.language = sp.getString(MY_LANGUAGE, MyLanguage.VIETNAMESE);
	}
	
	public void setMyLanguage(String language){
		editor.putString(MY_LANGUAGE, language);
		editor.commit();
	}
}
