package pong;
import javax.swing.*;
import java.awt.image.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable, KeyListener{

	public static JFrame frame;
	public static final int WIDTH=150;
	public static final int HEIGHT=80;
	private boolean isRunning;
	public BufferedImage image;
	private final int SCALE=4;
	private Thread thread;
	public static Player player;
	public static Enemy enemy;
	public static Ball ball;
	
	public Game() {
		initFrame();
		entities();
	}
	
	public void initFrame() {
		this.addKeyListener(this);
		frame.add(this);
		setPreferredSize(new Dimension (WIDTH*SCALE, HEIGHT*SCALE));
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
	}
	
	public synchronized void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
		
	public void tick() {
		player.tick();
		enemy.tick();
		ball.tick();
	}
		
	public void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		
		g.setColor(new Color(35,20,40));
		g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
		
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.setColor(Color.white);
		g.drawString("----------------------------------------------------------", 0, 30);
		
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.setColor(Color.white);
		g.drawString("Made By: Korzre", 30, 40);
		
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.setColor(Color.white);
		g.drawString("----------------------------------------------------------", 0, 50);
		
		player.render(g);
		enemy.render(g);
		ball.render(g);
		
		
		
		g = bs.getDrawGraphics();				
		g.drawImage(image,0,0,WIDTH*SCALE,HEIGHT*SCALE, null);
		bs.show();
		g.dispose();
		
	}
		
	public void run() {
		long last = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000/amountOfTicks;
		
		double timer = System.currentTimeMillis();
		double delta =0;
		int frames=0;
		
		while(isRunning) {
			long now = System.nanoTime();
			delta +=(now-last)/ns;
			last = now;
			if(delta>=1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: "+frames);
				timer+=1000;
				frames =0;
			}
		}
		stop();
	}
		
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right= true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right= false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
		
	}
	public void entities() {
		player = new Player(50,HEIGHT-5);
		enemy = new Enemy(50,0);
		ball = new Ball(50,HEIGHT/2-4);	
		image=new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

	}
	
	public static void main(String[] args) {
		frame = new JFrame("Game: Pong");
		Game game = new Game();
		game.start();
	}
	
}
