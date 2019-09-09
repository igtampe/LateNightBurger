package Game.Entities.Dynamic;

import Game.Entities.Static.*;
import Main.Handler;
import Resources.Images;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends BaseDynamicEntity {
    Item item;
    float money;
    int speed = 5;
    private Burger burger;
    private String direction = "right";
    private int interactionCounter = 0;
    public Player(BufferedImage sprite, int xPos, int yPos, Handler handler) {
        super(sprite, xPos, yPos,82,112, handler);
        createBurger();
    }

    public void createBurger(){
        burger = new Burger(handler.getWidth() - 110, 100, 100, 50);

    }

    public void tick(){
        if(xPos + width >= handler.getWidth()){
            direction = "left";

        } else if(xPos <= 0){
            direction = "right";
        }
        if (direction.equals("right")){
            xPos+=speed;
        } else{
            xPos-=speed;
        }
        if (interactionCounter > 15 && handler.getKeyManager().attbut){
            interact();
            interactionCounter = 0;
        } else {
            interactionCounter++;
        }
        if(handler.getKeyManager().fattbut){
            for(BaseCounter counter: handler.getWorld().Counters){
                if (counter instanceof EmptyCounter && counter.isInteractable()){
                    createBurger();
                }
            }
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_R)){
            for(BaseCounter counter: handler.getWorld().Counters) {
                if (counter instanceof PlateCounter && counter.isInteractable()) {
                    ringCustomer();
                }
            }
        }
    }

    private void ringCustomer() {

        for(Client client: handler.getWorld().clients){
            boolean matched =true;
            for(int i =0 ;i<((Burger)client.order.food).ingredients.size();i++){
                if(!((Burger)client.order.food).ingredients.get(i).sprite.equals(handler.getCurrentBurger().ingredients.get(i).sprite) ){
                    matched=false;
                    System.out.println("Didnt Match");
                    break;
                }

            }
            if(matched){
                money+=client.order.value;
                handler.getWorld().clients.remove(client);
                handler.getPlayer().createBurger();
                System.out.println("Total money earned is: " + String.valueOf(money));
                return;
            }

        }
    }

    public void render(Graphics g) {
        g.drawImage(Images.player, xPos, yPos, width, height, null);
        g.setColor(Color.green);
        burger.render(g);
    }

    public void interact(){
        for(BaseCounter counter: handler.getWorld().Counters){
            if (counter.isInteractable()){
                counter.interact();
            }
        }
    }
    public Burger getBurger(){
        return this.burger;
    }
}
