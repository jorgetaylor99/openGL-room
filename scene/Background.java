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
  private float offsetX, offsetY;
  private Shader shader;

  public Background(GL3 gl, Camera c, Light l) {
    camera = c;
    light = l;
    this.t0 = t0;
    this.t1 = t1;
    loadTextures(gl);
    background = makeBackWall(gl);
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
    modelMatrix = Mat4.multiply(Mat4Transform.scale(size*1.5f,1f,size*1.5f), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(-90), modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,size*0.3f,size*0.55f), modelMatrix);
    Mesh mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    shader = new Shader(gl, "shaders/vertex/background.glsl", "shaders/fragment/background.glsl");
    Model model = new Model(gl, camera, light, shader , material, modelMatrix, mesh, texture_cloud);
    return model;
  }
  
  public void setBackground(GL3 gl, float[] offset) {
      offsetX = offset[0]; 
      offsetY = offset[1]; 
      shader.setFloat(gl, "offset", offsetX, offsetY);
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