package de.whiletrue.processingguiengine.components;

import java.awt.Color;
import java.util.AbstractMap;
import java.util.Map.Entry;

import de.whiletrue.processingguiengine.GuiComponent;
import de.whiletrue.processingguiengine.RenderObject;
import processing.core.PApplet;

public class GuiTextfield extends GuiComponent{
	
	//Position of the field
	private int x,y,width,height;
	//Typelistener
	private TypeListener ontype;
	//Max amount of typeable chars
	private int maxLength = 32;
	
	//If the field is focused
	private boolean focused;
	//The current text
	private StringBuffer text;
	//Preset string that gets rendered if the actual text is not set
	private String presetText;
	//Defines where the curser is a the text
	private int curser = 0;
	//Colors
	private int textColor,fillColor,outlineColor,presetColor;
	
	public GuiTextfield(int x,int y,int width,int height,TypeListener ontype,String defaultText) {
		this(x,y,width,height,ontype,defaultText,null,0xffffff,0,0xffffff,0x4A63D4);
	}
	
	public GuiTextfield(int x,int y,int width,int height,TypeListener ontype,String defaultText,String presetText,int textColor,int fillColor,int outlineColor,int presetColor) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.ontype = ontype;
		this.text = new StringBuffer(defaultText==null?"":defaultText);
		this.textColor = new Color(textColor).getRGB();
		this.fillColor = new Color(fillColor).getRGB();
		this.outlineColor = new Color(outlineColor).getRGB();
		this.presetColor = new Color(presetColor).getRGB();
		this.presetText = presetText==null?"":presetText;
	}
	
	@Override
	public boolean handleMousePressed(PApplet app) {
		//Gets if the field is hovered
		boolean hovered = this.isHovered(app.mouseX, app.mouseY);
		//Sets the curser to the end
		this.curser = this.text.length();
		
		//Sets the fields focus depending if its hovered
		this.focused = hovered;
		return hovered;
	}

	@Override
	public boolean handleMouseReleased(PApplet app) {
		return false;
	}

	@Override
	public boolean handleKeyPressed(PApplet app) {
		//Checks if the field is focused
		if(!this.focused)
			return false;
		
		//Checks which key was pressee
		switch (app.keyCode) {
		//Backspace
		case 8:
			//Checks if the char before can be removed
			if(this.curser<=0)
				break;

			//Removes the char and fixes the curser
			this.text.deleteCharAt(--this.curser);
			//Handles the type callback-event
			this.ontype.execute(this.text.toString());
			
			break;
		//Entf
		case 127:
			//Checks if the char after can be removed
			if(this.curser>=this.text.length())
				break;
			
			//Removes the char
			this.text.deleteCharAt(this.curser);
			//Handles the type callback-event
			this.ontype.execute(this.text.toString());
			
			break;
		//Arrow Left
		case 37:
			//Checks if the curser can go left
			if(this.curser<=0)
				break;
			
			//Moves the curser
			this.curser--;
			//Handles the type callback-event
			this.ontype.execute(this.text.toString());
			
			break;
		//Arrow Right
		case 39:
			//Checks if the curser can go right
			if(this.curser>=this.text.length())
				break;
			
			this.curser++;
			//Handles the type callback-event
			this.ontype.execute(this.text.toString());
			
			break;
		//Else writing chars
		default:
			//Checks if the character can be written by the max length
			if(this.text.length()+1 > this.maxLength)
				break;
			//Checks if the written character is valid
			if(!this.isValid(app.key))
				break;
			//Inserts the character
			this.text.insert(this.curser++, app.key);
			//Handles the type callback-event
			if(!this.ontype.execute(this.text.toString()))
				this.text.deleteCharAt(--this.curser);
			break;
		}
		return false;
	}

	@Override
	public boolean handleKeyReleased(PApplet app) {
		return false;
	}

	@Override
	public Entry<Boolean, RenderObject> handleRender(PApplet app, boolean usedBefore) {
		//Gets the rendercolor
		int textRenderColor = this.text.length()<=0&&!this.presetText.isEmpty()?this.presetColor:this.textColor;
		
		//Gets if the field is hovered
		boolean hovered = this.isHovered(app.mouseX, app.mouseY);
		
		//Clones the text, so the real text wont get changed
		StringBuffer renderText = new StringBuffer(this.text.length()<=0?this.presetText:this.text);
		
		//Checks if the field is focused
		if(this.focused) {
			//Ensures that the curser's position is good
			this.curser = Math.min(this.curser, renderText.length());
			this.curser = Math.max(this.curser, 0);
			
			//Gets the curser state (Blinking)
			char state = app.millis()%1000>500?'|':' ';
			
			//Inserts the curser
			renderText.insert(this.curser, state);
		}
		
		//Prepares the size
		app.textSize(Math.min(this.width, this.height)/2);
		
		//Goes on as long as the text wont fit
		while(app.textWidth(renderText.toString())>this.width)
			//Checks if the left side of the curser is bigger than the right
			if(this.curser>renderText.length()/2)
				//Removes one char from the left
				renderText.delete(0, 1);
			else
				//Removes one char from the right
				renderText.delete(renderText.length()-1, renderText.length());

		return new AbstractMap.SimpleEntry<Boolean, RenderObject>(hovered,()->{
			//Renders the box
			app.stroke(this.outlineColor);
			app.fill(this.fillColor);
			app.strokeWeight(3);
			app.rect(this.x, this.y, this.width, this.height);
			
			//Renders the text
			app.textAlign(PApplet.LEFT,PApplet.CENTER);
			app.textSize(Math.min(this.width, this.height)/2);
			app.fill(textRenderColor);
			app.text(renderText.toString(), this.x+6, this.y+this.height/2);
		});
	}
	
	/*
	 * Returns if the textfield gets hovered
	 * */
	private boolean isHovered(int mouseX,int mouseY) {
		return mouseX > this.x &&
				mouseX < this.x+this.width &&
				mouseY > this.y &&
				mouseY < this.y+this.height;
	}
	
	/*
	 * Validates a given character
	 * */
	private boolean isValid(Character c) {
		//Returns if the character is in the ascii printable zone
		return c >= 32 && c < 127;
	}
	
	//Listener that executes everytime the user types on the field
	@FunctionalInterface
	public interface TypeListener{
		public boolean execute(String text);
	}
	
	
	
	//Sets the maxlength
	public GuiTextfield setMaxLength(int length) {
		this.maxLength = length;
		return this;
	}
	
	//Sets the focused
	public GuiTextfield setFocused(boolean focused) {
		this.focused = focused;
		return this;
	}
	
	//Sets the text
	public GuiTextfield setText(String text) {
		this.text.setLength(0);
		this.text.append(text);
		return this;
	}
	
	//Sets the preset text
	public GuiTextfield setPresetText(String text) {
		this.presetText = text;
		return this;
	}
	
	//Sets the preset color
	public GuiTextfield setPresetColor(int color) {
		this.presetColor = new Color(color).getRGB();
		return this;
	}
	
	//Sets the fillcolor
	public GuiTextfield setFillColor(int color) {
		this.fillColor = new Color(color).getRGB();
		return this;
	}
	
	//Sets the textcolor
	public GuiTextfield setTextColor(int color) {
		this.textColor = new Color(color).getRGB();
		return this;
	}
	
	//Sets the outlinecolor
	public GuiTextfield setOutlineColor(int color) {
		this.outlineColor = new Color(color).getRGB();
		return this;
	}
	
	//Sets the curser
	public GuiTextfield setCurserPosition(int position) {
		this.curser = position;
		return this;
	}


	//Sets the x
	public GuiTextfield setX(int x) {
		this.x = x;
		return this;
	}

	
	//Sets the y
	public GuiTextfield setY(int y) {
		this.y = y;
		return this;
	}
	

	//Sets the width
	public GuiTextfield setWidth(int width) {
		this.width = width;
		return this;
	}

	
	//Sets the height
	public GuiTextfield setHeight(int height) {
		this.height = height;
		return this;
	}
}
