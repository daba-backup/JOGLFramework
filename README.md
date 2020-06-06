# JOGLFramework

This is a hobby project that obviates the need for direct calls to OpenGL functions in my personal graphics programs.

# Overview

## Requirements

Java SE 11+

## Installation

1. Download *[Data](https://github.com/Dabasan/JOGLFramework/releases/download/v11.6.0/Data.zip)* and extract it
2. Move the *Data* folder to the same directory as your program

### Maven project

#### GL module

```xml
<dependency>
    <groupId>com.github.dabasan</groupId>
    <artifactId>joglframework-gl</artifactId>
    <version>11.6.0</version>
</dependency>
```

#### AL module

```xml
<dependency>
    <groupId>com.github.dabasan</groupId>
    <artifactId>joglframework-al</artifactId>
    <version>11.6.0</version>
</dependency>
```

## Example

### DrawModelWindow.java

```java
import static com.github.dabasan.basis.vector.VectorFunctions.*;

import com.github.dabasan.joglf.gl.front.CameraFront;
import com.github.dabasan.joglf.gl.model.Model3DFunctions;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;

class DrawModelWindow extends JOGLFWindow {
	private int model_handle;

	@Override
	public void Init() {
		model_handle = Model3DFunctions.LoadModel("./Data/Model/BD1/Cube/cube.bd1");
	}

	@Override
	public void Update() {
		CameraFront.SetCameraPositionAndTarget_UpVecY(VGet(30.0f, 30.0f, 30.0f),
				VGet(0.0f, 0.0f, 0.0f));
	}

	@Override
	public void Draw() {
		Model3DFunctions.DrawModel(model_handle);
	}
}
```

### ModelMain.java

```java
public class ModelMain {
	public static void main(String[] args) {
		new ModelMain();
	}
	public ModelMain() {
		var window = new DrawModelWindow();
		window.SetTitle("Draw Model");
	}
}
```

<img src="https://i.imgur.com/SQhECaA.png" width="320">

## Sample code

Sample code is available in [JOGLFramework3Samples](https://github.com/Dabasan/JOGLFramework3Samples).

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

See also: [LICENSES](./LICENSES)

## Todo

- Provide add-ons for specific usage (spotlight, shadow, etc.)

# External links

- [JOGL - Java Binding for the OpenGL API](https://jogamp.org/jogl/www/)
  Official website of JOGL
- [HDRI Haven](https://hdrihaven.com/) 
- [HDRI to Cubemap](https://matheowis.github.io/HDRI-to-CubeMap/) 
- [Good Textures](https://www.goodtextures.com/)

