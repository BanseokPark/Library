package service;

import java.util.List;

import dao.BookReviewDao;
import dto.BookReviewDto;
import dto.BookReviewRepleDto;

public class BookReviewService implements BookReviewServiceImpl{
	BookReviewDao dao = new BookReviewDao();
	
	@Override
	public BookReviewDto getBookReviewDto(int index) {
		return dao.getBookReviewDto(index);
	}
	
	@Override
	public List<BookReviewDto> getBookReviewList(String isbn) {
		return dao.getBookReviewList(isbn);
	}
	
	@Override
	public double getAvgBookRate(String isbn) {
		return dao.getAvgBookRate(isbn);
	}

	@Override
	public boolean insertReview(BookReviewDto dto) {
		return dao.insertReview(dto);
	}

	@Override
	public boolean updateReview(int index, String title, String content, int rate) {
		return dao.updateReview(index, title, content, rate);
	}
	
	@Override
	public boolean removeReview(int index) {
		return dao.removeReview(index);
	}

	@Override
	public List<BookReviewRepleDto> getReviewRepleList(int review_index) {
		return dao.getReviewRepleList(review_index);
	}
	
	@Override
	public boolean addReviewReple(String id, String content, int review_index, int origin_reple_index, int step, int depth) {
		return dao.addReviewReple(id, content, review_index, origin_reple_index, step, depth);
	}

	


}
