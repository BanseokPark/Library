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

import dto.BookDto;
import dto.MemberDto;
import dto.RentDto;
import dto.RentLogRankDto;
import single.Delegate;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class UserView extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	Delegate d = Delegate.getInstance();

	// 테이블
	private String[] bookColumn = {"번호","제목", "저자", "출판사" , "장르", "대여상태"};
	private String[] rentColumn = {"번호", "제목", "저자", "대여일" , "반납예정일"};
	private String[] bookingColumn = {"번호", "제목", "저자", "반납예정일", "대여상태"};
	private String[] rankColumn = {"순위", "제목", "대여횟수"};
	DefaultTableModel model;
	private Object[][] rowData;

	// 검색
	JComboBox<String> searchType;
	private JTable tableR;
	private JTable tableB;
	private JTable tableP;


	// 로그인 정보
	private JLabel idLab;
	private JLabel rentCountLab;
	private JLabel rentCountLab2;
	private JLabel bookingLab;
	private JLabel bookingLab2;

	// 메뉴
	private JButton rentLogBtn;
	private JButton requestBtn;
	private JButton chatBtn;
	private JButton logoutBtn;

	// myInfo
	String myId;
	String myName;
	int myRentCount;
	int myBookingCount;


	public UserView(MemberDto myInfo)  {
		super("도서 관리 프로그램");
		getContentPane().setLayout(null);

    JPanel titlePanel = new JPanel(){
			private static final long serialVersionUID = 1L;
			@Override
			protected void paintComponent(Graphics g) {
				Image image = null;
				try {
					//image = ImageIO.read(new File("\\\\192.168.10.20\\공유\\LibraryProject_Image\\title1.png"));
					image = ImageIO.read(new File("img\\title1.png"));
				}
				catch (NullPointerException|IOException e) {
					try {
						//image = ImageIO.read(new File("\\\\192.168.10.20\\공유\\LibraryProject_Image\\notFound.jpg"));
						image = ImageIO.read(new File("img\\notFound.png"));
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

		// 검색 + 도서 리스트 패널
		JPanel bookTablePanel = new BookTablePanel(this, 20);
		bookTablePanel.setBounds(10, 128, 886, 462);
		getContentPane().add(bookTablePanel);

		// 로그인 패널
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


		// 메뉴 패널
		JPanel menuPanel = new JPanel();
		menuPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "메뉴", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		menuPanel.setBounds(901, 275, 265, 315);
		getContentPane().add(menuPanel);
		menuPanel.setLayout(null);

		rentLogBtn = new JButton("대여/반납 목록");
		rentLogBtn.addActionListener(this);
		rentLogBtn.setBounds(31, 30, 210, 70);
		menuPanel.add(rentLogBtn);

		requestBtn = new JButton("구매 신청/목록");
		requestBtn.setBounds(31, 120, 210, 70);
		requestBtn.addActionListener(this);
		menuPanel.add(requestBtn);

		chatBtn = new JButton("관리자와 채팅");
		chatBtn.setBounds(31, 210, 210, 70);
		chatBtn.addActionListener(this);
		menuPanel.add(chatBtn);


		// 인기 도서 패널
		rentLogRankTable(Delegate.getInstance().rentLogCtrl.getRentRogRankList(), 10);

		// 대여중인 리스트 패널
		rentTable(d.rentCtrl.getRentList(d.memCtrl.getLoginId()));

		// 예약중인 리스트 패널
		bookingTable(d.bookCtrl.getBookingList(d.memCtrl.getLoginId()));


		// 창 사이즈 설정
		setSize(1192, 800);
		setLocationRelativeTo(null);
		setVisible(true);
	}






	public void rentTable(List<RentDto> rentList){
		rowData = new Object[rentList.size()][rentColumn.length];
		for (int i = 0; i < rentList.size(); i++) {
			rowData[i][0] = i+1;
			rowData[i][1] = rentList.get(i).getTitle();
			rowData[i][2] = rentList.get(i).getAuthor();
			rowData[i][3] = rentList.get(i).getRentdate();
			rowData[i][4] = rentList.get(i).getReturndate();
		}

		tableR = new JTable();
		model = new DefaultTableModel(rentColumn, 0);
		model.setDataVector(rowData, rentColumn);
		model.fireTableDataChanged();
		tableR.setModel(model);
		tableR.getColumnModel().getColumn(0).setMaxWidth(50);
		tableR.getColumnModel().getColumn(1).setMaxWidth(300);
		tableR.getColumnModel().getColumn(2).setMaxWidth(80);
		tableR.getColumnModel().getColumn(3).setMaxWidth(80);
		tableR.getColumnModel().getColumn(4).setMaxWidth(80);

		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);

		tableR.getColumn("번호").setCellRenderer(celAlignCenter);
		tableR.getColumn("제목").setCellRenderer(celAlignCenter);
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
		rentPanel.setBounds(10, 601, 440, 150);
		getContentPane().add(rentPanel);
		rentPanel.setLayout(null);


		JScrollPane scrollPane = new JScrollPane(tableR);
		scrollPane.setBounds(15, 30, 410, 103);
		rentPanel.add(scrollPane);
	}




	public void bookingTable(List<BookDto> bookingList){
		rowData = new Object[bookingList.size()][bookColumn.length];
		for (int i = 0; i < bookingList.size(); i++) {
			rowData[i][0] = rowData[i][0] = i+1;
			rowData[i][1] = bookingList.get(i).getTitle();
			rowData[i][2] = bookingList.get(i).getAuthor();
			rowData[i][3] = bookingList.get(i).getReturn_date();
			rowData[i][4] = bookingList.get(i).getRentstate();
		}

		tableB = new JTable();
		model = new DefaultTableModel(bookingColumn, 0);
		model.setDataVector(rowData, bookingColumn);
		model.fireTableDataChanged();
		tableB.setModel(model);

		tableB.getColumnModel().getColumn(0).setMaxWidth(30);
		tableB.getColumnModel().getColumn(1).setMaxWidth(300);
		tableB.getColumnModel().getColumn(2).setMaxWidth(80);
		tableB.getColumnModel().getColumn(3).setMaxWidth(80);
		tableB.getColumnModel().getColumn(4).setMaxWidth(80);

		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);

		tableB.getColumn("번호").setCellRenderer(celAlignCenter);
		tableB.getColumn("제목").setCellRenderer(celAlignCenter);
		tableB.getColumn("저자").setCellRenderer(celAlignCenter);
		tableB.getColumn("반납예정일").setCellRenderer(celAlignCenter);
		tableB.getColumn("대여상태").setCellRenderer(celAlignCenter);

		tableB.addMouseListener(new MouseListener() {

			@Override public void mousePressed(MouseEvent e) {
				int rowNum = tableB.getSelectedRow();
				System.out.println("tableSize : " + tableB.getRowCount());
				System.out.println("rowNum : " + rowNum);


				if(rowNum == -1) return;
				String isbn = (String) bookingList.get(rowNum).getIsbn(); // seq
				d.bookCtrl.showDetail(isbn);
				dispose();
			}
			@Override public void mouseReleased(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseClicked(MouseEvent e) {}
		});


		JPanel bookingPanel = new JPanel();
		bookingPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "예약중인 도서", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		bookingPanel.setBounds(455, 601, 440, 150);
		getContentPane().add(bookingPanel);
		bookingPanel.setLayout(null);


		JScrollPane scrollPane = new JScrollPane(tableB);
		scrollPane.setBounds(15, 30, 410, 103);
		bookingPanel.add(scrollPane);

	}

	public void rentLogRankTable(List<RentLogRankDto> list, int page_size){
		page_size = list.size()<page_size ? list.size() : page_size;
		rowData = new Object[page_size][3];
		for (int i = 0; i < rowData.length; i++) {
			rowData[i][0] = list.get(i).getRank();
			rowData[i][1] = list.get(i).getTitle();
			rowData[i][2] = list.get(i).getRent_count();
		}

		tableP = new JTable();
		model = new DefaultTableModel(rankColumn, 0);
		model.setDataVector(rowData, rankColumn);
		tableP.setModel(model);

		tableP.getColumnModel().getColumn(0).setMaxWidth(50);
		tableP.getColumnModel().getColumn(1).setMaxWidth(200);
		tableP.getColumnModel().getColumn(2).setMaxWidth(50);

		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);

		tableP.getColumn("순위").setCellRenderer(celAlignCenter);
		tableP.getColumn("제목").setCellRenderer(celAlignCenter);
		tableP.getColumn("대여횟수").setCellRenderer(celAlignCenter);


		tableP.addMouseListener(new MouseListener() {

			@Override public void mousePressed(MouseEvent e) {
				int rowNum = tableP.getSelectedRow();
				System.out.println("tableSize : " + tableP.getRowCount());
				System.out.println("rowNum : " + rowNum);

				if(rowNum == -1) return;
				String isbn = (String) list.get(rowNum).getIsbn(); // seq
				d.bookCtrl.showDetail(isbn);
				dispose();
			}
			@Override public void mouseReleased(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseClicked(MouseEvent e) {}
		});


		JPanel rentLogRankPanel = new JPanel();
		rentLogRankPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "인기 도서", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		rentLogRankPanel.setBounds(901, 601, 265, 150);
		getContentPane().add(rentLogRankPanel);
		rentLogRankPanel.setLayout(null);


		JScrollPane scrollPane = new JScrollPane(tableP);
		scrollPane.setBounds(15, 30, 235, 103);
		rentLogRankPanel.add(scrollPane);

	}


	@Override public void actionPerformed(ActionEvent e) {

		JButton btn = (JButton)e.getSource();

		if (btn == rentLogBtn){ // 대여/반납 목록
			d.rentLogCtrl.UserRentLogView(new MemberDto(myId, myName, myRentCount, myBookingCount));
			this.dispose();

		}else if(btn == requestBtn){ // 구매요청 목록
			new UserBookRequestView(new MemberDto(myId, myName, myRentCount, myBookingCount));
			this.dispose();

		}else if(btn == chatBtn){ // 채팅
			d.chatCtrl.Chatting(myId);

		}else if(btn == logoutBtn){ // 로그아웃
			new MainView();
			d.memCtrl.logout();
			this.dispose();
		}
	}



}
