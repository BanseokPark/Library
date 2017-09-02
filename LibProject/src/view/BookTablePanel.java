package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dto.BookDto;
import single.Delegate;

public class BookTablePanel extends JPanel implements ActionListener, MouseListener{
	private static final long serialVersionUID = 1L;

	private JPanel pageBtnPanel;
	private JButton prevPageBtn;
	private JButton nextPageBtn;
	private JButton[] pageBtn;

	private List<BookDto> list;
	private JComboBox<String> selectCombo;
	private JTextField selectTextF;
	private JButton selectBtn;
	private JTable table;
	public JScrollPane scrollPane;
	
	private String[] columnNames = {"번호", "제목", "저자", "출판사" , "장르", "대여상태"	};
	
	DefaultTableModel model;
	private Object[][] rowData;
	
	private int page = 1;
	private int page_size;
	JFrame view;

	public BookTablePanel(JFrame view, int page_size) {
		this.view = view;
		this.page_size = page_size;
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\uB3C4\uC11C \uAC80\uC0C9", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		setLayout(null);
		setBounds(12, 22, 886, 458);
		pageBtnPanel = new JPanel();
		pageBtnPanel.setBounds(14, 418, 857, 30);
		add(pageBtnPanel);
		pageBtnPanel.setLayout(null);
		
		selectCombo = new JComboBox<String>();
		selectCombo.addItem("제목");
		selectCombo.addItem("저자");
		selectCombo.addItem("출판사");
		selectCombo.addItem("장르");

		selectCombo.setBounds(14, 32, 116, 30);
		add(selectCombo);

		selectTextF = new JTextField();
		selectTextF.setBounds(144, 32, 602, 30);
		add(selectTextF);
		selectTextF.addActionListener(this);
		selectTextF.setColumns(10);

		selectBtn = new JButton("검 색");
		selectBtn.setBounds(760, 32, 111, 30);
		selectBtn.addActionListener(this);
		add(selectBtn);

		table = new JTable();
		//.setBorder(null);
		table.addMouseListener(this);

		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(14, 74, 857, 343);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		add(scrollPane);

		bookTable(Delegate.getInstance().bookCtrl.getList(), this.page);
	}

	public void bookTable(List<BookDto> list , int page){
		this.list = list;
		this.page = page;
		int start_num = (page-1)*page_size;                  // 해당 페이지 시작번호   
		int last_num = start_num+page_size;                  // 해당 페이지 마지막 번호
		if(last_num > list.size()) last_num = list.size();   // 마지막 페이지의 경우 page_size보다 row가 적을수 있으므로 
		//System.out.println("page :"+page);
		//System.out.println("start:" + start_num);
		//System.out.println("last :" + last_num);
		
		rowData = new Object[last_num-start_num][columnNames.length];
		for (int i = 0; i < rowData.length; i++) {
			rowData[i][0] = list.get(start_num).getIsbn();
			rowData[i][1] = list.get(start_num).getTitle();
			rowData[i][2] = list.get(start_num).getAuthor();
			rowData[i][3] = list.get(start_num).getPublisher();
			rowData[i][4] = list.get(start_num).getType();
			if(list.get(start_num).getRentstate().equals("대여중")){
				rowData[i][5] = "<HTML><p style=\"color:#FF0000\">" + list.get(start_num).getRentstate() + "</p></HTML>"; 
			}else if(list.get(start_num).getRentstate().equals("대여가능")){
				rowData[i][5] = "<HTML><p style=\"color:0100FF\">" + list.get(start_num).getRentstate() + "</p></HTML>";
			}
			start_num++;
		}

		model = new DefaultTableModel(columnNames, 0);
		model.setDataVector(rowData, columnNames);			
		table.setModel(model);

		table.getColumnModel().getColumn(0).setMaxWidth(150);
		table.getColumnModel().getColumn(1).setMaxWidth(400);
		table.getColumnModel().getColumn(2).setMaxWidth(100);
		table.getColumnModel().getColumn(3).setMaxWidth(120);
		table.getColumnModel().getColumn(4).setMaxWidth(70);
		table.getColumnModel().getColumn(5).setMaxWidth(70);

		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);

		table.getColumn("번호").setCellRenderer(celAlignCenter);
		table.getColumn("제목").setCellRenderer(celAlignCenter);
		table.getColumn("저자").setCellRenderer(celAlignCenter);
		table.getColumn("출판사").setCellRenderer(celAlignCenter);
		table.getColumn("장르").setCellRenderer(celAlignCenter);
		table.getColumn("대여상태").setCellRenderer(celAlignCenter);
				
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
		
		//System.out.println("Start page:" + start_page);
		//System.out.println("last page:" + last_page);
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
			String selectedOption =selectCombo.getSelectedItem().toString();
			String txt = selectTextF.getText();  
			bookTable(d.bookCtrl.select(selectedOption, txt), 1);
		}

		if(e.getSource()==prevPageBtn){
			bookTable(this.list, page-1);
		}else if(e.getSource()==nextPageBtn){
			bookTable(this.list, page+1);
		}
		for (int i = 0; i < pageBtn.length; i++) {
			if(page==i+1) continue;
			if(e.getSource()==pageBtn[i]){
				bookTable(this.list, i+1);
			}
		}
	}

	@Override public void mousePressed(MouseEvent e) {
		Delegate d = Delegate.getInstance();
		int rowNum = table.getSelectedRow();
		if(rowNum==-1) return;
		String isbn = (String) rowData[rowNum][0];
		d.bookCtrl.showDetail(isbn);
		if(!d.memCtrl.getLoginId().equals("admin") && !d.memCtrl.getLoginId().equals("")){
			view.dispose();
		}
	}
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}

}
  