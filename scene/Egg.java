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

public class Egg {

  private Model egg;
  private Camera camera;
  private Light light;
  private Vec3 position;
  private float rotation;
  private float size = 16f;
  private Texture t0, t1, texture_egg, texture_egg_specular;

  public Egg(GL3 gl, Camera c, Light l) {
    camera = c;
    light = l;
    this.t0 = t0;
    this.t1 = t1;
    loadTextures(gl);
    position = new Vec3(0.0f,2.1f,0.0f);
    egg = makeEgg(gl);
  }

  private void loadTextures(GL3 gl) {
    texture_egg = TextureLibrary.loadTexture(gl, "textures/egg.jpg");
    texture_egg_specular = TextureLibrary.loadTexture(gl, "textures/egg_specular.jpg");
  }

  private Model makeEgg(GL3 gl) {
    Mesh mesh = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
    Shader shader = new Shader(gl, "shaders/vertex/sphere.glsl", "shaders/fragment/sphere.glsl");
    Material material = new Material(new Vec3(1.0f, 0.5f, 0.31f), 
                            new Vec3(1.0f, 0.5f, 0.31f), 
                            new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    Mat4 modelMatrix = new Mat4(1);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(1.5f,2.5f,1.5f), 
                                Mat4Transform.translate(position));
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(rotation), modelMatrix);
    Model sphere = new Model(gl, camera, light, shader, material, modelMatrix, mesh, 
                        texture_egg, texture_egg_specular);
    return sphere;
  }

  public void setPosition(Vec3 v) {
    position.x = v.x;
    position.y = v.y;
    position.z = v.z;
  }

  public void setRotation(float r) {
    rotation = r;
  }

  private double startTime;
  
  private double getSeconds() {
    return System.currentTimeMillis()/1000.0;
  }

  public void render(GL3 gl) {
    egg = makeEgg(gl);
    egg.render(gl);
  }

  public void dispose(GL3 gl) {
    egg.dispose(gl);
  }
}