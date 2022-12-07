package scene;

import gmaths.*;
import camera.*;
import nodes.*;
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

public class Lamp {

  private Model sphere, cube;
  private Camera camera;
  private Light light;
  private float size = 16f;
  private Texture t0, t1, texture_tiger, texture_zebra;
  private SGNode lampRoot;
  private TransformNode translateX, rotateAll, rotateUpper, rotateUpper2, rotateUpper3;
  private float xPosition = 10;
  private float rotateAllAngle = 0;
  private float rotateLowerAngle = -15;
  private float rotateUpperAngle = 35;
  private float rotateHeadAngle = 0;
  private Vec3 lamp1pos = new Vec3(5f, 0f, 0f);

  public Lamp(GL3 gl, Camera c, Light l) {
    camera = c;
    light = l;
    this.t0 = t0;
    this.t1 = t1;
    loadTextures(gl);
    lampRoot = makeLamp(gl);
  }

  private void loadTextures(GL3 gl) {
    texture_tiger = TextureLibrary.loadTexture(gl, "textures/tiger.jpg");
    texture_zebra = TextureLibrary.loadTexture(gl, "textures/zebra.jpg");
  }

  private SGNode makeLamp(GL3 gl) {
    Mesh mesh = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
    Shader shader = new Shader(gl, "shaders/vertex/sphere.glsl", "shaders/fragment/sphere.glsl");
    Material material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    Mat4 modelMatrix = Mat4.multiply(Mat4Transform.scale(4,4,4), Mat4Transform.translate(0,0.5f,0));
    sphere = new Model(gl, camera, light, shader, material, modelMatrix, mesh, texture_zebra);
    
    mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "shaders/vertex/cube.glsl", "shaders/fragment/cube.glsl");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(4,4,4), Mat4Transform.translate(0,0.5f,0));
    cube = new Model(gl, camera, light, shader, material, modelMatrix, mesh, texture_zebra);
    
    // cube2 = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId5, textureId6); 
    
    lampRoot = new NameNode("lamp structure");
    // translateX = new TransformNode("translate("+lamp1pos.x+",0,0)", Mat4Transform.translate(xPosition,0,0));
    translateX = new TransformNode("translate"+lamp1pos, Mat4Transform.translate(lamp1pos));
    rotateAll = new TransformNode("rotateAroundY("+rotateAllAngle+")", Mat4Transform.rotateAroundY(rotateAllAngle));

    //base
    NameNode lowerBranch = new NameNode("base");
    Mat4 m = Mat4Transform.scale(1f,0.5f,1f);
    m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
    TransformNode makeLowerBranch = new TransformNode("scale(1,0.5,1); translate(0,0.5,0)", m);
    ModelNode cube0Node = new ModelNode("Cube(0)", cube);

    //lower
    TransformNode translateToTop = new TransformNode("translate(0,0.5,0)",Mat4Transform.translate(0,0.5f,0));
    rotateUpper = new TransformNode("rotateAroundZ("+rotateLowerAngle+")",Mat4Transform.rotateAroundZ(rotateLowerAngle));
    NameNode upperBranch = new NameNode("lower arm");
    m = Mat4Transform.scale(0.3f,3f,0.3f);
    m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
    TransformNode makeUpperBranch = new TransformNode("scale(0.3f,3f,0.3f);translate(0,0.5,0)", m);
    ModelNode cube1Node = new ModelNode("Cube(1)", cube);

    //upper
    TransformNode translateToTop2 = new TransformNode("translate(0,2.8f,0)",Mat4Transform.translate(0,2.8f,0));
    rotateUpper2 = new TransformNode("rotateAroundZ("+rotateUpperAngle+")",Mat4Transform.rotateAroundZ(rotateUpperAngle));
    NameNode upperBranch2 = new NameNode("upper arm");
    m = Mat4Transform.scale(0.3f,3f,0.3f);
    m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
    TransformNode makeUpperBranch2 = new TransformNode("scale(0.3f,3f,0.3f);translate(0,0.5,0)", m);
    ModelNode cube2Node = new ModelNode("Cube(2)",cube);

    //head
    TransformNode translateToTop3 = new TransformNode("translate(0,3f,0)",Mat4Transform.translate(0,3f,0));
    rotateUpper3 = new TransformNode("rotateAroundZ("+rotateHeadAngle+")",Mat4Transform.rotateAroundZ(rotateHeadAngle));              
    NameNode upperBranch3 = new NameNode("head");
    m = Mat4Transform.scale(2f,0.75f,0.75f);
    m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
    TransformNode makeUpperBranch3 = new TransformNode("scale(0.5f,3f,0.5f);translate(0,0.5,0)", m); //update here
    ModelNode cube3Node = new ModelNode("Cube(3)",cube);

    lampRoot.addChild(translateX);
      translateX.addChild(rotateAll);
        rotateAll.addChild(lowerBranch);
          lowerBranch.addChild(makeLowerBranch);
            makeLowerBranch.addChild(cube0Node);
          lowerBranch.addChild(translateToTop);
            translateToTop.addChild(rotateUpper);
              rotateUpper.addChild(upperBranch);
                upperBranch.addChild(makeUpperBranch);
                  makeUpperBranch.addChild(cube1Node);
                upperBranch.addChild(translateToTop2);
                  translateToTop2.addChild(rotateUpper2);
                    rotateUpper2.addChild(upperBranch2);
                      upperBranch2.addChild(makeUpperBranch2);
                        makeUpperBranch2.addChild(cube2Node);
                      upperBranch2.addChild(translateToTop3);
                        translateToTop3.addChild(rotateUpper3);
                          rotateUpper3.addChild(upperBranch3);
                            upperBranch3.addChild(makeUpperBranch3);
                              makeUpperBranch3.addChild(cube3Node);

          // lowerBranch.addChild(translateToTop2);
          //   translateToTop2.addChild(rotateUpper2);
          //     rotateUpper2.addChild(upperBranch2);
          //       upperBranch2.addChild(makeUpperBranch2);
          //         makeUpperBranch2.addChild(cube2Node);

    lampRoot.update();  // IMPORTANT – must be done every time any part of the scene graph changes
    //twoBranchRoot.print(0, false);
    //System.exit(0);
    return lampRoot;
  }

  private double startTime;
  
  private double getSeconds() {
    return System.currentTimeMillis()/1000.0;
  }

  public void render(GL3 gl) {
    lampRoot = makeLamp(gl);
    lampRoot.draw(gl);
  }

  // default
  public void lamp1pose1() {
    rotateAllAngle = 0;
    rotateLowerAngle = -15;
    rotateUpperAngle = 35;
    rotateHeadAngle = 0;
    lamp1pos = new Vec3(5, 0, 0);
  }

  public void lamp1pose2() {
    rotateAllAngle = 45;
    rotateLowerAngle = -45;
    rotateUpperAngle = 90;
    rotateHeadAngle = -60;
    lamp1pos = new Vec3(3, 0, -3f);
  }

  public void lamp1pose3() {
    rotateAllAngle = -45;
    rotateLowerAngle = -75;
    rotateUpperAngle = 150;
    rotateHeadAngle = -70;
    lamp1pos = new Vec3(4, 0, 4);
  }

}