package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBConn;
import dto.RentLogDto;
import dto.RentLogRankDto;

public class RentLogDao implements RentLogDaoImpl {
	private Connection conn= null;
	private PreparedStatement psmt= null;
	private ResultSet rs= null;
		
	@Override
	public List<RentLogDto> getReturnList(String loginID) {
		List<RentLogDto> list = new ArrayList<RentLogDto>();

		String sql = " SELECT R.LOG_INDEX, B.ISBN, B.TITLE, B.TYPE, B.PUBLISHER, B.AUTHOR, R.ID, R.RENT_STATE, TO_CHAR(R.RENT_DATE, 'YY/MM/DD') "
					  + " FROM LIB_RENTlOG R, LIB_BOOK B  "
					  + " WHERE R.ISBN = B.ISBN "
					  + " AND ID = '" + loginID + "' "
					  + " ORDER BY R.RENT_DATE DESC ";
		
		try {
			System.out.println("\n[DB] getReturnList");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");

			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUpdate() Success");

			while(rs.next()){
				RentLogDto dto = new RentLogDto(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getString(5),
							rs.getString(6),
							rs.getString(7),
							rs.getString(8),
							rs.getString(9));
				list.add(dto);					

			}					
			System.out.println("4. into Dto Success");

		} catch (SQLException e) {
			System.out.println("fail!!!!! ");
			e.printStackTrace();
		} finally{
			try {DBConn.close(rs, psmt, conn);}
			catch (SQLException e) {e.printStackTrace();}	
			System.out.println("5. DB Close Success");
		}		
		return list;
	}

	public List<RentLogDto> selectRentLogList(String loginID, String type, String txt, String firstDate, String lastDate) {
		
		List<RentLogDto> list = new ArrayList<RentLogDto>();
		
		String sql = null;
		String sql1 = " SELECT R.LOG_INDEX, B.ISBN, B.TITLE, B.TYPE, B.PUBLISHER, B.AUTHOR, R.ID, R.RENT_STATE, TO_CHAR(R.RENT_DATE, 'YY/MM/DD') "
				 + " FROM LIB_RENTlOG R, LIB_BOOK B  "
				 + " WHERE R.ISBN = B.ISBN "
				 + " AND ID = '" + loginID + "' ";
		
		if(firstDate == null || lastDate == null){ // 단어로 검색				

			String case1 = " AND "  + type + " LIKE('%" + txt + "%') ";
			sql = sql1 + case1;

		}else if(type == null || txt == null){ // 날짜로 검색
	
			String case2 = " AND R.RENT_DATE >= '"  + firstDate + "' AND R.RENT_DATE <= TO_DATE('" + lastDate + " 23:59:59', 'yy/mm/dd hh24:mi:ss') ";
			sql = sql1 + case2;
		}else{
			String case3 = " AND "  + type + " LIKE('%" + txt + "%') AND R.RENT_DATE >= '" + firstDate + "' AND R.RENT_DATE <= TO_DATE('" + lastDate + " 23:59:59', 'yy/mm/dd hh24:mi:ss') ";
			sql = sql1 + case3;
			
		}
		sql+=" ORDER BY R.RENT_DATE DESC ";			

		try {
			System.out.println("\n[DB] selectRentLogList");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");

			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUpdate() Success");

			while(rs.next()){
				RentLogDto dto = new RentLogDto(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getString(5),
							rs.getString(6),
							rs.getString(7),
							rs.getString(8),
							rs.getString(9));
				list.add(dto);					

			}			
			System.out.println("4. into Dto Success");

		} catch (SQLException e) {
			System.out.println("fail!!!!! ");
			e.printStackTrace();
		} finally{
			try {DBConn.close(rs, psmt, conn);}
			catch (SQLException e) {e.printStackTrace();}	
			System.out.println("5. DB Close Success");
		}		
		return list;
	}
	
	@Override
	public List<RentLogRankDto> selectRentRogRankList() {
		
		List<RentLogRankDto> list = new ArrayList<RentLogRankDto>();
		
		String sql = 	" SELECT ROW_NUMBER() OVER (ORDER BY COUNT(*) DESC, LOG.ISBN ASC) AS RANK, LOG.ISBN, BOOK.TITLE, "
								+ " BOOK.TYPE, BOOK.PUBLISHER, BOOK.AUTHOR, COUNT(*) AS RENT_COUNT "
				        + " FROM LIB_RENTLOG LOG, LIB_BOOK BOOK "
				        + " WHERE LOG.ISBN = BOOK.ISBN "
				        + " GROUP BY LOG.ISBN , BOOK.TITLE, BOOK.TYPE, BOOK.PUBLISHER, BOOK.AUTHOR ";

		try {
			System.out.println("\n[DB] selectRentRogRankList");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");

			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUpdate() Success");

			while(rs.next()){
				RentLogRankDto dto = new RentLogRankDto(
							rs.getInt("RANK"),
							rs.getString("ISBN"),
							rs.getString("TITLE"),
							rs.getString("TYPE"),
							rs.getString("PUBLISHER"),
							rs.getString("AUTHOR"),
							rs.getInt("RENT_COUNT"));
				list.add(dto);					
			}			
			System.out.println("4. into Dto Success");
			
		} catch (SQLException e) {
			System.out.println("fail!!!!! ");
			e.printStackTrace();
		} finally{
			try {DBConn.close(rs, psmt, conn);}
			catch (SQLException e) {e.printStackTrace();}	
			System.out.println("5. DB Close Success");
		}		
		return list;
	}

	@Override
	public boolean addRentLog(String isbn, String id) {
		
		int count = 0;
			
		String sql = " INSERT INTO LIB_RENTLOG(LOG_INDEX, ISBN, ID, RENT_STATE, RENT_DATE) "
	    		   + " VALUES(LIB_RENTLOG_SEQ.NEXTVAL,'" + isbn + "', '" + id + "', '대여', SYSDATE) ";

		try {
			System.out.println("\n[DB] addRentLog");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");
			
			count = psmt.executeUpdate();
			System.out.println("3. psmt.executeUpdate() Success");

		} catch (SQLException e) {			
			System.out.println("fail!!!!! ");
			e.printStackTrace();
		} finally{
			try {DBConn.close(null, psmt, conn);}
			catch (SQLException e) {e.printStackTrace();}	
			System.out.println("4. DB Close Success");		
		}

		return count>0 ? true:false;			

	}
	
	@Override
	public boolean addReturnLog(String isbn, String id) {
		int count = 0;

		Connection conn = null;
		PreparedStatement psmt = null;
			
		String sql = " INSERT INTO LIB_RENTLOG(LOG_INDEX, ISBN, ID, RENT_STATE, RENT_DATE) "
	    		   + " VALUES(LIB_RENTLOG_SEQ.NEXTVAL,'" + isbn + "', '" + id + "', '반납', SYSDATE) ";
				
		try {
			System.out.println("\n[DB] addReturnLog");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");
			
			count = psmt.executeUpdate();
			System.out.println("3. psmt.executeUpdate() Success");

		} catch (SQLException e) {	
			System.out.println("fail!!!!! ");
			e.printStackTrace();
		} finally{
			try {DBConn.close(null, psmt, conn);}
			catch (SQLException e) {e.printStackTrace();}	
			System.out.println("4. DB Close Success");			
		}

		return count>0 ? true:false;			

	}
}
