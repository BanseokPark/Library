package controller;

import java.util.List;
import javax.swing.JOptionPane;
import dto.MemberDto;
import service.MemberService;
import view.AccountView;
import view.AdminView;
import view.MainView;
import view.AdminMemberUpdateView;
import view.AdminMembershipView;
import view.UserView;

public class MemberController {
	private String loginId = "";
	
	MemberService memService = new MemberService();
	public void start() {
		new MainView();	
	}

	public void loginAf(MainView mainView, String id, String pw) {
		MemberDto dto = memService.login(new MemberDto(id, pw, null, null, 0, 0, 0));
		if(dto == null){
			JOptionPane.showMessageDialog(null, "ID와 Password를 다시 확인해주세요");
		}else if(dto.getAuth()==1){
			loginId = dto.getId();
			new AdminView();
			mainView.dispose();
		}else if(dto.getAuth()==3){
			loginId = dto.getId();
			new UserView(memService.getMemberRentInfo(loginId));
			mainView.dispose();
		}
	}

	public String getLoginId() {return loginId;}
	public void regi() {	new AccountView();}
	public void admin_regi() {	new AccountView();}	
	public boolean IdCheck(String id) {
		return memService.getId(id);
	}

	public boolean regiAf(String id, String pw, String name, String phone) {
		return memService.addMember(new MemberDto(id, pw, name, phone, 0, 0, 3));	
	}
	
	public void Member(){
		new AdminMembershipView();		
	}
	
	public MemberDto memberCheck(String id){
		MemberDto dto = memService.getMember(id);
		return dto;
	}
	
	public void getMember(String id){
		JOptionPane.showMessageDialog(null, "id = " + id);
		
		MemberDto dto = memService.getMember(id);	
		
		System.out.println(dto.toString());
		
		new AdminMemberUpdateView(dto);	
	}	
	
	public List<MemberDto> selectMember(String selectedOption, String txt){
		return memService.selectMember(selectedOption, txt);
	}
	
	public void updateMember(String id , String pw, String name, String phone){
		boolean b = memService.updateMember(id, pw, name, phone);
		if(b){
			JOptionPane.showMessageDialog(null, "수정 성공");
			new AdminMembershipView();
		}else{
			JOptionPane.showMessageDialog(null, "수정 실패");
		}
		
	}	
	
	public List<MemberDto> getList(){
		return memService.getList();
	}
	
	public void userHome() {new UserView(memService.getMemberRentInfo(loginId));}
	
	public MemberDto getMemberRentInfo(String id){
		return memService.getMemberRentInfo(id);
	}
	
	public boolean getRentCount(String id){
		return memService.getRentCount(id);
	}
	
	public boolean getBookingCount(String id){
		return memService.getBookingCount(id);
	}
	
	public boolean minusRentCount(String id){
		return memService.minusRentCount(id);
	}

	public void logout() {loginId = "";	}
}
