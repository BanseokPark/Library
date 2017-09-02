package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dto.RentLogDto;
import single.Delegate;


public class UserRentLogPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	Delegate d = Delegate.getInstance();

	private JPanel pageBtnPanel;
	private JButton prevPageBtn;
	private JButton nextPageBtn;
	private JButton[] pageBtn;

	private List<RentLogDto> list;
	private JComboBox<String> selectCombo;
	private JTextField selectTextF;
	private JButton selectBtn;
	private JTable table;
	public JScrollPane scrollPane;

	private int page = 1;

	private String[] columnNames = {"번호", "코드", "제목", "장르", "출판사", "저자", "날짜", "대여/반납"};

	DefaultTableModel model;
	private Object[][] rowData;
	private int page_size;



	// 날짜검색
	private JTextField dateTextF;
	private JLabel barLabel;

	JComboBox<Integer> firstYearCombo;
	JComboBox<Integer> firstMonthCombo;    
	JComboBox<Integer> firstDayCombo;
	JComboBox<Integer> lastYearCombo;
	JComboBox<Integer> lastMonthCombo;    
	JComboBox<Integer> lastDayCombo;

	int firstDays[]={31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	int firstLeapDays[]={31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //윤달
	int lastDays[]={31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	int lastLeapDays[]={31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //윤달

	UserRentLogView view;

	public UserRentLogPanel(UserRentLogView view, int page_size) {
		this.page_size = page_size;
		this.view = view;
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "대여/반납 목록", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		setLayout(null);
		setBounds(12, 22, 886, 464);
		pageBtnPanel = new JPanel();
		pageBtnPanel.setBounds(29, 413, 825, 30);
		add(pageBtnPanel);
		pageBtnPanel.setLayout(null);

		// 날짜 검색
		dateTextF = new JTextField("기간");
		dateTextF.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		dateTextF.setBackground(new Color(0xEEEEEE));
		dateTextF.setForeground(Color.BLACK);
		dateTextF.setBounds(14, 25, 116, 30);
		//dateTextF.setEnabled(false);


		firstYearCombo = new JComboBox<Integer>();
		firstYearCombo.setBounds(145, 25, 85, 30);

		firstMonthCombo = new JComboBox<Integer>();
		firstMonthCombo.setBounds(240, 25, 85, 30);		

		firstDayCombo = new JComboBox<Integer>();
		firstDayCombo.setBounds(335, 25, 85, 30);
		for (int i=1; i <=firstDays[0]; i++) {
			firstDayCombo.addItem(i);
		}

		barLabel = new JLabel(" ~ ");
		barLabel.setBounds(438, 25, 20, 30);

		lastYearCombo = new JComboBox<Integer>();
		lastYearCombo.setBounds(468, 25, 85, 30);

		lastMonthCombo = new JComboBox<Integer>();
		lastMonthCombo.setBounds(563, 25, 85, 30);		

		lastDayCombo = new JComboBox<Integer>();
		lastDayCombo.setBounds(658, 25, 85, 30);
		for (int i=1; i <=lastDays[0]; i++) {
			lastDayCombo.addItem(i);
		}
		///// 시작
		//년
		Calendar cal  = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);		
		for (int y = year; y >= 2010; y--) {firstYearCombo.addItem(y);}	

		// 월
		for (int m = 1; m <= 12; m++) {	firstMonthCombo.addItem(m);}				

		// 윤년 확인
		firstYearCombo.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				firstDayCombo.removeAllItems();
				int mon = (Integer)firstMonthCombo.getSelectedItem();
				if((Integer)(firstYearCombo.getSelectedItem())%4 != 0){
					for (int d = 1; d <= firstDays[mon-1]; d++) {firstDayCombo.addItem(d);}
				}else{
					for (int d = 1; d <= firstLeapDays[mon-1]; d++) {firstDayCombo.addItem(d);}	
				}
			}
		});	

		// 일
		firstMonthCombo.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				firstDayCombo.removeAllItems();
				int mon = (Integer)firstMonthCombo.getSelectedItem();				
				if((Integer)(firstYearCombo.getSelectedItem())%4 != 0){
					for (int d = 1; d <= firstDays[mon-1]; d++) {firstDayCombo.addItem(d);}
				}else{
					for (int d = 1; d <= firstLeapDays[mon-1]; d++) {firstDayCombo.addItem(d);}					
				}
			}
		});

		///// 종료
		//년		
		for (int y = year; y >= 2010; y--) {lastYearCombo.addItem(y);}	

		// 월
		for (int m = 1; m <= 12; m++) {lastMonthCombo.addItem(m);}				

		// 윤년 확인
		lastYearCombo.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				lastDayCombo.removeAllItems();
				int mon = (Integer)lastMonthCombo.getSelectedItem();	
				if((Integer)(lastYearCombo.getSelectedItem())%4 != 0){
					for (int d = 1; d <= lastDays[mon-1]; d++) {lastDayCombo.addItem(d);}
				}else{
					for (int d = 1; d <= lastLeapDays[mon-1]; d++) {lastDayCombo.addItem(d);}					
				}		
			}
		});	

		// 일
		lastMonthCombo.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				lastDayCombo.removeAllItems();
				int mon = (Integer)lastMonthCombo.getSelectedItem();	
				if((Integer)(lastYearCombo.getSelectedItem())%4 != 0){
					for (int d = 1; d <= lastDays[mon-1]; d++) {lastDayCombo.addItem(d);}
				}else{
					for (int d = 1; d <= lastLeapDays[mon-1]; d++) {lastDayCombo.addItem(d);}					
				}	
			}
		});		





		add(dateTextF);
		add(barLabel);
		add(firstYearCombo);
		add(firstMonthCombo);
		add(firstDayCombo);
		add(lastYearCombo);
		add(lastMonthCombo);
		add(lastDayCombo);

		selectCombo = new JComboBox<String>();
		selectCombo.addItem("제목");
		selectCombo.addItem("저자");
		selectCombo.addItem("출판사");
		selectCombo.addItem("장르");

		selectCombo.setBounds(14, 71, 116, 30);
		add(selectCombo);

		selectTextF = new JTextField();
		selectTextF.setBounds(144, 71, 602, 30);
		add(selectTextF);
		selectTextF.addActionListener(this);
		selectTextF.setColumns(10);

		//	dateBtn = new JButton("날짜 검색");
		//	dateBtn.setBounds(760, 25, 111, 30);
		//	dateBtn.addActionListener(this);
		//	add(dateBtn);

		//selectBtn = new JButton("검 색");
		//selectBtn.setBounds(760, 71, 111, 30);
		//selectBtn.addActionListener(this);
		//add(selectBtn);

		selectBtn = new JButton("검 색");
		selectBtn.setBounds(760, 25, 111, 76);
		selectBtn.addActionListener(this);
		add(selectBtn);

		table = new JTable();

		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(14, 113, 857, 343);
		add(scrollPane);

		//bookTable(Delegate.getInstance().bookCtrl.getList(), this.page);
		rentLogTable(d.rentLogCtrl.getReturnList(d.memCtrl.getLoginId()), this.page);
	}

	public void rentLogTable(List<RentLogDto> list , int page){
		this.list = list;
		this.page = page;
		int start_num = (page-1)*page_size;                  // 해당 페이지 시작번호   
		int last_num = start_num+page_size;                  // 해당 페이지 마지막 번호
		if(last_num > list.size()) last_num = list.size();   // 마지막 페이지의 경우 page_size보다 row가 적을수 있으므로 

		rowData = new Object[last_num-start_num][columnNames.length];
		for (int i = 0; i < rowData.length; i++) {
			rowData[i][0] = i+1;
			rowData[i][1] = list.get(start_num).getIsbn();
			rowData[i][2] = list.get(start_num).getTitle();
			rowData[i][3] = list.get(start_num).getType();
			rowData[i][4] = list.get(start_num).getPublisher();
			rowData[i][5] = list.get(start_num).getAuthor();
			rowData[i][6] = list.get(start_num).getRentdate();
			rowData[i][7] = list.get(start_num).getRentstate();//imgPanel;
			start_num++;
		}

		model = new DefaultTableModel(columnNames, 0);
		model.setDataVector(rowData, columnNames);			
		table.setModel(model);

		table.getColumnModel().getColumn(0).setMaxWidth(50);
		table.getColumnModel().getColumn(1).setMaxWidth(100);
		table.getColumnModel().getColumn(2).setMaxWidth(300);
		table.getColumnModel().getColumn(3).setMaxWidth(100);
		table.getColumnModel().getColumn(4).setMaxWidth(100);
		table.getColumnModel().getColumn(5).setMaxWidth(100);
		table.getColumnModel().getColumn(6).setMaxWidth(100);
		table.getColumnModel().getColumn(7).setMaxWidth(100);


		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);

		table.getColumn("번호").setCellRenderer(celAlignCenter);
		table.getColumn("코드").setCellRenderer(celAlignCenter);
		table.getColumn("제목").setCellRenderer(celAlignCenter);
		table.getColumn("장르").setCellRenderer(celAlignCenter);
		table.getColumn("출판사").setCellRenderer(celAlignCenter);
		table.getColumn("저자").setCellRenderer(celAlignCenter);
		table.getColumn("날짜").setCellRenderer(celAlignCenter);
		table.getColumn("대여/반납").setCellRenderer(celAlignCenter);

		table.addMouseListener(new MouseListener() {

			@Override public void mousePressed(MouseEvent e) {				
				int rowNum = table.getSelectedRow();
				System.out.println("tableSize : " + table.getRowCount());
				System.out.println("rowNum : " + rowNum);				

				if(rowNum == -1) return;			
				String isbn = (String) list.get(rowNum).getIsbn(); // seq		
				d.bookCtrl.showDetail(isbn);
				view.dispose();

			}
			@Override public void mouseReleased(MouseEvent e) {}	
			@Override public void mouseExited(MouseEvent e) {}			
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseClicked(MouseEvent e) {}
		});




		pageBtnPanel.removeAll();

		int page_count = (list.size()+(page_size-1))/page_size; // 총 페이지 개수
		int btn_x_position = pageBtnPanel.getWidth()/2-40 * page_count/2; // 버튼 x좌표 시작 위치
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
		if(page==page_count){
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
	@Override
	public void actionPerformed(ActionEvent e) {
		//String firstDate; 
		//String lastDate;
		if(e.getSource()== selectBtn){

			//검색정보가 없을 경우
			if(selectTextF.getText().equals("") && firstDayCombo.getSelectedItem() == null && lastDayCombo.getSelectedItem() == null){ 
				JOptionPane.showMessageDialog(null, "검색기간 선택 또는 검색어를 입력하세요.");

				// 날짜로 검색하는 경우	
			}else if(selectTextF.getText().equals("") && (firstDayCombo.getSelectedItem() != null || lastDayCombo.getSelectedItem() != null)){ 
				if(firstDayCombo.getSelectedItem() == null){
					JOptionPane.showMessageDialog(null, "시작일을 다시 설정해주세요.");
				}else if(lastDayCombo.getSelectedItem() == null){
					JOptionPane.showMessageDialog(null, "종료일을 다시 설정해주세요.");
				}else{
					String yy1 = firstYearCombo.getSelectedItem().toString();
					String mm1 = firstMonthCombo.getSelectedItem().toString();
					String dd1 = firstDayCombo.getSelectedItem().toString();
					String yy2 = lastYearCombo.getSelectedItem().toString();
					String mm2 = lastMonthCombo.getSelectedItem().toString();
					String dd2 = lastDayCombo.getSelectedItem().toString();
					String firstDate = yy1 + "/" + mm1 + "/" + dd1;
					String lastDate = yy2 + "/" + mm2 + "/" + dd2;

					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
						Date date1 = sdf.parse(firstDate);
						Date date2 = sdf.parse(lastDate);			       
						long term = (date2.getTime()- date1.getTime()) / (24 * 60 * 60 * 1000);

						if(term < 0){
							JOptionPane.showMessageDialog(null, "시작일이 종료일보다 큽니다. 다시 설정해주세요. ");
						}else{
							// 넘기기!!
							list = d.rentLogCtrl.selectRentLogList(d.memCtrl.getLoginId(), null, null, firstDate, lastDate);			
							// for (RentDto dto : rentLogList) { System.out.println(dto.toString());}
							rentLogTable(list, this.page);	


						}						
					} catch (ParseException e1) {e1.printStackTrace();}	
				}

				//단어로 검색하는 경우
			}else if(selectTextF.getText() != "" && (firstDayCombo.getSelectedItem() == null && lastDayCombo.getSelectedItem() == null)){

				// 단어 검색
				String searchT =selectCombo.getSelectedItem().toString(); // 검색 종류		
				String type = null;		
				if(searchT.equals("제목")){ type = "TITLE"; }
				else if(searchT.equals("출판사")){ type = "PUBLISHER"; }
				else if(searchT.equals("저자")){ type = "AUTHOR"; }
				else if(searchT.equals("장르")){ type = "TYPE"; }			

				String txt = selectTextF.getText();	 // 검색 단어				

				// 넘기기!!
				list = d.rentLogCtrl.selectRentLogList(d.memCtrl.getLoginId(), type, txt, null, null);			
				// for (RentDto dto : rentLogList) { System.out.println(dto.toString());}
				rentLogTable(list, this.page);	

				// 날짜+단어로 검색하는 경우
			}else{
				String firstDate = null;
				String lastDate = null;

				// 단어 검색
				String searchT =selectCombo.getSelectedItem().toString(); // 검색 종류		
				String type = null;		
				if(searchT.equals("제목")){ type = "TITLE"; }
				else if(searchT.equals("출판사")){ type = "PUBLISHER"; }
				else if(searchT.equals("저자")){ type = "AUTHOR"; }
				else if(searchT.equals("장르")){ type = "TYPE"; }			

				String txt = selectTextF.getText();	 // 검색 단어			


				// 날짜 검색

				if(firstDayCombo.getSelectedItem() == null){
					JOptionPane.showMessageDialog(null, "시작일을 다시 설정해주세요.");
				}else if(lastDayCombo.getSelectedItem() == null){
					JOptionPane.showMessageDialog(null, "종료일을 다시 설정해주세요.");
				}else{
					String yy1 = firstYearCombo.getSelectedItem().toString();
					String mm1 = firstMonthCombo.getSelectedItem().toString();
					String dd1 = firstDayCombo.getSelectedItem().toString();
					String yy2 = lastYearCombo.getSelectedItem().toString();
					String mm2 = lastMonthCombo.getSelectedItem().toString();
					String dd2 = lastDayCombo.getSelectedItem().toString();
					firstDate = yy1 + "/" + mm1 + "/" + dd1;
					lastDate = yy2 + "/" + mm2 + "/" + dd2;

					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
						Date date1 = sdf.parse(firstDate);
						Date date2 = sdf.parse(lastDate);			       
						long term = (date2.getTime()- date1.getTime()) / (24 * 60 * 60 * 1000);

						if(term < 0){
							JOptionPane.showMessageDialog(null, "시작일이 종료일보다 큽니다. 다시 설정해주세요. ");
						}else{
							JOptionPane.showMessageDialog(null, "시작일 : " + firstDate + ", 종료일 : " + lastDate);       				        	
						}						
					} catch (ParseException e1) {e1.printStackTrace();}	
				}


				System.out.println("firstDate : " + firstDate);
				System.out.println("lastDate : " + lastDate);


				// 넘기기!!
				list = d.rentLogCtrl.selectRentLogList(d.memCtrl.getLoginId(), type, txt, firstDate, lastDate);			
				// for (RentDto dto : rentLogList) { System.out.println(dto.toString());}
				rentLogTable(list, this.page);	
			}


		}


		if(e.getSource()==prevPageBtn){
			rentLogTable(this.list, page-1);
		}else if(e.getSource()==nextPageBtn){
			rentLogTable(this.list, page+1);
		}
		for (int i = 0; i < pageBtn.length; i++) {
			if(page==i+1) {continue;}
			if(e.getSource()==pageBtn[i]){
				rentLogTable(this.list, i+1);
			}
		}
	}


}
