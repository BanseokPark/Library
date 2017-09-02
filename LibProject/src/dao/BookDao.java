package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBConn;
import dto.BookDto;

public class BookDao implements BookDaoImpl{
	private Connection conn= null;
	private PreparedStatement psmt= null;
	private ResultSet rs= null;

	@Override
	public List<BookDto> getList() {
		
		List<BookDto> list = new ArrayList<BookDto>();
		
		String sql = "SELECT ISBN, LOCATION, TITLE, TYPE, PUBLISHER, AUTHOR, IMG, INFO, RENT_STATE, BOOKING_ID "
				   + " FROM LIB_BOOK "
				   + " ORDER BY ISBN ";
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
				BookDto dto = new BookDto(
						rs.getString("ISBN"),
						rs.getString("LOCATION"),
						rs.getString("TITLE"),
						rs.getString("TYPE"),
						rs.getString("PUBLISHER"),
						rs.getString("AUTHOR"),
						rs.getString("IMG"),
						rs.getString("INFO"),
						rs.getString("RENT_STATE"),
						rs.getString("BOOKING_ID")
				);
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

	public List<BookDto> getSelectList(String selectedOption, String txt) {
		
		List<BookDto> list = new ArrayList<BookDto>();

		String sql = "SELECT ISBN, LOCATION, TITLE, TYPE, PUBLISHER, AUTHOR, IMG, INFO, RENT_STATE, BOOKING_ID "
				   + " FROM LIB_BOOK ";

		switch (selectedOption) {
			case "제목":
				sql += " WHERE TITLE LIKE('%" + txt + "%') ";
				break;
			case "저자":
				sql += " WHERE AUTHOR LIKE('%" + txt + "%') ";
				break;
			case "출판사":
				sql += " WHERE PUBLISHER LIKE('%" + txt + "%') ";
				break;
			case "장르":
				sql += " WHERE TYPE LIKE('%" + txt + "%') ";
				break;
		}
		sql+=" ORDER BY ISBN ";
		
		try {
			System.out.println("\n[DB] getSelectList");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");

			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUpdate() Success");

			while(rs.next()){
				BookDto dto = new BookDto(
						rs.getString("ISBN"),
						rs.getString("LOCATION"),
						rs.getString("TITLE"),
						rs.getString("TYPE"),
						rs.getString("PUBLISHER"),
						rs.getString("AUTHOR"),
						rs.getString("IMG"),
						rs.getString("INFO"),
						rs.getString("RENT_STATE"),
						rs.getString("BOOKING_ID")
						);
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
	
	public BookDto getDto(String isbn) {
		
		BookDto dto = null;

		String sql = " SELECT ISBN, LOCATION, TITLE, TYPE, PUBLISHER, "
				   + " AUTHOR, IMG, INFO, RENT_STATE, BOOKING_ID "
			       + " FROM LIB_BOOK "
				   + " WHERE ISBN=? ";
		
		try {			
			System.out.println("\n[DB] getDto");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");
			
			psmt.setString(1,isbn);			
			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUpdate() Success");
			
			while(rs.next()){
				dto = new BookDto(
						rs.getString("ISBN"),
						rs.getString("LOCATION"),
						rs.getString("TITLE"),
						rs.getString("TYPE"),
						rs.getString("PUBLISHER"),
						rs.getString("AUTHOR"),
						rs.getString("IMG"),
						rs.getString("INFO"),
						rs.getString("RENT_STATE"),
						rs.getString("BOOKING_ID")
						);
			}
			System.out.println("4. into Dto Success");
			
		}catch (SQLException e){
			System.out.println("fail!!!!! ");
			e.printStackTrace();
			
		}finally{
			try {DBConn.close(rs, psmt, conn);}
			catch (SQLException e) {e.printStackTrace();}	
			System.out.println("5. DB Close Success");
		}
		
		return dto;
	}

	@Override
	public boolean BookAdd(BookDto dto) {
		
		int count = 0;

		String sql = "INSERT INTO LIB_BOOK(isbn, LOCATION, title, type, publisher, author, img, info, rent_state, booking_id) "
				   + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, '대여가능', null) ";

		try {
			System.out.println("\n[DB] BookAdd");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");

			psmt.setString(1, dto.getIsbn());
			psmt.setString(2, dto.getLocation());
			psmt.setString(3, dto.getTitle());
			psmt.setString(4, dto.getType());
			psmt.setString(5, dto.getPublisher());
			psmt.setString(6, dto.getAuthor());
			psmt.setString(7, dto.getImg());
			psmt.setString(8, dto.getInfo());

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
	public List<BookDto> getBookingList(String loginId) {
		
		List<BookDto> list = new ArrayList<BookDto>();
		
		String sql = " SELECT B.ISBN, B.LOCATION, B.TITLE, B.TYPE, B.PUBLISHER, B.AUTHOR, B.RENT_STATE, B.BOOKING_ID, TO_CHAR(R.RETURN_DATE, 'YY/MM/DD') "
				   + " FROM LIB_BOOK B, LIB_RENT R "
				   + " WHERE B.ISBN = R.ISBN "
				   + " AND BOOKING_ID = '" + loginId + "'";
				
		try {
			System.out.println("\n[DB] getBookingList");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");

			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUpdate() Success");

			while(rs.next()){
				BookDto dto = new BookDto(
							rs.getString(1),
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
	public boolean changeRentState(String isbn, String renstState) {
		
		int count = 0;

		String sql = " UPDATE LIB_BOOK SET RENT_STATE = '" + renstState + "' "
				   + " WHERE ISBN = '" + isbn + "' " ;

		try {
			System.out.println("\n[DB] changeRentState");
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
	public boolean checkBookingState(String isbn) { // 내가 대여한 도서인지 체크
		
		boolean check = false;
		
		String sql = " SELECT BOOKING_ID FROM LIB_BOOK "
				   + " WHERE ISBN = '" + isbn + "' ";
		
		try {
			System.out.println("\n[DB] checkBookingState");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");

			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUpdate() Success");

			while(rs.next()){
				if(rs.getString(1) == null){ // 예약이 되어있지 않으면
					check = true; // 예약 가능
				}else{
					check = false; // 예약 불가
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
	public boolean addBookingID(String id, String isbn) {
		
		int count = 0;

		String sql = " UPDATE LIB_BOOK "
			       + " SET BOOKING_ID = '" + id + "' "
				   + " WHERE ISBN = '" + isbn + "' " ;

		try {
			System.out.println("\n[DB] addBookingID");
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
	public boolean updateBook(String title, String author, String publisher, String type, String bookcode, String info) {
		int count = 0;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		String sql = "UPDATE LIB_BOOK"
				 + " SET TITLE=?, AUTHOR=?, PUBLISHER=?, TYPE=?, INFO=?"
				 + " WHERE ISBN=?";
		
		try {
			conn = DBConn.getConnection();
			System.out.println("2/5 getMemList Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("3/5 getMemList Success");
				
			psmt.setString(1, title);
			psmt.setString(2, author);
			psmt.setString(3, publisher);
			psmt.setString(4, type);
			psmt.setString(5, info);
			psmt.setString(6, bookcode);
						
			count = psmt.executeUpdate();
			System.out.println("count : " + count);
			System.out.println("4/5 getMemList Success");

		} catch (SQLException e) {
			System.out.println("getMemList fail");
		} finally{
			try {DBConn.close(null, psmt, conn);}
			catch (SQLException e) {e.printStackTrace();}	
			System.out.println("5/5 getMemList Success");
		}	
		
		
		return count>0 ? true:false;
		
	}	

}
