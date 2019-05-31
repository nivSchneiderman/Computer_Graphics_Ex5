package edu.cg.models.Car;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import edu.cg.models.IRenderable;

public class Wheel implements IRenderable {

	@Override
	public void render(GL2 gl) {
		
		GLU glu = new GLU();
        GLUquadric quad = glu.gluNewQuadric();
        Materials.setMaterialTire(gl);
        
        //cylinder
        gl.glPushMatrix();
        gl.glTranslated(0, 0, -Specification.TIRE_DEPTH/2);
        glu.gluCylinder(quad, Specification.TIRE_RADIUS, Specification.TIRE_RADIUS, Specification.TIRE_DEPTH, 30, 1);
        
        //rime for of the wheel 
        gl.glTranslated(0, 0, Specification.TIRE_DEPTH);
        glu.gluDisk(quad, 0.05, Specification.TIRE_RADIUS, 30, 1);
        Materials.setMaterialRims(gl);
        glu.gluDisk(quad, 0, 0.05, 30, 1);
       
        //second rime for of the wheel 
        Materials.setMaterialTire(gl);
        gl.glTranslated(0, 0, -Specification.TIRE_DEPTH);
        gl.glRotated(180.0, 1.0, 0.0, 0.0); //need to turn facing out 
        glu.gluDisk(quad, 0.05, Specification.TIRE_RADIUS, 30, 1);
        Materials.setMaterialRims(gl);
        glu.gluDisk(quad, 0, 0.05, 30, 1);
        
       gl.glPopMatrix();
       glu.gluDeleteQuadric(quad);
	}

	@Override
	public void init(GL2 gl) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public String toString() {
		return "Wheel";
	}

}
