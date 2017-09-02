package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dto.MemberDto;
import single.Delegate;

import javax.swing.JButton;
import javax.swing.JTable;

public class AdminMembershipView extends JFrame implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private JTextField selectTextF;
	private JTable table;
	private JScrollPane jScrPane;
	
	private JButton addBtn;
	private JButton updateBtn;
	private JButton cancleBtn;
	private JButton selectBtn;
	
	private JComboBox<String> choiceCombo;
	
	private String id;
	
	Delegate d = Delegate.getInstance();
	
	private String[] columnNames = {
			"아이디", "이름", "핸드폰" , "대여수"
	};

	DefaultTableModel model;
	private Object[][] rowData;
//	private List<MemberDto> list;
	
	public AdminMembershipView() {
		setTitle("회원관리");
		getContentPane().setLayout(null);
		
		choiceCombo = new JComboBox<String>();
		choiceCombo.setBounds(12, 20, 95, 34);
		getContentPane().add(choiceCombo);
		choiceCombo.addItem("아이디");
		choiceCombo.addItem("이름");
		choiceCombo.addItem("핸드폰");
		
		selectTextF = new JTextField();
		selectTextF.setBounds(130, 20, 277, 34);
		getContentPane().add(selectTextF);
		selectTextF.setColumns(10);
		
		selectBtn = new JButton("검   색");
		selectBtn.setBounds(419, 20, 103, 34);
		getContentPane().add(selectBtn);
		selectBtn.addActionListener(this); 
		
		addBtn = new JButton("회원등록");
		addBtn.setBounds(12, 563, 119, 41);
		getContentPane().add(addBtn);
		addBtn.addActionListener(this);
		
		updateBtn = new JButton("회원수정");
		updateBtn.setBounds(206, 563, 119, 41);
		getContentPane().add(updateBtn);
		updateBtn.addActionListener(this);
		
		cancleBtn = new JButton("취    소");		
		cancleBtn.setBounds(403, 563, 119, 41);
		getContentPane().add(cancleBtn);
		cancleBtn.addActionListener(this);
		
		
		
		table = new JTable();
		table.addMouseListener(this);
		
		jScrPane = new JScrollPane(table);		
		jScrPane.setBounds(12, 68, 510, 460);
		getContentPane().add(jScrPane);	
		
		createTable(d.memCtrl.getList());		
		
		
		setSize(550, 665);
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
	
	public void createTable(List<MemberDto> list){
		rowData = new Object[list.size()][4];
		int count = 0;
		
		for (int i = 0; i < list.size(); i++) {			
			rowData[count][0] = list.get(i).getId();
			rowData[count][1] = list.get(i).getName();
			rowData[count][2] = list.get(i).getPhone();
			rowData[count][3] = list.get(i).getRentcount();	
			count++;
		}
		
		model = new DefaultTableModel(columnNames, 0);
		model.setDataVector(rowData, columnNames);			
		model.fireTableDataChanged();
		table.setModel(model);		

		table.getColumnModel().getColumn(0).setMaxWidth(200);
		table.getColumnModel().getColumn(1).setMaxWidth(200);
		table.getColumnModel().getColumn(2).setMaxWidth(200);
		table.getColumnModel().getColumn(3).setMaxWidth(200);

		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);

		table.getColumn("아이디").setCellRenderer(celAlignCenter);
		table.getColumn("이름").setCellRenderer(celAlignCenter);
		table.getColumn("핸드폰").setCellRenderer(celAlignCenter);
		table.getColumn("대여수").setCellRenderer(celAlignCenter);
				
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addBtn){	//회원등록
			d.memCtrl.admin_regi();			
		}else if(e.getSource() == updateBtn){	//회원수정			
			d.memCtrl.getMember(id);
			this.dispose();					
		}else if(e.getSource() == cancleBtn){	//취소
			d.bookCtrl.Admin();
			this.dispose();
		}else if(e.getSource() == selectBtn){ 	//검색
			String selectedOption = choiceCombo.getSelectedItem().toString();
			System.out.println("selectedOption : " + selectedOption);			
			createTable(d.memCtrl.selectMember(selectedOption, selectTextF.getText()));
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {		
		int rowNum = table.getSelectedRow();
		System.out.println(rowNum);
		if(rowNum==-1) return;
		this.id = (String) rowData[rowNum][0];
	}
	@Override public void mouseClicked(MouseEvent e) {}	
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
}
