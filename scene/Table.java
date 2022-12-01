package scene;

import gmaths.*;
import camera.*;
import structures.*;
import objects.*;
import light.*;
import textures.*;

import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;
import com.jogamp.opengl.util.texture.*;

public class Table {

  private Model[] table;
  private Camera camera;
  private Light light;
  private float size = 16f;
  private Texture t0, t1, texture_table;

  public Table(GL3 gl, Camera c, Light l) {
    camera = c;
    light = l;
    this.t0 = t0;
    this.t1 = t1;
    loadTextures(gl);
    table = new Model[6];
    table[0] = makeLeg1(gl);
    table[1] = makeLeg2(gl);
    table[2] = makeLeg3(gl);
    table[3] = makeLeg4(gl);
    table[4] = makeTabletop(gl);
    table[5] = makeStand(gl);
  }

  private void loadTextures(GL3 gl) {
    texture_table = TextureLibrary.loadTexture(gl, "textures/wood_table.jpg");
  }

  private Model makeLeg1(GL3 gl) {
    Mesh mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    Shader shader = new Shader(gl, "shaders/vertex/cube.glsl", "shaders/fragment/cube.glsl");
    Material material = new Material(new Vec3(1.0f, 0.5f, 0.31f), 
                            new Vec3(1.0f, 0.5f, 0.31f), 
                            new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    Mat4 modelMatrix = new Mat4(1);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.5f,3.0f,0.5f), 
                                Mat4Transform.translate(3.0f,0.5f,3.0f));
    Model cube = new Model(gl, camera, light, shader, material, modelMatrix, mesh, texture_table);
    return cube;
  }

  private Model makeLeg2(GL3 gl) {
    Mesh mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    Shader shader = new Shader(gl, "shaders/vertex/cube.glsl", "shaders/fragment/cube.glsl");
    Material material = new Material(new Vec3(1.0f, 0.5f, 0.31f), 
                            new Vec3(1.0f, 0.5f, 0.31f), 
                            new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    Mat4 modelMatrix = new Mat4(1);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.5f,3.0f,0.5f), 
                                Mat4Transform.translate(3.0f,0.5f,-3.0f));
    Model cube = new Model(gl, camera, light, shader, material, modelMatrix, mesh, texture_table);
    return cube;
  }

  private Model makeLeg3(GL3 gl) {
    Mesh mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    Shader shader = new Shader(gl, "shaders/vertex/cube.glsl", "shaders/fragment/cube.glsl");
    Material material = new Material(new Vec3(1.0f, 0.5f, 0.31f), 
                            new Vec3(1.0f, 0.5f, 0.31f), 
                            new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    Mat4 modelMatrix = new Mat4(1);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.5f,3.0f,0.5f), 
                                Mat4Transform.translate(-3.0f,0.5f,3.0f));
    Model cube = new Model(gl, camera, light, shader, material, modelMatrix, mesh, texture_table);
    return cube;
  }

  private Model makeLeg4(GL3 gl) {
    Mesh mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    Shader shader = new Shader(gl, "shaders/vertex/cube.glsl", "shaders/fragment/cube.glsl");
    Material material = new Material(new Vec3(1.0f, 0.5f, 0.31f), 
                            new Vec3(1.0f, 0.5f, 0.31f), 
                            new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    Mat4 modelMatrix = new Mat4(1);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.5f,3.0f,0.5f), 
                                Mat4Transform.translate(-3.0f,0.5f,-3.0f));
    Model cube = new Model(gl, camera, light, shader, material, modelMatrix, mesh, texture_table);
    return cube;
  }

  private Model makeTabletop(GL3 gl) {
    Mesh mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    Shader shader = new Shader(gl, "shaders/vertex/cube.glsl", "shaders/fragment/cube.glsl");
    Material material = new Material(new Vec3(1.0f, 0.5f, 0.31f), 
                            new Vec3(1.0f, 0.5f, 0.31f), 
                            new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    Mat4 modelMatrix = new Mat4(1);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(3.5f,0.5f,3.5f), 
                                Mat4Transform.translate(0.0f,6.5f,0.0f));
    Model cube = new Model(gl, camera, light, shader, material, modelMatrix, mesh, texture_table);
    return cube;
  }

  private Model makeStand(GL3 gl) {
    Mesh mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    Shader shader = new Shader(gl, "shaders/vertex/cube.glsl", "shaders/fragment/cube.glsl");
    Material material = new Material(new Vec3(1.0f, 0.5f, 0.31f), 
                            new Vec3(1.0f, 0.5f, 0.31f), 
                            new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    Mat4 modelMatrix = new Mat4(1);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(1.0f,0.5f,1.0f), 
                                Mat4Transform.translate(0.0f,7.5f,0.0f));
    Model cube = new Model(gl, camera, light, shader, material, modelMatrix, mesh, texture_table);
    return cube;
  }

  private double startTime;
  
  private double getSeconds() {
    return System.currentTimeMillis()/1000.0;
  }

  public void render(GL3 gl) {
    for (int i=0; i<6; i++) {
      table[i].render(gl);
    }
  }

  public void dispose(GL3 gl) {
    for (int i=0; i<6; i++) {
      table[i].dispose(gl);
    }
  }
}