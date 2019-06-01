package edu.cg.models.Car;

import com.jogamp.opengl.GL2;

import edu.cg.models.IRenderable;
import edu.cg.models.SkewedBox;

public class Back implements IRenderable {
	// TODO: This method is implemented. 
	//       Look at the implementation and understand it.
	private SkewedBox baseBox = new SkewedBox(Specification.B_BASE_LENGTH, Specification.B_BASE_HEIGHT,
			Specification.B_BASE_HEIGHT, Specification.B_BASE_DEPTH, Specification.B_BASE_DEPTH);
	private SkewedBox backBox = new SkewedBox(Specification.B_LENGTH, Specification.B_HEIGHT_1,
			Specification.B_HEIGHT_2, Specification.B_DEPTH_1, Specification.B_DEPTH_2);
	private PairOfWheels wheels = new PairOfWheels();
	private Spolier spoiler = new Spolier();

	@Override
	public void render(GL2 gl) {
		gl.glPushMatrix();
		
		// Render backbox
		Materials.SetRedMetalMaterial(gl);
		gl.glTranslated(0, Specification.B_BASE_HEIGHT, 0);
		backBox.render(gl);
		
		// Render basebox
		Materials.SetBlackMetalMaterial(gl);
		gl.glTranslated(Specification.B_LENGTH/2-Specification.B_BASE_LENGTH/2, -Specification.B_BASE_HEIGHT, 0);
		baseBox.render(gl);
		gl.glTranslated(0, Specification.B_BASE_HEIGHT, 0);
		
		// Render wheels
		gl.glTranslated(-Specification.B_BASE_LENGTH/2-Specification.TIRE_RADIUS, Specification.B_HEIGHT_1/4, 0);
		wheels.render(gl);
		
		// Render spolier
		gl.glTranslated(0, Specification.B_HEIGHT_1*3/4,0);
		spoiler.render(gl);

		gl.glPopMatrix();
	}

	@Override
	public void init(GL2 gl) {
		// TODO Auto-generated method stub

	}

}
