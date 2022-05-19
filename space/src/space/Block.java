package space;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Block {
	private Rectangle hitbox;
	public Block(int x, int y, int width, int height) {
		this.hitbox = new Rectangle(x, y, width, height);
	}
	public void checkMissileCollision(Missile m) {
		if(m.getHitbox().intersects(this.hitbox)) {
			m.setVisible(false);
		}
	}
	public boolean checkPlayerCollision(SpaceShip s) {
		Rectangle r = new Rectangle(s.getHitbox().x + s.getdx(), s.getHitbox().y + s.getdy(), 40, 40);
		
		if(r.intersectsLine(this.hitbox.getMinX(), this.hitbox.getMinY(), this.hitbox.getMinX(), this.hitbox.getMaxY())) {
			System.out.println("L");
			s.setLocation(this.hitbox.x - 41, s.getHitbox().y);
			return true;
		}
		if(r.intersectsLine(this.hitbox.getMinX(), this.hitbox.getMinY(), this.hitbox.getMaxX(), this.hitbox.getMinY())) {
			System.out.println("T");
			s.setLocation(s.getHitbox().x, this.hitbox.y - 41);
			return true;
		}
		if(r.intersectsLine(this.hitbox.getMaxX(), this.hitbox.getMaxY(), this.hitbox.getMinX(), this.hitbox.getMaxY())) {
			System.out.println("B");
			s.setLocation(s.getHitbox().x, this.hitbox.y + this.hitbox.height + 1);
			return true;
		}
		if(r.intersectsLine(this.hitbox.getMaxX(), this.hitbox.getMaxY(), this.hitbox.getMaxX(), this.hitbox.getMinY())) {
			System.out.println("R");
			s.setLocation(this.hitbox.x + this.hitbox.width + 1, s.getHitbox().y);
			return true;
		}
		return false;
//		if(this.hitbox.intersects(r)) {
//			if(s.getHitbox().x + 40 <= this.hitbox.x && s.getHitbox().x + 40 + s.getdx() >= this.hitbox.x) {
//				System.out.println("L");
//				s.setCollision("L");
//			} else if(s.getHitbox().x >= this.hitbox.x + this.hitbox.width && s.getHitbox().x + s.getdx() <= this.hitbox.x + this.hitbox.width) {
//				System.out.println("R");
//				s.setCollision("R");
//			} else if(s.getHitbox().y + 40 <= this.hitbox.y && s.getHitbox().y + 40 + s.getdy() >= this.hitbox.y) {
//				System.out.println("T");
//				s.setCollision("T");
//			} else if(s.getHitbox().y >= this.hitbox.y + this.hitbox.height && s.getHitbox().y + s.getdy() <= this.hitbox.y + this.hitbox.height) {
//				System.out.println("B");
//				s.setCollision("B");
//			}
//		} else {
//			s.setCollision("");
//		}
	}
	public void draw(Graphics2D g2d) {
		g2d.fillRect(this.hitbox.x, this.hitbox.y, this.hitbox.width, this.hitbox.height);
	}
}
