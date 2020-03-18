package de.whiletrue.processingguiengine.components;

import java.awt.Color;
import java.util.AbstractMap;
import java.util.Map.Entry;
import java.util.function.Function;

import de.whiletrue.processingguiengine.GuiComponent;
import de.whiletrue.processingguiengine.RenderObject;
import processing.core.PApplet;

public class GuiButton extends GuiComponent{

	//Default values for the class
	private static int defaultBgColor=0xff717171,defaultHoverColor=0xffA00000,defaultClickColor=0xffF50000,
			defaultTextColor=0xffFFFFFF,defaultTextShadowColor=0xffACACAC,defaultOutlineColor=0x0,
			defaultCornerStrength=0x0;
	private static float defaultSize=1f,defaultOutlineStrength=0x4;

	//Position
	private int x,y,width,height,
			//Styles
			defaultColor,hoverColor,clickColor,textColor,textShadowColor,outlineColor,cornerStrength;
	//Scale of the button
	private float size=defaultSize,
			//Style
			outlineStrength;
	//Events
	private Function<Integer,String> onclick;
	//Text on the button
	private String text;

	public GuiButton(float x,float y,float width,float height,Function<Integer,String> onclick){
		this(x,y,width,height,onclick,defaultBgColor,defaultHoverColor,defaultClickColor,defaultTextColor,
				defaultTextShadowColor,defaultOutlineColor,defaultOutlineStrength,defaultCornerStrength);
	}

	public GuiButton(float x,float y,float width,float height,Function<Integer,String> onclick,int background,int hoverColor,int clickColor,int textColor,int textShadowColor,int outlineColor,float outlineStrength,int cornerStrength){
		this.x=(int)x;
		this.y=(int)y;
		this.width=(int)width;
		this.height=(int)height;
		this.onclick=onclick;
		this.cornerStrength=cornerStrength;
		this.defaultColor=background;
		this.hoverColor=hoverColor;
		this.clickColor=clickColor;
		this.textColor=textColor;
		this.textShadowColor=textShadowColor;
		this.outlineColor=outlineColor;
		this.outlineStrength=outlineStrength;
		this.text=onclick.apply(-1);
	}

	/*
	 * Sets the default style values for every new class instance
	 */
	public static void setDefaults(int background,int hoverColor,int clickColor,int textColor,int textShadowColor,
			int outlineColor,int outlineStrength,int cornerStrength){
		defaultBgColor=background;
		defaultHoverColor=hoverColor;
		defaultClickColor=clickColor;
		defaultTextColor=textColor;
		defaultTextShadowColor=textShadowColor;
		defaultOutlineColor=outlineColor;
		defaultOutlineStrength=outlineStrength;
		defaultCornerStrength=cornerStrength;
	}

	@Override
	public boolean handleMousePressed(PApplet app){
		//Checks if the button gots clicked
		if(this.isHovered(app.mouseX,app.mouseY)){
			//Handles the onclick
			this.text=this.onclick.apply(app.mouseButton);
			return true;
		}

		return false;
	}
	
	@Override
	public void handleAfterMousePressed(PApplet app){}

	@Override
	public boolean handleMouseReleased(PApplet app){
		return false;
	}

	@Override
	public boolean handleKeyPressed(PApplet app){
		return false;
	}

	@Override
	public boolean handleKeyReleased(PApplet app){
		return false;
	}

	@Override
	public Entry<Boolean,RenderObject> handleRender(PApplet app,boolean usedBefore){
		//Gets if the button is hovered
		boolean hover=this.isHovered(app.mouseX,app.mouseY);
		//Gets what color the button should be rendered
		int col=hover && !usedBefore?(app.mousePressed?this.clickColor:this.hoverColor):this.defaultColor;

		//Returns a new entry with the new used boolean and the render object
		return new AbstractMap.SimpleEntry<Boolean,RenderObject>(hover,()->{

			//Calculates the real coords
			int[] coords = this.getRealCoordinates();
			
			//Renders the button
			app.stroke(this.outlineColor);
			app.strokeWeight(this.outlineStrength);
			app.fill(new Color(col).getRGB());
			app.rect(coords[0],coords[1],coords[2],coords[3],this.cornerStrength);

			//Renders the text
			app.textSize(Math.max(.01f,Math.min(coords[2],coords[3])) / 2);
			app.textAlign(PApplet.CENTER,PApplet.CENTER);
			//Checks if the text should be rendered as a shadow
			if(this.textShadowColor!=-1){
				app.fill(this.textShadowColor);
				app.text(this.text,coords[0] + coords[2]/ 2 - 2,coords[1] + coords[3] / 2.5f + 2);
			}
			app.fill(this.textColor);
			app.text(this.text,coords[0] + coords[2]/ 2,coords[1] + coords[3] / 2.5f);
		});
	}

	/*
	 * Returns if the button gets hovered
	 */
	private boolean isHovered(int mouseX,int mouseY){
		//Gets the real coordinates
		int[] coords = this.getRealCoordinates();
		
		//Returns if the mouse is inside the field
		return mouseX > coords[0] && mouseX < coords[0] + coords[2] && mouseY > coords[1] && mouseY < coords[1] + coords[3];
	}
	
	/*
	 * Returns the real coordinates depending on the size
	 * */
	private int[] getRealCoordinates() {
		return new int[] {
			(int)(this.x+this.width/2-this.width/2*this.size),
			(int)(this.y+this.height/2-this.height/2*this.size),
			(int)(this.width*this.size),
			(int)(this.height*this.size)
		};
	}

	//Return the x
	public final int getX(){
		return this.x;
	}

	//Return the y
	public final int getY(){
		return this.y;
	}

	//Return the width
	public final int getWidth(){
		return this.width;
	}

	//Return the height
	public final int getHeight(){
		return this.height;
	}

	//Return the defaultColor
	public final int getDefaultColor(){
		return this.defaultColor;
	}

	//Return the hoverColor
	public final int getHoverColor(){
		return this.hoverColor;
	}

	//Return the clickColor
	public final int getClickColor(){
		return this.clickColor;
	}

	//Return the text
	public final String getText(){
		return this.text;
	}

	//Sets x
	public GuiButton setX(int x){
		this.x=x;
		return this;
	}

	//Sets y
	public GuiButton setY(int y){
		this.y=y;
		return this;
	}

	//Sets width
	public GuiButton setWidth(int width){
		this.width=width;
		return this;
	}

	//Sets height
	public GuiButton setHeight(int height){
		this.height=height;
		return this;
	}

	//Sets defaultColor
	public GuiButton setDefaultColor(int defaultColor){
		this.defaultColor=defaultColor;
		return this;
	}

	//Sets hoverColor
	public GuiButton setHoverColor(int hoverColor){
		this.hoverColor=hoverColor;
		return this;
	}

	//Sets clickColor
	public GuiButton setClickColor(int clickColor){
		this.clickColor=clickColor;
		return this;
	}

	//Sets text
	public GuiButton setText(String text){
		this.text=text;
		return this;
	}

	//Return the textColor
	public final int getTextColor(){
		return this.textColor;
	}

	//Return the textShadowColor
	public final int getTextShadowColor(){
		return this.textShadowColor;
	}

	//Return the outlineColor
	public final int getOutlineColor(){
		return this.outlineColor;
	}

	//Return the outlineStrength
	public final float getOutlineStrength(){
		return this.outlineStrength;
	}

	//Sets textColor
	public final GuiButton setTextColor(int textColor){
		this.textColor=textColor;
		return this;
	}

	//Sets textShadowColor
	public final GuiButton setTextShadowColor(int textShadowColor){
		this.textShadowColor=textShadowColor;
		return this;
	}

	//Sets outlineColor
	public final GuiButton setOutlineColor(int outlineColor){
		this.outlineColor=outlineColor;
		return this;
	}

	//Sets outlineStrength
	public final GuiButton setOutlineStrength(float outlineStrength){
		this.outlineStrength=outlineStrength;
		return this;
	}

	//Return the size
	public final float getSize(){
		return this.size;
	}

	//Sets size
	public final GuiButton setSize(float size){
		this.size=size;
		return this;
	}
}
