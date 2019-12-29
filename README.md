# JOGLFramework

JOGLFramework (abbr. JOGLF) aims to simplify OpenGL-relating code by wrapping functions provided by Java OpenGL (JOGL).

It also provides several wrapper functions for Java OpenAL (JOAL).

# Overview

## Install

Locate the *Data* folder at the current directory of your program.

Direct link:https://github.com/Dabasan/JOGLFramework/releases/download/v4.0.0/Data.zip

After setting up *Data*, you need to get required jars.

### Java project

Download all required jars and add them to the classpath.

### Maven project

```xml
<dependency>
    <groupId>com.github.dabasan</groupId>
    <artifactId>joglframework</artifactId>
    <version>4.0.1</version>
</dependency>
```

## Example

### MyWindow.java

```java
import com.daxie.joglf.gl.window.JOGLFWindow;

public class MyWindow extends JOGLFWindow{
	public MyWindow() {
		
	}
	
	@Override
	protected void Init() {
		System.out.println("Init");
	}
	@Override
	protected void Reshape(int x,int y,int width,int height) {
		System.out.println("Reshape");
	}
	@Override
	protected void Update() {
		System.out.println("Update");
	}
	@Override
	protected void Draw() {
		System.out.println("Draw");
	}
	@Override
	protected void Dispose() {
		System.out.println("Dispose");
	}
}
```

### Main.java

```java
import com.daxie.joglf.gl.front.GLFront;
import com.daxie.joglf.gl.wrapper.GLVersion;

public class Main {
	public static void main(String[] args) {
		new Main();
	}
	public Main() {
		GLFront.Setup(GLVersion.GL4);
		
		JOGLFWindow window=new MyWindow();
		window.SetTitle("MyWindow");
	}
}
```

## Sample

Samples are available in [JOGLFramework2Samples](https://github.com/Dabasan/JOGLFramework2Samples).

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

### Dependencies

####  New BSD 2-Clause License

- [JOGL](https://github.com/sgothel/jogl/blob/master/LICENSE.txt)
- [GlueGen](https://github.com/sgothel/gluegen/blob/master/LICENSE.txt)

They are mostly licensed under the New BSD 2-Clause License.

#### BSD License

- [JOAL](https://github.com/sgothel/joal/blob/master/LICENSE.txt)

#### LGPL

- [JLayer](https://github.com/pdudits/soundlibs/blob/master/jlayer/LICENSE.txt)

#### Other

- [Obj](https://github.com/javagl/Obj/blob/master/LICENSE.txt)

# External links

- [JOGL - Java Binding for the OpenGL API](https://jogamp.org/jogl/www/)
  Official website of JOGL

