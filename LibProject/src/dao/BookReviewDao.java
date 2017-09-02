package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBConn;
import dto.BookReviewDto;
import dto.BookReviewRepleDto;

public class BookReviewDao implements BookReviewDaoImpl{
	private Connection conn= null;
	private PreparedStatement psmt= null;
	private ResultSet rs= null;

	@Override
	public List<BookReviewDto> getBookReviewList(String isbn) {
		List<BookReviewDto> list = new ArrayList<BookReviewDto>();
		String sql = " SELECT R.REVIEW_INDEX, R.ISBN, B.TITLE AS BOOKTITLE,"
				+ " R.ID, M.NAME, R.TITLE, R.CONTENT, TO_CHAR(WRITE_DATE, 'YY/MM/DD HH24:MI:SS') AS WRITE_DATE, R.RATE "
				+ " FROM LIB_BOOK_REVIEW R, LIB_MEMBER M, LIB_BOOK B "
				+ " WHERE R.ID = M.ID AND R.ISBN = B.ISBN "
				+ " AND R.ISBN = ?"
				+ " ORDER BY WRITE_DATE DESC ";

		System.out.println("\n[DB] getBookReviewList");
		System.out.println("[SQL] " + sql);

		try {
			conn = DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, isbn);
			System.out.println("2. psmt Connection Success");
			
			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeQuery() Success");

			while(rs.next()){
				BookReviewDto dto = new BookReviewDto(
						rs.getInt("REVIEW_INDEX"),
						rs.getString("ISBN"),
						rs.getString("BOOKTITLE"),
						rs.getString("ID"),
						rs.getString("NAME"),
						rs.getString("TITLE"),
						rs.getString("CONTENT"),
						rs.getString("WRITE_DATE"),
						rs.getInt("RATE"));
				list.add(dto);		
			}		
			System.out.println("4. into Dto Success");

		} catch (SQLException e) {
			System.out.println("fail!!!!!");
		} finally{
			try {	DBConn.close(rs, psmt, conn);}
			catch (SQLException e) {e.printStackTrace();}
			System.out.println("5 DBClose Success");
		}		
		return list;
	}

	@Override
	public double getAvgBookRate(String isbn) {
		double avg_rate = 0;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		String sql = "SELECT AVG(RATE) AS AVGRATE "
				+ " FROM LIB_BOOK_REVIEW "
				+ " GROUP BY ISBN "
				+ " HAVING ISBN = ? ";

		System.out.println("\n[DB] getAvgBookRate");
		System.out.println("[SQL] " + sql);
		
		try {
			conn = DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");
			psmt.setString(1, isbn);

			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeQuery() Success");

			if(rs.next()){
				avg_rate = rs.getDouble("AVGRATE");
			}		
			System.out.println("4. GET avg_rate Success");

		} catch (SQLException e) {
			System.out.println("fail");
		} finally{
			try {DBConn.close(rs, psmt, conn);}
			catch (SQLException e) {e.printStackTrace();}
			System.out.println("5. DB Close Success");
		}		
		return avg_rate;
	}

	@Override
	public BookReviewDto getBookReviewDto(int index) {
		BookReviewDto dto = null;

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		String sql = " SELECT REVIEW_INDEX, R.ISBN, B.TITLE AS BOOKTITLE, R.ID, M.NAME, R.TITLE, CONTENT, WRITE_DATE, RATE "
				+ " FROM LIB_BOOK_REVIEW R, LIB_BOOK B, LIB_MEMBER M "
				+ " WHERE R.ISBN = B.ISBN AND R.ID = M.ID AND REVIEW_INDEX=? ";
		System.out.println("\n[DB] getBookReviewDto");
		System.out.println("[SQL] " + sql);  

		try {
			conn = DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, index);
			System.out.println("2. psmt Connection Success");


			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeQuery() Success");

			if(rs.next()){
				dto = new BookReviewDto(
						rs.getInt("REVIEW_INDEX"),
						rs.getString("ISBN"),
						rs.getString("BOOKTITLE"),
						rs.getString("ID"),
						rs.getString("NAME"),
						rs.getString("TITLE"),
						rs.getString("CONTENT"),
						rs.getString("WRITE_DATE"),
						rs.getInt("RATE"));
			}		
			System.out.println("4. into Dto Success");

		} catch (SQLException e) {
			System.out.println("fail!!!");
		} finally{
			try {DBConn.close(rs, psmt, conn);}
			catch (SQLException e){e.printStackTrace();}
			System.out.println("5. DB Close Success");
		}		
		return dto;
	}

	@Override
	public boolean insertReview(BookReviewDto dto) {
		int count = 0;
		Connection conn = null;
		PreparedStatement psmt = null;

		System.out.println(dto.toString());

		String sql = " INSERT INTO LIB_BOOK_REVIEW "
				+ " VALUES(LIB_REVIEW_SEQ.NEXTVAL, ?, ?, ?, ?, SYSDATE, ?)";
		System.out.println("\n[DB] insertReview");
		System.out.println("[SQL] " + sql);

		
		try {
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");

			psmt.setString(1, dto.getIsbn());
			psmt.setString(2, dto.getId());
			psmt.setString(3, dto.getTitle());
			psmt.setString(4, dto.getContent());
			psmt.setInt(5, dto.getRate());

			count = psmt.executeUpdate();
			System.out.println("3. psmt.executeUpdate() Success");
			
		} catch (SQLException e) {
			System.out.println("fail!!!");
		} finally{
			try {DBConn.close(rs, psmt, conn);}
			catch (SQLException e){e.printStackTrace();}
			System.out.println("5. DB Close Success");
		}		
		return count>0 ? true:false;
	}

	@Override
	public boolean updateReview(int index, String title, String content, int rate) {
		int count = 0;
		Connection conn = null;
		PreparedStatement psmt = null;

		String sql = " UPDATE LIB_BOOK_REVIEW "
				+ " SET TITLE=?, CONTENT=?, RATE=? "
				+ " WHERE REVIEW_INDEX=? ";

		System.out.println("updateReview SQL: " + sql);  

		try {
			conn = DBConn.getConnection();
			System.out.println("2/6 updateReview Success");

			psmt = conn.prepareStatement(sql);

			psmt.setString(1, title);
			psmt.setString(2, content);
			psmt.setInt(3, rate);
			psmt.setInt(4, index);

			System.out.println("3/6 updateReview Success");

			count = psmt.executeUpdate();
			System.out.println("4/6 updateReview Success");
			System.out.println("5/6 updateReview Success");

		} catch (SQLException e) {
			System.out.println("updateReview fail");
		} finally{
			try {DBConn.close(rs, psmt, conn);}
			catch (SQLException e){e.printStackTrace();}
			System.out.println("6/6 updateReview Success");
		}		
		return count>0 ? true:false;
	}

	@Override
	public boolean removeReview(int index) {
		int count = 0;

		Connection conn = null;
		PreparedStatement psmt = null;

		String sql = " DELETE FROM LIB_BOOK_REVIEW "
				+ " WHERE REVIEW_INDEX=? ";

		System.out.println("\n[DB] removeReview");
		System.out.println("[SQL] " + sql);

		try {
			conn = DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");
			psmt.setInt(1, index);

			count = psmt.executeUpdate();
			System.out.println("3. psmt.executeUpdate() Success");

		} catch (SQLException e) {
			System.out.println("fail!!!!! ");
		} finally{
			try {DBConn.close(rs, psmt, conn);}
			catch (SQLException e){e.printStackTrace();}
			System.out.println("4. DB Close Success");
		}		
		return count>0 ? true:false;
	}

	@Override
	public List<BookReviewRepleDto> getReviewRepleList(int review_index) {
		List<BookReviewRepleDto> list = new ArrayList<BookReviewRepleDto>();

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		String sql = " select REVIEW_INDEX, ID, CONTENT, to_char(WRITE_DATE, 'yyyy/mm/dd hh24:mm:ss') wdate , GRP, STEP, DEPTH "
				+ " from LIB_REVIEW_REPLE "
				+ " where review_index = ? order by grp asc, STEP asc ";

		System.out.println("\n[DB] getReviewRepleList");
		System.out.println("[SQL] " + sql);

		try {
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");

			psmt.setInt(1, review_index);
			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUQuery() Success");

			while(rs.next()){
				BookReviewRepleDto dto = new BookReviewRepleDto(
						rs.getInt("REVIEW_INDEX"),
						rs.getString("ID"),
						rs.getString("CONTENT"),
						rs.getString("WDATE"),
						rs.getInt("GRP"),
						rs.getInt("STEP"),
						rs.getInt("DEPTH"));
				list.add(dto);		
			}		
			System.out.println("4. Into Dto Success");

		} catch (SQLException e) {
			System.out.println("fail!!!!!");
		} finally{
			try {DBConn.close(rs, psmt, conn);}
			catch (SQLException e){e.printStackTrace();}
			System.out.println("5. DB Close Success");
		}		
		return list;
	}

	@SuppressWarnings("resource")
	@Override
	public boolean addReviewReple(String id, String content, int review_index, int group, int _step, int depth) {

		int count = 0;

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int step=0;
		
		System.out.println("\n[DB] addReviewReple");
		try {

			String sql = " select min(STEP) minstep from LIB_REVIEW_REPLE "
					+ " where review_index=? and GRP = ? and STEP > ? and depth <= ? ";
			System.out.println("addReviewReple SQL: " + sql);  
			
			System.out.println("[DB] (1). getStep");
			System.out.println("[SQL] " + sql);
			
			conn = DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");
			
			psmt.setInt(1, review_index);
			psmt.setInt(2, group);
			psmt.setInt(3, _step);
			psmt.setInt(4, depth);

			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeQuery()  Success");

			if(rs.next()) step = rs.getInt("minstep");
			System.out.println("4. rs.next()  Success");

			if(step==0){
				sql = "select max(step)+1 step from LIB_REVIEW_REPLE where review_index = ? and grp = ?";
				
				System.out.println("[DB] (2-1) select step");
				System.out.println("[SQL] " + sql);
				
				psmt = conn.prepareStatement(sql);
				System.out.println("5.(if) psmt Connection Success");
				psmt.setInt(1, review_index);
				psmt.setInt(2, group);

				rs = psmt.executeQuery();
				System.out.println("6.(if) psmt.executeQuery()  Success");

				if(rs.next()){step = rs.getInt("step");	}
			}else{
				sql = " update LIB_REVIEW_REPLE set STEP = STEP + 1 "
						+ " where review_index=? and GRP = ? and STEP >= ? ";
				System.out.println("[DB] (2-2) select step");
				System.out.println("[SQL] " + sql);
				
				psmt = conn.prepareStatement(sql);
				System.out.println("5.(else) psmt Connection Success");
				psmt.setInt(1, review_index);
				psmt.setInt(2, group);
				psmt.setInt(3, step);
				count = psmt.executeUpdate();
				System.out.println("6.(else) psmt.executeUpdate()  Success");
				//step = step+1;
			}
			if(depth==-1){
				sql = " select nvl(max(grp),0) max_grp from LIB_REVIEW_REPLE where review_index =? ";
				System.out.println("[DB] (2.5) get max_grp");
				System.out.println("[SQL] " + sql);
				psmt = conn.prepareStatement(sql);
				System.out.println("6.1. psmt Connection  Success");
				psmt.setInt(1, review_index);
				
				rs = psmt.executeQuery();
				System.out.println("6.2. psmt.executeQuery()  Success");

				if(rs.next()){
					group = rs.getInt("max_grp")+1;
				}
			}
			sql = " INSERT INTO LIB_REVIEW_REPLE(REVIEW_INDEX, ID, CONTENT, WRITE_DATE, GRP, STEP, DEPTH) "
					+ " VALUES(?, ?, ?, SYSDATE, ?, ?, ?)";

			System.out.println("[DB] (3) insert into");
			System.out.println("[SQL] " + sql); 

			psmt = conn.prepareStatement(sql);
			System.out.println("7. psmt Connection Success");
			psmt.setInt(1, review_index);
			psmt.setString(2, id);
			psmt.setString(3, content);
			psmt.setInt(4, group);
			psmt.setInt(5, step);
			  psmt.setInt(6, depth+1);

			count = psmt.executeUpdate();
			System.out.println("8. psmt.executeUpdate() Success");

		} catch (SQLException e) {
			System.out.println("fail!!");
		} finally{
			try {DBConn.close(rs, psmt, conn);}
			catch (SQLException e){e.printStackTrace();}
			System.out.println("9. DB Close Success");
		}
		return count>0 ? true:false;
	}

}
