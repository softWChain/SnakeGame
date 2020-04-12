package window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

import body.BodyPart;
import body.Tile;
import input.KeyInput;

public class Game extends Canvas implements Runnable{
	
	public static int WIDTH = 500;
	public static int HEIGHT = 500;
	
	private Window window;
	private boolean running = false;
	private Thread thread;
	
	private BodyPart b;
	private ArrayList<BodyPart> snake;
	private int x = 10,y=10,size = 3;
	
	private Tile tile;
	private ArrayList<Tile> tills;
	
	private Random rand = new Random();
	
	private int ticks=0;
	
	public boolean right =true,left = false,up=false,down=false;
	
	public Game(){
		setFocusable(true);
		window = new Window(WIDTH,HEIGHT,this);
		
	}
	
	public void init(){
		setFocusable(true);
		snake = new ArrayList<BodyPart>();
		tills = new ArrayList<Tile>(); 
		
		addKeyListener(new KeyInput(this));
		
	}
	
	public void tick(){
		
		if(snake.size() == 0){
			b = new BodyPart(x, y, 20);
			snake.add(b);
		}
		ticks++;
		
		if(ticks>20){
			if(isRight()) x++;
			if(isLeft()) x--;
			if(isUp()) y--;
			if(isDown()) y++;
			ticks=0;
			
			b= new BodyPart(x, y, 20);
			snake.add(b);
			
			if(snake.size()>size){
				snake.remove(0);
			}
		}
		
		if(tills.size()==0){
			int x = rand.nextInt(24);
			int y = rand.nextInt(24);
			
			tile = new Tile(x, y, 20);
			tills.add(tile);
		}
		
		
		//PİCK UP TİLE
		for(int i=0;i<tills.size();i++){
			if(x == tills.get(i).getX() && y == tills.get(i).getY()){
				size++;
				tills.remove(i);
				i++;
			}
		}
		
		///SNAKE BODY COLLOSİON
		for(int i=0;i<snake.size();i++){
			if(x == snake.get(i).getX() && y == snake.get(i).getY()){
				if(i !=snake.size() - 1){
					System.out.println("GAME OVER");
					stop();
				}
			}
		}
		
		//BORDER COLLOSİON
		if(x<0 || y<0 || y>24 || x>24){
			System.out.println("GAME OVER");
			stop();
		}
	
		
	}
	public void render(){
		
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		for(int i=0;i<WIDTH/20;i++){
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(i*20, 0, i*20, HEIGHT);
		}
		for(int i=0;i<WIDTH/20;i++){
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(0, i*20, WIDTH, i*20);
		}
		
		
		for(int i=0;i<snake.size();i++){
			snake.get(i).render(g);
		}
		for(int i=0;i<tills.size();i++){
			tills.get(i).render(g);
		}

		g.setColor(Color.WHITE);
		g.setFont(new Font("New Times Roman",Font.PLAIN,12));
		g.drawString("SCORE : " + (size - 3), 20, 20);
		
		bs.show();
		g.dispose();
		
	}
	
	public void run(){
		init();
		
		int FPS = 200;
		double targetFPS = 1000000000 / FPS;
		double delta = 0;
		
		long lastTime = System.nanoTime();
		long now;
		long timer = System.currentTimeMillis();
		
		int ticks=0;
		int updates = 0;
		
		while(running){
			
			now = System.nanoTime();
			delta +=(now - lastTime ) / targetFPS;
			lastTime = now;
			
			if(delta>=1){
				tick();
				ticks++;
				delta--;
			}
			render();
			updates++;
			
			if(System.currentTimeMillis() - timer >= 1000){
				timer += 1000;
				System.out.println("FPS : " + ticks + "  - UPDATES : " + updates);
				ticks = 0;
				updates = 0;
			}
			
			
		}
		
		stop();
	}
	
	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this,"ThreadGame");
		thread.start();
		
	}
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void main(String args[]){
		new Game().start();
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	
	

}
