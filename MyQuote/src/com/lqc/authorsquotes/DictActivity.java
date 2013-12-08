package com.lqc.authorsquotes;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebView;

import com.lqc.database.MyDictionaryDatabase;
import com.lqc.myquote.R;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

public class DictActivity extends Activity {
	WebView wvDict;
	private String inputString;
	private String translated;
	MyDictionaryDatabase madb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getIntent().getBundleExtra("bundle");
		inputString = b.getString("mWord");
		Log.d("word", inputString);
		setContentView(R.layout.dict_activity);

		wvDict = (WebView)findViewById(R.id.wvDict);
		//new TranslateAsyncTask().execute(inputString);
		
		MyDictionaryDatabase madb = new MyDictionaryDatabase(getApplicationContext());
		String t = madb.searchMeaning(inputString);
		wvDict.loadDataWithBaseURL(null, t, "text/html", "utf-8", null);
	}

	public class TranslateAsyncTask extends AsyncTask<String, Void, String>{

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			wvDict.loadDataWithBaseURL(null, result, "text/html", "utf-8", null);
		}
		
		@Override
		protected String doInBackground(String... params) {
			String translated = "";
			try {
				translated = translate(params[0]);
			} catch (Exception e) {
				Log.d("exception", e.toString());
			}
			return translated;
		}
		
	}
	
	public String translate(String text) throws Exception{
		// Set the Client ID / Client Secret once per JVM. It is set statically and applies to all services
		Log.d("input", text);   
		Translate.setClientId("ChuongLam184");
		Translate.setClientSecret("/wiud6NhCzFuwojwg5/aWu3kzMn7ZYwB5HtYT8RN33A=");

		String translatedText = "";

		// English AUTO_DETECT -> gERMAN Change this if u wanna other languages
		translatedText = Translate.execute(text,Language.ENGLISH, Language.VIETNAMESE);
		Log.d("translated", translatedText);
		return translatedText;
	}
}
