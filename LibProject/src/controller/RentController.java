package controller;

import java.util.List;

import dto.RentDto;
import service.RentService;
import view.AdminRentStateView;

public class RentController {
	
	RentService rentService = new RentService();
	
	public void rentState(){
		new AdminRentStateView();
	}	
	public List<RentDto> getList(){
		return rentService.getList();
	}
	
	public List<RentDto> getRentList(String loginID){
		return rentService.getRentList(loginID);
	}
	
	public List<RentDto> searchRentList(String loginID, String type, String txt) {
		return rentService.selectRentList(loginID, type, txt);
	}

	public boolean checkMyRent(String id, String isbn){
		return rentService.checkMyRent(id, isbn);
	}
	
	public boolean addRent(String isbn, String id){
		return rentService.addRent(isbn, id);
	}	
	
	public boolean deleteRent(String isbn){
		return rentService.deleteRent(isbn);
	}
	
	public List<RentDto> selectList(String selectedOption, String txt){
		return rentService.selectList(selectedOption, txt);
	}
}
