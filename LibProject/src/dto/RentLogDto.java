package dto;

import java.io.Serializable;

public class RentLogDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int logindex;		// 대여기록
	private String isbn;		// 	
	private String title;
	private String type;
	private String publisher;
	private String author;
	private String id;	
	private String rentstate;	// 대여상태(대여 , 반납)
	private String rentdate;	// 대여,반납일
	
	public RentLogDto(int logindex, String isbn, String title, String type, String publisher, String author, String id,
			String rentstate, String rentdate) {
		super();
		this.logindex = logindex;
		this.isbn = isbn;
		this.title = title;
		this.type = type;
		this.publisher = publisher;
		this.author = author;
		this.id = id;
		this.rentstate = rentstate;
		this.rentdate = rentdate;
	}

	public int getLogindex() {return logindex;}
	public String getIsbn() {return isbn;}
	public String getTitle() {return title;}
	public String getType() {return type;}
	public String getPublisher() {return publisher;}
	public String getAuthor() {return author;}
	public String getId() {return id;}
	public String getRentstate() {return rentstate;}
	public String getRentdate() {return rentdate;}
	
	public void setLogindex(int logindex) {this.logindex = logindex;}
	public void setIsbn(String isbn) {this.isbn = isbn;}
	public void setTitle(String title) {this.title = title;}
	public void setType(String type) {this.type = type;}
	public void setPublisher(String publisher) {this.publisher = publisher;}
	public void setAuthor(String author) {this.author = author;}
	public void setId(String id) {this.id = id;}
	public void setRentstate(String rentstate) {this.rentstate = rentstate;}
	public void setRentdate(String rentdate) {this.rentdate = rentdate;}

	@Override
	public String toString() {
		return "RentLogDto [logindex=" + logindex + ", isbn=" + isbn + ", title=" 
				+ title + ", type=" + type+ ", publisher=" + publisher + ", author="
				+ author + ", id=" + id + ", rentstate=" + rentstate+ ", rentdate=" 
				+ rentdate + "]";
	}
}