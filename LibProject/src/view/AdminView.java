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
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import single.Delegate;

public class AdminView extends JFrame implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;

	private JTable table;

	private JButton addBtn;
	private JButton retalBtn;
	private JButton memBtn;
	private JButton exitBtn;
	private JButton chatBtn;
	private JButton requestBtn;
	private JLabel idLab;

	Delegate d = Delegate.getInstance();

	DefaultTableModel model;
	private Object[][] rowData;

	public AdminView() {
		setTitle("도서 관리 프로그램");
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
		bookTablePanel.setBounds(10, 123, 886, 462);
		getContentPane().add(bookTablePanel);

		JPanel menuPanel = new JPanel();
		menuPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "메뉴", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		menuPanel.setBounds(901, 268, 265, 317);
		getContentPane().add(menuPanel);
		menuPanel.setLayout(null);

		JPanel panelLogin = new JPanel();
		panelLogin.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "로그인", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelLogin.setBounds(901, 123, 265, 142);
		getContentPane().add(panelLogin);
		panelLogin.setLayout(null);

		Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy / MM / dd");
        String sysdate = sdf.format(date);
		idLab = new JLabel(sysdate + ", '" + d.memCtrl.getLoginId() + "' 님  접속");
		idLab.setBounds(45, 24, 250, 15);
		panelLogin.add(idLab);

		JLabel bookRequestCountLabel = new JLabel("구매 신청 : " + d.bookRequestCtrl.getRequestBookCount() + "권");
		bookRequestCountLabel.setBounds(45, 50, 250, 15);
		panelLogin.add(bookRequestCountLabel);

		exitBtn = new JButton("종 료");
		exitBtn.setBounds(31, 95, 210, 30);
		panelLogin.add(exitBtn);
		exitBtn.addActionListener(this);

		addBtn = new JButton("도서 추가");
		addBtn.setBounds(12, 30, 110, 53);
		menuPanel.add(addBtn);
		addBtn.addActionListener(this);

		retalBtn = new JButton("대여 현황");
		retalBtn.setBounds(143, 30, 110, 53);
		menuPanel.add(retalBtn);
		retalBtn.addActionListener(this);

		memBtn = new JButton("회원 관리");
		memBtn.setBounds(12, 130, 110, 53);
		menuPanel.add(memBtn);
		memBtn.addActionListener(this);

		requestBtn = new JButton("요청 목록");
		requestBtn.setBounds(143, 130, 110, 53);
		menuPanel.add(requestBtn);
		requestBtn.addActionListener(this);


		chatBtn = new JButton("채팅");
		chatBtn.addActionListener(this);
		chatBtn.setBounds(12, 230, 241, 53);
		menuPanel.add(chatBtn);

		setSize(1192, 627);
		setLocationRelativeTo(null);
		setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				d.memCtrl.logout();
				d.memCtrl.start();
			}
		});
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addBtn){
			d.bookCtrl.BookAdd();
			this.dispose();
		}else if(e.getSource() == retalBtn){
			d.rentCtrl.rentState();
			this.dispose();
		}else if(e.getSource() == memBtn){
			d.memCtrl.Member();
			this.dispose();
		}else if(e.getSource() == requestBtn){
			d.bookRequestCtrl.getRequestListView();
			this.dispose();
		}else if(e.getSource() == exitBtn){
			d.memCtrl.start();
			this.dispose();
		}else if(e.getSource() == chatBtn){
			d.chatCtrl.Chatting(d.memCtrl.getLoginId());
		}

	}

	@Override public void mousePressed(MouseEvent e) {
		Delegate d = Delegate.getInstance();

		System.out.println("table size : " + table.getRowCount());

		int rowNum = table.getSelectedRow();
		System.out.println(rowNum);
		if(rowNum==-1) return;
		String bookcode = (String) rowData[rowNum][0];
		d.bookCtrl.showDetail(bookcode);
	}
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
}
