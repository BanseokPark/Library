package service;

import java.util.List;

import dao.BookDao;
import dto.BookDto;

public class BookService implements BookServiceImpl{
	BookDao dao = new BookDao();
	@Override
	public List<BookDto> getList() {
		return dao.getList();
	}
	@Override
	public List<BookDto> getSelectList(String selectedOption, String txt) {
		return dao. getSelectList(selectedOption, txt);
	}
	@Override
	public BookDto getDto(String bookcode) {
		return dao.getDto(bookcode);
	}
	
	@Override
	public boolean BookAdd(BookDto dto) {				
		return dao.BookAdd(dto);
	}
	
	@Override
	public List<BookDto> getBookingList(String loginId) {
		// TODO Auto-generated method stub
		return dao.getBookingList(loginId);
	}

	@Override
	public boolean changeRentState(String isbn, String renstState) {
		// TODO Auto-generated method stub
		return dao.changeRentState(isbn, renstState);
	}
	
	@Override
	public boolean checkBookingState(String isbn) { // 예약가능한 책인지 확인
		// TODO Auto-generated method stub
		return dao.checkBookingState(isbn);
	}
	
	@Override
	public boolean addBookingID(String id, String isbn) { // 예약
		// TODO Auto-generated method stub
		return dao.addBookingID(id, isbn);
	}
	@Override
	public boolean updateBook(String title, String author, String publisher, String type, String bookcode, String info) {
		return dao.updateBook(title, author, publisher, type, bookcode, info);		
	}

}
