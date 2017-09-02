package chatting;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import view.ChattingView;

public class WriteClass {
	ChattingView cf;
	Socket sock;

	public WriteClass(ChattingView cf) {
		this.cf = cf;
		this.sock = cf.getSocket();
	}

	public void sendMsg(String id){

		try{
				// 보낼 메시지	Write(file)		
				PrintWriter writer = new PrintWriter(sock.getOutputStream());			
				writer.println("[" + id + "]: " + cf.getTextFieldMsg()); // send(전송)
				cf.setTextFieldMsg("");
				writer.flush();	// 잊지말고 꼭!
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}
