package dto;

import java.io.Serializable;

/**
 * @author user2
 *
 */
public class RentLogRankDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int rank;		// 대여기록
	private String isbn;		// 	
	private String title;
	private String type;
	private String publisher;
	private String author;
	private int rent_count;
	
	public RentLogRankDto(int rank, String isbn, String title, String type, String publisher, String author,
		int rent_count) {
		this.rank = rank;
		this.isbn = isbn;
		this.title = title;
		this.type = type;
		this.publisher = publisher;
		this.author = author;
		this.rent_count = rent_count;
	}
	public int getRank() {return rank;}
	public String getIsbn() {return isbn;}
	public String getTitle() {return title;}
	public String getType() {return type;}
	public String getPublisher() {return publisher;}
	public String getAuthor() {return author;}
	public int getRent_count() {return rent_count;}
	
	public void setRank(int rank) {this.rank = rank;}
	public void setIsbn(String isbn) {this.isbn = isbn;}
	public void setTitle(String title) {this.title = title;}
	public void setType(String type) {this.type = type;}
	public void setPublisher(String publisher) {this.publisher = publisher;}
	public void setAuthor(String author) {this.author = author;}
	public void setRent_count(int rent_count) {this.rent_count = rent_count;}
	
	@Override public String toString() {
		return "RentLogRankDto [rank=" + rank + ", isbn=" + isbn + ", title=" + title + ", type=" + type + ", publisher="
				+ publisher + ", author=" + author + ", rent_count=" + rent_count + "]";
	}
}