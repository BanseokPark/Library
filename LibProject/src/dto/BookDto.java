package dto;

import java.io.Serializable;

public class BookDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String isbn;		// 도서코드
	private String location;
	private String title;		// 제목
	private String type;		// 장르
	private String publisher;	// 출판사
	private String author;		// 저자
	private String img;			// 이미지 경로
	private String info;		// 소개
	private String rentstate;	// 대여정보 
	private String bookingid; 
	private String return_date;
	
	public BookDto(String isbn, String location, String title, String type, String publisher, String author,
			String img, String info, String rentstate, String bookingid) {
		super();
		this.isbn = isbn;
		this.location = location;
		this.title = title;
		this.type = type;
		this.publisher = publisher;
		this.author = author;
		this.img = img;
		this.info = info;
		this.rentstate = rentstate;
		this.setBookingid(bookingid);
	}	


	public BookDto(String isbn, String location, String title, String type, String publisher, String author, 
			       String rentstate, String bookingid, String return_date) {
		super();
		this.isbn = isbn;
		this.location = location;
		this.title = title;
		this.type = type;
		this.publisher = publisher;
		this.author = author;
		this.rentstate = rentstate;
		this.bookingid = bookingid;
		this.return_date = return_date;
	}



	public String getIsbn() {return isbn;}
	public String getLocation() {return location;}
	public String getTitle() {return title;}
	public String getType() {return type;}
	public String getPublisher() {return publisher;}
	public String getAuthor() {return author;}
	public String getImg() {return img;}
	public String getInfo() {return info;}
	public String getRentstate() {return rentstate;}
	public String getBookingid() {return bookingid;}
	public String getReturn_date() {return return_date;}
	
	public void setIsbn(String isbn) {this.isbn = isbn;}
	public void setLocation(String location) {this.location = location;}
	public void setTitle(String title) {this.title = title;}
	public void setType(String type) {this.type = type;}
	public void setPublisher(String publisher) {this.publisher = publisher;}
	public void setAuthor(String author) {this.author = author;}
	public void setImg(String img) {this.img = img;}
	public void setInfo(String info) {this.info = info;}
	public void setRentstate(String rentstate) {this.rentstate = rentstate;}
	public void setBookingid(String bookingid) {this.bookingid = bookingid;}
	public void setReturn_date(String return_date) {this.return_date = return_date;	}


	@Override
	public String toString() {
		return "BookDto [isbn=" + isbn + ", location=" + location + ", title=" + title + ", type=" + type
				+ ", publisher=" + publisher + ", author=" + author + ", img=" + img + ", info=" + info + ", rentstate="
				+ rentstate + ", bookingid=" + bookingid + ", return_date=" + return_date + "]";
	}


}
