package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import dto.BookRequestDto;
import dto.MemberDto;
import single.Delegate;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class UserBookRequestView extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	Delegate d = Delegate.getInstance();

	// 도서 신청 작성
	private JTextField titleText;
	private JTextField authorTxt;
	private JTextField publisherTxt;
	private JTextField typeTxt;

	private JButton submitBtn;
	DefaultTableModel model;
	// 검색
	JComboBox<String> searchType;


	// 로그인 정보
	private JLabel idLab;
	private JLabel rentCountLab;
	private JLabel rentCountLab2;
	private JLabel bookingLab;
	private JLabel bookingLab2;

	// 메뉴
	private JButton rentLogBtn;
	private JButton chatBtn;
	private JButton homeBtn;
	private JButton logoutBtn;

	// myInfo
	String myId;
	String myName;
	int myRentCount;
	int myBookingCount;
	private UserBookRequestPanel bookRequestPanel;



	public UserBookRequestView(MemberDto myInfo) {
		super("도서 신청");
		getContentPane().setLayout(null);

		JPanel titlePanel = new JPanel(){
			private static final long serialVersionUID = 1L;
			@Override
			protected void paintComponent(Graphics g) {
				Image image = null;
				try {
					image = ImageIO.read(new File("img\\title1.png"));
				}
				catch (NullPointerException|IOException e) {
					try {
						image = ImageIO.read(new File("img\\notFound.jpg"));
					} catch (IOException e1) {}
				}
				g.drawImage(image, 0, 0, this);
			}
		};
		titlePanel.setOpaque(false);
		titlePanel.setLayout(null);
		titlePanel.setBackground(Color.red);
		titlePanel.setBounds(0, 0, 1182, 118);
		getContentPane().add(titlePanel);

		// 신청할 도서
		JPanel writePanel = new JPanel();
		writePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "신청할 도서정보", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		writePanel.setBounds(10, 128, 295, 460);
		getContentPane().add(writePanel);
		writePanel.setLayout(null);

		JLabel titleLabel = new JLabel("* 제           목");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(15, 40, 80, 17);
		writePanel.add(titleLabel);

		JLabel authorlabel = new JLabel(" 저           자");
		authorlabel.setHorizontalAlignment(SwingConstants.CENTER);
		authorlabel.setBounds(15, 90, 80, 17);
		writePanel.add(authorlabel);

		JLabel publisherLabel = new JLabel(" 출   판   사");
		publisherLabel.setHorizontalAlignment(SwingConstants.CENTER);
		publisherLabel.setBounds(15, 140, 80, 17);
		writePanel.add(publisherLabel);

		JLabel typeLabel = new JLabel(" 장           르");
		typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		typeLabel.setBounds(15, 190, 80, 17);
		writePanel.add(typeLabel);


		titleText = new JTextField();
		titleText.setBounds(110, 40, 160, 25);
		titleText.setColumns(10);
		writePanel.add(titleText);

		authorTxt = new JTextField();
		authorTxt.setColumns(10);
		authorTxt.setBounds(110, 90, 160, 25);
		writePanel.add(authorTxt);

		publisherTxt = new JTextField();
		publisherTxt.setColumns(10);
		publisherTxt.setBounds(110, 140, 160, 25);
		writePanel.add(publisherTxt);

		typeTxt = new JTextField();
		typeTxt.setColumns(10);
		typeTxt.setBounds(110, 190, 160, 25);
		writePanel.add(typeTxt);

		submitBtn = new JButton("신청");
		submitBtn.setBounds(20, 240, 250, 35);
		submitBtn.addActionListener(this);
		writePanel.add(submitBtn);

		//나중에 지우기
		bookRequestPanel = new UserBookRequestPanel(5);
		bookRequestPanel.setBounds(310, 128, 586, 462);
		getContentPane().add(bookRequestPanel);

		// 로그인
		JPanel panelLogin = new JPanel();
		panelLogin.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "로그인", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelLogin.setBounds(901, 128, 265, 142);
		getContentPane().add(panelLogin);
		panelLogin.setLayout(null);

		myId = myInfo.getId();
		myName = myInfo.getName();
		myRentCount = myInfo.getRentcount();
		myBookingCount = myInfo.getbookingcount();

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy / MM / dd");
		String sysdate = sdf.format(date);
		idLab = new JLabel(sysdate + ", '" + myName + "' 님  접속");
		idLab.setBounds(45, 24, 250, 15);
		panelLogin.add(idLab);

		rentCountLab = new JLabel("대여중 : " + myRentCount + "권");
		rentCountLab.setBounds(45, 50, 250, 15);
		panelLogin.add(rentCountLab);
		rentCountLab2 = new JLabel("대여가능 : " + (5-myRentCount)+ "권");
		rentCountLab2.setBounds(135, 50, 250, 15);
		panelLogin.add(rentCountLab2);
		bookingLab = new JLabel("예약중 : " + myBookingCount + "권");
		bookingLab.setBounds(45, 70, 250, 15);
		panelLogin.add(bookingLab);
		bookingLab2 = new JLabel("예약가능 : " + (5-myBookingCount)+ "권");
		bookingLab2.setBounds(135, 70, 250, 15);
		panelLogin.add(bookingLab2);


		logoutBtn = new JButton("로그아웃");
		logoutBtn.setBounds(31, 95, 210, 30);
		logoutBtn.addActionListener(this);
		panelLogin.add(logoutBtn);


		// 메뉴
		JPanel menuPanel = new JPanel();
		menuPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "메뉴", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		menuPanel.setBounds(901, 273, 265, 315);
		getContentPane().add(menuPanel);
		menuPanel.setLayout(null);

		homeBtn = new JButton("홈");
		homeBtn.addActionListener(this);
		homeBtn.setBounds(31, 30, 210, 70);
		menuPanel.add(homeBtn);

		rentLogBtn = new JButton("대여/반납 목록");
		rentLogBtn.setBounds(31, 120, 210, 70);
		rentLogBtn.addActionListener(this);
		menuPanel.add(rentLogBtn);

		chatBtn = new JButton("관리자와 채팅");
		chatBtn.setBounds(31, 210, 210, 70);
		chatBtn.addActionListener(this);
		menuPanel.add(chatBtn);

		setSize(1192, 800);
		setLocationRelativeTo(null);
		setVisible(true);
	}




	@Override public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();

		if(btn == submitBtn){
			boolean add = d.bookRequestCtrl.addBookRequest(new BookRequestDto(titleText.getText(),
					typeTxt.getText(),publisherTxt.getText(),authorTxt.getText(),d.memCtrl.getLoginId()));
			if(add){
				JOptionPane.showMessageDialog(null, "작성하신 내용이 신청되었습니다.");
				bookRequestPanel.bookRequestTable(d.bookRequestCtrl.getBookRequestList(d.memCtrl.getLoginId()), 1);
			} else { JOptionPane.showMessageDialog(null, "신청에 실패하였습니다.");}

		}else if(btn == homeBtn){
			d.memCtrl.userHome();
			this.dispose();

		}else if(btn == rentLogBtn){
			d.rentLogCtrl.UserRentLogView(new MemberDto(myId, myName, myRentCount, myBookingCount));

			this.dispose();
		}else if(btn == logoutBtn){
			new MainView();
			d.memCtrl.logout();
			this.dispose();
		}else if(btn == chatBtn){
			d.chatCtrl.Chatting(myId);
		}

	}
}
