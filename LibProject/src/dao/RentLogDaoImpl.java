package dao;

import java.util.List;
import dto.RentLogDto;
import dto.RentLogRankDto;

public interface RentLogDaoImpl {

	public List<RentLogDto> getReturnList(String loginID); 
	public List<RentLogDto> selectRentLogList(String loginID, String type, String txt, String firstDate, String lastDate);
	public List<RentLogRankDto> selectRentRogRankList();
	public boolean addRentLog(String isbn, String id);
	public boolean addReturnLog(String isbn, String id);

}
