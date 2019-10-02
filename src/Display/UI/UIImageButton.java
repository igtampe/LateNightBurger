package Display.UI;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class UIImageButton extends UIObject{
    private BufferedImage[] images;
    private ClickListlener clicker;
    
    /**
     * UI Image Button makes a UI Image Button. <br>
     * (AlexVR May have wanted to put some javadoc on this)
     * @param x Position in X
     * @param y Position in Y
     * @param width Width of the coso
     * @param height Height of the coso
     * @param images Images (0 being inactive, 1 being hovered, and 2 being clicked)
     * @param clicker The click listener (do not touch pls kthx)
     */
    public UIImageButton(float x, float y, int width, int height, BufferedImage[] images,ClickListlener clicker ) {
        super(x, y, width, height);
        this.images=images;
        this.clicker=clicker;
    }


    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        if(active){
            if(images.length==3) {
                g.drawImage(images[2], (int) x, (int) y, width, heith, null);
            }
        }
        else if(hovering){
            g.drawImage(images[1],(int)x,(int)y,width,heith,null);
        }else{
            g.drawImage(images[0],(int)x,(int)y,width,heith,null);

        }
    }


    @Override
    public void onClick()
    {
        clicker.onClick();
    }
}
