package Game.World;

import Game.Entities.Dynamic.Client;
import Game.Entities.Dynamic.FloatText;
import Game.Entities.Dynamic.Player;
import Game.Entities.Static.BaseCounter;
import Main.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class BaseWorld {

    public BufferedImage Background;

    public BaseCounter Counters[];

    public Handler handler;

    public ArrayList<Client> clients = new ArrayList<>();
    public ArrayList<FloatText> ThosePeskyTexts = new ArrayList<>();

    public BaseWorld(BufferedImage Background, BaseCounter Counters[], Handler handler, Player player){
        this.Background = Background;
        this.Counters = Counters;
        this.handler=handler;
        handler.setWorld(this);
        handler.setPlayer(player);
    }
    
    public void makemeappear() {
    	Random d = new Random();
    	FloatText floating = new FloatText((int)(d.nextDouble()*600)+100,(int)(d.nextDouble()*600)+100,"UNE TEST",2,Color.red,new Font("Arial",Font.BOLD,25));
    	this.ThosePeskyTexts.add(floating);
    }
    
    public FloatText DialogueBubble(int x, int y, String text,Color color, Font font) {
    	FloatText floating = new FloatText(x,y,text,3,color,font);
    	this.ThosePeskyTexts.add(floating);
    	return floating;
    }
    
    public Client generateClient(){
        Client client =  new Client(10,485,handler,1);
        this.clients.add(client);
        return client;
    }

    public void tick(){

    }

    public void render(Graphics g){

    }
}
