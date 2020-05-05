# JOGLFramework

JOGLFramework (abbr. JOGLF) aims to simplify OpenGL-relating code in Java.

# Overview

## Install

Locate the *Data* folder at the current directory of your program.

Direct link:https://github.com/Dabasan/JOGLFramework/releases/download/v11.0.0/Data.zip

After setting up *Data*, get the required JARs.

### Maven project

#### GL module

```xml
<dependency>
    <groupId>com.github.dabasan</groupId>
    <artifactId>joglframework-gl</artifactId>
    <version>11.0.1</version>
</dependency>
```

#### AL module

```xml
<dependency>
    <groupId>com.github.dabasan</groupId>
    <artifactId>joglframework-al</artifactId>
    <version>11.0.1</version>
</dependency>
```

## Demo (Runnable JAR)

### ReflectionMapping

Download:[Google Drive](https://drive.google.com/open?id=19AIKvqsXXTAp2hM0yGhlSzc-jdK61NW2)

![Imgur](https://i.imgur.com/gRG3SOH.jpg)

## Example

### MyWindow.java

```java
import static com.github.dabasan.basis.coloru8.ColorU8Functions.*;
import static com.github.dabasan.basis.vector.VectorFunctions.*;

import com.github.dabasan.joglf.gl.draw.DrawFunctions3D;
import com.github.dabasan.joglf.gl.front.CameraFront;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;

class MyWindow extends JOGLFWindow {
	@Override
	public void Init() {
		System.out.println("Init");
	}
	@Override
	public void Reshape(int x, int y, int width, int height) {
		System.out.println("Reshape");
	}
	@Override
	public void Update() {
		CameraFront.SetCameraPositionAndTarget_UpVecY(VGet(30.0f, 30.0f, 30.0f),
				VGet(0.0f, 0.0f, 0.0f));
	}
	@Override
	public void Draw() {
		DrawFunctions3D.DrawAxes(100.0f);
		DrawFunctions3D.DrawSphere3D(VGet(0.0f, 0.0f, 0.0f), 10.0f, 32, 32,
				GetColorU8(1.0f, 1.0f, 1.0f, 1.0f));
	}
	@Override
	public void Dispose() {
		System.out.println("Dispose");
	}
}
```

### MyWindowTestMain.java

```java
import com.github.dabasan.joglf.gl.window.JOGLFWindowInterface;

public class MyWindowTestMain {
	public static void main(String[] args) {
		new MyWindowTestMain();
	}
	public MyWindowTestMain() {
		JOGLFWindowInterface window = new MyWindow();
		window.SetTitle("MyWindow");
	}
}
```

![Imgur](https://i.imgur.com/ik8Dn3c.png)

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
- [logback](https://github.com/qos-ch/logback)
  Logging framework

------

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
- [logback](https://github.com/qos-ch/logback/blob/master/LICENSE.txt)

#### Other

- [Obj](https://github.com/javagl/Obj/blob/master/LICENSE.txt)

# External links

- [JOGL - Java Binding for the OpenGL API](https://jogamp.org/jogl/www/)
  Official website of JOGL

