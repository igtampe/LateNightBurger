package Game.Entities.Dynamic;

import Game.Entities.Static.Burger;
import Game.Entities.Static.Item;
import Game.Entities.Static.Order;
import Main.Handler;
import Resources.Dialogue;
import Resources.Images;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Client extends BaseDynamicEntity {
	private FloatText myDialogBox = new FloatText();
	int patience;
	Graphics2D g2D;
	int OGpatience;
	int inspectorLeft;
	Order order;
	int Position;
	int patience2;
	public boolean isLeaving = false;
	private boolean ComplainedAngry=false;
	private boolean ComplainedAnnoyed=false;
	public boolean inspectorAlert=false;
	public boolean inspectorMad=false;
	public boolean inspectorHappy=false;
	public boolean antiVAlert=false;
	public Client(int xPos, int yPos, Handler handler, int pos) {
		super(Images.people[new Random().nextInt(3)], xPos, yPos,64,72, handler);
		//super(Images.people[9], xPos, yPos,64,72, handler);
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
			case 4:
				((Burger) order.food).addIngredient(Item.bacon);
				break;
				
			}

		}
		((Burger) order.food).addIngredient(Item.topBread);

		myDialogBox=handler.getWorld().DialogueBubble(this.xPos, this.yPos, Dialogue.getGreeting(), Dialogue.color, Dialogue.font);
		
//		if(inspectorMad == true) {
//			patience2 = (int) (patience - (patience*0.06));
//			
//			System.out.println("Patience: " + patience);
//			System.out.println("Patience 2: " + patience2);
//		}
		
	}


	public double getPatiencePercentage() {return (0.0+patience)/(0.0+OGpatience);}


	public int getPosition() {return Position;}
	public void setPosition(int setPos) {
		Position=setPos;
		UpdateXPos();    	
	}

	public void AddPercentageOfPatience(int Percentage) {patience+= (OGpatience*Percentage)/100;} 
	public void resetPatience() {patience=OGpatience;}

	public void tick(){
		patience--;
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_L)) {
			patience = 0;
		} //debugging key, test purposes
		if(patience==0){
			isLeaving=true;
			handler.getWorld().DialogueBubble(xPos, yPos, Dialogue.getWithoutBurger(), Dialogue.color, Dialogue.font);
			Position=-1;
			handler.getPlayer().OhNoSomeoneLeft();
			if(inspectorAlert() == true) {
				inspectorMad = true;
				System.out.println("STRANGER DANGER");
				handler.getPlayer().totalMoney();
			
			}
		}
		
	}
	public boolean inspectorAlert(){
		if(sprite == Images.people[1]) {
			inspectorAlert=true;
		} else {
			inspectorAlert=false;
		}
		return inspectorAlert;
	}
	
	public boolean antiVAlert(){
		if(sprite == Images.people[10]) {
			antiVAlert=true;
		} else {
			antiVAlert=false;
		}
		return antiVAlert;
	}


	public void PleaseLeave() {
		isLeaving=true;

		double PatiencePercentage = (0.0+patience)/(0.0+OGpatience);
		if (PatiencePercentage>=.50) {
			handler.getWorld().DialogueBubble(xPos, yPos, Dialogue.getHappyBurger(), Dialogue.color, Dialogue.font);
			if(inspectorAlert() == true) {
				inspectorHappy = true;
				System.out.println("Patience: " + patience);
				System.out.println("Patience 2: " + patience2);

			}
		}
		else if (PatiencePercentage>=.25) {
			handler.getWorld().DialogueBubble(xPos, yPos, Dialogue.getAnnoyedBurger(), Dialogue.color, Dialogue.font);
		}
		else {
			handler.getWorld().DialogueBubble(xPos, yPos, Dialogue.getAngryBurger(), Dialogue.color, Dialogue.font);
		}


	}

	public void render(Graphics g){
		g2D= (Graphics2D) g;

		if(!isLeaving){
			g.drawImage(Images.tint(sprite,1.0f,((float)patience/(float)OGpatience),((float)patience/(float)OGpatience)),xPos,yPos,width,height,null);

			double PatiencePercentage = (0.0+patience)/(0.0+OGpatience);
			if (PatiencePercentage>=.50) {
				g.setColor(Color.green);
				ComplainedAngry=false;
				ComplainedAnnoyed=false;
			}
			else if (PatiencePercentage>=.25) {
				g.setColor(Color.yellow);
				ComplainedAngry=false;

				if (!ComplainedAnnoyed) {
					ComplainedAnnoyed=true;
					if (myDialogBox.isTrash()) {myDialogBox=handler.getWorld().DialogueBubble(this.xPos, this.yPos, Dialogue.getAnnoyed(), Dialogue.color, Dialogue.font);}
				}
			}
			else {g.setColor(Color.RED);
			ComplainedAnnoyed=true;
			if (!ComplainedAngry) {
				ComplainedAngry=true;
				if (myDialogBox.isTrash()) {myDialogBox=handler.getWorld().DialogueBubble(this.xPos, this.yPos, Dialogue.getAngry(), Dialogue.color, Dialogue.font);}
			}
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
		myDialogBox.setXpos(xPos);
		((Burger) order.food).x=(132*(Position-1))+10+50;
	}

	public void GotIncorrectFood() {
		if (myDialogBox.isTrash()) {myDialogBox=handler.getWorld().DialogueBubble(xPos, yPos, Dialogue.getIncorrect(), Dialogue.color, Dialogue.font);}}

	public void move(){
		Position++;
		UpdateXPos();
		//        yPos+=102;
		//        ((Burger) order.food).y+=102;

	}
}