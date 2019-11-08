package Game.World;

import Game.Entities.Dynamic.Client;
import Game.Entities.Dynamic.FloatText;
import Game.Entities.Dynamic.Player;
import Game.Entities.Static.*;
import Main.Handler;
import Resources.Images;

import java.awt.*;
import java.util.ArrayList;


public class Restaurant_1 extends BaseWorld {
	private int count=0;
	private int capacity = 5;
	public Restaurant_1(BaseCounter[] Counters, Handler handler) {
		super(Images.floor,Counters, handler, new Player(null,0,650,handler));

	}

	public void tick(){
		count++;
		if(count >= 5*60 && !isFull()){
			count = 0;
			for(Client client: this.clients){
				client.move();
			}
			this.generateClient();
		}else if(count >= 5*60 && isFull()){
			count=0;
			boolean left=false;
			Client toLeave = null;
			ArrayList<Client> toMove = new ArrayList<>();
			for (Client client : this.clients) {
				if (client.isLeaving && !left) {
					toLeave = client;
					left=true;
				}else if (left) {
					toMove.add(client);
				}
			}
			if(left){
				this.clients.remove(toLeave);
				for (Client client : toMove) {
					client.move();
				}
				this.generateClient();
			}
		}


		for(Client client: this.clients){
			client.tick();
		}
		for(BaseCounter counter: Counters){
			counter.tick();
		}
		handler.getPlayer().tick();
	}

	public boolean isFull(){
		return this.clients.size() >=capacity;
	}
	public void render(Graphics g){
		
		g.drawImage(Background,0,0,handler.getWidth(), handler.getHeight(),null);
		g.drawImage(Images.welcome,5,150,43,82,null);

		g.drawImage(Images.ModernMisc[0],(11*handler.getWidth())/16,90,192,94,null);
		g.drawImage(Images.ModernMisc[1],(7*handler.getWidth())/16,20,42*3,29*3,null);


		g.drawImage(Images.kitchenChairTable[0],handler.getWidth()/3,90+150,96,96,null);
		g.drawImage(Images.ModernChair[4],handler.getWidth()/3+96,140+110,52,62,null);
		g.drawImage(Images.ModernChair[3],handler.getWidth()/3-52,140+110,52,62,null);

		g.drawImage(Images.kitchenChairTable[0],handler.getWidth()/3+handler.getWidth()/6,190+150,96,96,null);
		g.drawImage(Images.ModernChair[4],handler.getWidth()/3+handler.getWidth()/6+96,240+110,52,62,null);
		g.drawImage(Images.ModernChair[3],handler.getWidth()/3+handler.getWidth()/6-52,240+110,52,62,null);

		g.drawImage(Images.kitchenChairTable[0],handler.getWidth()/3+handler.getWidth()/3,90+150,96,96,null);
		g.drawImage(Images.ModernChair[4],handler.getWidth()/3+handler.getWidth()/3+96,140+110,52,62,null);
		g.drawImage(Images.ModernChair[3],handler.getWidth()/3+handler.getWidth()/3-52,140+110,52,62,null);

		g.drawImage(Images.kitchenChairTable[0],handler.getWidth()/3+handler.getWidth()/3,292+150,96,96,null);
		g.drawImage(Images.ModernChair[4],handler.getWidth()/3+handler.getWidth()/3+96,312+150,52,62,null);
		g.drawImage(Images.ModernChair[3],handler.getWidth()/3+handler.getWidth()/3-52,312+150,52,62,null);

		g.drawImage(Images.kitchenChairTable[0],handler.getWidth()/3,292+150,96,96,null);
		g.drawImage(Images.ModernChair[4],handler.getWidth()/3+96,312+150,52,62,null);
		g.drawImage(Images.ModernChair[3],handler.getWidth()/3-52,312+150,52,62,null);

		for(Client client: clients){client.render(g);}
		for(BaseCounter counter: Counters){counter.render(g);}
		
		handler.getPlayer().render(g);

		try {for(FloatText TextThatsFloating: ThosePeskyTexts) {
			if (!TextThatsFloating.isTrash()){TextThatsFloating.render(g);}}} //if it isn't trash, render it
		catch (Exception e) {} //This is here for when there's concurrent access to avoid the game just FLIPPING THE HECC OUT.

	}
}
