package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerMain {
	public static void main(String[] args) {
		int port = Integer.parseInt("8000");
		ServerSocket serverSocket = null;
		Socket socket = null;
		List<Socket> list = new ArrayList<Socket>();
		try{
			serverSocket = new ServerSocket(port);
			while(true){
				System.out.println("Waiting connection...");
				socket = serverSocket.accept();
				list.add(socket);
				System.out.println("연결IP:" + socket.getInetAddress()
									+ " Port:" + socket.getPort());
				new ServerThread(socket, list).start();
			}
		}catch(IOException e){e.printStackTrace();}
	}
}
