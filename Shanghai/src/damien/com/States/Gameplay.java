package damien.com.States;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import damien.com.Camera.Camera;
import damien.com.Main.Driver;
import damien.com.Map.Map;
import damien.com.Sprites.Bullet;
import damien.com.Sprites.Player;
import damien.com.Sprites.Sprite;

public class Gameplay extends BasicGameState{

	int stateID;
	Player player;
	Map map1;
	Camera camera;
	Sound gun1, level1Music;
	
	//list of all the bullets currently on the screen
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	public static final float GRAVITY = 0.2f;
	
	public Gameplay(int id)
	{
		stateID = id;
	}
	
	public void enter(GameContainer gc, StateBasedGame sb)
	{
		if(!level1Music.playing())
			level1Music.loop();
	}
	
	public void leave(GameContainer gc, StateBasedGame sb)
	{
		
	}
	
	public void loadSounds() throws SlickException
	{
		gun1 = new Sound("sounds/gunShot1.wav");
		level1Music = new Sound("sounds/QuickSilver.wav");
	}//end loadSounds
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		loadSounds();
		
		player = new Player(new Image("images/Guy_Sprite.PNG"));
		player.x = 300;
		player.y = 300;
		player.speed = 0.2f;
		player.alive = true;
		player.jumpSpeed = -1.25f;
		this.camera = new Camera(Driver.app.getHeight(), Driver.app.getHeight(),0,0,map1);
		map1 = new Map("data/map1.tmx");
		
	}

	public void renderBullets(GameContainer gc, StateBasedGame sb, Graphics g)
			throws SlickException {
		
		//for every bullet in the list
		for(int i=0; i < bullets.size(); i++)
		{
			Bullet b = bullets.get(i); //get the current bullet
			b.draw(g); //draw the bullet to the screen
		}//end for
		
	}//end renderBullets
	
	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g)
			throws SlickException {
		System.out.println(map1.map.getWidth()*48);
		System.out.println(player.x + "player");
		camera.drawCamera(g);
		
	
		map1.draw(g);
		player.draw(g);
		renderBullets(gc, sb, g);
		g.drawString("jumping = " + player.jumping, 300, 10);
		g.drawString("Player (" + player.x + ", " + player.y + ")", 300, 30);
		g.drawString("Camera (" + camera.viewPort.getX() + ", " + camera.viewPort.getY() + ")", 300, 50);
	}

	public void updatePlayer(GameContainer gc, StateBasedGame sb, int delta, Input input)
			throws SlickException {
		
		/*
		 * Gravity Code
		 */
		if(!map1.collidingWithMap(player))
		{
			player.y+=GRAVITY*delta;
		}
		
		/* remember his location BEFORE he moves */
		float oldx = player.x;
		float oldy = player.y;
		
		/* jump code */
		if(input.isKeyPressed(Input.KEY_W) || input.isKeyPressed(Input.KEY_UP))
		{
			player.jump(delta);
		}
		player.updateJump(delta, GRAVITY, map1);
		
		if(input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT))
		{
			player.moveLeft(delta);
			if(map1.collideLRMap(player))
			{
				player.x = oldx;
			}
		}
		if(input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT))
		{
			player.moveRight(delta);
			if(map1.collideLRMap(player))
			{
				player.x = oldx;
			}
		}
		
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
		{
			Bullet b = player.shootBullet(input.getMouseX(), input.getAbsoluteMouseY(), camera);
			bullets.add(b); //add the new bullet to the list
			gun1.play(1.0f, 0.5f);
		}
	}
	
	public void updateBullets(GameContainer gc, StateBasedGame sb, int delta)
			throws SlickException {
		
		//for every bullet in the list
		for(int i=0; i < bullets.size(); i++)
		{
			Bullet b = bullets.get(i); //get the current bullet
			b.update(delta); //move the bullet
		}//end for
		
	}//end updateBullets
	
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta)
			throws SlickException {
		
		/* Get the keyboard, mouse input */
		Input input = gc.getInput();
		
		updatePlayer(gc, sb, delta, input);
		updateBullets(gc, sb, delta);
		this.camera.translate(player.x);
		/*
		 * If to handle the changing of the game state
		 */
		if(input.isKeyPressed(Input.KEY_ESCAPE))
		{
			sb.enterState(Driver.ENDSCREEN);
		}
		if(input.isKeyPressed(Input.KEY_P))
		{
			sb.enterState(Driver.PAUSESCREEN);
		}
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}

}
