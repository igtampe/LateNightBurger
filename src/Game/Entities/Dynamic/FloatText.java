package Game.Entities.Dynamic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class FloatText  {

	private int xpos;
	private int ypos;
	private String text;
	private int timeout;
	private Color bgcolor;
	private Color fgcolor;
	private Font font;
	private boolean isBackgrounded;
	private int backgroundxdim;
	private int backgroundydim;
	private boolean isFadingOut;
	private int FadeOutTime;
	private int MaxFadeOutTime;
	
	public int getXpos() {return xpos;}
	public void setXpos(int xpos) {this.xpos = xpos;}
	public int getYpos() {return ypos;}
	public void setYpos(int ypos) {this.ypos = ypos;}
	public String getText() {return text;}
	public void setText(String text) {this.text = text;}
	public int getTimeout() {return timeout;}
	public void setTimeout(int timeout) {this.timeout = timeout;}
	public Color getBgcolor() {return bgcolor;}
	public void setBgcolor(Color bgcolor) {this.bgcolor = bgcolor;}
	public Color getFgcolor() {return fgcolor;}
	public void setFgcolor(Color fgcolor) {this.fgcolor = fgcolor;}
	public Font getFont() {return font;}
	public void setFont(Font font) {this.font = font;}
	public boolean isBackgrounded() {return isBackgrounded;}
	public void setBackgrounded(boolean isBackgrounded) {this.isBackgrounded = isBackgrounded;}
	public int getBackgroundxdim() {return backgroundxdim;}
	public void setBackgroundxdim(int backgroundxdim) {this.backgroundxdim = backgroundxdim;}
	public int getBackgroundydim() {return backgroundydim;}
	public void setBackgroundydim(int backgroundydim) {this.backgroundydim = backgroundydim;}
	public boolean isFadingOut() {return isFadingOut;}
	public void setFadingOut(boolean isFadingOut) {this.isFadingOut = isFadingOut;}
	public int getFadeOutTime() {return FadeOutTime;}
	public void setFadeOutTime(int fadeOutTime) {FadeOutTime = fadeOutTime;}
	public int getOpacity() {return Opacity;}
	public void setOpacity(int opacity) {Opacity = opacity;}
	public boolean isTrash() {return PleaseThrowMeAway;}


	private int Opacity;
	private int InitialOpacity;
	private boolean PleaseThrowMeAway;

	/**
	 * JESUS I'm probably never going to use all of this but hey maybe someone else eventually will :shrug:
	 * @param xPos
	 * @param yPos
	 * @param Text
	 * @param TimeOut
	 * @param FadeOutTime
	 * @param InitialOpacity
	 * @param fgColor
	 * @param bgColor
	 * @param XDim
	 * @param YDim
	 * @param theFont
	 */
	public FloatText(int xPos, int yPos, String Text, int TimeOut, int FadeOutTime, int InitialOpacity, Color fgColor, Color bgColor, int XDim, int YDim, Font theFont) {
		this.xpos=xPos;
		this.ypos=yPos;
		this.text=Text;
		this.timeout=TimeOut*60;
		
		if (InitialOpacity>255) {InitialOpacity=255;}
		if (InitialOpacity<0) {InitialOpacity=0;}
		
		this.Opacity=InitialOpacity;
		this.InitialOpacity = InitialOpacity;
		
		this.FadeOutTime=FadeOutTime*60;
		this.MaxFadeOutTime=FadeOutTime*60;

		this.bgcolor=bgColor;
		this.isBackgrounded=true;
		this.backgroundxdim=XDim;
		this.backgroundydim=YDim;

		this.fgcolor=fgColor;
		this.font=theFont;

	}
	
	/**
	 * wow ok you want the whole thing don't you
	 * @param xPos xposition
	 * @param yPos y position
	 * @param Text text of the text
	 * @param TimeOut time (In Seconds) before fading out
	 * @param FadeOutTime time (in seconds) for the text to fade out
	 * @param fgColor color of the text
	 * @param bgColor background color
	 * @param XDim Width of the background
	 * @param YDim length of the background
	 * @param theFont Font for the text
	 */
	public FloatText(int xPos, int yPos, String Text, int TimeOut, int FadeOutTime, Color fgColor, Color bgColor, int XDim, int YDim, Font theFont) {
		this.xpos=xPos;
		this.ypos=yPos;
		this.text=Text;
		this.timeout=TimeOut*60;

		this.FadeOutTime=FadeOutTime*60;
		this.MaxFadeOutTime=FadeOutTime*60;

		this.Opacity=255;
		this.InitialOpacity = 255;
		
		this.bgcolor=bgColor;
		this.isBackgrounded=true;
		this.backgroundxdim=XDim;
		this.backgroundydim=YDim;

		this.fgcolor=fgColor;
		this.font=theFont;

	}
	/**
	 * Creates a floating bit of text that fades out upon reaching timeout
	 * @param xPos
	 * @param yPos
	 * @param Text
	 * @param TimeOut
	 * @param FadeOutTime
	 * @param fgColor
	 * @param theFont
	 */
	public FloatText(int xPos, int yPos, String Text, int TimeOut, int FadeOutTime, Color fgColor, Font theFont) {
		this.xpos=xPos;
		this.ypos=yPos;
		this.text=Text;
		this.timeout=TimeOut*60;
		
		this.FadeOutTime=FadeOutTime*60;
		this.MaxFadeOutTime=FadeOutTime*60;

		this.Opacity=255;
		this.InitialOpacity = 255;
		
		this.bgcolor=Color.BLACK;
		this.isBackgrounded=false;
		this.backgroundxdim=0;
		this.backgroundydim=0;

		this.fgcolor=fgColor;
		this.font=theFont;		
	}

	/**
	 * kk arial isn't good enough for you got it.
	 * @param xPos
	 * @param yPos
	 * @param Text
	 * @param TimeOut
	 * @param fgColor
	 * @param theFont
	 */
	public FloatText(int xPos, int yPos, String Text, int TimeOut, Color fgColor, Font theFont) {
		this.xpos=xPos;
		this.ypos=yPos;
		this.text=Text;
		this.timeout=TimeOut*60;;

		this.FadeOutTime=2*60;
		this.MaxFadeOutTime=2*60;

		this.Opacity=255;
		this.InitialOpacity = 255;
		
		this.bgcolor=Color.BLACK;
		this.isBackgrounded=false;
		this.backgroundxdim=0;
		this.backgroundydim=0;

		this.fgcolor=fgColor;
		this.font=theFont;		
	}
	
	/**
	 * ok now we're getting somewhere
	 * @param xPos
	 * @param yPos
	 * @param Text
	 * @param TimeOut
	 * @param fgColor
	 */
	public FloatText(int xPos, int yPos, String Text, int TimeOut, Color fgColor){
		this.xpos=xPos;
		this.ypos=yPos;
		this.text=Text;
		this.timeout=TimeOut*60;

		this.FadeOutTime=2*60;
		this.MaxFadeOutTime=2*60;

		this.Opacity=255;
		this.InitialOpacity = 255;
		
		this.bgcolor=Color.BLACK;
		this.isBackgrounded=false;
		this.backgroundxdim=0;
		this.backgroundydim=0;

		this.fgcolor=fgColor;
		this.font=new Font("Arial",Font.PLAIN,11);
	}

	/**
	 * The simplest constructor
	 * @param xPos
	 * @param yPos
	 * @param Text
	 * @param TimeOut
	 */
	public FloatText(int xPos, int yPos, String Text, int TimeOut) {
		this.xpos=xPos;
		this.ypos=yPos;
		this.text=Text;
		this.timeout=TimeOut*60;

		this.FadeOutTime=2*60;
		this.MaxFadeOutTime=2*60;

		this.Opacity=255;
		this.InitialOpacity = 255;
		
		this.bgcolor=Color.BLACK;
		this.isBackgrounded=false;
		this.backgroundxdim=0;
		this.backgroundydim=0;

		this.fgcolor=Color.white;
		this.font=new Font("Arial",Font.PLAIN,11);
	}

	/**
	 * I lied, this is a dummy creator. it takes NO PARAMETERS! HAHAHAHAHAHAH!
	 */
	public FloatText() {
		this.xpos=0;
		this.ypos=0;
		this.text="DUMMY";
		this.timeout=0;

		this.FadeOutTime=0;
		this.MaxFadeOutTime=1;

		this.Opacity=0;
		this.InitialOpacity = 255;
		
		this.bgcolor=Color.BLACK;
		this.isBackgrounded=false;
		this.backgroundxdim=0;
		this.backgroundydim=0;

		this.fgcolor=Color.white;
		this.font=new Font("Arial",Font.PLAIN,11);
	}
	

	
	public void render(Graphics g) {
		
		if (FadeOutTime==0) {
			PleaseThrowMeAway=true;
			return;
		}
		
		Opacity=(int) (InitialOpacity*((FadeOutTime*1.0)/(MaxFadeOutTime*1.0)));
		if (isBackgrounded) {
			g.setColor(new Color(bgcolor.getRed(),bgcolor.getGreen(),bgcolor.getBlue(),Opacity));
			g.drawRect(xpos, ypos, backgroundxdim, backgroundydim);	
		}
		g.setFont(font);
		g.setColor(new Color(fgcolor.getRed(),fgcolor.getGreen(),fgcolor.getBlue(),Opacity));
		g.drawString(text, xpos, ypos);
		
		if (timeout==0) {
			if (isFadingOut) {FadeOutTime--;}
			else {isFadingOut=true;}
		}
		else {timeout--;}


	}




}
