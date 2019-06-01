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

		// draw the center
		center.render(gl);

		gl.glPushMatrix();
		// draw the back
		gl.glTranslated(-Specification.C_BASE_LENGTH/2-Specification.B_LENGTH/2, 0, 0);
		back.render(gl);
		gl.glPopMatrix();

		// draw the front
		gl.glTranslated(Specification.C_BASE_LENGTH/2+Specification.F_HOOD_LENGTH_1,0,0);
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
