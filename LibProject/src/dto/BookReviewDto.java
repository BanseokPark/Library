package dto;

import java.io.Serializable;

public class BookReviewDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private int index;
	private String isbn;
	private String booktitle;
	private String id;
	private String name;
	private String title;
	private String content;
	private String writedate;
	private int rate;

	public BookReviewDto(int index, String isbn, String booktitle, String id,
			String name, String title, String content, String writedate, int rate) {
		this.index = index;
		this.isbn = isbn;
		this.booktitle = booktitle;
		this.id = id;
		this.name = name;
		this.title = title;
		this.content = content;
		this.writedate = writedate;
		this.rate = rate;
	}
	
	public int getIndex() {return index;}
	public String getIsbn() {return isbn;}
	public String getBooktitle() {return booktitle;}
	public String getId() {return id;}
	public String getName() {return name;}
	public String getTitle() {return title;}
	public String getContent() {return content;}
	public String getWritedate() {return writedate;}
	public int getRate() {return rate;}

	public void setIndex(int index) {this.index = index;}
	public void setIsbn(String isbn) {this.isbn = isbn;}
	public void setBooktitle(String booktitle) {this.booktitle = booktitle;}
	public void setId(String id) {this.id = id;}
	public void setName(String name) {this.name = name;}
	public void setTitle(String title) {this.title = title;}
	public void setContent(String content) {this.content = content;}
	public void setRate(int rate) {this.rate = rate;}
	public void setWritedate(String writedate) {this.writedate = writedate;}
	
	@Override
	public String toString() {
		return "BookReviewDto [index=" + index + ", isbn=" + isbn + ", booktitle=" + booktitle + ", id=" + id + ", name="
				+ name + ", title=" + title + ", content=" + content + ", writedate=" + writedate + ", rate=" + rate + "]";
	}
}
