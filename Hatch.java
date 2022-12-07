// import camera.*;

// import java.awt.*;
// import java.awt.event.*;
// import javax.swing.*;

// import com.jogamp.opengl.*;
// import com.jogamp.opengl.awt.GLCanvas;
// import com.jogamp.opengl.util.FPSAnimator;

// public class Hatch extends JFrame implements ActionListener {
  
//   private static final int WIDTH = 1024;
//   private static final int HEIGHT = 768;
//   private static final Dimension dimension = new Dimension(WIDTH, HEIGHT);
//   private GLCanvas canvas;
//   private GLEventListener glEventListener;
//   private final FPSAnimator animator; 
//   private Camera camera;

//   public static void main(String[] args) {
//     Hatch hatch = new Hatch("Hatch");
//     hatch .getContentPane().setPreferredSize(dimension);
//     hatch.pack();
//     hatch.setVisible(true);
//     hatch.canvas.requestFocusInWindow();
//   }

//   public Hatch(String textForTitleBar) {
//     super(textForTitleBar);
//     setUpCanvas();
//     getContentPane().add(canvas, BorderLayout.CENTER);
//     addWindowListener(new windowHandler());
//     animator = new FPSAnimator(canvas, 60);
//     animator.start();

//     JMenuBar menuBar=new JMenuBar();
//     this.setJMenuBar(menuBar);
//       JMenu fileMenu = new JMenu("File");
//         JMenuItem quitItem = new JMenuItem("Quit");
//         quitItem.addActionListener(this);
//         fileMenu.add(quitItem);
//     menuBar.add(fileMenu);
    
//     JPanel p = new JPanel();
//       JButton b = new JButton("camera X");
//       b.addActionListener(this);
//       p.add(b);
//       b = new JButton("camera Z");
//       b.addActionListener(this);
//       p.add(b);
//       b = new JButton("start");
//       b.addActionListener(this);
//       p.add(b);
//       b = new JButton("stop");
//       b.addActionListener(this);
//       p.add(b);
//       b = new JButton("increase X position");
//       b.addActionListener(this);
//       p.add(b);
//       b = new JButton("decrease X position");
//       b.addActionListener(this);
//       p.add(b);
//       b = new JButton("lowered arms");
//       b.addActionListener(this);
//       p.add(b);
//       b = new JButton("raised arms");
//       b.addActionListener(this);
//       p.add(b);
//     this.add(p, BorderLayout.SOUTH);
    
//     addWindowListener(new WindowAdapter() {
//       public void windowClosing(WindowEvent e) {
//         animator.stop();
//         remove(canvas);
//         dispose();
//         System.exit(0);
//       }
//     });
//   }

//   private void setUpCanvas() {
//     GLCapabilities glcapabilities = new GLCapabilities(GLProfile.get(GLProfile.GL3));
//     canvas = new GLCanvas(glcapabilities);
//     Camera camera = new Camera(Camera.DEFAULT_POSITION,
//         Camera.DEFAULT_TARGET, Camera.DEFAULT_UP);
//     glEventListener = new Hatch_GLEventListener(camera);
//     canvas.addGLEventListener(glEventListener);
//     canvas.addMouseMotionListener(new MyMouseInput(camera));
//     canvas.addKeyListener(new MyKeyboardInput(camera));
//   }

//   private class windowHandler extends WindowAdapter {
//     public void windowClosing(WindowEvent e) {
//       animator.stop();
//       remove(canvas);
//       dispose();
//       System.exit(0);
//     }
//   }
// }

// public void actionPerformed(ActionEvent e) {
//   if (e.getActionCommand().equalsIgnoreCase("camera X")) {
//     camera.setCamera(Camera.CameraType.X);
//     canvas.requestFocusInWindow();
//   }
//   else if (e.getActionCommand().equalsIgnoreCase("camera Z")) {
//     camera.setCamera(Camera.CameraType.Z);
//     canvas.requestFocusInWindow();
//   }
//   else if (e.getActionCommand().equalsIgnoreCase("start")) {
//     glEventListener.startAnimation();
//   }
//   else if (e.getActionCommand().equalsIgnoreCase("stop")) {
//     glEventListener.stopAnimation();
//   }
//   else if (e.getActionCommand().equalsIgnoreCase("increase X position")) {
//     glEventListener.incXPosition();
//   }
//   else if (e.getActionCommand().equalsIgnoreCase("decrease X position")) {
//     glEventListener.decXPosition();
//   }
//   else if (e.getActionCommand().equalsIgnoreCase("lowered arms")) {
//     glEventListener.loweredArms();
//   }
//   else if (e.getActionCommand().equalsIgnoreCase("raised arms")) {
//     glEventListener.raisedArms();
//   }
//   else if(e.getActionCommand().equalsIgnoreCase("quit"))
//     System.exit(0);
// }
  
// class MyKeyboardInput extends KeyAdapter  {
//   private Camera camera;
  
//   public MyKeyboardInput(Camera camera) {
//     this.camera = camera;
//   }
  
//   public void keyPressed(KeyEvent e) {
//     Camera.Movement m = Camera.Movement.NO_MOVEMENT;
//     switch (e.getKeyCode()) {
//       case KeyEvent.VK_LEFT:  m = Camera.Movement.LEFT;  break;
//       case KeyEvent.VK_RIGHT: m = Camera.Movement.RIGHT; break;
//       case KeyEvent.VK_UP:    m = Camera.Movement.UP;    break;
//       case KeyEvent.VK_DOWN:  m = Camera.Movement.DOWN;  break;
//       case KeyEvent.VK_A:  m = Camera.Movement.FORWARD;  break;
//       case KeyEvent.VK_Z:  m = Camera.Movement.BACK;  break;
//     }
//     camera.keyboardInput(m);
//   }
// }

// class MyMouseInput extends MouseMotionAdapter {
//   private Point lastpoint;
//   private Camera camera;
  
//   public MyMouseInput(Camera camera) {
//     this.camera = camera;
//   }
  
//     /**
//    * mouse is used to control camera position
//    *
//    * @param e  instance of MouseEvent
//    */    
//   public void mouseDragged(MouseEvent e) {
//     Point ms = e.getPoint();
//     float sensitivity = 0.001f;
//     float dx=(float) (ms.x-lastpoint.x)*sensitivity;
//     float dy=(float) (ms.y-lastpoint.y)*sensitivity;
//     //System.out.println("dy,dy: "+dx+","+dy);
//     if (e.getModifiers()==MouseEvent.BUTTON1_MASK)
//       camera.updateYawPitch(dx, -dy);
//     lastpoint = ms;
//   }

//   /**
//    * mouse is used to control camera position
//    *
//    * @param e  instance of MouseEvent
//    */  
//   public void mouseMoved(MouseEvent e) {   
//     lastpoint = e.getPoint(); 
//   }

// }
import gmaths.*;
import camera.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class Hatch extends JFrame implements ActionListener {
  
  private static final int WIDTH = 1024;
  private static final int HEIGHT = 768;
  private static final Dimension dimension = new Dimension(WIDTH, HEIGHT);
  private GLCanvas canvas;
  private Hatch_GLEventListener glEventListener;
  private final FPSAnimator animator; 
  private Camera camera;

  public static void main(String[] args) {
    Hatch b1 = new Hatch("Hatch - Jorge Taylor - acb20jt");
    b1.getContentPane().setPreferredSize(dimension);
    b1.pack();
    b1.setVisible(true);
  }

  public Hatch(String textForTitleBar) {
    super(textForTitleBar);
    GLCapabilities glcapabilities = new GLCapabilities(GLProfile.get(GLProfile.GL3));
    canvas = new GLCanvas(glcapabilities);
    camera = new Camera(Camera.DEFAULT_POSITION, Camera.DEFAULT_TARGET, Camera.DEFAULT_UP);
    glEventListener = new Hatch_GLEventListener(camera);
    canvas.addGLEventListener(glEventListener);
    canvas.addMouseMotionListener(new MyMouseInput(camera));
    canvas.addKeyListener(new MyKeyboardInput(camera));
    getContentPane().add(canvas, BorderLayout.CENTER);
    
    JMenuBar menuBar=new JMenuBar();
    this.setJMenuBar(menuBar);
      JMenu fileMenu = new JMenu("File");
        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(this);
        fileMenu.add(quitItem);
    menuBar.add(fileMenu);
    
    JPanel p = new JPanel();
      JButton b = new JButton("Light on");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("Light off");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("Lamp 1 Pose 1");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("Lamp 1 Pose 2");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("Lamp 1 Pose 3");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("Lamp 2 Pose 1");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("Lamp 2 Pose 2");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("Lamp 2 Pose 3");
      b.addActionListener(this);
      p.add(b);
    this.add(p, BorderLayout.SOUTH);
    
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        animator.stop();
        remove(canvas);
        dispose();
        System.exit(0);
      }
    });
    animator = new FPSAnimator(canvas, 60);
    animator.start();
  }
  
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equalsIgnoreCase("Light on")) {
      System.out.println("Light on");
    }
    else if (e.getActionCommand().equalsIgnoreCase("Light off")) {
      System.out.println("Light off");
    }
    else if (e.getActionCommand().equalsIgnoreCase("Lamp 1 Pose 1")) {
      // glEventListener.startAnimation();
      glEventListener.lamp1pose1();
    }
    else if (e.getActionCommand().equalsIgnoreCase("Lamp 1 Pose 2")) {
      // glEventListener.stopAnimation();
      glEventListener.lamp1pose2();
    }
    else if (e.getActionCommand().equalsIgnoreCase("Lamp 1 Pose 3")) {
      // glEventListener.incXPosition();
      glEventListener.lamp1pose3();
    }
    else if (e.getActionCommand().equalsIgnoreCase("Lamp 2 Pose 1")) {
      // glEventListener.decXPosition();
      System.out.println("4");
    }
    else if (e.getActionCommand().equalsIgnoreCase("Lamp 2 Pose 2")) {
      // glEventListener.loweredArms();
      System.out.println("5");
    }
    else if (e.getActionCommand().equalsIgnoreCase("Lamp 2 Pose 3")) {
      // glEventListener.raisedArms();
      System.out.println("6");
    }
    else if(e.getActionCommand().equalsIgnoreCase("quit"))
      System.exit(0);
  }
  
}
 
class MyKeyboardInput extends KeyAdapter  {
  private Camera camera;
  
  public MyKeyboardInput(Camera camera) {
    this.camera = camera;
  }
  
  public void keyPressed(KeyEvent e) {
    Camera.Movement m = Camera.Movement.NO_MOVEMENT;
    switch (e.getKeyCode()) {
      case KeyEvent.VK_LEFT:  m = Camera.Movement.LEFT;  break;
      case KeyEvent.VK_RIGHT: m = Camera.Movement.RIGHT; break;
      case KeyEvent.VK_UP:    m = Camera.Movement.UP;    break;
      case KeyEvent.VK_DOWN:  m = Camera.Movement.DOWN;  break;
      case KeyEvent.VK_A:  m = Camera.Movement.FORWARD;  break;
      case KeyEvent.VK_Z:  m = Camera.Movement.BACK;  break;
    }
    camera.keyboardInput(m);
  }
}

class MyMouseInput extends MouseMotionAdapter {
  private Point lastpoint;
  private Camera camera;
  
  public MyMouseInput(Camera camera) {
    this.camera = camera;
  }
  
    /**
   * mouse is used to control camera position
   *
   * @param e  instance of MouseEvent
   */    
  public void mouseDragged(MouseEvent e) {
    Point ms = e.getPoint();
    float sensitivity = 0.001f;
    float dx=(float) (ms.x-lastpoint.x)*sensitivity;
    float dy=(float) (ms.y-lastpoint.y)*sensitivity;
    //System.out.println("dy,dy: "+dx+","+dy);
    if (e.getModifiers()==MouseEvent.BUTTON1_MASK)
      camera.updateYawPitch(dx, -dy);
    lastpoint = ms;
  }

  /**
   * mouse is used to control camera position
   *
   * @param e  instance of MouseEvent
   */  
  public void mouseMoved(MouseEvent e) {   
    lastpoint = e.getPoint(); 
  }
}
