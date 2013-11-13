package server.model;

import server.view.ThermometerBarChart;
import serverThreads.UDPvideoThread;
import serverThreads.XmlDataThread;

public class ServerMain {

	Thread xmlDataThread, udpVideoThread;

	public void init() {

		xmlDataThread = new Thread(new XmlDataThread());
		udpVideoThread = new Thread(new UDPvideoThread());

		xmlDataThread.start();
		udpVideoThread.start();
	}

	public Thread getXmlDataThread() {
		return xmlDataThread;
	}

	public Thread getUdpVideoThread() {
		return udpVideoThread;
	}

	public static void main(String[] args) {
		
		ThermometerBarChart.getInstance();
		ServerMain server = new ServerMain();
		server.init();

		try {
			server.getUdpVideoThread().join();
			server.getXmlDataThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
