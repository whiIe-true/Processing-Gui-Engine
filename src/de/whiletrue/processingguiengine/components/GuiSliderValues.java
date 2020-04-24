package de.whiletrue.processingguiengine.components;

import processing.core.PFont;

public class GuiSliderValues<T>extends GuiSlider{

	private T[] values;

	public GuiSliderValues(float x,float y,float width,float height,T[] values,int index,ValueSliderChangeEvent<T> onChange){
		super(x,y,width,height,0,values.length - 1,index,(perc,val)->onChange.execute(val,values[val]));
		this.values=values;
	}

	public GuiSliderValues(float x,float y,float width,float height,T[] values,int index,ValueSliderChangeEvent<T> onChange,
			int fillColor,int emptyColor,int outlineColor,int textColor,int outlineStrength,PFont font){
		super(x,y,width,height,0,values.length - 1,index,(perc,val)->onChange.execute(val,values[val]),fillColor,
				emptyColor,outlineColor,textColor,outlineStrength,font);
		this.values=values;
	}

	/**
	 * Sets the slider state to the given value
	 * 
	 * @param value the value
	 */
	public void setStateByValue(T value){
		//Iterates over every value
		for(int i=0; i < this.values.length; i++)
			//Checks if the values if equal
			if(value.equals(this.values[i])){
				//Sets the state
				this.setState(i);
				break;
			}
	}
	
	public GuiSliderValues<T> setValueChangeEvent(ValueSliderChangeEvent<T> onChange){
		this.setChangeEvent((perc,val)->onChange.execute(val,values[val]));
		return this;
	}
	
	/*
	 * Returns the current selected value
	 */
	public T getValue(){
		return this.values[this.getState()];
	}

	@FunctionalInterface
	public static interface ValueSliderChangeEvent<T>{
		public String execute(int index,T value);
	}

}
