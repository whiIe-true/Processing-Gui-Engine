package de.whiletrue.processingguiengine.components;

import java.awt.Color;
import java.util.AbstractMap;
import java.util.Map.Entry;
import java.util.function.Function;

import de.whiletrue.processingguiengine.GuiComponent;
import de.whiletrue.processingguiengine.RenderObject;
import processing.core.PApplet;

public class GuiButton extends GuiComponent{

	private int x,y,width,height,
	defaultColor,hoverColor,clickColor;
	private Function<Integer, String> onclick;
	private String text;
	
	public GuiButton(int x,int y,int width,int height,Function<Integer,String> onclick) {
		this(x,y,width,height,onclick,0x717171,0xA00000,0x0F50000);
	}
	
	public GuiButton(int x,int y,int width,int height,Function<Integer,String> onclick,int defaultBackground,int hoverColor,int clickColor) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.onclick = onclick;
		this.defaultColor = new Color(defaultBackground).getRGB();
		this.hoverColor = new Color(hoverColor).getRGB();
		this.clickColor = new Color(clickColor).getRGB();
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
		//Gets what color the button should be rendered
		int col = hover&&!usedBefore?(app.mousePressed?this.clickColor:this.hoverColor):this.defaultColor;
		
		//Returns a new entry with the new used boolean and the render object
		return new AbstractMap.SimpleEntry<Boolean,RenderObject>(hover, ()->{
			
			//Renders the button
			app.fill(new Color(col).getRGB());
			app.rect(this.x, this.y, this.width, this.height);
			
			//Renders the text
			app.fill(255);
			app.textSize(Math.min(this.width, this.height)/2);
			app.textAlign(PApplet.CENTER,PApplet.CENTER);
			app.text(this.text, this.x+this.width/2, this.y+this.height/2);
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
	public final int getX() {
		return this.x;
	}

	//Return the y
	public final int getY() {
		return this.y;
	}

	//Return the width
	public final int getWidth() {
		return this.width;
	}

	//Return the height
	public final int getHeight() {
		return this.height;
	}

	//Return the defaultColor
	public final int getDefaultColor() {
		return this.defaultColor;
	}

	//Return the hoverColor
	public final int getHoverColor() {
		return this.hoverColor;
	}

	//Return the clickColor
	public final int getClickColor() {
		return this.clickColor;
	}

	//Return the text
	public final String getText() {
		return this.text;
	}

	//Sets x
	public GuiButton setX(int x) {
		this.x = x;
		return this;
	}

	//Sets y
	public GuiButton setY(int y) {
		this.y = y;
		return this;
	}

	//Sets width
	public GuiButton setWidth(int width) {
		this.width = width;
		return this;
	}

	//Sets height
	public GuiButton setHeight(int height) {
		this.height = height;
		return this;
	}

	//Sets defaultColor
	public GuiButton setDefaultColor(int defaultColor) {
		this.defaultColor = defaultColor;
		return this;
	}

	//Sets hoverColor
	public GuiButton setHoverColor(int hoverColor) {
		this.hoverColor = hoverColor;
		return this;
	}

	//Sets clickColor
	public GuiButton setClickColor(int clickColor) {
		this.clickColor = clickColor;
		return this;
	}

	//Sets text
	public GuiButton setText(String text) {
		this.text = text;
		return this;
	}
}
