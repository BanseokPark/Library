package dto;

import java.io.Serializable;

public class BookRequestDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int requestindex;
	private String title;
	private String type;
	private String publisher;
	private String author;
	private String id;
	private String date;
	private String state;	
	
	public BookRequestDto(String title, String type, String publisher, String author, String id) {
		super();
		this.title = title;
		this.type = type;
		this.publisher = publisher;
		this.author = author;
		this.id = id;
	}
	
	public BookRequestDto(int requestindex, String title, String type, String publisher, String author, String id,
			String date, String state) {
		super();
		this.requestindex = requestindex;
		this.title = title;
		this.type = type;
		this.publisher = publisher;
		this.author = author;
		this.id = id;
		this.date = date;
		this.state = state;
	}
	
	public int getRequestindex() {return requestindex;}
	public String getTitle() {return title;}
	public String getType() {return type;}
	public String getPublisher() {return publisher;}
	public String getAuthor() {return author;}
	public String getId() {return id;}
	public String getDate() {return date;}
	public String getState() {return state;}

	public void setRequestindex(int requestindex) {this.requestindex = requestindex;}
	public void setTitle(String title) {this.title = title;}
	public void setType(String type) {this.type = type;}
	public void setPublisher(String publisher) {this.publisher = publisher;}
	public void setAuthor(String author) {this.author = author;}
	public void setId(String id) {this.id = id;}
	public void setDate(String date) {this.date = date;}
	public void setState(String state) {this.state = state;}

	@Override
	public String toString() {
		return "BookRequestDto [requestindex=" + requestindex + ", title=" + title + ", type=" + type + ", publisher="
				+ publisher + ", author=" + author + ", id=" + id + ", date=" + date + ", state=" + state + "]";
	}
}
