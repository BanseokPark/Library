package view;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import dto.BookDto;
import dto.BookReviewDto;
import dto.BookReviewRepleDto;
import single.Delegate;

import javax.swing.JTextArea;
import javax.imageio.ImageIO;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.SystemColor;;

public class ReviewDetailView extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	Delegate d = Delegate.getInstance();

	BookDetailView bookDetailView;
	BookReviewDto rdto;
	BookDto dto;
	private JTextField titleTextF;
	private JTextArea contentTextA;
	private JTextField rateTextF;

	private JButton updateReviewBtn;
	private JButton deleteReviewBtn;
	private JTextField idTextF;
	private JTextField nameTextF;
	private JPanel replePanel;
	private JPanel reviewPanel;

	private JTextField repleTextF;
	private JButton addRepleBtn;
	private JButton exitBtn;

	public ReviewDetailView(BookDto dto, BookReviewDto rdto) {
		setTitle("리뷰");
		this.dto = dto;
		this.rdto = rdto;
		getContentPane().setLayout(null);

		JPanel bookInfoPanel = new JPanel();
		bookInfoPanel.setBorder(new TitledBorder(null, "책 정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		bookInfoPanel.setLayout(null);
		bookInfoPanel.setBounds(14, 12, 694, 133);
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

		reviewPanel = new JPanel();
		reviewPanel.setBorder(new TitledBorder(null, "리뷰", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		reviewPanel.setLayout(null);
		reviewPanel.setBounds(14, 157, 694, 596);
		getContentPane().add(reviewPanel);

		JLabel idLabel = new JLabel("아이디");
		idLabel.setBounds(14, 23, 44, 24);
		reviewPanel.add(idLabel);

		idTextF = new JTextField(rdto.getId());
		idTextF.setBounds(72, 23, 166, 24);
		reviewPanel.add(idTextF);
		idTextF.setEditable(false);
		idTextF.setColumns(10);
		idTextF.setBorder(null);

		JLabel nameLabel = new JLabel("이름");
		nameLabel.setBounds(261, 23, 44, 24);
		reviewPanel.add(nameLabel);

		nameTextF = new JTextField(rdto.getName());
		nameTextF.setEditable(false);
		nameTextF.setColumns(10);
		nameTextF.setBorder(null);
		nameTextF.setBounds(308, 23, 134, 24);
		reviewPanel.add(nameTextF);

		JLabel contentLabel = new JLabel("내용");
		contentLabel.setBounds(14, 75, 44, 24);
		reviewPanel.add(contentLabel);

		JLabel titleLabel = new JLabel("제목");
		titleLabel.setBounds(14, 48, 44, 24);
		reviewPanel.add(titleLabel);

		titleTextF = new JTextField(rdto.getTitle().length()>20?rdto.getTitle().substring(0, 20)+"...":rdto.getTitle());
		titleTextF.setBorder(null);
		titleTextF.setEditable(false);
		titleTextF.setBounds(72, 48, 186, 24);
		reviewPanel.add(titleTextF);
		titleTextF.setColumns(10);

		JLabel rateLabel = new JLabel("평점");
		rateLabel.setBounds(261, 48, 44, 24);
		reviewPanel.add(rateLabel);

		String rate = "";
		for (int i = 0; i < rdto.getRate(); i++) rate+="★";
		rateTextF = new JTextField(rate);
		rateTextF.setEditable(false);
		rateTextF.setColumns(10);
		rateTextF.setBorder(null);
		rateTextF.setBounds(308, 50, 116, 21);
		reviewPanel.add(rateTextF);

		contentTextA = new JTextArea(rdto.getContent());
		contentTextA.setEditable(false);
		contentTextA.setBackground(SystemColor.menu);

		JScrollPane scrollPane = new JScrollPane(contentTextA);
		scrollPane.setBorder(null);
		scrollPane.setBounds(14, 104, 666, 164);
		reviewPanel.add(scrollPane);

		replePanel = new replePanel(this, rdto);
		replePanel.setBounds(14, 280, 666, 276);
		reviewPanel.add(replePanel);

		repleTextF = new JTextField();
		repleTextF.setBounds(24, 560, 539, 24);
		repleTextF.addActionListener(this);
		reviewPanel.add(repleTextF);
		repleTextF.setColumns(10);

		addRepleBtn = new JButton("추가");
		addRepleBtn.setBounds(577, 559, 89, 27);
		reviewPanel.add(addRepleBtn);
		addRepleBtn.addActionListener(this);

		if(d.memCtrl.getLoginId().equals(rdto.getId())){
			updateReviewBtn = new JButton("리뷰 수정");
			updateReviewBtn.setBounds(296, 765, 105, 24);
			updateReviewBtn.addActionListener(this);
			getContentPane().add(updateReviewBtn);
		}
		if(d.memCtrl.getLoginId().equals(rdto.getId())
				|| d.memCtrl.getLoginId().equals("admin")){
			deleteReviewBtn = new JButton("리뷰 삭제");
			deleteReviewBtn.setBounds(413, 765, 105, 24);
			deleteReviewBtn.addActionListener(this);
			getContentPane().add(deleteReviewBtn);
		}
			exitBtn = new JButton("닫기");
			exitBtn.setBounds(603, 766, 105, 24);
			exitBtn.addActionListener(this);
			getContentPane().add(exitBtn);

		setSize(740, 846);
		setLocationRelativeTo(null);
		setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				new BookDetailView(dto);
				dispose();
			}
		});
	}//constructor

	public void setReplePanel(){
		reviewPanel.remove(replePanel);
		replePanel.removeAll();
		this.replePanel = new replePanel(this, rdto);
		replePanel.setBounds(14, 280, 666, 276);
		reviewPanel.add(replePanel);
	}


	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()==addRepleBtn || e.getSource()==repleTextF){
			if(d.memCtrl.getLoginId().equals("")){
				JOptionPane.showMessageDialog(null, "로그인후 댓글을 달수 있습니다.");
			}
			else{
				if(repleTextF.getText().equals("")){
					JOptionPane.showMessageDialog(null, "내용을 입력하세요");
				}else{
					d.bookReviewCtrl.ReviewRepleAdd(d.memCtrl.getLoginId(), repleTextF.getText(), rdto.getIndex(), 0, 0, -1);
					setReplePanel();
				}
			}
		}
		if(e.getSource()==updateReviewBtn){
			new ReviewUpdateView(dto, rdto);
			this.dispose();
		}else if(e.getSource()==deleteReviewBtn){
			int option = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?");
			switch(option){
				case 0:
					boolean b = d.bookReviewCtrl.reviewDelete(rdto.getIndex());
					if(b){
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
			new BookDetailView(dto);
			this.dispose();
		}
	}//action
}

class replePanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JTable repleTable;
	private String[] columnNames = {"번호", "내용", "아이디", "작성일", "+"};
	private Object[][] rowData;
	private DefaultTableModel model;

	JScrollPane repleScrollPane;

	Delegate d = Delegate.getInstance();
	ReviewDetailView reviewDetailView;
	BookReviewDto rdto;

	public replePanel(ReviewDetailView reviewDetailView, BookReviewDto rdto) {
		this.reviewDetailView = reviewDetailView;
		this.rdto = rdto;

		setLayout(null);
		JLabel repleLabel = new JLabel("댓글");
		repleLabel.setBounds(14, 0, 44, 24);
		add(repleLabel);

		repleTable = new JTable();
		makeRepleTable(d.bookReviewCtrl.getReviewRepleList(rdto.getIndex()));
		CellRenderer renderer = new CellRenderer(reviewDetailView, repleTable, rdto, model, rowData);
		repleTable.getColumnModel().getColumn(4).setCellEditor(renderer);

	}

	public void makeRepleTable(List<BookReviewRepleDto> list){
		rowData = new Object[list.size()][9];
		for (int i = 0; i < rowData.length; i++) {
			rowData[i][0] = i+1;
			String depth = "";
			for (int j = 0; j < list.get(i).getDepth(); j++)
				depth += "      ";
			if(list.get(i).getDepth()!=0) depth+="[re]:";
			rowData[i][1] = depth + list.get(i).getContent();
			rowData[i][2] = list.get(i).getId();
			rowData[i][3] = list.get(i).getWritedate();
			rowData[i][4] = list.get(i).getDepth()<2
					? "<html><p style=\"color:blue\"><u>추가</u></p></html>" : "";
			rowData[i][5] = list.get(i).getReviewindex();
			rowData[i][6] = list.get(i).getGroup();
			rowData[i][7] = list.get(i).getStep();
			rowData[i][8] = list.get(i).getDepth();
		}

		model = new DefaultTableModel(columnNames, 0);
		model.setDataVector(rowData, columnNames);
		repleTable.setModel(model);

		repleScrollPane = new JScrollPane(repleTable);
		repleScrollPane.setBounds(14, 28, 640, 238);
		add(repleScrollPane);

		repleTable.getColumnModel().getColumn(0).setMaxWidth(40);
		repleTable.getColumnModel().getColumn(1).setMaxWidth(450);
		repleTable.getColumnModel().getColumn(2).setMaxWidth(80);
		repleTable.getColumnModel().getColumn(3).setMaxWidth(130);
		repleTable.getColumnModel().getColumn(4).setMaxWidth(50);

		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);

		repleTable.getColumn("번호").setCellRenderer(celAlignCenter);
		repleTable.getColumn("아이디").setCellRenderer(celAlignCenter);
		repleTable.getColumn("작성일").setCellRenderer(celAlignCenter);

		repleScrollPane.getVerticalScrollBar().setValue(rowData.length*20);
		repleScrollPane.getVerticalScrollBar().setValue(repleScrollPane.getVerticalScrollBar().getMaximum());
		repleTable.updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
}


class CellRenderer extends AbstractCellEditor implements TableCellRenderer,TableCellEditor {
	private static final long serialVersionUID = 1L;
	Object[][] rowData;
	JTable table;
	public CellRenderer(ReviewDetailView reviewDetailView,JTable table, BookReviewDto rdto, final DefaultTableModel model, Object[][] rowData) {
		this.rowData = rowData;
		this.table = table;
		System.out.println(table.getSelectedRow());
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Delegate d = Delegate.getInstance();
				Point point = e.getPoint();
				if (table.columnAtPoint(point) == 4) {
					fireEditingStopped();
					int row = table.getSelectedRow();
					if (!rowData[row][4].equals("")) {
						if(d.memCtrl.getLoginId().equals("")){
							JOptionPane.showMessageDialog(null, "로그인후 댓글을 달수 있습니다.");
						}else{
							String s = JOptionPane.showInputDialog("댓글을 입력하세요");
							d.bookReviewCtrl.ReviewRepleAdd (
									d.memCtrl.getLoginId(), s,
									(int)rowData[row][5], (int)rowData[row][6], (int)rowData[row][7], (int)rowData[row][8]);
							reviewDetailView.setReplePanel();
						}
					}
				}
			}
		});
	}
	public Component getTableCellRendererComponent(JTable table, Object value,	boolean isSelected, boolean hasFocus, int row, int column) { return null;}
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,  int column) { return null;}
	public Object getCellEditorValue() {return rowData[table.getSelectedRow()][4];}
}
