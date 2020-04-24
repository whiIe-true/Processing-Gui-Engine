package de.whiletrue.processingguiengine.components;

import java.util.AbstractMap;
import java.util.Map.Entry;

import de.whiletrue.processingguiengine.RenderObject;
import de.whiletrue.processingguiengine.components.GuiButton.ButtonClickEvent;
import de.whiletrue.processingguiengine.components.defaults.SizeComponent;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class GuiImageButton extends SizeComponent{

	//Default values
	private static int DEF_textColor=0xFFFFFF,DEF_textShadowColor=0xACACAC,DEF_hoverOffsetX=0x0,DEF_hoverOffsetY=-0xA;
	private static PFont DEF_font=new PFont(PFont.findFont("Arial"),true);

	//Image to display
	private PImage image;

	//Events
	private ButtonClickEvent onClick;

	//Text beneath the button
	private String text;

	//Styles
	private int textColor,textShadowColor,hoverOffsetX,hoverOffsetY;
	private PFont font;

	public GuiImageButton(PImage image,float x,float y,float width,float height,ButtonClickEvent onClick){
		this(image,x,y,width,height,onClick,DEF_textColor,DEF_textShadowColor,DEF_hoverOffsetX,DEF_hoverOffsetY,DEF_font);
	}

	public GuiImageButton(PImage image,float x,float y,float width,float height,ButtonClickEvent onClick,
			int textColor,int textShadowColor,int hoverOffsetX,int hoverOffsetY,PFont font){
		super(x,y,width,height);
		this.image=image;
		this.onClick=onClick;
		this.textColor=textColor;
		this.textShadowColor=textShadowColor;
		this.hoverOffsetX=hoverOffsetX;
		this.hoverOffsetY=hoverOffsetY;
		this.font=font;
		
		//Sets the text
		this.text=onClick.execute(-1);
	}

	/*
	 * Sets the defaults
	 * */
	public static void setDefaults(int textColor,int textShadowColor,int hoverOffsetX,int hoverOffsetY,PFont font) {
		DEF_hoverOffsetX=hoverOffsetX;
		DEF_hoverOffsetY=hoverOffsetY;
		DEF_textColor=textColor;
		DEF_textShadowColor=textShadowColor;
		if(font!=null)
			DEF_font=font;
	}

	@Override
	public Entry<Boolean,RenderObject> handleRender(PApplet app,boolean usedBefore){
		//Gets if the button is hovered
		boolean hover = this.isHovered(app.mouseX, app.mouseY);
		
		//Checks what offset to use
		int offX = hover&&!usedBefore?this.hoverOffsetX:0;
		int offY = hover&&!usedBefore?this.hoverOffsetY:0;
		
		//Returns a new entry with the new used boolean and the render object
		return new AbstractMap.SimpleEntry<Boolean,RenderObject>(hover, ()->{
			
			//Gets the coordinates
			float[] coords = this.getRealCoordinates();
			
			//Renders the image
			app.imageMode(PApplet.CORNER);
			app.tint(0xff,255*this.getOpacity());
			app.image(this.image,coords[0]+offX,coords[1]+offY,coords[2],coords[3]);
			
			//Checks if text should be rendered
			if(this.text!=null) {	
				app.textFont(this.font);
				//Renders the text
				app.textSize(coords[2]*.3f);
				app.textAlign(PApplet.CENTER,PApplet.TOP);
				//Checks if the text should be rendered as a shadow
				if(this.textShadowColor!=-1) {
					app.fill(this.applyOpacity(this.textShadowColor));
					app.text(this.text, coords[0]+coords[2]/2-2, coords[1]+coords[3]+4);
				}
				app.fill(this.applyOpacity(this.textColor));
				app.text(this.text, coords[0]+coords[2]/2, coords[1]+coords[3]+2);
			}
		});
	}

	@Override
	public boolean handleMousePressed(PApplet app){
		//Checks if the button got clicked
		if(this.isHovered(app.mouseX, app.mouseY)) {
			//Handles the onclick
			this.text = this.onClick.execute(app.mouseButton);
			return true;
		}
		return false;
	}

	@Override
	public boolean handleMouseReleased(PApplet app){
		return false;
	}

	@Override
	public void handleAfterMousePressed(PApplet app){
	}

	@Override
	public boolean handleKeyPressed(PApplet app){
		return false;
	}

	@Override
	public boolean handleKeyReleased(PApplet app){
		return false;
	}

	//Sets image
	public final GuiImageButton setImage(PImage image){
		this.image=image;
		return this;
	}

	//Sets onClick
	public final GuiImageButton setClickEvent(ButtonClickEvent onClick){
		this.onClick=onClick;
		return this;
	}

	//Sets text
	public final GuiImageButton setText(String text){
		this.text=text;
		return this;
	}

	//Sets textColor
	public final GuiImageButton setTextColor(int textColor){
		this.textColor=textColor;
		return this;
	}

	//Sets textShadowColor
	public final GuiImageButton setTextShadowColor(int textShadowColor){
		this.textShadowColor=textShadowColor;
		return this;
	}

	//Sets hoverOffsetX
	public final GuiImageButton setHoverOffsetX(int hoverOffsetX){
		this.hoverOffsetX=hoverOffsetX;
		return this;
	}

	//Sets hoverOffsetY
	public final GuiImageButton setHoverOffsetY(int hoverOffsetY){
		this.hoverOffsetY=hoverOffsetY;
		return this;
	}
	
	//Sets font
	public final GuiImageButton setFont(PFont font){
		this.font=font;
		return this;
	}

}
