package serve;
import java.awt.Rectangle;
public class ServerBlock {
	private Rectangle hitbox;
	public ServerBlock(int x, int y, int width, int height) {
		this.hitbox = new Rectangle(x, y, width, height);
	}
	public int getBot() {
		return this.hitbox.y + this.hitbox.height;
	}
	public int getLeft() {
		return this.hitbox.x;
	}
	public int getRight() {
		return this.hitbox.x + this.hitbox.width;
	}
	public int getTop() {
		return this.hitbox.y;
	}
	public int checkCollision(int x, int y) {
		Rectangle r = new Rectangle(x, y, 40, 40);
		if(r.intersectsLine(this.hitbox.getMinX(), this.hitbox.getMinY(), this.hitbox.getMinX(), this.hitbox.getMaxY())) {
//			System.out.println("L");
//			s.setLocation(this.hitbox.x - 40, y);
			return 1;
		}
		if(r.intersectsLine(this.hitbox.getMinX(), this.hitbox.getMinY(), this.hitbox.getMaxX(), this.hitbox.getMinY())) {
//			System.out.println("T");
//			s.setLocation(x, this.hitbox.y - 40);
			return 2;
		}
		if(r.intersectsLine(this.hitbox.getMaxX(), this.hitbox.getMaxY(), this.hitbox.getMinX(), this.hitbox.getMaxY())) {
//			System.out.println("B");
//			s.setLocation(x, this.hitbox.y + this.hitbox.height);
			return 3;
		}
		if(r.intersectsLine(this.hitbox.getMaxX(), this.hitbox.getMaxY(), this.hitbox.getMaxX(), this.hitbox.getMinY())) {
//			System.out.println("R");
//			s.setLocation(this.hitbox.x + this.hitbox.width, y);
			return 4;
		}
		return 0;
	}
}
