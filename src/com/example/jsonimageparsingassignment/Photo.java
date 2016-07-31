package com.example.jsonimageparsingassignment;

public class Photo {

	String id,title,url;

	public Photo(){}
	
	public Photo(String id, String title, String url) {
		super();
		this.id = id;
		this.title = title;
		this.url = url;
	}

	@Override
	public String toString() {
		return "Photo [id=" + id + ", title=" + title + ", url=" + url + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
