package de.whiletrue.processingguiengine.components;

import java.util.AbstractMap;
import java.util.Map.Entry;

import de.whiletrue.processingguiengine.GuiComponent;
import de.whiletrue.processingguiengine.RenderObject;
import processing.core.PApplet;

public class GuiCheckbox extends GuiComponent{

	//Position of the checkbox
	public int x,y,width,height;
	//Sets the box to checked
	private boolean checked;
	//Colors
	private int outlineColor,fillColor;
	//Callback listener
	private CheckboxListener onchange;

	public GuiCheckbox(float x,float y,float width,float height,boolean checked,CheckboxListener onchange){
		this(x,y,width,height,checked,onchange,0,0xffff0000);
	}

	public GuiCheckbox(float x,float y,float width,float height,boolean checked,CheckboxListener onchange,int outlineColor,int fillColor){
		this.x=(int)x;
		this.y=(int)y;
		this.width=(int)width;
		this.height=(int)height;
		this.checked=checked;
		this.onchange=onchange;
		this.outlineColor=outlineColor;
		this.fillColor=fillColor;
	}

	@Override
	public boolean handleMousePressed(PApplet app){

		//Gets if the box is hovered
		boolean hovered=this.isHovered(app.mouseX,app.mouseY);

		//Checks if the box is hovered
		if(hovered){
			//Negates the checked and executes the callback
			this.checked=!this.checked;
			this.onchange.execute(this.checked);
		}

		return hovered;
	}

	@Override
	public void handleAfterMousePressed(PApplet app){}
	
	@Override
	public boolean handleMouseReleased(PApplet app){
		return false;
	}

	@Override
	public boolean handleKeyPressed(PApplet app){
		return false;
	}

	@Override
	public boolean handleKeyReleased(PApplet app){
		return false;
	}

	@Override
	public Entry<Boolean,RenderObject> handleRender(PApplet app,boolean usedBefore){

		//Gets if the box is hovered
		boolean hovered=this.isHovered(app.mouseX,app.mouseY);

		return new AbstractMap.SimpleEntry<Boolean,RenderObject>(hovered,()->{

			//Renders the checkmark if the box is checked
			if(this.checked){
				app.stroke(5);
				app.stroke(this.fillColor);
				app.line(this.x,this.y,this.x + this.width,this.y + this.height);
				app.line(this.x,this.y + height,this.x + this.width,this.y);
			}

			//Renders the box
			app.noFill();
			app.stroke(this.outlineColor);
			app.strokeWeight(2);
			app.rect(this.x,this.y,this.width,this.height);
		});
	}

	/*
	 * Returns if the checkbox gets hovered
	 */
	private boolean isHovered(int mouseX,int mouseY){
		return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
	}

	//Listener that executes everytime the box changed
	@FunctionalInterface
	public interface CheckboxListener{
		public void execute(boolean checked);
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

	//Return the height
	public final int getHeight(){
		return this.height;
	}

	//Return the checked
	public final boolean isChecked(){
		return this.checked;
	}

	//Return the outlineColor
	public final int getOutlineColor(){
		return this.outlineColor;
	}

	//Return the fillColor
	public final int getFillColor(){
		return this.fillColor;
	}

	//Sets x
	public GuiCheckbox setX(int x){
		this.x=x;
		return this;
	}

	//Sets y
	public GuiCheckbox setY(int y){
		this.y=y;
		return this;
	}

	//Sets width
	public GuiCheckbox setWidth(int width){
		this.width=width;
		return this;
	}

	//Sets height
	public GuiCheckbox setHeight(int height){
		this.height=height;
		return this;
	}

	//Sets checked
	public GuiCheckbox setChecked(boolean checked){
		this.checked=checked;
		return this;
	}

	//Sets outlineColor
	public GuiCheckbox setOutlineColor(int outlineColor){
		this.outlineColor=outlineColor;
		return this;
	}

	//Sets fillColor
	public GuiCheckbox setFillColor(int fillColor){
		this.fillColor=fillColor;
		return this;
	}
}
