package dto;

import java.io.Serializable;

public class MemberDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;				// 아이디
	private String pw;				// 비밀번호
	private String name;			// 이름
	private String phone;			// 핸드폰
	private int rentcount;			// 대여중인 책의 갯수
	private int bookingcount;		// 예약중인 책의 갯수
	private int auth;				

	public MemberDto(String id, String pwd, String name, String phone, int rentcount, int bookingcount, int auth) {
		this.id = id;
		this.pw = pwd;
		this.name = name;
		this.phone = phone;
		this.rentcount = rentcount;
		this.bookingcount = bookingcount;
		this.auth = auth;
	}
	
	public MemberDto(String id, String name, int rentcount, int bookingcount) {
		this.id = id;
		this.name = name;
		this.rentcount = rentcount;
		this.bookingcount = bookingcount;
	}

	public String getId() {return id;}
	public String getPwd() {return pw;}
	public String getName() {return name;}
	public String getPhone() {return phone;}
	public int getRentcount() {return rentcount;}
	public int getbookingcount() {return bookingcount;}
	public int getAuth() {	return auth;}
	
	public void setId(String id) {this.id = id;}
	public void setPwd(String pwd) {this.pw = pwd;}
	public void setName(String name) {this.name = name;}
	public void setPhone(String phone) {this.phone = phone;}
	public void setRentcount(int rentcount) {this.rentcount = rentcount;}
	public int setbookingcount() {return bookingcount;}
	public void setAuth(int auth) {this.auth = auth;}

	@Override
	public String toString() {
		return "MemberDto [id=" + id + ", pw=" + pw + ", name=" + name 
				+ ", phone=" + phone + ", rentcount=" + rentcount
				+ ", bookingcount=" + bookingcount + ", auth=" + auth + "]";
	}
}