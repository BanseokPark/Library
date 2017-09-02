package view;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import single.Delegate;

import javax.swing.JTextArea;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

public class AdminBookAddView extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextField titleTextF;
	private JTextField authorTextF;
	private JTextField publisherTextF;
	private JTextField codeTextF;
	private JTextField locationTextF;

	private JScrollPane jScrPane;

	private JTextArea info;
	private JButton addBtn;
	private JButton cancleBtn;

	private JComboBox<String> typeCombo;

	Delegate d = Delegate.getInstance();


	private JFileChooser jfc;
	private JButton jbt_open;
	private JTextField uploadTextF;

	private String id = "";
	private String title = "";

	public AdminBookAddView() {
		setTitle("도서 추가");
		getContentPane().setLayout(null);

		JLabel iamgeLabel = new JLabel("이 미 지");
		iamgeLabel.setBounds(12, 11, 73, 29);
		getContentPane().add(iamgeLabel);

		JLabel titleLabel = new JLabel("도서 제목");
		titleLabel.setBounds(12, 60, 73, 29);
		getContentPane().add(titleLabel);

		JLabel authorLabel = new JLabel("저    자");
		authorLabel.setBounds(12, 110, 73, 30);
		getContentPane().add(authorLabel);

		JLabel publisherLabel = new JLabel("출판사");
		publisherLabel.setBounds(12, 160, 73, 30);
		getContentPane().add(publisherLabel);

		JLabel typeLabel = new JLabel("장    르");
		typeLabel.setBounds(12, 210, 73, 30);
		getContentPane().add(typeLabel);

		JLabel codeLabel = new JLabel("도서코드");
		codeLabel.setBounds(153, 210, 73, 30);
		getContentPane().add(codeLabel);

		JLabel locationLabel = new JLabel("도서위치");
		locationLabel.setBounds(12, 262, 73, 30);
		getContentPane().add(locationLabel);

		JLabel contentLabel = new JLabel("내    용");
		contentLabel.setBounds(12, 302, 73, 30);
		getContentPane().add(contentLabel);

		jfc = new JFileChooser("\\\\192.168.10.20\\공유\\LibraryProject_Image\\");
//		jfc = new JFileChooser("img\\");

		jbt_open = new JButton("열기");
		jbt_open.setBounds(353, 10, 100, 30);
		getContentPane().add(jbt_open);
		jbt_open.addActionListener(this);

		uploadTextF = new JTextField();
		uploadTextF.setBounds(77, 11, 245, 30);
		getContentPane().add(uploadTextF);

		jfc.setFileFilter(new FileNameExtensionFilter("이미지 파일",  "jpg", "gif", "png"));
		// 파일 필터
		jfc.setMultiSelectionEnabled(false);//다중 선택 불가

		titleTextF = new JTextField();
		titleTextF.setBounds(77, 60, 376, 30);
		getContentPane().add(titleTextF);
		titleTextF.setColumns(10);

		authorTextF = new JTextField();
		authorTextF.setColumns(10);
		authorTextF.setBounds(77, 110, 376, 30);
		getContentPane().add(authorTextF);

		publisherTextF = new JTextField();
		publisherTextF.setColumns(10);
		publisherTextF.setBounds(77, 160, 376, 30);
		getContentPane().add(publisherTextF);

		typeCombo = new JComboBox<String>();
		typeCombo.setBounds(77, 210, 64, 30);
		getContentPane().add(typeCombo);
		typeCombo.addItem("소설");
		typeCombo.addItem("시");
		typeCombo.addItem("요리");
		typeCombo.addItem("과학");
		typeCombo.addItem("교양");


		codeTextF = new JTextField();
		codeTextF.setColumns(10);
		codeTextF.setBounds(214, 211, 239, 30);
		getContentPane().add(codeTextF);

		locationTextF = new JTextField();
		locationTextF.setColumns(10);
		locationTextF.setBounds(77, 263, 376, 30);
		getContentPane().add(locationTextF);

		info = new JTextArea();
		jScrPane = new JScrollPane(info);
		jScrPane.setBounds(12, 342, 441, 153);
		getContentPane().add(jScrPane);

		addBtn = new JButton("도서 추가");
		addBtn.setBounds(12, 505, 132, 41);
		getContentPane().add(addBtn);
		addBtn.addActionListener(this);

		cancleBtn = new JButton("취    소");
		cancleBtn.setBounds(328, 505, 125, 41);
		getContentPane().add(cancleBtn);
		cancleBtn.addActionListener(this);

		setSize(483, 593);
		setLocationRelativeTo(null);
		setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				d.bookCtrl.Admin();
			}
		});
	}//construct

	public AdminBookAddView(String id, String title, String author, String publisher) {
		getContentPane().setLayout(null);

		this.id = id;
		this.title = title;

		JLabel iamgeLabel = new JLabel("이 미 지");
		iamgeLabel.setBounds(44, 30, 73, 30);
		getContentPane().add(iamgeLabel);

		JLabel titleLabel = new JLabel("도서 제목");
		titleLabel.setBounds(44, 80, 73, 30);
		getContentPane().add(titleLabel);

		JLabel authorLabel = new JLabel("저    자");
		authorLabel.setBounds(44, 130, 73, 30);
		getContentPane().add(authorLabel);

		JLabel publisherLabel = new JLabel("출판사");
		publisherLabel.setBounds(44, 180, 73, 30);
		getContentPane().add(publisherLabel);

		JLabel typeLabel = new JLabel("장    르");
		typeLabel.setBounds(44, 230, 73, 30);
		getContentPane().add(typeLabel);

		JLabel codeLabel = new JLabel("도서코드");
		codeLabel.setBounds(44, 280, 73, 30);
		getContentPane().add(codeLabel);

		JLabel locationLabel = new JLabel("도서위치");
		locationLabel.setBounds(44, 330, 73, 30);
		getContentPane().add(locationLabel);

		JLabel contentLabel = new JLabel("내    용");
		contentLabel.setBounds(44, 380, 73, 30);
		getContentPane().add(contentLabel);

		jfc = new JFileChooser("img/");

		jbt_open = new JButton("열기");
		jbt_open.setBounds(385, 30, 100, 36);
		getContentPane().add(jbt_open);
		jbt_open.addActionListener(this);

		uploadTextF = new JTextField();
		uploadTextF.setBounds(166, 30, 188, 36);
		getContentPane().add(uploadTextF);

		jfc.setFileFilter(new FileNameExtensionFilter("이미지 파일",  "jpg", "gif", "png"));
		// 파일 필터
		jfc.setMultiSelectionEnabled(false);//다중 선택 불가


		titleTextF = new JTextField();
		titleTextF.setBounds(166, 80, 319, 30);
		getContentPane().add(titleTextF);
		titleTextF.setColumns(10);
		titleTextF.setText(title);

		authorTextF = new JTextField();
		authorTextF.setColumns(10);
		authorTextF.setBounds(166, 130, 319, 30);
		getContentPane().add(authorTextF);
		authorTextF.setText(author);

		publisherTextF = new JTextField();
		publisherTextF.setColumns(10);
		publisherTextF.setBounds(166, 180, 319, 30);
		getContentPane().add(publisherTextF);
		publisherTextF.setText(publisher);

		typeCombo = new JComboBox<String>();
		typeCombo.setBounds(166, 230, 319, 30);
		getContentPane().add(typeCombo);
		typeCombo.addItem("소설");
		typeCombo.addItem("SF");
		typeCombo.addItem("자기계발");
		typeCombo.addItem("인문학");

		codeTextF = new JTextField();
		codeTextF.setColumns(10);
		codeTextF.setBounds(166, 280, 319, 30);
		getContentPane().add(codeTextF);

		locationTextF = new JTextField();
		locationTextF.setColumns(10);
		locationTextF.setBounds(166, 330, 319, 30);
		getContentPane().add(locationTextF);



		info = new JTextArea();
		jScrPane = new JScrollPane(info);
		jScrPane.setBounds(44, 415, 441, 153);
		getContentPane().add(jScrPane);

		addBtn = new JButton("도서 추가");
		addBtn.setBounds(44, 605, 161, 41);
		getContentPane().add(addBtn);
		addBtn.addActionListener(this);

		cancleBtn = new JButton("취    소");
		cancleBtn.setBounds(324, 605, 161, 41);
		getContentPane().add(cancleBtn);
		cancleBtn.addActionListener(this);

		setSize(550, 711);
		setLocationRelativeTo(null);
		setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				new AdminView();
			}
		});
	}//construct

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addBtn){
			Image image = null;
			String img_src = "notFound.jpg";
			try {
				image = ImageIO.read(new File(uploadTextF.getText()));
				image = image.getScaledInstance(500, 750, Image.SCALE_SMOOTH);
				BufferedImage buffImg = new BufferedImage(500, 750, BufferedImage.TYPE_INT_BGR);
				buffImg.createGraphics().drawImage(image, 0, 0, this);
				img_src = codeTextF.getText() + ".jpg";
				//ImageIO.write(buffImg, "jpg", new File("\\\\192.168.10.20\\공유\\LibraryProject_Image\\" + img_src));
        ImageIO.write(buffImg, "jpg", new File(img_src));
			} catch (IOException e1) {e1.printStackTrace();}

			String type = typeCombo.getSelectedItem().toString();
			d.bookCtrl.BookAddAf(
					img_src,
					locationTextF.getText(),
					titleTextF.getText(),
					authorTextF.getText(),
					publisherTextF.getText(),
					codeTextF.getText(),
					type,
					info.getText());
			this.dispose();
			d.bookRequestCtrl.RequestBuy(id, title);
		}else if(e.getSource() == cancleBtn){
			this.dispose();
			d.bookCtrl.Admin();
		}else  if(e.getSource() == jbt_open){
			if(jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
				// showopendialog 열기 창을 열고 확인 버튼을 눌렀는지 확인
				uploadTextF.setText(/*"열기 경로 : " +*/ jfc.getSelectedFile().toString());
				System.out.println(jfc.getSelectedFile().toString());
			}
		}
	}//action
}
