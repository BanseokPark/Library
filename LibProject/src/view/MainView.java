package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import single.Delegate;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dto.RentLogRankDto;

import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Graphics;
import java.awt.Image;

public class MainView extends JFrame implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;

	private JTextField idTextF;
	private JPasswordField pwTextF;

	private JButton log_btn;
	private JButton membership_btn;

	private String[] columnNames = { "순위", "제목", "대여횟수" };

	DefaultTableModel model;
	private Object[][] rowData;
	private JTable table;


	public MainView() {
		setTitle("도서 관리 프로그램");
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

		JPanel bookTablePanel = new BookTablePanel(this, 20);
		bookTablePanel.setBounds(10, 128, 886, 484);
		getContentPane().add(bookTablePanel);

		JPanel loginPanel = new JPanel();
		loginPanel.setBorder(new TitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"로그인", TitledBorder.CENTER, TitledBorder.TOP,
				null, new Color(0, 0, 0)));
		loginPanel.setBounds(908, 128, 265, 142);
		getContentPane().add(loginPanel);
		loginPanel.setLayout(null);

		JLabel idLabel = new JLabel("ID");
		idLabel.setHorizontalAlignment(SwingConstants.CENTER);
		idLabel.setBounds(12, 24, 38, 29);
		loginPanel.add(idLabel);

		JLabel pwLabel = new JLabel("PW");
		pwLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pwLabel.setBounds(12, 63, 38, 29);
		loginPanel.add(pwLabel);

		idTextF = new JTextField();
		idTextF.setBounds(49, 24, 202, 29);
		idTextF.addKeyListener(this);
		loginPanel.add(idTextF);
		idTextF.setColumns(10);

		pwTextF = new JPasswordField();
		pwTextF.setBounds(49, 63, 202, 29);
		pwTextF.addKeyListener(this);
		pwTextF.addActionListener(this);
		loginPanel.add(pwTextF);


		log_btn = new JButton("로그인");
		log_btn.setBounds(31, 102, 104, 29);
		log_btn.addActionListener(this);
		loginPanel.add(log_btn);

		membership_btn = new JButton("회원가입");
		membership_btn.setBounds(147, 102, 104, 29);
		membership_btn.addActionListener(this);
		loginPanel.add(membership_btn);

		JPanel rentLogRankPanel = new JPanel();
		rentLogRankPanel.setBorder(new TitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"대여 순위",
				TitledBorder.LEADING, TitledBorder.TOP,
				null, new Color(0, 0, 0)));
		rentLogRankPanel.setLayout(null);
		rentLogRankPanel.setBounds(912, 280, 262, 332);
		getContentPane().add(rentLogRankPanel);

		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setLocation(14, 32);
		scrollPane.setSize(234, 278);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		rentLogRankPanel.add(scrollPane);

		rentLogRankTable(Delegate.getInstance().rentLogCtrl.getRentRogRankList(), 16);

		setSize(1192, 654);
		setLocationRelativeTo(null);
		setVisible(true);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void rentLogRankTable(List<RentLogRankDto> list, int page_size){
		page_size = list.size()<page_size ? list.size() : page_size;
		rowData = new Object[page_size][4];
		for (int i = 0; i < rowData.length; i++) {
			rowData[i][0] = list.get(i).getRank();
			rowData[i][1] = list.get(i).getTitle();
			rowData[i][2] = list.get(i).getRent_count();
			rowData[i][3] = list.get(i).getIsbn();
		}

		model = new DefaultTableModel(columnNames, 0);
		model.setDataVector(rowData, columnNames);
		table.setModel(model);

		table.getColumnModel().getColumn(0).setMaxWidth(50);
		table.getColumnModel().getColumn(1).setMaxWidth(200);
		table.getColumnModel().getColumn(2).setMaxWidth(50);

		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);

		table.getColumn("순위").setCellRenderer(celAlignCenter);
		table.getColumn("제목").setCellRenderer(celAlignCenter);
		table.getColumn("대여횟수").setCellRenderer(celAlignCenter);

		table.addMouseListener(new MouseListener() {
			Delegate d = Delegate.getInstance();
			@Override public void mousePressed(MouseEvent e) {
				int row = table.getSelectedRow();
				d.bookCtrl.showDetail((String)rowData[row][3]);
			}
			@Override	public void mouseReleased(MouseEvent e) {	}
			@Override	public void mouseExited(MouseEvent e) {	}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseClicked(MouseEvent e) {}
		});
	}

	@Override public void actionPerformed(ActionEvent e) {
		Delegate d = Delegate.getInstance();

		if(e.getSource()==log_btn || e.getSource()==pwTextF){
			d.memCtrl.loginAf(this, idTextF.getText(), String.valueOf(pwTextF.getPassword()));
		}
		else if(e.getSource()==membership_btn){
			d.memCtrl.regi();
		}
		else if(e.getSource()==idTextF){
			pwTextF.grabFocus();
		}
	}
	
	@Override public void keyReleased(KeyEvent e) {
		JTextField textF = (JTextField) e.getSource();
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			textF.setText(textF.getText().trim());
		}
	}
	@Override public void keyPressed(KeyEvent e) {}
	@Override public void keyTyped(KeyEvent e) {}
}
