package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dto.BookRequestDto;
import single.Delegate;

public class AdminBookRequestListView extends JFrame implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private JButton cancleBtn;
	private JButton buyBtn;
	
	private String id = "";
	private String title = "";
	private String author = "";
	private String publisher = "";
	private String state = "";
	
	Delegate d = Delegate.getInstance();	
	private JTable table;
	private JScrollPane jScrPane;	
	private String[] columnNames = {"번호", "아이디", "제목", "저자" , "출판사", "신청일", "상태"};
	DefaultTableModel model;
	private Object[][] rowData;
	
	public AdminBookRequestListView() {
		getContentPane().setLayout(null);
		setTitle("도서 요청 목록");
		
		table = new JTable();
		table.addMouseListener(this);
		
		jScrPane = new JScrollPane(table);		
		jScrPane.setBounds(12, 10, 510, 460);
		getContentPane().add(jScrPane);	
		
		cancleBtn = new JButton("취    소");		
		cancleBtn.setBounds(403, 480, 119, 41);
		getContentPane().add(cancleBtn);
		cancleBtn.addActionListener(this);
		
		buyBtn = new JButton("구    매");
		buyBtn.setBounds(12, 480, 119, 41);
		getContentPane().add(buyBtn);
		buyBtn.addActionListener(this);		
		
		
		
		createTable(d.bookRequestCtrl.getRequestList());		
		
		
		setSize(550, 574);
		setLocationRelativeTo(null);		
		setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {				
				dispose();
				d.bookCtrl.Admin();		
			}			
		});	
	}
	
	public void createTable(List<BookRequestDto> list){
		System.out.println("list : " + list.size());
		rowData = new Object[list.size()][columnNames.length];		
		
		for (int i = 0; i < list.size(); i++) {		
			rowData[i][0] = (i+1);
			rowData[i][1] = list.get(i).getId();
			rowData[i][2] = list.get(i).getTitle();
			rowData[i][3] = list.get(i).getAuthor();
			rowData[i][4] = list.get(i).getPublisher();	
			rowData[i][5] = list.get(i).getDate();	
			rowData[i][6] = list.get(i).getState();	
			
		}
		
		model = new DefaultTableModel(columnNames, 0);
		model.setDataVector(rowData, columnNames);			
		model.fireTableDataChanged();
		table.setModel(model);		

		table.getColumnModel().getColumn(0).setMaxWidth(50);
		table.getColumnModel().getColumn(1).setMaxWidth(100);
		table.getColumnModel().getColumn(2).setMaxWidth(80);
		table.getColumnModel().getColumn(3).setMaxWidth(80);
		table.getColumnModel().getColumn(4).setMaxWidth(80);
		table.getColumnModel().getColumn(5).setMaxWidth(80);
		table.getColumnModel().getColumn(6).setMaxWidth(80);

		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);

		table.getColumn("번호").setCellRenderer(celAlignCenter);
		table.getColumn("아이디").setCellRenderer(celAlignCenter);
		table.getColumn("제목").setCellRenderer(celAlignCenter);
		table.getColumn("저자").setCellRenderer(celAlignCenter);
		table.getColumn("출판사").setCellRenderer(celAlignCenter);
		table.getColumn("신청일").setCellRenderer(celAlignCenter);
		table.getColumn("상태").setCellRenderer(celAlignCenter);
				
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cancleBtn){
			d.bookCtrl.Admin();
			this.dispose();
		}else if(e.getSource() == buyBtn){
			d.bookCtrl.BookAdd(id, title, author, publisher);
			this.dispose(); 			
		}
	}

	
	@Override public void mousePressed(MouseEvent e) {
		int rowNum = table.getSelectedRow();
		System.out.println(rowNum);
		if(rowNum==-1) return;
		this.id = (String) rowData[rowNum][1]; // seq
		this.title = (String) rowData[rowNum][2];
		this.author = (String) rowData[rowNum][3];
		this.publisher = (String) rowData[rowNum][4];	
		this.state = (String) rowData[rowNum][6];
		
		System.out.println("rownum[0] : " +  rowData[rowNum][0]);	
		
		if(state.equals("구매완료")){		
			System.out.println("state" +state);
			buyBtn.setEnabled(false);
		}else{
			System.out.println("state" +state);
			buyBtn.setEnabled(true);
		}
	}

	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}

}
