package serve;

import java.net.DatagramPacket;


import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Server {
	
	public static void main(String[] args) throws Exception {
		
		int[] playerCountArray = new int[10];
		
		ArrayList<Player> players = new ArrayList<Player>();
		
		ArrayList<ServerBlock> blocks = new ArrayList<ServerBlock>();
		blocks.add(new ServerBlock(150, 350, 100, 100));
		blocks.add(new ServerBlock(550, 350, 100, 100));
		blocks.add(new ServerBlock(350, 150, 100, 100));
		blocks.add(new ServerBlock(350, 550, 100, 100));
		
		DatagramSocket ds = new DatagramSocket(6000);
		
		byte[] b1 = new byte[26];
		
		String[] ips = new String[10];
		
		int count = 0;
		
		//int playerCount = 0;
		
		for (int i = 0; i < 10; i++) {
			players.add(new Player(i, blocks));
		}
		
		int[][] shootQueue = new int[10][10];
		boolean[][] hitQueue = new boolean[10][10];
		//int[] hp = new int[10];
		
		
		while (true) {
			
			//System.out.println("Waiting..");
			
			DatagramPacket dp = new DatagramPacket(b1, b1.length);
			ds.receive(dp);
			
			
			
			String str = new String(dp.getData(), 0, dp.getLength());
//			System.out.println("Received: " + str);
//			int num = Integer.parseInt(str.trim());
//			count += num;
//			System.out.println("Count: " + count);
			
//			System.out.println("received: " + str);
			
			if (str.substring(0, 3).equals("NEW")) {
				
				int j = -1;
				
				for (int i = 0; i < 10; i++) {
					if (playerCountArray[i] == 0) {
						j = i;
						break;
					}
				}
				
				if (j == -1) {
					
					byte[] b2 = (-1 + "").getBytes();
					//InetAddress ia = InetAddress.getLocalHost();
					InetAddress ia = InetAddress.getByName(ips[j]);
					DatagramPacket dp1 = new DatagramPacket(b2,b2.length,ia,dp.getPort());
					ds.send(dp1);
					continue;
					
				}
				
				playerCountArray[j] = 1;
				
				
				
//				players.add(new Player(playerCount));
				
				for (int i = 0; i < 10; i++) {
					shootQueue[i][j] = 0;
					hitQueue[i][j] = false;
				}
				
				ips[j] = str.substring(3);
				
				
				
				
				byte[] b2 = (j + "").getBytes();
				//InetAddress ia = InetAddress.getLocalHost();
				InetAddress ia = InetAddress.getByName(ips[j]);
				DatagramPacket dp1 = new DatagramPacket(b2,b2.length,ia,dp.getPort());
				ds.send(dp1);
				
				//playerCount++;
				
				continue;
			} 
			
			
			int p = Integer.parseInt(str.substring(0, 1));
			
			players.get(p).move(str.substring(1));
			
			
			if (str.charAt(5) == '1') {
				for (int i = 0; i < 10; i++) {
					shootQueue[p][i] += 1;
				}
				
				
			}

			if (str.charAt(6) == '1') {
				for (int i = 0; i < 10; i++) {
					hitQueue[p][i] = true;
				}
			}
			int hitBy = Integer.parseInt(str.substring(13, 14));
//			System.out.println(hitBy);
			
			
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
			
			
			if (players.get(p).hp <= 0) {
				byte[] b2 = ("-").getBytes();
				
				players.get(p).x = 900;
				players.get(p).y = 900;
				players.get(p).firing = false;
				players.get(p).hit = false;
				players.get(p).bx = 0;
				players.get(p).by = 0;
				players.get(p).hp = 9;
				
				players.get(hitBy).hp = 9;
				
				playerCountArray[p] = 0;
				
				
				
				//players.get(index)
				
				InetAddress ia = InetAddress.getByName(ips[p]);
				
				DatagramPacket dp1 = new DatagramPacket(b2,b2.length,ia,dp.getPort());
				ds.send(dp1);
			}
			
			
			String sendString = "";
			
			
			
			
			
			for (int i = 0; i < 10; i++) {
				sendString = sendString.concat(String.format("%03d", players.get(i).x));
				sendString = sendString.concat(String.format("%03d", players.get(i).y));
				
				
				if (shootQueue[i][p] > 0) {
					sendString = sendString.concat("1");
					shootQueue[i][p]--;
				} else {
					sendString = sendString.concat("0");
				}
				
				
				if (hitQueue[i][p]) {
					sendString = sendString.concat("1");
					hitQueue[i][p] = false;
				} else {
					sendString = sendString.concat("0");
				}
				
				sendString = sendString.concat(String.format("%03d", players.get(i).bx));
				sendString = sendString.concat(String.format("%03d", players.get(i).by));
				
				sendString = sendString.concat(Integer.toString(players.get(i).hp));
				
//				if (players.get(i).firing) {
//					sendString = sendString.concat("1");
//				} else {
//					sendString = sendString.concat("0");
//				}
				
			}
			
			
			
			
			byte[] b2 = (sendString).getBytes();
			//InetAddress ia = InetAddress.getLocalHost();
			
			InetAddress ia = InetAddress.getByName(ips[p]);
			//System.out.println(ia);
			//System.out.println(ips[playerCount]);
			DatagramPacket dp1 = new DatagramPacket(b2,b2.length,ia,dp.getPort());
			ds.send(dp1);
			
			
			
//			byte[] b2 = (count + "").getBytes();
//			InetAddress ia = InetAddress.getLocalHost();
//			DatagramPacket dp1 = new DatagramPacket(b2,b2.length,ia,dp.getPort());
//			ds.send(dp1);
			
		}
		
		
	}

}
