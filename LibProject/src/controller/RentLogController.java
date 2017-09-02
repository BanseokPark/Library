package controller;

import java.util.List;

import dto.MemberDto;
import dto.RentLogDto;
import dto.RentLogRankDto;
import service.RentLogService;
import view.UserRentLogView;

public class RentLogController {
	
	RentLogService rentLogService = new RentLogService();
	
	public void UserRentLogView(MemberDto myInfo) {	
		new UserRentLogView(myInfo);
	}
	
	public List<RentLogDto> getReturnList(String loginID){
		return rentLogService.getRentList(loginID);
	}
	
	public List<RentLogDto> selectRentLogList(String loginID, String type, String txt, String startDate, String lastDate) {
		return rentLogService.selectRentLogList(loginID, type, txt, startDate, lastDate);
	}
	
	public List<RentLogRankDto> getRentRogRankList() {
		return rentLogService.selectRentRogRankList();
	}
	
	public boolean addRentLog(String isbn, String id){
		return rentLogService.addRentLog(isbn, id);
	}
	
	public boolean addReturnLog(String isbn, String id){
		return rentLogService.addReturnLog(isbn, id);
	}
}
