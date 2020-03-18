# Processing Gui-engine
This engine is a side project to handle gui stuff for me.
Im using the [Eclipse IDE](https://www.eclipse.org/), so there is no version with a .pde file. You can't use it with Processing IDE only. To use Eclipse or any other IDE with processing, read [this](https://riptutorial.com/processing/example/31227/using-processing-with-eclipse).

# Features
* Includes
  * Textfields
  * Checkboxes
  * Sliders
  * Buttons
  * Imagebuttons
* Can handle multiple elements on top of each other without executing anything by a click or hover.


# How to use
To use it you have to put the files into your processing Project (either the source files or the .jar as a library include)

First you need to create a GuiEngine object and parse the Papplet (Processing instance) to it.

```
void setup(){
	GuiEngine engine = new GuiEngine(this);
    //...
}
```

Now to add any element call the GuiEngine#addComp method and parse it any gui element.
Some of the gui elements require async events. You can use Lambdas for this events.

```
void setup(){

	//...

	//Defines the position values for the textfield
	int x=20,y=50,width=200,height=50;

	//Defines the event that handles it when the user writes something
	//You have to return an boolean that can cancle the type event with a false
	TypeListener onTypeEvent = text->{
		System.out.println("I wrote something: "+text);
		return true;
	};

	//Defines the colors for the textfields states
	int textColor=Color.blue.getRGB(),
	fillColor=Color.black.getRGB(),
	outlineColor=Color.white.getRGB(),
	presetTextColor=Color.cyan.getRGB();

	//Default text for the textfield
	String defaultText=null;
	
	//Text that gets displayed when the textfield is empty
	String presetText="Name input";

	//Creates an adds the textfield
	this.engine.addComp(
		new GuiTextfield(
			x,
			y,
			width,
			height,
			onTypeEvent,
			defaultText,
			presetText,
			textColor,
			fillColor,
			outlineColor,
			presetTextColor)
	);
}

```

On every of the following events call the gui engine like this

```
@Override
public void draw(){
	this.engine.draw();
}

@Override
public void mousePressed(){
	this.engine.mousePressed();
}

@Override
public void mouseReleased(){
	this.engine.mouseReleased();
}

@Override
public void keyPressed(){
	this.engine.keyPressed();
}

@Override
public void keyReleased(){
	this.engine.keyReleased();
}
```

If you want to set default styles for any gui component call the static GuiComponent#setDefaults method on the class.
Here is an example for the GuiButton

```
	GuiButton.setDefaults(0xffA00000,0xff717171,0xffFFFFFF,0xffF50000,0xffACACAC,0,0);
```