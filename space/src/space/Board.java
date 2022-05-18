package space;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    private final int DELAY = 100;
    private Timer timer;
    //private SpaceShip spaceShip;
    private int player;
    private InetAddress ia;
    
    //private SpaceShip otherShip;
    private SpaceShip[] shipList;
    
    public Board() throws Exception {
    	
    	int playerNum;
		
		DatagramSocket ds = new DatagramSocket();
		
//		int i = -4;
//		byte[] b = String.valueOf(i).getBytes();
		byte[] b = "NEW".getBytes();
		this.ia= InetAddress.getLocalHost();
		DatagramPacket dp = new DatagramPacket(b,b.length, ia, 6000);
		System.out.println("Sending..");
		ds.send(dp);
		
		byte[] b1 = new byte[1024];
		DatagramPacket dp1 = new DatagramPacket(b1, b1.length);
		ds.receive(dp1);
		
		String str = new String(dp1.getData());
		
		playerNum = Integer.parseInt(str.trim());
		
		System.out.println("PlayerNum is: "+playerNum);
		
		if (playerNum == -1) {
			return;
		}
    	
    	this.player = playerNum;
    	
        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);
        
        shipList = new SpaceShip[10];

        //spaceShip = new SpaceShip(ICRAFT_X, ICRAFT_Y);
        //otherShip = new SpaceShip(-100, -100);
        
        for (int i = 0; i < 10; i++) {
        	shipList[i] = new SpaceShip(900, 900);
        }

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        
        List<Missile> missiles = new ArrayList<Missile>();
        
        
        for (int i = 0; i < 10; i++) {
        	
        	g2d.fillOval(shipList[i].getX(), shipList[i].getY(), 40, 40);

            missiles.addAll(shipList[i].getMissiles());
        	
        }
        
//        g2d.fillOval(spaceShip.getX(), spaceShip.getY(), 40, 40);
//
//        List<Missile> missiles = spaceShip.getMissiles();
//        
//        missiles.addAll(otherShip.getMissiles());
//        
//        g2d.fillOval(otherShip.getX(), otherShip.getY(), 40, 40);

        for (Missile missile : missiles) {
            
            g2d.fillOval(missile.getX(), missile.getY(), 10, 10);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        updateMissiles();
        try {
			updateSpaceShip();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        repaint();
    }
    
    private List<Missile> getAllMissiles() {
    	
    	
    	
    	List<Missile> missiles = new ArrayList<Missile>();
        
        
        for (int i = 0; i < 10; i++) {
        	
        	

            missiles.addAll(shipList[i].getMissiles());
            
            
        	
        }
        
        System.out.println(missiles.size());
    	
    	return missiles;
    }

    private void updateMissiles() {

        List<Missile> missiles = this.getAllMissiles();

        for (int i = 0; i < missiles.size(); i++) {

            Missile missile = missiles.get(i);

            if (missile.isVisible()) {

                missile.move();
            } else {

            	
                missiles.remove(i);
            }
        }
    }

    private void updateSpaceShip() throws Exception {
    	
    	for (int i = 0; i < 10; i++) {
    		for (Missile m : shipList[i].getMissiles()) {
        		if (!m.isVisible()) {
        			shipList[i].removeMissile(m);
        		}
        	}
    	}
    	
    	
    	DatagramSocket ds = new DatagramSocket();
    	
    	String s = this.player + shipList[this.player].getMoveString();
    	
    	if (shipList[this.player].getFire()) {
    		
    		
    		
    		shipList[this.player].setFire(false);
    		
    	} 
    	
    	byte[] b1 = new byte[80];
		DatagramPacket dp1 = new DatagramPacket(b1, b1.length);
    	
		byte[] fb = s.getBytes();
		DatagramPacket dp = new DatagramPacket(fb,fb.length, this.ia, 6000);
		//DatagramSocket ds;
		long startTime = System.nanoTime();
		ds.send(dp);
		
		
		
		
		
		
		ds.receive(dp1);
		long endTime = System.nanoTime();
		
		System.out.println((endTime - startTime)/1000000);
		
		String position = new String(dp1.getData());
		
		//System.out.println(position);
		
		
		
//		int x = Integer.parseInt(position.substring(0, 3));
//		int y = Integer.parseInt(position.substring(3, 6).trim());
		
		for (int i = 0; i < 10; i++) {
			int x = Integer.parseInt(position.substring(i*7, i*7+3));
			int y = Integer.parseInt(position.substring(i*7+3, i*7+6).trim());
			int f = Integer.parseInt(position.substring(i*7+6, i*7+7).trim());
			
			if (f == 1) {
				shipList[i].fire();
			}
			
			shipList[i].setLocation(x, y);
			
		}
		
		
		
//		for (int i = 0; i < position.length()/6 - 1; i++) {
//			System.out.println(position.length()/6 - 1);
//			
//			int nx = Integer.parseInt(position.substring(i*6, i*6+3));
//			int ny = Integer.parseInt(position.substring(i*6+3, i*6+6).trim());
//			
//			otherShip.setLocation(nx, ny);
//			
//		}
		
//		if (position.charAt(6) == '1') {
//			
//			System.out.println("here");
//			int nx = Integer.parseInt(position.substring(7, 10));
//			int ny = Integer.parseInt(position.substring(10, 13).trim());
//			
//			otherShip.setLocation(nx, ny);
//		}
    	
		//spaceShip.setLocation(x, y);

        //spaceShip.move();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            shipList[player].keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
        	shipList[player].keyPressed(e);
        }
    }
}
