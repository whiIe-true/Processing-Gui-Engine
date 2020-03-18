import de.whiletrue.processingguiengine.GuiEngine;
import de.whiletrue.processingguiengine.components.GuiSlider;
import de.whiletrue.processingguiengine.components.GuiSliderValues;
import processing.core.PApplet;

public class Main extends PApplet{

	private GuiEngine engine = new GuiEngine(this);
	
	public static void main(String[] args){
		main(Main.class);
	}
	
	@Override
	public void settings(){
		size(500,500);
	}
	
	@Override
	public void setup(){
		int w = this.width;
		int h = this.height;
		
		GuiSlider.setDefaults(0xff292929,0xff6C6C6C,0,255,3);
		
//		this.engine.addComp(new GuiSlider(w*.3f,h*.4f,w*.4f,h*.06f,20,40,22,(i,val)->{
//			
//			return val+"";
//		},""));
		
		String[] values = {"a","b","c","d","e"};
		
		this.engine.addComp(new GuiSliderValues(w*.3f,h*.4f,w*.4f,h*.06f,values,0,(i,val)->{
			
			return val;
		},""));
	}
	
	@Override
	public void draw(){
		this.engine.draw();
	}
	
	@Override
	public void mouseReleased(){
		this.engine.mouseReleased();
	}
	
	@Override
	public void mousePressed(){
		this.engine.mousePressed();
	}
	
	@Override
	public void keyReleased(){
		this.engine.keyReleased();
	}
	
	@Override
	public void keyPressed(){
		this.engine.keyPressed();
	}
	
}
