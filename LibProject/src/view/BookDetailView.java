package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import dto.BookDto;
import dto.BookReviewDto;
import dto.MemberDto;
import single.Delegate;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;

public class BookDetailView extends JFrame implements ActionListener, MouseListener{
	private static final long serialVersionUID = 1L;
	private JTextField titleTextF;
	private JTextField authorTextF;
	private JTextField publisherTextF;
	private JTextField locationTextF;
	private JTextField typeTextF;
	private JTextField isbnTextF;
	private JTable reviewTable;

	private JButton updateBtn;
	private JButton exitBtn;
	private JButton addReviewBtn;

	private JButton rentBtn;
	private JButton bookingBtn;
	private JButton returnBtn;

	private String[] columnNames = {"번호", "아이디", "제목", "작성일", "평점"};
	Object[][] rowData;

	private Delegate d = Delegate.getInstance();
	private BookDto dto;

	private int page = 1;
	private int page_size = 8;
	private JPanel pageBtnPanel;
	private JButton prevPageBtn;
	private JButton nextPageBtn;
	private JButton[] pageBtn;
	private JComboBox<String> typeCombo;
	private JTextArea infoTextA;

	private String loginId = "";
	private MemberDto mem = null;

	public BookDetailView(BookDto dto) {
		super("도서 세부 정보");
		this.dto = dto;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

		loginId = d.memCtrl.getLoginId();
		System.out.println("loginId : " + loginId);
		mem = d.memCtrl.memberCheck(loginId);

		int img_width = 373;
		int img_height = img_width*3/2;


		JPanel imgPanel = new JPanel(){
			private static final long serialVersionUID = 1L;
			@Override
			protected void paintComponent(Graphics g) {
				Image image = null;
				try {
					image = ImageIO.read(new File("img\\"+dto.getImg()));
				}
				catch (NullPointerException|IOException e) {
					try {
						image = ImageIO.read(new File("img\\notFound.jpg"));
					} catch (IOException e1) {}
				}
				image = image.getScaledInstance(img_width, img_height, Image.SCALE_SMOOTH);
				g.drawImage(image, 0, 0, this);
			}
		};

		imgPanel.setBorder(null);
		imgPanel.setBounds(14, 127, img_width, img_height);
		getContentPane().add(imgPanel);

		JPanel bookInfoPanel = new JPanel();
		bookInfoPanel.setBorder(new TitledBorder(null, "\uCC45 \uC815\uBCF4", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		bookInfoPanel.setLayout(null);
		bookInfoPanel.setBounds(407, 121, 713, 312);
		getContentPane().add(bookInfoPanel);


		JLabel titleLabel = new JLabel("제목");
		titleLabel.setBounds(14, 27, 38, 15);
		bookInfoPanel.add(titleLabel);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel authorlabel = new JLabel("저자");
		authorlabel.setBounds(14, 57, 38, 15);
		bookInfoPanel.add(authorlabel);
		authorlabel.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel publisherLabel = new JLabel("출판사");
		publisherLabel.setBounds(210, 57, 57, 15);
		bookInfoPanel.add(publisherLabel);
		publisherLabel.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel locationLabel = new JLabel("위치");
		locationLabel.setBounds(417, 57, 38, 15);
		bookInfoPanel.add(locationLabel);
		locationLabel.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel typeLabel = new JLabel("장르");
		typeLabel.setBounds(14, 84, 38, 15);
		bookInfoPanel.add(typeLabel);
		typeLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel isbnLabel = new JLabel("도서번호");
		isbnLabel.setBounds(210, 84, 57, 15);
		bookInfoPanel.add(isbnLabel);
		isbnLabel.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel rateLabel = new JLabel("평점");
		rateLabel.setBounds(417, 83, 57, 17);
		bookInfoPanel.add(rateLabel);
		rateLabel.setHorizontalAlignment(SwingConstants.LEFT);

		titleTextF = new JTextField(dto.getTitle());
		titleTextF.setBounds(57, 24, 221, 21);
		bookInfoPanel.add(titleTextF);
		titleTextF.setBorder(null);
		titleTextF.setEditable(false);
		titleTextF.setColumns(10);

		authorTextF = new JTextField(dto.getAuthor());
		authorTextF.setBounds(57, 54, 116, 21);
		bookInfoPanel.add(authorTextF);
		authorTextF.setBorder(null);
		authorTextF.setEditable(false);
		authorTextF.setColumns(10);

		publisherTextF = new JTextField(dto.getPublisher());
		publisherTextF.setBounds(273, 54, 116, 21);
		bookInfoPanel.add(publisherTextF);
		publisherTextF.setBorder(null);
		publisherTextF.setEditable(false);
		publisherTextF.setColumns(10);

		locationTextF = new JTextField(dto.getLocation());
		locationTextF.setBounds(467, 54, 116, 21);
		bookInfoPanel.add(locationTextF);
		locationTextF.setBorder(null);
		locationTextF.setEditable(false);
		locationTextF.setColumns(10);


		typeTextF = new JTextField(dto.getType());
		typeTextF.setBounds(57, 82, 116, 21);
		bookInfoPanel.add(typeTextF);
		typeTextF.setBorder(null);
		typeTextF.setEditable(false);
		typeTextF.setColumns(10);

		isbnTextF = new JTextField(dto.getIsbn());
		isbnTextF.setBounds(273, 81, 116, 21);

		bookInfoPanel.add(isbnTextF);
		isbnTextF.setBorder(null);
		isbnTextF.setEditable(false);
		isbnTextF.setColumns(10);

		int star_img_size = 17;
		JPanel rateImgPanel = new JPanel() {
			private static final long serialVersionUID = 1L;
			double avg_rate = d.bookReviewCtrl.getAvgBookRate(dto.getIsbn());
			protected void paintComponent(Graphics g) {
				Image image = null;
				try {

					for(int i = 0; i<5; i++){
						// 평균 평점의 정수부분 숫자만큼 모두 채워진 별을 그리고
						// 소수 부분은 소수점 2째자리에서 반올림한 후 0.1단위로 나누어서 그림
						if(i<(int)avg_rate){ // 모두 채워진 별
							image = ImageIO.read(new File("img\\star10.jpg"));
						}else if(i==(int)avg_rate){
							// j : avg_rate의 소수점 2번째 자리 반올림하고 소수점 1번째 자리수 가져옴
							int j = (int)((avg_rate+0.05)*10)%10;
							// == Math.round(avg_rate*10)%10;
							image = ImageIO.read(new File("img\\star" + j + ".jpg"));
						}else{ // 윤곽선만 있는 별
							image = ImageIO.read(new File("img\\star0.jpg"));
						}
						image = image.getScaledInstance(star_img_size, star_img_size, 4);
						g.drawImage(image, i*star_img_size, 0, this);
					}
				}
				catch (IOException e) {}
				catch (NullPointerException e) {}
			}
		};

		rateImgPanel.setBorder(null);
		rateImgPanel.setBackground(new Color(0xEEEEEE));
		rateImgPanel.setBounds(467, 82, star_img_size*5, star_img_size);
		bookInfoPanel.add(rateImgPanel);

		infoTextA = new JTextArea(dto.getInfo());
		infoTextA.setBackground(new Color(0xEEEEEE));
		infoTextA.setBorder(null);
		infoTextA.setEditable(false);
		infoTextA.setLineWrap(true);
		infoTextA.setBounds(0,0, 404, 218);
		JScrollPane scrollPane = new JScrollPane(infoTextA);
		scrollPane.setBounds(14, 114, 685, 186);
		bookInfoPanel.add(scrollPane);
		scrollPane.setBorder(null);

		JPanel reviewTablePanel = new JPanel();
		reviewTablePanel.setBorder(new TitledBorder(null, "리뷰", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		reviewTablePanel.setLayout(null);
		reviewTablePanel.setBounds(407, 433, 713, 254);
		getContentPane().add(reviewTablePanel);

		reviewTable = new JTable();
		reviewTable.addMouseListener(this);
		reviewTable.setBorder(null);

		JScrollPane reviewTableScrollPane = new JScrollPane(reviewTable);
		reviewTableScrollPane.setViewportBorder(null);
		reviewTableScrollPane.setToolTipText("");
		reviewTableScrollPane.setBorder(null);
		reviewTableScrollPane.setBounds(14, 29, 685, 150);
		reviewTablePanel.add(reviewTableScrollPane);

		pageBtnPanel = new JPanel();
		pageBtnPanel.setBounds(14, 179, 685, 30);
		reviewTablePanel.add(pageBtnPanel);
		pageBtnPanel.setLayout(null);

		bookReviewTable(d.bookReviewCtrl.getBookReviewList(dto.getIsbn()), 1);

		if(!d.memCtrl.getLoginId().equals("")){
			addReviewBtn = new JButton("리뷰 작성");
			addReviewBtn.setBounds(594, 220, 105, 27);
			addReviewBtn.addActionListener(this);
			reviewTablePanel.add(addReviewBtn);
		}

		if(!d.memCtrl.getLoginId().equals("") && mem.getAuth() != 3){
			titleTextF.setEditable(true);
			authorTextF.setEditable(true);
			isbnTextF.setEditable(true);
			locationTextF.setEditable(true);
			publisherTextF.setEditable(true);
			infoTextA.setEditable(true);
			infoTextA.setBackground(Color.WHITE);

			bookInfoPanel.remove(typeTextF);
			typeCombo = new JComboBox<String>();
			typeCombo.addItem("시");
			typeCombo.addItem("소설");
			typeCombo.addItem("요리");
			typeCombo.addItem("과학");
			typeCombo.addItem("교양");
			typeCombo.setSelectedItem(dto.getType());
			typeCombo.setBounds(57, 78, 116, 21);
			bookInfoPanel.add(typeCombo);

			updateBtn = new JButton("책 수정");
			updateBtn.setBounds(908, 697, 100, 40);
			getContentPane().add(updateBtn);
			updateBtn.addActionListener(this);
		}

		exitBtn = new JButton("닫기");
		exitBtn.setBounds(1020, 697, 100, 40);
		getContentPane().add(exitBtn);
		exitBtn.addActionListener(this);



		boolean myRent = d.rentCtrl.checkMyRent(d.memCtrl.getLoginId(), dto.getIsbn()); // 내가 빌린 도서인지 확인
		boolean bookingState = d.bookCtrl.checkBookingState(dto.getIsbn());

		if(d.memCtrl.getLoginId() != "" && mem.getAuth() != 1){ // 로그인 상태인지 확인

			if(dto.getRentstate().equals("대여가능")){ // 도서가 대여가능한 상태인지 확인
				rentBtn = new JButton("대여");
				rentBtn.setBounds(577, 697, 100, 40);
				rentBtn.addActionListener(this);
				getContentPane().add(rentBtn);

			}else if(bookingState == true && myRent == false){ // 도서가 예약가능한 상태이며, 내가빌린도서가 아닌지 확인
				bookingBtn = new JButton("예약");
				bookingBtn.setBounds(577, 697, 100, 40);
				bookingBtn.addActionListener(this);
				getContentPane().add(bookingBtn);
			}
		}

		if (myRent){
			returnBtn = new JButton("반납");
			returnBtn.setBounds(577, 697, 100, 40);
			returnBtn.addActionListener(this);
			getContentPane().add(returnBtn);
		}

		setSize(1150,808);
		setLocationRelativeTo(null);
		setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				if(!d.memCtrl.getLoginId().equals("admin") && !d.memCtrl.getLoginId().equals("")){
					new UserView(d.memCtrl.getMemberRentInfo(loginId));
				}
			}
		});
	}

	public void bookReviewTable(List<BookReviewDto> list){
		bookReviewTable(list, this.page);
	}
	public void bookReviewTable(List<BookReviewDto> list, int page){
		this.page = page;
		int start_num = (this.page-1)*page_size;                  // 해당 페이지 시작번호
		int last_num = start_num+page_size;                  // 해당 페이지 마지막 번호
		if(last_num > list.size()) last_num = list.size();

		rowData = new Object[last_num-start_num][6];
		for (int i = 0; i < rowData.length; i++) {
			rowData[i][0] = start_num+1;
			rowData[i][1] = list.get(start_num).getId();
			rowData[i][2] = list.get(start_num).getTitle();
			rowData[i][3] = list.get(start_num).getWritedate();
			String rate = "";
			for (int j = 0; j<list.get(start_num).getRate(); j++)
				rate += "★";
			rowData[i][4] = "<html><p style=\"color:#FF0000\">" + rate + "</p><html>";
			rowData[i][5] = list.get(start_num).getIndex();
			start_num++;
		}

		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		model.setDataVector(rowData, columnNames);
		reviewTable.setModel(model);

		reviewTable.getColumnModel().getColumn(0).setMaxWidth(50);
		reviewTable.getColumnModel().getColumn(1).setMaxWidth(50);
		reviewTable.getColumnModel().getColumn(2).setMaxWidth(400);
		reviewTable.getColumnModel().getColumn(3).setMaxWidth(130);
		reviewTable.getColumnModel().getColumn(4).setMaxWidth(70);

		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);

		reviewTable.getColumn("번호").setCellRenderer(celAlignCenter);
		reviewTable.getColumn("아이디").setCellRenderer(celAlignCenter);
		reviewTable.getColumn("제목").setCellRenderer(celAlignCenter);
		reviewTable.getColumn("작성일").setCellRenderer(celAlignCenter);
		reviewTable.getColumn("평점").setCellRenderer(celAlignCenter);

		pageBtnPanel.removeAll();

		int page_count = (list.size()+(page_size-1))/page_size; // 총 페이지 개수
		int btn_x_position = pageBtnPanel.getWidth()/2- 20*page_count - 35; // 버튼 x좌표 시작 위치
		prevPageBtn = new JButton("<");
		prevPageBtn.setBounds(btn_x_position, 0, 30, 30);
		prevPageBtn.setBackground(new Color(0xEEEEEE));
		prevPageBtn.setBorder(null);

		if(page==1){
			prevPageBtn.setEnabled(false);
			prevPageBtn.setForeground(Color.GRAY);
		}
		else{	prevPageBtn.addActionListener(this);}
		pageBtnPanel.add(prevPageBtn);

		nextPageBtn = new JButton(">");
		nextPageBtn.setBounds(btn_x_position + (page_count+1)*40, 0, 30, 30);
		nextPageBtn.setBackground(new Color(0xEEEEEE));
		nextPageBtn.setBorder(null);
		if(page>=page_count){
			nextPageBtn.setEnabled(false);
			nextPageBtn.setForeground(Color.GRAY);
		}
		else{ nextPageBtn.addActionListener(this);}
		pageBtnPanel.add(nextPageBtn);

		pageBtn = new JButton[page_count];
		for (int i = 0; i < pageBtn.length; i++) {
			pageBtn[i] = new JButton(i+1 + "");
			pageBtn[i].setBounds(btn_x_position + (i+1)*40, 0, 30, 30);
			pageBtn[i].setBackground(new Color(0xEEEEEE));
			pageBtn[i].setBorder(null);
			if(page==i+1){
				pageBtn[i].setEnabled(false);
				pageBtn[i].setForeground(Color.GRAY);
			}else{
				pageBtn[i].addActionListener(this);
			}
			pageBtnPanel.add(pageBtn[i]);
		}
		pageBtnPanel.updateUI();

	}

	@Override public void actionPerformed(ActionEvent e) {
		String isbn = isbnTextF.getText();
		String id = d.memCtrl.getLoginId();

		if (e.getSource() == rentBtn){ // 대여
			if(d.memCtrl.getRentCount(id)){ // 1.대여 갯수가 4개 이하일 경우, 대여 허용(true)으로 보고 대여갯수를 +1 시킴 (MEMBER)
				boolean addrentList = d.rentCtrl.addRent(isbn, id);
				boolean addrentLog = d.rentLogCtrl.addRentLog(isbn, id);
				boolean changeRentState =d.bookCtrl.changeRentState(isbn, "대여중");
				if(addrentList && addrentLog && changeRentState){
					JOptionPane.showMessageDialog(null, "대여 성공 : 대여 되었습니다.");
				}
				this.dispose();
				new UserView(d.memCtrl.getMemberRentInfo(id)); // 새로고침

			}else{ // 대여 갯수가 최대 한도인 3개일 경우, 오류메시지 출력
				JOptionPane.showMessageDialog(null, "대여 불가 : 최대 대여 가능 갯수 5개를 초과하였습니다.");
			}

		}else if(e.getSource() == bookingBtn){ // 예약
			boolean bookingAllow = d.memCtrl.getBookingCount(id); // 1.예약 갯수가 4개 이하일 경우, 대여 허용(true)으로 보고 대여갯수를 +1 시킴 (MEMBER)
			if(bookingAllow){
				boolean addBookingID =  d.bookCtrl.addBookingID(id, isbn); // 2.예약 id 추가
				if(addBookingID){
					JOptionPane.showMessageDialog(null, "예약 성공 : 예약 되었습니다.");
					this.dispose();
					new UserView(d.memCtrl.getMemberRentInfo(id)); // 새로고침
				}
				else{ JOptionPane.showMessageDialog(null, "예약에 실패하였습니다."); }

			}else{ // 대여 갯수가 최대 한도인 3개일 경우, 오류메시지 출력
				JOptionPane.showMessageDialog(null, "예약 불가 : 최대 예약 가능 갯수 5개를 초과하였습니다.");
			}

		}else if(e.getSource() == returnBtn){ // 반납
			boolean deleteRent = d.rentCtrl.deleteRent(isbn);
			boolean addreturnLog = d.rentLogCtrl.addReturnLog(isbn, id);
			boolean changeRentState = d.bookCtrl.changeRentState(isbn, "대여가능");
			boolean minusRentCount = d.memCtrl.minusRentCount(id);

			if(deleteRent && addreturnLog && changeRentState && minusRentCount){
				JOptionPane.showMessageDialog(null, "반납 성공 : 반납 되었습니다.");
			}else{
				JOptionPane.showMessageDialog(null, "반납 실패 : 반납 실패했습니다.");
			}
			this.dispose();
			new UserView(d.memCtrl.getMemberRentInfo(id)); // 새로고침
		}

		if(e.getSource()==addReviewBtn){
			d.bookReviewCtrl.reviewAdd(dto.getIsbn());
			this.dispose();
		}
		if(e.getSource()==prevPageBtn){
			bookReviewTable(d.bookReviewCtrl.getBookReviewList(dto.getIsbn()), page-1);
		}else if(e.getSource()==nextPageBtn){
			bookReviewTable(d.bookReviewCtrl.getBookReviewList(dto.getIsbn()), page+1);
		}
		for (int i = 0; i < pageBtn.length; i++) {
			if(page==i+1) {continue;}
			if(e.getSource()==pageBtn[i]){
				bookReviewTable(d.bookReviewCtrl.getBookReviewList(dto.getIsbn()), i+1);
			}
		}

		if(e.getSource() == exitBtn){
			this.dispose();
			if(!d.memCtrl.getLoginId().equals("admin") && !d.memCtrl.getLoginId().equals("")){
				new UserView(d.memCtrl.getMemberRentInfo(loginId));
			}
		}else if(e.getSource() == updateBtn){
			String type = typeCombo.getSelectedItem().toString();
			d.bookCtrl.updateBook(titleTextF.getText(), authorTextF.getText(), publisherTextF.getText(),
					type, isbnTextF.getText(), infoTextA.getText());
			this.dispose();
		}
	}

	@Override public void mousePressed(MouseEvent e) {
		int rowNum = reviewTable.getSelectedRow();
		if(rowNum==-1) return;
		int index = (int) rowData[rowNum][5];
		d.bookReviewCtrl.showReviewDetail(this.dto, index);
		this.dispose();
	}
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
}
