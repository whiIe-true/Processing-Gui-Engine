package de.whiletrue.processingguiengine.components;

import java.util.AbstractMap;
import java.util.Map.Entry;
import java.util.function.Function;

import de.whiletrue.processingguiengine.GuiComponent;
import de.whiletrue.processingguiengine.RenderObject;
import processing.core.PApplet;
import processing.core.PImage;

public class GuiImageButton extends GuiComponent{

	//Image to display
	private PImage image;
	//Position
	private int x,y,width,height,
	//Styles
	textColor,textShadowColor,hoverOffsetX,hoverOffsetY;
	private boolean shadow;
	//Events
	private Function<Integer, String> onclick;
	//Text on the button
	private String text;
	
	public GuiImageButton(PImage image,int x,int y,int width,int height,Function<Integer,String> onclick) {
		this(image,x,y,width,height,onclick,0,-10,0xffFFFFFF,0xffACACAC);
	}
	
	public GuiImageButton(PImage image,int x,int y,int width,int height,Function<Integer,String> onclick,
			int hoverOffsetX,int hoverOffsetY,int textColor,int textShadowColor) {
		this.x = x;
		this.y = y;
		this.image=image;
		this.width = width;
		this.height = height;
		this.onclick = onclick;
		this.textColor=textColor;
		this.textShadowColor=textShadowColor;
		this.shadow=textShadowColor!=-1;
		this.hoverOffsetX=hoverOffsetX;
		this.hoverOffsetY=hoverOffsetY;
		this.text = onclick.apply(-1);
	}

	@Override
	public boolean handleMousePressed(PApplet app) {
		//Checks if the button gots clicked
		if(this.isHovered(app.mouseX, app.mouseY)) {
			//Handles the onclick
			this.text = this.onclick.apply(app.mouseButton);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean handleMouseReleased(PApplet app) {
		return false;
	}

	@Override
	public boolean handleKeyPressed(PApplet app) {
		return false;
	}

	@Override
	public boolean handleKeyReleased(PApplet app) {
		return false;
	}

	@Override
	public Entry<Boolean, RenderObject> handleRender(PApplet app, boolean usedBefore) {
		//Gets if the button is hovered
		boolean hover = this.isHovered(app.mouseX, app.mouseY);
		
		//Checks what offset to use
		int offX = hover&&!usedBefore?this.hoverOffsetX:0;
		int offY = hover&&!usedBefore?this.hoverOffsetY:0;
		
		//Returns a new entry with the new used boolean and the render object
		return new AbstractMap.SimpleEntry<Boolean,RenderObject>(hover, ()->{
			
			//Renders the image
			app.imageMode(PApplet.CORNER);
			app.image(this.image,this.x+offX,this.y+offY,this.width,this.height);
			
			//Checks if text should be rendered
			if(this.text!=null) {				
				//Renders the text
				app.textSize(this.width*.3f);
				app.textAlign(PApplet.CENTER,PApplet.TOP);
				//Checks if the text should be rendered as a shadow
				if(this.shadow) {
					app.fill(this.textShadowColor);
					app.text(this.text, this.x+this.width/2-2, this.y+this.height+4);
				}
				app.fill(this.textColor);
				app.text(this.text, this.x+this.width/2, this.y+this.height+2);
			}
		});
	}

	/*
	 * Returns if the button gets hovered
	 * */
	private boolean isHovered(int mouseX,int mouseY) {
		return mouseX > this.x &&
				mouseX < this.x+this.width &&
				mouseY > this.y &&
				mouseY < this.y+this.height;
	}
	
}
