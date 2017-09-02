package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dto.BookRequestDto;

import single.Delegate;


public class UserBookRequestPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	Delegate d = Delegate.getInstance();

	private JPanel pageBtnPanel;
	private JButton prevPageBtn;
	private JButton nextPageBtn;
	private JButton[] pageBtn;

	private List<BookRequestDto> list;
	private JComboBox<String> selectCombo;
	private JTextField selectTextF;
	private JButton selectBtn;
	private JTable table;
	public JScrollPane scrollPane;
	
	private int page = 1;
	
	private String[] columnNames = {"번호", "제목", "저자", "출판사", "장르", "신청일", "상태"};
	DefaultTableModel model;
	private Object[][] rowData;
	private int page_size;
	


	public UserBookRequestPanel(int page_size) {
		this.page_size = page_size;
		
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "신청도서목록", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		setLayout(null);
		setBounds(12, 22, 586, 468);
		pageBtnPanel = new JPanel();
		pageBtnPanel.setBounds(14, 413, 560, 30);
		add(pageBtnPanel);
		pageBtnPanel.setLayout(null);
			
		// 검색
		selectCombo = new JComboBox<String>();
		selectCombo.addItem("제목");
		selectCombo.addItem("저자");
		selectCombo.addItem("출판사");
		selectCombo.addItem("장르");

		selectCombo.setBounds(14, 21, 116, 30);
		add(selectCombo);

		selectTextF = new JTextField();
		selectTextF.setBounds(144, 21, 302, 30);
		add(selectTextF);
		selectTextF.addActionListener(this);
		selectTextF.setColumns(10);

		selectBtn = new JButton("검 색");
		selectBtn.setBounds(460, 21, 111, 30);
		selectBtn.addActionListener(this);
		add(selectBtn);

		table = new JTable();

		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(14, 63, 557, 343);
		add(scrollPane);

		//bookTable(Delegate.getInstance().bookCtrl.getList(), this.page);
		bookRequestTable(d.bookRequestCtrl.getBookRequestList(d.memCtrl.getLoginId()), this.page);
		
	}

	public void bookRequestTable(List<BookRequestDto> list , int page){
		this.list = list;
		this.page = page;
		int start_num = (page-1)*page_size;                  // 해당 페이지 시작번호   
		int last_num = start_num+page_size;                  // 해당 페이지 마지막 번호
		if(last_num > list.size()) last_num = list.size();   // 마지막 페이지의 경우 page_size보다 row가 적을수 있으므로 
		
		rowData = new Object[last_num-start_num][columnNames.length];
		for (int i = 0; i < rowData.length; i++) {
			rowData[i][0] = i+1;
			rowData[i][1] = list.get(start_num).getTitle();
			rowData[i][2] = list.get(start_num).getAuthor();
			rowData[i][3] = list.get(start_num).getPublisher();
			rowData[i][4] = list.get(start_num).getType();
			rowData[i][5] = list.get(start_num).getDate();
			rowData[i][6] = list.get(start_num).getState();
			start_num++;
		}
		
		model = new DefaultTableModel(columnNames, 0);
		model.setDataVector(rowData, columnNames);			
		table.setModel(model);
		
		table.getColumnModel().getColumn(0).setMaxWidth(50);
		table.getColumnModel().getColumn(1).setMaxWidth(300);
		table.getColumnModel().getColumn(2).setMaxWidth(100);
		table.getColumnModel().getColumn(3).setMaxWidth(100);
		table.getColumnModel().getColumn(4).setMaxWidth(80);
		table.getColumnModel().getColumn(5).setMaxWidth(80);
		table.getColumnModel().getColumn(6).setMaxWidth(50);


		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);
		
		table.getColumn("번호").setCellRenderer(celAlignCenter);
		table.getColumn("제목").setCellRenderer(celAlignCenter);
		table.getColumn("저자").setCellRenderer(celAlignCenter);
		table.getColumn("출판사").setCellRenderer(celAlignCenter);
		table.getColumn("장르").setCellRenderer(celAlignCenter);	
		table.getColumn("신청일").setCellRenderer(celAlignCenter);
		table.getColumn("상태").setCellRenderer(celAlignCenter);			

		pageBtnPanel.removeAll();		
		int page_count = (list.size()+(page_size-1))/page_size; // 총 페이지 개수
		
		// 1~10 / 11~20 / 21~30 등  page버튼을 10개만 보여줌. 
		int start_page = (page-1)/10*10; // 일의자리에서 내림
		int last_page = page_count < (page+9)/10*10 ? page_count : (page+9)/10*10; //일의 자리에서 올림
		
		int btn_x_position = pageBtnPanel.getWidth()/2 - 20*(last_page-start_page) - 35; // 버튼 x좌표 시작 위치
		
		prevPageBtn = new JButton("<");
		prevPageBtn.setBounds(btn_x_position, 0, 30, 30);
		prevPageBtn.setBackground(new Color(0xEEEEEE));
		prevPageBtn.setBorder(null);
		
		if(page==1){ // 현재 페이지가 1일경우 이전페이지 버튼 비활성화
			prevPageBtn.setEnabled(false);
			prevPageBtn.setForeground(Color.GRAY);
		}
		else{	prevPageBtn.addActionListener(this);}
		pageBtnPanel.add(prevPageBtn);

		nextPageBtn = new JButton(">");
		nextPageBtn.setBounds(btn_x_position + (last_page-start_page+1)*40, 0, 30, 30);
		nextPageBtn.setBackground(new Color(0xEEEEEE));
		nextPageBtn.setBorder(null);
		
		if(page>=page_count){ // 현재 페이지가 전체 페이지 수보다 크거나 같을경우 버튼 비활성화
			nextPageBtn.setEnabled(false);
			nextPageBtn.setForeground(Color.GRAY);
		}
		else{ nextPageBtn.addActionListener(this);}
		pageBtnPanel.add(nextPageBtn);

		// 전체 페이지의 개수만큼 JButton배열을 만들고 현재 페이지 버튼 비활성화
		pageBtn = new JButton[page_count];
		
		System.out.println("Start page:" + start_page);
		System.out.println("last page:" + last_page);
		for (int i = start_page; i < last_page; i++) {
			pageBtn[i] = new JButton(i+1 + "");
			pageBtn[i].setBounds(btn_x_position + (i-start_page+1)*40, 0, 30, 30);
			pageBtn[i].setBackground(new Color(0xEEEEEE));
			pageBtn[i].setBorder(null);
			if(page==i+1){
				pageBtn[i].setText("<html><p style=\"color:red\"><u>"+(i+1)+"</u></p></html>");
			}else{
				pageBtn[i].addActionListener(this);
			}
			pageBtnPanel.add(pageBtn[i]);
		}
		pageBtnPanel.updateUI();


	}			
	@Override
	public void actionPerformed(ActionEvent e) {

		Delegate d = Delegate.getInstance();

		if(e.getSource()==selectBtn || e.getSource()==selectTextF){
			// 종류
			String searchT =selectCombo.getSelectedItem().toString();			
			String type = null;		
			if(searchT.equals("제목")){ type = "TITLE"; }
			else if(searchT.equals("출판사")){ type = "PUBLISHER"; }
			else if(searchT.equals("저자")){ type = "AUTHOR"; }
			else if(searchT.equals("장르")){ type = "TYPE"; }
			
			// 검색어			
			String txt = selectTextF.getText();	
			list = d.bookRequestCtrl.searchBookRequestList(d.memCtrl.getLoginId(), type, txt);		
			// for (RentDto dto : rentLogList) { System.out.println(dto.toString());}
			bookRequestTable(list, this.page);		
			
		}else if(e.getSource()==prevPageBtn){
			bookRequestTable(this.list, page-1);
		}else if(e.getSource()==nextPageBtn){
			bookRequestTable(this.list, page+1);
		}
		for (int i = 0; i < pageBtn.length; i++) {
			if(page==i+1) {continue;}
			if(e.getSource()==pageBtn[i]){
				bookRequestTable(this.list, i+1);
			}
		}
	}


}
