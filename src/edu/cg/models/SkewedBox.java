package edu.cg.models;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES3;

public class SkewedBox implements IRenderable {
	
	private double length, height1, height2, depth1, depth2;

	public SkewedBox() {   //Default constructor
		length  = 0.1;
		height1 = 0.2;
		height2 = 0.1;
		depth1  = 0.2;
		depth2  = 0.1;
	};

	public SkewedBox(double length, double h1, double h2, double d1, double d2) {
		this.length = length;
		this.height1 = h1;
		this.height2 = h2;
		this.depth1 = d1;
		this.depth2 = d2;
	}

	@Override
	public void render(GL2 gl) {
		                
		//front sqaure 
		gl.glBegin(gl.GL_QUADS);  
        gl.glVertex3d(length / 2, 0,  depth2 / 2);
        gl.glVertex3d(length / 2, 0, -depth2 / 2);
        gl.glVertex3d(length / 2, height2, -depth2 / 2);
        gl.glVertex3d(length / 2, height2,  depth2 / 2);
        gl.glEnd();
        
        //back box
        gl.glBegin(gl.GL_QUADS);
        gl.glVertex3d(-length / 2,    0   , -depth1/2);
        gl.glVertex3d(-length / 2,    0   ,  depth1/2);
        gl.glVertex3d(-length / 2, height1,  depth1/2);
        gl.glVertex3d(-length / 2, height1, -depth1/2);
        gl.glEnd();
        
        //upper box
        gl.glBegin(gl.GL_QUADS);
        gl.glVertex3d(-length / 2, height1,  depth1 / 2);
        gl.glVertex3d( length / 2, height2,  depth2 / 2);
        gl.glVertex3d( length / 2, height2, -depth2 / 2);
        gl.glVertex3d(-length / 2, height1, -depth1 / 2);
        gl.glEnd();
        
        //lower box 
        gl.glBegin(gl.GL_QUADS);
        gl.glVertex3d(-length / 2, 0,  depth1 / 2);
        gl.glVertex3d(-length / 2, 0, -depth1 / 2);
        gl.glVertex3d( length / 2, 0, -depth2 / 2);
        gl.glVertex3d( length / 2, 0,  depth2 / 2);
        gl.glEnd();
        
        //right box
        gl.glBegin(gl.GL_QUADS);
        gl.glVertex3d(-length / 2, height1, depth1 / 2);
        gl.glVertex3d(-length / 2, 0, depth1 / 2);
        gl.glVertex3d( length / 2, 0, depth2 / 2);
        gl.glVertex3d( length / 2, height2, depth2 / 2);
        gl.glEnd();
              
        //left box
        gl.glBegin(gl.GL_QUADS);
        gl.glVertex3d(-length / 2, 0, -depth1 / 2);
        gl.glVertex3d(-length / 2, height1, -depth1 / 2);
        gl.glVertex3d( length / 2, height2, -depth2 / 2);
        gl.glVertex3d( length / 2, 0, -depth2 / 2);
        gl.glEnd();
	}

	@Override
	public void init(GL2 gl) {
       	
	}
	
	@Override
	public String toString() {
		return "SkewedBox";
	}

}








    


