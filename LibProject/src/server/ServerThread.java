package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ServerThread extends Thread{
	Socket socket;
	List<Socket> list;
	public ServerThread(Socket socket, List<Socket> list) {
		this.socket = socket;
		this.list = list;
	}

	@Override
	public void run() {
		BufferedReader bufferedReader = null;
		PrintWriter printWriter = null;
		try{
			bufferedReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			while(true){
				String str = bufferedReader.readLine();
				System.out.println("Client Msg : " + str);
				for (Socket sock : list) {
					if(this.socket == sock){
						continue;
					}
					printWriter = new PrintWriter(sock.getOutputStream());
					printWriter.println(str);
					printWriter.flush();
				}
				Thread.sleep(10);
			}
		}catch(IOException | InterruptedException e){
			System.out.println("Disconnect : " + socket.getPort());
			list.remove(socket);
			//printWriter.close();
			try   { bufferedReader.close();}
			catch (IOException e1) {e1.printStackTrace();}
		}
	}
}
