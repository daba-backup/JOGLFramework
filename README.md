# JOGLFramework

JOGLFramework aims to simplify OpenGL-relating processing by wrapping raw functions provided by Java OpenGL (JOGL).

It also provides several wrapper functions for Java OpenAL (JOAL).

# Overview

## Features

- 3D model management
- Texture management
- Play WAV and MP3

## Install

### Maven

Add these lines to pom.xml in your project.

```xml
<groupId>com.github.dabasan</groupId>
<artifactId>joglframework</artifactId>
<version>1.0.0</version>
```

## Usage

Below is a short snippet.

------

```java
public class Main extends MainBase{
	public static void main(String[] args) {
		new Main(GLVersion.GL4);
	}
	public Main(GLVersion version) {
		super(version);
	}
	
	//init() is called when the OpenGL context is created.
	@Override
	public void init(GLAutoDrawable drawable) {
		super.init(drawable);
		
		System.out.println("init");
	}
	//reshape() is called when the main window is resized.
	@Override
	public void reshape(GLAutoDrawable drawable,int x,int y,int width,int height) {
		super.reshape(drawable, x, y, width, height);
		
		System.out.println("reshape");
	}
	//display() is called every frame.
	@Override
	public void display(GLAutoDrawable drawable) {
		super.display(drawable);
		
		//Close the window if ESC is pressed.
		if(KeyboardFront.GetKeyboardPressingCount(KeyboardEnum.KEY_ESCAPE)==1) {
			WindowFront.CloseWindow();
		}
	}
	//dispose() is called when the OpenGL context is destroyed.
	@Override
	public void dispose(GLAutoDrawable drawable) {
		super.dispose(drawable);
		
		System.out.println("dispose");
	}
}
```

## Sample

Samples are available in [JOGLFrameworkSamples](https://github.com/Dabasan/JOGLFrameworkSamples).

## Dependencies

- [JOGL (Java Binding for the OpenGL API)](https://github.com/sgothel/jogl)
  Java OpenGL
- [JOAL (Java Binding for the OpenAL API)](https://github.com/sgothel/joal)
  Java OpenAL
- [GlueGen](https://github.com/sgothel/gluegen)
  JNI Glue Code Generator
- [Obj](https://github.com/javagl/Obj)
  Wavefront OBJ file loader
- [JLayer](https://github.com/pdudits/soundlibs/tree/master/jlayer)
  MP3 decoder

------

- [DHLog](https://github.com/Dabasan/DHLog)
  Log output
- [DHTool](https://github.com/Dabasan/DHTool)
  Fundamental tools for my Java programs
- [DH3DBasis](https://github.com/Dabasan/DH3DBasis)
  Basic classes for 3D operations

## License

JOGLFramework is released under the MIT license.

# External links

- [JOGL - Java Binding for the OpenGL API](https://jogamp.org/jogl/www/)
  Official website of JOGL

------

- [Twitter](https://twitter.com/Daxie_tksm6)
  My twitter account