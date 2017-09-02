package dao;

import java.util.List;

import dto.RentDto;

public interface RentDaoImpl {	

	public List<RentDto> getRentList(String loginID); 
	public List<RentDto> selectRentList(String loginID, String type, String txt);
	public boolean checkMyRent(String id, String isbn); // 내가 대여한 도서인지 확인
	public boolean addRent(String isbn, String id); // 대여
	public boolean deleteRent(String isbn); // 반납 : 대여목록에서 삭제
	public List<RentDto> getList();
	public List<RentDto> selectList(String selectedOption, String txt);
}
