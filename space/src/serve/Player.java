package serve;

public class Player {
	
	public int x;
	public int y;
	public int playernum;
	public boolean firing;
	
	public Player(int playernum) {
		
		this.firing = false;
		this.playernum = playernum;
		this.x = 900;
		this.y = 900;
		
	}
	
	
	
	public void move(String s) {
		
		if (s.charAt(0) == '1') {
			this.y -= 5;
		}
		if (s.charAt(1) == '1') {
			this.y += 5;
		}
		if (s.charAt(2) == '1') {
			this.x -= 5;
		}
		if (s.charAt(3) == '1') {
			this.x += 5;
		}
		if (s.charAt(4) == '1') {
			this.firing = true;
		} else {
			this.firing = false;
		}
		
		if (this.x < 0) {
			this.x = 0;
		}
		if (this.y < 0) {
			this.y = 0;
		}
		
		if (this.x == 900 && this.y == 900) {
			this.x = 100;
			this.y = 100;
		}
		
		if (this.x > 340) {
			this.x = 340;
		}
		if (this.y > 210) {
			this.y = 210;
		}
	}
	

}
