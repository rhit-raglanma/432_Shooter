package serve;

import java.util.ArrayList;

public class Player {
	
	public int x;
	public int y;
	public int playernum;
	public boolean firing;
	public boolean hit;
	public int bx;
	public int by;
	public int hp;
	private int[] startxA;
	private int[] startyA;
	
	private ArrayList<ServerBlock> blocks = new ArrayList<ServerBlock>();
	
	public Player(int playernum, ArrayList<ServerBlock> blocks) {
		
		int[] startx = {70, 670, 70, 670, 70, 670, 370, 370, 370};
		int[] starty = {55, 655, 655, 55, 355, 355, 55, 655, 355};
		
		this.startxA = startx;
		this.startyA = starty;
		
		this.hp = 9;
		this.bx = 0;
		this.by = 0;
		this.firing = false;
		this.playernum = playernum;
		this.x = 900;
		this.y = 900;
		this.hit = false;
		
		this.blocks = blocks;
	}
	
	
	
	public void move(String s) {
		
		if (s.charAt(0) == '1') {
			this.y -= 4 + (9 - this.hp) / 2;
			for(ServerBlock b : blocks) {
				if(b.checkCollision(x, y) == 3) {
					this.y = b.getBot() + 1;
				}
			}
		}
		if (s.charAt(1) == '1') {
			this.y += 4 + (9 - this.hp) / 2;
			for(ServerBlock b : blocks) {
				if(b.checkCollision(x, y) == 2) {
					this.y = b.getTop() - 41;
				}
			}
		}
		if (s.charAt(2) == '1') {
			this.x -= 4 + (9 - this.hp) / 2;
			for(ServerBlock b : blocks) {
				if(b.checkCollision(x, y) == 4) {
					this.x = b.getRight() + 1;
				}
			}
		}
		if (s.charAt(3) == '1') {
			this.x += 4 + (9 - this.hp) / 2;
			for(ServerBlock b : blocks) {
				if(b.checkCollision(x, y) == 1) {
					this.x = b.getLeft() - 41;
				}
			}
		}
		if (s.charAt(4) == '1') {
			this.firing = true;
		} else {
			this.firing = false;
		}
		if (s.charAt(5) == '1') {
			
			if (!this.hit && this.hp > 0) {
				this.hp--;
			}
			
			this.hit = true;
		} else {
			this.hit = false;
		}
		
		this.bx = Integer.parseInt(s.substring(6, 9));
		this.by = Integer.parseInt(s.substring(9, 12));
		
		if (this.x < 0) {
			this.x = 0;
		}
		if (this.y < 0) {
			this.y = 0;
		}
		
		if (this.x == 900 && this.y == 900) {
			this.x = this.startxA[this.playernum];
			this.y = this.startyA[this.playernum];
		}
		
		if (this.x > 740) {
			this.x = 740;
		}
		if (this.y > 710) {
			this.y = 710;
		}
	}
	

}
