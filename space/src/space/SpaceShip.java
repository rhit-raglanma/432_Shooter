package space;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class SpaceShip extends Sprite {

    private int dx;
    private int dy;
    private List<Missile> missiles;
    private boolean firing;
    private Rectangle hitbox;
    private boolean hit;
    private int hp;
    private int hitBy;
    
    private int bdx, bdy;
    
    private String collision;

    public SpaceShip(int x, int y) {
        super(x, y);
        
        this.hitBy = 0;
        this.hp = 9;
        this.firing = false;
        this.hitbox = new Rectangle(x, y, 40, 40);
        this.hit = false;
        
        this.collision = "";
        
        initSpaceShip();
        
        
    }

    public void setCollision(String s) {
    	this.collision = s;
    }
    
    public int getdx() {
    	return this.dx;
    }
    
    public int getdy() {
    	return this.dy;
    }
    
    private void initSpaceShip() {
    	

        missiles = new ArrayList<>();
        
        loadImage("src/resources/spaceship.png"); 
        getImageDimensions();
        
        
//        addMouseListener(new MouseAdapter() {
//        	public void mousePressed(MouseEvent e) {
//        		
//        	}
//        });
        
//        addMouseMotionListener(new MouseMotionAdapter() {
//        	
//        })
        
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
		
		if (this.hit) {
			move = move.concat("1");
		} else {
			move = move.concat("0");
		}
		
		move = move.concat(String.format("%03d", this.bdx));
		move = move.concat(String.format("%03d", this.bdy));
		
		move = move.concat(Integer.toString(this.hitBy));
		
		return move;
	}
    
    public void setHP(int h) {
    	this.hp = h;
    }
    
    public int getHP() {
    	return this.hp;
    }
    
    public int getHitBy() {
    	return this.hitBy;
    }
    
    public void setHitBy(int i) {
    	this.hitBy = i;
    }
    
    public void setDirection(int bx, int by) {
    	this.bdx = bx;
    	this.bdy = by;
    }
    
    public void setLocation(int mx, int my) {
    	x = mx;
    	y = my;
    	
    	this.hitbox = new Rectangle(x, y, 40, 40);
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
    
    public void mousePressed(MouseEvent e) {
    	
    	this.bdx = e.getX();
    	this.bdy = e.getY();
    	
    	this.firing = true;
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
//        missiles.add(new Missile(x + width, y + height / 2, (this.bdx - this.x)/10, (this.bdy - this.y)/10));
    	double vecLen = Math.sqrt(Math.pow(this.bdx - (this.x + 20), 2) + Math.pow(this.bdy - (this.y + 20), 2));
    	
    	Missile m = new Missile(x + 20, y + 20, (double)20*(this.bdx - (this.x + 20))/vecLen, (double)20*(this.bdy - (this.y + 20))/vecLen);
    	
    	m.move(); m.move();
    	
    	missiles.add(m);
    	
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
    
    public Rectangle getHitbox() {
    	return this.hitbox;
    }
    
    public void setHit(boolean b) {
    	this.hit = b;
    }
    
    public boolean getHit() {
    	return this.hit;
    }
}
