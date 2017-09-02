package controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import view.ChattingView;

public class ChattingController {
	public void Chatting(String id) {		
		//IP, Port
		InetSocketAddress sockAddr = new InetSocketAddress("192.168.10.20", 8000);

		// Client Socket 생성
		Socket socket = new Socket();
		try {
			// Server 접속		
			socket.connect(sockAddr, 10000);

			InetAddress inetAddr;
			if((inetAddr = socket.getInetAddress()) != null){
				System.out.println("연결:" + inetAddr);
			}else{
				System.out.println("연결실패");
			}
			new ChattingView(socket, id);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
