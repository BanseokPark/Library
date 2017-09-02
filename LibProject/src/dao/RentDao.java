package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBConn;
import dto.RentDto;

public class RentDao implements RentDaoImpl{	
	private Connection conn= null;
	private PreparedStatement psmt= null;
	private ResultSet rs= null;
	
	@Override
	public List<RentDto> getRentList(String loginId) {
		
		List<RentDto> list = new ArrayList<RentDto>();
		
		String sql = " SELECT R.RENT_INDEX, B.ISBN, B.TITLE, B.TYPE, B.PUBLISHER, B.AUTHOR, R.ID, TO_CHAR(R.RENT_DATE, 'YY/MM/DD'), TO_CHAR(R.RETURN_DATE) "
					  + " FROM LIB_RENT R, LIB_BOOK B "
					  + " WHERE R.ISBN = B.ISBN"
					  + " AND ID = '" + loginId + "'";
		
		try {
			System.out.println("\n[DB] getRentList");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");

			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUpdate() Success");

			while(rs.next()){
				RentDto dto = new RentDto(
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
	public List<RentDto> getList() {
		
		List<RentDto> list = new ArrayList<>();
		
		String sql = " SELECT R.RENT_INDEX, R.ISBN, B.TITLE, B.TYPE, B.PUBLISHER, B.AUTHOR, "
				   + " M.ID, R.RENT_DATE, R.RETURN_DATE, M.NAME "
				   + " FROM LIB_MEMBER M, LIB_BOOK B, LIB_RENT R "
				   + " WHERE M.ID = R.ID AND R.ISBN = B.ISBN ";
		
		try {
			System.out.println("\n[DB] getList");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");
			
			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUpdate() Success");
			
			while(rs.next()){
				RentDto dto = new RentDto(rs.getInt(1),
											rs.getString(2),
											rs.getString(3),
											rs.getString(4), 
											rs.getString(5),
											rs.getString(6),
											rs.getString(7),
											rs.getString(8),
											rs.getString(9),
											rs.getString(10));
				list.add(dto);
			}
			//for (int i = 0; i < list.size(); i++) { System.out.println(list.get(i).toString()); }
			System.out.println("4. into Dto Success");
			
		} catch (SQLException e) {			
			e.printStackTrace();
			System.out.println("fail!!!!! ");
		} finally {
			try {DBConn.close(rs, psmt, conn);}
			catch (SQLException e) {e.printStackTrace();}	
			System.out.println("5. DB Close Success");
		}
		return list;
	}
	
	public List<RentDto> selectRentList(String loginId, String type, String txt) {
		
		List<RentDto> list = new ArrayList<RentDto>();

		String sql = " SELECT R.RENT_INDEX, B.ISBN, B.TITLE, B.TYPE, B.PUBLISHER, B.AUTHOR, R.ID, TO_CHAR(R.RENT_DATE, 'YY/MM/DD'), TO_CHAR(R.RETURN_DATE) "
				  + " FROM LIB_RENT R, LIB_BOOK B "
				  + " WHERE R.ISBN = B.ISBN"
				  + " AND ID = '" + loginId + "' "
				  + " AND " + type + " LIKE('%" + txt + "%') ";

		try {
			System.out.println("\n[DB] selectRentList");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");

			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUpdate() Success");

			while(rs.next()){
				RentDto dto = new RentDto(
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
	public boolean checkMyRent(String id, String isbn) { // 내가 대여한 도서인지 체크
		
		boolean check = false;
		
		String sql = " SELECT ISBN, ID FROM LIB_RENT "
					  + " WHERE ID = '" + id + "' "
					  + " AND ISBN = '" + isbn + "'";
				
		try {
			System.out.println("\n[DB] checkMyRent");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");

			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUpdate() Success");

			while(rs.next()){
				if(rs.getString(1) != null){ // 내가 대여한 책이라면
					check = true; // 반납 가능
				}else{
					check = false; // 반납 불가
				}
			}		
			System.out.println("4. check Success");

		} catch (SQLException e) {
			System.out.println("fail!!!!! ");
			e.printStackTrace();
		} finally{
			try {DBConn.close(rs, psmt, conn);}
			catch (SQLException e) {e.printStackTrace();}	
			System.out.println("5. DB Close Success");
		}	

		return check;
	}
	
	@Override
	public boolean addRent(String isbn, String id) {
		int count = 0;
			
		String sql = " INSERT INTO LIB_RENT(RENT_INDEX, ISBN, ID, RENT_DATE, RETURN_DATE) "
	    		   + " VALUES(LIB_RENT_SEQ.NEXTVAL,'" + isbn + "', '" + id + "', SYSDATE, (SYSDATE+7)) ";
		
		try {
			System.out.println("\n[DB] addRent");
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
	public boolean deleteRent(String isbn) {
		
		int count = 0;
			
		String sql = " DELETE FROM LIB_RENT "
	    		   + " WHERE ISBN = '" + isbn + "' ";
		
		try {
			System.out.println("\n[DB] deleteRent");
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
	public List<RentDto> selectList(String selectedOption, String txt) {
		
		List<RentDto> list = new ArrayList<>();
		String choice = null;
		
		switch(selectedOption){
		
		case "아이디":
			choice = " M.ID LIKE ('%"+txt+"%')";		
			break;
			
		case "이름":
			choice = " M.NAME LIKE ('%"+txt+"%')";
			break;
			
		case "제목":
			choice = " B.TITLE LIKE ('%"+txt+"%')";
			break;
		}
		
		String sql = "SELECT R.RENT_INDEX, R.ISBN, B.TITLE, B.TYPE, B.PUBLISHER, B.AUTHOR, "
				+ " M.ID, R.RENT_DATE, R.RETURN_DATE, M.NAME "
				+ " FROM LIB_MEMBER M, LIB_BOOK B, LIB_RENT R "
				+ " WHERE M.ID = R.ID AND R.ISBN = B.ISBN AND " + choice;
		
		try {
			System.out.println("\n[DB] selectList");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");
			
			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUpdate() Success");
			
			while(rs.next()){
				RentDto dto = new RentDto(rs.getInt(1),
											rs.getString(2),
											rs.getString(3),
											rs.getString(4), 
											rs.getString(5),
											rs.getString(6),
											rs.getString(7),
											rs.getString(8),
											rs.getString(9),
											rs.getString(10));
				list.add(dto);
			}
			//for (int i = 0; i < list.size(); i++) {	System.out.println(list.get(i).toString());}
			System.out.println("4. into Dto Success");
			
		} catch (SQLException e) {	
			System.out.println("fail!!!!! ");
			e.printStackTrace();
		} finally {
			try {DBConn.close(rs, psmt, conn);}
			catch (SQLException e) {e.printStackTrace();}	
			System.out.println("5. DB Close Success");
		}				
		return list;
	}

}
