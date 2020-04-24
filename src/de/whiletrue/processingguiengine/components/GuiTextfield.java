package de.whiletrue.processingguiengine.components;

import java.util.AbstractMap;
import java.util.Map.Entry;

import de.whiletrue.processingguiengine.RenderObject;
import de.whiletrue.processingguiengine.components.defaults.SizeComponent;
import processing.core.PApplet;
import processing.core.PFont;

public class GuiTextfield extends SizeComponent{

	//Default styles
	private static int DEF_textColor=0xFFFFFF,DEF_fillColor=0x0,DEF_outlineColor=0xFFFFFF,DEF_outlineStrength=0x3,
			DEF_presetColor=0x4A63D4,DEF_cornerStrength=0x0;
	private static PFont DEF_font=new PFont(PFont.findFont("Arial"),true);

	//Events
	private TextfieldTypeEvent onType;

	//Max length
	private int maxLength=32;

	//Text
	private StringBuffer text;

	//Preset text
	private String presetText;

	//Is the field focused
	private boolean focused;
	//Cursor position
	private int cursor=0;

	//Styles
	private int textColor,fillColor,outlineColor,outlineStrength,presetColor,cornerStrength;
	private PFont font;
	
	public GuiTextfield(float x,float y,float width,float height,TextfieldTypeEvent onType,String text){
		this(x,y,width,height,onType,text,null,DEF_textColor,DEF_fillColor,DEF_outlineColor,DEF_outlineStrength,
				DEF_presetColor,DEF_cornerStrength,DEF_font);
	}

	public GuiTextfield(float x,float y,float width,float height,TextfieldTypeEvent onType,String text,String presetText){
		this(x,y,width,height,onType,text,presetText,DEF_textColor,DEF_fillColor,DEF_outlineColor,DEF_outlineStrength,
				DEF_presetColor,DEF_cornerStrength,DEF_font);
	}

	public GuiTextfield(float x,float y,float width,float height,TextfieldTypeEvent onType,String text,String presetText,
			int textColor,int fillColor,int outlineColor,int outlineStrength,int presetColor,int cornerStrength,PFont font){
		super(x,y,width,height);
		this.onType=onType;
		this.text=new StringBuffer(text == null?"":text);
		this.presetText=presetText == null?"":presetText;
		this.fillColor=fillColor;
		this.textColor=textColor;
		this.outlineStrength=outlineStrength;
		this.outlineColor=outlineColor;
		this.presetColor=presetColor;
		this.cornerStrength=cornerStrength;
		this.font=font;
	}

	/*
	 * Sets the default styles values
	 */
	public static void setDefaults(int textColor,int fillColor,int outlineColor,int outlineStrength,int presetColor,
			int cornerStrength,PFont font){
		DEF_textColor=textColor;
		DEF_fillColor=fillColor;
		DEF_outlineColor=outlineColor;
		DEF_outlineStrength=outlineStrength;
		DEF_presetColor=presetColor;
		DEF_cornerStrength=cornerStrength;
		if(font!=null)
			DEF_font=font;
	}

	@Override
	public Entry<Boolean,RenderObject> handleRender(PApplet app,boolean usedBefore){
		//Gets the rendercolor
		int textRenderColor=this.text.length() <= 0 && !this.presetText.isEmpty()?this.presetColor:this.textColor;

		//Gets if the field is hovered
		boolean hovered=this.isHovered(app.mouseX,app.mouseY);

		//Gets the coordinates
		float[] coords = this.getRealCoordinates();
		
		//Clones the text, so the real text wont get changed
		StringBuffer renderText=new StringBuffer(this.text.length() <= 0?this.presetText:this.text);

		//Checks if the field is focused
		if(this.focused){
			//Ensures that the curser's position is good
			this.cursor=Math.min(this.cursor,renderText.length());
			this.cursor=Math.max(this.cursor,0);

			//Gets the cursor state (Blinking)
			char state=app.millis() % 1000 > 500?'|':' ';

			//Inserts the cursor
			renderText.insert(this.cursor,state);
		}
		
		//Prepares the size
		app.textFont(this.font);
		app.textSize(Math.min(coords[2],coords[3]) / 2);

		//Goes on as long as the text wont fit
		while(app.textWidth(renderText.toString()) > coords[2])
			//Checks if the left side of the cursor is bigger than the right
			if(this.cursor > renderText.length() / 2)
				//Removes one char from the left
				renderText.delete(0,1);
			else
				//Removes one char from the right
				renderText.delete(renderText.length() - 1,renderText.length());

		return new AbstractMap.SimpleEntry<Boolean,RenderObject>(hovered,()->{
			//Renders the box
			app.stroke(this.applyOpacity(this.outlineColor));
			app.fill(this.applyOpacity(this.fillColor));
			app.strokeWeight(this.outlineStrength);
			app.rect(coords[0],coords[1],coords[2],coords[3],this.cornerStrength);

			//Renders the text
			app.textFont(this.font);
			app.textAlign(PApplet.LEFT,PApplet.CENTER);
			app.textSize(Math.min(coords[2],coords[3]) / 2);
			app.fill(this.applyOpacity(textRenderColor));
			app.text(renderText.toString(),coords[0] + 6,coords[1] + coords[3] / 2);
		});
	}

	@Override
	public boolean handleMousePressed(PApplet app){
		//Gets if the field is hovered
		boolean hovered=this.isHovered(app.mouseX,app.mouseY);
		//Sets the cursor to the end
		this.cursor=this.text.length();

		//Sets the fields focus depending if its hovered
		this.focused=hovered;
		return hovered;
	}

	@Override
	public boolean handleMouseReleased(PApplet app){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void handleAfterMousePressed(PApplet app){
		this.focused=false;
	}

	@Override
	public boolean handleKeyPressed(PApplet app){
		return false;
	}

	@Override
	public boolean handleKeyReleased(PApplet app){
		//Checks if the field is focused
		if(!this.focused)
			return false;

		//Checks which key was pressed
		switch(app.keyCode) {
			//Backspace
			case 8:
				//Checks if the char before can be removed
				if(this.cursor <= 0)
					break;

				//Removes the char and fixes the cursor
				this.text.deleteCharAt(--this.cursor);
				//Handles the type callback-event
				this.onType.execute(this.text.toString());

				break;
			//Entf
			case 127:
				//Checks if the char after can be removed
				if(this.cursor >= this.text.length())
					break;

				//Removes the char
				this.text.deleteCharAt(this.cursor);
				//Handles the type callback-event
				this.onType.execute(this.text.toString());

				break;
			//Arrow Left
			case 37:
				//Checks if the curser can go left
				if(this.cursor <= 0)
					break;

				//Moves the curser
				this.cursor--;
				//Handles the type callback-event
				this.onType.execute(this.text.toString());

				break;
			//Arrow Right
			case 39:
				//Checks if the curser can go right
				if(this.cursor >= this.text.length())
					break;

				this.cursor++;
				//Handles the type callback-event
				this.onType.execute(this.text.toString());

				break;
			//Else writing chars
			default:
				//Checks if the character can be written by the max length
				if(this.text.length() + 1 > this.maxLength)
					break;
				//Checks if the written character is valid
				if(!this.isValid(app.key))
					break;
				//Inserts the character
				this.text.insert(this.cursor++,app.key);
				//Handles the type callback-event
				if(!this.onType.execute(this.text.toString()))
					this.text.deleteCharAt(--this.cursor);
				break;
		}
		return false;
	}

	/*
	 * Validates a given character
	 */
	private boolean isValid(Character c){
		//Returns if the character is in the ascii printable zone
		return c >= 32 && c < 127;
	}

	@FunctionalInterface
	public static interface TextfieldTypeEvent{
		public boolean execute(String text);
	}

	//Return the text
	public final String getText(){
		return this.text.toString();
	}

	//Sets onType
	public final GuiTextfield setTypeEvent(TextfieldTypeEvent onType){
		this.onType=onType;
		return this;
	}

	//Sets maxLength
	public final GuiTextfield setMaxLength(int maxLength){
		this.maxLength=maxLength;
		return this;
	}

	//Sets text
	public final GuiTextfield setText(String text){
		this.text=new StringBuffer(text);
		return this;
	}

	//Sets presetText
	public final GuiTextfield setPresetText(String presetText){
		this.presetText=presetText;
		return this;
	}

	//Sets focused
	public final GuiTextfield setFocused(boolean focused){
		this.focused=focused;
		return this;
	}

	//Sets textColor
	public final GuiTextfield setTextColor(int textColor){
		this.textColor=textColor;
		return this;
	}

	//Sets fillColor
	public final GuiTextfield setFillColor(int fillColor){
		this.fillColor=fillColor;
		return this;
	}

	//Sets outlineColor
	public final GuiTextfield setOutlineColor(int outlineColor){
		this.outlineColor=outlineColor;
		return this;
	}

	//Sets outlineStrength
	public final GuiTextfield setOutlineStrength(int outlineStrength){
		this.outlineStrength=outlineStrength;
		return this;
	}

	//Sets presetColor
	public final GuiTextfield setPresetColor(int presetColor){
		this.presetColor=presetColor;
		return this;
	}

	//Sets cornerStrength
	public final GuiTextfield setCornerStrength(int cornerStrength){
		this.cornerStrength=cornerStrength;
		return this;
	}

	//Sets font
	public final GuiTextfield setFont(PFont font){
		this.font=font;
		return this;
	}

}
