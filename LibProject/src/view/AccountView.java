package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import single.Delegate;
import java.awt.Color;

public class AccountView extends JFrame implements ActionListener, FocusListener, KeyListener{
	private static final long serialVersionUID = 1L;
	private JTextField idTextF;
	private JTextField passTextF;
	private JTextField nameTextF;
	private JTextField phoneTextF;	
	private JTextField passCheckTextF;
	
	private JButton cancelBtn;
	private JButton accountBtn;
	private JButton idBtn;
	private JLabel pwSameLabel;
	
	private boolean id_checked = false;
	private boolean pw_checked = false;
	
	public AccountView() {
		super("회원가입");
		getContentPane().setLayout(null);
		
		JLabel idLabel = new JLabel("ID:");
		idLabel.setBounds(12, 11, 67, 20);
		getContentPane().add(idLabel);
		
		idTextF = new JTextField();
		idTextF.setBounds(91, 7, 150, 30);
		idTextF.addKeyListener(this);
		getContentPane().add(idTextF);
		idTextF.setColumns(10);
		
		idBtn = new JButton("중복 확인");
		idBtn.addActionListener(this);
		idBtn.setBounds(255, 6, 103, 30);
		getContentPane().add(idBtn);
		
		JLabel passLabel = new JLabel("PW:");
		passLabel.setBounds(12, 56, 67, 20);
		getContentPane().add(passLabel);
		
		passTextF = new JTextField();
		passTextF.setBounds(91, 52, 150, 30);
		passTextF.addKeyListener(this);
		passTextF.addFocusListener(this);
		getContentPane().add(passTextF);
		
		JLabel nameLabel = new JLabel("이름:");
		nameLabel.setBounds(12, 136, 67, 20);
		getContentPane().add(nameLabel);
		
		nameTextF = new JTextField();
		nameTextF.setBounds(91, 132, 150, 30);
		nameTextF.addKeyListener(this);
		getContentPane().add(nameTextF);
		
		JLabel phoneLabel = new JLabel("폰 번호:");
		phoneLabel.setBounds(12, 176, 67, 20);
		getContentPane().add(phoneLabel);
		
		phoneTextF = new JTextField();
		phoneTextF.setBounds(91, 172, 150, 30);
		phoneTextF.addKeyListener(this);
		getContentPane().add(phoneTextF);
		
		accountBtn = new JButton("회원가입");
		accountBtn.addActionListener(this);
		accountBtn.setBounds(91, 212, 89, 30);
		getContentPane().add(accountBtn);		
		
		JLabel pwCheckLabel = new JLabel("PW 확인:");
		pwCheckLabel.setBounds(12, 96, 67, 20);
		getContentPane().add(pwCheckLabel);
		
		passCheckTextF = new JTextField();
		passCheckTextF.setBounds(91, 92, 150, 30);
		passCheckTextF.addKeyListener(this);
		passCheckTextF.addFocusListener(this);
		getContentPane().add(passCheckTextF);
		
		pwSameLabel = new JLabel("");
		pwSameLabel.setBounds(245, 86, 90, 47);
		getContentPane().add(pwSameLabel);
		
		cancelBtn = new JButton("취소");
		cancelBtn.addActionListener(this);
		cancelBtn.setBounds(184, 212, 67, 30);
		getContentPane().add(cancelBtn);
		setSize(384,315);
		setLocationRelativeTo(null);		
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Delegate d = Delegate.getInstance();
		if(e.getSource()==idBtn){
			boolean b = d.memCtrl.IdCheck(idTextF.getText());
			if(b){
				JOptionPane.showMessageDialog(null, "사용중인 ID입니다.");
				id_checked = false;
				idTextF.setText("");
			}else{
				JOptionPane.showMessageDialog(null, "사용 가능한 ID입니다.");
				id_checked = true;
				passTextF.grabFocus();
			}
		}
		else if(e.getSource()==accountBtn){
			if(idTextF.getText().equals("") || passTextF.getText().equals("")
					||nameTextF.getText().equals("")|| phoneTextF.getText().equals("")){
				JOptionPane.showMessageDialog(null, "모두 기입해 주십시오");
				return;
			}		
			if(!id_checked){
				JOptionPane.showMessageDialog(null, "id 중복 확인을 해주세요.");
				return;
			}
			if(!pw_checked){
				JOptionPane.showMessageDialog(null, "동일한 pw를 입력해주세요");
				return;
			}
			
			boolean b = d.memCtrl.regiAf(idTextF.getText(),
										   passTextF.getText(),
										   nameTextF.getText(),
										   phoneTextF.getText());
			if(b){
				JOptionPane.showMessageDialog(null, "회원가입 성공");
			}else{
				JOptionPane.showMessageDialog(null, "회원가입 실패");
			}	
			this.dispose();
		}else if(e.getSource()==cancelBtn){
			this.dispose();
		}
	}
	@Override public void focusGained(FocusEvent e) {}
	@Override public void focusLost(FocusEvent e) {
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
			pwSameLabel.setText("<html>동일한 PW를<br />입력해주세요.</html>");
		}
	}
	
	
	@Override public void keyReleased(KeyEvent e) {
		JTextField textF = (JTextField) e.getSource();
		if(textF.equals(idTextF)) id_checked = false; // id필드의 값이 변경되면 id_checked -> false;
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
				textF.setText(textF.getText().trim());
		}
	}
	@Override	public void keyPressed(KeyEvent e) {}
	@Override	public void keyTyped(KeyEvent e) {	}
}
