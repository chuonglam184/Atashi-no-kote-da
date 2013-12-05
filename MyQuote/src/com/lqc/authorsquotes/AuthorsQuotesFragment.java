package com.lqc.authorsquotes;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lqc.MySharedPreferences.MySharedPreferences;
import com.lqc.database.MyAssetDatabase;
import com.lqc.downloadimage.DownloadAndReadImage;
import com.lqc.dto.MyImage;
import com.lqc.dto.Quote;
import com.lqc.myquote.R;

@SuppressLint("ValidFragment")
public class AuthorsQuotesFragment extends Fragment implements OnClickListener{

	private TextView tvContent, tvLikeCount, tvViewCount;
	private ImageView ivLike, ivDownload, ivBookmarkDetail;
	private Button bShowHide;
	private boolean isShow = false;
	private WebView wvDetail;
	private int quote_id;
	private Quote quote;
	private int isBookmarked;
	private MySharedPreferences msp;
	public static int current_index = 0;
	private MyImage myImage;
	public AuthorsQuotesFragment(){

	}
	public AuthorsQuotesFragment(int quote_id){
		this.quote_id  = quote_id;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.authors_quotes_detail_fragment, container, false);
		tvContent = (TextView)rootView.findViewById(R.id.tv_authors_quotes_detail);
		tvLikeCount = (TextView)rootView.findViewById(R.id.tvLikeCount);
		ivLike = (ImageView)rootView.findViewById(R.id.ivLike);
		tvViewCount = (TextView)rootView.findViewById(R.id.tvViewCount);
		ivDownload = (ImageView)rootView.findViewById(R.id.ivDownload);
		ivBookmarkDetail = (ImageView)rootView.findViewById(R.id.ivBookmarkDetails);
		bShowHide = (Button)rootView.findViewById(R.id.bShowHide);
		wvDetail = (WebView)rootView.findViewById(R.id.wvDetail);

		ivLike.setOnClickListener(this);
		ivDownload.setOnClickListener(this);
		ivBookmarkDetail.setOnClickListener(this);
		bShowHide.setOnClickListener(this);
		
		msp = new MySharedPreferences(getActivity().getApplicationContext());
		isBookmarked = msp.getBookmarkQuote(String.valueOf(quote_id));
		if (isBookmarked >= 0){
			ivBookmarkDetail.setImageResource(R.drawable.icon_bookmarked_0);
		} else {
			ivBookmarkDetail.setImageResource(R.drawable.icon_bookmark);
		}

		MyAssetDatabase madb = new MyAssetDatabase(getActivity().getApplicationContext());
		quote = madb.getQuoteById(quote_id);
		tvContent.setText(quote.getQuoteContent());
		Typeface font = Typeface.createFromAsset(getResources().getAssets(), "TIMES.TTF");
		tvContent.setTypeface(font);
		tvContent.setVisibility(View.INVISIBLE);
		// Connecting-2-service gets data thread
		Thread thread = new Thread(){
			final String SOAP_ACTION = "http://tempuri.org/getImageById";
			final String METHOD_NAME = "getImageById";
			final String NAMESPACE = "http://tempuri.org/";
			final String URL = "http://atashinokoute.somee.com/service1.asmx";
			MyImage tmp;

			@Override
			public void run() {
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				request.addProperty("quote_id", quote_id);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				envelope.dotNet = true;
				AndroidHttpTransport transport = new AndroidHttpTransport(URL);
				try{
					transport.call(SOAP_ACTION, envelope);
					SoapObject result = (SoapObject)envelope.bodyIn;
					if(result != null)
					{
						SoapObject object = (SoapObject)result.getProperty(0);
						Log.d("object", object.toString());
						int id = Integer.parseInt(object.getProperty(0).toString());
						Log.d("id", String.valueOf(id));
						String image_url = (String)object.getProperty(1).toString();
						String image_note = (String)object.getProperty(2).toString();
						int view = Integer.parseInt(object.getProperty(3).toString());
						int like = Integer.parseInt(object.getProperty(4).toString());
						tmp = new MyImage(id, image_url, image_note, view, like);

						// Send data to handler updates UI
						Message mess = handler.obtainMessage(LOAD_MY_IMAGE, tmp);
						handler.sendMessage(mess);
					}
				}catch(Exception e){
					Log.d("IOException", e.toString());
				}
				super.run();
			}
		};

		thread.start();
		return rootView;
	}

	// Button like, button download, button bookmark onClick event
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()){
		case R.id.ivLike:
			// Thread connect to service when user click like button
			Thread threadLike = new Thread(){
				final String SOAP_ACTION = "http://tempuri.org/setLikeCount";
				final String METHOD_NAME = "setLikeCount";
				final String NAMESPACE = "http://tempuri.org/";
				final String URL = "http://atashinokoute.somee.com/service1.asmx";
				@Override
				public void run() {
					if (isLiked == false){
						SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
						request.addProperty("quote_id", quote_id);
						SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
						envelope.setOutputSoapObject(request);
						envelope.dotNet = true;
						AndroidHttpTransport transport = new AndroidHttpTransport(URL);
						try{
							transport.call(SOAP_ACTION, envelope);
							Message mess = handler.obtainMessage(LIKE_IMAGE);
							handler.sendMessage(mess);
						}catch(Exception e){
							Log.d("IOException", e.toString());
						}
						isLiked = true;
					}
					super.run();
				}
			};
			threadLike.start();
			break;
		case R.id.ivDownload:
			// Alert when user click download button
			AlertDialog.Builder b=new AlertDialog.Builder(getActivity());
			b.setTitle("Alert");
			b.setMessage("Click OK to save image!");
			b.setPositiveButton("OK", new DialogInterface. OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					DownloadAndReadImage downloader = new DownloadAndReadImage(getActivity(), myImage.getImageURL());
					downloader.downloadBitmapImage();					
				}
			});

			b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.cancel();
				}
			});
			b.create().show();
			break;
		case R.id.ivBookmarkDetails:// When user click to bookmark image
			// If image was bookmarked, then remove it from bookmarked list
			if (isBookmarked >= 0){
				isBookmarked = -1;
				msp.setBookmarkQuote(String.valueOf(quote_id), -1);
			} else {	// else add it to bookmarked list
				isBookmarked = 0;
				msp.setBookmarkQuote(String.valueOf(quote_id), quote_id);
			}	// According to the isBookmarked flag, set bookmark_icon for the label
			if (isBookmarked >= 0){
				ivBookmarkDetail.setImageResource(R.drawable.icon_bookmarked_0);
			} else {
				ivBookmarkDetail.setImageResource(R.drawable.icon_bookmark);
			}
			break;
		case R.id.bShowHide:
			if (isShow == false){
				tvContent.setText(quote.getQuoteContent());
				tvContent.setVisibility(View.VISIBLE);
				bShowHide.setBackgroundResource(R.drawable.hide_icon);
			} else {
				//tvContent.setText("");
				tvContent.setVisibility(View.INVISIBLE);
				bShowHide.setBackgroundResource(R.drawable.show_icon);
			}

			isShow = !isShow;
			break;
		}
		
	}

	// Handler updates UI
	public static final int LOAD_MY_IMAGE = 0;
	public static final int LIKE_IMAGE = LOAD_MY_IMAGE + 1;
	public static final int LOAD_DONE = LIKE_IMAGE + 1;
	public boolean isLiked = false;
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == LOAD_MY_IMAGE){
				myImage = (MyImage)msg.obj;
				
				// Update like count, view count, and display the image into webview
				tvLikeCount.setText(String.valueOf(myImage.getLikeCount()));
				tvViewCount.setText(String.valueOf(myImage.getViewCount()));
				String x = "<style type='text/css'>   img {max-width: 100%;height:initial;align:middle;} div,p,span,a {max-width: 100%;}   </style>";
				x+=	"<body ><img id=\"resizeImage\" src=\"" + myImage.getImageURL() + "\" height=\"100%\" alt=\"\" /></body>";
				wvDetail.loadDataWithBaseURL(null, x, "text/html", "utf-8", null);
			}
			if (msg.what == LIKE_IMAGE){
				
				// Update new like count after user click like button
				int currentLike = Integer.parseInt(tvLikeCount.getText().toString());
				tvLikeCount.setText(String.valueOf(currentLike + 1));
				ivLike.setImageResource(R.drawable.like_icon);
			}
			super.handleMessage(msg);
		}
	};


}
