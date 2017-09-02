package chatting;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import single.Delegate;
import view.ChattingView;

public class ReadThread extends Thread {
	ChattingView cf;
	Socket sock;

	public ReadThread(ChattingView cf) {
		this.cf = cf;
		this.sock = cf.getSocket();
	}

	@Override
	public void run() {
		super.run();
		BufferedReader bufferedReader = null;
		try{
			while(true){
				if(Delegate.getInstance().memCtrl.getLoginId().equals("admin"))
					cf.setVisible(true);
				bufferedReader = new BufferedReader(
						new InputStreamReader(sock.getInputStream()));
				String msg = bufferedReader.readLine();
				// 전송된 문자열
				
				System.out.println("recv : " + msg);
				cf.appendMsgToTextArea(msg);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} 	
	}

}


