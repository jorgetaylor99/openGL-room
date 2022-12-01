import camera.*;
import gmaths.*;
import light.*;
import scene.*;
import textures.*;
import structures.*;

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
  private Table table;
  private Light light;
  private Background background;
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
    table = new Table(gl, camera, light);
    background = new Background(gl, camera, light);
  }
  
  public void render(GL3 gl) {
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    light.setPosition(getLightPosition());  // changing light position each frame
    light.render(gl);
    
    room.render(gl);
    table.render(gl);

    background.render(gl);
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