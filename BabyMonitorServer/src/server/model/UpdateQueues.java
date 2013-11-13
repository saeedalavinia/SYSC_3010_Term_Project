package server.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


// singleton class
public class UpdateQueues {
	private static UpdateQueues theInstance=null;
	private static BlockingQueue<String> tempQ= new LinkedBlockingQueue<String>();
	private static BlockingQueue<String> noiseAlarmQ= new LinkedBlockingQueue<String>();
	
	private UpdateQueues(){}
	
	public static synchronized UpdateQueues getInstance(){
		if(theInstance==null){
			theInstance= new UpdateQueues();
		}
		return theInstance;
		
	}
		
		
	
	
	
	public  BlockingQueue<String> getTempQ() {
		return tempQ;
	}
	public  BlockingQueue<String> getNoiseAlarmQ() {
		return noiseAlarmQ;
	}

}
