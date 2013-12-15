package com.lqc.authorsquotes;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lqc.MySharedPreferences.MySharedPreferences;
import com.lqc.database.Decompress;
import com.lqc.database.MyAssetDictionaryDatabase;
import com.lqc.database.MyDictionaryDatabase;
import com.lqc.myquote.R;

public class DictActivity extends Activity implements OnInitListener{
	WebView wvDict;
	ImageView ivReadWord;
	TextView tvWordRead;
	private TextToSpeech myTTS;
	private int MY_DATA_CHECK_CODE = 0;
	
	private String inputString;
	MyDictionaryDatabase madb;
	MySharedPreferences msp ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getIntent().getBundleExtra("bundle");
		inputString = b.getString("mWord");
		inputString = inputString.toLowerCase(Locale.ENGLISH);
		setContentView(R.layout.dict_activity);
		wvDict = (WebView)findViewById(R.id.wvDict);
		tvWordRead = (TextView)findViewById(R.id.tvWordRead);
		tvWordRead.setText(inputString);
		
		/*ivReadWord = (ImageView)findViewById(R.id.ivReadWord);
		ivReadWord.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				speakWords(inputString);
			}
		});		
		Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);*/
        
		madb = new MyDictionaryDatabase(getApplicationContext());
		String t = madb.searchMeaning(inputString);
		wvDict.loadDataWithBaseURL(null, t, "text/html", "utf-8", null);
		
		////////////////////////// Unused////////////////////////
		/*boolean isExists = checkIfDBExists();
		Log.d("is exists", String.valueOf(isExists));
		if (isExists == true){
			MyDictionaryDatabase madb = new MyDictionaryDatabase(getApplicationContext());
			String t = madb.searchMeaning(inputString);
			wvDict.loadDataWithBaseURL(null, t, "text/html", "utf-8", null);
		} else {
			showAlertDialogDownloadChoice();
		}*/
	}

	private void speakWords(String speech) {
        //speak straight away
        myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
	}
	
	////////////////////////////////// unused
	@SuppressWarnings("unused")
	private void showAlertDialogDownloadChoice() {
		AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
				DictActivity.this);

		// Setting Dialog Title
		alertDialog2.setTitle("Download dictionary...");

		// Setting Dialog Message
		alertDialog2.setMessage("You don't have any dictionary. Click Yes to install one!");

		// Setting Positive "Yes" Btn
		alertDialog2.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				startDownload();
			}
		});
		// Setting Negative "NO" Btn
		alertDialog2.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		alertDialog2.show();

	}

	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	private ProgressDialog mProgressDialog;
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_DOWNLOAD_PROGRESS:
			mProgressDialog = new ProgressDialog(DictActivity.this);
			mProgressDialog.setMessage("Downloading file..");
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setCancelable(true);
			mProgressDialog.show();
			return mProgressDialog;
		default:
			return null;
		}
	}

	private void startDownload() {
		String url = "https://dl.boxcloud.com/bc/2/5ebc590faf168e7375344d93617d7cc2/oxsr6n5bVQhyic6N7TfrMf0sVTkQUOBIH0HsZBGRDI0si84Chpt_3ZjzaZYMPOwbyyATOQjCMtuz5i34lvwOP6jpszWlLv8WEdznyZPTapEL7jhsMH-dydGXCG7uH9b3oAP7jkXgaUO2k-1Qz1gBVmHEBw0D4q0oEnYMytv70dEv-5WAJCzdk706gVLdI8bZpdfkUT768RAehwrOoOeyuUXvXi6z0M5sgii5GSAIGT-_n1XZyQDnPq0vprXOJCR-yWQmx7CmSB0Sxq17BwbFH1nXAEgNPz9KRvDuAYxF5c_gbu-snizR0_7hvcRAwOPEOZgfQjSS1TMTTDgSVI2qDzpFGcuDAqCfkTNfNhdWIihYKRLRiwQBFE3zHURsmy41T4gKlD3Qfj2PWUwIC4eIxUxUmniocX_Ss9n0RSmgIaHyWCgPI7arskHaBUPzyJRcw0Apo_wB5yH7eJD5HLY_OCTkB7ylet5_U8piHrtoAwtZth3M3yoz0dJSEgjDW4gd-mhLhTQjgw5s5jxuTSx-WOSDuPl8SWglICiwM8-FpASx99ZGm1_A9ekluWx04eBldiFPnNdM75XlST72zSZZjqLNLJrT9TiX8Ve0BLJx1lSLnHfayUhVGbX5rwQ-0jDF96qhJtF1Hou9cRZbUwHKkAjVN-JRbLL4cHag6lbkbjaAMIZY7R0n_Y7hNAPC0ONpOTzeEBsIN8oMI_kYOUnQ7s6c7pr_3e4wBIMeu-iGQKG7x-JKGsrvJ4xwysB5FoGj/1cbfa3c3b03acc3b4f172569f59abb33047f9a502ab8bbba1dcc1091e5b6a6f9/";
		new DownloadFileAsync().execute(url);
	}

	class DownloadFileAsync extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(DIALOG_DOWNLOAD_PROGRESS);
		}

		@Override
		protected String doInBackground(String... aurl) {
			int count;
			try {

				URL url = new URL(aurl[0]);
				URLConnection conexion = url.openConnection();
				conexion.connect();

				int lenghtOfFile = conexion.getContentLength();
				Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);
				
				InputStream input = new BufferedInputStream(url.openStream());
				OutputStream output = new FileOutputStream("/sdcard/DIC/anh_viet.zip");

				byte data[] = new byte[1024];

				long total = 0;

				while ((count = input.read(data)) != -1) {
					total += count;
					//publishProgress(""+(int)((total*100)/lenghtOfFile));
					publishProgress(""+(int)(total));
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				input.close();
			} catch (Exception e) {}
			return null;

		}
		protected void onProgressUpdate(String... progress) {
			Log.d("ANDRO_ASYNC",progress[0]);
			mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		}

		@Override
		protected void onPostExecute(String unused) {
			Log.d("ANDRO_ASYNC","Done");
			dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
			unzippedFile();
		}
	}

	private boolean checkIfDBExists() {
		File extStore = Environment.getExternalStorageDirectory();
		File myFile = new File(extStore.getAbsolutePath() + "/DIC/anh_viet.db");
		return myFile.exists();
	}
	
	class UnZippedFileAsyncTask extends AsyncTask<Void, Void, Void>{
		ProgressDialog mProgressDialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(DictActivity.this);
			mProgressDialog.setTitle("Tu vung tieng anh");
			mProgressDialog.setMessage("Extracting file...");
			mProgressDialog.show();
		}
		@Override
		protected Void doInBackground(Void... params) {
			unzippedFile();
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			mProgressDialog.dismiss();
		}
		
	}
	
	private void unzippedFile(){
		String zipFile = Environment.getExternalStorageDirectory() + "/DIC/anh_viet.zip"; 
		String unzipLocation = Environment.getExternalStorageDirectory() + "/DIC/"; 
		 
		Decompress d = new Decompress(zipFile, unzipLocation); 
		d.unzip(); 
	}
	//////////////////////////////////unused

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the TTS
            myTTS = new TextToSpeech(this, this);
            }
            else {
                    //no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }
	
	@Override
	public void onInit(int arg0) {
		if (arg0 == TextToSpeech.SUCCESS) {
            if(myTTS.isLanguageAvailable(Locale.US)==TextToSpeech.LANG_AVAILABLE)
                myTTS.setLanguage(Locale.US);
        }
        else if (arg0 == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
		
	}
}
