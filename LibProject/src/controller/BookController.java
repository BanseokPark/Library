package controller;

import java.util.List;
import javax.swing.JOptionPane;
import dto.BookDto;
import service.BookService;
import view.AdminBookAddView;
import view.BookDetailView;
import view.AdminView;

public class BookController {
	BookService bookService = new BookService();

	public void BookAdd(){
		new AdminBookAddView();
	}
	
	public void BookAdd(String id, String title, String author, String publisher){
		new AdminBookAddView(id, title, author, publisher);
	}

	public void Admin(){
		new AdminView();
	}
		
	public void BookAddAf(String image, String location, String title, String author, String publisher, 
			String code, String type, String info){

		BookDto dto = new BookDto(code, location, title, type, publisher, author, image, info, "대출 가능", null);
		boolean b  = bookService.BookAdd(dto);
		if(b){
			JOptionPane.showMessageDialog(null, "성공적으로 추가되었습니다");
			new AdminView();
		}else{				
			JOptionPane.showMessageDialog(null, "추가되지 못했습니다");
		}
	}
              
	public List<BookDto> getList(){
		return bookService.getList();
	}

	public List<BookDto> select(String selectedOption, String txt) {
		return bookService.getSelectList(selectedOption, txt);
	}

	public void showDetail(String bookcode) {
		BookDto dto = bookService.getDto(bookcode);
		System.out.println(bookcode);
		new BookDetailView(dto);
	}
	
	public void updateBook(String title, String author, String publisher, String type, String bookcode, String info){
		boolean b = bookService.updateBook(title,author,publisher,type,bookcode, info);
		if(b){
			JOptionPane.showMessageDialog(null, "수정 성공");			
		}else{
			JOptionPane.showMessageDialog(null, "수정 실패");
		}
		 
	}	
	
	public List<BookDto> getBookingList(String loginID){
		return bookService.getBookingList(loginID);
	}
	
	public boolean changeRentState(String isbn, String renstState){ // [user] 대여 상태 바꾸기
		return bookService.changeRentState(isbn, renstState);
	}
	
	public boolean checkBookingState(String isbn){ // [user] 대여 가능한 책인지 확인하기
		return bookService.checkBookingState(isbn);
	}
	
	public boolean addBookingID(String id, String isbn){
		return bookService.addBookingID(id, isbn);
	}



}
