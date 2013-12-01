package com.lqc.dto;

public class Quote {
	private int quote_id;
	private String quote_content;
	private int author_id;
	private int page_index;
	public int getPageIndex(){
		return this.page_index;
	}
	public void setPageIndex(int index){
		this.page_index = index;
	}
	public int getQuoteId(){
		return this.quote_id;
	}
	public void setQuoteId(int id){
		this.quote_id = id;
	}
	public String getQuoteContent(){
		return this.quote_content;
	}
	public void setQuoteContent(String content){
		this.quote_content = content;
	}
	public int getAuthorId(){
		return this.author_id;
	}
	public void setAuthorId(int id){
		this.author_id = id;
	}
}
