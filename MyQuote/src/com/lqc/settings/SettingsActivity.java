package com.lqc.settings;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.lqc.MySharedPreferences.MySharedPreferences;
import com.lqc.dto.MyLanguage;
import com.lqc.main.MySherlockActivity;
import com.lqc.myquote.R;

public class SettingsActivity extends MySherlockActivity implements OnClickListener{
	public static int text_size;
	public static String language;
	private Button bVietnamese, bFrance;
	public static String dict_url = "";
	private int flag = -1;	// indicates which button is clicked
	public static int flag_Vietnamese = 0;	
	public static int flag_France = flag_Vietnamese + 1;
	private ProgressDialog mProgressDialog;
	String unzipLocation = Environment.getExternalStorageDirectory() + "/DIC/";
	String zipFile =Environment.getExternalStorageDirectory().toString() + "/DIC/anh_viet.zip";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);

		bVietnamese = (Button)findViewById(R.id.bVietnamese);
		bFrance = (Button)findViewById(R.id.bFrance);

		bVietnamese.setOnClickListener(this);
		bFrance.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		boolean isExist = false;
		switch (arg0.getId()){
		case R.id.bVietnamese:
			flag = flag_Vietnamese;
			dict_url = "https://andict.googlecode.com/files/anh_viet.zip";
			isExist = checkIfDBExists(VIETNAMESE);
			break;
		case R.id.bFrance:
			flag = flag_France;
			dict_url = "";
			Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
			isExist = checkIfDBExists(FRANCE);
			break;
		}
		if (isExist == false){
			// -------- Code from http://iamvijayakumar.blogspot.com/2012/07/android-unzip-example.html
			DownloadMapAsync mew = new DownloadMapAsync();
			mew.execute(dict_url);
			//-----------------------------------------------------------------------------------------
		} else {
			MySharedPreferences sp = new MySharedPreferences(getApplicationContext());
			String language ="";
			if (flag == flag_Vietnamese){
				sp.setMyLanguage(MyLanguage.VIETNAMESE);
				language = "Vietnamese";
			}
			if (flag == flag_France){
				sp.setMyLanguage(MyLanguage.FRANCE);
				language = "France";
			}
			Toast.makeText(getApplicationContext(), "Make your language be " + language, Toast.LENGTH_SHORT).show();
		}
	}

	class DownloadMapAsync extends AsyncTask<String, String, String> {
		String result ="";
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(SettingsActivity.this);
			mProgressDialog.setMessage("Downloading Zip File..");
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
		}

		@Override
		protected String doInBackground(String... aurl) {
			int count;
			try {
				URL url = new URL(aurl[0]);
				URLConnection conexion = url.openConnection();
				conexion.connect();
				int lenghtOfFile = conexion.getContentLength();
				mProgressDialog.setTitle(String.valueOf(lenghtOfFile));
				InputStream input = new BufferedInputStream(url.openStream(), 8192);

				OutputStream output = new FileOutputStream(zipFile);

				byte data[] = new byte[1024];
				long total = 0;

				while ((count = input.read(data)) != -1) {
					total += count;
					publishProgress(""+(int)((total*100)/lenghtOfFile));
					output.write(data, 0, count);
				}
				output.close();
				input.close();
				result = "true";

			} catch (Exception e) {
				result = "false";
			}
			return null;
		}
		protected void onProgressUpdate(String... progress) {
			Log.d("ANDRO_ASYNC",progress[0]);
			mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		}

		@Override
		protected void onPostExecute(String unused) {
			mProgressDialog.dismiss();
			if(result.equalsIgnoreCase("true")){
				try {
					unzip();
					MySharedPreferences msp = new MySharedPreferences(getApplicationContext());
					if (flag == flag_Vietnamese){
						msp.setMyLanguage(MyLanguage.VIETNAMESE);
					} else if (flag == flag_France){
						msp.setMyLanguage(MyLanguage.FRANCE);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else{

			}
		}
	}

	public void unzip() throws IOException {
		mProgressDialog = new ProgressDialog(SettingsActivity.this);
		mProgressDialog.setMessage("Please Wait...Extracting zip file ... ");
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
		new UnZipTask().execute(zipFile, unzipLocation);
	}

	private class UnZipTask extends AsyncTask<String, Void, Boolean> {
		@SuppressWarnings("rawtypes")
		@Override
		protected Boolean doInBackground(String... params) {
			String filePath = params[0];
			String destinationPath = params[1];

			File archive = new File(filePath);
			try {


				ZipFile zipfile = new ZipFile(archive);
				for (Enumeration e = zipfile.entries(); e.hasMoreElements();) {
					ZipEntry entry = (ZipEntry) e.nextElement();
					unzipEntry(zipfile, entry, destinationPath);
				}
				UnzipUtil d = new UnzipUtil(zipFile, unzipLocation); 
				d.unzip();
			} catch (Exception e) {

				return false;
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			mProgressDialog.dismiss();
			Toast.makeText(getApplicationContext(), "Congratulation! You've installed the dictionary successfully!", Toast.LENGTH_LONG).show();
		}


		private void unzipEntry(ZipFile zipfile, ZipEntry entry,
				String outputDir) throws IOException {

			if (entry.isDirectory()) {
				createDir(new File(outputDir, entry.getName()));
				return;
			}

			File outputFile = new File(outputDir, entry.getName());
			if (!outputFile.getParentFile().exists()) {
				createDir(outputFile.getParentFile());
			}

			// Log.v("", "Extracting: " + entry);
			BufferedInputStream inputStream = new BufferedInputStream(zipfile.getInputStream(entry));
			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

			try {

			} finally {
				outputStream.flush();
				outputStream.close();
				inputStream.close();
			}
		}

		private void createDir(File dir) {
			if (dir.exists()) {
				return;
			}
			if (!dir.mkdirs()) {
				throw new RuntimeException("Can not create dir " + dir);
			}
		}}

	public static final int VIETNAMESE = 0;
	public static final int FRANCE = VIETNAMESE + 1;
	public static boolean checkIfDBExists(int DICT) {
		String path="";
		if (DICT == VIETNAMESE){
			path = "/DIC/anh_viet/anh_viet.db";
		} else if (DICT == FRANCE){
			path = "/DIC/anh_phap/anh_phap.db";
		}
		File extStore = Environment.getExternalStorageDirectory();
		File myFile = new File(extStore.getAbsolutePath() + path);
		return myFile.exists();
	}
}

