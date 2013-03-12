package damien.com.States;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import damien.com.Main.Driver;

public class StartScreen extends BasicGameState{

	int stateID;
	
	public StartScreen(int id)
	{
		stateID = id;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g)
			throws SlickException {
		
		g.setColor(new Color(255,100,100));
		g.drawString("Start Screen", 350, 10);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta)
			throws SlickException {
		
		/* Get the keyboard, mouse input */
		Input input = gc.getInput();
		
		if(input.isKeyPressed(Input.KEY_ENTER))
		{
			sb.enterState(Driver.GAMEPLAYSCREEN);
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

}










