package space;

import java.awt.Rectangle;

public class Missile extends Sprite {

    private final int BOARD_WIDTH = 390;
    private final int MISSILE_SPEED = 25;
    private Rectangle hitbox;

    public Missile(int x, int y) {
        super(x, y);
        
        this.hitbox = new Rectangle(x, y, 10, 10);
        
        initMissile();
    }
    
    private void initMissile() {
        
        loadImage("src/resources/missile.png");  
        getImageDimensions();
    }

    public void move() {
        
        x += MISSILE_SPEED;
        
        this.hitbox = new Rectangle(x, y, 10, 10);
        
        if (x > BOARD_WIDTH) {
            visible = false;
        }
    }
    
    public Rectangle getHitbox() {
    	return this.hitbox;
    }
}