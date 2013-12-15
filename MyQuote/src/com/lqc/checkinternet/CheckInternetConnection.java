package com.lqc.checkinternet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckInternetConnection {
	private Context _context;
	public CheckInternetConnection(Context context){
		this._context = context;
	}
	
	public boolean checkMobileInternetConn() {
        //Táº¡o Ä‘á»‘i tÆ°Æ¡ng ConnectivityManager Ä‘á»ƒ tráº£ vá»� thÃ´ng tin máº¡ng
        ConnectivityManager connectivity = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //Náº¿u Ä‘á»‘i tÆ°á»£ng khÃ¡c null
        if (connectivity != null) {
            //Nháº­n thÃ´ng tin máº¡ng
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo info1 = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if ((info != null) || (info1!=null)) {
                //TÃ¬m kiáº¿m thiáº¿t bá»‹ Ä‘Ã£ káº¿t ná»‘i Ä‘Æ°á»£c internet chÆ°a
                if (info.isConnected()|| info1.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }
}
