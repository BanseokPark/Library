package dto;

import java.io.Serializable;

public class RentDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int rentindex;			// 
	private String isbn;			// 도서코드
	private String title;			// 제목
	private String type;			// 장르
	private String publisher;		// 출판사
	private String author;			// 저자
	private String id;				// 아이디
	private String rentdate;		// 대여일
	private String returndate;		// 반납예졍일
	private String name;
	
	public RentDto(int rentindex, String isbn, String title, String type, String publisher, String author, String id,
			String rentdate, String returndate) {
		super();
		this.rentindex = rentindex;
		this.isbn = isbn;
		this.title = title;
		this.type = type;
		this.publisher = publisher;
		this.author = author;
		this.id = id;
		this.rentdate = rentdate;
		this.returndate = returndate;
	}


//아이디 librent  이름libmem  책제목libbook   대여일librent  반납일librent
	public RentDto(int rentindex, String isbn, String title, String type, String publisher, String author, String id,
			String rentdate, String returndate, String name) {
		super();
		this.rentindex = rentindex;
		this.isbn = isbn;
		this.title = title;
		this.type = type;
		this.publisher = publisher;
		this.author = author;
		this.id = id;
		this.rentdate = rentdate;
		this.returndate = returndate;
		this.name = name;
	}

	public int getRentindex() {return rentindex;}
	public String getIsbn() {return isbn;}
	public String getTitle() {return title;}
	public String getType() {return type;}
	public String getPublisher() {return publisher;}
	public String getAuthor() {return author;}
	public String getId() {return id;}
	public String getRentdate() {return rentdate;}
	public String getReturndate() {return returndate;}
	public String getName() {return name;}
	
	public void setRentindex(int rentindex) {this.rentindex = rentindex;}
	public void setIsbn(String isbn) {this.isbn = isbn;}
	public void setTitle(String title) {this.title = title;}
	public void setType(String type) {this.type = type;}
	public void setPublisher(String publisher) {this.publisher = publisher;}
	public void setAuthor(String author) {this.author = author;}
	public void setId(String id) {this.id = id;}
	public void setRentdate(String rentdate) {this.rentdate = rentdate;}
	public void setReturndate(String returndate) {this.returndate = returndate;}	
	public void setName(String name) {this.name = name;}


	@Override
	public String toString() {
		return "RentDto [rentindex=" + rentindex + ", isbn=" + isbn + ", title=" 
				+ title + ", type=" + type+ ", publisher=" + publisher + ", author=" 
				+ author + ", id=" + id + ", rentdate=" + rentdate+ ", returndate=" + returndate + "]";
	}
}