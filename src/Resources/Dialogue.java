package Resources;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class Dialogue {

	public static Font font= new Font("Arial",Font.BOLD,15);
	public static Color color=Color.WHITE; 

	private static Random randomizer = new Random();
	
	private static String[] AngryDialog= {
			"I'm going to miss my flight!",
			"Come on already!",
			"What's taking you so long?!",
			"Ugh!",
			"I don't have all day!",
			"Move it!",
			"Hurry for god's sake!",
			"This can't be so hard!"
	};

	private static String[] AnnoyedDialog= {
			"Please hurry",
			"Can I please have my order",
			"Could you please hurry",
			"Please don't make me wait",
			"I need my food please",
			"I don't have too much time",
			"Please!",
			"Excuse me",
			"Ahem, my food?"
	};

	private static String[] GreetingDialog= {
			"Hello!",
			"Good evening",
			"Oh, just in time!",
			"I'm very hungry!",
			"Could you make me this?",
			"Bonsoir monseiur!",
			"Hey",
	};

	private static String[] IncorrectFoodDialog= {
			"Uh, no.",
			"Excuse me?",
			"This isn't right",
			"This isn't what I ordered",
			"Nope",
			"No! I don't want that!",
			"Send this back please",
	};
	
	private static String[] LeaveWithoutBurgerDialog= {
			"Damnit! I got to go.",
			"That's it!",
			"I've had enough!",
			"I'll go somewhere else",
			"Good God!",
			"Thanks for wasting my time!"
	};
	
	private static String[] AngryGotBurger= {
			"Finally!",
			"Took you long enough.",
			"Delicious, finally some food.",
			"Good god, finally",
			"This better be good."
	};
	
	private static String[] AnnoyedGotBurger= {
			"Thank you",
			"Ugh, Here keep the change.",
			"Finally",
			"Finally, thanks.",
			"Ugh, thanks"
	};

	private static String[] HappyGotBurger= {
			"Thank you so much!",
			"Delicious!",
			"Thanks for the food!",
			"Wow, that was fast!",
			"Thanks!"
	};
	
	public static String getAngry() {
		//this try catch is just CYA but JIC its nice to have it.
		try {return AngryDialog[randomizer.nextInt(AngryDialog.length-1)];} 
		catch (Exception e) {return "";}}
	
	public static String getAnnoyed() {
		try {return AnnoyedDialog[randomizer.nextInt(AnnoyedDialog.length-1)];} 
		catch (Exception e) {return "";}}
	
	public static String getGreeting() {
		try {return GreetingDialog[randomizer.nextInt(GreetingDialog.length-1)];} 
		catch (Exception e) {return "";}}
	
	public static String getIncorrect() {
		try {return IncorrectFoodDialog[randomizer.nextInt(IncorrectFoodDialog.length-1)];} 
		catch (Exception e) {return "";}}
	
	public static String getWithoutBurger() {
		try {return LeaveWithoutBurgerDialog[randomizer.nextInt(LeaveWithoutBurgerDialog.length-1)];} 
		catch (Exception e) {return "";}}
	
	public static String getAngryBurger() {
		try {return AngryGotBurger[randomizer.nextInt(AngryGotBurger.length-1)];} 
		catch (Exception e) {return "";}}
	
	public static String getAnnoyedBurger() {
		try {return AnnoyedGotBurger[randomizer.nextInt(AnnoyedGotBurger.length-1)];} 
		catch (Exception e) {return "";}}
	
	public static String getHappyBurger() {
		try {return HappyGotBurger[randomizer.nextInt(HappyGotBurger.length-1)];} 
		catch (Exception e) {return "";}}
	


}
