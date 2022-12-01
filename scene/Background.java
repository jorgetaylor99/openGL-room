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

public class Background {

  private Model background;
  private Camera camera;
  private Light light;
  private float size = 16f;
  private Texture t0, t1, texture_cloud;
  private double startTime;

  public Background(GL3 gl, Camera c, Light l) {
    camera = c;
    light = l;
    this.t0 = t0;
    this.t1 = t1;
    loadTextures(gl);
  }

  private void loadTextures(GL3 gl) {
    texture_cloud = TextureLibrary.loadTexture(gl, "textures/cloud.jpg");
  }

  // Background
  private Model makeBackWall(GL3 gl) {
    // grey basecolor with main colour given by texture map
    Vec3 basecolor = new Vec3(0.5f, 0.5f, 0.5f); // grey
    Material material = new Material(basecolor, basecolor, new Vec3(0.3f, 0.3f, 0.3f), 4.0f);
    Mat4 modelMatrix = new Mat4(1);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(size*2.0f,1f,size*2.0f), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(-90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,size*0.3f,size*1.0f), modelMatrix);
    Mesh mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    Shader shader = new Shader(gl, "shaders/vertex/background.glsl", "shaders/fragment/background.glsl");
    double elapsedTime = getSeconds() - startTime;
    double t = elapsedTime;  // *0.1 slows it down a bit
    float offsetX = (float)(t - Math.floor(t));
    float offsetY = 0.0f;
    shader.setFloat(gl, "offset", offsetX, offsetY);
    // System.out.println(offsetX);
    Model model = new Model(gl, camera, light, shader , material, modelMatrix, mesh, texture_cloud);
    return model;
  }
  
  private double getSeconds() {
    return System.currentTimeMillis()/1000.0;
  }

  public void render(GL3 gl) {
    background = makeBackWall(gl);
    background.render(gl);
  }

  public void dispose(GL3 gl) {
    background.dispose(gl);
  }
}