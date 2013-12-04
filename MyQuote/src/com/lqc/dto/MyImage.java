package com.lqc.dto;

public class MyImage {
	private int quote_id;
	private String image_url;
	private String image_note;
	private int view_count;
	private int like_count;
	
	public MyImage(){
		
	}
	
	public int getQuoteId(){
		return this.quote_id;
	}
	public String getImageURL(){
		return this.image_url;
	}
	public String getImageNote(){
		return this.image_note;
	}
	public int getViewCount(){
		return this.view_count;
	}
	public int getLikeCount(){
		return this.like_count;
	}
	public MyImage(int id, String url, String note, int view, int like){
		this.quote_id  =  id;
		image_url = url;
		image_note = note;
		view_count = view;
		like_count = like;
	}
}
