package service;

import java.util.List;

import dao.RentDao;
import dto.RentDto;

public class RentService implements RentServiceImpl{

	RentDao dao = new RentDao();

	@Override
	public List<RentDto> getRentList(String loginID){
		// TODO Auto-generated method stub
		return dao.getRentList(loginID);
	}
	
	public List<RentDto> selectRentList(String loginID, String type, String txt) {
		return dao.  selectRentList(loginID, type, txt);
	}

	@Override
	public boolean checkMyRent(String id, String isbn) {
		// TODO Auto-generated method stub
		return dao.checkMyRent(id, isbn);
	}

	@Override
	public boolean addRent(String isbn, String id) {
		// TODO Auto-generated method stub
		return dao.addRent(isbn, id);
	}

	@Override
	public boolean deleteRent(String isbn) {
		// TODO Auto-generated method stub
		return dao.deleteRent(isbn);
	}

	@Override
	public List<RentDto> getList() {
		return dao.getList();
	}

	@Override
	public List<RentDto> selectList(String selectedOption, String txt) {		
		return dao.selectList(selectedOption, txt);
	}

}
