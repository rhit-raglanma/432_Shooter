package space;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import java.awt.EventQueue;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JFrame;

public class Main extends JFrame {

    public Main() {
    	
    	
        
        initUI();
    }
    
    private void initUI() {
        
        try {
			add(new Board());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        setSize(400, 300);
        setResizable(false);
        
        setTitle("Shooting missiles");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws Exception {
    	
    	
		
		
        
        EventQueue.invokeLater(() -> {
        	Main ex = new Main();
            ex.setVisible(true);
        });
    }
}
