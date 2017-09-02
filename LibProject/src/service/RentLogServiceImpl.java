package service;

import java.util.List;

import dto.RentLogDto;
import dto.RentLogRankDto;

public interface RentLogServiceImpl {
	public List<RentLogDto> getRentList(String loginID);
	public List<RentLogRankDto> selectRentRogRankList();
	public boolean addRentLog(String isbn, String id);
	public boolean addReturnLog(String isbn, String id);
	public List<RentLogDto> selectRentLogList(String loginID, String type, String txt, String firstDate, String lastDate);
}
