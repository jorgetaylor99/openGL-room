# OpenGL Room Scene

## Introduction

This project involves using modern OpenGL to render a room scene, incorporating a table, an egg-like object, two angle-poise lamps, and a window. The scene can be modeled using transformed planes, cubes, and spheres.

## Learning Outcomes

- Use data structures and mathematics in representing and manipulating 3D objects
- Produce interactive software that makes use of a graphics API

## Scene Description

A room scene for an advert is to be created for a range of new angle-poise lamps. These lamps are inspecting an egg-like object, which occasionally jumps and twists as if something inside is trying to get out. The room includes three walls and a floor, a window with a dynamic outside scene, and the lamps themselves.

## Setup and Running

To run the program, execute the `start.bat` file, which compiles and runs the Java classes in the correct order. Specifically, it executes the following commands:

```bash
javac camera/*.java
javac gmaths/*.java
javac light/*.java
javac nodes/*.java
javac objects/*.java
javac scene/*.java
javac structures/*.java
javac textures/*.java
javac *.java
java Hatch
```

This will start the application, and you can begin interacting with the scene.
