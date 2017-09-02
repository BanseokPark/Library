package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBConn;
import dto.BookRequestDto;

public class BookRequestDao implements BookRequestDaoImpl{
	private Connection conn= null;
	private PreparedStatement psmt= null;
	private ResultSet rs= null;	
	
	@Override
	public boolean addBookRequest(BookRequestDto dto) {		
		
			int count = 0;

			String sql = " INSERT INTO LIB_BOOK_REQUEST (REQUEST_INDEX, TITLE, TYPE, PUBLISHER, AUTHOR, ID, REQUEST_DATE, STATE) "
				       + " VALUES(LIB_BOOK_REQUEST_SEQ.NEXTVAL, ?, ?, ?, ?, ?, SYSDATE, '신청중') ";

			try {
				System.out.println("\n[DB] addBookRequest");
				System.out.println("[SQL] " + sql);
				
				conn =  DBConn.getConnection();
				System.out.println("1. DB Connection Success");

				psmt = conn.prepareStatement(sql);
				System.out.println("2. psmt Connection Success");

				psmt.setString(1, dto.getTitle());
				psmt.setString(2, dto.getType());
				psmt.setString(3, dto.getPublisher());
				psmt.setString(4, dto.getAuthor());
				psmt.setString(5, dto.getId());

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
	public List<BookRequestDto> getBookRequestList(String id) {
		List<BookRequestDto> list = new ArrayList<BookRequestDto>();

		String sql = " SELECT REQUEST_INDEX, TITLE, TYPE, PUBLISHER, AUTHOR, ID, TO_CHAR(REQUEST_DATE, 'YY/MM/DD'), STATE "
					  + " FROM LIB_BOOK_REQUEST "
					  + " WHERE ID = '" + id + "' "
					  + " ORDER BY REQUEST_INDEX DESC ";
		
		try {
			System.out.println("\n[DB] getBookRequestList");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");

			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUpdate() Success");

			while(rs.next()){
				BookRequestDto dto = new BookRequestDto(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getString(5),
							rs.getString(6),
							rs.getString(7),
							rs.getString(8));
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

	public List<BookRequestDto> searchBookRequestList(String id, String type, String txt) {
		List<BookRequestDto> list = new ArrayList<BookRequestDto>();
		
		String sql = " SELECT REQUEST_INDEX, TITLE, TYPE, PUBLISHER, AUTHOR, ID, TO_CHAR(REQUEST_DATE, 'YY/MM/DD'), STATE "
				  + " FROM LIB_BOOK_REQUEST "
				  + " WHERE ID = '" + id + "' "
				  + " AND "  + type + " LIKE('%" + txt + "%') "
				  + " ORDER BY REQUEST_INDEX DESC ";	
		
		try {
			System.out.println("\n[DB] searchBookRequestList");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");

			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUpdate() Success");

			while(rs.next()){
				BookRequestDto dto = new BookRequestDto(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getString(5),
							rs.getString(6),
							rs.getString(7),
							rs.getString(8));
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
	public List<BookRequestDto> getRequestList() {
		List<BookRequestDto> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql = " SELECT REQUEST_INDEX, TITLE, TYPE, PUBLISHER, AUTHOR, ID, TO_CHAR(REQUEST_DATE, 'YY/MM/DD'), STATE "
				   + " FROM LIB_BOOK_REQUEST ";						
		

		try {
			System.out.println("\n[DB] getRequestList");
			System.out.println("[SQL] " + sql);
			
			conn = DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");

			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUpdate() Success");

			while(rs.next()){
				BookRequestDto dto = new BookRequestDto(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getString(5),
							rs.getString(6),
							rs.getString(7),
							rs.getString(8));
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
	public boolean RequestBuy(String id, String title) {
		
		int count = 0;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql = " UPDATE LIB_BOOK_REQUEST "
				   + " SET STATE = '구매완료' "
				   + " WHERE ID=? AND TITLE=?";	
		try {
			System.out.println("\n[DB] RequestComplete");
			System.out.println("[SQL] " + sql);
			
			conn = DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");
			
			psmt.setString(1, id);
			psmt.setString(2, title);			
			count = psmt.executeUpdate();
			System.out.println("3. psmt.executeUpdate() Success");
				

		} catch (SQLException e) {
			System.out.println("fail!!!!! ");
			e.printStackTrace();
			
		} finally{
			try {DBConn.close(rs, psmt, conn);}
			catch (SQLException e) {e.printStackTrace();}	
			System.out.println("4. DB Close Success");
		}			
		return count>0 ? true:false;
	}

	@Override
	public int getRequestBookCount() {
		int count = 0;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql = " SELECT COUNT(*) as CNT "
				   + " FROM LIB_BOOK_REQUEST "
				   + " WHERE STATE=?";	
		try {
			System.out.println("\n[DB] getRequestBookCount");
			System.out.println("[SQL] " + sql);
			
			conn = DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");
			
			psmt.setString(1, "신청중");
			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUpdate() Success");
			
			if(rs.next()) count = rs.getInt("CNT");

		} catch (SQLException e) {
			System.out.println("fail!!!!! ");
			e.printStackTrace();
			
		} finally{
			try {DBConn.close(rs, psmt, conn);}
			catch (SQLException e) {e.printStackTrace();}	
			System.out.println("4. DB Close Success");
		}			
		return count;
	}

}
