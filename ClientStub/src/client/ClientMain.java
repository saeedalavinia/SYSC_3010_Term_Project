package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMain {
	public PrintWriter p;
	public Socket socket;
	
	
	public void clientStart() throws InterruptedException{
		
		try {
			socket= new Socket("LocalHost",5000);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.sendmsgs();
		
		p.close();
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public void sendmsgs() throws InterruptedException{
		try {
			p=new PrintWriter(socket.getOutputStream(),true);
			p.println("<temp>3</temp>");
			Thread.sleep(2000);
			p.println("<voiceData>OFF</VoiceData> ");
			p.println("<temp>12</temp>");
			Thread.sleep(2000);
			p.println("<temp>18</temp>");
			Thread.sleep(2000);
			p.println("<temp>35</temp>");
			Thread.sleep(2000);
			p.println("<voiceData>ON</VoiceData> ");
			p.println("<voiceData>OFF</VoiceData> ");
			p.println("<temp>28</temp>");
			Thread.sleep(2000);
			p.println("<temp>22</temp>");
			Thread.sleep(2000);
			p.println("<temp>20</temp>");
			Thread.sleep(2000);
			p.println("<temp>-7</temp>");
			Thread.sleep(2000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	} 
	
	public static void main(String[] args) throws InterruptedException{
		
		ClientMain cm= new ClientMain();
		cm.clientStart();
		
		
		
		
	}
}
