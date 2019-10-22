# JOGLFramework

JOGLFramework aims to simplify OpenGL-relating processing by wrapping raw functions provided by Java OpenGL (JOGL).

It also provides several wrapper functions for Java OpenAL (JOAL).

# Overview

## Features

- 3D model management
- 3D hitcheck functions
- Texture management
- Play WAV and MP3
- Multiwindow support

## Install

Locate the *Data* folder at the current directory of your program.

Direct link:https://github.com/Dabasan/JOGLFramework/releases/download/v3.0.0/Data.zip

After setting up *Data*, you need to get required jars.

### Java project

Download all required jars and add them to the classpath.

### Maven project

```xml
<dependency>
    <groupId>com.github.dabasan</groupId>
    <artifactId>joglframework</artifactId>
    <version>3.0.3</version>
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

- JOGL
- GlueGen

They are mostly licensed under the New BSD 2-Clause License.

#### BSD License

- JOAL

#### LGPL

- JLayer

#### Other

##### Obj

> www.javagl.de - Obj
>
> Copyright (c) 2008-2015 Marco Hutter - http://www.javagl.de
>
> Permission is hereby granted, free of charge, to any person
> obtaining a copy of this software and associated documentation
> files (the "Software"), to deal in the Software without
> restriction, including without limitation the rights to use,
> copy, modify, merge, publish, distribute, sublicense, and/or sell
> copies of the Software, and to permit persons to whom the
> Software is furnished to do so, subject to the following
> conditions:
>
> The above copyright notice and this permission notice shall be
> included in all copies or substantial portions of the Software.
>
> THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
> EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
> OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
> NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
> HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
> WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
> FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
> OTHER DEALINGS IN THE SOFTWARE.

# External links

- [JOGL - Java Binding for the OpenGL API](https://jogamp.org/jogl/www/)
  Official website of JOGL

------

- [Twitter](https://twitter.com/Daxie_tksm6)
  My twitter account