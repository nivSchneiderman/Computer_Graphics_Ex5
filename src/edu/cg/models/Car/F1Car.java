package edu.cg.models.Car;

import com.jogamp.opengl.*;

import edu.cg.models.IRenderable;

/**
 * A F1 Racing Car.
 *
 */
public class F1Car implements IRenderable {

	@Override
	public void render(GL2 gl) {

	Back back = new Back();
	Center center = new Center();
	Front front = new Front();
	
	 //draw the center 	
	 center.render(gl);
	 gl.glPushMatrix();
	
	 //draw the back 
	 gl.glTranslated(-Specification.B_BASE_DEPTH, 0, 0);
     back.render(gl);
     gl.glPopMatrix();
     gl.glPushMatrix();
     
     //draw the front
     gl.glTranslated(Specification.F_HOOD_LENGTH_1 + 0.1, 0, 0);
     front.render(gl);
     gl.glPopMatrix();
     
	}

	@Override
	public String toString() {
		return "F1Car";
	}

	@Override
	public void init(GL2 gl) {

	}
}
