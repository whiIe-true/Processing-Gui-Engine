package de.whiletrue.processingguiengine.components.defaults;

import de.whiletrue.processingguiengine.GuiComponent;

public abstract class CoordinateComponent extends GuiComponent{

	//Coordinates of the component
	protected float x=0,
			y=0,
			width=0,
			height=0;

	public CoordinateComponent(float x,float y,float width,float height){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	
	/*
	 * Returns if the component is hovered
	 * */
	public boolean isHovered(int mouseX,int mouseY) {
		return mouseX>this.x && mouseX < this.x+this.width && mouseY>this.y && mouseY < this.y+this.height;
	}

	//Return the x
	public final float getX(){
		return this.x;
	}

	//Return the y
	public final float getY(){
		return this.y;
	}

	//Return the width
	public final float getWidth(){
		return this.width;
	}

	//Return the height
	public final float getHeight(){
		return this.height;
	}

	//Sets x
	public final CoordinateComponent setX(float x){
		this.x=x;
		return this;
	}

	//Sets y
	public final CoordinateComponent setY(float y){
		this.y=y;
		return this;
	}

	//Sets width
	public final CoordinateComponent setWidth(float width){
		this.width=width;
		return this;
	}

	//Sets height
	public final CoordinateComponent setHeight(float height){
		this.height=height;
		return this;
	}
}
