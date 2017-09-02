package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import dto.BookDto;
import dto.BookReviewDto;
import single.Delegate;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;;

public class ReviewAddView extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;


	private Delegate d = Delegate.getInstance();
	private BookDto dto;
	private JTextField titleTextF;
	private JButton addReviewBtn;
	private JTextArea contentTextA;
	private JComboBox<String> rateCombo;

	public ReviewAddView(BookDto dto) {
		setTitle("리뷰 작성");
		this.dto = dto;
		getContentPane().setLayout(null);

		JPanel bookInfoPanel = new JPanel();
		bookInfoPanel.setBorder(new TitledBorder(null, "책 정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		bookInfoPanel.setLayout(null);
		bookInfoPanel.setBounds(14, 12, 504, 129);
		getContentPane().add(bookInfoPanel);

		JLabel bootTitleLabel = new JLabel("책 제목");
		bootTitleLabel.setBounds(14, 28, 62, 18);
		bookInfoPanel.add(bootTitleLabel);
		bootTitleLabel.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel authorLabel = new JLabel("저자");
		authorLabel.setBounds(14, 61, 38, 15);
		bookInfoPanel.add(authorLabel);
		authorLabel.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel pubisherLabel = new JLabel("출판사");
		pubisherLabel.setBounds(210, 61, 57, 15);
		bookInfoPanel.add(pubisherLabel);
		pubisherLabel.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel isbnLabel = new JLabel("도서번호");
		isbnLabel.setBounds(210, 88, 57, 15);
		bookInfoPanel.add(isbnLabel);
		isbnLabel.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel typeLabel = new JLabel("장르");
		typeLabel.setBounds(14, 88, 38, 15);
		bookInfoPanel.add(typeLabel);
		typeLabel.setHorizontalAlignment(SwingConstants.LEFT);

		JTextField bookTitleTextF = new JTextField(dto.getTitle());
		bookTitleTextF.setBounds(78, 25, 387, 24);
		bookInfoPanel.add(bookTitleTextF);
		bookTitleTextF.setBorder(null);
		bookTitleTextF.setEditable(false);
		bookTitleTextF.setColumns(10);

		JTextField authorTextF = new JTextField(dto.getAuthor());
		authorTextF.setBounds(57, 58, 116, 21);
		bookInfoPanel.add(authorTextF);
		authorTextF.setEditable(false);
		authorTextF.setColumns(10);
		authorTextF.setBorder(null);

		JTextField publisherTextF = new JTextField(dto.getPublisher());
		publisherTextF.setBounds(273, 58, 116, 21);
		bookInfoPanel.add(publisherTextF);
		publisherTextF.setEditable(false);
		publisherTextF.setColumns(10);
		publisherTextF.setBorder(null);

		JTextField typeTextF = new JTextField(dto.getType());
		typeTextF.setBounds(57, 85, 116, 21);
		bookInfoPanel.add(typeTextF);
		typeTextF.setEditable(false);
		typeTextF.setColumns(10);
		typeTextF.setBorder(null);

		JTextField isbnTextF = new JTextField(dto.getIsbn());
		isbnTextF.setBounds(273, 85, 116, 21);
		bookInfoPanel.add(isbnTextF);
		isbnTextF.setEditable(false);
		isbnTextF.setColumns(10);
		isbnTextF.setBorder(null);

		JPanel addReviewPanel = new JPanel();
		addReviewPanel.setBorder(new TitledBorder(null, "리뷰 작성", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		addReviewPanel.setLayout(null);
		addReviewPanel.setBounds(14, 153, 504, 460);
		getContentPane().add(addReviewPanel);

		JLabel label = new JLabel("제목");
		label.setBounds(14, 27, 62, 24);
		addReviewPanel.add(label);

		JLabel label_1 = new JLabel("내용");
		label_1.setBounds(14, 55, 62, 24);
		addReviewPanel.add(label_1);

		titleTextF = new JTextField();
		titleTextF.setBounds(53, 27, 285, 24);
		addReviewPanel.add(titleTextF);
		titleTextF.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(14, 83, 476, 356);
		addReviewPanel.add(scrollPane);

		contentTextA = new JTextArea();
		scrollPane.setViewportView(contentTextA);

		JLabel rateLabel = new JLabel("평점");
		rateLabel.setBounds(352, 27, 36, 24);
		addReviewPanel.add(rateLabel);

		rateCombo = new JComboBox<String>();

		rateCombo.setBounds(385, 27, 105, 24);
		addReviewPanel.add(rateCombo);

		addReviewBtn = new JButton("작성");
		addReviewBtn.setBounds(413, 625, 105, 24);
		getContentPane().add(addReviewBtn);
		addReviewBtn.addActionListener(this);
		for(int i=0; i<5; i++){
			String item_name = "";
			for(int j=0; j<=i; j++) item_name+="★";
			rateCombo.addItem(item_name);
		}


		setSize(550, 711);
		setLocationRelativeTo(null);		
		setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {				
				dispose();
				new BookDetailView(dto);
			}			
		});	
	}//constructor

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==addReviewBtn){
			BookReviewDto rdto = new BookReviewDto(
					0, dto.getIsbn(), null, d.memCtrl.getLoginId(),
					null, titleTextF.getText(), contentTextA.getText(), null,
					rateCombo.getSelectedItem().toString().length());
			boolean b =	d.bookReviewCtrl.reviewAddAf(rdto);
			if(b){	
				JOptionPane.showMessageDialog(null, "리뷰 작성 성공");
			}else{
				JOptionPane.showMessageDialog(null, "리뷰 작성 실패");
			}

			this.dispose();
			new BookDetailView(dto);
		}
	}//action
}
















