package dao;

import java.util.List;

import dto.BookDto;

public interface BookDaoImpl {
	public List<BookDto> getList(); 
	public List<BookDto> getSelectList(String selectedOption, String txt);
	public boolean BookAdd(BookDto dto);
	public BookDto getDto(String bookcode);
	public List<BookDto> getBookingList(String loginID);
	public boolean changeRentState(String isbn, String renstState);
	public boolean checkBookingState(String isbn); // 예약가능한 책인지 확인
	public boolean addBookingID(String id, String isbn); // 예약
	public boolean updateBook(String title, String author, String publisher, String type, String bookcode, String info);;
}
