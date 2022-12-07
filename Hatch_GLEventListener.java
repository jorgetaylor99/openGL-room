import camera.*;
import gmaths.*;
import light.*;
import nodes.*;
import scene.*;
import textures.*;
import structures.*;

import java.nio.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    this.camera.setTarget(new Vec3(0.0f,4.0f,0.0f));
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
    light.dispose(gl);
    room.dispose(gl);
    background.dispose(gl);
    table.dispose(gl);
    egg.dispose(gl);
  }

  // ***************************************************
  /* THE SCENE
   * Now define all the methods to handle the scene.
   * This will be added to in later examples.
   */

  private Room room;
  private Background background;
  private Table table;
  private Light light;
  private Egg egg;
  private Lamp lamp;
  
  private final int T_CONTAINER_DIFFUSE = 0;
  private final int T_CONTAINER_SPECULAR = 1;

  public void initialise(GL3 gl) {
    light = new Light(gl);
    light.setCamera(camera);
    room = new Room(gl, camera, light);
    background = new Background(gl, camera, light);
    table = new Table(gl, camera, light);
    egg = new Egg(gl, camera, light);
    lamp = new Lamp(gl, camera, light);
  }
  
  public void render(GL3 gl) {
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    light.setPosition(getLightPosition()); 
    light.render(gl);

    room.render(gl);

    background.setBackground(gl, getBackground());
    background.render(gl);

    table.render(gl);

    egg.setPosition(getEggPosition());
    egg.setRotation(getEggRotation(rotation));
    egg.render(gl);

    lamp.render(gl);
  }
  
  private double startTime;
  private boolean cycle = false;
  private double randomInterval;
  private double count;
  private float rotation = 0;

  // The light's position is continually being changed, so needs to be calculated for each frame.
  private Vec3 getLightPosition() {
    double elapsedTime = getSeconds()-startTime;
    float x = 5.0f*(float)(Math.sin(Math.toRadians(elapsedTime*50)));
    float y = 10.0f;
    float z = 5.0f*(float)(Math.cos(Math.toRadians(elapsedTime*50)));
    return new Vec3(x,y,z);
  }

  private float[] getBackground() {
    double elapsedTime = getSeconds()-startTime;
    double t = elapsedTime; // *0.1;
    float offsetX = 20 * (float)(t - Math.floor(t));
    float offsetY = 0.0f;
    return new float[] {offsetX, offsetY};
  }

  private Vec3 getEggPosition() {
    double elapsedTime = getSeconds()-startTime;
    float x = 0.0f;
    float y = 2.1f;
    float z = 0.0f;

    if (!cycle) {
       randomInterval = elapsedTime + (Math.random() * 5 + 3);
      cycle = true;
    }

    if ((elapsedTime > randomInterval) && cycle) {
      count += 0.03;
      y += 0.25f * (1.0f + (float)(-Math.cos(Math.toRadians(count*500))));

      if ((y < 2.15f) && (count > 0.10)) {
        cycle = false;
        count = 0;
      }
    }

    return new Vec3(x,y,z);
  }

  // private void updateBranches() {
  //   double elapsedTime = getSeconds()-startTime;
  //   rotateAllAngle = rotateAllAngleStart*(float)Math.sin(elapsedTime);
  //   rotateUpperAngle = rotateUpperAngleStart*(float)Math.sin(elapsedTime*0.7f);
  //   rotateAll.setTransform(Mat4Transform.rotateAroundZ(rotateAllAngle));
  //   rotateUpper.setTransform(Mat4Transform.rotateAroundZ(rotateUpperAngle));
  //   twoBranchRoot.update(); // IMPORTANT – the scene graph has changed
  // }

  private float getEggRotation(float r) {
    double elapsedTime = getSeconds()-startTime;
    float rotation = r;

    if ((elapsedTime > randomInterval) && cycle) {
      rotation += 30 * (float)Math.toRadians(elapsedTime*500);
    }
    // System.out.println(rotation);
    return rotation;
  }

  public void lamp1pose1() {
    lamp.lamp1pose1();
  }

  public void lamp1pose2() {
    lamp.lamp1pose2();
  }

  public void lamp1pose3() {
    lamp.lamp1pose3();
  }

  private double getSeconds() {
    return System.currentTimeMillis()/1000.0;
  }
  
}