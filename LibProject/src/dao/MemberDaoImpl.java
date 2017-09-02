package dao;

import java.util.List;

import dto.MemberDto;

public interface MemberDaoImpl {
	public boolean addMember(MemberDto dto);
	public boolean getId(String id);
	public MemberDto login(MemberDto dto);
	public List<MemberDto> getList();
	
	public MemberDto getMember(String id);
	public boolean updateMember(String id , String pw, String name, String phone);
	public List<MemberDto> selectMember(String selectedOption, String txt);
	
	public MemberDto getMemberRentInfo(String id);
	public boolean getRentCount(String id); // 내 대여 횟수 확인 후, 5개 미만이면 대여횟수 +1 추가(대여)
	public boolean getBookingCount(String id); // 내 예약 횟수  확인 후, 5개 미만이면 대여횟수 +1 추가(예약)
	public boolean minusRentCount(String id); // 반납 시, 대여횟수 -1
}
