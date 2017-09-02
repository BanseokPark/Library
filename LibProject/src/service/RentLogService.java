package service;

import java.util.List;

import dao.RentLogDao;
import dto.RentLogDto;
import dto.RentLogRankDto;

public class RentLogService implements RentLogServiceImpl{
	
	RentLogDao dao = new RentLogDao();

	@Override
	public List<RentLogDto> getRentList(String loginID){
		return dao.getReturnList(loginID);
	}
	
	@Override
	public List<RentLogDto> selectRentLogList(String loginID, String type, String txt, String firstDate, String lastDate) {
		// TODO Auto-generated method stub
		return dao.selectRentLogList(loginID, type, txt, firstDate, lastDate);
	}
	@Override
	public List<RentLogRankDto> selectRentRogRankList() {
		return dao.selectRentRogRankList();
	}

	@Override
	public boolean addRentLog(String isbn, String id) {
		// TODO Auto-generated method stub
		return dao.addRentLog(isbn, id);
	}

	@Override
	public boolean addReturnLog(String isbn, String id) {
		// TODO Auto-generated method stub
		return dao.addReturnLog(isbn, id);
	}
		
}
