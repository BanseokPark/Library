package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import dto.BookDto;
import dto.BookReviewDto;
import single.Delegate;

import javax.swing.JTextArea;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;;

public class ReviewUpdateView extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;


	Delegate d = Delegate.getInstance();
	private BookReviewDto rdto;
	private BookDto dto;
	private JTextField titleTextF;
	private JTextArea contentTextA;
	private JButton updateReviewBtn;
	private JButton deleteReviewBtn;
	private JComboBox<String> rateCombo;
	private JButton exitBtn;

	public ReviewUpdateView(BookDto dto, BookReviewDto rdto) {
		setTitle("리뷰 수정");
		this.dto = dto;
		this.rdto = rdto;
		getContentPane().setLayout(null);

		JPanel bookInfoPanel = new JPanel();
		bookInfoPanel.setBorder(new TitledBorder(null, "책 정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		bookInfoPanel.setLayout(null);
		bookInfoPanel.setBounds(14, 12, 504, 133);
		getContentPane().add(bookInfoPanel);

		int img_width = 70;
		int img_height = img_width*3/2;
		JPanel imgPanel = new JPanel(){
			private static final long serialVersionUID = 1L;
			@Override
			protected void paintComponent(Graphics g) {
				Image image = null;
				try {
					image = ImageIO.read(new File("img\\"+dto.getImg()));
				}
				catch (IOException | NullPointerException e) {
					try {
						image = ImageIO.read(new File("img\\notFound.jpg"));
					} catch (IOException e1) {}
				}
				image = image.getScaledInstance(img_width, img_height, Image.SCALE_SMOOTH);
				g.drawImage(image, 0, 0, this);
			}
		};

		imgPanel.setBorder(null);
		imgPanel.setBounds(12, 18, img_width, img_height);
		bookInfoPanel.add(imgPanel);

		JLabel bootTitleLabel = new JLabel("책 제목");
		bootTitleLabel.setBounds(96, 27, 62, 18);
		bookInfoPanel.add(bootTitleLabel);
		bootTitleLabel.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel authorLabel = new JLabel("저자");
		authorLabel.setBounds(96, 60, 38, 15);
		bookInfoPanel.add(authorLabel);
		authorLabel.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel pubisherLabel = new JLabel("출판사");
		pubisherLabel.setBounds(292, 60, 57, 15);
		bookInfoPanel.add(pubisherLabel);
		pubisherLabel.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel isbnLabel = new JLabel("도서번호");
		isbnLabel.setBounds(292, 87, 57, 15);
		bookInfoPanel.add(isbnLabel);
		isbnLabel.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel typeLabel = new JLabel("장르");
		typeLabel.setBounds(96, 87, 38, 15);
		bookInfoPanel.add(typeLabel);
		typeLabel.setHorizontalAlignment(SwingConstants.LEFT);

		JTextField bookTitleTextF = new JTextField(dto.getTitle());
		bookTitleTextF.setBounds(160, 24, 330, 24);
		bookInfoPanel.add(bookTitleTextF);
		bookTitleTextF.setBorder(null);
		bookTitleTextF.setEditable(false);
		bookTitleTextF.setColumns(10);

		JTextField authorTextF = new JTextField(dto.getAuthor());
		authorTextF.setBounds(139, 57, 116, 21);
		bookInfoPanel.add(authorTextF);
		authorTextF.setEditable(false);
		authorTextF.setColumns(10);
		authorTextF.setBorder(null);

		JTextField publisherTextF = new JTextField(dto.getPublisher());
		publisherTextF.setBounds(355, 57, 116, 21);
		bookInfoPanel.add(publisherTextF);
		publisherTextF.setEditable(false);
		publisherTextF.setColumns(10);
		publisherTextF.setBorder(null);

		JTextField typeTextF = new JTextField(dto.getType());
		typeTextF.setBounds(139, 84, 116, 21);
		bookInfoPanel.add(typeTextF);
		typeTextF.setEditable(false);
		typeTextF.setColumns(10);
		typeTextF.setBorder(null);

		JTextField isbnTextF = new JTextField(dto.getIsbn());
		isbnTextF.setBounds(355, 84, 116, 21);
		bookInfoPanel.add(isbnTextF);
		isbnTextF.setEditable(false);
		isbnTextF.setColumns(10);
		isbnTextF.setBorder(null);

		JPanel addReviewPanel = new JPanel();
		addReviewPanel.setBorder(new TitledBorder(null, "리뷰", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		addReviewPanel.setLayout(null);
		addReviewPanel.setBounds(14, 157, 504, 270);
		getContentPane().add(addReviewPanel);

		JLabel label = new JLabel("제목");
		label.setBounds(14, 27, 44, 24);
		addReviewPanel.add(label);

		JLabel label_1 = new JLabel("내용");
		label_1.setBounds(14, 55, 44, 24);
		addReviewPanel.add(label_1);

		titleTextF = new JTextField(rdto.getTitle());
		titleTextF.setBounds(53, 27, 262, 24);
		addReviewPanel.add(titleTextF);
		titleTextF.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(14, 83, 476, 164);
		addReviewPanel.add(scrollPane);

		contentTextA = new JTextArea(rdto.getContent());
		contentTextA.setBackground(Color.WHITE);
		scrollPane.setViewportView(contentTextA);

		JLabel rateLabel = new JLabel("평점");
		rateLabel.setBounds(329, 27, 44, 24);
		addReviewPanel.add(rateLabel);

		rateCombo = new JComboBox<String>();
		rateCombo.setBounds(369, 27, 121, 24);

		for (int i = 0; i < 5; i++){
			String rate = "";
			for (int j = 0; j <= i; j++)
				rate+="★";
			rateCombo.addItem(rate);
		}

		addReviewPanel.add(rateCombo);

		if(d.memCtrl.getLoginId().equals(rdto.getId())){
			updateReviewBtn = new JButton("리뷰 수정");
			updateReviewBtn.setBounds(134, 437, 105, 35);
			updateReviewBtn.addActionListener(this);
			getContentPane().add(updateReviewBtn);
		}
		if(d.memCtrl.getLoginId().equals(rdto.getId())
				|| d.memCtrl.getLoginId().equals("admin")){
			deleteReviewBtn = new JButton("리뷰 삭제");
			deleteReviewBtn.setBounds(251, 437, 105, 35);
			deleteReviewBtn.addActionListener(this);
			getContentPane().add(deleteReviewBtn);

			exitBtn = new JButton("닫기");
			exitBtn.setBounds(413, 437, 105, 35);
			exitBtn.addActionListener(this);
			getContentPane().add(exitBtn);
		}

		setSize(550, 521);
		setLocationRelativeTo(null);
		setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				new ReviewDetailView(dto, rdto);
				dispose();
			}
		});
	}//constructor

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==updateReviewBtn){
			System.out.println(rateCombo.getSelectedItem().toString());
			boolean b = d.bookReviewCtrl.reviewUpdate(this.rdto.getIndex(), titleTextF.getText(), contentTextA.getText(),rateCombo.getSelectedItem().toString().length());
			if(b){
				JOptionPane.showMessageDialog(null, "리뷰 수정 성공");
			}else{
				JOptionPane.showMessageDialog(null, "리뷰 수정 실패");
			}
			this.dispose();
			d.bookReviewCtrl.showReviewDetail(dto, rdto.getIndex());
		}else if(e.getSource()==deleteReviewBtn){
			int option = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?");
			switch(option){
				case 0:
					boolean b = d.bookReviewCtrl.reviewDelete(rdto.getIndex());
					if(b) {
						JOptionPane.showMessageDialog(null, "삭제 성공");
						this.dispose();
						new BookDetailView(dto);
					}
					else	JOptionPane.showMessageDialog(null, "삭제 실패");
					break;
				default:
					JOptionPane.showMessageDialog(null, "삭제를 취소했습니다.");
			}
		}else if(e.getSource()==exitBtn){
			new ReviewDetailView(dto, rdto);
			dispose();
		}

	}//action
}
