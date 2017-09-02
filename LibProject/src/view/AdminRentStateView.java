package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dto.RentDto;
import single.Delegate;

public class AdminRentStateView extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JTextField selectTextF;
	private JTable table;
	private JScrollPane jScrPane;
	private JComboBox<String> choiceCombo;
	private JButton selectBtn;
	private JButton prevBtn;
	Delegate d = Delegate.getInstance();
	
	private String[] columnNames = {"아이디", "이름", "책제목" , "대여일", "반납일"};
	
	DefaultTableModel model;
	private Object[][] rowData;	
	public AdminRentStateView() {
		getContentPane().setLayout(null);
		setTitle("도서 대여 현황");
		
		choiceCombo = new JComboBox<String>();
		choiceCombo.setBounds(12, 20, 95, 34);
		getContentPane().add(choiceCombo);
		choiceCombo.addItem("아이디");
		choiceCombo.addItem("이름");
		choiceCombo.addItem("제목");
		
		selectTextF = new JTextField();
		selectTextF.setBounds(130, 20, 340, 34);
		getContentPane().add(selectTextF);
		selectTextF.setColumns(10);
		
		selectBtn = new JButton("검   색");
		selectBtn.setBounds(503, 20, 119, 34);
		getContentPane().add(selectBtn);
		selectBtn.addActionListener(this);

		table = new JTable();
		
		jScrPane = new JScrollPane(table);		
		jScrPane.setBounds(12, 68, 610, 460);
		getContentPane().add(jScrPane);	
		
		prevBtn = new JButton("취    소");		
		prevBtn.setBounds(477, 560, 145, 41);
		getContentPane().add(prevBtn);
		prevBtn.addActionListener(this);
		
		createTable(d.rentCtrl.getList());
		
		setSize(650, 665);
		setLocationRelativeTo(null);		
		setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {			
				d.bookCtrl.Admin();
				dispose();		
			}			
		});	
	}
	
	public void createTable(List<RentDto> list){
		rowData = new Object[list.size()][5];
		for (int i = 0; i < list.size(); i++) {
			rowData[i][0] = list.get(i).getId();
			rowData[i][1] = list.get(i).getName();
			rowData[i][2] = list.get(i).getTitle();
			rowData[i][3] = list.get(i).getRentdate();			
			rowData[i][4] = list.get(i).getReturndate();
		}	
		
		model = new DefaultTableModel(columnNames, 0);
		model.setDataVector(rowData, columnNames);
		table.setModel(model);		
		
		table.getColumnModel().getColumn(0).setMaxWidth(200);
		table.getColumnModel().getColumn(1).setMaxWidth(200);
		table.getColumnModel().getColumn(2).setMaxWidth(200);
		table.getColumnModel().getColumn(3).setMaxWidth(200);
		table.getColumnModel().getColumn(4).setMaxWidth(200);

		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);

		table.getColumn("아이디").setCellRenderer(celAlignCenter);
		table.getColumn("이름").setCellRenderer(celAlignCenter);
		table.getColumn("책제목").setCellRenderer(celAlignCenter);
		table.getColumn("대여일").setCellRenderer(celAlignCenter);
		table.getColumn("반납일").setCellRenderer(celAlignCenter);

		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==selectBtn){	// 검색
			String selectedOption = choiceCombo.getSelectedItem().toString();
			System.out.println("selectedOption : " + selectedOption);			
			createTable(d.rentCtrl.selectList(selectedOption, selectTextF.getText()));
			
		}else if(e.getSource()==prevBtn){  	// 이전으로 돌아가
			d.bookCtrl.Admin();
			this.dispose();
		}
	}
}
