import camera.*;
import gmaths.*;
import light.*;
import textures.*;

import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;
import com.jogamp.opengl.util.texture.*;
  
public class Hatch_GLEventListener implements GLEventListener {
  
  private static final boolean DISPLAY_SHADERS = false;
  private Camera camera;
    
  /* The constructor is not used to initialise anything */
  public Hatch_GLEventListener(Camera camera) {
    this.camera = camera;
    this.camera.setPosition(new Vec3(0.0f,9.0f,-18.0f));
    this.camera.setTarget(new Vec3(0.0f,6.0f,0.0f));
  }
  
  // ***************************************************
  /*
   * METHODS DEFINED BY GLEventListener
   */

  /* Initialisation */
  public void init(GLAutoDrawable drawable) {   
    GL3 gl = drawable.getGL().getGL3();
    System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());
    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); 
    gl.glClearDepth(1.0f);
    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glDepthFunc(GL.GL_LESS);
    gl.glFrontFace(GL.GL_CCW);    // default is 'CCW'
    gl.glEnable(GL.GL_CULL_FACE); // default is 'not enabled' so needs to be enabled
    gl.glCullFace(GL.GL_BACK);   // default is 'back', assuming CCW
    initialise(gl);
    startTime = getSeconds();
  }
  
  /* Called to indicate the drawing surface has been moved and/or resized  */
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL3 gl = drawable.getGL().getGL3();
    gl.glViewport(x, y, width, height);
    float aspect = (float)width/(float)height;
    camera.setPerspectiveMatrix(Mat4Transform.perspective(45, aspect));
  }

  /* Draw */
  public void display(GLAutoDrawable drawable) {
    GL3 gl = drawable.getGL().getGL3();
    render(gl);
  }

  /* Clean up memory */
  public void dispose(GLAutoDrawable drawable) {
    GL3 gl = drawable.getGL().getGL3();
    room.dispose(gl);
    light.dispose(gl);
  }

  // ***************************************************
  /* THE SCENE
   * Now define all the methods to handle the scene.
   * This will be added to in later examples.
   */

  private Room room;
  private Light light;
  private Texture[] texture;   // array of textures
  
  private final int T_CONTAINER_DIFFUSE = 0;
  private final int T_CONTAINER_SPECULAR = 1;

  private void loadTextures(GL3 gl) {
    texture = new Texture[2];
    texture[T_CONTAINER_DIFFUSE] = TextureLibrary.loadTexture(gl, "textures/container2.jpg");
    texture[T_CONTAINER_SPECULAR] = TextureLibrary.loadTexture(gl, "textures/container2_specular.jpg");
  }

  public void initialise(GL3 gl) {
    loadTextures(gl);
    light = new Light(gl);
    light.setCamera(camera);
    room = new Room(gl, camera, light);
  }
  
  public void render(GL3 gl) {
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    light.setPosition(getLightPosition());  // changing light position each frame
    light.render(gl);
    
    room.render(gl);
  }
  
  // The light's position is continually being changed, so needs to be calculated for each frame.
  private Vec3 getLightPosition() {
    double elapsedTime = getSeconds()-startTime;
    float x = 5.0f*(float)(Math.sin(Math.toRadians(elapsedTime*50)));
    float y = 3.4f;
    float z = 5.0f*(float)(Math.cos(Math.toRadians(elapsedTime*50)));
    return new Vec3(x,y,z);
  }
  
  private double startTime;
  
  private double getSeconds() {
    return System.currentTimeMillis()/1000.0;
  }
  
}
/**
// I've used an inner class here. A separate class would be better.
class Room {

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
} */