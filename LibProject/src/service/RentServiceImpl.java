package service;

import java.util.List;

import dto.RentDto;

public interface RentServiceImpl {
	public List<RentDto> getRentList(String loginID);
	public List<RentDto> selectRentList(String loginID, String type, String txt);
	public boolean checkMyRent(String id, String isbn);	
	public boolean addRent(String isbn, String id);
	public boolean deleteRent(String isbn);
	public List<RentDto> getList();
	public List<RentDto> selectList(String selectedOption, String txt);
}
