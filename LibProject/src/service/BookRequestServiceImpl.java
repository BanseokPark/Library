package service;

import java.util.List;
import dto.BookRequestDto;

public interface BookRequestServiceImpl {
	public boolean addBookRequest(BookRequestDto dto);
	public List<BookRequestDto> getBookRequestList(String id);
	public List<BookRequestDto> searchBookRequestList(String id, String type, String txt);
	public List<BookRequestDto> getRequestList();
	public boolean RequestBuy(String id, String title);
	public int getRequestBookCount();
}
