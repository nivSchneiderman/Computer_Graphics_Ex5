package edu.cg.models.Car;

import com.jogamp.opengl.GL2;

import edu.cg.models.IRenderable;
import edu.cg.models.SkewedBox;

public class Front implements IRenderable {
	// TODO: The front of the car is build from the following elements:
	private SkewedBox hoodBox1 = new SkewedBox(Specification.F_HOOD_LENGTH_1,Specification.F_HOOD_HEIGHT_1,Specification.F_HOOD_HEIGHT_2,Specification.F_HOOD_DEPTH_1,Specification.F_HOOD_DEPTH_2);
	private SkewedBox hoodBox2 = new SkewedBox(Specification.F_HOOD_LENGTH_2,Specification.F_HOOD_HEIGHT_2,Specification.F_BUMPER_HEIGHT_1,Specification.F_HOOD_DEPTH_2,Specification.F_HOOD_DEPTH_3);
	private SkewedBox bumperBox = new SkewedBox(Specification.F_BUMPER_LENGTH,Specification.F_BUMPER_HEIGHT_1,Specification.F_BUMPER_HEIGHT_2,Specification.F_BUMPER_DEPTH,Specification.F_BUMPER_DEPTH);
	private SkewedBox bumperWingsBox = new SkewedBox(Specification.F_BUMPER_LENGTH,Specification.F_BUMPER_WINGS_HEIGHT,Specification.F_BUMPER_HEIGHT_2,Specification.F_BUMPER_WINGS_DEPTH,Specification.F_BUMPER_WINGS_DEPTH);
	private PairOfWheels wheels = new PairOfWheels();


	@Override
	public void render(GL2 gl) {
		
		gl.glPushMatrix();
		Materials.SetRedMetalMaterial(gl);
		
		gl.glTranslated(-Specification.F_HOOD_DEPTH_1/3, 0, 0);
        hoodBox1.render(gl);
                
        gl.glTranslated(Specification.F_HOOD_TOTAL_LENGTH/2, 0.0, 0.0);
        hoodBox2.render(gl);
        
        gl.glTranslated(0.14375, 0, 0);
        gl.glPushMatrix();//save the middle point
        Materials.SetDarkRedMetalMaterial(gl);
        bumperBox.render(gl);

        Materials.SetRedMetalMaterial(gl);
        gl.glTranslated(0, 0, 0.17);
        bumperWingsBox.render(gl);

        gl.glPopMatrix();//go back to the center
        gl.glTranslated(0, 0, -0.17);
        bumperWingsBox.render(gl);
        
        gl.glPopMatrix();//go back to the center
        gl.glTranslated(0.125, Specification.TIRE_RADIUS/2, 0);
        wheels.render(gl);
        
        gl.glPopMatrix();
	}

	@Override
	public void init(GL2 gl) {
		// TODO Auto-generated method stub

	}

}
