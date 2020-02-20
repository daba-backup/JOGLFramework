# JOGLFramework

JOGLFramework (abbr. JOGLF) aims to simplify OpenGL-relating code in Java.

# Overview

## Install

Locate the *Data* folder at the current directory of your program.

Direct link:https://github.com/Dabasan/JOGLFramework/releases/download/v9.0.0/Data.zip

After setting up *Data*, get the required JARs.

### Maven project

#### GL module

```xml
<dependency>
    <groupId>com.github.dabasan</groupId>
    <artifactId>joglframework-gl</artifactId>
    <version>9.1.0</version>
</dependency>
```

#### AL module

```xml
<dependency>
    <groupId>com.github.dabasan</groupId>
    <artifactId>joglframework-al</artifactId>
    <version>9.1.0</version>
</dependency>
```

## Demo (Runnable JAR)

### ShowTeapot

Download:[Google Drive](https://drive.google.com/open?id=1H20f_pgWRLw1lILl9wQkQIhLtOPMSQHo)

<img src="https://i.imgur.com/Pqgv1u3.png" alt="Utah teapot"  />

## Example

### MyWindow.java

```java
import com.daxie.basis.coloru8.ColorU8Functions;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.joglf.gl.draw.GLDrawFunctions3D;
import com.daxie.joglf.gl.front.CameraFront;
import com.daxie.joglf.gl.window.JOGLFWindow;

public class MyWindow extends JOGLFWindow{
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
		CameraFront.SetCameraPositionAndTarget_UpVecY(
				VectorFunctions.VGet(30.0f, 30.0f, 30.0f), 
				VectorFunctions.VGet(0.0f, 0.0f, 0.0f));
	}
	@Override
	protected void Draw() {
		GLDrawFunctions3D.DrawAxes(100.0f);
		GLDrawFunctions3D.DrawSphere3D(
				VectorFunctions.VGet(0.0f, 0.0f, 0.0f), 10.0f, 32, 32, 
				ColorU8Functions.GetColorU8(1.0f, 1.0f, 1.0f, 1.0f));
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
import com.daxie.joglf.gl.window.JOGLFWindow;
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

<img src="https://i.imgur.com/CvHyAIm.png" alt="MyWindow"  />

## Samples

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

