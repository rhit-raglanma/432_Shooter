package serve;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Server {
	
	public static void main(String[] args) throws Exception {
		
		ArrayList<Player> players = new ArrayList<Player>();
		
		DatagramSocket ds = new DatagramSocket(6000);
		
		byte[] b1 = new byte[7];
		
		//int count = 0;
		
		int playerCount = 0;
		
		for (int i = 0; i < 10; i++) {
			players.add(new Player(i));
		}
		
		
		while (true) {
			
			//System.out.println("Waiting..");
			
			DatagramPacket dp = new DatagramPacket(b1, b1.length);
			ds.receive(dp);
			
			//System.out.println("received");
			
			String str = new String(dp.getData(), 0, dp.getLength());
			//System.out.println("Received: " + str);
			//int num = Integer.parseInt(str.trim());
			//count += num;
			//System.out.println("Count: " + count);
			
			if (str.equals("NEW")) {
				
				if (playerCount == 10) {
					
					byte[] b2 = (-1 + "").getBytes();
					InetAddress ia = InetAddress.getLocalHost();
					DatagramPacket dp1 = new DatagramPacket(b2,b2.length,ia,dp.getPort());
					ds.send(dp1);
					continue;
					
				}
				
//				players.add(new Player(playerCount));
				
				
				
				byte[] b2 = (playerCount + "").getBytes();
				InetAddress ia = InetAddress.getLocalHost();
				DatagramPacket dp1 = new DatagramPacket(b2,b2.length,ia,dp.getPort());
				ds.send(dp1);
				
				playerCount++;
				
				continue;
			} 
			
			
			int p = Integer.parseInt(str.substring(0, 1));
			
			players.get(p).move(str.substring(1));
			
			
			//X,Y
			
			
					
//			String formatX = String.format("%03d", players.get(0).x);
//			String formatY = String.format("%03d", players.get(0).y);
//			String sendString = formatX.concat(formatY);
//			if (playerCount == 2) {
//				String formatX2 = String.format("%03d", players.get(1).x);
//				String formatY2 = String.format("%03d", players.get(1).y);
//				sendString = sendString.concat("1");
//				sendString = sendString.concat(formatX2);
//				sendString = sendString.concat(formatY2);
//			}
			
			String sendString = "";
			for (int i = 0; i < 10; i++) {
				sendString = sendString.concat(String.format("%03d", players.get(i).x));
				sendString = sendString.concat(String.format("%03d", players.get(i).y));
				if (players.get(i).firing) {
					sendString = sendString.concat("1");
				} else {
					sendString = sendString.concat("0");
				}
				
			}
			
			
			
			
			byte[] b2 = (sendString).getBytes();
			InetAddress ia = InetAddress.getLocalHost();
			DatagramPacket dp1 = new DatagramPacket(b2,b2.length,ia,dp.getPort());
			ds.send(dp1);
			
			
			
//			byte[] b2 = (count + "").getBytes();
//			InetAddress ia = InetAddress.getLocalHost();
//			DatagramPacket dp1 = new DatagramPacket(b2,b2.length,ia,dp.getPort());
//			ds.send(dp1);
			
		}
		
		
	}

}