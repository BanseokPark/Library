package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dto.MemberDto;
import single.Delegate;

public class AdminMemberUpdateView extends JFrame implements ActionListener, KeyListener, FocusListener{
	private static final long serialVersionUID = 1L;
	private JTextField idTextF;
	private JTextField passTextF;
	private JTextField nameTextF;
	private JTextField phoneTextF;	
	private JTextField passCheckTextF;
	Delegate d = Delegate.getInstance();
	private JButton updateBtn;
	
	private boolean pw_checked = false;
	private JLabel pwSameLabel;
	private JButton cancleBtn;
	
	public AdminMemberUpdateView(MemberDto dto) {
		super("회원 정보 수정");
		getContentPane().setLayout(null);
		
		JLabel idLabel = new JLabel("ID:");
		idLabel.setBounds(12, 10, 67, 30);
		getContentPane().add(idLabel);
		
		idTextF = new JTextField();
		idTextF.setBounds(91, 11, 150, 30);		
		getContentPane().add(idTextF);
		idTextF.setColumns(10);	
		if(dto == null){
			JOptionPane.showMessageDialog(null, "dto는 null!");
		}
		
		idTextF.setText(dto.getId());
		idTextF.setEditable(false);	
		
		JLabel passLabel = new JLabel("PassWord:");
		passLabel.setBounds(12, 50, 67, 30);
		getContentPane().add(passLabel);
		
		passTextF = new JTextField();
		passTextF.setBounds(91, 51, 150, 30);
		passTextF.addKeyListener(this);
		passTextF.addFocusListener(this);
		getContentPane().add(passTextF);
		
		JLabel nameLabel = new JLabel("이름:");
		nameLabel.setBounds(12, 160, 67, 30);
		getContentPane().add(nameLabel);
		
		nameTextF = new JTextField();
		nameTextF.setBounds(91, 161, 150, 30);
		nameTextF.addKeyListener(this);
		nameTextF.setText(dto.getName());
		getContentPane().add(nameTextF);
		
		JLabel phoneLabel = new JLabel("폰 번호:");
		phoneLabel.setBounds(12, 200, 67, 30);
		getContentPane().add(phoneLabel);
		
		phoneTextF = new JTextField();
		phoneTextF.setBounds(91, 201, 150, 30);
		phoneTextF.addKeyListener(this);
		phoneTextF.setText(dto.getPhone());
		getContentPane().add(phoneTextF);
		
		updateBtn = new JButton("수정");
		updateBtn.addActionListener(this);
		updateBtn.setBounds(12, 240, 85, 31);
		getContentPane().add(updateBtn);		
		
		JLabel pwCheckLabel = new JLabel("PW 확인:");
		pwCheckLabel.setBounds(12, 90, 67, 30);
		getContentPane().add(pwCheckLabel);
		
		passCheckTextF = new JTextField();
		passCheckTextF.setBounds(91, 91, 150, 30);
		passCheckTextF.addKeyListener(this);
		passCheckTextF.addFocusListener(this);
		getContentPane().add(passCheckTextF);
		
		pwSameLabel = new JLabel("");
		pwSameLabel.setBounds(91, 131, 150, 20);
		getContentPane().add(pwSameLabel);
		
		cancleBtn = new JButton("취소");
		cancleBtn.setBounds(143, 241, 98, 31);
		getContentPane().add(cancleBtn);
		setSize(272, 318);
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == updateBtn){
			d.memCtrl.updateMember(idTextF.getText(), passTextF.getText(), nameTextF.getText(), phoneTextF.getText());
			this.dispose();
			
		}else if(e.getSource() == cancleBtn){
			d.bookCtrl.Admin();
			this.dispose();
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void focusGained(FocusEvent e) {}

	@Override
	public void focusLost(FocusEvent e) {
		if(e.getSource()==passTextF){
			if(passCheckTextF.getText().equals("")) return;			
		}
		if(e.getSource()==passCheckTextF){
			if(passTextF.getText().equals("")) return;
		}
		pw_checked = passTextF.getText().equals(passCheckTextF.getText()) ? true : false;
		if(pw_checked){
			pwSameLabel.setForeground(Color.BLUE);
			pwSameLabel.setText("PW 확인");
		}else{
			pwSameLabel.setForeground(Color.RED);
			pwSameLabel.setText("동일한 PW를 입력해주세요.");
		}		
	}

}
