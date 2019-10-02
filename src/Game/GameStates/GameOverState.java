package Game.GameStates;

import Main.Handler;
import Resources.Images;
import Resources.fonts;
import Display.UI.UIImageButton;
import Display.UI.UIManager;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class GameOverState extends State {

	private int count = 0;
	private UIManager uiManager;

	public GameOverState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUimanager(uiManager);

		uiManager.addObjects(new UIImageButton(56, 223, 256, 128, Images.Resume, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().gameState);
        }));

        uiManager.addObjects(new UIImageButton(56, (223+(64+16))+(64+16), 256, 128, Images.Quit, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
        }));



	}

	@Override
	public void tick() {

		handler.getMouseManager().setUimanager(uiManager);
		uiManager.tick();
		count++;
		if( count>=30){
			count=30;
		}
		if(handler.getKeyManager().pbutt && count>=30){
			count=0;

			State.setState(handler.getGame().gameState);
		}


	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Images.InactiveBG,0,0,768,768,null);
		g.drawImage(Images.GameOverPanel,0,0,768,768,null);
		uiManager.Render(g);

	}
}
