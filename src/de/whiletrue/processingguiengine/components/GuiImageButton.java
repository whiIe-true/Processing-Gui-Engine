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
				if(this.textShadowColor!=-1) {
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

	//Return the x
	public final int getX(){
		return this.x;
	}

	//Return the y
	public final int getY(){
		return this.y;
	}

	//Return the width
	public final int getWidth(){
		return this.width;
	}

	//Return the textColor
	public final int getTextColor(){
		return this.textColor;
	}

	//Return the textShadowColor
	public final int getTextShadowColor(){
		return this.textShadowColor;
	}

	//Return the hoverOffsetX
	public final int getHoverOffsetX(){
		return this.hoverOffsetX;
	}

	//Return the hoverOffsetY
	public final int getHoverOffsetY(){
		return this.hoverOffsetY;
	}

	//Return the text
	public final String getText(){
		return this.text;
	}

	//Sets x
	public final GuiImageButton setX(int x){
		this.x=x;
		return this;
	}

	//Sets y
	public final GuiImageButton setY(int y){
		this.y=y;
		return this;
	}

	//Sets width
	public final GuiImageButton setWidth(int width){
		this.width=width;
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

	//Sets text
	public final GuiImageButton setText(String text){
		this.text=text;
		return this;
	}
	
}
