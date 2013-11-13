package serverThreads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import server.model.UpdateQueues;

public class XmlDataThread implements Runnable {
	//ServerSocket Socket ;
    Socket connectionSocket;
    BufferedReader buffReader;
    String receivedMsg;
    volatile boolean stop=false;
    String parsedTemperature, alarm,parsedTag;
    
    public XmlDataThread(){
    	try {
			//Socket = new ServerSocket(5003);
    		connectionSocket= new Socket("10.0.0.53", 5004);
    		
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
	@Override
	public void run() {
		try {
			//connectionSocket= Socket.accept();
			
			buffReader= new BufferedReader (new InputStreamReader(connectionSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(!stop){
			// receive msg
			try {
				
				receivedMsg=buffReader.readLine();
				if(receivedMsg == null){
					stop=true;
					break;
				}
				//System.out.println("received msg: "+receivedMsg);
				
				
			} catch (IOException e) {
				System.err.println("Error receiving msg!");
				e.printStackTrace();
			}
			// parse the xml
			String delim="[<>]";
			String[] parsedFragments=receivedMsg.split(delim);
			/*for(String i:parsedFragments){
				System.out.println(i);
			}*/
			
			//Strore parsed values
			if(parsedFragments[1].equalsIgnoreCase("temp")){
				parsedTemperature=parsedFragments[2];
				try {
					UpdateQueues.getInstance().getTempQ().put(parsedTemperature);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Current Temperature is: "+parsedTemperature + "C");
			}
			
			if(parsedFragments[1].equalsIgnoreCase("alarm")){
				if(parsedFragments[2].equalsIgnoreCase("true"))
					alarm="ON";
				else
					alarm="OFF";
				try {
					UpdateQueues.getInstance().getNoiseAlarmQ().put(alarm);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Voice alarm is: "+ alarm);
				
			}
			
		}

	}

}
