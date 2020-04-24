package de.whiletrue.processingguiengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;

import processing.core.PApplet;

public class GuiEngine {

	private PApplet app;
	private List<GuiComponent> loadedComponents = new ArrayList<GuiComponent>();
	
	
	public GuiEngine(PApplet app) {
		this.app = app;
	}
	
	/*
	 * Removes a GUI-component
	 * */
	public void removeComponent(GuiComponent component) {
		this.loadedComponents.remove(component);
	}
	
	/*
	 * Adds a GUI-component
	 * */
	public void addComponent(GuiComponent component) {
		this.loadedComponents.add(component);
	}
	
	/*
	 * Gets the requested component
	 * */
	public GuiComponent getComponent(int index) {
		return this.loadedComponents.get(index);
	}
	
	/*
	 * Clears all components
	 * */
	public void clearComponents() {
		this.loadedComponents.clear();
	}
	
	/*
	 * Adds a gui component
	 * */
	@Deprecated
	public void addComp(GuiComponent comp) {
		this.loadedComponents.add(comp);
	}
	
	/*
	 * Removes a gui component
	 * */
	@Deprecated
	public void removeComp(GuiComponent comp) {
		this.loadedComponents.remove(comp);
	}
	
	/*
	 * Should be called whenever the screen updates and gets rendered
	 * */
	public void draw() {
		//Defines the used variable that defines if any object before has activly used the mouse
		boolean used = false;
		
		//Creates an empty array with renderobjects
		RenderObject[] renderObjects = new RenderObject[this.loadedComponents.size()];
		
		//Iterates over all components
		for(int i = this.loadedComponents.size()-1; i >= 0; i--) {
			//Gets the component
			GuiComponent comp = this.loadedComponents.get(i);

			//Checks if the component is hidden
			if(comp.isHidden())
				continue;
			
			//Gets the rendering object and if they activly used the mouse position
			Entry<Boolean,RenderObject> entry = comp.handleRender(this.app, used);

			
			//Sets the used variable
			used |= entry.getKey();
			
			//Adds the renderobject reverse to the order before
			renderObjects[i] = entry.getValue();
		}
		
		//Iterates over all render object
		Arrays.stream(renderObjects)
		//Checks if the button is given
		.filter(i->i!=null)
		//Renders it
		.forEach(i->i.execute());
	}
	
	/*
	 * Should be called whenever the mouse gets pressed
	 * */
	public void mousePressed() {
		//Component on that the event got used
		AtomicReference<GuiComponent>finalComp=new AtomicReference<>();
		
		
		//Iterates over all components
		for(int i = this.loadedComponents.size()-1; i >= 0; i--) {
			//Gets the component
			GuiComponent comp = this.loadedComponents.get(i);

			//Checks if the component is hidden
			if(comp.isHidden())
				continue;
			
			//Executes the event and checks if if used the mouse
			if(comp.handleMousePressed(this.app)) {
				//Sets the component
				finalComp.set(comp);
				//Exits the loop
				break;
			}
		}
		
		//Executes the after events on all components
		this.loadedComponents.stream()
		.filter(i->i!=finalComp.get())
		.forEach(i->i.handleAfterMousePressed(this.app));
	}
	
	/*
	 * Should be called whenever the mouse gets released
	 * */
	public void mouseReleased() {
		//Iterates over all components
		for(int i = this.loadedComponents.size()-1; i >= 0; i--) {
			//Gets the component
			GuiComponent comp = this.loadedComponents.get(i);

			//Checks if the component is hidden
			if(comp.isHidden())
				continue;
			
			//Executes the event and checks if if used the mouse
			if(comp.handleMouseReleased(this.app))
				//Exits the loop
				break;
		}
	}
	
	/*
	 * Should be called whenever a key gets pressed
	 * */
	public void keyPressed() {
		//Iterates over all components
		for(int i = this.loadedComponents.size()-1; i >= 0; i--) {
			//Gets the component
			GuiComponent comp = this.loadedComponents.get(i);

			//Checks if the component is hidden
			if(comp.isHidden())
				continue;
			
			//Executes the event and checks if if used the mouse
			if(comp.handleKeyPressed(this.app))
				//Exits the loop
				break;
		}
	}
	
	/*
	 * Should be called whenever a key gets pressed
	 * */
	public void keyReleased() {
		//Iterates over all components
		for(int i = this.loadedComponents.size()-1; i >= 0; i--) {
			//Gets the component
			GuiComponent comp = this.loadedComponents.get(i);

			//Checks if the component is hidden
			if(comp.isHidden())
				continue;
			
			//Executes the event and checks if if used the mouse
			if(comp.handleKeyReleased(this.app))
				//Exits the loop
				break;
		}
	}
	
}
