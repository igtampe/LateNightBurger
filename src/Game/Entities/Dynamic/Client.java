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
	private static int ImageInt=0;
	public boolean isLeaving = false;
	private boolean ComplainedAngry=false;
	private boolean ComplainedAnnoyed=false;
	public boolean inspectorAlert=false;
	public boolean inspectorMad=false;
	public boolean inspectorHappy=false;
	public boolean antiVAlert=false;
	private double antiVOldPatience = 100.00;
	public Client(int xPos, int yPos, Handler handler, int pos) {
		super(Images.people[ImageInt], xPos, yPos,64,72, handler);
		ImageInt=new Random().nextInt(11);
//		ImageInt=(new Random().nextInt(3))+8;
		sprite=Images.people[ImageInt];
		inspectorAlert=(ImageInt==9);
		antiVAlert=(ImageInt==10);
		System.out.println(ImageInt + " " + inspectorAlert + " " + antiVAlert);
		Position=pos;
		patience = new Random().nextInt(120*60)+60*60;

		if (handler.getPlayer().InspectorHasLeft()) {
			if(handler.getPlayer().InspectorLeftAngry()) {patience=(int) (patience*.94);}
			else {patience=(int)(patience*1.12);}
		}

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
	}


	public double getPatiencePercentage() {return (0.0+patience)/(0.0+OGpatience);}


	public int getPosition() {return Position;}
	public void setPosition(int setPos) {
		Position=setPos;
		UpdateXPos();    	
	}

	public void AddPercentageOfPatience(int Percentage) {
		patience+= (OGpatience*Percentage)/100;
		if (patience>=OGpatience) {patience=OGpatience;}
	} 
	public void resetPatience() {
		patience=OGpatience;
		
		//this is to make sure the AntiV doesn't have a negative patience difference.
		antiVOldPatience=getPatiencePercentage();
	}

	public void tick(){
		patience--;
		if (antiVAlert && antiVOldPatience-getPatiencePercentage()>=.08 ) {
			System.out.println("Attempting to annoy someone");
			Random Randomizer = new Random();
			int Direction=(int) Math.pow(-1, Randomizer.nextInt(4));
			if (Position==1) {Direction=1;}
			if (Position==5) {Direction=-1;}
			antiVOldPatience=getPatiencePercentage();
			for(Client client: handler.getWorld().clients) {
				boolean matched = (client.Position == Position + Direction);
				if(matched) {
					System.out.println("Anonyed client on position: " + (Position+Direction));
					client.AddPercentageOfPatience(-4);
					if (myDialogBox.isTrash()) {myDialogBox=handler.getWorld().DialogueBubble(xPos, yPos, Dialogue.getAntiVDialogue(),Dialogue.color, Dialogue.font);

					}

				}
			}
		}
		if(patience<=0 && isLeaving==false){

			if (inspectorAlert) {
				handler.getPlayer().SetInspectorAngry(true);
				handler.getPlayer().SetInspectorLeft(true);

				handler.getPlayer().TakeHalfMoney();
			}


			isLeaving=true;
			handler.getWorld().DialogueBubble(xPos, yPos, Dialogue.getWithoutBurger(), Dialogue.color, Dialogue.font);
			Position=-1;
			handler.getPlayer().OhNoSomeoneLeft();
		}

	}
	public boolean inspectorAlert(){return inspectorAlert;}
	public boolean antiVAlert(){return antiVAlert;}

	public void PleaseLeave() {
		isLeaving=true;

		if (getPatiencePercentage()>=.50) {
			handler.getWorld().DialogueBubble(xPos, yPos, Dialogue.getHappyBurger(), Dialogue.color, Dialogue.font);
		}
		else if (getPatiencePercentage()>=.25) {
			handler.getWorld().DialogueBubble(xPos, yPos, Dialogue.getAnnoyedBurger(), Dialogue.color, Dialogue.font);
		}
		else {
			handler.getWorld().DialogueBubble(xPos, yPos, Dialogue.getAngryBurger(), Dialogue.color, Dialogue.font);
		}

		if (inspectorAlert) {
			handler.getPlayer().SetInspectorAngry(false);
			handler.getPlayer().SetInspectorLeft(true);
			for (Client TheCurrentOne : handler.getWorld().clients) {TheCurrentOne.AddPercentageOfPatience(12);}
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