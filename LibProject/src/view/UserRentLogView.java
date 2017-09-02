package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dto.MemberDto;
import dto.RentDto;
import single.Delegate;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class UserRentLogView extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	Delegate d = Delegate.getInstance();

	// 테이블
	private String[] rentColumn = {"번호", "코드", "제목", "장르", "출판사", "저자", "대여일" , "반납예정일"};
	//private String[] rentLogColumn = {"번호", "코드", "제목", "장르", "출판사", "저자", "날짜", "대여/반납"};
	DefaultTableModel model;
	private Object[][] rowData;


	// 검색
	JComboBox<String> searchType;
	private JTable tableR;

	// 로그인 정보
	private JLabel idLab;
	private JLabel rentCountLab;
	private JLabel rentCountLab2;
	private JLabel bookingLab;
	private JLabel bookingLab2;

	// 메뉴
	private JButton requestBtn;
	private JButton chatBtn;
	private JButton homeBtn;
	private JButton logoutBtn;

	// myInfo
	String myId;
	String myName;
	int myRentCount;
	int myBookingCount;



	public UserRentLogView(MemberDto myInfo) {

		super("대여/반납 목록");
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
				} g.drawImage(image, 0, 0, this);
			}
		};
		titlePanel.setOpaque(false);
		titlePanel.setLayout(null);
		titlePanel.setBackground(Color.red);
		titlePanel.setBounds(0, 0, 1182, 118);
		getContentPane().add(titlePanel);

		JPanel rentLogPanel = new UserRentLogPanel(this,20);
		rentLogPanel.setBounds(10, 128, 886, 462);
		getContentPane().add(rentLogPanel);



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


		JPanel menuPanel = new JPanel();
		menuPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "메뉴", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		menuPanel.setBounds(901, 273, 265, 315);
		getContentPane().add(menuPanel);
		menuPanel.setLayout(null);

		homeBtn = new JButton("홈");
		homeBtn.addActionListener(this);
		homeBtn.setBounds(31, 30, 210, 70);
		menuPanel.add(homeBtn);

		requestBtn = new JButton("구매 신청/목록");
		requestBtn.setBounds(31, 120, 210, 70);
		requestBtn.addActionListener(this);
		menuPanel.add(requestBtn);

		chatBtn = new JButton("관리자와 채팅");
		chatBtn.setBounds(31, 210, 210, 70);
		chatBtn.addActionListener(this);
		menuPanel.add(chatBtn);



		rentTable(Delegate.getInstance().rentCtrl.getRentList(d.memCtrl.getLoginId()));

		setSize(1192, 800);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void rentTable(List<RentDto> rentList){
		rowData = new Object[rentList.size()][rentColumn.length];
		for (int i = 0; i < rentList.size(); i++) {
			rowData[i][0] = i+1;
			rowData[i][1] = rentList.get(i).getIsbn();
			rowData[i][2] = rentList.get(i).getTitle();
			rowData[i][3] = rentList.get(i).getType();
			rowData[i][4] = rentList.get(i).getPublisher();
			rowData[i][5] = rentList.get(i).getAuthor();
			rowData[i][6] = rentList.get(i).getRentdate();
			rowData[i][7] = rentList.get(i).getReturndate();
		}

		tableR = new JTable();
		model = new DefaultTableModel(rentColumn, 0);
		model.setDataVector(rowData, rentColumn);
		model.fireTableDataChanged();
		tableR.setModel(model);

		tableR.getColumnModel().getColumn(0).setMaxWidth(30);
		tableR.getColumnModel().getColumn(1).setMaxWidth(100);
		tableR.getColumnModel().getColumn(2).setMaxWidth(300);
		tableR.getColumnModel().getColumn(3).setMaxWidth(100);
		tableR.getColumnModel().getColumn(4).setMaxWidth(100);
		tableR.getColumnModel().getColumn(5).setMaxWidth(100);
		tableR.getColumnModel().getColumn(6).setMaxWidth(100);
		tableR.getColumnModel().getColumn(7).setMaxWidth(100);

		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);

		tableR.getColumn("번호").setCellRenderer(celAlignCenter);
		tableR.getColumn("코드").setCellRenderer(celAlignCenter);
		tableR.getColumn("제목").setCellRenderer(celAlignCenter);
		tableR.getColumn("장르").setCellRenderer(celAlignCenter);
		tableR.getColumn("출판사").setCellRenderer(celAlignCenter);
		tableR.getColumn("저자").setCellRenderer(celAlignCenter);
		tableR.getColumn("대여일").setCellRenderer(celAlignCenter);
		tableR.getColumn("반납예정일").setCellRenderer(celAlignCenter);

		tableR.addMouseListener(new MouseListener() {

			@Override public void mousePressed(MouseEvent e) {
				int rowNum = tableR.getSelectedRow();
				System.out.println("tableSize : " + tableR.getRowCount());
				System.out.println("rowNum : " + rowNum);

				if(rowNum == -1) return;
				String isbn = (String) rentList.get(rowNum).getIsbn(); // seq
				d.bookCtrl.showDetail(isbn);
				dispose();
			}
			@Override public void mouseReleased(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseClicked(MouseEvent e) {}
		});



		JPanel rentPanel = new JPanel();
		rentPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "대여중인 도서", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		rentPanel.setBounds(10, 598, 886, 150);
		getContentPane().add(rentPanel);
		rentPanel.setLayout(null);


		JScrollPane scrollPane = new JScrollPane(tableR);
		scrollPane.setBounds(15, 30, 857, 103);
		rentPanel.add(scrollPane);

	}

	@Override public void actionPerformed(ActionEvent e) {
		if(e.getSource() == homeBtn){
			d.memCtrl.userHome();
			this.dispose();
		}else if(e.getSource() == requestBtn){
			new UserBookRequestView(new MemberDto(myId, myName, myRentCount, myBookingCount));
			this.dispose();
			this.dispose();
		}else if(e.getSource() == logoutBtn){
			new MainView();
			d.memCtrl.logout();
			this.dispose();
		}else if(e.getSource() == chatBtn){
			d.chatCtrl.Chatting(myId);
		}

	}
}
