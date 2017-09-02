package service;

import java.util.List;

import dto.BookDto;

public interface BookServiceImpl {
	public List<BookDto> getList();
	public List<BookDto> getSelectList(String selectedOption, String txt);
	public boolean BookAdd(BookDto dto);
	public BookDto getDto(String bookcode);
	public List<BookDto> getBookingList(String loginId);
	public boolean changeRentState(String isbn, String renstState);
	public boolean checkBookingState(String isbn);
	public boolean addBookingID(String id, String isbn);
	public boolean updateBook(String title, String author, String publisher, String type, String bookcode, String info);
}
