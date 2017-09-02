package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBConn;
import dto.MemberDto;

public class MemberDao implements MemberDaoImpl{
	private Connection conn= null;
	private PreparedStatement psmt= null;
	private ResultSet rs= null;
	
	@Override
	public boolean addMember(MemberDto dto) {
		
		int count = 0;

		String sql = " INSERT INTO LIB_MEMBER "
				   + " (ID, PW, NAME, PHONE, RENTCOUNT, BOOKINGCOUNT, AUTH) "
				   + " VALUES(?, ?, ?, ?, 0, 0, 3) ";
		
		try {
			System.out.println("\n[DB] addMember");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");
			
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPwd());
			psmt.setString(3, dto.getName());
			psmt.setString(4, dto.getPhone());
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
		
		return count > 0 ? true:false;
	}

	@Override
	public boolean getId(String id) {
		int count = 0;
		
		String sql = " SELECT ID FROM LIB_MEMBER "
				   + " WHERE ID = ?";		
				
		try {			
			System.out.println("\n[DB] getId");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");
			
			psmt = conn.prepareStatement(sql);	
			System.out.println("2. psmt Connection Success");
			
			psmt.setString(1, id);
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
		
		return count>0 ? true : false;
	}
	
	@Override
	public MemberDto login(MemberDto dto) {
		
		MemberDto mem = null;
		
		String sql = " SELECT ID, NAME, PHONE, RENTCOUNT, BOOKINGCOUNT, AUTH "
				   + " FROM LIB_MEMBER "
				   + " WHERE ID=? AND PW=? ";
		try {
			System.out.println("\n[DB] login");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");			
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");
			
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPwd());			
			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUpdate() Success");
			
			while(rs.next()){
				String id = rs.getString("id");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				int rentcount = rs.getInt("rentcount");
				int bookingcount = rs.getInt("bookingcount");
				int auth = rs.getInt("auth");
				
				mem = new MemberDto(id, null, name, phone, rentcount, bookingcount, auth);	
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
		
		return mem;
	}
	
	@Override
	public List<MemberDto> getList() {
		
		List<MemberDto> list = new ArrayList<MemberDto>();

		String sql = "SELECT ID, PW, NAME, PHONE, RENTCOUNT, BOOKINGCOUNT, AUTH"
							 + " FROM LIB_MEMBER "
							 + " WHERE AUTH != 1 ";
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
				MemberDto dto = new MemberDto(
										rs.getString("ID"),
										rs.getString("PW"),
										rs.getString("NAME"),
										rs.getString("PHONE"),
										rs.getInt("RENTCOUNT"),
										rs.getInt("BOOKINGCOUNT"),
										rs.getInt("AUTH"));
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
	public MemberDto getMember(String id) {
		
		MemberDto dto = null;

		String sql = "SELECT ID, NAME, PHONE, RENTCOUNT, BOOKINGCOUNT, AUTH"
				 + " FROM LIB_MEMBER "
				 + " WHERE ID='" + id + "'";
		
		try {
			System.out.println("\n[DB] getMember");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");

			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUpdate() Success");

			while(rs.next()){
				String _id = rs.getString(1);
				String name = rs.getString(2);
				String phone = rs.getString(3);
				int rent = rs.getInt(4);
				int booking = rs.getInt(5);
				int auth = rs.getInt(6);
				
				dto = new MemberDto(_id, null, name, phone, rent, booking, auth);	
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
		
		return dto;
	}
	
	@Override
	public boolean updateMember(String id, String pw, String name, String phone) {	
		
		int count = 0;
		
		String sql = "UPDATE LIB_MEMBER"
				 + " SET PW=?, NAME=?, PHONE=?"
				 + " WHERE ID=?";
		
		try {
			System.out.println("\n[DB] updateMember");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");
				
			psmt.setString(1, pw);
			psmt.setString(2, name);
			psmt.setString(3, phone);
			psmt.setString(4, id);			
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
	}//updateMember
	
	@Override
	public List<MemberDto> selectMember(String selectedOption, String txt) {
		
		List<MemberDto> list = new ArrayList<>();
		String choice = null;
						
		switch(selectedOption){
		
		case "아이디":
			choice = " WHERE ID LIKE ('%"+txt+"%') ";		
			break;
			
		case "이름":
			choice = " WHERE NAME LIKE ('%"+txt+"%') ";
			break;
			
		case "핸드폰":
			choice = " WHERE PHONE LIKE ('%"+txt+"%') ";
			break;
		}
		
		String sql = "SELECT ID, NAME, PHONE, RENTCOUNT, BOOKINGCOUNT "
				+ " FROM LIB_MEMBER " + choice + " AND AUTH != 1 ";;
		
		try {
			System.out.println("\n[DB] selectMember");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");
			
			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUpdate() Success");
			
			while(rs.next()){
				int i = 1;
				MemberDto dto = new MemberDto(
						rs.getString(i++),
						null,
						rs.getString(i++),
						rs.getString(i++), 
						rs.getInt(i++),
						rs.getInt(i++),
						3);
				list.add(dto);
			}			
			// for (int i = 0; i < list.size(); i++) {	System.out.println(list.get(i).toString());}
			System.out.println("4. into Dto Success");
			
		} catch (SQLException e) {	
			System.out.println("fail!!!!! ");
			e.printStackTrace();
		}finally {
			try {DBConn.close(rs, psmt, conn);}
			catch (SQLException e) {e.printStackTrace();}	
			System.out.println("5. DB Close Success");
		}				
		
		return list;
	}
	
	@Override
	public MemberDto getMemberRentInfo(String id) {
		
		MemberDto dto = null;

		String sql = " SELECT ID, NAME, RENTCOUNT, BOOKINGCOUNT "
				   + " FROM LIB_MEMBER "
				   + " WHERE ID = '" + id + "'";
		
		try {
			System.out.println("\n[DB] getMemberRentInfo");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");

			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUpdate() Success");

			while(rs.next()){	
				dto = new MemberDto( rs.getString("ID"),
						             rs.getString("NAME"),
					                 rs.getInt("RENTCOUNT"),
						             rs.getInt("BOOKINGCOUNT"));			
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
		
		return dto;
	}
	
	@Override
	public boolean getRentCount(String id) {
		
		boolean result = false;

		String sql = " SELECT RENTCOUNT FROM LIB_MEMBER "
				   + " WHERE ID = '" + id + "' ";
				
		try {
			System.out.println("\n[DB] getRentCount");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");

			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUpdate() Success");
			
			if(rs.next()){
				if(rs.getInt("RENTCOUNT") < 5){ // 렌트 횟수가 5번 이하이면	
										
					String updateSql = " UPDATE  LIB_MEMBER SET RENTCOUNT = (RENTCOUNT+1) " // 대여 가능 : 대여 횟수를 +1 해준다
					                 + " WHERE ID = '" + id +"'";
					try {						
						psmt = conn.prepareStatement(updateSql);
						System.out.println("5. psmt Connection Success");
						rs = psmt.executeQuery();
						System.out.println("6. into Dto Success");
						
					} catch (SQLException e) {
						System.out.println("fail(1)!!!!! ");
						e.printStackTrace();
					} 
					
					result = true;
					
				}else{
					result = false;
				}
			}									

		} catch (SQLException e) {
			System.out.println("fail(2)!!!!! ");
			e.printStackTrace();
			
		} finally{
			try {DBConn.close(rs, psmt, conn);}
			catch (SQLException e) {e.printStackTrace();}	
			System.out.println("7. DB Close Success");
		}		
		
		return result;
	}

	@Override
	public boolean getBookingCount(String id) {
		
		boolean result = false;

		String sql = " SELECT BOOKINGCOUNT FROM LIB_MEMBER "
				   + " WHERE ID = '" + id + "' ";
				
		try {
			System.out.println("\n[DB] getBookingCount");
			System.out.println("[SQL] " + sql);
			
			conn =  DBConn.getConnection();
			System.out.println("1. DB Connection Success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2. psmt Connection Success");

			rs = psmt.executeQuery();
			System.out.println("3. psmt.executeUpdate() Success");			
			
			if(rs.next()){
				if(rs.getInt("BOOKINGCOUNT") < 5){ // 예약 횟수가 5번 이하이면		
										
					String updateSql = " UPDATE  LIB_MEMBER SET BOOKINGCOUNT = (BOOKINGCOUNT+1) " // 대여 가능 : 예약 횟수를 +1 해준다
					                 + " WHERE ID = '" + id +"'";
					try {						
						psmt = conn.prepareStatement(updateSql);
						System.out.println("4. psmt Connection Success");
						
						rs = psmt.executeQuery();
						System.out.println("5. psmt.executeUpdate() Success");	
						
					} catch (SQLException e) {e.printStackTrace();	
						System.out.println("fail(1)!!!!! ");
						e.printStackTrace();
					} 
					
					result = true;
					
				}else{
					result = false;
				}
			}

		} catch (SQLException e) {
			System.out.println("fail(2)!!!!! ");
			e.printStackTrace();
			
		} finally{
			try {DBConn.close(rs, psmt, conn);}
			catch (SQLException e) {e.printStackTrace();}	
			System.out.println("6. DB Close Success");
		}	
		
		return result;
	}
	
	@Override
	public boolean minusRentCount(String id) {
		
		int count = 0;
		
		String sql = " UPDATE  LIB_MEMBER SET RENTCOUNT = (RENTCOUNT-1) "
                   + " WHERE ID = '" + id +"'";

		try {
			System.out.println("\n[DB] minusRentCount");
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
			System.out.println("5. DB Close Success");
		}

		return count > 0 ? true:false;
	}
}
