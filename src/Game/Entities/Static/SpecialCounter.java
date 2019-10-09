package Game.Entities.Static;

import Main.Handler;
import Resources.Images;

import java.awt.*;

public class SpecialCounter extends BaseCounter {
	
	public SpecialCounter(int xPos, int yPos, Handler handler) {
        super(Images.kitchenCounter[9], xPos, yPos,96,117+11,handler);
    }
    
    @Override
    public void render(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        if (DistractionAvailable) {g.drawImage(Images.tint(sprite,1.0f,0.0f,1.0f),xPos,yPos,width,height,null);} //Draw the image in pink
        else {g.drawImage(sprite,xPos,yPos,width,height,null);} //Draw it normally
    }
}
