package de.whiletrue.processingguiengine;

import java.util.Map.Entry;

import processing.core.PApplet;

public abstract class GuiComponent {

	//Hidden
	private boolean hidden=false;
	
	//Opacity
	private float opacity=1;
	
	public abstract boolean handleMousePressed(PApplet app);
	public abstract boolean handleMouseReleased(PApplet app);
	public abstract void handleAfterMousePressed(PApplet app);
	public abstract boolean handleKeyPressed(PApplet app);
	public abstract boolean handleKeyReleased(PApplet app);
	public abstract Entry<Boolean, RenderObject> handleRender(PApplet app,boolean usedBefore);
	
	/*
	 * Returns the color with the opacity applied
	 * 
	 * @param
	 * */
	protected int applyOpacity(int color) {
		return color|((int)(this.opacity*0xff)<<24);
	}
	
	public boolean isHidden(){
		return this.hidden;
	}
	public GuiComponent setHidden(boolean hidden){
		this.hidden=hidden;
		return this;
	}
	//Return the opacity
	public final float getOpacity(){
		return this.opacity;
	}
	//Sets opacity
	public final GuiComponent setOpacity(float opacity){
		this.opacity=opacity;
		return this;
	}
	
}
