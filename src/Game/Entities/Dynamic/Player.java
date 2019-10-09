package Game.Entities.Dynamic;

import Game.Entities.Static.*;
import Game.GameStates.State;
import Main.Handler;
import Resources.Animation;
import Resources.Dialogue;
import Resources.Images;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;


public class Player extends BaseDynamicEntity {
	Item item;
	float money;
	int speed = 4;

	private int PeopleWhoHaveLeft=0;
	private int PeopleWhoHaveBeenServed=0;
	private boolean DistractionAvailable=true;
	private boolean TimeForADistraction;
	private boolean DrawBars=true;
	private double CurrentDistraction=-1;
	private double MaxDistraction=-1;
	private double DistractionCountDown=120;
	private boolean TimeToRing=false;
	private int CustomerToRing=1;
	private Random SpinTheWheel=new Random(); 

	private FloatText DistractionText = new FloatText();

	private Burger burger;
	private String direction = "right";
	private int interactionCounter = 0;
	private Animation playerAnim;
	public Player(BufferedImage sprite, int xPos, int yPos, Handler handler) {
		super(sprite, xPos, yPos,82,112, handler);
		createBurger();
		playerAnim = new Animation(120,Images.chef);
	}

	public boolean ImSupposedToBeDistracted() {return TimeForADistraction;}
	public int GetPeopleServed() {return PeopleWhoHaveBeenServed;}
	public int GetPeopleLeft() {return PeopleWhoHaveLeft;}
	public float GiveMeTheMoney() {return money;}


	public void OhNoSomeoneLeft() {
		PeopleWhoHaveLeft++;
		System.out.println("Oh no, someone left! The total number of people who have left is: " + PeopleWhoHaveLeft);
		if (PeopleWhoHaveLeft==10) {State.setState(handler.getGame().GameOverState);}
	}

	public void createBurger(){
		burger = new Burger(handler.getWidth() - 110, 100, 100, 50);

	}

	public void tick(){

		handler.getWorld().Counters[8].setDistractionAvailable(DistractionAvailable);

		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
			State.setState(handler.getGame().pauseState);
		}


		if (CurrentDistraction==MaxDistraction) {
			DistractionAvailable=true;
			DistractionCountDown--;
			if (DistractionCountDown==0) {
				DistractionCountDown=120;
				MaxDistraction=SpinTheWheel.nextInt(10)*100.0+1000;
				CurrentDistraction=0.0;
				DistractionAvailable=false;
			}
		}
		else {CurrentDistraction++;}



		playerAnim.tick();
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
			//Fattbut is C. I don't know who decided to name it that.
			for(BaseCounter counter: handler.getWorld().Counters){
				if (counter instanceof PlateCounter && counter.isInteractable()){createBurger();}
			}
		}
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_SHIFT)) {speed--;}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {speed++;}

		//THESE ARE FOR TEST PURPOSES ONLY AND SHOULD BE REMOVED UPON SUBMISSION OF THE PROJECT
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_F1)) {DrawBars=!DrawBars;}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_F9)) {CurrentDistraction=MaxDistraction;}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_F10)) {handler.getWorld().makemeappear();}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_F12)) {money=money+10; if(money>=50) {State.setState(handler.getGame().WinState);}}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_F11)) {PeopleWhoHaveLeft++; if(PeopleWhoHaveLeft==10) {State.setState(handler.getGame().GameOverState);}}
		//DO NOT FORGET!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_1)){
			TimeToRing=true;
			CustomerToRing=1;
		}
		else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_2)){
			TimeToRing=true;
			CustomerToRing=2;
		}
		else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_3)){
			TimeToRing=true;
			CustomerToRing=3;
		}
		else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_4)){
			TimeToRing=true;
			CustomerToRing=4;
		}
		else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_5)){
			TimeToRing=true;
			CustomerToRing=5;
		}
		
		if (TimeToRing) {
			TimeToRing=false;
			for(BaseCounter counter: handler.getWorld().Counters) {
				if (counter instanceof PlateCounter && counter.isInteractable()) {ringCustomer(CustomerToRing);}
			}
		}

	}

	private void ringCustomer(int CustomerID) {
		for(Client client: handler.getWorld().clients){
			boolean matched = ((Burger)client.order.food).equals(handler.getCurrentBurger()) & (client.getPosition() == CustomerID);
			if(matched){
				//For this the game needs to act as if we added the percentage
				if ((client.getPatiencePercentage()+.25)>=.50) {money+=client.order.value+(.15*client.order.value);}
				else {money+=client.order.value;}

				money=(float) (Math.round(money*100.0)/100.0);

				PeopleWhoHaveBeenServed++;

				if (money>=50) {State.setState(handler.getGame().WinState);}

				//We can't actually add it because the dialogue needs the original percentage.
				client.PleaseLeave();
				handler.getPlayer().createBurger();
				System.out.println("Total money earned is: " + String.valueOf(money));

				return;
			}
			else {
				if ((client.getPosition() == CustomerID)) {
					client.GotIncorrectFood();
					client.AddPercentageOfPatience(-10);
				}
			}

		}

	}

	public void render(Graphics g) {

		if (TimeForADistraction) {
			//System.out.println("DistractionTriggered");
			DistractionText = handler.getWorld().DialogueBubble(xPos, 645, "Here, have some!", Color.black, Dialogue.font);
			TimeForADistraction=false;
			DistractionAvailable=false;
			MaxDistraction=SpinTheWheel.nextInt(10)*100.0+1000;
			CurrentDistraction=0.0;
			for(Client client: handler.getWorld().clients) {client.resetPatience();}
		}

		DistractionText.setXpos(xPos-25);

		g.setColor(Color.green);
		burger.render(g);

		if (DrawBars) {

			g.setColor(Color.BLACK);
			g.fillRect(5+15, 5+15, 200, 60); //Render the status box
			//g.fillRect(handler.getWidth()-200-5-15, 5+15, 200, 30); //Render the distraction box


			//Money Progress bar
			g.setColor(Color.green);
			g.fillRect(5+15+2,5+15+2,(int) ((money/50)*196),26);

			//People Lost Progress Bar
			g.setColor(Color.red);
			g.fillRect(5+15+2,5+15+2+30,(int)((PeopleWhoHaveLeft/10.0)*196),26);

			//Status Box Text
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 15));
			g.drawString("Money earned: $" + money, 10+15, 25+15);
			g.setFont(new Font("Arial", Font.PLAIN, 15));
			g.drawString("Customers Lost: " + PeopleWhoHaveLeft, 10+15, 25+15+25+5);

			g.setColor(Color.BLACK);
			g.fillRect(handler.getWidth()-100, handler.getHeight()-100, 96, 20); //Render the distraction box but on top of the counter
			if (DistractionAvailable) {
				g.setColor(Color.pink);
				g.fillRect(handler.getWidth()-100+2,handler.getHeight()-100+2,(int) ((DistractionCountDown/120)*92),16);
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 12));
				g.drawString("DISTRACT (E)", handler.getWidth()-100+4, handler.getHeight()-100+15);

			}
			else {
				g.setColor(Color.magenta);
				g.fillRect(handler.getWidth()-100+2,handler.getHeight()-100+2,(int) ((CurrentDistraction/MaxDistraction)*92),16);
				g.setColor(Color.WHITE);
				g.drawString("Charging...", handler.getWidth()-100+4, handler.getHeight()-100+15);

			}
		}

		if(direction=="right") {g.drawImage(playerAnim.getCurrentFrame(), xPos, yPos, width, height, null);}
		else{g.drawImage(playerAnim.getCurrentFrame(), xPos+width, yPos, -width, height, null);}

		//
	}


	public void interact(){
		for(BaseCounter counter: handler.getWorld().Counters){
			if (counter.isInteractable()){
				counter.interact();
				//Since interact only allows you to add burger parts, we have to introduce this check
				//hey at least we're not doing this twice like before.
				if (DistractionAvailable && counter instanceof SpecialCounter) {TimeForADistraction=true;}
			}
		}
	}
	public Burger getBurger(){
		return this.burger;
	}
}
