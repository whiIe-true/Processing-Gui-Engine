package de.whiletrue.processingguiengine;

import java.util.Map.Entry;

import processing.core.PApplet;

public abstract class GuiComponent {

	//Hidden
	private boolean hidden=false;
	
	public abstract boolean handleMousePressed(PApplet app);
	public abstract boolean handleMouseReleased(PApplet app);
	public abstract void handleAfterMousePressed(PApplet app);
	public abstract boolean handleKeyPressed(PApplet app);
	public abstract boolean handleKeyReleased(PApplet app);
	public abstract Entry<Boolean, RenderObject> handleRender(PApplet app,boolean usedBefore);
	
	public boolean isHidden(){
		return this.hidden;
	}
	
	public GuiComponent setHidden(boolean hidden){
		this.hidden=hidden;
		return this;
	}
	
}
