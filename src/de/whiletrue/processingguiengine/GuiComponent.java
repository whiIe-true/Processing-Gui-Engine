package de.whiletrue.processingguiengine;

import java.util.Map.Entry;

import processing.core.PApplet;

public abstract class GuiComponent {

	public abstract boolean handleMousePressed(PApplet app);
	public abstract boolean handleMouseReleased(PApplet app);
	public abstract void handleAfterMousePressed(PApplet app);
	public abstract boolean handleKeyPressed(PApplet app);
	public abstract boolean handleKeyReleased(PApplet app);
	public abstract Entry<Boolean, RenderObject> handleRender(PApplet app,boolean usedBefore);
	
}
