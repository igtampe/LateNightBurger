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
public class WinState extends State {

    private int count = 0;
    private UIManager uiManager;

    public WinState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);

        uiManager.addObjects(new UIImageButton(64+48, 432, 256, 128, Images.PAgain, () -> {
            handler.getMouseManager().setUimanager(null);
            handler.getGame().reStart();
            State.setState(handler.getGame().gameState);
        }));

        uiManager.addObjects(new UIImageButton(448+48, 432, 256, 128, Images.Quit, () -> {
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
    	g.drawImage(Images.InactiveBG,0,0,864,768,null);
    	g.drawImage(Images.WinPanel,0,0,864,768,null);
    	g.setFont(fonts.StatisticsFont);
		g.setColor(Color.WHITE);
		g.drawString(Integer.toString(handler.getPlayer().GetPeopleServed()), fonts.StatisticsX, fonts.ServedY);
		g.drawString(Integer.toString(handler.getPlayer().GetPeopleLeft()), fonts.StatisticsX, fonts.LostY);
    	uiManager.Render(g);

    }
}
