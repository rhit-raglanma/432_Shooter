package space;

import java.awt.Rectangle;

public class Missile extends Sprite {

    private final int BOARD_WIDTH = 390;
    private final int MISSILE_SPEED = 25;
    private Rectangle hitbox;
    private double dx;
    private double dy;

    public Missile(int x, int y, double dx, double dy) {
        super(x, y);
        
        this.dx = dx;
        this.dy = dy;
        
        this.hitbox = new Rectangle(x, y, 10, 10);
        
        initMissile();
    }
    
    private void initMissile() {
        
        loadImage("src/resources/missile.png");  
        getImageDimensions();
    }

    public void move() {
        
        x += this.dx;
        y += this.dy;
        
        this.hitbox = new Rectangle(x, y, 10, 10);
        
        if (x > BOARD_WIDTH) {
            visible = false;
        }
    }
    
    public Rectangle getHitbox() {
    	return this.hitbox;
    }
}