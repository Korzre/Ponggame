package pong;
import java.awt.Color;
import java.awt.Graphics;


public class Enemy {
	
	public double x,y;
	public int width, height;
	
	public Enemy(int x, int y) {
		this.x = x;
		this.y=y;
		this.width = 50;
		this.height = 5;
	}
	
	public void tick() {
		//x+= (Game.ball.x - x-6)*0.6;IA(HARD)
		//x+= (Game.ball.x - x-6)*0.02;IA(MEDIUM)
		x+= (Game.ball.x - x-6)*0.08;
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(25,255,255));
		g.fillRect((int)x,(int)y, width,height);	
	}
	
}
