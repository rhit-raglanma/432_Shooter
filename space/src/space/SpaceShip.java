package space;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SpaceShip extends Sprite {

    private int dx;
    private int dy;
    private List<Missile> missiles;
    private boolean firing;

    public SpaceShip(int x, int y) {
        super(x, y);
        
        this.firing = false;
        
        initSpaceShip();
    }

    private void initSpaceShip() {
    	

        missiles = new ArrayList<>();
        
        loadImage("src/resources/spaceship.png"); 
        getImageDimensions();
    }
    
    public String getMoveString() {
		
		String move = "";
		
		if (this.dy == -1) {
			move = move.concat("1");
		} else {
			move = move.concat("0");
		}
		
		if (this.dy == 1) {
			move = move.concat("1");
		} else {
			move = move.concat("0");
		}
		
		if (this.dx == -1) {
			move = move.concat("1");
		} else {
			move = move.concat("0");
		}
		
		if (this.dx == 1) {
			move = move.concat("1");
		} else {
			move = move.concat("0");
		}
		
		if (this.firing) {
			move = move.concat("1");
		} else {
			move = move.concat("0");
		}
		
		return move;
	}
    
    public void setLocation(int mx, int my) {
    	x = mx;
    	y = my;
    }

    public void move() {
        x += dx;
        y += dy;
    }
    
    public void removeMissile(Missile m) {
    	this.missiles.remove(m);
    }

    public List<Missile> getMissiles() {
    	
    	
    	
        return new ArrayList<Missile>(missiles);
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
        	this.firing = true;
            //fire();
        }

        if (key == KeyEvent.VK_LEFT) {
            dx = -1;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -1;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 1;
        }
    }
    
    public boolean getFire() {
    	return this.firing;
    }
    
    public void setFire(boolean b) {
    	this.firing = b;
    }

    public void fire() {
        missiles.add(new Missile(x + width, y + height / 2));
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}
