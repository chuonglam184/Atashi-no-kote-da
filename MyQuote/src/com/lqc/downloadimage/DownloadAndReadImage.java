package com.lqc.downloadimage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.SimpleTimeZone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.Toast;

public class DownloadAndReadImage {
	String strURL;
    Bitmap bitmap=null;
    Context mContext;
    // pass image url and Pos for example i:
    public DownloadAndReadImage(Context context, String url)
    {
    	this.mContext = context;
        this.strURL=url;
    }
    
    public Bitmap getBitmapImage()
    {
           downloadBitmapImage();
            return readBitmapImage();
    }
    
    public void downloadBitmapImage()
    {
        InputStream input;
        boolean externalAvailable = false;
        boolean externalWritable = false;
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)){
        	externalAvailable = true;
        	externalWritable = true;
        } else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
        	externalAvailable = true;
        } else {
        	// no option
        }
        if (externalAvailable && externalWritable){
        	try {
                URL url = new URL (strURL);
                String[] tmp = strURL.split("/");
                String name = tmp[tmp.length-1];
                input = url.openStream();
                byte[] buffer = new byte[1500];
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                OutputStream output = new FileOutputStream (path + "/" + name);
                try 
                {     
                    int bytesRead = 0;
                    while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) 
                    {
                        output.write(buffer, 0, bytesRead);
                    }
                } 
                finally 
                {
                	Toast.makeText(mContext, "Done!", Toast.LENGTH_SHORT).show();
                    output.close();
                    buffer=null;
                }
            } 
            catch(Exception e) 
            {
                if (externalAvailable && externalWritable){
	                File direct = new File(Environment.getExternalStorageDirectory() + "/FamousQuote");
	                Toast.makeText(mContext,"Cannot create folder on sd card!", Toast.LENGTH_LONG).show();
	                if(!direct.exists())
	                {
	                   if(direct.mkdir()){
	                	   try{
	                           URL url = new URL (strURL);
	                           String[] tmp = strURL.split("/");
	                           
	                           Calendar c = Calendar.getInstance();
	                           SimpleDateFormat df = new SimpleDateFormat("hh-mm-ss");
	                   		   String formattedDate = df.format(c.getTime());
	                   		   
	                           String name = tmp[tmp.length-1];
	                           name += formattedDate;
	                           input = url.openStream();
	                           byte[] buffer = new byte[1500];
	                           String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
	                           OutputStream output = new FileOutputStream (path+"/" + name);
	                           try 
	                           {     
	                               int bytesRead = 0;
	                               while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) 
	                               {
	                                   output.write(buffer, 0, bytesRead);
	                               }
	                           } 
	                           finally 
	                           {
	                           		Toast.makeText(mContext, "Done!", Toast.LENGTH_SHORT).show();
	                                output.close();
	                                buffer=null;
	                           }
	                       }catch(Exception e1){
	                    	   
	                       }
                       }  
	
	                }
                }
            }
        }
    }
    
    @SuppressLint("SdCardPath")
	Bitmap readBitmapImage()
    {
        String imageInSD = "/sdcard/mac/"+strURL;
        BitmapFactory.Options bOptions = new BitmapFactory.Options();
        bOptions.inTempStorage = new byte[16*1024];
                
            bitmap = BitmapFactory.decodeFile(imageInSD,bOptions);
            
        return bitmap;
    }
}
