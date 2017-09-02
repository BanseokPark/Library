package service;

import java.util.List;

import dto.MemberDto;

public interface MemberServiceImpl {
	public boolean addMember(MemberDto dto);
	public boolean getId(String id);
	public MemberDto login(MemberDto dto);
	public List<MemberDto> getList();
	public MemberDto getMember(String id);
	public boolean updateMember(String id , String pw, String name, String phone);
	public List<MemberDto> selectMember(String selectedOption, String txt);	
	public MemberDto getMemberRentInfo(String id);
	public boolean getRentCount(String id);
	public boolean getBookingCount(String id);
	public boolean minusRentCount(String id);
}
