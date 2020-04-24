package de.whiletrue.processingguiengine.components;

import java.util.AbstractMap;
import java.util.Map.Entry;

import de.whiletrue.processingguiengine.RenderObject;
import de.whiletrue.processingguiengine.components.defaults.SizeComponent;
import processing.core.PApplet;
import processing.core.PFont;

public class GuiSlider extends SizeComponent{

	//Default styles
	private static int DEF_fillColor=0xf00000,DEF_emptyColor=0x717171,DEF_outlineColor=0x0,DEF_textColor=0xffffff,
			DEF_outlineStrength=5;
	private static PFont DEF_font=new PFont(PFont.findFont("Arial"),true);

	//State of the slider
	private int min,max;
	private double percState;

	//Events
	private SliderChangeEvent onChange;

	//Displayed text
	private String text;

	//Styles
	private int fillColor,emptyColor,outlineColor,textColor,outlineStrength;
	private PFont font;
	
	//Currently dragged
	private boolean dragged;

	public GuiSlider(float x,float y,float width,float height,int min,int max,int current,SliderChangeEvent onChange){
		//Init's the slider with the default styles
		this(x,y,width,height,min,max,current,onChange,DEF_fillColor,DEF_emptyColor,DEF_outlineColor,
				DEF_outlineStrength,DEF_textColor,DEF_font);
	}

	public GuiSlider(float x,float y,float width,float height,int min,int max,int current,SliderChangeEvent onChange,
			int fillColor,int emptyColor,int outlineColor,int outlineStrength,int textColor,PFont font){
		super(x,y,width,height);
		this.min=min;
		this.max=max;
		this.onChange=onChange;
		this.fillColor=fillColor;
		this.emptyColor=emptyColor;
		this.outlineColor=outlineColor;
		this.textColor=textColor;
		this.outlineStrength=outlineStrength;
		this.font=font;

		//Sets the state
		this.setState(current);

		//Gets the text
		this.text=this.onChange.execute(this.percState,current);
	}

	public static void setDefaults(int fillColor,int emptyColor,int outlineColor,int outlineStrength,int textColor,PFont font){
		DEF_fillColor=fillColor;
		DEF_emptyColor=emptyColor;
		DEF_outlineColor=outlineColor;
		DEF_outlineStrength=outlineStrength;
		DEF_textColor=textColor;
		if(font!=null)
			DEF_font=font;
	}

	@Override
	public Entry<Boolean,RenderObject> handleRender(PApplet app,boolean usedBefore){
		//Calculates the real coord's
		final float[] coords=this.getRealCoordinates();

		//Shorts the variables for the mouse
		int mx=app.mouseX,my=app.mouseY;

		//Gets if the slider is hovered by the users mouse
		boolean hovered=this.isHovered(mx,my);

		//Checks if the slider is dragged
		change:if(this.dragged){

			//Calculates the new state of the slider
			double state=((double)(mx - coords[0])) / (double)coords[2];

			//Ensures that the state is not higher or lower that the min or max
			state=Math.min(state,1);
			state=Math.max(state,0);

			//Checks if the new state is equal to the old state
			if(this.percState == state)
				break change;

			//Updates the state
			this.percState=state;

			//Calculates the value
			int value=this.getState();

			//Executes the change callback and updates the text
			this.text=this.onChange.execute(this.percState,value);
		}

		//Shorts the variable for the fill-width
		int wid=(int)(coords[2] * this.percState);

		//Returns if the slider is hovered and the renderer
		return new AbstractMap.SimpleEntry<Boolean,RenderObject>(hovered,()->{
			//Renders the outline
			app.noFill();
			app.stroke(this.applyOpacity(this.outlineColor));
			app.strokeWeight(this.outlineStrength);
			app.rect(coords[0] - 1,coords[1] - 1,coords[2] + 1,coords[3] + 1);

			//Renders the slider
			app.noStroke();
			app.fill(this.applyOpacity(this.fillColor));
			app.rect(coords[0],coords[1],(float)wid,coords[3]);
			app.fill(this.applyOpacity(this.emptyColor));
			app.rect(coords[0] + wid,coords[1],coords[2] - wid,coords[3]);

			//Renders the slider text
			app.textFont(this.font);
			app.fill(this.applyOpacity(this.textColor));
			app.textAlign(PApplet.CENTER,PApplet.CENTER);
			app.textSize(Math.max(.01f,Math.min(coords[1],coords[3])) / 2);
			app.text(this.text,coords[0] + coords[2] / 2,coords[1] + coords[3] / 2);
		});
	}

	@Override
	public boolean handleMousePressed(PApplet app){
		//Sets the slider dragged, depending if it was hovered
		this.dragged=this.isHovered(app.mouseX,app.mouseY);
		return this.dragged;
	}

	@Override
	public boolean handleMouseReleased(PApplet app){
		this.dragged=false;
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

	/**
	 * Sets the state of the slider
	 * 
	 * @param currentValue the value
	 */
	public void setState(int currentValue){
		//Ensures that the values is in the slider's range
		currentValue=Math.min(this.max,Math.max(this.min,currentValue));
		//Calculates the percentual value of the slider
		this.percState=((double)(currentValue - this.min)) / ((double)(this.max - this.min));

		//Updates the text
		this.text=this.onChange.execute(this.percState,this.getState());
	}
	
	/**
	 * Updates the slider without sending a update
	 * 
	 * @param percentage the new percentage
	 * */
	public void setStateWithoutUpdate(double percentage) {
		this.percState=percentage;
	}

	/*
	 * Returns the current state of the slider
	 */
	public int getState(){
		//Calculates the value
		return (int)((this.max - this.min) * this.percState + this.min);
	}

	@FunctionalInterface
	public static interface SliderChangeEvent{
		public String execute(double perc,int value);
	}

	//Sets onChange
	public GuiSlider setChangeEvent(SliderChangeEvent onChange){
		this.onChange=onChange;
		return this;
	}

	//Sets fillColor
	public final GuiSlider setFillColor(int fillColor){
		this.fillColor=fillColor;
		return this;
	}

	//Sets emptyColor
	public final GuiSlider setEmptyColor(int emptyColor){
		this.emptyColor=emptyColor;
		return this;
	}

	//Sets outlineColor
	public final GuiSlider setOutlineColor(int outlineColor){
		this.outlineColor=outlineColor;
		return this;
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
	
	//Sets font
	public final GuiSlider setFont(PFont font){
		this.font=font;
		return this;
	}
}
