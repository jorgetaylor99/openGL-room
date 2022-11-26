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

public class Room {

  private Model[] wall;
  private Camera camera;
  private Light light;
  private float size = 16f;
  private Texture t0, t1, texture_cloud, texture_wood, texture_brick;

  public Room(GL3 gl, Camera c, Light l) {
    camera = c;
    light = l;
    this.t0 = t0;
    this.t1 = t1;
    loadTextures(gl);
    wall = new Model[6];
    wall[0] = makeFloor(gl);
    wall[1] = makeRoof(gl);
    wall[2] = makeRightWall(gl);
    wall[3] = makeLeftWall(gl);
    wall[4] = makeFrontWall(gl);
    wall[5] = makeBackWall(gl);
  }

  private void loadTextures(GL3 gl) {
    texture_cloud = TextureLibrary.loadTexture(gl, "textures/cloud.jpg");
    texture_wood = TextureLibrary.loadTexture(gl, "textures/wood.jpg");
    texture_brick = TextureLibrary.loadTexture(gl, "textures/brick.jpg");
  }

  // There is repetion in each of the following methods 
  // An alternative would attempt to remove the repetition

  // Floor
  private Model makeFloor(GL3 gl) {
    // grey basecolor with main colour given by texture map
    Vec3 basecolor = new Vec3(0.5f, 0.5f, 0.5f); // grey
    Material material = new Material(basecolor, basecolor, new Vec3(0.3f, 0.3f, 0.3f), 4.0f);
    Mat4 modelMatrix = new Mat4(1);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(size,1f,size), modelMatrix);
    Mesh mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    Shader shader = new Shader(gl, "shaders/vertex/vs_tt_05.txt", "shaders/fragment/fs_tt_05.txt");
    Model model = new Model(gl, camera, light, shader, material, modelMatrix, mesh, texture_wood);
    return model;
  }

  // Roof
  private Model makeRoof(GL3 gl) {
    // grey basecolor with main colour given by texture map
    Vec3 basecolor = new Vec3(0.5f, 0.5f, 0.5f); // grey
    Material material = new Material(basecolor, basecolor, new Vec3(0.3f, 0.3f, 0.3f), 4.0f);
    Mat4 modelMatrix = new Mat4(1);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(size,1.0f,size), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(180), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,size*0.8f,0), modelMatrix);
    Mesh mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    Shader shader = new Shader(gl, "shaders/vertex/vs_tt_05.txt", "shaders/fragment/fs_tt_05.txt");
    Model model = new Model(gl, camera, light, shader, material, modelMatrix, mesh, texture_wood);
    return model;
  }

  // Right wall
  private Model makeRightWall(GL3 gl) {
    Vec3 basecolor = new Vec3(0.5f, 0.5f, 0.5f); // grey
    Material material = new Material(basecolor, basecolor, new Vec3(0.3f, 0.3f, 0.3f), 4.0f);
    Mat4 modelMatrix = new Mat4(1);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(size,1f,size*0.8f), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(-90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(-size*0.5f,size*0.4f,0), modelMatrix);
    Mesh mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    Shader shader = new Shader(gl, "shaders/vertex/vs_tt_05.txt", "shaders/fragment/fs_tt_05.txt");
    // no texture on this model
    Model model = new Model(gl, camera, light, shader, material, modelMatrix, mesh, texture_brick);
    return model;
  }

  // Left wall
  private Model makeLeftWall(GL3 gl) {
    Vec3 basecolor = new Vec3(0.5f, 0.5f, 0.5f); // grey
    Material material = new Material(basecolor, basecolor, new Vec3(0.3f, 0.3f, 0.3f), 4.0f);
    Mat4 modelMatrix = new Mat4(1);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(size,1f,size*0.8f), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(size*0.5f,size*0.4f,0), modelMatrix);
    Mesh mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    Shader shader = new Shader(gl, "shaders/vertex/vs_tt_05.txt", "shaders/fragment/fs_tt_05.txt");
    // no texture on this model
    Model model = new Model(gl, camera, light, shader, material, modelMatrix, mesh, texture_brick);
    return model;
  }
  
  // Close wall
  private Model makeFrontWall(GL3 gl) {
    // grey basecolor with main colour given by texture map
    Vec3 basecolor = new Vec3(0.5f, 0.5f, 0.5f); // grey
    Material material = new Material(basecolor, basecolor, new Vec3(0.3f, 0.3f, 0.3f), 4.0f);
    Mat4 modelMatrix = new Mat4(1);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(size,1f,size*0.8f), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,size*0.4f,-size*0.5f), modelMatrix);
    Mesh mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    Shader shader = new Shader(gl, "shaders/vertex/vs_tt_05.txt", "shaders/fragment/fs_tt_05.txt");
    Model model = new Model(gl, camera, light, shader , material, modelMatrix, mesh, texture_brick);
    return model;
  }

  // Background
  private Model makeBackWall(GL3 gl) {
    // grey basecolor with main colour given by texture map
    Vec3 basecolor = new Vec3(0.5f, 0.5f, 0.5f); // grey
    Material material = new Material(basecolor, basecolor, new Vec3(0.3f, 0.3f, 0.3f), 4.0f);
    Mat4 modelMatrix = new Mat4(1);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(size*2.0f,1f,size*2.0f), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(-90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,size*0.5f,size*2.0f), modelMatrix);
    Mesh mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    Shader shader = new Shader(gl, "shaders/vertex/vs_tt_05.txt", "shaders/fragment/fs_tt_05.txt");
    double elapsedTime = getSeconds() - startTime;
    double t = elapsedTime*0.1;  // *0.1 slows it down a bit
    float offsetX = (float)(t - Math.floor(t));
    float offsetY = 0.0f;
    shader.setFloat(gl, "offset", offsetX, offsetY);
    Model model = new Model(gl, camera, light, shader , material, modelMatrix, mesh, texture_cloud);
    return model;
  }

  private double startTime;
  
  private double getSeconds() {
    return System.currentTimeMillis()/1000.0;
  }

  public void render(GL3 gl) {
    for (int i=0; i<=5; i++) {
      wall[i].render(gl);
    }
  }

  public void dispose(GL3 gl) {
    for (int i=0; i<=5; i++) {
      wall[i].dispose(gl);
    }
  }
}