package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import chatting.ReadThread;
import chatting.WriteClass;

import javax.swing.JTextArea;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class ChattingView extends JFrame implements ActionListener{
	WriteClass wc;
	Socket socket;
	private JTextField textField;
	private JTextArea textArea;
	private JButton btnSend;
	private JButton btnQuit;
	public String id;

	public ChattingView(Socket socket, String id) {
		setTitle("채팅 [접속 아이디 : " + id +"]");
		this.socket = socket;
		this.id = id;
		new ReadThread(this).start();
		wc = new WriteClass(this);
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 432, 202);
		add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		textField = new JTextField();
		textField.setBounds(10, 214, 227, 27);
		textField.addActionListener(this);
		add(textField);
		textField.setColumns(10);

		btnSend = new JButton("Send");
		btnSend.setBounds(251, 214, 80, 27);
		btnSend.addActionListener(this);
		add(btnSend);

		btnQuit = new JButton("Quit");
		btnQuit.setBounds(344, 214, 74, 27);
		btnQuit.addActionListener(this);
		add(btnQuit);
		
		setSize(450,300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		System.out.println("id: " + id);
		setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnSend || e.getSource()==textField){
			appendMsgToTextArea("[나] : " + textField.getText()+ "");
			wc.sendMsg(id);
		}else if(e.getSource()==btnQuit){
			this.dispose();
		}
	}

	public Socket getSocket() {return socket;}
	public void setSocket(Socket socket) {this.socket = socket;}

	public String getTextFieldMsg() {return textField.getText();}
	public void setTextFieldMsg(String str) {this.textField.setText(str);}
	public void appendMsgToTextArea(String str) {this.textArea.append(str+"\n");}

}
