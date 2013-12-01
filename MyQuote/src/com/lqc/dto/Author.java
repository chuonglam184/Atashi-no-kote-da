package com.lqc.dto;

import android.graphics.Bitmap;
import android.media.Image;

public class Author {
	private int author_id;
	private String author_name;
	private String author_description;
	private String author_url;
	private Bitmap author_image;
	
	public int getAuthorId(){
		return this.author_id;
	}
	public void setAuthorId(int id){
		this.author_id = id;
	}
	
	public String getAuthorName(){
		return this.author_name;
	}
	public void setAuthorName(String name){
		this.author_name = name;
	}
	
	public String getAuthorDescription(){
		return this.author_description;
	}
	public void setAuthorDescription(String desc){
		this.author_description = desc;
	}
	
	public String getAuthorURL(){
		return this.author_url;
	}
	public void setAuthorURL(String url){
		this.author_url = url;
	}
	public Bitmap getAuthorImage(){
		return this.author_image;
	}
	public void setAuthorImage(Bitmap img){
		this.author_image = img;
	}
	
}
