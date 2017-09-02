package service;

import java.util.List;

import dao.MemberDao;
import dto.MemberDto;

public class MemberService implements MemberServiceImpl{

	private MemberDao dao = new MemberDao();

	@Override
	public boolean addMember(MemberDto dto) {
		return dao.addMember(dto);
	}

	@Override
	public boolean getId(String id) {
		return dao.getId(id);
	}

	@Override
	public MemberDto login(MemberDto dto) {
		return dao.login(dto);
	}
	
	@Override
	public List<MemberDto> getList() {
		return dao.getList();
	}
	
	@Override
	public MemberDto getMember(String id) {		
		return dao.getMember(id);
	}

	@Override
	public boolean updateMember(String id, String pw, String name, String phone) {		
		return dao.updateMember(id, pw, name, phone);
	}

	@Override
	public List<MemberDto> selectMember(String selectedOption, String txt) {		
		return dao.selectMember(selectedOption, txt);
	}

	@Override
	public MemberDto getMemberRentInfo(String id) {
		// TODO Auto-generated method stub
		return dao.getMemberRentInfo(id);
	}
	
	@Override
	public boolean getRentCount(String id) {
		// TODO Auto-generated method stub
		return dao.getRentCount(id);
	}
	
	@Override
	public boolean getBookingCount(String id) {
		// TODO Auto-generated method stub
		return dao.getBookingCount(id);
	}
	
	@Override
	public boolean minusRentCount(String id) {
		// TODO Auto-generated method stub
		return dao.minusRentCount(id);
	}
}
