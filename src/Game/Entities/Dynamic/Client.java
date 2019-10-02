package Game.Entities.Dynamic;

import Game.Entities.Static.Burger;
import Game.Entities.Static.Item;
import Game.Entities.Static.Order;
import Main.Handler;
import Resources.Images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Client extends BaseDynamicEntity {
    int patience;
    Graphics2D g2D;
    int OGpatience;
    Order order;
    int Position;
    public boolean isLeaving = false;
    public Client(int xPos, int yPos, Handler handler, int pos) {
        super(Images.people[new Random().nextInt(9)], xPos, yPos,64,72, handler);
        Position=pos;
        patience = new Random().nextInt(120*60)+60*60;
        OGpatience = patience;
        int numOfIngredients = new Random().nextInt(4)+1;
        order = new Order();
        order.food = new Burger(xPos +50,yPos,52,22);
        ((Burger) order.food).addIngredient(Item.botBread);
        ((Burger) order.food).addIngredient(Item.burger);
        order.value += 1.0;
        for(int i = 0;i<numOfIngredients;i++){
            int ingredients = new Random().nextInt(4)+1;
            order.value += 0.5;
            switch (ingredients){
                case 1:
                    ((Burger) order.food).addIngredient(Item.lettuce);

                    break;
                case 2:
                    ((Burger) order.food).addIngredient(Item.tomato);

                    break;

                case 3:
                    ((Burger) order.food).addIngredient(Item.cheese);

                    break;

            }

        }
        ((Burger) order.food).addIngredient(Item.topBread);

    }

    public double getPatiencePercentage() {return (0.0+patience)/(0.0+OGpatience);}
    
    
    public int getPosition() {return Position;}
    public void setPosition(int setPos) {
    	Position=setPos;
    	UpdateXPos();    	
    }
    
    public void AddPercentageOfPatience(int Percentage) {
    	patience+= (OGpatience*Percentage)/100;
    } 
    
   
    
    public void tick(){
        patience--;
        if(patience==0){
            isLeaving=true;
            Position=-1;
            handler.getPlayer().OhNoSomeoneLeft();
        }
    }
    
    public void PleaseLeave() {
    	isLeaving=true;
    }
    public void render(Graphics g){
    	 g2D= (Graphics2D) g;

        if(!isLeaving){
        	g.drawImage(Images.tint(sprite,1.0f,((float)patience/(float)OGpatience),((float)patience/(float)OGpatience)),xPos,yPos,width,height,null);
            
        	double PatiencePercentage = (0.0+patience)/(0.0+OGpatience);
        	if (PatiencePercentage>=.50) {
        		g.setColor(Color.green);
			}
        	else if (PatiencePercentage>=.25) {
        		g.setColor(Color.yellow);
        	}
        	else {
        		g.setColor(Color.RED);
        	}
        		
        	
        	
        	
            Rectangle.Double r	= new Rectangle.Double(xPos,(72-10)+yPos, PatiencePercentage*64, 10);
            
            g2D.fill(r);
            g.setColor(Color.WHITE);
            g.drawString(Integer.toString(Position),xPos,(72-10)+yPos);
            r	= new Rectangle.Double(xPos,(72-10)+yPos, 64, 10);
        	g.setColor(Color.black);
        	g2D.draw(r);
        	
            
            ((Burger) order.food).render(g);
        }
    }

    public void UpdateXPos() {
    	xPos=(132*(Position-1))+10;
    	((Burger) order.food).x=(132*(Position-1))+10+50;
    }
    
    public void move(){
    	Position++;
    	UpdateXPos();
//        yPos+=102;
//        ((Burger) order.food).y+=102;

    }
}
