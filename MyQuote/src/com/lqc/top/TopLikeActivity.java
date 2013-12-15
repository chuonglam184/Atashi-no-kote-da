package com.lqc.top;

import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.lqc.authorsquotes.AuthorsQuotesAdapter;
import com.lqc.authorsquotes.AuthorsQuotesFragmentActivity;
import com.lqc.database.MyAssetDatabase;
import com.lqc.dto.Quote;
import com.lqc.myquote.R;

@SuppressLint("HandlerLeak")
public class TopLikeActivity extends SherlockFragment implements OnItemClickListener{
	
	private ArrayList<Integer> list_id;
	private ArrayList<Quote> list_quote;
	private AuthorsQuotesAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewGroup root = (ViewGroup)inflater.inflate(R.layout.authors_quotes_activity, null);
		ListView lv = (ListView)root.findViewById(R.id.lvAuthorsQuotes);
		list_id  = new ArrayList<Integer>();
		list_quote = new ArrayList<Quote>();
		adapter = new AuthorsQuotesAdapter(getActivity(), R.layout.authors_quotes_list_item, list_quote);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
		
		return root;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Thread thread = new Thread(){
			final String SOAP_ACTION = "http://tempuri.org/getTopLikeQuoteId";
			final String METHOD_NAME = "getTopLikeQuoteId";
			final String NAMESPACE = "http://tempuri.org/";
			final String URL = "http://atashinokoute.somee.com/service1.asmx";
			
			@Override
			public void run() {
			
				ArrayList<Integer> listId = new ArrayList<Integer>();
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				request.addProperty("topCount", 50);
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
						Log.d("object size", String.valueOf(object.getPropertyCount()));
						for (int i =0; i<object.getPropertyCount(); i++){
							listId.add(Integer.parseInt(object.getProperty(i).toString()));
						}
						
						// Send data to handler updates UI
						Message mess = mHandler.obtainMessage(LOAD_TOP_ID, listId);
						mHandler.sendMessage(mess);
					}
				}catch(Exception e){
					Log.d("IOException", e.toString());
				}
				super.run();
			}
		};
		thread.start();
	}
	
	
	
	public static final int LOAD_TOP_ID = 0;
	Handler mHandler = new Handler(){
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == LOAD_TOP_ID){
				list_id = (ArrayList<Integer>)msg.obj;
				MyAssetDatabase madb = new MyAssetDatabase(getSherlockActivity());
				for (int i=0; i<list_id.size();i++){
					Quote quote = madb.getQuoteById(list_id.get(i));
					list_quote.add(quote);
				}
				adapter.notifyDataSetChanged();
			}
		}
	};
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Bundle b = new Bundle();
		b.putInt("selectedIndex", arg2);
		b.putIntegerArrayList("listQuoteId", list_id);
		b.putString("author_name", "Top like");
		Intent i = new Intent(getSherlockActivity().getApplicationContext(), AuthorsQuotesFragmentActivity.class);
		i.putExtra("bundle", b);
		startActivity(i);
	}
}
