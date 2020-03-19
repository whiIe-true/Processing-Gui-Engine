package de.whiletrue.processingguiengine.components;

public class GuiSliderValues<T> extends GuiSlider{

	//All slider values
	private T sliderValues[];
	//The new change listener
	private ChangeValueListener<T> changeListener;

	public GuiSliderValues(float x,float y,float width,float height,T sliderValues[],int currentIndex,ChangeValueListener<T> onChange,String defaultText){
		super(x,y,width,height,0,sliderValues.length-1,currentIndex,null,defaultText);
		this.sliderValues=sliderValues;
		this.changeListener=onChange;
		//Updates the change listener
		this.setChangeListener(this::handleChange);
	}

	/*
	 * Handles all change events for the slider
	 * */
	public String handleChange(double percent,int value){
		//Executes the new change listener
		return this.changeListener.execute(value,this.sliderValues[value]);
	}

	/*
	 * Sets the state to the value with the given string
	 * */
	public void setStateByValue(String value) {
		//Gets the index of the value
		int index = this.getIndex(value);
		
		//Checks if the index got found
		if(index == -1)
			return;
		
		//Sets the slider
		this.setState(index);
	}
	
	/*
	 * Returns the index of the given value on the slider
	 */
	public int getIndex(String value){
		//Iterates over all slider values
		for(int i=0; i < this.sliderValues.length; i++)
			//Checks if the current value is equal to the searched value
			if(this.sliderValues[i].equals(value))
				//Returns that index
				return i;
		//Returns error -1
		return -1;
	}

	//Listener that does the same as the change listener but with other values
	@FunctionalInterface
	public interface ChangeValueListener<T>{
		/**
		 * @param index the index of the value in the given string array
		 * @param value the actual value
		 */
		public String execute(int index,T value);
	}
}
