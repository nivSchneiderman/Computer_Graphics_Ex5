package edu.cg;

import java.awt.Component;
import java.awt.Point;

import com.jogamp.opengl.*;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.FPSAnimator;

import edu.cg.algebra.Vec;
import edu.cg.models.IRenderable;

/**
 * An OpenGL model viewer
 *
 */
public class Viewer implements GLEventListener {
	private double zoom; // How much to zoom in? >0 mean come closer, <0 means get back. This will be
							// used to translate the whole seen relative to the camera.
	private Point mouseFrom, mouseTo; // From where to where was the mouse dragged between the last redraws?
	private int canvasWidth, canvasHeight;
	private boolean isWireframe = false; // Should we display wireframe or not?
	private boolean isLightEnabled = false; // Should we display lights or not?
	private boolean isAxes = true; // Should we display axes or not?
	private IRenderable model; // Model to display
	private FPSAnimator ani; // This object is responsible to redraw the model with a constant FPS
	// rotationMatrix is the stored rotation. You need to keep updating this matrix.
	private double rotationMatrix[] = { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 };
	private Component glPanel;
	private boolean isModelInitialized = false; // Whether model.init() was called.

	public Viewer(Component glPanel) {
		this.glPanel = glPanel;
		zoom = 0;
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		// TODO: This method is already implemented. But you should understand it very
		// well.
		// When this method is invoked, the model is basically render. Follow the
		// different calls
		// inside this method, since some of the methods (i.e setUpCamera), change the
		// state of OpenGL.
		GL2 gl = drawable.getGL().getGL2();
		if (!isModelInitialized) {
			initModel(gl);
			isModelInitialized = true;
		}

		// clear the window before drawing
		gl.glClearColor(1, 1, 1, 1);
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL2.GL_MODELVIEW);

		setupCamera(gl);
		if (isAxes)
			renderAxes(gl);

		setupLights(gl);

		if (isWireframe)
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
		else
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
		// Let us set the default color to red -
		// this color will be used to render primitives such as SkewedBox.
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		model.render(gl);

		// IMPORTANT: Polygon mode needs to be set back to GL_FILL to work around a bug
		// on some platforms.
		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
	}

	// ===========================needs work ============
	private Vec mousePointToVec(Point pt) {
		double x = (double) (2 * pt.x) / (double) this.canvasWidth - 1.0;
		double y = 1.0 - (double) (2 * pt.y) / (double) this.canvasHeight;
		double z2 = 2.0 - x * x - y * y;
		if (z2 < 0.0) {
			z2 = 0.0;
		}
		double z = Math.sqrt(z2);
		return new Vec(x, y, z).normalize();
	}
	// =========================needs work ===================

	private void setupCamera(GL2 gl) {
		gl.glLoadIdentity();
		final double distanceFromAxisIntersection = 1.2;

		if (mouseFrom != null && mouseTo != null) 
		{
			Vec fromVecToMouse = this.mousePointToVec(this.mouseFrom);
			Vec mouseToVec = this.mousePointToVec(this.mouseTo);
			Vec axis = fromVecToMouse.cross(mouseToVec);
			
			if (axis.normalize().isFinite())
			{
				double angle = Math.toDegrees(Math.acos(fromVecToMouse.dot(mouseToVec)));
				angle = Double.isFinite(angle) ? angle : 0;
				gl.glRotated(angle, (double)axis.x, (double)axis.y, (double)axis.z);
			}
		}
		
		gl.glMultMatrixd(this.rotationMatrix, 0);
		gl.glGetDoublev(GL2.GL_MODELVIEW0_MATRIX_EXT, this.rotationMatrix, 0);
		gl.glLoadIdentity();
		gl.glTranslated(0, 0, -distanceFromAxisIntersection);
		gl.glTranslated(0, 0, -this.zoom);
		gl.glMultMatrixd(this.rotationMatrix, 0);
		this.mouseFrom = null;
		this.mouseTo = null;
	}

	private void setupLights(GL2 gl) {
		// For this exercise we don't require lighting.
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		// Initialize display callback timer
		ani = new FPSAnimator(30, true);
		ani.add(drawable);
		glPanel.repaint();

		initModel(gl);
	}

	public void initModel(GL2 gl) {
		gl.glCullFace(GL2.GL_BACK); // Set Culling Face To Back Face
		gl.glEnable(GL2.GL_CULL_FACE); // Enable back face culling

		gl.glEnable(GL2.GL_NORMALIZE);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glLightModelf(GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_TRUE);
		if (isLightEnabled)
			gl.glEnable(GL2.GL_LIGHTING);
		gl.glLineWidth(4);

		model.init(gl);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// this is for resizing the window feature
		GL2 gl = drawable.getGL().getGL2();
        
		//reset the canvas   
		canvasWidth = width;
        canvasHeight = height;
        
        gl.glMatrixMode(GL2.GL_PROJECTION); 
        gl.glLoadIdentity();
        //clip the veiw plane - last values are the near and far 
        double r = 0.1;
        gl.glFrustum(-r, r, -r * (double)height / (double)width, r * (double)height / (double)width, r, 1500); 
	}

	/**
	 * Rotate model in a way that corresponds with a virtual trackball. This
	 * function is called whenever the mouse is dragged inside the window. The
	 * window is refreshed 30 times/sec, and if there are more than 1 mouse drag
	 * event between 2 refreshes, we just need to store the first and last points.
	 * 
	 * @param from 2D canvas point of drag beginning
	 * @param to   2D canvas point of drag ending
	 */
	public void trackball(Point from, Point to) {
		// The following lines store the rotation for use when next displaying the
		// model.
		// After you redraw the model, you should set these variables back to null.

		if (null == mouseFrom)
			mouseFrom = from;
		mouseTo = to;
		glPanel.repaint();

	}

	/**
	 * Zoom in or out of object. s<0 - zoom out. s>0 zoom in.
	 * 
	 * @param s Scalar
	 */
	public void zoom(double s) {
		zoom += s * 0.1;
		glPanel.repaint();
	}

	/**
	 * Toggle rendering method. Either wireframes (lines) or fully shaded
	 */
	public void toggleRenderMode() {
		isWireframe = !isWireframe;
		glPanel.repaint();
	}

	/**
	 * Toggle whether little spheres are shown at the location of the light sources.
	 */
	public void toggleLightSpheres() {
		isLightEnabled = !isLightEnabled;
		glPanel.repaint();
	}

	/**
	 * Toggle whether axes are shown.
	 */
	public void toggleAxes() {
		isAxes = !isAxes;
		glPanel.repaint();
	}

	/**
	 * Start redrawing the scene with 60 FPS
	 */
	public void startAnimation() {
		if (!ani.isAnimating())
			ani.start();
	}

	/**
	 * Stop redrawing the scene with 60 FPS
	 */
	public void stopAnimation() {
		if (ani.isAnimating())
			ani.stop();
	}

	private void renderAxes(GL2 gl) {
		gl.glLineWidth(2);
		boolean flag = gl.glIsEnabled(GL2.GL_LIGHTING);
		gl.glDisable(GL2.GL_LIGHTING);
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3d(1, 0, 0);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(1, 0, 0);

		gl.glColor3d(0, 1, 0);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(0, 1, 0);

		gl.glColor3d(0, 0, 1);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(0, 0, 1);

		gl.glEnd();
		if (flag)
			gl.glEnable(GL2.GL_LIGHTING);
	}

	public void setModel(IRenderable model) {
		this.model = model;
		isModelInitialized = false;
	}

}
