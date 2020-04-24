package de.whiletrue.processingguiengine.components;

import java.util.AbstractMap;
import java.util.Map.Entry;

import de.whiletrue.processingguiengine.RenderObject;
import de.whiletrue.processingguiengine.components.defaults.SizeComponent;
import processing.core.PApplet;
import processing.core.PFont;

public class GuiButton extends SizeComponent{

	//Default style values
	private static int DEF_bgColor=0x717171,DEF_hoverColor=0xA00000,DEF_clickColor=0xF50000,
			DEF_textColor=0xFFFFFF,DEF_shadowColor=0xACACAC,DEF_outlineColor=0x0,DEF_outlineStrength=0x5,
			DEF_cornerStrength=0x0;
	private static PFont DEF_font=new PFont(PFont.findFont("Arial"),true);

	//Events
	private ButtonClickEvent onClick;

	//Current text
	private String text;

	//Styles
	private int bgColor,hoverColor,clickColor,textColor,shadowColor,outlineColor,cornerStrength,outlineStrength;
	private PFont font;
	
	public GuiButton(float x,float y,float width,float height,ButtonClickEvent onClick){
		//Init's the button with the preset default values
		this(x,y,width,height,onClick,DEF_bgColor,DEF_hoverColor,DEF_clickColor,DEF_textColor,DEF_shadowColor,
				DEF_outlineColor,DEF_outlineStrength,DEF_cornerStrength,DEF_font);
	}

	public GuiButton(float x,float y,float width,float height,ButtonClickEvent onClick,int bgColor,
			int hoverColor,int clickColor,int textColor,int shadowColor,int outlineColor,int outlineStrength,
			int cornerStrength,PFont font){
		super(x,y,width,height);
		this.bgColor=bgColor;
		this.hoverColor=hoverColor;
		this.clickColor=clickColor;
		this.textColor=textColor;
		this.shadowColor=shadowColor;
		this.outlineColor=outlineColor;
		this.outlineStrength=outlineStrength;
		this.cornerStrength=cornerStrength;
		this.font=font;
		this.onClick=onClick;

		//Init's the button text
		this.text=this.onClick.execute(-1);
	}

	public static void setDefaults(int bgColor,int hoverColor,int clickColor,int textColor,int shadowColor,
			int outlineColor,int outlineStrength,int cornerStrength,PFont font){
		DEF_bgColor=bgColor;
		DEF_hoverColor=hoverColor;
		DEF_clickColor=clickColor;
		DEF_textColor=textColor;
		DEF_shadowColor=shadowColor;
		DEF_outlineColor=outlineColor;
		DEF_outlineStrength=outlineStrength;
		DEF_cornerStrength=cornerStrength;
		if(font!=null)
			DEF_font=font;
	}

	@Override
	public Entry<Boolean,RenderObject> handleRender(PApplet app,boolean usedBefore){
		//Gets if the button is hovered
		boolean hover=this.isHovered(app.mouseX,app.mouseY);
		//Gets what color the button should be rendered
		int col=hover && !usedBefore?(app.mousePressed?this.clickColor:this.hoverColor):this.bgColor;

		//Returns a new entry with the new used boolean and the render object
		return new AbstractMap.SimpleEntry<Boolean,RenderObject>(hover,()->{

			//Calculates the real coords
			float[] coords=this.getRealCoordinates();

			//Renders the button
			app.stroke(this.applyOpacity(this.outlineColor));
			app.strokeWeight(this.outlineStrength);
			app.fill(this.applyOpacity(col));
			app.rect(coords[0],coords[1],coords[2],coords[3],this.cornerStrength);

			//Sets the font
			app.textFont(this.font);
			
			//Renders the text
			app.textSize(Math.max(.01f,Math.min(coords[2],coords[3])) / 2);
			app.textAlign(PApplet.CENTER,PApplet.CENTER);
			//Checks if the text should be rendered with a shadow
			if(this.shadowColor != -1){
				app.fill(this.applyOpacity(this.shadowColor));
				app.text(this.text,coords[0] + coords[2] / 2 - 2,coords[1] + coords[3] / 2.5f + 2);
			}
			app.fill(this.applyOpacity(this.textColor));
			app.text(this.text,coords[0] + coords[2] / 2,coords[1] + coords[3] / 2.5f);
		});
	}

	@Override
	public boolean handleMousePressed(PApplet app){
		//Checks if the button got clicked
		if(this.isHovered(app.mouseX,app.mouseY)){
			//Handles the onclick
			this.text=this.onClick.execute(app.mouseButton);
			return true;
		}

		return false;
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
	public static interface ButtonClickEvent{
		public String execute(int id);
	}

	//Sets onClick
	public final GuiButton setClickEvent(ButtonClickEvent onClick){
		this.onClick=onClick;
		return this;
	}

	//Sets text
	public final GuiButton setText(String text){
		this.text=text;
		return this;
	}

	//Sets bgColor
	public final GuiButton setBgColor(int bgColor){
		this.bgColor=bgColor;
		return this;
	}

	//Sets hoverColor
	public final GuiButton setHoverColor(int hoverColor){
		this.hoverColor=hoverColor;
		return this;
	}

	//Sets clickColor
	public final GuiButton setClickColor(int clickColor){
		this.clickColor=clickColor;
		return this;
	}

	//Sets textColor
	public final GuiButton setTextColor(int textColor){
		this.textColor=textColor;
		return this;
	}

	//Sets shadowColor
	public final GuiButton setShadowColor(int shadowColor){
		this.shadowColor=shadowColor;
		return this;
	}

	//Sets outlineColor
	public final GuiButton setOutlineColor(int outlineColor){
		this.outlineColor=outlineColor;
		return this;
	}

	//Sets cornerStrength
	public final GuiButton setCornerStrength(int cornerStrength){
		this.cornerStrength=cornerStrength;
		return this;
	}

	//Sets outlineStrength
	public final GuiButton setOutlineStrength(int outlineStrength){
		this.outlineStrength=outlineStrength;
		return this;
	}
	
	//Sets font
	public final GuiButton setFont(PFont font){
		this.font=font;
		return this;
	}
}
