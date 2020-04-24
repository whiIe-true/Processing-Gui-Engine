package de.whiletrue.processingguiengine.components;

import java.util.AbstractMap;
import java.util.Map.Entry;

import de.whiletrue.processingguiengine.RenderObject;
import de.whiletrue.processingguiengine.components.defaults.SizeComponent;
import processing.core.PApplet;

public class GuiCheckbox extends SizeComponent{

	//Default styles
	private static int DEF_outlineColor=0x0,DEF_fillColor=0xFF0000,DEF_outlineStrength=0x2,DEF_innerStrength=0x2;
	
	//If the box is checked
	private boolean checked;

	//Events
	private CheckboxChangeEvent onChange;

	//Styles
	private int outlineColor,fillColor,outlineStrength,innerStrength;
	
	public GuiCheckbox(float x,float y,float width,float height,boolean checked,CheckboxChangeEvent onChange){
		this(x,y,width,height,checked,onChange,DEF_outlineColor,DEF_fillColor,DEF_outlineStrength,DEF_innerStrength);
	}

	public GuiCheckbox(float x,float y,float width,float height,boolean checked,CheckboxChangeEvent onChange,int outlineColor,int fillColor,int outlineStrength,int innerStrength){
		super(x,y,width,height);
		this.checked=checked;
		this.onChange=onChange;
		this.outlineColor=outlineColor;
		this.fillColor=fillColor;
		this.outlineStrength=outlineStrength;
		this.innerStrength=innerStrength;
	}

	/*
	 * Sets the defaults
	 */
	public static void setDefaults(int outlineColor,int fillColor,int outlineStrength,int innerStrength){
		DEF_fillColor=fillColor;
		DEF_outlineColor=outlineColor;
		DEF_outlineStrength=outlineStrength;
		DEF_innerStrength=innerStrength;
	}

	@Override
	public Entry<Boolean,RenderObject> handleRender(PApplet app,boolean usedBefore){
		//Gets if the box is hovered
		boolean hovered=this.isHovered(app.mouseX,app.mouseY);

		return new AbstractMap.SimpleEntry<Boolean,RenderObject>(hovered,()->{

			//Gets the coordinates
			float[] coords=this.getRealCoordinates();

			//Renders the checkmark if the box is checked
			if(this.checked){
				app.strokeWeight(this.innerStrength);
				app.stroke(this.applyOpacity(this.fillColor));
				app.line(coords[0],coords[1],coords[0] + coords[2],coords[1] + coords[3]);
				app.line(coords[0]+coords[2],coords[1],coords[0],coords[1]+coords[3]);
			}

			//Renders the box
			app.noFill();
			app.stroke(this.applyOpacity(this.outlineColor));
			app.strokeWeight(this.outlineStrength);
			app.rect(coords[0],coords[1],coords[2],coords[3]);
		});
	}

	@Override
	public boolean handleMousePressed(PApplet app){
		//Gets if the box is hovered
		boolean hovered=this.isHovered(app.mouseX,app.mouseY);

		//Checks if the box is hovered
		if(hovered){
			//Negates the checked and executes the callback
			this.checked=!this.checked;
			this.onChange.execute(this.checked);
		}

		return hovered;
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

	@FunctionalInterface
	public static interface CheckboxChangeEvent{
		public void execute(boolean checked);
	}

	//Sets checked
	public final GuiCheckbox setChecked(boolean checked){
		this.checked=checked;
		return this;
	}

	//Sets onChange
	public final GuiCheckbox setChangeEvent(CheckboxChangeEvent onChange){
		this.onChange=onChange;
		return this;
	}

	//Sets outlineColor
	public final GuiCheckbox setOutlineColor(int outlineColor){
		this.outlineColor=outlineColor;
		return this;
	}

	//Sets fillColor
	public final GuiCheckbox setFillColor(int fillColor){
		this.fillColor=fillColor;
		return this;
	}

	//Sets outlineStrength
	public final GuiCheckbox setOutlineStrength(int outlineStrength){
		this.outlineStrength=outlineStrength;
		return this;
	}

	//Sets innerStrength
	public final GuiCheckbox setInnerStrength(int innerStrength){
		this.innerStrength=innerStrength;
		return this;
	}
}
