package service;

import java.util.List;
import dto.BookReviewDto;
import dto.BookReviewRepleDto;

public interface BookReviewServiceImpl {
	public BookReviewDto getBookReviewDto(int index);
	public List<BookReviewDto> getBookReviewList(String isbn);
	public double getAvgBookRate(String isbn);
	public boolean insertReview(BookReviewDto dto);
	public boolean updateReview(int index, String title, String content, int rate);
	public boolean removeReview(int index);
	public List<BookReviewRepleDto> getReviewRepleList(int review_index);
	public boolean addReviewReple(String id, String content, int review_index, int origin_reple_index, int step, int depth);
}
