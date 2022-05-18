package space;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseListener extends MouseAdapter {

    public void mousePressed(MouseEvent me) { 
    	System.out.println(me.getX() +", " +me.getY()); 
    } 

}
