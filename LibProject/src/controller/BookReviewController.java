package controller;

import java.util.List;

import dto.BookDto;
import dto.BookReviewDto;
import dto.BookReviewRepleDto;
import service.BookReviewService;
import service.BookService;
import view.ReviewAddView;
import view.ReviewDetailView;

public class BookReviewController {
	BookService bookService = new BookService();
	BookReviewService bookReviewService = new BookReviewService();

	
	public List<BookReviewDto> getBookReviewList(String isbn) {
		return bookReviewService.getBookReviewList(isbn);
	}

	public double getAvgBookRate(String isbn) {
		return bookReviewService.getAvgBookRate(isbn);
	}

	public void reviewAdd(String isbn) {
		new ReviewAddView(bookService.getDto(isbn));
	}

	public boolean reviewAddAf(BookReviewDto dto) {
		return bookReviewService.insertReview(dto);
	}
	
	public void showReviewDetail(BookDto dto, int index) {
		new ReviewDetailView(dto, bookReviewService.getBookReviewDto(index));
	}

	public boolean reviewUpdate(int index, String title, String content, int rate) {
		return bookReviewService.updateReview(index, title, content, rate);
		
	}

	public boolean reviewDelete(int index) {
		return bookReviewService.removeReview(index);
	}

	public List<BookReviewRepleDto> getReviewRepleList(int index) {
		return bookReviewService.getReviewRepleList(index);
	}

	public boolean ReviewRepleAdd(String id, String content, int review_index, int group, int step, int depth) {
		return bookReviewService.addReviewReple(id, content, review_index, group, step, depth);
	}
	
}
