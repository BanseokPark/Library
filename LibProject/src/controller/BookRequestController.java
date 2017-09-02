package controller;

import java.util.List;

import dto.BookRequestDto;
import service.BookRequestService;
import view.AdminBookRequestListView;

public class BookRequestController {	
	BookRequestService bookRequestService = new BookRequestService();
	
	//////////////// VIEW //////////////

	public void getRequestListView(){
		new AdminBookRequestListView();
	}
	
	
	//////////////// DB //////////////
	public boolean addBookRequest(BookRequestDto dto) {
		// TODO Auto-generated method stub
		return bookRequestService.addBookRequest(dto);
	}
	
	public List<BookRequestDto> getBookRequestList(String id){
		return bookRequestService.getBookRequestList(id);
	}
	
	public List<BookRequestDto> searchBookRequestList(String id, String type, String txt){
		return bookRequestService.searchBookRequestList(id, type, txt);
	}
	
	public List<BookRequestDto> getRequestList(){	// admin 요청 목록 불러오기
		return bookRequestService.getRequestList();
	}
	
	public boolean RequestBuy(String id, String title){
		return bookRequestService.RequestBuy(id, title); 		
	}


	public int getRequestBookCount() {
		return bookRequestService.getRequestBookCount();
	}
	


}
