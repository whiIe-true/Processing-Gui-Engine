package de.whiletrue.processingguiengine.components.defaults;

public abstract class SizeComponent extends CoordinateComponent{

	//Size of the component
	protected float size=1;

	public SizeComponent(float x,float y,float width,float height){
		super(x,y,width,height);
	}

	/*
	 * Sets the size of the component
	 * */
	public void setSize(float size) {
		this.size=size;
	}
	
	/*
	 * Returns the real coordinates depending on the size
	 * */
	protected float[] getRealCoordinates() {
		return new float[] {
			this.x+this.width/2-this.width/2*this.size,
			this.y+this.height/2-this.height/2*this.size,
			this.width*this.size,
			this.height*this.size
		};
	}
	
	@Override
	public boolean isHovered(int mouseX,int mouseY){
		//Gets the real coordinates
		float[] coords = this.getRealCoordinates();
		
		//Returns if the mouse is inside the field
		return mouseX > coords[0] && mouseX < coords[0] + coords[2] && mouseY > coords[1] && mouseY < coords[1] + coords[3];
	}
	
}
