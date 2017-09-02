package service;

import java.util.List;
import dao.BookRequestDao;
import dto.BookRequestDto;

public class BookRequestService implements BookRequestServiceImpl{
	BookRequestDao dao = new BookRequestDao();

	@Override
	public boolean addBookRequest(BookRequestDto dto) {
		// TODO Auto-generated method stub
		return dao.addBookRequest(dto);
	}

	@Override
	public List<BookRequestDto> getBookRequestList(String id) {
		// TODO Auto-generated method stub
		return dao.getBookRequestList(id);
	}

	@Override
	public List<BookRequestDto> searchBookRequestList(String id, String type, String txt) {
		// TODO Auto-generated method stub
		return dao.searchBookRequestList(id, type, txt);
	}
	
	@Override
	public List<BookRequestDto> getRequestList() {		
		return dao.getRequestList();
	}

	@Override
	public boolean RequestBuy(String id, String title) {		
		return dao.RequestBuy(id, title);
	}
	@Override
	public int getRequestBookCount() {
		return dao.getRequestBookCount();
	}
	
	
		

}
