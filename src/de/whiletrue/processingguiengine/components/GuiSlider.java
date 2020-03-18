package de.whiletrue.processingguiengine.components;

import java.util.AbstractMap;
import java.util.Map.Entry;

import de.whiletrue.processingguiengine.GuiComponent;
import de.whiletrue.processingguiengine.RenderObject;
import processing.core.PApplet;

public class GuiSlider extends GuiComponent{

	//Default style values
	private static int defaultFilledColor=0xfff00000,defaultEmptyColor=0xff717171,defaultOutlineColor=0,
			defaultTextColor=255,defaultOutlineStrength=5;
	
	//Position of the slider
	private int x,y,width,height;
	//Min an max values of the slider
	private int min,max;
	//Styles
	private int filledColor,emptyColor,outlineColor,textColor,outlineStrength;
	//Percentual value of the slider
	private double percState = 0;
	//Sets if the slider is dragged
	private boolean dragged = false;
	//Callback gets executes everytime the slider changes
	private ChangeListener onchange;
	//The current display value
	private String text;
	
	public GuiSlider(float x,float y,float width,float height,int minValue,int maxValue,int currentValue,ChangeListener onchange,String defaultText) {
		this(x,y,width,height,minValue,maxValue,currentValue,onchange,defaultText,defaultFilledColor,defaultEmptyColor,defaultOutlineColor,defaultTextColor,defaultOutlineStrength);
	}
	
	public GuiSlider(float x,float y,float width,float height,int minValue,int maxValue,int currentValue,ChangeListener onchange,String defaultText,
			int filledColor,int emptyColor,int outlineColor,int textColor,int outlineStrength) {
		this.x = (int)x;
		this.y = (int)y;
		this.width = (int)width;
		this.height = (int)height;
		this.min = minValue;
		this.max = maxValue;
		this.onchange = onchange;
		this.text = defaultText;
		this.setState(currentValue);
		this.filledColor=filledColor;
		this.emptyColor=emptyColor;
		this.outlineColor=outlineColor;
		this.textColor=textColor;
		this.outlineStrength=outlineStrength;
	}
	
	/*
	 * Sets the default style values for every new class instance
	 */
	public static void setDefaults(int filledColor,int emptyColor,int outlineColor,
			int textColor,int outlineStrength) {
		defaultFilledColor=filledColor;
		defaultEmptyColor=emptyColor;
		defaultOutlineColor=outlineColor;
		defaultTextColor=textColor;
		defaultOutlineStrength=outlineStrength;
	}
	
	@Override
	public boolean handleMousePressed(PApplet app) {
		//Sets the slider dragged, depending if it was hovered
		this.dragged = this.isHovered(app.mouseX, app.mouseY);
		return this.dragged;
	}

	@Override
	public boolean handleMouseReleased(PApplet app) {
		this.dragged = false;
		return false;
	}
	
	@Override
	public void handleAfterMousePressed(PApplet app){}

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
		//Shorts the variables for the mouse
		int mx = app.mouseX,my = app.mouseY;
		
		//Gets if the slider is hovered by the users mouse
		boolean hovered = this.isHovered(mx, my);
		
		//Checks if the slider gets slider
		change:if(this.dragged) {
		
			//Calculates the new state of the slider
			double state = ((double)(mx-this.x))/(double)this.width;

			//Ensures that the state is not higher or lower that the min or max
			state = Math.min(state, 1);
			state = Math.max(state, 0);
			
			//Checks if the new state is equal to the old state
			if(this.percState==state)
				break change;
			
			//Updates the state
			this.percState = state;
			
			//Calculates the value
			int value = (int) ((this.max-this.min)*this.percState+this.min);
			
			//Executes the change callback and updates the text
			this.text = this.onchange.execute(this.percState,value);
		}
		
		//Shorts the variable for the fille width
		int wid = (int) (this.width*this.percState);
		
		//Returns if the slider is hovered and the renderer
		return new AbstractMap.SimpleEntry<Boolean,RenderObject>(hovered,()->{
			//Renders the outline
			app.noFill();
			app.stroke(this.outlineColor);
			app.strokeWeight(this.outlineStrength);
			app.rect(this.x-1, this.y-1, this.width+1, this.height+1);
			
			//Renders the slider
			app.noStroke();
			app.fill(this.filledColor);
			app.rect(this.x, this.y,(float)wid, this.height);
			app.fill(this.emptyColor);
			app.rect(this.x+wid, this.y, this.width-wid, this.height);
			
			//Renders the slider text
			app.fill(this.textColor);
			app.textAlign(PApplet.CENTER,PApplet.CENTER);
			app.textSize(Math.min(this.width, this.height)/2);
			app.text(this.text,this.x+this.width/2, this.y+this.height/2);
		});
	}
	
	/*
	 * Returns if the slider gets hovered
	 * */
	private boolean isHovered(int mouseX,int mouseY) {
		return mouseX > this.x &&
				mouseX < this.x+this.width &&
				mouseY > this.y &&
				mouseY < this.y+this.height;
	}
	
	/*
	 * Sets the state of the slider
	 * */
	public void setState(int currentValue) {
		//Checks if current value is higher than the max
		if(currentValue>this.max)
			currentValue=this.max;
		
		//Checks if the current is lower than the min
		if(currentValue < this.min)
			currentValue = this.min;
		
		//Calculates the percentual value of the slider
		this.percState = ((double)(currentValue-this.min))/((double)(this.max-this.min));
	}	

	//Listener that executes everytime the sliders value changes
	@FunctionalInterface
	public interface ChangeListener{
		/**
		 * @param percentualState The percentual state of the slider
		 * @param value The current exact value of the slider
		 * */
		public String execute(double percentualState,int value);
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

	//Return the min
	public final int getMin() {
		return this.min;
	}

	//Return the max
	public final int getMax() {
		return this.max;
	}

	//Return the filledColor
	public final int getFilledColor() {
		return this.filledColor;
	}

	//Return the emptyColor
	public final int getEmptyColor() {
		return this.emptyColor;
	}

	//Return the outlineColor
	public final int getOutlineColor() {
		return this.outlineColor;
	}

	//Return the percState
	public final double getPercState() {
		return this.percState;
	}

	//Return the dragged
	public final boolean isDragged() {
		return this.dragged;
	}

	//Return the text
	public final String getText() {
		return this.text;
	}

	//Sets x
	public GuiSlider setX(int x) {
		this.x = x;
		return this;
	}

	//Sets y
	public GuiSlider setY(int y) {
		this.y = y;
		return this;
	}

	//Sets width
	public GuiSlider setWidth(int width) {
		this.width = width;
		return this;
	}

	//Sets height
	public GuiSlider setHeight(int height) {
		this.height = height;
		return this;
	}

	//Sets min
	public GuiSlider setMin(int min) {
		this.min = min;
		return this;
	}

	//Sets max
	public GuiSlider setMax(int max) {
		this.max = max;
		return this;
	}

	//Sets filledColor
	public GuiSlider setFilledColor(int filledColor) {
		this.filledColor = filledColor;
		return this;
	}

	//Sets emptyColor
	public GuiSlider setEmptyColor(int emptyColor) {
		this.emptyColor = emptyColor;
		return this;
	}

	//Sets outlineColor
	public GuiSlider setOutlineColor(int outlineColor) {
		this.outlineColor = outlineColor;
		return this;
	}

	//Return the textColor
	public final int getTextColor(){
		return this.textColor;
	}

	//Return the outlineStrength
	public final int getOutlineStrength(){
		return this.outlineStrength;
	}

	//Sets textColor
	public final GuiSlider setTextColor(int textColor){
		this.textColor=textColor;
		return this;
	}

	//Sets outlineStrength
	public final GuiSlider setOutlineStrength(int outlineStrength){
		this.outlineStrength=outlineStrength;
		return this;
	}

	//Sets percState
	public GuiSlider setPercState(double percState) {
		this.percState = percState;
		return this;
	}

	//Sets dragged
	public GuiSlider setDragged(boolean dragged) {
		this.dragged = dragged;
		return this;
	}

	//Sets text
	public GuiSlider setText(String text) {
		this.text = text;
		return this;
	}
	
	//Sets the change listener
	public GuiSlider setChangeListener(ChangeListener onchange){
		this.onchange=onchange;
		return this;
	}
}
