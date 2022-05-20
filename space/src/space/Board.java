package space;

import java.net.DatagramPacket;
import java.util.Scanner;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private boolean gameOver;
    
    //private SpaceShip otherShip;
    private SpaceShip[] shipList;
    
    private ArrayList<Missile> removeMissiles;
    
    private Block[] blockList;
    
    public Board() throws Exception {
    	
//    	String thisIPString = InetAddress.getLocalHost().getHostAddress();
//		System.out.println(thisIPString);
    	
    	this.gameOver = false;
    	
    	Scanner myScanner = new Scanner(System.in);
    	
    	System.out.println("Enter your ip: ");
    	String ipString = myScanner.nextLine();
    	
    	System.out.println("Enter host ip: ");
    	String hostIpString = myScanner.nextLine();
    	
    	this.removeMissiles = new ArrayList<>();
    	
    	int playerNum;
		
		DatagramSocket ds = new DatagramSocket();
		
		
		
		//RHIT-R90Y2R7N/192.168.56.1
		
//		int i = -4;
//		byte[] b = String.valueOf(i).getBytes();
		byte[] b = ("NEW" + ipString).getBytes();
		//this.ia= InetAddress.getByAddress("137.112.230.174".getBytes());
		//this.ia = InetAddress.getLocalHost();
		this.ia = InetAddress.getByName(hostIpString);
		//System.out.println(this.ia);
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
        addMouseListener(new BoardMouseListener());
        setBackground(Color.BLACK);
        setFocusable(true);
        
        shipList = new SpaceShip[10];
        blockList = new Block[4];

        //spaceShip = new SpaceShip(ICRAFT_X, ICRAFT_Y);
        //otherShip = new SpaceShip(-100, -100);
        blockList[0] = new Block(150, 350, 100, 100);
        blockList[1] = new Block(550, 350, 100, 100);
        blockList[2] = new Block(350, 150, 100, 100);
        blockList[3] = new Block(350, 550, 100, 100);
        
        
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
        
        if (this.gameOver) {
        	g2d.setColor(Color.red);
        	g2d.drawString("GAME OVER", 360, 390);
        	this.timer.stop();
        }
        
        
        
        List<Missile> missiles = new ArrayList<Missile>();
        
        
        for (int i = 0; i < 10; i++) {
        	
        	//g2d.setColor(Color.white);
        	if (shipList[i].getHit()) {
        		g2d.setColor(Color.white);
        	} else {
        		if (shipList[i].getHP() > 6 ) {
        			g2d.setColor(Color.green);
        		} else if (shipList[i].getHP() < 4) {
        			g2d.setColor(Color.red);
        		} else {
        			g2d.setColor(Color.yellow);
        		}
        	}
        	g2d.fillOval(shipList[i].getX(), shipList[i].getY(), 40, 40);
        	g2d.drawString(shipList[i].getHP() + "", shipList[i].getX(), shipList[i].getY());
        	
        	g2d.setColor(Color.white);

            missiles.addAll(shipList[i].getMissiles());
        	
        }
        
        for(Block b : blockList) {
        	g2d.setColor(Color.LIGHT_GRAY);
        	b.draw(g2d);
        	g2d.setColor(Color.white);
        }
        
//        g2d.fillOval(spaceShip.getX(), spaceShip.getY(), 40, 40);
//
//        List<Missile> missiles = spaceShip.getMissiles();
//        
//        missiles.addAll(otherShip.getMissiles());
//        
//        g2d.fillOval(otherShip.getX(), otherShip.getY(), 40, 40);

        for (Missile missile : missiles) {
        	
        	//Color hitColor = 
            
        	
            g2d.fillOval(missile.getX(), missile.getY(), 10, 10);
            
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	
    	for (int i = 0; i < 10; i++) {
    		if (shipList[i].getHit()) {
    			shipList[i].setHit(false);
    		}
    	}
    	

        updateMissiles();
        try {
			updateSpaceShip();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        

        repaint();
    }
    
    private void collissionCheck() {
    	
    	
    	for (int i = 0; i < 10; i++) {
    		
    		for (Missile m: shipList[i].getMissiles()) {
    			if (m.getHitbox().intersects(shipList[this.player].getHitbox())) {
        			shipList[this.player].setHit(true);
        			shipList[this.player].setHitBy(i);
        		}
    			for(int j = 0; j < 10; j++) {
    				if(m.getHitbox().intersects(shipList[j].getHitbox())) {
    					m.setVisible(false);
    				}
    			}
    			for(Block b : blockList) {
    				b.checkMissileCollision(m);
    			}
    		}
    		
//    		if (m.getHitbox().intersects(shipList[this.player].getHitbox())) {
//    			shipList[this.player].setHit(true);
//    			
//    		}
    	}
    }
    
    private List<Missile> getAllMissiles() {
    	
    	
    	
    	List<Missile> missiles = new ArrayList<Missile>();
        
        
        for (int i = 0; i < 10; i++) {
        	
        	

            missiles.addAll(shipList[i].getMissiles());
            
            
        	
        }
        
        //System.out.println(missiles.size());
    	
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
        
        collissionCheck();
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
    	
    	byte[] b1 = new byte[160];
		DatagramPacket dp1 = new DatagramPacket(b1, b1.length);
    	
		byte[] fb = s.getBytes();
		DatagramPacket dp = new DatagramPacket(fb,fb.length, this.ia, 6000);
		//DatagramSocket ds;
		//long startTime = System.nanoTime();
		ds.send(dp);
		
		
		
		
		
		
		ds.receive(dp1);
		//long endTime = System.nanoTime();
		
		//System.out.println((endTime - startTime)/1000000);
		
		
		
		
		
		String position = new String(dp1.getData());
		
		//System.out.println(position);
		
		if (position.charAt(0) == '-') {
			System.out.println("YOU DIED");
			//System.exit(0);
			//this.timer.stop();
			this.gameOver = true;
			return;
		}
		
//		int x = Integer.parseInt(position.substring(0, 3));
//		int y = Integer.parseInt(position.substring(3, 6).trim());
		
		for (int i = 0; i < 10; i++) {
			int x = Integer.parseInt(position.substring(i*15, i*15+3));
			int y = Integer.parseInt(position.substring(i*15+3, i*15+6));
			int f = Integer.parseInt(position.substring(i*15+6, i*15+7));
			int h = Integer.parseInt(position.substring(i*15+7, i*15+8));
			int bx = Integer.parseInt(position.substring(i*15+8, i*15+11));
			int by = Integer.parseInt(position.substring(i*15+11, i*15+14));
			int hp = Integer.parseInt(position.substring(i*15+14, i*15+15).trim());
			
			shipList[i].setDirection(bx, by);
			
			if (f == 1) {
				shipList[i].fire();
			}
			
			if (h == 1) {
				shipList[i].setHit(true);
			}
			
			boolean coll = false;
			for(Block b : blockList) {
				if(!coll) {
					coll = b.checkPlayerCollision(shipList[i]);
				} else {
					b.checkPlayerCollision(shipList[i]);
				}
    		}
			//if(!coll) {
			shipList[i].setLocation(x, y);
			//}
//			if (i == this.player) {
//				System.out.println(x + " " + y);
//			}
			
			shipList[i].setHP(hp);
			
			
			
		}
		
		//System.out.println(shipList[this.player].x + " " + shipList[this.player].y);
		
		
		
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
    
    public class BoardMouseListener extends MouseAdapter {

    	@Override
        public void mousePressed(MouseEvent me) { 
//        	System.out.println(me.getX() +", " +me.getY()); 
    		shipList[player].mousePressed(me);
        } 

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
